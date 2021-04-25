package com.qtech.forgemod.modules.pcShutdown;

import com.qtech.forgemod.Modules;
import com.qtech.forgemod.common.ModuleManager;
import com.qtech.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qtech.forgemod.modules.actionmenu.IActionMenuItem;
import com.qtech.forgemod.modules.debugMenu.DebugMenu;
import com.qtech.forgemod.util.ComputerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ComputerMenu extends AbstractActionMenu {
    public ComputerMenu() {
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                ComputerUtils.shutdown();
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
                ComputerUtils.hibernate();
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Hibernate");
            }

            @Override
            public boolean isEnabled() {
                return ModuleManager.getInstance().isEnabled(Modules.PC_SHUTDOWN) && ComputerUtils.supportsHibernate();
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                ComputerUtils.reboot();
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Reboot");
            }

            @Override
            public boolean isEnabled() {
                return ModuleManager.getInstance().isEnabled(Modules.PC_SHUTDOWN) && ComputerUtils.supportsReboot();
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                ComputerUtils.crash();
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Crash");
            }

            @Override
            public boolean isEnabled() {
                return ModuleManager.getInstance().isEnabled(Modules.PC_CRASH) && ComputerUtils.supportsCrash();
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.COMPUTER;
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Set Debug Page");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.dimension != null && mc.playerController != null;
            }
        });
    }
}
