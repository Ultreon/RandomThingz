package com.qsoftware.forgemod.objects.items;

import com.qsoftware.forgemod.common.TextColors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class MagnetItem extends Item {
    public MagnetItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        CompoundNBT nbt = stack.getOrCreateChildTag(Objects.requireNonNull(stack.getItem().getRegistryName()).getNamespace());
        boolean magnetized;
        if (nbt.contains("magnetized")) {
            magnetized = nbt.getBoolean("magnetized");
            magnetized = !magnetized;
            nbt.putBoolean("magnetized", magnetized);
        } else {
            magnetized = true;
            nbt.putBoolean("magnetized", true);
        }

        if (magnetized) {
            playerIn.sendStatusMessage(new StringTextComponent(TextColors.LIME + "Magnet is set to " + TextColors.GREEN + TextColors.BOLD + "ON"), true);
        } else {
            playerIn.sendStatusMessage(new StringTextComponent(TextColors.LIGHT_RED + "Magnet is set to " + TextColors.RED + TextColors.BOLD + "OFF"), true);
        }

        return ActionResult.resultSuccess(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull World worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        CompoundNBT nbt = stack.getOrCreateChildTag(Objects.requireNonNull(stack.getItem().getRegistryName()).getNamespace());
        if (nbt.contains("magnetized")) {
            boolean magnetized = nbt.getBoolean("magnetized");
            if (magnetized && entityIn instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entityIn;

                List<ItemEntity> itemEntities = player.world.getEntitiesWithinAABB(ItemEntity.class, player.getBoundingBox().grow(10));
                for (ItemEntity item : itemEntities) {
                    double distX = player.getPosX() - item.getPosX();
                    double distZ = player.getPosZ() - item.getPosZ();
                    double distY = item.getPosY() + 1.5D - item.getPosY();
                    double dir = Math.atan2(distZ, distX);
                    double speed = 5F / item.getDistance(player) * 0.5;
                    if (distY < 0) {
                        item.setMotion(item.getMotion().x, item.getMotion().y + speed, item.getMotion().z);
                    } else if (distY > 0) {
                        item.setMotion(item.getMotion().x, item.getMotion().y - speed, item.getMotion().z);
                    }
                    item.setMotion(Math.cos(dir) * speed, item.getMotion().y, item.getMotion().z);
                    item.setMotion(item.getMotion().x, item.getMotion().y, Math.sin(dir) * speed);
                }
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
