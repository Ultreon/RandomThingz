package com.ultreon.texturedmodels.client;

import com.ultreon.texturedmodels.tileentity.SignFrameBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;

/**
 * Currently unused, does not work
 */
public class EditSignFrameScreen extends SignEditScreen {
    public EditSignFrameScreen(SignFrameBlockEntity teSign) {
        super(teSign, Minecraft.getInstance().isTextFilteringEnabled());
    }
}
