package com.qsoftware.mcscript;

import com.mojang.authlib.GameProfile;
import com.qsoftware.forgemod.common.enums.TextColors;
import com.qsoftware.forgemod.script.js.ServerScriptUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@SuppressWarnings({"unused", "deprecation"})
public class Player extends LivingEntity {
    final ServerPlayerEntity wrapper;

    public Player(UUID uuid) {
        this(ServerScriptUtils.getServer().getPlayerList().getPlayerByUUID(uuid));
    }

    public Player(String uuid) {
        this(UUID.fromString(uuid));
    }

    protected Player(ServerPlayerEntity player) {
        super(player);
        this.wrapper = player;
    }

    public static Player getFromName(String name) {
        return new Player(ServerScriptUtils.getServer().getPlayerList().getPlayerByUsername(name));
    }

    public void setGameType(String gameType) {
        wrapper.setGameType(GameType.valueOf(gameType.toUpperCase(Locale.ROOT)));
    }
    public void setGameType(GameType gameType) {
        wrapper.setGameType(gameType);
    }
    public void setExpLevel(int level) {
        wrapper.setExperienceLevel(level);
    }
    public void setHunger(int foodLevel) {
        wrapper.getFoodStats().setFoodLevel(foodLevel);
    }
    public void setSaturation(float saturation) {
        wrapper.getFoodStats().setFoodSaturationLevel(saturation);
    }
    public float getSaturation() {
        return wrapper.getFoodStats().getSaturationLevel();
    }
    public int getHunger() {
        return wrapper.getFoodStats().getFoodLevel();
    }
    public int getExpLevel() {
        return wrapper.experienceLevel;
    }

    public String getPlayerIP() {
        return wrapper.getPlayerIP();
    }
    public String getLanguage() {
        return wrapper.getLanguage();
    }
    public Entity getSpectatingEntity() {
        return Entity.getCorrectEntity(wrapper.getSpectatingEntity());
    }
    public void setSpectatingEntity(Entity entity) {
        wrapper.setSpectatingEntity(entity.wrapper);
    }

    public void allowFlying() {
        wrapper.abilities.allowFlying = true;
    }
    public void disallowFlying() {
        wrapper.abilities.allowFlying = false;
    }
    public boolean allowsFlying() {
        return wrapper.abilities.allowFlying;
    }

    public void sendMessage(String message) {
        wrapper.sendMessage(new StringTextComponent(message), wrapper.getUniqueID());
    }
    public void sendMessage(ITextComponent text) {
        wrapper.sendMessage(text, wrapper.getUniqueID());
    }

    public boolean ban(@Nullable ITextComponent reason) {
        PlayerEntity playerVictim = wrapper;
        if (playerVictim.world instanceof ServerWorld) {
            if (playerVictim.getServer() != null) {
                BanList banlist = playerVictim.getServer().getPlayerList().getBannedPlayers();
                GameProfile gameprofile = playerVictim.getGameProfile();

                if (!banlist.isBanned(gameprofile)) {
                    ProfileBanEntry profilebanentry = new ProfileBanEntry(gameprofile, new Date(), playerVictim.getName().getString(), null, TextColors.LIGHT_RED + "The banhammer has spoken");
                    banlist.addEntry(profilebanentry);
                    ServerPlayerEntity serverplayerentity = playerVictim.getServer().getPlayerList().getPlayerByUUID(gameprofile.getId());
                    if (serverplayerentity != null) {
                        serverplayerentity.connection.disconnect(reason != null ? reason : new TranslationTextComponent("multiplayer.disconnect.banned"));
                    }
                }
            }
        }
        return true;
    }

    public boolean ban(@Nullable String reason) {
        return this.ban(reason != null ? new StringTextComponent(reason) : null);
    }

    public boolean ban() {
        return this.ban((ITextComponent)null);
    }

    public void give(Item item, int count) {
        if (item == null) {
            throw new IllegalArgumentException("Item was not found!");
        }

        net.minecraft.item.Item itemMc = item.wrapper;

        int i = count;

        while(i > 0) {
            int j = Math.min(itemMc.getMaxStackSize(), i);
            i -= j;
            ItemStack itemstack = new ItemStack(itemMc, j);
            boolean flag = wrapper.inventory.addItemStackToInventory(itemstack);
            if (flag && itemstack.isEmpty()) {
                itemstack.setCount(1);
                ItemEntity itemEntity1 = wrapper.dropItem(itemstack, false);
                if (itemEntity1 != null) {
                    itemEntity1.makeFakeItem();
                }

                wrapper.world.playSound(null, wrapper.getPosX(), wrapper.getPosY(), wrapper.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((wrapper.getRNG().nextFloat() - wrapper.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                wrapper.container.detectAndSendChanges();
            } else {
                ItemEntity itemEntity = wrapper.dropItem(itemstack, false);
                if (itemEntity != null) {
                    itemEntity.setNoPickupDelay();
                    itemEntity.setOwnerId(wrapper.getUniqueID());
                }
            }
        }
    }
    public void give(Item item) {

    }
    public void give(String item) {
        this.give(new Item(ForgeRegistries.ITEMS.getValue(new ResourceLocation(item))));
    }
    public void give(String item, int count) {
        this.give(new Item(ForgeRegistries.ITEMS.getValue(new ResourceLocation(item))), count);
    }

    net.minecraft.world.World getMcWorld() {
        return wrapper.world;
    }

    ServerPlayerEntity getPlayerEntity() {
        return wrapper;
    }

    public Entity getEntityLookingAt() {
        ServerPlayerEntity player = wrapper;
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;

        Vector3d vec3d = player.getEyePosition(1.0F);

        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));

        float f6 = f3 * f4;
        float f7 = f2 * f4;

        double d0 = 16;

        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);

        int i = 1;
        int j = 1;

        if (Minecraft.getInstance().world != null) {
            RayTraceResult raytraceresult = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
                vec3d1 = raytraceresult.getHitVec();
            }

            RayTraceResult rayTraceResult1 = ProjectileHelper.rayTraceEntities(Minecraft.getInstance().world, player, vec3d, vec3d1, player.getBoundingBox().grow(16.0D), entity -> !entity.equals(player));
            if (rayTraceResult1 != null) {
                raytraceresult = rayTraceResult1;
            }
            if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
                @SuppressWarnings("ConstantConditions") EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) raytraceresult;
                return Entity.getCorrectEntity(entityRayTraceResult.getEntity());
            }
        }

        return null;
    }
}
