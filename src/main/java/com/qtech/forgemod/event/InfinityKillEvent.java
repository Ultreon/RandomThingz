package com.qtech.forgemod.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author Qboi123
 */
@Getter
@AllArgsConstructor
public class InfinityKillEvent extends Event {
    private final ItemStack stack;
    private final PlayerEntity attacker;
    private final LivingEntity victim;
}
