package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.renderer.layers.CustomEndermanEyesLayer;
import com.ultreon.randomthingz.client.renderer.layers.CustomHeldBlockLayer;
import com.ultreon.randomthingz.entity.IceEndermanEntity;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Ice enderman entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class IceEndermanRenderer extends MobRenderer<IceEndermanEntity, EndermanModel<IceEndermanEntity>> {
    private static final ResourceLocation ENDERMAN_TEXTURES = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/enderman/ice.png");

    private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/enderman/ice_eyes.png"));

    private final Random rnd = new Random();

    public IceEndermanRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new EndermanModel<>(0.0F), 0.5F);

        this.addLayer(new CustomEndermanEyesLayer<>(this, RENDER_TYPE));
        this.addLayer(new CustomHeldBlockLayer<>(this));
    }

    @Override
    public void render(IceEndermanEntity entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        BlockState blockstate = entityIn.getCarriedBlock();
        EndermanModel<IceEndermanEntity> endermanModel = this.getModel();

        endermanModel.carrying = blockstate != null;
        endermanModel.creepy = entityIn.isCreepy();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public @NotNull Vec3 getRenderOffset(IceEndermanEntity entityIn, float partialTicks) {
        if (entityIn.isCreepy()) {
            return new Vec3(this.rnd.nextGaussian() * 0.02D, 0.0D, this.rnd.nextGaussian() * 0.02D);
        } else {
            return super.getRenderOffset(entityIn, partialTicks);
        }
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull IceEndermanEntity entity) {
        return ENDERMAN_TEXTURES;
    }
}
