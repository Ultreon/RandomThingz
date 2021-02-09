package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import com.qsoftware.forgemod.modules.blocks.blocks.GamePcBlock;
import com.qsoftware.forgemod.modules.blocks.blocks.base.*;
import com.qsoftware.forgemod.modules.blocks.blocks.furniture.WoodenCrateBlock;
import com.qsoftware.forgemod.modules.items.objects.type.FaceableBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Blocks initialization class
 *
 * @author Qboi123
 * @deprecated use {@link ModBlocks} instead.
 */
@Deprecated
@SuppressWarnings("unused")
public class BlockInit extends ObjectInit<Block> {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, QForgeMod.modId);

    // Doors
    public static final RegistryObject<FlowerBlock> BUTTERCUP = register("buttercup", () -> new FlowerBlock(Effects.ABSORPTION, 8, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT)));

    // Doors
    public static final RegistryObject<DoorBlock> LAB_DOOR = register("lab_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F).sound(SoundType.METAL).notSolid()));
    public static final RegistryObject<DoorBlock> SHOPPING_DOOR = register("shopping_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()));
    public static final RegistryObject<DoorBlock> IRON_GLASS_DOOR = register("iron_glass_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()));
    public static final RegistryObject<DoorBlock> IRON_BARRIER_DOOR = register("iron_barrier_door", () -> new BaseDoorBlock(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(4.7F).sound(SoundType.METAL).notSolid()));

    // Wood
    public static final RegistryObject<Block> EUCALYPTUS_PLANKS = register("eucalyptus_planks", () -> new BaseBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> EUCALYPTUS_LOG = register("eucalyptus_log", () -> new RotatedPillarBlock(Block.Properties.create(Material.WOOD, MaterialColor.QUARTZ).hardnessAndResistance(2.0f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));

    // Furniture
//    public static final RegistryObject<Block> MODERN_SOFA = register("modern_sofa", () -> new FaceableBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.7f, 15.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GAME_PC = register("game_pc", () -> new GamePcBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)));
    public static final RegistryObject<Block> ROUTER = register("router", () -> new FaceableBlock(Block.Properties.create(Material.ANVIL).hardnessAndResistance(4.7f).sound(SoundType.ANVIL)));

    // Stairs & Slabs
    public static final RegistryObject<StairsBlock> EUCALYPTUS_STAIRS = register("eucalyptus_stairs", () -> new StairsBlock(EUCALYPTUS_PLANKS.get()::getDefaultState, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> EUCALYPTUS_SLAB = register("eucalyptus_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));

    // Fences
    public static final RegistryObject<FenceBlock> EUCALYPTUS_FENCE = register("eucalyptus_fence", () -> new BaseFenceBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> EUCALYPTUS_FENCE_GATE = register("eucalyptus_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));

    // Buttons & Pressure plates.
    public static final RegistryObject<WoodButtonBlock> EUCALYPTUS_BUTTON = register("eucalyptus_button", () -> new BaseWoodButtonBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));
    public static final RegistryObject<PressurePlateBlock> EUCALYPTUS_PRESSURE_PLATE = register("eucalyptus_pressure_plate", () -> new BasePressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)));

    // Tile entity
//    public static final RegistryObject<Block> QUARRY_BLOCK = register("quarry", QuarryBlock::new);
    public static final RegistryObject<Block> WOODEN_CRATE = register("wooden_crate", () -> new WoodenCrateBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.9f).sound(SoundType.WOOD)));

    // Ore
//    public static final RegistryObject<Block> COPPER_ORE = register("copper_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> STEEL_ORE = register("steel_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.125f, 3.375f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TUNGSTEN_ORE = register("tungsten_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.125f, 6.425f).sound(SoundType.STONE)));
    //    public static final RegistryObject<Block> URANIUM_ORE = register("uranium_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.325f, 4.5625f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RUBY_ORE = register("ruby_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.75f, 2.875f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AMETHYST_ORE = register("amethyst_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.25f, 2.375f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AQUAMARINE_ORE = register("aquamarine_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.155f, 2.4635f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SAPHIRE_ORE = register("saphire_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.363f, 2.8460f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> MALACHITE_ORE = register("malachite_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TANZANITE_ORE = register("tanzanite_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 2.5f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ULTRINIUM_ORE = register("ultrinium_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(12.9f, 9999999.9f).sound(SoundType.STONE).harvestLevel(3)));
    public static final RegistryObject<Block> INFINITY_ORE = register("infinity_ore", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(64.5f, Float.MAX_VALUE).sound(SoundType.STONE).harvestLevel(4)));

    // Solid Gem / Metal block
//    public static final RegistryObject<Block> COPPER_BLOCK = register("copper_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.9f, 2.9f).sound(SoundType.STONE)));
//    public static final RegistryObject<Block> STEEL_BLOCK = register("steel_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(3.9875f, 4.275f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TUNGSTEN_BLOCK = register("tungsten_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(5.9875f, 6.5525f).sound(SoundType.STONE)));
    //    public static final RegistryObject<Block> URANIUM_BLOCK = register("uranium_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(2.8345f, 4.4675f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RUBY_BLOCK = register("ruby_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.25f, 5.5f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AMETHYST_BLOCK = register("amethyst_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.875f, 4.0625f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AQUAMARINE_BLOCK = register("aquamarine_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.995f, 4.1275f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SAPHIRE_BLOCK = register("saphire_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.120f, 4.5735f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> MALACHITE_BLOCK = register("malachite_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(4.8914f, 5.06635f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TANZANITE_BLOCK = register("tanzanite_block", () -> new BaseBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(4.26f, 5.5f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ULTRINIUM_BLOCK = register("ultrinium_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(12.9f, 99999999.9f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> INFINITY_BLOCK = register("infinity_block", () -> new BaseBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(64.5f, Float.MAX_VALUE).sound(SoundType.STONE)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }
}
