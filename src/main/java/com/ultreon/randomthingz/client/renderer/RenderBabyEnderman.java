package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.model.BabyEnderman;
import com.ultreon.randomthingz.client.renderer.layers.BabyEndermanEyesLayer;
import com.ultreon.randomthingz.client.renderer.layers.BabyEndermanHeldBlockLayer;
import com.ultreon.randomthingz.entity.baby.BabyEndermanEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Copy of vanilla's enderman render, modified to use our own model/layer that is properly scaled, so that the block is held in the correct spot and the head is in the
 * proper place.
 */
public class RenderBabyEnderman extends MobRenderer<BabyEndermanEntity, BabyEnderman> {

    private static final ResourceLocation ENDERMAN_TEXTURES = new ResourceLocation("textures/entity/enderman/enderman.png");
    private final Random rnd = new Random();

    public RenderBabyEnderman(EntityRenderDispatcher renderManager) {
        super(renderManager, new BabyEnderman(), 0.5F);
        this.addLayer(new BabyEndermanEyesLayer(this));
        this.addLayer(new BabyEndermanHeldBlockLayer(this));
    }

    @Override
    public void render(BabyEndermanEntity enderman, float entityYaw, float partialTicks, @NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int packedLightIn) {
        BabyEnderman model = getModel();
        model.carrying = enderman.getCarriedBlock() != null;
        model.creepy = enderman.isCreepy();
        super.render(enderman, entityYaw, partialTicks, matrix, renderer, packedLightIn);
    }

    @NotNull
    @Override
    public Vec3 getRenderOffset(BabyEndermanEntity enderman, float partialTicks) {
        if (enderman.isCreepy()) {
            return new Vec3(this.rnd.nextGaussian() * 0.02, 0, this.rnd.nextGaussian() * 0.02);
        }
        return super.getRenderOffset(enderman, partialTicks);
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull BabyEndermanEntity enderman) {
        return ENDERMAN_TEXTURES;
    }
}