package com.qsoftware.forgemod.client.gui;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.widgets.ModuleList;
import com.qsoftware.forgemod.client.toasts.ErrorToast;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.common.interfaces.Module;
import com.qsoftware.forgemod.graphics.MCGraphics;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.util.*;

@SuppressWarnings({"unused"})
@OnlyIn(Dist.CLIENT)
public class ModuleScreen extends Screen {
   // Icons
   private static final ResourceLocation SCREEN_ICONS = new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/icons.png");

   // Drop info.
   private static final ITextComponent DROP_INFO = (new TranslationTextComponent("pack.dropInfo")).mergeStyle(TextFormatting.GRAY);

   // Back screen.
   private final Screen backScreen;

   // Lists.
   private ModuleList leftScreen;
   private ModuleList rightScreen;

   // ?
   private final Map<String, ResourceLocation> field_243394_y = Maps.newHashMap();

   // Modules.
   private final ModuleManager manager = ModuleManager.getInstance();
   private Set<Module> enabled;
   private Set<Module> disabled;

   public ModuleScreen(@Nullable Screen backScreen, ITextComponent p_i242060_5_) {
      // Super call.
      super(p_i242060_5_);

      // Assign back screen.
      this.backScreen = backScreen;
   }

   @Override
   public void closeScreen() {
      // Display the back screen.
      Objects.requireNonNull(this.minecraft).displayGuiScreen(this.backScreen);
   }

   @Override
   protected void init() {
      Button cancelButton = this.addButton(new Button(this.width / 2 + 5, this.height - 48, 150, 20, DialogTexts.GUI_CANCEL, (p_238903_1_) -> {
         manager.discardChanges();
         this.closeScreen();
      }));

      Button doneButton = this.addButton(new Button(this.width / 2 - 155, this.height - 48, 150, 20, DialogTexts.GUI_DONE, (p_238903_1_) -> {
         try {
            manager.saveChanges();
         } catch (IOException e) {
            e.printStackTrace();

            if (QForgeMod.isClientSide()) {
               ErrorToast systemToast = new ErrorToast(new TranslationTextComponent("misc.qforgemod.error"), new TranslationTextComponent("misc.qforgemod.failed_save", I18n.format("misc.qforgemod.modules")));
               Objects.requireNonNull(minecraft).getToastGui().add(systemToast);
            }
         }
         this.closeScreen();
      }));

      // Force to check the Minecraft instance isn't null.
      assert this.minecraft != null;
      
      // Create the left screen.
      this.leftScreen = new ModuleList(this, this.minecraft, 200, this.height, new TranslationTextComponent("pack.available.title"));
      this.leftScreen.setLeftPos(this.width / 2 - 4 - 200);
      this.children.add(this.leftScreen);
      
      // Create the right screen.
      this.rightScreen = new ModuleList(this, this.minecraft, 200, this.height, new TranslationTextComponent("pack.selected.title"));
      this.rightScreen.setLeftPos(this.width / 2 + 4);
      this.children.add(this.rightScreen);

      // Reload all.
      reload();
   }

   /**
    * Reload the module screen.
    */
   public void reload() {
      // Enabled / disabled.
      this.enabled = manager.getUnsavedEnabled();
      this.disabled = manager.getUnsavedDisabled();

      // Reload challenges.
      this.reloadModules(this.rightScreen, this.enabled);
      this.reloadModules(this.leftScreen, this.disabled);
   }

   /**
    * Reload modules.
    *
    * @param list the module list.
    * @param modules the modules.
    */
   private void reloadModules(ModuleList list, Set<Module> modules) {
      // Clear challenge in list.
      list.getEventListeners().clear();
      
      // Add challenge entries.
      for (Module challenge : modules) {
         list.getEventListeners().add(new ModuleList.ModuleEntry(Objects.requireNonNull(this.minecraft), list, this, challenge));
      }
   }

   @Override
   public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.renderDirtBackground(0);

      // Render lists.
      this.leftScreen.render(matrixStack, mouseX, mouseY, partialTicks);
      this.rightScreen.render(matrixStack, mouseX, mouseY, partialTicks);
      
      // Render text.
      drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
      drawCenteredString(matrixStack, this.font, DROP_INFO, this.width / 2, 20, 16777215);

      // Render super object.
      super.render(matrixStack, mouseX, mouseY, partialTicks);

      MCGraphics mcg = new MCGraphics(matrixStack, Objects.requireNonNull(minecraft).fontRenderer, this);

      // Draw help icon.
      mcg.drawTexture(1, 1, 64, 15, 16, 16, SCREEN_ICONS);

      // Draw help message if mouse pointer is on the help icon.
      if (AdvancedScreen.isPointInRegion(1, 1, 17, 17, mouseX, mouseY)) {
         mcg.renderTooltip(new TranslationTextComponent("msg.qforgemod.module_screen.help"), new Point(16, mouseY));
      }
   }

   public ModuleManager getManager() {
      return manager;
   }

   public Set<Module> getEnabled() {
      return enabled;
   }

   public Set<Module> getDisabled() {
      return disabled;
   }
}
