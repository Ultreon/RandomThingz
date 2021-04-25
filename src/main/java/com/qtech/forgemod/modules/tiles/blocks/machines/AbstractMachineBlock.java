package com.qtech.forgemod.modules.tiles.blocks.machines;

import com.qtech.forgemod.common.enums.MachineTier;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public abstract class AbstractMachineBlock extends AbstractFurnaceBlock {
    protected final MachineTier tier;

    public AbstractMachineBlock(MachineTier tier, Properties properties) {
        super(properties.setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0));
        this.tier = tier;
    }

    private static int calcRedstoneFromInventory(AbstractMachineBaseTileEntity inv) {
        // Copied from Container.calcRedstoneFromInventory
        int slotsFilled = 0;
        float fillRatio = 0.0F;

        for (int i = 0; i < inv.getSizeInventory() - inv.getMachineTier().getUpgradeSlots(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                fillRatio += (float) itemstack.getCount() / Math.min(inv.getInventoryStackLimit(), itemstack.getMaxSize());
                ++slotsFilled;
            }
        }

        fillRatio = fillRatio / (float) inv.getSizeInventory();
        return MathHelper.floor(fillRatio * 14.0F) + (slotsFilled > 0 ? 1 : 0);
    }

    @Override
    protected void interactWith(World dimensionIn, BlockPos pos, PlayerEntity player) {
        TileEntity tileEntity = dimensionIn.getTileEntity(pos);
        if (tileEntity instanceof INamedContainerProvider) {
            player.openContainer((INamedContainerProvider) tileEntity);
        }
    }

    @Override
    public void onReplaced(BlockState state, World dimensionIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = dimensionIn.getTileEntity(pos);
            if (tileentity instanceof IInventory) {
                InventoryHelper.dropInventoryItems(dimensionIn, pos, (IInventory) tileentity);
                dimensionIn.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, dimensionIn, pos, newState, isMoving);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(BlockState blockState, World dimensionIn, BlockPos pos) {
        TileEntity tileEntity = dimensionIn.getTileEntity(pos);
        if (tileEntity instanceof AbstractMachineBaseTileEntity) {
            return calcRedstoneFromInventory((AbstractMachineBaseTileEntity) tileEntity);
        }
        return super.getComparatorInputOverride(blockState, dimensionIn, pos);
    }
}
