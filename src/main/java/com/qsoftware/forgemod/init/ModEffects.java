package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.effects.CurseEffect;
import com.qsoftware.forgemod.util.ExceptionUtil;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ModEffects {

    ///////////////////
    //     Racks     //
    ///////////////////
    public static final RegistryObject<CurseEffect> CURSE = register("curse", CurseEffect::new);

    //////////////////////////////
    //     Utility methods     //
    //////////////////////////////
    private ModEffects() {
        throw ExceptionUtil.utilityConstructor();
    }

    private static <T extends Effect> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return Registration.POTIONS.register(name, supplier);
    }

    public static void register() {

    }
}
