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
    public static final Stat<ResourceLocation> INTERACT_WITH_CRUSHER = registerCustomStat("interact_with_crusher");
    public static final Stat<ResourceLocation> INTERACT_WITH_COMPRESSOR = registerCustomStat("interact_with_compressor");
    public static final Stat<ResourceLocation> INTERACT_WITH_DRYING_RACK = registerCustomStat("interact_with_drying_rack");
    public static final Stat<ResourceLocation> INTERACT_WITH_ELECTRIC_FURNACE = registerCustomStat("interact_with_electric_furnace");

    @SuppressWarnings("SameParameterValue")
    private static Stat<ResourceLocation> registerCustomStat(String name) {
        ResourceLocation resourcelocation = new ResourceLocation(RandomThingz.MOD_ID, name);
        Registry.register(Registry.CUSTOM_STAT, name, resourcelocation);
        return Stats.CUSTOM.get(resourcelocation, StatFormatter.DEFAULT);
    }

    public static void register() {

    }
}
