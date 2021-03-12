package com.qsoftware.forgemod.data.recipes;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.ModTags;
import com.qsoftware.forgemod.modules.tiles.ModBlocks;
import com.qsoftware.forgemod.modules.items.ModItems;
import com.qsoftware.forgemod.modules.items.OreMaterial;
import com.qsoftware.forgemod.modules.items.tools.Tools;
import com.qsoftware.forgemod.modules.items.objects.CraftingItems;
import com.qsoftware.forgemod.modules.items.objects.upgrades.MachineUpgrades;
import com.qsoftware.modlib.api.crafting.recipe.fluid.FluidIngredient;
import com.qsoftware.modlib.silentlib.data.ExtendedShapedRecipeBuilder;
import com.qsoftware.modlib.silentlib.data.ExtendedShapelessRecipeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
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

    private static void registerAlloySmelting(Consumer<IFinishedRecipe> consumer) {
        AlloySmeltingRecipeBuilder.builder(OreMaterial.ALUMINUM_STEEL, 4, 600)
                .ingredient(OreMaterial.IRON, 2)
                .ingredient(ModTags.Items.DUSTS_COAL, 3)
                .ingredient(OreMaterial.ALUMINUM, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.BISMUTH_BRASS, 4, 400)
                .ingredient(OreMaterial.COPPER, 2)
                .ingredient(OreMaterial.ZINC, 1)
                .ingredient(OreMaterial.BISMUTH, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.BISMUTH_STEEL, 4, 600)
                .ingredient(OreMaterial.IRON, 2)
                .ingredient(ModTags.Items.DUSTS_COAL, 3)
                .ingredient(OreMaterial.BISMUTH, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.BRASS, 4, 400)
                .ingredient(OreMaterial.COPPER, 3)
                .ingredient(OreMaterial.ZINC, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.BRONZE, 4, 400)
                .ingredient(OreMaterial.COPPER, 3)
                .ingredient(OreMaterial.TIN, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.ELECTRUM, 2, 400)
                .ingredient(OreMaterial.GOLD, 1)
                .ingredient(OreMaterial.SILVER, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.ENDERIUM, 4, 500)
                .ingredient(OreMaterial.LEAD, 3)
                .ingredient(OreMaterial.PLATINUM, 1)
                .ingredient(Tags.Items.ENDER_PEARLS, 4)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.INVAR, 3, 400)
                .ingredient(OreMaterial.IRON, 2)
                .ingredient(OreMaterial.NICKEL, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.LUMIUM, 4, 500)
                .ingredient(OreMaterial.TIN, 3)
                .ingredient(OreMaterial.SILVER, 1)
                .ingredient(Tags.Items.DUSTS_GLOWSTONE, 4)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.REDSTONE_ALLOY, 2, 200)
                .ingredient(OreMaterial.IRON, 1)
                .ingredient(Tags.Items.DUSTS_REDSTONE, 4)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.SIGNALUM, 4, 500)
                .ingredient(OreMaterial.COPPER, 3)
                .ingredient(OreMaterial.SILVER, 1)
                .ingredient(Tags.Items.DUSTS_REDSTONE, 10)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(CraftingItems.SOLDER, 12, 200)
                .ingredient(OreMaterial.TIN, 1)
                .ingredient(OreMaterial.LEAD, 1)
                .build(consumer);
        AlloySmeltingRecipeBuilder.builder(OreMaterial.STEEL, 2, 600)
                .ingredient(OreMaterial.IRON, 2)
                .ingredient(ModTags.Items.DUSTS_COAL, 2)
                .build(consumer);
    }

    private static void registerCompressingRecipes(Consumer<IFinishedRecipe> consumer) {
        CompressingRecipeBuilder.builder(Items.BLAZE_POWDER, 4, Items.BLAZE_ROD, 1, 400)
                .build(consumer);
        assert (OreMaterial.COMPRESSED_IRON.getIngot().isPresent());
        CompressingRecipeBuilder.builder(Tags.Items.INGOTS_IRON, 1, OreMaterial.COMPRESSED_IRON.getIngot().get(), 1, 400)
                .build(consumer);
        CompressingRecipeBuilder.builder(Tags.Items.STORAGE_BLOCKS_COAL, 16, Items.DIAMOND, 1, 800)
                .build(consumer);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static void registerCrushingRecipes(Consumer<IFinishedRecipe> consumer) {
        for (OreMaterial metal : OreMaterial.values()) {
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
                        .build(consumer, QForgeMod.rl("crushing/" + metal.getName() + "_dust_from_ingot"));
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
        crushingOre(Tags.Items.ORES_GOLD, OreMaterial.GOLD.getChunks().get(), Blocks.COBBLESTONE).build(consumer);
        crushingOre(Blocks.NETHER_GOLD_ORE, OreMaterial.GOLD.getChunks().get(), Blocks.NETHERRACK)
                .build(consumer, QForgeMod.rl("crushing/gold_chunks_nether"));
        crushingOre(Tags.Items.ORES_IRON, OreMaterial.IRON.getChunks().get(), Blocks.COBBLESTONE).build(consumer);

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
                Ingredient.fromItems(Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR, Blocks.CHISELED_QUARTZ_BLOCK, Blocks.SMOOTH_QUARTZ),
                200)
                .result(Items.QUARTZ, 4)
                .build(consumer, QForgeMod.rl("crushing/quartz_from_blocks"));
        CrushingRecipeBuilder.builder(Ingredient.fromItems(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE), 200)
                .result(Blocks.RED_SAND, 4)
                .build(consumer, QForgeMod.rl("crushing/red_sand_from_sandstone"));
        CrushingRecipeBuilder.builder(Ingredient.fromItems(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE), 200)
                .result(Blocks.SAND, 4)
                .build(consumer, QForgeMod.rl("crushing/sand_from_sandstone"));
        CrushingRecipeBuilder.builder(Blocks.GRAVEL, 200)
                .result(Blocks.SAND, 1)
                .result(Items.FLINT, 1, 0.1f)
                .build(consumer);
    }

    private static void registerInfusingRecipes(Consumer<IFinishedRecipe> consumer) {
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

    public static CrushingRecipeBuilder crushingChunks(ITag<Item> chunks, IItemProvider dust) {
        return CrushingRecipeBuilder.crushingChunks(chunks, dust, CRUSHING_CHUNKS_TIME, CRUSHING_CHUNKS_EXTRA_CHANCE);
    }

    public static CrushingRecipeBuilder crushingIngot(ITag<Item> ingot, IItemProvider dust) {
        return CrushingRecipeBuilder.crushingIngot(ingot, dust, CRUSHING_INGOT_TIME);
    }

    public static CrushingRecipeBuilder crushingOre(ITag<Item> ore, IItemProvider chunks, @Nullable IItemProvider extra) {
        return CrushingRecipeBuilder.crushingOre(ore, chunks, CRUSHING_ORE_TIME, extra, CRUSHING_ORE_STONE_CHANCE);
    }

    public static CrushingRecipeBuilder crushingOre(IItemProvider ore, IItemProvider chunks, @Nullable IItemProvider extra) {
        return CrushingRecipeBuilder.crushingOre(ore, chunks, CRUSHING_ORE_TIME, extra, CRUSHING_ORE_STONE_CHANCE);
    }

    public static CrushingRecipeBuilder crushingOreBonus(ITag<Item> ore, IItemProvider item) {
        return CrushingRecipeBuilder.builder(ore, CRUSHING_ORE_TIME)
                .result(item, 2)
                .result(item, 1, 0.1f)
                .result(Blocks.COBBLESTONE, 1, 0.1f);
    }

    @Override
    public String getName() {
        return "QForgeMod - Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerCrafting(consumer);
        registerSmelting(consumer);
        registerAlloySmelting(consumer);
        registerCompressingRecipes(consumer);
        registerCrushingRecipes(consumer);
        registerInfusingRecipes(consumer);
    }

    private void registerCrafting(Consumer<IFinishedRecipe> consumer) {
        registerMetalCrafting(consumer);
        registerBlockCrafting(consumer);
        registerItemCrafting(consumer);
    }

    private void registerMetalCrafting(Consumer<IFinishedRecipe> consumer) {
        for (OreMaterial metal : OreMaterial.values()) {
            if (metal.getIngot().isPresent() && metal.getNuggetTag().isPresent()) {
                ExtendedShapedRecipeBuilder.vanillaBuilder(metal.getIngot().get())
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .key('#', metal.getNuggetTag().get())
                        .build(consumer, QForgeMod.rl("metals/" + metal.getName() + "_ingot_from_nugget"));
            }
            if (metal.getNugget().isPresent() && metal.getIngotTag().isPresent()) {
                ExtendedShapelessRecipeBuilder.vanillaBuilder(metal.getNugget().get(), 9)
                        .addIngredient(metal.getIngotTag().get())
                        .build(consumer, QForgeMod.rl("metals/" + metal.getName() + "_nugget"));
            }
            if (metal.getStorageBlock().isPresent() && metal.getIngotTag().isPresent()) {
                ExtendedShapedRecipeBuilder.vanillaBuilder(metal.getStorageBlock().get())
                        .patternLine("###")
                        .patternLine("###")
                        .patternLine("###")
                        .key('#', metal.getIngotTag().get())
                        .build(consumer, QForgeMod.rl("metals/" + metal.getName() + "_block"));
            }
            if (metal.getIngot().isPresent() && metal.getStorageBlockItemTag().isPresent()) {
                ExtendedShapelessRecipeBuilder.vanillaBuilder(metal.getIngot().get(), 9)
                        .addIngredient(metal.getStorageBlockItemTag().get())
                        .build(consumer, QForgeMod.rl("metals/" + metal.getName() + "_ingot_from_block"));
            }
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private void registerBlockCrafting(Consumer<IFinishedRecipe> consumer) {
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

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.STONE_MACHINE_FRAME, 2)
                .patternLine("/#/")
                .patternLine("#s#")
                .patternLine("/#/")
                .key('/', Blocks.SMOOTH_STONE)
                .key('#', Tags.Items.GLASS)
                .key('s', Tags.Items.INGOTS_IRON)
                .addCriterion("has_item", hasItem(Blocks.SMOOTH_STONE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ALLOY_MACHINE_FRAME, 2)
                .patternLine("/#/")
                .patternLine("#s#")
                .patternLine("/#/")
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('#', Tags.Items.GLASS)
                .key('s', ModTags.Items.STEELS)
                .addCriterion("has_item", hasItem(OreMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.BASIC_ALLOY_SMELTER)
                .patternLine("###")
                .patternLine("/X/")
                .patternLine("O/O")
                .key('#', OreMaterial.TIN.getIngotTag().get())
                .key('/', OreMaterial.COPPER.getIngotTag().get())
                .key('X', ModBlocks.STONE_MACHINE_FRAME)
                .key('O', Blocks.BRICKS)
                .addCriterion("has_item", hasItem(ModBlocks.STONE_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ALLOY_SMELTER)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("OHO")
                .key('#', OreMaterial.BISMUTH_BRASS.getIngotTag().get())
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', Blocks.BRICKS)
                .key('H', CraftingItems.HEATING_ELEMENT)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ALLOY_SMELTER)
                .patternLine("#C#")
                .patternLine("/X/")
                .key('#', OreMaterial.BISMUTH_BRASS.getIngotTag().get())
                .key('C', ModBlocks.BASIC_ALLOY_SMELTER)
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer, QForgeMod.rl("alloy_smelter_from_basic"));

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.BASIC_CRUSHER)
                .patternLine("###")
                .patternLine("/X/")
                .patternLine("O/O")
                .key('#', OreMaterial.BRONZE.getIngotTag().get())
                .key('/', OreMaterial.ALUMINUM.getIngotTag().get())
                .key('X', ModBlocks.STONE_MACHINE_FRAME)
                .key('O', Blocks.SMOOTH_STONE)
                .addCriterion("has_item", hasItem(ModBlocks.STONE_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.CRUSHER)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("ODO")
                .key('#', OreMaterial.BISMUTH_STEEL.getIngotTag().get())
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', Blocks.SMOOTH_STONE)
                .key('D', Tags.Items.GEMS_DIAMOND)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.CRUSHER)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine(" D ")
                .key('#', OreMaterial.BISMUTH_STEEL.getIngotTag().get())
                .key('C', ModBlocks.BASIC_CRUSHER)
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('D', Tags.Items.GEMS_DIAMOND)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer, QForgeMod.rl("crusher_from_basic"));

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.COMPRESSOR)
                .patternLine("#D#")
                .patternLine("/X/")
                .patternLine("ODC")
                .key('#', Tags.Items.INGOTS_IRON)
                .key('D', Tags.Items.GEMS_DIAMOND)
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', Blocks.SMOOTH_STONE)
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.ELECTRIC_FURNACE)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("OHO")
                .key('#', Tags.Items.INGOTS_IRON)
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', Blocks.SMOOTH_STONE)
                .key('H', CraftingItems.HEATING_ELEMENT)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.REFINERY)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("OHO")
                .key('#', OreMaterial.ALUMINUM_STEEL.getIngotTag().get())
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', ModItems.EMPTY_CANISTER)
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', OreMaterial.ELECTRUM.getIngotTag().get())
                .key('H', CraftingItems.HEATING_ELEMENT)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.MIXER)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("OHO")
                .key('#', OreMaterial.BISMUTH_STEEL.getIngotTag().get())
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', ModItems.EMPTY_CANISTER)
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', OreMaterial.BRASS.getIngotTag().get())
                .key('H', Items.PISTON)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.SOLIDIFIER)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("OHO")
                .key('#', OreMaterial.STEEL.getIngotTag().get())
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', ModItems.EMPTY_CANISTER)
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', OreMaterial.SILVER.getIngotTag().get())
                .key('H', Items.IRON_BARS)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.INFUSER)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("OPO")
                .key('#', OreMaterial.BISMUTH_BRASS.getIngotTag().get())
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', ModItems.EMPTY_CANISTER)
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', OreMaterial.NICKEL.getIngotTag().get())
                .key('P', ModTags.Items.PLASTIC)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.PUMP)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("OHO")
                .key('#', OreMaterial.ALUMINUM.getIngotTag().get())
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', OreMaterial.INVAR.getIngotTag().get())
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', Items.BUCKET)
                .key('H', Items.PISTON)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.COAL_GENERATOR)
                .patternLine("###")
                .patternLine("/X/")
                .patternLine("OAO")
                .key('#', Tags.Items.INGOTS_IRON)
                .key('/', OreMaterial.COPPER.getIngotTag().get())
                .key('X', ModBlocks.STONE_MACHINE_FRAME)
                .key('O', Tags.Items.COBBLESTONE)
                .key('A', OreMaterial.REFINED_IRON.getIngotTag().get())
                .addCriterion("has_item", hasItem(ModBlocks.STONE_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.LAVA_GENERATOR)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("#O#")
                .key('#', OreMaterial.INVAR.getIngotTag().get())
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('O', Blocks.SMOOTH_STONE)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.DIESEL_GENERATOR)
                .patternLine("#C#")
                .patternLine("/X/")
                .patternLine("#B#")
                .key('#', OreMaterial.STEEL.getIngotTag().get())
                .key('C', CraftingItems.CIRCUIT_BOARD)
                .key('/', Ingredient.fromItemListStream(Stream.of(
                        new Ingredient.TagList(OreMaterial.PLATINUM.getNuggetTag().get()),
                        new Ingredient.TagList(OreMaterial.SILVER.getIngotTag().get())
                )))
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('B', Items.BUCKET)
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.BATTERY_BOX)
                .patternLine("#B#")
                .patternLine("/X/")
                .patternLine("L/L")
                .key('#', OreMaterial.ALUMINUM.getIngotTag().get())
                .key('B', ModItems.BATTERY)
                .key('/', ModBlocks.WIRE)
                .key('X', ModBlocks.ALLOY_MACHINE_FRAME)
                .key('L', OreMaterial.LEAD.getIngotTag().get())
                .addCriterion("has_item", hasItem(ModBlocks.ALLOY_MACHINE_FRAME))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.WIRE, 12)
                .patternLine("///")
                .patternLine("###")
                .key('/', Ingredient.fromItemListStream(Stream.of(
                        new Ingredient.TagList(OreMaterial.COPPER.getIngotTag().get()),
                        new Ingredient.TagList(OreMaterial.REFINED_IRON.getIngotTag().get())
                )))
                .key('#', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .addCriterion("has_item", hasItem(OreMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .build(consumer);
    }

    @SuppressWarnings({"OptionalGetWithoutIsPresent", "ConstantConditions"})
    private void registerItemCrafting(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(CraftingItems.CIRCUIT_BOARD, 3)
                .patternLine("/G/")
                .patternLine("###")
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('G', Tags.Items.INGOTS_GOLD)
                .key('#', OreMaterial.COPPER.getIngotTag().get())
                .addCriterion("has_item", hasItem(OreMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(CraftingItems.HEATING_ELEMENT, 2)
                .patternLine("##")
                .patternLine("##")
                .patternLine("/ ")
                .key('#', OreMaterial.COPPER.getIngotTag().get())
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .addCriterion("has_item", hasItem(OreMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(CraftingItems.PLASTIC_SHEET)
                .patternLine("##")
                .patternLine("##")
                .key('#', CraftingItems.PLASTIC_PELLETS)
                .addCriterion("has_item", hasItem(CraftingItems.PLASTIC_PELLETS))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(CraftingItems.UPGRADE_CASE, 2)
                .patternLine("###")
                .patternLine("###")
                .patternLine("///")
                .key('#', ModTags.Items.PLASTIC)
                .key('/', Tags.Items.NUGGETS_GOLD)
                .addCriterion("has_item", hasItem(ModTags.Items.PLASTIC))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MachineUpgrades.PROCESSING_SPEED)
                .addIngredient(CraftingItems.UPGRADE_CASE)
                .addIngredient(Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .addIngredient(OreMaterial.SILVER.getIngotTag().get())
                .addIngredient(OreMaterial.SILVER.getIngotTag().get())
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_CASE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MachineUpgrades.OUTPUT_CHANCE)
                .addIngredient(CraftingItems.UPGRADE_CASE)
                .addIngredient(Tags.Items.STORAGE_BLOCKS_LAPIS)
                .addIngredient(OreMaterial.PLATINUM.getIngotTag().get())
                .addIngredient(OreMaterial.PLATINUM.getIngotTag().get())
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_CASE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MachineUpgrades.ENERGY_EFFICIENCY)
                .addIngredient(CraftingItems.UPGRADE_CASE)
                .addIngredient(Items.GLOWSTONE)
                .addIngredient(OreMaterial.ELECTRUM.getIngotTag().get())
                .addIngredient(OreMaterial.ELECTRUM.getIngotTag().get())
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_CASE))
                .build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(MachineUpgrades.RANGE)
                .addIngredient(CraftingItems.UPGRADE_CASE)
                .addIngredient(Tags.Items.ENDER_PEARLS)
                .addIngredient(OreMaterial.INVAR.getIngotTag().get())
                .addIngredient(OreMaterial.INVAR.getIngotTag().get())
                .addCriterion("has_item", hasItem(CraftingItems.UPGRADE_CASE))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.WRENCH)
                .patternLine("/ /")
                .patternLine(" # ")
                .patternLine(" / ")
                .key('/', Tags.Items.INGOTS_IRON)
                .key('#', OreMaterial.REFINED_IRON.getIngotTag().get())
                .addCriterion("has_item", hasItem(OreMaterial.REFINED_IRON.getIngotTag().get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModItems.BATTERY)
                .patternLine(" / ")
                .patternLine("#X#")
                .patternLine("LXL")
                .key('/', OreMaterial.REDSTONE_ALLOY.getIngotTag().get())
                .key('#', Tags.Items.INGOTS_IRON)
                .key('X', Tags.Items.DUSTS_REDSTONE)
                .key('L', OreMaterial.LEAD.getIngotTag().get())
                .addCriterion("has_item", hasItem(OreMaterial.REDSTONE_ALLOY.getIngotTag().get()))
                .build(consumer);

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.HAND_PUMP)
                .patternLine("/C#")
                .patternLine(" B#")
                .key('/', OreMaterial.ALUMINUM.getIngotTag().get())
                .key('C', ModItems.EMPTY_CANISTER)
                .key('#', ModTags.Items.PLASTIC)
                .key('B', ModItems.BATTERY)
                .build(consumer);

        for (Tools tools : Tools.values()) {
            QForgeMod.LOGGER.info("Loading recipe for tool: " + tools.getName());
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getSword())
                    .patternLine("X")
                    .patternLine("X")
                    .patternLine("/")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getAxe())
                    .patternLine("XX")
                    .patternLine("X/")
                    .patternLine(" /")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getAxe())
                    .patternLine("XX")
                    .patternLine("/X")
                    .patternLine("/ ")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer, new ResourceLocation(QForgeMod.modId, tools.getAxe().getName() + "_mirror"));
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getShovel())
                    .patternLine("X")
                    .patternLine("/")
                    .patternLine("/")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getPickaxe())
                    .patternLine("XXX")
                    .patternLine(" / ")
                    .patternLine(" / ")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getHoe())
                    .patternLine("XX")
                    .patternLine(" /")
                    .patternLine(" /")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getHoe())
                    .patternLine("XX")
                    .patternLine("/ ")
                    .patternLine("/ ")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer, new ResourceLocation(QForgeMod.modId, tools.getHoe().getName() + "_mirror"));
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getKatana())
                    .patternLine(" X")
                    .patternLine("X ")
                    .patternLine("X/")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getKatana())
                    .patternLine("X ")
                    .patternLine(" X")
                    .patternLine("/X")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer, new ResourceLocation(QForgeMod.modId, tools.getKatana().getName() + "_mirror"));
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getLongsword())
                    .patternLine("X ")
                    .patternLine("X ")
                    .patternLine("X/")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getLongsword())
                    .patternLine(" X")
                    .patternLine(" X")
                    .patternLine("/X")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer, new ResourceLocation(QForgeMod.modId, tools.getLongsword().getName() + "_mirror"));
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getBroadsword())
                    .patternLine("XX")
                    .patternLine("XX")
                    .patternLine("/ ")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getBroadsword())
                    .patternLine("XX")
                    .patternLine("XX")
                    .patternLine(" /")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer, new ResourceLocation(QForgeMod.modId, tools.getBroadsword().getName() + "_mirror"));
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getHammer())
                    .patternLine("XXX")
                    .patternLine("XXX")
                    .patternLine(" / ")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getExcavator())
                    .patternLine("XX ")
                    .patternLine("X/ ")
                    .patternLine("  /")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getExcavator())
                    .patternLine(" XX")
                    .patternLine(" /X")
                    .patternLine("/  ")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer, new ResourceLocation(QForgeMod.modId, tools.getExcavator().getName() + "_mirror"));
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getBattleaxe())
                    .patternLine("XXX")
                    .patternLine("X/X")
                    .patternLine(" / ")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getLumberAxe())
                    .patternLine("XXX")
                    .patternLine("X/ ")
                    .patternLine(" / ")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer);
            ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getLumberAxe())
                    .patternLine("XXX")
                    .patternLine(" /X")
                    .patternLine(" / ")
                    .key('/', () -> tools.getHandleMaterial().get())
                    .key('X', () -> tools.getBaseMaterial().get())
                    .build(consumer, new ResourceLocation(QForgeMod.modId, tools.getLumberAxe().getName() + "_mirror"));
            if (tools.getArmorSubMaterial() != null) {
                ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getHelmet())
                        .patternLine("XXX")
                        .patternLine("XOX")
                        .key('O', () -> tools.getArmorSubMaterial().get())
                        .key('X', () -> tools.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getChestplate())
                        .patternLine("XOX")
                        .patternLine("XXX")
                        .patternLine("XXX")
                        .key('O', () -> tools.getArmorSubMaterial().get())
                        .key('X', () -> tools.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getLeggings())
                        .patternLine("XXX")
                        .patternLine("XOX")
                        .patternLine("X X")
                        .key('O', () -> tools.getArmorSubMaterial().get())
                        .key('X', () -> tools.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getBoots())
                        .patternLine("X X")
                        .patternLine("XOX")
                        .key('O', () -> tools.getArmorSubMaterial().get())
                        .key('X', () -> tools.getBaseMaterial().get())
                        .build(consumer);
            } else {
                ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getHelmet())
                        .patternLine("XXX")
                        .patternLine("X X")
                        .key('X', () -> tools.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getChestplate())
                        .patternLine("X X")
                        .patternLine("XXX")
                        .patternLine("XXX")
                        .key('X', () -> tools.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getLeggings())
                        .patternLine("XXX")
                        .patternLine("X X")
                        .patternLine("X X")
                        .key('X', () -> tools.getBaseMaterial().get())
                        .build(consumer);
                ExtendedShapedRecipeBuilder.vanillaBuilder(tools.getBoots())
                        .patternLine("X X")
                        .patternLine("X X")
                        .key('X', () -> tools.getBaseMaterial().get())
                        .build(consumer);
            }
        }

        ExtendedShapedRecipeBuilder.vanillaBuilder(ModItems.EMPTY_CANISTER, 8)
                .patternLine(" # ")
                .patternLine("# #")
                .patternLine(" # ")
                .key('#', OreMaterial.ALUMINUM.getIngotTag().get())
                .build(consumer);

        ExtendedShapelessRecipeBuilder.vanillaBuilder(ModItems.EMPTY_CANISTER)
                .addIngredient(ModItems.CANISTER)
                .build(consumer, QForgeMod.rl("canister_clear"));

        ExtendedShapelessRecipeBuilder.vanillaBuilder(Items.LEATHER)
                .addIngredient(CraftingItems.ZOMBIE_LEATHER, 4)
                .build(consumer, QForgeMod.rl("leather"));
    }

    private void registerSmelting(Consumer<IFinishedRecipe> consumer) {
        for (OreMaterial metal : OreMaterial.values()) {
            if (metal.getIngot().isPresent() && (metal.getChunksTag().isPresent() || metal.getDustTag().isPresent())) {
                smeltingAndBlasting(consumer, metal.getName() + "_ingot",
                        metal.getSmeltables(false), metal.getIngot().get());
            }
            if (metal.getIngot().isPresent() && metal.getOreItemTag().isPresent()) {
                smeltingAndBlasting(consumer, metal.getName() + "_ingot_from_ore",
                        Ingredient.fromTag(metal.getOreItemTag().get()), metal.getIngot().get());
            }
        }

        smeltingAndBlasting(consumer, "iron_ingot", OreMaterial.IRON.getSmeltables(false), Items.IRON_INGOT);
        smeltingAndBlasting(consumer, "gold_ingot", OreMaterial.GOLD.getSmeltables(false), Items.GOLD_INGOT);

        assert (OreMaterial.REFINED_IRON.getIngot().isPresent());
        smeltingAndBlasting(consumer, "refined_iron_ingot", Ingredient.fromTag(Tags.Items.INGOTS_IRON), OreMaterial.REFINED_IRON.getIngot().get());
    }

    private void smeltingAndBlasting(Consumer<IFinishedRecipe> consumer, String name, Ingredient ingredient, IItemProvider result) {
        CookingRecipeBuilder.smeltingRecipe(ingredient, result, 1f, 200)
                .addCriterion("has_item", hasItem(Blocks.FURNACE))
                .build(consumer, QForgeMod.rl("smelting/" + name));
        CookingRecipeBuilder.blastingRecipe(ingredient, result, 1f, 100)
                .addCriterion("has_item", hasItem(Blocks.FURNACE))
                .build(consumer, QForgeMod.rl("blasting/" + name));
    }
}
