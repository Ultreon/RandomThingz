package com.ultreon.randomthingz.block.machines.generator.diesel;

import com.ultreon.modlib.api.FluidContainer;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.entity.ModMachines;
import com.ultreon.randomthingz.block.machines.generator.FluidFuelGeneratorBlockEntity;
import com.ultreon.randomthingz.util.InventoryUtils;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public class DieselGeneratorBlockEntity extends FluidFuelGeneratorBlockEntity {
    // Energy constants
    public static final int MAX_ENERGY = 50_000;
    public static final int MAX_SEND = 500;
    public static final int ENERGY_CREATED_PER_TICK = 120;
    public static final int TICKS_PER_MILLIBUCKET = 10;

    static final Tag.Named<Fluid> FUEL_TAG = FluidTags.bind(new ResourceLocation("forge", "diesel").toString());

    public DieselGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.DIESEL_GENERATOR.get(), pos, state, 2, MAX_ENERGY, 0, MAX_SEND, new FluidTank(4000, s -> s.getFluid().is(FUEL_TAG)));
    }

    public DieselGeneratorBlockEntity() {
        this(BlockPos.ZERO, ModBlocks.DIESEL_GENERATOR.asBlockState());
    }

    @Override
    protected boolean hasFuel() {
        return tank.getFluidAmount() > 0;
    }

    @Override
    protected int getFuelBurnTime(FluidStack fluid) {
        return TICKS_PER_MILLIBUCKET * fluid.getAmount();
    }

    @Override
    protected int getEnergyCreatedPerTick() {
        return ENERGY_CREATED_PER_TICK;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0, 1};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return index == 0
                && InventoryUtils.isFilledFluidContainer(stack)
                && tank.isFluidValid(FluidContainer.getBucketOrContainerFluid(stack));
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 1;
    }

    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "diesel_generator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new DieselGeneratorContainer(id, player, this, this.fields);
    }
}
