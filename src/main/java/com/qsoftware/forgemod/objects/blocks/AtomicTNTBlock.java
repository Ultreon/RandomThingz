package com.qsoftware.forgemod.objects.blocks;

import com.qsoftware.forgemod.common.TNTProperties;
import com.qsoftware.forgemod.init.types.ModEntities;
import net.minecraft.world.Explosion;

/**
 * Atomic TNT block.
 *
 * @author Qboi123
 */
public class AtomicTNTBlock extends CustomTNTBlock<AtomicTNTBlock> {
    public AtomicTNTBlock(Properties properties) {
        super(properties, TNTProperties.builder().radius(48.0f).causesFire().mode(Explosion.Mode.DESTROY).build());
    }
}
