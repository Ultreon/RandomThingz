package com.ultreon.randomthingz.client.renderer;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.entity.Bison;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Bison entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class BisonRenderer extends MobRenderer<Bison, CowModel<Bison>> {
    private static final ResourceLocation BISON_TEXTURE = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/cow/bison.png");

    /**
     * Bison entity renderer constructor.
     *
     * @param ctx render manager.
     */
    public BisonRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new CowModel<>(ctx.bakeLayer(ModelLayers.COW)), .7f);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull Bison entity) {
        return BISON_TEXTURE;
    }
}