package com.qsoftware.forgemod.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.QForgeMod;
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
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import sun.awt.OSInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Client listener
 *
 * @author (partial) CoFH - https://github.com/CoFH
 */
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameOverlayListener {
	public enum PAGE {
		NONE,
		PLAYER_1,
		PLAYER_2,
		WORLD,
		WORLD_INFO,
		MINECRAFT,
		WINDOW,
		COMPUTER,
	}

	public static PAGE DEBUG_PAGE = PAGE.NONE;

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

		int width = mc.getMainWindow().getScaledWidth();
		int height = mc.getMainWindow().getScaledHeight();

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
			case WINDOW:
				mc.fontRenderer.drawStringWithShadow(matrixStack, "guiScaleFactor: " + mainWindow.getGuiScaleFactor() + "x", 12f, 12f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "windowSizeScaled: " + width + "x" + height, 12f, 24f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "windowSize: " + mainWindow.getWidth() + "x" + mainWindow.getHeight(), 12f, 36f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "framebufferSize: " + mainWindow.getFramebufferWidth() + "x" + mainWindow.getFramebufferHeight(), 12f, 48f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "windowHandle: " + mainWindow.getHandle(), 12f, 60f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "refreshTate: " + mainWindow.getRefreshRate(), 12f, 72f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "framerateLimit: " + mainWindow.getLimitFramerate(), 12f, 84f, 0xffaa00);
				drawRightString(matrixStack, "fullscreenMode: " + (mainWindow.isFullscreen() ? "on" : "off"), 12f, 12f, 0xffaa00);
				break;
			case COMPUTER:
				mc.fontRenderer.drawStringWithShadow(matrixStack, "screenSize: " + dw + "x" + dh, 12f, 12f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "osType: " + OSInfo.getOSType().name(), 12f, 24f, 0xffaa00);
				drawRightString(matrixStack, "isJava64bit: " + (mc.isJava64bit() ? "on" : "off"), 12f, 12f, 0xffaa00);
				break;
			case MINECRAFT:
				mc.fontRenderer.drawStringWithShadow(matrixStack, "version: " + mc.getVersion(), 12f, 12f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "versionType: " + mc.getVersionType(), 12f, 24f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "name: " + mc.getName(), 12f, 36f, 0xffaa00);
				mc.fontRenderer.drawStringWithShadow(matrixStack, "forceUnicodeFont: " + mc.getForceUnicodeFont(), 12f, 48f, 0xffaa00);
				drawRightString(matrixStack, "demoMode: " + (mc.isDemo() ? "on" : "off"), 12f, 12f, 0xffaa00);
				drawRightString(matrixStack, "isChatEnabled: " + (mc.isChatEnabled() ? "yes" : "no"), 12f, 24f, 0xffaa00);
				drawRightString(matrixStack, "isFocused: " + (mc.isGameFocused() ? "yes" : "no"), 12f, 36f, 0xffaa00);
				drawRightString(matrixStack, "isGamePaused: " + (mc.isGamePaused() ? "yes" : "no"), 12f, 48f, 0xffaa00);
				drawRightString(matrixStack, "isIntegratedServerRunning: " + (mc.isIntegratedServerRunning() ? "yes" : "no"), 12f, 60f, 0xffaa00);
				break;
			case WORLD:
				if (Minecraft.getInstance().world != null) {
					ClientWorld world = Minecraft.getInstance().world;


					mc.fontRenderer.drawStringWithShadow(matrixStack, "timeLightningFlash: " + world.getTimeLightningFlash(), 12f, 12f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "providerName: " + world.getProviderName(), 12f, 24f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "loadedEntities: " + world.getCountLoadedEntities(), 12f, 36f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "nextMapId: " + world.getNextMapId(), 12f, 48f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "dayTime: " + world.getDayTime(), 12f, 60f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "gameTime: " + world.getGameTime(), 12f, 72f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "difficulty: " + world.getDifficulty().getDisplayName().getString(), 12f, 84f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "dimensionLocation: " + world.getDimensionKey().getLocation(), 12f, 96f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "dimensionRegistryName: " + world.getDimensionKey().getRegistryName(), 12f, 108f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "seaLevel: " + world.getSeaLevel(), 12f, 120f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "moonPhase: " + world.getMoonPhase(), 12f, 132f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "cloudColor: " + world.getCloudColor(mc.getRenderPartialTicks()), 12f, 144f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "starBrightness: " + world.getStarBrightness(mc.getRenderPartialTicks()), 12f, 156f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "sunBrightness: " + world.getSunBrightness(mc.getRenderPartialTicks()), 12f, 168f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "spawnAngle: " + world.getWorldInfo().getSpawnAngle(), 12f, 180f, 0xffaa00);
					drawRightString(matrixStack, "isDaytime: " + (world.isDaytime() ? "yes" : "no"), 12f, 12f, 0xffaa00);
					drawRightString(matrixStack, "isNightTime: " + (world.isNightTime() ? "yes" : "no"), 12f, 24f, 0xffaa00);
					drawRightString(matrixStack, "isRaining: " + (world.isRaining() ? "yes" : "no"), 12f, 36f, 0xffaa00);
					drawRightString(matrixStack, "isThundering: " + (world.isThundering() ? "yes" : "no"), 12f, 48f, 0xffaa00);
					drawRightString(matrixStack, "isSaveDisabled: " + (world.isSaveDisabled() ? "yes" : "no"), 12f, 60f, 0xffaa00);
					drawRightString(matrixStack, "debug: " + (world.isDebug() ? "on" : "off"), 12f, 72f, 0xffaa00);

					if (Minecraft.getInstance().player != null) {
						ClientPlayerEntity player = Minecraft.getInstance().player;

						drawRightString(matrixStack, "skyColor: " + world.getSkyColor(player.getPosition(), mc.getRenderPartialTicks()), 12f, height - 30f, 0xffaa00);
						drawRightString(matrixStack, "isAreaLoaded: " + (world.isAreaLoaded(player.getPosition(), 1) ? "on" : "off"), 12f, height - 18f, 0xffaa00);
					}
				}
				break;
			case WORLD_INFO:
				if (Minecraft.getInstance().world != null) {
					ClientWorld.ClientWorldInfo worldInfo = Minecraft.getInstance().world.getWorldInfo();

					mc.fontRenderer.drawStringWithShadow(matrixStack, "spawnAngle: " + worldInfo.getSpawnAngle(), 12f, 12f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "difficulty: " + worldInfo.getDifficulty().name(), 12f, 24f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "dayTime: " + worldInfo.getDayTime(), 12f, 36f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "gameTime: " + worldInfo.getGameTime(), 12f, 48f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "fogDistance: " + worldInfo.getFogDistance(), 12f, 60f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "spawnX: " + worldInfo.getSpawnX(), 12f, 72f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "spawnY: " + worldInfo.getSpawnY(), 12f, 84f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "spawnZ: " + worldInfo.getSpawnZ(), 12f, 96f, 0xffaa00);
					drawRightString(matrixStack, "isDifficultyLocked " + (worldInfo.isDifficultyLocked() ? "yes" : "no"), 12f, 12f, 0xffaa00);
					drawRightString(matrixStack, "isHardcore " + (worldInfo.isHardcore() ? "yes" : "no"), 12f, 24f, 0xffaa00);
					drawRightString(matrixStack, "isRaining: " + (worldInfo.isRaining() ? "yes" : "no"), 12f, 36f, 0xffaa00);
					drawRightString(matrixStack, "isThundering: " + (worldInfo.isThundering() ? "yes" : "no"), 12f, 48f, 0xffaa00);
				}
				break;
			case PLAYER_2:
				if (Minecraft.getInstance().player != null) {
					PlayerEntity player = Minecraft.getInstance().player;
					Team team = player.getTeam();

					mc.fontRenderer.drawStringWithShadow(matrixStack, "idleTime: " + player.getIdleTime(), 12f, 12f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "motion: " + player.getMotion(), 12f, 24f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "team: " + (team != null ? team.getName() : ""), 12f, 36f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "xpSeed: " + player.getXPSeed(), 12f, 48f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "yOffset: " + player.getYOffset(), 12f, 60f, 0xffaa00);

					drawRightString(matrixStack, "glowing: " + player.isGlowing(), 12f, 12f, 0xffaa00);
					drawRightString(matrixStack, "invisible: " + player.isInvisible(), 12f, 24f, 0xffaa00);
					drawRightString(matrixStack, "onGround: " + player.isOnGround(), 12f, 36f, 0xffaa00);
					drawRightString(matrixStack, "onLadder: " + player.isOnLadder(), 12f, 48f, 0xffaa00);
				}
				break;
			case PLAYER_1:
				if (Minecraft.getInstance().player != null) {
					PlayerEntity player = Minecraft.getInstance().player;

					mc.fontRenderer.drawStringWithShadow(matrixStack, "luck: " + player.getLuck(), 12f, 12f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "speed: " + player.getAIMoveSpeed(), 12f, 24f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "score: " + player.getScore(), 12f, 36f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "totalArmorValue: " + player.getTotalArmorValue(), 12f, 48f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "health: " + player.getHealth(), 12f, 60f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "absorptionAmount: " + player.getAbsorptionAmount(), 12f, 72f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "hunger: " + player.getFoodStats().getFoodLevel(), 12f, 84f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "saturation: " + player.getFoodStats().getSaturationLevel(), 12f, 96f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "air: " + player.getAir(), 12f, 108f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "positionBlock: " + player.getPosition().getCoordinatesAsString(), 12f, 120f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "position: " + player.getPositionVec().toString(), 12f, 132f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "pitchYaw: " + player.getPitchYaw().x + ", " + player.getPitchYaw().y, 12f, 144f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "sleepTimer: " + player.getSleepTimer(), 12f, 156f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "fireTimer: " + player.getFireTimer(), 12f, 168f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "brightness: " + player.getBrightness(), 12f, 180f, 0xffaa00);
					mc.fontRenderer.drawStringWithShadow(matrixStack, "beeStingCount: " + player.getBeeStingCount(), 12f, 192f, 0xffaa00);

					// String to be scanned to find the pattern.
					String pattern = "[a-zA-Z0-9_]*";

					// Create a Pattern object
					Pattern r = Pattern.compile(pattern);

					// Now create matcher object.
					Matcher m = r.matcher(player.getName().getString());

					if (m.find()) {
						drawRightString(matrixStack, "legalUsername: true", 12f, 12f, 0xffaa00);
					}else {
						drawRightString(matrixStack, "legalUsername: false", 12f, 12f, 0xffaa00);
					}

					drawRightString(matrixStack, "isJumping: " + player.isJumping, 12f, 24f, 0xffaa00);
					drawRightString(matrixStack, "isSneaking: " + player.isSneaking(), 12f, 36f, 0xffaa00);
					drawRightString(matrixStack, "isSwimming: " + player.isSwimming(), 12f, 48f, 0xffaa00);
					drawRightString(matrixStack, "isSleeping: " + player.isSleeping(), 12f, 60f, 0xffaa00);
					drawRightString(matrixStack, "isSprinting: " + player.isSprinting(), 12f, 72f, 0xffaa00);
					drawRightString(matrixStack, "isSilent: " + player.isSilent(), 12f, 84f, 0xffaa00);
					drawRightString(matrixStack, "isSwingInProgress: " + player.isSwingInProgress, 12f, 96f, 0xffaa00);
					drawRightString(matrixStack, "isUser: " + player.isUser(), 12f, 108f, 0xffaa00);
					drawRightString(matrixStack, "isAlive: " + player.isAlive(), 12f, 120f, 0xffaa00);
					drawRightString(matrixStack, "isBurning " + player.isBurning(), 12f, 132f, 0xffaa00);
					drawRightString(matrixStack, "isWet " + player.isWet(), 12f, 144f, 0xffaa00);
					drawRightString(matrixStack, "creative: " + player.isCreative(), 12f, 156f, 0xffaa00);
					drawRightString(matrixStack, "invulnerable: " + player.isInvulnerable(), 12f, 168f, 0xffaa00);
					drawRightString(matrixStack, "spectator: " + player.isSpectator(), 12f, 180f, 0xffaa00);
					drawRightString(matrixStack, "allowEdit: " + player.isAllowEdit(), 12f, 192f, 0xffaa00);

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
								Screen.drawCenteredString(matrixStack, mc.fontRenderer, blockState.getBlock().getTranslatedName(), width / 2, height / 2 - 16, 0xffaa00);
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
									Screen.drawCenteredString(matrixStack, mc.fontRenderer, blockState.getBlock().getTranslatedName(), width / 2, height / 2 - 32, 0x0033ff);
								} else {
									// not looking at a fluid, or too far away from one to tell
									Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
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
								Screen.drawCenteredString(matrixStack, mc.fontRenderer, I18n.format(entityRayTraceResult.getEntity().getType().getTranslationKey()), width / 2, height / 2 - 48, 0x00bf00);
							} else {
								// not looking at a block, or too far away from one to tell
								Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 48, 0xff0000);
							}
						}
					}
				} else {
					Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<Invalid>", width / 2, height / 2 - 16, 0xbf0000);
				}
				break;
			default:
				break;
		}
	}

	private static void drawRightString(MatrixStack matrixStack, String text, float mx, float y, int color) {
		// Declare local variables before draw.
		FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
		int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

		// Draw text.
		fontRenderer.drawStringWithShadow(matrixStack, text, width - mx - fontRenderer.getStringWidth(text), y, color);
	}
}
