package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.common.enums.TextColors;
import com.ultreon.randomthingz.common.item.ModCreativeTabs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
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
        super(new Properties().tab(ModCreativeTabs.OVERPOWERED).rarity(Rarity.RARE).stacksTo(4));
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull Level dimensionIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        CompoundTag nbt = stack.getOrCreateTagElement("randomthingz");
        if (!nbt.contains("createTime")) {
            nbt.putLong("createTime", dimensionIn.getGameTime());
        } else {
            long createTime = nbt.getLong("createTime");
            if ((createTime + 200) < dimensionIn.getGameTime()) {
                trigger(stack, dimensionIn, entityIn, itemSlot);
            }
        }

        super.inventoryTick(stack, dimensionIn, entityIn, itemSlot, isSelected);
    }

    public void trigger(ItemStack stack, @NotNull Level dimensionIn, @NotNull Entity entityIn, int itemSlot) {
        entityIn.setSlot(itemSlot, ItemStack.EMPTY);
        dimensionIn.explode(entityIn, entityIn.getX(), entityIn.getY(), entityIn.getZ(), 8f * (float) stack.getCount(), true, Explosion.BlockInteraction.DESTROY);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        CompoundTag nbt = stack.getOrCreateTagElement("randomthingz");
        if (dimensionIn == null) {
            return;
        }

        if (nbt.contains("createTime")) {
            long createTime = nbt.getLong("createTime");
            double ticks = ((double) createTime + 200) - (double) dimensionIn.getGameTime();
            long seconds = Math.round(ticks / 20d);

            //////////////////////
            // (400 + 200) - 600
            // 600 - 600
            // 0
            //////////////////////
            // (400 + 200) - 400
            // 600 - 400
            // 200

            if ((createTime + 200) < dimensionIn.getGameTime()) {
                tooltip.add(new TextComponent(TextColors.RED + "" + TextColors.BOLD + "TOO LATE!"));
                super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
            } else if ((createTime + 150) < dimensionIn.getGameTime()) {
                tooltip.add(new TextComponent(TextColors.LIGHT_RED + "" + seconds + " seconds"));
                super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
            } else if ((createTime + 100) < dimensionIn.getGameTime()) {
                tooltip.add(new TextComponent(TextColors.GOLD + "" + seconds + " seconds"));
                super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
            } else if ((createTime + 50) < dimensionIn.getGameTime()) {
                tooltip.add(new TextComponent(TextColors.YELLOW + "" + seconds + " seconds"));
                super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
            } else {
                tooltip.add(new TextComponent(TextColors.LIME + "" + seconds + " seconds"));
                super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
            }
        }
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level dimension = entity.getCommandSenderWorld();

        CompoundTag nbt = stack.getOrCreateTagElement("randomthingz");
        if (!nbt.contains("createTime")) {
            nbt.putLong("createTime", dimension.getGameTime());
            entity.setInvulnerable(true);
        } else {
            long createTime = nbt.getLong("createTime");
            if ((createTime + 200) < dimension.getGameTime()) {
                dimension.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 8f * (float) stack.getCount(), true, Explosion.BlockInteraction.DESTROY);
                if (entity.isAlive()) {
                    entity.remove(false);
                }
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }
}
