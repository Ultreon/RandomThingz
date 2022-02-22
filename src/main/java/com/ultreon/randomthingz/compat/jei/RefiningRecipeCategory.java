package com.ultreon.randomthingz.compat.jei;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.refinery.RefineryBlockEntity;
import com.ultreon.randomthingz.block.machines.refinery.RefineryScreen;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.item.CanisterItem;
import com.ultreon.randomthingz.item.crafting.RefiningRecipe;
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
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RefiningRecipeCategory implements IRecipeCategory<RefiningRecipe> {
    private static final int GUI_START_X = 5;
    private static final int GUI_START_Y = 13;
    private static final int GUI_WIDTH = 148;
    private static final int GUI_HEIGHT = 65;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final MutableComponent title;

    public RefiningRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(RefineryScreen.TEXTURE, GUI_START_X, GUI_START_Y, GUI_WIDTH, GUI_HEIGHT);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.REFINERY));
        arrow = guiHelper.drawableBuilder(RefineryScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        title = TextUtils.translate("jei", "category.refining");
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
        return Constants.REFINING;
    }

    @Override
    public Class<? extends RefiningRecipe> getRecipeClass() {
        return RefiningRecipe.class;
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
//    public void setIngredients(RefiningRecipe recipe, IIngredients ingredients) {
//        // Fluids (in tanks)
//        ingredients.setInputLists(VanillaTypes.FLUID, Collections.singletonList(recipe.getIngredient().getFluids()));
//        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidOutputs().stream().map(Collections::singletonList).toList());
//
//        // Input fluid containers
//        ImmutableList<ItemStack> emptyContainers = ImmutableList.of(new ItemStack(Items.BUCKET), new ItemStack(ModItems.EMPTY_CANISTER));
//        List<ItemStack> feedstockContainers = new ArrayList<>();
//        recipe.getIngredient().getFluids().forEach(fluid -> addFluidContainers(feedstockContainers, fluid.getFluid()));
//        ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(
//                feedstockContainers,
//                emptyContainers
//        ));
//
//        // Output fluid containers
//        List<ItemStack> outputContainers = new ArrayList<>();
//        recipe.getFluidOutputs().forEach(fluid -> addFluidContainers(outputContainers, fluid.getFluid()));
//        ingredients.setOutputLists(VanillaTypes.ITEM, Arrays.asList(
//                emptyContainers,
//                outputContainers
//        ));
//    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RefiningRecipe recipe, IFocusGroup ingredients) {
        List<List<FluidStack>> collect = recipe.getFluidOutputs().stream().map(Collections::singletonList).toList();

        ImmutableList<ItemStack> emptyContainers = ImmutableList.of(new ItemStack(Items.BUCKET), new ItemStack(ModItems.EMPTY_CANISTER));
        List<ItemStack> feedstockContainers = new ArrayList<>();
        recipe.getIngredient().getFluids().forEach(fluid -> addFluidContainers(feedstockContainers, fluid.getFluid()));

        List<ItemStack> outputContainers = new ArrayList<>();
        recipe.getFluidOutputs().forEach(fluid -> addFluidContainers(outputContainers, fluid.getFluid()));
        
        builder.addSlot(RecipeIngredientRole.INPUT, 24, 5).setFluidRenderer(RefineryBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredients(VanillaTypes.FLUID, recipe.getIngredient().getFluids());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 64, 5).setFluidRenderer(RefineryBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredients(VanillaTypes.FLUID, collect.size() >= 1 ? collect.get(0) : List.of());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 5).setFluidRenderer(RefineryBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredients(VanillaTypes.FLUID, collect.size() >= 2 ? collect.get(1) : List.of());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 5).setFluidRenderer(RefineryBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredients(VanillaTypes.FLUID, collect.size() >= 3 ? collect.get(2) : List.of());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 5).setFluidRenderer(RefineryBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredients(VanillaTypes.FLUID, collect.size() >= 4 ? collect.get(3) : List.of());
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 2).addItemStacks(collect.size() >= 4 ? feedstockContainers : List.of());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 2, 45).addItemStacks(collect.size() >= 4 ? emptyContainers : List.of());
        builder.addSlot(RecipeIngredientRole.INPUT, 128, 2).addItemStacks(collect.size() >= 4 ? emptyContainers : List.of());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 45).addItemStacks(collect.size() >= 4 ? outputContainers : List.of());
        
//        IGuiFluidStackGroup fluidStacks = builder.getFluidStacks();
//        final int capacity = RefineryBlockEntity.TANK_CAPACITY;
//        fluidStacks.init(0, true, 24, 5, 12, 50, capacity, true, null);
//        fluidStacks.init(1, false, 64, 5, 12, 50, capacity, true, null);
//        fluidStacks.init(2, false, 80, 5, 12, 50, capacity, true, null);
//        fluidStacks.init(3, false, 96, 5, 12, 50, capacity, true, null);
//        fluidStacks.init(4, false, 112, 5, 12, 50, capacity, true, null);
//
//        fluidStacks.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
//        List<List<FluidStack>> outputs = ingredients.getOutputs(VanillaTypes.FLUID);
//        for (int i = 0; i < outputs.size(); ++i) {
//            fluidStacks.set(i + 1, outputs.get(i));
//        }
//
//        IGuiItemStackGroup itemStacks = builder.getItemStacks();
//        itemStacks.init(0, true, 2, 2);
//        itemStacks.init(1, false, 2, 45);
//        itemStacks.init(2, true, 128, 2);
//        itemStacks.init(3, false, 128, 45);
//
//        itemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
//        itemStacks.set(2, ingredients.getInputs(VanillaTypes.ITEM).get(1));
//        itemStacks.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
//        itemStacks.set(3, ingredients.getOutputs(VanillaTypes.ITEM).get(1));
    }

    @Override
    public void draw(RefiningRecipe recipe, IRecipeSlotsView slotsView, PoseStack pose, double mouseX, double mouseY) {
        arrow.draw(pose, 43 - GUI_START_X, 35 - GUI_START_Y);
    }
}
