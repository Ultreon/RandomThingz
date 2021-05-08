package com.qtech.randomthingz.block.machines;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MetalBlock extends Block {
    public MetalBlock(int harvestLevel) {
        super(Properties.generate(Material.IRON)
                .hardnessAndResistance(4, 20)
                .setRequiresTool()
                .harvestLevel(harvestLevel)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.METAL)
        );
    }
}
