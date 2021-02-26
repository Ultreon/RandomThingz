package com.qsoftware.forgemod.script.js.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.keybinds.KeyBindingList;
import com.qsoftware.forgemod.network.Network;
import com.qsoftware.forgemod.network.ScriptPacket;
import com.qsoftware.forgemod.script.js.ScriptJSFormatter;
import com.qsoftware.forgemod.util.TextUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = QForgeMod.modId, value = Dist.CLIENT)
public class ScriptJSScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS:Screen");
   private String historyBuffer = "";

   /**
    * keeps position of which chat message you will select when you press up, (does not increase for duplicated messages
    * sent immediately after each other)
    */
   private int sentHistoryCursor = -1;
   /** Chat entry field */
   protected ScriptJSTextWidget inputField;
   /** is the text that appears when you press the chat key and the input box appears pre-filled */
   private final String defaultInputFieldText;
   private boolean textLock;
   //   private CommandSuggestionHelper commandSuggestionHelper;

   public ScriptJSScreen(String defaultText) {
      super(NarratorChatListener.EMPTY);
      this.defaultInputFieldText = defaultText;
   }

   protected void init() {
      Objects.requireNonNull(this.minecraft).keyboardListener.enableRepeatEvents(true);
      this.sentHistoryCursor = ScriptJSGui.getInstance().getSentMessages().size();
      this.inputField = new ScriptJSTextWidget(this.font, 4, this.height - 12, this.width - 4, 12, new TranslationTextComponent("chat.editBox")) {
//         protected IFormattableTextComponent getNarrationMessage() {
//            return super.getNarrationMessage().appendString(ChatScreen.this.commandSuggestionHelper.getSuggestionMessage());
//         }
      };
//      this.inputField.setTextFormatter((p_195610_0_, p_195610_1_) -> (p_242236_0_) -> false);
      this.inputField.setMaxStringLength(256);
      this.inputField.setEnableBackgroundDrawing(false);
      this.inputField.setText(this.defaultInputFieldText);
      this.inputField.setResponder(this::textChanged);
      this.children.add(this.inputField);
//      this.commandSuggestionHelper = new CommandSuggestionHelper(this.minecraft, this, this.inputField, this.font, false, false, 1, 10, true, -805306368);
//      this.commandSuggestionHelper.init();
      this.setFocusedDefault(this.inputField);
   }

   public void resize(Minecraft minecraft, int width, int height) {
      String s = this.inputField.getText();
      this.init(minecraft, width, height);
      this.setChatLine(s);
//      this.commandSuggestionHelper.init();
   }

   public void onClose() {
      Objects.requireNonNull(this.minecraft).keyboardListener.enableRepeatEvents(false);
      ScriptJSGui.getInstance().resetScroll();
   }

   public void tick() {
      this.inputField.tick();
   }

   private void textChanged(String p_212997_1_) {
//      if (textLock) {
//         return;
//      }
//
//      this.textLock = true;
//      String s = TextUtils.stripFormatting(this.inputField.getText());
//      s = this.formatCode(s);
//      this.inputField.setText(s);
//      LOGGER.info(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "$ " + TextFormatting.RESET + TextFormatting.WHITE + s);
//
////      this.commandSuggestionHelper.shouldAutoSuggest(!s.equals(this.defaultInputFieldText));
////      this.commandSuggestionHelper.init();
//
//      this.textLock = false;
   }

   private String formatCode(String s) {
      return ScriptJSFormatter.instance().format(s);
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.minecraft == null) {
         return true;
      }
      
      /*if (this.commandSuggestionHelper.onKeyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else */if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (keyCode == 256) {
         Objects.requireNonNull(this.minecraft).displayGuiScreen(null);
         return true;
      } else if (keyCode != 257 && keyCode != 335) {
         if (keyCode == 265) {
            this.getSentHistory(-1);
            return true;
         } else if (keyCode == 264) {
            this.getSentHistory(1);
            return true;
         } else if (keyCode == 266) {
            ScriptJSGui.getInstance().addScrollPos(ScriptJSGui.getInstance().getLineCount() - 1);
            return true;
         } else if (keyCode == 267) {
            ScriptJSGui.getInstance().addScrollPos(-ScriptJSGui.getInstance().getLineCount() + 1);
            return true;
         } else {
            return false;
         }
      } else {
         String s = TextUtils.stripFormatting(this.inputField.getText().trim());
         if (!s.isEmpty()) {
            if (this.minecraft != null && this.minecraft.player != null) {
               Network.channel.sendToServer(new ScriptPacket(s));
            }
         }

         ScriptJSGui.getInstance().addToSentMessages(s);
         ScriptJSGui.getInstance().printScriptMessage(new StringTextComponent(TextFormatting.GRAY.toString() + TextFormatting.BOLD.toString() + TextFormatting.UNDERLINE + "$ " + TextFormatting.RESET + TextFormatting.WHITE.toString() + TextFormatting.UNDERLINE + s));

         this.inputField.setText("");

         return true;
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
//      if (delta > 1.0D) {
//         delta = 1.0D;
//      }
//
//      if (delta < -1.0D) {
//         delta = -1.0D;
//      }

      /*if (this.commandSuggestionHelper.onScroll(delta)) {
         return true;
      } else */{
         if (!hasShiftDown()) {
            delta *= 7.0D;
         }

         ScriptJSGui.getInstance().addScrollPos(delta);
         return true;
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      /*if (this.commandSuggestionHelper.onClick((double)((int)mouseX), (double)((int)mouseY), button)) {
         return true;
      } else */{
         if (button == 0) {
            ScriptJSGui newScriptGui = ScriptJSGui.getInstance();
            if (newScriptGui.func_238491_a_(mouseX, mouseY)) {
               return true;
            }

            Style style = newScriptGui.getStyleAt(mouseX, mouseY);
            if (style != null && this.handleComponentClicked(style)) {
               return true;
            }
         }

         return this.inputField.mouseClicked(mouseX, mouseY, button) || super.mouseClicked(mouseX, mouseY, button);
      }
   }

   protected void insertText(String text, boolean overwrite) {
      if (overwrite) {
         this.inputField.setText(text);
      } else {
         this.inputField.writeText(text);
      }

   }

   /**
    * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
    * message from the current cursor position
    */
   public void getSentHistory(int msgPos) {
      int i = this.sentHistoryCursor + msgPos;
      int j = ScriptJSGui.getInstance().getSentMessages().size();
      i = MathHelper.clamp(i, 0, j);
      if (i != this.sentHistoryCursor) {
         if (i == j) {
            this.sentHistoryCursor = j;
            this.inputField.setText(this.historyBuffer);
         } else {
            if (this.sentHistoryCursor == j) {
               this.historyBuffer = this.inputField.getText();
            }

            this.inputField.setText(ScriptJSGui.getInstance().getSentMessages().get(i));
//            this.commandSuggestionHelper.shouldAutoSuggest(false);
            this.sentHistoryCursor = i;
         }
      }
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      // Render history.
      RenderSystem.disableAlphaTest();
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, (float)(this.height - 48), 0.0F);
      ScriptJSGui.getInstance().render(matrixStack, Objects.requireNonNull(this.minecraft).ingameGUI.getTicks());
      RenderSystem.popMatrix();

      this.setListener(this.inputField);
      this.inputField.setFocused2(true);

      fill(matrixStack, 2, this.height - 14, this.width - 2, this.height - 2, Objects.requireNonNull(this.minecraft).gameSettings.getChatBackgroundColor(Integer.MIN_VALUE));

      this.inputField.render(matrixStack, mouseX, mouseY, partialTicks);

      // Command suggestions. Todo: add command suggestions.
//      this.commandSuggestionHelper.drawSuggestionList(matrixStack, mouseX, mouseY);

      Style style = ScriptJSGui.getInstance().getStyleAt(mouseX, mouseY);
      if (style != null && style.getHoverEvent() != null) {
         this.renderComponentHoverEffect(matrixStack, style, mouseX, mouseY);
      }

      super.render(matrixStack, mouseX, mouseY, partialTicks);
   }
   
   public boolean isPauseScreen() {
      return false;
   }

   private void setChatLine(String p_208604_1_) {
      this.inputField.setText(p_208604_1_);
   }

   @SubscribeEvent
   public static void onClientTick(TickEvent.ClientTickEvent event) {
      if (KeyBindingList.SCRIPT_SCREEN.isPressed()) {
         Minecraft mc = Minecraft.getInstance();
         mc.displayGuiScreen(new ScriptJSScreen(""));
      }
   }
}
