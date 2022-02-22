package com.ultreon.randomthingz.client;

import com.google.common.collect.ImmutableList;
import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class OptionsMenu extends AbstractActionMenu {
    public OptionsMenu() {

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void client() {
        addClient(new ActionMenuItem(new TranslatableComponent("options.skinCustomisation"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new SkinCustomizationScreen(mc.screen, mc.options));
        }));
        addClient(new ActionMenuItem(new TranslatableComponent("options.sounds"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new SoundOptionsScreen(mc.screen, mc.options));
        }));
        addClient(new ActionMenuItem(new TranslatableComponent("options.video"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new VideoSettingsScreen(mc.screen, mc.options));
        }));
        addClient(new ActionMenuItem(new TranslatableComponent("options.controls"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new ControlsScreen(mc.screen, mc.options));
        }));
        addClient(new ActionMenuItem(new TranslatableComponent("options.language"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new LanguageSelectScreen(mc.screen, mc.options, mc.getLanguageManager()));
        }));
        addClient(new ActionMenuItem(new TranslatableComponent("options.chat.title"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new ChatOptionsScreen(mc.screen, mc.options));
        }));
        addClient(new ActionMenuItem(new TranslatableComponent("options.resourcepack"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new PackSelectionScreen(mc.screen, mc.getResourcePackRepository(), this::savePackChanges, mc.getResourcePackDirectory(), new TranslatableComponent("resourcePack.title")));
        }));
        addClient(new ActionMenuItem(new TranslatableComponent("options.accessibility.title"), () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.setScreen(new AccessibilityOptionsScreen(mc.screen, mc.options));
        }));
    }

    @Override
    public void server() {

    }

    @OnlyIn(Dist.CLIENT)
    private void savePackChanges(PackRepository resourcePacks) {
        Minecraft mc = Minecraft.getInstance();
        Options settings = mc.options;
        List<String> packNames = ImmutableList.copyOf(settings.resourcePacks);
        settings.resourcePacks.clear();
        settings.incompatibleResourcePacks.clear();

        for (Pack resourcePackInfo : resourcePacks.getSelectedPacks()) {
            if (!resourcePackInfo.isFixedPosition()) {
                settings.resourcePacks.add(resourcePackInfo.getId());
                if (!resourcePackInfo.getCompatibility().isCompatible()) {
                    settings.incompatibleResourcePacks.add(resourcePackInfo.getId());
                }
            }
        }

        settings.save();
        List<String> oldPackNames = ImmutableList.copyOf(settings.resourcePacks);
        if (!oldPackNames.equals(packNames)) {
            mc.reloadResourcePacks();
        }
    }
}
