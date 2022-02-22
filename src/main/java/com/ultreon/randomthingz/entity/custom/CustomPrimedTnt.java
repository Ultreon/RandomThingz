package com.ultreon.randomthingz.entity.custom;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.CustomTntBlock;
import com.ultreon.randomthingz.common.TntProperties;
import com.ultreon.randomthingz.common.entity.ModEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class CustomPrimedTnt extends PrimedTnt {
    private final TntProperties properties;
    private @Nullable BlockState state;
    private static final Supplier<BlockState> defaultBlockState = Blocks.TNT::defaultBlockState;
    private static final TntProperties defaultProperties = TntProperties.builder().radius(4f).mode(Explosion.BlockInteraction.BREAK).fuse(80).build();

    public CustomPrimedTnt(@NotNull BlockState state, Level level) {
        super(ModEntities.CUSTOM_TNT.get(), level);

        Block block = state.getBlock();
        TntProperties properties = null;
        if (block instanceof CustomTntBlock<?> tntBlock) {
            properties = tntBlock.getTNTProperties();
        }

        this.state = state;
        this.properties = properties != null ? properties : defaultProperties;
    }

    public CustomPrimedTnt(@NotNull BlockState state, Level level, double x, double y, double z, @Nullable LivingEntity owner) {
        this(state, level);
        this.setPos(x, y, z);

        double d0 = level.random.nextDouble() * (double) ((float) Math.PI * 2f);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, .2f, -Math.cos(d0) * 0.02D);
        this.setFuse(properties.getFuse());
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.owner = owner;
    }

    /**
     * Get block
     *
     * @return block of the entity.
     * @deprecated Use {@linkplain BlockState#getBlock()} using {@linkplain #getState()} instead.
     */
    @Nullable
    @Deprecated
    public Block getBlock() {
        return state != null ? state.getBlock() : null;
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        compound.contains(RandomThingz.NBT_NAME);
        CompoundTag blockStateNbt = compound.getCompound(RandomThingz.NBT_NAME);
        BlockState state = NbtUtils.readBlockState(blockStateNbt);
        if (!(state.getBlock() instanceof CustomTntBlock<?>)) {
            Minecraft.getInstance().submit(this::discard);
        } else {
            this.state = state;
        }
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        CompoundTag qfmNbt = new CompoundTag();
        qfmNbt.put("BlockState", NbtUtils.writeBlockState(this.state == null ? defaultBlockState.get() : state));
        compound.put(RandomThingz.NBT_NAME, qfmNbt);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void explode() {
        Level dimension = this.level;
        BlockPos pos = this.getOnPos();
        BlockState state = this.state;
        Block blockBefore = Objects.requireNonNull(state).getBlock();
        if (blockBefore instanceof CustomTntBlock<?> customTNTBlock) {
            customTNTBlock.beforeExplosion(dimension, pos, state, this);
        }
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4f, Explosion.BlockInteraction.BREAK);
        Block blockAfter = state.getBlock();
        if (blockAfter instanceof CustomTntBlock<?> customTNTBlock) {
            customTNTBlock.afterExplosion(dimension, pos, state, this);
        }
    }

    public @NotNull BlockState getState() {
        return state == null ? defaultBlockState.get() : state;
    }

    public void setState(@NotNull BlockState state) {
        this.state = state;
    }
}
