package com.qsoftware.scriptjs;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.enums.TextColors;
import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
//import net.minecraft.item.ItemStack;
import net.minecraft.server.management.BanList;
import net.minecraft.server.management.ProfileBanEntry;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.Locale;

@SuppressWarnings({"deprecation", "unused"})
public class Player extends LivingBeing implements Formattable {
    private final PlayerEntity wrapper;

    public static Player getCorrectEntity(PlayerEntity player) {
        if (QForgeMod.isClientSide()) {
            if (player instanceof ClientPlayerEntity) {
                return new ClientPlayer((ClientPlayerEntity) player);
            }
        }
        if (QForgeMod.isServerSide()) {
            if (player instanceof ServerPlayerEntity) {
                return new ServerPlayer((ServerPlayerEntity) player);
            }
        }
        return new Player(player);
    }

    protected Player(PlayerEntity wrapper) {
        super(wrapper);

        this.wrapper = wrapper;
    }

    public void setGameType(String gameType) {
        this.wrapper.setGameType(GameType.valueOf(gameType.toUpperCase(Locale.ROOT)));
    }
    public void setGameType(GameType gameType) {
        this.wrapper.setGameType(gameType);
    }
    public void setHunger(int foodLevel) {
        this.wrapper.getFoodStats().setFoodLevel(foodLevel);
    }
    public void setSaturation(float saturation) {
        this.wrapper.getFoodStats().setFoodSaturationLevel(saturation);
    }
    public float getSaturation() {
        return this.wrapper.getFoodStats().getSaturationLevel();
    }
    public int getHunger() {
        return this.wrapper.getFoodStats().getFoodLevel();
    }
    public int getExpLevel() {
        return this.wrapper.experienceLevel;
    }

    public void allowFlying() {
        this.wrapper.abilities.allowFlying = true;
    }
    public void disallowFlying() {
        this.wrapper.abilities.allowFlying = false;
    }
    public boolean allowsFlying() {
        return this.wrapper.abilities.allowFlying;
    }

    public void sendMessage(String message) {
        this.wrapper.sendMessage(new StringTextComponent(message), this.wrapper.getUniqueID());
    }
    public void sendMessage(ITextComponent text) {
        this.wrapper.sendMessage(text, this.wrapper.getUniqueID());
    }

    public boolean ban(@Nullable ITextComponent reason) {
        PlayerEntity playerVictim = this.wrapper;
        if (playerVictim.world instanceof net.minecraft.world.server.ServerWorld) {
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
            net.minecraft.item.ItemStack stack = new net.minecraft.item.ItemStack(itemMc, j);
            boolean couldAddStack = this.wrapper.inventory.addItemStackToInventory(stack);
            if (couldAddStack && stack.isEmpty()) {
                stack.setCount(1);
                net.minecraft.entity.item.ItemEntity itemEntity1 = this.wrapper.dropItem(stack, false);
                if (itemEntity1 != null) {
                    itemEntity1.makeFakeItem();
                }

                this.wrapper.world.playSound(null, this.wrapper.getPosX(), this.wrapper.getPosY(), this.wrapper.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((wrapper.getRNG().nextFloat() - this.wrapper.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                this.wrapper.container.detectAndSendChanges();
            } else {
                ItemEntity itemEntity = this.wrapper.dropItem(stack, false);
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

    World getMcWorld() {
        return this.wrapper.getEntityWorld();
    }

    PlayerEntity getWrapper() {
        return this.wrapper;
    }

    public Entity getEntityLookingAt() {
        PlayerEntity player = this.wrapper;
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
                return getCorrectEntity(entityRayTraceResult.getEntity());
            }
        }

        return null;
    }

    public boolean isServerWorld() {
        return wrapper.isServerWorld();
    }

    public boolean getAllowFlying() {
        return wrapper.abilities.allowFlying;
    }

    public void setAllowFlying(boolean allow) {
        wrapper.abilities.allowFlying = allow;
    }

    public boolean getAllowEdit() {
        return wrapper.abilities.allowEdit;
    }

    public void setAllowEdit(boolean allow) {
        wrapper.abilities.allowEdit = allow;
    }

    public boolean getDisableDamage() {
        return wrapper.abilities.disableDamage;
    }

    public void setDisableDamage(boolean disable) {
        wrapper.abilities.disableDamage = disable;
    }

    public boolean getIsCreativeMode() {
        return wrapper.abilities.isCreativeMode;
    }

    public void setIsCreativeMode(boolean enable) {
        wrapper.abilities.isCreativeMode = enable;
    }

    public boolean getIsFlying() {
        return wrapper.abilities.isFlying;
    }

    public void setIsFlying(boolean enable) {
        wrapper.abilities.isFlying = enable;
    }

    public float getFlySpeed() {
        return wrapper.abilities.getFlySpeed();
    }

    public float getWalkSpeed() {
        return wrapper.abilities.getWalkSpeed();
    }

    public ItemEntity dropItem(ItemStack stack, boolean unused) {
        return wrapper.dropItem(stack.wrapper, unused);
    }

    public ItemEntity dropItem(ItemStack stack, boolean dropAround, boolean traceItem) {
        return wrapper.dropItem(stack.wrapper, dropAround, traceItem);
    }

    public boolean drop(boolean all) {
        return wrapper.drop(all);
    }

    public int getExperienceLevel() {
        return wrapper.experienceLevel;
    }

    public void setExperienceLevel(int level) {
        wrapper.experienceLevel = level;
    }

    public int getExperienceTotal() {
        return wrapper.experienceTotal;
    }

    public void setExperienceTotal(int value) {
        wrapper.experienceTotal = value;
    }

    public float getExperience() {
        return wrapper.experience;
    }

    public void setExperience(float value) {
        wrapper.experience = value;
    }

    public boolean hasReducedDebug() {
        return wrapper.hasReducedDebug();
    }

    public boolean isAllowEdit() {
        return wrapper.isAllowEdit();
    }

    public boolean isCreative() {
        return wrapper.isCreative();
    }

    public boolean isPlayerFullyAsleep() {
        return wrapper.isPlayerFullyAsleep();
    }

    public boolean isPushedByWater() {
        return wrapper.isPushedByWater();
    }

    public boolean isSpectator() {
        return wrapper.isSpectator();
    }

    public boolean isSecondaryUseActive() {
        return wrapper.isSecondaryUseActive();
    }

    public boolean isUser() {
        return wrapper.isUser();
    }

    public boolean isWearing(PlayerModelPart part) {
        return wrapper.isWearing(part);
    }

    public void jump() {
        wrapper.jump();
    }

    public boolean isNoClipping() {
        return wrapper.noClip;
    }

    public Container getOpenContainer() {
        return new Container(wrapper.openContainer);
    }

    public void openBook(ItemStack stack, Hand hand) {
        wrapper.openBook(stack.wrapper, hand);
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        wrapper.playSound(sound.wrapper, volume, pitch);
    }

    public boolean replaceItemInInventory(int slot, ItemStack stack) {
        return wrapper.replaceItemInInventory(slot, stack.wrapper);
    }

    public boolean shouldHeal() {
        return wrapper.shouldHeal();
    }

    public Either<PlayerEntity.SleepResult, Unit> trySleep(BlockPos at) {
        return wrapper.trySleep(at);
    }

    public int getXpCooldown() {
        return wrapper.xpCooldown;
    }

    public int getXpBarCapacity() {
        return wrapper.xpBarCap();
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Player", getName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Player", getName());
    }
}
