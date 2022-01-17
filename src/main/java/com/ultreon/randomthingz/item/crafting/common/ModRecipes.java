package com.ultreon.randomthingz.item.crafting.common;

import com.ultreon.randomthingz.item.crafting.*;
import com.ultreon.randomthingz.registration.Registration;
import com.ultreon.randomthingz.util.Constants;
import lombok.experimental.UtilityClass;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@UtilityClass
public final class ModRecipes {
    public static final RegistryObject<RecipeSerializer<?>> ALLOY_SMELTING = registerSerializer(Constants.ALLOY_SMELTING, AlloySmeltingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ARCANE_ESCALATING = registerSerializer(Constants.ARCANE_ESCALATING, ArcaneEscalatingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ENCHANTING = registerSerializer(Constants.ENCHANTING, EnchantingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> COMPRESSING = registerSerializer(Constants.COMPRESSING, CompressingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> CRUSHING = registerSerializer(Constants.CRUSHING, CrushingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> DRYING = registerSerializer(Constants.DRYING, DryingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> INFUSING = registerSerializer(Constants.INFUSING, InfusingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> MIXING = registerSerializer(Constants.MIXING, MixingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> REFINING = registerSerializer(Constants.REFINING, RefiningRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> SOLIDIFYING = registerSerializer(Constants.SOLIDIFYING, SolidifyingRecipe.Serializer::new);

    public static void register() {
    }

    private static RegistryObject<RecipeSerializer<?>> registerSerializer(ResourceLocation name, Supplier<RecipeSerializer<?>> serializer) {
        return Registration.RECIPE_SERIALIZERS.register(name.getPath(), serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(ResourceLocation name) {
        return Registry.register(Registry.RECIPE_TYPE, name, new RecipeType<T>() {
            @Override
            public String toString() {
                return name.toString();
            }
        });
    }

    public static final class Types {
        public static final RecipeType<AlloySmeltingRecipe> ALLOY_SMELTING = registerType(Constants.ALLOY_SMELTING);
        public static final RecipeType<ArcaneEscalatingRecipe> ARCANE_ESCALATING = registerType(Constants.ARCANE_ESCALATING);
        public static final RecipeType<EnchantingRecipe> ENCHANTING = registerType(Constants.ENCHANTING);
        public static final RecipeType<CompressingRecipe> COMPRESSING = registerType(Constants.ALLOY_SMELTING);
        public static final RecipeType<CrushingRecipe> CRUSHING = registerType(Constants.ALLOY_SMELTING);
        public static final RecipeType<DryingRecipe> DRYING = registerType(Constants.ALLOY_SMELTING);
        public static final RecipeType<InfusingRecipe> INFUSING = registerType(Constants.INFUSING);
        public static final RecipeType<MixingRecipe> MIXING = registerType(Constants.MIXING);
        public static final RecipeType<RefiningRecipe> REFINING = registerType(Constants.REFINING);
        public static final RecipeType<SolidifyingRecipe> SOLIDIFYING = registerType(Constants.SOLIDIFYING);
    }
}
