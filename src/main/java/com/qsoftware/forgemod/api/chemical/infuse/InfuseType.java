package com.qsoftware.forgemod.api.chemical.infuse;

import com.qsoftware.forgemod.api.MekanismAPI;
import com.qsoftware.forgemod.api.NBTConstants;
import com.qsoftware.forgemod.api.chemical.Chemical;
import com.qsoftware.forgemod.api.chemical.ChemicalTags;
import com.qsoftware.forgemod.api.chemical.ChemicalUtils;
import com.qsoftware.forgemod.api.providers.IInfuseTypeProvider;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class InfuseType extends Chemical<InfuseType> implements IInfuseTypeProvider {

    public InfuseType(InfuseTypeBuilder builder) {
        super(builder, ChemicalTags.INFUSE_TYPE);
    }

    public static InfuseType readFromNBT(@Nullable CompoundNBT nbtTags) {
        return ChemicalUtils.readChemicalFromNBT(nbtTags, MekanismAPI.EMPTY_INFUSE_TYPE, NBTConstants.INFUSE_TYPE_NAME, InfuseType::getFromRegistry);
    }

    public static InfuseType getFromRegistry(@Nullable ResourceLocation name) {
        return ChemicalUtils.readChemicalFromRegistry(name, MekanismAPI.EMPTY_INFUSE_TYPE, MekanismAPI.infuseTypeRegistry());
    }

    @Override
    public String toString() {
        return "[InfuseType: " + getRegistryName() + "]";
    }

    @Override
    public CompoundNBT write(CompoundNBT nbtTags) {
        nbtTags.putString(NBTConstants.INFUSE_TYPE_NAME, getRegistryName().toString());
        return nbtTags;
    }

    @Override
    public final boolean isEmptyType() {
        return this == MekanismAPI.EMPTY_INFUSE_TYPE;
    }

    @Override
    protected String getDefaultTranslationKey() {
        return Util.makeTranslationKey("infuse_type", getRegistryName());
    }
}