package com.ultreon.randomthingz.modules.ui;

import com.ultreon.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

@UtilityClass
public class ModStats {
    private static final HashMap<String, IStatFormatter> PRE_REGISTER = new HashMap<>();

    public static final Stat<ResourceLocation> EAT_CHEESE_SLICE = registerCustomStat("eat_cheese_slice");
    public static final Stat<ResourceLocation> INFINITY_KILL = registerCustomStat("infinity_kill");

    @SuppressWarnings("SameParameterValue")
    private static Stat<ResourceLocation> registerCustomStat(String name) {
        ResourceLocation resourcelocation = new ResourceLocation(RandomThingz.MOD_ID, name);
        Registry.register(Registry.CUSTOM_STAT, name, resourcelocation);
        return Stats.CUSTOM.get(resourcelocation, IStatFormatter.DEFAULT);
    }

    public static void register() {

    }
}
