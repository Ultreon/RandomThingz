package com.qsoftware.forgemod.modules.updates;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.QVersion;

import java.net.MalformedURLException;
import java.net.URL;

public class QFMUpdater extends AbstractUpdater<QVersion> {
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
    public QVersion parseVersion(String version) {
        return new QVersion(version);
    }

    @Override
    public QVersion getCurrentModVersion() {
        return QForgeMod.VERSION;
    }
}
