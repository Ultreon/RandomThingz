package com.ultreon.randomthingz.common.math;

import java.util.Objects;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface LongOp {
    LongOp ZERO = () -> 0L;
    LongOp ONE = () -> 1L;
    LongOp TWO = () -> 2L;
    LongOp THREE = () -> 3L;
    LongOp FOUR = () -> 4L;
    LongOp FIVE = () -> 5L;
    LongOp SIX = () -> 6L;
    LongOp SEVEN = () -> 7L;
    LongOp EIGHT = () -> 8L;
    LongOp NINE = () -> 9L;
    LongOp TEN = () -> 10L;

    long calc();

    default LongOp not() {
        return () -> ~this.calc();
    }

    default LongOp and(LongOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() & other.calc();
    }

    default LongOp nand(LongOp other) {
        Objects.requireNonNull(other);
        return () -> ~(this.calc() & other.calc());
    }

    default LongOp or(LongOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() | other.calc();
    }

    default LongOp nor(LongOp other) {
        Objects.requireNonNull(other);
        return () -> ~(this.calc() | other.calc());
    }

    default LongOp xor(LongOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() ^ other.calc();
    }

    default LongOp xnor(LongOp other) {
        Objects.requireNonNull(other);
        return () -> ~(this.calc() ^ other.calc());
    }

    default LongOp plus(LongOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() + other.calc();
    }

    default LongOp sub(LongOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() - other.calc();
    }

    default LongOp mul(LongOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() * other.calc();
    }

    default LongOp div(LongOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() / other.calc();
    }

    default LongOp mod(LongOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() % other.calc();
    }

    default DoubleOp pow(LongOp other) {
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

    default DoubleOp atan2(LongOp other) {
        return () -> Math.atan2(this.calc(), other.calc());
    }

    default LongOp abs() {
        return () -> Math.abs(calc());
    }

    static LongOp of(Supplier<Long> supplier) {
        return supplier::get;
    }

    static LongOp of(LongSupplier supplier) {
        return supplier::getAsLong;
    }

    static LongOp of(long b) {
        return () -> b;
    }
}
