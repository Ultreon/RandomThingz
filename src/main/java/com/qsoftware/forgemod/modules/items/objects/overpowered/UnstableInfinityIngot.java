package com.qsoftware.forgemod.modules.items.objects.overpowered;

import com.qsoftware.forgemod.common.enums.TextColors;
import com.qsoftware.forgemod.modules.ui.ModItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Unstable infinity ingot item class.
 *
 * @author Qboi123
 */
public class UnstableInfinityIngot extends Item {
    public UnstableInfinityIngot() {
        super(new Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.RARE).maxStackSize(4));
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull World worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (!nbt.contains("createTime")) {
            nbt.putLong("createTime", worldIn.getGameTime());
        } else {
            long createTime = nbt.getLong("createTime");
            if ((createTime + 200) < worldIn.getGameTime()) {
                trigger(stack, worldIn, entityIn, itemSlot);
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    public void trigger(ItemStack stack, @NotNull World worldIn, @NotNull Entity entityIn, int itemSlot) {
        entityIn.replaceItemInInventory(itemSlot, ItemStack.EMPTY);
        worldIn.createExplosion(entityIn, entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ(), 8f * (float) stack.getCount(), true, Explosion.Mode.DESTROY);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (worldIn == null) {
            return;
        }

        if (nbt.contains("createTime")) {
            long createTime = nbt.getLong("createTime");
            double ticks = ((double) createTime + 200) - (double) worldIn.getGameTime();
            long seconds = Math.round(ticks / 20d);

            //////////////////////
            // (400 + 200) - 600
            // 600 - 600
            // 0
            //////////////////////
            // (400 + 200) - 400
            // 600 - 400
            // 200

            if ((createTime + 200) < worldIn.getGameTime()) {
                tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "TOO LATE!"));
                super.addInformation(stack, worldIn, tooltip, flagIn);
            } else if ((createTime + 150) < worldIn.getGameTime()) {
                tooltip.add(new StringTextComponent(TextColors.LIGHT_RED + "" + seconds + " seconds"));
                super.addInformation(stack, worldIn, tooltip, flagIn);
            } else if ((createTime + 100) < worldIn.getGameTime()) {
                tooltip.add(new StringTextComponent(TextColors.GOLD + "" + seconds + " seconds"));
                super.addInformation(stack, worldIn, tooltip, flagIn);
            } else if ((createTime + 50) < worldIn.getGameTime()) {
                tooltip.add(new StringTextComponent(TextColors.YELLOW + "" + seconds + " seconds"));
                super.addInformation(stack, worldIn, tooltip, flagIn);
            } else {
                tooltip.add(new StringTextComponent(TextColors.LIME + "" + seconds + " seconds"));
                super.addInformation(stack, worldIn, tooltip, flagIn);
            }
        }
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        World world = entity.getEntityWorld();

        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (!nbt.contains("createTime")) {
            nbt.putLong("createTime", world.getGameTime());
            entity.setInvulnerable(true);
        } else {
            long createTime = nbt.getLong("createTime");
            if ((createTime + 200) < world.getGameTime()) {
                world.createExplosion(entity, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 8f * (float) stack.getCount(), true, Explosion.Mode.DESTROY);
                if (entity.isAlive()) {
                    entity.remove(false);
                }
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
