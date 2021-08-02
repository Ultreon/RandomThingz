package com.ultreon.randomthingz.block.machines.pipe;

import com.qsoftware.modlib.api.ConnectionType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class PipeNetwork implements IFluidHandler {
    private final IWorldReader dimension;
    private final Map<BlockPos, Set<Connection>> connections = new HashMap<>();
    private final FluidTank fluidTank;
    private boolean connectionsBuilt;

    private PipeNetwork(IWorldReader dimension, Set<BlockPos> wires) {
        this.dimension = dimension;
        wires.forEach(pos -> connections.put(pos, Collections.emptySet()));
        this.fluidTank = new FluidTank(1000);
    }

    static PipeNetwork buildNetwork(IWorldReader dimension, BlockPos pos) {
        Set<BlockPos> pipes = buildPipeSet(dimension, pos);
//        int energyStored = pipes.stream().mapToInt(p -> {
//            TileEntity tileEntity = dimension.getTileEntity(p);
//            return tileEntity instanceof PipeTileEntity ? ((PipeTileEntity) tileEntity).energyStored : 0;
//        }).sum();
        return new PipeNetwork(dimension, pipes);
    }

    private static Set<BlockPos> buildPipeSet(IWorldReader dimension, BlockPos pos) {
        return buildPipeSet(dimension, pos, new HashSet<>());
    }

    private static Set<BlockPos> buildPipeSet(IWorldReader dimension, BlockPos pos, Set<BlockPos> set) {
        // Get all positions that have a wire connected to the wire at pos
        set.add(pos);
        for (Direction side : Direction.values()) {
            BlockPos pos1 = pos.offset(side);
            if (!set.contains(pos1) && dimension.getTileEntity(pos1) instanceof PipeTileEntity) {
                set.add(pos1);
                set.addAll(buildPipeSet(dimension, pos1, set));
            }
        }
        return set;
    }

    @Nullable
    private static IFluidHandler getFluidHandler(IBlockReader dimension, BlockPos pos, Direction side) {
        TileEntity tileEntity = dimension.getTileEntity(pos.offset(side));
        if (tileEntity != null) {
            //noinspection ConstantConditions
            return tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite()).orElse(null);
        }
        return null;
    }

    public boolean contains(IWorldReader dimension, BlockPos pos) {
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

    private Set<Connection> getConnections(IBlockReader dimension, BlockPos pos) {
        // Get all connections for the wire at pos
        Set<Connection> connections = new HashSet<>();
        for (Direction direction : Direction.values()) {
            TileEntity te = dimension.getTileEntity(pos.offset(direction));
            if (te != null && !(te instanceof PipeTileEntity) && te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).isPresent()) {
                ConnectionType type = PipeBlock.getConnection(dimension.getBlockState(pos), direction);
                connections.add(new Connection(this, direction, type));
            }
        }
        return connections;
    }

    void moveFluids() {
        buildConnections();

        for (Map.Entry<BlockPos, Set<Connection>> entry : connections.entrySet()) {
            BlockPos pos = entry.getKey();
            Set<Connection> connections = entry.getValue();
            for (Connection con : connections) {
                if (con.type.canExtract()) {
                    IFluidHandler fluidHandler = getFluidHandler(dimension, pos, con.side);
                    if (fluidHandler != null) {
                        FluidStack toSend = fluidHandler.drain(10, FluidAction.SIMULATE);
                        if (!toSend.isEmpty()) {
                            if (fill(toSend, FluidAction.EXECUTE) > 0) {
                                break;
                            }
                        }
                    }
                }
            }
            for (Connection con : connections) {
                if (con.type.canReceive()) {
                    IFluidHandler fluidHandler = getFluidHandler(dimension, pos, con.side);
                    if (fluidHandler != null) {
                        FluidStack toSend = drain(10, FluidAction.SIMULATE);
                        if (!toSend.isEmpty()) {
                            if (fluidHandler.fill(toSend, FluidAction.EXECUTE) > 0) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("PipeNetwork %s, %d pipes", Integer.toHexString(hashCode()), connections.size());
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return fluidTank.getFluidInTank(tank);
    }

    @Override
    public int getTankCapacity(int tank) {
        return fluidTank.getTankCapacity(0);
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return fluidTank.isFluidValid(tank, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return fluidTank.fill(resource, action);
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return fluidTank.drain(resource, action);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return fluidTank.drain(maxDrain, action);
    }

    public static class Connection implements IFluidHandler {
        private final PipeNetwork network;
        private final Direction side;
        private final ConnectionType type;
        private final LazyOptional<Connection> lazyOptional;

        Connection(PipeNetwork network, Direction side, ConnectionType type) {
            this.network = network;
            this.side = side;
            this.type = type;
            this.lazyOptional = LazyOptional.of(() -> this);
        }

        public LazyOptional<Connection> getLazyOptional() {
            return lazyOptional;
        }

        @Override
        public int getTanks() {
            return 1;
        }

        @Nonnull
        @Override
        public FluidStack getFluidInTank(int tank) {
            return network.fluidTank.getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return network.fluidTank.getTankCapacity(tank);
        }

        @Override
        public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
            return network.fluidTank.isFluidValid(tank, stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!type.canReceive()) {
                return 0;
            }
            return network.fluidTank.fill(resource, action);
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (!type.canExtract()) {
                return FluidStack.EMPTY;
            }
            return network.fluidTank.drain(resource, action);
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (!type.canExtract()) {
                return FluidStack.EMPTY;
            }
            return network.fluidTank.drain(maxDrain, action);
        }
    }
}
