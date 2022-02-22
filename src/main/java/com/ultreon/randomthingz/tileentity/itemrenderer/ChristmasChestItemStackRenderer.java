package com.ultreon.randomthingz.tileentity.itemrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ChristmasChestItemStackRenderer<T extends BlockEntity> extends BlockEntityWithoutLevelRenderer {

    private final Supplier<T> te;
    private final BlockEntityRenderDispatcher dispatcher;

    public ChristmasChestItemStackRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet models, Supplier<T> te) {
        super(dispatcher, models);
        this.te = te;
        this.dispatcher = dispatcher;
    }

    @Override
    public void renderByItem(@NotNull ItemStack itemStackIn, ItemTransforms.@NotNull TransformType transformType, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        dispatcher.renderItem(this.te.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
