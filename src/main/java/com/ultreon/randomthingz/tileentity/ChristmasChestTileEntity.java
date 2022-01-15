package com.ultreon.randomthingz.tileentity;

import com.ultreon.randomthingz.block.entity.ModTileEntities;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = LidBlockEntity.class
)
public class ChristmasChestTileEntity extends ChestBlockEntity {
    public ChristmasChestTileEntity() {
        super(ModTileEntities.CHRISTMAS_CHEST.get());
    }
}
