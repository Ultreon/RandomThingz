package com.qsoftware.forgemod.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author MrCrayfish
 */
public class ItemSolidifyEvent extends Event
{
    private final PlayerEntity player;
    private final ItemStack stack;

    public ItemSolidifyEvent(PlayerEntity player, ItemStack stack)
    {
        this.player = player;
        this.stack = stack;
    }

    public PlayerEntity getPlayer()
    {
        return player;
    }

    public ItemStack getStack()
    {
        return this.stack;
    }
}
