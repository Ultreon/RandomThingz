package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.ChatFormatting.RED;

public class FluidPage extends DebugPage {
    private static final Minecraft MC = Minecraft.getInstance();

    public FluidPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {
        if (MC.player != null) {
            Player player = MC.player;
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
            if (MC.level != null) {
                lookingAt = MC.level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player));
                if (lookingAt.getType() == HitResult.Type.BLOCK) {
                    BlockPos pos = lookingAt.getBlockPos();

                    // now the coordinates you want are in pos. Example of use:
                    FluidState state = MC.player.getCommandSenderWorld().getBlockState(pos).getFluidState();
                    if (!state.isEmpty()) {
                        ctx.left("height", state.getOwnHeight());
                        ctx.left("amount", state.getAmount());
                        ctx.left("actualHeight", state.getType().getHeight(state, player.getCommandSenderWorld(), pos));
                        try {
                            ctx.left("filledBucket", state.getType().getBucket());
                        } catch (Throwable ignored) {

                        }
                        ctx.left("tickRate", state.getType().getTickDelay(player.getCommandSenderWorld()));

                        ctx.right("empty", state.isEmpty());
                        ctx.right("source", state.isSource());
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
