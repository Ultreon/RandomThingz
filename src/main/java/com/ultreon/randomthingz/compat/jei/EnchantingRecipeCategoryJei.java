package com.ultreon.randomthingz.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.alloysmelter.AlloySmelterScreen;
import com.ultreon.randomthingz.item.crafting.AlloySmeltingRecipe;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.Map;

public class EnchantingRecipeCategoryJei implements IRecipeCategory<AlloySmeltingRecipe> {
    private static final int GUI_START_X = 16;
    private static final int GUI_START_Y = 30;
    private static final int GUI_WIDTH = 146 - GUI_START_X;
    private static final int GUI_HEIGHT = 55 - GUI_START_Y;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final MutableComponent title;

    public EnchantingRecipeCategoryJei(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(AlloySmelterScreen.TEXTURE, GUI_START_X, GUI_START_Y, GUI_WIDTH, GUI_HEIGHT);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.ARCANE_ESCALATOR));
        arrow = guiHelper.drawableBuilder(AlloySmelterScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        title = TextUtils.translate("jei", "category.alloy_smelting");
    }

    @Override
    public ResourceLocation getUid() {
        return Constants.ALLOY_SMELTING;
    }

    @Override
    public Class<? extends AlloySmeltingRecipe> getRecipeClass() {
        return AlloySmeltingRecipe.class;
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
    public void setRecipe(IRecipeLayoutBuilder builder, AlloySmeltingRecipe recipe, IFocusGroup ingredients) {
        IRecipeSlotBuilder[] inputs = new IRecipeSlotBuilder[]{
                builder.addSlot(RecipeIngredientRole.INPUT, 16 - GUI_START_X, 34 - GUI_START_Y),
                builder.addSlot(RecipeIngredientRole.INPUT, 34 - GUI_START_X, 34 - GUI_START_Y),
                builder.addSlot(RecipeIngredientRole.INPUT, 52 - GUI_START_X, 34 - GUI_START_Y),
                builder.addSlot(RecipeIngredientRole.INPUT, 70 - GUI_START_X, 34 - GUI_START_Y)
        };

        int i = 0;
        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredientMap().entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer count = entry.getValue();
            if (i >= inputs.length) {
                break;
            }
            inputs[i++].addItemStacks(Arrays.stream(ingredient.getItems())
                    .map(s -> {
                        ItemStack stack = s.copy();
                        stack.setCount(count);
                        return stack;
                    })
                    .toList()
            );
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 125 - GUI_START_X, 34 - GUI_START_Y).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(AlloySmeltingRecipe recipe, IRecipeSlotsView slotsView, PoseStack pose, double mouseX, double mouseY) {
        arrow.draw(pose, 92 - GUI_START_X, 35 - GUI_START_Y);
    }
}
