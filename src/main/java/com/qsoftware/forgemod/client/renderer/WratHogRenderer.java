package com.qsoftware.forgemod.client.renderer;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.client.model.HogModel;
import com.qsoftware.forgemod.objects.entities.HogEntity;
import com.qsoftware.forgemod.objects.entities.WratHogEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class WratHogRenderer extends MobRenderer<WratHogEntity, HogModel<WratHogEntity>> {
    private static final ResourceLocation WRATHOG_TEXTURE = new ResourceLocation(QForgeUtils.MOD_ID, "textures/entity/hog/wrathog.png");

    public WratHogRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new HogModel<>(), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getEntityTexture(WratHogEntity entity) {
        return WRATHOG_TEXTURE;
    }
}
