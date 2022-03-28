package com.ultreon.randomthingz.init;

import com.ultreon.randomthingz.effect.BleedingEffect;
import com.ultreon.randomthingz.effect.CurseEffect;
import com.ultreon.randomthingz.effect.RadiationEffect;
import com.ultreon.randomthingz.registration.Registration;
import lombok.experimental.UtilityClass;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("unused")
@UtilityClass
public final class ModEffects {

    ///////////////////
    //     Racks     //
    ///////////////////
    public static final RegistryObject<CurseEffect> CURSE = register("curse", CurseEffect::new);
    public static final RegistryObject<RadiationEffect> RADIATION = register("radiation", RadiationEffect::new);
    public static final RegistryObject<BleedingEffect> BLEEDING = register("bleeding", BleedingEffect::new);

    //////////////////////////////
    //     Utility methods     //
    //////////////////////////////
    private static <T extends MobEffect> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return Registration.POTIONS.register(name, supplier);
    }

    public static void register() {

    }
}
