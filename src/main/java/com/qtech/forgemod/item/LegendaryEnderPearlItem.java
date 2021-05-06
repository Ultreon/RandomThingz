package com.qtech.forgemod.item;

import com.qtech.forgemod.entity.LegendaryEnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Legendary ender pearl item class.
 *
 * @author Qboi123
 */
public class LegendaryEnderPearlItem extends EnderPearlItem {
    public LegendaryEnderPearlItem(Properties builder) {
        super(builder);
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@linkplain #onUseItem}.
     */
    public @NotNull ActionResult<ItemStack> onItemRightClick(World dimensionIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        dimensionIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 5);
        if (!dimensionIn.isClientSided) {
            LegendaryEnderPearlEntity enderPearlEntity = new LegendaryEnderPearlEntity(dimensionIn, playerIn);
            enderPearlEntity.setItem(itemstack);
            enderPearlEntity.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            dimensionIn.spawnEntity(enderPearlEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));

        return ActionResult.func_233538_a_(itemstack, dimensionIn.isClientSided());
    }
}
