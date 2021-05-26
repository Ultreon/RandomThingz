package com.qtech.randomthingz.world.gen.ores.configs;

import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Ore configuration with chance per X chunks.
 *
 * @author Qbo1i23
 * @see DefaultOreConfig
 * @see #getChance()
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
public class ChancedOreConfig extends DefaultOreConfig {
    private final int chance;

    /**
     * Default Ore Configuration: Constructor
     *
     * @param veinSize the size of the vein.
     * @param minHeight the lowest level of generating.
     * @param maxHeight the highest level of generating.
     * @param chance the chance per X chunks. {@linkplain #getChance() The getter}.
     */
    public ChancedOreConfig(int veinCount, int veinSize, int minHeight, int maxHeight, int chance) {
        super(veinCount, veinSize, minHeight, maxHeight);
        this.chance = chance;
    }

    public int getChance() {
        return chance;
    }
}
