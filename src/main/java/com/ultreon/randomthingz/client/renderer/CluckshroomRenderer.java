package com.ultreon.randomthingz.client.renderer;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.model.CluckshroomModel;
import com.ultreon.randomthingz.entity.CluckshroomEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
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

    public CluckshroomRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new CluckshroomModel<>(), 0.5F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(@NotNull CluckshroomEntity entity) {
        return CLUCKSHROOM_TEXTURE;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    @Override
    protected float getBob(CluckshroomEntity livingBase, float partialTicks) {
        float f = Mth.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
        float f1 = Mth.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }
}
