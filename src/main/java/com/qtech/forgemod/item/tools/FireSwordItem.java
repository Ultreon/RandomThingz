package com.qtech.forgemod.item.tools;

import com.qtech.forgemod.item.tools.trait.AbstractTrait;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.*;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Fire sword item class.
 *
 * @author Qboi123
 */
@Deprecated
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FireSwordItem extends SwordTool {
    public FireSwordItem(ItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties, () -> new AbstractTrait[]{ModTraits.BLAZE.get()});
    }
}
