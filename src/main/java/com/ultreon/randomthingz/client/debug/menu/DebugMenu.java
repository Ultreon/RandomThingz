package com.ultreon.randomthingz.client.debug.menu;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.ultreon.modlib.silentlib.client.key.InputUtils;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.hud.HudItems;
import com.ultreon.randomthingz.client.hud.IHasHud;
import com.ultreon.randomthingz.client.input.KeyBindingList;
import com.ultreon.randomthingz.common.*;
import com.ultreon.randomthingz.common.enums.MoonPhase;
import com.ultreon.randomthingz.common.interfaces.Formattable;
import com.ultreon.randomthingz.common.interfaces.Sliceable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.*;
import net.minecraft.world.scores.Team;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Client listener
 *
 * @author (partial) CoFH - https://github.com/CoFH
 */
@SuppressWarnings("unused")
public class DebugMenu {
    public static PAGE DEBUG_PAGE = PAGE.NONE;
    public static final List<DebugPage> PAGES = new ArrayList<>();

    public enum PAGE {
        NONE,
        PLAYER_1,
        PLAYER_2,
        ITEM,
        BLOCK,
        ENTITY,
        WORLD,
        WORLD_INFO,
        MINECRAFT,
        WINDOW,
        COMPUTER,
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <T extends DebugPage> T registerPage(T page) {
        PAGES.add(page);
        return page;
    }

    public static void onKeyReleased(InputEvent.KeyInputEvent event) {
        if (event.getAction() == GLFW.GLFW_RELEASE) {
            if (event.getKey() == KeyBindingList.DEBUG_SCREEN.getKey().getValue()) {
                if (InputUtils.isShiftDown()) {
                    int next = DEBUG_PAGE.ordinal() - 1;

                    PAGE[] values = PAGE.values();
//                    if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
//                        if (next < 1) {
//                            next = values.length - 1;
//                        }
//                    } else {
                    if (next < 0) {
                        next = values.length - 1;
                    }
//                    }

                    DEBUG_PAGE = values[next];
                    return;
                }

                int next = DEBUG_PAGE.ordinal() + 1;

                PAGE[] values = PAGE.values();
                if (next >= values.length) {
//                    if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
//                        next = 1;
//                    } else {
                    next = 0;
//                    }
                }

                DEBUG_PAGE = values[next];
            }
        }
    }

    @SuppressWarnings({"UnusedAssignment", "StatementWithEmptyBody"})
    public static void renderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.LAYER) return;

        if (Minecraft.getInstance().options.renderDebug) {
            return;
        }

        PoseStack matrixStack = event.getMatrixStack();
        Minecraft mc = Minecraft.getInstance();
        Window mainWindow = mc.getWindow();
        Monitor monitor = mainWindow.findBestMonitor();

        int width = mainWindow.getGuiScaledWidth();
        int height = mainWindow.getGuiScaledHeight();

        int dw, dh;
        if (monitor != null) {
            dw = monitor.getCurrentMode().getWidth();
            dh = monitor.getCurrentMode().getHeight();
        } else {
            dw = 0;
            dh = 0;
        }

        for (IHasHud hud : HudItems.ITEMS) {
            hud.render(matrixStack, mc);
        }

        if (DEBUG_PAGE != PAGE.NONE) {
            mc.font.drawShadow(matrixStack, "debugPage: " + DEBUG_PAGE.name(), 12f, height - 16f, 0xffaa00);
        }

        switch (DEBUG_PAGE) {
            case WINDOW: {
                int i = 0;
                drawLeftTopString(matrixStack, "guiScaleFactor", i++, getMultiplier(mainWindow.getGuiScale()));
                drawLeftTopString(matrixStack, "windowSizeScaled", i++, getSize(mainWindow.getGuiScaledWidth(), mainWindow.getGuiScaledHeight()));
                drawLeftTopString(matrixStack, "windowSize", i++, getSize(mainWindow.getScreenWidth(), mainWindow.getScreenHeight()));
                drawLeftTopString(matrixStack, "windowHandle", i++, mainWindow.getWindow());
                drawLeftTopString(matrixStack, "framebufferSize", i++, getSize(mainWindow.getWidth(), mainWindow.getHeight()));
                drawLeftTopString(matrixStack, "refreshTate", i++, getFormatted(ChatFormatting.GOLD.toString() + mainWindow.getRefreshRate() + ChatFormatting.GRAY + " fps"));
                drawLeftTopString(matrixStack, "framerateLimit", i++, getFormatted(ChatFormatting.GOLD.toString() + mainWindow.getFramerateLimit() + ChatFormatting.GRAY + "fps"));

                int j = 0;
                drawRightTopString(matrixStack, "fullscreenMode", j++, mainWindow.isFullscreen());
                break;
            }
            case BLOCK: {
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

                    int i = 1;
                    int j = 1;

                    BlockHitResult lookingAt = null;
                    if (Minecraft.getInstance().level != null) {
                        lookingAt = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
                        if (lookingAt.getType() == HitResult.Type.BLOCK) {
                            BlockPos pos = lookingAt.getBlockPos();

                            // now the coordinates you want are in pos. Example of use:
                            BlockState blockState = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos);
                            Block block = blockState.getBlock();
                            mc.font.drawShadow(matrixStack, ChatFormatting.GRAY + "-== BLOCK ==-", 12f, 12f, 0xffffff);
                            drawLeftTopString(matrixStack, "translatedName", i++, block.getName().getString());
                            drawLeftTopString(matrixStack, "blockHardness", i++, blockState.getDestroySpeed(player.getCommandSenderWorld(), pos));
                            drawLeftTopString(matrixStack, "lightValue", i++, blockState.getLightEmission(Minecraft.getInstance().level, Minecraft.getInstance().player.blockPosition()));
                            drawLeftTopString(matrixStack, "opacity", i++, blockState.getLightBlock(player.getCommandSenderWorld(), pos));
                            drawLeftTopString(matrixStack, "offset", i++, blockState.getOffset(player.getCommandSenderWorld(), pos));
                            drawLeftTopString(matrixStack, "playerRelativeHardness", i++, blockState.getDestroyProgress(player, player.getCommandSenderWorld(), pos));
                            drawLeftTopString(matrixStack, "requiresTool", i++, blockState.requiresCorrectToolForDrops());
                            drawLeftTopString(matrixStack, "renderType", i++, blockState.getRenderShape());
                            drawLeftTopString(matrixStack, "slipperiness", i++, blockState.getFriction(player.getCommandSenderWorld(), pos, player));
                            drawLeftTopString(matrixStack, "jumpFactor", i++, block.getJumpFactor());
                            drawLeftTopString(matrixStack, "enchantPowerBonus", i++, blockState.getEnchantPowerBonus(player.getCommandSenderWorld(), pos));
                            drawLeftTopString(matrixStack, "target", i++, block.getLootTable());
                            drawLeftTopString(matrixStack, "materialColor", i++, block.defaultMaterialColor().id, getColor(block.defaultMaterialColor().col));
                            drawLeftTopString(matrixStack, "offsetType", i++, block.getOffsetType());
                            drawLeftTopString(matrixStack, "registryName", i++, block.getRegistryName());
                            drawLeftTopString(matrixStack, "defaultSlipperiness", i++, block.getFriction());
                            drawLeftTopString(matrixStack, "speedFactor", i++, getMultiplier(block.getSpeedFactor()));
                        } else {
                            // not looking at a block, or too far away from one to tell
                            Screen.drawCenteredString(matrixStack, mc.font, "<None>", width / 2, height / 2 - 16, 0xff0000);
                        }

                        lookingAt = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player));
                        if (lookingAt.getType() == HitResult.Type.BLOCK) {
                            BlockPos pos = lookingAt.getBlockPos();

                            // now the coordinates you want are in pos. Example of use:
                            BlockState blockState = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos);
                            FluidState fluidState = blockState.getFluidState();
                            if (!fluidState.isEmpty()) {
                                drawRightString(matrixStack, ChatFormatting.GRAY + "-== Fluid ==-", 12f, 12f, 0xffffff);
                                drawRightTopString(matrixStack, "empty", j++, fluidState.isEmpty());
                                drawRightTopString(matrixStack, "height", j++, fluidState.getOwnHeight());
                                drawRightTopString(matrixStack, "level", j++, fluidState.getAmount());
                                drawRightTopString(matrixStack, "actualHeight", j++, fluidState.getType().getHeight(fluidState, player.getCommandSenderWorld(), pos));
                                try {
                                    drawRightTopString(matrixStack, "filledBucket", j++, fluidState.getType().getBucket());
                                } catch (Throwable ignored) {

                                }
                                drawRightTopString(matrixStack, "tickRate", j++, fluidState.getType().getTickDelay(player.getCommandSenderWorld()));
                            } else {
                                // not looking at a fluid, or too far away from one to tell
//                                    Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
                            }
                        } else {
                            // not looking at a fluid, or too far away from one to tell
                            Screen.drawCenteredString(matrixStack, mc.font, "<None>", width / 2, height / 2 - 32, 0xff0000);
                        }
                    } else {
                        Screen.drawCenteredString(matrixStack, mc.font, "<Invalid>", width / 2, height / 2 - 32, 0xbf0000);
                    }
                }
                break;
            }
            case COMPUTER: {
                int i = 0;
                if (monitor != null) {
                    drawLeftTopString(matrixStack, "screenSize", i++, dw, dh);
                }
                try {
                    drawLeftTopString(matrixStack, "osVersion", i++, System.getProperty("os.version"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "osName", i++, System.getProperty("os.name"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "osArch", i++, System.getProperty("os.arch"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVersion", i++, System.getProperty("java.version"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVendor", i++, System.getProperty("java.vendor"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVmVersion", i++, System.getProperty("java.vm.version"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVmVendor", i++, System.getProperty("java.vm.vendor"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVmVendor", i++, System.getProperty("java.vm.name"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaClassVersion", i++, System.getProperty("java.class.version"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaCompiler", i++, System.getProperty("java.compiler"));
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }

                i = 0;
                drawRightTopString(matrixStack, "isJava64bit", i++, (mc.is64Bit() ? "yes" : "no"));
                break;
            }
            case ITEM: {
                if (Minecraft.getInstance().player != null) {
                    Player player = Minecraft.getInstance().player;
                    ItemStack stack = player.getMainHandItem();
                    Item item = stack.getItem();
                    FoodProperties food = item.getFoodProperties();
                    CreativeModeTab group = item.getItemCategory();

                    //noinspection ConstantConditions
                    if (stack == null) {
                        drawTopString(matrixStack, "NO ITEM", 0);
                        break;
                    }

                    int i = 0;

                    drawLeftTopString(matrixStack, "registryName", i++, stack.getItem().getRegistryName());
                    if (food != null) {
                        drawLeftTopString(matrixStack, "foodHealing", i++, food.getNutrition());
                        drawLeftTopString(matrixStack, "foodSaturation", i++, food.getSaturationModifier());
                    }
                    if (group != null) {
                        drawLeftTopString(matrixStack, "groupName", i++, group.getDisplayName().getString());
                    }
                    drawLeftTopString(matrixStack, "rarity", i++, item.getRarity(stack));
                    drawLeftTopString(matrixStack, "enchantability", i++, item.getItemEnchantability(stack));
                    drawLeftTopString(matrixStack, "stackLimit", i++, item.getItemStackLimit(stack));
                    drawLeftTopString(matrixStack, "maxDamage", i++, item.getMaxDamage(stack));
                    drawLeftTopString(matrixStack, "damage", i++, item.getDamage(stack));
                    drawLeftTopString(matrixStack, "count", i++, stack.getCount());
                    drawLeftTopString(matrixStack, "repairCost", i++, stack.getBaseRepairCost());
                    drawLeftTopString(matrixStack, "useDuration", i++, stack.getUseDuration());
                    drawLeftTopString(matrixStack, "xpRepairRation", i++, stack.getXpRepairRatio());

                    int j = 0;
                    drawRightTopString(matrixStack, "complex", j++, item.isComplex());
                    drawRightTopString(matrixStack, "immuneToFire", j++, item.isFireResistant());
                    drawRightTopString(matrixStack, "enchantable", j++, item.isEnchantable(stack));
                    drawRightTopString(matrixStack, "empty", j++, stack.isEmpty());
                    drawRightTopString(matrixStack, "isPiglinCurrency", j++, stack.isPiglinCurrency());
                    drawRightTopString(matrixStack, "repairable", j++, stack.isRepairable());
                    drawRightTopString(matrixStack, "stackable", j++, stack.isStackable());
                    drawRightTopString(matrixStack, "sliceable", j++, (item instanceof Sliceable));
                    drawRightTopString(matrixStack, "damageable", j++, item.canBeDepleted());
                    drawRightTopString(matrixStack, "damaged", j++, item.isDamaged(stack));
                }
                break;
            }
            case ENTITY: {
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

                    int i = 1;
                    int j = 1;

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

                            mc.font.drawShadow(matrixStack, ChatFormatting.GRAY + "-== Entity ==-", 12f, 12f, 0xffffff);
                            drawLeftTopString(matrixStack, "translatedName", i++, I18n.get(type.getDescriptionId()));
                            drawLeftTopString(matrixStack, "height", i++, type.getHeight());
                            drawLeftTopString(matrixStack, "target", i++, type.getDefaultLootTable());
                            drawLeftTopString(matrixStack, "name", i++, type.getDescription().getString());
                            drawLeftTopString(matrixStack, "registryName", i++, type.getRegistryName());
                            drawLeftTopString(matrixStack, "size", i++, getSize(type.getDimensions().width, type.getDimensions().height));
                            drawLeftTopString(matrixStack, "air", i++, entity.getAirSupply());
                            drawLeftTopString(matrixStack, "maxAir", i++, entity.getMaxAirSupply());
                            drawLeftTopString(matrixStack, "brightness", i++, entity.getBrightness());
                            drawLeftTopString(matrixStack, "entityId", i++, entity.getId());
                            drawLeftTopString(matrixStack, "eyeHeight", i++, entity.getEyeHeight());
                            drawLeftTopString(matrixStack, "lookVec", i++, entity.getLookAngle());
                            drawLeftTopString(matrixStack, "ridingEntity", i++, entity.getVehicle());
                            drawLeftTopString(matrixStack, "uniqueID", i++, entity.getUUID());
                            drawLeftTopString(matrixStack, "yOffset", i++, entity.getMyRidingOffset());

                            if (entity instanceof LivingEntity living) {

                                drawRightString(matrixStack, ChatFormatting.GRAY + "-== Living Entity ==-", 12f, 12f, 0xffffff);
                                drawRightTopString(matrixStack, "health", j++, living.getHealth());
                                drawRightTopString(matrixStack, "maxHealth", j++, living.getMaxHealth());
                                drawRightTopString(matrixStack, "absorptionAmount", j++, living.getAbsorptionAmount());
                                drawRightTopString(matrixStack, "movementSpeed", j++, living.canSpawnSoulSpeedParticle());
                                drawRightTopString(matrixStack, "aiMoveSpeed", j++, living.getSpeed());
                                drawRightTopString(matrixStack, "activeHand", j++, living.getUsedItemHand());
                                drawRightTopString(matrixStack, "attackingEntity", j++, living.getKillCredit());
                                drawRightTopString(matrixStack, "itemInUseCount", j++, living.getUseItemRemainingTicks());
                                drawRightTopString(matrixStack, "lastAttackedEntity", j++, living.getLastHurtMob());
                                drawRightTopString(matrixStack, "leashPosition", j++, living.getRopeHoldPosition(mc.getFrameTime()));
                                drawRightTopString(matrixStack, "pose", j++, living.getPose());
                                drawRightTopString(matrixStack, "scale", j++, living.getScale());
                                drawRightTopString(matrixStack, "revengeTarget", j++, living.getLastHurtByMob());
                                drawRightTopString(matrixStack, "totalArmorValue", j++, living.getArmorValue());
                            } else if (entity instanceof ItemEntity itemEntity) {

                                drawRightString(matrixStack, ChatFormatting.GRAY + "-== Item Entity ==-", 12f, 12f, 0xffffff);
                                drawRightTopString(matrixStack, "age", j++, itemEntity.getAge());
                                drawRightTopString(matrixStack, "item", j++, itemEntity.getItem());
                                drawRightTopString(matrixStack, "leashPosition", j++, itemEntity.getRopeHoldPosition(mc.getFrameTime()));
                                drawRightTopString(matrixStack, "pose", j++, itemEntity.getPose());
                            }
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, I18n.format(entityRayTraceResult.getEntity().getType().getTranslationId()), width / 2, height / 2 - 48, 0x00bf00);
                        } else {
                            // not looking at a block, or too far away from one to tell
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 48, 0xff0000);
                        }
                    }
                }
                break;
            }
            case MINECRAFT: {
                int i = 0;
                drawLeftTopString(matrixStack, "version", i++, mc.getLaunchedVersion());
                drawLeftTopString(matrixStack, "versionType", i++, mc.getVersionType());
                drawLeftTopString(matrixStack, "name", i++, mc.name());
                drawLeftTopString(matrixStack, "forceUnicodeFont", i++, mc.isEnforceUnicode());

                int j = 0;
                drawRightTopString(matrixStack, "demoMode", j++, mc.isDemo());
                drawRightTopString(matrixStack, "gameFocused", j++, mc.isWindowActive());
                drawRightTopString(matrixStack, "gamePaused", j++, mc.isPaused());
                drawRightTopString(matrixStack, "integratedServerRunning", j++, mc.isLocalServer());
                break;
            }
            case WORLD: {
                if (Minecraft.getInstance().level != null) {
                    ClientLevel dimension = Minecraft.getInstance().level;

                    int i = 0;
                    drawLeftTopString(matrixStack, "timeLightningFlash", i++, dimension.getSkyFlashTime());
                    drawLeftTopString(matrixStack, "providerName", i++, dimension.gatherChunkSourceStats());
                    drawLeftTopString(matrixStack, "loadedEntities", i++, dimension.getEntityCount());
                    drawLeftTopString(matrixStack, "nextMapId", i++, dimension.getFreeMapId());
                    drawLeftTopString(matrixStack, "difficulty", i++, dimension.getDifficulty().getDisplayName().getString());
                    drawLeftTopString(matrixStack, "seaLevel", i++, dimension.getSeaLevel());
                    drawLeftTopString(matrixStack, "moonPhase", i++, getMoonPhase(dimension.getMoonPhase()));
                    drawLeftTopString(matrixStack, "spawnAngle", i++, getAngle(dimension.getLevelData().getSpawnAngle()));
                    drawLeftTopString(matrixStack, "dimension", i++, dimension.dimension().location());
                    drawLeftTopString(matrixStack, "dayTime", i++, dimension.getDayTime());
                    drawLeftTopString(matrixStack, "gameTime", i++, dimension.getGameTime());
                    drawLeftTopString(matrixStack, "cloudColor", i++, getColor(dimension.getCloudColor(mc.getFrameTime())));
                    if (Minecraft.getInstance().player != null) {
                        LocalPlayer player = Minecraft.getInstance().player;
                        drawLeftTopString(matrixStack, "skyColor", i++, getColor(dimension.getSkyColor(player.position(), mc.getFrameTime())));
                    }
                    drawLeftTopString(matrixStack, "starBrightness", i++, getPercentage(dimension.getStarBrightness(mc.getFrameTime())));
                    drawLeftTopString(matrixStack, "sunBrightness", i++, getPercentage(dimension.getSkyDarken(mc.getFrameTime())));

                    int j = 0;
                    drawRightTopString(matrixStack, "daytime", j++, dimension.isDay());
                    drawRightTopString(matrixStack, "nightTime", j++, dimension.isNight());
                    drawRightTopString(matrixStack, "raining", j++, dimension.isRaining());
                    drawRightTopString(matrixStack, "thundering", j++, dimension.isThundering());
                    drawRightTopString(matrixStack, "saveDisabled", j++, dimension.noSave());
                    drawRightTopString(matrixStack, "debug", j++, dimension.isDebug());
                }
                break;
            }
            case WORLD_INFO: {
                if (Minecraft.getInstance().level != null) {
                    ClientLevel.ClientLevelData dimensionInfo = Minecraft.getInstance().level.getLevelData();

                    int i = 0;
                    drawLeftTopString(matrixStack, "spawnAngle", i++, dimensionInfo.getSpawnAngle());
                    drawLeftTopString(matrixStack, "difficulty", i++, dimensionInfo.getDifficulty());
                    drawLeftTopString(matrixStack, "dayTime", i++, dimensionInfo.getDayTime());
                    drawLeftTopString(matrixStack, "gameTime", i++, dimensionInfo.getGameTime());
                    drawLeftTopString(matrixStack, "fogDistance", i++, dimensionInfo.getClearColorScale());
                    drawLeftTopString(matrixStack, "spawnX", i++, dimensionInfo.getXSpawn());
                    drawLeftTopString(matrixStack, "spawnY", i++, dimensionInfo.getYSpawn());
                    drawLeftTopString(matrixStack, "spawnZ", i++, dimensionInfo.getZSpawn());

                    int j = 0;
                    drawRightTopString(matrixStack, "difficultyLocked", j++, dimensionInfo.isDifficultyLocked());
                    drawRightTopString(matrixStack, "hardcore", j++, dimensionInfo.isHardcore());
                    drawRightTopString(matrixStack, "raining", j++, dimensionInfo.isRaining());
                    drawRightTopString(matrixStack, "thundering", j++, dimensionInfo.isThundering());
                }
                break;
            }
            case PLAYER_2: {
                if (Minecraft.getInstance().player != null) {
                    Player player = Minecraft.getInstance().player;
                    Team team = player.getTeam();

                    int i = 0;
                    drawLeftTopString(matrixStack, "idleTime", i++, player.getNoActionTime());
                    drawLeftTopString(matrixStack, "motion", i++, player.getDeltaMovement());
                    drawLeftTopString(matrixStack, "team", i++, (team != null ? team.getName() : ""));
                    drawLeftTopString(matrixStack, "xpSeed", i++, player.getEnchantmentSeed());
                    drawLeftTopString(matrixStack, "yOffset", i++, player.getMyRidingOffset());

                    int j = 0;
                    drawRightTopString(matrixStack, "glowing", j++, player.isCurrentlyGlowing());
                    drawRightTopString(matrixStack, "invisible", j++, player.isInvisible());
                    drawRightTopString(matrixStack, "onGround", j++, player.isOnGround());
                    drawRightTopString(matrixStack, "onLadder", j++, player.onClimbable());
                }
                break;
            }
            case PLAYER_1: {
                if (Minecraft.getInstance().player != null) {
                    Player player = Minecraft.getInstance().player;

                    int i = 0;
                    drawLeftTopString(matrixStack, "luck", i++, player.getLuck());
                    drawLeftTopString(matrixStack, "speed", i++, player.getSpeed());
                    drawLeftTopString(matrixStack, "score", i++, player.getScore());
                    drawLeftTopString(matrixStack, "totalArmorValue", i++, player.getArmorValue());
                    drawLeftTopString(matrixStack, "health", i++, player.getHealth());
                    drawLeftTopString(matrixStack, "absorptionAmount", i++, player.getAbsorptionAmount());
                    drawLeftTopString(matrixStack, "hunger", i++, player.getFoodData().getFoodLevel());
                    drawLeftTopString(matrixStack, "saturation", i++, player.getFoodData().getSaturationLevel());
                    drawLeftTopString(matrixStack, "air", i++, player.getAirSupply());
                    drawLeftTopString(matrixStack, "positionBlock", i++, player.blockPosition());
                    drawLeftTopString(matrixStack, "position", i++, player.position());
                    drawLeftTopString(matrixStack, "pitchYaw", i++, getDegrees(player.getRotationVector().x), getDegrees(player.getRotationVector().y));
                    drawLeftTopString(matrixStack, "sleepTimer", i++, player.getSleepTimer());
                    drawLeftTopString(matrixStack, "fireTimer", i++, player.getRemainingFireTicks());
                    drawLeftTopString(matrixStack, "brightness", i++, player.getBrightness());
                    drawLeftTopString(matrixStack, "beeStingCount", i++, player.getStingerCount());

                    // String to be scanned to find the pattern.
                    String pattern = "[a-zA-Z0-9_]*";

                    // Create a Pattern object
                    Pattern r = Pattern.compile(pattern);

                    // Now create matcher object.
                    Matcher m = r.matcher(player.getName().getString());

                    int j = 0;
                    drawRightTopString(matrixStack, "legalUsername", j++, m.find());
                    drawRightTopString(matrixStack, "jumping", j++, player.jumping);
                    drawRightTopString(matrixStack, "sneaking", j++, player.isShiftKeyDown());
                    drawRightTopString(matrixStack, "swimming", j++, player.isSwimming());
                    drawRightTopString(matrixStack, "sleeping", j++, player.isSleeping());
                    drawRightTopString(matrixStack, "sprinting", j++, player.isSprinting());
                    drawRightTopString(matrixStack, "silent", j++, player.isSilent());
                    drawRightTopString(matrixStack, "swingInProgress", j++, player.swinging);
                    drawRightTopString(matrixStack, "user", j++, player.isLocalPlayer());
                    drawRightTopString(matrixStack, "alive", j++, player.isAlive());
                    drawRightTopString(matrixStack, "burning", j++, player.isOnFire());
                    drawRightTopString(matrixStack, "wet ", j++, player.isInWaterOrRain());
                    drawRightTopString(matrixStack, "creative", j++, player.isCreative());
                    drawRightTopString(matrixStack, "invulnerable", j++, player.isInvulnerable());
                    drawRightTopString(matrixStack, "spectator", j++, player.isSpectator());
                    drawRightTopString(matrixStack, "allowEdit", j++, player.mayBuild());

                    int k = 0;
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

                        BlockHitResult lookingAt = null;
                        if (Minecraft.getInstance().level != null) {
                            lookingAt = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
                            if (lookingAt.getType() == HitResult.Type.BLOCK) {
                                BlockPos pos = lookingAt.getBlockPos();

                                // now the coordinates you want are in pos. Example of use:
                                BlockState blockState = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos);
                                drawTopString(matrixStack, "-== Block ==-", k++);
                                drawTopString(matrixStack, blockState.getBlock().getName().getString(), k++);
                                k++;
                            } else {
                                // not looking at a block, or too far away from one to tell
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 16, 0xff0000);
                            }
                            lookingAt = Minecraft.getInstance().level.clip(new ClipContext(vec3d, vec3d1, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player));
                            if (lookingAt.getType() == HitResult.Type.BLOCK) {
                                BlockPos pos = lookingAt.getBlockPos();

                                // now the coordinates you want are in pos. Example of use:
                                BlockState blockState = Minecraft.getInstance().player.getCommandSenderWorld().getBlockState(pos);
                                FluidState fluidState = blockState.getFluidState();
                                if (!fluidState.isEmpty()) {
                                    drawTopString(matrixStack, "-== Fluid ==-", k++);
                                    drawTopString(matrixStack, blockState.getBlock().getName().getString(), k++);
                                    k++;
                                } else {
                                    // not looking at a fluid, or too far away from one to tell
//                                    Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
                                }
                            } else {
                                // not looking at a fluid, or too far away from one to tell
                                Screen.drawCenteredString(matrixStack, mc.font, "<None>", width / 2, height / 2 - 32, 0xff0000);
                            }
                        } else {
                            Screen.drawCenteredString(matrixStack, mc.font, "<Invalid>", width / 2, height / 2 - 32, 0xbf0000);
                        }
                    }

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

                                drawTopString(matrixStack, "-== Entity ==-", k++);
                                drawTopString(matrixStack, I18n.get(entityRayTraceResult.getEntity().getType().getDescriptionId()), k++);
                                k++;
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, I18n.format(entityRayTraceResult.getEntity().getType().getTranslationId()), width / 2, height / 2 - 48, 0x00bf00);
                            } else {
                                // not looking at a block, or too far away from one to tell
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 48, 0xff0000);
                            }
                        }
                    }
                } else {
//                    Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<Invalid>", width / 2, height / 2 - 16, 0xbf0000);
                }
                break;
            }
            case NONE: {
                ClientLevel dimension = Minecraft.getInstance().level;
                LocalPlayer player = Minecraft.getInstance().player;
                if (dimension != null && player != null) {
                    String dayTimeStr = Long.toString(dimension.getDayTime() % 24000);
                    StringBuilder timeText = new StringBuilder();
                    if (dayTimeStr.length() == 1) {
                        timeText.append("0:00,").append(dayTimeStr);
                    } else if (dayTimeStr.length() == 2) {
                        timeText.append("0:0").append(dayTimeStr.charAt(0)).append(",").append(dayTimeStr.substring(1));
                    } else if (dayTimeStr.length() == 3) {
                        timeText.append("0:").append(dayTimeStr, 0, 2).append(",").append(dayTimeStr.substring(2));
                    } else if (dayTimeStr.length() == 4) {
                        timeText.append(dayTimeStr.charAt(0)).append(":").append(dayTimeStr, 1, 3).append(",").append(dayTimeStr.substring(3));
                    } else if (dayTimeStr.length() == 5) {
                        timeText.append(dayTimeStr, 0, 2).append(":").append(dayTimeStr, 2, 4).append(",").append(dayTimeStr.substring(4));
                    } else {
                        timeText.append("OVERFLOW");
                    }

                    int i = 0;

                    drawLeftTopString(matrixStack, "RandomThingz Build", i++, new Formatted(RandomThingz.RT_ARGS.getVersion().getBuild()));
                    drawLeftTopString(matrixStack, "Time", i++, new Formatted(timeText.toString()));

                    long dayTime = dimension.getDayTime() % 24000;

                    String timePhase;
                    if (dayTime < 3000 || dayTime > 21000) {
                        timePhase = "Evening";
                    } else if (dayTime > 3000 && dayTime < 9000) {
                        timePhase = "Noon";
                    } else if (dayTime > 9000 && dayTime < 15000) {
                        timePhase = "Afternoon";
                    } else if (dayTime > 15000 && dayTime < 21000) {
                        timePhase = "Night";
                    } else {
                        timePhase = "UNKNOWN";
                    }

                    drawLeftTopString(matrixStack, "Time Phase", i++, new Formatted(timePhase));

                    if (!dimension.isClientSide) {
                        Biome biome = dimension.getBiome(player.getOnPos());
                        ResourceLocation location = biome.getRegistryName();
                        if (location != null) {
                            @NotNull ResourceLocation registryName = location;
                            drawLeftTopString(matrixStack, "Current Biome", i++, new Formatted(I18n.get("biome." + registryName.getNamespace() + "." + registryName.getPath())));
                        }
                    }
                    drawLeftTopString(matrixStack, "Current Pos", i++, new Formatted(player.getOnPos().toShortString()));
                }
            }
            default: {
                break;
            }
        }
    }

    private static com.ultreon.randomthingz.common.interfaces.Formattable getFormatted(String s) {
        return () -> s;
    }

    private static com.ultreon.randomthingz.common.interfaces.Formattable getMultiplier(double multiplier) {
        return new Multiplier(multiplier);
    }

    private static com.ultreon.randomthingz.common.interfaces.Formattable getSize(int w, int h) {
        return new IntSize(w, h);
    }

    private static com.ultreon.randomthingz.common.interfaces.Formattable getSize(float w, float h) {
        return () -> ChatFormatting.GOLD.toString() + w + ChatFormatting.GRAY + " x " + ChatFormatting.GOLD + h;
    }

    private static com.ultreon.randomthingz.common.interfaces.Formattable getPercentage(double value) {
        return new Percentage(value);
    }

    private static Color getColor(Vec3 color) {
        return new Color((float) color.x, (float) color.y, (float) color.z);
    }

    private static Color getColor(int rgb) {
        return new Color(rgb);
    }

    private static com.ultreon.randomthingz.common.interfaces.Formattable getAngle(double angle) {
        return new Angle(angle * 360.0d);
    }

    private static com.ultreon.randomthingz.common.interfaces.Formattable getRadians(double angle) {
        return new Angle(Math.toDegrees(angle));
    }

    private static com.ultreon.randomthingz.common.interfaces.Formattable getDegrees(double angle) {
        return new Angle(angle);
    }

    private static MoonPhase getMoonPhase(int index) {
        return MoonPhase.fromIndex(index);
    }

    public static String format(String text, Object obj, Object... objects) {
        StringBuilder sb = new StringBuilder();

//        sb.append(ChatFormatting.DARK_AQUA).append(text);
        sb.append(ChatFormatting.GRAY).append(text);
        sb.append(ChatFormatting.GRAY).append(": ");
        sb.append(format(obj));

        for (Object object : objects) {
            sb.append(ChatFormatting.GRAY).append(", ");
            sb.append(format(object));
        }

        return sb.toString();
    }

    public static String format(Object obj) {
        StringBuilder sb = new StringBuilder();

        if (obj == null) {
            sb.append(ChatFormatting.LIGHT_PURPLE);
            sb.append("null");
        } else if (obj instanceof String) {
            sb.append(ChatFormatting.GOLD);
            sb.append("\"");
            sb.append(obj.toString()
                    .replaceAll("\\\\", ChatFormatting.WHITE + "\\\\" + ChatFormatting.GOLD)
                    .replaceAll("\n", ChatFormatting.WHITE + "\\n" + ChatFormatting.GOLD)
                    .replaceAll("\r", ChatFormatting.WHITE + "\\r" + ChatFormatting.GOLD)
                    .replaceAll("\t", ChatFormatting.WHITE + "\\t" + ChatFormatting.GOLD)
                    .replaceAll("\b", ChatFormatting.WHITE + "\\b" + ChatFormatting.GOLD)
                    .replaceAll("\f", ChatFormatting.WHITE + "\\f" + ChatFormatting.GOLD));
            sb.append("\"");
        } else if (obj instanceof Character) {
            sb.append(ChatFormatting.GOLD);
            sb.append("'");
            if (obj.equals('\\')) {
                sb.append(ChatFormatting.WHITE);
                sb.append("\\\\");
            } else if (obj.equals('\n')) {
                sb.append(ChatFormatting.WHITE);
                sb.append("\\n");
            } else if (obj.equals('\r')) {
                sb.append(ChatFormatting.WHITE);
                sb.append("\\r");
            } else if (obj.equals('\t')) {
                sb.append(ChatFormatting.WHITE);
                sb.append("\\t");
            } else if (obj.equals('\b')) {
                sb.append(ChatFormatting.WHITE);
                sb.append("\\b");
            } else if (obj.equals('\f')) {
                sb.append(ChatFormatting.WHITE);
                sb.append("\\f");
            } else {
                sb.append(obj);
            }
            sb.append(ChatFormatting.GOLD);
            sb.append("'");
        } else if (obj instanceof Integer) {
            sb.append(ChatFormatting.YELLOW);
            sb.append(obj);
        } else if (obj instanceof Short) {
            sb.append(ChatFormatting.YELLOW);
            sb.append(obj);
            sb.append("s");
        } else if (obj instanceof Byte) {
            sb.append(ChatFormatting.YELLOW);
            sb.append(obj);
            sb.append("b");
        } else if (obj instanceof Long) {
            sb.append(ChatFormatting.YELLOW);
            sb.append(obj);
            sb.append("L");
        } else if (obj instanceof Float) {
            sb.append(ChatFormatting.YELLOW);
            sb.append(obj);
            sb.append("f");
        } else if (obj instanceof Double) {
            sb.append(ChatFormatting.YELLOW);
            sb.append(obj);
            sb.append("d");
        } else if (obj instanceof Boolean) {
            sb.append(ChatFormatting.LIGHT_PURPLE);
            sb.append(obj);
        } else if (obj instanceof Enum<?> e) {
            sb.append(ChatFormatting.GREEN).append(e);
        } else if (obj instanceof List<?> list) {
            sb.append(ChatFormatting.GRAY);
            sb.append("[");

            Iterator<?> it = list.iterator();
            if (!it.hasNext()) {
                sb.append("]");
                return sb.toString();
            }

            for (; ; ) {
                Object e = it.next();
                sb.append(e == list ? (ChatFormatting.GRAY + "(" + ChatFormatting.WHITE + "this List" + ChatFormatting.GRAY + ")") : format(e));
                if (!it.hasNext()) {
                    return sb.append(ChatFormatting.GRAY).append(']').toString();
                }
                sb.append(ChatFormatting.GRAY).append(',').append(' ');
            }
        } else if (obj instanceof Set<?> set) {
            sb.append(ChatFormatting.GRAY);
            sb.append("{");

            Iterator<?> it = set.iterator();
            if (!it.hasNext()) {
                sb.append("}");
                return sb.toString();
            }

            for (; ; ) {
                Object e = it.next();
                sb.append(e == set ? (ChatFormatting.GRAY + "(" + ChatFormatting.WHITE + "this Set" + ChatFormatting.GRAY + ")") : format(e));
                if (!it.hasNext()) {
                    return sb.append(ChatFormatting.GRAY).append('}').toString();
                }
                sb.append(ChatFormatting.GRAY).append(',').append(' ');
            }
        } else if (obj instanceof Map<?, ?> map) {
            sb.append(ChatFormatting.GRAY);
            sb.append("{");

            Iterator<? extends Map.Entry<?, ?>> it = map.entrySet().iterator();
            if (!it.hasNext()) {
                sb.append("}");
                return sb.toString();
            }

            for (; ; ) {
                Map.Entry<?, ?> e = it.next();
                sb.append(e.getKey() == map ? (ChatFormatting.GRAY + "(" + ChatFormatting.WHITE + "this Map" + ChatFormatting.GRAY + ")") : format(e.getKey()));
                sb.append(ChatFormatting.GRAY).append(": ");
                sb.append(e.getValue() == map ? (ChatFormatting.GRAY + "(" + ChatFormatting.WHITE + "this Map" + ChatFormatting.GRAY + ")") : format(e.getValue()));
                if (!it.hasNext()) {
                    return sb.append(ChatFormatting.GRAY).append('}').toString();
                }
                sb.append(ChatFormatting.GRAY).append(", ");
            }
        } else if (obj instanceof Map.Entry<?, ?> e) {
            sb.append(ChatFormatting.GRAY);

            sb.append(e.getKey() == e ? (ChatFormatting.GRAY + "(" + ChatFormatting.WHITE + "this Entry" + ChatFormatting.GRAY + ")") : format(e.getKey()));
            sb.append(ChatFormatting.GRAY).append(": ");
            sb.append(e.getValue() == e ? (ChatFormatting.GRAY + "(" + ChatFormatting.WHITE + "this Entry" + ChatFormatting.GRAY + ")") : format(e.getValue()));
            sb.append(ChatFormatting.GRAY).append(", ");
        } else if (obj instanceof Vec2 v) {
            sb.append(ChatFormatting.GRAY).append("x: ");
            sb.append(format(v.x));
            sb.append(ChatFormatting.GRAY).append(", y: ");
            sb.append(format(v.y));
        } else if (obj instanceof Vector3f v) {
            sb.append(ChatFormatting.GRAY).append("x: ");
            sb.append(format(v.x()));
            sb.append(ChatFormatting.GRAY).append(", y: ");
            sb.append(format(v.y()));
            sb.append(ChatFormatting.GRAY).append(", z: ");
            sb.append(format(v.z()));
        } else if (obj instanceof Vector3d v) {
            sb.append(ChatFormatting.GRAY).append("x: ");
            sb.append(format(v.x));
            sb.append(ChatFormatting.GRAY).append(", y: ");
            sb.append(format(v.y));
            sb.append(ChatFormatting.GRAY).append(", z: ");
            sb.append(format(v.z));
        } else if (obj instanceof Vector4f v) {
            sb.append(ChatFormatting.GRAY).append("x: ");
            sb.append(format(v.x()));
            sb.append(ChatFormatting.GRAY).append(", y: ");
            sb.append(format(v.y()));
            sb.append(ChatFormatting.GRAY).append(", z: ");
            sb.append(format(v.z()));
            sb.append(ChatFormatting.GRAY).append(", w: ");
            sb.append(format(v.w()));
        } else if (obj instanceof BlockPos v) {
            sb.append(ChatFormatting.GRAY).append("x: ");
            sb.append(format(Math.round(v.getX())));
            sb.append(ChatFormatting.GRAY).append(", y: ");
            sb.append(format(Math.round(v.getY())));
            sb.append(ChatFormatting.GRAY).append(", z: ");
            sb.append(format(Math.round(v.getZ())));
        } else if (obj instanceof Color c) {
            sb.append(ChatFormatting.GRAY).append("#");
            sb.append(ChatFormatting.BLUE);
            String s = Integer.toHexString(c.getRGB());
            sb.append("0".repeat(8 - s.length()));

            sb.append(s);
        } else if (obj instanceof Component e) {
            sb.append(ChatFormatting.GRAY).append("Component: ");
            sb.append(format(e.getString()));
        } else if (obj instanceof ItemStack e) {
            sb.append(ChatFormatting.BLUE).append(e.getItem().getRegistryName()).append(" ");
            sb.append(ChatFormatting.GRAY).append(e.getCount()).append("x");
        } else if (obj instanceof Formattable e) {
            sb.append(e.toFormattedString());
        } else if (obj instanceof IForgeRegistryEntry<?> ifrEntry) {
            sb.append(format(ifrEntry.getRegistryType()));
            sb.append(ChatFormatting.GRAY).append("@");
            sb.append(format(ifrEntry.getRegistryName()));
        } else if (obj instanceof IForgeRegistry<?> ifr) {
            sb.append(format(ifr.getRegistryName()));
        } else if (obj instanceof ResourceLocation rl) {
            sb.append(ChatFormatting.GOLD).append(rl.getNamespace());
            sb.append(ChatFormatting.GRAY).append(":");
            sb.append(ChatFormatting.YELLOW).append(rl.getPath());
        } else if (obj instanceof Player) {
            Entity e = (Entity) obj;
            sb.append(ChatFormatting.GRAY).append("Player: ");
            sb.append(format(e.getName().getString()));
        } else if (obj instanceof Entity e) {
            sb.append(ChatFormatting.GRAY).append("Entity: ");
            sb.append(format(e.getUUID()));
        } else if (obj instanceof UUID uuid) {
            sb.append(ChatFormatting.GOLD).append(uuid.toString().replaceAll("-", ChatFormatting.GRAY + "-" + ChatFormatting.GOLD));
        } else if (obj instanceof Collection<?> collection) {
            sb.append(ChatFormatting.GRAY);
            sb.append("(");

            Iterator<?> it = collection.iterator();
            if (!it.hasNext()) {
                sb.append(")");
                return sb.toString();
            }

            for (; ; ) {
                Object e = it.next();
                sb.append(e == collection ? (ChatFormatting.GRAY + "(" + ChatFormatting.WHITE + "this Collection" + ChatFormatting.GRAY + ")") : format(e));
                if (!it.hasNext()) {
                    return sb.append(ChatFormatting.GRAY).append(')').toString();
                }
                sb.append(ChatFormatting.GRAY).append(',').append(' ');
            }
        } else if (obj instanceof Class<?> c) {

            sb.append(ChatFormatting.AQUA);
            sb.append(c.getPackage().getName().replaceAll("\\.", ChatFormatting.GRAY + "." + ChatFormatting.AQUA));
            sb.append(ChatFormatting.GRAY).append(".").append(ChatFormatting.AQUA);
            sb.append(ChatFormatting.DARK_AQUA);
            sb.append(c.getSimpleName());
        } else {
            Class<?> c = obj.getClass();
            sb.append(ChatFormatting.AQUA);
            sb.append(c.getPackage().getName().replaceAll("\\.", ChatFormatting.GRAY + "." + ChatFormatting.AQUA));
            sb.append(ChatFormatting.GRAY).append(".").append(ChatFormatting.AQUA);
            sb.append(ChatFormatting.DARK_AQUA);
            sb.append(c.getSimpleName());
            sb.append(ChatFormatting.GRAY).append("@").append(ChatFormatting.YELLOW);
            sb.append(Integer.toHexString(obj.hashCode()));
        }
        return sb.toString();
    }

    private static void drawTopString(PoseStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        Font fontRenderer = Minecraft.getInstance().font;
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        // Draw text.
        fontRenderer.drawShadow(matrixStack, text, width / 2f - fontRenderer.width(text) / 2f, 12f + (line * 12), 0xffffff);
    }

    private static void drawLeftTopString(PoseStack matrixStack, String text, int line, Object obj, Object... objects) {
        // Declare local variables before draw.
        Font fontRenderer = Minecraft.getInstance().font;

        text = format(text, obj, objects);

        // Draw text.
        fontRenderer.drawShadow(matrixStack, text, 12f, 12f + (line * 12), 0xffffff);
    }

    private static void drawRightTopString(PoseStack matrixStack, String text, int line, Object obj, Object... objects) {
        // Declare local variables before draw.
        Font fontRenderer = Minecraft.getInstance().font;
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        text = format(text, obj, objects);

        // Draw text.
        fontRenderer.drawShadow(matrixStack, text, width - 12 - fontRenderer.width(text), 12f + (line * 12), 0xffffff);
    }

    @SuppressWarnings("unused")
    private static void drawBottomString(PoseStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        Font fontRenderer = Minecraft.getInstance().font;
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        // Draw text.
        fontRenderer.drawShadow(matrixStack, text, width / 2f - fontRenderer.width(text) / 2f, height - 29f - (line * 12), 0xffffff);
    }

    @SuppressWarnings("unused")
    private static void drawLeftBottomString(PoseStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        Font fontRenderer = Minecraft.getInstance().font;
        int height = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        // Draw text.
        fontRenderer.drawShadow(matrixStack, text, 12, height - 29f - (line * 12), 0xffffff);
    }

    @SuppressWarnings("unused")
    private static void drawRightBottomString(PoseStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        Font fontRenderer = Minecraft.getInstance().font;
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        // Draw text.
        fontRenderer.drawShadow(matrixStack, text, width - 12 - fontRenderer.width(text), height - 29f - (line * 12), 0xffffff);
    }

    @SuppressWarnings({"unused", "SameParameterValue"})
    private static void drawRightString(PoseStack matrixStack, String text, float mx, float y, int color) {
        // Declare local variables before draw.
        Font fontRenderer = Minecraft.getInstance().font;
        int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();

        // Draw text.
        fontRenderer.drawShadow(matrixStack, text, width - mx - fontRenderer.width(text), y, color);
    }
}
