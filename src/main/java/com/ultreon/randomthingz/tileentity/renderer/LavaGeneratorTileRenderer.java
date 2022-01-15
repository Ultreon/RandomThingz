package com.ultreon.randomthingz.tileentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.block.machines.generator.lava.LavaGeneratorTileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import org.jetbrains.annotations.NotNull;

public class LavaGeneratorTileRenderer extends BlockEntityRenderer<LavaGeneratorTileEntity> {
    public LavaGeneratorTileRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@NotNull LavaGeneratorTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    }
}
