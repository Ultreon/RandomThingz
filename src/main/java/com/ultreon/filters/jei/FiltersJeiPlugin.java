package com.ultreon.filters.jei;

import com.ultreon.filters.Filters;
import com.ultreon.filters.Reference;
import com.ultreon.filters.gui.widget.button.IconButton;
import com.ultreon.filters.gui.widget.button.TagButton;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ocelot
 */
@JeiPlugin
public class FiltersJeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Reference.MOD_ID, Reference.MOD_ID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(CreativeModeInventoryScreen.class, new IGuiContainerHandler<CreativeModeInventoryScreen>() {
            @Override
            public List<Rect2i> getGuiExtraAreas(CreativeModeInventoryScreen screen) {
                if (Filters.get().hasFilters(CreativeModeTab.TABS[screen.getSelectedTab()])) {
                    List<Rect2i> areas = new ArrayList<>();

                    /* Tabs */
                    areas.add(new Rect2i(screen.getGuiLeft() - 28, screen.getGuiTop() + 10, 56, 230));

                    /* Buttons */
                    for (GuiEventListener child : screen.children) {
                        if (child instanceof IconButton || child instanceof TagButton) {
                            Button button = (Button) child;
                            areas.add(new Rect2i(button.x, button.y, button.getWidth(), button.height));
                        }
                    }

                    return areas;
                }
                return Collections.emptyList();
            }
        });
    }
}
