package com.qtech.forgemod.modules.environment;

import com.qtech.forgemod.effects.CurseEffect;
import com.qtech.forgemod.init.Registration;
import com.qtech.forgemod.modules.environment.effects.RadiationEffect;
import com.qtech.forgemod.util.ExceptionUtil;
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

    //////////////////////////////
    //     Utility methods     //
    //////////////////////////////
    private static <T extends Effect> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return Registration.POTIONS.register(name, supplier);
    }

    public static void register() {

    }
}
