package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.datafixers.util.Pair;
import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.math.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class BlockPage extends DebugPage {
    public BlockPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();

        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return list;
        }
        float f = player.xRot;
        float f1 = player.yRot;

        Vec3 vec3d = player.getEyePosition(1.0f);

        float f2 = Mth.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-f * ((float) Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float) Math.PI / 180F));

        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 16;

        Vec3 vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

        BlockHitResult lookingAt;
        if (Minecraft.getInstance().level != null) {
            lookingAt = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
            if (lookingAt.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = lookingAt.getBlockPos();

                // now the coordinates you want are in pos. Example of use:
                BlockState blockState = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos);
                Block block = blockState.getBlock();
                list.add(new DebugEntry("translatedName", () -> block.getName().getString()));
                list.add(new DebugEntry("harvestLevel", blockState::getHarvestLevel));
                list.add(new DebugEntry("harvestTool", () -> blockState.getHarvestTool() == null ? null : blockState.getHarvestTool().getName()));
                list.add(new DebugEntry("blockHardness", () -> blockState.getDestroySpeed(player.getCommandSenderWorld(), pos)));
                list.add(new DebugEntry("lightValue", blockState::getLightEmission));
                list.add(new DebugEntry("opacity", () -> blockState.getLightBlock(player.getCommandSenderWorld(), pos)));
                list.add(new DebugEntry("offset", () -> blockState.getOffset(player.getCommandSenderWorld(), pos)));
                list.add(new DebugEntry("playerRelativeHardness", () -> blockState.getDestroyProgress(player, player.getCommandSenderWorld(), pos)));
                list.add(new DebugEntry("requiresTool", blockState::requiresCorrectToolForDrops));
                list.add(new DebugEntry("renderType", blockState::getRenderShape));
                list.add(new DebugEntry("slipperiness", () -> blockState.getSlipperiness(player.getCommandSenderWorld(), pos, player)));
                list.add(new DebugEntry("jumpFactor", block::getJumpFactor));
                list.add(new DebugEntry("enchantPowerBonus", () -> blockState.getEnchantPowerBonus(player.getCommandSenderWorld(), pos)));
                list.add(new DebugEntry("lootTable", block::getLootTable));
                list.add(new DebugEntry("materialColor", () -> new Pair<>(block.defaultMaterialColor().id, getColor(block.defaultMaterialColor().col))));
                list.add(new DebugEntry("offsetType", block::getOffsetType));
                list.add(new DebugEntry("registryName", block::getRegistryName));
                list.add(new DebugEntry("defaultSlipperiness", block::getFriction));
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
