package com.qsoftware.forgemod.modules.actionmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ActionMenuScreen extends Screen {
    private final List<Screen> screens = new ArrayList<>();
    private final @Nullable Screen parent;
    @Getter private boolean initialized = false;
    @Getter private final int menuIndex;
    @Getter private final AbstractActionMenu menu;
    @Getter @Nullable private Rectangle buttonRect;
    @Getter @Setter(AccessLevel.PACKAGE) @Nullable private ActionMenuButton activeItem;

    protected ActionMenuScreen(@Nullable Screen parent, AbstractActionMenu menu, int menuIndex) {
        super(new StringTextComponent("Main"));
        this.parent = parent;
        this.menu = menu;
        this.menuIndex = menuIndex;
    }

    protected ActionMenuScreen(@Nullable Screen parent, AbstractActionMenu menu, int menuIndex, ITextComponent title) {
        super(title);
        this.parent = parent;
        this.menu = menu;
        this.menuIndex = menuIndex;
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        if (parent != null && initialized) {
            parent.init(minecraft, width, height);
        }
        super.init(minecraft, width, height);
    }

    @Override
    protected void init() {
        this.buttons.clear();
        this.children.clear();

        int x = 1;
        if (menuIndex > 0) {
            x = (151 * menuIndex) + 1;
        }

        int y = height - 16;
        List<? extends IActionMenuItem> items = menu.getItems();
        for (int i = items.size() - 1; i >= 0; i--) {
            IActionMenuItem item = items.get(i);
            ActionMenuButton actionMenuButton = addButton(new ActionMenuButton(this, item, x, y, 150, 15));
            actionMenuButton.active = item.isEnabled();

            y -= 16;
        }

//        y -= 16;
//
//        addButton(new ActionMenuButton(this, new IActionMenuItem() {
//            @Override
//            public void onActivate() {
//                Minecraft mc = Minecraft.getInstance();
//                mc.displayGuiScreen(parent);
//            }
//
//            @Override
//            public ITextComponent getText() {
//                return DialogTexts.GUI_BACK;
//            }
//        }, x, y, 150, 15));

        y -= 16;
        addButton(new ActionMenuTitle(this, x, y, 150, 15));

        initialized = true;
    }

    void setButtonRectangle(Rectangle rect) {
        this.buttonRect = rect;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (parent instanceof ActionMenuScreen) {
            ((ActionMenuScreen)parent).render(matrixStack, mouseX, mouseY, partialTicks, this.menuIndex);
        } else if (parent != null) {
            parent.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        for (Widget widget : this.buttons) {
            if (widget instanceof IActionMenuIndexable) {
                IActionMenuIndexable indexable = (IActionMenuIndexable) widget;
                indexable.setMenuIndex(0);
            }
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        int startX = 1;
        if (menuIndex > 0) {
            startX = (151 * menuIndex) + 1;
        }

//        int endX = 151;
//        if (menuIndex > 0) {
//            endX = (151 * (menuIndex + 1)) + 1;
//        }
//
//        if (mouseX > startX && mouseX < endX) {
//            wasHovered = true;
//        }

        if (mouseX < startX && Minecraft.getInstance().currentScreen == this) {
            if (buttonRect != null && !buttonRect.contains(mouseX, mouseY)) {
                goBack();
            }
        }

        for (Screen screen : screens) {
            Minecraft.getInstance().displayGuiScreen(screen);
        }
        screens.clear();
    }

    private void goBack() {
        Minecraft.getInstance().displayGuiScreen(parent);
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, int childIndex) {
        if (parent instanceof ActionMenuScreen) {
            ((ActionMenuScreen)parent).render(matrixStack, mouseX, mouseY, partialTicks, childIndex);
        } else if (parent != null) {
            parent.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        for (Widget widget : this.buttons) {
            if (widget instanceof IActionMenuIndexable) {
                IActionMenuIndexable indexable = (IActionMenuIndexable) widget;
                indexable.setMenuIndex(childIndex - this.menuIndex);
            }
            widget.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        for (Screen screen : screens) {
            Minecraft.getInstance().displayGuiScreen(screen);
        }
        screens.clear();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    void scheduleDisplay(ActionMenuScreen screen) {
        this.screens.add(screen);
    }
}
