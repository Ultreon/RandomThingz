package com.ultreon.randomthingz.pc.common.computerapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UIComponent implements IRenderable {
    private int x;
    private int y;
    private int width;
    private int height;
}
