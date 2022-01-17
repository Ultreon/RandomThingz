package com.ultreon.randomthingz.client.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.util.GraphicsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
            PoseStack matrixStack = event.getPoseStack();
            Minecraft mc = Minecraft.getInstance();

            LocalPlayer player = mc.player;

            if (player != null) {
                ItemStack stack = player.getMainHandItem();
                Item item = stack.getItem();
                if (item instanceof HudItem) {
                    HudItem hudItem = (HudItem) item;
                    GraphicsUtil gu = new GraphicsUtil(mc.getItemRenderer(), matrixStack, mc.font);
                    hudItem.renderHud(gu, mc, stack, player);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void renderHud(GraphicsUtil gu, Minecraft mc, ItemStack stack, LocalPlayer player);

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
//        RenderSystem.translatef(0.0f, 0.0f, 32.0f);
////        this.setBlitOffset(200);
//        this.itemRenderer.zLevel = 200.0f;
//        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
//        if (font == null) font = Minecraft.getInstance().fontRenderer;
//        this.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
//        this.itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y - (stack.isEmpty() ? 0 : 8), altText);
////        this.setBlitOffset(0);
//        this.itemRenderer.zLevel = 0.0f;
//    }

}
