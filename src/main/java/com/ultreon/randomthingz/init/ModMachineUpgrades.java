package com.ultreon.randomthingz.init;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.item.upgrade.MachineUpgrade;
import com.ultreon.randomthingz.util.Constants;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModMachineUpgrades {
    private static Supplier<IForgeRegistry<MachineUpgrade>> registry;
    private static final DeferredRegister<MachineUpgrade> REGISTER = DeferredRegister.create(MachineUpgrade.class, RandomThingz.MOD_ID);

    public static final MachineUpgrade PROCESSING_SPEED = register("processing_speed", () -> new MachineUpgrade(Constants.UPGRADE_PROCESSING_SPEED_AMOUNT, .5f));

    public static final MachineUpgrade OUTPUT_CHANCE = register("output_chance", () -> new MachineUpgrade(Constants.UPGRADE_SECONDARY_OUTPUT_AMOUNT, .25f));

    public static final MachineUpgrade ENERGY_CAPACITY = register("energy_capacity", () -> new MachineUpgrade(0, 0f, false));
    public static final MachineUpgrade ENERGY_EFFICIENCY = register("energy_efficiency", () -> new MachineUpgrade(Constants.UPGRADE_ENERGY_EFFICIENCY_AMOUNT, Constants.UPGRADE_ENERGY_EFFICIENCY_AMOUNT));
    public static final MachineUpgrade RANGE = register("range", () -> new MachineUpgrade(Constants.UPGRADE_RANGE_AMOUNT, .15f, false));

    private static <T extends MachineUpgrade> T register(String name, Supplier<T> supplier) {
        T t = supplier.get();
        REGISTER.register(name, () -> t);
        return t;
    }

    public static void register(IEventBus bus) {
        registry = REGISTER.makeRegistry("machine_upgrades", () -> new RegistryBuilder<MachineUpgrade>().setMaxID(1024).disableOverrides().onBake((owner, stage) -> {
            MachineUpgrade.register();
        }));
    }

    public static IForgeRegistry<MachineUpgrade> registry() {
        return registry.get();
    }
}
