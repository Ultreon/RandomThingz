package com.qsoftware.forgemod.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.qsoftware.forgemod.init.ModTags;
import com.qsoftware.forgemod.init.OreMaterials;
import com.qsoftware.forgemod.objects.items.CraftingItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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

    private static ITag.INamedTag<Item> itemTag(ResourceLocation id) {
        return ItemTags.makeWrapperTag(id.toString());
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
                .addTag(OreMaterials.ALUMINUM_STEEL.getIngotTag().get())
                .addTag(OreMaterials.BISMUTH_STEEL.getIngotTag().get())
                .addTag(OreMaterials.STEEL.getIngotTag().get());
        getOrCreateBuilder(ModTags.Items.COAL_GENERATOR_FUELS)
                .addTag(ItemTags.COALS)
                .addTag(itemTag(forgeId("nuggets/coal")))
                .addTag(itemTag(forgeId("storage_blocks/charcoal")))
                .addTag(Tags.Items.STORAGE_BLOCKS_COAL);
        copy(ModTags.Blocks.DRYING_RACKS, ModTags.Items.DRYING_RACKS);

        getOrCreateBuilder(ModTags.Items.DUSTS_COAL).add(CraftingItems.COAL_DUST.asItem());

        for (OreMaterials metal : OreMaterials.values()) {
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
        groupBuilder(ModTags.Items.CHUNKS, OreMaterials::getChunksTag);
        groupBuilder(Tags.Items.DUSTS, OreMaterials::getDustTag,
                ModTags.Items.DUSTS_COAL);
        groupBuilder(Tags.Items.INGOTS, OreMaterials::getIngotTag);
        groupBuilder(Tags.Items.NUGGETS, OreMaterials::getNuggetTag);
    }

    @SafeVarargs
    private final void groupBuilder(ITag.INamedTag<Item> tag, Function<OreMaterials, Optional<ITag.INamedTag<Item>>> tagGetter, ITag.INamedTag<Item>... extras) {
        Builder<Item> builder = getOrCreateBuilder(tag);
        for (OreMaterials metal : OreMaterials.values()) {
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
            Path path = this.makePath(p_240524_4_);
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
                LOGGER.error("Couldn't save tags to {}", path, ioexception);
            }

        });
    }
}
