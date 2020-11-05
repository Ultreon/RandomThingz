package com.qsoftware.forgemod.objects.items;

import com.qsoftware.forgemod.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Infinity ore block class.
 *
 * @author Qboi123
 */
public class InfinityOreBlock extends OreBlock {
    public InfinityOreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockClicked(@NotNull BlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull PlayerEntity player) {
        super.onBlockClicked(state, worldIn, pos, player);

        ItemStack heldItemMainhand = player.getHeldItemMainhand();
        if (heldItemMainhand.isEmpty()) {
            player.getCombatTracker().trackDamage(new DamageSource("death.mine_infinity_ore"), player.getHealth(), player.getHealth());
            player.setHealth(0);
            player.onDeath(new DamageSource("death.mine.infinity_ore"));
        } else if (heldItemMainhand.getItem() != ItemInit.ULTRINIUM_SHOVEL.get() &&
                heldItemMainhand.getItem() != ItemInit.ULTRINIUM_PICKAXE.get() &&
                heldItemMainhand.getItem() != ItemInit.ULTRINIUM_HOE.get() &&
                heldItemMainhand.getItem() != ItemInit.ULTRINIUM_SWORD.get() &&
                heldItemMainhand.getItem() != ItemInit.ULTRINIUM_AXE.get() &&
                heldItemMainhand.getItem() == ItemInit.INFINITY_SHOVEL.get() &&
                heldItemMainhand.getItem() == ItemInit.INFINITY_PICKAXE.get() &&
                heldItemMainhand.getItem() == ItemInit.INFINITY_HOE.get() &&
                heldItemMainhand.getItem() == ItemInit.INFINITY_SWORD.get() &&
                heldItemMainhand.getItem() == ItemInit.INFINITY_AXE.get()) {
            if (heldItemMainhand.isDamageable()) {
                player.getHeldItemMainhand().damageItem(player.getHeldItemMainhand().getMaxDamage(), player, (entity) -> {
                    entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            } else {
                player.getCombatTracker().trackDamage(new DamageSource("death.mine_infinity_ore"), player.getHealth(), player.getHealth());
                player.setHealth(0);
                player.onDeath(new DamageSource("death.mine.infinity_ore"));
            }
        } else {
            player.getCombatTracker().trackDamage(new DamageSource("death.mine_infinity_ore"), player.getHealth(), player.getHealth());
            player.setHealth(0);
            player.onDeath(new DamageSource("death.mine.infinity_ore"));
        }
    }
}
