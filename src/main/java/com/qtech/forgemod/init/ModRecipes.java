package com.qtech.forgemod.init;

import com.qtech.forgemod.crafting.recipe.*;
import com.qtech.forgemod.util.Constants;
import com.qtech.forgemod.util.ExceptionUtil;
import lombok.experimental.UtilityClass;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

@UtilityClass
public final class ModRecipes {
    public static final RegistryObject<IRecipeSerializer<?>> ALLOY_SMELTING = registerSerializer(Constants.ALLOY_SMELTING, AlloySmeltingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> ARCANE_ESCALATING = registerSerializer(Constants.ARCANE_ESCALATING, ArcaneEscalatingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> ENCHANTING = registerSerializer(Constants.ENCHANTING, EnchantingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> COMPRESSING = registerSerializer(Constants.COMPRESSING, CompressingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> CRUSHING = registerSerializer(Constants.CRUSHING, CrushingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> DRYING = registerSerializer(Constants.DRYING, DryingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> INFUSING = registerSerializer(Constants.INFUSING, InfusingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> MIXING = registerSerializer(Constants.MIXING, MixingRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> REFINING = registerSerializer(Constants.REFINING, RefiningRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> SOLIDIFYING = registerSerializer(Constants.SOLIDIFYING, SolidifyingRecipe.Serializer::new);

    static void register() {
    }

    private static RegistryObject<IRecipeSerializer<?>> registerSerializer(ResourceLocation name, Supplier<IRecipeSerializer<?>> serializer) {
        return Registration.RECIPE_SERIALIZERS.register(name.getPath(), serializer);
    }

    private static <T extends IRecipe<?>> IRecipeType<T> registerType(ResourceLocation name) {
        return Registry.register(Registry.RECIPE_TYPE, name, new IRecipeType<T>() {
            @Override
            public String toString() {
                return name.toString();
            }
        });
    }

    public static final class Types {
        public static final IRecipeType<AlloySmeltingRecipe> ALLOY_SMELTING = registerType(Constants.ALLOY_SMELTING);
        public static final IRecipeType<ArcaneEscalatingRecipe> ARCANE_ESCALATING = registerType(Constants.ARCANE_ESCALATING);
        public static final IRecipeType<EnchantingRecipe> ENCHANTING = registerType(Constants.ENCHANTING);
        public static final IRecipeType<CompressingRecipe> COMPRESSING = registerType(Constants.ALLOY_SMELTING);
        public static final IRecipeType<CrushingRecipe> CRUSHING = registerType(Constants.ALLOY_SMELTING);
        public static final IRecipeType<DryingRecipe> DRYING = registerType(Constants.ALLOY_SMELTING);
        public static final IRecipeType<InfusingRecipe> INFUSING = registerType(Constants.INFUSING);
        public static final IRecipeType<MixingRecipe> MIXING = registerType(Constants.MIXING);
        public static final IRecipeType<RefiningRecipe> REFINING = registerType(Constants.REFINING);
        public static final IRecipeType<SolidifyingRecipe> SOLIDIFYING = registerType(Constants.SOLIDIFYING);
    }
}
