package com.qsoftware.forgemod.modules.tiles.blocks;

import com.qsoftware.forgemod.common.TNTProperties;
import net.minecraft.world.Explosion;

/**
 * Atomic TNT block.
 *
 * @author Qboi123
 */
public class AtomicTNTBlock extends CustomTNTBlock<AtomicTNTBlock> {
    public AtomicTNTBlock(Properties properties) {
        super(properties, TNTProperties.builder().radius(48.0f).fuse(80).causesFire(true).mode(Explosion.Mode.DESTROY).build());
    }
}
