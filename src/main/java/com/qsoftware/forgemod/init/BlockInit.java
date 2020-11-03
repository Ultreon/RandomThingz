package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.objects.blocks.GamePcBlock;
import com.qsoftware.forgemod.objects.blocks.QuarryBlock;
import com.qsoftware.forgemod.objects.blocks.base.*;
import com.qsoftware.forgemod.objects.blocks.furniture.OldWoodenCrate;
import com.qsoftware.forgemod.objects.items.base.FaceableBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, QForgeUtils.MOD_ID);

    // Doors
    public static final RegistryObject<FlowerBlock> BUTTERCUP = BLOCKS.register("buttercup", () -> new FlowerBlock(Effects.ABSORPTION, 8, Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()));

    // Doors
    public static final RegistryObject<DoorBlock> LAB_DOOR = BLOCKS.register("lab_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()));
    public static final RegistryObject<DoorBlock> SHOPPING_DOOR = BLOCKS.register("shopping_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()));
    public static final RegistryObject<DoorBlock> IRON_GLASS_DOOR = BLOCKS.register("iron_glass_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()));
    public static final RegistryObject<DoorBlock> IRON_BARRIER_DOOR = BLOCKS.register("iron_barrier_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()));

    // Wood
    public static final RegistryObject<Block> EUCALYPTUS_PLANKS = BLOCKS.register("eucalyptus_planks", () -> new BaseBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> EUCALYPTUS_LOG = BLOCKS.register("eucalyptus_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.QUARTZ).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));

    // Furniture
    public static final RegistryObject<Block> MODERN_SOFA = BLOCKS.register("modern_sofa", () -> new FaceableBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GAME_PC = BLOCKS.register("game_pc", () -> new GamePcBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> ROUTER = BLOCKS.register("router", () -> new FaceableBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)));

    // Stairs & Slabs
    public static final RegistryObject<StairsBlock> EUCALYPTUS_STAIRS = BLOCKS.register("eucalyptus_stairs", () -> new StairsBlock(EUCALYPTUS_PLANKS.get()::getDefaultState, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> EUCALYPTUS_SLAB = BLOCKS.register("eucalyptus_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));

    // Fences
    public static final RegistryObject<FenceBlock> EUCALYPTUS_FENCE = BLOCKS.register("eucalyptus_fence", () -> new BaseFenceBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> EUCALYPTUS_FENCE_GATE = BLOCKS.register("eucalyptus_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));

    // Buttons & Pressure plates.
    public static final RegistryObject<WoodButtonBlock> EUCALYPTUS_BUTTON = BLOCKS.register("eucalyptus_button", () -> new BaseWoodButtonBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
    public static final RegistryObject<PressurePlateBlock> EUCALYPTUS_PRESSURE_PLATE = BLOCKS.register("eucalyptus_pressure_plate", () -> new BasePressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));

    // Tile entity
    public static final RegistryObject<Block> QUARRY_BLOCK = BLOCKS.register("quarry", () -> new QuarryBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.9f, 2.9f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> WOODEN_CRATE = BLOCKS.register("wooden_crate", () -> new OldWoodenCrate(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.9f).sound(SoundType.WOOD)));

    // Ore
    public static final RegistryObject<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> STEEL_ORE = BLOCKS.register("steel_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.125f, 3.375f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TUNGSTEN_ORE = BLOCKS.register("tungsten_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.125f, 6.425f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.325f, 4.5625f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RUBY_ORE = BLOCKS.register("ruby_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.75f, 2.875f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AMETHYST_ORE = BLOCKS.register("amethyst_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.25f, 2.375f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AQUAMARINE_ORE = BLOCKS.register("aquamarine_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.155f, 2.4635f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SAPHIRE_ORE = BLOCKS.register("saphire_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.363f, 2.8460f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> MALACHITE_ORE = BLOCKS.register("malachite_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TANZANITE_ORE = BLOCKS.register("tanzanite_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ULTRINIUM_ORE = BLOCKS.register("ultrinium_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(12.9f, 9999999.9f).sound(SoundType.STONE).harvestLevel(3)));
    public static final RegistryObject<Block> INFINITY_ORE = BLOCKS.register("infinity_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(64.5f, Float.MAX_VALUE).sound(SoundType.STONE).harvestLevel(4)));

    // Solid Gem / Metal block
    public static final RegistryObject<Block> COPPER_BLOCK = BLOCKS.register("copper_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.9f, 2.9f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3.9875f, 4.275f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TUNGSTEN_BLOCK = BLOCKS.register("tungsten_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(5.9875f, 6.5525f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> URANIUM_BLOCK = BLOCKS.register("uranium_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.8345f, 4.4675f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RUBY_BLOCK = BLOCKS.register("ruby_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.25f, 5.5f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AMETHYST_BLOCK = BLOCKS.register("amethyst_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.875f, 4.0625f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AQUAMARINE_BLOCK = BLOCKS.register("aquamarine_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.995f, 4.1275f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SAPHIRE_BLOCK = BLOCKS.register("saphire_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.120f, 4.5735f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> MALACHITE_BLOCK = BLOCKS.register("malachite_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.8914f, 5.06635f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TANZANITE_BLOCK = BLOCKS.register("tanzanite_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(4.26f, 5.5f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ULTRINIUM_BLOCK = BLOCKS.register("ultrinium_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(12.9f, 99999999.9f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> INFINITY_BLOCK = BLOCKS.register("infinity_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(64.5f, Float.MAX_VALUE).sound(SoundType.STONE)));

    // TNT Blocks
//    public static final RegistryObject<TNTBlock> TNT_5 = BLOCKS.register(() -> new AdvancedTNTBlock("tnt_5", Block.Properties.create(Material.TNT).hardnessAndResistance(1.9f, 1.5f).sound(SoundType.PLANT)));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, QForgeUtils.MOD_ID);
    // Flowers
    public static final RegistryObject<Item> BUTTERCUP_ITEM = ITEMS.register("buttercup", () -> new BlockItem(BUTTERCUP.get(), new Item.Properties().group(Groups.NATURE)));

    // Wood
    public static final RegistryObject<Item> EUCALYPTUS_LOG_ITEM = ITEMS.register("eucalyptus_log", () -> new BlockItem(EUCALYPTUS_LOG.get(), new Item.Properties().group(Groups.LOGS)));
    public static final RegistryObject<Item> EUCALYPTUS_PLANKS_ITEM = ITEMS.register("eucalyptus_planks", () -> new BlockItem(EUCALYPTUS_PLANKS.get(), new Item.Properties().maxStackSize(16).group(Groups.WOOD)));

    // Doors Items
    public static final RegistryObject<Item> LAB_DOOR_ITEM = ITEMS.register("lab_door", () -> new TallBlockItem(BlockInit.LAB_DOOR.get(), (new Item.Properties()).group(Groups.REDSTONE)));
    public static final RegistryObject<Item> SHOPPING_DOOR_ITEM = ITEMS.register("shopping_door", () -> new TallBlockItem(BlockInit.SHOPPING_DOOR.get(), (new Item.Properties()).group(Groups.REDSTONE)));
    public static final RegistryObject<Item> IRON_GLASS_DOOR_ITEM = ITEMS.register("iron_glass_door", () -> new TallBlockItem(BlockInit.IRON_GLASS_DOOR.get(), (new Item.Properties()).group(Groups.REDSTONE)));
    public static final RegistryObject<Item> IRON_BARRIER_DOOR_ITEM = ITEMS.register("iron_barrier_door", () -> new TallBlockItem(BlockInit.IRON_BARRIER_DOOR.get(), (new Item.Properties()).group(Groups.REDSTONE)));

    // Furniture
    public static final RegistryObject<Item> MODERN_SOFA_ITEM = ITEMS.register("modern_sofa", () -> new BlockItem(MODERN_SOFA.get(), new Item.Properties().maxStackSize(32).group(Groups.WOOD)));
    public static final RegistryObject<Item> GAME_PC_ITEM = ITEMS.register("game_pc", () -> new BlockItem(GAME_PC.get(), new Item.Properties().maxStackSize(4).group(Groups.WOOD)));
    public static final RegistryObject<Item> ROUTER_ITEM = ITEMS.register("router", () -> new BlockItem(ROUTER.get(), new Item.Properties().maxStackSize(6).group(Groups.WOOD)));

    // Stairs & Slabs
    public static final RegistryObject<Item> EUCALYPTUS_STAIRS_ITEM = ITEMS.register("eucalyptus_stairs", () -> new BlockItem(EUCALYPTUS_STAIRS.get(), new Item.Properties().group(Groups.SHAPES)));
    public static final RegistryObject<Item> EUCALYPTUS_SLAB_ITEM = ITEMS.register("eucalyptus_slab", () -> new BlockItem(EUCALYPTUS_SLAB.get(), new Item.Properties().group(Groups.SHAPES)));

    // Fences
    public static final RegistryObject<Item> EUCALYPTUS_FENCE_ITEM = ITEMS.register("eucalyptus_fence", () -> new BlockItem(EUCALYPTUS_FENCE.get(), new Item.Properties().group(Groups.SHAPES)));
    public static final RegistryObject<Item> EUCALYPTUS_FENCE_GATE_ITEM = ITEMS.register("eucalyptus_fence_gate", () -> new BlockItem(EUCALYPTUS_FENCE_GATE.get(), new Item.Properties().group(Groups.SHAPES)));

    // Buttons & Pressure plates
    public static final RegistryObject<Item> EUCALYPTUS_BUTTON_ITEM = ITEMS.register("eucalyptus_button", () -> new BlockItem(EUCALYPTUS_BUTTON.get(), new Item.Properties().group(Groups.SHAPES)));
    public static final RegistryObject<Item> EUCALYPTUS_PRESSURE_PLATE_ITEM = ITEMS.register("eucalyptus_pressure_plate", () -> new BlockItem(EUCALYPTUS_PRESSURE_PLATE.get(), new Item.Properties().group(Groups.SHAPES)));

    // Tile entity
    public static final RegistryObject<Item> QUARRY_BLOCK_ITEM = ITEMS.register("quarry", () -> new BlockItem(QUARRY_BLOCK.get(), new Item.Properties().group(Groups.MACHINES)));
    public static final RegistryObject<Item> WOODEN_CRATE_ITEM = ITEMS.register("wooden_crate", () -> new BlockItem(WOODEN_CRATE.get(), new Item.Properties().group(Groups.MACHINES)));

    // Solid Ore & material
    public static final RegistryObject<Item> COPPER_ORE_ITEM = ITEMS.register("copper_ore", () -> new BlockItem(COPPER_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> COPPER_BLOCK_ITEM = ITEMS.register("copper_block", () -> new BlockItem(COPPER_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> STEEL_ORE_ITEM = ITEMS.register("steel_ore", () -> new BlockItem(STEEL_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> STEEL_BLOCK_ITEM = ITEMS.register("steel_block", () -> new BlockItem(STEEL_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> TUNGSTEN_ORE_ITEM = ITEMS.register("tungsten_ore", () -> new BlockItem(TUNGSTEN_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> TUNGSTEN_BLOCK_ITEM = ITEMS.register("tungsten_block", () -> new BlockItem(TUNGSTEN_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> URANIUM_ORE_ITEM = ITEMS.register("uranium_ore", () -> new BlockItem(URANIUM_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> URANIUM_BLOCK_ITEM = ITEMS.register("uranium_block", () -> new BlockItem(URANIUM_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> RUBY_ORE_ITEM = ITEMS.register("ruby_ore", () -> new BlockItem(RUBY_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> RUBY_BLOCK_ITEM = ITEMS.register("ruby_block", () -> new BlockItem(RUBY_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> AMETHYST_ORE_ITEM = ITEMS.register("amethyst_ore", () -> new BlockItem(AMETHYST_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> AMETHYST_BLOCK_ITEM = ITEMS.register("amethyst_block", () -> new BlockItem(AMETHYST_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> AQUAMARINE_ORE_ITEM = ITEMS.register("aquamarine_ore", () -> new BlockItem(AQUAMARINE_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> AQUAMARINE_BLOCK_ITEM = ITEMS.register("aquamarine_block", () -> new BlockItem(AQUAMARINE_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> SAPHIRE_ORE_ITEM = ITEMS.register("saphire_ore", () -> new BlockItem(SAPHIRE_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> SAPHIRE_BLOCK_ITEM = ITEMS.register("saphire_block", () -> new BlockItem(SAPHIRE_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> MALACHITE_ORE_ITEM = ITEMS.register("malachite_ore", () -> new BlockItem(MALACHITE_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> MALACHITE_BLOCK_ITEM = ITEMS.register("malachite_block", () -> new BlockItem(MALACHITE_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> TANZANITE_ORE_ITEM = ITEMS.register("tanzanite_ore", () -> new BlockItem(TANZANITE_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> TANZANITE_BLOCK_ITEM = ITEMS.register("tanzanite_block", () -> new BlockItem(TANZANITE_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> ULTRINIUM_ORE_ITEM = ITEMS.register("ultrinium_ore", () -> new BlockItem(ULTRINIUM_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> ULTRINIUM_BLOCK_ITEM = ITEMS.register("ultrinium_block", () -> new BlockItem(ULTRINIUM_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> INFINITY_ORE_ITEM = ITEMS.register("infinity_ore", () -> new BlockItem(INFINITY_ORE.get(), new Item.Properties().group(Groups.ORES)));
    public static final RegistryObject<Item> INFINITY_BLOCK_ITEM = ITEMS.register("infinity_block", () -> new BlockItem(INFINITY_BLOCK.get(), new Item.Properties().group(Groups.ORES)));
}
