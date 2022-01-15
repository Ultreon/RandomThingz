package com.ultreon.randomthingz.util.player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SkullBlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Player skins.
 *
 * @author Gravestone Mod
 */
@UtilityClass
public class PlayerSkins {
    private static final HashMap<String, GameProfile> players = new HashMap<String, GameProfile>();

    public static ResourceLocation getSkin(UUID uuid, String name) {
        GameProfile profile = getGameProfile(uuid, name);

        Minecraft minecraft = Minecraft.getInstance();
        Map<Type, MinecraftProfileTexture> map = minecraft.getSkinManager().getInsecureSkinInformation(profile);

        if (map.containsKey(Type.SKIN)) {
            return minecraft.getSkinManager().registerTexture(map.get(Type.SKIN), Type.SKIN);
        } else {
            return DefaultPlayerSkin.getDefaultSkin(uuid);
        }
    }

    public static GameProfile getGameProfile(UUID uuid, String name) {
        if (players.containsKey(uuid.toString())) {
            return players.get(uuid.toString());
        } else {
            GameProfile profile = SkullBlockEntity.updateGameprofile(new GameProfile(uuid, name));
            players.put(uuid.toString(), profile);
            return profile;
        }
    }

}
