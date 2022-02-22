package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.entity.LegendaryEnderPearlEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Legendary ender pearl item class.
 *
 * @author Qboi123
 */
public class LegendaryEnderPearlItem extends EnderpearlItem {
    public LegendaryEnderPearlItem(Properties builder) {
        super(builder);
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@linkplain #onUseItem}.
     */
    public @NotNull
    InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, .5f, .4f / (player.getRandom().nextFloat() * .4f + .8f));
        player.getCooldowns().addCooldown(this, 5);
        if (!level.isClientSide) {
            LegendaryEnderPearlEntity enderPearlEntity = new LegendaryEnderPearlEntity(level, player);
            enderPearlEntity.setItem(itemstack);
            enderPearlEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, 1.5F, 1f);
            level.addFreshEntity(enderPearlEntity);
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
