package com.ultreon.texturedmodels.bakedmodels;

import com.ultreon.texturedmodels.block.FrameBlock;
import com.ultreon.texturedmodels.block.SixWaySlabFrameBlock;
import com.ultreon.texturedmodels.tileentity.FrameBlockEntity;
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
 * @version 1.4 10/01/20
 */
public class IllusionSlabBakedModel implements IDynamicBakedModel {
    @NotNull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData) {
        BlockState mimic = extraData.getData(FrameBlockEntity.MIMIC);
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

    private List<BakedQuad> getIllusionQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull Random rand, @NotNull IModelData extraData, BakedModel model) {
        if (side != null) {
            return Collections.emptyList();
        }
        BlockState mimic = extraData.getData(FrameBlockEntity.MIMIC);
        if (mimic != null && state != null) {
            int tintIndex = -1;
            if (mimic.getBlock() instanceof GrassBlock) {
                tintIndex = 1;
            }
            List<BakedQuad> quads = new ArrayList<>();
            switch (state.getValue(SixWaySlabFrameBlock.FACING)) {
                case UP:
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 0f, .5f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                    break;
                case DOWN:
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, .5f, 1f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                    break;
                case WEST:
                    quads.addAll(ModelHelper.createSixFaceCuboid(.5f, 1f, 0f, 1f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                    break;
                case SOUTH:
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 0f, 1f, 0f, .5f, mimic, model, extraData, rand, tintIndex));
                    break;
                case NORTH:
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, 1f, 0f, 1f, .5f, 1f, mimic, model, extraData, rand, tintIndex));
                    break;
                case EAST:
                    quads.addAll(ModelHelper.createSixFaceCuboid(0f, .5f, 0f, 1f, 0f, 1f, mimic, model, extraData, rand, tintIndex));
                    break;
            }
            int overlayIndex = extraData.getData(FrameBlockEntity.OVERLAY);
            if (extraData.getData(FrameBlockEntity.OVERLAY) != 0) {
                switch (state.getValue(SixWaySlabFrameBlock.FACING)) {
                    case UP:
                        quads.addAll(ModelHelper.createOverlay(0f, 1f, 0f, .5f, 0f, 1f, overlayIndex));
                        break;
                    case DOWN:
                        quads.addAll(ModelHelper.createOverlay(0f, 1f, .5f, 1f, 0f, 1f, overlayIndex));
                        break;
                    case WEST:
                        quads.addAll(ModelHelper.createOverlay(.5f, 1f, 0f, 1f, 0f, 1f, overlayIndex));
                        break;
                    case SOUTH:
                        quads.addAll(ModelHelper.createOverlay(0f, 1f, 0f, 1f, 0f, .5f, overlayIndex));
                        break;
                    case NORTH:
                        quads.addAll(ModelHelper.createOverlay(0f, 1f, 0f, 1f, .5f, 1f, overlayIndex));
                        break;
                    case EAST:
                        quads.addAll(ModelHelper.createOverlay(0f, .5f, 0f, 1f, 0f, 1f, overlayIndex));
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
