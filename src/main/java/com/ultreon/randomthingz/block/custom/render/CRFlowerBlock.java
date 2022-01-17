package com.ultreon.randomthingz.block.custom.render;

import com.ultreon.modlib.common.interfaces.IHasRenderType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.FlowerBlock;

/**
 * Door block with custom render type.
 *
 * @author Qboi123
 */
public abstract class CRFlowerBlock extends FlowerBlock implements IHasRenderType {
    public CRFlowerBlock(MobEffect effect, int effectDuration, Properties properties) {
        super(effect, effectDuration, properties);
    }
}
