package com.ultreon.randomthingz.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackBlock;
import com.ultreon.randomthingz.commons.tags.ModTags;
import com.ultreon.randomthingz.item.common.ItemMaterial;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.tags.ITag;
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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@SuppressWarnings("deprecation")
public class ModBlockTagsProvider extends BlockTagsProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    public ModBlockTagsProvider(DataGenerator gen) {
        super(gen);
    }

    public ModBlockTagsProvider(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    protected void registerTags() {
        getOrCreateBuilder(ModTags.Blocks.DRYING_RACKS).add(Registration.getBlocks(DryingRackBlock.class).toArray(new Block[0]));

        for (ItemMaterial metal : ItemMaterial.values()) {
            metal.getOreTag().ifPresent(tag ->
                    getOrCreateBuilder(tag).add(metal.getOre().get()));
            metal.getStorageBlockTag().ifPresent(tag ->
                    getOrCreateBuilder(tag).add(metal.getStorageBlock().get()));
        }

        groupBuilder(Tags.Blocks.ORES, ItemMaterial::getOreTag);
        groupBuilder(Tags.Blocks.STORAGE_BLOCKS, ItemMaterial::getStorageBlockTag);
    }

    private void groupBuilder(ITag.INamedTag<Block> tag, Function<ItemMaterial, Optional<ITag.INamedTag<Block>>> tagGetter) {
        Builder<Block> builder = getOrCreateBuilder(tag);
        for (ItemMaterial metal : ItemMaterial.values()) {
            tagGetter.apply(metal).ifPresent(builder::addTag);
        }
    }

    @Override
    public @NotNull String getName() {
        return "RandomThingz - Block Tags";
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
                String s1 = HASH_FUNCTION.hashUnencodedChars(s).toString();
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
}
