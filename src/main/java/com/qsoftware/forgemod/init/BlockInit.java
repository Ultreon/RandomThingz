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
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, QForgeUtils.MOD_ID);

    // Doors
    public static final FlowerBlock BUTTERCUP = register("buttercup", () -> new FlowerBlock(Effects.ABSORPTION, 8, Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()));

    // Doors
    public static final DoorBlock LAB_DOOR = register("lab_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()));
    public static final DoorBlock SHOPPING_DOOR = register("shopping_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()));
    public static final DoorBlock IRON_GLASS_DOOR = register("iron_glass_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()));
    public static final DoorBlock IRON_BARRIER_DOOR = register("iron_barrier_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()));

    // Wood
    public static final Block EUCALYPTUS_PLANKS = register("eucalyptus_planks", () -> new BaseBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD)));
    public static final Block EUCALYPTUS_LOG = register("eucalyptus_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.QUARTZ).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));

    // Furniture
    public static final Block MODERN_SOFA = register("modern_sofa", () -> new FaceableBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD)));
    public static final Block GAME_PC = register("game_pc", () -> new GamePcBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)));
    public static final Block ROUTER = register("router", () -> new FaceableBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)));

    // Stairs & Slabs
    public static final StairsBlock EUCALYPTUS_STAIRS = register("eucalyptus_stairs", () -> new StairsBlock(EUCALYPTUS_PLANKS::getDefaultState, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
    public static final SlabBlock EUCALYPTUS_SLAB = register("eucalyptus_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));

    // Fences
    public static final FenceBlock EUCALYPTUS_FENCE = register("eucalyptus_fence", () -> new BaseFenceBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
    public static final FenceGateBlock EUCALYPTUS_FENCE_GATE = register("eucalyptus_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));

    // Buttons & Pressure plates.
    public static final WoodButtonBlock EUCALYPTUS_BUTTON = register("eucalyptus_button", () -> new BaseWoodButtonBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
    public static final PressurePlateBlock EUCALYPTUS_PRESSURE_PLATE = register("eucalyptus_pressure_plate", () -> new BasePressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));

    // Tile entity
    public static final Block QUARRY_BLOCK = register("quarry", () -> new QuarryBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.9f, 2.9f).sound(SoundType.STONE)));
    public static final Block WOODEN_CRATE = register("wooden_crate", () -> new OldWoodenCrate(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.9f).sound(SoundType.WOOD)));

    // Ore
    public static final Block COPPER_ORE = register("copper_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE)));
    public static final Block STEEL_ORE = register("steel_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.125f, 3.375f).sound(SoundType.STONE)));
    public static final Block TUNGSTEN_ORE = register("tungsten_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.125f, 6.425f).sound(SoundType.STONE)));
    public static final Block URANIUM_ORE = register("uranium_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.325f, 4.5625f).sound(SoundType.STONE)));
    public static final Block RUBY_ORE = register("ruby_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.75f, 2.875f).sound(SoundType.STONE)));
    public static final Block AMETHYST_ORE = register("amethyst_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.25f, 2.375f).sound(SoundType.STONE)));
    public static final Block AQUAMARINE_ORE = register("aquamarine_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.155f, 2.4635f).sound(SoundType.STONE)));
    public static final Block SAPHIRE_ORE = register("saphire_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.363f, 2.8460f).sound(SoundType.STONE)));
    public static final Block MALACHITE_ORE = register("malachite_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE)));
    public static final Block TANZANITE_ORE = register("tanzanite_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE)));
    public static final Block ULTRINIUM_ORE = register("ultrinium_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(12.9f, 9999999.9f).sound(SoundType.STONE).harvestLevel(3)));
    public static final Block INFINITY_ORE = register("infinity_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(64.5f, Float.MAX_VALUE).sound(SoundType.STONE).harvestLevel(4)));

    // Solid Gem / Metal block
    public static final Block COPPER_BLOCK = register("copper_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.9f, 2.9f).sound(SoundType.STONE)));
    public static final Block STEEL_BLOCK = register("steel_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3.9875f, 4.275f).sound(SoundType.STONE)));
    public static final Block TUNGSTEN_BLOCK = register("tungsten_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(5.9875f, 6.5525f).sound(SoundType.STONE)));
    public static final Block URANIUM_BLOCK = register("uranium_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.8345f, 4.4675f).sound(SoundType.STONE)));
    public static final Block RUBY_BLOCK = register("ruby_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.25f, 5.5f).sound(SoundType.STONE)));
    public static final Block AMETHYST_BLOCK = register("amethyst_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.875f, 4.0625f).sound(SoundType.STONE)));
    public static final Block AQUAMARINE_BLOCK = register("aquamarine_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.995f, 4.1275f).sound(SoundType.STONE)));
    public static final Block SAPHIRE_BLOCK = register("saphire_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.120f, 4.5735f).sound(SoundType.STONE)));
    public static final Block MALACHITE_BLOCK = register("malachite_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.8914f, 5.06635f).sound(SoundType.STONE)));
    public static final Block TANZANITE_BLOCK = register("tanzanite_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(4.26f, 5.5f).sound(SoundType.STONE)));
    public static final Block ULTRINIUM_BLOCK = register("ultrinium_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(12.9f, 99999999.9f).sound(SoundType.STONE)));
    public static final Block INFINITY_BLOCK = register("infinity_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(64.5f, Float.MAX_VALUE).sound(SoundType.STONE)));

    private static <T extends Block> T register(String name, Supplier<T> supplier) {
        BLOCKS.register(name, supplier);
        return supplier.get();
    }

    // TNT Blocks
//    public static final TNTBlock TNT_5 = register(() -> new AdvancedTNTBlock("tnt_5", Block.Properties.create(Material.TNT).hardnessAndResistance(1.9f, 1.5f).sound(SoundType.PLANT)));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, QForgeUtils.MOD_ID);
    // Flowers
    public static final Item BUTTERCUP_ITEM = registerItem("buttercup", () -> new BlockItem(BUTTERCUP, new Item.Properties().group(Groups.NATURE)));

    // Wood
    public static final Item EUCALYPTUS_LOG_ITEM = registerItem("eucalyptus_log", () -> new BlockItem(EUCALYPTUS_LOG, new Item.Properties().group(Groups.LOGS)));
    public static final Item EUCALYPTUS_PLANKS_ITEM = registerItem("eucalyptus_planks", () -> new BlockItem(EUCALYPTUS_PLANKS, new Item.Properties().maxStackSize(16).group(Groups.WOOD)));

    // Doors Items
    public static final Item LAB_DOOR_ITEM = registerItem("lab_door", () -> new TallBlockItem(BlockInit.LAB_DOOR, (new Item.Properties()).group(Groups.REDSTONE)));
    public static final Item SHOPPING_DOOR_ITEM = registerItem("shopping_door", () -> new TallBlockItem(BlockInit.SHOPPING_DOOR, (new Item.Properties()).group(Groups.REDSTONE)));
    public static final Item IRON_GLASS_DOOR_ITEM = registerItem("iron_glass_door", () -> new TallBlockItem(BlockInit.IRON_GLASS_DOOR, (new Item.Properties()).group(Groups.REDSTONE)));
    public static final Item IRON_BARRIER_DOOR_ITEM = registerItem("iron_barrier_door", () -> new TallBlockItem(BlockInit.IRON_BARRIER_DOOR, (new Item.Properties()).group(Groups.REDSTONE)));

    // Furniture
    public static final Item MODERN_SOFA_ITEM = registerItem("modern_sofa", () -> new BlockItem(MODERN_SOFA, new Item.Properties().maxStackSize(32).group(Groups.WOOD)));
    public static final Item GAME_PC_ITEM = registerItem("game_pc", () -> new BlockItem(GAME_PC, new Item.Properties().maxStackSize(4).group(Groups.WOOD)));
    public static final Item ROUTER_ITEM = registerItem("router", () -> new BlockItem(ROUTER, new Item.Properties().maxStackSize(6).group(Groups.WOOD)));

    // Stairs & Slabs
    public static final Item EUCALYPTUS_STAIRS_ITEM = registerItem("eucalyptus_stairs", () -> new BlockItem(EUCALYPTUS_STAIRS, new Item.Properties().group(Groups.SHAPES)));
    public static final Item EUCALYPTUS_SLAB_ITEM = registerItem("eucalyptus_slab", () -> new BlockItem(EUCALYPTUS_SLAB, new Item.Properties().group(Groups.SHAPES)));

    // Fences
    public static final Item EUCALYPTUS_FENCE_ITEM = registerItem("eucalyptus_fence", () -> new BlockItem(EUCALYPTUS_FENCE, new Item.Properties().group(Groups.SHAPES)));
    public static final Item EUCALYPTUS_FENCE_GATE_ITEM = registerItem("eucalyptus_fence_gate", () -> new BlockItem(EUCALYPTUS_FENCE_GATE, new Item.Properties().group(Groups.SHAPES)));

    // Buttons & Pressure plates
    public static final Item EUCALYPTUS_BUTTON_ITEM = registerItem("eucalyptus_button", () -> new BlockItem(EUCALYPTUS_BUTTON, new Item.Properties().group(Groups.SHAPES)));
    public static final Item EUCALYPTUS_PRESSURE_PLATE_ITEM = registerItem("eucalyptus_pressure_plate", () -> new BlockItem(EUCALYPTUS_PRESSURE_PLATE, new Item.Properties().group(Groups.SHAPES)));

    // Tile entity
    public static final Item QUARRY_BLOCK_ITEM = registerItem("quarry", () -> new BlockItem(QUARRY_BLOCK, new Item.Properties().group(Groups.MACHINES)));
    public static final Item WOODEN_CRATE_ITEM = registerItem("wooden_crate", () -> new BlockItem(WOODEN_CRATE, new Item.Properties().group(Groups.MACHINES)));

    // Solid Ore & material
    public static final Item COPPER_ORE_ITEM = registerItem("copper_ore", () -> new BlockItem(COPPER_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item COPPER_BLOCK_ITEM = registerItem("copper_block", () -> new BlockItem(COPPER_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item STEEL_ORE_ITEM = registerItem("steel_ore", () -> new BlockItem(STEEL_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item STEEL_BLOCK_ITEM = registerItem("steel_block", () -> new BlockItem(STEEL_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item TUNGSTEN_ORE_ITEM = registerItem("tungsten_ore", () -> new BlockItem(TUNGSTEN_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item TUNGSTEN_BLOCK_ITEM = registerItem("tungsten_block", () -> new BlockItem(TUNGSTEN_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item URANIUM_ORE_ITEM = registerItem("uranium_ore", () -> new BlockItem(URANIUM_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item URANIUM_BLOCK_ITEM = registerItem("uranium_block", () -> new BlockItem(URANIUM_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item RUBY_ORE_ITEM = registerItem("ruby_ore", () -> new BlockItem(RUBY_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item RUBY_BLOCK_ITEM = registerItem("ruby_block", () -> new BlockItem(RUBY_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item AMETHYST_ORE_ITEM = registerItem("amethyst_ore", () -> new BlockItem(AMETHYST_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item AMETHYST_BLOCK_ITEM = registerItem("amethyst_block", () -> new BlockItem(AMETHYST_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item AQUAMARINE_ORE_ITEM = registerItem("aquamarine_ore", () -> new BlockItem(AQUAMARINE_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item AQUAMARINE_BLOCK_ITEM = registerItem("aquamarine_block", () -> new BlockItem(AQUAMARINE_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item SAPHIRE_ORE_ITEM = registerItem("saphire_ore", () -> new BlockItem(SAPHIRE_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item SAPHIRE_BLOCK_ITEM = registerItem("saphire_block", () -> new BlockItem(SAPHIRE_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item MALACHITE_ORE_ITEM = registerItem("malachite_ore", () -> new BlockItem(MALACHITE_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item MALACHITE_BLOCK_ITEM = registerItem("malachite_block", () -> new BlockItem(MALACHITE_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item TANZANITE_ORE_ITEM = registerItem("tanzanite_ore", () -> new BlockItem(TANZANITE_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item TANZANITE_BLOCK_ITEM = registerItem("tanzanite_block", () -> new BlockItem(TANZANITE_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item ULTRINIUM_ORE_ITEM = registerItem("ultrinium_ore", () -> new BlockItem(ULTRINIUM_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item ULTRINIUM_BLOCK_ITEM = registerItem("ultrinium_block", () -> new BlockItem(ULTRINIUM_BLOCK, new Item.Properties().group(Groups.ORES)));
    public static final Item INFINITY_ORE_ITEM = registerItem("infinity_ore", () -> new BlockItem(INFINITY_ORE, new Item.Properties().group(Groups.ORES)));
    public static final Item INFINITY_BLOCK_ITEM = registerItem("infinity_block", () -> new BlockItem(INFINITY_BLOCK, new Item.Properties().group(Groups.ORES)));

    private static <T extends Item> T registerItem(String name, Supplier<T> supplier) {
        ITEMS.register(name, supplier);
        return supplier.get();
    }
}
