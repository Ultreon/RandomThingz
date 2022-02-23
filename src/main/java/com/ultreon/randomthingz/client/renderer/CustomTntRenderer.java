package com.ultreon.randomthingz.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.ultreon.randomthingz.entity.custom.CustomPrimedTnt;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CustomTntRenderer extends EntityRenderer<CustomPrimedTnt> {
    public CustomTntRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.shadowRadius = .5f;
    }

    public void render(CustomPrimedTnt entity, float entityYaw, float partialTicks, PoseStack pose, @NotNull MultiBufferSource buf, int packedLightIn) {
        pose.pushPose();
        pose.translate(0.0D, 0.5D, 0.0D);
        int i = entity.getFuse();
        if ((float)i - partialTicks + 1f < 10f) {
            float f = 1f - ((float)i - partialTicks + 1f) / 10f;
            f = Mth.clamp(f, 0f, 1f);
            f *= f;
            f *= f;
            float f1 = 1f + f * .3f;
            pose.scale(f1, f1, f1);
        }

        pose.mulPose(Vector3f.YP.rotationDegrees(-90f));
        pose.translate(-0.5D, -0.5D, 0.5D);
        pose.mulPose(Vector3f.YP.rotationDegrees(90f));
        TntMinecartRenderer.renderWhiteSolidBlock(Blocks.TNT.defaultBlockState(), pose, buf, packedLightIn, i / 5 % 2 == 0);
        pose.popPose();
        super.render(entity, entityYaw, partialTicks, pose, buf, packedLightIn);
    }

    /**
     * Returns the location of an entity's texture.
     */
    @SuppressWarnings("deprecation")
    public @NotNull ResourceLocation getTextureLocation(@NotNull CustomPrimedTnt entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
