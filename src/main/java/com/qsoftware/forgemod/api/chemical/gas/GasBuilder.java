package com.qsoftware.forgemod.api.chemical.gas;

import mcp.MethodsReturnNonnullByDefault;
import com.qsoftware.forgemod.api.MekanismAPI;
import com.qsoftware.forgemod.api.chemical.ChemicalBuilder;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GasBuilder extends ChemicalBuilder<Gas, GasBuilder> {

    protected GasBuilder(ResourceLocation texture) {
        super(texture);
    }

    public static GasBuilder builder() {
        return builder(new ResourceLocation(MekanismAPI.MEKANISM_MODID, "liquid/liquid"));
    }

    public static GasBuilder builder(ResourceLocation texture) {
        return new GasBuilder(Objects.requireNonNull(texture));
    }
}