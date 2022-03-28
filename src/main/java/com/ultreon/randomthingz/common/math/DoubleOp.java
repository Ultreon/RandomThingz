package com.ultreon.randomthingz.common.math;

import java.util.Objects;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface DoubleOp {
    DoubleOp ZERO = () -> 0d;
    DoubleOp ONE = () -> 1d;
    DoubleOp TWO = () -> 2d;
    DoubleOp THREE = () -> 3d;
    DoubleOp FOUR = () -> 4d;
    DoubleOp FIVE = () -> 5d;
    DoubleOp SIX = () -> 6d;
    DoubleOp SEVEN = () -> 7d;
    DoubleOp EIGHT = () -> 8d;
    DoubleOp NINE = () -> 9d;
    DoubleOp TEN = () -> 10d;

    double calc();

    default DoubleOp plus(DoubleOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() + other.calc();
    }

    default DoubleOp sub(DoubleOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() - other.calc();
    }

    default DoubleOp mul(DoubleOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() * other.calc();
    }

    default DoubleOp div(DoubleOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() / other.calc();
    }

    default DoubleOp mod(DoubleOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() % other.calc();
    }

    default DoubleOp pow(DoubleOp other) {
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

    default DoubleOp atan2(DoubleOp other) {
        return () -> Math.atan2(this.calc(), other.calc());
    }

    default DoubleOp abs() {
        return () -> Math.abs(calc());
    }

    static DoubleOp of(Supplier<Double> supplier) {
        return supplier::get;
    }

    static DoubleOp of(DoubleSupplier supplier) {
        return supplier::getAsDouble;
    }

    static DoubleOp of(double b) {
        return () -> b;
    }
}
