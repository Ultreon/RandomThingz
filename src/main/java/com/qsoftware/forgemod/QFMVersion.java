package com.qsoftware.forgemod;

import com.qsoftware.forgemod.common.interfaces.IVersion;
import lombok.Getter;
import net.minecraft.client.resources.I18n;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QFMVersion implements IVersion {
    @Getter private final int version;
    @Getter private final int release;
    @Getter private final int buildNumber;
    @Getter private final Stage stage;
    @Getter private final int stageRelease;
    public static final QFMVersion EMPTY = new QFMVersion(0, 0, 0, Stage.ALPHA, 0);

    /**
     * 
     * @throws IllegalArgumentException when an invalid version has given.
     * @param s the version to parse.
     */
    public QFMVersion(String s) {
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
                case "alpha":
                case "a":
                    stage = Stage.ALPHA;
                    break;
                case "beta":
                case "b":
                    stage = Stage.BETA;
                    break;
                case "pre":
                case "rc":
                    stage = Stage.PRE;
                    break;
                case "release":
                case "r":
                    stage = Stage.RELEASE;
                    break;
                default:
                    throw new InternalError("Regex has invalid output.");
            }

            stageRelease = Integer.parseInt(m.group(4));
        } else {
            throw new IllegalArgumentException("Invalid version,");
        }
    }

    public QFMVersion(int version, int release, int buildNumber, String stage, int stageRelease) {
        this.version = version;
        this.release = release;
        switch (stage) {
            case "alpha":
            case "a":
                this.stage = Stage.ALPHA;
                break;
            case "beta":
            case "b":
                this.stage = Stage.BETA;
                break;
            case "pre":
            case "rc":
                this.stage = Stage.PRE;
                break;
            case "release":
            case "r":
                this.stage = Stage.RELEASE;
                break;
            default:
                throw new InternalError("Invalid QForgeMod version stage!");
        }

        this.stageRelease = stageRelease;
        this.buildNumber = buildNumber;
    }

    public QFMVersion(int version, int release, int buildNumber, Stage stage, int stageRelease) {
        this.version = version;
        this.release = release;
        this.stage = stage;
        this.stageRelease = stageRelease;
        this.buildNumber = buildNumber;
    }

    @Override
    public boolean isStable() {
        return stage == Stage.RELEASE && !QForgeMod.getModArgs().getFlags().isDevTest();
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
        if (QForgeMod.getModArgs().getFlags().isDevTest()) {
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
        sb.append(stageRelease);
        if (QForgeMod.getModArgs().getFlags().isDevTest()) {
            sb.append(" Dev-Test");
        }

        return sb.toString();
    }

    @Override
    public int compareTo(@NotNull IVersion o) {
        if (!(o instanceof QFMVersion)) {
            throw new IllegalArgumentException("Can't compare other than QVersion");
        }

        QFMVersion version = (QFMVersion) o;

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
