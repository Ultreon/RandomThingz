package com.ultreon.randomthingz.item.tool;

import com.ultreon.randomthingz.entity.DynamiteEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Legendary ender pearl item class.
 *
 * @author Qboi123
 */
public class DynamiteItem extends Item {
    public DynamiteItem(Properties builder) {
        super(builder);
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@linkplain #onUseItem}.
     */
    public @NotNull InteractionResultHolder<ItemStack> use(Level dimensionIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        dimensionIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldowns().addCooldown(this, 5);
        if (!dimensionIn.isClientSide) {
            DynamiteEntity dynamiteEntity = new DynamiteEntity(dimensionIn, playerIn);
            dynamiteEntity.setItem(itemstack);
            dynamiteEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0f, 1.5F, 1.0f);
            dimensionIn.addFreshEntity(dynamiteEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.sidedSuccess(itemstack, dimensionIn.isClientSide());
    }
}
