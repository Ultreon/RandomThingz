package com.ultreon.randomthingz.config;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.Module;
import com.ultreon.randomthingz.common.java.maps.OrderedHashMap;
import com.ultreon.randomthingz.util.ExceptionUtil;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@FieldsAreNonnullByDefault
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Config {
    public static final ForgeConfigSpec.BooleanValue showBetaWelcomeMessage;
    private static final ForgeConfigSpec.BooleanValue searchUpdatesOnStartup;
    private static final ForgeConfigSpec.BooleanValue searchForUnstableReleases;
    public static final ForgeConfigSpec.IntValue dimensionGenOilLakeChance;
    public static final ForgeConfigSpec.IntValue fluidGeneratorInjectionVolume;
    private static final ForgeConfigSpec commonSpec;

    @Deprecated
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<Module, ForgeConfigSpec.BooleanValue> modules = new OrderedHashMap<>();

    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

    @Deprecated
    public static final RTConfig conf = RTConfig.instance;

    static {
        // Beta welcome message.
        showBetaWelcomeMessage = builder
                .comment("Shows a message in chat warning the player that the mod is early in development")
                .define("general.showBetaWelcomeMessage", true);

        // Updates
        searchUpdatesOnStartup = builder
                .comment("Search for updates on startup.")
                .define("updates.searchOnStartup", false);
        searchForUnstableReleases = builder
                .comment("Search for unstable updates.")
                .comment("Places:")
                .comment("  ITEM:   Kill switch")
                .comment("  BUTTON: In the exit confirm screen")
                .define("updates.searchForUnstable", !RandomThingz.RT_VERSION.isStable());

        // ?
        fluidGeneratorInjectionVolume = builder
                .comment("The amount of fluid (in milliBuckets, or mB) fluid generators consume at once.",
                        "Lower values reduce waste, but may cause lag as the generator more frequently turns on/off.",
                        "A generator with less fluid in the tank will not be able to run.")
                .defineInRange("machine.fluidGenerators.injectionVolume", 100, 1, 1000);

        // Level Gen/Ore
        {
            builder.push("world");

            dimensionGenOilLakeChance = builder
                    .comment("Chance of oil lakes spawning (1 in chance). Higher numbers = less common. Set 0 to disable.",
                            "Water is 4, lava is 80. Oil lakes will spawn underground about 90% of the time.",
                            "Note that disabling oil will make some items uncraftable unless recipes are changed")
                    .defineInRange("oilLake.chance", 6, 0, Integer.MAX_VALUE);
        }

        commonSpec = builder.build();
    }

    private Config() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static void initialize() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
    }

    public static void sync() {

    }

    @SubscribeEvent
    public static void sync(ModConfigEvent.Loading event) {
        sync();
    }

    @SubscribeEvent
    public static void sync(ModConfigEvent.Reloading event) {
        sync();
    }

    public static void save() {
        commonSpec.save();
    }

    public static ForgeConfigSpec.BooleanValue getModuleSpec(Module module) {
        return modules.get(module);
    }

    @Deprecated
    public static class RTConfig {
        @Deprecated
        private static final RTConfig instance = new RTConfig();

        @Deprecated
        public UpdateConfig getUpdateConfig() {
            return UpdateConfig.instance;
        }

        @Deprecated
        public static class UpdateConfig {
            @Deprecated
            private static final UpdateConfig instance = new UpdateConfig();

            @Deprecated
            public boolean getCheckOnStartup() {
                return searchUpdatesOnStartup.get();
            }

            @Deprecated
            public void setCheckOnStartup(boolean check) {
                searchUpdatesOnStartup.set(check);
            }

            @Deprecated
            public boolean getAllowUnstable() {
                return searchForUnstableReleases.get();
            }

            @Deprecated
            public void setAllowUnstable(boolean check) {
                searchForUnstableReleases.set(check);
            }
        }
    }
}
