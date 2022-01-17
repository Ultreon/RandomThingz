package com.ultreon.randomthingz.block.machines;

import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.inventory.ContaienrHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.INamedContainerProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolType;

public abstract class AbstractMachineBlock extends AbstractFurnaceBlock {
    protected final MachineTier tier;

    public AbstractMachineBlock(MachineTier tier, Properties properties) {
        super(properties.requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(0));
        this.tier = tier;
    }

    private static int calcRedstoneFromInventory(AbstractMachineBaseTileEntity inv) {
        // Copied from AbstractContainerMenu.calcRedstoneFromInventory
        int slotsFilled = 0;
        float fillRatio = 0.0f;

        for (int i = 0; i < inv.getContainerSize() - inv.getMachineTier().getUpgradeSlots(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (!itemstack.isEmpty()) {
                fillRatio += (float) itemstack.getCount() / Math.min(inv.getMaxStackSize(), itemstack.getMaxSize());
                ++slotsFilled;
            }
        }

        fillRatio = fillRatio / (float) inv.getContainerSize();
        return MathHelper.floor(fillRatio * 14.0f) + (slotsFilled > 0 ? 1 : 0);
    }

    @Override
    protected void interactWith(Level dimensionIn, BlockPos pos, Player player) {
        TileEntity tileEntity = dimensionIn.getTileEntity(pos);
        if (tileEntity instanceof INamedContainerProvider) {
            player.openContainer((INamedContainerProvider) tileEntity);
        }
    }

    @Override
    public void onReplaced(BlockState state, Level dimensionIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = dimensionIn.getTileEntity(pos);
            if (tileentity instanceof IInventory) {
                ContaienrHelper.dropInventoryItems(dimensionIn, pos, (IInventory) tileentity);
                dimensionIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, dimensionIn, pos, newState, isMoving);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(BlockState blockState, Level dimensionIn, BlockPos pos) {
        TileEntity tileEntity = dimensionIn.getTileEntity(pos);
        if (tileEntity instanceof AbstractMachineBaseTileEntity) {
            return calcRedstoneFromInventory((AbstractMachineBaseTileEntity) tileEntity);
        }
        return super.getComparatorInputOverride(blockState, dimensionIn, pos);
    }
}
