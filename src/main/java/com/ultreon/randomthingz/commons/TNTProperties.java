package com.ultreon.randomthingz.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.minecraft.world.Explosion;

/**
 * TNT-Properties.
 *
 * @author Qboi123
 */
@Builder(builderClassName = "Builder")
@AllArgsConstructor
@Getter
public class TNTProperties {
    private final float radius;
    private final Explosion.Mode mode;
    private final int fuse;
    private final boolean causesFire;
}
