package com.ultreon.randomthingz.block.overpowered;

import com.ultreon.randomthingz.item.tool.Toolset;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Infinity ore block class.
 *
 * @author Qboi123
 */
@SuppressWarnings("deprecation")
public class InfinityOreBlock extends OreBlock {
    public InfinityOreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void attack(@NotNull BlockState state, @NotNull Level dimensionIn, @NotNull BlockPos pos, @NotNull Player player) {
        super.attack(state, dimensionIn, pos, player);

        ItemStack heldItemMainhand = player.getMainHandItem();
        if (heldItemMainhand.isEmpty()) {
            player.getCombatTracker().recordDamage(new DamageSource("mine_infinity_ore"), player.getHealth(), player.getHealth());
            player.setHealth(0);

            Minecraft.getInstance().tell(() -> {
                player.die(new DamageSource("mine.infinity_ore"));
            });
        } else if (heldItemMainhand.getItem() != Toolset.INFINITY.getShovel().get() &&
                heldItemMainhand.getItem() != Toolset.INFINITY.getPickaxe().get() &&
                heldItemMainhand.getItem() != Toolset.INFINITY.getHoe().get() &&
                heldItemMainhand.getItem() != Toolset.INFINITY.getSword().get() &&
                heldItemMainhand.getItem() != Toolset.INFINITY.getAxe().get()) {
            if (heldItemMainhand.isDamageableItem()) {
                player.getMainHandItem().hurtAndBreak(player.getMainHandItem().getMaxDamage(), player, (entity) -> {
                    entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }
        } else {
            player.getCombatTracker().recordDamage(new DamageSource("mine_infinity_ore"), player.getHealth(), player.getHealth());
            player.setHealth(0);

            Minecraft.getInstance().tell(() -> {
                player.die(new DamageSource("mine.infinity_ore"));
            });
        }
    }
}
