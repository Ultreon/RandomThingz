package com.ultreon.randomthingz.modules.debugMenu;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.commons.Module;
import com.ultreon.randomthingz.commons.ModuleSafety;
import com.ultreon.randomthingz.modules.actionmenu.MainActionMenu;
import com.ultreon.randomthingz.modules.actionmenu.MenuHandler;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class DebugMenuModule extends Module {
    private static final DebugMenuMenu debugMenuMenu = new DebugMenuMenu();

    public DebugMenuModule() {

        MainActionMenu.registerHandler(new MenuHandler(new StringTextComponent("Debug Menu"), debugMenuMenu));
    }

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    @Override
    public void onEnable() {
        if (RandomThingz.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public void onDisable() {
        if (RandomThingz.isClientSide()) {
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    @SubscribeEvent
    public void onKeyReleased(InputEvent.KeyInputEvent event) {
        DebugMenu.onKeyReleased(event);
    }

    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent event) {
        DebugMenu.renderGameOverlay(event);
    }

    @Override
    public boolean canDisable() {
        return true;
    }

    @Override
    public @NotNull String getName() {
        return "debug_menu";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        if (RandomThingz.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (RandomThingz.isServerSide()) {
            return ModuleCompatibility.PARTIAL;
        } else {
            return ModuleCompatibility.NONE;
        }
    }

    @Override
    public boolean isDefaultEnabled() {
        return true;
    }
}
