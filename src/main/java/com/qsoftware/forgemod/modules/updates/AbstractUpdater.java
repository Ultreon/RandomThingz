package com.qsoftware.forgemod.modules.updates;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.QVersion;
import com.qsoftware.forgemod.common.interfaces.IVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUpdater<T extends IVersion> {
    private static final String qfmUpdateUrl = "https://raw.githubusercontent.com/Qboi123/QForgeMod/master/update.json";
    private static final List<AbstractUpdater<?>> INSTANCES = new ArrayList<>();
    public static boolean DEBUG = true;
    private final URL updateUrl;
    private final ModContainer modContainer;
    private T latestVersion = null;
    private URL releaseUrl;

    private static final AbstractUpdater<QVersion> QFM_INSTANCE;

    static {
        URL updateUrl;
        try {
            updateUrl = new URL(qfmUpdateUrl);
            QFM_INSTANCE = new AbstractUpdater<QVersion>(updateUrl, QForgeMod.MOD_ID) {
                @Override
                public QVersion parseVersion(String version) {
                    return new QVersion(version);
                }

                @Override
                public QVersion getCurrentModVersion() {
                    return QForgeMod.VERSION;
                }
            };
        } catch (MalformedURLException e) {
            CrashReport crashreport = CrashReport.makeCrashReport(e, "Invalid update URL.");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("AbstractUpdater being initialized.");
            crashreportcategory.addDetail("Url", qfmUpdateUrl);
            throw new ReportedException(crashreport);
        }
    }

    public AbstractUpdater(URL url, String modId) {
        String modIdRepr = modId
                .replaceAll("\n", "\\n")
                .replaceAll("\r", "\\r")
                .replaceAll("\t", "\\t")
                .replaceAll("\"", "\\\"")
                .replaceAll("\\\\", "\\\\");
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Mod with id \"" + modIdRepr + "\" not found.");
        this.modContainer = ModList.get().getModContainerById(modId).orElseThrow(() -> illegalArgumentException);
        this.updateUrl = url;
        
        INSTANCES.add(this);
    }

    ///////////////
    // Instances //
    ///////////////
    public static AbstractUpdater<QVersion> getQFMInstance() {
        return QFM_INSTANCE;
    }

    public static AbstractUpdater<?>[] getInstances() {
        return INSTANCES.toArray(new AbstractUpdater[0]);
    }

    ///////////
    // Urls. //
    ///////////
    public URL getReleaseUrl() {
        return releaseUrl;
    }

    public URL getUpdateFileUrl() {
        return updateUrl;
    }

    ///////////////
    // Versions. //
    ///////////////
    public abstract T parseVersion(String version);

    public abstract T getCurrentModVersion();

    public T getLatestVersion() {
        return latestVersion;
    }

    ///////////////
    // Mod info. //
    ///////////////
    public IModInfo getModInfo() {
        return modContainer.getModInfo();
    }

    /////////////////////////////////
    // Has update. / Is up to date //
    /////////////////////////////////
    public boolean hasUpdate() {
        return latestVersion != null && getCurrentModVersion().compareTo(latestVersion) < 0;
    }

    public boolean isUpToDate(T version) {
        return version.compareTo(latestVersion) < 0;
    }

    @NotNull
    public UpdateInfo checkForUpdates() {
        try {
            String id = Minecraft.getInstance().getMinecraftGame().getVersion().getId();
            InputStream inputStream = updateUrl.openStream();
            Reader targetReader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            try {
                JsonObject mcVersions = gson.fromJson(targetReader, JsonObject.class).get("mc_versions").getAsJsonObject();
                if (DEBUG) {
                    QForgeMod.LOGGER.info("===================================================");
                    QForgeMod.LOGGER.info("Update Data:");
                    QForgeMod.LOGGER.info("---------------------------------------------------");
                    QForgeMod.LOGGER.info(mcVersions.toString());
                    QForgeMod.LOGGER.info("===================================================");
                }
                JsonObject versionIndex = mcVersions.get(id).getAsJsonObject();
                JsonObject releaseIndex = versionIndex.get(QForgeMod.VERSION.isStable() ? "stable" : "unstable").getAsJsonObject();
                JsonPrimitive latestJson = releaseIndex.get("version").getAsJsonPrimitive();
                JsonPrimitive downloadJson = releaseIndex.get("download").getAsJsonPrimitive();
                T latestVersion = parseVersion(latestJson.getAsString());
                URL url = new URL(downloadJson.getAsString());
                this.latestVersion = latestVersion;
                this.releaseUrl = url;
                if (getCurrentModVersion().compareTo(latestVersion) < 0) {
                    targetReader.close();
                    inputStream.close();
                    return new UpdateInfo(UpdateStatus.UPDATE_AVAILABLE, null);
                }
                targetReader.close();
                inputStream.close();
                return new UpdateInfo(UpdateStatus.UP_TO_DATE, null);
            } catch (IllegalStateException | NullPointerException | IOException | IllegalArgumentException e) {
                return new UpdateInfo(UpdateStatus.INCOMPATIBLE, e);
            }
        } catch (IOException e) {
            return new UpdateInfo(UpdateStatus.OFFLINE, e);
        }
    }

    public enum UpdateStatus {
        INCOMPATIBLE, OFFLINE, UPDATE_AVAILABLE, UP_TO_DATE;
    }
    public static class UpdateInfo {

        private final UpdateStatus status;
        private final Throwable throwable;

        public UpdateInfo(UpdateStatus status, Throwable throwable) {
            this.status = status;
            this.throwable = throwable;
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }

        public UpdateStatus getStatus() {
            return status;
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }
}
