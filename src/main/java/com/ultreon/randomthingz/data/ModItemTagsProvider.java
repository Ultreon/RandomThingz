package com.ultreon.randomthingz.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.common.tags.ModTags;
import com.ultreon.randomthingz.item.CraftingItems;
import com.ultreon.randomthingz.item.tool.Toolset;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("deprecation")
public class ModItemTagsProvider extends ItemTagsProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    public ModItemTagsProvider(DataGenerator generatorIn, ModBlockTagsProvider blockTags) {
        super(generatorIn, blockTags);
    }

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, modId, existingFileHelper);
    }

    private static Tag.Named<Item> itemTag(ResourceLocation id) {
        return ItemTags.bind(id.toString());
    }

    private static ResourceLocation forgeId(String path) {
        return new ResourceLocation("forge", path);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    protected void addTags() {
        // Empties
        builder(forgeId("nuggets/coal"));
        builder(forgeId("storage_blocks/charcoal"));

        tag(ModTags.Items.PLASTIC).add(CraftingItems.PLASTIC_SHEET.asItem());

        tag(ModTags.Items.STEELS)
                .addTag(ItemMaterial.ALUMINUM_STEEL.getIngotTag().get())
                .addTag(ItemMaterial.BISMUTH_STEEL.getIngotTag().get())
                .addTag(ItemMaterial.STEEL.getIngotTag().get());
        tag(ModTags.Items.COAL_GENERATOR_FUELS)
                .addTag(ItemTags.COALS)
                .addTag(itemTag(forgeId("nuggets/coal")))
                .addTag(itemTag(forgeId("storage_blocks/charcoal")))
                .addTag(Tags.Items.STORAGE_BLOCKS_COAL);
        copy(ModTags.Blocks.DRYING_RACKS, ModTags.Items.DRYING_RACKS);

        tag(ModTags.Items.DUSTS_COAL).add(CraftingItems.COAL_DUST.asItem());

        TagAppender<Item> dusts = tag(itemTag(new ResourceLocation(modId, "metal_craftables/dusts")));
        TagAppender<Item> ingots = tag(itemTag(new ResourceLocation(modId, "metal_craftables/ingots")));
        TagAppender<Item> nuggets = tag(itemTag(new ResourceLocation(modId, "metal_craftables/nuggets")));
        TagAppender<Item> chunks = tag(itemTag(new ResourceLocation(modId, "metal_craftables/chunks")));

        for (ItemMaterial metal : ItemMaterial.values()) {
            metal.getOreTag().ifPresent(tag ->
                    copy(tag, metal.getOreItemTag().get()));
            metal.getStorageBlockTag().ifPresent(tag ->
                    copy(tag, metal.getStorageBlockItemTag().get()));
            metal.getChunksTag().ifPresent(tag -> {
                if (metal.getChunks().isPresent()) {
                    chunks.add(metal.getChunks().get());
                    tag(tag).add(metal.getChunks().get());
                }
            });
            metal.getDustTag().ifPresent(tag -> {
                if (metal.getDust().isPresent()) {
                    dusts.add(metal.getDust().get());
                    tag(tag).add(metal.getDust().get());
                }
            });
            metal.getIngotTag().ifPresent(tag ->
                    metal.getIngot().ifPresent(item -> {
                        ingots.add(metal.getIngot().get());
                        tag(tag).add(item);
                    }));
            metal.getNuggetTag().ifPresent(tag ->
                    metal.getNugget().ifPresent(item -> {
                        nuggets.add(metal.getNugget().get());
                        tag(tag).add(item);
                    }));
        }

        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        groupBuilder(ModTags.Items.CHUNKS, ItemMaterial::getChunksTag);
        groupBuilder(Tags.Items.DUSTS, ItemMaterial::getDustTag,
                ModTags.Items.DUSTS_COAL);
        groupBuilder(Tags.Items.INGOTS, ItemMaterial::getIngotTag);
        groupBuilder(Tags.Items.NUGGETS, ItemMaterial::getNuggetTag);

        TagAppender<Item> cookedMeat = tag(itemTag(forgeId("food/cooked_meat")))
                .add(Items.COOKED_BEEF)
                .add(Items.COOKED_PORKCHOP)
                .add(Items.COOKED_CHICKEN)
                .add(Items.COOKED_MUTTON)
                .add(Items.COOKED_RABBIT)
                .add(ModItems.COOKED_BACON.asItem())
                .add(ModItems.COOKED_TOMAHAWK.asItem())
                .add(ModItems.COOKED_MEATBALL.asItem())
                .add(ModItems.COOKED_CHICKEN_LEG.asItem())
                .add(ModItems.COOKED_PORK_SHANK.asItem())
                .add(ModItems.COOKED_SHOARMA.asItem());

        TagAppender<Item> rawMeat = tag(itemTag(forgeId("food/raw_meat")))
                .add(Items.BEEF)
                .add(Items.PORKCHOP)
                .add(Items.CHICKEN)
                .add(Items.MUTTON)
                .add(Items.RABBIT)
                .add(ModItems.BACON.asItem())
                .add(ModItems.TOMAHAWK.asItem())
                .add(ModItems.MEATBALL.asItem())
                .add(ModItems.CHICKEN_LEG.asItem())
                .add(ModItems.PORK_SHANK.asItem())
                .add(ModItems.SHOARMA.asItem());

        TagAppender<Item> meat = tag(itemTag(forgeId("food/meat")))
                .addTag(ModTags.Items.RAW_MEAT)
                .addTag(ModTags.Items.COOKED_MEAT);

        TagAppender<Item> armors = tag(itemTag(new ResourceLocation(modId, "tools/armors")));
        TagAppender<Item> swords = tag(itemTag(new ResourceLocation(modId, "tools/swords")));
        TagAppender<Item> axes = tag(itemTag(new ResourceLocation(modId, "tools/axes")));
        TagAppender<Item> pickaxes = tag(itemTag(new ResourceLocation(modId, "tools/pickaxes")));
        TagAppender<Item> shovels = tag(itemTag(new ResourceLocation(modId, "tools/shovels")));
        TagAppender<Item> hoes = tag(itemTag(new ResourceLocation(modId, "tools/hoes")));
        TagAppender<Item> longswords = tag(itemTag(new ResourceLocation(modId, "tools/longswords")));
        TagAppender<Item> broadswords = tag(itemTag(new ResourceLocation(modId, "tools/broadswords")));
        TagAppender<Item> katanas = tag(itemTag(new ResourceLocation(modId, "tools/katanas")));
        TagAppender<Item> cutlasses = tag(itemTag(new ResourceLocation(modId, "tools/cutlasses")));
        TagAppender<Item> battleaxes = tag(itemTag(new ResourceLocation(modId, "tools/battleaxes")));
        TagAppender<Item> lumberAxes = tag(itemTag(new ResourceLocation(modId, "tools/lumber_axes")));
        TagAppender<Item> excavators = tag(itemTag(new ResourceLocation(modId, "tools/excavators")));
        TagAppender<Item> hammers = tag(itemTag(new ResourceLocation(modId, "tools/hammers")));
        for (Toolset toolset : Toolset.values()) {
            armors.add(toolset.getHelmet().get());
            armors.add(toolset.getChestplate().get());
            armors.add(toolset.getLeggings().get());
            armors.add(toolset.getBoots().get());
            swords.add(toolset.getSword().get());
            axes.add(toolset.getAxe().get());
            pickaxes.add(toolset.getPickaxe().get());
            shovels.add(toolset.getShovel().get());
            hoes.add(toolset.getHoe().get());
        }
    }

    @SafeVarargs

    private final void groupBuilder(Tag.Named<Item> tag, Function<ItemMaterial, Optional<Tag.Named<Item>>> tagGetter, Tag.Named<Item>... extras) {
        Tag.Builder builder = getOrCreateRawBuilder(tag);
        for (ItemMaterial metal : ItemMaterial.values()) {
            tagGetter.apply(metal).ifPresent(namedTag -> builder.add(new Tag.TagEntry(namedTag.getName()), modId));
        }
        for (Tag.Named<Item> extraTag : extras) {
            builder.addTag(extraTag.getName(), modId);
        }
    }

    private void builder(ResourceLocation id, ItemLike... items) {
        for (Item item : Arrays.stream(items).map(ItemLike::asItem).toArray(Item[]::new)) {
            getOrCreateRawBuilder(itemTag(id)).add(new Tag.ElementEntry(Objects.requireNonNull(item.getRegistryName())), modId);
        }
    }

    @Override
    public @NotNull
    String getName() {
        return "RandomThingz - Item Tags";
    }



    @SuppressWarnings("ConstantConditions")
    @Override
    public void run(@NotNull HashCache cache) {
        // Temp fix that removes the broken safety check
        this.builders.clear();
        this.addTags();
        this.builders.forEach((p_240524_4_, p_240524_5_) -> {
            JsonObject jsonobject = p_240524_5_.serializeToJson();
            Path path = this.getPath(p_240524_4_);
            if (path == null)
                return; //Forge: Allow running this data provider without writing it. Recipe provider needs valid tags.

            try {
                String s = GSON.toJson(jsonobject);
                String s1 = SHA1.hashUnencodedChars(s).toString();
                if (!Objects.equals(cache.getHash(path), s1) || !Files.exists(path)) {
                    Files.createDirectories(path.getParent());

                    try (BufferedWriter bufferedwriter = Files.newBufferedWriter(path)) {
                        bufferedwriter.write(s);
                    }
                }

                cache.putNew(path, s1);
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't write tags to {}", path, ioexception);
            }

        });
    }

    private static Tag.Named<Item> itemTag(String path) {
        return ItemTags.bind(new ResourceLocation(RandomThingz.MOD_ID, path).toString());
    }
}
