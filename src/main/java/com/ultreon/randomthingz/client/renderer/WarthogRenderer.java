package com.ultreon.randomthingz.client.renderer;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.model.HogModel;
import com.ultreon.randomthingz.entity.WarthogEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Warthog renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class WarthogRenderer extends MobRenderer<WarthogEntity, HogModel<WarthogEntity>> {
    private static final ResourceLocation WARTHOG_TEXTURE = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/hog/warthog.png");

    public WarthogRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new HogModel<>(), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull
    ResourceLocation getTextureLocation(@NotNull WarthogEntity entity) {
        return WARTHOG_TEXTURE;
    }
}
