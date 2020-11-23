package com.qsoftware.forgemod.api.recipes.chemical;

import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.annotations.NonNull;
import com.qsoftware.forgemod.api.chemical.Chemical;
import com.qsoftware.forgemod.api.chemical.ChemicalStack;
import com.qsoftware.forgemod.api.recipes.MekanismRecipe;
import com.qsoftware.forgemod.api.recipes.inputs.FluidStackIngredient;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.IChemicalStackIngredient;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.BiPredicate;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class FluidChemicalToChemicalRecipe<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
        INGREDIENT extends IChemicalStackIngredient<CHEMICAL, STACK>> extends MekanismRecipe implements BiPredicate<@NonNull FluidStack, @NonNull STACK> {

    protected final STACK output;
    private final FluidStackIngredient fluidInput;
    private final INGREDIENT chemicalInput;

    public FluidChemicalToChemicalRecipe(ResourceLocation id, FluidStackIngredient fluidInput, INGREDIENT chemicalInput, STACK output) {
        super(id);
        this.fluidInput = fluidInput;
        this.chemicalInput = chemicalInput;
        this.output = output;
    }

    @Override
    public boolean test(FluidStack fluidStack, STACK chemicalStack) {
        return fluidInput.test(fluidStack) && chemicalInput.test(chemicalStack);
    }

    public FluidStackIngredient getFluidInput() {
        return fluidInput;
    }

    public INGREDIENT getChemicalInput() {
        return chemicalInput;
    }

    public STACK getOutputRepresentation() {
        return output;
    }

    @Contract(value = "_, _ -> new", pure = true)
    public abstract STACK getOutput(FluidStack fluidStack, STACK chemicalStack);

    @Override
    public void write(PacketBuffer buffer) {
        fluidInput.write(buffer);
        chemicalInput.write(buffer);
        output.writeToPacket(buffer);
    }
}