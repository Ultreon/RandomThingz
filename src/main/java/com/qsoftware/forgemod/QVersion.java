package com.qsoftware.forgemod;

import com.qsoftware.forgemod.common.IVersion;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QVersion implements IVersion {
    private final int version;
    private final int subversion;
    private final TYPE type;
    private final int release;
    public static final QVersion EMPTY = new QVersion(0, 0, TYPE.BETA, 0);

    /**
     * 
     * @throws IllegalArgumentException when an invalid version has given.
     * @param s the version to parse.
     */
    public QVersion(String s) {
        // String to be scanned to find the pattern.
        String pattern = "([0-9]*)\\.([0-9]*)-(alpha|beta|pre|release)([0-9]*)"; // 1.0-alpha4 // 5.4-release-7

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(s);
        if (m.find()) {
            version = Integer.parseInt(m.group(1));
            subversion = Integer.parseInt(m.group(2));

            switch (m.group(3)) {
                case "alpha":
                    type = TYPE.ALPHA;
                    break;
                case "beta":
                    type = TYPE.BETA;
                    break;
                case "pre":
                    type = TYPE.PRE;
                    break;
                case "release":
                    type = TYPE.RELEASE;
                    break;
                default:
                    throw new InternalError("Regex has invalid output.");
            }

            release = Integer.parseInt(m.group(4));
        } else {
            throw new IllegalArgumentException("Invalid version,");
        }
    }

    public QVersion(int version, int subversion, TYPE type, int release) {
        this.version = version;
        this.subversion = subversion;
        this.type = type;
        this.release = release;
    }

    public int getVersion() {
        return version;
    }

    public int getSubversion() {
        return subversion;
    }

    public TYPE getType() {
        return type;
    }

    @Override
    public boolean isStable() {
        return type == TYPE.RELEASE;
    }

    public String toString() {
        String sb = String.valueOf(version) +
                '.' +
                subversion +
                '-' +
                type.name().toLowerCase() +
                release;
        return sb;
    }

    public String toLocalizedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(version);
        sb.append('.');
        sb.append(subversion);
        sb.append(' ');

        switch (type) {
            case ALPHA:
                sb.append(I18n.format("misc.qforgemod.version.alpha"));
            case BETA:
                sb.append(I18n.format("misc.qforgemod.version.beta"));
            case PRE:
                sb.append(I18n.format("misc.qforgemod.version.pre"));
            case RELEASE:
                sb.append(I18n.format("misc.qforgemod.version.release"));
            default:
                sb.append(I18n.format("misc.qforgemod.unknown"));
        }

        sb.append(' ');
        sb.append(release);

        return sb.toString();
    }

    public int getRelease() {
        return release;
    }

    @Override
    public int compareTo(@NotNull IVersion o) {
        if (!(o instanceof QVersion)) {
            throw new IllegalArgumentException("Can't compare other than QVersion");
        }

        QVersion version = (QVersion) o;

        int cmp = Integer.compare(this.version, version.version);
        if (cmp == 0) {
            int cmp1 = Integer.compare(this.subversion, version.subversion);
            if (cmp1 == 0) {
                int cmp2 = this.type.compareTo(version.type);
                if (cmp2 == 0) {
                    return Integer.compare(this.release, version.release);
                } else {
                    return cmp2;
                }
            } else {
                return cmp1;
            }
        } else {
            return cmp;
        }
    }

    public enum TYPE {
        ALPHA,
        BETA,
        PRE,
        RELEASE
    }
}
