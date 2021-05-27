package com.qtech.randomthingz.client.gui.modules;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.commons.Module;
import com.qtech.randomthingz.commons.ModuleManager;
import com.qtech.randomthingz.commons.ModuleSafety;
import com.qtech.randomthingz.modules.ui.screens.AdvancedScreen;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.PackScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.Tessellator;
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
   private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(RandomThingz.MOD_ID, "textures/empty.png");
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
      ITextComponent itextcomponent = (new StringTextComponent("")).appendSibling(this.title).mergeStyle(TextFormatting.UNDERLINE, TextFormatting.BOLD);
      this.minecraft.fontRenderer.drawText(matrixStack, itextcomponent, (float)(x + this.width / 2 - this.minecraft.fontRenderer.getStringPropertyWidth(itextcomponent) / 2), (float)Math.min(this.y0 + 3, y), 16777215);
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
      private static final ResourceLocation MODULE_OVERLAYS = new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/overlays/modules.png");
      private static final ResourceLocation OPTIONS_BUTTON = new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/modules/buttons/options.png");
      private static final ResourceLocation MANAGER_BUTTON = new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/modules/buttons/manager.png");
      private final ModuleList list;
      protected final Minecraft mc;
      protected final Screen screen;
      private final Module module;
      private final IReorderingProcessor reorderingLocalizedName;
      private final ModuleManager manager = ModuleManager.getInstance();
      private long debugRefresh = 0;

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
       * @return a {@linkplain IReorderingProcessor reordering processor}.
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
       * @param listIndex the list index.
       * @param scroll the scroll delta y position.
       * @param xOffset the x-offset of the list.
       * @param rowWidth the row width of the list entry.
       * @param rowHeight the row height of the list entry.
       * @param mouseX real mouse x coordinate.
       * @param mouseY real mouse y coordinate.
       * @param isHovered ...
       * @param partialTicks the {@linkplain Minecraft#getRenderPartialTicks() render partial ticks}.
       */
      @SuppressWarnings("DanglingJavadoc")
      public void render(MatrixStack matrixStack, int listIndex, int scroll, int xOffset, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isHovered, float partialTicks) {
         // Get module compatibility and security.
         ModuleCompatibility compatibility = this.module.getCompatibility();
         ModuleSafety security = this.module.getSafety();

         // If there's no compatibility show a red color.
         if (compatibility == ModuleCompatibility.NONE) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            AbstractGui.fill(matrixStack, xOffset - 1, scroll - 1, xOffset + rowWidth - 9, scroll + rowHeight + 1, 0xff770000);
         }

         // If there's partial compatibility show a orange yellow color.
         if (compatibility == ModuleCompatibility.PARTIAL) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            AbstractGui.fill(matrixStack, xOffset - 1, scroll - 1, xOffset + rowWidth - 9, scroll + rowHeight + 1, 0xff773a00);
         }

         // Calculate mouse position offset for scrolling etc.
         int mouseXOffset = mouseX - xOffset;
         int mouseYOffset = mouseY - scroll;

         // Bind the icon texture.
         this.mc.getTextureManager().bindTexture(this.module.getTextureLocation());

         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         AbstractGui.blit(matrixStack, xOffset, scroll, 0.0F, 0.0F, 32, 32, 32, 32);

         int dx = 64 + xOffset + 8;

         // Bind the informative icon texture overlays.
         this.mc.getTextureManager().bindTexture(MODULE_OVERLAYS);

         // Show icon for core modules.
         if (this.module.isCore()) {
            dx += 18;
            AbstractGui.blit(matrixStack, dx, scroll + 14, 64.0F, 0.0F, 16, 16, 256, 256);
         }

         // Check security, show icon for experimental / risc security.
         switch (security) {
            case EXPERIMENTAL:
               // Show experimental icon texture. (Red shield icon, white exclamation point)
               dx += 18;
               AbstractGui.blit(matrixStack, dx, scroll + 14, 80.0F, 0.0F, 16, 16, 256, 256);
               break;
            case RISC:
               // Show risc icon texture. (Yellow shield icon, black exclamation point)
               dx += 18;
               AbstractGui.blit(matrixStack, dx, scroll + 14, 96.0F, 0.0F, 16, 16, 256, 256);
               break;
            case SAFE:
               // Don't show any security icon texture.
               break;
         }

         // Check compatibility, show icon for no / partial compatibility.
         switch (compatibility) {
            case NONE:
               // Show no compatibility icon texture. (Red circle, white exclamation point)
               dx += 18;
               AbstractGui.blit(matrixStack, dx, scroll + 14, 48.0F, 0.0F, 16, 16, 256, 256);
               break;
            case PARTIAL:
               // Show partial compatibility icon texture. (Yellow triangle, black exclamation point)
               dx += 18;
               AbstractGui.blit(matrixStack, dx, scroll + 14, 32.0F, 0.0F, 16, 16, 256, 256);
               break;
            case FULL:
               // Don't show any compatibility icon texture.
               break;
         }

         /**
          * ? Don't really know what this is doing, but it's probably needed, because it's in the PackScreen class.
          *
          * @see PackScreen;
          */
         IReorderingProcessor reorderingLocalizedName = this.reorderingLocalizedName;

         // Check if hovered / touchscreen support enabled.
         if (this.mc.gameSettings.touchscreen || isHovered) {
            // Bind icon texture for arrow.
            this.mc.getTextureManager().bindTexture(ModuleList.ICONS);

            // Check if can be disabled.
            if (this.module.canDisable()) {
               // Show white overlay on top of the module icon.
               AbstractGui.fill(matrixStack, xOffset, scroll, xOffset + 32, scroll + 32, -1601138544);
            }

            // Set to full color for arrow icon to display.
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            // Normal arrow button state.
            if (!manager.isUnsavedEnabled(this.module)) {
               // Module is scheduled to be enabled when saved.
               // Check hover state.
               if (mouseXOffset < 32) {
                  // Hovered.
                  AbstractGui.blit(matrixStack, xOffset, scroll, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  // Non-hovered.
                  AbstractGui.blit(matrixStack, xOffset, scroll, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            } else if (manager.isUnsavedEnabled(this.module) && this.module.canDisable()) {
               // Module is scheduled to be disabled when saved.
               // Check hover state.
               if (mouseXOffset < 32) {
                  // Hovered.
                  AbstractGui.blit(matrixStack, xOffset, scroll, 26.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  // Non-hovered.
                  AbstractGui.blit(matrixStack, xOffset, scroll, 26.0F, 0.0F, 32, 32, 256, 256);
               }
            }
         }

         this.mc.fontRenderer.drawTextWithShadow(matrixStack, reorderingLocalizedName, (float)(xOffset + 32 + 2), (float)(scroll + 1), 16777215);

         int btnX = 36;
         int btnY = 12;
         int btnSubX = btnX + xOffset;
         int btnSubY = btnY + scroll;

         // Rendering options button.
         this.mc.getTextureManager().bindTexture(OPTIONS_BUTTON);

         // Check if the module of this entry has an options screen available.
         if (this.module.hasOptions()) {
            // Check hover state.
            if (AdvancedScreen.isPointInRegion(btnX, btnY, 19, 20, mouseXOffset, mouseYOffset)) {
               // Hovered state.
               blit(matrixStack, btnSubX, btnSubY, 19, 20, 0, 40, 19, 20, 19, 60);
            } else {
               // Normal state.
               blit(matrixStack, btnSubX, btnSubY, 19, 20, 0, 20, 19, 20, 19, 60);
            }
         } else {
            // Disabled state.
            blit(matrixStack, btnSubX, btnSubY, 19, 20, 0, 0, 19, 20, 19, 60);
         }

         // Rendering submodule screen button.
         this.mc.getTextureManager().bindTexture(MANAGER_BUTTON);

         // Check if submodule manager is enabled for the module of this entry.
         if (this.module.isSubManagerEnabled()) {
            // Check hover state.
            if (AdvancedScreen.isPointInRegion(btnX + 22, btnY, 19, 20, mouseXOffset, mouseYOffset)) {
               // Hovered state.
               blit(matrixStack, btnSubX + 22, btnSubY, 19, 20, 0, 40, 19, 20, 19, 60);
            } else {
               // Normal state.
               blit(matrixStack, btnSubX + 22, btnSubY, 19, 20, 0, 20, 19, 20, 19, 60);
            }
         } else {
            // Disabled state.
            blit(matrixStack, btnSubX + 22, btnSubY, 19, 20, 0, 0, 19, 20, 19, 60);
         }
      }

      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         // Get offset position for mouse pointer.
         double mouseXOffset = mouseX - (double)this.list.getRowLeft();
         double mouseYOffset = mouseY - (double)this.list.getRowTop(this.list.getEventListeners().indexOf(this));

         // Declare button position for mouse click event check for the buttons (options, submodules).
         int btnX = 36;
         int btnY = 12;

         // ! Todo: Remove this debug information.
         System.out.println("btnX1=" + btnX);
         System.out.println("btnY1=" + btnY);
         System.out.println("btnX2=" + (btnX + 19));
         System.out.println("btnY2=" + (btnY + 20));
         System.out.println("btnWidth=" + 19);
         System.out.println("btnHeight=" + 20);
         System.out.println("deltaX=" + mouseXOffset);
         System.out.println("deltaY=" + mouseYOffset);
         System.out.println("isPointInRegion=" + AdvancedScreen.isPointInRegion(btnX, btnY, 19, 20, mouseXOffset, mouseYOffset));

         if (this.module.hasOptions() && AdvancedScreen.isPointInRegion(btnX, btnY, 19, 20, mouseXOffset, mouseYOffset)) {
            this.module.showOptions(this.screen);
         }
         if (this.module.isSubManagerEnabled() && AdvancedScreen.isPointInRegion(btnX + 22, btnY, 19, 20, mouseXOffset, mouseYOffset)) {
            Minecraft.getInstance().displayGuiScreen(new ModuleScreen(this.screen, this.module.getSubmoduleManager()));
         }
         // Check if is on arrow button.
         if (mouseXOffset <= 32.0D) {
            // Get the unsaved is disabled flag from the module using the module manager.
            if (this.manager.isUnsavedDisabled(this.module)) {
               // Get the compatibility of the module.
               ModuleCompatibility compatibility = this.module.getCompatibility();
               ModuleSafety security = this.module.getSafety();

               // Switch security state enum.
               switch (security) {
                  case SAFE:
                     // Just enable the module. It's safe, so no warnings should be needed.
                     this.manager.enable(this.module);
                     break;
                  case RISC:
                  case EXPERIMENTAL:
                     // Show confirm message, because it's insecure.
                     ITextComponent securityText = security.getConfirmMessage();

                     // Display gui screen.
                     this.mc.displayGuiScreen(new ConfirmScreen((confirmed) -> {
                        // Check if confirmed.
                        if (confirmed) {
                           // Switch compatibility state enum.
                           if (compatibility.isCompatible() && compatibility.isRunnable()) {
                              // Just enable the module.
                              this.manager.enable(this.module);

                              // Return back to module screen.
                              this.mc.displayGuiScreen(this.screen);
                           } else if (!compatibility.isCompatible() && compatibility.isRunnable()) {
                              // Get the confirm message for the compatibility.
                              ITextComponent compatText = compatibility.getConfirmMessage();

                              // Show confirm message, because compatibility is partial.
                              this.mc.displayGuiScreen(new ConfirmScreen((p_238921_1_) -> {
                                 if (p_238921_1_) {
                                    this.manager.enable(this.module);
                                 }
                                 this.mc.displayGuiScreen(this.screen);

                              }, ModuleList.INCOMPATIBLE_CONFIRM_TITLE, compatText));
                           } // Note: Don't enable if no compatibility.
                        } else {
                           this.mc.displayGuiScreen(this.screen);
                        }
                     }, ModuleList.INCOMPATIBLE_CONFIRM_TITLE, securityText));
                     break;
                  default:
                     break;
               }

               switch (compatibility) {
                  case FULL:
                     // Just enable the module.
                     this.manager.enable(this.module);
                     break;
                  case PARTIAL:
                     // Show confirm message, because compatibility is partial.
                     ITextComponent textComponent = compatibility.getConfirmMessage();
                     this.mc.displayGuiScreen(new ConfirmScreen((p_238921_1_) -> {
                        if (p_238921_1_) {
                           this.manager.enable(this.module);
                        }
                        this.mc.displayGuiScreen(this.screen);

                     }, ModuleList.INCOMPATIBLE_CONFIRM_TITLE, textComponent));
                     break;
                  case NONE:
                     // Don't enable if no compatibility.
                     return false;
                  default:
                     break;
               }

               // Reload module screen.
               this.list.screen.reload();
               return true;
            }

            if (this.manager.isUnsavedEnabled(this.module)) {
               if (this.module.canDisable()) {
                  // The module can be disabled, so disable the module.
                  this.manager.disable(this.module);

                  // Reload module screen.
                  this.list.screen.reload();
                  return true;
               } else {
                  // Don't disable
                  return false;
               }
            }
         }

         return false;
      }
   }
}
