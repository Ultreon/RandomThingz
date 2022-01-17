package com.ultreon.randomthingz.item.tool;

import com.mojang.authlib.GameProfile;
import com.ultreon.randomthingz.common.enums.TextColors;
import com.ultreon.randomthingz.common.item.ModCreativeTabs;
import com.ultreon.randomthingz.util.Targeter;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Ban hammer item class.
 *
 * @author Qboi123
 */
public class BanHammerItem extends Item {
    public BanHammerItem() {
        super(new Item.Properties().tab(ModCreativeTabs.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity victim, @NotNull LivingEntity player) {
        if (victim instanceof Player) {
            Player playerVictim = (Player) victim;
            if (playerVictim.level instanceof ServerLevel) {
                if (player.getServer() != null) {
                    UserBanList banlist = player.getServer().getPlayerList().getBans();
                    GameProfile gameprofile = playerVictim.getGameProfile();

                    if (!banlist.isBanned(gameprofile)) {
                        UserBanListEntry profilebanentry = new UserBanListEntry(gameprofile, new Date(), player.getName().getString(), null, TextColors.LIGHT_RED + "The banhammer has spoken");
                        banlist.add(profilebanentry);
                        player.sendMessage(new TranslatableComponent("commands.ban.success", ComponentUtils.getDisplayName(gameprofile), profilebanentry.getReason()), playerVictim.getUUID());
                        ServerPlayer serverplayerentity = player.getServer().getPlayerList().getPlayer(gameprofile.getId());
                        if (serverplayerentity != null) {
                            serverplayerentity.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.banned"));
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level dimensionIn, Player playerIn, InteractionHand handIn) {
        super.use(dimensionIn, playerIn, handIn);

        ItemStack stack = playerIn.getItemInHand(handIn);
        Entity entity = Targeter.getTarget(playerIn);
        if (entity == null) {
            return InteractionResultHolder.fail(stack);
        }

        if (!entity.level.isClientSide && entity instanceof Player) {
            Player victim = (Player) entity;
            if (victim.level instanceof ServerLevel) {
                if (playerIn.getServer() != null) {
                    UserBanList banlist = playerIn.getServer().getPlayerList().getBans();
                    GameProfile gameprofile = victim.getGameProfile();

                    if (!banlist.isBanned(gameprofile)) {
                        UserBanListEntry profilebanentry = new UserBanListEntry(gameprofile, new Date(), playerIn.getName().getString(), null, TextColors.LIGHT_RED + "The banhammer has spoken");
                        banlist.add(profilebanentry);
                        playerIn.sendMessage(new TranslatableComponent("commands.ban.success", ComponentUtils.getDisplayName(gameprofile), profilebanentry.getReason()), victim.getUUID());
                        ServerPlayer serverplayerentity = playerIn.getServer().getPlayerList().getPlayer(gameprofile.getId());
                        if (serverplayerentity != null) {
                            serverplayerentity.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.banned"));
                            playerIn.awardStat(Stats.ITEM_USED.get(this));

                            return InteractionResultHolder.success(stack);
                        }
                    } else {
                        return InteractionResultHolder.fail(stack);
                    }
                }
            }
        } else if (!entity.level.isClientSide) {
            if (entity.level instanceof ServerLevel) {
                entity.remove();
                playerIn.sendMessage(new TranslatableComponent("commands.ban.success", entity.getName(), "The banhammer has spoken!"), entity.getUUID());
                playerIn.awardStat(Stats.ITEM_USED.get(this));

                return InteractionResultHolder.success(stack);
            }
        }

        return super.use(dimensionIn, playerIn, handIn);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (!entity.level.isClientSide && entity instanceof Player) {
            Player victim = (Player) entity;
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
                            player.awardStat(Stats.ITEM_USED.get(this));
                        }
                    }
                }
            }
        } else if (!entity.level.isClientSide) {
            if (entity.level instanceof ServerLevel) {
                entity.remove();
                player.sendMessage(new TranslatableComponent("commands.ban.success", entity.getName(), "The banhammer has spoken!"), entity.getUUID());
            }
        }

        return false;
    }
}
