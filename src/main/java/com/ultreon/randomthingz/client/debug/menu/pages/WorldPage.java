package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class WorldPage extends DebugPage {
    public WorldPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {
        if (Minecraft.getInstance().level != null) {
            ClientLevel dimension = Minecraft.getInstance().level;

            ctx.left("Lightning Flash Time", dimension.getSkyFlashTime());
            ctx.left("Chunk Provider", dimension.gatherChunkSourceStats());
            ctx.left("Loaded Entities", dimension.getEntityCount());
            ctx.left("Difficulty", dimension.getDifficulty().getDisplayName().getString());
            ctx.left("Sea Level", dimension.getSeaLevel());
            ctx.left("Moon Phase", getMoonPhase(dimension.getMoonPhase()));
            ctx.left("Spawn Angle", getAngle(dimension.getLevelData().getSpawnAngle()));
            ctx.left("Dimension", dimension.dimension().location());
            ctx.left("Day Time", dimension.getDayTime());
            ctx.left("Game Time", dimension.getGameTime());
            ctx.left("Cloud Color", getColor(dimension.getCloudColor(mc.getFrameTime())));
            if (Minecraft.getInstance().player != null) {
                LocalPlayer player = Minecraft.getInstance().player;
                ctx.left("Sky Color", getColor(dimension.getSkyColor(player.position(), mc.getFrameTime())));
            }
            ctx.left("Star Brightness", getPercentage(dimension.getStarBrightness(mc.getFrameTime())));
            ctx.left("Sun Brightness", getPercentage(dimension.getSkyDarken(mc.getFrameTime())));

            ctx.right("Daytime Now", dimension.isDay());
            ctx.right("Nighttime Now", dimension.isNight());
            ctx.right("Raining", dimension.isRaining());
            ctx.right("Thundering", dimension.isThundering());
            ctx.right("No Saving", dimension.noSave());
            ctx.right("Debug World", dimension.isDebug());
        }
    }
}
