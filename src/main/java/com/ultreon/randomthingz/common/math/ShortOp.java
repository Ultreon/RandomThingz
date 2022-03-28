package com.ultreon.randomthingz.common.math;

import java.util.Objects;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

@FunctionalInterface
public interface ShortOp {
    ShortOp ZERO = () -> (short)0;
    ShortOp ONE = () -> (short)1;
    ShortOp TWO = () -> (short)2;
    ShortOp THREE = () -> (short)3;
    ShortOp FOUR = () -> (short)4;
    ShortOp FIVE = () -> (short)5;
    ShortOp SIX = () -> (short)6;
    ShortOp SEVEN = () -> (short)7;
    ShortOp EIGHT = () -> (short)8;
    ShortOp NINE = () -> (short)9;
    ShortOp TEN = () -> (short)10;

    short calc();

    default IntOp not() {
        return () -> ~this.calc();
    }

    default IntOp and(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() & other.calc();
    }

    default IntOp nand(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> ~(this.calc() & other.calc());
    }

    default IntOp or(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() | other.calc();
    }

    default IntOp nor(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> ~(this.calc() | other.calc());
    }

    default IntOp xor(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() ^ other.calc();
    }

    default IntOp xnor(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> ~(this.calc() ^ other.calc());
    }

    default IntOp plus(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() + other.calc();
    }

    default IntOp sub(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() - other.calc();
    }

    default IntOp mul(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() * other.calc();
    }

    default IntOp div(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() / other.calc();
    }

    default IntOp mod(ShortOp other) {
        Objects.requireNonNull(other);
        return () -> this.calc() % other.calc();
    }

    default DoubleOp pow(ShortOp other) {
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

    default DoubleOp atan2(ShortOp other) {
        return () -> Math.atan2(this.calc(), other.calc());
    }

    default IntOp abs() {
        return () -> Math.abs(calc());
    }

    static ShortOp of(Supplier<Short> supplier) {
        return supplier::get;
    }

    static ShortOp of(short b) {
        return () -> b;
    }
}
