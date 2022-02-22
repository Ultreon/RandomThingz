package com.ultreon.randomthingz.compat.jei;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.infuser.InfuserBlockEntity;
import com.ultreon.randomthingz.block.machines.infuser.InfuserScreen;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.item.CanisterItem;
import com.ultreon.randomthingz.item.crafting.InfusingRecipe;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InfusingRecipeCategory implements IRecipeCategory<InfusingRecipe> {
    private static final int GUI_START_X = 7;
    private static final int GUI_START_Y = 15;
    private static final int GUI_WIDTH = 136 - GUI_START_X;
    private static final int GUI_HEIGHT = 75 - GUI_START_Y;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final MutableComponent title;

    public InfusingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(InfuserScreen.TEXTURE, GUI_START_X, GUI_START_Y, GUI_WIDTH, GUI_HEIGHT);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.INFUSER));
        arrow = guiHelper.drawableBuilder(InfuserScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        title = TextUtils.translate("jei", "category.infusing");
    }

    private static void addFluidContainers(Collection<ItemStack> list, Fluid fluid) {
        ItemStack bucket = new ItemStack(fluid.getBucket());
        if (!bucket.isEmpty()) {
            list.add(bucket);
        }
        list.add(CanisterItem.getStack(fluid));
    }

    @Override
    public ResourceLocation getUid() {
        return Constants.INFUSING;
    }

    @Override
    public Class<? extends InfusingRecipe> getRecipeClass() {
        return InfusingRecipe.class;
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
//    public void setIngredients(InfusingRecipe recipe, IIngredients ingredients) {
//        // Fluids (in tanks)
//        ingredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getFluidIngredient().getFluids()));
//        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidOutputs().stream().map(Collections::singletonList).toList());
//
//        // Input fluid containers and recipe ingredient
//        ImmutableList<ItemStack> emptyContainers = ImmutableList.of(new ItemStack(Items.BUCKET), new ItemStack(ModItems.EMPTY_CANISTER));
//        List<ItemStack> feedstockContainers = new ArrayList<>();
//        recipe.getFluidIngredient().getFluids().forEach(fluid -> addFluidContainers(feedstockContainers, fluid.getFluid()));
//        ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(
//                feedstockContainers,
//                Arrays.asList(recipe.getIngredient().getItems())
//        ));
//
//        // Output fluid containers and recipe result
//        ingredients.setOutputLists(VanillaTypes.ITEM, Arrays.asList(
//                emptyContainers,
//                Collections.singletonList(recipe.getResultItem())
//        ));
//    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfusingRecipe recipe, IFocusGroup ingredients) {
        ImmutableList<ItemStack> emptyContainers = ImmutableList.of(new ItemStack(Items.BUCKET), new ItemStack(ModItems.EMPTY_CANISTER));
        List<ItemStack> feedstockContainers = new ArrayList<>();
        recipe.getFluidIngredient().getFluids().forEach(fluid -> addFluidContainers(feedstockContainers, fluid.getFluid()));

        builder.addSlot(RecipeIngredientRole.INPUT, 25, 5)
                .setFluidRenderer(InfuserBlockEntity.TANK_CAPACITY, true, 12, 50)
                .addIngredients(VanillaTypes.FLUID, recipe.getFluidIngredient().getFluids());
        builder.addSlot(RecipeIngredientRole.INPUT, 8 - GUI_START_X, 8 - GUI_START_Y).addItemStacks(feedstockContainers);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 8 - GUI_START_X, 59 - GUI_START_Y).addItemStacks(emptyContainers);
        builder.addSlot(RecipeIngredientRole.INPUT, 54 - GUI_START_X, 35 - GUI_START_Y).addItemStacks(List.of(recipe.getIngredient().getItems()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116 - GUI_START_X, 35 - GUI_START_Y).addItemStack(recipe.getResultItem());

//        IGuiFluidStackGroup fluidStacks = builder.getFluidStacks();
//        final int capacity = InfuserBlockEntity.TANK_CAPACITY;
//        fluidStacks.init(0, true, 25, 5, 12, 50, capacity, true, null);
//
//        fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
//
//        IGuiItemStackGroup itemStacks = builder.getItemStacks();
//        itemStacks.init(0, true, 8 - GUI_START_X, 8 - GUI_START_Y);
//        itemStacks.init(1, false, 8 - GUI_START_X, 59 - GUI_START_Y);
//        itemStacks.init(2, true, 54 - GUI_START_X, 35 - GUI_START_Y);
//        itemStacks.init(3, false, 116 - GUI_START_X, 35 - GUI_START_Y);
//
//        itemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
//        itemStacks.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
//        itemStacks.set(2, ingredients.getInputs(VanillaTypes.ITEM).get(1));
//        itemStacks.set(3, ingredients.getOutputs(VanillaTypes.ITEM).get(1));
    }

    @Override
    public void draw(InfusingRecipe recipe, IRecipeSlotsView slotsView, PoseStack pose, double mouseX, double mouseY) {
        arrow.draw(pose, 79 - GUI_START_X, 35 - GUI_START_Y);
    }
}
