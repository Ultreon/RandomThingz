package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import com.ultreon.randomthingz.common.interfaces.Sliceable;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemPage extends DebugPage {
    public ItemPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {
        if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            ItemStack stack = player.getMainHandItem();
            Item item = stack.getItem();
            FoodProperties food = item.getFoodProperties();
            CreativeModeTab group = item.getItemCategory();

            //noinspection ConstantConditions
            if (stack == null) {
                ctx.top("<No Item Information>");
                return;
            }

            ctx.left("Internal Name", stack.getItem().getRegistryName());
            ctx.left("Item Type Name", stack.getItem().getDescription().getString());
            if (stack.hasCustomHoverName()) {
                ctx.left("Custom name", stack.getHoverName().getString());
            }
            if (food != null) {
                ctx.left("Food Hunger Points", food.getNutrition());
                ctx.left("Food Saturation", food.getSaturationModifier());
            }
            if (group != null) {
                ctx.left("Creative Tab", group.getDisplayName().getString());
            }
            ctx.left("Rarity", item.getRarity(stack));
            ctx.left("Enchantability", item.getItemEnchantability(stack));
            ctx.left("Stack Limit", item.getItemStackLimit(stack));
            ctx.left("Max Damage", item.getMaxDamage(stack));
            ctx.left("Damage", item.getDamage(stack));
            ctx.left("Count", stack.getCount());
            ctx.left("Repair Cost", stack.getBaseRepairCost());
            ctx.left("Use Duration", stack.getUseDuration());
            ctx.left("XP Repair Ration", stack.getXpRepairRatio());

            ctx.right("Complex", item.isComplex());
            ctx.right("Immune To Fire", item.isFireResistant());
            ctx.right("Enchantable", item.isEnchantable(stack));
            ctx.right("Empty", stack.isEmpty());
            ctx.right("Is Piglin Currency", stack.isPiglinCurrency());
            ctx.right("Repairable", stack.isRepairable());
            ctx.right("Stackable", stack.isStackable());
            ctx.right("Sliceable", (item instanceof Sliceable));
            ctx.right("Damageable", item.canBeDepleted());
            ctx.right("Damaged", item.isDamaged(stack));
        }
    }
}
