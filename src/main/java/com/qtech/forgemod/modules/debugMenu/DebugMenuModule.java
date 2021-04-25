package com.qtech.forgemod.modules.debugMenu;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.client.gui.modules.ModuleCompatibility;
import com.qtech.forgemod.common.Module;
import com.qtech.forgemod.common.ModuleSecurity;
import com.qtech.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qtech.forgemod.modules.actionmenu.MainActionMenu;
import com.qtech.forgemod.modules.actionmenu.IMenuHandler;
import com.qtech.forgemod.modules.actionmenu.MenuHandler;
import net.minecraft.util.text.ITextComponent;
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
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
    }

    @Override
    public void onEnable() {
        if (QForgeMod.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public void onDisable() {
        if (QForgeMod.isClientSide()) {
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
        if (QForgeMod.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (QForgeMod.isServerSide()) {
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
