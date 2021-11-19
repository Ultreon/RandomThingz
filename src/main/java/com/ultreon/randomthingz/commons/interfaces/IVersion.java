package com.ultreon.randomthingz.commons.interfaces;

public interface IVersion extends Comparable<IVersion> {
    boolean isStable();

    String toString();

    String toLocalizedString();
}
