package com.qtech.randomthingz.commons.xinput;

import com.studiohartman.jamepad.ControllerIndex;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ControllerManager {
    public static final int MAX_CONTROLLERS = 1;

    private static final ControllerManager instance = new ControllerManager();
    private final Minecraft mc;

    public static ControllerManager getInstance() {
        return instance;
    }

    private final AbstractController[] controllers = new AbstractController[MAX_CONTROLLERS];
    private final com.studiohartman.jamepad.ControllerManager nativeManager = new com.studiohartman.jamepad.ControllerManager(MAX_CONTROLLERS);

    private ControllerManager() {
        this.nativeManager.initSDLGamepad();
        this.mc = Minecraft.getInstance();
    }

    public List<AbstractController> getControllers() {
        return Collections.unmodifiableList(Arrays.asList(this.controllers));
    }

    public void tick() {
        checkForNewControllers();

        for (AbstractController controller : this.controllers) {
            controller.tick();
            if (this.mc.currentScreen != null && this.mc.currentScreen.shouldCloseOnEsc()) {
                if (controller.isPressingBack()) {
                    this.mc.currentScreen.closeScreen();
                }
            }
        }
    }

    private void checkForNewControllers() {
        for (int i = 0; i < MAX_CONTROLLERS; i++) {
            ControllerIndex controllerIndex = this.nativeManager.getControllerIndex(i);
            boolean wasConnected = this.controllers[i] != null;
            boolean currentConnected = controllerIndex.isConnected();
            if (wasConnected != currentConnected) {
                if (!currentConnected) {
                    this.controllers[i] = null;
                } else {
                    this.controllers[i] = new XInputController(nativeManager, i);
                }
            }
        }
    }
}
