package com.ultreon.randomthingz.block.advanced;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.block.CustomTNTBlock;
import net.minecraft.block.TNTBlock;

/**
 * Advanced TNT block class.
 *
 * @author Qboi123
 * @deprecated replaced by {@link CustomTNTBlock}
 */
@Beta
@Deprecated
public class AdvancedTNTBlock extends TNTBlock {
    public AdvancedTNTBlock(String name, Properties properties) {
        super(properties);
    }
}
