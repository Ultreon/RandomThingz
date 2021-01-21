package com.qsoftware.forgemod.modules.blocks.objects.custom.render;

import com.qsoftware.forgemod.common.interfaces.IHasRenderType;
import net.minecraft.block.FlowerBlock;
import net.minecraft.potion.Effect;

/**
 * Door block with custom render type.
 *
 * @author Qboi123
 */
public abstract class CRFlowerBlock extends FlowerBlock implements IHasRenderType {
    public CRFlowerBlock(Effect effect, int effectDuration, Properties properties) {
        super(effect, effectDuration, properties);
    }
}
