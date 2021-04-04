package com.qsoftware.forgemod.modules.items;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.modules.tiles.blocks.machines.MetalBlock;
import com.qsoftware.forgemod.modules.environment.ores.DefaultOre;
import com.qsoftware.forgemod.modules.environment.ores.IOre;
import com.qsoftware.forgemod.modules.environment.ores.Ores;
import com.qsoftware.forgemod.modules.ui.ModItemGroups;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public enum OreMaterial implements IOreMaterial {
    // Metals
    REDSTONE_ALLOY(builderAlloy("redstone_alloy", 2)),
    REFINED_IRON(builder("refined_iron").ingot()),
    COMPRESSED_IRON(builder("compressed_iron").ingot()),
    IRON(builder("iron").chunks().dust().ingotTagOnly().nuggetTagOnly()),
    GOLD(builder("gold").chunks().dust().ingotTagOnly().nuggetTagOnly()),
    COPPER(builderBaseWithOre("copper", Ores.COPPER, 1)),
    TIN(builderBaseWithOre("tin", Ores.TIN, 1)),
    SILVER(builderBaseWithOre("silver", Ores.SILVER, 1)),
    LEAD(builderBaseWithOre("lead", Ores.LEAD, 1)),
    NICKEL(builderBaseWithOre("nickel", Ores.NICKEL, 1)),
    PLATINUM(builderBaseWithOre("platinum", Ores.PLATINUM, 2)),
    ZINC(builderBaseWithOre("zinc", Ores.ZINC, 1)),
    BISMUTH(builderBaseWithOre("bismuth", Ores.BISMUTH, 1)),
    ALUMINUM(builderBaseWithOre("aluminum", Ores.BAUXITE, 1), "bauxite"),
    URANIUM(builderBaseWithOre("uranium", Ores.URANIUM, 2)),
    BRONZE(builderAlloy("bronze", 1)),
    BRASS(builderAlloy("brass", 1)),
    INVAR(builderAlloy("invar", 1)),
    ELECTRUM(builderAlloy("electrum", 1)),
    STEEL(builderAlloy("steel", 1)),
    BISMUTH_BRASS(builderAlloy("bismuth_brass", 1)),
    ALUMINUM_STEEL(builderAlloy("aluminum_steel", 1)),
    BISMUTH_STEEL(builderAlloy("bismuth_steel", 1)),
    SIGNALUM(builderAlloy("signalum", 1)),
    LUMIUM(builderAlloy("lumium", 1)),
    ENDERIUM(builderAlloy("enderium", 3)),
    COBALT(builderBaseWithOre("cobalt", Ores.COBALT, 4)),
    ULTRINIUM(builderBaseWithOre("ultrinium", Ores.ULTRINIUM, 5)),
    INFINITY(builderBaseWithOre("infinity", Ores.INFINITY, 6)),
    RUBY(builder("ruby").ore(Ores.RUBY).storageBlock(2)),
    AMETHYST(builder("amethyst").ore(Ores.AMETHYST).storageBlock(2)),
    SAPHIRE(builder("saphire").ore(Ores.SAPHIRE).storageBlock(2)),
    AMBER(builder("amber").ore(Ores.AMBER).storageBlock(2)),
    PERIDOT(builder("peridot").ore(Ores.PERIDOT).storageBlock(2)),
    BERYL(builder("beryl").ore(Ores.BERYL).storageBlock(2)),
    TOPAZ(builder("topaz").ore(Ores.TOPAZ).storageBlock(2)),
    TANZANITE(builder("tanzanite").ore(Ores.TANZANITE).storageBlock(2)),
    MALACHITE(builder("malachite").ore(Ores.MALACHITE).storageBlock(2))
    ;
//    OBSIDIAN(builder("obsidian").dust().ingot().ingotTagOnly());

//    // Gems
//    RUBY(builderGem("ruby", Ore.RUBY)),
//    BERYL(builderGem("beryl", Ore.BERYL)),
//    MALACHITE(builderGem("malachite", Ore.MALACHITE)),
//    PERIDOT(builderGem("peridot", Ore.PERIDOT)),
//    AMBER(builderGem("amber", Ore.AMBER)),
//    SAPPHIRE(builderGem("sapphire", Ore.SAPPHIRE)),
//    AMETHYST(builderGem("amethyst", Ore.AMETHYST)),
//    TANZANITE(builderGem("tanzanite", Ore.TANZANITE)),
//    ;

    private final String oreName;
    private final Supplier<Block> oreSupplier;
    private final Supplier<Block> storageBlockSupplier;
    private final Supplier<Item> chunksSupplier;
    private final Supplier<Item> dustSupplier;
    private final Supplier<Item> ingotSupplier;
    private final Supplier<Item> nuggetSupplier;
    private final Supplier<Item> gemSupplier;
    private final ITag.INamedTag<Block> storageBlockTag;
    private final ITag.INamedTag<Block> oreTag;
    private final ITag.INamedTag<Item> storageBlockItemTag;
    private final ITag.INamedTag<Item> oreItemTag;
    private final ITag.INamedTag<Item> chunksTag;
    private final ITag.INamedTag<Item> dustTag;
    private final ITag.INamedTag<Item> ingotTag;
    private final ITag.INamedTag<Item> nuggetTag;
    @SuppressWarnings("NonFinalFieldInEnum")
    private BlockRegistryObject<Block> ore;
    @SuppressWarnings("NonFinalFieldInEnum")
    private BlockRegistryObject<Block> storageBlock;
    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<Item> chunks;
    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<Item> dust;
    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<Item> ingot;
    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<Item> gem;
    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<Item> nugget;;

    OreMaterial(Builder builder) {
        this(builder, builder.name);
    }

    OreMaterial(Builder builder, String oreName) {
        if (!builder.name.equals(this.getName())) {
            throw new IllegalArgumentException("Builder name is incorrect, should be " + this.getName());
        }
        this.oreName = oreName;
        this.storageBlockSupplier = builder.storageBlock;
        this.oreSupplier = builder.ore;
        this.chunksSupplier = builder.chunks;
        this.dustSupplier = builder.dust;
        this.ingotSupplier = builder.ingot;
        this.gemSupplier = builder.gem;
        this.nuggetSupplier = builder.nugget;
        this.oreTag = builder.oreTag;
        this.storageBlockTag = builder.storageBlockTag;
        this.oreItemTag = this.oreTag != null ? Builder.itemTag(this.oreTag.getName()) : null;
        this.storageBlockItemTag = this.storageBlockTag != null ? Builder.itemTag(this.storageBlockTag.getName()) : null;
        this.chunksTag = builder.chunksTag;
        this.dustTag = builder.dustTag;
        this.ingotTag = builder.ingotTag;
        this.nuggetTag = builder.nuggetTag;
    }

    public static void registerBlocks() {
        for (OreMaterial metal : values()) {
            if (metal.oreSupplier != null) {
                String name = metal.oreName + "_ore";
                metal.ore = new BlockRegistryObject<>(Registration.BLOCKS.register(name, metal.oreSupplier));
                Registration.ITEMS.register(name, () ->
                        new BlockItem(metal.ore.get(), new Item.Properties().group(ModItemGroups.ORES)));
            }
        }
        for (OreMaterial metal : values()) {
            if (metal.storageBlockSupplier != null) {
                String name = metal.getName() + "_block";
                metal.storageBlock = new BlockRegistryObject<>(Registration.BLOCKS.register(name, metal.storageBlockSupplier));
                Registration.ITEMS.register(name, () ->
                        new BlockItem(metal.storageBlock.get(), new Item.Properties().group(ModItemGroups.ORES)));
            }
        }
    }

    public static void registerItems() {
        for (OreMaterial metal : values()) {
            if (metal.chunksSupplier != null) {
                metal.chunks = Registration.ITEMS.register(
                        metal.oreName + "_chunks", metal.chunksSupplier);
            }
            if (metal.dustSupplier != null) {
                metal.dust = Registration.ITEMS.register(
                        metal.getName() + "_dust", metal.dustSupplier);
            }
            if (metal.ingotSupplier != null) {
                metal.ingot = Registration.ITEMS.register(
                        metal.getName() + "_ingot", metal.ingotSupplier);
            }
            if (metal.gemSupplier != null) {
                metal.gem = Registration.ITEMS.register(
                        metal.getName(), metal.gemSupplier);
            }
            if (metal.nuggetSupplier != null) {
                metal.nugget = Registration.ITEMS.register(
                        metal.getName() + "_nugget", metal.nuggetSupplier);
            }
        }
    }

    private static Builder builder(String name) {
        return new Builder(name);
    }

    private static Builder builderBaseWithOre(String name, IOre ore, int harvestLevel) {
        return builder(name).storageBlock(harvestLevel).ore(ore).chunks().dust().ingot().nugget();
    }

    private static Builder builderAlloy(String name, int harvestLevel) {
        return builder(name).storageBlock(harvestLevel).dust().ingot().nugget();
    }

    private static Builder builderGem(String name, DefaultOre ore, int harvestLevel) {
        return builder(name).storageBlock(harvestLevel).ore(ore).dust().gem();
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public Optional<Block> getOre() {
        return ore != null ? Optional.of(ore.get()) : Optional.empty();
    }

    @Override
    public Optional<Block> getStorageBlock() {
        return storageBlock != null ? Optional.of(storageBlock.get()) : Optional.empty();
    }

    @Override
    public Optional<Item> getChunks() {
        return chunks != null ? Optional.of(chunks.get()) : Optional.empty();
    }

    @Override
    public Optional<Item> getDust() {
        return dust != null ? Optional.of(dust.get()) : Optional.empty();
    }

    @Override
    public Optional<Item> getIngot() {
        return ingot != null ? Optional.of(ingot.get()) : Optional.empty();
    }

    @Override
    public Optional<Item> getGem() {
        return gem != null ? Optional.of(gem.get()) : Optional.empty();
    }

    @Override
    public Optional<Item> getNugget() {
        return nugget != null ? Optional.of(nugget.get()) : Optional.empty();
    }

    @Override
    public Optional<ITag.INamedTag<Block>> getOreTag() {
        return oreTag != null ? Optional.of(oreTag) : Optional.empty();
    }

    @Override
    public Optional<ITag.INamedTag<Block>> getStorageBlockTag() {
        return storageBlockTag != null ? Optional.of(storageBlockTag) : Optional.empty();
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getOreItemTag() {
        return oreItemTag != null ? Optional.of(oreItemTag) : Optional.empty();
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getStorageBlockItemTag() {
        return storageBlockItemTag != null ? Optional.of(storageBlockItemTag) : Optional.empty();
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getChunksTag() {
        return chunksTag != null ? Optional.of(chunksTag) : Optional.empty();
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getDustTag() {
        return dustTag != null ? Optional.of(dustTag) : Optional.empty();
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getIngotTag() {
        return ingotTag != null ? Optional.of(ingotTag) : Optional.empty();
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getNuggetTag() {
        return nuggetTag != null ? Optional.of(nuggetTag) : Optional.empty();
    }

    public Ingredient getSmeltables() {
        return getSmeltables(true);
    }

    public Ingredient getSmeltables(boolean includeIngot) {
        Stream.Builder<ITag.INamedTag<Item>> builder = Stream.builder();
        if (includeIngot) {
            getIngotTag().ifPresent(builder::add);
        }
        getChunksTag().ifPresent(builder::add);
        getDustTag().ifPresent(builder::add);
        return Ingredient.fromItemListStream(builder.build().map(Ingredient.TagList::new));
    }

    private static class Builder {
        final String name;
        Supplier<Block> ore;
        Supplier<Block> storageBlock;
        Supplier<Item> chunks;
        Supplier<Item> dust;
        Supplier<Item> ingot;
        Supplier<Item> gem;
        Supplier<Item> nugget;
        ITag.INamedTag<Block> oreTag;
        ITag.INamedTag<Block> storageBlockTag;
        ITag.INamedTag<Item> chunksTag;
        ITag.INamedTag<Item> dustTag;
        ITag.INamedTag<Item> ingotTag;
        ITag.INamedTag<Item> gemTag;
        ITag.INamedTag<Item> nuggetTag;

        Supplier<ToolsOld> tools;

        Builder(String name) {
            this.name = name;
        }

        private static ITag.INamedTag<Block> blockTag(String path) {
            return BlockTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> itemTag(String path) {
            return ItemTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> itemTag(ResourceLocation tag) {
            return ItemTags.makeWrapperTag(tag.toString());
        }

        Builder ore(IOre ore) {
            this.ore = () -> new OreBlock(AbstractBlock.Properties.create(Material.ROCK)
                    .setRequiresTool()
                    .harvestTool(ToolType.PICKAXE)
                    .harvestLevel(ore.getHarvestLevel())
                    .hardnessAndResistance(ore.getHardness(), 3)
                    .sound(SoundType.STONE));
            this.oreTag = blockTag("ores/" + name);
            return this;
        }

        Builder tools(Supplier<ToolsOld> tools) {
            this.tools = tools;
            return this;
        }

        Builder storageBlock(int harvestLevel) {
            this.storageBlock = () -> new MetalBlock(harvestLevel);
            this.storageBlockTag = blockTag("storage_blocks/" + name);
            return this;
        }

        Builder chunks() {
            this.chunks = () -> new Item(new Item.Properties().group(ModItemGroups.METAL_CRAFTABLES));
            this.chunksTag = itemTag(QForgeMod.rl("chunks/" + name));
            return this;
        }

        Builder dust() {
            this.dust = () -> new Item(new Item.Properties().group(ModItemGroups.METAL_CRAFTABLES));
            this.dustTag = itemTag("dusts/" + name);
            return this;
        }

        Builder ingot() {
            this.ingot = () -> new Item(new Item.Properties().group(ModItemGroups.METAL_CRAFTABLES));
            this.ingotTag = itemTag("ingots/" + name);
            return this;
        }

        Builder ingotTagOnly() {
            this.ingotTag = itemTag("ingots/" + name);
            return this;
        }

        Builder nugget() {
            this.nugget = () -> new Item(new Item.Properties().group(ModItemGroups.METAL_CRAFTABLES));
            this.nuggetTag = itemTag("nuggets/" + name);
            return this;
        }

        Builder nuggetTagOnly() {
            this.nuggetTag = itemTag("nuggets/" + name);
            return this;
        }

        Builder gem() {
            this.gem = () -> new Item(new Item.Properties().group(ModItemGroups.GEMS));
            this.gemTag = itemTag("gems/" + name);
            return this;
        }
    }
}
