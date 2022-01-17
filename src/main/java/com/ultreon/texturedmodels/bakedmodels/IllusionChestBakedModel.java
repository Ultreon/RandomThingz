package com.ultreon.texturedmodels.bakedmodels;

import com.ultreon.texturedmodels.QTextureModels;
import com.ultreon.texturedmodels.block.FrameBlock;
import com.ultreon.texturedmodels.tileentity.ChestFrameTileEntity;
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
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
 * @version 1.0 09/22/20
 */
@SuppressWarnings("deprecation")
public class IllusionChestBakedModel implements IDynamicBakedModel {
    public static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "block/oak_planks");

    private TextureAtlasSprite getTexture() {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TEXTURE);
    }

    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
        BlockState mimic = extraData.getData(ChestFrameTileEntity.MIMIC);
        if (mimic != null && !(mimic.getBlock() instanceof FrameBlock)) {
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

    public List<BakedQuad> getMimicQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData, BakedModel model) {
        if (side != null) {
            return Collections.emptyList();
        }
        BlockState mimic = extraData.getData(ChestFrameTileEntity.MIMIC);
        if (mimic != null && state != null) {
            List<TextureAtlasSprite> designTextureList = new ArrayList<>(TextureHelper.getMetalTextures());
            designTextureList.add(Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("minecraft", "block/shulker_box")));
            TextureAtlasSprite chestFront = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(QTextureModels.MOD_ID, "block/chest_front"));
            TextureAtlasSprite chestSide = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(QTextureModels.MOD_ID, "block/chest_side"));
            TextureAtlasSprite chestTop = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(QTextureModels.MOD_ID, "block/chest_top"));
            int tintIndex = -1;
            if (mimic.getBlock() instanceof GrassBlock) {
                tintIndex = 1;
            }
            int design = extraData.getData(ChestFrameTileEntity.DESIGN);
            int desTex = extraData.getData(ChestFrameTileEntity.DESIGN_TEXTURE);
            TextureAtlasSprite designTexture = designTextureList.get(desTex);
            List<BakedQuad> quads = new ArrayList<>();
            if (design == 0) {
                return new ArrayList<>(ModelHelper.createSixFaceCuboid(0f, 1f, 0f, 1f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
            }
            if (design == 1 || design == 2) {
                quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 2 / 16f, 14 / 16f, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
            }
            if (design == 3) {
                quads.addAll(ModelHelper.createCuboid(2 / 16f, 14 / 16f, 2 / 16f, 14 / 16f, 2 / 16f, 14 / 16f, designTexture, tintIndex));
            }
            if (design == 1 || design == 3 || design == 4) {
                //vertical
                quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 0f, 1f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 0f, 1f, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 0f, 1f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 0f, 1f, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                //horizontal down
                quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 0f, 2 / 16f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 0f, 2 / 16f, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 0f, 2 / 16f, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 0f, 2 / 16f, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                //horizontal up
                quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 14 / 16f, 1f, 0f, 2 / 16f, mimic, model, extraData, rand, tintIndex));
                quads.addAll(ModelHelper.createSixFaceCuboid(2 / 16f, 14 / 16f, 14 / 16f, 1f, 14 / 16f, 1f, mimic, model, extraData, rand, tintIndex));
                quads.addAll(ModelHelper.createSixFaceCuboid(0f, 2 / 16f, 14 / 16f, 1f, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
                quads.addAll(ModelHelper.createSixFaceCuboid(14 / 16f, 1f, 14 / 16f, 1f, 2 / 16f, 14 / 16f, mimic, model, extraData, rand, tintIndex));
            }
            if (design == 2) {
                //vertical
                quads.addAll(ModelHelper.createCuboid(0f, 2 / 16f, 0f, 1f, 0f, 2 / 16f, designTexture, tintIndex));
                quads.addAll(ModelHelper.createCuboid(0f, 2 / 16f, 0f, 1f, 14 / 16f, 1f, designTexture, tintIndex));
                quads.addAll(ModelHelper.createCuboid(14 / 16f, 1f, 0f, 1f, 0f, 2 / 16f, designTexture, tintIndex));
                quads.addAll(ModelHelper.createCuboid(14 / 16f, 1f, 0f, 1f, 14 / 16f, 1f, designTexture, tintIndex));
                //horizontal down
                quads.addAll(ModelHelper.createCuboid(2 / 16f, 14 / 16f, 0f, 2 / 16f, 0f, 2 / 16f, designTexture, tintIndex));
                quads.addAll(ModelHelper.createCuboid(2 / 16f, 14 / 16f, 0f, 2 / 16f, 14 / 16f, 1f, designTexture, tintIndex));
                quads.addAll(ModelHelper.createCuboid(0f, 2 / 16f, 0f, 2 / 16f, 2 / 16f, 14 / 16f, designTexture, tintIndex));
                quads.addAll(ModelHelper.createCuboid(14 / 16f, 1f, 0f, 2 / 16f, 2 / 16f, 14 / 16f, designTexture, tintIndex));
                //horizontal up
                quads.addAll(ModelHelper.createCuboid(2 / 16f, 14 / 16f, 14 / 16f, 1f, 0f, 2 / 16f, designTexture, tintIndex));
                quads.addAll(ModelHelper.createCuboid(2 / 16f, 14 / 16f, 14 / 16f, 1f, 14 / 16f, 1f, designTexture, tintIndex));
                quads.addAll(ModelHelper.createCuboid(0f, 2 / 16f, 14 / 16f, 1f, 2 / 16f, 14 / 16f, designTexture, tintIndex));
                quads.addAll(ModelHelper.createCuboid(14 / 16f, 1f, 14 / 16f, 1f, 2 / 16f, 14 / 16f, designTexture, tintIndex));
            }
            if (design == 4) {
                quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 1 / 16f, 2 / 16f, 1 / 16f, 15 / 16f, chestTop, tintIndex));
                quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 14 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, chestTop, tintIndex));
                //has to be inverted because chest knob-thing is also inverted
                int[] ulow = {7, 7, 7, 9, 8, 9};
                int[] uhigh = {9, 9, 8, 7, 9, 7};
                int[] vlow = {4, 7, 4, 4, 4, 4};
                int[] vhigh = {5, 8, 8, 8, 8, 8};
                switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                    case NORTH:
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 2 / 16f, chestFront, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 14 / 16f, 15 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 2 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(14 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 8 / 16f, 12 / 16f, 0 / 16f, 1 / 16f, chestFront, tintIndex, ulow, uhigh, vlow, vhigh));
                        break;
                    case EAST:
                        ulow = new int[]{7, 8, 9, 8, 9, 7};
                        uhigh = new int[]{8, 9, 7, 9, 7, 8};
                        vlow = new int[]{6, 4, 4, 4, 4, 4};
                        vhigh = new int[]{4, 6, 8, 8, 8, 8};
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 2 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 14 / 16f, 15 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 2 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(14 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, chestFront, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(15 / 16f, 1f, 8 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, chestFront, tintIndex, ulow, uhigh, vlow, vhigh));
                        break;
                    case SOUTH:
                        ulow = new int[]{9, 9, 8, 9, 7, 9};
                        uhigh = new int[]{7, 7, 9, 7, 8, 7};
                        vlow = new int[]{4, 8, 4, 4, 4, 4};
                        vhigh = new int[]{5, 7, 8, 8, 8, 8};
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 2 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 14 / 16f, 15 / 16f, chestFront, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 2 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(14 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(7 / 16f, 9 / 16f, 8 / 16f, 12 / 16f, 15 / 16f, 1f, chestFront, tintIndex, ulow, uhigh, vlow, vhigh));
                        break;
                    case WEST:
                        ulow = new int[]{7, 7, 9, 7, 9, 8};
                        uhigh = new int[]{8, 8, 7, 8, 7, 9};
                        vlow = new int[]{4, 6, 4, 4, 4, 4};
                        vhigh = new int[]{6, 4, 8, 8, 8, 8};
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 2 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 14 / 16f, 15 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(1 / 16f, 2 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, chestFront, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(14 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, 1 / 16f, 15 / 16f, chestSide, tintIndex));
                        quads.addAll(ModelHelper.createCuboid(0 / 16f, 1 / 16f, 8 / 16f, 12 / 16f, 7 / 16f, 9 / 16f, chestFront, tintIndex, ulow, uhigh, vlow, vhigh));
                        break;
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
