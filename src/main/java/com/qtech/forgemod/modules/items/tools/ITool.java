package com.qtech.forgemod.modules.items.tools;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.modules.items.tools.trait.AbstractTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

public interface ITool {
    AbstractTrait[] getTraits();
    Set<ToolType> getQfmToolTypes();
}
