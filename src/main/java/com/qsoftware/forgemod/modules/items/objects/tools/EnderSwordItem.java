package com.qsoftware.forgemod.modules.items.objects.tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

/**
 * Lightning staff item class.
 *
 * @author Qboi123
 */
public class EnderSwordItem extends SwordItem {
    public EnderSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    protected static BlockRayTraceResult rayTrace(World dimensionIn, PlayerEntity player, RayTraceContext.@NotNull FluidMode fluidMode) {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = 48;
        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return dimensionIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World dimensionIn, @NotNull PlayerEntity playerIn, @NotNull Hand handIn) {
        if (dimensionIn instanceof ServerWorld) {
            ServerWorld dimension = (ServerWorld) dimensionIn;

            RayTraceResult raytraceresult = rayTrace(dimensionIn, playerIn, RayTraceContext.FluidMode.ANY);
            double posX = raytraceresult.getHitVec().x;
            double posY = Math.floor(raytraceresult.getHitVec().y);
            double posZ = raytraceresult.getHitVec().z;

            for (int i = 0; i < 32; ++i) {
                playerIn.dimension.addParticle(ParticleTypes.PORTAL, posX, posY + playerIn.getRNG().nextDouble() * 2.0D, posZ, playerIn.getRNG().nextGaussian(), 0.0D, playerIn.getRNG().nextGaussian());
            }

            if (playerIn.isPassenger()) {
                playerIn.stopRiding();
            }

            playerIn.setPositionAndUpdate(posX, posY, posZ);
            playerIn.fallDistance = 0.0F;

            playerIn.addStat(Stats.ITEM_USED.get(this));
        }

        return super.onItemRightClick(dimensionIn, playerIn, handIn);
    }
}
