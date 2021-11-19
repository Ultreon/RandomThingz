package com.ultreon.randomthingz.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.hud.HudItem;
import com.ultreon.randomthingz.commons.RomanNumber;
import com.ultreon.randomthingz.commons.enums.TextColors;
import com.ultreon.randomthingz.util.GraphicsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
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
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.gui.GuiUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.minecraftforge.fml.client.gui.GuiUtils.drawGradientRect;

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
        CompoundNBT nbt = stack.getOrCreateChildTag("randomthingz");
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
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, @NotNull World dimension, @NotNull LivingEntity entityLiving, int timeLeft) {
        CompoundNBT nbt = stack.getOrCreateChildTag("randomthingz");
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
     * {@linkplain #onUseItem}.
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
        CompoundNBT nbt = stack.getOrCreateChildTag("randomthingz");
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
        CompoundNBT nbt = stack.getOrCreateChildTag("randomthingz");
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
        CompoundNBT nbt = stack.getOrCreateChildTag("randomthingz");
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
        CompoundNBT nbt = stack.getOrCreateChildTag("randomthingz");
        if (nbt.contains("strength", 3)) {
            return nbt.getInt("strength");
        }
        return -1;
    }

    @Override
    public void addInformation(ItemStack stack, @javax.annotation.Nullable World dimensionIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
        CompoundNBT nbt = stack.getOrCreateChildTag("randomthingz");
        if (dimensionIn == null) {
            return;
        }

        if (RandomThingz.isModDev(false)) {
            boolean hasError = false;
            if (!nbt.contains("mana", 5)) {
                hasError = true;
                tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "Mana: N/A"));
                super.addInformation(stack, dimensionIn, tooltip, flagIn);
            }

            if (!nbt.contains("maxMana", 3)) {
                hasError = true;
                tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "Max Mana: N/A"));
                super.addInformation(stack, dimensionIn, tooltip, flagIn);
            }

            if (!nbt.contains("chargeTime", 3)) {
                hasError = true;
                tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "Charge Time: N/A"));
                super.addInformation(stack, dimensionIn, tooltip, flagIn);
            }

            if (!nbt.contains("strength", 3)) {
                hasError = true;
                tooltip.add(new StringTextComponent(TextColors.RED + "" + TextColors.BOLD + "Strength: N/A"));
                super.addInformation(stack, dimensionIn, tooltip, flagIn);
            }

            if (hasError) {
                return;
            }
        }


        if (nbt.contains("mana", 5)) {
            if (nbt.contains("maxMana", 3)) {
                float mana = nbt.getFloat("mana");
                int maxMana = nbt.getInt("maxMana");
                tooltip.add(new StringTextComponent(TextColors.LIGHT_GRAY + "" + TextColors.BOLD + "Mana: " + TextColors.LIGHT_GRAY + "" + TextColors.ITALIC + Math.round(mana) + " / " + maxMana));
            }
        }
        if (nbt.contains("chargeTime", 3)) {
            int chargeTime = nbt.getInt("chargeTime");
            tooltip.add(new StringTextComponent(TextColors.LIGHT_GRAY + "" + TextColors.BOLD + "Charge Time: " + TextColors.LIGHT_GRAY + "" + TextColors.ITALIC + chargeTime));
        }
        if (nbt.contains("strength", 3)) {
            int strength = nbt.getInt("strength");
            tooltip.add(new StringTextComponent(TextColors.LIGHT_GRAY + "" + TextColors.BOLD + "Strength: " + TextColors.LIGHT_GRAY + "" + TextColors.ITALIC + RomanNumber.toRoman(strength)));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderHud(GraphicsUtil gu, Minecraft mc, ItemStack stack, ClientPlayerEntity player) {
        int height = mc.getMainWindow().getScaledHeight();

        World dimensionIn = player.getEntityDimension();
        CompoundNBT nbt = stack.getOrCreateChildTag("randomthingz");
        if (dimensionIn == null) {
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

        drawItemTooltipText(gu, val, stack, Arrays.asList(new StringTextComponent(TextFormatting.BLUE + I18n.format(stack.getTranslationId())), new StringTextComponent(TextFormatting.GRAY + "" + Math.round(mana) + " / " + Math.round(maxMana))), 0, height, 96, 10, -10, mc.getMainWindow().getWidth() - 20, mc.getMainWindow().getHeight(), -1, GuiUtils.DEFAULT_BACKGROUND_COLOR, GuiUtils.DEFAULT_BORDER_COLOR_START, GuiUtils.DEFAULT_BORDER_COLOR_END, mc.fontRenderer);
        MatrixStack matrixStack = gu.getMatrixStack();
    }

    /**
     * Use this version if calling from somewhere where ItemStack context is available.
     *
     * @see GuiUtils#drawHoveringText(MatrixStack, List, int, int, int, int, int, int, int, int, FontRenderer)
     */
    //TODO, Validate rendering is the same as the original
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("deprecation")
    public void drawItemTooltipText(GraphicsUtil gu, int val, @Nonnull final ItemStack stack, List<? extends ITextProperties> textLines, int mouseX, int mouseY,
                                    int minWidth, int xOffset, int yOffset,
                                    int screenWidth, int screenHeight, int maxTextWidth,
                                    int backgroundColor, int borderColorStart, int borderColorEnd, FontRenderer font) {
        MatrixStack matrixStack = gu.getMatrixStack();
        if (!textLines.isEmpty())
        {
            RenderTooltipEvent.Pre event = new RenderTooltipEvent.Pre(stack, textLines, matrixStack, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font);
            if (MinecraftForge.EVENT_BUS.post(event))
                return;
            mouseX = event.getX();
            mouseY = event.getY();
            screenWidth = event.getScreenWidth();
            screenHeight = event.getScreenHeight();
            maxTextWidth = event.getMaxWidth();
            font = event.getFontRenderer();

            RenderSystem.disableRescaleNormal();
            RenderSystem.disableDepthTest();
            int tooltipTextWidth = minWidth; // Added minWidth.

            for (ITextProperties textLine : textLines)
            {
                int textLineWidth = font.getStringPropertyWidth(textLine);
                if (textLineWidth > tooltipTextWidth)
                    tooltipTextWidth = textLineWidth;
            }

            boolean needsWrap = false;

            int titleLinesCount = 1;
            int tooltipX = mouseX + 12;
            if (tooltipX + tooltipTextWidth + 4 > screenWidth)
            {
                tooltipX = mouseX - 16 - tooltipTextWidth;
                if (tooltipX < 4) // if the tooltip doesn't fit on the screen
                {
                    if (mouseX > screenWidth / 2)
                        tooltipTextWidth = mouseX - 12 - 8;
                    else
                        tooltipTextWidth = screenWidth - 16 - mouseX;
                    needsWrap = true;
                }
            }

            if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth)
            {
                tooltipTextWidth = maxTextWidth;
                needsWrap = true;
            }

            if (needsWrap)
            {
                int wrappedTooltipWidth = 0;
                List<ITextProperties> wrappedTextLines = new ArrayList<>();
                for (int i = 0; i < textLines.size(); i++)
                {
                    ITextProperties textLine = textLines.get(i);
                    List<ITextProperties> wrappedLine = font.getCharacterManager().func_238362_b_(textLine, tooltipTextWidth, Style.EMPTY);
                    if (i == 0)
                        titleLinesCount = wrappedLine.size();

                    for (ITextProperties line : wrappedLine)
                    {
                        int lineWidth = font.getStringPropertyWidth(line);
                        if (lineWidth > wrappedTooltipWidth)
                            wrappedTooltipWidth = lineWidth;
                        wrappedTextLines.add(line);
                    }
                }
                tooltipTextWidth = wrappedTooltipWidth;
                textLines = wrappedTextLines;

                if (mouseX > screenWidth / 2)
                    tooltipX = mouseX - 16 - tooltipTextWidth;
                else
                    tooltipX = mouseX + 12;
            }

            int tooltipY = mouseY - 12;
            int tooltipHeight = 8;

            if (textLines.size() > 1)
            {
                tooltipHeight += (textLines.size() - 1) * 10;
                if (textLines.size() > titleLinesCount)
                    tooltipHeight += 2; // gap between title lines and next lines
            }

            if (tooltipY < 4)
                tooltipY = 4;
            else if (tooltipY + tooltipHeight + 4 > screenHeight)
                tooltipY = screenHeight - tooltipHeight - 4;

            final int zLevel = 10; // Was 400
            RenderTooltipEvent.Color colorEvent = new RenderTooltipEvent.Color(stack, textLines, matrixStack, tooltipX, tooltipY, font, backgroundColor, borderColorStart, borderColorEnd);
            MinecraftForge.EVENT_BUS.post(colorEvent);
            backgroundColor = colorEvent.getBackground();
            borderColorStart = colorEvent.getBorderStart();
            borderColorEnd = colorEvent.getBorderEnd();

            tooltipX += xOffset;
            tooltipY += yOffset;

            matrixStack.push();
            Matrix4f mat = matrixStack.getLast().getMatrix();

            //TODO, lots of unnessesary GL calls here, we can buffer all these together.
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);

            drawGradientRect(mat, zLevel, tooltipX, tooltipY - 4 - 24, tooltipX + 24, tooltipY - 4, 0xff9f9f9f, 0xff9f9f9f);
            drawGradientRect(mat, zLevel, tooltipX, tooltipY - 4 - 24, tooltipX + 24, tooltipY - 4, 0xff9f9f9f, 0xff9f9f9f);

            drawGradientRect(mat, zLevel, tooltipX + 1, tooltipY - 4 - 23, tooltipX + 23, tooltipY - 5, 0xff5f5f5f, 0xff5f5f5f);
            drawGradientRect(mat, zLevel, tooltipX + 1, tooltipY - 4 - 23, tooltipX + 23, tooltipY - 5, 0xff5f5f5f, 0xff5f5f5f);

            drawGradientRect(mat, zLevel, tooltipX + 2, tooltipY - 4 - 22, tooltipX + 22, tooltipY - 6, 0xff9f9f9f, 0xff9f9f9f);
            drawGradientRect(mat, zLevel, tooltipX + 2, tooltipY - 4 - 22, tooltipX + 22, tooltipY - 6, 0xff9f9f9f, 0xff9f9f9f);

            gu.drawItemStack(stack, tooltipX + 8, tooltipY - 4 - 8, "");

            Screen.fill(matrixStack, tooltipX + 16 - 3, tooltipY + tooltipHeight + 4, tooltipX + 80 + 2, tooltipY + tooltipHeight + 4 + 2 + 4 + 3, 0xff9f9f9f);
            Screen.fill(matrixStack, tooltipX + 16 - 2, tooltipY + tooltipHeight + 4 + 1, tooltipX + 80 + 1, tooltipY + tooltipHeight + 4 + 2 + 4 + 2, 0xff5f5f5f);
            Screen.fill(matrixStack, tooltipX + 16 - 1, tooltipY + tooltipHeight + 4 + 2, tooltipX + 80, tooltipY + tooltipHeight + 4 + 2 + 4 + 1, 0xff9f9f9f);

            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
//        textureManager.bindTexture(new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/wand/background.png"));
//        gu.blit(0, height - 64, 128, 64, 0, 0, 128, 64, 128, 64);

            textureManager.bindTexture(new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/wand/bar.png"));
            gu.blit(16, tooltipY + tooltipHeight + 4, 64, 2, 0, 2, 64, 1, 64, 3);

            textureManager.bindTexture(new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/wand/bar.png"));
            gu.blit(16, tooltipY + tooltipHeight + 4 - 1, 64, 2, 0, 1, 64, 1, 64, 3);

            textureManager.bindTexture(new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/wand/bar.png"));
            gu.blit(16, tooltipY + tooltipHeight + 4, val, 2, 0, 1, val, 1, 64, 3);

            textureManager.bindTexture(new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/wand/bar.png"));
            gu.blit(16, tooltipY + tooltipHeight + 4 - 1, val, 2, 0, 0, val, 1, 64, 3);

//            gu.drawItemStack(stack, 56, tooltipY - 60, "");
//            gu.drawCenteredString(Math.round(mana) + " / " + Math.round(maxMana), 64, height - 24, 0xe97fff);
            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostBackground(stack, textLines, matrixStack, tooltipX, tooltipY, font, tooltipTextWidth, tooltipHeight));

            IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
            matrixStack.translate(0.0D, 0.0D, zLevel);

            int tooltipTop = tooltipY;

            for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber)
            {
                ITextProperties line = textLines.get(lineNumber);
                if (line != null)
                    font.drawEntityText(LanguageMap.getInstance().func_241870_a(line), (float)tooltipX, (float)tooltipY, -1, true, mat, renderType, false, 0, 15728880);

                if (lineNumber + 1 == titleLinesCount)
                    tooltipY += 2;

                tooltipY += 10;
            }

            renderType.finish();
            matrixStack.pop();

            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostText(stack, textLines, matrixStack, tooltipX, tooltipTop, font, tooltipTextWidth, tooltipHeight));

            RenderSystem.enableDepthTest();
            RenderSystem.enableRescaleNormal();
        }
    }

}
