package com.qsoftware.forgemod.client.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.ModuleScreen;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.common.interfaces.Module;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Module list for the challenge screen.
 * 
 * @author Qboi123
 */
@SuppressWarnings({"unused", "deprecation"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class ModuleList extends ExtendedList<ModuleList.ModuleEntry> {
   private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/resource_packs.png");
   private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(QForgeMod.MOD_ID, "textures/empty.png");
   private static final ITextComponent INCOMPATIBLE_TEXT = new TranslationTextComponent("challenges.incompatible");
   private static final ITextComponent INCOMPATIBLE_CONFIRM_TITLE = new TranslationTextComponent("challenges.incompatible.confirm.title");
   private final ITextComponent title;
   private final ModuleScreen screen;

   /**
    * Constructor for the challenge list widget.
    * 
    * @param minecraft the minecraft instance.
    * @param widthIn the list's width.
    * @param heightIn the list's height.
    * @param title the list title.
    */
   public ModuleList(ModuleScreen screen, Minecraft minecraft, int widthIn, int heightIn, ITextComponent title) {
      super(minecraft, widthIn, heightIn, 32, heightIn - 55 + 4, 36);

      // The module screen.
      this.screen = screen;

      // The title.
      this.title = title;

      // Center list vertically, NOPE!
      this.centerListVertically = false;

      // Set to render header.
      this.setRenderHeader(true, (int)(9.0F * 1.5F));
   }

   /**
    * Render the header.
    *
    * @param matrixStack the matrix stack.
    * @param x the delta x position to where to render.
    * @param y the delta y position to where to render.
    * @param tessellator the tessellator.
    */
   @Override
   protected void renderHeader(MatrixStack matrixStack, int x, int y, Tessellator tessellator) {
      ITextComponent itextcomponent = (new StringTextComponent("")).append(this.title).mergeStyle(TextFormatting.UNDERLINE, TextFormatting.BOLD);
      this.minecraft.fontRenderer.func_243248_b(matrixStack, itextcomponent, (float)(x + this.width / 2 - this.minecraft.fontRenderer.getStringPropertyWidth(itextcomponent) / 2), (float)Math.min(this.y0 + 3, y), 16777215);
   }

   /**
    * @return the row width.
    */
   @Override
   public int getRowWidth() {
      return this.width;
   }

   /**
    * @return the scrollbar x position.
    */
   @Override
   protected int getScrollbarPosition() {
      return this.x1 - 6;
   }

   /**
    * The module list-entry.
    *
    * @author Qboi123
    */
   @SuppressWarnings("deprecation")
   @OnlyIn(Dist.CLIENT)
   public static class ModuleEntry extends ExtendedList.AbstractListEntry<ModuleEntry> {
      private final ModuleList list;
      protected final Minecraft mc;
      protected final Screen screen;
      private final Module module;
      private final IReorderingProcessor reorderingLocalizedName;
      private final ModuleManager manager = ModuleManager.getInstance();

      /**
       * Module list entry.
       *
       * @param minecraft the minecraft instance.
       * @param list the challenge list.
       * @param screen a screen.
       * @param module the challenge to hold in the entry,
       */
      public ModuleEntry(Minecraft minecraft, ModuleList list, Screen screen, Module module) {
         this.mc = minecraft;
         this.screen = screen;
         this.module = module;
         this.list = list;
         this.reorderingLocalizedName = getReordering(minecraft, module.getLocalizedName());
      }

      /**
       * Get reordering processor from a text component.
       *
       * @param minecraft the minecraft instance.
       * @param text the text component to process.
       * @return a {@link IReorderingProcessor reordering processor}.
       */
      private static IReorderingProcessor getReordering(Minecraft minecraft, ITextComponent text) {
         int i = minecraft.fontRenderer.getStringPropertyWidth(text);
         if (i > 157) {
            ITextProperties itextproperties = ITextProperties.func_240655_a_(minecraft.fontRenderer.func_238417_a_(text, 157 - minecraft.fontRenderer.getStringWidth("...")), ITextProperties.func_240652_a_("..."));
            return LanguageMap.getInstance().func_241870_a(itextproperties);
         } else {
            return text.func_241878_f();
         }
      }

      /**
       * Renders the challenge list entry.
       *
       * @param matrixStack the matrix-stack for rendering.
       * @param unknown1 ...
       * @param subY ... (Not checked)
       * @param subX ... (Not checked)
       * @param x ... (Not checked)
       * @param y ... (Not checked)
       * @param mouseX real mouse x coordinate.
       * @param mouseY real mouse y coordinate.
       * @param p_230432_9_ ...
       * @param partialTicks the {@link Minecraft#getRenderPartialTicks() render partial ticks}.
       */
      public void render(MatrixStack matrixStack, int unknown1, int subY, int subX, int x, int y, int mouseX, int mouseY, boolean p_230432_9_, float partialTicks) {
         ModuleCompatibility compatibility = this.module.getCompatibility();
         if (!compatibility.isCompatible()) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            AbstractGui.fill(matrixStack, subX - 1, subY - 1, subX + x - 9, subY + y + 1, -8978432);
         }

         this.mc.getTextureManager().bindTexture(this.module.getTextureLocation());
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         AbstractGui.blit(matrixStack, subX, subY, 0.0F, 0.0F, 32, 32, 32, 32);
         IReorderingProcessor reorderingLocalizedName = this.reorderingLocalizedName;
         if (this.mc.gameSettings.touchscreen || p_230432_9_) {
            this.mc.getTextureManager().bindTexture(ModuleList.ICONS);
            if (this.module.canDisable()) {
               AbstractGui.fill(matrixStack, subX, subY, subX + 32, subY + 32, -1601138544);
            }
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int i = mouseX - subX;
            int j = mouseY - subY;

            // Normal arrow button state.
            if (!manager.isUnsavedEnabled(this.module)) {
               // Check hover state.
               if (i < 32) {
                  // Hovered.
                  AbstractGui.blit(matrixStack, subX, subY, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  // Non-hovered.
                  AbstractGui.blit(matrixStack, subX, subY, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            } else if (manager.isUnsavedEnabled(this.module) && this.module.canDisable()) {
               // Check hover state.
               if (i < 32) {
                  // Hovered.
                  AbstractGui.blit(matrixStack, subX, subY, 26.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  // Non-hovered.
                  AbstractGui.blit(matrixStack, subX, subY, 26.0F, 0.0F, 32, 32, 256, 256);
               }
            }
         }

         this.mc.fontRenderer.func_238407_a_(matrixStack, reorderingLocalizedName, (float)(subX + 32 + 2), (float)(subY + 1), 16777215);
      }

      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         double deltaX = mouseX - (double)this.list.getRowLeft();
         double deltaY = mouseY - (double)this.list.getRowTop(this.list.getEventListeners().indexOf(this));
         if (deltaX <= 32.0D) {
            if (this.manager.isUnsavedDisabled(this.module)) {
               ModuleCompatibility compatibility = this.module.getCompatibility();
               if (compatibility.isCompatible()) {
                  this.manager.enable(this.module);
               } else {
                  ITextComponent itextcomponent = compatibility.getConfirmMessage();
                  this.mc.displayGuiScreen(new ConfirmScreen((p_238921_1_) -> {
                     this.mc.displayGuiScreen(this.screen);
                     if (p_238921_1_) {
                        this.manager.enable(this.module);
                     }

                  }, ModuleList.INCOMPATIBLE_CONFIRM_TITLE, itextcomponent));
               }

               this.list.screen.reload();

               return true;
            }

            if (deltaX < 32.0D && this.manager.isUnsavedEnabled(this.module)
            ) {
               if (this.module.canDisable()) {
                  this.manager.disable(this.module);
               }
               this.list.screen.reload();
               return true;
            }
         }

         return false;
      }
   }
}
