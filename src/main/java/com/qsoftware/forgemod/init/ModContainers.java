package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.objects.block.alloysmelter.AlloySmelterScreen;
import com.qsoftware.forgemod.objects.block.batterybox.BatteryBoxContainer;
import com.qsoftware.forgemod.objects.block.batterybox.BatteryBoxScreen;
import com.qsoftware.forgemod.objects.block.compressor.CompressorContainer;
import com.qsoftware.forgemod.objects.block.compressor.CompressorScreen;
import com.qsoftware.forgemod.objects.block.crusher.CrusherScreen;
import com.qsoftware.forgemod.objects.block.electricfurnace.ElectricFurnaceContainer;
import com.qsoftware.forgemod.objects.block.electricfurnace.ElectricFurnaceScreen;
import com.qsoftware.forgemod.objects.block.generator.coal.CoalGeneratorContainer;
import com.qsoftware.forgemod.objects.block.generator.coal.CoalGeneratorScreen;
import com.qsoftware.forgemod.objects.block.generator.diesel.DieselGeneratorContainer;
import com.qsoftware.forgemod.objects.block.generator.diesel.DieselGeneratorScreen;
import com.qsoftware.forgemod.objects.block.generator.lava.LavaGeneratorContainer;
import com.qsoftware.forgemod.objects.block.generator.lava.LavaGeneratorScreen;
import com.qsoftware.forgemod.objects.block.infuser.InfuserContainer;
import com.qsoftware.forgemod.objects.block.infuser.InfuserScreen;
import com.qsoftware.forgemod.objects.block.mixer.MixerContainer;
import com.qsoftware.forgemod.objects.block.mixer.MixerScreen;
import com.qsoftware.forgemod.objects.block.pump.PumpContainer;
import com.qsoftware.forgemod.objects.block.pump.PumpScreen;
import com.qsoftware.forgemod.objects.block.quarry.QuarryContainer;
import com.qsoftware.forgemod.objects.block.quarry.QuarryScreen;
import com.qsoftware.forgemod.objects.block.refinery.RefineryContainer;
import com.qsoftware.forgemod.objects.block.refinery.RefineryScreen;
import com.qsoftware.forgemod.objects.block.solidifier.SolidifierContainer;
import com.qsoftware.forgemod.objects.block.solidifier.SolidifierScreen;
import com.qsoftware.forgemod.util.MachineTier;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModContainers {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, QForgeMod.MOD_ID);

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

    private ModContainers() {
    }

    public static void registerAll(RegistryEvent.Register<ContainerType<?>> event) {
        register("basic_alloy_smelter", MachineType.ALLOY_SMELTER.getContainerType(MachineTier.BASIC));
        register("alloy_smelter", MachineType.ALLOY_SMELTER.getContainerType(MachineTier.STANDARD));
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
        containerType.setRegistryName(QForgeMod.getId(name));
        ForgeRegistries.CONTAINERS.register(containerType);
        return containerType;
    }
}
