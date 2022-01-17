package com.ultreon.randomthingz.pc.apps.desktop;

import com.ultreon.randomthingz.pc.common.computerapi.*;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DesktopApplication extends WindowApplication {
    private AbstractFileIO wallpaper;

    public DesktopApplication(Computer computer) {
        super(computer);
    }

    @Override
    public Point getPreferredPosition() {
        return new Point(0, 0);
    }

    @Override
    public Dimension getPreferredSize() {
        return getComputer().getScreen().getSize();
    }

    @Override
    public void initialize() {
        this.wallpaper = getComputer().getFileSystem().getFile(new File("/system/user/data/wallpaper.png"));
    }

    @Override
    public void render(PCGraphics gfx) {
        super.render(gfx);
    }
}
