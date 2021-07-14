package com.qtech.randomthingz.modules.client;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.modules.actionmenu.*;
import com.qtech.randomthingz.modules.debugMenu.DebugMenu;
import com.qtech.randomthingz.util.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.ShareToLanScreen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.util.Objects;

public class MinecraftMenu extends AbstractActionMenu {
    private static final OptionsMenu optionsMenu = DistExecutor.unsafeRunForDist(() -> OptionsMenu::new, () -> () -> null);

    public MinecraftMenu() {

    }

    public void server() {
        addServer(new ServerActionMenuItem() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate(ServerPlayerEntity player) {
                RandomThingz.LOGGER.debug("Got activation for initiating server shutdown.");
                player.sendMessage(new TranslationTextComponent("commands.stop.stopping"), player.getUniqueID());
                player.getServer().initiateShutdown(false);
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.randomthingz.stop_server");
            }
        });
    }

    public void client() {
        addClient(new SubmenuItem(new MenuHandler(new TranslationTextComponent("menu.options"), optionsMenu)) {
            @OnlyIn(Dist.CLIENT)
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.displayGuiScreen(new OptionsScreen(mc.currentScreen, mc.gameSettings));
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                WorldUtils.saveWorldThenOpenTitle();
            }

            @Override
            public ITextComponent getText() {
                if (!Minecraft.getInstance().isIntegratedServerRunning()) {
                    return new TranslationTextComponent("menu.disconnect");
                }
                return new TranslationTextComponent("menu.returnToMenu");
            }
        });
        addClient(new ActionMenuItem() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                mc.displayGuiScreen(new ShareToLanScreen(mc.currentScreen));
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("menu.shareToLan");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.isSingleplayer() && !Objects.requireNonNull(mc.getIntegratedServer()).getPublic();
            }
        });
        addClient(new ActionMenuItem() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.displayGuiScreen(new StatsScreen(mc.currentScreen, mc.player.getStats()));
                }
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("gui.stats");
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null) {
                    mc.displayGuiScreen(new AdvancementsScreen(mc.player.connection.getAdvancementManager()));
                }
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("gui.advancements");
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.MINECRAFT;
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.randomthingz.set_debug_page");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.dimension != null && mc.playerController != null;
            }
        });
    }
}
