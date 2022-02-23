package com.ultreon.randomthingz.common.updates;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.internal.RtVersion;

import java.net.MalformedURLException;
import java.net.URL;

public class UpdaterUpdater extends AbstractUpdater<RtVersion> {
    private static final String UPDATE_URL = "https://raw.githubusercontent.com/Ultreon/RandomThingz/master/update.json";
    private static final UpdaterUpdater INSTANCE = new UpdaterUpdater();

    @SuppressWarnings({"unused", "SameParameterValue"})
    private static URL getUrl(String s) {
        try {
            return new URL(UPDATE_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private UpdaterUpdater() {
        super(getUrl(UPDATE_URL), RandomThingz.getInstance());
    }

    static UpdaterUpdater getInstance() {
        return INSTANCE;
    }

    @Override
    public RtVersion parseVersion(String version) {
        return new RtVersion(version);
    }

    @Override
    public RtVersion getCurrentVersion() {
        return RandomThingz.RT_VERSION;
    }
}
