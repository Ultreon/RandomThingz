package com.qsoftware.forgemod.modules.items;

import com.qsoftware.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.IActionMenuItem;
import com.qsoftware.forgemod.modules.debugMenu.DebugMenu;
import com.qsoftware.forgemod.modules.debugMenu.DebugMenuModule;
import com.qsoftware.forgemod.modules.debugMenu.ModDebugPages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class ItemMenu extends AbstractActionMenu {
    public ItemMenu() {
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.world != null && mc.playerController != null) {
                    mc.playerController.processRightClick(mc.player, mc.world, Hand.MAIN_HAND);
                }
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Use");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.world != null && mc.playerController != null;
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.world != null && mc.playerController != null) {
                    if (!mc.player.isSpectator() && mc.player.drop(Screen.hasControlDown())) {
                        mc.player.swingArm(Hand.MAIN_HAND);
                    }
                }
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Drop");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.world != null && mc.playerController != null;
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.world != null && mc.playerController != null) {
                    if (!mc.player.isSpectator() && mc.player.drop(true)) {
                        mc.player.swingArm(Hand.MAIN_HAND);
                    }
                }
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Drop All");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.world != null && mc.playerController != null;
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.world != null && mc.playerController != null) {
                    if (!mc.player.isSpectator() && mc.player.drop(false)) {
                        mc.player.swingArm(Hand.MAIN_HAND);
                    }
                }
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Drop One");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.world != null && mc.playerController != null;
            }
        });
        addItem(new IActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenuModule.getDebugMenu().setPage(ModDebugPages.ITEM_PAGE.get());
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Set Debug Page");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.world != null && mc.playerController != null;
            }
        });
    }
    
    private ItemStack getCurrentStack() {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        if (player != null) {
            return player.getHeldItemMainhand();
        }
        return ItemStack.EMPTY;
    }
}
