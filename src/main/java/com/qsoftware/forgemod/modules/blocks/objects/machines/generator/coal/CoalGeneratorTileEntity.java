package com.qsoftware.forgemod.modules.blocks.objects.machines.generator.coal;

import com.qsoftware.forgemod.init.ModTags;
import com.qsoftware.forgemod.modules.tileentities.ModMachineTileEntities;
import com.qsoftware.forgemod.modules.blocks.objects.machines.generator.AbstractGeneratorTileEntity;
import com.qsoftware.forgemod.util.MachineTier;
import com.qsoftware.forgemod.util.TextUtils;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

public class CoalGeneratorTileEntity extends AbstractGeneratorTileEntity {
    // Energy constants
    public static final int MAX_ENERGY = 10_000;
    public static final int MAX_SEND = 500;
    public static final int ENERGY_CREATED_PER_TICK = 60;

    public CoalGeneratorTileEntity() {
        super(ModMachineTileEntities.coalGenerator, 1, MAX_ENERGY, 0, MAX_SEND, MachineTier.BASIC);
    }

    static boolean isFuel(ItemStack stack) {
        return stack.getItem().isIn(ModTags.Items.COAL_GENERATOR_FUELS) && AbstractFurnaceTileEntity.isFuel(stack);
    }

    private static int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack);
    }

    @Override
    protected boolean hasFuel() {
        return isFuel(getStackInSlot(0));
    }

    @Override
    protected void consumeFuel() {
        ItemStack fuel = getStackInSlot(0);
        burnTime = getBurnTime(fuel);
        if (burnTime > 0) {
            totalBurnTime = burnTime;

            if (fuel.hasContainerItem()) {
                setInventorySlotContents(0, fuel.getContainerItem());
            } else if (!fuel.isEmpty()) {
                fuel.shrink(1);
                if (fuel.isEmpty()) {
                    setInventorySlotContents(0, fuel.getContainerItem());
                }
            }
        }
    }

    @Override
    protected int getEnergyCreatedPerTick() {
        return ENERGY_CREATED_PER_TICK;
    }

    @Override
    protected BlockState getActiveState() {
        return getBlockState().with(AbstractFurnaceBlock.LIT, true);
    }

    @Override
    protected BlockState getInactiveState() {
        return getBlockState().with(AbstractFurnaceBlock.LIT, false);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
        return isFuel(stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return !isFuel(stack);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return TextUtils.translate("container", "coal_generator");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return new CoalGeneratorContainer(id, playerInventory, this, this.fields);
    }
}
