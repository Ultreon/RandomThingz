package com.ultreon.filters;

import com.ultreon.filters.gui.widget.button.IconButton;
import com.ultreon.filters.gui.widget.button.TagButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.glfw.GLFW;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MrCrayfish
 */
public class Events {
    private static final ResourceLocation ICONS = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");
    private static final Map<CreativeModeTab, Integer> scrollMap = new HashMap<>();

    private boolean updatedFilters;
    private final List<TagButton> buttons = new ArrayList<>();
    private final Map<CreativeModeTab, FilterEntry> miscFilterMap = new HashMap<>();
    private Button btnScrollUp;
    private Button btnScrollDown;
    private Button btnEnableAll;
    private Button btnDisableAll;
    private boolean viewingFilterTab;
    private int guiCenterX = 0;
    private int guiCenterY = 0;
    private CreativeModeTab currentGroup = CreativeModeTab.TAB_BUILDING_BLOCKS;

    @SubscribeEvent
    public void onPlayerLogout(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        this.updatedFilters = false;
    }

    @SubscribeEvent
    public void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof CreativeModeInventoryScreen) {
            if (!this.updatedFilters) {
                this.updateFilters();
                this.updatedFilters = true;
            }

            this.viewingFilterTab = false;
            this.guiCenterX = ((CreativeModeInventoryScreen) event.getGui()).getGuiLeft();
            this.guiCenterY = ((CreativeModeInventoryScreen) event.getGui()).getGuiTop();

            event.addWidget(this.btnScrollUp = new IconButton(this.guiCenterX - 22, this.guiCenterY - 12, new TranslatableComponent("gui.button.filters.scroll_filters_up"), button -> this.scrollUp(), ICONS, 0, 0));
            event.addWidget(this.btnScrollDown = new IconButton(this.guiCenterX - 22, this.guiCenterY + 127, new TranslatableComponent("gui.button.filters.scroll_filters_down"), button -> this.scrollDown(), ICONS, 16, 0));
            event.addWidget(this.btnEnableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 10, new TranslatableComponent("gui.button.filters.enable_filters"), button -> this.enableAllFilters(), ICONS, 32, 0));
            event.addWidget(this.btnDisableAll = new IconButton(this.guiCenterX - 50, this.guiCenterY + 32, new TranslatableComponent("gui.button.filters.disable_filters"), button -> this.disableAllFilters(), ICONS, 48, 0));

            this.hideButtons();

            CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) event.getGui();
            this.updateTagButtons(screen);

            CreativeModeTab group = this.getGroup(screen.getSelectedTab());
            if (Filters.get().hasFilters(group)) {
                this.showButtons();
                this.viewingFilterTab = true;
                this.updateItems(screen);
            }

            this.currentGroup = group;
        }
    }

    @SubscribeEvent
    public void onScreenClick(GuiScreenEvent.MouseClickedEvent.Pre event) {
        if (event.getButton() != GLFW.GLFW_MOUSE_BUTTON_LEFT)
            return;

        if (event.getGui() instanceof CreativeModeInventoryScreen) {
            for (Button button : this.buttons) {
                if (button.isMouseOver(event.getMouseX(), event.getMouseY())) {
                    if (button.mouseClicked(event.getMouseX(), event.getMouseY(), event.getButton())) {
                        return;
                    }
                }
            }
        }
    }

    public void onCreativeTabChange(CreativeModeInventoryScreen screen, CreativeModeTab group) {
        if (Filters.get().hasFilters(group)) {
            if (group != this.currentGroup) {
                this.updateItems(screen);
                this.currentGroup = group;
            }
        }
        this.updateTagButtons(screen);
    }

    @SubscribeEvent
    public void onScreenDrawPre(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (event.getGui() instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) event.getGui();
            CreativeModeTab group = this.getGroup(screen.getSelectedTab());

            if (Filters.get().hasFilters(group)) {
                if (!this.viewingFilterTab) {
                    this.updateItems(screen);
                    this.viewingFilterTab = true;
                }
            } else {
                this.viewingFilterTab = false;
            }
        }
    }

    @SubscribeEvent
    public void onScreenDrawBackground(GuiContainerEvent.DrawBackground event) {
        if (event.getGuiContainer() instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) event.getGuiContainer();
            CreativeModeTab group = this.getGroup(screen.getSelectedTab());

            if (Filters.get().hasFilters(group)) {
                /* Render buttons */
                this.buttons.forEach(button ->
                {
                    button.render(event.getMatrixStack(), event.getMouseX(), event.getMouseY(), Minecraft.getInstance().getFrameTime());
                });
            }
        }
    }

    @SubscribeEvent
    public void onScreenDrawPost(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen screen = (CreativeModeInventoryScreen) event.getGui();
            CreativeModeTab group = this.getGroup(screen.getSelectedTab());

            if (Filters.get().hasFilters(group)) {
                /* Render tooltips after so it renders above buttons */
                this.buttons.forEach(button ->
                {
                    if (button.isMouseOver(event.getMouseX(), event.getMouseY())) {
                        screen.renderTooltip(event.getMatrixStack(), button.getFilter().getName(), event.getMouseX(), event.getMouseY());
                    }
                });

                if (this.btnEnableAll.isMouseOver(event.getMouseX(), event.getMouseY())) {
                    screen.renderTooltip(event.getMatrixStack(), this.btnEnableAll.getMessage(), event.getMouseX(), event.getMouseY());
                }

                if (this.btnDisableAll.isMouseOver(event.getMouseX(), event.getMouseY())) {
                    screen.renderTooltip(event.getMatrixStack(), this.btnDisableAll.getMessage(), event.getMouseX(), event.getMouseY());
                }
            }
        }
    }

    @SubscribeEvent
    public void onMouseScroll(GuiScreenEvent.MouseScrollEvent.Pre event) {
        if (event.getGui() instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen creativeScreen = (CreativeModeInventoryScreen) event.getGui();
            int guiLeft = creativeScreen.getGuiLeft();
            int guiTop = creativeScreen.getGuiTop();
            int startX = guiLeft - 32;
            int startY = guiTop + 10;
            int endX = guiLeft;
            int endY = startY + 28 * 4 + 3;
            if (event.getMouseX() >= startX && event.getMouseX() < endX && event.getMouseY() >= startY && event.getMouseY() < endY) {
                if (event.getScrollDelta() > 0) {
                    this.scrollUp();
                } else {
                    this.scrollDown();
                }
                event.setCanceled(true);
            }
        }
    }

    private void updateTagButtons(CreativeModeInventoryScreen screen) {
        if (!this.updatedFilters)
            return;

        this.buttons.clear();
        CreativeModeTab group = this.getGroup(screen.getSelectedTab());
        if (Filters.get().hasFilters(group)) {
            List<FilterEntry> entries = this.getFilters(group);
            int scroll = scrollMap.computeIfAbsent(group, group1 -> 0);
            for (int i = scroll; i < scroll + 4 && i < entries.size(); i++) {
                TagButton button = new TagButton(screen.getGuiLeft() - 28, screen.getGuiTop() + 29 * (i - scroll) + 10, entries.get(i), button1 -> this.updateItems(screen));
                this.buttons.add(button);
            }
            this.btnScrollUp.active = scroll > 0;
            this.btnScrollDown.active = scroll <= entries.size() - 4 - 1;
            this.showButtons();
        } else {
            this.hideButtons();
        }
    }

    private void updateItems(CreativeModeInventoryScreen screen) {
        CreativeModeInventoryScreen.ItemPickerMenu container = screen.getMenu();
        Set<Item> filteredItems = new LinkedHashSet<>();
        CreativeModeTab group = this.getGroup(screen.getSelectedTab());
        if (group != null) {
            if (Filters.get().hasFilters(group)) {
                List<FilterEntry> entries = Filters.get().getFilters(group);
                if (entries != null) {
                    for (FilterEntry filter : this.getFilters(group)) {
                        if (filter.isEnabled()) {
                            filteredItems.addAll(filter.getItems());
                        }
                    }
                    container.items.clear();
                    filteredItems.forEach(item -> item.fillItemCategory(group, container.items));
                    container.items.sort(Comparator.comparingInt(o -> Item.getId(o.getItem())));
                    container.scrollTo(0);
                }
            }
        }
    }

    private void updateFilters() {
        Filters.get().getGroups().forEach(group ->
        {
            List<FilterEntry> entries = Filters.get().getFilters(group);
            entries.forEach(FilterEntry::clear);

            Set<Item> removed = new HashSet<>();
            List<Item> items = ForgeRegistries.ITEMS.getValues().stream()
                    .filter(item -> item.getItemCategory() == group || item == Items.ENCHANTED_BOOK)
                    .collect(Collectors.toList());
            items.forEach(item ->
            {
                for (ResourceLocation location : item.getTags()) {
                    for (FilterEntry filter : entries) {
                        if (location.equals(filter.getTag())) {
                            filter.add(item);
                            removed.add(item);
                        }
                    }
                }
            });
            items.removeAll(removed);

            if (group.getEnchantmentCategories().length == 0) {
                items.remove(Items.ENCHANTED_BOOK);
            }

            if (!items.isEmpty()) {
                FilterEntry entry = new FilterEntry(new ResourceLocation("miscellaneous"), new ItemStack(Blocks.BARRIER));
                items.forEach(entry::add);
                this.miscFilterMap.put(group, entry);
            }
        });
    }

    private CreativeModeTab getGroup(int index) {
        if (index < 0 || index >= CreativeModeTab.TABS.length)
            return null;
        return CreativeModeTab.TABS[index];
    }

    private List<FilterEntry> getFilters(CreativeModeTab group) {
        if (Filters.get().hasFilters(group)) {
            List<FilterEntry> filters = new ArrayList<>(Filters.get().getFilters(group));
            if (this.miscFilterMap.containsKey(group)) {
                filters.add(this.miscFilterMap.get(group));
            }
            return filters;
        }
        return Collections.emptyList();
    }

    private void showButtons() {
        this.btnScrollUp.visible = true;
        this.btnScrollDown.visible = true;
        this.btnEnableAll.visible = true;
        this.btnDisableAll.visible = true;
        this.buttons.forEach(button -> button.visible = true);
    }

    private void hideButtons() {
        this.btnScrollUp.visible = false;
        this.btnScrollDown.visible = false;
        this.btnEnableAll.visible = false;
        this.btnDisableAll.visible = false;
        this.buttons.forEach(button -> button.visible = false);
    }

    private void scrollUp() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen creativeScreen = (CreativeModeInventoryScreen) screen;
            CreativeModeTab group = this.getGroup(creativeScreen.getSelectedTab());
            List<FilterEntry> entries = this.getFilters(group);
            if (entries != null) {
                int scroll = scrollMap.computeIfAbsent(group, group1 -> 0);
                if (scroll > 0) {
                    scrollMap.put(group, scroll - 1);
                    this.updateTagButtons(creativeScreen);
                }
            }
        }
    }

    private void scrollDown() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen creativeScreen = (CreativeModeInventoryScreen) screen;
            CreativeModeTab group = this.getGroup(creativeScreen.getSelectedTab());
            List<FilterEntry> entries = this.getFilters(group);
            if (entries != null) {
                int scroll = scrollMap.computeIfAbsent(group, group1 -> 0);
                if (scroll <= entries.size() - 4 - 1) {
                    scrollMap.put(group, scroll + 1);
                    this.updateTagButtons(creativeScreen);
                }
            }
        }
    }

    private void enableAllFilters() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen creativeScreen = (CreativeModeInventoryScreen) screen;
            CreativeModeTab group = this.getGroup(creativeScreen.getSelectedTab());
            List<FilterEntry> entries = this.getFilters(group);
            if (entries != null) {
                entries.forEach(entry -> entry.setEnabled(true));
                this.buttons.forEach(TagButton::updateState);
                this.updateItems(creativeScreen);
            }
        }
    }

    private void disableAllFilters() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof CreativeModeInventoryScreen) {
            CreativeModeInventoryScreen creativeScreen = (CreativeModeInventoryScreen) screen;
            CreativeModeTab group = this.getGroup(creativeScreen.getSelectedTab());
            List<FilterEntry> entries = this.getFilters(group);
            if (entries != null) {
                entries.forEach(filters -> filters.setEnabled(false));
                this.buttons.forEach(TagButton::updateState);
                this.updateItems(creativeScreen);
            }
        }
    }
}
