package com.qtech.randomthingz.item.tools.trait;

import com.qtech.randomthingz.item.tools.ToolType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class HolyTrait extends AbstractTrait {
    public HolyTrait() {

    }

    @Override
    public float getSmiteValue(Set<ToolType> qfmToolTypes, ItemStack stack, LivingEntity attacker) {
        return 8.0f;
    }
}
