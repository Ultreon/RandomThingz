package com.ultreon.randomthingz.common.internal;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.interfaces.Version;
import lombok.Getter;
import lombok.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RtVersion implements Version {
    @Getter
    private final int version;
    @Getter
    private final int release;
    @Getter
    private final int buildNumber;
    @Getter
    private final Stage stage;
    @Getter
    private final int stageRelease;
    public static final RtVersion EMPTY = new RtVersion(0, 0, 0, Stage.ALPHA, 0);

    /**
     * @param s the version to parse.
     * @throws IllegalArgumentException when an invalid version has given.
     */
    public RtVersion(String s) {
        // String to be scanned to find the pattern.
        String pattern = "([0-9]*)\\.([0-9]*)\\.([0-9]*)-(a|alpha|b|beta|rc|pre|r|release)([0-9]*)"; // 1.0-alpha4 // 5.4-release-7

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(s);
        if (m.find()) {
            version = Integer.parseInt(m.group(1));
            release = Integer.parseInt(m.group(2));
            buildNumber = Integer.parseInt(m.group(3));

            switch (m.group(4)) {
                case "alpha", "a" -> stage = Stage.ALPHA;
                case "beta", "b" -> stage = Stage.BETA;
                case "pre", "rc" -> stage = Stage.PRE;
                case "release", "r" -> stage = Stage.RELEASE;
                default -> throw new InternalError("Regex has invalid output.");
            }

            stageRelease = Integer.parseInt(m.group(5));
        } else {
            throw new IllegalArgumentException("Invalid version,");
        }
    }

    public RtVersion(int version, int release, int buildNumber, String stage, int stageRelease) {
        this.version = version;
        this.release = release;
        switch (stage) {
            case "alpha", "a" -> this.stage = Stage.ALPHA;
            case "beta", "b" -> this.stage = Stage.BETA;
            case "pre", "rc" -> this.stage = Stage.PRE;
            case "release", "r" -> this.stage = Stage.RELEASE;
            default -> throw new InternalError("Invalid RandomThingz version stage!");
        }

        this.stageRelease = stageRelease;
        this.buildNumber = buildNumber;
    }

    public RtVersion(int version, int release, int buildNumber, Stage stage, int stageRelease) {
        this.version = version;
        this.release = release;
        this.stage = stage;
        this.stageRelease = stageRelease;
        this.buildNumber = buildNumber;
    }

    @Override
    public boolean isStable() {
        return stage == Stage.RELEASE && !RandomThingz.RT_ARGS.getFlags().isDevTest();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(version);
        sb.append('.');
        sb.append(release);
        sb.append('.');
        sb.append(buildNumber);
        sb.append('-');
        sb.append(stage.name().toLowerCase());
        sb.append(stageRelease);
        if (RandomThingz.RT_ARGS.getFlags().isDevTest()) {
            sb.append("-DEVTEST");
        }
        return sb.toString();
    }

    public String toLocalizedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(version);
        sb.append('.');
        sb.append(release);
        sb.append(" (Build ");
        sb.append(buildNumber);
        sb.append(") ");

        switch (stage) {
            case ALPHA:
                sb.append("Alpha");
            case BETA:
                sb.append("Beta");
            case PRE:
                sb.append("Pre");
            case RELEASE:
                sb.append("Release");
            default:
                sb.append("UNKNOWN");
        }

        sb.append(' ');
        sb.append(stageRelease);
        if (RandomThingz.RT_ARGS.getFlags().isDevTest()) {
            sb.append(" Dev-Test");
        }

        return sb.toString();
    }

    @Override
    public int compareTo(@NonNull Version o) {
        if (!(o instanceof RtVersion version)) {
            throw new IllegalArgumentException("Can't compare other than QVersion");
        }

        return Integer.compare(this.buildNumber, version.buildNumber);

//        int cmp = Integer.compare(this.version, version.version);
//        if (cmp == 0) {
//            int cmp1 = Integer.compare(this.release, version.release);
//            if (cmp1 == 0) {
//                int cmp2 = this.stage.compareTo(version.stage);
//                if (cmp2 == 0) {
//                    return Integer.compare(this.stageRelease, version.stageRelease);
//                } else {
//                    return cmp2;
//                }
//            } else {
//                return cmp1;
//            }
//        } else {
//            return cmp;
//        }
    }

    public enum Stage {
        ALPHA,
        BETA,
        PRE,
        RELEASE
    }
}
