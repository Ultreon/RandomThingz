package com.qtech.randomthingz.item.tools;

import com.qtech.randomthingz.item.tools.trait.AbstractTrait;
import net.minecraft.item.IItemTier;

/**
 * Lightning staff item class.
 *
 * @author Qboi123
 */
@Deprecated
public class EnderSwordItem extends SwordTool {
    public EnderSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn, () -> new AbstractTrait[]{ModTraits.ENDER.get()});
    }
}
