package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.ChatFormatting.GRAY;
import static net.minecraft.ChatFormatting.RED;

public class BlockPage extends DebugPage {
    public BlockPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {
        if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            float f = player.getXRot();
            float f1 = player.getYRot();

            Vec3 vec3d = player.getEyePosition(1f);

            float f2 = Mth.cos(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
            float f3 = Mth.sin(-f1 * ((float) Math.PI / 180f) - (float) Math.PI);
            float f4 = -Mth.cos(-f * ((float) Math.PI / 180f));
            float f5 = Mth.sin(-f * ((float) Math.PI / 180f));

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
                    BlockState state = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos);
                    Block block = state.getBlock();
                    ctx.left(GRAY + "-== BLOCK ==-");
                    ctx.left("type", block.getRegistryName());
                    ctx.left("translatedName", block.getName().getString());
                    ctx.left("blockHardness", state.getDestroySpeed(player.getCommandSenderWorld(), pos));
                    ctx.left("lightValue", state.getLightEmission(Minecraft.getInstance().level, Minecraft.getInstance().player.blockPosition()));
                    ctx.left("opacity", state.getLightBlock(player.getCommandSenderWorld(), pos));
                    ctx.left("offset", state.getOffset(player.getCommandSenderWorld(), pos));
                    ctx.left("playerRelativeHardness", state.getDestroyProgress(player, player.getCommandSenderWorld(), pos));
                    ctx.left("requiresTool", state.requiresCorrectToolForDrops());
                    ctx.left("renderType", state.getRenderShape());
                    ctx.left("slipperiness", state.getFriction(player.getCommandSenderWorld(), pos, player));
                    ctx.left("jumpFactor", block.getJumpFactor());
                    ctx.left("enchantPowerBonus", state.getEnchantPowerBonus(player.getCommandSenderWorld(), pos));
                    ctx.left("target", block.getLootTable());
                    ctx.left("materialColor", block.defaultMaterialColor().id, getColor(block.defaultMaterialColor().col));
                    ctx.left("offsetType", block.getOffsetType());
                    ctx.left("registryName", block.getRegistryName());
                    ctx.left("defaultSlipperiness", block.getFriction());
                    ctx.left("speedFactor", getMultiplier(block.getSpeedFactor()));
                } else {
                    // not looking at a block, or too far away from one to tell
                    ctx.top(RED + "<No Block Was Found>");
                }

                lookingAt = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player));
                if (lookingAt.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = lookingAt.getBlockPos();

                    // now the coordinates you want are in pos. Example of use:
                    FluidState state = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos).getFluidState();
                    if (!state.isEmpty()) {
                        ctx.right(GRAY + "-== Fluid ==-");
                        ctx.right("empty", state.isEmpty());
                        ctx.right("height", state.getOwnHeight());
                        ctx.right("level", state.getAmount());
                        ctx.right("actualHeight", state.getType().getHeight(state, player.getCommandSenderWorld(), pos));
                        try {
                            ctx.right("filledBucket", state.getType().getBucket());
                        } catch (Throwable ignored) {

                        }
                        ctx.right("tickRate", state.getType().getTickDelay(player.getCommandSenderWorld()));
                    } else {
                        // not looking at a fluid, or too far away from one to tell
                        ctx.top(RED + "<No Fluid Was Found>");
                    }
                }
            } else {
                ctx.top(RED + "<World / Dimension not loaded>");
            }
        }
    }
}
