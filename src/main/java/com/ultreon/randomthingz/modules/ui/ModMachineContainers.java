package com.ultreon.randomthingz.modules.ui;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.common.MachineType;
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
import com.ultreon.randomthingz.commons.enums.MachineTier;
import lombok.experimental.UtilityClass;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@UtilityClass
public final class ModMachineContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, RandomThingz.MOD_ID);

    public static ContainerType<BatteryBoxContainer> batteryBox;
    public static ContainerType<CoalGeneratorContainer> coalGenerator;
    public static ContainerType<CompressorContainer> compressor;
    public static ContainerType<DieselGeneratorContainer> dieselGenerator;
    public static ContainerType<ElectricFurnaceContainer> electricFurnace;
    public static ContainerType<InfuserContainer> infuser;
    public static ContainerType<LavaGeneratorContainer> lavaGenerator;
    public static ContainerType<MixerContainer> mixer;
    public static ContainerType<PumpContainer> pump;
    public static ContainerType<QuarryContainer> quarry;
    public static ContainerType<RefineryContainer> refinery;
    public static ContainerType<SolidifierContainer> solidifier;

    public static void registerAll(RegistryEvent.Register<ContainerType<?>> event) {
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
        ScreenManager.registerFactory(MachineType.ALLOY_SMELTER.getContainerType(MachineTier.BASIC), AlloySmelterScreen::new);
        ScreenManager.registerFactory(MachineType.ALLOY_SMELTER.getContainerType(MachineTier.STANDARD), AlloySmelterScreen::new);
        ScreenManager.registerFactory(MachineType.ARCANE_ESCALATOR.getContainerType(MachineTier.BASIC), ArcaneEscalatorScreen::new);
        ScreenManager.registerFactory(MachineType.ARCANE_ESCALATOR.getContainerType(MachineTier.STANDARD), ArcaneEscalatorScreen::new);
        ScreenManager.registerFactory(batteryBox, BatteryBoxScreen::new);
        ScreenManager.registerFactory(coalGenerator, CoalGeneratorScreen::new);
        ScreenManager.registerFactory(compressor, CompressorScreen::new);
        ScreenManager.registerFactory(MachineType.CRUSHER.getContainerType(MachineTier.BASIC), CrusherScreen::new);
        ScreenManager.registerFactory(MachineType.CRUSHER.getContainerType(MachineTier.STANDARD), CrusherScreen::new);
        ScreenManager.registerFactory(dieselGenerator, DieselGeneratorScreen::new);
        ScreenManager.registerFactory(electricFurnace, ElectricFurnaceScreen::new);
        ScreenManager.registerFactory(infuser, InfuserScreen::new);
        ScreenManager.registerFactory(lavaGenerator, LavaGeneratorScreen::new);
        ScreenManager.registerFactory(mixer, MixerScreen::new);
        ScreenManager.registerFactory(pump, PumpScreen::new);
        ScreenManager.registerFactory(quarry, QuarryScreen::new);
        ScreenManager.registerFactory(refinery, RefineryScreen::new);
        ScreenManager.registerFactory(solidifier, SolidifierScreen::new);
    }

    private static <C extends Container> ContainerType<C> register(String name, ContainerType.IFactory<C> containerFactory) {
        ContainerType<C> type = new ContainerType<>(containerFactory);
        return register(name, type);
    }

    private static <C extends Container> ContainerType<C> register(String name, ContainerType<C> containerType) {
        containerType.setRegistryName(RandomThingz.rl(name));
        ForgeRegistries.CONTAINERS.register(containerType);
        return containerType;
    }

    public static void register() {

    }
}
