package com.qsoftware.forgemod.modules.environment.ores.configs;

import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
public class ChancedOreConfigs extends DefaultOreConfig {
    private final int chance;

    public ChancedOreConfigs(int veinCount, int veinSize, int minHeight, int maxHeight, int chance) {
        super(veinCount, veinSize, minHeight, maxHeight);
        this.chance = chance;
    }

    public int getChance() {
        return chance;
    }
}
