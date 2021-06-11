package com.qtech.randomthingz.modules.updates;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.commons.internal.RtVersion;

import java.net.MalformedURLException;
import java.net.URL;

public class QFMUpdater extends AbstractUpdater<RtVersion> {
    private static final String UPDATE_URL = "https://raw.githubusercontent.com/QTechCommunity/RandomThingz/master/update.json";
    private static final QFMUpdater INSTANCE = new QFMUpdater();

    @SuppressWarnings({"unused", "SameParameterValue"})
    private static URL getUrl(String s) {
        try {
            return new URL(UPDATE_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private QFMUpdater() {
        super(getUrl(UPDATE_URL), RandomThingz.getInstance());
    }

    static QFMUpdater getInstance() {
        return INSTANCE;
    }

    @Override
    public RtVersion parseVersion(String version) {
        return new RtVersion(version);
    }

    @Override
    public RtVersion getCurrentModVersion() {
        return RandomThingz.RT_VERSION;
    }
}
