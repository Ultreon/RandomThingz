package com.ultreon.randomthingz.item.wand;

import com.ultreon.randomthingz.common.item.ModCreativeTabs;
import com.ultreon.randomthingz.item.WandItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Lightning staff item class.
 *
 * @author Qboi123
 */
@Deprecated
public class LightningStaffItem extends WandItem {
    public LightningStaffItem() {
        super(5, 1, new Properties().tab(ModCreativeTabs.OVERPOWERED).rarity(Rarity.EPIC));
    }

    protected static BlockHitResult rayTrace(Level dimensionIn, Player player, ClipContext.@NotNull Fluid fluidMode) {
        final float f = player.xRot;
        final float f1 = player.yRot;
        final Vec3 vec3d = player.getEyePosition(1.0f);
        final float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        final float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        final float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        final float f5 = Mth.sin(-f * ((float) Math.PI / 180F));
        final float f6 = f3 * f4;
        final float f7 = f2 * f4;
        final double reach = 128;
        final Vec3 vec3d1 = vec3d.add((double) f6 * reach, (double) f5 * reach, (double) f7 * reach);
        return dimensionIn.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, fluidMode, player));
    }

    @Override
    public void activate(ItemStack stack, @NotNull Level dimensionIn, @NotNull LivingEntity livingIn, float charge) {
        if (!(livingIn instanceof Player)) {
            return;
        }

        final Player playerIn = (Player) livingIn;

        if (dimensionIn instanceof ServerLevel) {
            final ServerLevel dimension = (ServerLevel) dimensionIn;

            final HitResult raytraceresult = rayTrace(dimensionIn, playerIn, ClipContext.Fluid.ANY);
            final double posX = raytraceresult.getLocation().x;
            final double posY = Math.floor(raytraceresult.getLocation().y);
            final double posZ = raytraceresult.getLocation().z;

            final LightningBolt l = new LightningBolt(EntityType.LIGHTNING_BOLT, dimensionIn);
            l.setPos(posX, posY, posZ);
            l.setVisualOnly(false);
            dimension.addFreshEntity(l);

            playerIn.awardStat(Stats.ITEM_USED.get(this));
        }
    }
}
