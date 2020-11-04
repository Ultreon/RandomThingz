package com.qsoftware.forgemod.api.recipes.chemical;

import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.NonNull;
import com.qsoftware.forgemod.api.chemical.Chemical;
import com.qsoftware.forgemod.api.chemical.ChemicalStack;
import com.qsoftware.forgemod.api.recipes.MekanismRecipe;
import com.qsoftware.forgemod.api.recipes.inputs.ItemStackIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ItemStackToChemicalRecipe<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>> extends MekanismRecipe implements
      Predicate<@NonNull ItemStack> {

    protected final ItemStackIngredient input;
    protected final STACK output;

    public ItemStackToChemicalRecipe(ResourceLocation id, ItemStackIngredient input, STACK output) {
        super(id);
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return input.test(itemStack);
    }

    public ItemStackIngredient getInput() {
        return input;
    }

    @Contract(value = "_ -> new", pure = true)
    public abstract STACK getOutput(ItemStack input);

    public STACK getOutputDefinition() {
        return output;
    }

    @Override
    public void write(PacketBuffer buffer) {
        input.write(buffer);
        output.writeToPacket(buffer);
    }
}