package com.ultreon.randomthingz.item.tool;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.item.tool.trait.*;
import lombok.experimental.UtilityClass;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@UtilityClass
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class ModTraits {
    public static final DeferredRegister<AbstractTrait> REGISTRY = DeferredRegister.create(AbstractTrait.class, RandomThingz.MOD_ID);

    static {
        REGISTRY.makeRegistry("trait", () -> new RegistryBuilder<AbstractTrait>().disableOverrides());
    }


    public static final RegistryObject<RadioactiveTrait> RADIOACTIVE = register("radioactive", RadioactiveTrait::new);
    public static final RegistryObject<InfinityTrait> INFINITY = register("infinity", InfinityTrait::new);
    public static final RegistryObject<WitherTrait> WITHER = register("wither", WitherTrait::new);
    public static final RegistryObject<PoisonTrait> POISON = register("poison", PoisonTrait::new);
    public static final RegistryObject<EnderTrait> ENDER = register("ender", EnderTrait::new);
    public static final RegistryObject<BlazeTrait> BLAZE = register("blaze", BlazeTrait::new);
    public static final RegistryObject<HolyTrait> HOLY = register("holy", HolyTrait::new);
    public static final RegistryObject<SharpTrait> SHARP = register("sharp", SharpTrait::new);
    public static final RegistryObject<BlastResistantTrait> BLAST_RESISTANT = register("blast_resistant", BlastResistantTrait::new);
    public static final RegistryObject<FireResistantTrait> FIRE_RESISTANT = register("fire_resistant", FireResistantTrait::new);
    public static final RegistryObject<MagicResistantTrait> MAGIC_RESISTANT = register("magic_resistant", MagicResistantTrait::new);
    public static final RegistryObject<ProjectileResistantTrait> PROJECTILE_RESISTANT = register("projectile_resistant", ProjectileResistantTrait::new);
    public static final RegistryObject<KnockbackTrait> KNOCKBACK = register("knockback", KnockbackTrait::new);
    public static final RegistryObject<SlimeyTrait> SLIMEY = register("slimey", SlimeyTrait::new);

    public static <T extends AbstractTrait> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return REGISTRY.register(name, supplier);
    }

    public static void register() {

    }
}
