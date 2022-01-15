package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.item.tool.ModTraits;
import com.ultreon.randomthingz.item.tool.SwordTool;
import com.ultreon.randomthingz.item.tool.trait.AbstractTrait;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.Tiers;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Fire sword item class.
 *
 * @author Qboi123
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SlimeSwordItem extends SwordTool {
    public SlimeSwordItem(Tiers tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties, () -> new AbstractTrait[]{ModTraits.SLIMEY.get()});
    }
}
