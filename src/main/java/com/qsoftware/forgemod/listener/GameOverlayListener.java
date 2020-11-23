package com.qsoftware.forgemod.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.Sliceable;
import com.qsoftware.forgemod.keybinds.KeyBindingList;
import com.qsoftware.silent.lib.client.key.InputUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Monitor;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.codehaus.plexus.util.Os;
import org.jline.utils.OSUtils;
import org.lwjgl.glfw.GLFW;
import sun.awt.OSInfo;
import sun.misc.OSEnvironment;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Client listener
 *
 * @author (partial) CoFH - https://github.com/CoFH
 */
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameOverlayListener {
    public static PAGE DEBUG_PAGE = PAGE.NONE;
    
    public enum PAGE {
        NONE,
        PLAYER_1,
        PLAYER_2,
        ITEM,
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
                    if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
                        if (next < 1) {
                            next = values.length - 1;
                        }
                    } else {
                        if (next < 0) {
                            next = values.length - 1;
                        }
                    }

                    DEBUG_PAGE = values[next];
                    return;
                }

                int next = DEBUG_PAGE.ordinal() + 1;

                PAGE[] values = PAGE.values();
                if (next >= values.length) {
                    if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
                        next = 1;
                    } else {
                        next = 0;
                    }
                }

                DEBUG_PAGE = values[next];
            }
        }
    }

    @SuppressWarnings("UnusedAssignment")
    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;

        boolean debug = false;

//		System.out.println("Type: " + event.getType());

        if (Minecraft.getInstance().gameSettings.showDebugInfo) {
            return;
        }

        MatrixStack matrixStack = event.getMatrixStack();
        Minecraft mc = Minecraft.getInstance();
        MainWindow mainWindow = mc.getMainWindow();
        Monitor monitor = mainWindow.getMonitor();
        FontRenderer fontRenderer = mc.fontRenderer;

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

        if (DEBUG_PAGE != PAGE.NONE) {
            mc.fontRenderer.drawStringWithShadow(matrixStack, "debugPage: " + DEBUG_PAGE.name(), 12f, height - 16f, 0xffaa00);
        }

        switch (DEBUG_PAGE) {
            case WINDOW: {
                int i = 0;
                drawLeftTopString(matrixStack, "guiScaleFactor: " + mainWindow.getGuiScaleFactor() + "x", i++);
                drawLeftTopString(matrixStack, "windowSizeScaled: " + mainWindow.getScaledWidth() + "x" + mainWindow.getScaledHeight(), i++);
                drawLeftTopString(matrixStack, "windowSize: " + mainWindow.getWidth() + "x" + mainWindow.getHeight(), 2);
                drawLeftTopString(matrixStack, "windowHandle: " + mainWindow.getHandle(), i++);
                drawLeftTopString(matrixStack, "framebufferSize: " + mainWindow.getFramebufferWidth() + "x" + mainWindow.getFramebufferHeight(), i++);
                drawLeftTopString(matrixStack, "refreshTate: " + mainWindow.getRefreshRate(), i++);
                drawLeftTopString(matrixStack, "framerateLimit: " + mainWindow.getLimitFramerate(), i++);
                
                int j = 0;
                drawRightTopString(matrixStack, "fullscreenMode: " + (mainWindow.isFullscreen() ? "on" : "off"), j++);
                break;
            }
            case COMPUTER: {
                int i = 0;
                if (monitor != null) {
                    drawLeftTopString(matrixStack, "screenSize: " + dw + "x" + dh, i++);
                }
                drawLeftTopString(matrixStack, "osType: " + OSInfo.getOSType().name(), i++);
                if (OSInfo.getWindowsVersion() != null) {
                    try {
                        drawLeftTopString(matrixStack, "windowsVersion: " + OSInfo.getWindowsVersion().toString(), i++);
                    } catch (SecurityException ignored) {

                    }
                }
                try {
                    drawLeftTopString(matrixStack, "osVersion: " + System.getProperty("os.version"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "osName: " + System.getProperty("os.name"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "osArch: " + System.getProperty("os.arch"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVersion: " + System.getProperty("java.version"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVendor: " + System.getProperty("java.vendor"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVmVersion: " + System.getProperty("java.vm.version"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVmVendor: " + System.getProperty("java.vm.vendor"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaVmVendor: " + System.getProperty("java.vm.name"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaClassVersion: " + System.getProperty("java.class.version"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }
                try {
                    drawLeftTopString(matrixStack, "javaCompiler: " + System.getProperty("java.compiler"), i++);
                } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

                }

                i = 0;
                drawRightTopString(matrixStack, "isJava64bit: " + (mc.isJava64bit() ? "yes" : "no"), i++);
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

                    drawLeftTopString(matrixStack, "registryName: " + stack.getItem().getRegistryName(), i++);
                    if (food != null) {
                        drawLeftTopString(matrixStack, "foodHealing: " + food.getHealing(), i++);
                        drawLeftTopString(matrixStack, "foodSaturation: " + food.getSaturation(), i++);
                    }
                    if (group != null) {
                        drawLeftTopString(matrixStack, "groupName: " + group.getGroupName().getString(), i++);
                    }
                    drawLeftTopString(matrixStack, "rarity: " + Objects.requireNonNull(item.getRarity(stack).name(), "null"), i++);
                    drawLeftTopString(matrixStack, "enchantability: " + item.getItemEnchantability(stack), i++);
                    drawLeftTopString(matrixStack, "stackLimit: " + item.getItemStackLimit(stack), i++);
                    drawLeftTopString(matrixStack, "maxDamage: " + item.getMaxDamage(stack), i++);
                    drawLeftTopString(matrixStack, "damage: " + item.getDamage(stack), i++);
                    drawLeftTopString(matrixStack, "burnTime: " + item.getBurnTime(stack), i++);
                    drawLeftTopString(matrixStack, "count: " + stack.getCount(), i++);
                    drawLeftTopString(matrixStack, "repairCost: " + stack.getRepairCost(), i++);
                    drawLeftTopString(matrixStack, "useDuration: " + stack.getUseDuration(), i++);
                    drawLeftTopString(matrixStack, "xpRepairRation: " + stack.getXpRepairRatio(), i++);

                    int j = 0;
                    drawRightTopString(matrixStack, "complex: " + item.isComplex(), j++);
                    drawRightTopString(matrixStack, "immuneToFire: " + item.isImmuneToFire(), j++);
                    drawRightTopString(matrixStack, "enchantable: " + item.isEnchantable(stack), j++);
                    drawRightTopString(matrixStack, "empty: " + stack.isEmpty(), j++);
                    drawRightTopString(matrixStack, "isPiglinCurrency: " + stack.isPiglinCurrency(), j++);
                    drawRightTopString(matrixStack, "repairable: " + stack.isRepairable(), j++);
                    drawRightTopString(matrixStack, "stackable: " + stack.isStackable(), j++);
                    drawRightTopString(matrixStack, "sliceable: " + (item instanceof Sliceable), j++);
                    drawRightTopString(matrixStack, "damageable: " + item.isDamageable(), j++);
                    drawRightTopString(matrixStack, "damaged: " + item.isDamaged(stack), j++);
                }
                break;
            } case MINECRAFT: {
                int i = 0;
                drawLeftTopString(matrixStack, "version: " + mc.getVersion(), i++);
                drawLeftTopString(matrixStack, "versionType: " + mc.getVersionType(), i++);
                drawLeftTopString(matrixStack, "name: " + mc.getName(), i++);
                drawLeftTopString(matrixStack, "forceUnicodeFont: " + mc.getForceUnicodeFont(), i++);

                int j = 0;
                drawRightTopString(matrixStack, "demoMode: " + (mc.isDemo() ? "on" : "off"), j++);
                drawRightTopString(matrixStack, "isChatEnabled: " + (mc.isChatEnabled() ? "yes" : "no"), j++);
                drawRightTopString(matrixStack, "isFocused: " + (mc.isGameFocused() ? "yes" : "no"), j++);
                drawRightTopString(matrixStack, "isGamePaused: " + (mc.isGamePaused() ? "yes" : "no"), j++);
                drawRightTopString(matrixStack, "isIntegratedServerRunning: " + (mc.isIntegratedServerRunning() ? "yes" : "no"), j++);
                break;
            } case WORLD: {
                if (Minecraft.getInstance().world != null) {
                    ClientWorld world = Minecraft.getInstance().world;

                    int i = 0;
                    drawLeftTopString(matrixStack, "timeLightningFlash: " + world.getTimeLightningFlash(), i++);
                    drawLeftTopString(matrixStack, "providerName: " + world.getProviderName(), i++);
                    drawLeftTopString(matrixStack, "loadedEntities: " + world.getCountLoadedEntities(), i++);
                    drawLeftTopString(matrixStack, "nextMapId: " + world.getNextMapId(), i++);
                    drawLeftTopString(matrixStack, "difficulty: " + world.getDifficulty().getDisplayName().getString(), i++);
                    drawLeftTopString(matrixStack, "seaLevel: " + world.getSeaLevel(), i++);
                    drawLeftTopString(matrixStack, "moonPhase: " + world.getMoonPhase(), i++);
                    drawLeftTopString(matrixStack, "spawnAngle: " + world.getWorldInfo().getSpawnAngle(), i++);
                    drawLeftTopString(matrixStack, "dimensionLocation: " + world.getDimensionKey().getLocation(), i++);
                    drawLeftTopString(matrixStack, "dayTime: " + world.getDayTime(), i++);
                    drawLeftTopString(matrixStack, "gameTime: " + world.getGameTime(), i++);
                    drawLeftTopString(matrixStack, "cloudColor: " + world.getCloudColor(mc.getRenderPartialTicks()), i++);
                    if (Minecraft.getInstance().player != null) {
                        ClientPlayerEntity player = Minecraft.getInstance().player;
                        drawLeftTopString(matrixStack, "skyColor: " + world.getSkyColor(player.getPosition(), mc.getRenderPartialTicks()), i++);
                    }
                    drawLeftTopString(matrixStack, "starBrightness: " + world.getStarBrightness(mc.getRenderPartialTicks()), i++);
                    drawLeftTopString(matrixStack, "sunBrightness: " + world.getSunBrightness(mc.getRenderPartialTicks()), i++);

                    int j = 0;
                    drawRightTopString(matrixStack, "isDaytime: " + (world.isDaytime() ? "yes" : "no"), j++);
                    drawRightTopString(matrixStack, "isNightTime: " + (world.isNightTime() ? "yes" : "no"), j++);
                    drawRightTopString(matrixStack, "isRaining: " + (world.isRaining() ? "yes" : "no"), j++);
                    drawRightTopString(matrixStack, "isThundering: " + (world.isThundering() ? "yes" : "no"), j++);
                    drawRightTopString(matrixStack, "isSaveDisabled: " + (world.isSaveDisabled() ? "yes" : "no"), j++);
                    if (Minecraft.getInstance().player != null) {
                        ClientPlayerEntity player = Minecraft.getInstance().player;
                        drawRightTopString(matrixStack, "isAreaLoaded: " + (world.isAreaLoaded(player.getPosition(), 1) ? "on" : "off"), j++);
                    }
                    drawRightTopString(matrixStack, "debug: " + (world.isDebug() ? "on" : "off"), j++);
                }
                break;
            } case WORLD_INFO: {
                if (Minecraft.getInstance().world != null) {
                    ClientWorld.ClientWorldInfo worldInfo = Minecraft.getInstance().world.getWorldInfo();

                    int i = 0;
                    drawLeftTopString(matrixStack, "spawnAngle: " + worldInfo.getSpawnAngle(), i++);
                    drawLeftTopString(matrixStack, "difficulty: " + worldInfo.getDifficulty().name(), i++);
                    drawLeftTopString(matrixStack, "dayTime: " + worldInfo.getDayTime(), i++);
                    drawLeftTopString(matrixStack, "gameTime: " + worldInfo.getGameTime(), i++);
                    drawLeftTopString(matrixStack, "fogDistance: " + worldInfo.getFogDistance(), i++);
                    drawLeftTopString(matrixStack, "spawnX: " + worldInfo.getSpawnX(), i++);
                    drawLeftTopString(matrixStack, "spawnY: " + worldInfo.getSpawnY(), i++);
                    drawLeftTopString(matrixStack, "spawnZ: " + worldInfo.getSpawnZ(), i++);

                    int j = 0;
                    drawRightTopString(matrixStack, "isDifficultyLocked " + (worldInfo.isDifficultyLocked() ? "yes" : "no"), j++);
                    drawRightTopString(matrixStack, "isHardcore " + (worldInfo.isHardcore() ? "yes" : "no"), j++);
                    drawRightTopString(matrixStack, "isRaining: " + (worldInfo.isRaining() ? "yes" : "no"), j++);
                    drawRightTopString(matrixStack, "isThundering: " + (worldInfo.isThundering() ? "yes" : "no"), j++);
                }
                break;
            } case PLAYER_2: {
                if (Minecraft.getInstance().player != null) {
                    PlayerEntity player = Minecraft.getInstance().player;
                    Team team = player.getTeam();

                    int i = 0;
                    drawLeftTopString(matrixStack, "idleTime: " + player.getIdleTime(), i++);
                    drawLeftTopString(matrixStack, "motion: " + player.getMotion(), i++);
                    drawLeftTopString(matrixStack, "team: " + (team != null ? team.getName() : ""), i++);
                    drawLeftTopString(matrixStack, "xpSeed: " + player.getXPSeed(), i++);
                    drawLeftTopString(matrixStack, "yOffset: " + player.getYOffset(), i++);

                    int j = 0;
                    drawRightTopString(matrixStack, "glowing: " + player.isGlowing(), j++);
                    drawRightTopString(matrixStack, "invisible: " + player.isInvisible(), j++);
                    drawRightTopString(matrixStack, "onGround: " + player.isOnGround(), j++);
                    drawRightTopString(matrixStack, "onLadder: " + player.isOnLadder(), j++);
                }
                break;
            } case PLAYER_1: {
                if (Minecraft.getInstance().player != null) {
                    PlayerEntity player = Minecraft.getInstance().player;

                    int i = 0;
                    drawLeftTopString(matrixStack, "luck: " + player.getLuck(), i++);
                    drawLeftTopString(matrixStack, "speed: " + player.getAIMoveSpeed(), i++);
                    drawLeftTopString(matrixStack, "score: " + player.getScore(), i++);
                    drawLeftTopString(matrixStack, "totalArmorValue: " + player.getTotalArmorValue(), i++);
                    drawLeftTopString(matrixStack, "health: " + player.getHealth(), i++);
                    drawLeftTopString(matrixStack, "absorptionAmount: " + player.getAbsorptionAmount(), i++);
                    drawLeftTopString(matrixStack, "hunger: " + player.getFoodStats().getFoodLevel(), i++);
                    drawLeftTopString(matrixStack, "saturation: " + player.getFoodStats().getSaturationLevel(), i++);
                    drawLeftTopString(matrixStack, "air: " + player.getAir(), i++);
                    drawLeftTopString(matrixStack, "positionBlock: " + player.getPosition().getCoordinatesAsString(), i++);
                    drawLeftTopString(matrixStack, "position: " + player.getPositionVec().toString(), i++);
                    drawLeftTopString(matrixStack, "pitchYaw: " + player.getPitchYaw().x + ", " + player.getPitchYaw().y, i++);
                    drawLeftTopString(matrixStack, "sleepTimer: " + player.getSleepTimer(), i++);
                    drawLeftTopString(matrixStack, "fireTimer: " + player.getFireTimer(), i++);
                    drawLeftTopString(matrixStack, "brightness: " + player.getBrightness(), i++);
                    drawLeftTopString(matrixStack, "beeStingCount: " + player.getBeeStingCount(), i++);

                    // String to be scanned to find the pattern.
                    String pattern = "[a-zA-Z0-9_]*";

                    // Create a Pattern object
                    Pattern r = Pattern.compile(pattern);

                    // Now create matcher object.
                    Matcher m = r.matcher(player.getName().getString());

                    int j = 0;
                    drawRightTopString(matrixStack, "legalUsername: " + m.find(), j++);
                    drawRightTopString(matrixStack, "isJumping: " + player.isJumping, j++);
                    drawRightTopString(matrixStack, "isSneaking: " + player.isSneaking(), j++);
                    drawRightTopString(matrixStack, "isSwimming: " + player.isSwimming(), j++);
                    drawRightTopString(matrixStack, "isSleeping: " + player.isSleeping(), j++);
                    drawRightTopString(matrixStack, "isSprinting: " + player.isSprinting(), j++);
                    drawRightTopString(matrixStack, "isSilent: " + player.isSilent(), j++);
                    drawRightTopString(matrixStack, "isSwingInProgress: " + player.isSwingInProgress, j++);
                    drawRightTopString(matrixStack, "isUser: " + player.isUser(), j++);
                    drawRightTopString(matrixStack, "isAlive: " + player.isAlive(), j++);
                    drawRightTopString(matrixStack, "isBurning " + player.isBurning(), j++);
                    drawRightTopString(matrixStack, "isWet " + player.isWet(), j++);
                    drawRightTopString(matrixStack, "creative: " + player.isCreative(), j++);
                    drawRightTopString(matrixStack, "invulnerable: " + player.isInvulnerable(), j++);
                    drawRightTopString(matrixStack, "spectator: " + player.isSpectator(), j++);
                    drawRightTopString(matrixStack, "allowEdit: " + player.isAllowEdit(), j++);

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

                            RayTraceResult raytraceresult1 = ProjectileHelper.rayTraceEntities(Minecraft.getInstance().world, player, vec3d, vec3d1, player.getBoundingBox().grow(16.0D), entity -> !entity.equals(player));
                            if (raytraceresult1 != null) {
                                raytraceresult = raytraceresult1;
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

    private static void drawTopString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width / 2f - fontRenderer.getStringWidth(text) / 2f,  12f + (line * 12), 0xffffff);
    }
    
    private static void drawLeftTopString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, 12f,  12f + (line * 12), 0xffffff);
    }

    private static void drawRightTopString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width - 12 - fontRenderer.getStringWidth(text), 12f + (line * 12), 0xffffff);
    }

    private static void drawBottomString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width / 2f - fontRenderer.getStringWidth(text) / 2f,  height - 29f - (line * 12), 0xffffff);
    }

    private static void drawLeftBottomString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, 12, height - 29f - (line * 12), 0xffffff);
    }

    private static void drawRightBottomString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width - 12 - fontRenderer.getStringWidth(text), height - 29f - (line * 12), 0xffffff);
    }

    private static void drawRightString(MatrixStack matrixStack, String text, float mx, float y, int color) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width - mx - fontRenderer.getStringWidth(text), y, color);
    }
}
