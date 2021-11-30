package com.ultreon.randomthingz.common.internal;

import com.google.gson.JsonObject;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.ultreon.randomthingz.common.annotation.MethodsReturnNonnullByDefault;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Random Thingz Build Arguments.
 *
 * @author Qboi123
 */
@Data
@AllArgsConstructor
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class RtArgs {
    private final RtFlags flags;
    private final RtVersionInfo version;
    private final Object buildInfo;

    public RtArgs(JsonObject object) {
        this.flags = new RtFlags(object.getAsJsonObject("flags"));
        this.version = new RtVersionInfo(object.getAsJsonObject("version"));
        this.buildInfo = new RtBuildInfo(object.getAsJsonObject("build_info"));
    }
}
