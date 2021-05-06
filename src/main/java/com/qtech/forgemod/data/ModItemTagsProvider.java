package com.qtech.forgemod.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.init.ModTags;
import com.qtech.forgemod.item.common.ItemMaterial;
import com.qtech.forgemod.item.tools.Tools;
import com.qtech.forgemod.item.CraftingItems;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
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

    private static ITag.INamedTag<Item> itemTag(ResourceLocation id) {
        return ItemTags.createWrapperTag(id.toString());
    }

    private static ResourceLocation forgeId(String path) {
        return new ResourceLocation("forge", path);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    protected void registerTags() {
        // Empties
        builder(forgeId("nuggets/coal"));
        builder(forgeId("storage_blocks/charcoal"));

        getOrCreateBuilder(ModTags.Items.PLASTIC).add(CraftingItems.PLASTIC_SHEET.asItem());

        getOrCreateBuilder(ModTags.Items.STEELS)
                .addTag(ItemMaterial.ALUMINUM_STEEL.getIngotTag().get())
                .addTag(ItemMaterial.BISMUTH_STEEL.getIngotTag().get())
                .addTag(ItemMaterial.STEEL.getIngotTag().get());
        getOrCreateBuilder(ModTags.Items.COAL_GENERATOR_FUELS)
                .addTag(ItemTags.COALS)
                .addTag(itemTag(forgeId("nuggets/coal")))
                .addTag(itemTag(forgeId("storage_blocks/charcoal")))
                .addTag(Tags.Items.STORAGE_BLOCKS_COAL);
        copy(ModTags.Blocks.DRYING_RACKS, ModTags.Items.DRYING_RACKS);

        getOrCreateBuilder(ModTags.Items.DUSTS_COAL).add(CraftingItems.COAL_DUST.asItem());

        for (ItemMaterial metal : ItemMaterial.values()) {
            metal.getOreTag().ifPresent(tag ->
                    copy(tag, metal.getOreItemTag().get()));
            metal.getStorageBlockTag().ifPresent(tag ->
                    copy(tag, metal.getStorageBlockItemTag().get()));
            metal.getChunksTag().ifPresent(tag ->
                    getOrCreateBuilder(tag).add(metal.getChunks().get()));
            metal.getDustTag().ifPresent(tag ->
                    getOrCreateBuilder(tag).add(metal.getDust().get()));
            metal.getIngotTag().ifPresent(tag ->
                    metal.getIngot().ifPresent(item ->
                            getOrCreateBuilder(tag).add(item)));
            metal.getNuggetTag().ifPresent(tag ->
                    metal.getNugget().ifPresent(item ->
                            getOrCreateBuilder(tag).add(item)));
        }

        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        groupBuilder(ModTags.Items.CHUNKS, ItemMaterial::getChunksTag);
        groupBuilder(Tags.Items.DUSTS, ItemMaterial::getDustTag,
                ModTags.Items.DUSTS_COAL);
        groupBuilder(Tags.Items.INGOTS, ItemMaterial::getIngotTag);
        groupBuilder(Tags.Items.NUGGETS, ItemMaterial::getNuggetTag);

        Builder<Item> swords = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/swords")));
        Builder<Item> axes = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/axes")));
        Builder<Item> pickaxes = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/pickaxes")));
        Builder<Item> shovels = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/shovels")));
        Builder<Item> hoes = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/hoes")));
        Builder<Item> longswords = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/longswords")));
        Builder<Item> broadswords = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/broadswords")));
        Builder<Item> katanas = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/katanas")));
        Builder<Item> battleaxes = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/battleaxes")));
        Builder<Item> lumberAxes = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/lumber_axes")));
        Builder<Item> excavators = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/excavators")));
        Builder<Item> hammers = getOrCreateBuilder(itemTag(new ResourceLocation(modId, "tools/hammers")));
        for (Tools tools : Tools.values()) {
            swords.add(tools.getSword().get());
            axes.add(tools.getAxe().get());
            pickaxes.add(tools.getPickaxe().get());
            shovels.add(tools.getShovel().get());
            hoes.add(tools.getHoe().get());
            longswords.add(tools.getLongsword().get());
            broadswords.add(tools.getBroadsword().get());
            katanas.add(tools.getKatana().get());
            battleaxes.add(tools.getBattleaxe().get());
            lumberAxes.add(tools.getLumberAxe().get());
            excavators.add(tools.getExcavator().get());
            hammers.add(tools.getHammer().get());
        }
    }

    @SafeVarargs

    private final void groupBuilder(ITag.INamedTag<Item> tag, Function<ItemMaterial, Optional<ITag.INamedTag<Item>>> tagGetter, ITag.INamedTag<Item>... extras) {
        Builder<Item> builder = getOrCreateBuilder(tag);
        for (ItemMaterial metal : ItemMaterial.values()) {
            tagGetter.apply(metal).ifPresent(builder::addTag);
        }
        for (ITag.INamedTag<Item> extraTag : extras) {
            builder.addTag(extraTag);
        }
    }

    private void builder(ResourceLocation id, IItemProvider... items) {
        getOrCreateBuilder(itemTag(id)).add(Arrays.stream(items).map(IItemProvider::asItem).toArray(Item[]::new));
    }

    @Override
    public @NotNull String getName() {
        return "QForgeMod - Item Tags";
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void act(@NotNull DirectoryCache cache) {
        // Temp fix that removes the broken safety check
        this.tagToBuilder.clear();
        this.registerTags();
        this.tagToBuilder.forEach((p_240524_4_, p_240524_5_) -> {
            JsonObject jsonobject = p_240524_5_.serialize();
            Path path = this.createPath(p_240524_4_);
            if (path == null)
                return; //Forge: Allow running this data provider without writing it. Recipe provider needs valid tags.

            try {
                String s = GSON.toJson(jsonobject);
                @SuppressWarnings("UnstableApiUsage") String s1 = HASH_FUNCTION.hashUnencodedChars(s).toString();
                if (!Objects.equals(cache.getPreviousHash(path), s1) || !Files.exists(path)) {
                    Files.createDirectories(path.getParent());

                    try (BufferedWriter bufferedwriter = Files.newBufferedWriter(path)) {
                        bufferedwriter.write(s);
                    }
                }

                cache.recordHash(path, s1);
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't write tags to {}", path, ioexception);
            }

        });
    }

    private static ITag.INamedTag<Item> itemTag(String path) {
        return ItemTags.createWrapperTag(new ResourceLocation(QForgeMod.MOD_ID, path).toString());
    }
}
