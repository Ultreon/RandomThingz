package com.qsoftware.forgemod.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@AllArgsConstructor()
@Getter
public class DistCompatibility {
    private final boolean client;
    private final boolean server;
}
