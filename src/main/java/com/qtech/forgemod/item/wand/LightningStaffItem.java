package com.qtech.forgemod.item.wand;

import com.qtech.forgemod.item.base.WandItem;
import com.qtech.forgemod.modules.ui.ModItemGroups;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.stats.Stats;
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
public class LightningStaffItem extends WandItem {
    public LightningStaffItem() {
        super(5, 1, new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC));
    }

    protected static BlockRayTraceResult rayTrace(World dimensionIn, PlayerEntity player, RayTraceContext.@NotNull FluidMode fluidMode) {
        final float f = player.rotationPitch;
        final float f1 = player.rotationYaw;
        final Vector3d vec3d = player.getEyePosition(1.0F);
        final float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        final float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        final float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
        final float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));
        final float f6 = f3 * f4;
        final float f7 = f2 * f4;
        final double reach = 128;
        final Vector3d vec3d1 = vec3d.add((double) f6 * reach, (double) f5 * reach, (double) f7 * reach);
        return dimensionIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }

    @Override
    public void activate(ItemStack stack, @NotNull World dimensionIn, @NotNull LivingEntity livingIn, float charge) {
        if (!(livingIn instanceof PlayerEntity)) {
            return;
        }

        final PlayerEntity playerIn = (PlayerEntity) livingIn;

        if (dimensionIn instanceof ServerWorld) {
            final ServerWorld dimension = (ServerWorld) dimensionIn;

            final RayTraceResult raytraceresult = rayTrace(dimensionIn, playerIn, RayTraceContext.FluidMode.ANY);
            final double posX = raytraceresult.getHitVec().x;
            final double posY = Math.floor(raytraceresult.getHitVec().y);
            final double posZ = raytraceresult.getHitVec().z;

            final LightningBoltEntity l = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, dimensionIn);
            l.setPosition(posX, posY, posZ);
            l.setEffectOnly(false);
            dimension.spawnEntity(l);

            playerIn.addStat(Stats.ITEM_USED.get(this));
        }
    }
}
