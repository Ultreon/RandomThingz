package com.qsoftware.forgemod.modules.ui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.FloatSize;
import com.qsoftware.forgemod.modules.ui.common.AspectRatio;
import com.qsoftware.forgemod.modules.ui.common.Screenshot;
import com.qsoftware.forgemod.modules.ui.screens.ScreenshotsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ScreenshotSelectionList extends ExtendedList<ScreenshotSelectionList.Entry> {
   private static final ResourceLocation field_214378_c = new ResourceLocation("textures/misc/unknown_server.png");
   private final ScreenshotsScreen gui;
   private List<Screenshot> screenshots;

   public ScreenshotSelectionList(ScreenshotsScreen gui, Minecraft minecraft, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn, @Nullable ScreenshotSelectionList parent) {
      super(minecraft, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
      this.gui = gui;

      if (parent != null) {
         this.screenshots = parent.screenshots;
      }

      this.loadScreenshots();
   }

   public void loadScreenshots() {
      this.clearEntries();

      if (this.screenshots == null) {
         this.screenshots = this.gui.getScreenshots();
      }

      for(Screenshot screenshot : this.screenshots) {
         this.addEntry(new Entry(this, screenshot));
      }
   }

   @Override
   protected int getScrollbarPosition() {
      return 150;
   }

   @Override
   public int getRowWidth() {
      return 150;
   }

   @Override
   protected boolean isFocused() {
      return this.gui.getListener() == this;
   }

   public Optional<ScreenshotSelectionList.Entry> func_214376_a() {
      return Optional.ofNullable(this.getSelected());
   }

   public ScreenshotsScreen getGuiScreenshots() {
      return this.gui;
   }

   @OnlyIn(Dist.CLIENT)
   public final class Entry extends ExtendedList.AbstractListEntry<ScreenshotSelectionList.Entry> implements AutoCloseable {
      private final Minecraft minecraft;
      private final ScreenshotsScreen gui;
      private final ResourceLocation textureLocation;
      private File file;
      @Nullable
      private final DynamicTexture texture;
      private long lastClick;

      public Entry(ScreenshotSelectionList list, Screenshot screenshot) {
         this.gui = list.getGuiScreenshots();
         this.minecraft = Minecraft.getInstance();
         String s = screenshot.getFile().getName();
         this.textureLocation = new ResourceLocation(QForgeMod.modId, "screenshots/" + s);
         this.file = screenshot.getFile();
         if (!this.file.isFile()) {
            this.file = null;
         }

         this.texture = screenshot.getTexture();
      }

      @SuppressWarnings("deprecation")
      @Override
      public void render(@NotNull MatrixStack matrixStack, int p_230432_2_, int scroll, int xOffset, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
         String name = this.file.getName();
         String size;
         if (this.texture != null && this.texture.getTextureData() != null) {
            size = this.texture.getTextureData().getWidth() + "×" + this.texture.getTextureData().getHeight();
         } else {
            size = "Invalid screenshot.";
         }

         StringTextComponent description = new StringTextComponent("");

         this.minecraft.fontRenderer.drawString(matrixStack, name, (float)(xOffset + 32 + 3), (float)(scroll + 1), 16777215);
         this.minecraft.fontRenderer.drawString(matrixStack, size, (float)(xOffset + 32 + 3), (float)(scroll + 9 + 3), 8421504);
         this.minecraft.fontRenderer.func_243248_b(matrixStack, description, (float)(xOffset + 32 + 3), (float)(scroll + 9 + 9 + 3), 8421504);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.minecraft.getTextureManager().bindTexture(this.texture != null ? this.textureLocation : ScreenshotSelectionList.field_214378_c);
         RenderSystem.enableBlend();
//         AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 0.0F, 0.0F, 32, 32, 32, 32);

         if (this.texture != null) {
            int imgWidth = this.texture.getTextureData().getWidth();
            int imgHeight = this.texture.getTextureData().getHeight();

            AspectRatio aspectRatio = new AspectRatio(imgWidth, imgHeight);
            FloatSize size1 = aspectRatio.thumbnail(32f, 32f);

            int width = (int) size1.width;
            int height = (int) size1.height;

            blit(matrixStack, xOffset, scroll, 0, 0, width, height, width, height);
         } else {
            AbstractGui.blit(matrixStack, xOffset, scroll, 0.0F, 0.0F, 32, 32, 32, 32);
         }

         RenderSystem.disableBlend();
//         if (this.minecraft.gameSettings.touchscreen || p_230432_9_) {
//            this.minecraft.getTextureManager().bindTexture(ScreenshotSelectionList.field_214379_d);
////            AbstractGui.fill(p_230432_1_, p_230432_4_, p_230432_3_, p_230432_4_ + 32, p_230432_3_ + 32, -1601138544);
//            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//            int i = p_230432_7_ - p_230432_4_;
//            boolean flag = i < 32;
//            int j = flag ? 32 : 0;
//            if (this.field_214451_d.isLocked()) {
//               AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 96.0F, (float)j, 32, 32, 256, 256);
//               if (flag) {
//                  this.gui.func_239026_b_(this.minecraft.fontRenderer.trimStringToWidth(ScreenshotSelectionList.field_243466_v, 175));
//               }
//            } else if (this.field_214451_d.markVersionInList()) {
//               AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 32.0F, (float)j, 32, 32, 256, 256);
//               if (this.field_214451_d.askToOpenWorld()) {
//                  AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 96.0F, (float)j, 32, 32, 256, 256);
//                  if (flag) {
//                     this.gui.func_239026_b_(ImmutableList.of(ScreenshotSelectionList.field_243462_r.func_241878_f(), ScreenshotSelectionList.field_243463_s.func_241878_f()));
//                  }
//               } else if (!SharedConstants.getVersion().isStable()) {
//                  AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 64.0F, (float)j, 32, 32, 256, 256);
//                  if (flag) {
//                     this.gui.func_239026_b_(ImmutableList.of(ScreenshotSelectionList.field_243464_t.func_241878_f(), ScreenshotSelectionList.field_243465_u.func_241878_f()));
//                  }
//               }
//            } else {
//               AbstractGui.blit(p_230432_1_, p_230432_4_, p_230432_3_, 0.0F, (float)j, 32, 32, 256, 256);
//            }
//         }
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         ScreenshotSelectionList.this.setSelected(this);
         this.gui.refreshFromList(ScreenshotSelectionList.this.func_214376_a().isPresent());
         if (mouseX - (double) ScreenshotSelectionList.this.getRowLeft() <= 32.0D) {
//            this.openScreenshot();
            return true;
         } else if (Util.milliTime() - this.lastClick < 250L) {
//            this.openScreenshot();
            return true;
         } else {
            this.lastClick = Util.milliTime();
            return false;
         }
      }

      public void close() {
         if (this.texture != null) {
            this.texture.close();
         }

      }
   }
}
