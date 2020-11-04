package com.qsoftware.forgemod.api.recipes;

import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.recipes.inputs.ItemStackIngredient;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.GasStackIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

/**
 * Extension of {@link ItemStackGasToItemStackRecipe} with a defined amount of ticks needed to process.
 */
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class NucleosynthesizingRecipe extends ItemStackGasToItemStackRecipe {

    private final int duration;

    public NucleosynthesizingRecipe(ResourceLocation id, ItemStackIngredient itemInput, GasStackIngredient gasInput, ItemStack output, int duration) {
        super(id, itemInput, gasInput, output);
        this.duration = duration;
    }

    @Override
    public void write(PacketBuffer buffer) {
        super.write(buffer);
        buffer.writeVarInt(duration);
    }

    public int getDuration() {
        return duration;
    }
}