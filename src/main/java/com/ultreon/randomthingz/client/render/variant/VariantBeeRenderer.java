package com.ultreon.randomthingz.client.render.variant;

import com.google.common.collect.ImmutableList;
import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Bee;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class VariantBeeRenderer extends BeeRenderer {

    private static final List<String> VARIANTS = ImmutableList.of(
            "acebee", "agenbee", "arobee", "beefluid", "beesexual",
            "beequeer", "enbee", "gaybee", "interbee", "lesbeean",
            "panbee", "polysexbee", "transbee", "helen");

    public VariantBeeRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Bee entity) {
        if (entity.hasCustomName()/* || MobVariantsModule.everyBeeIsLGBT*/) {
            String custName = entity.hasCustomName() ? Objects.requireNonNull(entity.getCustomName()).getString().trim() : "";
            String name = custName.toLowerCase(Locale.ROOT);

            if (custName.matches("wire(se|bee)gal"))
                name = "enbee";

            if (VARIANTS.contains(name)) {
                String type = "normal";
                boolean angery = entity.hasStung();
                boolean nectar = entity.hasNectar();

                if (angery)
                    type = nectar ? "angry_nectar" : "angry";
                else if (nectar)
                    type = "nectar";

                String path = String.format("textures/model/entity/variants/bees/%s/%s.png", name, type);
                return new ResourceLocation(RandomThingz.MOD_ID, path);
            }
        }

        return super.getTextureLocation(entity);
    }

}
