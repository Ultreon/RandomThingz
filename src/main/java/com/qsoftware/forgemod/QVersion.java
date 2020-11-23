package com.qsoftware.forgemod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QVersion {
    private final int version;
    private final int subversion;
    private final TYPE type;
    private final int release;

    public QVersion(String s) {
        // String to be scanned to find the pattern.
        String pattern = "([0-9]*)\\.([0-9]*)-(alpha|beta|release)([0-9]*)";

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
                sb.append("Alpha");
            case BETA:
                sb.append("Beta");
            case PRE:
                sb.append("Pre Release");
            case RELEASE:
                sb.append("Release");
            default:
                sb.append("UNKNOWN");
        }

        sb.append(' ');
        sb.append(release);

        return sb.toString();
    }

    public int getRelease() {
        return release;
    }

    public enum TYPE {
        ALPHA,
        BETA,
        PRE,
        RELEASE
    }
}
