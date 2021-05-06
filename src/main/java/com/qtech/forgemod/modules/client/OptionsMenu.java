package com.qtech.forgemod.modules.client;

import com.google.common.collect.ImmutableList;
import com.qtech.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qtech.forgemod.modules.actionmenu.ActionMenuItem;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AccessibilityScreen;
import net.minecraft.client.gui.screen.*;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class OptionsMenu extends AbstractActionMenu {
    @SuppressWarnings("ConstantConditions")
    public OptionsMenu() {
        addItem(new ActionMenuItem(new TranslationTextComponent("options.skinCustomisation"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new CustomizeSkinScreen(mc.currentScreen, mc.gameSettings));
        }));
        addItem(new ActionMenuItem(new TranslationTextComponent("options.sounds"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new OptionsSoundsScreen(mc.currentScreen, mc.gameSettings));
        }));
        addItem(new ActionMenuItem(new TranslationTextComponent("options.video"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new VideoSettingsScreen(mc.currentScreen, mc.gameSettings));
        }));
        addItem(new ActionMenuItem(new TranslationTextComponent("options.controls"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new ControlsScreen(mc.currentScreen, mc.gameSettings));
        }));
        addItem(new ActionMenuItem(new TranslationTextComponent("options.language"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new LanguageScreen(mc.currentScreen, mc.gameSettings, mc.getLanguageManager()));
        }));
        addItem(new ActionMenuItem(new TranslationTextComponent("options.chat.title"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new ChatOptionsScreen(mc.currentScreen, mc.gameSettings));
        }));
        addItem(new ActionMenuItem(new TranslationTextComponent("options.resourcepack"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new PackScreen(mc.currentScreen, mc.getResourcePackList(), this::savePackChanges, mc.getFileResourcePacks(), new TranslationTextComponent("resourcePack.title")));
        }));
        addItem(new ActionMenuItem(new TranslationTextComponent("options.accessibility.title"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new AccessibilityScreen(mc.currentScreen, mc.gameSettings));
        }));
    }

    @SuppressWarnings("deprecation")
    private void savePackChanges(ResourcePackList resourcePacks) {
        Minecraft mc = Minecraft.getInstance();
        GameSettings settings = mc.gameSettings;
        List<String> packNames = ImmutableList.copyOf(settings.resourcePacks);
        settings.resourcePacks.clear();
        settings.incompatibleResourcePacks.clear();

        for(ResourcePackInfo resourcePackInfo : resourcePacks.getEnabledPacks()) {
            if (!resourcePackInfo.isOrderLocked()) {
                settings.resourcePacks.add(resourcePackInfo.getName());
                if (!resourcePackInfo.getCompatibility().isCompatible()) {
                    settings.incompatibleResourcePacks.add(resourcePackInfo.getName());
                }
            }
        }

        settings.writeOptions();
        List<String> oldPackNames = ImmutableList.copyOf(settings.resourcePacks);
        if (!oldPackNames.equals(packNames)) {
            mc.reloadResources();
        }
    }
}
