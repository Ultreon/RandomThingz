package com.qtech.randomthingz.effect.common;

import com.qtech.randomthingz.effect.BleedingEffect;
import com.qtech.randomthingz.effect.CurseEffect;
import com.qtech.randomthingz.effect.RadiationEffect;
import com.qtech.randomthingz.registration.Registration;
import lombok.experimental.UtilityClass;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

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
    private static <T extends Effect> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return Registration.POTIONS.register(name, supplier);
    }

    public static void register() {

    }
}
