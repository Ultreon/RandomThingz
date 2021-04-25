package com.qtech.forgemod.listener;

import com.studiohartman.jamepad.*;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import org.jline.utils.ShutdownHooks;

/**
 * Tick listener.
 *
 * @author Qboi123
 */
//@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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

//    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientPlayerTick(TickEvent.ClientTickEvent event) {
        ControllerState currState = controllers.getState(0);

        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        if (player == null) {
            return;
        }

        if (!currState.isConnected) {
            if (attackBusy) {
                mc.gameSettings.keyBindAttack.setPressed(false);
                attackBusy = false;
            }
            if (useBusy) {
                mc.gameSettings.keyBindUseItem.setPressed(false);
                useBusy = false;
            }
            if (inventoryBusy) {
                mc.gameSettings.keyBindInventory.setPressed(false);
                inventoryBusy = false;
            }
            if (sneakBusy) {
                player.setSprinting(false);
                sneakBusy = false;
            }
            if (jumpBusy) {
                mc.gameSettings.keyBindJump.setPressed(false);
                jumpBusy = false;
            }
            return;
        }

        if (event.phase == TickEvent.Phase.END)
            if (mc.isGamePaused()) {
                if (currState.startJustPressed) {
                    mc.displayGuiScreen(null);
                }
            } else {
                if (currState.startJustPressed) {
                    mc.displayInGameMenu(false);
                }

                //was plugged in or unplugged at this index.
                if (player.openContainer == null) {
                    if (player.isOnGround()) {
                        player.setSneaking(currState.b);
                        sneakBusy = currState.b;
                    }
                } else {
                    player.closeScreen();
                }

                if (currState.leftStickMagnitude > 0.2) {
                    Vector2f py = player.getPitchYaw();
                    py = new Vector2f(py.x + currState.leftStickX * 90, 0);

                    double mul = player.getAIMoveSpeed();
                    player.setMotion(Vector3d.fromPitchYaw(py.x, py.y).mul(mul, mul, mul));
                }

                if (currState.rightStickMagnitude > 0.2) {
                    Vector2f pitchYaw = player.getPitchYaw();
                    float pitch = pitchYaw.x;
                    float yaw = pitchYaw.y;

                    pitch -= Math.max(Math.min(currState.rightStickY * 10 * mc.gameSettings.mouseSensitivity, 90), -90);
                    yaw += (currState.rightStickX * 10 * mc.gameSettings.mouseSensitivity) % 360f;

                    player.setPositionAndRotation(player.getPosX(), player.getPosY(), player.getPosZ(), yaw, pitch);
                }

                if (currState.lbJustPressed) {
                    player.inventory.currentItem = Math.max(player.inventory.currentItem - 1, 0);
                }
                if (currState.rbJustPressed) {
                    player.inventory.currentItem = Math.min(player.inventory.currentItem + 1, 9);
                }
                if (currState.dpadUp) {
                    player.inventory.currentItem = Math.min(player.inventory.currentItem + 1, 9);
                }

                if (currState.rightTrigger > 0.2) {
                    mc.gameSettings.keyBindAttack.setPressed(true);
                    attackBusy = true;

                } else {
                    mc.gameSettings.keyBindAttack.setPressed(false);
                    attackBusy = false;
                }

                if (currState.leftTrigger > 0.2) {
                    mc.gameSettings.keyBindUseItem.setPressed(true);
                    useBusy = true;
                } else {
                    mc.gameSettings.keyBindUseItem.setPressed(false);
                    useBusy = false;
                }

                if (currState.y) {
                    mc.gameSettings.keyBindInventory.setPressed(true);
                    inventoryBusy = true;
                } else {
                    mc.gameSettings.keyBindInventory.setPressed(false);
                    inventoryBusy = false;
                }

                if (currState.x) {
                    inventoryBusy = true;
                } else {
                    mc.gameSettings.keyBindInventory.setPressed(false);
                    inventoryBusy = false;
                }

                if (currState.a) {
                    mc.gameSettings.keyBindJump.setPressed(true);
                    jumpBusy = true;
                } else {
                    mc.gameSettings.keyBindJump.setPressed(false);
                    jumpBusy = false;
                }
            }
    }
}
