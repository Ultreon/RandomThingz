package com.ultreon.randomthingz.common.item;

import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.client.debug.menu.DebugGui;
import com.ultreon.randomthingz.client.debug.menu.DebugPages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemMenu extends AbstractActionMenu {
    public ItemMenu() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void client() {

        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.level != null && mc.gameMode != null) {
                    mc.gameMode.useItem(mc.player, mc.level, InteractionHand.MAIN_HAND);
//                    mc.rightClickMouse(); // Todo: use right-click method of the Minecraft Class.
                }
            }

            @Override
            public Component getText() {
                return new TextComponent("Use");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.level != null && mc.gameMode != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.level != null && mc.gameMode != null) {
                    if (!mc.player.isSpectator() && mc.player.drop(Screen.hasControlDown())) {
                        mc.player.swing(InteractionHand.MAIN_HAND);
                    }
                }
            }

            @Override
            public Component getText() {
                return new TextComponent("Drop");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.level != null && mc.gameMode != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.level != null && mc.gameMode != null) {
                    if (!mc.player.isSpectator() && mc.player.drop(true)) {
                        mc.player.swing(InteractionHand.MAIN_HAND);
                    }
                }
            }

            @Override
            public Component getText() {
                return new TextComponent("Drop All");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.level != null && mc.gameMode != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.level != null && mc.gameMode != null) {
                    if (!mc.player.isSpectator() && mc.player.drop(false)) {
                        mc.player.swing(InteractionHand.MAIN_HAND);
                    }
                }
            }

            @Override
            public Component getText() {
                return new TextComponent("Drop One");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.level != null && mc.gameMode != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                DebugGui.get().setPage(DebugPages.ITEM);
            }

            @Override
            public Component getText() {
                return new TextComponent("Set Debug Page");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.level != null && mc.gameMode != null;
            }
        });
    }

    @Override
    public void server() {

    }

    @OnlyIn(Dist.CLIENT)
    private ItemStack getCurrentStack() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null) {
            return player.getMainHandItem();
        }
        return ItemStack.EMPTY;
    }
}
