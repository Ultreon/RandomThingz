package com.qsoftware.forgemod.api.recipes;

import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.chemical.slurry.Slurry;
import com.qsoftware.forgemod.api.chemical.slurry.SlurryStack;
import com.qsoftware.forgemod.api.recipes.chemical.FluidChemicalToChemicalRecipe;
import com.qsoftware.forgemod.api.recipes.inputs.FluidStackIngredient;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.SlurryStackIngredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class FluidSlurryToSlurryRecipe extends FluidChemicalToChemicalRecipe<Slurry, SlurryStack, SlurryStackIngredient> {

    public FluidSlurryToSlurryRecipe(ResourceLocation id, FluidStackIngredient fluidInput, SlurryStackIngredient slurryInput, SlurryStack output) {
        super(id, fluidInput, slurryInput, output);
    }

    @Override
    public SlurryStack getOutput(FluidStack fluidStack, SlurryStack slurryStack) {
        return output.copy();
    }
}