package com.qsoftware.forgemod.client.renderer;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.objects.entities.BisonEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BisonRenderer extends MobRenderer<BisonEntity, CowModel<BisonEntity>> {
    private static final ResourceLocation BISON_TEXTURE = new ResourceLocation(QForgeUtils.MOD_ID, "textures/entity/cow/bison.png");

    public BisonRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CowModel<>(), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getEntityTexture(BisonEntity entity) {
        return BISON_TEXTURE;
    }
}