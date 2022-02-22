package com.ultreon.randomthingz.tileentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.block.machines.generator.lava.LavaGeneratorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class LavaGeneratorTileRenderer implements BlockEntityRenderer<LavaGeneratorBlockEntity> {
    public LavaGeneratorTileRenderer(BlockEntityRendererProvider.Context ctx) {
        super();
    }

    @Override
    public void render(@NotNull LavaGeneratorBlockEntity tileEntity, float partialTicks, PoseStack pose, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    }
}
