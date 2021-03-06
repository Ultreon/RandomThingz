package com.ultreon.randomthingz.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.block.machines.generator.lava.LavaGeneratorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class LavaGeneratorBlockEntityRenderer implements BlockEntityRenderer<LavaGeneratorBlockEntity> {
    public LavaGeneratorBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super();
    }

    @Override
    public void render(@NotNull LavaGeneratorBlockEntity tileEntity, float partialTicks, PoseStack pose, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    }
}
