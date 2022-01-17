package com.ultreon.randomthingz.block.machines.generator;

import com.ultreon.randomthingz.block.machines.AbstractMachineBlock;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractGeneratorBlock extends AbstractMachineBlock {
    public AbstractGeneratorBlock(MachineTier tier, BlockBehaviour.Properties properties) {
        super(tier, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter dimensionIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        // TODO: Show energy production rate and fuel consumption rate?
    }
}
