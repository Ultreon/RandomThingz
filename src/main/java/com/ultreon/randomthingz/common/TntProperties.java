package com.ultreon.randomthingz.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.minecraft.world.level.Explosion;

/**
 * TNT-Properties.
 *
 * @author Qboi123
 */
@Builder(builderClassName = "Builder")
@AllArgsConstructor
@Getter
public class TntProperties {
    private final float radius;
    private final Explosion.BlockInteraction mode;
    private final int fuse;
    private final boolean causesFire;
}
