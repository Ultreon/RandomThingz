package com.qsoftware.forgemod.api.chemical.slurry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import com.qsoftware.forgemod.api.MekanismAPI;
import com.qsoftware.forgemod.api.NBTConstants;
import com.qsoftware.forgemod.api.chemical.Chemical;
import com.qsoftware.forgemod.api.chemical.ChemicalTags;
import com.qsoftware.forgemod.api.chemical.ChemicalUtils;
import com.qsoftware.forgemod.api.providers.ISlurryProvider;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

/**
 * Represents a slurry chemical subtype
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Slurry extends Chemical<Slurry> implements ISlurryProvider {

    @Nullable
    private final ITag<Item> oreTag;

    public Slurry(SlurryBuilder builder) {
        super(builder, ChemicalTags.SLURRY);
        this.oreTag = builder.getOreTag();
    }

    public static Slurry readFromNBT(@Nullable CompoundNBT nbtTags) {
        return ChemicalUtils.readChemicalFromNBT(nbtTags, MekanismAPI.EMPTY_SLURRY, NBTConstants.SLURRY_NAME, Slurry::getFromRegistry);
    }

    public static Slurry getFromRegistry(@Nullable ResourceLocation name) {
        return ChemicalUtils.readChemicalFromRegistry(name, MekanismAPI.EMPTY_SLURRY, MekanismAPI.slurryRegistry());
    }

    @Override
    public String toString() {
        return "[Slurry: " + getRegistryName() + "]";
    }

    @Override
    public CompoundNBT write(CompoundNBT nbtTags) {
        nbtTags.putString(NBTConstants.SLURRY_NAME, getRegistryName().toString());
        return nbtTags;
    }

    @Override
    public final boolean isEmptyType() {
        return this == MekanismAPI.EMPTY_SLURRY;
    }

    @Override
    protected String getDefaultTranslationKey() {
        return Util.makeTranslationKey("slurry", getRegistryName());
    }

    /**
     * Gets the item tag representing the ore for this slurry.
     *
     * @return The tag for the item the slurry goes with. May be null.
     */
    @Nullable
    public ITag<Item> getOreTag() {
        return oreTag;
    }
}