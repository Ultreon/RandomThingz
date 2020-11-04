package com.qsoftware.forgemod.api.datagen.recipe.builder;

import com.google.gson.JsonObject;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import com.qsoftware.forgemod.api.JsonConstants;
import com.qsoftware.forgemod.api.SerializerHelper;
import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.chemical.gas.GasStack;
import com.qsoftware.forgemod.api.datagen.recipe.MekanismRecipeBuilder;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.GasStackIngredient;
import net.minecraft.util.ResourceLocation;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GasToGasRecipeBuilder extends MekanismRecipeBuilder<GasToGasRecipeBuilder> {

    private final GasStackIngredient input;
    private final GasStack output;

    protected GasToGasRecipeBuilder(GasStackIngredient input, GasStack output, ResourceLocation serializerName) {
        super(serializerName);
        this.input = input;
        this.output = output;
    }

    public static GasToGasRecipeBuilder activating(GasStackIngredient input, GasStack output) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This solar neutron activator recipe requires a non empty gas output.");
        }
        return new GasToGasRecipeBuilder(input, output, mekSerializer("activating"));
    }

    public static GasToGasRecipeBuilder centrifuging(GasStackIngredient input, GasStack output) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("This Isotopic Centrifuge recipe requires a non empty gas output.");
        }
        return new GasToGasRecipeBuilder(input, output, mekSerializer("centrifuging"));
    }

    @Override
    protected GasToGasRecipeResult getResult(ResourceLocation id) {
        return new GasToGasRecipeResult(id);
    }

    public class GasToGasRecipeResult extends RecipeResult {

        protected GasToGasRecipeResult(ResourceLocation id) {
            super(id);
        }

        @Override
        public void serialize(@Nonnull JsonObject json) {
            json.add(JsonConstants.INPUT, input.serialize());
            json.add(JsonConstants.OUTPUT, SerializerHelper.serializeGasStack(output));
        }
    }
}