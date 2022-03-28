package com.ultreon.randomthingz.block.machines.dryingrack;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DryingRackBlockEntityRenderer implements BlockEntityRenderer<DryingRackBlockEntity> {
    public DryingRackBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    private static Direction getFacing(DryingRackBlockEntity tileEntity) {
        Level dimension = tileEntity.getLevel();
        if (dimension != null) {
            BlockState state = dimension.getBlockState(tileEntity.getBlockPos());
            return state.getValue(DryingRackBlock.FACING);
        }
        return Direction.NORTH;
    }

    @Override
    public void render(DryingRackBlockEntity tileEntityIn, float partialTicks, PoseStack poses, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStack stack = tileEntityIn.getItem();
        if (!stack.isEmpty()) {
            poses.pushPose();
            RenderSystem.enableBlend();
            Direction facing = getFacing(tileEntityIn);
            Direction opposite = facing.getOpposite();
            double posX = 0.5 + 0.375 * opposite.getStepX();
            double posY = 0.425;
            double posZ = 0.5 + 0.375 * opposite.getStepZ();
            poses.translate(posX, posY, posZ);
            poses.mulPose(new Quaternion(0, 180 - facing.toYRot(), 0, true));

            float scale = .75f;
            poses.scale(scale, scale, scale);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, poses, bufferIn, 0);
            poses.popPose();
        }
    }
}
