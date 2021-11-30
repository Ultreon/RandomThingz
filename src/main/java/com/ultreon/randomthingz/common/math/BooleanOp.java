package com.ultreon.randomthingz.common.math;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface BooleanOp {
    BooleanOp TRUE = () -> true;
    BooleanOp FALSE = () -> false;

    boolean calc();

    default BooleanOp and(BooleanOp var1) {
        Objects.requireNonNull(var1);
        return () -> this.calc() && var1.calc();
    }

    default BooleanOp negate() {
        return () -> !this.calc();
    }

    default BooleanOp or(BooleanOp var1) {
        Objects.requireNonNull(var1);
        return () -> this.calc() || var1.calc();
    }

    static BooleanOp of(Supplier<Boolean> supplier) {
        return supplier::get;
    }

    static BooleanOp of(BooleanSupplier supplier) {
        return supplier::getAsBoolean;
    }

    static BooleanOp of(boolean b) {
        return () -> b;
    }
}
