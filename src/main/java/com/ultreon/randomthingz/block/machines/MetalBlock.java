package com.ultreon.randomthingz.block.machines;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ToolType;

public class MetalBlock extends Block {
    public MetalBlock(int harvestLevel) {
        super(Properties.of(Material.METAL)
                .strength(4, 20)
                .requiresCorrectToolForDrops()
                .harvestLevel(harvestLevel)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.METAL)
        );
    }
}
