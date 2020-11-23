package com.qsoftware.forgemod.api;

import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public enum Action {
    EXECUTE(FluidAction.EXECUTE),
    SIMULATE(FluidAction.SIMULATE);

    private final FluidAction fluidAction;

    Action(FluidAction fluidAction) {
        this.fluidAction = fluidAction;
    }

    public static Action get(boolean execute) {
        return execute ? EXECUTE : SIMULATE;
    }

    public static Action fromFluidAction(FluidAction action) {
        if (action == FluidAction.EXECUTE) {
            return EXECUTE;
        } //else FluidAction.SIMULATE
        return SIMULATE;
    }

    public boolean execute() {
        return this == EXECUTE;
    }

    public boolean simulate() {
        return this == SIMULATE;
    }

    public FluidAction toFluidAction() {
        return fluidAction;
    }

    public Action combine(boolean execute) {
        return get(execute && execute());
    }
}