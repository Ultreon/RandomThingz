package com.qtech.randomthingz.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString(of = {"currentTicks"})
public class Ticker {
    @Getter private int currentTicks;
    private final Predicate<Ticker> autoReset;
    private final Consumer<Ticker> onTick;

    public Ticker(int currentTicks) {
        this(currentTicks, (ticker) -> false);
    }

    public Ticker(int currentTicks, @NotNull Predicate<Ticker> autoReset) {
        this(currentTicks, autoReset, (ticker) -> {});
    }

    public void advance() {
        this.currentTicks++;
        onTick.accept(this);
        if (autoReset.test(this)) {
            reset();
        }
    }
    
    public void reset() {
        this.currentTicks = 0;
    }
}
