package com.ultreon.randomthingz.block.entity;

import com.mojang.datafixers.DSL;
import com.ultreon.modlib.silentlib.registry.BlockRegistryObject;
import com.ultreon.randomthingz.block._common.MachineType;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.machines.batterybox.BatteryBoxBlockEntity;
import com.ultreon.randomthingz.block.machines.compressor.CompressorBlockEntity;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackBlock;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackTileEntity;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackBlockEntityRenderer;
import com.ultreon.randomthingz.block.machines.electricfurnace.ElectricFurnaceBlockEntity;
import com.ultreon.randomthingz.block.machines.generator.coal.CoalGeneratorBlockEntity;
import com.ultreon.randomthingz.block.machines.generator.diesel.DieselGeneratorBlockEntity;
import com.ultreon.randomthingz.block.machines.generator.lava.LavaGeneratorBlockEntity;
import com.ultreon.randomthingz.block.machines.infuser.InfuserBlockEntity;
import com.ultreon.randomthingz.block.machines.itempipe.ItemPipeTileEntity;
import com.ultreon.randomthingz.block.machines.mixer.MixerBlockEntity;
import com.ultreon.randomthingz.block.machines.pipe.PipeTileEntity;
import com.ultreon.randomthingz.block.machines.pump.PumpBlockEntity;
import com.ultreon.randomthingz.block.machines.quarry.QuarryBlockEntity;
import com.ultreon.randomthingz.block.machines.refinery.RefineryBlockEntity;
import com.ultreon.randomthingz.block.machines.solidifier.SolidifierBlockEntity;
import com.ultreon.randomthingz.block.machines.wire.WireBlockEntity;
import com.ultreon.randomthingz.registration.Registration;
import com.ultreon.randomthingz.util.ExceptionUtil;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class ModMachines {
    public static final RegistryObject<BlockEntityType<BatteryBoxBlockEntity>> BATTERY_BOX = register("battery_box", BatteryBoxBlockEntity::new, ModBlocks.BATTERY_BOX);
    public static final RegistryObject<BlockEntityType<CoalGeneratorBlockEntity>> COAL_GENERATOR = register("coal_generator", CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR);
    public static final RegistryObject<BlockEntityType<CompressorBlockEntity>> COMPRESSOR = register("compressor", CompressorBlockEntity::new, ModBlocks.COMPRESSOR);
    public static final RegistryObject<BlockEntityType<DieselGeneratorBlockEntity>> DIESEL_GENERATOR = register("diesel_generator", DieselGeneratorBlockEntity::new, ModBlocks.DIESEL_GENERATOR);
    public static final RegistryObject<BlockEntityType<DryingRackTileEntity>> DRYING_RACK = register("drying_rack", DryingRackTileEntity::new, () -> Registration.getBlocks(DryingRackBlock.class).toArray(new Block[0]));
    public static final RegistryObject<BlockEntityType<ElectricFurnaceBlockEntity>> ELECTRIC_FURNACE = register("electric_furnace", ElectricFurnaceBlockEntity::new, ModBlocks.ELECTRIC_FURNACE);
    public static final RegistryObject<BlockEntityType<InfuserBlockEntity>> INFUSER = register("infuser", InfuserBlockEntity::new, ModBlocks.INFUSER);
    public static final RegistryObject<BlockEntityType<LavaGeneratorBlockEntity>> LAVA_GENERATOR = register("lava_generator", LavaGeneratorBlockEntity::new, ModBlocks.LAVA_GENERATOR);
    public static final RegistryObject<BlockEntityType<MixerBlockEntity>> MIXER = register("mixer", MixerBlockEntity::new, ModBlocks.MIXER);
    public static final RegistryObject<BlockEntityType<ItemPipeTileEntity>> ITEM_PIPE = register("item_pipe", ItemPipeTileEntity::new, ModBlocks.ITEM_PIPE);
    public static final RegistryObject<BlockEntityType<PipeTileEntity>> PIPE = register("pipe", PipeTileEntity::new, ModBlocks.PIPE);
    public static final RegistryObject<BlockEntityType<PumpBlockEntity>> PUMP = register("pump", PumpBlockEntity::new, ModBlocks.PUMP);
    public static final RegistryObject<BlockEntityType<QuarryBlockEntity>> QUARRY = register("quarry", QuarryBlockEntity::new, ModBlocks.QUARRY);
    public static final RegistryObject<BlockEntityType<RefineryBlockEntity>> REFINERY = register("refinery", RefineryBlockEntity::new, ModBlocks.REFINERY);
    public static final RegistryObject<BlockEntityType<SolidifierBlockEntity>> SOLIDIFIER = register("solidifier", SolidifierBlockEntity::new, ModBlocks.SOLIDIFIER);
    public static final RegistryObject<BlockEntityType<WireBlockEntity>> WIRE = register("wire", WireBlockEntity::new, ModBlocks.WIRE);

    private ModMachines() {
        throw ExceptionUtil.utilityConstructor();
    }

    /**
     * Register all tile entities of this mod.
     *
     */
    public static void registerAll() {
        register("basic_alloy_smelter", MachineType.ALLOY_SMELTER::getBasicTileEntityType);
        register("alloy_smelter", MachineType.ALLOY_SMELTER::getStandardTileEntityType);
        register("basic_arcane_escalator", MachineType.ARCANE_ESCALATOR::getBasicTileEntityType);
        register("arcane_escalator", MachineType.ARCANE_ESCALATOR::getStandardTileEntityType);
        register("basic_crusher", MachineType.CRUSHER::getBasicTileEntityType);
        register("crusher", MachineType.CRUSHER::getStandardTileEntityType);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> tileFactory, BlockRegistryObject<?> block) {
        return register(name, tileFactory, () -> new Block[]{block.asBlock()});
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> tileFactory, Supplier<Block[]> blocks) {
        return register(name, () -> BlockEntityType.Builder.of(tileFactory, blocks.get()).build(DSL.remainderType()));
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> type) {
        return Registration.BLOCK_ENTITIES.register(name, type);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        BlockEntityRenderers.register(DRYING_RACK.get(), DryingRackBlockEntityRenderer::new);
    }

    public static void register() {

    }
}
