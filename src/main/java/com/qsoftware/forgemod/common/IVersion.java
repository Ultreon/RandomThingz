package com.qsoftware.forgemod.common;

public interface IVersion extends Comparable<IVersion> {
    boolean isStable();
    String toString();
    String toLocalizedString();
}
