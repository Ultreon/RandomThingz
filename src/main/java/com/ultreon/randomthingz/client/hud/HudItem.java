package com.ultreon.randomthingz.client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.util.GraphicsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public abstract class HudItem extends Item {
    public HudItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            MatrixStack matrixStack = event.getMatrixStack();
            Minecraft mc = Minecraft.getInstance();

            ClientPlayerEntity player = mc.player;

            if (player != null) {
                ItemStack stack = player.getHeldItemMainhand();
                Item item = stack.getItem();
                if (item instanceof HudItem) {
                    HudItem hudItem = (HudItem) item;
                    GraphicsUtil gu = new GraphicsUtil(mc.getItemRenderer(), matrixStack, mc.fontRenderer);
                    hudItem.renderHud(gu, mc, stack, player);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void renderHud(GraphicsUtil gu, Minecraft mc, ItemStack stack, ClientPlayerEntity player);

//    protected final void drawCenteredString(MatrixStack matrixStack, FontRenderer fontRenderer, String text, float x, float y, int color) {
//        drawCenteredString(matrixStack, fontRenderer, text, x, y, color, false);
//    }
//
//    protected final void drawCenteredString(MatrixStack matrixStack, FontRenderer fontRenderer, String text, float x, float y, int color, boolean shadow) {
//        if (shadow) {
//            fontRenderer.drawStringWithShadow(matrixStack, text, (float) (x - fontRenderer.getStringWidth(text) / 2), y, color);
//        } else {
//            fontRenderer.drawString(matrixStack, text, (float) (x - fontRenderer.getStringWidth(text) / 2), y, color);
//        }
//    }
//
//    /**
//     * Draws an ItemStack.
//     *
//     * The z index is increased by 32 (and not decreased afterwards), and the item is then rendered at z=200.
//     */
//    protected final void drawItemStack(ItemStack stack, int x, int y, String altText) {
//        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
//
//        RenderSystem.translatef(0.0F, 0.0F, 32.0F);
////        this.setBlitOffset(200);
//        this.itemRenderer.zLevel = 200.0F;
//        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
//        if (font == null) font = Minecraft.getInstance().fontRenderer;
//        this.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
//        this.itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y - (stack.isEmpty() ? 0 : 8), altText);
////        this.setBlitOffset(0);
//        this.itemRenderer.zLevel = 0.0F;
//    }

}
