package com.qsoftware.forgemod.modules.items.objects.tools;

import com.qsoftware.forgemod.modules.entities.objects.DynamiteEntity;
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
public class DynamiteItem extends EnderPearlItem {
    public DynamiteItem(Properties builder) {
        super(builder);
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public @NotNull ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, @NotNull Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 5);
        if (!worldIn.isRemote) {
            DynamiteEntity dynamiteEntity = new DynamiteEntity(worldIn, playerIn);
            dynamiteEntity.setItem(itemstack);
            dynamiteEntity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(dynamiteEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));

        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }
}
