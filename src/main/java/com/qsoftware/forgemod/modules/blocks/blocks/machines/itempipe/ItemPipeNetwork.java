package com.qsoftware.forgemod.modules.blocks.blocks.machines.itempipe;

import com.qsoftware.forgemod.util.ItemCapabilityUtils;
import com.qsoftware.modlib.api.ConnectionType;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public final class ItemPipeNetwork implements IItemHandler {
    private final IWorldReader world;
    private final Map<BlockPos, Set<Connection>> connections = new HashMap<>();
    private boolean connectionsBuilt;
    private final ItemContainer itemTank;

    private ItemPipeNetwork(IWorldReader world, Set<BlockPos> wires) {
        this.world = world;
        wires.forEach(pos -> connections.put(pos, Collections.emptySet()));
        this.itemTank = new ItemContainer(250);
    }

    static ItemPipeNetwork buildNetwork(IWorldReader world, BlockPos pos) {
        Set<BlockPos> pipes = buildPipeSet(world, pos);
//        int energyStored = pipes.stream().mapToInt(p -> {
//            TileEntity tileEntity = world.getTileEntity(p);
//            return tileEntity instanceof PipeTileEntity ? ((PipeTileEntity) tileEntity).energyStored : 0;
//        }).sum();
        return new ItemPipeNetwork(world, pipes);
    }

    private static Set<BlockPos> buildPipeSet(IWorldReader world, BlockPos pos) {
        return buildPipeSet(world, pos, new HashSet<>());
    }

    private static Set<BlockPos> buildPipeSet(IWorldReader world, BlockPos pos, Set<BlockPos> set) {
        // Get all positions that have a wire connected to the wire at pos
        set.add(pos);
        for (Direction side : Direction.values()) {
            BlockPos pos1 = pos.offset(side);
            if (!set.contains(pos1) && world.getTileEntity(pos1) instanceof ItemPipeTileEntity) {
                set.add(pos1);
                set.addAll(buildPipeSet(world, pos1, set));
            }
        }
        return set;
    }

    @Nullable
    private static IItemHandler getItemHandler(IBlockReader world, BlockPos pos, Direction side) {
        TileEntity tileEntity = world.getTileEntity(pos.offset(side));
        if (tileEntity != null) {
            //noinspection ConstantConditions
            return tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()).orElse(null);
        }
        return null;
    }

    public boolean contains(IWorldReader world, BlockPos pos) {
        return this.world == world && connections.containsKey(pos);
    }

    public int getPipeCount() {
        return connections.size();
    }

    public Connection getConnection(BlockPos pos, Direction side) {
        if (connections.containsKey(pos)) {
            for (Connection connection : connections.get(pos)) {
                if (connection.side == side) {
                    return connection;
                }
            }
        }
        return new Connection(this, side, ConnectionType.NONE);
    }

    void invalidate() {
        connections.values().forEach(set -> set.forEach(con -> con.getLazyOptional().invalidate()));
    }

    private void buildConnections() {
        // Determine all connections. This will be done once the connections are actually needed.
        if (!connectionsBuilt) {
            connections.keySet().forEach(p -> connections.put(p, getConnections(world, p)));
            connectionsBuilt = true;
        }
    }

    private Set<Connection> getConnections(IBlockReader world, BlockPos pos) {
        // Get all connections for the wire at pos
        Set<Connection> connections = new HashSet<>();
        for (Direction direction : Direction.values()) {
            TileEntity te = world.getTileEntity(pos.offset(direction));
            if (te != null && !(te instanceof ItemPipeTileEntity) && te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
                ConnectionType type = ItemPipeBlock.getConnection(world.getBlockState(pos), direction);
                connections.add(new Connection(this, direction, type));
            }
        }
        return connections;
    }

    void moveItems() {
        buildConnections();

        for (Map.Entry<BlockPos, Set<Connection>> entry : connections.entrySet()) {
            BlockPos pos = entry.getKey();
            Set<Connection> connections = entry.getValue();
            for (Connection con : connections) {
                if (con.type.canExtract()) {
                    IItemHandler itemHandler = getItemHandler(world, pos, con.side);
                    if (itemHandler != null) {
//                        ItemStack toSend = itemHandler.extractItem(0, 10, true);
                        ItemCapabilityUtils.trySendItems(4, itemHandler, this);
//                        if (!toSend.isEmpty()) {
//                            if (insertItem(0, toSend, false).getCount() > 0) {
//                                break;
//                            }
//                        }
                    }
                }
            }
            for (Connection con : connections) {
                if (con.type.canReceive()) {
                    IItemHandler itemHandler = getItemHandler(world, pos, con.side);
                    if (itemHandler != null) {
//                        ItemStack toSend = extractItem(0, 10, true);
//                        if (!toSend.isEmpty()) {
//                            if (itemHandler.insertItem(0, toSend, false).getCount() > 0) {
//                                break;
//                            }
//                        }
                        ItemCapabilityUtils.trySendItems(4, this, itemHandler);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("ItemPipeNetwork %s, %d pipes", Integer.toHexString(hashCode()), connections.size());
    }

    @Override
    public boolean isItemValid(int tank, @Nonnull ItemStack stack) {
        return itemTank.isItemValid(tank, stack);
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return itemTank.getStackInSlot(slot);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return itemTank.insertItem(slot, stack, simulate);
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return itemTank.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    public static class Connection implements IItemHandler {
        private final ItemPipeNetwork network;
        private final Direction side;
        private final ConnectionType type;
        private final LazyOptional<Connection> lazyOptional;

        Connection(ItemPipeNetwork network, Direction side, ConnectionType type) {
            this.network = network;
            this.side = side;
            this.type = type;
            this.lazyOptional = LazyOptional.of(() -> this);
        }

        public LazyOptional<Connection> getLazyOptional() {
            return lazyOptional;
        }

        @Override
        public boolean isItemValid(int tank, @Nonnull ItemStack stack) {
            return network.itemTank.isItemValid(tank, stack);
        }

        @Override
        public int getSlots() {
            return 1;
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return network.itemTank.getStack();
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (!type.canReceive()) {
                return ItemStack.EMPTY;
            }
            return network.itemTank.insertItem(slot, stack, simulate);
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (!type.canExtract()) {
                return ItemStack.EMPTY;
            }
            return network.itemTank.extractItem(slot, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    }
}
