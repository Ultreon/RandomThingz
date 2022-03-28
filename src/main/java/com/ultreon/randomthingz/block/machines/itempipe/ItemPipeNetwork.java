package com.ultreon.randomthingz.block.machines.itempipe;

import com.ultreon.modlib.api.ConnectionType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class ItemPipeNetwork implements IItemHandler {
    private final LevelReader dimension;
    private final Map<BlockPos, Set<Connection>> connections = new HashMap<>();
    private boolean connectionsBuilt;
    private final ItemContainer itemContainer;

    private ItemPipeNetwork(LevelReader dimension, Set<BlockPos> wires) {
        this.dimension = dimension;
        wires.forEach(pos -> connections.put(pos, Collections.emptySet()));
        this.itemContainer = new ItemContainer(250);
    }

    static ItemPipeNetwork buildNetwork(LevelReader dimension, BlockPos pos) {
        Set<BlockPos> pipes = buildPipeSet(dimension, pos);
//        int energyStored = pipes.stream().mapToInt(p -> {
//            BlockEntity tileEntity = dimension.getTileEntity(p);
//            return tileEntity instanceof PipeTileEntity ? ((PipeTileEntity) tileEntity).energyStored : 0;
//        }).sum();
        return new ItemPipeNetwork(dimension, pipes);
    }

    private static Set<BlockPos> buildPipeSet(LevelReader dimension, BlockPos pos) {
        return buildPipeSet(dimension, pos, new HashSet<>());
    }

    private static Set<BlockPos> buildPipeSet(LevelReader dimension, BlockPos pos, Set<BlockPos> set) {
        // Get all positions that have a wire connected to the wire at pos
        set.add(pos);
        for (Direction side : Direction.values()) {
            BlockPos pos1 = pos.relative(side);
            if (!set.contains(pos1) && dimension.getBlockEntity(pos1) instanceof ItemPipeBlockEntity) {
                set.add(pos1);
                set.addAll(buildPipeSet(dimension, pos1, set));
            }
        }
        return set;
    }

    @Nullable
    private static IItemHandler getItemHandler(BlockGetter dimension, BlockPos pos, Direction side) {
        BlockEntity tileEntity = dimension.getBlockEntity(pos.relative(side));
        if (tileEntity != null) {
            //noinspection ConstantConditions
            return tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()).orElse(null);
        }
        return null;
    }

    public boolean contains(LevelReader dimension, BlockPos pos) {
        return this.dimension == dimension && connections.containsKey(pos);
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
            connections.keySet().forEach(p -> connections.put(p, getConnections(dimension, p)));
            connectionsBuilt = true;
        }
    }

    private Set<Connection> getConnections(BlockGetter dimension, BlockPos pos) {
        // Get all connections for the wire at pos
        Set<Connection> connections = new HashSet<>();
        for (Direction direction : Direction.values()) {
            BlockEntity te = dimension.getBlockEntity(pos.relative(direction));
            if (te != null && !(te instanceof ItemPipeBlockEntity) && te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
                ConnectionType type = ItemPipeBlock.getConnection(dimension.getBlockState(pos), direction);
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
                    IItemHandler itemHandler = getItemHandler(dimension, pos, con.side);
                    if (itemHandler != null) {
                        ItemStack toSend = itemHandler.extractItem(0, 10, true);
//                        ItemCapabilityUtils.trySendItems(4, itemHandler, this);
                        if (!toSend.isEmpty()) {
                            if (insertItem(0, toSend, false).getCount() > 0) {
                                break;
                            }
                        }
                    }
                }
            }
            for (Connection con : connections) {
                if (con.type.canReceive()) {
                    IItemHandler itemHandler = getItemHandler(dimension, pos, con.side);
                    if (itemHandler != null) {
                        ItemStack toSend = extractItem(0, 10, true);
                        if (!toSend.isEmpty()) {
                            if (itemHandler.insertItem(0, toSend, false).getCount() > 0) {
                                break;
                            }
                        }
//                        ItemCapabilityUtils.trySendItems(4, this, itemHandler);
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
    public boolean isItemValid(int tank, @NotNull ItemStack stack) {
        return itemContainer.isItemValid(tank, stack);
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @NotNull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return itemContainer.getStackInSlot(slot);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        return itemContainer.insertItem(slot, stack, simulate);
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return itemContainer.extractItem(slot, amount, simulate);
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
        public boolean isItemValid(int tank, @NotNull ItemStack stack) {
            return network.itemContainer.isItemValid(tank, stack);
        }

        @Override
        public int getSlots() {
            return 1;
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            return network.itemContainer.getStack();
        }

        @NotNull
        @Override
        public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (!type.canReceive()) {
                return ItemStack.EMPTY;
            }
            return network.itemContainer.insertItem(slot, stack, simulate);
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (!type.canExtract()) {
                return ItemStack.EMPTY;
            }
            return network.itemContainer.extractItem(slot, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    }
}
