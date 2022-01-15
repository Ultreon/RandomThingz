package com.ultreon.randomthingz.data.recipes;

import com.qsoftware.modlib.api.crafting.recipe.fluid.FluidIngredient;
import com.qsoftware.modlib.silentlib.data.ExtendedShapedRecipeBuilder;
import com.qsoftware.modlib.silentlib.data.ExtendedShapelessRecipeBuilder;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.common.FeatureStatus;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.common.tags.ModTags;
import com.ultreon.randomthingz.item.CraftingItems;
import com.ultreon.randomthingz.item.tool.Toolset;
import com.ultreon.randomthingz.item.upgrade.MachineUpgrades;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ModRecipesProvider extends RecipeProvider {
    private static final int CRUSHING_CHUNKS_TIME = 300;
    private static final int CRUSHING_INGOT_TIME = 200;
    private static final int CRUSHING_ORE_TIME = 400;
    private static final float CRUSHING_CHUNKS_EXTRA_CHANCE = 0.1f;
    private static final float CRUSHING_ORE_STONE_CHANCE = 0.1f;

    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    private static void registerAlloySmelting(Consumer<FinishedRecipe> consumer) {
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.ALUMINUM_STEEL, 4, 600)
                .ingredient(ItemMaterial.IRON, 2)
                .ingredient(ModTags.Items.DUSTS_COAL, 3)
                .ingredient(ItemMaterial.ALUMINUM, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.BISMUTH_BRASS, 4, 400)
                .ingredient(ItemMaterial.COPPER, 2)
                .ingredient(ItemMaterial.ZINC, 1)
                .ingredient(ItemMaterial.BISMUTH, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.BISMUTH_STEEL, 4, 600)
                .ingredient(ItemMaterial.IRON, 2)
                .ingredient(ModTags.Items.DUSTS_COAL, 3)
                .ingredient(ItemMaterial.BISMUTH, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.BRASS, 4, 400)
                .ingredient(ItemMaterial.COPPER, 3)
                .ingredient(ItemMaterial.ZINC, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.BRONZE, 4, 400)
                .ingredient(ItemMaterial.COPPER, 3)
                .ingredient(ItemMaterial.TIN, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.ELECTRUM, 2, 400)
                .ingredient(ItemMaterial.GOLD, 1)
                .ingredient(ItemMaterial.SILVER, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.ENDERIUM, 4, 500)
                .ingredient(ItemMaterial.LEAD, 3)
                .ingredient(ItemMaterial.PLATINUM, 1)
                .ingredient(Tags.Items.ENDER_PEARLS, 4)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.INVAR, 3, 400)
                .ingredient(ItemMaterial.IRON, 2)
                .ingredient(ItemMaterial.NICKEL, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.LUMIUM, 4, 500)
                .ingredient(ItemMaterial.TIN, 3)
                .ingredient(ItemMaterial.SILVER, 1)
                .ingredient(Tags.Items.DUSTS_GLOWSTONE, 4)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.REDSTONE_ALLOY, 2, 200)
                .ingredient(ItemMaterial.IRON, 1)
                .ingredient(Tags.Items.DUSTS_REDSTONE, 4)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.SIGNALUM, 4, 500)
                .ingredient(ItemMaterial.COPPER, 3)
                .ingredient(ItemMaterial.SILVER, 1)
                .ingredient(Tags.Items.DUSTS_REDSTONE, 10)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(CraftingItems.SOLDER, 12, 200)
                .ingredient(ItemMaterial.TIN, 1)
                .ingredient(ItemMaterial.LEAD, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(ItemMaterial.STEEL, 2, 600)
                .ingredient(ItemMaterial.IRON, 2)
                .ingredient(ModTags.Items.DUSTS_COAL, 2)
                .build(consumer);
    }

    private static void registerCompressingRecipes(Consumer<FinishedRecipe> consumer) {
        CompressingRecipeBuilder.builder(Items.BLAZE_POWDER, 4, Items.BLAZE_ROD, 1, 400)
                .build(consumer);
        assert (ItemMaterial.COMPRESSED_IRON.getIngot().isPresent());
        CompressingRecipeBuilder.builder(Tags.Items.INGOTS_IRON, 1, ItemMaterial.COMPRESSED_IRON.getIngot().get(), 1, 400)
                .build(consumer);
        CompressingRecipeBuilder.builder(Tags.Items.STORAGE_BLOCKS_COAL, 16, Items.DIAMOND, 1, 800)
                .build(consumer);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static void registerCrushingRecipes(Consumer<FinishedRecipe> consumer) {
        for (ItemMaterial metal : ItemMaterial.values()) {
            if (metal.getOreItemTag().isPresent() && metal.getChunks().isPresent()) {
                crushingOre(metal.getOreItemTag().get(), metal.getChunks().get(), Blocks.COBBLESTONE)
                        .build(consumer);
            }
            if (metal.getChunksTag().isPresent() && metal.getDust().isPresent()) {
                crushingChunks(metal.getChunksTag().get(), metal.getDust().get())
                        .build(consumer);
            }
            if (metal.getIngotTag().isPresent() && metal.getDust().isPresent()) {
                crushingIngot(metal.getIngotTag().get(), metal.getDust().get())
                        .build(consumer, RandomThingz.rl("crushing/" + metal.getName() + "_dust_from_ingot"));
            }
        }

        // Vanilla ores
        CrushingRecipeBuilder.builder(Tags.Items.ORES_COAL, CRUSHING_ORE_TIME)
                .result(Items.COAL, 2)
                .result(Items.COBBLESTONE, 1, CRUSHING_ORE_STONE_CHANCE)
                .result(Items.DIAMOND, 1, 0.001f)
                .build(consumer);
        CrushingRecipeBuilder.builder(Tags.Items.ORES_LAPIS, CRUSHING_ORE_TIME)
                .result(Items.LAPIS_LAZULI, 12)
                .build(consumer);
        CrushingRecipeBuilder.builder(Tags.Items.ORES_REDSTONE, CRUSHING_ORE_TIME)
                .result(Items.REDSTONE, 6)
                .build(consumer);
        crushingOreBonus(Tags.Items.ORES_QUARTZ, Items.QUARTZ).build(consumer);
        crushingOreBonus(Tags.Items.ORES_DIAMOND, Items.DIAMOND).build(consumer);
        crushingOreBonus(Tags.Items.ORES_EMERALD, Items.EMERALD).build(consumer);
        crushingOre(Tags.Items.ORES_GOLD, ItemMaterial.GOLD.getChunks().get(), Blocks.COBBLESTONE).build(consumer);
        crushingOre(Blocks.NETHER_GOLD_ORE, ItemMaterial.GOLD.getChunks().get(), Blocks.NETHERRACK)
                .build(consumer, RandomThingz.rl("crushing/gold_chunks_nether"));
        crushingOre(Tags.Items.ORES_IRON, ItemMaterial.IRON.getChunks().get(), Blocks.COBBLESTONE).build(consumer);

        CrushingRecipeBuilder.builder(Blocks.ANCIENT_DEBRIS, 2 * CRUSHING_ORE_TIME)
                .result(Items.NETHERITE_SCRAP, 2)
                .result(Items.NETHERITE_SCRAP, 1, 0.1f)
                .result(Items.NETHERITE_SCRAP, 1, 0.01f)
                .build(consumer);

        // Others
        CrushingRecipeBuilder.builder(Tags.Items.RODS_BLAZE, 200)
                .result(Items.BLAZE_POWDER, 4)
                .build(consumer);
        CrushingRecipeBuilder.builder(Blocks.CLAY, 100)
                .result(Items.CLAY_BALL, 4)
                .build(consumer);
        CrushingRecipeBuilder.builder(Items.COAL, 200)
                .result(CraftingItems.COAL_DUST, 1)
                .build(consumer);
        CrushingRecipeBuilder.builder(Blocks.GLOWSTONE, 100)
                .result(Items.GLOWSTONE_DUST, 4)
                .build(consumer);
        CrushingRecipeBuilder.builder(Tags.Items.COBBLESTONE, 200)
                .result(Blocks.GRAVEL, 1)
                .build(consumer);
        CrushingRecipeBuilder.builder(ItemTags.LOGS, 200)
                .result(Items.PAPER, 1, 0.75f)
                .result(Items.PAPER, 1, 0.25f)
                .result(Items.STICK, 1, 0.25f)
                .result(Items.STICK, 1, 0.25f)
                .build(consumer);
        CrushingRecipeBuilder.builder(
                        Ingredient.of(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR, Blocks.CHISELED_QUARTZ_BLOCK, Blocks.SMOOTH_QUARTZ),
                        200)
                .result(Items.QUARTZ, 4)
                .build(consumer, RandomThingz.rl("crushing/quartz_from_blocks"));
        CrushingRecipeBuilder.builder(Ingredient.of(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE), 200)
                .result(Blocks.RED_SAND, 4)
                .build(consumer, RandomThingz.rl("crushing/red_sand_from_sandstone"));
        CrushingRecipeBuilder.builder(Ingredient.of(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE), 200)
                .result(Blocks.SAND, 4)
                .build(consumer, RandomThingz.rl("crushing/sand_from_sandstone"));
        CrushingRecipeBuilder.builder(Blocks.GRAVEL, 200)
                .result(Blocks.SAND, 1)
                .result(Items.FLINT, 1, 0.1f)
                .build(consumer);
    }

    private static void registerInfusingRecipes(Consumer<FinishedRecipe> consumer) {
        FluidIngredient water100mb = new FluidIngredient(FluidTags.WATER, 100);
        InfusingRecipeBuilder.builder(Items.WHITE_CONCRETE, 1, 100, Items.WHITE_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.ORANGE_CONCRETE, 1, 100, Items.ORANGE_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.MAGENTA_CONCRETE, 1, 100, Items.MAGENTA_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.LIGHT_BLUE_CONCRETE, 1, 100, Items.LIGHT_BLUE_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.YELLOW_CONCRETE, 1, 100, Items.YELLOW_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.LIME_CONCRETE, 1, 100, Items.LIME_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.PINK_CONCRETE, 1, 100, Items.PINK_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.GRAY_CONCRETE, 1, 100, Items.GRAY_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.LIGHT_GRAY_CONCRETE, 1, 100, Items.LIGHT_GRAY_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.CYAN_CONCRETE, 1, 100, Items.CYAN_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.PURPLE_CONCRETE, 1, 100, Items.PURPLE_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.BLUE_CONCRETE, 1, 100, Items.BLUE_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.BROWN_CONCRETE, 1, 100, Items.BROWN_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.GREEN_CONCRETE, 1, 100, Items.GREEN_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.RED_CONCRETE, 1, 100, Items.RED_CONCRETE_POWDER, water100mb).build(consumer);
        InfusingRecipeBuilder.builder(Items.BLACK_CONCRETE, 1, 100, Items.BLACK_CONCRETE_POWDER, water100mb).build(consumer);
    }

    public static CrushingRecipeBuilder crushingChunks(Tag<Item> chunks, ItemLike dust) {
        return CrushingRecipeBuilder.crushingChunks(chunks, dust, CRUSHING_CHUNKS_TIME, CRUSHING_CHUNKS_EXTRA_CHANCE);
    }

    public static CrushingRecipeBuilder crushingIngot(Tag<Item> ingot, ItemLike dust) {
        return CrushingRecipeBuilder.crushingIngot(ingot, dust, CRUSHING_INGOT_TIME);
    }

    public static CrushingRecipeBuilder crushingOre(Tag<Item> ore, ItemLike chunks, @Nullable ItemLike extra) {
        return CrushingRecipeBuilder.crushingOre(ore, chunks, CRUSHING_ORE_TIME, extra, CRUSHING_ORE_STONE_CHANCE);
    }

    public static CrushingRecipeBuilder crushingOre(ItemLike ore, ItemLike chunks, @Nullable ItemLike extra) {
        return CrushingRecipeBuilder.crushingOre(ore, chunks, CRUSHING_ORE_TIME, extra, CRUSHING_ORE_STONE_CHANCE);
    }

    public static CrushingRecipeBuilder crushingOreBonus(Tag<Item> ore, ItemLike item) {
        return CrushingRecipeBuilder.builder(ore, CRUSHING_ORE_TIME)
                .result(item, 2)
                .result(item, 1, 0.1f)
                .result(Blocks.COBBLESTONE, 1, 0.1f);
    }

    @Override
    public String getName() {
        return "Random Thingz - Recipes";
    }

    @Override
    protected void buildShapelessRecipes(Consumer<FinishedRecipe> consumer) {
        registerCrafting(consumer);
        registerSmelting(consumer);
        registerAlloySmelting(consumer);
        registerCompressingRecipes(consumer);
        registerCrushingRecipes(consumer);
        registerInfusingRecipes(consumer);
    }

    private void registerCrafting(Consumer<FinishedRecipe> consumer) {
        registerMetalCrafting(consumer);
        registerBlockCrafting(consumer);
        registerItemCrafting(consumer);
    }

    private void registerMetalCrafting(Consumer<FinishedRecipe> consumer) {
        for (ItemMaterial metal : ItemMaterial.values()) {
            if (metal.getIngot().isPresent() && metal.getNuggetTag().isPresent()) {
                ExtendedShapedRecipeBuilder.vanillaBuilder(metal.getIngot().get())
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .key('#', metal.getNuggetTag().get())
                        .build(consumer, RandomThingz.rl("metals/" + metal.getName() + "_ingot_from_nugget"));
            }
            if (metal.getNugget().isPresent() && metal.getIngotTag().isPresent()) {
                ExtendedShapelessRecipeBuilder.vanillaBuilder(metal.getNugget().get(), 9)
                        .addIngredient(metal.getIngotTag().get())
                        .build(consumer, RandomThingz.rl("metals/" + metal.getName() + "_nugget"));
            }
            if (metal.getStorageBlock().isPresent() && metal.getIngotTag().isPresent()) {
                ExtendedShapedRecipeBuilder.vanillaBuilder(metal.getStorageBlock().get())
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .key('#', metal.getIngotTag().get())
                        .build(consumer, RandomThingz.rl("metals/" + metal.getName() + "_block"));
            }
            if (metal.getIngot().isPresent() && metal.getStorageBlockItemTag().isPresent()) {
                ExtendedShapelessRecipeBuilder.vanillaBuilder(metal.getIngot().get(), 9)
                        .addIngredient(metal.getStorageBlockItemTag().get())
                        .build(consumer, RandomThingz.rl("metals/" + metal.getName() + "_ingot_from_block"));
            }
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private void registerBlockCrafting(Consumer<FinishedRecipe> consumer) {
        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.ACACIA_DRYING_RACK)
                .patternLine("###")
                .key('#', Blocks.ACACIA_SLAB)
                .build(consumer);
        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.BIRCH_DRYING_RACK)
                .patternLine("###")
                .key('#', Blocks.BIRCH_SLAB)
                .build(consumer);
        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.DARK_OAK_DRYING_RACK)
                .patternLine("###")
                .key('#', Blocks.DARK_OAK_SLAB)
                .build(consumer);
        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.JUNGLE_DRYING_RACK)
                .patternLine("###")
                .key('#', Blocks.JUNGLE_SLAB)
                .build(consumer);
        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.OAK_DRYING_RACK)
                .patternLine("###")
                .key('#', Blocks.OAK_SLAB)
                .build(consumer);
        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.SPRUCE_DRYING_RACK)
                .patternLine("###")
                .key('#', Blocks.SPRUCE_SLAB)
                .build(consumer);
        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.EUCALYPTUS_DRYING_RACK)
                .patternLine("###")
                .key('#', ModBlocks.EUCALYPTUS_SLAB)
                .build(consumer);
        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.CHERRY_DRYING_RACK)
                .patternLine("###")
                .key('#', ModBlocks.CHERRY_SLAB)
                .build(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CHRISTMAS_CHEST)
                .pattern("###")
                .pattern("#A#")
                .pattern("###")
                .define('#', ItemTags.PLANKS)
                .define('A', Items.SPRUCE_SAPLING)
                .unlockedBy("has_item", has(ItemTags.PLANKS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.STONE_MACHINE_FRAME, 2)
                .pattern("/#/")
                .pattern("#s#")
                .pattern("/#/")
                .define('/', Blocks.SMOOTH_STONE)
                .define('#', Tags.Items.GLASS)
                .define('s', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_item", has(Blocks.SMOOTH_STONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ALLOY_MACHINE_FRAME, 2)
                .pattern("/#/")
                .pattern("#s#")
                .pattern("/#/")
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('#', Tags.Items.GLASS)
                .define('s', ModTags.Items.STEELS)
                .unlockedBy("has_item", has(ItemMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.BASIC_ALLOY_SMELTER)
                .pattern("###")
                .pattern("/X/")
                .pattern("O/O")
                .define('#', ItemMaterial.TIN.getIngotTag().get())
                .define('/', ItemMaterial.COPPER.getIngotTag().get())
                .define('X', ModBlocks.STONE_MACHINE_FRAME)
                .define('O', Blocks.BRICKS)
                .unlockedBy("has_item", has(ModBlocks.STONE_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ALLOY_SMELTER)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("OHO")
                .define('#', ItemMaterial.BISMUTH_BRASS.getIngotTag().get())
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', Blocks.BRICKS)
                .define('H', CraftingItems.HEATING_ELEMENT)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ALLOY_SMELTER)
                .pattern("#C#")
                .pattern("/X/")
                .define('#', ItemMaterial.BISMUTH_BRASS.getIngotTag().get())
                .define('C', ModBlocks.BASIC_ALLOY_SMELTER)
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer, RandomThingz.rl("alloy_smelter_from_basic"));

        ShapedRecipeBuilder.shaped(ModBlocks.BASIC_CRUSHER)
                .pattern("###")
                .pattern("/X/")
                .pattern("O/O")
                .define('#', ItemMaterial.BRONZE.getIngotTag().get())
                .define('/', ItemMaterial.ALUMINUM.getIngotTag().get())
                .define('X', ModBlocks.STONE_MACHINE_FRAME)
                .define('O', Blocks.SMOOTH_STONE)
                .unlockedBy("has_item", has(ModBlocks.STONE_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CRUSHER)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("ODO")
                .define('#', ItemMaterial.BISMUTH_STEEL.getIngotTag().get())
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', Blocks.SMOOTH_STONE)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.CRUSHER)
                .pattern("#C#")
                .pattern("/X/")
                .pattern(" D ")
                .define('#', ItemMaterial.BISMUTH_STEEL.getIngotTag().get())
                .define('C', ModBlocks.BASIC_CRUSHER)
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer, RandomThingz.rl("crusher_from_basic"));

        ShapedRecipeBuilder.shaped(ModBlocks.COMPRESSOR)
                .pattern("#D#")
                .pattern("/X/")
                .pattern("ODC")
                .define('#', Tags.Items.INGOTS_IRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', Blocks.SMOOTH_STONE)
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ELECTRIC_FURNACE)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("OHO")
                .define('#', Tags.Items.INGOTS_IRON)
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', Blocks.SMOOTH_STONE)
                .define('H', CraftingItems.HEATING_ELEMENT)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.REFINERY)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("OHO")
                .define('#', ItemMaterial.ALUMINUM_STEEL.getIngotTag().get())
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', ModItems.EMPTY_CANISTER)
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', ItemMaterial.ELECTRUM.getIngotTag().get())
                .define('H', CraftingItems.HEATING_ELEMENT)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.MIXER)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("OHO")
                .define('#', ItemMaterial.BISMUTH_STEEL.getIngotTag().get())
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', ModItems.EMPTY_CANISTER)
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', ItemMaterial.BRASS.getIngotTag().get())
                .define('H', Items.PISTON)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.SOLIDIFIER)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("OHO")
                .define('#', ItemMaterial.STEEL.getIngotTag().get())
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', ModItems.EMPTY_CANISTER)
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', ItemMaterial.SILVER.getIngotTag().get())
                .define('H', Items.IRON_BARS)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.INFUSER)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("OPO")
                .define('#', ItemMaterial.BISMUTH_BRASS.getIngotTag().get())
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', ModItems.EMPTY_CANISTER)
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', ItemMaterial.NICKEL.getIngotTag().get())
                .define('P', ModTags.Items.PLASTIC)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.PUMP)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("OHO")
                .define('#', ItemMaterial.ALUMINUM.getIngotTag().get())
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', ItemMaterial.INVAR.getIngotTag().get())
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', Items.BUCKET)
                .define('H', Items.PISTON)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.COAL_GENERATOR)
                .pattern("###")
                .pattern("/X/")
                .pattern("OAO")
                .define('#', Tags.Items.INGOTS_IRON)
                .define('/', ItemMaterial.COPPER.getIngotTag().get())
                .define('X', ModBlocks.STONE_MACHINE_FRAME)
                .define('O', Tags.Items.COBBLESTONE)
                .define('A', ItemMaterial.REFINED_IRON.getIngotTag().get())
                .unlockedBy("has_item", has(ModBlocks.STONE_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.LAVA_GENERATOR)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("#O#")
                .define('#', ItemMaterial.INVAR.getIngotTag().get())
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('O', Blocks.SMOOTH_STONE)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.DIESEL_GENERATOR)
                .pattern("#C#")
                .pattern("/X/")
                .pattern("#B#")
                .define('#', ItemMaterial.STEEL.getIngotTag().get())
                .define('C', CraftingItems.CIRCUIT_BOARD)
                .define('/', Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(ItemMaterial.PLATINUM.getNuggetTag().get()),
                        new Ingredient.TagValue(ItemMaterial.SILVER.getIngotTag().get())
                )))
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('B', Items.BUCKET)
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.BATTERY_BOX)
                .pattern("#B#")
                .pattern("/X/")
                .pattern("L/L")
                .define('#', ItemMaterial.ALUMINUM.getIngotTag().get())
                .define('B', ModItems.BATTERY)
                .define('/', ModBlocks.WIRE)
                .define('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .define('L', ItemMaterial.LEAD.getIngotTag().get())
                .unlockedBy("has_item", has(ModBlocks.ALLOY_MACHINE_FRAME))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.WIRE, 12)
                .pattern("///")
                .pattern("###")
                .define('/', Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(ItemMaterial.COPPER.getIngotTag().get()),
                        new Ingredient.TagValue(ItemMaterial.REFINED_IRON.getIngotTag().get())
                )))
                .define('#', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .unlockedBy("has_item", has(ItemMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .save(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModBlocks.ATOMIC_TNT)
                .patternLine("XCX")
                .patternLine("/O/")
                .patternLine("XEX")
                .key('X', ItemMaterial.PLATINUM.getIngotTag().get())
                .key('C', ItemMaterial.COPPER.getIngotTag().get())
                .key('E', ItemMaterial.ELECTRUM.getIngotTag().get())
                .key('/', ModItems.URANIUM_ROD)
                .key('O', Items.GUNPOWDER)
                .addCriterion("has_item", has(ModItems.URANIUM_ROD))
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModBlocks.GOLD_BUTTON)
                .addIngredient(Items.GOLD_NUGGET)
                .addCriterion("has_item", has(Items.GOLD_NUGGET))
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModBlocks.IRON_BUTTON)
                .addIngredient(Items.IRON_NUGGET)
                .addCriterion("has_item", has(Items.IRON_NUGGET))
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModBlocks.QUARTZ_BUTTON)
                .addIngredient(Items.QUARTZ)
                .addCriterion("has_item", has(Items.QUARTZ))
                .build(consumer);

    }

    @SuppressWarnings({"OptionalGetWithoutIsPresent"})
    private void registerItemCrafting(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(CraftingItems.CIRCUIT_BOARD, 3)
                .pattern("/G/")
                .pattern("###")
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('#', ItemMaterial.COPPER.getIngotTag().get())
                .unlockedBy("has_item", has(ItemMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(CraftingItems.HEATING_ELEMENT, 2)
                .pattern("##")
                .pattern("##")
                .pattern("/ ")
                .define('#', ItemMaterial.COPPER.getIngotTag().get())
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .unlockedBy("has_item", has(ItemMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(CraftingItems.PLASTIC_SHEET)
                .pattern("##")
                .pattern("##")
                .define('#', CraftingItems.PLASTIC_PELLETS)
                .unlockedBy("has_item", has(CraftingItems.PLASTIC_PELLETS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(CraftingItems.UPGRADE_CASE, 2)
                .pattern("###")
                .pattern("###")
                .pattern("///")
                .define('#', ModTags.Items.PLASTIC)
                .define('/', Tags.Items.NUGGETS_GOLD)
                .unlockedBy("has_item", has(ModTags.Items.PLASTIC))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(MachineUpgrades.PROCESSING_SPEED)
                .requires(CraftingItems.UPGRADE_CASE)
                .requires(Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .requires(ItemMaterial.SILVER.getIngotTag().get())
                .requires(ItemMaterial.SILVER.getIngotTag().get())
                .unlockedBy("has_item", has(CraftingItems.UPGRADE_CASE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(MachineUpgrades.OUTPUT_CHANCE)
                .requires(CraftingItems.UPGRADE_CASE)
                .requires(Tags.Items.STORAGE_BLOCKS_LAPIS)
                .requires(ItemMaterial.PLATINUM.getIngotTag().get())
                .requires(ItemMaterial.PLATINUM.getIngotTag().get())
                .unlockedBy("has_item", has(CraftingItems.UPGRADE_CASE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(MachineUpgrades.ENERGY_EFFICIENCY)
                .requires(CraftingItems.UPGRADE_CASE)
                .requires(Items.GLOWSTONE)
                .requires(ItemMaterial.ELECTRUM.getIngotTag().get())
                .requires(ItemMaterial.ELECTRUM.getIngotTag().get())
                .unlockedBy("has_item", has(CraftingItems.UPGRADE_CASE))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(MachineUpgrades.RANGE)
                .requires(CraftingItems.UPGRADE_CASE)
                .requires(Tags.Items.ENDER_PEARLS)
                .requires(ItemMaterial.INVAR.getIngotTag().get())
                .requires(ItemMaterial.INVAR.getIngotTag().get())
                .unlockedBy("has_item", has(CraftingItems.UPGRADE_CASE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.WRENCH)
                .pattern("/ /")
                .pattern(" # ")
                .pattern(" / ")
                .define('/', Tags.Items.INGOTS_IRON)
                .define('#', ItemMaterial.REFINED_IRON.getIngotTag().get())
                .unlockedBy("has_item", has(ItemMaterial.REFINED_IRON.getIngotTag().get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.BATTERY)
                .pattern(" / ")
                .pattern("#X#")
                .pattern("LXL")
                .define('/', ItemMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .define('#', Tags.Items.INGOTS_IRON)
                .define('X', Tags.Items.DUSTS_REDSTONE)
                .define('L', ItemMaterial.LEAD.getIngotTag().get())
                .unlockedBy("has_item", has(ItemMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .save(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.HAND_PUMP)
                .patternLine("/C#")
                .patternLine(" B#")
                .key('/', ItemMaterial.ALUMINUM.getIngotTag().get())
                .key('C', ModItems.EMPTY_CANISTER)
                .key('#', ModTags.Items.PLASTIC)
                .key('B', ModItems.BATTERY)
                .addCriterion("has_item", has(Items.WATER_BUCKET))
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.URANIUM_ROD)
                .patternLine("/")
                .patternLine("/")
                .key('/', ItemMaterial.URANIUM.getIngotTag().get())
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.HAMBURGER_BUN)
                .patternLine("YY")
                .key('Y', Items.WHEAT)
                .build(consumer, modId("food/hamburger_bun"));

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.CHEESE_BURGER)
                .patternLine(" U ")
                .patternLine("-OP")
                .patternLine(" U ")
                .key('U', ModItems.HAMBURGER_BUN)
                .key('-', ModItems.CHEESE_SLICE)
                .key('O', ModTags.Items.MEAT)
                .key('P', Tags.Items.MUSHROOMS)
                .build(consumer, modId("food/cheese_burger1"));

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.CHEESE_BURGER)
                .patternLine(" U ")
                .patternLine("O-P")
                .patternLine(" U ")
                .key('U', ModItems.HAMBURGER_BUN)
                .key('-', ModItems.CHEESE_SLICE)
                .key('O', ModTags.Items.MEAT)
                .key('P', Tags.Items.MUSHROOMS)
                .build(consumer, modId("food/cheese_burger2"));

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.CHEESE_BURGER)
                .patternLine(" U ")
                .patternLine("OP-")
                .patternLine(" U ")
                .key('U', ModItems.HAMBURGER_BUN)
                .key('-', ModItems.CHEESE_SLICE)
                .key('O', ModTags.Items.MEAT)
                .key('P', Tags.Items.MUSHROOMS)
                .build(consumer, modId("food/cheese_burger3"));

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.CHEESE_BURGER)
                .patternLine(" U ")
                .patternLine("-PO")
                .patternLine(" U ")
                .key('U', ModItems.HAMBURGER_BUN)
                .key('-', ModItems.CHEESE_SLICE)
                .key('O', ModTags.Items.MEAT)
                .key('P', Tags.Items.MUSHROOMS)
                .build(consumer, modId("food/cheese_burger4"));

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.CHEESE_BURGER)
                .patternLine(" U ")
                .patternLine("P-O")
                .patternLine(" U ")
                .key('U', ModItems.HAMBURGER_BUN)
                .key('-', ModItems.CHEESE_SLICE)
                .key('O', ModTags.Items.MEAT)
                .key('P', Tags.Items.MUSHROOMS)
                .build(consumer, modId("food/cheese_burger5"));

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.CHEESE_BURGER)
                .patternLine(" U ")
                .patternLine("PO-")
                .patternLine(" U ")
                .key('U', ModItems.HAMBURGER_BUN)
                .key('-', ModItems.CHEESE_SLICE)
                .key('O', ModTags.Items.MEAT)
                .key('P', Tags.Items.MUSHROOMS)
                .build(consumer, modId("food/cheese_burger6"));

        for (Toolset toolset : Toolset.values()) {
            if (toolset.getStatus() == FeatureStatus.DEPRECATED) {
                continue;
            }

            try {
                RandomThingz.LOGGER.info("Loading recipe for tool: " + toolset.getName());
                ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getSword())
                        .patternLine("X")
                        .patternLine("X")
                        .patternLine("/")
                        .key('/', () -> toolset.getHandleMaterial().get())
                        .key('X', () -> toolset.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getAxe())
                        .patternLine("XX")
                        .patternLine("X/")
                        .patternLine(" /")
                        .key('/', () -> toolset.getHandleMaterial().get())
                        .key('X', () -> toolset.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getAxe())
                        .patternLine("XX")
                        .patternLine("/X")
                        .patternLine("/ ")
                        .key('/', () -> toolset.getHandleMaterial().get())
                        .key('X', () -> toolset.getBaseMaterial().get())
                        .build(consumer, new ResourceLocation(RandomThingz.MOD_ID, toolset.getAxe().getName() + "_mirror"));
                ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getShovel())
                        .patternLine("X")
                        .patternLine("/")
                        .patternLine("/")
                        .key('/', () -> toolset.getHandleMaterial().get())
                        .key('X', () -> toolset.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getPickaxe())
                        .patternLine("XXX")
                        .patternLine(" / ")
                        .patternLine(" / ")
                        .key('/', () -> toolset.getHandleMaterial().get())
                        .key('X', () -> toolset.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getHoe())
                        .patternLine("XX")
                        .patternLine(" /")
                        .patternLine(" /")
                        .key('/', () -> toolset.getHandleMaterial().get())
                        .key('X', () -> toolset.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getHoe())
                        .patternLine("XX")
                        .patternLine("/ ")
                        .patternLine("/ ")
                        .key('/', () -> toolset.getHandleMaterial().get())
                        .key('X', () -> toolset.getBaseMaterial().get())
                        .build(consumer, new ResourceLocation(RandomThingz.MOD_ID, toolset.getHoe().getName() + "_mirror"));
                if (toolset.getArmorSubMaterial() != null) {
                    ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getHelmet())
                            .patternLine("XXX")
                            .patternLine("XOX")
                            .key('O', () -> toolset.getArmorSubMaterial().get())
                            .key('X', () -> toolset.getBaseMaterial().get())
                            .build(consumer);
                    ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getChestplate())
                            .patternLine("XOX")
                            .patternLine("XXX")
                            .patternLine("XXX")
                            .key('O', () -> toolset.getArmorSubMaterial().get())
                            .key('X', () -> toolset.getBaseMaterial().get())
                            .build(consumer);
                    ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getLeggings())
                            .patternLine("XXX")
                            .patternLine("XOX")
                            .patternLine("X X")
                            .key('O', () -> toolset.getArmorSubMaterial().get())
                            .key('X', () -> toolset.getBaseMaterial().get())
                            .build(consumer);
                    ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getBoots())
                            .patternLine("X X")
                            .patternLine("XOX")
                            .key('O', () -> toolset.getArmorSubMaterial().get())
                            .key('X', () -> toolset.getBaseMaterial().get())
                            .build(consumer);
                } else {
                    ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getHelmet())
                            .patternLine("XXX")
                            .patternLine("X X")
                            .key('X', () -> toolset.getBaseMaterial().get())
                            .build(consumer);
                    ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getChestplate())
                            .patternLine("X X")
                            .patternLine("XXX")
                            .patternLine("XXX")
                            .key('X', () -> toolset.getBaseMaterial().get())
                            .build(consumer);
                    ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getLeggings())
                            .patternLine("XXX")
                            .patternLine("X X")
                            .patternLine("X X")
                            .key('X', () -> toolset.getBaseMaterial().get())
                            .build(consumer);
                    ExtendedShapedRecipeBuilder.vanillaBuilder(toolset.getBoots())
                            .patternLine("X X")
                            .patternLine("X X")
                            .key('X', () -> toolset.getBaseMaterial().get())
                            .build(consumer);
                }
            } catch (Throwable t) {
                throw new RuntimeException("Could not load tools data for type '" + toolset.getName() + "'", t);
            }
        }

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.EMPTY_CANISTER, 8)
                .patternLine(" # ")
                .patternLine("# #")
                .patternLine(" # ")
                .key('#', ItemMaterial.ALUMINUM.getIngotTag().get())
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModItems.EMPTY_CANISTER)
                .addIngredient(ModItems.CANISTER)
                .build(consumer, RandomThingz.rl("canister_clear"));

        ExtendedShapelessRecipeBuilder.vanillaBuilder(Items.LEATHER)
                .addIngredient(CraftingItems.ZOMBIE_LEATHER, 4)
                .build(consumer, RandomThingz.rl("leather"));
    }

    private ResourceLocation modId(String path) {
        return RandomThingz.rl(path);
    }

    private void registerSmelting(Consumer<IFinishedRecipe> consumer) {
        for (ItemMaterial metal : ItemMaterial.values()) {
            if (metal.getIngot().isPresent() && (metal.getChunksTag().isPresent() || metal.getDustTag().isPresent())) {
                smeltingAndBlasting(consumer, metal.getName() + "_ingot",
                        metal.getSmeltables(false), metal.getIngot().get());
            }
            if (metal.getIngot().isPresent() && metal.getOreItemTag().isPresent()) {
                smeltingAndBlasting(consumer, metal.getName() + "_ingot_from_ore",
                        Ingredient.fromTag(metal.getOreItemTag().get()), metal.getIngot().get());
            }
        }

        smeltingAndBlasting(consumer, "iron_ingot", ItemMaterial.IRON.getSmeltables(false), Items.IRON_INGOT);
        smeltingAndBlasting(consumer, "gold_ingot", ItemMaterial.GOLD.getSmeltables(false), Items.GOLD_INGOT);

        assert (ItemMaterial.REFINED_IRON.getIngot().isPresent());
        smeltingAndBlasting(consumer, "refined_iron_ingot", Ingredient.fromTag(Tags.Items.INGOTS_IRON), ItemMaterial.REFINED_IRON.getIngot().get());

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.MILK_BUCKET), ModItems.CHEESE, 0.4f, 400)
                .addCriterion("has_item", hasItem(Items.MILK_BUCKET))
                .build(consumer, RandomThingz.rl("food/cheese"));
    }

    private void smeltingAndBlasting(Consumer<IFinishedRecipe> consumer, String name, Ingredient ingredient, IItemProvider result) {
        CookingRecipeBuilder.smeltingRecipe(ingredient, result, 1f, 200)
                .addCriterion("has_item", hasItem(Blocks.FURNACE))
                .build(consumer, RandomThingz.rl("smelting/" + name));
        CookingRecipeBuilder.blastingRecipe(ingredient, result, 1f, 100)
                .addCriterion("has_item", hasItem(Blocks.FURNACE))
                .build(consumer, RandomThingz.rl("blasting/" + name));
    }
}
