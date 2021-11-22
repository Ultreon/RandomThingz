package com.ultreon.randomthingz.item.common;

import com.ultreon.randomthingz.modules.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.modules.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.modules.debugMenu.DebugMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
                if (mc.player != null && mc.dimension != null && mc.playerController != null) {
                    mc.playerController.processRightClick(mc.player, mc.dimension, Hand.MAIN_HAND);
//                    mc.rightClickMouse(); // Todo: use right-click method of the Minecraft Class.
                }
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Use");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.dimension != null && mc.playerController != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.dimension != null && mc.playerController != null) {
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
                return mc.player != null && mc.dimension != null && mc.playerController != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.dimension != null && mc.playerController != null) {
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
                return mc.player != null && mc.dimension != null && mc.playerController != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.dimension != null && mc.playerController != null) {
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
                return mc.player != null && mc.dimension != null && mc.playerController != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.ITEM;
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

    @Override
    public void server() {

    }

    @OnlyIn(Dist.CLIENT)
    private ItemStack getCurrentStack() {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        if (player != null) {
            return player.getHeldItemMainhand();
        }
        return ItemStack.EMPTY;
    }
}
