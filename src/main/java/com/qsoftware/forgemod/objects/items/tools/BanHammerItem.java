package com.qsoftware.forgemod.objects.items.tools;

import com.mojang.authlib.GameProfile;
import com.qsoftware.forgemod.common.TextColors;
import com.qsoftware.forgemod.groups.Groups;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Ban hammer item class.
 *
 * @author Qboi123
 */
public class BanHammerItem extends Item {
    public BanHammerItem() {
        super(new Item.Properties().group(Groups.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public boolean hitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, @NotNull LivingEntity player) {
        if (victim instanceof PlayerEntity) {
            PlayerEntity playerVictim = (PlayerEntity) victim;
            if (playerVictim.world instanceof ServerWorld) {
                if (player.getServer() != null) {
                    BanList banlist = player.getServer().getPlayerList().getBannedPlayers();
                    GameProfile gameprofile = playerVictim.getGameProfile();

                    if (!banlist.isBanned(gameprofile)) {
                        ProfileBanEntry profilebanentry = new ProfileBanEntry(gameprofile, new Date(), player.getName().getString(), null, TextColors.LIGHT_RED + "The banhammer has spoken");
                        banlist.addEntry(profilebanentry);
                        player.sendMessage(new TranslationTextComponent("commands.ban.success", TextComponentUtils.getDisplayName(gameprofile), profilebanentry.getBanReason()), playerVictim.getUniqueID());
                        ServerPlayerEntity serverplayerentity = player.getServer().getPlayerList().getPlayerByUUID(gameprofile.getId());
                        if (serverplayerentity != null) {
                            serverplayerentity.connection.disconnect(new TranslationTextComponent("multiplayer.disconnect.banned"));
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (!entity.world.isRemote && entity instanceof PlayerEntity) {
            PlayerEntity victim = (PlayerEntity) entity;
            if (victim.world instanceof ServerWorld) {
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
        return false;
    }
}
