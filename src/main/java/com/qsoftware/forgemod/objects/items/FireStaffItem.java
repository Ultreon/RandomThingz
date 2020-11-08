package com.qsoftware.forgemod.objects.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
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
public class FireStaffItem extends Item {
    public FireStaffItem() {
        super(new Properties().group(ItemGroup.TOOLS));
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, @NotNull PlayerEntity playerIn, @NotNull Hand handIn) {
        if (worldIn instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) worldIn;

            FireballEntity l = new FireballEntity(EntityType.FIREBALL, worldIn);
            l.setPositionAndRotation(playerIn.getPosX(), playerIn.getPosY() + 1, playerIn.getPosZ(), playerIn.rotationYaw, playerIn.rotationPitch);
            Vector3d vector3d = playerIn.getLookVec();
            l.setMotion(vector3d);
            l.accelerationX = vector3d.x * 0.2D;
            l.accelerationY = vector3d.y * 0.2D;
            l.accelerationZ = vector3d.z * 0.2D;
//            l.setInvulnerable(true);
            l.setShooter(playerIn);

            world.addEntity(l);
        }
        
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        return ActionResult.resultSuccess(itemStack);
    }
}
