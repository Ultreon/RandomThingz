package com.ultreon.texturedmodels.util;

import com.ultreon.texturedmodels.block.BedFrameBlock;
import com.ultreon.texturedmodels.setup.Registration;
import com.ultreon.texturedmodels.tileentity.BedFrameTile;
import com.ultreon.texturedmodels.tileentity.ChestFrameTileEntity;
import com.ultreon.texturedmodels.tileentity.FrameBlockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;

import static com.ultreon.texturedmodels.util.BCBlockStateProperties.CONTAINS_BLOCK;
import static com.ultreon.texturedmodels.util.BCBlockStateProperties.LIGHT_LEVEL;

/**
 * Util class for certain frame block things like light level and textures
 *
 * @author PianoManu
 * @version 1.6 10/29/20
 */
public class BlockAppearanceHelper {
    public static int setLightLevel(ItemStack item, BlockState state, Level dimension, BlockPos pos, Player player, InteractionHand hand) {
        if (item.getItem() == Items.GLOWSTONE_DUST && state.getValue(LIGHT_LEVEL) < 13) {
            int count = player.getItemInHand(hand).getCount();
            dimension.setBlockAndUpdate(pos, state.setValue(LIGHT_LEVEL, state.getBlock().getLightValue(state, dimension, pos) + 3));
            player.getItemInHand(hand).setCount(count - 1);
            player.displayClientMessage(new TranslatableComponent("Light Level: " + (state.getValue(LIGHT_LEVEL) + 3)), true);
        }
        if ((item.getItem() == Items.COAL || item.getItem() == Items.CHARCOAL) && state.getValue(LIGHT_LEVEL) < 15) {
            int count = player.getItemInHand(hand).getCount();
            dimension.setBlockAndUpdate(pos, state.setValue(LIGHT_LEVEL, state.getBlock().getLightValue(state, dimension, pos) + 1));
            player.getItemInHand(hand).setCount(count - 1);
            player.displayClientMessage(new TranslatableComponent("Light Level: " + (state.getValue(LIGHT_LEVEL) + 1)), true);
        }
        if (item.getItem() == Items.GLOWSTONE_DUST && state.getValue(LIGHT_LEVEL) >= 13) {
            player.displayClientMessage(new TranslatableComponent("Light Level: " + state.getValue(LIGHT_LEVEL)), true);
        }
        if ((item.getItem() == Items.COAL || item.getItem() == Items.CHARCOAL) && state.getValue(LIGHT_LEVEL) == 15) {
            player.displayClientMessage(new TranslatableComponent("Light Level: " + state.getValue(LIGHT_LEVEL)), true);
        }
        return state.getValue(LIGHT_LEVEL);
    }

    public static void setTexture(ItemStack item, BlockState state, Level dimension, Player player, BlockPos pos) {
        if (item.getItem() == Registration.TEXTURE_WRENCH.get() && !player.isShiftKeyDown() && state.getValue(CONTAINS_BLOCK)) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                if (fte.getTexture() < 5) { //six sides possible
                    fte.setTexture(fte.getTexture() + 1);
                } else {
                    fte.setTexture(0);
                }
                player.displayClientMessage(new TranslatableComponent("Texture: " + fte.getTexture()), true);
            }
            if (tileEntity instanceof BedFrameTile) {
                BedFrameTile fte = (BedFrameTile) tileEntity;
                if (fte.getTexture() < 5) { //six sides possible
                    fte.setTexture(fte.getTexture() + 1);
                } else {
                    fte.setTexture(0);
                }
                player.displayClientMessage(new TranslatableComponent("Texture: " + fte.getTexture()), true);
            }
            if (tileEntity instanceof ChestFrameTileEntity) {
                ChestFrameTileEntity fte = (ChestFrameTileEntity) tileEntity;
                if (fte.getTexture() < 5) { //six sides possible
                    fte.setTexture(fte.getTexture() + 1);
                } else {
                    fte.setTexture(0);
                }
                player.displayClientMessage(new TranslatableComponent("Texture: " + fte.getTexture()), true);
            }
        }
    }

    public static void setDesign(Level dimension, BlockPos pos, Player player, ItemStack item) {
        if (item.getItem() == Registration.CHISEL.get() && !player.isShiftKeyDown()) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                if (fte.getDesign() < fte.maxDesigns) {
                    fte.setDesign(fte.getDesign() + 1);
                } else {
                    fte.setDesign(0);
                }
                player.displayClientMessage(new TranslatableComponent("Design: " + fte.getDesign()), true);
            }
            if (tileEntity instanceof BedFrameTile) {
                BedFrameTile fte = (BedFrameTile) tileEntity;
                if (fte.getDesign() < fte.maxDesigns) {
                    fte.setDesign(fte.getDesign() + 1);
                } else {
                    fte.setDesign(0);
                }
                player.displayClientMessage(new TranslatableComponent("Design: " + fte.getDesign()), true);
            }
            if (tileEntity instanceof ChestFrameTileEntity) {
                ChestFrameTileEntity fte = (ChestFrameTileEntity) tileEntity;
                if (fte.getDesign() < fte.maxDesigns) {
                    fte.setDesign(fte.getDesign() + 1);
                } else {
                    fte.setDesign(0);
                }
                player.displayClientMessage(new TranslatableComponent("Design: " + fte.getDesign()), true);
            }
        }
    }

    public static void setDesignTexture(Level dimension, BlockPos pos, Player player, ItemStack item) {
        if (item.getItem() == Registration.PAINTBRUSH.get() && !player.isShiftKeyDown()) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                if (fte.getDesignTexture() < fte.maxDesignTextures) {
                    fte.setDesignTexture(fte.getDesignTexture() + 1);
                } else {
                    fte.setDesignTexture(0);
                }
                //player.sendMessage(new TranslationTextComponent("message.frame.design_texture"));
                player.displayClientMessage(new TranslatableComponent("Design Texture: " + fte.getDesignTexture()), true);
            }
            if (tileEntity instanceof BedFrameTile) {
                BedFrameTile fte = (BedFrameTile) tileEntity;
                if (fte.getDesignTexture() < 7) {
                    fte.setDesignTexture(fte.getDesignTexture() + 1);
                } else {
                    fte.setDesignTexture(0);
                }
                //player.sendMessage(new TranslationTextComponent("message.frame.design_texture"));
                player.displayClientMessage(new TranslatableComponent("Design Texture: " + fte.getDesignTexture()), true);
            }
            if (tileEntity instanceof ChestFrameTileEntity) {
                ChestFrameTileEntity fte = (ChestFrameTileEntity) tileEntity;
                if (fte.getDesignTexture() < fte.maxDesignTextures) {
                    fte.setDesignTexture(fte.getDesignTexture() + 1);
                } else {
                    fte.setDesignTexture(0);
                }
                //player.sendMessage(new TranslationTextComponent("message.frame.design_texture"));
                player.displayClientMessage(new TranslatableComponent("Design Texture: " + fte.getDesignTexture()), true);
            }
        }
    }

    public static void setGlassColor(Level dimension, BlockPos pos, Player player, InteractionHand hand) {
        if (BlockSavingHelper.isDyeItem(player.getItemInHand(hand).getItem())) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                fte.setGlassColor(dyeItemToInt(player.getItemInHand(hand).getItem()) + 1); //plus 1, because 0 is undyed glass
                //player.sendStatusMessage(new TranslationTextComponent("Glass Color: " + glassColorToString(fte.getGlassColor()-1)), true);
            }
        }
    }

    public static void setWoolColor(Level dimension, BlockPos pos, Player player, InteractionHand hand) {
        if (BlockSavingHelper.isDyeItem(player.getItemInHand(hand).getItem())) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (tileEntity instanceof BedFrameTile) {
                BedFrameTile fte = (BedFrameTile) tileEntity;
                if (dimension.getBlockState(pos).getValue(BedFrameBlock.PART) == BedPart.FOOT) {
                    fte.setBlanketColor(dyeItemToInt(player.getItemInHand(hand).getItem()));
                }
                if (dimension.getBlockState(pos).getValue(BedFrameBlock.PART) == BedPart.HEAD) {
                    fte.setPillowColor(dyeItemToInt(player.getItemInHand(hand).getItem()));
                }
                //player.sendStatusMessage(new TranslationTextComponent("Glass Color: " + glassColorToString(fte.getGlassColor()-1)), true);
            }
        }
    }

    //reminder to myself: DO NOT USE, CAUSES SERVER CRASHES, fix or remove
    private static String glassColorToString(int glassColor) {
        List<String> colors = new ArrayList<>();
        for (Item item : Tags.Items.DYES.getValues()) {
            colors.add(item.getDescription().getString());
        }
        return colors.get(glassColor);
    }

    public static Integer dyeItemToInt(Item item) {
        List<Item> colors = new ArrayList<>(BlockSavingHelper.getDyeItems());
        if (colors.contains(item)) {
            return colors.indexOf(item);
        }
        return 0;
    }

    public static void setOverlay(Level dimension, BlockPos pos, Player player, ItemStack itemStack) {
        if (itemStack.getItem().equals(Items.GRASS)) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                if (fte.getOverlay() == 1) {
                    fte.setOverlay(2);
                    player.displayClientMessage(new TranslatableComponent("Activated Large Grass Overlay"), true);
                } else {
                    fte.setOverlay(1);
                    player.displayClientMessage(new TranslatableComponent("Activated Grass Overlay"), true);
                }
            }
        }
        if (itemStack.getItem().equals(Items.SNOWBALL)) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                if (fte.getOverlay() == 3) {
                    fte.setOverlay(4);
                    player.displayClientMessage(new TranslatableComponent("Activated Small Snow Overlay"), true);
                } else {
                    fte.setOverlay(3);
                    player.displayClientMessage(new TranslatableComponent("Activated Snow Overlay"), true);
                }
            }
        }
        if (itemStack.getItem().equals(Items.VINE)) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                fte.setOverlay(5);
                player.displayClientMessage(new TranslatableComponent("Activated Vine Overlay"), true);
            }
        }
        if (itemStack.getItem().equals(Items.GUNPOWDER)) {
            BlockEntity tileEntity = dimension.getBlockEntity(pos);
            if (tileEntity instanceof FrameBlockTile) {
                FrameBlockTile fte = (FrameBlockTile) tileEntity;
                if (fte.getOverlay() > 5 && fte.getOverlay() < 10) {
                    fte.setOverlay(fte.getOverlay() + 1);
                } else fte.setOverlay(6);
                player.displayClientMessage(new TranslatableComponent("Activated special Overlay " + (fte.getOverlay() - 5)), true);
            }
        }
    }
}
//========SOLI DEO GLORIA========//