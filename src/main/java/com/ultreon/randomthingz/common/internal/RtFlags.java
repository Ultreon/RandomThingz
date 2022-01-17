package com.ultreon.randomthingz.common.internal;

import com.google.gson.JsonObject;
import com.ultreon.randomthingz.common.annotation.MethodsReturnNonnullByDefault;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.FieldsAreNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Random Thingz Mod Flags.
 *
 * @author Qboi123
 */
@Data
@AllArgsConstructor
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RtFlags {
    private final boolean devTest;

    public RtFlags(JsonObject json) {
        devTest = json.getAsJsonPrimitive("dev_test").getAsBoolean();
    }
}
