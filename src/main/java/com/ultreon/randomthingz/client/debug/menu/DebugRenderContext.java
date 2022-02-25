package com.ultreon.randomthingz.client.debug.menu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public abstract class DebugRenderContext {
    private int left;
    private int top;
    private int right;

    private final PoseStack pose;
    private final Minecraft mc = Minecraft.getInstance();
    private final int width;
    private final int height;
    private static final int HORIZONTAL_OFFSET = 6;
    private static final int VERTICAL_OFFSET = 6;

    public DebugRenderContext(PoseStack pose, int width, int height) {
        this.pose = pose;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void left(Component text, Object object, Object... objects) {
        drawLeft(pose, text.getString(), left++, object, objects);
    }

    public void left(String text, Object object, Object... objects) {
        drawLeft(pose, text, left++, object, objects);
    }

    public void left(Component text) {
        drawLeft(pose, text.getString(), left++);
    }

    public void left(String text) {
        drawLeft(pose, text, left++);
    }

    public void left() {
        left++;
    }

    public void right(Component text, Object object, Object... objects) {
        drawRight(pose, text.getString(), right++, object, objects);
    }

    public void right(String text, Object object, Object... objects) {
        drawRight(pose, text, right++, object, objects);
    }

    public void right(Component text) {
        drawRight(pose, text.getString(), right++);
    }

    public void right(String text) {
        drawRight(pose, text, right++);
    }

    public void right() {
        right++;
    }

    public void top(Component text) {
        drawTop(pose, text.getString(), top++);
    }

    public void top(String text) {
        drawTop(pose, text, top++);
    }

    public void top() {
        top++;
    }

    private void drawTop(PoseStack pose, String text, int line) {
        drawLine(pose, text, (int) (this.width / 2f - mc.font.width(text) / 2f), VERTICAL_OFFSET + line * (mc.font.lineHeight + 2));
    }

    private void drawLeft(PoseStack pose, String text, int line, Object obj, Object... objects) {
        drawLine(pose, DebugGui.format(text, obj, objects), HORIZONTAL_OFFSET, VERTICAL_OFFSET + line * (mc.font.lineHeight + 2));
    }

    private void drawLeft(PoseStack pose, String text, int line) {
        drawLine(pose, text, HORIZONTAL_OFFSET, VERTICAL_OFFSET + line * (mc.font.lineHeight + 2));
    }

    private void drawRight(PoseStack pose, String text, int line, Object obj, Object... objects) {
        drawLine(pose, DebugGui.format(text, obj, objects), this.width - HORIZONTAL_OFFSET - mc.font.width(text), VERTICAL_OFFSET + line * (mc.font.lineHeight + 2));
    }

    private void drawRight(PoseStack pose, String text, int line) {
        drawLine(pose, text, this.width - HORIZONTAL_OFFSET - mc.font.width(text), VERTICAL_OFFSET + line * (mc.font.lineHeight + 2));
    }
    
    protected abstract void drawLine(PoseStack pose, String text, int x, int y);
}
