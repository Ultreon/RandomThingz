package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ToolType;

@SuppressWarnings("unused")
public class ItemStack implements Formattable {
    final net.minecraft.item.ItemStack wrapper;

    public ItemStack(net.minecraft.item.ItemStack wrapper) {
        this.wrapper = wrapper;
    }

    public Item getItem() {
        return new Item(this.wrapper.getItem());
    }

    public int getCount() {
        return this.wrapper.getCount();
    }

    public int getDamage() {
        return this.wrapper.getDamage();
    }

    public int getMaxDamage() {
        return this.wrapper.getMaxDamage();
    }

    public float getDestroySpeed(BlockState state) {
        return this.wrapper.getDestroySpeed(state.wrapper);
    }

    public ITextComponent getDisplayName() {
        return this.wrapper.getDisplayName();
    }

    public EquipmentSlotType getEquipmentSlot() {
        return this.wrapper.getEquipmentSlot();
    }

    public int getHarvestLevel(ToolType tool, ServerPlayer player, BlockState state) {
        return this.wrapper.getHarvestLevel(tool, player.wrapper, state.wrapper);
    }

    public int getEnchantability() {
        return this.wrapper.getItemEnchantability();
    }

    public int getMaxStackSize() {
        return this.wrapper.getMaxStackSize();
    }

    public int getRepairCost() {
        return this.wrapper.getRepairCost();
    }

    public Rarity getRarity() {
        return this.wrapper.getRarity();
    }

    public UseAction getUseAction() {
        return this.wrapper.getUseAction();
    }

    public int getUseDuration() {
        return this.wrapper.getUseDuration();
    }

    public void addEnchantment(Enchantment enchantment, int level) {
        this.wrapper.addEnchantment(enchantment, level);
    }

    public void addAttributeModifier(Attribute attribute, AttributeModifier modifier, EquipmentSlotType slot) {
        this.wrapper.addAttributeModifier(attribute, modifier, slot);
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("ItemStack", wrapper.getItem().getRegistryName(), wrapper.getCount());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("ItemStack", wrapper.getItem().getRegistryName(), wrapper.getCount());
    }
}
