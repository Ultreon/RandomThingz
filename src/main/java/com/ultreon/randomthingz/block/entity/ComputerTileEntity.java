package com.ultreon.randomthingz.block.entity;

import com.ultreon.randomthingz.pc.common.computerapi.Computer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ComputerTileEntity extends BlockEntity implements BlockEntityTicker<ComputerTileEntity> {
    private final Computer computer = new UltreonPC(this);
    private boolean powered;

    public ComputerTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public void tick(Level p_155253_, BlockPos p_155254_, BlockState p_155255_, ComputerTileEntity p_155256_) {
        this.computer.tick();
    }

    public void shutdown() {
        powered = false;
    }

    public boolean isPowered() {
        return powered;
    }

    public Computer getComputer() {
        return computer;
    }
}
