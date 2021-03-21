package com.qsoftware.forgemod.pc.apps.desktop;

import com.qsoftware.forgemod.pc.common.computerapi.App;
import com.qsoftware.forgemod.pc.common.computerapi.Computer;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DesktopApp extends App {
    public DesktopApp(Computer computer) {
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
    public void init() {
//        this.wallpaper = getComputer().getFileSystem().getFile();
    }
}
