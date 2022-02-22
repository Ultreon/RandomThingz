package com.ultreon.randomthingz.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.modlib.silentlib.util.TextRenderUtils;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.compressor.CompressorScreen;
import com.ultreon.randomthingz.item.crafting.DryingRecipe;
import com.ultreon.randomthingz.util.Constants;
import com.ultreon.randomthingz.util.TextUtils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DryingRecipeCategoryJei implements IRecipeCategory<DryingRecipe> {
    private static final int GUI_START_X = 55;
    private static final int GUI_START_Y = 30;
    private static final int GUI_WIDTH = 137 - GUI_START_X;
    private static final int GUI_HEIGHT = 56 - GUI_START_Y;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final MutableComponent title;

    public DryingRecipeCategoryJei(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(CompressorScreen.TEXTURE, GUI_START_X, GUI_START_Y, GUI_WIDTH, GUI_HEIGHT);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.OAK_DRYING_RACK));
        arrow = guiHelper.drawableBuilder(CompressorScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        title = TextUtils.translate("jei", "category.drying");
    }

    @Override
    public ResourceLocation getUid() {
        return Constants.DRYING;
    }

    @Override
    public Class<? extends DryingRecipe> getRecipeClass() {
        return DryingRecipe.class;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DryingRecipe recipe, IFocusGroup ingredients) {
        builder.addSlot(RecipeIngredientRole.INPUT, 55 - GUI_START_X, 34 - GUI_START_Y).addItemStacks(List.of(recipe.getIngredient().getItems()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 55 - GUI_START_X, 34 - GUI_START_Y).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(DryingRecipe recipe, IRecipeSlotsView slotsView, PoseStack pose, double mouseX, double mouseY) {
        arrow.draw(pose, 79 - GUI_START_X, 35 - GUI_START_Y);
        Font font = Minecraft.getInstance().font;
        Component text = TextUtils.translate("misc", "timeInSeconds", recipe.getProcessTime() / 20);
        TextRenderUtils.renderScaled(pose, font, text.getVisualOrderText(), 24, 20, .67f, 0xFFFFFF, true);
    }
}
