package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class FluidPage extends DebugPage {
    public FluidPage(String modId, String name) {
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
            lookingAt = Minecraft.getInstance().dimension.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
            if (lookingAt.getType() == RayTraceResult.Type.BLOCK) {
                BlockPos pos = lookingAt.getPos();

                // now the coordinates you want are in pos. Example of use:
                BlockState blockState = Minecraft.getInstance().player.getEntityDimension().getBlockState(pos);
                FluidState fluidState = blockState.getFluidState();
                if (!fluidState.isEmpty()) {
                    list.add(new DebugEntry("empty", fluidState::isEmpty));
                    list.add(new DebugEntry("height", fluidState::getHeight));
                    list.add(new DebugEntry("level", fluidState::getLevel));
                    list.add(new DebugEntry("actualHeight", () -> fluidState.getFluid().getActualHeight(fluidState, player.getEntityDimension(), pos)));
                    list.add(new DebugEntry("filledBucket", () -> fluidState.getFluid().getFilledBucket()));
                    list.add(new DebugEntry("tickRate", () -> fluidState.getFluid().getTickRate(player.getEntityDimension())));
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
