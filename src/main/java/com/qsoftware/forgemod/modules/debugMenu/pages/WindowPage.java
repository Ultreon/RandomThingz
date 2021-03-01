package com.qsoftware.forgemod.modules.debugMenu.pages;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.debugMenu.DebugEntry;
import com.qsoftware.forgemod.modules.debugMenu.DebugPage;
import net.minecraft.util.text.TextFormatting;

public class WindowPage extends DebugPage {
    public WindowPage() {
        super();

        this.addLeft(new DebugEntry("guiScaleFactor", () -> getMultiplier(mainWindow.getGuiScaleFactor())));
        this.addLeft(new DebugEntry("windowSizeScaled", () -> getSize(mainWindow.getScaledWidth(), mainWindow.getScaledHeight())));
        this.addLeft(new DebugEntry("windowSize", () -> getSize(mainWindow.getWidth(),  mainWindow.getHeight())));
        this.addLeft(new DebugEntry("windowHandle", () -> mainWindow.getHandle()));
        this.addLeft(new DebugEntry("framebufferSize", () -> getSize(mainWindow.getFramebufferWidth(), mainWindow.getFramebufferHeight())));
        this.addLeft(new DebugEntry("refreshTate", () -> getFormatted(TextFormatting.GOLD.toString() + mainWindow.getRefreshRate() + TextFormatting.GRAY + " fps")));
        this.addLeft(new DebugEntry("framerateLimit", () -> getFormatted(TextFormatting.GOLD.toString() + mainWindow.getLimitFramerate() + TextFormatting.GRAY + "fps")));
    }

    @Override
    public boolean hasRequiredComponents() {
        return QForgeMod.isClientSide();
    }

//    @Override
//    public List<DebugEntry> getLinesLeft() {
//        List<DebugEntry> list = new ArrayList<>();
//        return list;
//    }
}
