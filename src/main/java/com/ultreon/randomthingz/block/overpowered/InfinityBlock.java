package com.ultreon.randomthingz.block.overpowered;

import com.ultreon.randomthingz.item.tools.Toolset;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Infinity block.
 *
 * @author Qboi123
 */
public class InfinityBlock extends Block {
    public InfinityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockClicked(@NotNull BlockState state, @NotNull World dimensionIn, @NotNull BlockPos pos, @NotNull PlayerEntity player) {
        super.onBlockClicked(state, dimensionIn, pos, player);

        ItemStack heldItemMainhand = player.getHeldItemMainhand();
        if (heldItemMainhand.isEmpty()) {
            player.getCombatTracker().trackDamage(new DamageSource("mine.infinity_ore"), player.getHealth(), player.getHealth());
            player.setHealth(0);

            Minecraft.getInstance().enqueue(() -> {
                player.onDeath(new DamageSource("mine.infinity_ore"));
            });
        } else if (heldItemMainhand.getItem() != Toolset.INFINITY.getShovel().get() &&
                heldItemMainhand.getItem() != Toolset.INFINITY.getPickaxe().get() &&
                heldItemMainhand.getItem() != Toolset.INFINITY.getHoe().get() &&
                heldItemMainhand.getItem() != Toolset.INFINITY.getSword().get() &&
                heldItemMainhand.getItem() != Toolset.INFINITY.getAxe().get()) {
            if (heldItemMainhand.isDamageable()) {
                player.getHeldItemMainhand().damageItem(player.getHeldItemMainhand().getMaxDamage(), player, (entity) -> {
                    entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }
        } else {
            player.getCombatTracker().trackDamage(new DamageSource("mine.infinity_ore"), player.getHealth(), player.getHealth());
            player.setHealth(0);

            Minecraft.getInstance().enqueue(() -> {
                player.onDeath(new DamageSource("mine.infinity_ore"));
            });
        }
    }
}
