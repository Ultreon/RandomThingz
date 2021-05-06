package com.qtech.forgemod.modules.updates;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Predicate;

public class Dependencies extends HashSet<Dependency> {
    private boolean locked = false;

    public Dependencies(int initialCapacity) {
        super(initialCapacity);
    }

    public Dependencies() {
    }

    public Dependencies(@NotNull Collection<? extends Dependency> c) {
        super(c);
    }

    public Dependencies(Dependency... dependencies) {
        super(Arrays.asList(dependencies));
    }

    public Set<Dependency> getAll() {
        Set<Dependency> dependencySet = new HashSet<>();

        for (Dependency dependency : this) {
            dependencySet.add(dependency);
            dependencySet.addAll(dependency.getDependencies().getAll());
        }

        return Collections.unmodifiableSet(dependencySet);
    }

    @Override
    public boolean addAll(Collection<? extends Dependency> c) {
        if (this.locked) {
            throw new IllegalStateException("Dependencies list is locked, making it read only.");
        }
        return super.addAll(c);
    }

    public boolean addAll(Dependencies c) {
        if (this.locked) {
            throw new IllegalStateException("Dependencies list is locked, making it read only.");
        }
        return super.addAll(c);
    }

    @Override
    public boolean add(Dependency dependency) {
        if (this.locked) {
            throw new IllegalStateException("Dependencies list is locked, making it read only.");
        }
        return super.add(dependency);
    }

    @Override
    public void clear() {
        if (this.locked) {
            throw new IllegalStateException("Dependencies list is locked, making it read only.");
        }
        super.clear();
    }

    @Override
    public boolean remove(Object o) {
        if (this.locked) {
            throw new IllegalStateException("Dependencies list is locked, making it read only.");
        }
        return super.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (this.locked) {
            throw new IllegalStateException("Dependencies list is locked, making it read only.");
        }
        return super.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super Dependency> filter) {
        if (this.locked) {
            throw new IllegalStateException("Dependencies list is locked, making it read only.");
        }
        return false;
    }

    public void lock() {
        this.locked = true;
    }

    public boolean isLocked() {
        return locked;
    }
}
