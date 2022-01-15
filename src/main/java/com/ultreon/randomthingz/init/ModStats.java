package com.ultreon.randomthingz.init;

import com.ultreon.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

import java.util.HashMap;

@UtilityClass
public class ModStats {
    private static final HashMap<String, StatFormatter> PRE_REGISTER = new HashMap<>();

    public static final Stat<ResourceLocation> EAT_CHEESE_SLICE = registerCustomStat("eat_cheese_slice");
    public static final Stat<ResourceLocation> INFINITY_KILL = registerCustomStat("infinity_kill");

    @SuppressWarnings("SameParameterValue")
    private static Stat<ResourceLocation> registerCustomStat(String name) {
        ResourceLocation resourcelocation = new ResourceLocation(RandomThingz.MOD_ID, name);
        Registry.register(Registry.CUSTOM_STAT, name, resourcelocation);
        return Stats.CUSTOM.get(resourcelocation, StatFormatter.DEFAULT);
    }

    public static void register() {

    }
}
