package com.qsoftware.forgemod.modules.entities.renderer;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.entities.objects.OxEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Ox entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class OxRenderer extends MobRenderer<OxEntity, CowModel<OxEntity>> {
    private static final ResourceLocation BISON_TEXTURE = new ResourceLocation(QForgeMod.MOD_ID, "textures/entity/cow/ox.png");

    public OxRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getEntityTexture(@NotNull OxEntity entity) {
        return BISON_TEXTURE;
    }
}