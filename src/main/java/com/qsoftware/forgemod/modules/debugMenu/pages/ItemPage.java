package com.qsoftware.forgemod.modules.debugMenu.pages;

import com.qsoftware.forgemod.common.interfaces.Sliceable;
import com.qsoftware.forgemod.modules.debugMenu.DebugEntry;
import com.qsoftware.forgemod.modules.debugMenu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemPage extends DebugPage {
    public ItemPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();

        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("PLAYER", null));
            return list;
        }

        ItemStack stack = player.getHeldItemMainhand();
        Item item = stack.getItem();
        Food food = item.getFood();
        ItemGroup group = item.getGroup();

        int i = 0;

        list.add(new DebugEntry("registryName", () -> stack.getItem().getRegistryName()));
        if (food != null) {
            list.add(new DebugEntry("foodHealing", food::getHealing));
            list.add(new DebugEntry("foodSaturation", food::getSaturation));
        }
        if (group != null) {
            list.add(new DebugEntry("groupName", () -> group.getGroupName().getString()));
        }
        list.add(new DebugEntry("rarity", () -> item.getRarity(stack)));
        list.add(new DebugEntry("enchantability", () -> item.getItemEnchantability(stack)));
        list.add(new DebugEntry("stackLimit", () -> item.getItemStackLimit(stack)));
        list.add(new DebugEntry("maxDamage", () -> item.getMaxDamage(stack)));
        list.add(new DebugEntry("damage", () -> item.getDamage(stack)));
        list.add(new DebugEntry("burnTime", () -> item.getBurnTime(stack)));
        list.add(new DebugEntry("count", stack::getCount));
        list.add(new DebugEntry("repairCost", stack::getRepairCost));
        list.add(new DebugEntry("useDuration", stack::getUseDuration));
        list.add(new DebugEntry("xpRepairRation", stack::getXpRepairRatio));
        return list;
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();

        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("PLAYER", null));
            return list;
        }

        ItemStack stack = player.getHeldItemMainhand();
        Item item = stack.getItem();
        Food food = item.getFood();
        ItemGroup group = item.getGroup();

        int j = 0;
        list.add(new DebugEntry("complex", item.isComplex()));
        list.add(new DebugEntry("immuneToFire", item.isImmuneToFire()));
        list.add(new DebugEntry("enchantable", item.isEnchantable(stack)));
        list.add(new DebugEntry("empty", stack.isEmpty()));
        list.add(new DebugEntry("isPiglinCurrency", stack.isPiglinCurrency()));
        list.add(new DebugEntry("repairable", stack.isRepairable()));
        list.add(new DebugEntry("stackable", stack.isStackable()));
        list.add(new DebugEntry("sliceable", (item instanceof Sliceable)));
        list.add(new DebugEntry("damageable", item.isDamageable()));
        list.add(new DebugEntry("damaged", item.isDamaged(stack)));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
