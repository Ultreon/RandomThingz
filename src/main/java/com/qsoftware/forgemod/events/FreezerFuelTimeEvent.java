package com.qsoftware.forgemod.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

/**
 * @author MrCrayfish
 */
public class FreezerFuelTimeEvent extends Event {
    private final ItemStack stack;
    private int fuelTime;

    public FreezerFuelTimeEvent(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getFuelTime() {
        return fuelTime;
    }

    public void setFuelTime(int fuelTime) {
        this.fuelTime = fuelTime;
    }
}
