package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.common.enums.TextColors;
import com.ultreon.randomthingz.item.type.EnergyStoringItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Magnet item class.
 * Functionality to magnetize every item in an specific range.
 *
 * @author Qboi123
 */
public class MagnetItem extends EnergyStoringItem {
    public MagnetItem(Properties properties) {
        super(properties, 10_000, 1);
    }

    @Override
    public @NotNull
    InteractionResultHolder<ItemStack> use(@NotNull Level dimensionIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        CompoundTag nbt = stack.getOrCreateTagElement(Objects.requireNonNull(stack.getItem().getRegistryName()).getNamespace());
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
            playerIn.displayClientMessage(new TextComponent(TextColors.LIME + "Magnet is set to " + TextColors.GREEN + TextColors.BOLD + "ON"), true);
        } else {
            playerIn.displayClientMessage(new TextComponent(TextColors.LIGHT_RED + "Magnet is set to " + TextColors.RED + TextColors.BOLD + "OFF"), true);
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull Level dimensionIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        CompoundTag nbt = stack.getOrCreateTagElement(Objects.requireNonNull(stack.getItem().getRegistryName()).getNamespace());
        if (nbt.contains("magnetized")) {
            boolean magnetized = nbt.getBoolean("magnetized");
            if (magnetized) {
                if (entityIn instanceof Player) {
                    Player player = (Player) entityIn;

                    List<ItemEntity> itemEntities = player.level.getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(10));
                    for (ItemEntity item : itemEntities) {
                        double distX = player.getX() - item.getX();
                        double distZ = player.getZ() - item.getZ();
                        double distY = item.getY() + 1.5D - item.getY();
                        double dir = Math.atan2(distZ, distX);
                        double speed = 5F / item.distanceTo(player) * 0.5;
                        if (distY < 0) {
                            item.setDeltaMovement(item.getDeltaMovement().x, item.getDeltaMovement().y + speed, item.getDeltaMovement().z);
                        } else if (distY > 0) {
                            item.setDeltaMovement(item.getDeltaMovement().x, item.getDeltaMovement().y - speed, item.getDeltaMovement().z);
                        }
                        item.setDeltaMovement(Math.cos(dir) * speed, item.getDeltaMovement().y, item.getDeltaMovement().z);
                        item.setDeltaMovement(item.getDeltaMovement().x, item.getDeltaMovement().y, Math.sin(dir) * speed);
                    }
                }

                IEnergyStorage energyStorage = getEnergyStorage(stack);
                if (energyStorage != null) {
                    energyStorage.extractEnergy(1, false);
                }
            }
        }

        super.inventoryTick(stack, dimensionIn, entityIn, itemSlot, isSelected);
    }
}
