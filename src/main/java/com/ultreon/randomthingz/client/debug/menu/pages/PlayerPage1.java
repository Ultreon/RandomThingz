package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.minecraft.ChatFormatting.GRAY;
import static net.minecraft.ChatFormatting.RED;

public class PlayerPage1 extends DebugPage {
    public PlayerPage1(String modId, String name) {
        super(modId, name);
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {
        if (Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;

            ctx.left("Luck", player.getLuck());
            ctx.left("Speed", player.getSpeed());
            ctx.left("Score", player.getScore());
            ctx.left("Armor Value", player.getArmorValue());
            ctx.left("Health", player.getHealth());
            ctx.left("Absorption", player.getAbsorptionAmount());
            ctx.left("Hunger", player.getFoodData().getFoodLevel());
            ctx.left("Saturation", player.getFoodData().getSaturationLevel());
            ctx.left("Air", player.getAirSupply());
            ctx.left("Position Block", player.blockPosition());
            ctx.left("Position", player.position());
            ctx.left("Rotation (xy)", getDegrees(player.getRotationVector().x), getDegrees(player.getRotationVector().y));
            ctx.left("Sleep Timer", player.getSleepTimer());
            ctx.left("Fire Timer", player.getRemainingFireTicks());
            ctx.left("Brightness", player.getBrightness());
            ctx.left("Bee Sting Count", player.getStingerCount());

            // String to be scanned to find the pattern.
            String pattern = "[a-zA-Z0-9_]*";

            // Create a Pattern object
            Pattern r = Pattern.compile(pattern);

            // Now create matcher object.
            Matcher m = r.matcher(player.getName().getString());

            ctx.right("Legal Username", m.find());
            ctx.right("Jumping", player.jumping);
            ctx.right("Sneaking", player.isShiftKeyDown());
            ctx.right("Swimming", player.isSwimming());
            ctx.right("Sleeping", player.isSleeping());
            ctx.right("Sprinting", player.isSprinting());
            ctx.right("Silent", player.isSilent());
            ctx.right("Swing In Progress", player.swinging);
            ctx.right("User", player.isLocalPlayer());
            ctx.right("Alive", player.isAlive());
            ctx.right("Burning", player.isOnFire());
            ctx.right("Wet ", player.isInWaterOrRain());
            ctx.right("Creative", player.isCreative());
            ctx.right("Invulnerable", player.isInvulnerable());
            ctx.right("Spectator", player.isSpectator());
            ctx.right("Allow Build", player.mayBuild());

            {
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
                        BlockState blockState = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos);
                        ctx.top(GRAY + "-== Block ==-");
                        ctx.top(blockState.getBlock().getName().getString());
                        ctx.top();
                    } else {
                        // not looking at a block, or too far away from one to tell
                        ctx.top(RED +  "<No Target Block Was Found>");
                    }
                    lookingAt = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player));
                    if (lookingAt.getType() == HitResult.Type.BLOCK) {
                        BlockPos pos = lookingAt.getBlockPos();

                        // now the coordinates you want are in pos. Example of use:
                        BlockState blockState = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos);
                        FluidState fluidState = blockState.getFluidState();
                        if (!fluidState.isEmpty()) {
                            ctx.top(GRAY + "-== Fluid ==-");
                            ctx.top(blockState.getBlock().getName().getString());
                            ctx.top();
                        } else {
                            // not looking at a fluid, or too far away from one to tell
                            ctx.top(RED + "<No Target Fluid Was Found>");
                        }
                    } else {
                        // not looking at a fluid, or too far away from one to tell
                        ctx.top(RED + "<No Target Fluid Was Found>");
                    }
                } else {
                    ctx.top(RED + "<World / Dimension Not Found>");
                }
            }

            {
                float xRot = player.getXRot();
                float yRot = player.getYRot();

                Vec3 clipStart = player.getEyePosition(1f);

                float f2 = Mth.cos(-yRot * ((float) Math.PI / 180f) - (float) Math.PI);
                float f3 = Mth.sin(-yRot * ((float) Math.PI / 180f) - (float) Math.PI);
                float f4 = -Mth.cos(-xRot * ((float) Math.PI / 180f));
                float deltaY = Mth.sin(-xRot * ((float) Math.PI / 180f));

                float deltaX = f3 * f4;
                float deltaZ = f2 * f4;

                double distance = 16;

                Vec3 clipEnd = clipStart.add((double) deltaX * distance, (double) deltaY * distance, (double) deltaZ * distance);

                if (Minecraft.getInstance().level != null) {
                    HitResult hit = Minecraft.getInstance().level.clip(new ClipContext(clipStart, clipEnd, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
                    if (hit.getType() != HitResult.Type.MISS) {
                        clipEnd = hit.getLocation();
                    }

                    HitResult tempEntityHit = ProjectileUtil.getEntityHitResult(Minecraft.getInstance().level, player, clipStart, clipEnd, player.getBoundingBox().inflate(16.0D), entity -> !entity.equals(player));
                    if (tempEntityHit != null) {
                        hit = tempEntityHit;
                    }
                    if (hit instanceof EntityHitResult entityHit) {
                        ctx.top(GRAY + "-== Entity ==-");
                        ctx.top(I18n.get(entityHit.getEntity().getType().getDescriptionId()));
                        ctx.top();
                    } else {
                        // not looking at a block, or too far away from one to tell
                        ctx.top("<No Target Entity Found>");
                    }
                }
            }
        } else {
            ctx.top(RED + "<Local Player not found>");
        }
    }
}
