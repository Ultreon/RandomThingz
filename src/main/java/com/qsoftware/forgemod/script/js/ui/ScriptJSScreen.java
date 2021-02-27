package com.qsoftware.forgemod.script.js.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.keybinds.KeyBindingList;
import com.qsoftware.forgemod.modules.ui.widgets.TransparentButton;
import com.qsoftware.forgemod.network.Network;
import com.qsoftware.forgemod.network.ScriptPacket;
import com.qsoftware.forgemod.script.js.ScriptJSFormatter;
import com.qsoftware.forgemod.script.js.ScriptJSManager;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
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
import javax.script.ScriptException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

@SuppressWarnings({"deprecation", "unused"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = QForgeMod.modId, value = Dist.CLIENT)
public class ScriptJSScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS:Screen");
   private String historyBuffer = "";
   private Dist executeDist = Dist.DEDICATED_SERVER;

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
   private TransparentButton serverButton;
   private TransparentButton clientButton;
   //   private CommandSuggestionHelper commandSuggestionHelper;

   public ScriptJSScreen(String defaultText) {
      super(NarratorChatListener.EMPTY);
      this.defaultInputFieldText = defaultText;
   }

   protected void init() {
      Objects.requireNonNull(this.minecraft).keyboardListener.enableRepeatEvents(true);
      this.sentHistoryCursor = ScriptJSServerGui.getInstance().getSentMessages().size();
      this.inputField = new ScriptJSTextWidget(this.font, 4, this.height - 12, this.width - 4, 12, new TranslationTextComponent("chat.editBox")) {
//         protected IFormattableTextComponent getNarrationMessage() {
//            return super.getNarrationMessage().appendString(ChatScreen.this.commandSuggestionHelper.getSuggestionMessage());
//         }
      };
      this.serverButton = this.addButton(new TransparentButton(4, height - 40, 40, 20, new TranslationTextComponent("misc.server"), (btn) -> {
         this.executeDist = Dist.DEDICATED_SERVER;
         this.redirectServer();
      }));
      this.clientButton = this.addButton(new TransparentButton(4, height - 40, 40, 20, new TranslationTextComponent("misc.client"), (btm) -> {
         this.executeDist = Dist.CLIENT;
         this.redirectClient();
      }));
//      this.inputField.setTextFormatter((p_195610_0_, p_195610_1_) -> (p_242236_0_) -> false);
      this.inputField.setMaxStringLength(256);
      this.inputField.setEnableBackgroundDrawing(false);
      this.inputField.setText(this.defaultInputFieldText);
      this.inputField.setResponder(this::textChanged);
      this.children.add(this.inputField);
      this.children.add(this.serverButton);
      this.buttons.add(this.serverButton);
//      this.commandSuggestionHelper = new CommandSuggestionHelper(this.minecraft, this, this.inputField, this.font, false, false, 1, 10, true, -805306368);
//      this.commandSuggestionHelper.init();
      this.setFocusedDefault(this.inputField);

      this.serverButton.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(serverButton.getMessage().getString()) + 4);
      this.clientButton.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(clientButton.getMessage().getString()) + 4);
      this.clientButton.x = 4 + Minecraft.getInstance().fontRenderer.getStringWidth(serverButton.getMessage().getString()) + 8;
   }

   private void redirectClient() {

   }

   private void redirectServer() {

   }

   public void resize(Minecraft minecraft, int width, int height) {
      String s = this.inputField.getText();
      this.init(minecraft, width, height);
      this.setChatLine(s);
//      this.commandSuggestionHelper.init();
   }

   public void onClose() {
      Objects.requireNonNull(this.minecraft).keyboardListener.enableRepeatEvents(false);
      ScriptJSServerGui.getInstance().resetScroll();
   }

   public void tick() {
      this.inputField.tick();
      this.serverButton.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(serverButton.getMessage().getString()) + 4);
      this.clientButton.setWidth(Minecraft.getInstance().fontRenderer.getStringWidth(clientButton.getMessage().getString()) + 4);
      this.clientButton.x = 4 + Minecraft.getInstance().fontRenderer.getStringWidth(serverButton.getMessage().getString()) + 8;
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

   @SuppressWarnings("CodeBlock2Expr")
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      AbstractScriptJSGui jsGui;
      if (executeDist == Dist.DEDICATED_SERVER) {
         jsGui = ScriptJSServerGui.getInstance();
      } else if (executeDist == Dist.CLIENT) {
         jsGui = ScriptJSClientGui.getInstance();
      } else {
         throw new IllegalStateException("Invalid dist: " + executeDist);
      }
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
            jsGui.addScrollPos(ScriptJSServerGui.getInstance().getLineCount() - 1);
            return true;
         } else if (keyCode == 267) {
            jsGui.addScrollPos(-ScriptJSServerGui.getInstance().getLineCount() + 1);
            return true;
         } else {
            return false;
         }
      } else {
         String s = TextUtils.stripFormatting(this.inputField.getText().trim());
         jsGui.addToSentCommands(s);
         jsGui.printScriptMessage(new StringTextComponent(TextFormatting.GRAY.toString() + TextFormatting.BOLD.toString() + TextFormatting.UNDERLINE + "$ " + TextFormatting.RESET + TextFormatting.WHITE.toString() + TextFormatting.UNDERLINE + s));

         if (!s.isEmpty()) {
            if (executeDist == Dist.DEDICATED_SERVER) {
               Network.channel.sendToServer(new ScriptPacket(s));
            } else if (executeDist == Dist.CLIENT) {
               ScriptJSManager.getClientInstance().eval(s).ifLeft((e) -> {
                  e.printStackTrace();
                  if (e instanceof ScriptException) {
                     jsGui.printScriptMessage(new StringTextComponent(TextFormatting.RED + e.getMessage()));
                  } else {
                     StringWriter writer = new StringWriter();
                     PrintWriter writer1 = new PrintWriter(writer);
                     e.printStackTrace(writer1);
                     String s1 = writer.toString();
                     for (String s2 : s1.split("(\r\n|\r|\n)")) {
                        jsGui.printScriptMessage(new StringTextComponent(TextFormatting.RED + s2.replaceAll("\r", "")));
                     }
                  }
               }).ifRight((eval) -> {
                  jsGui.printScriptMessage(new StringTextComponent(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(eval)));
               });
               ScriptJSManager.getClientInstance().eval(s);
            }
         }

         this.inputField.setText("");

         return true;
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
      AbstractScriptJSGui jsGui;
      if (executeDist == Dist.DEDICATED_SERVER) {
         jsGui = ScriptJSServerGui.getInstance();
      } else if (executeDist == Dist.CLIENT) {
         jsGui = ScriptJSClientGui.getInstance();
      } else {
         throw new IllegalStateException("Invalid dist: " + executeDist);
      }
//      if (delta > 1.0D) {
//         delta = 1.0D;
//      }
//
//      if (delta < -1.0D) {
//         delta = -1.0D;
//      }

      /*if (this.commandSuggestionHelper.onScroll(delta)) {
         return true;
      } else */
      {
         if (!hasShiftDown()) {
            delta *= 7.0D;
         }

         jsGui.addScrollPos(delta);
         return true;
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      /*if (this.commandSuggestionHelper.onClick((double)((int)mouseX), (double)((int)mouseY), button)) {
         return true;
      } else */{
         if (button == 0) {
            ScriptJSServerGui newScriptGui = ScriptJSServerGui.getInstance();
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
      AbstractScriptJSGui jsGui;
      if (executeDist == Dist.DEDICATED_SERVER) {
         jsGui = ScriptJSServerGui.getInstance();
      } else if (executeDist == Dist.CLIENT) {
         jsGui = ScriptJSClientGui.getInstance();
      } else {
         throw new IllegalStateException("Invalid dist: " + executeDist);
      }
      int i = this.sentHistoryCursor + msgPos;
      int j = jsGui.getSentMessages().size();
      i = MathHelper.clamp(i, 0, j);
      if (i != this.sentHistoryCursor) {
         if (i == j) {
            this.sentHistoryCursor = j;
            this.inputField.setText(this.historyBuffer);
         } else {
            if (this.sentHistoryCursor == j) {
               this.historyBuffer = this.inputField.getText();
            }

            this.inputField.setText(jsGui.getSentMessages().get(i));
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

      if (executeDist == Dist.DEDICATED_SERVER) {
         ScriptJSServerGui.getInstance().render(matrixStack, Objects.requireNonNull(this.minecraft).ingameGUI.getTicks());
      } else if (executeDist == Dist.CLIENT) {
         ScriptJSClientGui.getInstance().render(matrixStack, Objects.requireNonNull(this.minecraft).ingameGUI.getTicks());
      } else {
         throw new IllegalStateException("Invalid dist: " + executeDist);
      }
      RenderSystem.popMatrix();

      this.setListener(this.inputField);
      this.inputField.setFocused2(true);

      fill(matrixStack, 2, this.height - 14, this.width - 2, this.height - 2, Objects.requireNonNull(this.minecraft).gameSettings.getChatBackgroundColor(Integer.MIN_VALUE));

      this.inputField.render(matrixStack, mouseX, mouseY, partialTicks);

      // Command suggestions. Todo: add command suggestions.
//      this.commandSuggestionHelper.drawSuggestionList(matrixStack, mouseX, mouseY);

      Style style = ScriptJSServerGui.getInstance().getStyleAt(mouseX, mouseY);
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
