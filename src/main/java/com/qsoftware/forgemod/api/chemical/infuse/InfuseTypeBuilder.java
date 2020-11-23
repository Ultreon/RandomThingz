package com.qsoftware.forgemod.api.chemical.infuse;

import com.qsoftware.forgemod.api.MekanismAPI;
import com.qsoftware.forgemod.api.chemical.ChemicalBuilder;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class InfuseTypeBuilder extends ChemicalBuilder<InfuseType, InfuseTypeBuilder> {

    protected InfuseTypeBuilder(ResourceLocation texture) {
        super(texture);
    }

    public static InfuseTypeBuilder builder() {
        return builder(new ResourceLocation(MekanismAPI.MEKANISM_MODID, "infuse_type/base"));
    }

    public static InfuseTypeBuilder builder(ResourceLocation texture) {
        return new InfuseTypeBuilder(Objects.requireNonNull(texture));
    }
}