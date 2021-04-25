package com.qtech.forgemod.modules.items.objects.tools;

import com.qtech.forgemod.modules.items.tools.ModTraits;
import com.qtech.forgemod.modules.items.tools.SwordTool;
import com.qtech.forgemod.modules.items.tools.trait.AbstractTrait;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

/**
 * Lightning staff item class.
 *
 * @author Qboi123
 */
public class EnderSwordItem extends SwordTool {
    public EnderSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn, () -> new AbstractTrait[]{ModTraits.ENDER.get()});
    }
}
