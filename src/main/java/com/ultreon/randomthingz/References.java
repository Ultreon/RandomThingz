package com.ultreon.randomthingz;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ultreon.randomthingz.common.internal.RtArgs;
import com.ultreon.randomthingz.common.internal.RtVersion;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

@Deprecated
public class References {
    @Deprecated
    public static final String MOD_ID = "randomthingz";
    @Deprecated
    public static final String MOD_VERSION;
    @Deprecated
    public static final RtVersion QFM_VERSION;
    @Deprecated
    public static final RtArgs QFM_ARGS;

    static {
        if (new File("/mnt/chromeos").exists()) {
            throw new UnsupportedOperationException("Tried to run RandomThingz on Chrome OS (Linux subsystem), this is unsupported.");
        }

        // Create gson instance.
        Gson gson = new Gson();

        // Get stream.
        InputStream qfmArgsStream = RandomThingz.class.getResourceAsStream("/META-INF/qfm_args.json");
        Objects.requireNonNull(qfmArgsStream, "Couldn't get RandomThingz Args file.");

        // Get data.
        InputStreamReader isr = new InputStreamReader(qfmArgsStream);
        JsonObject o = gson.fromJson(isr, JsonObject.class);

        QFM_ARGS = new RtArgs(o);
        MOD_VERSION = QFM_ARGS.getVersion().getName();
        QFM_VERSION = QFM_ARGS.getVersion().toVersionObject();
    }

}