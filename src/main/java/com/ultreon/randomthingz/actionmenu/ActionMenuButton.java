package com.ultreon.randomthingz.actionmenu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.widgets.TransparentButton;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ActionMenuButton extends TransparentButton implements IActionMenuIndexable {
    private static final ResourceLocation ICONS = new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/icons/action_menu.png");
    @Getter
    private final ActionMenuItem item;
    @Getter
    private final ActionMenuScreen screen;
    @Getter
    private int menuIndex;

    public ActionMenuButton(ActionMenuScreen screen, ActionMenuItem item, int x, int y, int width, int height) {
        super(x, y, width, height, item.getText(), (btn) -> item.activate());
        this.screen = screen;
        this.item = item;
    }

    public ActionMenuButton(ActionMenuScreen screen, ActionMenuItem item, int x, int y, int width, int height, OnTooltip onTooltip) {
        super(x, y, width, height, item.getText(), (btn) -> item.activate(), onTooltip);
        this.screen = screen;
        this.item = item;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        Font fontrenderer = mc.font;

        int col = 0;
        if (screen.getActiveItem() == this) {
            col = new Color(0, 0, 0, (int) Math.min(Math.max(127 - (51.2 * (menuIndex - 1)), 0), 127)).getRGB();
        } else {
            col = new Color(0, 0, 0, (int) Math.min(Math.max(127 - (51.2 * (menuIndex)), 0), 127)).getRGB();
        }

        fill(matrixStack, x, y, x + width, y + height, col);

        int hov;
        int nrm;
        int dis;
        if (screen.getActiveItem() == this) {
            hov = new Color(255, 255, 0, Math.max((int) Math.min(255 - (51.2 * (menuIndex - 1)), 255), 1)).getRGB();
            nrm = new Color(255, 255, 255, Math.max((int) Math.min(255 - (51.2 * (menuIndex - 1)), 255), 1)).getRGB();
            dis = new Color(160, 160, 160, Math.max((int) Math.min(255 - (51.2 * (menuIndex - 1)), 255), 1)).getRGB();
        } else {
            hov = new Color(255, 255, 0, (int) Math.max(Math.min(255 - (51.2 * (menuIndex)), 255), 1)).getRGB();
            nrm = new Color(255, 255, 255, (int) Math.max(Math.min(255 - (51.2 * (menuIndex)), 255), 1)).getRGB();
            dis = new Color(160, 160, 160, (int) Math.max(Math.min(255 - (51.2 * (menuIndex)), 255), 1)).getRGB();
        }

        int j;
        if (this.active) {
            if (menuIndex != 0) {
                if (screen.getActiveItem() == this) {
                    if (isHovered()) {
                        j = hov;
                    } else {
                        j = nrm;
                    }
                } else {
                    j = nrm;
                }
            } else {
                if (isHovered()) {
                    j = hov;
                } else {
                    j = nrm;
                }
            }
        } else {
            j = dis;
        }

        if (isHovered() && menuIndex == 0 && active) {
            drawCenteredString(matrixStack, fontrenderer, this.getMessage(), (this.x + this.width / 2) + 1, (this.y + (this.height - 8) / 2) + 1, j);
        } else {
            drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }

        if (item instanceof SubmenuItem) {
            Minecraft.getInstance().getTextureManager().bindTexture(ICONS);

            matrixStack.push();
            RenderSystem.pushMatrix();
            RenderSystem.color4f(1f, 1f, 1f, 1f / (menuIndex + 1));
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            if (active) {
                if (isHovered() && menuIndex == 0) {
                    blit(matrixStack, x + width - 6, y + height / 2 - 4, 6, 9, 12, 0, 6, 9, 64, 64);
                } else {
                    blit(matrixStack, x + width - 6, y + height / 2 - 4, 6, 9, 6, 0, 6, 9, 64, 64);
                }
            } else {
                blit(matrixStack, x + width - 6, y + height / 2 - 4, 6, 9, 0, 0, 6, 9, 64, 64);
            }
            RenderSystem.popMatrix();
            matrixStack.pop();

            if (mc.currentScreen instanceof ActionMenuScreen) {
                ActionMenuScreen currentScreen = (ActionMenuScreen) mc.currentScreen;
                if (isHovered() && active) {
                    if (currentScreen.getMenuIndex() >= screen.getMenuIndex()) {
                        screen.setButtonRectangle(new Rectangle(x, y, width + 1, height));
                        screen.setActiveItem(this);
                        mc.displayGuiScreen(new ActionMenuScreen(screen, ((SubmenuItem) item).getHandler().getMenu(), screen.getMenuIndex() + 1, item.getText()));
                    }
                } else if (isHovered() && !active) {
                    if (mc.currentScreen != screen) {
                        screen.scheduleDisplay(screen);
                    }
                }
            }
        } else {
            if (isHovered()) {
                if (mc.currentScreen != screen) {
                    screen.scheduleDisplay(screen);
                }
            }
        }

        if (this.isHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);
        }
    }

    @Override
    public void setMenuIndex(int menuIndex) {
        this.menuIndex = menuIndex;
    }
}
