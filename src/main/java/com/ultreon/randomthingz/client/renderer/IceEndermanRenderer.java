package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.renderer.layers.CustomEndermanEyesLayer;
import com.ultreon.randomthingz.client.renderer.layers.CustomHeldBlockLayer;
import com.ultreon.randomthingz.entity.IceEnderman;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
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
public class IceEndermanRenderer extends MobRenderer<IceEnderman, EndermanModel<IceEnderman>> {
    private static final ResourceLocation ENDERMAN_TEXTURES = new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/enderman/ice.png");

    private static final RenderType RENDER_TYPE = RenderType.eyes(new ResourceLocation(RandomThingz.MOD_ID, "textures/entity/enderman/ice_eyes.png"));

    private final Random rnd = new Random();

    public IceEndermanRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new EndermanModel<>(ctx.bakeLayer(ModelLayers.ENDERMAN)), .5f);

        this.addLayer(new CustomEndermanEyesLayer<>(this, RENDER_TYPE));
        this.addLayer(new CustomHeldBlockLayer<>(this));
    }

    @Override
    public void render(IceEnderman entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        BlockState blockstate = entityIn.getCarriedBlock();
        EndermanModel<IceEnderman> endermanModel = this.getModel();

        endermanModel.carrying = blockstate != null;
        endermanModel.creepy = entityIn.isCreepy();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public @NotNull Vec3 getRenderOffset(IceEnderman entityIn, float partialTicks) {
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
    public @NotNull ResourceLocation getTextureLocation(@NotNull IceEnderman entity) {
        return ENDERMAN_TEXTURES;
    }
}
