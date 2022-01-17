package com.ultreon.randomthingz.tileentity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

@OnlyIn(Dist.CLIENT)
public class ChristmasChestTileEntityRenderer<T extends BlockEntity & LidBlockEntity> extends BlockEntityRenderer<T> {
    private final ModelPart singleLid;
    private final ModelPart singleBottom;
    private final ModelPart singleLatch;
    private final ModelPart rightLid;
    private final ModelPart rightBottom;
    private final ModelPart rightLatch;
    private final ModelPart leftLid;
    private final ModelPart leftBottom;
    private final ModelPart leftLatch;

    public ChristmasChestTileEntityRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
        Calendar calendar = Calendar.getInstance();

        this.singleBottom = new ModelPart(64, 64, 0, 19);
        this.singleBottom.addBox(1.0f, 0.0f, 1.0f, 14.0f, 10.0f, 14.0f, 0.0f);
        this.singleLid = new ModelPart(64, 64, 0, 0);
        this.singleLid.addBox(1.0f, 0.0f, 0.0f, 14.0f, 5.0f, 14.0f, 0.0f);
        this.singleLid.y = 9.0f;
        this.singleLid.z = 1.0f;
        this.singleLatch = new ModelPart(64, 64, 0, 0);
        this.singleLatch.addBox(7.0f, -1.0f, 15.0f, 2.0f, 4.0f, 1.0f, 0.0f);
        this.singleLatch.y = 8.0f;
        this.rightBottom = new ModelPart(64, 64, 0, 19);
        this.rightBottom.addBox(1.0f, 0.0f, 1.0f, 15.0f, 10.0f, 14.0f, 0.0f);
        this.rightLid = new ModelPart(64, 64, 0, 0);
        this.rightLid.addBox(1.0f, 0.0f, 0.0f, 15.0f, 5.0f, 14.0f, 0.0f);
        this.rightLid.y = 9.0f;
        this.rightLid.z = 1.0f;
        this.rightLatch = new ModelPart(64, 64, 0, 0);
        this.rightLatch.addBox(15.0f, -1.0f, 15.0f, 1.0f, 4.0f, 1.0f, 0.0f);
        this.rightLatch.y = 8.0f;
        this.leftBottom = new ModelPart(64, 64, 0, 19);
        this.leftBottom.addBox(0.0f, 0.0f, 1.0f, 15.0f, 10.0f, 14.0f, 0.0f);
        this.leftLid = new ModelPart(64, 64, 0, 0);
        this.leftLid.addBox(0.0f, 0.0f, 0.0f, 15.0f, 5.0f, 14.0f, 0.0f);
        this.leftLid.y = 9.0f;
        this.leftLid.z = 1.0f;
        this.leftLatch = new ModelPart(64, 64, 0, 0);
        this.leftLatch.addBox(0.0f, -1.0f, 15.0f, 1.0f, 4.0f, 1.0f, 0.0f);
        this.leftLatch.y = 8.0f;
    }

    public void render(T tileEntityIn, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level dimension = tileEntityIn.getLevel();
        boolean flag = dimension != null;
        BlockState blockState = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        ChestType chestType = blockState.hasProperty(ChestBlock.TYPE) ? blockState.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
        Block block = blockState.getBlock();
        if (block instanceof AbstractChestBlock) {
            AbstractChestBlock<?> abstractChestBlock = (AbstractChestBlock<?>) block;
            boolean flag1 = chestType != ChestType.SINGLE;
            matrixStackIn.pushPose();
            float f = blockState.getValue(ChestBlock.FACING).toYRot();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-f));
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> iCallbackWrapper;
            if (flag) {
                iCallbackWrapper = abstractChestBlock.combine(blockState, dimension, tileEntityIn.getBlockPos(), true);
            } else {
                iCallbackWrapper = DoubleBlockCombiner.Combiner::acceptNone;
            }

            float f1 = iCallbackWrapper.apply(ChestBlock.opennessCombiner(tileEntityIn)).get(partialTicks);
            f1 = 1.0f - f1;
            f1 = 1.0f - f1 * f1 * f1;
            int i = iCallbackWrapper.apply(new BrightnessCombiner<>()).applyAsInt(combinedLightIn);
            Material renderMaterial = this.getMaterial(tileEntityIn, chestType);
            VertexConsumer iVertexBuilder = renderMaterial.buffer(bufferIn, RenderType::entityCutout);
            if (flag1) {
                if (chestType == ChestType.LEFT) {
                    this.renderModels(matrixStackIn, iVertexBuilder, this.leftLid, this.leftLatch, this.leftBottom, f1, i, combinedOverlayIn);
                } else {
                    this.renderModels(matrixStackIn, iVertexBuilder, this.rightLid, this.rightLatch, this.rightBottom, f1, i, combinedOverlayIn);
                }
            } else {
                this.renderModels(matrixStackIn, iVertexBuilder, this.singleLid, this.singleLatch, this.singleBottom, f1, i, combinedOverlayIn);
            }

            matrixStackIn.popPose();
        }
    }

    private void renderModels(PoseStack matrixStackIn, VertexConsumer bufferIn, ModelPart chestLid, ModelPart chestLatch, ModelPart chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn) {
        chestLid.xRot = -(lidAngle * ((float) Math.PI / 2F));
        chestLatch.xRot = chestLid.xRot;
        chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

    protected Material getMaterial(T tileEntity, ChestType chestType) {
        return Sheets.chooseMaterial(tileEntity, chestType, true);
    }
}
