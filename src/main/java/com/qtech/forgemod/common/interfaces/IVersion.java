package com.qtech.forgemod.common.interfaces;

public interface IVersion extends Comparable<IVersion> {
    boolean isStable();
    String toString();
    String toLocalizedString();
}
