package com.ultreon.texturedmodels.setup;

import com.ultreon.texturedmodels.QTextureModels;
import com.ultreon.texturedmodels.block.*;
import com.ultreon.texturedmodels.container.ChestFrameContainer;
import com.ultreon.texturedmodels.container.IllusionChestContainer;
import com.ultreon.texturedmodels.tileentity.BedFrameTile;
import com.ultreon.texturedmodels.tileentity.ChestFrameTileEntity;
import com.ultreon.texturedmodels.tileentity.FrameBlockTile;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Just a normal registering class. See Forge-Documentation on how to register objects
 *
 * @author PianoManu
 * @version 1.8 09/25/20
 */
@Mod.EventBusSubscriber(modid = QTextureModels.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
@SuppressWarnings("all") //only warning: datafixer for build()-method is null, but method is annotated as "NotNull"
public class Registration {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, QTextureModels.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, QTextureModels.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, QTextureModels.MOD_ID);
    //WHAT THE FACK???
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, QTextureModels.MOD_ID);
    //private static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, BlockCarpentryMain.MOD_ID);
    //private static final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, BlockCarpentryMain.MOD_ID);
    private static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, QTextureModels.MOD_ID);
    private static final Logger LOGGER = LogManager.getLogger();

    public static final RegistryObject<FrameBlock> FRAMEBLOCK = BLOCKS.register("frameblock", () -> new FrameBlock(Block.Properties.of(Material.WOOD)
            .sound(SoundType.WOOD)
            .strength(0.4f)
            .harvestTool(ToolType.AXE)
            .harvestLevel(0)
            .dynamicShape()
            .noOcclusion()));
    public static final RegistryObject<Item> FRAMEBLOCK_ITEM = ITEMS.register("frameblock", () -> new BlockItem(FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> FRAMEBLOCK_TILE = TILES.register("frameblock", () -> BlockEntityType.Builder.of(FrameBlockTile::new, FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<FrameBlock> FRAME_WALK_THRU = BLOCKS.register("frame_walk_thru", () -> new FrameBlock(Block.Properties.of(Material.WOOD)
            .sound(SoundType.WOOD)
            .strength(0.4f)
            .harvestTool(ToolType.AXE)
            .harvestLevel(0)
            .dynamicShape()
            .noOcclusion()
            .noCollission()));
    public static final RegistryObject<Item> FRAME_WALK_THRU_ITEM = ITEMS.register("frame_walk_thru", () -> new BlockItem(FRAME_WALK_THRU.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> FRAME_WALK_THRU_TILE = TILES.register("frame_walk_thru", () -> BlockEntityType.Builder.of(FrameBlockTile::new, FRAME_WALK_THRU.get()).build(null));

    //fixme or deleteme
    //public static final RegistryObject<FallingFrameBlock> FALLING_FRAMEBLOCK = BLOCKS.register("falling_frameblock", () -> new FallingFrameBlock(Block.Properties.from(FRAMEBLOCK.get()).notSolid()));
    //public static final RegistryObject<Item> FALLING_FRAMEBLOCK_ITEM = ITEMS.register("falling_frameblock", () -> new BlockItem(FALLING_FRAMEBLOCK.get(), new Item.Properties().group(BlockCarpentryMain.BlockCarpentryItemGroup.BLOCK_CARPENTRY)));
    //public static final RegistryObject<TileEntityType<FrameBlockTile>> FALLING_FRAMEBLOCK_TILE = TILES.register("falling_frameblock", () -> TileEntityType.Builder.create(FrameBlockTile::new, FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<SixWaySlabFrameBlock> SLAB_FRAMEBLOCK = BLOCKS.register("frame_slab", () -> new SixWaySlabFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> SLAB_FRAME_ITEM = ITEMS.register("frame_slab", () -> new BlockItem(SLAB_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> SLAB_FRAME_TILE = TILES.register("frame_slab", () -> BlockEntityType.Builder.of(FrameBlockTile::new, SLAB_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<FrameBlock> ILLUSION_BLOCK = BLOCKS.register("illusion_block", () -> new FrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> ILLUSION_BLOCK_ITEM = ITEMS.register("illusion_block", () -> new BlockItem(ILLUSION_BLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> ILLUSION_BLOCK_TILE = TILES.register("illusion_block", () -> BlockEntityType.Builder.of(FrameBlockTile::new, ILLUSION_BLOCK.get()).build(null));

    public static final RegistryObject<ButtonFrameBlock> BUTTON_FRAMEBLOCK = BLOCKS.register("frame_button", () -> new ButtonFrameBlock(Block.Properties.copy(FRAMEBLOCK.get()).noOcclusion().noCollission()));
    public static final RegistryObject<Item> BUTTON_FRAME_ITEM = ITEMS.register("frame_button", () -> new BlockItem(BUTTON_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> BUTTON_FRAME_TILE = TILES.register("frame_button", () -> BlockEntityType.Builder.of(FrameBlockTile::new, BUTTON_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<PressurePlateFrameBlock> PRESSURE_PLATE_FRAMEBLOCK = BLOCKS.register("frame_pressure_plate", () -> new PressurePlateFrameBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.copy(FRAMEBLOCK.get()).noOcclusion().noCollission()));
    public static final RegistryObject<Item> PRESSURE_PLATE_FRAME_ITEM = ITEMS.register("frame_pressure_plate", () -> new BlockItem(PRESSURE_PLATE_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> PRESSURE_PLATE_FRAME_TILE = TILES.register("frame_pressure_plate", () -> BlockEntityType.Builder.of(FrameBlockTile::new, BUTTON_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<StairsFrameBlock> STAIRS_FRAMEBLOCK = BLOCKS.register("frame_stairs", () -> new StairsFrameBlock(() -> FRAMEBLOCK.get().defaultBlockState(), Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> STAIRS_FRAME_ITEM = ITEMS.register("frame_stairs", () -> new BlockItem(STAIRS_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> STAIRS_FRAME_TILE = TILES.register("frame_stairs", () -> BlockEntityType.Builder.of(FrameBlockTile::new, STAIRS_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<DoorFrameBlock> DOOR_FRAMEBLOCK = BLOCKS.register("frame_door", () -> new DoorFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> DOOR_FRAME_ITEM = ITEMS.register("frame_door", () -> new BlockItem(DOOR_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> DOOR_FRAME_TILE = TILES.register("frame_door", () -> BlockEntityType.Builder.of(FrameBlockTile::new, DOOR_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<TrapdoorFrameBlock> TRAPDOOR_FRAMEBLOCK = BLOCKS.register("frame_trapdoor", () -> new TrapdoorFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> TRAPDOOR_FRAME_ITEM = ITEMS.register("frame_trapdoor", () -> new BlockItem(TRAPDOOR_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> TRAPDOOR_FRAME_TILE = TILES.register("frame_trapdoor", () -> BlockEntityType.Builder.of(FrameBlockTile::new, TRAPDOOR_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<FenceFrameBlock> FENCE_FRAMEBLOCK = BLOCKS.register("frame_fence", () -> new FenceFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> FENCE_FRAME_ITEM = ITEMS.register("frame_fence", () -> new BlockItem(FENCE_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> FENCE_FRAME_TILE = TILES.register("frame_fence", () -> BlockEntityType.Builder.of(FrameBlockTile::new, FENCE_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<BedFrameBlock> BED_FRAMEBLOCK = BLOCKS.register("frame_bed", () -> new BedFrameBlock(DyeColor.BROWN, Block.Properties.copy(FRAMEBLOCK.get()).noOcclusion()));
    public static final RegistryObject<Item> BED_FRAME_ITEM = ITEMS.register("frame_bed", () -> new BlockItem(BED_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<BedFrameTile>> BED_FRAME_TILE = TILES.register("frame_bed", () -> BlockEntityType.Builder.of(BedFrameTile::new, BED_FRAMEBLOCK.get()).build(null));
    //TODO this does not work...
    public static final RegistryObject<PoiType> BED_FRAME_POI = POI_TYPES.register("frame_bed", () -> new PoiType("frame_bed_poit", PoiType.getBlockStates(Registration.BED_FRAMEBLOCK.get()), 1, 1));

    public static final RegistryObject<WallFrameBlock> WALL_FRAMEBLOCK = BLOCKS.register("frame_wall", () -> new WallFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> WALL_FRAME_ITEM = ITEMS.register("frame_wall", () -> new BlockItem(WALL_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> WALL_FRAME_TILE = TILES.register("frame_wall", () -> BlockEntityType.Builder.of(FrameBlockTile::new, WALL_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<LadderFrameBlock> LADDER_FRAMEBLOCK = BLOCKS.register("frame_ladder", () -> new LadderFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> LADDER_FRAME_ITEM = ITEMS.register("frame_ladder", () -> new BlockItem(LADDER_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> LADDER_FRAME_TILE = TILES.register("frame_ladder", () -> BlockEntityType.Builder.of(FrameBlockTile::new, LADDER_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<ChestFrameBlock> CHEST_FRAMEBLOCK = BLOCKS.register("frame_chest", () -> new ChestFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> CHEST_FRAME_ITEM = ITEMS.register("frame_chest", () -> new BlockItem(CHEST_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<ChestFrameTileEntity>> CHEST_FRAME_TILE = TILES.register("frame_chest", () -> BlockEntityType.Builder.of(ChestFrameTileEntity::new, CHEST_FRAMEBLOCK.get()).build(null));
    public static final RegistryObject<MenuType<ChestFrameContainer>> CHEST_FRAME_CONTAINER = CONTAINERS.register("frame_chest", () -> IForgeContainerType.create(ChestFrameContainer::new));

    public static final RegistryObject<FenceGateFrameBlock> FENCE_GATE_FRAMEBLOCK = BLOCKS.register("frame_fence_gate", () -> new FenceGateFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> FENCE_GATE_FRAME_ITEM = ITEMS.register("frame_fence_gate", () -> new BlockItem(FENCE_GATE_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> FENCE_GATE_FRAME_TILE = TILES.register("frame_fence_gate", () -> BlockEntityType.Builder.of(FrameBlockTile::new, FENCE_GATE_FRAMEBLOCK.get()).build(null));

    //disabled signs - TODO fix signs
    //public static final RegistryObject<StandingSignFrameBlock> STANDING_SIGN_FRAMEBLOCK = BLOCKS.register("standing_frame_sign", () -> new StandingSignFrameBlock(Block.Properties.from(FRAMEBLOCK.get())));
    //public static final RegistryObject<WallSignFrameBlock> WALL_SIGN_FRAMEBLOCK = BLOCKS.register("wall_frame_sign", () -> new WallSignFrameBlock(Block.Properties.from(FRAMEBLOCK.get())));
    //public static final RegistryObject<Item> SIGN_FRAME_ITEM = ITEMS.register("frame_sign", () -> new FrameSignItem((new Item.Properties()).maxStackSize(16).group(BlockCarpentryMain.BlockCarpentryItemGroup.BLOCK_CARPENTRY), Registration.STANDING_SIGN_FRAMEBLOCK.get(), Registration.WALL_SIGN_FRAMEBLOCK.get()));
    //public static final RegistryObject<TileEntityType<SignFrameTile>> SIGN_FRAME_TILE = TILES.register("frame_sign", () -> TileEntityType.Builder.create(SignFrameTile::new, STANDING_SIGN_FRAMEBLOCK.get(), WALL_SIGN_FRAMEBLOCK.get()).build(null));

    //TODO WIP - may be removed or rewritten in the future
    public static final RegistryObject<SlopeFrameBlock> SLOPE_FRAMEBLOCK = BLOCKS.register("frame_slope", () -> new SlopeFrameBlock(() -> FRAMEBLOCK.get().defaultBlockState(), Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> SLOPE_FRAME_ITEM = ITEMS.register("frame_slope", () -> new BlockItem(SLOPE_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> SLOPE_FRAME_TILE = TILES.register("frame_slope", () -> BlockEntityType.Builder.of(FrameBlockTile::new, SLOPE_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<SlopeFrameBlock> EDGED_SLOPE_FRAMEBLOCK = BLOCKS.register("frame_edged_slope", () -> new SlopeFrameBlock(() -> FRAMEBLOCK.get().defaultBlockState(), Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> EDGED_SLOPE_FRAME_ITEM = ITEMS.register("frame_edged_slope", () -> new BlockItem(EDGED_SLOPE_FRAMEBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> EDGED_SLOPE_FRAME_TILE = TILES.register("frame_edged_slope", () -> BlockEntityType.Builder.of(FrameBlockTile::new, EDGED_SLOPE_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<SixWaySlabFrameBlock> SLAB_ILLUSIONBLOCK = BLOCKS.register("illusion_slab", () -> new SixWaySlabFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> SLAB_ILLUSION_ITEM = ITEMS.register("illusion_slab", () -> new BlockItem(SLAB_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> SLAB_ILLUSION_TILE = TILES.register("illusion_slab", () -> BlockEntityType.Builder.of(FrameBlockTile::new, SLAB_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<ButtonFrameBlock> BUTTON_ILLUSIONBLOCK = BLOCKS.register("illusion_button", () -> new ButtonFrameBlock(Block.Properties.copy(FRAMEBLOCK.get()).noOcclusion().noCollission()));
    public static final RegistryObject<Item> BUTTON_ILLUSION_ITEM = ITEMS.register("illusion_button", () -> new BlockItem(BUTTON_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> BUTTON_ILLUSION_TILE = TILES.register("illusion_button", () -> BlockEntityType.Builder.of(FrameBlockTile::new, BUTTON_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<PressurePlateFrameBlock> PRESSURE_PLATE_ILLUSIONBLOCK = BLOCKS.register("illusion_pressure_plate", () -> new PressurePlateFrameBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.copy(FRAMEBLOCK.get()).noOcclusion().noCollission()));
    public static final RegistryObject<Item> PRESSURE_PLATE_ILLUSION_ITEM = ITEMS.register("illusion_pressure_plate", () -> new BlockItem(PRESSURE_PLATE_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> PRESSURE_PLATE_ILLUSION_TILE = TILES.register("illusion_pressure_plate", () -> BlockEntityType.Builder.of(FrameBlockTile::new, BUTTON_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<StairsFrameBlock> STAIRS_ILLUSIONBLOCK = BLOCKS.register("illusion_stairs", () -> new StairsFrameBlock(() -> FRAMEBLOCK.get().defaultBlockState(), Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> STAIRS_ILLUSION_ITEM = ITEMS.register("illusion_stairs", () -> new BlockItem(STAIRS_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> STAIRS_ILLUSION_TILE = TILES.register("illusion_stairs", () -> BlockEntityType.Builder.of(FrameBlockTile::new, STAIRS_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<DoorFrameBlock> DOOR_ILLUSIONBLOCK = BLOCKS.register("illusion_door", () -> new DoorFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> DOOR_ILLUSION_ITEM = ITEMS.register("illusion_door", () -> new BlockItem(DOOR_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> DOOR_ILLUSION_TILE = TILES.register("illusion_door", () -> BlockEntityType.Builder.of(FrameBlockTile::new, DOOR_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<TrapdoorFrameBlock> TRAPDOOR_ILLUSIONBLOCK = BLOCKS.register("illusion_trapdoor", () -> new TrapdoorFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> TRAPDOOR_ILLUSION_ITEM = ITEMS.register("illusion_trapdoor", () -> new BlockItem(TRAPDOOR_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> TRAPDOOR_ILLUSION_TILE = TILES.register("illusion_trapdoor", () -> BlockEntityType.Builder.of(FrameBlockTile::new, TRAPDOOR_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<FenceFrameBlock> FENCE_ILLUSIONBLOCK = BLOCKS.register("illusion_fence", () -> new FenceFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> FENCE_ILLUSION_ITEM = ITEMS.register("illusion_fence", () -> new BlockItem(FENCE_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> FENCE_ILLUSION_TILE = TILES.register("illusion_fence", () -> BlockEntityType.Builder.of(FrameBlockTile::new, FENCE_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<BedFrameBlock> BED_ILLUSIONBLOCK = BLOCKS.register("illusion_bed", () -> new BedFrameBlock(DyeColor.BROWN, Block.Properties.copy(FRAMEBLOCK.get()).noOcclusion()));
    public static final RegistryObject<Item> BED_ILLUSION_ITEM = ITEMS.register("illusion_bed", () -> new BlockItem(BED_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<BedFrameTile>> BED_ILLUSION_TILE = TILES.register("illusion_bed", () -> BlockEntityType.Builder.of(BedFrameTile::new, BED_FRAMEBLOCK.get()).build(null));
    public static final RegistryObject<PoiType> BED_ILLUSION_POI = POI_TYPES.register("illusion_bed", () -> new PoiType("illusion_bed", PoiType.getBlockStates(Registration.BED_ILLUSIONBLOCK.get()), 1, PoiType.ALL, 1));

    public static final RegistryObject<WallFrameBlock> WALL_ILLUSIONBLOCK = BLOCKS.register("illusion_wall", () -> new WallFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> WALL_ILLUSION_ITEM = ITEMS.register("illusion_wall", () -> new BlockItem(WALL_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> WALL_ILLUSION_TILE = TILES.register("illusion_wall", () -> BlockEntityType.Builder.of(FrameBlockTile::new, WALL_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<LadderFrameBlock> LADDER_ILLUSIONBLOCK = BLOCKS.register("illusion_ladder", () -> new LadderFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> LADDER_ILLUSION_ITEM = ITEMS.register("illusion_ladder", () -> new BlockItem(LADDER_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> LADDER_ILLUSION_TILE = TILES.register("illusion_ladder", () -> BlockEntityType.Builder.of(FrameBlockTile::new, LADDER_FRAMEBLOCK.get()).build(null));

    public static final RegistryObject<ChestFrameBlock> CHEST_ILLUSIONBLOCK = BLOCKS.register("illusion_chest", () -> new ChestFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> CHEST_ILLUSION_ITEM = ITEMS.register("illusion_chest", () -> new BlockItem(CHEST_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<ChestFrameTileEntity>> CHEST_ILLUSION_TILE = TILES.register("illusion_chest", () -> BlockEntityType.Builder.of(ChestFrameTileEntity::new, CHEST_ILLUSIONBLOCK.get()).build(null));
    public static final RegistryObject<MenuType<IllusionChestContainer>> CHEST_ILLUSION_CONTAINER = CONTAINERS.register("illusion_chest", () -> IForgeContainerType.create(IllusionChestContainer::new));

    public static final RegistryObject<FenceGateFrameBlock> FENCE_GATE_ILLUSIONBLOCK = BLOCKS.register("illusion_fence_gate", () -> new FenceGateFrameBlock(Block.Properties.copy(FRAMEBLOCK.get())));
    public static final RegistryObject<Item> FENCE_GATE_ILLUSION_ITEM = ITEMS.register("illusion_fence_gate", () -> new BlockItem(FENCE_GATE_ILLUSIONBLOCK.get(), new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS)));
    public static final RegistryObject<BlockEntityType<FrameBlockTile>> FENCE_GATE_ILLUSION_TILE = TILES.register("illusion_fence_gate", () -> BlockEntityType.Builder.of(FrameBlockTile::new, FENCE_GATE_ILLUSIONBLOCK.get()).build(null));

    public static void initialize() {
        LOGGER.info("Registering blocks from QTextureModels");
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("Registering items from QTextureModels");
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("Registering tiles from QTextureModels");
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("Registering containers from QTextureModels");
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        //ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        //DIMENSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        LOGGER.info("Registering points of interest from Blockcarpentry");
        POI_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new Item(new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS).stacksTo(1)));
    public static final RegistryObject<Item> TEXTURE_WRENCH = ITEMS.register("texture_wrench", () -> new Item(new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS).stacksTo(1)));
    public static final RegistryObject<Item> CHISEL = ITEMS.register("chisel", () -> new Item(new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS).stacksTo(1)));
    public static final RegistryObject<Item> PAINTBRUSH = ITEMS.register("paintbrush", () -> new Item(new Item.Properties().tab(QTextureModels.QTexturedModelsItemGroup.QTEXTUREMODELS).stacksTo(1)));

}
//========SOLI DEO GLORIA========//