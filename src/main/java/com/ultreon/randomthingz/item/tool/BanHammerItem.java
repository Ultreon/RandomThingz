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
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity victim, @NotNull LivingEntity executor) {
        if (victim instanceof Player target) {
            if (target.level instanceof ServerLevel) {
                if (executor.getServer() != null) {
                    UserBanList banList = executor.getServer().getPlayerList().getBans();
                    GameProfile profile = target.getGameProfile();

                    if (!banList.isBanned(profile)) {
                        UserBanListEntry banEntry = new UserBanListEntry(profile, new Date(), executor.getName().getString(), null, TextColors.LIGHT_RED + "The ban-hammer has spoken");
                        banList.add(banEntry);
                        executor.sendMessage(new TranslatableComponent("commands.ban.success", ComponentUtils.getDisplayName(profile), banEntry.getReason()), target.getUUID());
                        ServerPlayer serverPlayer = executor.getServer().getPlayerList().getPlayer(profile.getId());
                        if (serverPlayer != null) {
                            serverPlayer.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.banned"));
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player executor, InteractionHand hand) {
        super.use(level, executor, hand);

        ItemStack stack = executor.getItemInHand(hand);
        Entity victim = Targeter.getTarget(executor);
        if (victim == null) {
            return InteractionResultHolder.fail(stack);
        }

        if (victim instanceof Player player) {
            if (player.level instanceof ServerLevel) {
                if (executor.getServer() != null) {
                    UserBanList banList = executor.getServer().getPlayerList().getBans();
                    GameProfile profile = player.getGameProfile();

                    if (!banList.isBanned(profile)) {
                        UserBanListEntry banEntry = new UserBanListEntry(profile, new Date(), executor.getName().getString(), null, TextColors.LIGHT_RED + "The ban-hammer has spoken");
                        banList.add(banEntry);
                        executor.sendMessage(new TranslatableComponent("commands.ban.success", ComponentUtils.getDisplayName(profile), banEntry.getReason()), player.getUUID());
                        ServerPlayer serverPlayer = executor.getServer().getPlayerList().getPlayer(profile.getId());
                        if (serverPlayer != null) {
                            serverPlayer.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.banned"));
                            executor.awardStat(Stats.ITEM_USED.get(this));

                            return InteractionResultHolder.success(stack);
                        }
                    } else {
                        return InteractionResultHolder.fail(stack);
                    }
                }
            }
        } else if (!victim.level.isClientSide) {
            if (victim.level instanceof ServerLevel) {
                victim.discard();
                executor.sendMessage(new TranslatableComponent("commands.ban.success", victim.getName(), "The ban-hammer has spoken!"), victim.getUUID());
                executor.awardStat(Stats.ITEM_USED.get(this));

                return InteractionResultHolder.success(stack);
            }
        }

        return super.use(level, executor, hand);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player executor, Entity victim) {
        if (!victim.level.isClientSide && victim instanceof Player target) {
            if (target.level instanceof ServerLevel) {
                if (executor.getServer() != null) {
                    UserBanList banList = executor.getServer().getPlayerList().getBans();
                    GameProfile profile = target.getGameProfile();

                    if (!banList.isBanned(profile)) {
                        UserBanListEntry banEntry = new UserBanListEntry(profile, new Date(), executor.getName().getString(), null, TextColors.LIGHT_RED + "The ban-hammer has spoken");
                        banList.add(banEntry);
                        executor.sendMessage(new TranslatableComponent("commands.ban.success", ComponentUtils.getDisplayName(profile), banEntry.getReason()), target.getUUID());
                        ServerPlayer serverPlayer = executor.getServer().getPlayerList().getPlayer(profile.getId());
                        if (serverPlayer != null) {
                            serverPlayer.connection.disconnect(new TranslatableComponent("multiplayer.disconnect.banned"));
                            executor.awardStat(Stats.ITEM_USED.get(this));
                        }
                    }
                }
            }
        } else if (!victim.level.isClientSide) {
            if (victim.level instanceof ServerLevel) {
                victim.discard();
                executor.sendMessage(new TranslatableComponent("commands.ban.success", victim.getName(), "The ban-hammer has spoken!"), victim.getUUID());
            }
        }

        return false;
    }
}
