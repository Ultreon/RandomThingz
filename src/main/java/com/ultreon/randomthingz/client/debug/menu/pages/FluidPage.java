package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class FluidPage extends DebugPage {
    public FluidPage(String modId, String name) {
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
            lookingAt = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player));
            if (lookingAt.getType() == HitResult.Type.BLOCK) {
                BlockPos pos = lookingAt.getBlockPos();

                // now the coordinates you want are in pos. Example of use:
                BlockState blockState = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos);
                FluidState fluidState = blockState.getFluidState();
                if (!fluidState.isEmpty()) {
                    list.add(new DebugEntry("empty", fluidState::isEmpty));
                    list.add(new DebugEntry("height", fluidState::getOwnHeight));
                    list.add(new DebugEntry("level", fluidState::getAmount));
                    list.add(new DebugEntry("actualHeight", () -> fluidState.getType().getHeight(fluidState, player.getCommandSenderWorld(), pos)));
                    list.add(new DebugEntry("filledBucket", () -> fluidState.getType().getBucket()));
                    list.add(new DebugEntry("tickRate", () -> fluidState.getType().getTickDelay(player.getCommandSenderWorld())));
                } else {
                    // not looking at a fluid, or too far away from one to tell
//                                    Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
                }
            }
        }
        return super.getLinesLeft();
    }

    @Override
    public boolean hasRequiredComponents() {
        return Minecraft.getInstance().player != null;
    }
}
