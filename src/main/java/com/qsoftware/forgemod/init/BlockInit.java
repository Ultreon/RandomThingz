package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.objects.blocks.furniture.OldWoodenCrate;
import com.qsoftware.forgemod.objects.blocks.GamePcBlock;
import com.qsoftware.forgemod.objects.blocks.QuarryBlock;
import com.qsoftware.forgemod.objects.blocks.base.*;
import com.qsoftware.forgemod.objects.items.base.FaceableBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
//@ObjectHolder(QForgeUtils.MOD_ID)
@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    // Doors
    public static final DoorBlock LAB_DOOR = (DoorBlock) new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()).setRegistryName("lab_door");
    public static final DoorBlock SHOPPING_DOOR = (DoorBlock) new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()).setRegistryName("shopping_door");
    public static final DoorBlock IRON_GLASS_DOOR = (DoorBlock) new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()).setRegistryName("iron_glass_door");
    public static final DoorBlock IRON_BARRIER_DOOR = (DoorBlock) new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()).setRegistryName("iron_barrier_door");

    // Doors Items
    public static final Item LAB_DOOR_ITEM = new TallBlockItem(BlockInit.LAB_DOOR, (new Item.Properties()).group(Groups.REDSTONE)).setRegistryName("lab_door");
    public static final Item SHOPPING_DOOR_ITEM = new TallBlockItem(BlockInit.SHOPPING_DOOR, (new Item.Properties()).group(Groups.REDSTONE)).setRegistryName("shopping_door");
    public static final Item IRON_GLASS_DOOR_ITEM = new TallBlockItem(BlockInit.IRON_GLASS_DOOR, (new Item.Properties()).group(Groups.REDSTONE)).setRegistryName("iron_glass_door");
    public static final Item IRON_BARRIER_DOOR_ITEM = new TallBlockItem(BlockInit.IRON_BARRIER_DOOR, (new Item.Properties()).group(Groups.REDSTONE)).setRegistryName("iron_barrier_door");

    // Wood
    public static final Block EUCALYPTUS_PLANKS = new BaseBlock("eucalyptus_planks", Block.Properties.create(Material.WOOD).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD));
    public static final Block EUCALYPTUS_LOG = new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.QUARTZ).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)).setRegistryName("eucalyptus_log");

    // Furniture
    public static final Block MODERN_SOFA = new FaceableBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD)).setRegistryName("modern_sofa");
    public static final Block GAME_PC = new GamePcBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)).setRegistryName("game_pc");
    public static final Block ROUTER = new FaceableBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)).setRegistryName("router");

    // Stairs & Slabs
    public static final StairsBlock EUCALYPTUS_STAIRS = (StairsBlock) new StairsBlock(EUCALYPTUS_PLANKS::getDefaultState, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)).setRegistryName("eucalyptus_stairs");
    public static final SlabBlock EUCALYPTUS_SLAB = (SlabBlock) new SlabBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)).setRegistryName("eucalyptus_slab");

    // Fences
    public static final FenceBlock EUCALYPTUS_FENCE = (FenceBlock) new BaseFenceBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)).setRegistryName("eucalyptus_fence");
    public static final FenceGateBlock EUCALYPTUS_FENCE_GATE = (FenceGateBlock) new FenceGateBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)).setRegistryName("eucalyptus_fence_gate");

    // Buttons & Pressure plates.
    public static final WoodButtonBlock EUCALYPTUS_BUTTON = new BaseWoodButtonBlock("eucalyptus_button", Block.Properties.create(Material.WOOD).sound(SoundType.WOOD));
    public static final PressurePlateBlock EUCALYPTUS_PRESSURE_PLATE = new BasePressurePlateBlock("eucalyptus_pressure_plate", PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD));

    // Tile entity
    public static final Block QUARRY_BLOCK = new QuarryBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.9f, 2.9f).sound(SoundType.STONE));
    public static final Block WOODEN_CRATE = new OldWoodenCrate(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.9f).sound(SoundType.WOOD)).setRegistryName("wooden_crate");

    // Ore
    public static final Block COPPER_ORE = new BaseBlock("copper_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE));
    public static final Block STEEL_ORE = new BaseBlock("steel_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(3.125f, 3.375f).sound(SoundType.STONE));
    public static final Block TUNGSTEN_ORE = new BaseBlock("tungsten_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(5.125f, 6.425f).sound(SoundType.STONE));
    public static final Block URANIUM_ORE = new BaseBlock("uranium_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(2.325f, 4.5625f).sound(SoundType.STONE));
    public static final Block RUBY_ORE = new BaseBlock("ruby_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(2.75f, 2.875f).sound(SoundType.STONE));
    public static final Block AMETHYST_ORE = new BaseBlock("amethyst_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(2.25f, 2.375f).sound(SoundType.STONE));
    public static final Block AQUAMARINE_ORE = new BaseBlock("aquamarine_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(2.155f, 2.4635f).sound(SoundType.STONE));
    public static final Block SAPHIRE_ORE = new BaseBlock("saphire_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(2.363f, 2.8460f).sound(SoundType.STONE));
    public static final Block MALACHITE_ORE = new BaseBlock("malachite_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE));
    public static final Block TANZANITE_ORE = new BaseBlock("tanzanite_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE));
    public static final Block ULTRINIUM_ORE = new BaseBlock("ultrinium_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(12.9f, 9999999.9f).sound(SoundType.STONE).harvestLevel(3));
    public static final Block INFINITY_ORE = new BaseBlock("infinity_ore", Block.Properties.create(Material.ROCK).hardnessAndResistance(64.5f, Float.MAX_VALUE).sound(SoundType.STONE).harvestLevel(4));

    // Solid Gem / Metal block
    public static final Block COPPER_BLOCK = new BaseBlock("copper_block", Block.Properties.create(Material.IRON).hardnessAndResistance(2.9f, 2.9f).sound(SoundType.STONE));
    public static final Block STEEL_BLOCK = new BaseBlock("steel_block", Block.Properties.create(Material.IRON).hardnessAndResistance(3.9875f, 4.275f).sound(SoundType.STONE));
    public static final Block TUNGSTEN_BLOCK = new BaseBlock("tungsten_block", Block.Properties.create(Material.IRON).hardnessAndResistance(5.9875f, 6.5525f).sound(SoundType.STONE));
    public static final Block URANIUM_BLOCK = new BaseBlock("uranium_block", Block.Properties.create(Material.IRON).hardnessAndResistance(2.8345f, 4.4675f).sound(SoundType.STONE));
    public static final Block RUBY_BLOCK = new BaseBlock("ruby_block", Block.Properties.create(Material.ROCK).hardnessAndResistance(4.25f, 5.5f).sound(SoundType.STONE));
    public static final Block AMETHYST_BLOCK = new BaseBlock("amethyst_block", Block.Properties.create(Material.ROCK).hardnessAndResistance(3.875f, 4.0625f).sound(SoundType.STONE));
    public static final Block AQUAMARINE_BLOCK = new BaseBlock("aquamarine_block", Block.Properties.create(Material.ROCK).hardnessAndResistance(3.995f, 4.1275f).sound(SoundType.STONE));
    public static final Block SAPHIRE_BLOCK = new BaseBlock("saphire_block", Block.Properties.create(Material.ROCK).hardnessAndResistance(4.120f, 4.5735f).sound(SoundType.STONE));
    public static final Block MALACHITE_BLOCK = new BaseBlock("malachite_block", Block.Properties.create(Material.ROCK).hardnessAndResistance(4.8914f, 5.06635f).sound(SoundType.STONE));
    public static final Block TANZANITE_BLOCK = new BaseBlock("tanzanite_block", Block.Properties.create(Material.IRON).hardnessAndResistance(4.26f, 5.5f).sound(SoundType.STONE));
    public static final Block ULTRINIUM_BLOCK = new BaseBlock("ultrinium_block", Block.Properties.create(Material.ROCK).hardnessAndResistance(12.9f, 99999999.9f).sound(SoundType.STONE));
    public static final Block INFINITY_BLOCK = new BaseBlock("infinity_block", Block.Properties.create(Material.ROCK).hardnessAndResistance(64.5f, Float.MAX_VALUE).sound(SoundType.STONE));

    // TNT Blocks
//    public static final TNTBlock TNT_5 = new AdvancedTNTBlock("tnt_5", Block.Properties.create(Material.TNT).hardnessAndResistance(1.9f, 1.5f).sound(SoundType.PLANT));

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        Class<BlockInit> clazz = BlockInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (Block.class.isAssignableFrom(field.getType())) {
                try {
                    Block block = (Block) field.get(null);
                    event.getRegistry().register(block);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field, or registering block: " + field.getName(), t);
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        Class<BlockInit> clazz = BlockInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (Item.class.isAssignableFrom(field.getType())) {
                try {
                    Item item = (Item) field.get(null);
                    event.getRegistry().register(item);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field, or registering item: " + field.getName(), t);
                }
            }
        }

//        // Doors
//        event.getRegistry().register(LAB_DOOR_ITEM);
//        event.getRegistry().register(SHOPPING_DOOR_ITEM);
//        event.getRegistry().register(IRON_GLASS_DOOR_ITEM);
//        event.getRegistry().register(IRON_BARRIER_DOOR_ITEM);

        // Wood
        event.getRegistry().register(new BlockItem(EUCALYPTUS_LOG, new Item.Properties().group(Groups.LOGS)).setRegistryName("eucalyptus_log"));
        event.getRegistry().register(new BlockItem(EUCALYPTUS_PLANKS, new Item.Properties().maxStackSize(16).group(Groups.WOOD)).setRegistryName("eucalyptus_planks"));

        // Furniture
        event.getRegistry().register(new BlockItem(MODERN_SOFA, new Item.Properties().maxStackSize(32).group(Groups.WOOD)).setRegistryName("modern_sofa"));
        event.getRegistry().register(new BlockItem(GAME_PC, new Item.Properties().maxStackSize(4).group(Groups.WOOD)).setRegistryName("game_pc"));
        event.getRegistry().register(new BlockItem(ROUTER, new Item.Properties().maxStackSize(6).group(Groups.WOOD)).setRegistryName("router"));

        // Stairs & Slabs
        event.getRegistry().register(new BlockItem(EUCALYPTUS_STAIRS, new Item.Properties().group(Groups.SHAPES)).setRegistryName("eucalyptus_stairs"));
        event.getRegistry().register(new BlockItem(EUCALYPTUS_SLAB, new Item.Properties().group(Groups.SHAPES)).setRegistryName("eucalyptus_slab"));

        // Fences
        event.getRegistry().register(new BlockItem(EUCALYPTUS_FENCE, new Item.Properties().group(Groups.SHAPES)).setRegistryName("eucalyptus_fence"));
        event.getRegistry().register(new BlockItem(EUCALYPTUS_FENCE_GATE, new Item.Properties().group(Groups.SHAPES)).setRegistryName("eucalyptus_fence_gate"));

        // Buttons & Pressure plates
        event.getRegistry().register(new BlockItem(EUCALYPTUS_BUTTON, new Item.Properties().group(Groups.SHAPES)).setRegistryName("eucalyptus_button"));
        event.getRegistry().register(new BlockItem(EUCALYPTUS_PRESSURE_PLATE, new Item.Properties().group(Groups.SHAPES)).setRegistryName("eucalyptus_pressure_plate"));

        // Tile entity
        event.getRegistry().register(new BlockItem(QUARRY_BLOCK, new Item.Properties().group(Groups.MACHINES)).setRegistryName("quarry"));
        event.getRegistry().register(new BlockItem(WOODEN_CRATE, new Item.Properties().group(Groups.MACHINES)).setRegistryName("wooden_crate"));

        // Solid Ore & material
        event.getRegistry().register(new BlockItem(COPPER_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("copper_ore"));
        event.getRegistry().register(new BlockItem(COPPER_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("copper_block"));
        event.getRegistry().register(new BlockItem(STEEL_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("steel_ore"));
        event.getRegistry().register(new BlockItem(STEEL_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("steel_block"));
        event.getRegistry().register(new BlockItem(TUNGSTEN_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("tungsten_ore"));
        event.getRegistry().register(new BlockItem(TUNGSTEN_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("tungsten_block"));
        event.getRegistry().register(new BlockItem(URANIUM_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("uranium_ore"));
        event.getRegistry().register(new BlockItem(URANIUM_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("uranium_block"));
        event.getRegistry().register(new BlockItem(RUBY_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("ruby_ore"));
        event.getRegistry().register(new BlockItem(RUBY_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("ruby_block"));
        event.getRegistry().register(new BlockItem(AMETHYST_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("amethyst_ore"));
        event.getRegistry().register(new BlockItem(AMETHYST_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("amethyst_block"));
        event.getRegistry().register(new BlockItem(AQUAMARINE_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("aquamarine_ore"));
        event.getRegistry().register(new BlockItem(AQUAMARINE_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("aquamarine_block"));
        event.getRegistry().register(new BlockItem(SAPHIRE_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("saphire_ore"));
        event.getRegistry().register(new BlockItem(SAPHIRE_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("saphire_block"));
        event.getRegistry().register(new BlockItem(MALACHITE_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("malachite_ore"));
        event.getRegistry().register(new BlockItem(MALACHITE_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("malachite_block"));
        event.getRegistry().register(new BlockItem(TANZANITE_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("tanzanite_ore"));
        event.getRegistry().register(new BlockItem(TANZANITE_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("tanzanite_block"));
        event.getRegistry().register(new BlockItem(ULTRINIUM_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("ultrinium_ore"));
        event.getRegistry().register(new BlockItem(ULTRINIUM_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("ultrinium_block"));
        event.getRegistry().register(new BlockItem(INFINITY_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("infinity_ore"));
        event.getRegistry().register(new BlockItem(INFINITY_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("infinity_block"));
    }
}
