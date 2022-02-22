package com.ultreon.randomthingz.block.machines;

import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractMachineBlock extends AbstractFurnaceBlock {
    protected final MachineTier tier;

    public AbstractMachineBlock(MachineTier tier, Properties properties) {
        super(properties.requiresCorrectToolForDrops());
        this.tier = tier;
    }

    private static int calcRedstoneFromInventory(AbstractMachineBaseBlockEntity inv) {
        // Copied from AbstractContainerMenu.calcRedstoneFromInventory
        int slotsFilled = 0;
        float fillRatio = 0f;

        for (int i = 0; i < inv.getContainerSize() - inv.getMachineTier().getUpgradeSlots(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (!itemstack.isEmpty()) {
                fillRatio += (float) itemstack.getCount() / Math.min(inv.getMaxStackSize(), itemstack.getMaxStackSize());
                ++slotsFilled;
            }
        }

        fillRatio = fillRatio / (float) inv.getContainerSize();
        return Mth.floor(fillRatio * 14f) + (slotsFilled > 0 ? 1 : 0);
    }

    @Override
    protected void openContainer(Level dimensionIn, BlockPos pos, Player player) {
        BlockEntity tileEntity = dimensionIn.getBlockEntity(pos);
        if (tileEntity instanceof MenuProvider menuProvider) {
            player.openMenu(menuProvider);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof Container) {
                Containers.dropContents(level, pos, (Container) entity);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level dimensionIn, BlockPos pos) {
        BlockEntity tileEntity = dimensionIn.getBlockEntity(pos);
        if (tileEntity instanceof AbstractMachineBaseBlockEntity) {
            return calcRedstoneFromInventory((AbstractMachineBaseBlockEntity) tileEntity);
        }
        return super.getAnalogOutputSignal(blockState, dimensionIn, pos);
    }
}
