package com.qtech.forgemod.modules.debugMenu.pages;

import com.mojang.datafixers.util.Pair;
import com.qtech.forgemod.modules.debugMenu.DebugEntry;
import com.qtech.forgemod.modules.debugMenu.DebugPage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class BlockPage extends DebugPage {
    public BlockPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();

        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return list;
        }
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;

        Vector3d vec3d = player.getEyePosition(1.0F);

        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));

        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 16;

        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

        BlockRayTraceResult lookingAt;
        if (Minecraft.getInstance().dimension != null) {
            lookingAt = Minecraft.getInstance().dimension.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
            if (lookingAt.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos pos = lookingAt.getPos();

                // now the coordinates you want are in pos. Example of use:
                BlockState blockState = Minecraft.getInstance().player.getEntityDimension().getBlockState(pos);
                Block block = blockState.getBlock();
                list.add(new DebugEntry("translatedName", () -> block.getTranslatedName().getString()));
                list.add(new DebugEntry("harvestLevel", blockState::getHarvestLevel));
                list.add(new DebugEntry("harvestTool", () -> blockState.getHarvestTool() == null ? null : blockState.getHarvestTool().getName()));
                list.add(new DebugEntry("blockHardness", () -> blockState.getBlockHardness(player.getEntityDimension(), pos)));
                list.add(new DebugEntry("lightValue", blockState::getLightValue));
                list.add(new DebugEntry("opacity", () -> blockState.getOpacity(player.getEntityDimension(), pos)));
                list.add(new DebugEntry("offset", () -> blockState.getOffset(player.getEntityDimension(), pos)));
                list.add(new DebugEntry("playerRelativeHardness", () -> blockState.getPlayerRelativeBlockHardness(player, player.getEntityDimension(), pos)));
                list.add(new DebugEntry("requiresTool", blockState::getRequiresTool));
                list.add(new DebugEntry("renderType", blockState::getRenderType));
                list.add(new DebugEntry("slipperiness", () -> blockState.getSlipperiness(player.getEntityDimension(), pos, player)));
                list.add(new DebugEntry("jumpFactor", block::getJumpFactor));
                list.add(new DebugEntry("enchantPowerBonus", () -> blockState.getEnchantPowerBonus(player.getEntityDimension(), pos)));
                list.add(new DebugEntry("lootTable", block::getLootTable));
                list.add(new DebugEntry("materialColor", () -> new Pair<>(block.getMaterialColor().colorIndex, getColor(block.getMaterialColor().colorValue))));
                list.add(new DebugEntry("offsetType", block::getOffsetType));
                list.add(new DebugEntry("registryName", block::getRegistryName));
                list.add(new DebugEntry("defaultSlipperiness", block::getSlipperiness));
                list.add(new DebugEntry("speedFactor", () -> getMultiplier(block.getSpeedFactor())));
            }

//            lookingAt = Minecraft.getInstance().dimension.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
//            if (lookingAt.getType() == RayTraceResult.Type.BLOCK) {
//                BlockPos pos = lookingAt.getPos();
//
//                // now the coordinates you want are in pos. Example of use:
//                BlockState blockState = Minecraft.getInstance().player.getEntityDimension().getBlockState(pos);
//                FluidState fluidState = blockState.getFluidState();
//                if (!fluidState.isEmpty()) {
//                    drawRightString(matrixStack, TextFormatting.GRAY + "-== Fluid ==-", 12f, 12f, 0xffffff);
//                    drawRightTopString(matrixStack, "empty", j++, fluidState.isEmpty());
//                    drawRightTopString(matrixStack, "height", j++, fluidState.getHeight());
//                    drawRightTopString(matrixStack, "level", j++, fluidState.getLevel());
//                    drawRightTopString(matrixStack, "actualHeight", j++, fluidState.getFluid().getActualHeight(fluidState, player.getEntityDimension(), pos));
//                    try {
//                        drawRightTopString(matrixStack, "filledBucket", j++, fluidState.getFluid().getFilledBucket());
//                    } catch (Throwable ignored) {
//
//                    }
//                    drawRightTopString(matrixStack, "tickRate", j++, fluidState.getFluid().getTickRate(player.getEntityDimension()));
//                } else {
//                    // not looking at a fluid, or too far away from one to tell
////                                    Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
//                }
//            } else {
//                // not looking at a fluid, or too far away from one to tell
//                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
//            }
        }
        return super.getLinesLeft();
    }

    @Override
    public boolean hasRequiredComponents() {
        return Minecraft.getInstance().player != null;
    }
}
