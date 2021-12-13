package com.ultreon.randomthingz.common.java.lists;

import com.ultreon.randomthingz.common.IntRange;
import com.ultreon.randomthingz.common.exceptions.OutOfRangeException;
import com.ultreon.randomthingz.common.exceptions.ValueExistsException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * This class is used for dynamically change ranges or get values from an index (based of all ranges merged).
 * One problem: it can cause performance issues. But, so far currently known is this the fastest method.
 *
 * @param <T> the type to use for the partition value.
 */
@SuppressWarnings({"unused", "DuplicatedCode"})
public class IntDynamicList<T> implements Iterable<IntDynamicList.Entry<T>> {
    CopyOnWriteArrayList<Integer> sizes = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<T> values = new CopyOnWriteArrayList<>();

    Integer totalSize = 0;

    public IntDynamicList() {

    }


    /**
     * Adds a partition along with the size and value.
     *
     * @param size  the size.
     * @param value the value.
     * @return the partition index of the new partition.
     */
    public synchronized int add(int size, T value) {
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

        totalSize = 0;
    }

    /**
     * Inserts a partition at the given index along with the size and value.
     *
     * @param index the partition index.
     * @param size  the size.
     * @param value the value.
     * @return the index.
     */
    public synchronized int insert(int index, Integer size, T value) {
        if (values.contains(value)) throw new ValueExistsException();

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
    public synchronized Integer getSize(int index) {
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
    public IntRange getRange(int index) {
        IntRange range = null;
        int currentSize = 0;
        for (int i = 0; i < sizes.size(); i++) {
            int newSize = currentSize + sizes.get(i);
            if (i == index) {
                range = IntRange.between(currentSize, newSize, IntComparators.asIntComparator(Comparator.naturalOrder()));
            }

            currentSize = newSize;
        }

        if (range == null) {
            throw new NullPointerException();
        }

        return range;
    }

    /**
     * Returns value based on the item index from all partitions merged.
     *
     * @param offset the index based on all ranges.
     * @return the value.
     */
    public T getValue(int offset) {
        if (!((0d <= offset) && (totalSize > offset))) {
            throw new OutOfRangeException(offset, 0, totalSize);
        }

        T value = null;
        int currentSize = -1;
        for (int i = 0; i < sizes.size(); i++) {
            int newSize = currentSize + sizes.get(i);

            if ((currentSize < offset) && (newSize >= offset)) {
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
     * @param size  the size for the partition to set.
     * @return the new size.
     */
    public synchronized Integer edit(T value, Integer size) {
        int index = indexOf(value);

        if (index >= sizes.size()) throw new OutOfRangeException(index, 0, sizes.size());

        totalSize = totalSize - sizes.get(index) + size;

        sizes.set(index, size);
        return sizes.get(index);
    }

    /**
     * Change the size and value of a partition.
     *
     * @param value    the value to change.
     * @param size     the partition size/
     * @param newValue the value.
     * @return the new size.
     */
    public synchronized Integer edit(T value, Integer size, T newValue) {
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
    @SuppressWarnings({"ConstantConditions"})
    public IntRange[] getRanges() {
        List<IntRange> ranges = new ArrayList<>();
        int currentSize = 0;
        for (Integer size : sizes) {
            int newSize = currentSize + size;

            ranges.add(IntRange.between(currentSize, newSize, IntComparators.asIntComparator(Comparator.naturalOrder())));
            currentSize = newSize;
        }

        return (IntRange[]) ranges.toArray();
    }


    public synchronized Integer getTotalSize() {
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
     * @param value the value to get the range from.
     * @return the index.
     */
    public IntList indexesOf(T value) {
        IntList ranges = new IntArrayList();
        boolean run = true;
        int index = 0;
        while (true) {
            index = values.indexOf(value, index);
            if (index == -1) {
                break;
            }

            ranges.add(index);
        }

        return IntLists.unmodifiable(ranges);
    }

    /**
     * Returns the range based of the value.
     *
     * @param value the value to get the range from.
     * @return the index.
     */
    public IntRange rangeOf(T value) {
        int index = values.indexOf(value);

        return getRange(index);
    }

    /**
     * Returns the range based of the value.
     *
     * @param value the value to get the range from.
     * @return the index.
     */
    public List<IntRange> rangesOf(T value) {
        List<IntRange> ranges = new ArrayList<>();
        boolean run = true;
        int index = 0;
        while (true) {
            index = values.indexOf(value, index);
            if (index == -1) {
                break;
            }

            ranges.add(getRange(index));
        }

        return Collections.unmodifiableList(ranges);
    }

    public synchronized void map(Function<T, Integer> applier) {
        int currentSize = 0;
        @SuppressWarnings("unchecked") CopyOnWriteArrayList<Integer> sizes2 = (CopyOnWriteArrayList<Integer>) sizes.clone();
        for (int i = 0; i < sizes2.size(); i++) {
            Integer applierSize = applier.apply(values.get(i));
            int newSize = currentSize + sizes2.get(i);
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
            Integer size = sizes.get(i);
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
        private final int size;

        private Entry(T value, int size) {
            this.value = value;
            this.size = size;
        }

        public T getValue() {
            return value;
        }

        public int getSize() {
            return size;
        }
    }
}
