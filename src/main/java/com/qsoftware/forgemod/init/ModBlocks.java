package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.objects.block.AbstractMachineBlock;
import com.qsoftware.forgemod.objects.block.MachineFrameBlock;
import com.qsoftware.forgemod.objects.block.alloysmelter.AlloySmelterBlock;
import com.qsoftware.forgemod.objects.block.batterybox.BatteryBoxBlock;
import com.qsoftware.forgemod.objects.block.compressor.CompressorBlock;
import com.qsoftware.forgemod.objects.block.crusher.CrusherBlock;
import com.qsoftware.forgemod.objects.block.dryingrack.DryingRackBlock;
import com.qsoftware.forgemod.objects.block.electricfurnace.ElectricFurnaceBlock;
import com.qsoftware.forgemod.objects.block.generator.coal.CoalGeneratorBlock;
import com.qsoftware.forgemod.objects.block.generator.diesel.DieselGeneratorBlock;
import com.qsoftware.forgemod.objects.block.generator.lava.LavaGeneratorBlock;
import com.qsoftware.forgemod.objects.block.infuser.InfuserBlock;
import com.qsoftware.forgemod.objects.block.mixer.MixerBlock;
import com.qsoftware.forgemod.objects.block.pipe.PipeBlock;
import com.qsoftware.forgemod.objects.block.pump.PumpBlock;
import com.qsoftware.forgemod.objects.block.quarry.QuarryBlock;
import com.qsoftware.forgemod.objects.block.refinery.RefineryBlock;
import com.qsoftware.forgemod.objects.block.solidifier.SolidifierBlock;
import com.qsoftware.forgemod.objects.block.wire.WireBlock;
import com.qsoftware.forgemod.objects.blocks.GamePcBlock;
import com.qsoftware.forgemod.objects.blocks.custom.CustomButtonBlock;
import com.qsoftware.forgemod.objects.blocks.customrender.CRDoorBlock;
import com.qsoftware.forgemod.objects.blocks.customrender.CRFlowerBlock;
import com.qsoftware.forgemod.objects.blocks.furniture.WoodenCrateBlock;
import com.qsoftware.forgemod.objects.blocks.overpowered.InfinityBlock;
import com.qsoftware.forgemod.objects.blocks.overpowered.InfinityOreBlock;
import com.qsoftware.forgemod.objects.blocks.trees.CherryTree;
import com.qsoftware.forgemod.objects.blocks.trees.EucalyptusTree;
import com.qsoftware.forgemod.objects.items.type.FaceableBlock;
import com.qsoftware.forgemod.registration.impl.ItemRegistryObject;
import com.qsoftware.forgemod.util.MachineTier;
import com.qsoftware.silent.lib.registry.BlockRegistryObject;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTableManager;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class ModBlocks {
    ///////////////////
    //     Racks     //
    ///////////////////
    public static final BlockRegistryObject<DryingRackBlock> OAK_DRYING_RACK = registerMachine("oak_drying_rack", DryingRackBlock::new);
    public static final BlockRegistryObject<DryingRackBlock> BIRCH_DRYING_RACK = registerMachine("birch_drying_rack", DryingRackBlock::new);
    public static final BlockRegistryObject<DryingRackBlock> SPRUCE_DRYING_RACK = registerMachine("spruce_drying_rack", DryingRackBlock::new);
    public static final BlockRegistryObject<DryingRackBlock> JUNGLE_DRYING_RACK = registerMachine("jungle_drying_rack", DryingRackBlock::new);
    public static final BlockRegistryObject<DryingRackBlock> DARK_OAK_DRYING_RACK = registerMachine("dark_oak_drying_rack", DryingRackBlock::new);
    public static final BlockRegistryObject<DryingRackBlock> ACACIA_DRYING_RACK = registerMachine("acacia_drying_rack", DryingRackBlock::new);
    public static final BlockRegistryObject<DryingRackBlock> EUCALYPTUS_DRYING_RACK = registerMachine("eucalyptus_drying_rack", DryingRackBlock::new);
    public static final BlockRegistryObject<DryingRackBlock> CHERRY_DRYING_RACK = registerMachine("cherry_drying_rack", DryingRackBlock::new);

    ////////////////////////
    //     Processing     //
    ////////////////////////
    public static final BlockRegistryObject<MachineFrameBlock> STONE_MACHINE_FRAME = registerMachine("stone_machine_frame", () ->
            new MachineFrameBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3, 10).sound(SoundType.STONE).notSolid()));
    public static final BlockRegistryObject<MachineFrameBlock> ALLOY_MACHINE_FRAME = registerMachine("alloy_machine_frame", () ->
            new MachineFrameBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3, 10).sound(SoundType.METAL).notSolid()));
    public static final BlockRegistryObject<AlloySmelterBlock> BASIC_ALLOY_SMELTER = registerMachine("basic_alloy_smelter", () ->
            new AlloySmelterBlock(MachineTier.BASIC));
    public static final BlockRegistryObject<AlloySmelterBlock> ALLOY_SMELTER = registerMachine("alloy_smelter", () ->
            new AlloySmelterBlock(MachineTier.STANDARD));
    public static final BlockRegistryObject<CrusherBlock> BASIC_CRUSHER = registerMachine("basic_crusher", () ->
            new CrusherBlock(MachineTier.BASIC));
    public static final BlockRegistryObject<CrusherBlock> CRUSHER = registerMachine("crusher", () ->
            new CrusherBlock(MachineTier.STANDARD));
    public static final BlockRegistryObject<CompressorBlock> COMPRESSOR = registerMachine("compressor", CompressorBlock::new);
    public static final BlockRegistryObject<ElectricFurnaceBlock> ELECTRIC_FURNACE = registerMachine("electric_furnace", ElectricFurnaceBlock::new);
    public static final BlockRegistryObject<RefineryBlock> REFINERY = registerMachine("refinery", RefineryBlock::new);
    public static final BlockRegistryObject<MixerBlock> MIXER = registerMachine("mixer", MixerBlock::new);
    public static final BlockRegistryObject<SolidifierBlock> SOLIDIFIER = registerMachine("solidifier", SolidifierBlock::new);
    public static final BlockRegistryObject<InfuserBlock> INFUSER = registerMachine("infuser", InfuserBlock::new);
    public static final BlockRegistryObject<PumpBlock> PUMP = registerMachine("pump", PumpBlock::new);
    public static final BlockRegistryObject<QuarryBlock> QUARRY = registerMachine("quarry", QuarryBlock::new);

    ////////////////////////
    //     Generators     //
    ////////////////////////
    public static final BlockRegistryObject<CoalGeneratorBlock> COAL_GENERATOR = registerMachine("coal_generator", CoalGeneratorBlock::new);
    public static final BlockRegistryObject<LavaGeneratorBlock> LAVA_GENERATOR = registerMachine("lava_generator", LavaGeneratorBlock::new);
    public static final BlockRegistryObject<DieselGeneratorBlock> DIESEL_GENERATOR = registerMachine("diesel_generator", DieselGeneratorBlock::new);

    ///////////////////////////
    //     Battery boxes     //
    ///////////////////////////
    public static final BlockRegistryObject<BatteryBoxBlock> BATTERY_BOX = registerMachine("battery_box", BatteryBoxBlock::new);

    ///////////////////////
    //     Transport     //
    ///////////////////////
    public static final BlockRegistryObject<WireBlock> WIRE = registerMachine("wire", () -> new WireBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1, 5)));
    public static final BlockRegistryObject<PipeBlock> PIPE = registerMachine("pipe", () -> new PipeBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(1, 5)));

    ////////////////////
    //     Fluids     //
    ////////////////////
    public static final BlockRegistryObject<FlowingFluidBlock> OIL = registerFluid("oil", () -> ModFluids.OIL);
    public static final BlockRegistryObject<FlowingFluidBlock> DIESEL = registerFluid("diesel", () -> ModFluids.DIESEL);

    /////////////////////
    //     Flowers     //
    /////////////////////
    public static final BlockRegistryObject<FlowerBlock> BUTTERCUP = register("buttercup", () -> new CRFlowerBlock(Effects.ABSORPTION, 200, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.getCutout();
        }
    });
    public static final BlockRegistryObject<FlowerBlock> SMALL_SUNFLOWER = register("small_sunflower", () -> new CRFlowerBlock(Effects.GLOWING, 60, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.getCutout();
        }
    });
    public static final BlockRegistryObject<FlowerBlock> SMALL_LILAC = register("small_lilac", () -> new CRFlowerBlock(Effects.HASTE, 220, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.getCutout();
        }
    });
    public static final BlockRegistryObject<FlowerBlock> SMALL_PEONY = register("small_peony", () -> new CRFlowerBlock(Effects.SPEED, 160, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.getCutout();
        }
    });
    public static final BlockRegistryObject<FlowerBlock> SMALL_ROSE_BUSH = register("small_rose_bush", () -> new CRFlowerBlock(Effects.HEALTH_BOOST, 220, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.getCutout();
        }
    });

    ///////////////////
    //     Doors     //
    ///////////////////
    public static final BlockRegistryObject<DoorBlock> LAB_DOOR = registerRedstone("lab_door", () -> new CRDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.getCutout();
        }
    });
    public static final BlockRegistryObject<DoorBlock> SHOPPING_DOOR = registerRedstone("shopping_door", () -> new CRDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.getCutout();
        }
    });
    public static final BlockRegistryObject<DoorBlock> IRON_GLASS_DOOR = registerRedstone("iron_glass_door", () -> new CRDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.getCutout();
        }
    });
    public static final BlockRegistryObject<DoorBlock> IRON_BARRIER_DOOR = registerRedstone("iron_barrier_door", () -> new CRDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.getCutout();
        }
    });

    ///////////////////////////
    //     Miscellaneous     //
    ///////////////////////////
    public static final BlockRegistryObject<Block> ASPHALT = registerMiscellaneous("asphalt", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.25F, 3.75F).sound(SoundType.STONE)));

    //////////////////
    //     Wood     //
    //////////////////
    public static final BlockRegistryObject<Block> EUCALYPTUS_PLANKS = registerWood("eucalyptus_planks", () -> new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<RotatedPillarBlock> EUCALYPTUS_LOG = registerWood("eucalyptus_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.QUARTZ).harvestTool(ToolType.AXE).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<LeavesBlock> EUCALYPTUS_LEAVES = register("eucalyptus_leaves", ModBlocks::createLeavesBlock);
    public static final BlockRegistryObject<SaplingBlock> EUCALYPTUS_SAPLING = register("eucalyptus_sapling", () -> new SaplingBlock(new EucalyptusTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final BlockRegistryObject<StairsBlock> EUCALYPTUS_STAIRS = registerShaped("eucalyptus_stairs", () -> new StairsBlock(EUCALYPTUS_PLANKS.get()::getDefaultState, Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<SlabBlock> EUCALYPTUS_SLAB = registerShaped("eucalyptus_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<FenceBlock> EUCALYPTUS_FENCE = registerShaped("eucalyptus_fence", () -> new FenceBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<FenceGateBlock> EUCALYPTUS_FENCE_GATE = registerShaped("eucalyptus_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<Block> CHERRY_PLANKS = registerWood("cherry_planks", () -> new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<Block> CHERRY_LOG = registerWood("cherry_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<LeavesBlock> CHERRY_LEAVES = register("cherry_leaves", ModBlocks::createLeavesBlock);
    public static final BlockRegistryObject<SaplingBlock> CHERRY_SAPLING = register("cherry_sapling", () -> new SaplingBlock(new CherryTree(), AbstractBlock.Properties.create(Material.PLANTS).harvestTool(ToolType.AXE).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    public static final BlockRegistryObject<StairsBlock> CHERRY_STAIRS = registerShaped("cherry_stairs", () -> new StairsBlock(CHERRY_PLANKS.get()::getDefaultState, Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<SlabBlock> CHERRY_SLAB = registerShaped("cherry_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<FenceBlock> CHERRY_FENCE = registerShaped("cherry_fence", () -> new FenceBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<FenceGateBlock> CHERRY_FENCE_GATE = registerShaped("cherry_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));

    //////////////////////
    //     Redstone     //
    /////////////////////.
    public static final BlockRegistryObject<WoodButtonBlock> EUCALYPTUS_BUTTON = registerRedstone("eucalyptus_button", () -> new WoodButtonBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<WoodButtonBlock> CHERRY_BUTTON = registerRedstone("cherry_button", () -> new WoodButtonBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<StoneButtonBlock> DIAMOND_BUTTON = registerRedstone("diamond_button", () -> new CustomButtonBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.AXE).sound(SoundType.STONE), 100));
    public static final BlockRegistryObject<StoneButtonBlock> IRON_BUTTON = registerRedstone("iron_button", () -> new CustomButtonBlock(Block.Properties.create(Material.IRON).harvestTool(ToolType.AXE).sound(SoundType.METAL), 60));
    public static final BlockRegistryObject<StoneButtonBlock> GOLD_BUTTON = registerRedstone("gold_button", () -> new CustomButtonBlock(Block.Properties.create(Material.IRON).harvestTool(ToolType.AXE).sound(SoundType.METAL), 10));
    public static final BlockRegistryObject<StoneButtonBlock> QUARTZ_BUTTON = registerRedstone("quartz_button", () -> new CustomButtonBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.AXE).sound(SoundType.STONE), 5));
    public static final BlockRegistryObject<PressurePlateBlock> EUCALYPTUS_PRESSURE_PLATE = registerRedstone("eucalyptus_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<PressurePlateBlock> CHERRY_PRESSURE_PLATE = registerRedstone("cherry_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));

    ///////////////////////
    //     Furniture     /4
    ///////////////////////
    public static final BlockRegistryObject<Block> GAME_PC = registerFurniture("game_pc", () -> new GamePcBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)));
    public static final BlockRegistryObject<Block> ROUTER = registerFurniture("router", () -> new FaceableBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)) {
        private final VoxelShape SHAPE = VoxelShapes.create(2d / 16, 0d / 16, 2d / 16, 14d / 16, 2.2d / 16, 14d / 16);

        @Override
        public @NotNull VoxelShape getShape(BlockState state, @NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
            return SHAPE;
        }
    });

    /////////////////////////
    //     Tile entity     //
    /////////////////////////
    public static final BlockRegistryObject<Block> WOODEN_CRATE = registerMachine("wooden_crate", () -> new WoodenCrateBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.9f).sound(SoundType.WOOD)));

    ////////////////////////
    //     Ore blocks     //
    ////////////////////////
    @Deprecated public static final BlockRegistryObject<OreBlock> STEEL_ORE = registerNoItem("steel_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.125f, 3.375f).sound(SoundType.STONE).harvestLevel(2)));
    @Deprecated public static final BlockRegistryObject<OreBlock> TUNGSTEN_ORE = registerNoItem("tungsten_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(5.125f, 6.425f).sound(SoundType.STONE).harvestLevel(3)));
    @Deprecated public static final ItemRegistryObject<BlockItem> STEEL_ORE_ITEM = registerItem("steel_ore", () -> new BlockItem(STEEL_ORE.get(), new Item.Properties()));
    @Deprecated public static final ItemRegistryObject<BlockItem> TUNGSTEN_ORE_ITEM = registerItem("tungsten_ore", () -> new BlockItem(TUNGSTEN_ORE.get(), new Item.Properties()));

    public static final BlockRegistryObject<OreBlock> GILDED_DIRT = registerOre("gilded_dirt", () -> new OreBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).setRequiresTool().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.5f).sound(SoundType.GROUND)));
    public static final BlockRegistryObject<OreBlock> RUBY_ORE = registerOre("ruby_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.75f, 2.875f).sound(SoundType.STONE).harvestLevel(2)));
    public static final BlockRegistryObject<OreBlock> AMETHYST_ORE = registerOre("amethyst_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.25f, 2.375f).sound(SoundType.STONE).harvestLevel(2)));
    public static final BlockRegistryObject<OreBlock> AQUAMARINE_ORE = registerOre("aquamarine_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.155f, 2.4635f).sound(SoundType.STONE).harvestLevel(2)));
    public static final BlockRegistryObject<OreBlock> SAPHIRE_ORE = registerOre("saphire_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.365f, 2.845f).sound(SoundType.STONE).harvestLevel(2)));
    public static final BlockRegistryObject<OreBlock> MALACHITE_ORE = registerOre("malachite_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.25f, 3.75f).sound(SoundType.STONE).hardnessAndResistance(2)));
    public static final BlockRegistryObject<OreBlock> PERIDOT_ORE = registerOre("peridot_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.95f, 3.2f).sound(SoundType.STONE).hardnessAndResistance(2)));
    public static final BlockRegistryObject<OreBlock> TANZANITE_ORE = registerOre("tanzanite_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE).harvestLevel(2)));
    public static final BlockRegistryObject<OreBlock> ULTRINIUM_ORE = registerOre("ultrinium_ore", () -> new OreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(250.0f, 9600.0f).sound(SoundType.STONE).harvestLevel(5)));
    public static final BlockRegistryObject<InfinityOreBlock> INFINITY_ORE = registerOverpowered("infinity_ore", () -> new InfinityOreBlock(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(10240f, Float.MAX_VALUE).sound(SoundType.STONE).harvestLevel(6)));

    /////////////////////////////////////
    //     Solid Gem / Metal block     //
    /////////////////////////////////////
    public static final BlockRegistryObject<Block> TUNGSTEN_BLOCK = register("tungsten_block", () -> new Block(Block.Properties.create(Material.IRON).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(5.9875f, 6.5525f).sound(SoundType.STONE).harvestLevel(3)));
    public static final BlockRegistryObject<Block> RUBY_BLOCK = register("ruby_block", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.25f, 5.5f).sound(SoundType.STONE).harvestLevel(2)));
    public static final BlockRegistryObject<Block> AMETHYST_BLOCK = register("amethyst_block", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.875f, 4.0625f).sound(SoundType.STONE).harvestLevel(1)));
    public static final BlockRegistryObject<Block> AQUAMARINE_BLOCK = register("aquamarine_block", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.995f, 4.1275f).sound(SoundType.STONE).harvestLevel(1)));
    public static final BlockRegistryObject<Block> SAPHIRE_BLOCK = register("saphire_block", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.120f, 4.5735f).sound(SoundType.STONE).harvestLevel(1)));
    public static final BlockRegistryObject<Block> MALACHITE_BLOCK = register("malachite_block", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.8914f, 5.06635f).sound(SoundType.STONE).harvestLevel(1)));
    public static final BlockRegistryObject<Block> PERIDOT_BLOCK = register("peridot_block", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.8914f, 5.06635f).sound(SoundType.STONE).harvestLevel(1)));
    public static final BlockRegistryObject<Block> TANZANITE_BLOCK = register("tanzanite_block", () -> new Block(Block.Properties.create(Material.IRON).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(4.26f, 5.5f).sound(SoundType.STONE).harvestLevel(2)));
    public static final BlockRegistryObject<Block> ULTRINIUM_BLOCK = register("ultrinium_block", () -> new Block(Block.Properties.create(Material.ROCK).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(12.9f, 99999999.9f).sound(SoundType.STONE).harvestLevel(5)));
    public static final BlockRegistryObject<InfinityBlock> INFINITY_BLOCK = registerOverpowered("infinity_block", () -> new InfinityBlock(Block.Properties.create(Material.IRON).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(64.5f, Float.MAX_VALUE).sound(SoundType.METAL).harvestLevel(6)));

    public static final ArrayList<BlockRegistryObject<Block>> BOOKSHELFS = new ArrayList<>();

    static {
        BOOKSHELFS.add(registerBookshelf("bookshelf", () -> new Block(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD)) {
            @Override
            public String getTranslationKey() {
                return "block.minecraft.bookshelf";
            }
        }));
        for (int i = 1; i < 225; i++) {
            BOOKSHELFS.add(registerBookshelf("bookshelf" + i, () -> new Block(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).sound(SoundType.WOOD)) {
                @Override
                public String getTranslationKey() {
                    return "block.minecraft.bookshelf";
                }
            }));
        }
    }

    //////////////////////////////
    //     Utility methods     //
    //////////////////////////////
    static {
        OreMaterials.registerBlocks();
    }

    private ModBlocks() {
    }

    private static <T extends Item> ItemRegistryObject<T> registerItem(String name, Supplier<T> supplier) {
        return new ItemRegistryObject<>(Registration.ITEMS.register(name, supplier));
    }

    static void register() {
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderTypes(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(STONE_MACHINE_FRAME.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ALLOY_MACHINE_FRAME.get(), RenderType.getCutout());
        Registration.getBlocks(AbstractMachineBlock.class).forEach(block ->
                RenderTypeLookup.setRenderLayer(block, RenderType.getTranslucent()));
    }

    private static <T extends Block> BlockRegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return new BlockRegistryObject<>(Registration.BLOCKS.register(name, block));
    }

    private static <T extends Block> BlockRegistryObject<T> register(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::item);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Block> BlockRegistryObject<T> registerMiscellaneous(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::miscellaneousItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerNature(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::natureItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerRedstone(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::redstoneItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerMachine(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::machineItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerIngredient(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::ingredientItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerSpecial(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::specialItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerOre(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::oreItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerWood(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::woodItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerShaped(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::shapedItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerOverpowered(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::godItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerFurniture(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::furnitureItem);
    }

    private static <T extends Block> BlockRegistryObject<T> registerBookshelf(String name, Supplier<T> block) {
        return register(name, block, ModBlocks::bookshelfItem);
    }

    private static <T extends Block> BlockRegistryObject<T> register(String name, Supplier<T> block, Function<BlockRegistryObject<T>, Supplier<? extends BlockItem>> item) {
        BlockRegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, item.apply(ret));
        return ret;
    }

    private static BlockRegistryObject<FlowingFluidBlock> registerFluid(String name, Supplier<FlowingFluid> fluid) {
        return registerNoItem(name, () ->
                new FlowingFluidBlock(fluid, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
    }

    private static <T extends Block> Supplier<BlockItem> item(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties());
    }

    private static <T extends Block> Supplier<BlockItem> miscellaneousItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.NATURE));
    }

    private static <T extends Block> Supplier<BlockItem> natureItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.NATURE));
    }

    private static <T extends Block> Supplier<BlockItem> redstoneItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.REDSTONE));
    }

    private static <T extends Block> Supplier<BlockItem> machineItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.MACHINES));
    }

    private static <T extends Block> Supplier<BlockItem> ingredientItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.INGREDIENTS));
    }

    private static <T extends Block> Supplier<BlockItem> specialItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.SPECIALS));
    }

    private static <T extends Block> Supplier<BlockItem> oreItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.ORES));
    }

    private static <T extends Block> Supplier<BlockItem> woodItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.WOOD));
    }

    private static <T extends Block> Supplier<BlockItem> shapedItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.SHAPES));
    }

    private static <T extends Block> Supplier<BlockItem> godItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.OVERPOWERED));
    }

    private static <T extends Block> Supplier<BlockItem> furnitureItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.FURNITURE));
    }

    private static <T extends Block> Supplier<BlockItem> bookshelfItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().group(Groups.BOOKSHELFS));
    }

    private static LeavesBlock createLeavesBlock() {
        return new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().setAllowsSpawn(ModBlocks::allowsSpawnOnLeaves).setSuffocates(ModBlocks::isNotSolid).setBlocksVision(ModBlocks::isNotSolid));
    }

    private static Boolean allowsSpawnOnLeaves(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    private static boolean isNotSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    @Nullable
    public static ITextComponent checkForMissingLootTables(PlayerEntity player) {
        // Checks for missing block loot tables, but only in dev
        if (!(player.world instanceof ServerWorld) || !QForgeMod.isDevState()) return null;

        LootTableManager lootTableManager = ((ServerWorld) player.world).getServer().getLootTableManager();
        Collection<String> missing = new ArrayList<>();

        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            ResourceLocation lootTable = block.getLootTable();
            // The AirBlock check filters out removed blocks
            if (lootTable.getNamespace().equals(QForgeMod.MOD_ID) && !(block instanceof AirBlock) && !lootTableManager.getLootTableKeys().contains(lootTable)) {
                QForgeMod.LOGGER.error("Missing block loot table '{}' for {}", lootTable, block.getRegistryName());
                missing.add(lootTable.toString());
            }
        }

        if (!missing.isEmpty()) {
            String list = String.join(", ", missing);
            return new StringTextComponent("The following block loot tables are missing: " + list).mergeStyle(TextFormatting.RED);
        }

        return null;
    }
}
