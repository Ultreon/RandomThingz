package com.qsoftware.forgemod.api.recipes.cache;

import java.util.function.LongSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.NonNull;
import com.qsoftware.forgemod.api.chemical.gas.Gas;
import com.qsoftware.forgemod.api.chemical.gas.GasStack;
import com.qsoftware.forgemod.api.recipes.ItemStackGasToItemStackRecipe;
import com.qsoftware.forgemod.api.recipes.cache.chemical.ItemStackChemicalToItemStackCachedRecipe;
import com.qsoftware.forgemod.api.recipes.inputs.IInputHandler;
import com.qsoftware.forgemod.api.recipes.inputs.ILongInputHandler;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.GasStackIngredient;
import com.qsoftware.forgemod.api.recipes.outputs.IOutputHandler;
import net.minecraft.item.ItemStack;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemStackGasToItemStackCachedRecipe<RECIPE extends ItemStackGasToItemStackRecipe> extends
      ItemStackChemicalToItemStackCachedRecipe<Gas, GasStack, GasStackIngredient, RECIPE> {

    public ItemStackGasToItemStackCachedRecipe(RECIPE recipe, IInputHandler<@NonNull ItemStack> itemInputHandler,
          ILongInputHandler<@NonNull GasStack> gasInputHandler, LongSupplier gasUsage, IOutputHandler<@NonNull ItemStack> outputHandler) {
        super(recipe, itemInputHandler, gasInputHandler, gasUsage, outputHandler);
    }
}