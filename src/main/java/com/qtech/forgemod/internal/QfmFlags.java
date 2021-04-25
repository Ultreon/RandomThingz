package com.qtech.forgemod.internal;

import com.google.gson.JsonObject;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import lombok.AllArgsConstructor;
import lombok.Data;
import mcp.MethodsReturnNonnullByDefault;

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
public class QfmFlags {
    private final boolean devTest;

    public QfmFlags(JsonObject json) {
        devTest = json.getAsJsonPrimitive("dev_test").getAsBoolean();
    }
}
