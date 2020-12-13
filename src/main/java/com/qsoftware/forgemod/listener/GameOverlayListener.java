package com.qsoftware.forgemod.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.*;
import com.qsoftware.forgemod.hud.HudItems;
import com.qsoftware.forgemod.hud.IHasHud;
import com.qsoftware.forgemod.keybinds.KeyBindingList;
import com.qsoftware.silent.lib.client.key.InputUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Monitor;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.lwjgl.glfw.GLFW;
import sun.awt.OSInfo;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Client listener
 *
 * @author (partial) CoFH - https://github.com/CoFH
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GameOverlayListener {
    public static PAGE DEBUG_PAGE = PAGE.NONE;
    
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
    
    @SubscribeEvent
    public static void onKeyReleased(InputEvent.KeyInputEvent event) {
        if (event.getAction() == GLFW.GLFW_RELEASE) {
            if (event.getKey() == KeyBindingList.DEBUG_SCREEN.getKey().getKeyCode()) {
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
    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;

        if (Minecraft.getInstance().gameSettings.showDebugInfo) {
            return;
        }

        MatrixStack matrixStack = event.getMatrixStack();
        Minecraft mc = Minecraft.getInstance();
        MainWindow mainWindow = mc.getMainWindow();
        Monitor monitor = mainWindow.getMonitor();

        int width = mainWindow.getScaledWidth();
        int height = mainWindow.getScaledHeight();

        int dw, dh;
        if (monitor != null) {
            dw = monitor.getDefaultVideoMode().getWidth();
            dh = monitor.getDefaultVideoMode().getHeight();
        } else {
            dw = 0;
            dh = 0;
        }

        for (IHasHud hud : HudItems.ITEMS) {
            hud.render(matrixStack, mc);
        }

        if (DEBUG_PAGE != PAGE.NONE) {
            mc.fontRenderer.drawStringWithShadow(matrixStack, "debugPage: " + DEBUG_PAGE.name(), 12f, height - 16f, 0xffaa00);
        }

        switch (DEBUG_PAGE) {
            case WINDOW: {
                int i = 0;
                drawLeftTopString(matrixStack, "guiScaleFactor", i++, getMultiplier(mainWindow.getGuiScaleFactor()));
                drawLeftTopString(matrixStack, "windowSizeScaled", i++, getSize(mainWindow.getScaledWidth(), mainWindow.getScaledHeight()));
                drawLeftTopString(matrixStack, "windowSize", i++, getSize(mainWindow.getWidth(),  mainWindow.getHeight()));
                drawLeftTopString(matrixStack, "windowHandle", i++, mainWindow.getHandle());
                drawLeftTopString(matrixStack, "framebufferSize", i++, getSize(mainWindow.getFramebufferWidth(), mainWindow.getFramebufferHeight()));
                drawLeftTopString(matrixStack, "refreshTate", i++, getFormatted(TextFormatting.GOLD.toString() + mainWindow.getRefreshRate() + TextFormatting.GRAY + " fps"));
                drawLeftTopString(matrixStack, "framerateLimit", i++, getFormatted(TextFormatting.GOLD.toString() + mainWindow.getLimitFramerate() + TextFormatting.GRAY + "fps"));
                
                int j = 0;
                drawRightTopString(matrixStack, "fullscreenMode", j++, mainWindow.isFullscreen());
                break;
            }
            case BLOCK: {
                if (Minecraft.getInstance().player != null) {
                    PlayerEntity player = Minecraft.getInstance().player;
                    float f = player.rotationPitch;
                    float f1 = player.rotationYaw;

                    Vector3d vec3d = player.getEyePosition(1.0F);

                    float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
                    float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
                    float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
                    float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));

                    float f6 = f3 * f4;
                    float f7 = f2 * f4;

                    double d0 = 16; // Todo: test value, if it will lag, then lower the value. ( Possible not needed ;) )

                    Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

                    int i = 1;
                    int j = 1;

                    BlockRayTraceResult lookingAt = null;
                    if (Minecraft.getInstance().world != null) {
                        lookingAt = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
                        if (lookingAt.getType() == RayTraceResult.Type.BLOCK) {
                            BlockPos pos = lookingAt.getPos();

                            // now the coordinates you want are in pos. Example of use:
                            BlockState blockState = Minecraft.getInstance().player.getEntityWorld().getBlockState(pos);
                            Block block = blockState.getBlock();
                            mc.fontRenderer.drawStringWithShadow(matrixStack, TextFormatting.GRAY + "-== BLOCK ==-", 12f, 12f, 0xffffff);
                            drawLeftTopString(matrixStack, "translatedName", i++, block.getTranslatedName().getString());
                            drawLeftTopString(matrixStack, "harvestLevel", i++, blockState.getHarvestLevel());
                            drawLeftTopString(matrixStack, "harvestTool", i++, blockState.getHarvestTool() == null ? null : blockState.getHarvestTool().getName());
                            drawLeftTopString(matrixStack, "blockHardness", i++, blockState.getBlockHardness(player.getEntityWorld(), pos));
                            drawLeftTopString(matrixStack, "lightValue", i++, blockState.getLightValue());
                            drawLeftTopString(matrixStack, "opacity", i++, blockState.getOpacity(player.getEntityWorld(), pos));
                            drawLeftTopString(matrixStack, "offset", i++, blockState.getOffset(player.getEntityWorld(), pos));
                            drawLeftTopString(matrixStack, "playerRelativeHardness", i++, blockState.getPlayerRelativeBlockHardness(player, player.getEntityWorld(), pos));
                            drawLeftTopString(matrixStack, "requiresTool", i++, blockState.getRequiresTool());
                            drawLeftTopString(matrixStack, "renderType", i++, blockState.getRenderType());
                            drawLeftTopString(matrixStack, "slipperiness", i++, blockState.getSlipperiness(player.getEntityWorld(), pos, player));
                            drawLeftTopString(matrixStack, "jumpFactor", i++, block.getJumpFactor());
                            drawLeftTopString(matrixStack, "enchantPowerBonus", i++, blockState.getEnchantPowerBonus(player.getEntityWorld(), pos));
                            drawLeftTopString(matrixStack, "lootTable", i++, block.getLootTable());
                            drawLeftTopString(matrixStack, "materialColor", i++, block.getMaterialColor().colorIndex, getColor(block.getMaterialColor().colorValue));
                            drawLeftTopString(matrixStack, "offsetType", i++, block.getOffsetType());
                            drawLeftTopString(matrixStack, "registryName", i++, block.getRegistryName());
                            drawLeftTopString(matrixStack, "defaultSlipperiness", i++, block.getSlipperiness());
                            drawLeftTopString(matrixStack, "speedFactor", i++, getMultiplier(block.getSpeedFactor()));
                        } else {
                            // not looking at a block, or too far away from one to tell
                            Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 16, 0xff0000);
                        }

                        lookingAt = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
                        if (lookingAt.getType() == RayTraceResult.Type.BLOCK) {
                            BlockPos pos = lookingAt.getPos();

                            // now the coordinates you want are in pos. Example of use:
                            BlockState blockState = Minecraft.getInstance().player.getEntityWorld().getBlockState(pos);
                            FluidState fluidState = blockState.getFluidState();
                            if (!fluidState.isEmpty()) {
                                drawRightString(matrixStack, TextFormatting.GRAY + "-== Fluid ==-", 12f, 12f, 0xffffff);
                                drawRightTopString(matrixStack, "empty", j++, fluidState.isEmpty());
                                drawRightTopString(matrixStack, "height", j++, fluidState.getHeight());
                                drawRightTopString(matrixStack, "level", j++, fluidState.getLevel());
                                drawRightTopString(matrixStack, "actualHeight", j++, fluidState.getFluid().getActualHeight(fluidState, player.getEntityWorld(), pos));
                                try {
                                    drawRightTopString(matrixStack, "filledBucket", j++, fluidState.getFluid().getFilledBucket());
                                } catch (Throwable ignored) {

                                }
                                drawRightTopString(matrixStack, "tickRate", j++, fluidState.getFluid().getTickRate(player.getEntityWorld()));
                            } else {
                                // not looking at a fluid, or too far away from one to tell
//                                    Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
                            }
                        } else {
                            // not looking at a fluid, or too far away from one to tell
                            Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
                        }
                    } else {
                        Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<Invalid>", width / 2, height / 2 - 32, 0xbf0000);
                    }
                }
                break;
            } case COMPUTER: {
                int i = 0;
                if (monitor != null) {
                    drawLeftTopString(matrixStack, "screenSize", i++, dw, dh);
                }
                drawLeftTopString(matrixStack, "osType", i++, OSInfo.getOSType());
//                if (OSInfo.getWindowsVersion() != null) {
//                    try {
//                        drawLeftTopString(matrixStack, "windowsVersion", i++, OSInfo.getWindowsVersion().toString());
//                    } catch (SecurityException ignored) {
//
//                    }
//                }
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
                drawRightTopString(matrixStack, "isJava64bit", i++, (mc.isJava64bit() ? "yes" : "no"));
                break;
            }
            case ITEM: {
                if (Minecraft.getInstance().player != null) {
                    PlayerEntity player = Minecraft.getInstance().player;
                    ItemStack stack = player.getHeldItemMainhand();
                    Item item = stack.getItem();
                    Food food = item.getFood();
                    ItemGroup group = item.getGroup();

                    //noinspection ConstantConditions
                    if (stack == null) {
                        drawTopString(matrixStack, "NO ITEM", 0);
                        break;
                    }

                    int i = 0;

                    drawLeftTopString(matrixStack, "registryName", i++, stack.getItem().getRegistryName());
                    if (food != null) {
                        drawLeftTopString(matrixStack, "foodHealing", i++, food.getHealing());
                        drawLeftTopString(matrixStack, "foodSaturation", i++, food.getSaturation());
                    }
                    if (group != null) {
                        drawLeftTopString(matrixStack, "groupName", i++, group.getGroupName().getString());
                    }
                    drawLeftTopString(matrixStack, "rarity", i++, item.getRarity(stack));
                    drawLeftTopString(matrixStack, "enchantability", i++, item.getItemEnchantability(stack));
                    drawLeftTopString(matrixStack, "stackLimit", i++, item.getItemStackLimit(stack));
                    drawLeftTopString(matrixStack, "maxDamage", i++, item.getMaxDamage(stack));
                    drawLeftTopString(matrixStack, "damage", i++, item.getDamage(stack));
                    drawLeftTopString(matrixStack, "burnTime", i++, item.getBurnTime(stack));
                    drawLeftTopString(matrixStack, "count", i++, stack.getCount());
                    drawLeftTopString(matrixStack, "repairCost", i++, stack.getRepairCost());
                    drawLeftTopString(matrixStack, "useDuration", i++, stack.getUseDuration());
                    drawLeftTopString(matrixStack, "xpRepairRation", i++, stack.getXpRepairRatio());

                    int j = 0;
                    drawRightTopString(matrixStack, "complex", j++, item.isComplex());
                    drawRightTopString(matrixStack, "immuneToFire", j++, item.isImmuneToFire());
                    drawRightTopString(matrixStack, "enchantable", j++, item.isEnchantable(stack));
                    drawRightTopString(matrixStack, "empty", j++, stack.isEmpty());
                    drawRightTopString(matrixStack, "isPiglinCurrency", j++, stack.isPiglinCurrency());
                    drawRightTopString(matrixStack, "repairable", j++, stack.isRepairable());
                    drawRightTopString(matrixStack, "stackable", j++, stack.isStackable());
                    drawRightTopString(matrixStack, "sliceable", j++, (item instanceof Sliceable));
                    drawRightTopString(matrixStack, "damageable", j++, item.isDamageable());
                    drawRightTopString(matrixStack, "damaged", j++, item.isDamaged(stack));
                }
                break;
            } case ENTITY: {
                if (Minecraft.getInstance().player != null) {
                    PlayerEntity player = Minecraft.getInstance().player;
                    float f = player.rotationPitch;
                    float f1 = player.rotationYaw;

                    Vector3d vec3d = player.getEyePosition(1.0F);

                    float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
                    float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
                    float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
                    float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));

                    float f6 = f3 * f4;
                    float f7 = f2 * f4;

                    double d0 = 16; // Todo: test value, if it will lag, then lower the value. ( Possible not needed ;) )

                    Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

                    int i = 1;
                    int j = 1;

                    if (Minecraft.getInstance().world != null) {
                        RayTraceResult raytraceresult = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
                        if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                            vec3d1 = raytraceresult.getHitVec();
                        }

                        RayTraceResult rayTraceResult1 = ProjectileHelper.rayTraceEntities(Minecraft.getInstance().world, player, vec3d, vec3d1, player.getBoundingBox().grow(16.0D), entity -> !entity.equals(player));
                        if (rayTraceResult1 != null) {
                            raytraceresult = rayTraceResult1;
                        }
                        if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                            @SuppressWarnings("ConstantConditions") EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) raytraceresult;

                            Entity entity = entityRayTraceResult.getEntity();
                            EntityType<? extends Entity> type = entity.getType();

                            mc.fontRenderer.drawStringWithShadow(matrixStack, TextFormatting.GRAY + "-== Entity ==-", 12f, 12f, 0xffffff);
                            drawLeftTopString(matrixStack, "translatedName", i++, I18n.format(type.getTranslationKey()));
                            drawLeftTopString(matrixStack, "height", i++, type.getHeight());
                            drawLeftTopString(matrixStack, "lootTable", i++, type.getLootTable());
                            drawLeftTopString(matrixStack, "name", i++, type.getName().getString());
                            drawLeftTopString(matrixStack, "registryName", i++, type.getRegistryName());
                            drawLeftTopString(matrixStack, "size", i++, getSize(type.getSize().width, type.getSize().height));
                            drawLeftTopString(matrixStack, "air", i++, entity.getAir());
                            drawLeftTopString(matrixStack, "maxAir", i++, entity.getMaxAir());
                            drawLeftTopString(matrixStack, "brightness", i++, entity.getBrightness());
                            drawLeftTopString(matrixStack, "entityId", i++, entity.getEntityId());
                            drawLeftTopString(matrixStack, "eyeHeight", i++, entity.getEyeHeight());
                            drawLeftTopString(matrixStack, "lookVec", i++, entity.getLookVec());
                            drawLeftTopString(matrixStack, "ridingEntity", i++, entity.getRidingEntity());
                            drawLeftTopString(matrixStack, "uniqueID", i++, entity.getUniqueID());
                            drawLeftTopString(matrixStack, "yOffser", i++, entity.getYOffset());

                            if (entity instanceof LivingEntity) {
                                LivingEntity living = (LivingEntity) entity;

                                drawRightString(matrixStack, TextFormatting.GRAY + "-== Living Entity ==-", 12f, 12f, 0xffffff);
                                drawRightTopString(matrixStack, "health", j++, living.getHealth());
                                drawRightTopString(matrixStack, "maxHealth", j++, living.getMaxHealth());
                                drawRightTopString(matrixStack, "absorptionAmount", j++, living.getAbsorptionAmount());
                                drawRightTopString(matrixStack, "movementSpeed", j++, living.getMovementSpeed());
                                drawRightTopString(matrixStack, "aiMoveSpeed", j++, living.getAIMoveSpeed());
                                drawRightTopString(matrixStack, "activeHand", j++, living.getActiveHand());
                                drawRightTopString(matrixStack, "attackingEntity", j++, living.getAttackingEntity());
                                drawRightTopString(matrixStack, "itemInUseCount", j++, living.getItemInUseCount());
                                drawRightTopString(matrixStack, "lastAttackedEntity", j++, living.getLastAttackedEntity());
                                drawRightTopString(matrixStack, "leashPosition", j++, living.getLeashPosition(mc.getRenderPartialTicks()));
                                drawRightTopString(matrixStack, "pose", j++, living.getPose());
                                drawRightTopString(matrixStack, "scale", j++, living.getRenderScale());
                                drawRightTopString(matrixStack, "revengeTarget", j++, living.getRevengeTarget());
                                drawRightTopString(matrixStack, "totalArmorValue", j++, living.getTotalArmorValue());
                            } else if (entity instanceof ItemEntity) {
                                ItemEntity itemEntity = (ItemEntity) entity;

                                drawRightString(matrixStack, TextFormatting.GRAY + "-== Item Entity ==-", 12f, 12f, 0xffffff);
                                drawRightTopString(matrixStack, "age", j++, itemEntity.getAge());
                                drawRightTopString(matrixStack, "item", j++, itemEntity.getItem());
                                drawRightTopString(matrixStack, "leashPosition", j++, itemEntity.getLeashPosition(mc.getRenderPartialTicks()));
                                drawRightTopString(matrixStack, "pose", j++, itemEntity.getPose());
                            }
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, I18n.format(entityRayTraceResult.getEntity().getType().getTranslationKey()), width / 2, height / 2 - 48, 0x00bf00);
                        } else {
                            // not looking at a block, or too far away from one to tell
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 48, 0xff0000);
                        }
                    }
                }
                break;
            } case MINECRAFT: {
                int i = 0;
                drawLeftTopString(matrixStack, "version", i++, mc.getVersion());
                drawLeftTopString(matrixStack, "versionType", i++, mc.getVersionType());
                drawLeftTopString(matrixStack, "name", i++, mc.getName());
                drawLeftTopString(matrixStack, "forceUnicodeFont", i++, mc.getForceUnicodeFont());

                int j = 0;
                drawRightTopString(matrixStack, "demoMode", j++, mc.isDemo());
                drawRightTopString(matrixStack, "chatEnabled", j++, mc.isChatEnabled());
                drawRightTopString(matrixStack, "gameFocused", j++, mc.isGameFocused());
                drawRightTopString(matrixStack, "gamePaused", j++, mc.isGamePaused());
                drawRightTopString(matrixStack, "integratedServerRunning", j++, mc.isIntegratedServerRunning());
                break;
            } case WORLD: {
                if (Minecraft.getInstance().world != null) {
                    ClientWorld world = Minecraft.getInstance().world;

                    int i = 0;
                    drawLeftTopString(matrixStack, "timeLightningFlash", i++, world.getTimeLightningFlash());
                    drawLeftTopString(matrixStack, "providerName", i++, world.getProviderName());
                    drawLeftTopString(matrixStack, "loadedEntities", i++, world.getCountLoadedEntities());
                    drawLeftTopString(matrixStack, "nextMapId", i++, world.getNextMapId());
                    drawLeftTopString(matrixStack, "difficulty", i++, world.getDifficulty().getDisplayName().getString());
                    drawLeftTopString(matrixStack, "seaLevel", i++, world.getSeaLevel());
                    drawLeftTopString(matrixStack, "moonPhase", i++, getMoonPhase(world.getMoonPhase()));
                    drawLeftTopString(matrixStack, "spawnAngle", i++, getAngle(world.getWorldInfo().getSpawnAngle()));
                    drawLeftTopString(matrixStack, "dimension", i++, world.getDimensionKey().getLocation());
                    drawLeftTopString(matrixStack, "dayTime", i++, world.getDayTime());
                    drawLeftTopString(matrixStack, "gameTime", i++, world.getGameTime());
                    drawLeftTopString(matrixStack, "cloudColor", i++, getColor(world.getCloudColor(mc.getRenderPartialTicks())));
                    if (Minecraft.getInstance().player != null) {
                        ClientPlayerEntity player = Minecraft.getInstance().player;
                        drawLeftTopString(matrixStack, "skyColor", i++, getColor(world.getSkyColor(player.getPosition(), mc.getRenderPartialTicks())));
                    }
                    drawLeftTopString(matrixStack, "starBrightness", i++, getPercentage(world.getStarBrightness(mc.getRenderPartialTicks())));
                    drawLeftTopString(matrixStack, "sunBrightness", i++, getPercentage(world.getSunBrightness(mc.getRenderPartialTicks())));

                    int j = 0;
                    drawRightTopString(matrixStack, "daytime", j++, world.isDaytime());
                    drawRightTopString(matrixStack, "nightTime", j++, world.isNightTime());
                    drawRightTopString(matrixStack, "raining", j++, world.isRaining());
                    drawRightTopString(matrixStack, "thundering", j++, world.isThundering());
                    drawRightTopString(matrixStack, "saveDisabled", j++, world.isSaveDisabled());
                    if (Minecraft.getInstance().player != null) {
                        ClientPlayerEntity player = Minecraft.getInstance().player;
                        drawRightTopString(matrixStack, "areaLoaded", j++, world.isAreaLoaded(player.getPosition(), 1));
                    }
                    drawRightTopString(matrixStack, "debug", j++, world.isDebug());
                }
                break;
            } case WORLD_INFO: {
                if (Minecraft.getInstance().world != null) {
                    ClientWorld.ClientWorldInfo worldInfo = Minecraft.getInstance().world.getWorldInfo();

                    int i = 0;
                    drawLeftTopString(matrixStack, "spawnAngle", i++, worldInfo.getSpawnAngle());
                    drawLeftTopString(matrixStack, "difficulty", i++, worldInfo.getDifficulty());
                    drawLeftTopString(matrixStack, "dayTime", i++, worldInfo.getDayTime());
                    drawLeftTopString(matrixStack, "gameTime", i++, worldInfo.getGameTime());
                    drawLeftTopString(matrixStack, "fogDistance", i++, worldInfo.getFogDistance());
                    drawLeftTopString(matrixStack, "spawnX", i++, worldInfo.getSpawnX());
                    drawLeftTopString(matrixStack, "spawnY", i++, worldInfo.getSpawnY());
                    drawLeftTopString(matrixStack, "spawnZ", i++, worldInfo.getSpawnZ());

                    int j = 0;
                    drawRightTopString(matrixStack, "difficultyLocked", j++, worldInfo.isDifficultyLocked());
                    drawRightTopString(matrixStack, "hardcore", j++, worldInfo.isHardcore());
                    drawRightTopString(matrixStack, "raining", j++, worldInfo.isRaining());
                    drawRightTopString(matrixStack, "thundering", j++, worldInfo.isThundering());
                }
                break;
            } case PLAYER_2: {
                if (Minecraft.getInstance().player != null) {
                    PlayerEntity player = Minecraft.getInstance().player;
                    Team team = player.getTeam();

                    int i = 0;
                    drawLeftTopString(matrixStack, "idleTime", i++, player.getIdleTime());
                    drawLeftTopString(matrixStack, "motion", i++, player.getMotion());
                    drawLeftTopString(matrixStack, "team", i++, (team != null ? team.getName() : ""));
                    drawLeftTopString(matrixStack, "xpSeed", i++, player.getXPSeed());
                    drawLeftTopString(matrixStack, "yOffset", i++, player.getYOffset());

                    int j = 0;
                    drawRightTopString(matrixStack, "glowing", j++, player.isGlowing());
                    drawRightTopString(matrixStack, "invisible", j++, player.isInvisible());
                    drawRightTopString(matrixStack, "onGround", j++, player.isOnGround());
                    drawRightTopString(matrixStack, "onLadder", j++, player.isOnLadder());
                }
                break;
            } case PLAYER_1: {
                if (Minecraft.getInstance().player != null) {
                    PlayerEntity player = Minecraft.getInstance().player;

                    int i = 0;
                    drawLeftTopString(matrixStack, "luck", i++, player.getLuck());
                    drawLeftTopString(matrixStack, "speed", i++, player.getAIMoveSpeed());
                    drawLeftTopString(matrixStack, "score", i++, player.getScore());
                    drawLeftTopString(matrixStack, "totalArmorValue", i++, player.getTotalArmorValue());
                    drawLeftTopString(matrixStack, "health", i++, player.getHealth());
                    drawLeftTopString(matrixStack, "absorptionAmount", i++, player.getAbsorptionAmount());
                    drawLeftTopString(matrixStack, "hunger", i++, player.getFoodStats().getFoodLevel());
                    drawLeftTopString(matrixStack, "saturation", i++, player.getFoodStats().getSaturationLevel());
                    drawLeftTopString(matrixStack, "air", i++, player.getAir());
                    drawLeftTopString(matrixStack, "positionBlock", i++, player.getPosition());
                    drawLeftTopString(matrixStack, "position", i++, player.getPositionVec());
                    drawLeftTopString(matrixStack, "pitchYaw", i++, getDegrees(player.getPitchYaw().x), getDegrees(player.getPitchYaw().y));
                    drawLeftTopString(matrixStack, "sleepTimer", i++, player.getSleepTimer());
                    drawLeftTopString(matrixStack, "fireTimer", i++, player.getFireTimer());
                    drawLeftTopString(matrixStack, "brightness", i++, player.getBrightness());
                    drawLeftTopString(matrixStack, "beeStingCount", i++, player.getBeeStingCount());

                    // String to be scanned to find the pattern.
                    String pattern = "[a-zA-Z0-9_]*";

                    // Create a Pattern object
                    Pattern r = Pattern.compile(pattern);

                    // Now create matcher object.
                    Matcher m = r.matcher(player.getName().getString());

                    int j = 0;
                    drawRightTopString(matrixStack, "legalUsername", j++, m.find());
                    drawRightTopString(matrixStack, "jumping", j++, player.isJumping);
                    drawRightTopString(matrixStack, "sneaking", j++, player.isSneaking());
                    drawRightTopString(matrixStack, "swimming", j++, player.isSwimming());
                    drawRightTopString(matrixStack, "sleeping", j++, player.isSleeping());
                    drawRightTopString(matrixStack, "sprinting", j++, player.isSprinting());
                    drawRightTopString(matrixStack, "silent", j++, player.isSilent());
                    drawRightTopString(matrixStack, "swingInProgress", j++, player.isSwingInProgress);
                    drawRightTopString(matrixStack, "user", j++, player.isUser());
                    drawRightTopString(matrixStack, "alive", j++, player.isAlive());
                    drawRightTopString(matrixStack, "burning", j++, player.isBurning());
                    drawRightTopString(matrixStack, "wet ", j++, player.isWet());
                    drawRightTopString(matrixStack, "creative", j++, player.isCreative());
                    drawRightTopString(matrixStack, "invulnerable", j++, player.isInvulnerable());
                    drawRightTopString(matrixStack, "spectator", j++, player.isSpectator());
                    drawRightTopString(matrixStack, "allowEdit", j++, player.isAllowEdit());

                    int k = 0;
                    {
                        float f = player.rotationPitch;
                        float f1 = player.rotationYaw;

                        Vector3d vec3d = player.getEyePosition(1.0F);

                        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
                        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
                        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
                        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));

                        float f6 = f3 * f4;
                        float f7 = f2 * f4;

                        double d0 = 16; // Todo: test value, if it will lag, then lower the value. ( Possible not needed ;) )

                        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

                        BlockRayTraceResult lookingAt = null;
                        if (Minecraft.getInstance().world != null) {
                            lookingAt = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
                            if (lookingAt.getType() == RayTraceResult.Type.BLOCK) {
                                BlockPos pos = lookingAt.getPos();

                                // now the coordinates you want are in pos. Example of use:
                                BlockState blockState = Minecraft.getInstance().player.getEntityWorld().getBlockState(pos);
                                drawTopString(matrixStack, "-== Block ==-", k++);
                                drawTopString(matrixStack, blockState.getBlock().getTranslatedName().getString(), k++);
                                k++;
                            } else {
                                // not looking at a block, or too far away from one to tell
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 16, 0xff0000);
                            }
                            lookingAt = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
                            if (lookingAt.getType() == RayTraceResult.Type.BLOCK) {
                                BlockPos pos = lookingAt.getPos();

                                // now the coordinates you want are in pos. Example of use:
                                BlockState blockState = Minecraft.getInstance().player.getEntityWorld().getBlockState(pos);
                                FluidState fluidState = blockState.getFluidState();
                                if (!fluidState.isEmpty()) {
                                    drawTopString(matrixStack, "-== Fluid ==-", k++);
                                    drawTopString(matrixStack, blockState.getBlock().getTranslatedName().getString(), k++);
                                    k++;
                                } else {
                                    // not looking at a fluid, or too far away from one to tell
//                                    Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
                                }
                            } else {
                                // not looking at a fluid, or too far away from one to tell
                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
                            }
                        } else {
                            Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<Invalid>", width / 2, height / 2 - 32, 0xbf0000);
                        }
                    }

                    {
                        float f = player.rotationPitch;
                        float f1 = player.rotationYaw;

                        Vector3d vec3d = player.getEyePosition(1.0F);

                        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
                        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
                        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
                        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));

                        float f6 = f3 * f4;
                        float f7 = f2 * f4;

                        double d0 = 16; // Todo: test value, if it will lag, then lower the value. ( Possible not needed ;) )

                        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

                        if (Minecraft.getInstance().world != null) {
                            RayTraceResult raytraceresult = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
                            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                                vec3d1 = raytraceresult.getHitVec();
                            }

                            RayTraceResult rayTraceResult1 = ProjectileHelper.rayTraceEntities(Minecraft.getInstance().world, player, vec3d, vec3d1, player.getBoundingBox().grow(16.0D), entity -> !entity.equals(player));
                            if (rayTraceResult1 != null) {
                                raytraceresult = rayTraceResult1;
                            }
                            if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                                @SuppressWarnings("ConstantConditions") EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) raytraceresult;

                                drawTopString(matrixStack, "-== Entity ==-", k++);
                                drawTopString(matrixStack, I18n.format(entityRayTraceResult.getEntity().getType().getTranslationKey()), k++);
                                k++;
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, I18n.format(entityRayTraceResult.getEntity().getType().getTranslationKey()), width / 2, height / 2 - 48, 0x00bf00);
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
            } default:
                break;
        }
    }

    private static IFormattable getFormatted(String s) {
        return () -> s;
    }

    private static IFormattable getMultiplier(double multiplier) {
        return new Multiplier(multiplier);
    }

    private static IFormattable getSize(int w, int h) {
        return new Size(w, h);
    }

    private static IFormattable getSize(float w, float h) {
        return () -> TextFormatting.GOLD.toString() + w + TextFormatting.GRAY + " x " + TextFormatting.GOLD + h;
    }

    private static IFormattable getPercentage(double value) {
        return new Percentage(value);
    }

    private static Color getColor(Vector3d color) {
        return new Color((float)color.x, (float)color.y, (float)color.z);
    }

    private static Color getColor(int rgb) {
        return new Color(rgb);
    }

    private static IFormattable getAngle(double angle) {
        return new Angle(angle * 360.0d);
    }

    private static IFormattable getRadians(double angle) {
        return new Angle(Math.toDegrees(angle));
    }

    private static IFormattable getDegrees(double angle) {
        return new Angle(angle);
    }

    private static MoonPhase getMoonPhase(int index) {
        return MoonPhase.fromIndex(index);
    }

    private static String format(String text, Object obj, Object... objects) {
        StringBuilder sb = new StringBuilder();

//        sb.append(TextFormatting.DARK_AQUA).append(text);
         sb.append(TextFormatting.GRAY).append(text);
        sb.append(TextFormatting.GRAY).append(": ");
        sb.append(format(obj));

        for (Object object : objects) {
            sb.append(TextFormatting.GRAY).append(", ");
            sb.append(format(object));
        }

        return sb.toString();
    }

    private static String format(Object obj) {
        StringBuilder sb = new StringBuilder();

        if (obj == null) {
            sb.append(TextFormatting.LIGHT_PURPLE);
            sb.append("null");
        } else if (obj instanceof String) {
            sb.append(TextFormatting.GOLD);
            sb.append("\"");
            sb.append(obj.toString()
                    .replaceAll("\\\\", TextFormatting.WHITE + "\\\\" + TextFormatting.GOLD)
                    .replaceAll("\n", TextFormatting.WHITE + "\\n" + TextFormatting.GOLD)
                    .replaceAll("\r", TextFormatting.WHITE + "\\r" + TextFormatting.GOLD)
                    .replaceAll("\t", TextFormatting.WHITE + "\\t" + TextFormatting.GOLD)
                    .replaceAll("\b", TextFormatting.WHITE + "\\b" + TextFormatting.GOLD)
                    .replaceAll("\f", TextFormatting.WHITE + "\\f" + TextFormatting.GOLD));
            sb.append("\"");
        } else if (obj instanceof Character) {
            sb.append(TextFormatting.GOLD);
            sb.append("'");
            if (obj.equals('\\')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\\\");
            } else if (obj.equals('\n')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\n");
            } else if (obj.equals('\r')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\r");
            } else if (obj.equals('\t')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\t");
            } else if (obj.equals('\b')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\b");
            } else if (obj.equals('\f')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\f");
            } else {
                sb.append(obj.toString());
            }
            sb.append(TextFormatting.GOLD);
            sb.append("'");
        } else if (obj instanceof Integer) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
        } else if (obj instanceof Short) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("s");
        } else if (obj instanceof Byte) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("b");
        } else if (obj instanceof Long) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("L");
        } else if (obj instanceof Float) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("f");
        } else if (obj instanceof Double) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("d");
        } else if (obj instanceof Boolean) {
            sb.append(TextFormatting.LIGHT_PURPLE);
            sb.append(obj.toString());
        } else if (obj instanceof Enum<?>) {
            Enum<?> e = (Enum<?>) obj;

            sb.append(TextFormatting.GREEN).append(e);
        } else if (obj instanceof List) {
            sb.append(TextFormatting.GRAY);
            sb.append("[");

            List<?> list = (List<?>) obj;

            Iterator<?> it = list.iterator();
            if (! it.hasNext()) {
                sb.append("]");
                return sb.toString();
            }

            for (;;) {
                Object e = it.next();
                sb.append(e == list ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this List" + TextFormatting.GRAY + ")") : format(e));
                if (! it.hasNext()) {
                    return sb.append(TextFormatting.GRAY).append(']').toString();
                }
                sb.append(TextFormatting.GRAY).append(',').append(' ');
            }
        } else if (obj instanceof Set<?>) {
            sb.append(TextFormatting.GRAY);
            sb.append("{");

            Set<?> set = (Set<?>) obj;

            Iterator<?> it = set.iterator();
            if (! it.hasNext()) {
                sb.append("}");
                return sb.toString();
            }

            for (;;) {
                Object e = it.next();
                sb.append(e == set ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Set" + TextFormatting.GRAY + ")") : format(e));
                if (! it.hasNext()) {
                    return sb.append(TextFormatting.GRAY).append('}').toString();
                }
                sb.append(TextFormatting.GRAY).append(',').append(' ');
            }
        } else if (obj instanceof Map<?, ?>) {
            sb.append(TextFormatting.GRAY);
            sb.append("{");

            Map<?, ?> map = (Map<?, ?>) obj;

            Iterator<? extends Map.Entry<?, ?>> it = map.entrySet().iterator();
            if (! it.hasNext()) {
                sb.append("}");
                return sb.toString();
            }

            for (;;) {
                Map.Entry<?, ?> e = it.next();
                sb.append(e.getKey() == map ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Map" + TextFormatting.GRAY + ")") : format(e.getKey()));
                sb.append(TextFormatting.GRAY).append(": ");
                sb.append(e.getValue() == map ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Map" + TextFormatting.GRAY + ")") : format(e.getValue()));
                if (! it.hasNext()) {
                    return sb.append(TextFormatting.GRAY).append('}').toString();
                }
                sb.append(TextFormatting.GRAY).append(", ");
            }
        } else if (obj instanceof Map.Entry<?, ?>) {
            sb.append(TextFormatting.GRAY);

            Map.Entry<?, ?> e = (Map.Entry<?, ?>) obj;

            sb.append(e.getKey() == e ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Entry" + TextFormatting.GRAY + ")") : format(e.getKey()));
            sb.append(TextFormatting.GRAY).append(": ");
            sb.append(e.getValue() == e ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Entry" + TextFormatting.GRAY + ")") : format(e.getValue()));
            sb.append(TextFormatting.GRAY).append(", ");
        } else if (obj instanceof Vector2f) {
            Vector2f v = (Vector2f) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(v.x));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(v.y));
        } else if (obj instanceof Vector3f) {
            Vector3f v = (Vector3f) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(v.getX()));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(v.getY()));
            sb.append(TextFormatting.GRAY).append(", z: ");
            sb.append(format(v.getZ()));
        } else if (obj instanceof Vector3d) {
            Vector3d v = (Vector3d) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(v.getX()));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(v.getY()));
            sb.append(TextFormatting.GRAY).append(", z: ");
            sb.append(format(v.getZ()));
        } else if (obj instanceof Vector4f) {
            Vector4f v = (Vector4f) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(v.getX()));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(v.getY()));
            sb.append(TextFormatting.GRAY).append(", z: ");
            sb.append(format(v.getZ()));
            sb.append(TextFormatting.GRAY).append(", w: ");
            sb.append(format(v.getW()));
        } else if (obj instanceof BlockPos) {
            BlockPos v = (BlockPos) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(Math.round(v.getX())));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(Math.round(v.getY())));
            sb.append(TextFormatting.GRAY).append(", z: ");
            sb.append(format(Math.round(v.getZ())));
        } else if (obj instanceof Color) {
            Color c = (Color) obj;
            sb.append(TextFormatting.GRAY).append("#");
            sb.append(TextFormatting.BLUE);
            String s = Integer.toHexString(c.getRGB());
            for (int i = 0; i < 8 - s.length(); i++) {
                sb.append("0");
            }

            sb.append(s);
        } else if (obj instanceof ITextComponent) {
            ITextComponent e = (ITextComponent) obj;
            sb.append(TextFormatting.GRAY).append("ITextComponent: ");
            sb.append(format(e.getString()));
        } else if (obj instanceof ItemStack) {
            ItemStack e = (ItemStack) obj;
            sb.append(TextFormatting.BLUE).append(e.getItem().getRegistryName()).append(" ");
            sb.append(TextFormatting.GRAY).append(e.getCount()).append("x");
        } else if (obj instanceof IFormattable) {
            IFormattable e = (IFormattable) obj;
            sb.append(e.toFormattedString());
        } else if (obj instanceof IForgeRegistryEntry<?>) {
            IForgeRegistryEntry<?> ifrEntry = (IForgeRegistryEntry<?>) obj;
            sb.append(format(ifrEntry.getRegistryType()));
            sb.append(TextFormatting.GRAY).append("@");
            sb.append(format(ifrEntry.getRegistryName()));
        } else if (obj instanceof IForgeRegistry<?>) {
            IForgeRegistry<?> ifr = (IForgeRegistry<?>) obj;
            sb.append(format(ifr.getRegistryName()));
        } else if (obj instanceof ResourceLocation) {
            ResourceLocation rl = (ResourceLocation) obj;
            sb.append(TextFormatting.GOLD).append(rl.getNamespace());
            sb.append(TextFormatting.GRAY).append(":");
            sb.append(TextFormatting.YELLOW).append(rl.getPath());
        } else if (obj instanceof PlayerEntity) {
            Entity e = (Entity) obj;
            sb.append(TextFormatting.GRAY).append("Player: ");
            sb.append(format(e.getName().getString()));
        } else if (obj instanceof Entity) {
            Entity e = (Entity) obj;
            sb.append(TextFormatting.GRAY).append("Entity: ");
            sb.append(format(e.getUniqueID()));
        } else if (obj instanceof UUID) {
            UUID uuid = (UUID) obj;
            sb.append(TextFormatting.GOLD).append(uuid.toString().replaceAll("-", TextFormatting.GRAY + "-" + TextFormatting.GOLD));
        } else if (obj instanceof Collection<?>) {
            sb.append(TextFormatting.GRAY);
            sb.append("(");

            Collection<?> collection = (Collection<?>) obj;

            Iterator<?> it = collection.iterator();
            if (! it.hasNext()) {
                sb.append(")");
                return sb.toString();
            }

            for (;;) {
                Object e = it.next();
                sb.append(e == collection ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Collection" + TextFormatting.GRAY + ")") : format(e));
                if (! it.hasNext()) {
                    return sb.append(TextFormatting.GRAY).append(')').toString();
                }
                sb.append(TextFormatting.GRAY).append(',').append(' ');
            }
        } else if (obj instanceof Class<?>) {
            Class<?> c = (Class<?>) obj;

            sb.append(TextFormatting.AQUA);
            sb.append(c.getPackage().getName().replaceAll("\\.", TextFormatting.GRAY + "." + TextFormatting.AQUA));
            sb.append(TextFormatting.GRAY).append(".").append(TextFormatting.AQUA);
            sb.append(TextFormatting.DARK_AQUA);
            sb.append(c.getSimpleName());
        } else {
            Class<?> c = obj.getClass();
            sb.append(TextFormatting.AQUA);
            sb.append(c.getPackage().getName().replaceAll("\\.", TextFormatting.GRAY + "." + TextFormatting.AQUA));
            sb.append(TextFormatting.GRAY).append(".").append(TextFormatting.AQUA);
            sb.append(TextFormatting.DARK_AQUA);
            sb.append(c.getSimpleName());
            sb.append(TextFormatting.GRAY).append("@").append(TextFormatting.YELLOW);
            sb.append(Integer.toHexString(obj.hashCode()));
        }
        return sb.toString();
    }

    private static void drawTopString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width / 2f - fontRenderer.getStringWidth(text) / 2f,  12f + (line * 12), 0xffffff);
    }

    private static void drawLeftTopString(MatrixStack matrixStack, String text, int line, Object obj, Object... objects) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

        text = format(text, obj, objects);

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, 12f,  12f + (line * 12), 0xffffff);
    }

    private static void drawRightTopString(MatrixStack matrixStack, String text, int line, Object obj, Object... objects) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        text = format(text, obj, objects);

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width - 12 - fontRenderer.getStringWidth(text), 12f + (line * 12), 0xffffff);
    }

    @SuppressWarnings("unused")
    private static void drawBottomString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width / 2f - fontRenderer.getStringWidth(text) / 2f,  height - 29f - (line * 12), 0xffffff);
    }

    @SuppressWarnings("unused")
    private static void drawLeftBottomString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, 12, height - 29f - (line * 12), 0xffffff);
    }

    @SuppressWarnings("unused")
    private static void drawRightBottomString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width - 12 - fontRenderer.getStringWidth(text), height - 29f - (line * 12), 0xffffff);
    }

    @SuppressWarnings({"unused", "SameParameterValue"})
    private static void drawRightString(MatrixStack matrixStack, String text, float mx, float y, int color) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width - mx - fontRenderer.getStringWidth(text), y, color);
    }
}
