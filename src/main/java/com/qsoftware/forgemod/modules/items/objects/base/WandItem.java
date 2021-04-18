package com.qsoftware.forgemod.modules.items.objects.base;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.RomanNumber;
import com.qsoftware.forgemod.common.enums.TextColors;
import com.qsoftware.forgemod.hud.HudItem;
import com.qsoftware.forgemod.util.GraphicsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public abstract class WandItem extends HudItem {
    private final int maxMana;
    private final int chargeTime;
    private ItemRenderer itemRenderer;

    public WandItem(int maxMana, int chargeTime, Properties properties) {
        super(properties);
        this.maxStackSize = 1;
        this.maxMana = maxMana;
        this.chargeTime = chargeTime;
    }

    @Override
    public void inventoryTick(ItemStack stack, @NotNull World dimensionIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (!nbt.contains("mana", 5)) {
            nbt.putFloat("mana", (float) this.maxMana);
        }
        if (!nbt.contains("maxMana", 3)) {
            nbt.putInt("maxMana", this.maxMana);
        }
        if (!nbt.contains("chargeTime", 3)) {
            nbt.putInt("chargeTime", this.chargeTime);
        }
        if (!nbt.contains("strength", 3)) {
            nbt.putInt("strength", 1);
        }
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onPlayerStoppedUsing(ItemStack stack, @NotNull World dimension, @NotNull LivingEntity entityLiving, int timeLeft) {
        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (!nbt.contains("mana", 5)) {
            nbt.putFloat("mana", (float) maxMana);
        } else {
            float mana = nbt.getFloat("mana");
            if (mana <= 0f) {
                if (entityLiving instanceof PlayerEntity) {
                    ((PlayerEntity) entityLiving).sendStatusMessage(new StringTextComponent(TextColors.LIGHT_RED.toString() + TextColors.BOLD + "No mana left."), true);
                }
            } else {
                float timeLeft1 = (float) timeLeft;
                float usedTime = getUseDuration(stack) - timeLeft1; // 10 = 72000 - 71990
                if (usedTime > (float) chargeTime) { // 10 > 20
                    usedTime = (float) chargeTime; // 10 = 20
                }
                if (mana - usedTime < 0f) { // 5 - 10 < 0
                    timeLeft1 = (int) (getUseDuration(stack) - mana); // 71995 = 72000 - 5
                    ((PlayerEntity) entityLiving).sendStatusMessage(new StringTextComponent(TextColors.LIGHT_RED.toString() + TextColors.BOLD + "Mana partial used, was not enough left."), true);
                }

                nbt.putFloat("mana", Math.max(mana - getTimeUsed(stack, timeLeft1), 0));
                activate(stack, dimension, entityLiving, getCharge(stack, timeLeft1));
            }
        }
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    public float getTimeUsed(ItemStack stack, float timeLeft) {
        float usedTime = getUseDuration(stack) - timeLeft;
        if (usedTime > chargeTime) {
            usedTime = chargeTime;
        }

        return usedTime;
    }

    /**
     * Gets the velocity of the arrow entity from the bow's charge
     */
    public float getCharge(ItemStack stack, float timeLeft) {
        float usedTime = getUseDuration(stack) - timeLeft;
        if (usedTime > chargeTime) {
            usedTime = chargeTime;
        }

        return usedTime / (float) chargeTime;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @ParametersAreNonnullByDefault
    public @NotNull UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onUseItem}.
     */
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World dimensionIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !playerIn.findAmmo(itemstack).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, dimensionIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.abilities.isCreativeMode && !flag) {
            return ActionResult.resultFail(itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    public abstract void activate(ItemStack stack, @NotNull World dimensionIn, @NotNull LivingEntity livingIn, float charge);

    public int getMaxMana() {
        return maxMana;
    }

    /**
     * Get mana.
     *
     * @param stack stack to get mana from.
     * @return the amount of mana, or -1 if it's invalid.
     */
    public int getMana(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (nbt.contains("mana", 3)) {
            return nbt.getInt("mana");
        }
        return -1;
    }

    /**
     * Get maximum mana.
     *
     * @param stack stack to get the maximum mana from.
     * @return the amount of mana, or -1 if it's invalid.
     */
    public int getMaxMana(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (nbt.contains("maxMana", 3)) {
            return nbt.getInt("maxMana");
        }
        return -1;
    }

    /**
     * Get the charge time (ticks).
     *
     * @param stack stack to get the charge time from.
     * @return the charge time, or -1 if it's invalid.
     */
    public int getChargeTime(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (nbt.contains("chargeTime", 3)) {
            return nbt.getInt("chargeTime");
        }
        return -1;
    }

    /**
     * Get the charge time (ticks).
     *
     * @param stack stack to get the charge time from.
     * @return the charge time, or -1 if it's invalid.
     */
    public int getStrength(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (nbt.contains("strength", 3)) {
            return nbt.getInt("strength");
        }
        return -1;
    }

    @Override
    public void addInformation(ItemStack stack, @javax.annotation.Nullable World dimensionIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (dimensionIn == null) {
            return;
        }

        if (!nbt.contains("mana", 5)) {
            tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "INVALID DATA"));
            super.addInformation(stack, dimensionIn, tooltip, flagIn);
            return;
        }

        if (!nbt.contains("maxMana", 3)) {
            tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "INVALID DATA"));
            super.addInformation(stack, dimensionIn, tooltip, flagIn);
            return;
        }

        if (!nbt.contains("chargeTime", 3)) {
            tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "INVALID DATA"));
            super.addInformation(stack, dimensionIn, tooltip, flagIn);
            return;
        }

        if (!nbt.contains("strength", 3)) {
            tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "INVALID DATA"));
            super.addInformation(stack, dimensionIn, tooltip, flagIn);
            return;
        }

        float mana = nbt.getFloat("mana");
        int maxMana = nbt.getInt("maxMana");
        int chargeTime = nbt.getInt("chargeTime");
        int strength = nbt.getInt("strength");

        tooltip.add(new StringTextComponent(TextColors.LIGHT_GRAY + "" + TextColors.BOLD + "Mana: " + TextColors.LIGHT_GRAY + "" + TextColors.ITALIC + Math.round(mana) + " / " + maxMana));
        tooltip.add(new StringTextComponent(TextColors.LIGHT_GRAY + "" + TextColors.BOLD + "Charge Time: " + TextColors.LIGHT_GRAY + "" + TextColors.ITALIC + chargeTime));
        tooltip.add(new StringTextComponent(TextColors.LIGHT_GRAY + "" + TextColors.BOLD + "Strength: " + TextColors.LIGHT_GRAY + "" + TextColors.ITALIC + RomanNumber.toRoman(strength)));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderHud(GraphicsUtil gu, Minecraft mc, ItemStack stack, ClientPlayerEntity player) {
        int height = mc.getMainWindow().getScaledHeight();

        World dimensionIn = player.getEntityDimension();

        CompoundNBT nbt = stack.getOrCreateChildTag("qforgemod");
        if (dimensionIn == null) {
            return;
        }

        if (!nbt.contains("mana", 5)) {
//            tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "INVALID DATA"));
//            super.addInformation(stack, dimensionIn, tooltip, flagIn);
            return;
        }

        if (!nbt.contains("maxMana", 3)) {
//            tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "INVALID DATA"));
//            super.addInformation(stack, dimensionIn, tooltip, flagIn);
            return;
        }

        if (!nbt.contains("chargeTime", 3)) {
//            tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "INVALID DATA"));
//            super.addInformation(stack, dimensionIn, tooltip, flagIn);
            return;
        }

        if (!nbt.contains("strength", 3)) {
//            tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "INVALID DATA"));
//            super.addInformation(stack, dimensionIn, tooltip, flagIn);
            return;
        }

        float mana = nbt.getFloat("mana");
        int maxMana = nbt.getInt("maxMana");
        int chargeTime = nbt.getInt("chargeTime");
        int strength = nbt.getInt("strength");

        int val;
        if (maxMana == 0) {
            val = 0;
        }

        val = (int)(64d * mana / maxMana);

        TextureManager textureManager = mc.getTextureManager();
        textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/wand/background.png"));
        gu.blit(0, height - 64, 128, 64, 0, 0, 128, 64, 128, 64);

        textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/wand/bar.png"));
        gu.blit(33, height - 11, 64, 2, 0, 2, 64, 1, 64, 3);

        textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/wand/bar.png"));
        gu.blit(32, height - 12, 64, 2, 0, 1, 64, 1, 64, 3);

        textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/wand/bar.png"));
        gu.blit(33, height - 11, val, 2, 0, 1, val, 1, 64, 3);

        textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/wand/bar.png"));
        gu.blit(32, height - 12, val, 2, 0, 0, val, 1, 64, 3);

        gu.drawItemStack(stack, 56, height - 60, "");
        gu.drawCenteredString(Math.round(mana) + " / " + Math.round(maxMana), 64, height - 24, 0xe97fff);
    }
}
