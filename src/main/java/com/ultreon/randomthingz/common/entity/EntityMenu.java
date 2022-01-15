package com.ultreon.randomthingz.common.entity;

import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.client.debug.menu.DebugMenu;
import com.ultreon.randomthingz.util.Targeter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityMenu extends AbstractActionMenu {
    public EntityMenu() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void client() {
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                ClientLevel dimension = mc.level;
                LocalPlayer player = mc.player;
                MultiPlayerGameMode controller = mc.gameMode;

                EntityHitResult result = Targeter.rayTraceEntities(player, dimension);
                if (result != null && controller != null) {
                    controller.attack(player, result.getEntity());
                }
            }

            @Override
            public Component getText() {
                return new TextComponent("Attack");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                ClientLevel dimension = mc.level;
                LocalPlayer player = mc.player;

                EntityHitResult result = Targeter.rayTraceEntities(player, dimension);
                return result != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                ClientLevel dimension = mc.level;
                LocalPlayer player = mc.player;
                MultiPlayerGameMode controller = mc.gameMode;

                EntityHitResult result = Targeter.rayTraceEntities(player, dimension);
                if (result != null && controller != null) {
                    controller.interactAt(player, result.getEntity(), result, InteractionHand.MAIN_HAND);
                }
            }

            @Override
            public Component getText() {
                return new TextComponent("Interact");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                ClientLevel dimension = mc.level;
                LocalPlayer player = mc.player;

                EntityHitResult result = Targeter.rayTraceEntities(player, dimension);
                return result != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.ENTITY;
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
}
