package com.ultreon.randomthingz.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.ultreon.modlib.silentlib.util.TextRenderUtils;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.crusher.CrusherScreen;
import com.ultreon.randomthingz.item.crafting.CrushingRecipe;
import com.ultreon.randomthingz.util.Constants;
import com.ultreon.randomthingz.util.TextUtils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CrushingRecipeCategoryJei implements IRecipeCategory<CrushingRecipe> {
    private static final int GUI_START_X = 25;
    private static final int GUI_START_Y = 34;
    private static final int GUI_WIDTH = 151 - GUI_START_X;
    private static final int GUI_HEIGHT = 60 - GUI_START_Y;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final MutableComponent title;

    public CrushingRecipeCategoryJei(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(CrusherScreen.TEXTURE, GUI_START_X, GUI_START_Y, GUI_WIDTH, GUI_HEIGHT);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.CRUSHER));
        arrow = guiHelper.drawableBuilder(CrusherScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        title = TextUtils.translate("jei", "category.crushing");
    }

    @Override
    public ResourceLocation getUid() {
        return Constants.CRUSHING;
    }

    @Override
    public Class<? extends CrushingRecipe> getRecipeClass() {
        return CrushingRecipe.class;
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
    public void setRecipe(IRecipeLayoutBuilder builder, CrushingRecipe recipe, IFocusGroup ingredients) {
        builder.addSlot(RecipeIngredientRole.INPUT, 0, 0).addItemStacks(List.of(recipe.getIngredient().getItems()));
        IRecipeSlotBuilder[] outputs = new IRecipeSlotBuilder[]{
                builder.addSlot(RecipeIngredientRole.OUTPUT, 54, 0),
                builder.addSlot(RecipeIngredientRole.OUTPUT, 72, 0),
                builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 0),
                builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 0),
        };

        // Outputs
        List<Pair<ItemStack, Float>> results = recipe.getPossibleResultsWithChances();
        for (int i = 0; i < results.size(); ++i) {
            if (i >= outputs.length) {
                break;
            }
            outputs[i].addItemStack(results.get(i).getFirst());
        }
    }

    @Override
    public void draw(CrushingRecipe recipe, IRecipeSlotsView slotsView, PoseStack pose, double mouseX, double mouseY) {
        arrow.draw(pose, 49 - GUI_START_X, 35 - GUI_START_Y);

        Font font = Minecraft.getInstance().font;

        List<Pair<ItemStack, Float>> results = recipe.getPossibleResultsWithChances();
        for (int i = 0; i < results.size(); ++i) {
            float chance = results.get(i).getSecond();
            if (chance < 1) {
                int asPercent = (int) (100 * chance);
                String text = asPercent < 1 ? "<1%" : asPercent + "%";
                TextRenderUtils.renderScaled(pose, font, new TextComponent(text).getVisualOrderText(), 57 + 18 * i, 20, .75f, 0xFFFFFF, true);
            }
        }
    }
}
