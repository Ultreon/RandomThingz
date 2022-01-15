package com.ultreon.randomthingz.block.machines.electricfurnace;

import com.google.common.collect.ImmutableList;
import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import com.ultreon.randomthingz.block.machines.AbstractMachineTileEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.Direction;
import net.minecraft.item.crafting.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ElectricFurnaceTileEntity extends AbstractMachineTileEntity<AbstractCookingRecipe> {
    // Energy constant
    public static final int MAX_ENERGY = 50_000;
    public static final int MAX_RECEIVE = 500;
    public static final int ENERGY_USED_PER_TICK = 30;

    // Inventory constants
    private static final int INVENTORY_SIZE = 2;
    private static final int[] SLOTS_INPUT = {0};
    private static final int[] SLOTS_OUTPUT = {1};
    private static final int[] SLOTS_ALL = {0, 1};

    public ElectricFurnaceTileEntity() {
        super(ModMachineTileEntities.electricFurnace, INVENTORY_SIZE, MachineTier.STANDARD);
    }

    @Override
    protected int getEnergyUsedPerTick() {
        return ENERGY_USED_PER_TICK;
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    protected int[] getOutputSlots() {
        return SLOTS_OUTPUT;
    }

    @Override
    @Nullable
    protected AbstractCookingRecipe getRecipe() {
        if (level == null) return null;

        RecipeManager recipeManager = level.getRecipeManager();
        Optional<BlastingRecipe> optional = recipeManager.getRecipeFor(RecipeType.BLASTING, this, level);
        if (optional.isPresent()) return optional.get();

        Optional<SmeltingRecipe> optional1 = recipeManager.getRecipeFor(RecipeType.SMELTING, this, level);
        return optional1.orElse(null);
    }

    @Override
    protected int getProcessTime(AbstractCookingRecipe recipe) {
        return recipe.getCookingTime();
    }

    @Override
    protected Collection<ItemStack> getProcessResults(AbstractCookingRecipe recipe) {
        return Collections.singleton(recipe.assemble(this));
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS_ALL;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return index == 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 1;
    }

    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "electric_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new ElectricFurnaceContainer(id, playerInventory, this, this.fields);
    }

    List<String> getDebugText() {
        return ImmutableList.of(
                "progress = " + progress,
                "processTime = " + processTime,
                "energy = " + getEnergyStored() + " FE / " + getMaxEnergyStored() + " FE",
                "ENERGY_USED_PER_TICK = " + ENERGY_USED_PER_TICK,
                "MAX_RECEIVE = " + MAX_RECEIVE
        );
    }
}
