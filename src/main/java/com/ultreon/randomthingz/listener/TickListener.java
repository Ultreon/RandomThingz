package com.ultreon.randomthingz.listener;

import com.google.common.annotations.Beta;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jline.utils.ShutdownHooks;

/**
 * Tick listener.
 *
 * @author Qboi123
 */
@Beta
//@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@UtilityClass
public class TickListener {
    private static boolean attackBusy = false;
    private static boolean useBusy = false;
    private static boolean inventoryBusy = false;
    private static boolean sneakBusy = false;
    private static boolean jumpBusy = false;

    private static final ControllerManager controllers = new ControllerManager();

    static {
        controllers.initSDLGamepad();
        ShutdownHooks.add(controllers::quitSDLGamepad);
    }

    @Beta
//    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientPlayerTick(TickEvent.ClientTickEvent event) {
        ControllerState currState = controllers.getState(0);

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) {
            return;
        }

        if (!currState.isConnected) {
            if (attackBusy) {
                mc.options.keyAttack.setDown(false);
                attackBusy = false;
            }
            if (useBusy) {
                mc.options.keyUse.setDown(false);
                useBusy = false;
            }
            if (inventoryBusy) {
                mc.options.keyInventory.setDown(false);
                inventoryBusy = false;
            }
            if (sneakBusy) {
                player.setSprinting(false);
                sneakBusy = false;
            }
            if (jumpBusy) {
                mc.options.keyJump.setDown(false);
                jumpBusy = false;
            }
            return;
        }

        if (event.phase == TickEvent.Phase.END)
            if (mc.isPaused()) {
                if (currState.startJustPressed) {
                    mc.setScreen(null);
                }
            } else {
                if (currState.startJustPressed) {
                    mc.pauseGame(false);
                }

                //was plugged in or unplugged at this index.
                if (player.containerMenu == null) {
                    if (player.isOnGround()) {
                        player.setShiftKeyDown(currState.b);
                        sneakBusy = currState.b;
                    }
                } else {
                    player.closeContainer();
                }

                if (currState.leftStickMagnitude > 0.2) {
                    Vec2 py = player.getRotationVector();
                    py = new Vec2(py.x + currState.leftStickX * 90, 0);

                    double mul = player.getSpeed();
                    player.setDeltaMovement(Vec3.directionFromRotation(py.x, py.y).multiply(mul, mul, mul));
                }

                if (currState.rightStickMagnitude > 0.2) {
                    Vec2 pitchYaw = player.getRotationVector();
                    float pitch = pitchYaw.x;
                    float yaw = pitchYaw.y;

                    pitch -= Math.max(Math.min(currState.rightStickY * 10 * mc.options.sensitivity, 90), -90);
                    yaw += (currState.rightStickX * 10 * mc.options.sensitivity) % 360f;

                    player.absMoveTo(player.getX(), player.getY(), player.getZ(), yaw, pitch);
                }

                if (currState.lbJustPressed) {
                    player.getInventory().selected = Math.max(player.getInventory().selected - 1, 0);
                }
                if (currState.rbJustPressed) {
                    player.getInventory().selected = Math.min(player.getInventory().selected + 1, 9);
                }
                if (currState.dpadUp) {
                    player.getInventory().selected = Math.min(player.getInventory().selected + 1, 9);
                }

                if (currState.rightTrigger > 0.2) {
                    mc.options.keyAttack.setDown(true);
                    attackBusy = true;

                } else {
                    mc.options.keyAttack.setDown(false);
                    attackBusy = false;
                }

                if (currState.leftTrigger > 0.2) {
                    mc.options.keyUse.setDown(true);
                    useBusy = true;
                } else {
                    mc.options.keyUse.setDown(false);
                    useBusy = false;
                }

                if (currState.y) {
                    mc.options.keyInventory.setDown(true);
                    inventoryBusy = true;
                } else {
                    mc.options.keyInventory.setDown(false);
                    inventoryBusy = false;
                }

                if (currState.x) {
                    inventoryBusy = true;
                } else {
                    mc.options.keyInventory.setDown(false);
                    inventoryBusy = false;
                }

                if (currState.a) {
                    mc.options.keyJump.setDown(true);
                    jumpBusy = true;
                } else {
                    mc.options.keyJump.setDown(false);
                    jumpBusy = false;
                }
            }
    }
}
