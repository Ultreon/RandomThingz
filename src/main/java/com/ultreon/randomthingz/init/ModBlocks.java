package com.ultreon.randomthingz.init;

import com.ultreon.modlib.silentlib.registry.BlockRegistryObject;
import com.ultreon.modlib.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.*;
import com.ultreon.randomthingz.block.custom.CustomButtonBlock;
import com.ultreon.randomthingz.block.custom.render.CRDoorBlock;
import com.ultreon.randomthingz.block.custom.render.CRFlowerBlock;
import com.ultreon.randomthingz.block.door.DoorType;
import com.ultreon.randomthingz.block.entity.ModBlockEntities;
import com.ultreon.randomthingz.block.entity.itemrenderer.CustomItemStackRenderer;
import com.ultreon.randomthingz.block.fluid.common.ModFluids;
import com.ultreon.randomthingz.block.furniture.WoodenCrateBlock;
import com.ultreon.randomthingz.block.machines.MachineBlock;
import com.ultreon.randomthingz.block.machines.MachineFrameBlock;
import com.ultreon.randomthingz.block.machines.alloysmelter.AlloySmelterBlock;
import com.ultreon.randomthingz.block.machines.arcaneescalator.ArcaneEscalatorBlock;
import com.ultreon.randomthingz.block.machines.batterybox.BatteryBoxBlock;
import com.ultreon.randomthingz.block.machines.compressor.CompressorBlock;
import com.ultreon.randomthingz.block.machines.crusher.CrusherBlock;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackBlock;
import com.ultreon.randomthingz.block.machines.electricfurnace.ElectricFurnaceBlock;
import com.ultreon.randomthingz.block.machines.generator.coal.CoalGeneratorBlock;
import com.ultreon.randomthingz.block.machines.generator.diesel.DieselGeneratorBlock;
import com.ultreon.randomthingz.block.machines.generator.lava.LavaGeneratorBlock;
import com.ultreon.randomthingz.block.machines.infuser.InfuserBlock;
import com.ultreon.randomthingz.block.machines.itempipe.ItemPipeBlock;
import com.ultreon.randomthingz.block.machines.mixer.MixerBlock;
import com.ultreon.randomthingz.block.machines.pipe.PipeBlock;
import com.ultreon.randomthingz.block.machines.pump.PumpBlock;
import com.ultreon.randomthingz.block.machines.quarry.QuarryBlock;
import com.ultreon.randomthingz.block.machines.refinery.RefineryBlock;
import com.ultreon.randomthingz.block.machines.solidifier.SolidifierBlock;
import com.ultreon.randomthingz.block.machines.wire.WireBlock;
import com.ultreon.randomthingz.block.rails.EmpoweredRailBlock;
import com.ultreon.randomthingz.block.rails.SpeedRailBlock;
import com.ultreon.randomthingz.block.trees.CherryTree;
import com.ultreon.randomthingz.block.trees.EucalyptusTree;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.item.ChestBlockItem;
import com.ultreon.randomthingz.item.ItemTileEntityRenderers;
import com.ultreon.randomthingz.item.block.DeprecatedBlockItem;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import com.ultreon.randomthingz.registration.Registration;
import com.ultreon.randomthingz.block.entity.ChristmasChestBlockEntity;
import lombok.experimental.UtilityClass;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@UtilityClass
@SuppressWarnings({"unused", "SameParameterValue"})
public final class ModBlocks {
    static {
        for (StoneType stoneType : StoneType.values()) {
            stoneType.register(Registration.BLOCKS, Registration.ITEMS);
        }
    }

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
            new MachineFrameBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5f, 6f).sound(SoundType.STONE).noOcclusion()));
    public static final BlockRegistryObject<MachineFrameBlock> ALLOY_MACHINE_FRAME = registerMachine("alloy_machine_frame", () ->
            new MachineFrameBlock(Block.Properties.of(Material.METAL).requiresCorrectToolForDrops().strength(3f, 10f).sound(SoundType.METAL).noOcclusion()));
    public static final BlockRegistryObject<AlloySmelterBlock> BASIC_ALLOY_SMELTER = registerMachine("basic_alloy_smelter", () ->
            new AlloySmelterBlock(MachineTier.BASIC));
    public static final BlockRegistryObject<AlloySmelterBlock> ALLOY_SMELTER = registerMachine("alloy_smelter", () ->
            new AlloySmelterBlock(MachineTier.STANDARD));
    public static final BlockRegistryObject<ArcaneEscalatorBlock> BASIC_ARCANE_ESCALATOR = registerMachine("basic_arcane_escalator", () ->
            new ArcaneEscalatorBlock(MachineTier.BASIC));
    public static final BlockRegistryObject<ArcaneEscalatorBlock> ARCANE_ESCALATOR = registerMachine("arcane_escalator", () ->
            new ArcaneEscalatorBlock(MachineTier.STANDARD));
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
    public static final BlockRegistryObject<QuarryBlock> QUARRY = registerMachine("quarry", () -> new QuarryBlock(MachineTier.STANDARD));
    public static final BlockRegistryObject<QuarryBlock> HEAVY_QUARRY = registerMachine("heavy_quarry", () -> new QuarryBlock(MachineTier.HEAVY));
    public static final BlockRegistryObject<QuarryBlock> SUPER_QUARRY = registerMachine("super_quarry", () -> new QuarryBlock(MachineTier.SUPER));
    public static final BlockRegistryObject<QuarryBlock> EXTREME_QUARRY = registerMachine("extreme_quarry", () -> new QuarryBlock(MachineTier.EXTREME));
    public static final BlockRegistryObject<QuarryBlock> ULTRA_QUARRY = registerMachine("ultra_quarry", () -> new QuarryBlock(MachineTier.ULTRA));

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
    public static final BlockRegistryObject<WireBlock> WIRE = registerMachine("wire", () -> new WireBlock(Block.Properties.of(Material.DECORATION).strength(1f, 5f)));
    public static final BlockRegistryObject<PipeBlock> PIPE = registerMachine("pipe", () -> new PipeBlock(Block.Properties.of(Material.DECORATION).strength(1f, 5f)));
    public static final BlockRegistryObject<ItemPipeBlock> ITEM_PIPE = registerMachine("item_pipe", () -> new ItemPipeBlock(Block.Properties.of(Material.DECORATION).strength(1f, 5f)));

    ////////////////////
    //     Fluids     //
    ////////////////////
    public static final BlockRegistryObject<LiquidBlock> OIL = registerFluid("oil", () -> ModFluids.OIL);
    public static final BlockRegistryObject<LiquidBlock> DIESEL = registerFluid("diesel", () -> ModFluids.DIESEL);

    /////////////////////
    //     Flowers     //
    /////////////////////
    public static final BlockRegistryObject<FlowerBlock> BUTTERCUP = registerNature("buttercup", () -> new CRFlowerBlock(MobEffects.ABSORPTION, 200, Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.cutout();
        }
    });
    public static final BlockRegistryObject<FlowerBlock> SMALL_SUNFLOWER = registerNature("small_sunflower", () -> new CRFlowerBlock(MobEffects.GLOWING, 60, Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.cutout();
        }
    });
    public static final BlockRegistryObject<FlowerBlock> SMALL_LILAC = registerNature("small_lilac", () -> new CRFlowerBlock(MobEffects.DIG_SPEED, 220, Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.cutout();
        }
    });
    public static final BlockRegistryObject<FlowerBlock> SMALL_PEONY = registerNature("small_peony", () -> new CRFlowerBlock(MobEffects.MOVEMENT_SPEED, 160, Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.cutout();
        }
    });
    public static final BlockRegistryObject<FlowerBlock> SMALL_ROSE_BUSH = registerNature("small_rose_bush", () -> new CRFlowerBlock(MobEffects.HEALTH_BOOST, 220, Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.cutout();
        }
    });

    ///////////////////
    //     Doors     //
    ///////////////////
    public static final BlockRegistryObject<DoorBlock> LAB_DOOR = registerRedstone("lab_door", () -> new CRDoorBlock(DoorType.IRON, Block.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5.5f).sound(SoundType.METAL).noOcclusion()) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.cutout();
        }
    });
    public static final BlockRegistryObject<DoorBlock> SHOPPING_DOOR = registerRedstone("shopping_door", () -> new CRDoorBlock(DoorType.IRON, Block.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(4.7f).sound(SoundType.METAL).noOcclusion()) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.cutout();
        }
    });
    public static final BlockRegistryObject<DoorBlock> IRON_GLASS_DOOR = registerRedstone("iron_glass_door", () -> new CRDoorBlock(DoorType.IRON, Block.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(4.7f).sound(SoundType.METAL).noOcclusion()) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.cutout();
        }
    });
    public static final BlockRegistryObject<DoorBlock> IRON_BARRIER_DOOR = registerRedstone("iron_barrier_door", () -> new CRDoorBlock(DoorType.IRON, Block.Properties.of(Material.METAL, MaterialColor.METAL).requiresCorrectToolForDrops().strength(5f).sound(SoundType.METAL).noOcclusion()) {
        @OnlyIn(Dist.CLIENT)
        @Override
        public RenderType getRenderType() {
            return RenderType.cutout();
        }
    });

    ///////////////////////////
    //     Miscellaneous     //
    ///////////////////////////
    public static final BlockRegistryObject<Block> ASPHALT = registerMiscellaneous("asphalt", () -> new StoneBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.2f, 4.5f).sound(SoundType.STONE)));
    public static final BlockRegistryObject<AtomicTntBlock> ATOMIC_TNT = registerMiscellaneous("atomic_tnt", () -> new AtomicTntBlock(Block.Properties.of(Material.EXPLOSIVE).instabreak().sound(SoundType.GRASS)));

    //////////////////
    //     Wood     //
    //////////////////
    public static final BlockRegistryObject<Block> EUCALYPTUS_PLANKS = registerWood("eucalyptus_planks", () -> new WoodBlock(Block.Properties.of(Material.WOOD).strength(2f, 3f).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<RotatedPillarBlock> EUCALYPTUS_LOG = registerWood("eucalyptus_log", () -> new LogBlock(Block.Properties.of(Material.WOOD, MaterialColor.QUARTZ).strength(2f).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<LeavesBlock> EUCALYPTUS_LEAVES = register("eucalyptus_leaves", ModBlocks::createLeavesBlock);
    public static final BlockRegistryObject<SaplingBlock> EUCALYPTUS_SAPLING = register("eucalyptus_sapling", () -> new SaplingBlock(new EucalyptusTree(), BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final BlockRegistryObject<StairBlock> EUCALYPTUS_STAIRS = registerShaped("eucalyptus_stairs", () -> new WoodenStairBlock(EUCALYPTUS_PLANKS.get()::defaultBlockState, Block.Properties.copy(EUCALYPTUS_PLANKS.get())));
    public static final BlockRegistryObject<SlabBlock> EUCALYPTUS_SLAB = registerShaped("eucalyptus_slab", () -> new WoodenSlabBlock(Block.Properties.copy(EUCALYPTUS_PLANKS.get())));
    public static final BlockRegistryObject<FenceBlock> EUCALYPTUS_FENCE = registerShaped("eucalyptus_fence", () -> new WoodenFenceBlock(Block.Properties.copy(EUCALYPTUS_PLANKS.get())));
    public static final BlockRegistryObject<FenceGateBlock> EUCALYPTUS_FENCE_GATE = registerShaped("eucalyptus_fence_gate", () -> new WoodenFenceGateBlock(Block.Properties.copy(EUCALYPTUS_PLANKS.get())));

    public static final BlockRegistryObject<Block> CHERRY_PLANKS = registerWood("cherry_planks", () -> new WoodBlock(Block.Properties.of(Material.WOOD).strength(2f, 3f).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<Block> CHERRY_LOG = registerWood("cherry_log", () -> new LogBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2f).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<LeavesBlock> CHERRY_LEAVES = registerNature("cherry_leaves", ModBlocks::createLeavesBlock);
    public static final BlockRegistryObject<SaplingBlock> CHERRY_SAPLING = registerNature("cherry_sapling", () -> new SaplingBlock(new CherryTree(), BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final BlockRegistryObject<StairBlock> CHERRY_STAIRS = registerShaped("cherry_stairs", () -> new WoodenStairBlock(CHERRY_PLANKS.get()::defaultBlockState, Block.Properties.copy(CHERRY_PLANKS.get())));
    public static final BlockRegistryObject<SlabBlock> CHERRY_SLAB = registerShaped("cherry_slab", () -> new WoodenSlabBlock(Block.Properties.copy(CHERRY_PLANKS.get())));
    public static final BlockRegistryObject<FenceBlock> CHERRY_FENCE = registerShaped("cherry_fence", () -> new WoodenFenceBlock(Block.Properties.copy(CHERRY_PLANKS.get())));
    public static final BlockRegistryObject<FenceGateBlock> CHERRY_FENCE_GATE = registerShaped("cherry_fence_gate", () -> new WoodenFenceGateBlock(Block.Properties.copy(CHERRY_PLANKS.get())));

    //////////////////////
    //     Redstone     //
    //////////////////////
    public static final BlockRegistryObject<WoodButtonBlock> EUCALYPTUS_BUTTON = registerRedstone("eucalyptus_button", () -> new WoodenButtonBlock(Block.Properties.of(Material.WOOD).noCollission().strength(.5f).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<WoodButtonBlock> CHERRY_BUTTON = registerRedstone("cherry_button", () -> new WoodenButtonBlock(Block.Properties.of(Material.WOOD).noCollission().strength(.5f).sound(SoundType.WOOD)));

    public static final BlockRegistryObject<StoneButtonBlock> DIAMOND_BUTTON = registerRedstone("diamond_button", () -> new CustomButtonBlock(ButtonType.ROCK_OR_METAL, Block.Properties.of(Material.STONE).noCollission().requiresCorrectToolForDrops().strength(3f).sound(SoundType.STONE), 100));
    public static final BlockRegistryObject<StoneButtonBlock> IRON_BUTTON = registerRedstone("iron_button", () -> new CustomButtonBlock(ButtonType.ROCK_OR_METAL, Block.Properties.of(Material.METAL).noCollission().requiresCorrectToolForDrops().strength(1.5f).sound(SoundType.METAL), 60));
    public static final BlockRegistryObject<StoneButtonBlock> GOLD_BUTTON = registerRedstone("gold_button", () -> new CustomButtonBlock(ButtonType.ROCK_OR_METAL, Block.Properties.of(Material.METAL).noCollission().requiresCorrectToolForDrops().strength(2.5f).sound(SoundType.METAL), 10));
    public static final BlockRegistryObject<StoneButtonBlock> QUARTZ_BUTTON = registerRedstone("quartz_button", () -> new CustomButtonBlock(ButtonType.ROCK_OR_METAL, Block.Properties.of(Material.STONE).noCollission().requiresCorrectToolForDrops().strength(.4f).sound(SoundType.STONE), 5));

    public static final BlockRegistryObject<PressurePlateBlock> EUCALYPTUS_PRESSURE_PLATE = registerRedstone("eucalyptus_pressure_plate", () -> new WoodenPressurePlateBlock(Block.Properties.of(Material.WOOD).noCollission().strength(.5f).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<PressurePlateBlock> CHERRY_PRESSURE_PLATE = registerRedstone("cherry_pressure_plate", () -> new WoodenPressurePlateBlock(Block.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_PURPLE).noCollission().strength(.5f).sound(SoundType.WOOD)));

    //
    public static final BlockRegistryObject<CheeseBlock> CHEESE = registerNoItem("cheese", () -> new CheeseBlock(Block.Properties.of(Material.CLAY, MaterialColor.QUARTZ).strength(.5f).sound(SoundType.WOOL)));

    ///////////////////////
    //     Furniture     /4
    ///////////////////////
    public static final BlockRegistryObject<Block> GAME_PC = registerFurniture("game_pc", GamePcBlock::new);
    public static final BlockRegistryObject<Block> ROUTER = registerFurniture("router", RouterBlock::new);

    ///////////////////
    //     Rails     //
    ///////////////////
    public static final BlockRegistryObject<EmpoweredRailBlock> EMPOWERED_RAIL = registerRedstone("empowered_rail", () -> new EmpoweredRailBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(.7f).sound(SoundType.METAL)));
    public static final BlockRegistryObject<SpeedRailBlock> SPEED_RAIL = registerRedstone("speed_rail", () -> new SpeedRailBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(.8f).sound(SoundType.METAL)));

    /////////////////////////
    //     Tile entity     //
    /////////////////////////
    public static final BlockRegistryObject<Block> WOODEN_CRATE = registerMachine("wooden_crate", () -> new WoodenCrateBlock(Block.Properties.of(Material.WOOD).strength(2f, 3f).sound(SoundType.WOOD)));
    public static final BlockRegistryObject<ChristmasChestBlock> CHRISTMAS_CHEST = registerChest(
            "christmas_chest", () -> new ChristmasChestBlock(Block.Properties.of(Material.WOOD)
                    .strength(2f, 3f)
                    .sound(SoundType.WOOD), ModBlockEntities.CHRISTMAS_CHEST::get),
            () -> ChristmasChestBlockEntity::new);

    ////////////////////////
    //     Ore blocks     //
    ////////////////////////
    public static final BlockRegistryObject<OreBlock> GILDED_DIRT = registerOre("gilded_dirt", () -> new ModOreBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).requiresCorrectToolForDrops().strength(.5f).sound(SoundType.GRAVEL), ToolType.SHOVEL, ToolRequirement.WOOD));

    /////////////////////////
    //     Bookshelves     //
    /////////////////////////
    public static final ArrayList<BlockRegistryObject<Block>> BOOKSHELVES = new ArrayList<>();

    static {
        BOOKSHELVES.add(registerBookshelf("bookshelf", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(1.5f).sound(SoundType.WOOD)) {
            @Override
            public String getDescriptionId() {
                return "block.minecraft.bookshelf";
            }
        }));
        for (int i = 1; i < 225; i++) {
            BOOKSHELVES.add(registerBookshelf("bookshelf" + i, () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(1.5f).sound(SoundType.WOOD)) {
                @Override
                public String getDescriptionId() {
                    return "block.minecraft.bookshelf";
                }
            }));
        }
    }

    //////////////////////////////
    //     Utility methods     //
    //////////////////////////////
    static {
        ItemMaterial.registerBlocks();
    }

    private static <T extends Item> ItemRegistryObject<T> registerItem(String name, Supplier<T> supplier) {
        return Registration.ITEMS.register(name, supplier);
    }

    public static void register() {
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderTypes(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(STONE_MACHINE_FRAME.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ALLOY_MACHINE_FRAME.get(), RenderType.cutout());
        Registration.getBlocks(MachineBlock.class).forEach(block ->
                ItemBlockRenderTypes.setRenderLayer(block, RenderType.translucent()));
    }

    private static <T extends Block> BlockRegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
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

    private static <T extends ChestBlock, E extends ChestBlockEntity> BlockRegistryObject<T> registerChest(String name, Supplier<T> block, Supplier<Supplier<E>> renderMethod) {
        return register(name, block, block1 -> chestItem(block1, renderMethod));
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

    private static <T extends Block> BlockRegistryObject<T> register(String name, Supplier<T> block, Function<BlockRegistryObject<T>, Supplier<? extends DeprecatedBlockItem>> item) {
        BlockRegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, item.apply(ret));
        return ret;
    }

    private static BlockRegistryObject<LiquidBlock> registerFluid(String name, Supplier<FlowingFluid> fluid) {
        return registerNoItem(name, () ->
                new LiquidBlock(fluid, Block.Properties.of(Material.WATER).noCollission().strength(100f).noDrops()));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> item(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties());
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> miscellaneousItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> natureItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> redstoneItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> machineItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(ModCreativeTabs.MACHINES));
    }

    private static <T extends ChestBlock, E extends ChestBlockEntity> Supplier<DeprecatedBlockItem> chestItem(BlockRegistryObject<T> block, Supplier<Supplier<E>> renderMethod) {
        return DistExecutor.unsafeRunForDist(() -> () -> () -> {
            Minecraft mc = Minecraft.getInstance();
            Supplier<Supplier<CustomItemStackRenderer<E>>> renderer = () -> () -> new CustomItemStackRenderer<>(mc.getBlockEntityRenderDispatcher(), mc.getEntityModels(), renderMethod.get());
            ItemTileEntityRenderers.lazyRegister(() -> (dispatcher, models) -> (BlockEntityWithoutLevelRenderer) renderer.get().get());

            return new DeprecatedBlockItem(block.get(), new Item.Properties().tab(ModCreativeTabs.MACHINES)) {
                @Override
                public Object getRenderPropertiesInternal() {
                    return super.getRenderPropertiesInternal();
                }

                @Override
                public void initializeClient(Consumer<IItemRenderProperties> consumer) {
                    consumer.accept(new IItemRenderProperties() {
                        @Override
                        public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                            return renderer.get().get();
                        }
                    });
                }
            };
        }, () -> () -> () -> new ChestBlockItem(block.get(), new Item.Properties().tab(ModCreativeTabs.MACHINES)));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> ingredientItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> specialItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(ModCreativeTabs.SPECIALS));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> oreItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> woodItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> shapedItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> godItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties());
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> furnitureItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
    }

    private static <T extends Block> Supplier<DeprecatedBlockItem> bookshelfItem(BlockRegistryObject<T> block) {
        return () -> new DeprecatedBlockItem(block.get(), new Item.Properties().tab(ModCreativeTabs.BOOKSHELVES));
    }

    private static LeavesBlock createLeavesBlock() {
        return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::allowsSpawnOnLeaves).isSuffocating(ModBlocks::isNotSolid).isViewBlocking(ModBlocks::isNotSolid));
    }

    private static Boolean allowsSpawnOnLeaves(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    private static boolean isNotSolid(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }

    @Nullable
    public static Component checkForMissingLootTables(Player player) {
        // Checks for missing block loot tables, but only in dev
        if (!(player.level instanceof ServerLevel) || !RandomThingz.isModDev()) return null;

        LootTables lootTableManager = ((ServerLevel) player.level).getServer().getLootTables();
        Collection<String> missing = new ArrayList<>();

        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            ResourceLocation lootTable = block.getLootTable();

            // The AirBlock check filters out removed blocks
            if (lootTable.getNamespace().equals(RandomThingz.MOD_ID) && !(block instanceof AirBlock) && !lootTableManager.getIds().contains(lootTable)) {
                RandomThingz.LOGGER.error("Missing block loot table '{}' for {}", lootTable, block.getRegistryName());
                missing.add(lootTable.toString());
            }
        }

        if (!missing.isEmpty()) {
            String list = String.join(", ", missing);
            return new TextComponent("The following block loot tables are missing: " + list).withStyle(ChatFormatting.RED);
        }

        return null;
    }
}
