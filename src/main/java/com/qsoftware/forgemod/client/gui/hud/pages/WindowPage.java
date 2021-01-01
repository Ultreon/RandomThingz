package com.qsoftware.forgemod.client.gui.hud.pages;

import com.qsoftware.forgemod.client.gui.hud.DebugEntry;
import com.qsoftware.forgemod.client.gui.hud.DebugPage;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class WindowPage extends DebugPage {
    public WindowPage(String modId, String name) {
        super(modId, name);
    }

//    @Override
//    public List<DebugEntry> getLinesLeft() {
//        List<DebugEntry> list = new ArrayList<>();
//        list.add(new DebugEntry("guiScaleFactor", getMultiplier(mainWindow.getGuiScaleFactor())));
//        list.add(new DebugEntry("windowSizeScaled", getSize(mainWindow.getScaledWidth(), mainWindow.getScaledHeight())));
//        list.add(new DebugEntry("windowSize", getSize(mainWindow.getWidth(),  mainWindow.getHeight())));
//        list.add(new DebugEntry("windowHandle", mainWindow.getHandle()));
//        list.add(new DebugEntry("framebufferSize", getSize(mainWindow.getFramebufferWidth(), mainWindow.getFramebufferHeight())));
//        list.add(new DebugEntry("refreshTate", getFormatted(TextFormatting.GOLD.toString() + mainWindow.getRefreshRate() + TextFormatting.GRAY + " fps")));
//        list.add(new DebugEntry("framerateLimit", getFormatted(TextFormatting.GOLD.toString() + mainWindow.getLimitFramerate() + TextFormatting.GRAY + "fps")));
//        return list;
//    }
}
