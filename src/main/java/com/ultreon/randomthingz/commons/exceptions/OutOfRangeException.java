package com.ultreon.randomthingz.commons.exceptions;

public class OutOfRangeException extends RuntimeException {
    public OutOfRangeException(byte index, byte start, byte end) {
        super("Index " + index + " is out of the range (" + start + ", " + end + ")");
    }
    public OutOfRangeException(short index, short start, short end) {
        super("Index " + index + " is out of the range (" + start + ", " + end + ")");
    }
    public OutOfRangeException(int index, int start, int end) {
        super("Index " + index + " is out of the range (" + start + ", " + end + ")");
    }
    public OutOfRangeException(long index, long start, long end) {
        super("Index " + index + " is out of the range (" + start + ", " + end + ")");
    }
    public OutOfRangeException(float index, float start, float end) {
        super("Index " + index + " is out of the range (" + start + ", " + end + ")");
    }
    public OutOfRangeException(double index, double start, double end) {
        super("Index " + index + " is out of the range (" + start + ", " + end + ")");
    }
}
