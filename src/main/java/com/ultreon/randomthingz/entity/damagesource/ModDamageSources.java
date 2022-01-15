package com.ultreon.randomthingz.entity.damagesource;

import lombok.experimental.UtilityClass;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

@UtilityClass
public class ModDamageSources {
    public static final DamageSource RADIATION = new DamageSource("qtech_rt_radiation");
    public static final DamageSource BLEEDING = new DamageSource("qtech_rt_bleeding");

    public static final Function<Entity, DamageSource> INFINITY = DamageSourceInfinitySword::new;
}
