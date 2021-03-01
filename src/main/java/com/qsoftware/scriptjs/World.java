package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.LightType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class World implements Formattable {
    final net.minecraft.world.World wrapper;

    public World(net.minecraft.world.World world) {
        this.wrapper = world;
    }

    public void removeBlock(int x, int y, int z) {
        wrapper.removeBlock(new BlockPos(x, y, z), false);
    }

    public void removeBlock(BlockPos pos) {
        wrapper.removeBlock(pos, false);
    }
    public void setBlock(BlockPos pos, String block) {
        wrapper.setBlockState(pos, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(block))).getDefaultState());
    }
    public void setBlock(BlockPos pos, ResourceLocation rl) {
        wrapper.setBlockState(pos, Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(rl)).getDefaultState());
    }
    public BlockData getBlockData(BlockPos pos) {
        return new BlockData(wrapper.getBlockState(pos), wrapper, pos);
    }
    public BlockState getBlockState(BlockPos pos) {
        return new BlockState(wrapper.getBlockState(pos));
    }
    public Block getBlockAt(BlockPos pos) {
        return new BlockState(wrapper.getBlockState(pos)).getBlock();
    }
    public FluidData getFluidData(BlockPos pos) {
        return new FluidData(wrapper.getFluidState(pos), wrapper, pos);
    }
    public FluidState getFluidState(BlockPos pos) {
        return new FluidState(wrapper.getFluidState(pos));
    }
    public Fluid getFluidAt(BlockPos pos) {
        return new FluidState(wrapper.getFluidState(pos)).getFluid();
    }
    public Chunk getChunkAt(int chunkX, int chunkZ) {
        return new Chunk(wrapper.getChunkProvider().getChunk(chunkX, chunkZ, false));
    }
    public long getDayTime() {
        return wrapper.getDayTime() % 24000;
    }
    public long getGameTime() {
        return wrapper.getGameTime();
    }
    public DifficultyInstance getDifficultyForLocation(BlockPos pos) {
        return wrapper.getDifficultyForLocation(pos);
    }
    public ResourceLocation getDimensionKey() {
        return wrapper.getDimensionKey().getLocation();
    }
    public Entity getEntityByID(int id) {
        return Entity.getCorrectEntity(wrapper.getEntityByID(id));
    }
    public BlockPos getHeight(Heightmap.Type type, BlockPos pos) {
        return wrapper.getHeight(type, pos);
    }
    public int getHeight(Heightmap.Type type, int x, int z) {
        return wrapper.getHeight(type, x, z);
    }
    public int getLight(BlockPos pos) {
        return wrapper.getLight(pos);
    }
    public int getLightValue(BlockPos pos) {
        return wrapper.getLightValue(pos);
    }
    public int getLightFor(LightType lightType, BlockPos pos) {
        return wrapper.getLightFor(lightType, pos);
    }
    public int getLightSubtracted(BlockPos pos, int amount) {
        return wrapper.getLightSubtracted(pos, amount);
    }
    public int getNextMapId() {
        return wrapper.getNextMapId();
    }
    public int getMoodPhase() {
        return wrapper.getMoonPhase();
    }
    public float getMoodFactor() {
        return wrapper.getMoonFactor();
    }
    public int getMaxLightLevel() {
        return wrapper.getMaxLightLevel();
    }
    public double getMaxEntityRadius() {
        return wrapper.getMaxEntityRadius();
    }
    public List<Player> getPlayers() {
        return wrapper.getPlayers().stream().map(ServerPlayer::getCorrectEntity).collect(Collectors.toList());
    }

//    public List<Entity> getAllEntities() {
//        AbstractChunkProvider chunkProvider = wrapper.getChunkProvider();
//        if (!(chunkProvider instanceof ServerChunkProvider)) {
//            throw new ClassCastException("Chunk provider cannot be cast to server variant.");
//        }
//        List<ChunkHolder> chunkHolders = Lists.newArrayList(((ServerChunkProvider) chunkProvider).chunkManager.getLoadedChunksIterable().iterator());
//        List<Entity> entities = new ArrayList<>();
//        for (ChunkHolder chunkHolder : chunkHolders) {
//            if (chunkHolder.isAccessible()) {
//                Chunk chunk = chunkHolder.getChunkIfComplete();
//                if (chunk != null) {
//                    for (ClassInheritanceMultiMap<net.minecraft.entity.Entity> list : chunk.getEntityLists()){
//                        for (net.minecraft.entity.Entity entity : list) {
//                            entities.add(new Entity(entity));
//                        }
//                    }
//                }
//            }
//        }
//
//        return entities;
//    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("World", getDimensionKey());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("World", getDimensionKey());
    }
}
