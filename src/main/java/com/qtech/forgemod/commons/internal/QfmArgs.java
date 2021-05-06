package com.qtech.forgemod.commons.internal;

import com.google.gson.JsonObject;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import lombok.AllArgsConstructor;
import lombok.Data;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * QForgeMod Build Arguments.
 *
 * @author Qboi123
 */
@Data
@AllArgsConstructor
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class QfmArgs {
    private final QfmFlags flags;
    private final QfmVersion version;
    private final Object buildInfo;

    public QfmArgs(JsonObject object) {
        this.flags = new QfmFlags(object.getAsJsonObject("flags"));
        this.version = new QfmVersion(object.getAsJsonObject("version"));
        this.buildInfo = new QfmBuildInfo(object.getAsJsonObject("build_info"));
    }
}
