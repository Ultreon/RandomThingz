package com.qtech.randomthingz.client.renderer;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.renderer.layers.MoobloomFlowerLayerOld;
import com.qtech.randomthingz.entity.MoobloomEntityOld;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Moobloom entity renderer class.
 *
 * @author Qboi123
 * @deprecated Use {@linkplain MoobloomRenderer} instead.
 */
@Deprecated
@OnlyIn(Dist.CLIENT)
public class MoobloomRendererOld extends MobRenderer<MoobloomEntityOld, CowModel<MoobloomEntityOld>> {
    private static final ResourceLocation MOOBLOOM_TEXTURES = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/cow/moobloom.png");

    public MoobloomRendererOld(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
        this.addLayer(new MoobloomFlowerLayerOld<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getEntityTexture(@NotNull MoobloomEntityOld entity) {
        return MOOBLOOM_TEXTURES;
    }

}
