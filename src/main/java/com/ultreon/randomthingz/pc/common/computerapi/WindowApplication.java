package com.ultreon.randomthingz.pc.common.computerapi;

import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class WindowApplication extends Application {
    @Getter
    private final Computer computer;
    @Getter
    private final Dimension size;
    @Getter
    private final Point pos;
    private final List<UIComponent> children = new ArrayList<>();

    public WindowApplication(Computer computer) {
        this.computer = computer;
        this.size = this.getPreferredSize();
        this.pos = this.getPreferredPosition();
    }

    public abstract Point getPreferredPosition();

    public abstract Dimension getPreferredSize();

    protected void render(PCGraphics gfx) {
        for (UIComponent component : this.children) {
            component.render(gfx);
        }
    }

    protected <T extends UIComponent> T addComponent(T component) {
        this.children.add(component);
        return component;
    }

    public List<UIComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }
}
