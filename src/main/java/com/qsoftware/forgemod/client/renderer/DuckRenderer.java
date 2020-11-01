package com.qsoftware.forgemod.client.renderer;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.client.model.FreeChickenModel;
import com.qsoftware.forgemod.objects.entities.DuckEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DuckRenderer extends MobRenderer<DuckEntity, FreeChickenModel<DuckEntity>> {
    private static final ResourceLocation DUCK_TEXTURE = new ResourceLocation(QForgeUtils.MOD_ID, "textures/entity/duck.png");

    public DuckRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FreeChickenModel<>(), 0.5F);
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(ChickenEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public @NotNull ResourceLocation getEntityTexture(DuckEntity entity) {
        return DUCK_TEXTURE;
    }
}
