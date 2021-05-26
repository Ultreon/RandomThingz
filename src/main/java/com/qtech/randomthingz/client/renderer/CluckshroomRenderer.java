package com.qtech.randomthingz.client.renderer;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.model.CluckshroomModel;
import com.qtech.randomthingz.entity.CluckshroomEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Duck entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class CluckshroomRenderer extends MobRenderer<CluckshroomEntity, CluckshroomModel<CluckshroomEntity>> {
    private static final ResourceLocation CLUCKSHROOM_TEXTURE = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/cluckshroom.png");

    public CluckshroomRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new CluckshroomModel<>(), 0.5F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getEntityTexture(@NotNull CluckshroomEntity entity) {
        return CLUCKSHROOM_TEXTURE;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    @Override
    protected float handleRotationFloat(CluckshroomEntity livingBase, float partialTicks) {
        float f = MathHelper.lerp(partialTicks, livingBase.oFlap, livingBase.wingRotation);
        float f1 = MathHelper.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.destPos);
        return (MathHelper.sin(f) + 1.0F) * f1;
    }
}
