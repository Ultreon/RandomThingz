package com.ultreon.texturedmodels.bakedmodels;

import com.ultreon.texturedmodels.block.FenceFrameBlock;
import com.ultreon.texturedmodels.block.FrameBlock;
import com.ultreon.texturedmodels.tileentity.FrameBlockTile;
import com.ultreon.texturedmodels.util.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Contains all information for the block model
 * See {@linkplain ModelHelper} for more information
 *
 * @author PianoManu
 * @version 1.3 09/28/20
 */
public class IllusionFenceBakedModel implements IDynamicBakedModel {
    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        BlockState mimic = extraData.getData(FrameBlockTile.MIMIC);
        if (mimic != null && !(mimic.getBlock() instanceof FrameBlock)) {
            ModelResourceLocation location = BlockModelShaper.stateToModelLocation(mimic);
            if (location != null) {
                BakedModel model = Minecraft.getInstance().getModelManager().getModel(location);
                if (model != null) {
                    return getIllusionQuads(state, side, rand, extraData, model);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<BakedQuad> getIllusionQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData, BakedModel model) {
        if (side != null) {
            return Collections.emptyList();
        }
        BlockState mimic = extraData.getData(FrameBlockTile.MIMIC);
        Integer design = extraData.getData(FrameBlockTile.DESIGN);
        if (mimic != null && state != null) {
            int tintIndex = -1;
            if (mimic.getBlock() instanceof GrassBlock) {
                tintIndex = 1;
            }
            List<BakedQuad> quads = new ArrayList<>(ModelHelper.createSixFaceCuboid(6 / 16f, 10 / 16f, 0f, 1f, 6 / 16f, 10 / 16f, mimic, model, extraData, rand, tintIndex));

            if (design == 0) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f, 9 / 16f, 0f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 12 / 16f, 15 / 16f, 0f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 1f, 6 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 1f, 12 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f, 9 / 16f, 10 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 12 / 16f, 15 / 16f, 10 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 6 / 16f, 6 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 6 / 16f, 12 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                }
            }
            if (design == 1) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 3 / 16f, 6 / 16f, 0f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 9 / 16f, 12 / 16f, 0f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 1f, 3 / 16f, 6 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 1f, 9 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 3 / 16f, 6 / 16f, 10 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 9 / 16f, 12 / 16f, 10 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 6 / 16f, 3 / 16f, 6 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 6 / 16f, 9 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                }
            }
            if (design == 2) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 4 / 16f, 11 / 16f, 0f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                    //quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 9 / 16f, 12 / 16f, 0f, 6 / 16f, mimic,model,extraData,rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 1f, 4 / 16f, 11 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    //quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 1f, 9 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, mimic,model,extraData,rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 4 / 16f, 11 / 16f, 10 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                    //quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 9 / 16f, 12 / 16f, 10 / 16f, 1f, mimic,model,extraData,rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 6 / 16f, 4 / 16f, 11 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    //quads.addAll(ModelHelper.createSixFaceCuboid(0f, 6 / 16f, 9 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, mimic,model,extraData,rand, tintIndex));
                }
            }
            if (design == 3) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f, 9 / 16f, 0f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 12 / 16f, 15 / 16f, 0f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(6 / 16f, 10 / 16f, 0f, 1f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 1f, 6 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 1f, 12 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 16 / 16f, 0f, 1f, 6 / 16f, 10 / 16f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f, 9 / 16f, 10 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 12 / 16f, 15 / 16f, 10 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(6 / 16f, 10 / 16f, 0f, 1f, 14 / 16f, 16 / 16f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 6 / 16f, 6 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 6 / 16f, 12 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 0f, 1f, 6 / 16f, 10 / 16f, mimic, model, extraData, rand, tintIndex));
                }
            }
            if (design == 4) {
                if (state.getValue(FenceFrameBlock.NORTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, 2 / 16f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 13 / 16f, 15 / 16f, 2 / 16f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f, 8 / 16f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 12 / 16f, 14 / 16f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.EAST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 13 / 16f, 7 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 13 / 16f, 13 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 6 / 16f, 8 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 12 / 16f, 14 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.SOUTH)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, 10 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 13 / 16f, 15 / 16f, 10 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f, 8 / 16f, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 12 / 16f, 14 / 16f, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                }
                if (state.getValue(FenceFrameBlock.WEST)) {
                    quads.addAll(ModelHelper.createSixFaceCuboid(3 / 16f, 6 / 16f, 7 / 16f, 9 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(3 / 16f, 6 / 16f, 13 / 16f, 15 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 6 / 16f, 8 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 12 / 16f, 14 / 16f, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                }
            }
            int overlayIndex = extraData.getData(FrameBlockTile.OVERLAY);
            if (overlayIndex != 0) {
                quads.addAll(ModelHelper.createOverlay(6 / 16f, 10 / 16f, 0f, 1f, 6 / 16f, 10 / 16f, overlayIndex));
            }
            return quads;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
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
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("minecraft", "block/oak_planks"));
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }

    @Override
    public ItemTransforms getTransforms() {
        return ItemTransforms.NO_TRANSFORMS;
    }
}
//========SOLI DEO GLORIA========//