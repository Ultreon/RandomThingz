package com.qtech.randomthingz.commons.internal;

import com.google.gson.JsonObject;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.qtech.randomthingz.commons.annotation.MethodsReturnNonnullByDefault;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * QForgeMod Version for Build Arguments.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RtVersionInfo implements DevStringConvertible {
    private final String name;
    private final int version;
    private final int release;
    private final int build;
    private final String stage;
    private final int stageRelease;
    private final JsonObject json;

    public RtVersionInfo(JsonObject o) {
        this.name = o.getAsJsonPrimitive("name").getAsString();
        this.version = o.getAsJsonPrimitive("version").getAsInt();
        this.release = o.getAsJsonPrimitive("release").getAsInt();
        this.build = o.getAsJsonPrimitive("build").getAsInt();
        this.stage = o.getAsJsonPrimitive("stage").getAsString();
        this.stageRelease = o.getAsJsonPrimitive("stage_release").getAsInt();
        this.json = o;
    }

    @Override
    public String toString() {
        return name;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Override
    public String toDevString() {
        StringBuilder sb = new StringBuilder();
        sb.append("QfmVersion{");
        sb.append("version=")     .append(version)     .append(";");
        sb.append("release=")     .append(release)     .append(";");
        sb.append("build=")       .append(build)       .append(";");
        sb.append("stage=")       .append(stage)       .append(";");
        sb.append("stageRelease=").append(stageRelease).append(";");
        sb.append("}");
        return sb.toString();
    }

    public RtVersion toVersionObject() {
        return new RtVersion(version, release, build, stage, stageRelease);
    }
}
