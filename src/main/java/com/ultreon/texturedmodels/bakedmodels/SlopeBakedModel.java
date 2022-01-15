package com.ultreon.texturedmodels.bakedmodels;

import com.ultreon.texturedmodels.block.FrameBlock;
import com.ultreon.texturedmodels.tileentity.FrameBlockTile;
import com.ultreon.texturedmodels.util.ModelHelper;
import com.ultreon.texturedmodels.util.TextureHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.Vec3;
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
 * @version 1.3 10/20/20
 */
public class SlopeBakedModel implements IDynamicBakedModel {
    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        //get block saved in frame tile
        BlockState mimic = extraData.getData(FrameBlockTile.MIMIC);
        if (mimic != null && !(mimic.getBlock() instanceof FrameBlock)) {
            ModelResourceLocation location = BlockModelShaper.stateToModelLocation(mimic);
            if (location != null) {
                BakedModel model = Minecraft.getInstance().getModelManager().getModel(location);
                model.getBakedModel().getQuads(mimic, side, rand, extraData);
                if (model != null) {
                    //only if model (from block saved in tile entity) exists:
                    return getMimicQuads(state, side, rand, extraData, model);
                }
            }
        }
        return Collections.emptyList();
    }

    private static Vec3 v(double x, double y, double z) {
        return new Vec3(x, y, z);
    }

    public List<BakedQuad> getMimicQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData, BakedModel model) {
        List<BakedQuad> quads = new ArrayList<>();
        BlockState mimic = extraData.getData(FrameBlockTile.MIMIC);
        List<TextureAtlasSprite> texture = TextureHelper.getTextureFromModel(model, extraData, rand);
        int index = extraData.getData(FrameBlockTile.TEXTURE);
        if (index >= texture.size()) {
            index = 0;
        }
        if (texture.size() == 0) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.displayClientMessage(new TranslatableComponent("We're sorry, but this block can't be displayed"), true);
            }
            return Collections.emptyList();
        }
        //TODO Remove when slopes are fixed
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.displayClientMessage(new TranslatableComponent("We're sorry, but Slopes do not work at the moment"), true);
        }
        int tintIndex = -1;
        if (mimic.getBlock() instanceof GrassBlock) {
            tintIndex = 1;
        }
        double w = 0.5;
        if (state.getValue(StairBlock.HALF) == Half.TOP) {
            w = -0.5;
        }
        //Eight corners of the block
        Vec3 NWU = v(0, 0.5 + w, 0); //North-West-Up
        Vec3 NEU = v(1, 0.5 + w, 0); //...
        Vec3 NWD = v(0, 0.5 - w, 0);
        Vec3 NED = v(1, 0.5 - w, 0);
        Vec3 SWU = v(0, 0.5 + w, 1);
        Vec3 SEU = v(1, 0.5 + w, 1);
        Vec3 SWD = v(0, 0.5 - w, 1);
        Vec3 SED = v(1, 0.5 - w, 1); //South-East-Down
        //bottom face
        /*quads.add(ModelHelper.createQuad(SED, SWD, NWD, NED, texture.get(index), 0, 16, 0, 16, tintIndex));
        switch (state.get(StairsBlock.FACING)) {
            case NORTH:
                SWU = v(0,0.5-w,1);
                SEU = v(1,0.5-w,1);
                SWD = v(0,0.5+w,1);
                SED = v(1,0.5+w,1);
                //back face
                quads.add(ModelHelper.createQuad(NEU, NED, NWD, NWU, texture.get(index), 0, 16, 0, 16, tintIndex));

                quads.add(ModelHelper.createQuad(SWU, NWU, NWD, SWU, texture.get(index), 0, 16, 0, 16, tintIndex));
                quads.add(ModelHelper.createQuad(NEU, SEU, NED, NEU, texture.get(index), 0, 16, 0, 16, tintIndex));


                break;
            case WEST:
                NED = v(1,0.5+w,0);
                NEU = v(1,0.5-w,0);
                SEU = v(1,0.5-w,1);
                SED = v(1,0.5+w,1);
                //back face
                quads.add(ModelHelper.createQuad(NWU, NWD, SWD, SWU, texture.get(index), 0, 16, 0, 16, tintIndex));
                break;
            case SOUTH:
                NWU = v(0,0.5-w,0);
                NEU = v(1,0.5-w,0);
                NWD = v(0,0.5+w,0);
                NED = v(1,0.5+w,0);
                //back face
                quads.add(ModelHelper.createQuad(SWU, SWD, SED, SEU, texture.get(index), 0, 16, 0, 16,tintIndex));
                break;
            case EAST:
                SWU = v(0,0.5-w,1);
                SWD = v(0,0.5+w,1);
                NWU = v(0,0.5-w,0);
                NWD = v(0,0.5+w,0);
                //back face
                quads.add(ModelHelper.createQuad(SEU, SED, NED, NEU, texture.get(index), 0, 16, 0, 16, tintIndex));
                break;
        }
        //top face
        quads.add(ModelHelper.createQuad(SWU, SEU, NEU, NWU, texture.get(index), 0, 16, 0, 16, tintIndex));
        //quads.addAll(ModelHelper.createQuad(0,1,0,1,0,1, texture.get(index), tintIndex,state.get(StairsBlock.FACING), state.get(StairsBlock.HALF))); //TODO remove or fix
        */
        quads.addAll(createSlope(0, 1, 0, 1, 0, 1, texture.get(index), tintIndex, state.getValue(StairBlock.FACING)));
        return quads;
    }

    public List<BakedQuad> createSlope(float xl, float xh, float yl, float yh, float zl, float zh, TextureAtlasSprite texture, int tintIndex, Direction direction) {
        List<BakedQuad> quads = new ArrayList<>();
        //Eight corners of the block
        Vec3 NWU = v(xl, yh, zl); //North-West-Up
        Vec3 SWU = v(xl, yh, zh); //...
        Vec3 NWD = v(xl, yl, zl);
        Vec3 SWD = v(xl, yl, zh);
        Vec3 NEU = v(xh, yh, zl);
        Vec3 SEU = v(xh, yh, zh);
        Vec3 NED = v(xh, yl, zl);
        Vec3 SED = v(xh, yl, zh); //South-East-Down
        if (xh - xl > 1 || yh - yl > 1 || zh - zl > 1) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.displayClientMessage(new TranslatableComponent("An error occured with this block, please report to the mod author (PianoManu)"), true);
            }
            return quads;
        }
        if (xl < 0) {
            xl++;
            xh++;
        }
        if (xh > 1) {
            xh--;
            xl--;
        }
        if (yl < 0) {
            yl++;
            yh++;
        }
        if (yh > 1) {
            yh--;
            yl--;
        }
        if (zl < 0) {
            zl++;
            zh++;
        }
        if (zh > 1) {
            zh--;
            zl--;
        }
        //bottom face
        quads.add(ModelHelper.createQuad(SWD, NWD, NED, SED, texture, 0, 16, 0, 16, tintIndex));
        switch (direction) {
            case NORTH:
                quads.add(ModelHelper.createQuad(NEU, NED, NWD, NWU, texture, 0, 16, 0, 16, tintIndex));

                quads.add(ModelHelper.createQuad(NEU, SED, NED, NEU, texture, 0, 16, 0, 16, tintIndex));
                quads.add(ModelHelper.createQuadInverted(NWD, SWD, NWU, NWU, texture, 0, 16, 16, 0, tintIndex));
                //top face
                quads.add(ModelHelper.createQuad(NWU, SWD, SED, NEU, texture, 0, 16, 0, 16, tintIndex));
                break;
            case WEST:
                //back face
                quads.add(ModelHelper.createQuad(NWU, NWD, SWD, SWU, texture, 0, 16, 0, 16, tintIndex));

                quads.add(ModelHelper.createQuad(NED, NED, NWD, NWU, texture, 0, 16, 0, 16, tintIndex));
                quads.add(ModelHelper.createQuadInverted(SWD, SED, SWU, SWU, texture, 0, 16, 16, 0, tintIndex));

                //top face
                quads.add(ModelHelper.createQuad(SWU, SED, NED, NWU, texture, 0, 16, 0, 16, tintIndex));
                break;
            case SOUTH:
                quads.add(ModelHelper.createQuad(SWU, SWD, SED, SEU, texture, 0, 16, 0, 16, tintIndex));

                quads.add(ModelHelper.createQuadInverted(NED, SEU, SED, NED, texture, 16, 0, 0, 16, tintIndex));
                quads.add(ModelHelper.createQuad(SWU, NWD, SWD, SWU, texture, 0, 16, 0, 16, tintIndex));
                //top face
                quads.add(ModelHelper.createQuad(NWD, SWU, SEU, NED, texture, 16, 0, 16, 0, tintIndex));
                break;
            case EAST:
                //back face
                quads.add(ModelHelper.createQuad(SEU, SED, NED, NEU, texture, 0, 16, 0, 16, tintIndex));

                quads.add(ModelHelper.createQuadInverted(NED, NWD, NWD, NEU, texture, 0, 16, 16, 0, tintIndex));
                quads.add(ModelHelper.createQuad(SED, SEU, SEU, SWD, texture, 16, 0, 16, 0, tintIndex));
                //top face
                quads.add(ModelHelper.createQuad(SWD, SEU, NEU, NWD, texture, 16, 0, 16, 0, tintIndex));
                break;
        }
        //top face
        quads.add(ModelHelper.createQuad(NEU, SEU, SWU, NWU, texture, 0, 16, 0, 16, tintIndex));
        return quads;
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