package com.qsoftware.mcscript;

import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class World {
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
    public List<Entity> getAllEntities() {
        AbstractChunkProvider chunkProvider = wrapper.getChunkProvider();
        if (!(chunkProvider instanceof ServerChunkProvider)) {
            throw new ClassCastException("Chunk provider cannot be cast to server variant.");
        }
        List<ChunkHolder> chunkHolders = Lists.newArrayList(((ServerChunkProvider) chunkProvider).chunkManager.getLoadedChunksIterable().iterator());
        List<Entity> entities = new ArrayList<>();
        for (ChunkHolder chunkHolder : chunkHolders) {
            if (chunkHolder.isAccessible()) {
                Chunk chunk = chunkHolder.getChunkIfComplete();
                if (chunk != null) {
                    for (ClassInheritanceMultiMap<net.minecraft.entity.Entity> list : chunk.getEntityLists()){
                        for (net.minecraft.entity.Entity entity : list) {
                            entities.add(new Entity(entity));
                        }
                    }
                }
            }
        }

        return entities;
    }
}
