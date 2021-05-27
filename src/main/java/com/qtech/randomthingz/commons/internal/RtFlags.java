package com.qtech.randomthingz.commons.internal;

import com.google.gson.JsonObject;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.qtech.randomthingz.commons.annotation.MethodsReturnNonnullByDefault;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * QForgeMod Mod Flags.
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
