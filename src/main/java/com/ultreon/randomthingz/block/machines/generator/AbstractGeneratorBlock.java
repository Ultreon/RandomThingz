package com.ultreon.randomthingz.block.machines.generator;

import com.ultreon.randomthingz.block.machines.AbstractMachineBlock;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractGeneratorBlock extends AbstractMachineBlock {
    public AbstractGeneratorBlock(MachineTier tier, AbstractBlock.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader dimensionIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, dimensionIn, tooltip, flagIn);
        // TODO: Show energy production rate and fuel consumption rate?
    }
}
