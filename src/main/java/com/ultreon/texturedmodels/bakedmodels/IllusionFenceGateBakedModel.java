package com.ultreon.texturedmodels.bakedmodels;

import com.ultreon.texturedmodels.block.FrameBlock;
import com.ultreon.texturedmodels.tileentity.FrameBlockTile;
import com.ultreon.texturedmodels.util.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
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
 * @version 1.0 09/24/20
 */
public class IllusionFenceGateBakedModel implements IDynamicBakedModel {

    public static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "block/oak_planks");

    private TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TEXTURE);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        BlockState mimic = extraData.getData(FrameBlockTile.MIMIC);
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

    @Nonnull
    public List<BakedQuad> getMimicQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, IModelData extraData, BakedModel model) {
        BlockState mimic = extraData.getData(FrameBlockTile.MIMIC);
        Integer design = extraData.getData(FrameBlockTile.DESIGN);
        if (side != null) {
            return Collections.emptyList();
        }
        if (mimic != null && state != null) {
            int tintIndex = -1;
            if (mimic.getBlock() instanceof GrassBlock) {
                tintIndex = 1;
            }
            float w = 0;
            if (state.getValue(FenceGateBlock.IN_WALL)) {
                w = -3 / 16f;
            }
            List<BakedQuad> quads = new ArrayList<>();
            if (design == 0 || design == 3) {
                if (state.getValue(FenceGateBlock.OPEN)) {
                    switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case NORTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            //0-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 15 / 16f + w, 1 / 16f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                            //1-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 15 / 16f + w, 1 / 16f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 9 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 12 / 16f + w, 15 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 9 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 12 / 16f + w, 15 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            //0-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 15 / 16f + w, 13 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            //1-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 15 / 16f + w, 13 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 9 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 12 / 16f + w, 15 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 9 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 12 / 16f + w, 15 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //1-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 15 / 16f, 6 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            //1-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 15 / 16f, 6 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 6 / 16f + w, 9 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 12 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 6 / 16f + w, 9 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 12 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //0-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 3 / 16f, 6 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            //0-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 3 / 16f, 6 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 6 / 16f + w, 9 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 12 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 6 / 16f + w, 9 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 12 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                } else {
                    switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case NORTH:
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 6 / 16f + w, 9 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 12 / 16f + w, 15 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(6 / 16f, 10 / 16f, 9 / 16f + w, 12 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f + w, 9 / 16f + w, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 12 / 16f + w, 15 / 16f + w, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 9 / 16f + w, 12 / 16f + w, 6 / 16f, 10 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                }
            }
            if (design == 1) {
                if (state.getValue(FenceGateBlock.OPEN)) {
                    switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case NORTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 9 / 16f + w, 1 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 12 / 16f + w, 15 / 16f + w, 1 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 9 / 16f + w, 1 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 12 / 16f + w, 15 / 16f + w, 1 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 9 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 12 / 16f + w, 15 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 9 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 12 / 16f + w, 15 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 6 / 16f + w, 9 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 12 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 6 / 16f + w, 9 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 12 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 6 / 16f + w, 9 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 12 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 6 / 16f + w, 9 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 12 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                } else {
                    switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case NORTH:
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 6 / 16f + w, 9 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 12 / 16f + w, 15 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f + w, 9 / 16f + w, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 12 / 16f + w, 15 / 16f + w, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                }
            }
            if (design == 2) {
                if (state.getValue(FenceGateBlock.OPEN)) {
                    switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case NORTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            //0-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 13 / 16f + w, 1 / 16f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                            //1-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 13 / 16f + w, 1 / 16f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                            //2 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 15 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 15 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            //0-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 13 / 16f + w, 13 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            //1-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 13 / 16f + w, 13 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            //2 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 15 / 16f + w, 9 / 16f, 13 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 15 / 16f + w, 9 / 16f, 13 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //1-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 15 / 16f, 6 / 16f + w, 13 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            //1-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 15 / 16f, 6 / 16f + w, 13 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //2 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 13 / 16f, 6 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 13 / 16f, 6 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //0-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 3 / 16f, 6 / 16f + w, 13 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            //0-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 3 / 16f, 6 / 16f + w, 13 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //2 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(3 / 16f, 7 / 16f, 6 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(3 / 16f, 7 / 16f, 6 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                } else {
                    switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case NORTH:
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 6 / 16f, 6 / 16f + w, 15 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(10 / 16f, 14 / 16f, 6 / 16f + w, 15 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(6 / 16f, 10 / 16f, 6 / 16f + w, 13 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f + w, 15 / 16f + w, 2 / 16f, 6 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f + w, 15 / 16f + w, 10 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f + w, 13 / 16f + w, 6 / 16f, 10 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                }
            }
            if (design == 3) {
                switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                    case NORTH:
                    case SOUTH:
                        quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 0f, 1f, 6 / 16f, 10 / 16f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 0f, 1f, 6 / 16f, 10 / 16f, mimic, model, extraData, rand, tintIndex));
                        break;
                    case EAST:
                    case WEST:
                        quads.addAll(ModelHelper.createSixFaceCuboid(6 / 16f, 10 / 16f, 0f, 1f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(6 / 16f, 10 / 16f, 0f, 1f, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                        break;
                }
            }
            if (design == 4) {
                //inverts gate height when connected with walls
                w = -3 / 16f;
                if (state.getValue(FenceGateBlock.IN_WALL)) {
                    w = 0;
                }
                if (state.getValue(FenceGateBlock.OPEN)) {
                    switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case NORTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            //0-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 15 / 16f + w, 1 / 16f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                            //1-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 15 / 16f + w, 1 / 16f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 9 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 12 / 16f + w, 15 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 9 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 12 / 16f + w, 15 / 16f + w, 3 / 16f, 7 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            //0-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 15 / 16f + w, 13 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            //1-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 15 / 16f + w, 13 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 6 / 16f + w, 9 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 12 / 16f + w, 15 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 6 / 16f + w, 9 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 12 / 16f + w, 15 / 16f + w, 9 / 16f, 15 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //1-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 15 / 16f, 6 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            //1-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 15 / 16f, 6 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 6 / 16f + w, 9 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 12 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 6 / 16f + w, 9 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(9 / 16f, 15 / 16f, 12 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //0-0 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 3 / 16f, 6 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            //0-1 post
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 3 / 16f, 6 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            //4 Crossbars
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 6 / 16f + w, 9 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 12 / 16f + w, 15 / 16f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 6 / 16f + w, 9 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1 / 16f, 7 / 16f, 12 / 16f + w, 15 / 16f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                } else {
                    switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
                        case NORTH:
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f + w, 1f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 6 / 16f + w, 9 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 12 / 16f + w, 15 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(6 / 16f, 10 / 16f, 9 / 16f + w, 12 / 16f + w, 7 / 16f, 9 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 5 / 16f + w, 1f + w, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 6 / 16f + w, 9 / 16f + w, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 12 / 16f + w, 15 / 16f + w, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(7 / 16f, 9 / 16f, 9 / 16f + w, 12 / 16f + w, 6 / 16f, 10 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                }
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
//========SOLI DEO GLORIA========//