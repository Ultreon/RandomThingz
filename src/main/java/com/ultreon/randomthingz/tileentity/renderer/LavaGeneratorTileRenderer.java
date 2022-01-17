package com.ultreon.randomthingz.tileentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.block.machines.generator.lava.LavaGeneratorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import org.jetbrains.annotations.NotNull;

public class LavaGeneratorTileRenderer extends BlockEntityRenderer<LavaGeneratorBlockEntity> {
    public LavaGeneratorTileRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@NotNull LavaGeneratorBlockEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
    }
}
