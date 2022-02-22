package com.ultreon.randomthingz.client;

import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.actionmenu.MenuHandler;
import com.ultreon.randomthingz.actionmenu.SubmenuItem;
import com.ultreon.randomthingz.client.debug.menu.DebugMenu;
import com.ultreon.randomthingz.util.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.util.Objects;

public class MinecraftMenu extends AbstractActionMenu {
    private static final OptionsMenu optionsMenu = DistExecutor.unsafeRunForDist(() -> OptionsMenu::new, () -> () -> null);

    public MinecraftMenu() {

    }

    public void server() {

    }

    public void client() {
        addClient(new SubmenuItem(new MenuHandler(new TranslatableComponent("menu.options"), optionsMenu)) {
            @OnlyIn(Dist.CLIENT)
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.setScreen(new OptionsScreen(mc.screen, mc.options));
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                WorldUtils.saveWorldThenOpenTitle();
            }

            @Override
            public Component getText() {
                if (!Minecraft.getInstance().isLocalServer()) {
                    return new TranslatableComponent("menu.disconnect");
                }
                return new TranslatableComponent("menu.returnToMenu");
            }
        });
        addClient(new ActionMenuItem() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.setScreen(new ShareToLanScreen(mc.screen));
            }

            @Override
            public Component getText() {
                return new TranslatableComponent("menu.shareToLan");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.hasSingleplayerServer() && !Objects.requireNonNull(mc.getSingleplayerServer()).isPublished();
            }
        });
        addClient(new ActionMenuItem() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.setScreen(new StatsScreen(mc.screen, mc.player.getStats()));
                }
            }

            @Override
            public Component getText() {
                return new TranslatableComponent("gui.stats");
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.setScreen(new AdvancementsScreen(mc.player.connection.getAdvancements()));
                }
            }

            @Override
            public Component getText() {
                return new TranslatableComponent("gui.advancements");
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.MINECRAFT;
            }

            @Override
            public Component getText() {
                return new TranslatableComponent("action.randomthingz.set_debug_page");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.level != null && mc.gameMode != null;
            }
        });
    }
}
