package com.qsoftware.forgemod.modules.pcShutdown;

import com.qsoftware.forgemod.Modules;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.IActionMenuItem;
import com.qsoftware.forgemod.modules.pcCrash.ConfirmCrashScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ComputerMenu extends AbstractActionMenu {
    public ComputerMenu() {
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.displayGuiScreen(new ConfirmShutdownScreen(mc.currentScreen));
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Shutdown");
            }

            @Override
            public boolean isEnabled() {
                return ModuleManager.getInstance().isEnabled(Modules.PC_SHUTDOWN);
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.displayGuiScreen(new ConfirmCrashScreen(mc.currentScreen));
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Shutdown");
            }

            @Override
            public boolean isEnabled() {
                return ModuleManager.getInstance().isEnabled(Modules.PC_CRASH);
            }
        });
    }
}
