package com.qsoftware.forgemod.objects.items;

import com.qsoftware.forgemod.api.Action;
import com.qsoftware.forgemod.api.energy.IStrictEnergyHandler;
import com.qsoftware.forgemod.api.math.FloatingLong;
import com.qsoftware.forgemod.capability.EnergyStorageItemImpl;
import com.qsoftware.forgemod.common.TextColors;
import com.qsoftware.forgemod.objects.items.base.EnergyStoringItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
        super(properties, 1000, 1);
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
            if (magnetized) {
                if (entityIn instanceof PlayerEntity) {
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

                IEnergyStorage energyStorage = getEnergyStorage(stack);
                if (energyStorage != null) {
                    energyStorage.extractEnergy(1, false);
                }
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
