package com.qtech.randomthingz.tileentity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.*;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

@OnlyIn(Dist.CLIENT)
public class ChristmasChestTileEntityRenderer<T extends TileEntity & IChestLid> extends TileEntityRenderer<T> {
   private final ModelRenderer singleLid;
   private final ModelRenderer singleBottom;
   private final ModelRenderer singleLatch;
   private final ModelRenderer rightLid;
   private final ModelRenderer rightBottom;
   private final ModelRenderer rightLatch;
   private final ModelRenderer leftLid;
   private final ModelRenderer leftBottom;
   private final ModelRenderer leftLatch;

   public ChristmasChestTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
      super(rendererDispatcherIn);
      Calendar calendar = Calendar.getInstance();

      this.singleBottom = new ModelRenderer(64, 64, 0, 19);
      this.singleBottom.addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
      this.singleLid = new ModelRenderer(64, 64, 0, 0);
      this.singleLid.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
      this.singleLid.rotationPointY = 9.0F;
      this.singleLid.rotationPointZ = 1.0F;
      this.singleLatch = new ModelRenderer(64, 64, 0, 0);
      this.singleLatch.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
      this.singleLatch.rotationPointY = 8.0F;
      this.rightBottom = new ModelRenderer(64, 64, 0, 19);
      this.rightBottom.addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
      this.rightLid = new ModelRenderer(64, 64, 0, 0);
      this.rightLid.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
      this.rightLid.rotationPointY = 9.0F;
      this.rightLid.rotationPointZ = 1.0F;
      this.rightLatch = new ModelRenderer(64, 64, 0, 0);
      this.rightLatch.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.rightLatch.rotationPointY = 8.0F;
      this.leftBottom = new ModelRenderer(64, 64, 0, 19);
      this.leftBottom.addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
      this.leftLid = new ModelRenderer(64, 64, 0, 0);
      this.leftLid.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
      this.leftLid.rotationPointY = 9.0F;
      this.leftLid.rotationPointZ = 1.0F;
      this.leftLatch = new ModelRenderer(64, 64, 0, 0);
      this.leftLatch.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.leftLatch.rotationPointY = 8.0F;
   }

   public void render(T tileEntityIn, float partialTicks, @NotNull MatrixStack matrixStackIn, @NotNull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
      World dimension = tileEntityIn.getDimension();
      boolean flag = dimension != null;
      BlockState blockState = flag ? tileEntityIn.getBlockState() : Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
      ChestType chestType = blockState.hasProperty(ChestBlock.TYPE) ? blockState.get(ChestBlock.TYPE) : ChestType.SINGLE;
      Block block = blockState.getBlock();
      if (block instanceof AbstractChestBlock) {
         AbstractChestBlock<?> abstractChestBlock = (AbstractChestBlock<?>)block;
         boolean flag1 = chestType != ChestType.SINGLE;
         matrixStackIn.push();
         float f = blockState.get(ChestBlock.FACING).getHorizontalAngle();
         matrixStackIn.translate(0.5D, 0.5D, 0.5D);
         matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-f));
         matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
         TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> iCallbackWrapper;
         if (flag) {
            iCallbackWrapper = abstractChestBlock.combine(blockState, dimension, tileEntityIn.getPos(), true);
         } else {
            iCallbackWrapper = TileEntityMerger.ICallback::func_225537_b_;
         }

         float f1 = iCallbackWrapper.<Float2FloatFunction>apply(ChestBlock.getLidRotationCallback(tileEntityIn)).get(partialTicks);
         f1 = 1.0F - f1;
         f1 = 1.0F - f1 * f1 * f1;
         int i = iCallbackWrapper.<Int2IntFunction>apply(new DualBrightnessCallback<>()).applyAsInt(combinedLightIn);
         RenderMaterial renderMaterial = this.getMaterial(tileEntityIn, chestType);
         IVertexBuilder iVertexBuilder = renderMaterial.getBuffer(bufferIn, RenderType::getEntityCutout);
         if (flag1) {
            if (chestType == ChestType.LEFT) {
               this.renderModels(matrixStackIn, iVertexBuilder, this.leftLid, this.leftLatch, this.leftBottom, f1, i, combinedOverlayIn);
            } else {
               this.renderModels(matrixStackIn, iVertexBuilder, this.rightLid, this.rightLatch, this.rightBottom, f1, i, combinedOverlayIn);
            }
         } else {
            this.renderModels(matrixStackIn, iVertexBuilder, this.singleLid, this.singleLatch, this.singleBottom, f1, i, combinedOverlayIn);
         }

         matrixStackIn.pop();
      }
   }

   private void renderModels(MatrixStack matrixStackIn, IVertexBuilder bufferIn, ModelRenderer chestLid, ModelRenderer chestLatch, ModelRenderer chestBottom, float lidAngle, int combinedLightIn, int combinedOverlayIn) {
      chestLid.rotateAngleX = -(lidAngle * ((float)Math.PI / 2F));
      chestLatch.rotateAngleX = chestLid.rotateAngleX;
      chestLid.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
      chestLatch.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
      chestBottom.render(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
   }

   protected RenderMaterial getMaterial(T tileEntity, ChestType chestType) {
      return Atlases.getChestMaterial(tileEntity, chestType, true);
   }
}
