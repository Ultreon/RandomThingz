package com.ultreon.randomthingz.common.updates;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.interfaces.IVersion;
import com.ultreon.randomthingz.common.internal.RtVersion;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An abstract updater used for checking for updates.
 * Other modders can use this to create their own updaters.
 * They need to create an implementation of {@linkplain IVersion} to make it work with their own version systems -
 * Or use {@linkplain RtVersion} if modders want to use the QSoftware / QTech version system.
 *
 * @param <T> an implementation of {@linkplain IVersion}.
 */
@SuppressWarnings("unused")
public abstract class AbstractUpdater<T extends IVersion> {
    private static final List<AbstractUpdater<?>> INSTANCES = new ArrayList<>();
    private static final Map<String, AbstractUpdater<?>> MOD_UPDATER_MAP = new HashMap<>();
    public static boolean DEBUG = true;
    private final URL updateUrl;
    private final ModContainer modContainer;
    private T latestVersion = null;
    private URL releaseUrl;
    private Dependencies dependencies = new Dependencies();
    private Release release;

    /**
     * Get a mod container from an instance of an {@linkplain Mod @Mod} annotated class.
     *
     * @param obj an instance of a {@linkplain Mod @Mod} annotated class
     * @return the mod container got from the Object.
     */
    private static ModContainer getModFromObject(Object obj) {
        ModList modList = ModList.get();
        return modList
                .getModContainerByObject(obj)
                .orElseThrow(() -> new IllegalArgumentException("Object is not registered as Mod."));
    }

    public AbstractUpdater(URL url, ModInfo info) {
        this(url, info.getModId());
    }

    public AbstractUpdater(URL url, ModContainer container) {
        this(url, container.getModId());
    }

    public AbstractUpdater(URL url, Object modObject) {
        this(url, getModFromObject(modObject));
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
        MOD_UPDATER_MAP.put(modId, this);
    }

    ///////////////
    // Instances //
    ///////////////

    /**
     * Get the updater that Random Thingz is using.
     *
     * @return the Random Thingz updater.
     */
    public static QFMUpdater getQFMUpdater() {
        return QFMUpdater.getInstance();
    }

    /**
     * Get all updater instances.
     *
     * @return all the updater instances in an array.
     */
    public static AbstractUpdater<?>[] getInstances() {
        return INSTANCES.toArray(new AbstractUpdater[0]);
    }

    /**
     * Get the updater associated with an mod-ID.
     *
     * @param modId the mod-ID.
     * @return the updater/
     */
    public static AbstractUpdater<?> getUpdater(String modId) {
        return MOD_UPDATER_MAP.get(modId);
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

    /**
     * Parse a version from a string.
     *
     * @param version an stringified version.
     * @return the parsed version.
     */
    public abstract T parseVersion(String version);

    /**
     * Get current mod version, of the mod associated with the updater.
     *
     * @return the current mod's version.
     */
    public abstract T getCurrentModVersion();

    /**
     * Get latest mod version.
     * Will return null if the updates wasn't checked before.
     *
     * @return the latest version of the mod associated with the updater.
     */
    @Nullable
    public T getLatestVersion() {
        return latestVersion;
    }

    ///////////////
    // Mod info. //
    ///////////////

    /**
     * Ge6 mod information, from the mod associated with the updater.
     *
     * @return the mod information.
     */
    public IModInfo getModInfo() {
        return modContainer.getModInfo();
    }

    /////////////////////////////////
    // Has update. / Is up to date //
    /////////////////////////////////

    /**
     * Get if there was an update available after checking.
     * Will return false, if the updates wasn't checked before.
     *
     * @return true if there's an update available, false otherwise.
     */
    public boolean hasUpdate() {
        return latestVersion != null && getCurrentModVersion().compareTo(latestVersion) < 0;
    }

    /**
     * @param version the version to check if it's up to date.
     * @return true if the given version is up to date, false otherwise.
     */
    public boolean isUpToDate(T version) {
        return version.compareTo(latestVersion) < 0;
    }

    /**
     * Check for updates.
     * Will always return an update information, holding information about "is there an update available" and which version is the latest.
     *
     * @return the update information.
     */
    @NotNull
    public UpdateInfo checkForUpdates() {
        try {
            // Get minecraft version.
            String id = Minecraft.getInstance().getMinecraftGame().getVersion().getId();

            // Open update url.
            InputStream inputStream = updateUrl.openStream();
            Reader targetReader = new InputStreamReader(inputStream);

            // Get update information.
            Gson gson = new Gson();
            try {
                // Get Minecraft versions.
                JsonObject mcVersions = gson.fromJson(targetReader, JsonObject.class).get("mc_versions").getAsJsonObject();
                if (DEBUG) {
                    RandomThingz.LOGGER.debug("===================================================");
                    RandomThingz.LOGGER.debug("Update Data:");
                    RandomThingz.LOGGER.debug("---------------------------------------------------");
                    RandomThingz.LOGGER.debug(mcVersions.toString());
                    RandomThingz.LOGGER.debug("===================================================");
                }

                // Get latest Mod version.
                JsonObject versionIndex = mcVersions.getAsJsonObject(id);
                if (versionIndex == null) {
                    return new UpdateInfo(UpdateStatus.INCOMPATIBLE, null);
                }

                JsonObject releaseIndex = versionIndex.getAsJsonObject(RandomThingz.RT_VERSION.isStable() ? "stable" : "unstable");
                JsonPrimitive latestJson = releaseIndex.getAsJsonPrimitive("version");

                // Get version download url.
                JsonPrimitive downloadJson = releaseIndex.getAsJsonPrimitive("download");
                if (releaseIndex.has("dependencies")) {
                    JsonObject dependenciesJson = releaseIndex.getAsJsonObject("dependencies");
                    this.dependencies = getDependencies(dependenciesJson);
                }
                T latestVersion = parseVersion(latestJson.getAsString());
                URL url = new URL(downloadJson.getAsString());

                // Assign values to fields.
                this.latestVersion = latestVersion;
                this.releaseUrl = url;

                this.release = new Release(this, modContainer.getModInfo().getDisplayName(), url, this.dependencies);

                // Check if up to date.
                if (getCurrentModVersion().compareTo(latestVersion) < 0) {
                    // Close reader and stream.
                    targetReader.close();
                    inputStream.close();

                    // Return information, there's an update available.
                    return new UpdateInfo(UpdateStatus.UPDATE_AVAILABLE, null);
                }

                // Close reader and stream.
                targetReader.close();
                inputStream.close();

                // Return information, it's up to date.
                return new UpdateInfo(UpdateStatus.UP_TO_DATE, null);
            } catch (IllegalStateException | NullPointerException | IOException | IllegalArgumentException e) {
                // There went something wrong.
                return new UpdateInfo(UpdateStatus.INCOMPATIBLE, e);
            }
        } catch (UnknownHostException e) {
            // The server / computer if offline.
            return new UpdateInfo(UpdateStatus.OFFLINE, e);
        } catch (IOException e) {
            // The server / computer if offline.
            return new UpdateInfo(UpdateStatus.OFFLINE, e);
        }
    }

    private Dependencies getDependencies(JsonObject dependenciesJson) throws MalformedURLException {
        Dependencies dependencies = new Dependencies();

        for (Map.Entry<String, JsonElement> entry : dependenciesJson.entrySet()) {
            String modId = entry.getKey();
            JsonElement dependencyJson = entry.getValue();

            if (dependencyJson instanceof JsonObject) {
                JsonObject dependencyObject = (JsonObject) dependencyJson;
                URL download = new URL(dependencyObject.getAsJsonPrimitive("download").getAsString());
                String name = dependencyObject.getAsJsonPrimitive("name").getAsString();
                if (dependencyObject.has("dependencies")) {
                    Dependencies subDependencies = getDependencies(dependencyObject.getAsJsonObject("dependencies"));
                    dependencies.add(new Dependency(modId, name, download, subDependencies));
                    continue;
                }
                dependencies.add(new Dependency(modId, name, download));
            }
        }

        dependencies.lock();
        return dependencies;
    }

    public Dependencies getDependencies() {
        return dependencies;
    }

    public Release getRelease() {
        return release;
    }

    /**
     * Update status.
     *
     * @author Qboi123
     */
    public enum UpdateStatus {
        INCOMPATIBLE, OFFLINE, UPDATE_AVAILABLE, UP_TO_DATE
    }

    /**
     * Update information.
     *
     * @author Qboi123
     */
    public static class UpdateInfo {

        // Fields.
        private final UpdateStatus status;
        private final Throwable throwable;

        /**
         * Update information constructor.
         *
         * @param status    the update status.
         * @param throwable the throwable thrown when checking for updates.
         */
        public UpdateInfo(UpdateStatus status, Throwable throwable) {
            this.status = status;
            this.throwable = throwable;
            if (throwable != null && !(throwable instanceof IOException)) {
                throwable.printStackTrace();
            }
        }

        /**
         * Get the update status.
         *
         * @return the update status.
         */
        public UpdateStatus getStatus() {
            return status;
        }

        /**
         * Get the throwable thrown when checking for updates.
         *
         * @return the throwable thrown when checking for updates.
         */
        public Throwable getThrowable() {
            return throwable;
        }
    }
}
