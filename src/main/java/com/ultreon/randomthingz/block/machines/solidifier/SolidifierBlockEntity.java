package com.ultreon.randomthingz.block.machines.solidifier;

import com.ultreon.modlib.api.FluidContainer;
import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import com.ultreon.randomthingz.block.machines.AbstractFluidMachineBlockEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.item.crafting.SolidifyingRecipe;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.util.InventoryUtils;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public class SolidifierBlockEntity extends AbstractFluidMachineBlockEntity<SolidifyingRecipe> {
    public static final int FIELDS_COUNT = 9;
    public static final int TANK_CAPACITY = 4000;
    public static final int ENERGY_PER_TICK = 50;

    public SolidifierBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachineTileEntities.solidifier, pos, state, 3, 1, TANK_CAPACITY, MachineTier.STANDARD);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;

        tryFillTank();

        super.tick();
    }

    @Override
    protected void consumeIngredients(SolidifyingRecipe recipe) {
        // NO-OP
    }

    private void tryFillTank() {
        // Try to fill feedstock tank with fluid containers
        ItemStack input = getItem(0);
        if (input.isEmpty()) return;

        FluidStack fluidStack = FluidContainer.getBucketOrContainerFluid(input);
        if (canAcceptFluidContainer(input, fluidStack)) {
            this.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);

            ItemStack containerItem = input.getContainerItem();
            input.shrink(1);

            ItemStack output = getItem(1);
            if (output.isEmpty()) {
                setItem(1, containerItem);
            } else {
                output.grow(1);
            }
        }
    }

    private boolean canAcceptFluidContainer(ItemStack input, FluidStack fluid) {
        ItemStack output = getItem(1);
        return !fluid.isEmpty()
                && this.isFluidValid(0, fluid)
                && this.fill(fluid, IFluidHandler.FluidAction.SIMULATE) == 1000
                && (output.isEmpty() || InventoryUtils.canItemsStack(input.getContainerItem(), output))
                && (output.isEmpty() || output.getCount() < output.getMaxStackSize());
    }

    @Override
    protected int getEnergyUsedPerTick() {
        return ENERGY_PER_TICK;
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[]{2};
    }

    @Override
    protected int getInputTanks() {
        return 1;
    }

    @Override
    protected int getOutputTanks() {
        return 0;
    }

    @Nullable
    @Override
    public SolidifyingRecipe getRecipe() {
        if (level == null) return null;
        return level.getRecipeManager().getRecipeFor(ModRecipes.Types.SOLIDIFYING, this, level).orElse(null);
    }

    @Override
    protected int getProcessTime(SolidifyingRecipe recipe) {
        return recipe.getProcessTime();
    }

    @Override
    protected Collection<ItemStack> getProcessResults(SolidifyingRecipe recipe) {
        return Collections.singletonList(recipe.getResultItem());
    }

    @Override
    protected Collection<FluidStack> getFluidResults(SolidifyingRecipe recipe) {
        return recipe.getFluidOutputs();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0, 1, 2};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return index == 0 && InventoryUtils.isFilledFluidContainer(stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 1 || index == 2;
    }

    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "solidifier");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new SolidifierContainer(id, player, this, this.fields);
    }
}
