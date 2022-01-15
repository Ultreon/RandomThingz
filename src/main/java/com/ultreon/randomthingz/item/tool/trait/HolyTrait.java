package com.ultreon.randomthingz.item.tool.trait;

import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class HolyTrait extends AbstractTrait {
    public HolyTrait() {

    }

    @Override
    public TextColor getColor() {
        return TextColor.parseColor("#B0D4ED");
    }

    @Override
    public float getSmiteValue(Set<ToolType> qfmToolTypes, ItemStack stack, LivingEntity attacker) {
        return 8.0f;
    }
}
