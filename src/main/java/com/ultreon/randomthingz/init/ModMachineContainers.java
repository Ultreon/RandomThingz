package com.ultreon.randomthingz.init;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.MachineType;
import com.ultreon.randomthingz.block.machines.alloysmelter.AlloySmelterScreen;
import com.ultreon.randomthingz.block.machines.arcaneescalator.ArcaneEscalatorScreen;
import com.ultreon.randomthingz.block.machines.batterybox.BatteryBoxContainer;
import com.ultreon.randomthingz.block.machines.batterybox.BatteryBoxScreen;
import com.ultreon.randomthingz.block.machines.compressor.CompressorContainer;
import com.ultreon.randomthingz.block.machines.compressor.CompressorScreen;
import com.ultreon.randomthingz.block.machines.crusher.CrusherScreen;
import com.ultreon.randomthingz.block.machines.electricfurnace.ElectricFurnaceContainer;
import com.ultreon.randomthingz.block.machines.electricfurnace.ElectricFurnaceScreen;
import com.ultreon.randomthingz.block.machines.generator.coal.CoalGeneratorContainer;
import com.ultreon.randomthingz.block.machines.generator.coal.CoalGeneratorScreen;
import com.ultreon.randomthingz.block.machines.generator.diesel.DieselGeneratorContainer;
import com.ultreon.randomthingz.block.machines.generator.diesel.DieselGeneratorScreen;
import com.ultreon.randomthingz.block.machines.generator.lava.LavaGeneratorContainer;
import com.ultreon.randomthingz.block.machines.generator.lava.LavaGeneratorScreen;
import com.ultreon.randomthingz.block.machines.infuser.InfuserContainer;
import com.ultreon.randomthingz.block.machines.infuser.InfuserScreen;
import com.ultreon.randomthingz.block.machines.mixer.MixerContainer;
import com.ultreon.randomthingz.block.machines.mixer.MixerScreen;
import com.ultreon.randomthingz.block.machines.pump.PumpContainer;
import com.ultreon.randomthingz.block.machines.pump.PumpScreen;
import com.ultreon.randomthingz.block.machines.quarry.QuarryContainer;
import com.ultreon.randomthingz.block.machines.quarry.QuarryScreen;
import com.ultreon.randomthingz.block.machines.refinery.RefineryContainer;
import com.ultreon.randomthingz.block.machines.refinery.RefineryScreen;
import com.ultreon.randomthingz.block.machines.solidifier.SolidifierContainer;
import com.ultreon.randomthingz.block.machines.solidifier.SolidifierScreen;
import com.ultreon.randomthingz.common.enums.MachineTier;
import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@UtilityClass
public final class ModMachineContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, RandomThingz.MOD_ID);

    public static MenuType<BatteryBoxContainer> batteryBox;
    public static MenuType<CoalGeneratorContainer> coalGenerator;
    public static MenuType<CompressorContainer> compressor;
    public static MenuType<DieselGeneratorContainer> dieselGenerator;
    public static MenuType<ElectricFurnaceContainer> electricFurnace;
    public static MenuType<InfuserContainer> infuser;
    public static MenuType<LavaGeneratorContainer> lavaGenerator;
    public static MenuType<MixerContainer> mixer;
    public static MenuType<PumpContainer> pump;
    public static MenuType<QuarryContainer> quarry;
    public static MenuType<RefineryContainer> refinery;
    public static MenuType<SolidifierContainer> solidifier;

    public static void registerAll(RegistryEvent.Register<MenuType<?>> event) {
        register("basic_alloy_smelter", MachineType.ALLOY_SMELTER.getContainerType(MachineTier.BASIC));
        register("alloy_smelter", MachineType.ALLOY_SMELTER.getContainerType(MachineTier.STANDARD));
        register("basic_arcane_escalator", MachineType.ARCANE_ESCALATOR.getContainerType(MachineTier.BASIC));
        register("arcane_escalator", MachineType.ARCANE_ESCALATOR.getContainerType(MachineTier.STANDARD));
        batteryBox = register("battery_box", BatteryBoxContainer::new);
        coalGenerator = register("coal_generator", CoalGeneratorContainer::new);
        compressor = register("compressor", CompressorContainer::new);
        register("basic_crusher", MachineType.CRUSHER.getContainerType(MachineTier.BASIC));
        register("crusher", MachineType.CRUSHER.getContainerType(MachineTier.STANDARD));
        dieselGenerator = register("diesel_generator", DieselGeneratorContainer::new);
        electricFurnace = register("electric_furnace", ElectricFurnaceContainer::new);
        infuser = register("infuser", InfuserContainer::new);
        lavaGenerator = register("lava_generator", LavaGeneratorContainer::new);
        mixer = register("mixer", MixerContainer::new);
        pump = register("pump", PumpContainer::new);
        quarry = register("quarry", QuarryContainer::new);
        refinery = register("refinery", RefineryContainer::new);
        solidifier = register("solidifier", SolidifierContainer::new);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event) {
        MenuScreens.register(MachineType.ALLOY_SMELTER.getContainerType(MachineTier.BASIC), AlloySmelterScreen::new);
        MenuScreens.register(MachineType.ALLOY_SMELTER.getContainerType(MachineTier.STANDARD), AlloySmelterScreen::new);
        MenuScreens.register(MachineType.ARCANE_ESCALATOR.getContainerType(MachineTier.BASIC), ArcaneEscalatorScreen::new);
        MenuScreens.register(MachineType.ARCANE_ESCALATOR.getContainerType(MachineTier.STANDARD), ArcaneEscalatorScreen::new);
        MenuScreens.register(batteryBox, BatteryBoxScreen::new);
        MenuScreens.register(coalGenerator, CoalGeneratorScreen::new);
        MenuScreens.register(compressor, CompressorScreen::new);
        MenuScreens.register(MachineType.CRUSHER.getContainerType(MachineTier.BASIC), CrusherScreen::new);
        MenuScreens.register(MachineType.CRUSHER.getContainerType(MachineTier.STANDARD), CrusherScreen::new);
        MenuScreens.register(dieselGenerator, DieselGeneratorScreen::new);
        MenuScreens.register(electricFurnace, ElectricFurnaceScreen::new);
        MenuScreens.register(infuser, InfuserScreen::new);
        MenuScreens.register(lavaGenerator, LavaGeneratorScreen::new);
        MenuScreens.register(mixer, MixerScreen::new);
        MenuScreens.register(pump, PumpScreen::new);
        MenuScreens.register(quarry, QuarryScreen::new);
        MenuScreens.register(refinery, RefineryScreen::new);
        MenuScreens.register(solidifier, SolidifierScreen::new);
    }

    private static <C extends AbstractContainerMenu> MenuType<C> register(String name, MenuType.MenuSupplier<C> containerFactory) {
        MenuType<C> type = new MenuType<>(containerFactory);
        return register(name, type);
    }

    private static <C extends AbstractContainerMenu> MenuType<C> register(String name, MenuType<C> containerType) {
        containerType.setRegistryName(RandomThingz.res(name));
        ForgeRegistries.CONTAINERS.register(containerType);
        return containerType;
    }

    public static void register() {

    }
}
