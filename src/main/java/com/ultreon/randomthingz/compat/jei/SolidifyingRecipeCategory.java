package com.ultreon.randomthingz.compat.jei;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.solidifier.SolidifierBlockEntity;
import com.ultreon.randomthingz.block.machines.solidifier.SolidifierScreen;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.item.CanisterItem;
import com.ultreon.randomthingz.item.crafting.SolidifyingRecipe;
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

import java.util.*;

public class SolidifyingRecipeCategory implements IRecipeCategory<SolidifyingRecipe> {
    private static final int GUI_START_X = 33;
    private static final int GUI_START_Y = 13;
    private static final int GUI_WIDTH = 106;
    private static final int GUI_HEIGHT = 65;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final MutableComponent title;

    public SolidifyingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(SolidifierScreen.TEXTURE, GUI_START_X, GUI_START_Y, GUI_WIDTH, GUI_HEIGHT);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.SOLIDIFIER));
        arrow = guiHelper.drawableBuilder(SolidifierScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        title = TextUtils.translate("jei", "category.solidifying");
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
        return Constants.SOLIDIFYING;
    }

    @Override
    public Class<? extends SolidifyingRecipe> getRecipeClass() {
        return SolidifyingRecipe.class;
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
//    public void setIngredients(SolidifyingRecipe recipe, IIngredients ingredients) {
//        // Fluids (in tanks)
//        ingredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getIngredient().getFluids()));
//        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidOutputs().stream().map(Collections::singletonList).toList());
//
//        // Input fluid containers
//        ImmutableList<ItemStack> emptyContainers = ImmutableList.of(new ItemStack(Items.BUCKET), new ItemStack(ModItems.EMPTY_CANISTER));
//        List<ItemStack> feedstockContainers = new ArrayList<>();
//        recipe.getIngredient().getFluids().forEach(fluid -> addFluidContainers(feedstockContainers, fluid.getFluid()));
//        ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(feedstockContainers));
//
//        // Output fluid containers and recipe result
//        ingredients.setOutputLists(VanillaTypes.ITEM, Arrays.asList(
//                emptyContainers,
//                Collections.singletonList(recipe.getRecipeOutput())
//        ));
//    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SolidifyingRecipe recipe, IFocusGroup ingredients) {
        ImmutableList<ItemStack> emptyContainers = ImmutableList.of(new ItemStack(Items.BUCKET), new ItemStack(ModItems.EMPTY_CANISTER));
        List<ItemStack> feedstockContainers = new ArrayList<>();
        recipe.getIngredient().getFluids().forEach(fluid -> addFluidContainers(feedstockContainers, fluid.getFluid()));

        builder.addSlot(RecipeIngredientRole.INPUT, 25, 5)
                .setFluidRenderer(SolidifierBlockEntity.TANK_CAPACITY, true, 12, 50)
                .addIngredients(VanillaTypes.FLUID, recipe.getIngredient().getFluids());
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 2)
                .addItemStacks(feedstockContainers);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 2, 45)
                .addItemStacks(emptyContainers);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 2, 45)
                .addItemStack(recipe.getResultItem());

//        IGuiFluidStackGroup fluidStacks = builder.getFluidStacks();
//        final int capacity = SolidifierBlockEntity.TANK_CAPACITY;
//        fluidStacks.init(0, true, 25, 5, 12, 50, capacity, true, null);
//
//        fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
//
//        IGuiItemStackGroup itemStacks = builder.getItemStacks();
//        itemStacks.init(0, true, 2, 2);
//        itemStacks.init(1, false, 2, 45);
//        itemStacks.init(2, false, 82, 22);
//
//        itemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
//        itemStacks.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
//        itemStacks.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(1));
    }

    @Override
    public void draw(SolidifyingRecipe recipe, IRecipeSlotsView slotsView, PoseStack pose, double mouseX, double mouseY) {
        arrow.draw(pose, 79 - GUI_START_X, 35 - GUI_START_Y);
    }
}
