package com.ultreon.randomthingz.common.java.lists;

import com.ultreon.randomthingz.common.exceptions.OutOfRangeException;
import com.ultreon.randomthingz.common.exceptions.ValueExistsException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
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
public class FloatSizedList<T> implements Iterable<FloatSizedList.Entry<T>> {
    CopyOnWriteArrayList<Float> sizes = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<T> values = new CopyOnWriteArrayList<>();

    Float totalSize = 0f;

    public FloatSizedList() {

    }
    
    /**
     * Adds a partition along with the size and value.
     *
     * @param size  the size.
     * @param value the value.
     * @return the partition index of the new partition.
     */
    public synchronized int add(Float size, T value) {
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

        totalSize = 0f;
    }

    /**
     * Inserts a partition at the given index along with the size and value.
     *
     * @param index the partition index.
     * @param size  the size.
     * @param value the value.
     * @return the index.
     */
    public synchronized int insert(int index, Float size, T value) {
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
    public synchronized Float getSize(int index) {
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
    public Range<Float> getRange(int index) {
        Range<Float> range = null;
        Float currentSize = 0f;
        for (int i = 0; i < sizes.size(); i++) {
            Float newSize = currentSize + sizes.get(i);
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
     * Returns value based on the item index from all partitions merged.
     *
     * @param offset the index based on all ranges.
     * @return the value.
     */
    public T getValue(Float offset) {
        if (!((0d <= offset) && (totalSize > offset))) {
            throw new OutOfRangeException(offset, 0, totalSize);
        }

        T value = null;
        float currentSize = -1f;
        for (int i = 0; i < sizes.size(); i++) {
            float newSize = currentSize + sizes.get(i);

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
    public synchronized Float edit(T value, Float size) {
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
    public synchronized Float edit(T value, Float size, T newValue) {
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
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public Range<Float>[] getRanges() {
        List<Range<Float>> ranges = new ArrayList<>();
        Float currentSize = 0f;
        for (Float size : sizes) {
            Float newSize = currentSize + size;

            ranges.add(Range.between(currentSize, newSize, Comparator.naturalOrder()));
            currentSize = newSize;
        }

        return (Range<Float>[]) ranges.toArray();
    }


    public synchronized Float getTotalSize() {
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
    public Range<Float> rangeOf(T value) {
        int index = values.indexOf(value);

        return getRange(index);
    }

    /**
     * Returns the range based of the value.
     *
     * @param value the value to get the range from.
     * @return the index.
     */
    public List<Range<Float>> rangesOf(T value) {
        List<Range<Float>> ranges = new ArrayList<>();
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

    public synchronized void map(Function<T, Float> applier) {
        float currentSize = 0f;
        @SuppressWarnings("unchecked") CopyOnWriteArrayList<Float> sizes2 = (CopyOnWriteArrayList<Float>) sizes.clone();
        for (int i = 0; i < sizes2.size(); i++) {
            Float applierSize = applier.apply(values.get(i));
            float newSize = currentSize + sizes2.get(i);
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
            Float size = sizes.get(i);
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

    @SuppressWarnings("ClassCanBeRecord")
    public static class Entry<T> {
        private final T value;
        private final Float size;

        private Entry(T value, Float size) {
            this.value = value;
            this.size = size;
        }

        public T getValue() {
            return value;
        }

        public Float getSize() {
            return size;
        }
    }
}
