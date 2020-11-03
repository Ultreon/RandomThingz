package com.qsoftware.forgemod.objects.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

public class LightningStaffItem extends Item {
    public LightningStaffItem() {
        super(new Item.Properties().group(ItemGroup.TOOLS));
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull PlayerEntity playerIn, @NotNull Hand handIn) {
        if (worldIn instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) worldIn;

            RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
            double posX = raytraceresult.getHitVec().x;
            double posY = Math.floor(raytraceresult.getHitVec().y);
            double posZ = raytraceresult.getHitVec().z;

            System.out.println(posX);
            System.out.println(posY);
            System.out.println(posZ);

            LightningBoltEntity l = new LightningBoltEntity(EntityType.LIGHTNING_BOLT, worldIn);
            l.setPosition(posX, posY, posZ);
            l.setEffectOnly(false);
            world.addEntity(l);
        }
        
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    protected static BlockRayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.@NotNull FluidMode fluidMode) {
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vector3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = 128; // Todo: test value, if it will lag again, then lower the value. ( Possible not needed ;) )
        Vector3d vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        return worldIn.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, fluidMode, player));
    }
}
