package com.qtech.randomthingz.block.machines.infuser;

import com.qsoftware.modlib.api.IFluidContainer;
import com.qtech.randomthingz.block.machines.AbstractFluidMachineTileEntity;
import com.qtech.randomthingz.commons.enums.MachineTier;
import com.qtech.randomthingz.item.crafting.InfusingRecipe;
import com.qtech.randomthingz.item.crafting.common.ModRecipes;
import com.qtech.randomthingz.modules.tiles.ModMachineTileEntities;
import com.qtech.randomthingz.util.InventoryUtils;
import com.qtech.randomthingz.util.TextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;

public class InfuserTileEntity extends AbstractFluidMachineTileEntity<InfusingRecipe> {
    public static final int FIELDS_COUNT = 9;
    public static final int TANK_CAPACITY = 4000;
    public static final int ENERGY_PER_TICK = 50;

    public static final int SLOT_FLUID_CONTAINER_IN = 0;
    public static final int SLOT_ITEM_IN = 2;
    public static final int SLOT_FLUID_CONTAINER_OUT = 1;
    public static final int SLOT_ITEM_OUT = 3;

    public InfuserTileEntity() {
        super(ModMachineTileEntities.infuser, 4, 1, TANK_CAPACITY, MachineTier.STANDARD);
    }

    @Override
    public void tick() {
        if (dimension == null || dimension.isClientSided) return;

        tryFillTank();

        super.tick();
    }

    @Override
    protected void consumeIngredients(InfusingRecipe recipe) {
        decrStackSize(SLOT_ITEM_IN, 1);
    }

    private void tryFillTank() {
        // Try fill feedstock tank with fluid containers
        ItemStack input = getStackInSlot(0);
        if (input.isEmpty()) return;

        FluidStack fluidStack = IFluidContainer.getBucketOrContainerFluid(input);
        if (canAcceptFluidContainer(input, fluidStack)) {
            this.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);

            ItemStack containerItem = input.getContainerItem();
            input.shrink(1);

            ItemStack output = getStackInSlot(1);
            if (output.isEmpty()) {
                setInventorySlotContents(1, containerItem);
            } else {
                output.grow(1);
            }
        }
    }

    private boolean canAcceptFluidContainer(ItemStack input, FluidStack fluid) {
        ItemStack output = getStackInSlot(1);
        return !fluid.isEmpty()
                && this.isFluidValid(0, fluid)
                && this.fill(fluid, IFluidHandler.FluidAction.SIMULATE) == 1000
                && (output.isEmpty() || InventoryUtils.canItemsStack(input.getContainerItem(), output))
                && (output.isEmpty() || output.getCount() < output.getMaxSize());
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
    public InfusingRecipe getRecipe() {
        if (dimension == null) return null;
        return dimension.getRecipeManager().getRecipe(ModRecipes.Types.INFUSING, this, dimension).orElse(null);
    }

    @Override
    protected int getProcessTime(InfusingRecipe recipe) {
        return recipe.getProcessTime();
    }

    @Override
    protected Collection<ItemStack> getProcessResults(InfusingRecipe recipe) {
        return Collections.singletonList(recipe.getCraftingResult(this));
    }

    @Override
    protected Collection<FluidStack> getFluidResults(InfusingRecipe recipe) {
        return recipe.getFluidOutputs();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0, 1, 2};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
        return (index == SLOT_FLUID_CONTAINER_IN && InventoryUtils.isFilledFluidContainer(stack)) || index == SLOT_ITEM_IN;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return index == SLOT_FLUID_CONTAINER_OUT || index == SLOT_ITEM_OUT;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return TextUtils.translate("container", "infuser");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new InfuserContainer(id, player, this, this.fields);
    }
}
