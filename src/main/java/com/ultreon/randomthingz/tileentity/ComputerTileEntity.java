package com.ultreon.randomthingz.tileentity;

import com.ultreon.randomthingz.pc.common.device.PowerSwitch;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.TickableBlockEntity;

public class ComputerTileEntity extends BlockEntity implements TickableBlockEntity {
    private PowerSwitch powerSwitch;

    public ComputerTileEntity(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        this.powerSwitch.tick();
    }
}
