package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.util.text.ITextComponent;

import java.util.UUID;

@SuppressWarnings("unused")
public class ItemEntity extends Entity implements Formattable {
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

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("ItemEntity", getUUID());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("ItemEntity", getUUID());
    }
}
