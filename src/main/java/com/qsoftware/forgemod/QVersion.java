package com.qsoftware.forgemod;

import com.sun.org.apache.xalan.internal.xsltc.runtime.InternalRuntimeError;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

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

    public enum TYPE {
        ALPHA,
        BETA,
        RELEASE
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

    public int getRelease() {
        return release;
    }
}
