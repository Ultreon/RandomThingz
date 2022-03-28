package com.ultreon.randomthingz.common.math;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface IntOp {
    IntOp ZERO = () -> 0;
    IntOp ONE = () -> 1;
    IntOp TWO = () -> 2;
    IntOp THREE = () -> 3;
    IntOp FOUR = () -> 4;
    IntOp FIVE = () -> 5;
    IntOp SIX = () -> 6;
    IntOp SEVEN = () -> 7;
    IntOp EIGHT = () -> 8;
    IntOp NINE = () -> 9;
    IntOp TEN = () -> 10;

    int calc();

    default IntOp not() {
        return () -> ~this.calc();
    }

    default IntOp and(IntOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() & other.calc();
    }

    default IntOp nand(IntOp other) {
        Objects.requireNonNull(other);
        return () -> ~(this.calc() & other.calc());
    }

    default IntOp or(IntOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() | other.calc();
    }

    default IntOp nor(IntOp other) {
        Objects.requireNonNull(other);
        return () -> ~(this.calc() | other.calc());
    }

    default IntOp xor(IntOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() ^ other.calc();
    }

    default IntOp xnor(IntOp other) {
        Objects.requireNonNull(other);
        return () -> ~(this.calc() ^ other.calc());
    }

    default IntOp plus(IntOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() + other.calc();
    }

    default IntOp sub(IntOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() - other.calc();
    }

    default IntOp mul(IntOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() * other.calc();
    }

    default IntOp div(IntOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() / other.calc();
    }

    default IntOp mod(IntOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() % other.calc();
    }

    default DoubleOp pow(IntOp other) {
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

    default DoubleOp atan2(IntOp other) {
        return () -> Math.atan2(this.calc(), other.calc());
    }

    default IntOp abs() {
        return () -> Math.abs(calc());
    }

    static IntOp of(Supplier<Integer> supplier) {
        return supplier::get;
    }

    static IntOp of(IntSupplier supplier) {
        return supplier::getAsInt;
    }

    static IntOp of(int b) {
        return () -> b;
    }
}
