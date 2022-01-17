package com.ultreon.randomthingz.block.machines.generator.coal;

import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import com.ultreon.randomthingz.block.machines.generator.AbstractGeneratorTileEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.common.tags.ModTags;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorTileEntity extends AbstractGeneratorTileEntity {
    // Energy constants
    public static final int MAX_ENERGY = 10_000;
    public static final int MAX_SEND = 500;
    public static final int ENERGY_CREATED_PER_TICK = 60;

    public CoalGeneratorTileEntity() {
        super(ModMachineTileEntities.coalGenerator, 1, MAX_ENERGY, 0, MAX_SEND, MachineTier.BASIC);
    }

    static boolean isFuel(ItemStack stack) {
        return stack.getItem().is(ModTags.Items.COAL_GENERATOR_FUELS) && AbstractFurnaceBlockEntity.isFuel(stack);
    }

    private static int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack);
    }

    @Override
    protected boolean hasFuel() {
        return isFuel(getItem(0));
    }

    @Override
    protected void consumeFuel() {
        ItemStack fuel = getItem(0);
        burnTime = getBurnTime(fuel);
        if (burnTime > 0) {
            totalBurnTime = burnTime;

            if (fuel.hasContainerItem()) {
                setItem(0, fuel.getContainerItem());
            } else if (!fuel.isEmpty()) {
                fuel.shrink(1);
                if (fuel.isEmpty()) {
                    setItem(0, fuel.getContainerItem());
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
        return getBlockState().setValue(AbstractFurnaceBlock.LIT, true);
    }

    @Override
    protected BlockState getInactiveState() {
        return getBlockState().setValue(AbstractFurnaceBlock.LIT, false);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{0};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return isFuel(stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return !isFuel(stack);
    }

    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "coal_generator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new CoalGeneratorContainer(id, playerInventory, this, this.fields);
    }
}
