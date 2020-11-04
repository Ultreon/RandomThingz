package com.qsoftware.forgemod.api.recipes.chemical;

import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.NonNull;
import com.qsoftware.forgemod.api.chemical.Chemical;
import com.qsoftware.forgemod.api.chemical.ChemicalStack;
import com.qsoftware.forgemod.api.recipes.MekanismRecipe;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.IChemicalStackIngredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Contract;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ChemicalToChemicalRecipe<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
      INGREDIENT extends IChemicalStackIngredient<CHEMICAL, STACK>> extends MekanismRecipe implements Predicate<@NonNull STACK> {

    private final INGREDIENT input;
    protected final STACK output;

    public ChemicalToChemicalRecipe(ResourceLocation id, INGREDIENT input, STACK output) {
        super(id);
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean test(STACK chemicalStack) {
        return input.test(chemicalStack);
    }

    public INGREDIENT getInput() {
        return input;
    }

    public STACK getOutputRepresentation() {
        return output;
    }

    @Contract(value = "_ -> new", pure = true)
    public abstract STACK getOutput(STACK input);

    @Override
    public void write(PacketBuffer buffer) {
        input.write(buffer);
        output.writeToPacket(buffer);
    }
}