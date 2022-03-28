package com.ultreon.randomthingz.common.math;

import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface FloatOp {
    FloatOp ZERO = () -> 0;
    FloatOp ONE = () -> 1;
    FloatOp TWO = () -> 2;
    FloatOp THREE = () -> 3;
    FloatOp FOUR = () -> 4;
    FloatOp FIVE = () -> 5;
    FloatOp SIX = () -> 6;
    FloatOp SEVEN = () -> 7;
    FloatOp EIGHT = () -> 8;
    FloatOp NINE = () -> 9;
    FloatOp TEN = () -> 10;

    float calc();

    default FloatOp plus(FloatOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() + other.calc();
    }

    default FloatOp sub(FloatOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() - other.calc();
    }

    default FloatOp mul(FloatOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() * other.calc();
    }

    default FloatOp div(FloatOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() / other.calc();
    }

    default FloatOp mod(FloatOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() % other.calc();
    }

    default DoubleOp pow(FloatOp other) {
        Objects.requireNonNull(other);
        return () -> Math.pow(this.calc(), other.calc());
    }

    default DoubleOp sin() {
        return () -> Math.sin(this.calc());
    }

    default DoubleOp sinh() {
        return () -> Math.sinh(this.calc());
    }

    default DoubleOp asin() {
        return () -> Math.asin(this.calc());
    }

    default DoubleOp cos() {
        return () -> Math.cos(this.calc());
    }

    default DoubleOp cosh() {
        return () -> Math.cosh(this.calc());
    }

    default DoubleOp acos() {
        return () -> Math.acos(this.calc());
    }

    default DoubleOp tan() {
        return () -> Math.tan(this.calc());
    }

    default DoubleOp tanh() {
        return () -> Math.tanh(this.calc());
    }

    default DoubleOp atan() {
        return () -> Math.atan(this.calc());
    }

    default DoubleOp atan2(FloatOp other) {
        return () -> Math.atan2(this.calc(), other.calc());
    }

    default FloatOp abs() {
        return () -> Math.abs(calc());
    }

    static FloatOp of(Supplier<Float> supplier) {
        return supplier::get;
    }

    static FloatOp of(float b) {
        return () -> b;
    }
}
