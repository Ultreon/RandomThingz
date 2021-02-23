package com.qsoftware.mcscript;

import net.minecraft.util.text.ITextComponent;

import java.util.UUID;

@SuppressWarnings("unused")
public class ItemEntity extends Entity {
    final net.minecraft.entity.item.ItemEntity wrapper;

    ItemEntity(net.minecraft.entity.item.ItemEntity entity) {
        super(entity);
        this.wrapper = entity;
    }

    public int getAge() {
        return wrapper.getAge();
    }

    public ItemStack getItem() {
        return new ItemStack(wrapper.getItem());
    }

    public UUID getOwnerId() {
        return wrapper.getOwnerId();
    }

    public UUID getThrowerId() {
        return wrapper.getThrowerId();
    }

    public ITextComponent getName() {
        return wrapper.getName();
    }
}
