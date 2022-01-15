package com.ultreon.randomthingz.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author Qboi123
 */
@Getter
@AllArgsConstructor
public class InfinityKillEvent extends Event {
    private final ItemStack stack;
    private final Player attacker;
    private final LivingEntity victim;
}
