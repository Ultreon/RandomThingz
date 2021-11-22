package com.ultreon.randomthingz.entity.common;

import com.mojang.authlib.GameProfile;
import com.ultreon.randomthingz.commons.enums.TextColors;
import com.ultreon.randomthingz.modules.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.modules.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.modules.actionmenu.ServerActionMenuItem;
import com.ultreon.randomthingz.modules.debugMenu.DebugMenu;
import com.ultreon.randomthingz.util.Targeter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
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
                ClientWorld dimension = mc.dimension;
                ClientPlayerEntity player = mc.player;
                PlayerController controller = mc.playerController;

                EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
                if (result != null && controller != null) {
                    controller.attackEntity(player, result.getEntity());
                }
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Attack");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                ClientWorld dimension = mc.dimension;
                ClientPlayerEntity player = mc.player;

                EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
                return result != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                ClientWorld dimension = mc.dimension;
                ClientPlayerEntity player = mc.player;
                PlayerController controller = mc.playerController;

                EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
                if (result != null && controller != null) {
                    controller.interactWithEntity(player, result.getEntity(), result, Hand.MAIN_HAND);
                }
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Interact");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                ClientWorld dimension = mc.dimension;
                ClientPlayerEntity player = mc.player;

                EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
                return result != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.ENTITY;
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
        add(new ServerActionMenuItem() {
            @Override
            public void onActivate(ServerPlayerEntity player) {
                EntityRayTraceResult entityRayTraceResult = Targeter.rayTraceEntities(player, player.getEntityDimension());
                if (entityRayTraceResult == null) {
                    return;
                }

                Entity entity = entityRayTraceResult.getEntity();
                if (entity instanceof PlayerEntity) {
                    PlayerEntity victim = ((PlayerEntity) entity);

                    if (victim.dimension instanceof ServerWorld) {
                        if (player.getServer() != null) {
                            BanList banlist = player.getServer().getPlayerList().getBannedPlayers();
                            GameProfile gameprofile = victim.getGameProfile();

                            if (!banlist.isBanned(gameprofile)) {
                                ProfileBanEntry profilebanentry = new ProfileBanEntry(gameprofile, new Date(), player.getName().getString(), null, TextColors.LIGHT_RED + "The banhammer has spoken");
                                banlist.addEntry(profilebanentry);
                                player.sendMessage(new TranslationTextComponent("commands.ban.success", TextComponentUtils.getDisplayName(gameprofile), profilebanentry.getBanReason()), victim.getUniqueID());
                                ServerPlayerEntity serverplayerentity = player.getServer().getPlayerList().getPlayerByUUID(gameprofile.getId());
                                if (serverplayerentity != null) {
                                    serverplayerentity.connection.disconnect(new TranslationTextComponent("multiplayer.disconnect.banned"));
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.randomthingz.player.ban");
            }

            @Override
            public boolean isEnabled() {
                return super.isEnabled();
            }
        });
        add(new ServerActionMenuItem() {
            @Override
            public void onActivate(ServerPlayerEntity player) {
                EntityRayTraceResult entityRayTraceResult = Targeter.rayTraceEntities(player, player.getEntityDimension());
                if (entityRayTraceResult == null) {
                    return;
                }

                Entity entity = entityRayTraceResult.getEntity();
                if (entity instanceof PlayerEntity) {
                    PlayerEntity victim = ((PlayerEntity) entity);

                    if (victim instanceof ServerPlayerEntity) {
                        String reason = "You've been kicked from the server.";
                        ((ServerPlayerEntity) victim).connection.disconnect(new StringTextComponent(reason));
                        player.sendMessage(new TranslationTextComponent("commands.kick.success", victim.getDisplayName(), reason), player.getUniqueID());
                    }
                }
            }

            @Override
            public ITextComponent getText() {
                return new TranslationTextComponent("action.randomthingz.player.kick");
            }

            @Override
            public boolean isEnabled() {
                return super.isEnabled();
            }
        });
    }
}
