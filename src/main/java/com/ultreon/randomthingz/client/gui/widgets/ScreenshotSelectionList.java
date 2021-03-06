package com.ultreon.randomthingz.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.common.Resizer;
import com.ultreon.randomthingz.client.common.Screenshot;
import com.ultreon.randomthingz.client.gui.screen.ScreenshotsScreen;
import com.ultreon.randomthingz.common.FloatSize;
import lombok.Getter;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ScreenshotSelectionList extends ObjectSelectionList<ScreenshotSelectionList.Entry> {
    private static final ResourceLocation UNKNOWN_IMAGE = new ResourceLocation("textures/misc/unknown_server.png");
    private final ScreenshotsScreen gui;
    private List<Screenshot> screenshots;

    public ScreenshotSelectionList(ScreenshotsScreen gui, Minecraft minecraft, int widthIn, int heightIn, int topIn, int bottomIn, @Nullable ScreenshotSelectionList parent) {
        super(minecraft, widthIn, heightIn, topIn, bottomIn, 26);
        this.gui = gui;

        if (parent != null) {
            this.screenshots = parent.screenshots;
        }

        this.x0 = 10;
        this.x1 = 210;
    }

    public void loadScreenshots() {
        this.clearEntries();

        if (this.screenshots == null) {
            this.screenshots = this.gui.getScreenshots();
        }

        List<Screenshot> screenshotList = this.screenshots;
        for (int i = 0; i < screenshotList.size(); i++) {
            Screenshot screenshot = screenshotList.get(i);
            this.addScreenshot(screenshot, i);
//         this.addEntry(new Entry(this, screenshot, i));
        }
    }

    public void addScreenshot(Screenshot screenshot, int index) {
        this.addEntry(new Entry(this, screenshot, index));
    }

    @Override
    public int addEntry(@NotNull ScreenshotSelectionList.Entry entry) {
        return super.addEntry(entry);
    }

    @Override
    protected int getScrollbarPosition() {
        return 200;
    }

    @Override
    public int getRowWidth() {
        return 200;
    }

    @Override
    public int getRowLeft() {
        return 12;
    }

    @Override
    protected boolean isFocused() {
        return this.gui.getFocused() == this;
    }

    public Optional<ScreenshotSelectionList.Entry> getSelectedOpt() {
        return Optional.ofNullable(this.getSelected());
    }

    public ScreenshotsScreen getGuiScreenshots() {
        return this.gui;
    }

    @OnlyIn(Dist.CLIENT)
    public final class Entry extends ObjectSelectionList.Entry<ScreenshotSelectionList.Entry> implements AutoCloseable {
        private final Minecraft minecraft;
        private final ScreenshotsScreen gui;
        @Getter
        private final int index;
        @Getter
        private final Screenshot screenshot;
        private final DynamicTexture texture;
        private final ResourceLocation textureLocation;
        private File file;
        private long lastClick;

        public Entry(ScreenshotSelectionList list, Screenshot screenshot, int index) {
            this.gui = list.getGuiScreenshots();
            this.index = index;
            this.minecraft = Minecraft.getInstance();
            this.file = screenshot.getFile();
            if (!this.file.isFile()) {
                this.file = null;
            }

            this.screenshot = screenshot;
            this.texture = this.screenshot.getTexture();
            this.textureLocation = this.screenshot.getResourceLocation();
        }

        @Override
        public void render(@NotNull PoseStack matrixStack, int p_230432_2_, int scroll, int xOffset, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
            String name = this.file.getName();
            String size;

            if (this.texture != null && this.texture.getPixels() != null) {
                size = this.texture.getPixels().getWidth() + "x" + this.texture.getPixels().getHeight();
            } else {
                size = "Invalid screenshot.";
            }

            TextComponent description = new TextComponent("");

            this.minecraft.font.draw(matrixStack, name, (float) (xOffset + 32 + 3), (float) (scroll + 1), 0xffffff);
            this.minecraft.font.draw(matrixStack, size, (float) (xOffset + 32 + 3), (float) (scroll + 9 + 3), 0x808080);
            this.minecraft.font.draw(matrixStack, description, (float) (xOffset + 32 + 3), (float) (scroll + 9 + 9 + 3), 0x808080);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, this.texture != null ? this.textureLocation : ScreenshotSelectionList.UNKNOWN_IMAGE);
            RenderSystem.enableBlend();
//         AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 0f, 0f, 32, 32, 32, 32);

            if (this.texture != null) {
                int imgWidth = this.texture.getPixels().getWidth();
                int imgHeight = this.texture.getPixels().getHeight();

                Resizer resizer = new Resizer(imgWidth, imgHeight);
                FloatSize size1 = resizer.thumbnail(32f, 22f);

                int width = (int) size1.width;
                int height = (int) size1.height;

                blit(matrixStack, xOffset, scroll, 0f, 0f, width, height, width, height);
            } else {
                blit(matrixStack, xOffset, scroll, 0f, 0f, 32, 32, 32, 32);
            }

            RenderSystem.disableBlend();
//         if (this.minecraft.gameSettings.touchscreen || p_230432_9_) {
//            RenderSystem.setShaderTexture(0, ScreenshotSelectionList.ICON_OVERLAY_LOCATION);
////            AbstractGui.fill(p_230432_1_, p_230432_4_, p_230432_3_, p_230432_4_ + 32, p_230432_3_ + 32, -1601138544);
//            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//            int i = p_230432_7_ - p_230432_4_;
//            boolean flag = i < 32;
//            int j = flag ? 32 : 0;
//            if (this.summary.isLocked()) {
//               AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 96f, (float)j, 32, 32, 256, 256);
//               if (flag) {
//                  this.gui.setToolTip(this.minecraft.font.trimStringToWidth(ScreenshotSelectionList.WORLD_LOCKED_TOOLTIP, 175));
//               }
//            } else if (this.summary.markVersionInList()) {
//               AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 32f, (float)j, 32, 32, 256, 256);
//               if (this.summary.askToOpenDimension()) {
//                  AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 96f, (float)j, 32, 32, 256, 256);
//                  if (flag) {
//                     this.gui.setToolTip(ImmutableList.of(ScreenshotSelectionList.FROM_NEWER_TOOLTIP_1.getVisualOrderText(), ScreenshotSelectionList.FROM_NEWER_TOOLTIP_2.getVisualOrderText()));
//                  }
//               } else if (!SharedConstants.getVersion().isStable()) {
//                  AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 64f, (float)j, 32, 32, 256, 256);
//                  if (flag) {
//                     this.gui.setToolTip(ImmutableList.of(ScreenshotSelectionList.SNAPSHOT_TOOLTIP_1.getVisualOrderText(), ScreenshotSelectionList.SNAPSHOT_TOOLTIP_2.getVisualOrderText()));
//                  }
//               }
//            } else {
//               AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 0f, (float)j, 32, 32, 256, 256);
//            }
//         }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            ScreenshotSelectionList.this.setSelected(this);
            this.gui.refresh();
            if (mouseX - (double) ScreenshotSelectionList.this.getRowLeft() <= 32.0D) {
//            this.openScreenshot();
                return true;
            } else if (Util.getMillis() - this.lastClick < 250L) {
//            this.openScreenshot();
                return true;
            } else {
                this.lastClick = Util.getMillis();
                return false;
            }
        }

        public void close() {

        }

        @Override
        public @NotNull Component getNarration() {
            return new TextComponent("Screenshot entry");
        }
    }
}
