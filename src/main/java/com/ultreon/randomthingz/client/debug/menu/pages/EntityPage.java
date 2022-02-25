package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.ChatFormatting.GRAY;
import static net.minecraft.ChatFormatting.RED;

public class EntityPage extends DebugPage {
    private final Minecraft mc = Minecraft.getInstance();

    public EntityPage(String modId, String name) {
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

            if (Minecraft.getInstance().level != null) {
                HitResult hitResult = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
                if (hitResult.getType() != HitResult.Type.MISS) {
                    vec3d1 = hitResult.getLocation();
                }

                HitResult rayTraceResult1 = ProjectileUtil.getEntityHitResult(Minecraft.getInstance().level, player, vec3d, vec3d1, player.getBoundingBox().inflate(16.0D), entity -> !entity.equals(player));
                if (rayTraceResult1 != null) {
                    hitResult = rayTraceResult1;
                }
                if (hitResult.getType() == HitResult.Type.ENTITY) {
                    @SuppressWarnings("ConstantConditions") EntityHitResult entityRayTraceResult = (EntityHitResult) hitResult;

                    Entity entity = entityRayTraceResult.getEntity();
                    EntityType<? extends Entity> type = entity.getType();

                    ctx.left(GRAY + "-== Entity Type ==-");
                    ctx.left("Height", type.getHeight());
                    ctx.left("Name", type.getDescription().getString());
                    ctx.left("Size", getSize(type.getDimensions().width, type.getDimensions().height));
                    ctx.left("Internal Name", type.getRegistryName());
                    ctx.left();
                    ctx.left(GRAY + "-== Entity ==-");
                    ctx.left("Air", entity.getAirSupply());
                    ctx.left("Max Air", entity.getMaxAirSupply());
                    ctx.left("Brightness", entity.getBrightness());
                    ctx.left("Eye Height", entity.getEyeHeight());
                    ctx.left("Look Angle", entity.getLookAngle());
                    ctx.left("Riding Entity", entity.getVehicle());
                    ctx.left("Riding Offset", entity.getMyRidingOffset());
                    ctx.left("Entity UUID", entity.getUUID());
                    ctx.left("Entity ID", entity.getId());

                    if (entity instanceof LivingEntity living) {
                        ctx.right(GRAY + "-== Living Entity ==-");
                        ctx.right("Health", living.getHealth());
                        ctx.right("Max. Health", living.getMaxHealth());
                        ctx.right("Main Hand", living.getItemInHand(InteractionHand.MAIN_HAND));
                        ctx.right("Off Hand", living.getItemInHand(InteractionHand.OFF_HAND));
                        ctx.right("Arrow Count", living.getArrowCount());
                        ctx.right("Absorption Amount", living.getAbsorptionAmount());
                        ctx.right("Speed", living.getSpeed());
                        ctx.right("Last Used Hand", living.getUsedItemHand());
                        ctx.right("Attacking Entity", living.getKillCredit());
                        ctx.right("Item Usage Ticks Remaining", living.getUseItemRemainingTicks());
                        ctx.right("Last Attacked Mob", living.getLastHurtMob());
                        ctx.right("Rope Position", living.getRopeHoldPosition(mc.getFrameTime()));
                        ctx.right("Pose", living.getPose());
                        ctx.right("Scale", living.getScale());
                        ctx.right("Armor Value", living.getArmorValue());
                    } else if (entity instanceof ItemEntity itemEntity) {
                        ctx.right(GRAY + "-== Item Entity ==-");
                        ctx.right("Age", itemEntity.getAge());
                        ctx.right("Item", itemEntity.getItem());
                        ctx.right("Owner", itemEntity.getOwner());
                        ctx.right("Thrower", itemEntity.getThrower());
                        ctx.right("Pose", itemEntity.getPose());
                    }
                } else {
                    // not looking at a block, or too far away from one to tell
                    ctx.top(RED + "<No Entity Was Found>");
                }
            } else {
                ctx.top(RED + "<World / Dimension Not Found>");
            }
        } else {
            ctx.top(RED + "<Local Player Not Found>");
        }
    }
}
