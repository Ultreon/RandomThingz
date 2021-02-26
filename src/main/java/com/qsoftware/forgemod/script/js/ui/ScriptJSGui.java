package com.qsoftware.forgemod.script.js.ui;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.RenderComponentsUtil;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Deque;
import java.util.List;

@SuppressWarnings({"unused", "SameParameterValue", "BooleanMethodIsAlwaysInverted", "deprecation"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class ScriptJSGui extends AbstractGui {
   private final Minecraft mc;
   /** A list of messages previously sent through the chat GUI */
   private final List<String> sentMessages = Lists.newArrayList();
   /** Chat lines to be displayed in the chat box */
   private final List<ChatLine<ITextComponent>> scriptLines = Lists.newArrayList();
   /** List of the ChatLines currently drawn */
   public final List<ChatLine<IReorderingProcessor>> drawnScriptLines = Lists.newArrayList();
   private final Deque<ITextComponent> lineQueue = Queues.newArrayDeque();
   public int scrollPos;
   private boolean isScrolled;
   private long field_238490_l_ = 0L;

   private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS:Gui");

   @Getter
   private static final ScriptJSGui instance = new ScriptJSGui(Minecraft.getInstance());

   private ScriptJSGui(Minecraft mcIn) {
      this.mc = mcIn;
   }

   public void render(MatrixStack matrixStack, int ticks) {
      this.func_238498_k_();
      int lineCount = this.getLineCount();
      int drawnLinesCount = this.drawnScriptLines.size();
      if (drawnLinesCount > 0) {
         boolean flag = true;
         double scale = this.getScale();
         int scaledWidth = MathHelper.ceil((double)this.getMessagesWidth() / scale);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(2.0F, 8.0F, 0.0F);
         RenderSystem.scaled(scale, scale, 1.0D);
         double chatOpacity = this.mc.gameSettings.chatOpacity * (double)0.9F + (double)0.1F;
         double backgroundOpacity = this.mc.gameSettings.accessibilityTextBackgroundOpacity;
         double chatLineSpacing = 9.0D * (this.mc.gameSettings.chatLineSpacing + 1.0D);
         double chatLineSpacingExtended = -8.0D * (this.mc.gameSettings.chatLineSpacing + 1.0D) + 4.0D * this.mc.gameSettings.chatLineSpacing;
         int line = 0;

         // Loop all chat lines.
         for(int index = 0; index + this.scrollPos < this.drawnScriptLines.size() && index < lineCount; ++index) {
            // Get chat line from index and scoll position.
            ChatLine<IReorderingProcessor> chatLine = this.drawnScriptLines.get(index + this.scrollPos);

            // Check chat line isn't null.
            if (chatLine != null) {
               // Get data.
               int updatedCount = ticks - chatLine.getUpdatedCounter();
               double transparency = 1.0D;
               int alpha = (int)(255.0D * transparency * chatOpacity);
               int backgroundAlpha = (int)(255.0D * transparency * backgroundOpacity);

               // Advance in line.
               ++line;

               // Render the line.
               if (alpha > 3) {
                  double endY = (double)(-index) * chatLineSpacing;

                  // Push and translate matrix stack.
                  matrixStack.push();
                  matrixStack.translate(0.0D, 0.0D, 50.0D);

                  // Fill background.
                  fill(matrixStack, -2, (int)(endY - chatLineSpacing), scaledWidth + 4, (int)endY, backgroundAlpha << 24);
                  RenderSystem.enableBlend();
                  matrixStack.translate(0.0D, 0.0D, 50.0D);

                  // Render chat line text
                  this.mc.fontRenderer.func_238407_a_(matrixStack, chatLine.getLineString(), 0.0F, (float)((int)(endY + chatLineSpacingExtended)), 0xffffff + (alpha << 24));

                  // Disable alpha test and blend.
                  RenderSystem.disableAlphaTest();
                  RenderSystem.disableBlend();

                  // Pop matrix stack.
                  matrixStack.pop();
               }
            }
         }

         if (!this.lineQueue.isEmpty()) {
            int k2 = (int)(128.0D * chatOpacity);
            int i3 = (int)(255.0D * backgroundOpacity);
            matrixStack.push();
            matrixStack.translate(0.0D, 0.0D, 50.0D);
            fill(matrixStack, -2, 0, scaledWidth + 4, 9, i3 << 24);
            RenderSystem.enableBlend();
            matrixStack.translate(0.0D, 0.0D, 50.0D);
            this.mc.fontRenderer.func_243246_a(matrixStack, new TranslationTextComponent("chat.queue", this.lineQueue.size()), 0.0F, 1.0F, 16777215 + (k2 << 24));
            matrixStack.pop();
            RenderSystem.disableAlphaTest();
            RenderSystem.disableBlend();
         }

         if (flag) {
            int l2 = 9;
            RenderSystem.translatef(-3.0F, 0.0F, 0.0F);
            int j3 = drawnLinesCount * l2 + drawnLinesCount;
            int k3 = line * l2 + line;
            int l3 = this.scrollPos * k3 / drawnLinesCount;
            int k1 = k3 * k3 / j3;
            if (j3 != k3) {
               int i4 = l3 > 0 ? 170 : 96;
               int j4 = this.isScrolled ? 13382451 : 3355562;
               fill(matrixStack, 0, -l3, 2, -l3 - k1, j4 + (i4 << 24));
               fill(matrixStack, 2, -l3, 1, -l3 - k1, 13421772 + (i4 << 24));
            }
         }

         RenderSystem.popMatrix();
      }
   }

   private boolean func_238496_i_() {
      return false;
   }

   private static double getLineBrightness(int counterIn) {
      double d0 = (double)counterIn / 200.0D;
      d0 = 1.0D - d0;
      d0 = d0 * 10.0D;
      d0 = MathHelper.clamp(d0, 0.0D, 1.0D);
      return d0 * d0;
   }

   /**
    * Clears the chat.
    *  
    * @param clearSentMsgHistory Whether or not to clear the user's sent message history
    */
   public void clearScriptMessages(boolean clearSentMsgHistory) {
      this.lineQueue.clear();
      this.drawnScriptLines.clear();
      this.scriptLines.clear();
      if (clearSentMsgHistory) {
         this.sentMessages.clear();
      }

   }

   public void printScriptMessage(ITextComponent chatComponent) {
      this.printScriptMessageWithOptionalDeletion(chatComponent, 0);
   }

   /**
    * prints the ChatComponent to Chat. If the ID is not 0, deletes an existing Chat Line of that ID from the GUI
    */
   private void printScriptMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId) {
      this.func_238493_a_(chatComponent, chatLineId, this.mc.ingameGUI.getTicks(), true);
      LOGGER.info("[STDOUT/STDERR] {}", chatComponent.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
   }

   private void func_238493_a_(ITextComponent p_238493_1_, int p_238493_2_, int p_238493_3_, boolean p_238493_4_) {
      if (p_238493_2_ != 0) {
         this.deleteChatLine(p_238493_2_);
      }

      int i = MathHelper.floor((double)this.getMessagesWidth() / this.getScale());
      List<IReorderingProcessor> list = RenderComponentsUtil.func_238505_a_(p_238493_1_, i, this.mc.fontRenderer);
      boolean flag = this.getScriptOpen();

      for(IReorderingProcessor ireorderingprocessor : list) {
         if (flag && this.scrollPos > 0) {
            this.isScrolled = true;
            this.addScrollPos(1.0D);
         }

         this.drawnScriptLines.add(0, new ChatLine<>(p_238493_3_, ireorderingprocessor, p_238493_2_));
      }

      while(this.drawnScriptLines.size() > 100) {
         this.drawnScriptLines.remove(this.drawnScriptLines.size() - 1);
      }

      if (!p_238493_4_) {
         this.scriptLines.add(0, new ChatLine<>(p_238493_3_, p_238493_1_, p_238493_2_));

         while(this.scriptLines.size() > 100) {
            this.scriptLines.remove(this.scriptLines.size() - 1);
         }
      }

   }

   public void refreshScriptGui() {
      this.drawnScriptLines.clear();
      this.resetScroll();

      for(int i = this.scriptLines.size() - 1; i >= 0; --i) {
         ChatLine<ITextComponent> chatline = this.scriptLines.get(i);
         this.func_238493_a_(chatline.getLineString(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
      }

   }

   /**
    * Gets the list of messages previously sent through the chat GUI
    */
   public List<String> getSentMessages() {
      return this.sentMessages;
   }

   /**
    * Adds this string to the list of sent messages, for recall using the up/down arrow keys
    */
   public void addToSentMessages(String message) {
      if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(message)) {
         this.sentMessages.add(message);
      }

   }

   /**
    * Resets the chat scroll (executed when the GUI is closed, among others)
    */
   public void resetScroll() {
      this.scrollPos = 0;
      this.isScrolled = false;
   }

   public void addScrollPos(double posInc) {
      this.scrollPos = (int)((double)this.scrollPos + posInc);
      int i = this.drawnScriptLines.size();
      if (this.scrollPos > i - this.getLineCount()) {
         this.scrollPos = i - this.getLineCount();
      }

      if (this.scrollPos <= 0) {
         this.scrollPos = 0;
         this.isScrolled = false;
      }

   }

   public boolean func_238491_a_(double x, double y) {
      if (this.getScriptOpen() && !this.mc.gameSettings.hideGUI && !this.func_238496_i_() && !this.lineQueue.isEmpty()) {
         double d0 = x - 2.0D;
         double d1 = (double)this.mc.getMainWindow().getScaledHeight() - y - 40.0D;
         if (d0 <= (double)MathHelper.floor((double)this.getMessagesWidth() / this.getScale()) && d1 < 0.0D && d1 > (double)MathHelper.floor(-9.0D * this.getScale())) {
            this.printScriptMessage(this.lineQueue.remove());
            this.field_238490_l_ = System.currentTimeMillis();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Nullable
   public Style getStyleAt(double x, double y) {
      if (this.getScriptOpen() && !this.mc.gameSettings.hideGUI && !this.func_238496_i_()) {
         double d0 = x - 2.0D;
         double d1 = (double)this.mc.getMainWindow().getScaledHeight() - y - 40.0D;
         d0 = MathHelper.floor(d0 / this.getScale());
         d1 = MathHelper.floor(d1 / (this.getScale() * (this.mc.gameSettings.chatLineSpacing + 1.0D)));
         if (!(d0 < 0.0D) && !(d1 < 0.0D)) {
            int i = Math.min(this.getLineCount(), this.drawnScriptLines.size());
            if (d0 <= (double)MathHelper.floor((double)this.getMessagesWidth() / this.getScale()) && d1 < (double)(9 * i + i)) {
               int j = (int)(d1 / 9.0D + (double)this.scrollPos);
               if (j >= 0 && j < this.drawnScriptLines.size()) {
                  ChatLine<IReorderingProcessor> chatline = this.drawnScriptLines.get(j);
                  return this.mc.fontRenderer.getCharacterManager().func_243239_a(chatline.getLineString(), (int)d0);
               }
            }

         }
      }
      return null;
   }

   /**
    * Returns true if the chat GUI is open
    */
   private boolean getScriptOpen() {
      return this.mc.currentScreen instanceof ScriptJSScreen;
   }

   /**
    * finds and deletes a Chat line by ID
    */
   private void deleteChatLine(int id) {
      this.drawnScriptLines.removeIf((p_243251_1_) -> p_243251_1_.getChatLineID() == id);
      this.scriptLines.removeIf((p_243250_1_) -> p_243250_1_.getChatLineID() == id);
   }

   public int getMessagesWidth() {
      return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
   }

   public int getMessagesHeight() {
      return calculateChatboxHeight(20);
   }

   public double getScale() {
      return this.mc.gameSettings.chatScale;
   }

   public static int calculateChatboxWidth(double p_194814_0_) {
      int i = 320;
      int j = 40;
      return MathHelper.floor(p_194814_0_ * 280.0D + 40.0D);
   }

   public static int calculateChatboxHeight(double p_194816_0_) {
      int i = 180;
      int j = 20;
      return MathHelper.floor(p_194816_0_ * 160.0D + 20.0D);
   }

   public int getLineCount() {
      return 10;
   }

   private long func_238497_j_() {
      return (long)(this.mc.gameSettings.chatDelay * 1000.0D);
   }

   private void func_238498_k_() {
      if (!this.lineQueue.isEmpty()) {
         long i = System.currentTimeMillis();
         if (i - this.field_238490_l_ >= this.func_238497_j_()) {
            this.printScriptMessage(this.lineQueue.remove());
            this.field_238490_l_ = i;
         }

      }
   }

   public void func_238495_b_(ITextComponent p_238495_1_) {
      if (this.mc.gameSettings.chatDelay <= 0.0D) {
         this.printScriptMessage(p_238495_1_);
      } else {
         long i = System.currentTimeMillis();
         if (i - this.field_238490_l_ >= this.func_238497_j_()) {
            this.printScriptMessage(p_238495_1_);
            this.field_238490_l_ = i;
         } else {
            this.lineQueue.add(p_238495_1_);
         }
      }

   }
}
