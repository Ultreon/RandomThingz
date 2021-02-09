package com.qsoftware.forgemod.internal;

import com.google.gson.JsonObject;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDateTime;

@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class QfmBuildInfo {
    private final LocalDateTime buildDate;
    private final Compile compile;
    private final Project project;

    public QfmBuildInfo(JsonObject o) {
        this.buildDate = LocalDateTime.parse(o.getAsJsonPrimitive("date").getAsString());
        this.compile = new Compile(o.getAsJsonObject("compile"));
        this.project = new Project(o.getAsJsonObject("project"));
    }

    @Data
    @AllArgsConstructor
    @FieldsAreNonnullByDefault
    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public static final class Project {
        private final String displayName;
        private final String name;
        private final Minecraft minecraft;

        public Project(JsonObject o) {
            this.displayName = o.getAsJsonPrimitive("display_name").getAsString();
            this.name = o.getAsJsonPrimitive("name").getAsString();
            this.minecraft = new Minecraft(o.getAsJsonObject("minecraft"));
        }

        @Data
        @AllArgsConstructor
        @FieldsAreNonnullByDefault
        @ParametersAreNonnullByDefault
        @MethodsReturnNonnullByDefault
        public static final class Minecraft {
            private final String mappingsVersion;
            private final String mappingsChannel;
            private final String mappings;
            private final String version;
            private final String forgeVersion;

            public Minecraft(JsonObject o) {
                this.mappingsVersion = o.getAsJsonPrimitive("mappings_version").getAsString();
                this.mappingsChannel = o.getAsJsonPrimitive("mappings_channel").getAsString();
                this.mappings = o.getAsJsonPrimitive("mappings").getAsString();
                this.version = o.getAsJsonPrimitive("version").getAsString();
                this.forgeVersion = o.getAsJsonPrimitive("forge_version").getAsString();
            }
        }
    }

    @Data
    @AllArgsConstructor
    @FieldsAreNonnullByDefault
    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public static final class Compile {
        private final String sourceCompatibility;
        private final String targetCompatibility;
        private final Gradle gradle;

        public Compile(JsonObject o) {
            this.sourceCompatibility = o.getAsJsonPrimitive("source_compat").getAsString();
            this.targetCompatibility = o.getAsJsonPrimitive("target_compat").getAsString();
            this.gradle = new Gradle(o.getAsJsonObject("gradle"));
        }

        @Data
        @AllArgsConstructor
        @FieldsAreNonnullByDefault
        @ParametersAreNonnullByDefault
        @MethodsReturnNonnullByDefault
        public static final class Gradle {
            private final String version;

            public Gradle(JsonObject o) {
                this.version = o.getAsJsonPrimitive("version").getAsString();
            }

            public String getVersion() {
                return version;
            }
        }
    }
}
