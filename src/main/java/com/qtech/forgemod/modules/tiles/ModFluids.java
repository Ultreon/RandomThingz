package com.qtech.forgemod.modules.tiles;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.modules.items.ModItems;
import lombok.experimental.UtilityClass;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@UtilityClass
@SuppressWarnings("SameParameterValue")
public final class ModFluids {
    public static FlowingFluid FLOWING_OIL;
    public static FlowingFluid OIL;
    public static FlowingFluid FLOWING_DIESEL;
    public static FlowingFluid DIESEL;
    public static Fluid ETHANE;
    public static Fluid POLYETHYLENE;

    @SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
    // Did this because IntelliJ thought it would be possible to do that, but then it will cause a crash.
    public static void registerFluids(RegistryEvent.Register<Fluid> event) {
        ForgeFlowingFluid.Properties oilProps = new ForgeFlowingFluid.Properties(
                () -> OIL, () -> FLOWING_OIL, FluidAttributes
                .builder(QForgeMod.rl("blocks/oil_still"), QForgeMod.rl("blocks/oil_flowing"))
                .density(5_000)
                .color(0xff00000)
                .viscosity(5000))
            .block(() -> ModBlocks.OIL.get())
            .bucket(() -> ModItems.OIL_BUCKET.get());
        FLOWING_OIL = register("flowing_oil", new ForgeFlowingFluid.Flowing(oilProps));
        OIL = register("oil", new ForgeFlowingFluid.Source(oilProps));

        ForgeFlowingFluid.Properties dieselProps = properties("diesel", () -> DIESEL, () -> FLOWING_DIESEL)
                .block(() -> ModBlocks.DIESEL.get())
                .bucket(() -> ModItems.DIESEL_BUCKET.get());
        FLOWING_DIESEL = register("flowing_diesel", new ForgeFlowingFluid.Flowing(dieselProps));
        DIESEL = register("diesel", new ForgeFlowingFluid.Source(dieselProps));

        ForgeFlowingFluid.Properties ethane = propertiesGas("ethane", () -> ETHANE)
                .bucket(() -> ModItems.ETHANE_BUCKET.get());
        ETHANE = register("ethane", new ForgeFlowingFluid.Source(ethane));
        ForgeFlowingFluid.Properties polyethylene = propertiesGas("polyethylene", () -> POLYETHYLENE)
                .bucket(() -> ModItems.POLYETHYLENE_BUCKET.get());
        POLYETHYLENE = register("polyethylene", new ForgeFlowingFluid.Source(polyethylene));
    }

    private static <T extends Fluid> T register(String name, T fluid) {
        ResourceLocation id = QForgeMod.rl(name);
        fluid.setRegistryName(id);
        ForgeRegistries.FLUIDS.register(fluid);
        return fluid;
    }

    private static ForgeFlowingFluid.Properties properties(String name, Supplier<Fluid> still, Supplier<Fluid> flowing) {
        String tex = "blocks/" + name;
        return new ForgeFlowingFluid.Properties(still, flowing, FluidAttributes.builder(QForgeMod.rl(tex + "_still"), QForgeMod.rl(tex + "_flowing")));
    }

    private static ForgeFlowingFluid.Properties propertiesGas(String name, Supplier<Fluid> still) {
        String tex = "blocks/" + name;
        //noinspection ReturnOfNull -- null-returning Supplier for flowing fluid
        return new ForgeFlowingFluid.Properties(still, () -> null, FluidAttributes.builder(QForgeMod.rl(tex), QForgeMod.rl(tex)).gaseous());
    }

    public static void register() {

    }
}
