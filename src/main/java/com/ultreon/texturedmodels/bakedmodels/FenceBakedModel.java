package com.ultreon.texturedmodels.bakedmodels;

import com.ultreon.texturedmodels.block.FenceFrameBlock;
import com.ultreon.texturedmodels.block.FrameBlock;
import com.ultreon.texturedmodels.tileentity.FrameBlockEntity;
import com.ultreon.texturedmodels.util.ModelHelper;
import com.ultreon.texturedmodels.util.TextureHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Contains all information for the block model
 * See {@linkplain ModelHelper} for more information
 *
 * @author PianoManu
 * @version 1.4 09/28/20
 */
public class FenceBakedModel implements IDynamicBakedModel {

    public static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "block/oak_planks");

    private TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TEXTURE);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
        BlockState mimic = extraData.getData(FrameBlockEntity.MIMIC);
        if (mimic != null && !(mimic.getBlock() instanceof FrameBlock)) {
            ModelResourceLocation location = BlockModelShaper.stateToModelLocation(mimic);
            if (location != null && state != null) {
                BakedModel model = Minecraft.getInstance().getModelManager().getModel(location);
                if (model != null) {
                    return getMimicQuads(state, side, rand, extraData, model);
                }
            }
        }
        return Collections.emptyList();
    }

    @NotNull
    public List<BakedQuad> getMimicQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, IModelData extraData, BakedModel model) {
        BlockState mimic = extraData.getData(FrameBlockEntity.MIMIC);
        Integer design = extraData.getData(FrameBlockEntity.DESIGN);
        if (side != null) {
            return Collections.emptyList();
        }
        if (mimic != null && state != null) {
            int index = extraData.getData(FrameBlockEntity.TEXTURE);
            List<TextureAtlasSprite> texture = TextureHelper.getTextureFromModel(model, extraData, rand);
            if (texture.size() <= index) {
                extraData.setData(FrameBlockEntity.TEXTURE, 0);
                index = 0;
            }
            if (texture.size() == 0) {
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().player.displayClientMessage(new TranslatableComponent("We're sorry, but this block can't be displayed"), true);
                }
                return Collections.emptyList();
            }
            int tintIndex = -1;
            if (mimic.getBlock() instanceof GrassBlock) {
                tintIndex = 1;
            }
            List<BakedQuad> quads = new ArrayList<>(ModelHelper.createCuboid(6 / 16f, 10 / 16f, 0f, 1f, 6 / 16f, 10 / 16f, texture.get(index), tintIndex));

            if (design == 0) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 6 / 16f, 9 / 16f, 0f, 6 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 12 / 16f, 15 / 16f, 0f, 6 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createCuboid(10 / 16f, 1f, 6 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(10 / 16f, 1f, 12 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 6 / 16f, 9 / 16f, 10 / 16f, 1f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 12 / 16f, 15 / 16f, 10 / 16f, 1f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createCuboid(0f, 6 / 16f, 6 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(0f, 6 / 16f, 12 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                }
            }
            if (design == 1) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 3 / 16f, 6 / 16f, 0f, 6 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 9 / 16f, 12 / 16f, 0f, 6 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createCuboid(10 / 16f, 1f, 3 / 16f, 6 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(10 / 16f, 1f, 9 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 3 / 16f, 6 / 16f, 10 / 16f, 1f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 9 / 16f, 12 / 16f, 10 / 16f, 1f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createCuboid(0f, 6 / 16f, 3 / 16f, 6 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(0f, 6 / 16f, 9 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                }
            }
            if (design == 2) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 4 / 16f, 11 / 16f, 0f, 6 / 16f, texture.get(index), tintIndex));
                    //quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 9 / 16f, 12 / 16f, 0f, 6 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createCuboid(10 / 16f, 1f, 4 / 16f, 11 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    //quads.addAll(ModelHelper.createCuboid(10 / 16f, 1f, 9 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 4 / 16f, 11 / 16f, 10 / 16f, 1f, texture.get(index), tintIndex));
                    //quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 9 / 16f, 12 / 16f, 10 / 16f, 1f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createCuboid(0f, 6 / 16f, 4 / 16f, 11 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    //quads.addAll(ModelHelper.createCuboid(0f, 6 / 16f, 9 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                }
            }
            if (design == 3) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 6 / 16f, 9 / 16f, 0f, 6 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 12 / 16f, 15 / 16f, 0f, 6 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(6 / 16f, 10 / 16f, 0f, 1f, 0f, 2 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createCuboid(10 / 16f, 1f, 6 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(10 / 16f, 1f, 12 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(14 / 16f, 16 / 16f, 0f, 1f, 6 / 16f, 10 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 6 / 16f, 9 / 16f, 10 / 16f, 1f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 12 / 16f, 15 / 16f, 10 / 16f, 1f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(6 / 16f, 10 / 16f, 0f, 1f, 14 / 16f, 16 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createCuboid(0f, 6 / 16f, 6 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(0f, 6 / 16f, 12 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(0f, 2 / 16f, 0f, 1f, 6 / 16f, 10 / 16f, texture.get(index), tintIndex));
                }
            }
            if (design == 4) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, 2 / 16f, 6 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 13 / 16f, 15 / 16f, 2 / 16f, 6 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 6 / 16f, 8 / 16f, 0f, 2 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 12 / 16f, 14 / 16f, 0f, 2 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createCuboid(10 / 16f, 13 / 16f, 7 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(10 / 16f, 13 / 16f, 13 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(13 / 16f, 1f, 6 / 16f, 8 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(13 / 16f, 1f, 12 / 16f, 14 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, 10 / 16f, 14 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 13 / 16f, 15 / 16f, 10 / 16f, 14 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 6 / 16f, 8 / 16f, 14 / 16f, 1f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 12 / 16f, 14 / 16f, 14 / 16f, 1f, texture.get(index), tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createCuboid(3 / 16f, 6 / 16f, 7 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(3 / 16f, 6 / 16f, 13 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(0f, 3 / 16f, 6 / 16f, 8 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                    quads.addAll(ModelHelper.createCuboid(0f, 3 / 16f, 12 / 16f, 14 / 16f, 7 / 16f, 9 / 16f, texture.get(index), tintIndex));
                }
            }
            int overlayIndex = extraData.getData(FrameBlockEntity.OVERLAY);
            if (overlayIndex != 0) {
                quads.addAll(ModelHelper.createOverlay(6 / 16f, 10 / 16f, 0f, 1f, 6 / 16f, 10 / 16f, overlayIndex));
            }
            return quads;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return getTexture();
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}
