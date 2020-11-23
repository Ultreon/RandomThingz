package com.qsoftware.forgemod.api.chemical.pigment;

import com.qsoftware.forgemod.api.MekanismAPI;
import com.qsoftware.forgemod.api.chemical.ChemicalBuilder;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PigmentBuilder extends ChemicalBuilder<Pigment, PigmentBuilder> {

    protected PigmentBuilder(ResourceLocation texture) {
        super(texture);
    }

    public static PigmentBuilder builder() {
        return builder(new ResourceLocation(MekanismAPI.MEKANISM_MODID, "pigment/base"));
    }

    public static PigmentBuilder builder(ResourceLocation texture) {
        return new PigmentBuilder(Objects.requireNonNull(texture));
    }
}