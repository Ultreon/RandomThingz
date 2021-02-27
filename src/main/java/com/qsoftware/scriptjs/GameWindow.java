package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.client.MainWindow;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("unused")
public class GameWindow implements Formattable {
    private final MainWindow wrapper;

    public GameWindow(MainWindow wrapper) {
        this.wrapper = wrapper;
    }

    public Size getSize() {
        return new Size(wrapper.getWidth(), wrapper.getHeight());
    }

    public int getRefreshRate() {
        return wrapper.getRefreshRate();
    }

    public int getFramerateLimit() {
        return wrapper.getLimitFramerate();
    }

    public double getGuiScale() {
        return wrapper.getGuiScaleFactor();
    }

    public double getHandle() {
        return wrapper.getHandle();
    }

    public Size getScaledSize() {
        return new Size(wrapper.getScaledWidth(), wrapper.getScaledHeight());
    }

    public boolean isFullscreen() {
        return wrapper.isFullscreen();
    }

    public void setWindowTitle(String title) {
        wrapper.setWindowTitle(title);
    }

    public void setFramerateLimit(int limit) {
        wrapper.setFramerateLimit(limit);
    }

    public void setFullscreen(boolean enable) {
        if (isFullscreen() && enable) {
            return;
        }
        if (!isFullscreen() && !enable) {
            return;
        }
        wrapper.toggleFullscreen();
    }

    public void setGuiScale(double scale) {
        wrapper.setGuiScale(scale);
    }

    public void toggleFullscreen() {
        wrapper.toggleFullscreen();
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("GameWindow", wrapper.getHandle());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("GameWindow", wrapper.getHandle());
    }
}
