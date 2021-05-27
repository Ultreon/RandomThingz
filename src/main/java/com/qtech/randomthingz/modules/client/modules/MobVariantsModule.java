package com.qtech.randomthingz.modules.client.modules;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.gui.modules.ModuleCompatibility;
import com.qtech.randomthingz.commons.Module;
import com.qtech.randomthingz.commons.ModuleSafety;
import com.qtech.randomthingz.modules.client.modules.render.variant.*;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Supplier;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MobVariantsModule extends Module {
    public static boolean enableCow = true;
    public static boolean enablePig = true;
    public static boolean enableChicken = true;
    public static boolean enableCreeper = true;
    public static boolean enableZombie = true;
    public static boolean enableShinyRabbit = true;
    public static boolean enableShinyLlama = true;
    public static boolean enableLGBTBees = true;

    private static ListMultimap<VariantTextureType, ResourceLocation> textures = Multimaps.newListMultimap(new EnumMap<>(VariantTextureType.class), ArrayList::new);
    private static ListMultimap<VariantTextureType, ResourceLocation> shinyTextures = Multimaps.newListMultimap(new EnumMap<>(VariantTextureType.class), ArrayList::new);

    private static final int COW_COUNT = 4;
    private static final int CREEPER_COUNT = 2;
    private static final int ZOMBIE_COUNT = 6;
    private static final int SHINY_COW_COUNT = 2;
    private static final int SHINY_CREEPER_COUNT = 1;
    private static final int SHINY_ZOMBIE_COUNT = 1;
    private static final int SHINY_PIG_COUNT = 2;
    private static final int SHINY_CHICKEN_COUNT = 2;
    private static final int PIG_COUNT = 3;
    private static final int CHICKEN_COUNT = 6;
    public static int shinyAnimalChance = 2048;

    public MobVariantsModule() {

    }

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void clientSetup() {
        super.clientSetup();

        textures = Multimaps.newListMultimap(new EnumMap<>(VariantTextureType.class), ArrayList::new);
        shinyTextures = Multimaps.newListMultimap(new EnumMap<>(VariantTextureType.class), ArrayList::new);

        RandomThingz.LOGGER.debug("Registering textures.");
        registerTextures(VariantTextureType.COW, COW_COUNT, SHINY_COW_COUNT, new ResourceLocation("textures/entity/cow/cow.png"));
        registerTextures(VariantTextureType.PIG, PIG_COUNT, SHINY_PIG_COUNT, new ResourceLocation("textures/entity/pig/pig.png"));
        registerTextures(VariantTextureType.CHICKEN, CHICKEN_COUNT, SHINY_CHICKEN_COUNT, new ResourceLocation("textures/entity/chicken.png"));
        registerTextures(VariantTextureType.CREEPER, CREEPER_COUNT, SHINY_CREEPER_COUNT, new ResourceLocation("textures/entity/creeper/creeper.png"));
        registerTextures(VariantTextureType.ZOMBIE, ZOMBIE_COUNT, SHINY_ZOMBIE_COUNT, new ResourceLocation("textures/entity/zombie/zombie.png"));

        RandomThingz.LOGGER.debug("Registering shiny textures.");
        registerShiny(VariantTextureType.RABBIT, 1);
        registerShiny(VariantTextureType.LLAMA, 1);

        if(enableCow)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.COW, VariantCowRenderer::new);
        if(enablePig)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.PIG, VariantPigRenderer::new);
        if(enableChicken)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.CHICKEN, VariantChickenRenderer::new);
        if(enableCreeper)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.CREEPER, VariantCreeperRenderer::new);
        if(enableZombie)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.ZOMBIE, VariantZombieRenderer::new);
        if(enableShinyRabbit)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.RABBIT, VariantRabbitRenderer::new);
        if(enableShinyLlama)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.LLAMA, VariantLlamaRenderer::new);
        if(enableLGBTBees)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.BEE, VariantBeeRenderer::new);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean canDisable() {
        return false;
    }

    @Override
    public @NotNull String getName() {
        return "client/mob_variants";
    }

    @Override
    public boolean isDefaultEnabled() {
        return true;
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        if (RandomThingz.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (RandomThingz.isServerSide()) {
            return ModuleCompatibility.NONE;
        } else {
            return ModuleCompatibility.NONE;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static ResourceLocation getTextureOrShiny(Entity e, VariantTextureType type, boolean enabled) {
        return getTextureOrShiny(e, type, () -> getRandomTexture(e, type, enabled));
    }

    @OnlyIn(Dist.CLIENT)
    public static ResourceLocation getTextureOrShiny(Entity e, VariantTextureType type, Supplier<ResourceLocation> nonShiny) {
        UUID id = e.getUniqueID();
        long most = id.getMostSignificantBits();
        long least = id.getLeastSignificantBits();
        List<ResourceLocation> styles = shinyTextures.get(type);
        if(shinyAnimalChance > 0 && (most % shinyAnimalChance) == 0) {
            if (styles.size() == 0) {
                return new ResourceLocation(RandomThingz.MOD_ID, "textures/default.png");
            }
            if (least == 0) {
                return styles.get(0);
            }
            int choice = Math.abs((int) (least % styles.size()));
            return styles.get(choice);
        }

        return nonShiny.get();
    }

    @OnlyIn(Dist.CLIENT)
    private static ResourceLocation getRandomTexture(Entity e, VariantTextureType type, boolean enabled) {
        List<ResourceLocation> styles = textures.get(type);
        if(!enabled)
            return styles.get(styles.size() - 1);

        UUID id = e.getUniqueID();
        long most = id.getMostSignificantBits();
        int choice = Math.abs((int) (most % styles.size()));
        return styles.get(choice);
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerTextures(VariantTextureType type, int count, int shinyCount, @Nullable ResourceLocation vanilla) {
        String name = type.name().toLowerCase(Locale.ROOT);
        for(int i = 1; i < count + 1; i++)
            textures.put(type, new ResourceLocation(RandomThingz.MOD_ID, String.format("textures/entity/variants/%s%d.png", name, i)));

        if(vanilla != null)
            textures.put(type, vanilla);
        registerShiny(type, shinyCount);
    }

    private static void registerShiny(VariantTextureType type, int count) {
        String name = type.name().toLowerCase(Locale.ROOT);
//        shinyTextures.put(type, new ResourceLocation(QForgeMod.MOD_ID, String.format("textures/entity/variants/%s_shiny.png", name)));
        for(int i = 1; i < count + 1; i++)
            textures.put(type, new ResourceLocation(RandomThingz.MOD_ID, String.format("textures/entity/variants/%s_shiny%d.png", name, i)));
    }

    @Override
    public boolean requiresRestart() {
        return true;
    }

    public enum VariantTextureType {
        COW, PIG, CHICKEN, LLAMA, CREEPER, ZOMBIE, RABBIT
    }
}
