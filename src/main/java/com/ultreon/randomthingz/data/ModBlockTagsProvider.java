package com.ultreon.randomthingz.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ultreon.randomthingz.block.machines.AbstractMachineBlock;
import com.ultreon.randomthingz.block.machines.MetalBlock;
import com.ultreon.randomthingz.block.machines.dryingrack.DryingRackBlock;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.common.tags.ModTags;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
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

@SuppressWarnings({"deprecation", "UnstableApiUsage"})
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
    protected void addTags() {
        tag(ModTags.Blocks.DRYING_RACKS).add(Registration.getBlocks(DryingRackBlock.class).toArray(new Block[0]));

        for (ItemMaterial metal : ItemMaterial.values()) {
            metal.getOreTag().ifPresent(tag ->
                    tag(tag).add(metal.getOre().get()));
            metal.getStorageBlockTag().ifPresent(tag ->
                    tag(tag).add(metal.getStorageBlock().get()));
            metal.getDataGenTags().forEach(tag -> {
                metal.getStorageBlock().ifPresent(block -> {
                    tag(tag).add(block);
                });
                metal.getOre().ifPresent(block -> {
                    tag(tag).add(block);
                });
            });
        }

        TagAppender<Block> mineableWithPickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        TagAppender<Block> needsStoneTool = tag(BlockTags.NEEDS_IRON_TOOL);
        for (Block block : Registration.getBlocks()) {
            if (block instanceof MetalBlock ||
                block instanceof AbstractMachineBlock) {
                mineableWithPickaxe.add(block);
                needsStoneTool.add(block);
            }
        }

        groupBuilder(Tags.Blocks.ORES, ItemMaterial::getOreTag);
        groupBuilder(Tags.Blocks.STORAGE_BLOCKS, ItemMaterial::getStorageBlockTag);
    }

    private void groupBuilder(Tag.Named<Block> tag, Function<ItemMaterial, Optional<Tag.Named<Block>>> tagGetter) {
        TagAppender<Block> builder = tag(tag);
        for (ItemMaterial metal : ItemMaterial.values()) {
            tagGetter.apply(metal).ifPresent(builder::addTag);
        }
    }

    @Override
    public @NotNull
    String getName() {
        return "RandomThingz - Block Tags";
    }

    @SuppressWarnings({"ConstantConditions", "DuplicatedCode"})
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
}
