package com.ultreon.randomthingz.common.interfaces;

public interface Version extends Comparable<Version> {
    boolean isStable();

    String toString();

    String toLocalizedString();
}
