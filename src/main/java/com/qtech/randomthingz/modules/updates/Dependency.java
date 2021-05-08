package com.qtech.randomthingz.modules.updates;

import java.net.URL;
import java.util.Objects;

public class Dependency {
    private final String modId;
    private final String name;
    private final URL download;
    private final Dependencies dependencies;

    public Dependency(String modId, String name, URL download) {
        this(modId, name, download, new Dependencies());
    }

    public Dependency(String modId, String name, URL download, Dependencies dependencies) {
        this.modId = modId;
        this.name = name;
        this.download = download;
        this.dependencies = dependencies;
    }

    public String getModId() {
        return modId;
    }

    public String getName() {
        return name;
    }

    public URL getDownload() {
        return download;
    }

    public Dependencies getDependencies() {
        return dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dependency that = (Dependency) o;
        return Objects.equals(modId, that.modId) && Objects.equals(download, that.download) && Objects.equals(dependencies, that.dependencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modId, download, dependencies);
    }
}
