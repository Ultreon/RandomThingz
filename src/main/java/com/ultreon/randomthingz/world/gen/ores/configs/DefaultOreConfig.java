package com.ultreon.randomthingz.world.gen.ores.configs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@Getter
@ToString
@EqualsAndHashCode
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
public class DefaultOreConfig implements OreConfig {
    private final int veinCount;
    private final int veinSize;
    private final int minHeight;
    private final int maxHeight;
    private final int spawnChance;

    public DefaultOreConfig(int veinCount, int veinSize, int minHeight, int maxHeight) {
        this(veinCount, veinSize, minHeight, maxHeight, 1);
    }

    public DefaultOreConfig(int veinCount, int veinSize, int minHeight, int maxHeight, int spawnChance) {
        this.veinCount = veinCount;
        this.veinSize = veinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.spawnChance = spawnChance;
    }

    @Override
    public int getVeinCount() {
        return veinCount;
    }

    @Override
    public int getVeinSize() {
        return veinSize;
    }

    @Override
    public int getMinHeight() {
        return minHeight;
    }

    @Override
    public int getMaxHeight() {
        return maxHeight;
    }

    @Override
    public int getSpawnChance() {
        return spawnChance;
    }
}
