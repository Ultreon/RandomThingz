package com.ultreon.randomthingz.block.machines.wire;

import com.google.common.collect.Maps;
import com.ultreon.modlib.api.ConnectionType;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.api.IWrenchable;
import com.ultreon.randomthingz.util.EnergyUtils;
import com.ultreon.modlib.block.entity.Tickable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WireBlock extends PipeBlock implements IWrenchable, EntityBlock {
    public static final EnumProperty<ConnectionType> NORTH = EnumProperty.create("north", ConnectionType.class);
    public static final EnumProperty<ConnectionType> EAST = EnumProperty.create("east", ConnectionType.class);
    public static final EnumProperty<ConnectionType> SOUTH = EnumProperty.create("south", ConnectionType.class);
    public static final EnumProperty<ConnectionType> WEST = EnumProperty.create("west", ConnectionType.class);
    public static final EnumProperty<ConnectionType> UP = EnumProperty.create("up", ConnectionType.class);
    public static final EnumProperty<ConnectionType> DOWN = EnumProperty.create("down", ConnectionType.class);
    public static final Map<Direction, EnumProperty<ConnectionType>> FACING_TO_PROPERTY_MAP = Util.make(Maps.newEnumMap(Direction.class), (map) -> {
        map.put(Direction.NORTH, NORTH);
        map.put(Direction.EAST, EAST);
        map.put(Direction.SOUTH, SOUTH);
        map.put(Direction.WEST, WEST);
        map.put(Direction.UP, UP);
        map.put(Direction.DOWN, DOWN);
    });

    public WireBlock(Properties properties) {
        super(.125f, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, ConnectionType.NONE)
                .setValue(EAST, ConnectionType.NONE)
                .setValue(SOUTH, ConnectionType.NONE)
                .setValue(WEST, ConnectionType.NONE)
                .setValue(UP, ConnectionType.NONE)
                .setValue(DOWN, ConnectionType.NONE));
    }

    @Nullable
    private static Direction getClickedConnection(Vec3 relative) {
        if (relative.x < 0.25)
            return Direction.WEST;
        if (relative.x > 0.75)
            return Direction.EAST;
        if (relative.y < 0.25)
            return Direction.DOWN;
        if (relative.y > 0.75)
            return Direction.UP;
        if (relative.z < 0.25)
            return Direction.NORTH;
        if (relative.z > 0.75)
            return Direction.SOUTH;
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> BlockState cycleProperty(BlockState state, Property<T> propertyIn) {
        T value = getAdjacentValue(propertyIn.getPossibleValues(), state.getValue(propertyIn));
        if (value == ConnectionType.NONE)
            value = (T) ConnectionType.IN;
        return state.setValue(propertyIn, value);
    }

    private static <T> T getAdjacentValue(Iterable<T> p_195959_0_, @Nullable T p_195959_1_) {
        return Util.findNextInIterable(p_195959_0_, p_195959_1_);
    }

    private static ConnectionType createConnection(BlockGetter level, BlockPos pos, Direction side, ConnectionType current) {
        BlockEntity tileEntity = level.getBlockEntity(pos.relative(side));
        if (tileEntity instanceof WireBlockEntity) {
            return ConnectionType.BOTH;
        } else if (tileEntity != null) {
            IEnergyStorage energy = EnergyUtils.getEnergyFromSideOrNull(tileEntity, side.getOpposite());
            if (energy != null) {
                if (energy.canExtract()) {
                    return current == ConnectionType.NONE ? ConnectionType.IN : current;
                } else if (energy.canReceive()) {
                    return current == ConnectionType.NONE ? ConnectionType.OUT : current;
                }
            }
        }
        return ConnectionType.NONE;
    }

    public static ConnectionType getConnection(BlockState state, Direction side) {
        return state.getValue(FACING_TO_PROPERTY_MAP.get(side));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WireBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return Tickable::blockEntity;
    }

    @Override
    public InteractionResult onWrench(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level dimension = context.getLevel();
        BlockState state = dimension.getBlockState(pos);
        Vec3 relative = context.getClickLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        RandomThingz.LOGGER.debug("onWrench: {}", relative);

        Direction side = getClickedConnection(relative);
        if (side != null) {
            BlockEntity other = dimension.getBlockEntity(pos.relative(side));
            if (!(other instanceof WireBlockEntity)) {
                BlockState state1 = cycleProperty(state, FACING_TO_PROPERTY_MAP.get(side));
                dimension.setBlock(pos, state1, 18);
                WireConnection.invalidateNetwork(dimension, pos);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.makeConnections(context.getLevel(), context.getClickedPos());
    }

    public BlockState makeConnections(BlockGetter level, BlockPos pos) {
        return this.defaultBlockState()
                .setValue(DOWN, createConnection(level, pos, Direction.DOWN, ConnectionType.NONE))
                .setValue(UP, createConnection(level, pos, Direction.UP, ConnectionType.NONE))
                .setValue(NORTH, createConnection(level, pos, Direction.NORTH, ConnectionType.NONE))
                .setValue(EAST, createConnection(level, pos, Direction.EAST, ConnectionType.NONE))
                .setValue(SOUTH, createConnection(level, pos, Direction.SOUTH, ConnectionType.NONE))
                .setValue(WEST, createConnection(level, pos, Direction.WEST, ConnectionType.NONE));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (level.getBlockEntity(facingPos) instanceof WireBlockEntity)
            WireConnection.invalidateNetwork(level, currentPos);

        EnumProperty<ConnectionType> property = FACING_TO_PROPERTY_MAP.get(facing);
        ConnectionType current = stateIn.getValue(property);
        return stateIn.setValue(property, createConnection(level, currentPos, facing, current));
    }

    @Override
    protected int getAABBIndex(BlockState state) {
        int i = 0;

        for (int j = 0; j < Direction.values().length; ++j) {
            if (state.getValue(FACING_TO_PROPERTY_MAP.get(Direction.values()[j])) != ConnectionType.NONE) {
                i |= 1 << j;
            }
        }

        return i;
    }
}
