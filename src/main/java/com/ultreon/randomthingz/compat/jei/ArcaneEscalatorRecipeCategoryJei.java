package com.ultreon.randomthingz.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.arcaneescalator.ArcaneEscalatorScreen;
import com.ultreon.randomthingz.item.crafting.ArcaneEscalatingRecipe;
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

public class ArcaneEscalatorRecipeCategoryJei implements IRecipeCategory<ArcaneEscalatingRecipe> {
    private static final int GUI_START_X = 16;
    private static final int GUI_START_Y = 30;
    private static final int GUI_WIDTH = 146 - GUI_START_X;
    private static final int GUI_HEIGHT = 55 - GUI_START_Y;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final MutableComponent title;

    public ArcaneEscalatorRecipeCategoryJei(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(ArcaneEscalatorScreen.TEXTURE, GUI_START_X, GUI_START_Y, GUI_WIDTH, GUI_HEIGHT);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.ARCANE_ESCALATOR));
        arrow = guiHelper.drawableBuilder(ArcaneEscalatorScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        title = TextUtils.translate("jei", "category.arcane_escalating");
    }

    @Override
    public ResourceLocation getUid() {
        return Constants.ARCANE_ESCALATING;
    }

    @Override
    public Class<? extends ArcaneEscalatingRecipe> getRecipeClass() {
        return ArcaneEscalatingRecipe.class;
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

//    @Override
//    public void setIngredients(ArcaneEscalatingRecipe recipe, IIngredients ingredients) {
//        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getIngredientMap().keySet().stream()
//                .map(ingredient -> Arrays.asList(ingredient.getItems()))
//                .toList());
//        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
//    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ArcaneEscalatingRecipe recipe, IFocusGroup ingredients) {
        IRecipeSlotBuilder[] inputs = new IRecipeSlotBuilder[]{
                builder.addSlot(RecipeIngredientRole.INPUT, 16 - GUI_START_X, 34 - GUI_START_Y),
                builder.addSlot(RecipeIngredientRole.INPUT, 34 - GUI_START_X, 34 - GUI_START_Y),
                builder.addSlot(RecipeIngredientRole.INPUT, 52 - GUI_START_X, 34 - GUI_START_Y),
                builder.addSlot(RecipeIngredientRole.INPUT, 70 - GUI_START_X, 34 - GUI_START_Y)
        };
        builder.addSlot(RecipeIngredientRole.OUTPUT, 125 - GUI_START_X, 34 - GUI_START_Y).addItemStack(recipe.getResultItem());

//        IGuiItemStackGroup itemStacks = builder.getItemStacks();
//        itemStacks.init(0, true, 16 - GUI_START_X, 34 - GUI_START_Y);
//        itemStacks.init(1, true, 34 - GUI_START_X, 34 - GUI_START_Y);
//        itemStacks.init(2, true, 52 - GUI_START_X, 34 - GUI_START_Y);
//        itemStacks.init(3, true, 70 - GUI_START_X, 34 - GUI_START_Y);
//        itemStacks.init(4, false, 125 - GUI_START_X, 34 - GUI_START_Y);

        int i = 0;
        for (Map.Entry<Ingredient, Integer> entry : recipe.getIngredientMap().entrySet()) {
            Ingredient ingredient = entry.getKey();
            Integer count = entry.getValue();
            inputs[i++].addItemStacks(Arrays.stream(ingredient.getItems())
                    .map(s -> {
                        ItemStack stack = s.copy();
                        stack.setCount(count);
                        return stack;
                    })
                    .toList()
            );
        }
//        itemStacks.set(4, recipe.getResultItem());
    }

    @Override
    public void draw(ArcaneEscalatingRecipe recipe, IRecipeSlotsView slotsView, PoseStack pose, double mouseX, double mouseY) {
        arrow.draw(pose, 92 - GUI_START_X, 35 - GUI_START_Y);
    }
}
