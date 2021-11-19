package com.ultreon.randomthingz.pc.common.computerapi;

import java.awt.*;

public abstract class PCGraphics {
    public abstract void renderCenteredText(int x, int y, String text);

    public abstract void renderLeftAlignedText(int x, int y, String text);

    public abstract void renderRightAlignedText(int x, int y, String text);

    public abstract void renderRectangle(int x, int y, int w, int h, Color outline);

    public abstract void renderRectangle(int x, int y, int w, int h, Color fill, Color outline);

    public abstract void renderPixel(int x, int y, Color color);
}
