package com.ultreon.randomthingz.compat.jei;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.modlib.api.crafting.recipe.fluid.FluidIngredient;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.mixer.MixerBlockEntity;
import com.ultreon.randomthingz.block.machines.mixer.MixerScreen;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.item.CanisterItem;
import com.ultreon.randomthingz.item.crafting.MixingRecipe;
import com.ultreon.randomthingz.util.Constants;
import com.ultreon.randomthingz.util.TextUtils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
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
import org.lwjgl.system.CallbackI;

import java.util.*;
import java.util.stream.Collectors;

public class MixingRecipeCategory implements IRecipeCategory<MixingRecipe> {
    private static final int GUI_START_X = 5;
    private static final int GUI_START_Y = 13;
    private static final int GUI_WIDTH = 148;
    private static final int GUI_HEIGHT = 65;

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final MutableComponent title;

    public MixingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(MixerScreen.TEXTURE, GUI_START_X, GUI_START_Y, GUI_WIDTH, GUI_HEIGHT);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.MIXER));
        arrow = guiHelper.drawableBuilder(MixerScreen.TEXTURE, 176, 14, 24, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        title = TextUtils.translate("jei", "category.mixing");
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
        return Constants.MIXING;
    }

    @Override
    public Class<? extends MixingRecipe> getRecipeClass() {
        return MixingRecipe.class;
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
//    public void setIngredients(MixingRecipe recipe, IIngredients ingredients) {
//        // Fluids (in tanks)
//        ingredients.setInputLists(VanillaTypes.FLUID, recipe.getFluidIngredients().stream().map(FluidIngredient::getFluids).toList());
//        ingredients.setOutputLists(VanillaTypes.FLUID, recipe.getFluidOutputs().stream().map(Collections::singletonList).toList());
//
//        // Input fluid containers
//        ImmutableList<ItemStack> emptyContainers = ImmutableList.of(new ItemStack(Items.BUCKET), new ItemStack(ModItems.EMPTY_CANISTER));
//        List<ItemStack> feedstockContainers = new ArrayList<>();
//        recipe.getFluidIngredients().stream()
//                .flatMap(ingredient -> ingredient.getFluids().stream())
//                .forEach(stack -> addFluidContainers(feedstockContainers, stack.getFluid()));
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
    public void setRecipe(IRecipeLayoutBuilder builder, MixingRecipe recipe, IFocusGroup ingredients) {
        ImmutableList<ItemStack> emptyContainers = ImmutableList.of(new ItemStack(Items.BUCKET), new ItemStack(ModItems.EMPTY_CANISTER));
        List<ItemStack> feedstockContainers = new ArrayList<>();
        List<List<FluidStack>> collect = recipe.getFluidIngredients().stream().map(FluidIngredient::getFluids).toList();
        List<ItemStack> outputContainers = new ArrayList<>();
        recipe.getFluidOutputs().forEach(fluid -> addFluidContainers(outputContainers, fluid.getFluid()));
        builder.addSlot(RecipeIngredientRole.INPUT, 24, 5).setFluidRenderer(MixerBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredients(VanillaTypes.FLUID, collect.size() >= 1 ? collect.get(0) : List.of());
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 5).setFluidRenderer(MixerBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredients(VanillaTypes.FLUID, collect.size() >= 2 ? collect.get(1) : List.of());
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 5).setFluidRenderer(MixerBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredients(VanillaTypes.FLUID, collect.size() >= 3 ? collect.get(2) : List.of());
        builder.addSlot(RecipeIngredientRole.INPUT, 72, 5).setFluidRenderer(MixerBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredients(VanillaTypes.FLUID, collect.size() >= 4 ? collect.get(3) : List.of());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 112, 5).setFluidRenderer(MixerBlockEntity.TANK_CAPACITY, true, 12, 50).addIngredient(VanillaTypes.FLUID, collect.size() >= 1 ? recipe.getFluidOutputs().get(0) : FluidStack.EMPTY);
        builder.addSlot(RecipeIngredientRole.INPUT, 2, 2).addItemStacks(feedstockContainers);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 2, 45).addItemStacks(emptyContainers);
        builder.addSlot(RecipeIngredientRole.INPUT, 128, 2).addItemStacks(emptyContainers);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 45).addItemStacks(outputContainers);

//        IGuiFluidStackGroup fluidStacks = builder.getFluidStacks();
//        final int capacity = MixerBlockEntity.TANK_CAPACITY;
//        fluidStacks.init(0, true, 24, 5, 12, 50, capacity, true, null);
//        fluidStacks.init(1, true, 40, 5, 12, 50, capacity, true, null);
//        fluidStacks.init(2, true, 56, 5, 12, 50, capacity, true, null);
//        fluidStacks.init(3, true, 72, 5, 12, 50, capacity, true, null);
//        fluidStacks.init(4, false, 112, 5, 12, 50, capacity, true, null);
//
//        List<List<FluidStack>> inputs = ingredients.getInputs(VanillaTypes.FLUID);
//        for (int i = 0; i < inputs.size(); ++i) {
//            fluidStacks.set(i, inputs.get(i));
//        }
//        fluidStacks.set(4, ingredients.getOutputs(VanillaTypes.FLUID).get(0));
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
    public void draw(MixingRecipe recipe, PoseStack pose, double mouseX, double mouseY) {
        arrow.draw(pose, 92 - GUI_START_X, 35 - GUI_START_Y);
    }
}
