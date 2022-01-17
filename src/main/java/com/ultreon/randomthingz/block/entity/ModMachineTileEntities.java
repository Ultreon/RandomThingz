package com.ultreon.randomthingz.block.entity;

import com.ultreon.modlib.embedded.silentlib.block.IBlockProvider;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block._common.MachineType;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.batterybox.BatteryBoxBlockEntity;
import com.ultreon.randomthingz.block.machines.compressor.CompressorBlockEntity;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackBlock;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackTileEntity;
import com.ultreon.randomthingz.block.machines.electricfurnace.ElectricFurnaceBlockEntity;
import com.ultreon.randomthingz.block.machines.generator.coal.CoalGeneratorBlockEntity;
import com.ultreon.randomthingz.block.machines.generator.diesel.DieselGeneratorBlockEntity;
import com.ultreon.randomthingz.block.machines.generator.lava.LavaGeneratorBlockEntity;
import com.ultreon.randomthingz.block.machines.infuser.InfuserBlockEntity;
import com.ultreon.randomthingz.block.machines.mixer.MixerBlockEntity;
import com.ultreon.randomthingz.block.machines.pipe.PipeTileEntity;
import com.ultreon.randomthingz.block.machines.pump.PumpBlockEntity;
import com.ultreon.randomthingz.block.machines.quarry.QuarryBlockEntity;
import com.ultreon.randomthingz.block.machines.refinery.RefineryBlockEntity;
import com.ultreon.randomthingz.block.machines.solidifier.SolidifierBlockEntity;
import com.ultreon.randomthingz.block.machines.wire.WireTileEntity;
import com.ultreon.randomthingz.registration.Registration;
import com.ultreon.randomthingz.util.ExceptionUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModMachineTileEntities {
    public static BlockEntityType<BatteryBoxBlockEntity> batteryBox;
    public static BlockEntityType<CoalGeneratorBlockEntity> coalGenerator;
    public static BlockEntityType<CompressorBlockEntity> compressor;
    public static BlockEntityType<DieselGeneratorBlockEntity> dieselGenerator;
    public static BlockEntityType<DryingRackTileEntity> dryingRack;
    public static BlockEntityType<ElectricFurnaceBlockEntity> electricFurnace;
    public static BlockEntityType<InfuserBlockEntity> infuser;
    public static BlockEntityType<LavaGeneratorBlockEntity> lavaGenerator;
    public static BlockEntityType<MixerBlockEntity> mixer;
    public static BlockEntityType<PipeTileEntity> pipe;
    public static BlockEntityType<PumpBlockEntity> pump;
    public static BlockEntityType<QuarryBlockEntity> quarry;
    public static BlockEntityType<RefineryBlockEntity> refinery;
    public static BlockEntityType<SolidifierBlockEntity> solidifier;
    public static BlockEntityType<WireTileEntity> wire;

    private ModMachineTileEntities() {
        throw ExceptionUtil.utilityConstructor();
    }

    /**
     * Register all tile entities of this mod.
     *
     * @param event registry event.
     */
    public static void registerAll(RegistryEvent.Register<BlockEntityType<?>> event) {
        register("basic_alloy_smelter", MachineType.ALLOY_SMELTER.getBasicTileEntityType());
        register("alloy_smelter", MachineType.ALLOY_SMELTER.getStandardTileEntityType());
        register("basic_arcane_escalator", MachineType.ARCANE_ESCALATOR.getBasicTileEntityType());
        register("arcane_escalator", MachineType.ARCANE_ESCALATOR.getStandardTileEntityType());
        register("basic_crusher", MachineType.CRUSHER.getBasicTileEntityType());
        register("crusher", MachineType.CRUSHER.getStandardTileEntityType());
        batteryBox = register("battery_box", BatteryBoxBlockEntity::new, ModBlocks.BATTERY_BOX);
        coalGenerator = register("coal_generator", CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR);
        compressor = register("compressor", CompressorBlockEntity::new, ModBlocks.COMPRESSOR);
        dieselGenerator = register("diesel_generator", DieselGeneratorBlockEntity::new, ModBlocks.DIESEL_GENERATOR);
        dryingRack = register("drying_rack", DryingRackTileEntity::new, Registration.getBlocks(DryingRackBlock.class).toArray(new Block[0]));
        electricFurnace = register("electric_furnace", ElectricFurnaceBlockEntity::new, ModBlocks.ELECTRIC_FURNACE);
        lavaGenerator = register("lava_generator", LavaGeneratorBlockEntity::new, ModBlocks.LAVA_GENERATOR);
        mixer = register("mixer", MixerBlockEntity::new, ModBlocks.MIXER);
        infuser = register("infuser", InfuserBlockEntity::new, ModBlocks.INFUSER);
        pipe = register("pipe", PipeTileEntity::new, ModBlocks.PIPE);
        pump = register("pump", PumpBlockEntity::new, ModBlocks.PUMP);
        quarry = register("quarry", QuarryBlockEntity::new, ModBlocks.QUARRY);
        refinery = register("refinery", RefineryBlockEntity::new, ModBlocks.REFINERY);
        solidifier = register("solidifier", () -> new SolidifierBlockEntity(pos, state), ModBlocks.SOLIDIFIER);
        wire = register("wire", WireTileEntity::new, ModBlocks.WIRE);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntitySupplier<T> tileFactory, IBlockProvider block) {
        return register(name, tileFactory, block.asBlock());
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntitySupplier<T> tileFactory, Block... blocks) {
        BlockEntityType<T> type = BlockEntityType.Builder.of(tileFactory, blocks).build(null);
        return register(name, type);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        if (type.getRegistryName() == null) {
            type.setRegistryName(RandomThingz.rl(name));
        }
        ForgeRegistries.BLOCK_ENTITIES.register(type);
        return type;
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers(FMLClientSetupEvent event) {
//        ClientRegistry.bindTileEntityRenderer(dryingRack, DryingRackTileEntityRenderer::new);
    }

    public static void register() {

    }
}
