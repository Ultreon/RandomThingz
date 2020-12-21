package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.util.ExceptionUtil;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class ModStats {
    private static final HashMap<String, IStatFormatter> PRE_REGISTER = new HashMap<>();

    public static final ResourceLocation INFINITY_KILL = registerCustomStat("infinity_kill");

    private ModStats() {
        throw ExceptionUtil.utilityConstructor();
    }

    private static ResourceLocation registerCustomStat(String name) {
        ResourceLocation resourcelocation = new ResourceLocation(QForgeMod.MOD_ID, name);
        Registry.register(Registry.CUSTOM_STAT, name, resourcelocation);
        Stats.CUSTOM.get(resourcelocation, IStatFormatter.DEFAULT);
        return resourcelocation;
    }
}
