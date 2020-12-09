package com.qsoftware.forgemod.client.renderer;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.objects.entities.MoobloomEntityOld;
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
 * @deprecated Use {@link MoobloomRenderer} instead.
 * @author Qboi123
 */
@Deprecated
@OnlyIn(Dist.CLIENT)
public class MoobloomRendererOld extends MobRenderer<MoobloomEntityOld, CowModel<MoobloomEntityOld>> {
    private static final ResourceLocation MOOBLOOM_TEXTURES = new ResourceLocation(QForgeMod.MOD_ID, "textures/entity/cow/moobloom.png");

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
