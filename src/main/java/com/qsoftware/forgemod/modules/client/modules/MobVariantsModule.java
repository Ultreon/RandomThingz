package com.qsoftware.forgemod.modules.client.modules;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.modules.client.modules.render.variant.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

public class MobVariantsModule extends Module {
    public static boolean enableCow;
    public static boolean enablePig;
    public static boolean enableChicken;
    public static boolean enableShinyRabbit;
    public static boolean enableShinyLlama;
    public static boolean enableLGBTBees;

    private static ListMultimap<VariantTextureType, ResourceLocation> textures;
    private static Map<VariantTextureType, ResourceLocation> shinyTextures;

    private static final int COW_COUNT = 4;
    private static final int PIG_COUNT = 3;
    private static final int CHICKEN_COUNT = 6;
    public static int shinyAnimalChance = 2048;

    public MobVariantsModule() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void clientSetup() {
        super.clientSetup();

        textures = Multimaps.newListMultimap(new EnumMap<>(VariantTextureType.class), ArrayList::new);
        shinyTextures = new HashMap<>();

        registerTextures(VariantTextureType.COW, COW_COUNT, new ResourceLocation("textures/entity/cow/cow.png"));
        registerTextures(VariantTextureType.PIG, PIG_COUNT, new ResourceLocation("textures/entity/pig/pig.png"));
        registerTextures(VariantTextureType.CHICKEN, CHICKEN_COUNT, new ResourceLocation("textures/entity/chicken.png"));
        registerShiny(VariantTextureType.RABBIT);
        registerShiny(VariantTextureType.LLAMA);

        if(enableCow)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.COW, VariantCowRenderer::new);
        if(enablePig)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.PIG, VariantPigRenderer::new);
        if(enableChicken)
            RenderingRegistry.registerEntityRenderingHandler(EntityType.CHICKEN, VariantChickenRenderer::new);
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
        if (QForgeMod.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (QForgeMod.isServerSide()) {
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
        if(shinyAnimalChance > 0 && (most % shinyAnimalChance) == 0)
            return shinyTextures.get(type);

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
    private static void registerTextures(VariantTextureType type, int count, ResourceLocation vanilla) {
        String name = type.name().toLowerCase(Locale.ROOT);
        for(int i = 1; i < count + 1; i++)
            textures.put(type, new ResourceLocation(QForgeMod.modId, String.format("textures/model/entity/variants/%s%d.png", name, i)));

        if(vanilla != null)
            textures.put(type, vanilla);
        registerShiny(type);
    }

    private static void registerShiny(VariantTextureType type) {
        shinyTextures.put(type, new ResourceLocation(QForgeMod.modId, String.format("textures/model/entity/variants/%s_shiny.png", type.name().toLowerCase(Locale.ROOT))));
    }

    @Override
    public boolean requiresRestart() {
        return true;
    }

    public enum VariantTextureType {
        COW, PIG, CHICKEN, LLAMA, RABBIT
    }
}
