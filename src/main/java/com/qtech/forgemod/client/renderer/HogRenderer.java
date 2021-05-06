package com.qtech.forgemod.client.renderer;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.client.model.HogModel;
import com.qtech.forgemod.entity.HogEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Hog entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class HogRenderer extends MobRenderer<HogEntity, HogModel<HogEntity>> {
    private static final ResourceLocation HOG_TEXTURE = new ResourceLocation(QForgeMod.MOD_ID, "textures/entity/hog/hog.png");

    public HogRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new HogModel<>(), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getEntityTexture(@NotNull HogEntity entity) {
        return HOG_TEXTURE;
    }
}