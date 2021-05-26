package com.qtech.randomthingz.block.machines.alloysmelter;

import com.qtech.randomthingz.block.common.MachineType;
import com.qtech.randomthingz.block.machines.AbstractMachineTileEntity;
import com.qtech.randomthingz.commons.enums.MachineTier;
import com.qtech.randomthingz.crafting.recipe.AlloySmeltingRecipe;
import com.qtech.randomthingz.crafting.recipe.common.ModRecipes;
import com.qtech.randomthingz.util.TextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.IntStream;

public class AlloySmelterTileEntity extends AbstractMachineTileEntity<AlloySmeltingRecipe> {
    // Energy constant
    public static final int MAX_ENERGY = 50_000;
    public static final int MAX_RECEIVE = 500;
    public static final int ENERGY_USED_PER_TICK = 30;

    // Inventory constants
    static final int INPUT_SLOT_COUNT = 4;
    private static final int[] SLOTS_INPUT = IntStream.range(0, INPUT_SLOT_COUNT).toArray();
    private static final int[] SLOTS_OUTPUT = {INPUT_SLOT_COUNT};
    private static final int[] SLOTS_ALL = IntStream.range(0, INPUT_SLOT_COUNT + 1).toArray();

    public AlloySmelterTileEntity() {
        this(MachineTier.STANDARD);
    }

    public AlloySmelterTileEntity(MachineTier tier) {
        super(MachineType.ALLOY_SMELTER.getTileEntityType(tier), SLOTS_ALL.length, tier);
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

    @Nullable
    @Override
    protected AlloySmeltingRecipe getRecipe() {
        if (dimension == null) return null;
        return dimension.getRecipeManager().getRecipe(ModRecipes.Types.ALLOY_SMELTING, this, dimension).orElse(null);
    }

    @Override
    protected int getProcessTime(AlloySmeltingRecipe recipe) {
        return recipe.getProcessTime();
    }

    @Override
    protected Collection<ItemStack> getProcessResults(AlloySmeltingRecipe recipe) {
        return Collections.singleton(recipe.getCraftingResult(this));
    }

    @Override
    protected void consumeIngredients(AlloySmeltingRecipe recipe) {
        recipe.consumeIngredients(this);
    }

    @Override
    public int getInputSlotCount() {
        return INPUT_SLOT_COUNT;
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS_ALL;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return index < INPUT_SLOT_COUNT;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return index == INPUT_SLOT_COUNT;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return TextUtils.translate("container", "alloy_smelter");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return new AlloySmelterContainer(id, playerInventory, this, this.fields);
    }

    public static class Basic extends AlloySmelterTileEntity {
        public Basic() {
            super(MachineTier.BASIC);
        }
    }
}
