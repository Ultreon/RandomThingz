package com.qsoftware.forgemod.api.recipes.inputs.chemical;

import com.google.gson.JsonElement;
import com.qsoftware.forgemod.api.chemical.pigment.Pigment;
import com.qsoftware.forgemod.api.chemical.pigment.PigmentStack;
import com.qsoftware.forgemod.api.providers.IPigmentProvider;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.ChemicalStackIngredient.MultiIngredient;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.ChemicalStackIngredient.SingleIngredient;
import com.qsoftware.forgemod.api.recipes.inputs.chemical.ChemicalStackIngredient.TaggedIngredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface PigmentStackIngredient extends IChemicalStackIngredient<Pigment, PigmentStack> {

    static PigmentStackIngredient from(@Nonnull PigmentStack instance) {
        return from(instance.getType(), instance.getAmount());
    }

    static PigmentStackIngredient from(@Nonnull IPigmentProvider pigment, long amount) {
        return new Single(pigment.getStack(amount));
    }

    static PigmentStackIngredient from(@Nonnull ITag<Pigment> tag, long amount) {
        return new Tagged(tag, amount);
    }

    static PigmentStackIngredient read(PacketBuffer buffer) {
        return ChemicalIngredientDeserializer.PIGMENT.read(buffer);
    }

    static PigmentStackIngredient deserialize(@Nullable JsonElement json) {
        return ChemicalIngredientDeserializer.PIGMENT.deserialize(json);
    }

    static PigmentStackIngredient createMulti(PigmentStackIngredient... ingredients) {
        return ChemicalIngredientDeserializer.PIGMENT.createMulti(ingredients);
    }

    @Override
    default ChemicalIngredientInfo<Pigment, PigmentStack> getIngredientInfo() {
        return ChemicalIngredientInfo.PIGMENT;
    }

    class Single extends SingleIngredient<Pigment, PigmentStack> implements PigmentStackIngredient {

        protected Single(@Nonnull PigmentStack stack) {
            super(stack);
        }
    }

    class Tagged extends TaggedIngredient<Pigment, PigmentStack> implements PigmentStackIngredient {

        protected Tagged(@Nonnull ITag<Pigment> tag, long amount) {
            super(tag, amount);
        }
    }

    class Multi extends MultiIngredient<Pigment, PigmentStack, PigmentStackIngredient> implements PigmentStackIngredient {

        protected Multi(@Nonnull PigmentStackIngredient... ingredients) {
            super(ingredients);
        }
    }
}