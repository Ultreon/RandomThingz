package com.ultreon.randomthingz.entity.custom;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.CustomTNTBlock;
import com.ultreon.randomthingz.common.TNTProperties;
import com.ultreon.randomthingz.common.entity.ModEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.world.Explosion;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class CustomTNTEntity extends PrimedTnt {
    private final TNTProperties properties;
    private @Nullable BlockState blockState;
    private static final Supplier<BlockState> defaultBlockState = Blocks.TNT::defaultBlockState;
    private static final TNTProperties defaultProperties = TNTProperties.builder().radius(4.0f).mode(Explosion.Mode.BREAK).fuse(80).build();

    public CustomTNTEntity(@NotNull BlockState blockState, Level dimensionIn) {
        super(ModEntities.CUSTOM_TNT.get(), dimensionIn);

        Block block = blockState.getBlock();
        TNTProperties properties = null;
        if (block instanceof CustomTNTBlock<?>) {
            CustomTNTBlock<?> tntBlock = (CustomTNTBlock<?>) block;
            properties = tntBlock.getTNTProperties();
        }

        this.blockState = blockState;
        this.properties = properties != null ? properties : defaultProperties;
    }

    public CustomTNTEntity(@NotNull BlockState blockState, Level dimensionIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(blockState, dimensionIn);
        this.setPosition(x, y, z);

        double d0 = dimensionIn.rand.nextDouble() * (double) ((float) Math.PI * 2F);
        this.setMotion(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(properties.getFuse());
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.igniter = igniter;
    }

    /**
     * Get block
     *
     * @return block of the entity.
     * @deprecated Use {@linkplain BlockState#getBlock()} using {@linkplain #getBlockState()} instead.
     */
    @Nullable
    @Deprecated
    public Block getBlock() {
        return blockState != null ? blockState.getBlock() : null;
    }

    @Override
    public void readAdditional(@NotNull CompoundTag compound) {
        super.readAdditional(compound);

        compound.contains(RandomThingz.NBT_NAME);
        CompoundTag blockStateNbt = compound.getCompound(RandomThingz.NBT_NAME);
        BlockState state = NBTUtil.readBlockState(blockStateNbt);
        if (!(state.getBlock() instanceof CustomTNTBlock<?>)) {
            Minecraft.getInstance().runAsync(() -> this.remove(false));
        } else {
            this.blockState = state;
        }
    }

    @Override
    protected void writeAdditional(@NotNull CompoundTag compound) {
        super.writeAdditional(compound);

        CompoundTag qfmNbt = new CompoundTag();
        qfmNbt.put("BlockState", NBTUtil.writeBlockState(this.blockState == null ? defaultBlockState.get() : blockState));
        compound.put(RandomThingz.NBT_NAME, qfmNbt);
    }

    @Override
    public IPacket<?> getSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void explode() {
        Level dimension = this.dimension;
        BlockPos pos = this.getPosition();
        BlockState state = this.blockState;
        Block blockBefore = this.blockState.getBlock();
        if (blockBefore instanceof CustomTNTBlock<?>) {
            CustomTNTBlock<?> customTNTBlock = (CustomTNTBlock<?>) blockBefore;
            customTNTBlock.beforeExplosion(dimension, pos, state, this);
        }
        dimension.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(), properties.getRadius(), properties.isCausesFire(), properties.getMode());
        Block blockAfter = this.blockState.getBlock();
        if (blockAfter instanceof CustomTNTBlock<?>) {
            CustomTNTBlock<?> customTNTBlock = (CustomTNTBlock<?>) blockAfter;
            customTNTBlock.afterExplosion(dimension, pos, state, this);
        }
    }

    public @NotNull BlockState getBlockState() {
        return blockState == null ? defaultBlockState.get() : blockState;
    }

    public void setBlockState(@NotNull BlockState blockState) {
        this.blockState = blockState;
    }
}
