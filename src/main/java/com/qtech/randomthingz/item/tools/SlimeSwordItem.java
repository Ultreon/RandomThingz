package com.qtech.randomthingz.item.tools;

import com.qtech.randomthingz.item.tools.trait.AbstractTrait;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemTier;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Fire sword item class.
 *
 * @author Qboi123
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SlimeSwordItem extends SwordTool {
    public SlimeSwordItem(ItemTier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties, () -> new AbstractTrait[]{ModTraits.SLIMEY.get()});
    }
}
