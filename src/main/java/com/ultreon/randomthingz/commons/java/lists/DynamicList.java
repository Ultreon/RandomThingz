package com.ultreon.randomthingz.commons.java.lists;

import com.ultreon.randomthingz.commons.exceptions.OutOfRangeException;
import com.ultreon.randomthingz.commons.exceptions.ValueExistsException;
import org.apache.commons.lang3.Range;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * This class is used for dynamically change ranges or get values from an index (based of all ranges merged).
 * One problem: it can cause performance issues. But, so far currently known is this the fastest method.
 *
 * @param <T> the type to use for the partition value.
 */
@SuppressWarnings("unused")
public class DynamicList<T> implements Iterable<DynamicList.Entry<T>> {
    CopyOnWriteArrayList<Double> sizes = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<T> values = new CopyOnWriteArrayList<>();

    Double totalSize = 0d;

    public DynamicList() {

    }


    /**
     * Adds a partition along with the size and value.
     *
     * @param size the size.
     * @param value the value.
     * @return the partition index of the new partition.
     * @throws ValueExistsException as the exception it says: if the value already exists.
     */
    public synchronized int add(double size, T value) throws ValueExistsException {
        if (values.contains(value)) throw new ValueExistsException();

        sizes.add(size);
        values.add(value);

        totalSize += size;

        return sizes.lastIndexOf(size);
    }

    /**
     * Clears all partitions.
     *
     * <i>In case of emergency.</i>
     */
    public synchronized void clear() {
        sizes.clear();
        values.clear();

        totalSize = 0d;
    }

    /**
     * Inserts a partition at the given index along with the size and value.
     *
     * @param index the partition index.
     * @param size the size.
     * @param value the value.
     * @return the index.
     */
    public synchronized int insert(int index, Double size, T value) {
        sizes.add(index, size);
        values.add(index, value);

        totalSize += size;

        return index;
    }

    /**
     * Returns the size of the partition at the given index.
     *
     * @param index the partition index.
     * @return the size.
     */
    public synchronized Double getSize(int index) {
        return sizes.get(index);
    }

    /**
     * Removes the partition at the given index.
     *
     * @param index the partition index.
     */
    public synchronized void delete(int index) {
        totalSize -= sizes.get(index);
        sizes.remove(index);
        values.remove(index);
    }

    /**
     * Returns a range from the ‘partition’ index.
     *
     * @param index the index.
     * @return the range at the given index.
     * @throws NullPointerException if the index is out of range.
     */
    public Range<Double> getRange(int index) {
        Range<Double> range = null;
        double currentSize = 0;
        for (int i = 0; i < sizes.size(); i++) {
            double newSize = currentSize + sizes.get(i);
            if (i == index) {
                range = Range.between(currentSize, newSize, Comparator.naturalOrder());
            }

            currentSize = newSize;
        }

        if (range == null) {
            throw new NullPointerException();
        }

        return range;
    }

    /**
     * Returns value based on the item index from all partitions merged..
     *
     * @param drIndex the index based on all ranges.
     * @return the value.
     */
    public T getValue(double drIndex) {
        if (!((0d <= drIndex) && (totalSize > drIndex))) {
            throw new OutOfRangeException(drIndex, 0, totalSize);
        }

        T value = null;
        double currentSize = -1;
        for (int i = 0; i < sizes.size(); i++) {
            double newSize = currentSize + sizes.get(i);

            if ((currentSize < drIndex) && (newSize >= drIndex)) {
                value = values.get(i);
            }

            currentSize = newSize;
        }

        return value;
    }

    /**
     * Change the size for a partition.
     *
     * @param value the value to change.
     * @param size the size for the partition to set.
     * @return the new size.
     */
    public synchronized Double edit(T value, Double size) {
        int index = indexOf(value);

        if (index >= sizes.size()) throw new OutOfRangeException(index, 0, sizes.size());

        totalSize = totalSize - sizes.get(index) + size;

        sizes.set(index, size);
        return sizes.get(index);
    }

    /**
     * Change the size and value of a partition.
     *
     * @param value the value to change.
     * @param size the partition size/
     * @param newValue the value.
     * @return the new size.
     */
    public synchronized Double edit(T value, Double size, T newValue) {
        int index = indexOf(value);

        if (index >= sizes.size()) throw new OutOfRangeException(index, 0, sizes.size());

        totalSize = totalSize - sizes.get(index) + size;

        sizes.set(index, size);
        values.set(index, newValue);
        return sizes.get(index);
    }

    /**
     * Returns ranges of all partitions.
     *
     * @return the ranges of all partitions.
     */
    @SuppressWarnings("unchecked")
    public Range<Double>[] getRanges() {
        List<Range<Double>> ranges = new ArrayList<>();
        double currentSize = 0;
        for (Double size : sizes) {
            double newSize = currentSize + size;

            ranges.add(Range.between(currentSize, newSize, Comparator.naturalOrder()));
            currentSize = newSize;
        }

        return (Range<Double>[]) ranges.toArray();
    }

    public synchronized Double getTotalSize() {
        return totalSize;
    }

    /**
     * Returns the index based of the value.
     *
     * @param value the value to get index from.
     * @return the index.
     */
    public int indexOf(T value) {
        return values.indexOf(value);
    }

    /**
     * Returns the range based of the value.
     *
     * @param value the value to get the range from..
     * @return the index.
     */
    public Range<Double> rangeOf(T value) {
        int index = values.indexOf(value);

        return getRange(index);
    }

    public synchronized void editLengths(Function<T, Double> applier) {
        double currentSize = 0;
        @SuppressWarnings("unchecked") CopyOnWriteArrayList<Double> sizes2 = (CopyOnWriteArrayList<Double>) sizes.clone();
        for (int i = 0; i < sizes2.size(); i++) {
            Double applierSize = applier.apply(values.get(i));
            double newSize = currentSize + sizes2.get(i);
            totalSize = totalSize - sizes2.get(i) + applierSize;
            sizes2.set(i, applierSize);

            currentSize = newSize;
        }
        sizes = sizes2;
    }

    @Override
    public Iterator<Entry<T>> iterator() {
        return getEntries().iterator();
    }

    public Collection<Entry<T>> getEntries() {
        checkSizes();

        List<Entry<T>> entries = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            T value = values.get(i);
            Double size = sizes.get(i);
            if (size == null) {
                continue;
            }

            entries.add(new Entry<>(value, size));
        }

        return Collections.unmodifiableList(entries);
    }

    private void checkSizes() {
        if (values.size() != sizes.size()) {
            throw new IllegalStateException("The values and sizes have different sizes, this is not allowed.");
        }
    }

    public static class Entry<T> {
        private final T value;
        private final double size;

        private Entry(T value, double size) {
            this.value = value;
            this.size = size;
        }

        public T getValue() {
            return value;
        }

        public double getSize() {
            return size;
        }
    }
}
