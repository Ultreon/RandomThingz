package com.qsoftware.mcscript;

import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

@SuppressWarnings("unused")
public class BlockData {
    private BlockState state;
    private final IWorldReader worldReader;
    private final BlockPos pos;

    public BlockData(BlockState state, IWorldReader worldReader, BlockPos pos) {
        this.state = state;
        this.worldReader = worldReader;
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    public com.qsoftware.mcscript.BlockState getState() {
        return new com.qsoftware.mcscript.BlockState(state);
    }

    public float[] getBeaconColorMultiplier(BlockPos beacon) {
        return state.getBeaconColorMultiplier(worldReader, pos, beacon);
    }

    public float[] getBeaconColorMultiplier(BlockData beacon) {
        return state.getBeaconColorMultiplier(worldReader, pos, beacon.pos);
    }

    public float getHardness() {
        return state.getBlockHardness(worldReader, pos);
    }

    public int getExpDrop(int fortune, int silkTouch) {
        return state.getExpDrop(worldReader, pos, fortune, silkTouch);
    }

    public float getHardness(Explosion explosion) {
        return state.getExplosionResistance(worldReader, pos, explosion);
    }

    public float getEnchantPowerBonus() {
        return state.getEnchantPowerBonus(worldReader, pos);
    }

    public FluidState getFluidState() {
        return new FluidState(state.getFluidState());
    }

    public int getHarvestLevel() {
        return state.getHarvestLevel();
    }

    public ToolType getHarvestTool() {
        return state.getHarvestTool();
    }

    public int getLightValue() {
        return state.getLightValue();
    }

    public int getLightValueFromWorld() {
        return state.getLightValue(worldReader, pos);
    }

    public Material getMaterial() {
        return state.getMaterial();
    }

    public MaterialColor getMaterialColor() {
        return state.getMaterialColor(worldReader, pos);
    }

    public Vector3d getOffset() {
        return state.getOffset(worldReader, pos);
    }

    public int getOpacity() {
        return state.getOpacity(worldReader, pos);
    }

    public float getPlayerRelativeBlockHardness(Player player) {
        return state.getPlayerRelativeBlockHardness(player.wrapper, worldReader, pos);
    }

    public boolean requiresTool() {
        return state.getRequiresTool();
    }

    public float getSlipperiness(Entity entity) {
        return state.getSlipperiness(worldReader, pos, entity.wrapper);
    }

    public SoundType getSoundType(Entity entity) {
        return state.getSoundType(worldReader, pos, entity.wrapper);
    }

    public int getStrontPower(Direction direction) {
        return state.getStrongPower(worldReader, pos, direction);
    }

    public boolean getWeakChanges() {
        return state.getWeakChanges(worldReader,pos);
    }

    public int getWeakPower(Direction direction) {
        return state.getWeakPower(worldReader, pos, direction);
    }

    public boolean canBeReplacedByLeaves() {
        return state.canBeReplacedByLeaves(worldReader, pos);
    }

    public boolean canBeReplacedByLogs() {
        return state.canBeReplacedByLogs(worldReader, pos);
    }

    public boolean canConnectRedstone(Direction direction) {
        return state.canConnectRedstone(worldReader, pos, direction);
    }

    public boolean canCreatureSpawn(EntitySpawnPlacementRegistry.PlacementType type, EntityType entityType) {
        return state.canCreatureSpawn(worldReader, pos, type, entityType.wrapper);
    }

    public boolean canBeReplacedByLeaves(Entity entity) {
        return state.canEntityDestroy(worldReader, pos, entity.wrapper);
    }

    public boolean canDropFromExplosion(Explosion explosion) {
        return state.canDropFromExplosion(worldReader, pos, explosion);
    }

    public boolean canEntitySpawn(EntityType type) {
        return state.canEntitySpawn(worldReader, pos, type.wrapper);
    }

    public boolean canBeReplacedByLeaves(Player player) {
        return state.canHarvestBlock(worldReader, pos, player.wrapper);
    }

    public void remove() {
        if (worldReader instanceof World) {
            World world = (World) worldReader;
            world.removeBlock(pos, false);
        }
    }

    public void setBlock(Block block) {
        if (worldReader instanceof World) {
            World world = (World) worldReader;
            com.qsoftware.mcscript.BlockState defaultState = block.getDefaultState();
            if (world.setBlockState(pos, defaultState.wrapper)) {
                this.state = defaultState.wrapper;
            }
        }
    }

    public void setBlockState(com.qsoftware.mcscript.BlockState block) {
        if (worldReader instanceof World) {
            World world = (World) worldReader;
            if (world.setBlockState(pos, block.wrapper)) {
                this.state = block.wrapper;
            }
        }
    }
}
