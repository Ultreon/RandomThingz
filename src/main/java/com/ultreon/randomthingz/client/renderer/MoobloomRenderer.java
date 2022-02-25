package com.ultreon.randomthingz.client.renderer;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.model.MoobloomModel;
import com.ultreon.randomthingz.client.renderer.layers.MoobloomFlowerLayer;
import com.ultreon.randomthingz.entity.Moobloom;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Moobloom entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class MoobloomRenderer extends MobRenderer<Moobloom, MoobloomModel<Moobloom>> {
    public MoobloomRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new MoobloomModel<>(ctx.bakeLayer(MoobloomModel.LAYER_LOCATION)), .7f);
//        this.addLayer(new MoobloomFlowerLayer<Moobloom>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull
    ResourceLocation getTextureLocation(@NotNull Moobloom entity) {
        return new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/cow/moobloom/" + entity.getMoobloomType().getFilename());
    }
}
