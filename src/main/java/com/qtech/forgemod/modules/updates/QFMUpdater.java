package com.qtech.forgemod.modules.updates;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.QFMVersion;

import java.net.MalformedURLException;
import java.net.URL;

public class QFMUpdater extends AbstractUpdater<QFMVersion> {
    private static final String UPDATE_URL = "https://raw.githubusercontent.com/Qboi123/QForgeMod/master/update.json";
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
        super(getUrl(UPDATE_URL), QForgeMod.getInstance());
    }

    static QFMUpdater getInstance() {
        return INSTANCE;
    }

    @Override
    public QFMVersion parseVersion(String version) {
        return new QFMVersion(version);
    }

    @Override
    public QFMVersion getCurrentModVersion() {
        return QForgeMod.QFM_VERSION;
    }
}
