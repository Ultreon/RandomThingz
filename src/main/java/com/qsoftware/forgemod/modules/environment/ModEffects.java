package com.qsoftware.forgemod.modules.environment;

import com.qsoftware.forgemod.effects.CurseEffect;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.util.ExceptionUtil;
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

    //////////////////////////////
    //     Utility methods     //
    //////////////////////////////
    private static <T extends Effect> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return Registration.POTIONS.register(name, supplier);
    }

    public static void register() {

    }
}
