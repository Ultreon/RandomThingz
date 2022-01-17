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
public class DefaultOreConfig implements IOreConfig {
    private final int veinCount;
    private final int veinSize;
    private final int minHeight;
    private final int maxHeight;

    public DefaultOreConfig(int veinCount, int veinSize, int minHeight, int maxHeight) {
        this.veinCount = veinCount;
        this.veinSize = veinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public int getVeinCount() {
        return veinCount;
    }

    public int getVeinSize() {
        return veinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}
