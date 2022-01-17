package com.ultreon.randomthingz.block.machines.generator.lava;

import com.ultreon.modlib.api.FluidContainer;
import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import com.ultreon.randomthingz.block.machines.generator.FluidFuelGeneratorBlockEntity;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public class LavaGeneratorBlockEntity extends FluidFuelGeneratorBlockEntity {
    // Energy constants
    public static final int MAX_ENERGY = 50_000;
    public static final int MAX_SEND = 500;
    public static final int ENERGY_CREATED_PER_TICK = 100;
    public static final int TICKS_PER_MILLIBUCKET = 5;

    static final int TANK_CAPACITY = 4000;

    public LavaGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachineTileEntities.lavaGenerator, pos, state, 2, MAX_ENERGY, 0, MAX_SEND, new FluidTank(TANK_CAPACITY, s -> s.getFluid().is(FluidTags.LAVA)));
    }

    public IFluidHandler getTank() {
        return tank;
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
        return index == 0 && FluidContainer.getBucketOrContainerFluid(stack).getFluid().is(FluidTags.LAVA);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index == 1;
    }

    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "lava_generator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new LavaGeneratorContainer(id, playerInventory, this, this.fields);
    }
}
