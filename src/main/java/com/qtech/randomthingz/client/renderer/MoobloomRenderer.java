package com.qtech.randomthingz.client.renderer;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.model.MoobloomModel;
import com.qtech.randomthingz.entity.MoobloomEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Moobloom entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class MoobloomRenderer extends MobRenderer<MoobloomEntity, MoobloomModel<MoobloomEntity>> {
    public MoobloomRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MoobloomModel<>(), 0.7F);
//        this.addLayer(new MoobloomFlowerLayer<>(this));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getEntityTexture(@NotNull MoobloomEntity entity) {
        return new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/cow/moobloom/" + entity.getMoobloomType().getFilename());
    }
}
