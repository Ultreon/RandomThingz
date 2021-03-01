package com.qsoftware.forgemod.modules.debugMenu;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleSecurity;
import com.qsoftware.forgemod.modules.actionmenu.MainActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.MenuHandler;
import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DebugMenuModule extends Module {
    @Getter private static final DebugMenuMenu debugMenuMenu = new DebugMenuMenu();
    @Getter private static final DebugMenu     debugMenu     = new DebugMenu();

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
    public void onClientTick(TickEvent.ClientTickEvent event) {
        debugMenu.onClientTick(event);
    }

    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent event) {
        debugMenu.renderGameOverlay(event);
    }

    @Override
    public boolean canDisable() {
        return true;
    }

    @Override
    public String getName() {
        return "debug_menu";
    }

    @Override
    public ModuleCompatibility getCompatibility() {
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
