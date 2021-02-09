package com.qsoftware.forgemod.modules.environment.client.renderer;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.environment.entities.BisonEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Bison entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class BisonRenderer extends MobRenderer<BisonEntity, CowModel<BisonEntity>> {
    private static final ResourceLocation BISON_TEXTURE = new ResourceLocation(QForgeMod.modId, "textures/entity/cow/bison.png");

    /**
     * Bison entity renderer constructor.
     *
     * @param renderManagerIn render manager.
     */
    public BisonRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getEntityTexture(@NotNull BisonEntity entity) {
        return BISON_TEXTURE;
    }
}