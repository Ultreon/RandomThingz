package com.qsoftware.forgemod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sun.jna.Platform;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public class ConfirmExitScreen extends Screen {
   private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;
   private final ITextComponent shutdownPcText;
   private final ITextComponent confirmButtonText;
   private final ITextComponent cancelButtonText;
   private Screen backScreen;
   private int ticksUntilEnable;

   public ConfirmExitScreen(Screen backScreen) {
      super(new TranslationTextComponent("msg.qforgemod.confirm_exit.title"));
      this.backScreen = backScreen;
      this.confirmButtonText = DialogTexts.GUI_PROCEED;
      this.cancelButtonText = DialogTexts.GUI_BACK;
      this.shutdownPcText = new TranslationTextComponent("button.qforgemod.confirm_exit.shutdown_pc");

      setButtonDelay(40);
   }

   protected void init() {
      super.init();

      this.buttons.clear();
      this.children.clear();

      this.addButton(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, this.confirmButtonText, (p_213006_1_) -> {
         if (this.minecraft != null) {
            this.minecraft.close();
            System.exit(0);
         }
      }));
      this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 96, 100, 20, this.cancelButtonText, (p_213004_1_) -> {
         if (this.minecraft != null) {
            this.minecraft.displayGuiScreen(backScreen);
         }
      }));
      this.addButton(new Button(this.width / 2 - 105, this.height / 6 + 126, 210, 20, this.shutdownPcText, (p_213006_1_) -> {
         if (this.minecraft != null) {
            this.minecraft.close();
         }
         if (Platform.isWindows()) {
            Runtime runtime = Runtime.getRuntime();
            try {
               runtime.exec("shutdown /s /t 0");
            } catch (IOException e) {
               try {
                  runtime.exec("shutdown -s -t 0");
               } catch (IOException f) {
                  f.printStackTrace();
               }
            }
         } else if (Platform.isLinux()) {
            Runtime runtime = Runtime.getRuntime();
            try {
               runtime.exec("shutdown");
            } catch (IOException e) {
               try {
                  runtime.exec("sudo shutdown");
               } catch (IOException f) {
                  f.printStackTrace();
                  e.printStackTrace();
               }
            }
         }
      }));
   }

   public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(matrixStack);
      drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
      drawCenteredString(matrixStack, this.font, new TranslationTextComponent("msg.qforgemod.confirm_exit.description"), this.width / 2, 90, 0xbfbfbf);
      this.field_243276_q.func_241863_a(matrixStack, this.width / 2, 90);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
   }

   /**
    * Sets the number of ticks to wait before enabling the buttons.
    */
   public void setButtonDelay(int ticksUntilEnableIn) {
      this.ticksUntilEnable = ticksUntilEnableIn;

      for(Widget widget : this.buttons) {
         widget.active = false;
      }

   }

   public void tick() {
      super.tick();
      if (--this.ticksUntilEnable == 0) {
         for(Widget widget : this.buttons) {
            widget.active = true;
         }
      }

   }

   public boolean shouldCloseOnEsc() {
      return false;
   }
}
