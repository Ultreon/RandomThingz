package com.ultreon.randomthingz.client.renderer;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.entity.Hog;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Hog entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class HogRenderer extends MobRenderer<Hog, PigModel<Hog>> {
    private static final ResourceLocation HOG_TEXTURE = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/hog/hog.png");

    public HogRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PigModel<>(ctx.bakeLayer(ModelLayers.PIG)), .7f);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull Hog entity) {
        return HOG_TEXTURE;
    }
}