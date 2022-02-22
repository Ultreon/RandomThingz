package com.ultreon.randomthingz.common.item;

import com.ultreon.modlib.silentlib.registry.BlockRegistryObject;
import com.ultreon.modlib.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.MetalBlock;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.Toolset;
import com.ultreon.randomthingz.registration.Registration;
import com.ultreon.randomthingz.world.gen.ores.ItemMaterialOre;
import com.ultreon.randomthingz.world.gen.ores.Ore;
import com.ultreon.randomthingz.world.gen.ores.Ores;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemMaterial implements BaseItemMaterial {
    // Metals
    public static final ItemMaterial REDSTONE_ALLOY = new ItemMaterial(builderAlloy("redstone_alloy", ToolRequirement.IRON));
    public static final ItemMaterial REFINED_IRON = new ItemMaterial(builder("refined_iron").ingot());
    public static final ItemMaterial COMPRESSED_IRON = new ItemMaterial(builder("compressed_iron").ingot());
    public static final ItemMaterial IRON = new ItemMaterial(builder("iron").chunks().dust().ingotTagOnly().nuggetTagOnly());
    public static final ItemMaterial GOLD = new ItemMaterial(builder("gold").chunks().dust().ingotTagOnly().nuggetTagOnly());
    public static final ItemMaterial COPPER = new ItemMaterial(builderBaseWithOre("copper", Ores.COPPER));
    public static final ItemMaterial TIN = new ItemMaterial(builderBaseWithOre("tin", Ores.TIN));
    public static final ItemMaterial SILVER = new ItemMaterial(builderBaseWithOre("silver", Ores.SILVER));
    public static final ItemMaterial LEAD = new ItemMaterial(builderBaseWithOre("lead", Ores.LEAD));
    public static final ItemMaterial NICKEL = new ItemMaterial(builderBaseWithOre("nickel", Ores.NICKEL));
    public static final ItemMaterial PLATINUM = new ItemMaterial(builderBaseWithOre("platinum", Ores.PLATINUM));
    public static final ItemMaterial TUNGSTEN = new ItemMaterial(builderAlloy("tungsten", ToolRequirement.IRON));
    public static final ItemMaterial ZINC = new ItemMaterial(builderBaseWithOre("zinc", Ores.ZINC));
    public static final ItemMaterial BISMUTH = new ItemMaterial(builderBaseWithOre("bismuth", Ores.BISMUTH));
    public static final ItemMaterial ALUMINUM = new ItemMaterial(builderBaseWithOre("aluminum", Ores.BAUXITE), "bauxite");
    public static final ItemMaterial URANIUM = new ItemMaterial(builderBaseWithOre("uranium", Ores.URANIUM));
    public static final ItemMaterial BRONZE = new ItemMaterial(builderAlloy("bronze", ToolRequirement.STONE));
    public static final ItemMaterial BRASS = new ItemMaterial(builderAlloy("brass", ToolRequirement.STONE));
    public static final ItemMaterial INVAR = new ItemMaterial(builderAlloy("invar", ToolRequirement.STONE));
    public static final ItemMaterial ELECTRUM = new ItemMaterial(builderAlloy("electrum", ToolRequirement.STONE));
    public static final ItemMaterial STEEL = new ItemMaterial(builderAlloy("steel", ToolRequirement.STONE));
    public static final ItemMaterial BISMUTH_BRASS = new ItemMaterial(builderAlloy("bismuth_brass", ToolRequirement.STONE));
    public static final ItemMaterial ALUMINUM_STEEL = new ItemMaterial(builderAlloy("aluminum_steel", ToolRequirement.STONE));
    public static final ItemMaterial BISMUTH_STEEL = new ItemMaterial(builderAlloy("bismuth_steel", ToolRequirement.STONE));
    public static final ItemMaterial SIGNALUM = new ItemMaterial(builderAlloy("signalum", ToolRequirement.STONE));
    public static final ItemMaterial LUMIUM = new ItemMaterial(builderAlloy("lumium", ToolRequirement.STONE));
    public static final ItemMaterial ENDERIUM = new ItemMaterial(builderAlloy("enderium", ToolRequirement.DIAMOND));
    public static final ItemMaterial COBALT = new ItemMaterial(builderBaseWithOre("cobalt", Ores.COBALT));
    public static final ItemMaterial ULTRINIUM = new ItemMaterial(builderBaseWithOre("ultrinium", Ores.ULTRINIUM));
    public static final ItemMaterial INFINITY = new ItemMaterial(builderBaseWithOre("infinity", Ores.INFINITY));
    public static final ItemMaterial RUBY = new ItemMaterial(builder("ruby").overworldOre(Ores.RUBY).storageBlock(ToolRequirement.IRON));
    public static final ItemMaterial AMETHYST = new ItemMaterial(builder("amethyst").overworldOre(Ores.AMETHYST).storageBlock(ToolRequirement.IRON));
    public static final ItemMaterial AQUAMARINE = new ItemMaterial(builder("aquamarine").overworldOre(Ores.AQUAMARINE).storageBlock(ToolRequirement.IRON));
    public static final ItemMaterial SAPPHIRE = new ItemMaterial(builder("sapphire").overworldOre(Ores.SAPPHIRE).storageBlock(ToolRequirement.IRON));
    public static final ItemMaterial AMBER = new ItemMaterial(builder("amber").overworldOre(Ores.AMBER).storageBlock(ToolRequirement.IRON));
    public static final ItemMaterial PERIDOT = new ItemMaterial(builder("peridot").overworldOre(Ores.PERIDOT).storageBlock(ToolRequirement.IRON));
    public static final ItemMaterial BERYL = new ItemMaterial(builder("beryl").overworldOre(Ores.BERYL).storageBlock(ToolRequirement.IRON));
    public static final ItemMaterial TOPAZ = new ItemMaterial(builder("topaz").overworldOre(Ores.TOPAZ).storageBlock(ToolRequirement.IRON));
    public static final ItemMaterial TANZANITE = new ItemMaterial(builder("tanzanite").overworldOre(Ores.TANZANITE).storageBlock(ToolRequirement.IRON));
    public static final ItemMaterial MALACHITE = new ItemMaterial(builder("malachite").overworldOre(Ores.MALACHITE).storageBlock(ToolRequirement.IRON));

    private final String oreName;
    private final Supplier<Block> stoneOreSupplier;
    private final Supplier<Block> deepslateOreSupplier;
    private final Supplier<Block> netherOreSupplier;
    private final Supplier<Block> storageBlockSupplier;
    private final Supplier<Item> chunksSupplier;
    private final Supplier<Item> dustSupplier;
    private final Supplier<Item> ingotSupplier;
    private final Supplier<Item> nuggetSupplier;
    private final Supplier<Item> gemSupplier;
    private final Tag.Named<Block> storageBlockTag;
    private final Tag.Named<Block> oreTag;
    private final Tag.Named<Item> storageBlockItemTag;
    private final Tag.Named<Item> oreItemTag;
    private final Tag.Named<Item> chunksTag;
    private final Tag.Named<Item> dustTag;
    private final Tag.Named<Item> ingotTag;
    private final Tag.Named<Item> nuggetTag;
    private final ToolRequirement harvestRequirement;
    private BlockRegistryObject<Block> stoneOre;
    private BlockRegistryObject<Block> deepslateOre;
    private BlockRegistryObject<Block> netherOre;
    private BlockRegistryObject<Block> storageBlock;
    private ItemRegistryObject<Item> chunks;
    private ItemRegistryObject<Item> dust;
    private ItemRegistryObject<Item> ingot;
    private ItemRegistryObject<Item> gem;
    private ItemRegistryObject<Item> nugget;

    private static final List<ItemMaterial> values = new ArrayList<>();
    private static final Map<String, ItemMaterial> map = new HashMap<>();
    private final Collection<Tag.Named<Block>> dataGenTags = new ArrayList<>();

    ItemMaterial(Builder builder) {
        this(builder, builder.name);
    }

    ItemMaterial(Builder builder, String oreName) {
        if (!builder.name.equals(this.getName())) {
            throw new IllegalArgumentException("Builder name is incorrect, should be " + this.getName());
        }
        this.oreName = oreName;
        this.storageBlockSupplier = builder.storageBlock;
        this.stoneOreSupplier = builder.stoneOre;
        this.deepslateOreSupplier = builder.deepslateOre;
        this.netherOreSupplier = builder.netherOre;
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
        this.harvestRequirement = builder.harvestRequirement;
        this.dataGenTags.addAll(builder.dataGenTags);

        map.put(oreName, this);
        values.add(this);
    }

    public static void registerBlocks() {
        for (ItemMaterial metal : values()) {
            if (metal.stoneOreSupplier != null) {
                String name = metal.oreName + "_ore";
                metal.stoneOre = Registration.BLOCKS.register(name, metal.stoneOreSupplier);
                Registration.ITEMS.register(name, () ->
                        new BlockItem(metal.stoneOre.get(), new Item.Properties().tab(ModCreativeTabs.ORES)));
            }
            if (metal.deepslateOreSupplier != null) {
                String name = "deepslate_" + metal.oreName + "_ore";
                metal.deepslateOre = Registration.BLOCKS.register(name, metal.deepslateOreSupplier);
                Registration.ITEMS.register(name, () ->
                        new BlockItem(metal.deepslateOre.get(), new Item.Properties().tab(ModCreativeTabs.ORES)));
            }
            if (metal.netherOreSupplier != null) {
                String name = "nether_" + metal.oreName + "_ore";
                metal.netherOre = Registration.BLOCKS.register(name, metal.netherOreSupplier);
                Registration.ITEMS.register(name, () ->
                        new BlockItem(metal.netherOre.get(), new Item.Properties().tab(ModCreativeTabs.ORES)));
            }
        }
        for (ItemMaterial metal : values()) {
            if (metal.storageBlockSupplier != null) {
                String name = metal.getName() + "_block";
                metal.storageBlock = Registration.BLOCKS.register(name, metal.storageBlockSupplier);
                Registration.ITEMS.register(name, () ->
                        new BlockItem(metal.storageBlock.get(), new Item.Properties().tab(ModCreativeTabs.ORES)));
            }
        }
    }

    public static void registerItems() {
        for (ItemMaterial metal : values()) {
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

    /**
     * Creates base builder with ore.
     *
     * @deprecated replaced by {@link #builderBaseWithOre(String, Ore)}.
     * @param name name of the material.
     * @param ore ore object.
     * @param harvestLevel the material's harvest level.
     * @return the item material builder.
     */
    @SuppressWarnings("unused")
    @Deprecated
    public static Builder builderBaseWithOre(String name, Ore ore, @Deprecated int harvestLevel) {
        return builderBaseWithOre(name, ore);
    }

    /**
     * Creates base builder with ore.
     *
     * @param name name of the material.
     * @param ore ore object.
     * @return the item material builder.
     */
    public static Builder builderBaseWithOre(String name, Ore ore) {
        return builder(name).harvestRequirement(ore.getToolRequirement()).storageBlock(ore.getToolRequirement()).overworldOre(ore).chunks().dust().ingot().nugget();
    }

    /**
     * Creates alloy material builder.
     *
     * @param name name of the material.
     * @param toolRequirement the material's harvest level.
     * @return the item material builder.
     */
    public static Builder builderAlloy(String name, ToolRequirement toolRequirement) {
        return builder(name).storageBlock(toolRequirement).dust().ingot().nugget();
    }

    /**
     * Creates gem material builder.
     *
     * @deprecated replaced by {@link #builderGem(String, ItemMaterialOre)}.
     * @param name name of the material.
     * @param ore the ore object for the gem.
     * @param harvestLevel the material's harvest level.
     * @return the item material builder.
     */
    @SuppressWarnings("unused")
    @Deprecated
    public static Builder builderGem(String name, ItemMaterialOre ore, @Deprecated int harvestLevel) {
        return builderGem(name, ore);
    }

    /**
     * Creates gem material builder.
     *
     * @param name name of the material.
     * @param ore the ore object for the gem.
     * @return the item material builder.
     */
    public static Builder builderGem(String name, ItemMaterialOre ore) {
        return builder(name).storageBlock(ore.getToolRequirement()).overworldOre(ore).dust().gem();
    }

    /**
     * Get the material's name.
     *
     * @return resource path name for the item material.
     */
    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    /**
     * Get the stone ore block.
     *
     * @return optional value for the stone ore block.
     */
    @Override
    public Optional<Block> getStoneOre() {
        return stoneOre != null ? Optional.of(stoneOre.get()) : Optional.empty();
    }

    /**
     * Get the deepslate ore block.
     *
     * @return optional value for the deepslate ore block.
     */
    @Override
    public Optional<Block> getDeepslateOre() {
        return deepslateOre != null ? Optional.of(deepslateOre.get()) : Optional.empty();
    }

    /**
     * Get the nether ore block.
     *
     * @return optional value for the nether ore block.
     */
    @Override
    public Optional<Block> getNetherOre() {
        return netherOre != null ? Optional.of(netherOre.get()) : Optional.empty();
    }

    /**
     * Get the storage block.
     *
     * @return optional value for storage block.
     */
    @Override
    public Optional<Block> getStorageBlock() {
        return storageBlock != null ? Optional.of(storageBlock.get()) : Optional.empty();
    }

    /**
     * Get the ore chunks item.
     *
     * @return optional value for the ore chunks item.
     */
    @Override
    public Optional<Item> getChunks() {
        return chunks != null ? Optional.of(chunks.get()) : Optional.empty();
    }

    /**
     * Get the dust item.
     *
     * @return optional value for the dust item.
     */
    @Override
    public Optional<Item> getDust() {
        return dust != null ? Optional.of(dust.get()) : Optional.empty();
    }

    /**
     * Get the ingot item.
     *
     * @return optional value for the ingot item.
     */
    @Override
    public Optional<Item> getIngot() {
        return ingot != null ? Optional.of(ingot.get()) : Optional.empty();
    }

    /**
     * Get the gem item.
     *
     * @return optional value for the gem item.
     */
    @Override
    public Optional<Item> getGem() {
        return gem != null ? Optional.of(gem.get()) : Optional.empty();
    }

    /**
     * Get the nugget item.
     *
     * @return optional value for the nugget item.
     */
    @Override
    public Optional<Item> getNugget() {
        return nugget != null ? Optional.of(nugget.get()) : Optional.empty();
    }

    /**
     * Get the ore tag.
     *
     * @return optional value for the ore block tag.
     */
    @Override
    public Optional<Tag.Named<Block>> getOreTag() {
        return oreTag != null ? Optional.of(oreTag) : Optional.empty();
    }

    /**
     * Get the storage block tag.
     *
     * @return optional value for the storage block tag.
     */
    @Override
    public Optional<Tag.Named<Block>> getStorageBlockTag() {
        return storageBlockTag != null ? Optional.of(storageBlockTag) : Optional.empty();
    }

    /**
     * Get the ore item tag.
     *
     * @return optional value for the ore item tag.
     */
    @Override
    public Optional<Tag.Named<Item>> getOreItemTag() {
        return oreItemTag != null ? Optional.of(oreItemTag) : Optional.empty();
    }

    /**
     * Get the storage block item tag.
     *
     * @return optional value for the storage block item tag.
     */
    @Override
    public Optional<Tag.Named<Item>> getStorageBlockItemTag() {
        return storageBlockItemTag != null ? Optional.of(storageBlockItemTag) : Optional.empty();
    }

    /**
     * Get the chunks tag.
     *
     * @return optional value for the ore chunks item tag.
     */
    @Override
    public Optional<Tag.Named<Item>> getChunksTag() {
        return chunksTag != null ? Optional.of(chunksTag) : Optional.empty();
    }

    /**
     * Get the dust tag.
     *
     * @return optional value for the dust item tag.
     */
    @Override
    public Optional<Tag.Named<Item>> getDustTag() {
        return dustTag != null ? Optional.of(dustTag) : Optional.empty();
    }

    /**
     * Get the ingot tag.
     *
     * @return optional value for the ingot item tag.
     */
    @Override
    public Optional<Tag.Named<Item>> getIngotTag() {
        return ingotTag != null ? Optional.of(ingotTag) : Optional.empty();
    }

    /**
     * Get the gem tag.
     *
     * @return optional value for the gem item tag.
     */
    @Override
    public Optional<Tag.Named<Item>> getGemTag() {
        return Optional.empty();
    }

    /**
     * Get the nugget tag.
     *
     * @return optional value for the nugget item tag.
     */
    @Override
    public Optional<Tag.Named<Item>> getNuggetTag() {
        return nuggetTag != null ? Optional.of(nuggetTag) : Optional.empty();
    }

    public ToolRequirement getHarvestRequirement() {
        return harvestRequirement;
    }

    /**
     * Get all smeltables ingredient.
     *
     * @return ingredient object containing all smeltables for the current item material.
     */
    public Ingredient getSmeltables() {
        return getSmeltables(true);
    }

    /**
     * Get all smeltables.
     *
     * @param includeIngot whether to include the ingot item or not.
     * @return ingredient object containing all smeltables for the current item material.
     */
    public Ingredient getSmeltables(boolean includeIngot) {
        Stream.Builder<Tag.Named<Item>> builder = Stream.builder();
        if (includeIngot) {
            getIngotTag().ifPresent(builder::add);
        }
        getChunksTag().ifPresent(builder::add);
        getDustTag().ifPresent(builder::add);
        return Ingredient.fromValues(builder.build().map(Ingredient.TagValue::new));
    }

    public String name() {
        return oreName;
    }

    public static ItemMaterial[] values() {
        return values.toArray(new ItemMaterial[0]);
    }

    public static ItemMaterial fromIndex(int index) {
        return values.get(index);
    }

    public static ItemMaterial fromName(String name) {
        return map.get(name);
    }

    public Collection<Tag.Named<Block>> getDataGenTags() {
        return Collections.unmodifiableCollection(dataGenTags);
    }

    /**
     * @author Qboi
     */
    public static class Builder {
        final String name;
        Supplier<Block> stoneOre;
        Supplier<Block> deepslateOre;
        Supplier<Block> netherOre;
        Supplier<Block> storageBlock;
        Supplier<Item> chunks;
        Supplier<Item> dust;
        Supplier<Item> ingot;
        Supplier<Item> gem;
        Supplier<Item> nugget;
        Tag.Named<Block> oreTag;
        Tag.Named<Block> storageBlockTag;
        Tag.Named<Item> chunksTag;
        Tag.Named<Item> dustTag;
        Tag.Named<Item> ingotTag;
        Tag.Named<Item> gemTag;
        Tag.Named<Item> nuggetTag;
        ToolRequirement harvestRequirement;

        Supplier<Toolset> tools;
        private final List<Tag.Named<Block>> dataGenTags = new ArrayList<>();

        /**
         * @param name name of the item material.
         */
        Builder(String name) {
            this.name = name;
        }

        public static Tag.Named<Block> blockTag(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        public static Tag.Named<Item> itemTag(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        public static Tag.Named<Item> itemTag(ResourceLocation tag) {
            return ItemTags.bind(tag.toString());
        }

        public Builder overworldOre(Ore ore) {
            this.stoneOre = () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .requiresCorrectToolForDrops()
                    .strength(ore.getHardness(), ore.getResistance())
                    .sound(SoundType.STONE));
            this.deepslateOre = () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .strength(ore.getHardness(), ore.getResistance())
                    .sound(SoundType.DEEPSLATE));
            this.dataGenTags.add(BlockTags.MINEABLE_WITH_PICKAXE);
            this.dataGenTags.add(ore.getToolRequirement().getTag());
            this.harvestRequirement = ore.getToolRequirement();
            if (oreTag == null) {
                this.oreTag = blockTag("ores/" + name);
            }
            return this;
        }

        public Builder netherOre(Ore ore, ToolRequirement toolRequirement) {
            this.netherOre = () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.NETHER)
                    .requiresCorrectToolForDrops()
                    .strength(ore.getHardness(), ore.getResistance())
                    .sound(SoundType.NETHERRACK));
            this.dataGenTags.add(BlockTags.MINEABLE_WITH_PICKAXE);
            this.dataGenTags.add(toolRequirement.getTag());
            this.harvestRequirement = toolRequirement;
            if (oreTag == null) {
                this.oreTag = blockTag("ores/" + name);
            }
            return this;
        }

        public Builder tools(Supplier<Toolset> tools) {
            this.tools = tools;
            return this;
        }

        public Builder storageBlock(ToolRequirement tool) {
            this.storageBlock = () -> new MetalBlock(tool);
            this.storageBlockTag = blockTag("storage_blocks/" + name);
            this.harvestRequirement = tool;
            return this;
        }

        public Builder chunks() {
            this.chunks = () -> new Item(new Item.Properties().tab(ModCreativeTabs.METAL_CRAFTABLES));
            this.chunksTag = itemTag(RandomThingz.res("chunks/" + name));
            return this;
        }

        public Builder dust() {
            this.dust = () -> new Item(new Item.Properties().tab(ModCreativeTabs.METAL_CRAFTABLES));
            this.dustTag = itemTag("dusts/" + name);
            return this;
        }

        public Builder ingot() {
            this.ingot = () -> new Item(new Item.Properties().tab(ModCreativeTabs.METAL_CRAFTABLES));
            this.ingotTag = itemTag("ingots/" + name);
            return this;
        }

        public Builder ingotTagOnly() {
            this.ingotTag = itemTag("ingots/" + name);
            return this;
        }

        public Builder nugget() {
            this.nugget = () -> new Item(new Item.Properties().tab(ModCreativeTabs.METAL_CRAFTABLES));
            this.nuggetTag = itemTag("nuggets/" + name);
            return this;
        }

        public Builder nuggetTagOnly() {
            this.nuggetTag = itemTag("nuggets/" + name);
            return this;
        }

        public Builder gem() {
            this.gem = () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC));
            this.gemTag = itemTag("gems/" + name);
            return this;
        }

        public Builder harvestRequirement(ToolRequirement toolRequirement) {
            this.harvestRequirement = toolRequirement;
            return this;
        }
    }
}
