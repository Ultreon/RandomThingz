package com.qsoftware.forgemod.objects.block.generator;

import net.minecraft.block.AbstractBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import com.qsoftware.forgemod.objects.block.AbstractMachineBlock;
import com.qsoftware.forgemod.util.MachineTier;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractGeneratorBlock extends AbstractMachineBlock {
    public AbstractGeneratorBlock(MachineTier tier, AbstractBlock.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        // TODO: Show energy production rate and fuel consumption rate?
    }
}
