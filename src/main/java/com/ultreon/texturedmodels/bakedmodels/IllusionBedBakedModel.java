package com.ultreon.texturedmodels.bakedmodels;

import com.ultreon.texturedmodels.block.BedFrameBlock;
import com.ultreon.texturedmodels.tileentity.BedFrameTile;
import com.ultreon.texturedmodels.util.ModelHelper;
import com.ultreon.texturedmodels.util.TextureHelper;
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
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
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
 * @version 1.1 09/21/20
 */
@SuppressWarnings("deprecation")
public class IllusionBedBakedModel implements IDynamicBakedModel {
    public static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "block/oak_planks");

    private TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TEXTURE);
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        BlockState mimic = extraData.getData(BedFrameTile.MIMIC);
        if (mimic != null) {
            ModelResourceLocation location = BlockModelShaper.stateToModelLocation(mimic);
            if (location != null) {
                BakedModel model = Minecraft.getInstance().getModelManager().getModel(location);
                if (model != null) {
                    return getMimicQuads(state, side, rand, extraData, model);
                }
            }
        }

        return Collections.emptyList();
    }

    public List<BakedQuad> getMimicQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData, BakedModel model) {
        if (side != null) {
            return Collections.emptyList();
        }
        BlockState mimic = extraData.getData(BedFrameTile.MIMIC);
        if (mimic != null && state != null) {
            int tintIndex = -1;
            if (mimic.getBlock() instanceof GrassBlock) {
                tintIndex = 1;
            }
            List<BakedQuad> quads = new ArrayList<>(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 5 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
            TextureAtlasSprite pillow = TextureHelper.getWoolTextures().get(extraData.getData(BedFrameTile.PILLOW));
            TextureAtlasSprite blanket = TextureHelper.getWoolTextures().get(extraData.getData(BedFrameTile.BLANKET));
            Integer design = extraData.getData(BedFrameTile.DESIGN);
            if (design == null) {
                return quads;
            }
            List<TextureAtlasSprite> planksList = TextureHelper.getPlanksTextures();
            TextureAtlasSprite planks;
            Integer desTex = extraData.getData(BedFrameTile.DESIGN_TEXTURE);
            if (desTex == null || desTex < 0 || desTex > 7) {
                return quads;
            } else {
                planks = planksList.get(desTex);
            }
            //four bed support cubes (bed feet)
            if (state.getValue(BedFrameBlock.PART) == BedPart.FOOT) {
                switch (state.getValue(BedBlock.FACING)) {
                    case NORTH:
                        quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 0f, 3 / 16f, 13 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 0f, 3 / 16f, 13 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                        break;
                    case EAST:
                        quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 0f, 3 / 16f, 0f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 0f, 3 / 16f, 13 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                        break;
                    case SOUTH:
                        quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 0f, 3 / 16f, 0f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 0f, 3 / 16f, 0f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                        break;
                    case WEST:
                        quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 0f, 3 / 16f, 0f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 0f, 3 / 16f, 13 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                        break;
                }

            }
            if (state.getValue(BedFrameBlock.PART) == BedPart.HEAD) {
                switch (state.getValue(BedBlock.FACING)) {
                    case SOUTH:
                        quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 0f, 3 / 16f, 13 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 0f, 3 / 16f, 13 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                        break;
                    case WEST:
                        quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 0f, 3 / 16f, 0f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 0f, 3 / 16f, 13 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                        break;
                    case NORTH:
                        quads.addAll(ModelHelper.createSixFaceCuboid(0f, 3 / 16f, 0f, 3 / 16f, 0f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 0f, 3 / 16f, 0f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                        break;
                    case EAST:
                        quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 0f, 3 / 16f, 0f, 3 / 16f, mimic, model, extraData, rand, tintIndex));
                        quads.addAll(ModelHelper.createSixFaceCuboid(13 / 16f, 1f, 0f, 3 / 16f, 13 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                        break;
                }
            }
            if (design == 0 || design == 1) {
                if (state.getValue(BedFrameBlock.PART) == BedPart.FOOT) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case NORTH:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 9 / 16f, -8 / 16f, 0f, blanket, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 24 / 16f, 5 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            break;
                        case SOUTH:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 9 / 16f, 1f, 24 / 16f, blanket, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-8 / 16f, 0f, 5 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            break;
                    }
                }
                if (state.getValue(BedFrameBlock.PART) == BedPart.HEAD) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case SOUTH:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, 8 / 16f, 1f, pillow, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createCuboid(0f, 8 / 16f, 5 / 16f, 10 / 16f, 0f, 1f, pillow, tintIndex));
                            break;
                        case NORTH:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, 0f, 8 / 16f, pillow, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createCuboid(8 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 1f, pillow, tintIndex));
                            break;
                    }
                }
            }
            if (design == 1) {
                if (state.getValue(BedFrameBlock.PART) == BedPart.FOOT) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case NORTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }

                }
                if (state.getValue(BedFrameBlock.PART) == BedPart.HEAD) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case SOUTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case NORTH:
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createSixFaceCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                }
            }
            if (design == 2) {
                if (state.getValue(BedFrameBlock.PART) == BedPart.FOOT) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case NORTH:
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 9 / 16f, 0f, 14 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 9 / 16f, -8 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1 / 16f, 5 / 16f, 10 / 16f, 0f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(15 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 5 / 16f, 12 / 16f, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createCuboid(2 / 16f, 1f, 5 / 16f, 9 / 16f, 1 / 16f, 15 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 24 / 16f, 5 / 16f, 9 / 16f, 1 / 16f, 15 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 1 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 1f, 5 / 16f, 10 / 16f, 15 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f, 12 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case SOUTH:
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 9 / 16f, 2 / 16f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 9 / 16f, 1f, 24 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1 / 16f, 5 / 16f, 10 / 16f, 2 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(15 / 16f, 1f, 5 / 16f, 10 / 16f, 2 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 5 / 16f, 12 / 16f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createCuboid(0f, 14 / 16f, 5 / 16f, 9 / 16f, 1 / 16f, 15 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-8 / 16f, 0f, 5 / 16f, 9 / 16f, 1 / 16f, 15 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 14 / 16f, 5 / 16f, 10 / 16f, 0f, 1 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 14 / 16f, 5 / 16f, 10 / 16f, 15 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f, 12 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                }
                if (state.getValue(BedFrameBlock.PART) == BedPart.HEAD) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case SOUTH:
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 10 / 16f, 8 / 16f, 14 / 16f, pillow, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1 / 16f, 5 / 16f, 10 / 16f, 0f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(15 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 5 / 16f, 12 / 16f, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createCuboid(2 / 16f, 8 / 16f, 5 / 16f, 10 / 16f, 1 / 16f, 15 / 16f, pillow, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 1 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 1f, 5 / 16f, 10 / 16f, 15 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 5 / 16f, 12 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case NORTH:
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 10 / 16f, 2 / 16f, 8 / 16f, pillow, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1 / 16f, 5 / 16f, 10 / 16f, 2 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(15 / 16f, 1f, 5 / 16f, 10 / 16f, 2 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 5 / 16f, 12 / 16f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createCuboid(8 / 16f, 14 / 16f, 5 / 16f, 10 / 16f, 1 / 16f, 15 / 16f, pillow, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 14 / 16f, 5 / 16f, 10 / 16f, 0f, 1 / 16f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(0f, 14 / 16f, 5 / 16f, 10 / 16f, 15 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                            quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 5 / 16f, 12 / 16f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                            break;
                    }
                }
            }
            if (design == 3) {
                if (state.getValue(BedFrameBlock.PART) == BedPart.FOOT) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case NORTH:
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 9 / 16f, 0f, 14 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 9 / 16f, -8 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1 / 16f, 5 / 16f, 10 / 16f, 0f, 14 / 16f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(15 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 14 / 16f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 12 / 16f, 14 / 16f, 1f, planks, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createCuboid(2 / 16f, 1f, 5 / 16f, 9 / 16f, 1 / 16f, 15 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 24 / 16f, 5 / 16f, 9 / 16f, 1 / 16f, 15 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(2 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 1 / 16f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(2 / 16f, 1f, 5 / 16f, 10 / 16f, 15 / 16f, 1f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 2 / 16f, 5 / 16f, 12 / 16f, 0f, 1f, planks, tintIndex));
                            break;
                        case SOUTH:
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 9 / 16f, 2 / 16f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 9 / 16f, 1f, 24 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1 / 16f, 5 / 16f, 10 / 16f, 2 / 16f, 1f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(15 / 16f, 1f, 5 / 16f, 10 / 16f, 2 / 16f, 1f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 12 / 16f, 0f, 2 / 16f, planks, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createCuboid(0f, 14 / 16f, 5 / 16f, 9 / 16f, 1 / 16f, 15 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-8 / 16f, 0f, 5 / 16f, 9 / 16f, 1 / 16f, 15 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 14 / 16f, 5 / 16f, 10 / 16f, 0f, 1 / 16f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 14 / 16f, 5 / 16f, 10 / 16f, 15 / 16f, 1f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(14 / 16f, 1f, 5 / 16f, 12 / 16f, 0f, 1f, planks, tintIndex));
                            break;
                    }
                }
                if (state.getValue(BedFrameBlock.PART) == BedPart.HEAD) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case SOUTH:
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 10 / 16f, 8 / 16f, 14 / 16f, pillow, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1 / 16f, 5 / 16f, 10 / 16f, 0f, 14 / 16f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(15 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 14 / 16f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 12 / 16f, 14 / 16f, 1f, planks, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createCuboid(2 / 16f, 8 / 16f, 5 / 16f, 10 / 16f, 1 / 16f, 15 / 16f, pillow, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(2 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 1 / 16f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(2 / 16f, 1f, 5 / 16f, 10 / 16f, 15 / 16f, 1f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 2 / 16f, 5 / 16f, 12 / 16f, 0f, 1f, planks, tintIndex));
                            break;
                        case NORTH:
                            quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 5 / 16f, 10 / 16f, 2 / 16f, 8 / 16f, pillow, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1 / 16f, 5 / 16f, 10 / 16f, 2 / 16f, 1f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(15 / 16f, 1f, 5 / 16f, 10 / 16f, 2 / 16f, 1f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 12 / 16f, 0f, 2 / 16f, planks, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createCuboid(8 / 16f, 14 / 16f, 5 / 16f, 10 / 16f, 1 / 16f, 15 / 16f, pillow, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 14 / 16f, 5 / 16f, 10 / 16f, 0f, 1 / 16f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 14 / 16f, 5 / 16f, 10 / 16f, 15 / 16f, 1f, planks, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(14 / 16f, 1f, 5 / 16f, 12 / 16f, 0f, 1f, planks, tintIndex));
                            break;
                    }
                }
            }
            if (design == 4) {
                if (state.getValue(BedFrameBlock.PART) == BedPart.FOOT) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case NORTH:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, -8 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, -8 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, -8 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 9 / 16f, -9 / 16f, -8 / 16f, blanket, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 24 / 16f, 5 / 16f, 10 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 24 / 16f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 24 / 16f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(24 / 16f, 25 / 16f, 5 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            break;
                        case SOUTH:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, 1f, 24 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-1 / 16f, 0f, 3 / 16f, 9 / 16f, 1f, 24 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 1f, 24 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 9 / 16f, 24 / 16f, 25 / 16f, blanket, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-8 / 16f, 0f, 5 / 16f, 10 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(1f, 17 / 16f, 3 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-8 / 16f, 0f, 3 / 16f, 9 / 16f, -1 / 16f, 0f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-8 / 16f, 0f, 3 / 16f, 9 / 16f, 1f, 17 / 16f, blanket, tintIndex));
                            quads.addAll(ModelHelper.createCuboid(-9 / 16f, -8 / 16f, 5 / 16f, 9 / 16f, 0f, 1f, blanket, tintIndex));
                            break;
                    }
                }
                if (state.getValue(BedFrameBlock.PART) == BedPart.HEAD) {
                    switch (state.getValue(BedBlock.FACING)) {
                        case SOUTH:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, 9 / 16f, 1f, pillow, tintIndex));
                            break;
                        case WEST:
                            quads.addAll(ModelHelper.createCuboid(0f, 7 / 16f, 5 / 16f, 10 / 16f, 0f, 1f, pillow, tintIndex));
                            break;
                        case NORTH:
                            quads.addAll(ModelHelper.createCuboid(0f, 1f, 5 / 16f, 10 / 16f, 0f, 7 / 16f, pillow, tintIndex));
                            break;
                        case EAST:
                            quads.addAll(ModelHelper.createCuboid(9 / 16f, 1f, 5 / 16f, 10 / 16f, 0f, 1f, pillow, tintIndex));
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
        return getTexture();
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