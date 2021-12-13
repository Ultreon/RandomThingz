package com.ultreon.randomthingz.common;

import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntComparators;

import java.util.Comparator;

public class IntRange extends Range<Integer> {
    protected IntRange(int min, int max, IntComparator comparator) {
        super(min, max, comparator);
    }

    protected IntRange(Range<Integer> range) {
        super(range.getMinimum(), range.getMaximum(), IntComparators.asIntComparator(range.getComparator()));
    }

    @Deprecated
    @Override
    public boolean contains(Integer element) {
        return super.contains(element);
    }

    @Deprecated
    public boolean contains(int element) {
        return super.contains(element);
    }

    @Deprecated
    @Override
    public boolean containsRange(Range<Integer> otherRange) {
        return super.containsRange(otherRange);
    }

    public boolean containsRange(IntRange otherRange) {
        return super.containsRange(otherRange);
    }

    @Deprecated
    @Override
    public int elementCompareTo(Integer element) {
        return super.elementCompareTo(element);
    }

    public int elementCompareTo(int element) {
        return super.elementCompareTo(element);
    }

    @Deprecated
    @Override
    public Integer fit(Integer element) {
        return super.fit(element);
    }

    public Integer fit(int element) {
        return super.fit(element);
    }

    @Deprecated
    @Override
    public Comparator<Integer> getComparator() {
        return super.getComparator();
    }

    public IntComparator getComparatorInt() {
        return (IntComparator) super.getComparator();
    }

    @Deprecated
    @Override
    public Integer getMaximum() {
        return super.getMaximum();
    }

    public int getMaximumInt() {
        return super.getMaximum();
    }

    @Deprecated
    @Override
    public Integer getMinimum() {
        return super.getMinimum();
    }

    public int getMinimumInt() {
        return super.getMinimum();
    }

    @Deprecated
    @Override
    public Range<Integer> intersectionWith(Range<Integer> other) {
        return super.intersectionWith(other);
    }

    public IntRange intersectionWith(IntRange other) {
        return new IntRange(super.intersectionWith(other));
    }

    @Deprecated
    @Override
    public boolean isAfter(Integer element) {
        return super.isAfter(element);
    }

    public boolean isAfter(int element) {
        return super.isAfter(element);
    }

    @Deprecated
    @Override
    public boolean isAfterRange(Range<Integer> otherRange) {
        return super.isAfterRange(otherRange);
    }

    public boolean isAfterRange(IntRange otherRange) {
        return super.isAfterRange(otherRange);
    }

    @Deprecated
    @Override
    public boolean isBefore(Integer element) {
        return super.isBefore(element);
    }

    public boolean isBefore(int element) {
        return super.isBefore(element);
    }

    @Deprecated
    @Override
    public boolean isBeforeRange(Range<Integer> otherRange) {
        return super.isBeforeRange(otherRange);
    }

    public boolean isBeforeRange(IntRange otherRange) {
        return super.isBeforeRange(otherRange);
    }

    @Deprecated
    @Override
    public boolean isEndedBy(Integer element) {
        return super.isEndedBy(element);
    }

    public boolean isEndedBy(int element) {
        return super.isEndedBy(element);
    }

    @Deprecated
    @Override
    public boolean isOverlappedBy(Range<Integer> otherRange) {
        return super.isOverlappedBy(otherRange);
    }

    public boolean isOverlappedBy(IntRange otherRange) {
        return super.isOverlappedBy(otherRange);
    }

    @Deprecated
    @Override
    public boolean isStartedBy(Integer element) {
        return super.isStartedBy(element);
    }

    public boolean isStartedBy(int element) {
        return super.isStartedBy(element);
    }

    /**
     * <p>Obtains a range with the specified minimum and maximum values (both inclusive).</p>
     *
     * <p>The range uses the natural ordering of the elements to determine where
     * values lie in the range.</p>
     *
     * <p>The arguments may be passed in the order (min,max) or (max,min).
     * The getMinimum and getMaximum methods will return the correct values.</p>
     *
     * @param fromInclusive the first value that defines the edge of the range, inclusive
     * @param toInclusive   the second value that defines the edge of the range, inclusive
     * @return the range object, not null
     * @throws IllegalArgumentException if either element is null
     * @throws ClassCastException       if the elements are not {@code Comparable}
     */
    public static IntRange between(final int fromInclusive, final int toInclusive) {
        return between(fromInclusive, toInclusive, null);
    }

    /**
     * <p>Obtains a range with the specified minimum and maximum values (both inclusive).</p>
     *
     * <p>The range uses the specified {@code Comparator} to determine where
     * values lie in the range.</p>
     *
     * <p>The arguments may be passed in the order (min,max) or (max,min).
     * The getMinimum and getMaximum methods will return the correct values.</p>
     *
     * @param fromInclusive the first value that defines the edge of the range, inclusive
     * @param toInclusive   the second value that defines the edge of the range, inclusive
     * @param comparator    the comparator to be used, null for natural ordering
     * @return the range object, not null
     * @throws IllegalArgumentException if either element is null
     * @throws ClassCastException       if using natural ordering and the elements are not {@code Comparable}
     */
    public static IntRange between(final int fromInclusive, final int toInclusive, final IntComparator comparator) {
        return new IntRange(fromInclusive, toInclusive, comparator);
    }

    /**
     * <p>Obtains a range using the specified element as both the minimum
     * and maximum in this range.</p>
     *
     * <p>The range uses the natural ordering of the elements to determine where
     * values lie in the range.</p>
     *
     * @param element the value to use for this range, not null
     * @return the range object, not null
     * @throws IllegalArgumentException if the element is null
     * @throws ClassCastException       if the element is not {@code Comparable}
     */
    public static IntRange is(final int element) {
        return between(element, element, null);
    }

    /**
     * <p>Obtains a range using the specified element as both the minimum
     * and maximum in this range.</p>
     *
     * <p>The range uses the specified {@code Comparator} to determine where
     * values lie in the range.</p>
     *
     * @param element    the value to use for this range, must not be {@code null}
     * @param comparator the comparator to be used, null for natural ordering
     * @return the range object, not null
     * @throws IllegalArgumentException if the element is null
     * @throws ClassCastException       if using natural ordering and the elements are not {@code Comparable}
     */
    public static IntRange is(final int element, final IntComparator comparator) {
        return between(element, element, comparator);
    }
}
