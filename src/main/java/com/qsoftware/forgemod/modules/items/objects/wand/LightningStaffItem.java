package com.qsoftware.forgemod.modules.items.objects.wand;

import com.qsoftware.forgemod.modules.items.objects.base.WandItem;
import com.qsoftware.forgemod.modules.ui.ModItemGroups;
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

    protected static BlockRayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.@NotNull FluidMode fluidMode) {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = 128;
        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
        return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }

    @Override
    public void activate(ItemStack stack, @NotNull World worldIn, @NotNull LivingEntity livingIn, float charge) {
        if (!(livingIn instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity playerIn = (PlayerEntity) livingIn;

        if (worldIn instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) worldIn;

            RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
            double posX = raytraceresult.getHitVec().x;
            double posY = Math.floor(raytraceresult.getHitVec().y);
            double posZ = raytraceresult.getHitVec().z;

            LightningBoltEntity l = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            l.setPosition(posX, posY, posZ);
            l.setEffectOnly(false);
            world.addEntity(l);

            playerIn.addStat(Stats.ITEM_USED.get(this));
        }
    }
}
