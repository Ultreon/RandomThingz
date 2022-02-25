package com.ultreon.randomthingz.common.entity;

import com.mojang.authlib.GameProfile;
import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.actionmenu.ServerActionMenuItem;
import com.ultreon.randomthingz.client.debug.menu.DebugGui;
import com.ultreon.randomthingz.client.debug.menu.DebugPages;
import com.ultreon.randomthingz.common.enums.TextColors;
import com.ultreon.randomthingz.util.Targeter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Date;

public class PlayerMenu extends AbstractActionMenu {
    public PlayerMenu() {
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
                DebugGui.get().setPage(DebugPages.PLAYER_PAGE1);
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
        add(new ServerActionMenuItem() {
            @Override
            public void onActivate(ServerPlayer player) {
                EntityHitResult entityRayTraceResult = Targeter.rayTraceEntities(player, player.getCommandSenderWorld());
                if (entityRayTraceResult == null) {
                    return;
                }

                Entity entity = entityRayTraceResult.getEntity();
                if (entity instanceof Player) {
                    Player victim = ((Player) entity);

                    if (victim.level instanceof ServerLevel) {
                        if (player.getServer() != null) {
                            UserBanList banlist = player.getServer().getPlayerList().getBans();
                            GameProfile gameprofile = victim.getGameProfile();

                            if (!banlist.isBanned(gameprofile)) {
                                UserBanListEntry profilebanentry = new UserBanListEntry(gameprofile, new Date(), player.getName().getString(), null, TextColors.LIGHT_RED + "The banhammer has spoken");
                                banlist.add(profilebanentry);
                                player.sendMessage(new TranslatableComponent("commands.ban.success", ComponentUtils.getDisplayName(gameprofile), profilebanentry.getReason()), victim.getUUID());
                                ServerPlayer serverplayerentity = player.getServer().getPlayerList().getPlayer(gameprofile.getId());
                                if (serverplayerentity != null) {
                                    serverplayerentity.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.banned"));
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public Component getText() {
                return new TranslatableComponent("action.randomthingz.player.ban");
            }

            @Override
            public boolean isEnabled() {
                return super.isEnabled();
            }
        });
        add(new ServerActionMenuItem() {
            @Override
            public void onActivate(ServerPlayer player) {
                EntityHitResult entityRayTraceResult = Targeter.rayTraceEntities(player, player.getCommandSenderWorld());
                if (entityRayTraceResult == null) {
                    return;
                }

                Entity entity = entityRayTraceResult.getEntity();
                if (entity instanceof Player) {
                    Player victim = ((Player) entity);

                    if (victim instanceof ServerPlayer) {
                        String reason = "You've been kicked from the server.";
                        ((ServerPlayer) victim).connection.disconnect(new TextComponent(reason));
                        player.sendMessage(new TranslatableComponent("commands.kick.success", victim.getDisplayName(), reason), player.getUUID());
                    }
                }
            }

            @Override
            public Component getText() {
                return new TranslatableComponent("action.randomthingz.player.kick");
            }

            @Override
            public boolean isEnabled() {
                return super.isEnabled();
            }
        });
    }
}
