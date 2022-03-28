package com.ultreon.randomthingz.common.math;

import java.util.Objects;
import java.util.function.Supplier;

@FunctionalInterface
public interface ByteOp {
    ByteOp ZERO = () -> (byte)0;
    ByteOp ONE = () -> (byte)1;
    ByteOp TWO = () -> (byte)2;
    ByteOp THREE = () -> (byte)3;
    ByteOp FOUR = () -> (byte)4;
    ByteOp FIVE = () -> (byte)5;
    ByteOp SIX = () -> (byte)6;
    ByteOp SEVEN = () -> (byte)7;
    ByteOp EIGHT = () -> (byte)8;
    ByteOp NINE = () -> (byte)9;
    ByteOp TEN = () -> (byte)10;
    ByteOp MIN_BYTE = () -> Byte.MIN_VALUE;
    ByteOp MAX_BYTE = () -> Byte.MAX_VALUE;

    byte calc();

    default ByteOp not() {
        return () -> (byte) ~this.calc();
    }

    default ByteOp and(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) (this.calc() & other.calc());
    }

    default ByteOp nand(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) ~(this.calc() & other.calc());
    }

    default ByteOp or(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) (this.calc() | other.calc());
    }

    default ByteOp nor(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) ~(this.calc() | other.calc());
    }

    default ByteOp xor(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) (this.calc() ^ other.calc());
    }

    default ByteOp xnor(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) ~(this.calc() ^ other.calc());
    }

    default ByteOp plus(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) (this.calc() + other.calc());
    }

    default ByteOp sub(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) (this.calc() - other.calc());
    }

    default ByteOp mul(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) (this.calc() * other.calc());
    }

    default ByteOp div(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) (this.calc() / other.calc());
    }

    default ByteOp mod(ByteOp other) {
        Objects.requireNonNull(other);
        return () -> (byte) (this.calc() % other.calc());
    }

    default DoubleOp pow(ByteOp other) {
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

    default DoubleOp atan2(ByteOp other) {
        return () -> Math.atan2(this.calc(), other.calc());
    }

    default ByteOp abs() {
        return () -> (byte) Math.abs(calc());
    }

    static ByteOp of(Supplier<Byte> supplier) {
        return supplier::get;
    }

    static ByteOp of(byte b) {
        return () -> b;
    }
}
