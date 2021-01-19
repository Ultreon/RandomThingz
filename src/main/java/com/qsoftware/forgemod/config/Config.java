package com.qsoftware.forgemod.config;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.common.interfaces.Module;
import com.qsoftware.forgemod.common.java.maps.SequencedHashMap;
import com.qsoftware.forgemod.init.variants.Ore;
import com.qsoftware.forgemod.util.ExceptionUtil;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@FieldsAreNonnullByDefault
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Config {
    public static final ForgeConfigSpec.BooleanValue showBetaWelcomeMessage;
    public static final ForgeConfigSpec.BooleanValue closePrompt;
    public static final ForgeConfigSpec.BooleanValue closePromptIngame;
    public static final ForgeConfigSpec.BooleanValue closePromptQuitButton;
    public static final ForgeConfigSpec.BooleanValue quitOnEscInTitle;
    public static final ForgeConfigSpec.BooleanValue allowShutdownPC;
    public static final ForgeConfigSpec.BooleanValue searchUpdatesOnStartup;
    public static final ForgeConfigSpec.BooleanValue searchForUnstableReleases;
    public static final ForgeConfigSpec.IntValue worldGenOilLakeChance;
    public static final ForgeConfigSpec.IntValue fluidGeneratorInjectionVolume;
    private static final ForgeConfigSpec commonSpec;
    private static final ForgeConfigSpec.BooleanValue oreWorldGenMasterSwitch;
    private static final Map<Ore, OreConfig> oreConfigs = new EnumMap<>(Ore.class);

    private static final Map<Module, ForgeConfigSpec.BooleanValue> modules = new SequencedHashMap<>();

    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();


    static {
        // Beta welcome message.
        showBetaWelcomeMessage = builder
                .comment("Shows a message in chat warning the player that the mod is early in development")
                .define("general.showBetaWelcomeMessage", true);

        // Close prompt
        closePrompt = builder
                .comment("Shows an exit confirm screen when closing window.")
                .define("quit.closePrompt", true);
        closePromptIngame = builder
                .comment("Shows an exit confirm screen when closing window ingame.")
                .define("quit.closePromptIngame", true);
        closePromptQuitButton = builder
                .comment("Shows an exit confirm screen when pressing quit button in the title screen.")
                .define("quit.closePromptQuitButton", true);
        quitOnEscInTitle = builder
                .comment("Shows an exit confirm screen when pressed ESC in the title screen.")
                .define("quit.quitOnEscInTitle", true);

        // Permissions.
        allowShutdownPC = builder
                .comment("Allow QForgeMod to shutdown your pc on specific things.")
                .comment("Places:")
                .comment("  ITEM:   Kill switch")
                .comment("  BUTTON: In the exit confirm screen")
                .define("general.allowShutdownPC", false);

        // Updates
        searchUpdatesOnStartup = builder
                .comment("Search for updates on startup.")
                .define("updates.searchOnStartup", false);
        searchForUnstableReleases = builder
                .comment("Search for unstable updates.")
                .comment("Places:")
                .comment("  ITEM:   Kill switch")
                .comment("  BUTTON: In the exit confirm screen")
                .define("updates.searchForUnstable", !QForgeMod.VERSION.isStable());

        // ?
        fluidGeneratorInjectionVolume = builder
                .comment("The amount of fluid (in milliBuckets, or mB) fluid generators consume at once.",
                        "Lower values reduce waste, but may cause lag as the generator more frequently turns on/off.",
                        "A generator with less fluid in the tank will not be able to run.")
                .defineInRange("machine.fluidGenerators.injectionVolume", 100, 1, 1000);

        // World Gen/Ore
        {
            builder.push("world");
            oreWorldGenMasterSwitch = builder
                    .comment("Set to 'false' to completely disable ore generation from this mod, ignoring all other settings.",
                            "You can also enable/disable ores individually, but this is useful if you plan to use another mod for ore generation.")
                    .define("masterSwitch", true);

            worldGenOilLakeChance = builder
                    .comment("Chance of oil lakes spawning (1 in chance). Higher numbers = less common. Set 0 to disable.",
                            "Water is 4, lava is 80. Oil lakes will spawn underground about 90% of the time.",
                            "Note that disabling oil will make some items uncraftable unless recipes are changed")
                    .defineInRange("oilLake.chance", 6, 0, Integer.MAX_VALUE);

            builder.comment("Configs for specific ores. Set veinCount to zero to disable an ore.");
            builder.push("ores");
            Arrays.stream(Ore.values()).forEach(ore -> oreConfigs.put(ore, new OreConfig(ore, builder, oreWorldGenMasterSwitch)));

            builder.pop(2);
        }

        commonSpec = builder.build();
    }

    private Config() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static Optional<OreConfig> getOreConfig(Ore ore) {
        return Optional.ofNullable(oreConfigs.getOrDefault(ore, null));
    }

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
    }

    public static void sync() {
    }

    @SubscribeEvent
    public static void sync(ModConfig.Loading event) {
        sync();
    }

    @SubscribeEvent
    public static void sync(ModConfig.Reloading event) {
        sync();
    }

    public static void save() {
        commonSpec.save();
    }

    public static ForgeConfigSpec.BooleanValue getModuleSpec(Module module) {
        return modules.get(module);
    }

    public static void registerModuleConfig(Module module) {
        ForgeConfigSpec.BooleanValue value = builder.comment("Search for unstable updates.")
                .comment("Places:")
                .comment("  ITEM:   Kill switch")
                .comment("  BUTTON: In the exit confirm screen")
                .define("modules." + module.getName(), module.isCompatible() && (module.isDefaultEnabled() || !module.canDisable()));
        modules.put(module, value);
    }

    public static Boolean getModuleValue(Module module) {
        return getModuleSpec(module).get();
    }
    public static void setModuleValue(Module module) {
        boolean enabled = ModuleManager.getInstance().isEnabled(module);
    }
}
