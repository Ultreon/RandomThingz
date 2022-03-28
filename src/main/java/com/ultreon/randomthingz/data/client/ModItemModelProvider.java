package com.ultreon.randomthingz.data.client;

import com.ultreon.modlib.silentlib.block.BlockLike;
import com.ultreon.modlib.silentlib.util.NameUtils;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.StoneType;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.init.ModBlocks;
import com.ultreon.randomthingz.init.ModItems;
import com.ultreon.randomthingz.init.ModMachineUpgrades;
import com.ultreon.randomthingz.item.CraftingItems;
import com.ultreon.randomthingz.item.tool.Toolset;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, RandomThingz.MOD_ID, existingFileHelper);
    }

    @Override
    public @NotNull
    String getName() {
        return "RandomThingz - Item Models";
    }

    @Override
    protected void registerModels() {
        Registration.BLOCKS.getAllBlocks().stream().filter((block) -> block.asBlock().getClass().getPackage().getName().startsWith("com.ultreon.randomthingz.block.machines")).forEach(block -> blockBuilder(block.asBlock()));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));

        //noinspection OverlyLongLambda
        ItemMaterial.getValues().forEach(metal -> {
            metal.getStoneOre().ifPresent(this::blockBuilder);
            metal.getDeepslateOre().ifPresent(this::blockBuilder);
            metal.getNetherOre().ifPresent(this::blockBuilder);
            metal.getStorageBlock().ifPresent(this::blockBuilder);
            metal.getChunks().ifPresent(item -> builder(item, itemGenerated));
            metal.getDust().ifPresent(item -> builder(item, itemGenerated));
            metal.getIngot().ifPresent(item -> builder(item, itemGenerated));
            metal.getNugget().ifPresent(item -> builder(item, itemGenerated));
        });
        Arrays.stream(Toolset.values()).forEach(metal -> {
            metal.getHelmet().ifPresent(item -> builder(item, itemHandheld));
            metal.getChestplate().ifPresent(item -> builder(item, itemHandheld));
            metal.getLeggings().ifPresent(item -> builder(item, itemHandheld));
            metal.getBoots().ifPresent(item -> builder(item, itemHandheld));
            metal.getSword().ifPresent(item -> builder(item, itemHandheld));
            metal.getAxe().ifPresent(item -> builder(item, itemHandheld));
            metal.getPickaxe().ifPresent(item -> builder(item, itemHandheld));
            metal.getShovel().ifPresent(item -> builder(item, itemHandheld));
            metal.getHoe().ifPresent(item -> builder(item, itemHandheld));
        });
        ModBlocks.BOOKSHELVES.forEach(block -> block.ifPresent(this::blockBuilder));
        for (StoneType stoneType : StoneType.values()) {
            stoneType.getRawBlock().ifPresent(this::blockBuilder);
            stoneType.getSlabBlock().ifPresent(this::blockBuilder);
            stoneType.getStairsBlock().ifPresent(this::blockBuilder);
            stoneType.getPolishedBlock().ifPresent(this::blockBuilder);
            stoneType.getPolishedSlabBlock().ifPresent(this::blockBuilder);
            stoneType.getPolishedStairsBlock().ifPresent(this::blockBuilder);
        }

        Arrays.stream(CraftingItems.values()).forEach(item -> builder(item, itemGenerated));
        ModMachineUpgrades.registry().getValues().forEach(item -> builder(item, itemGenerated));

        blockBuilder(ModBlocks.HEAVY_QUARRY);
        blockBuilder(ModBlocks.SUPER_QUARRY);
        blockBuilder(ModBlocks.EXTREME_QUARRY);
        blockBuilder(ModBlocks.ULTRA_QUARRY);

        builder(ModItems.WRENCH, itemGenerated);
        builder(ModItems.DEBUG_ITEM, itemGenerated);
        builder(ModItems.BATTERY, itemGenerated);
        builder(ModItems.HAND_PUMP, itemGenerated);
    }

    private void blockBuilder(BlockLike block) {
        blockBuilder(block.asBlock());
    }

    private void blockBuilder(Block block) {
        try {
            String name = NameUtils.from(block).getPath();
            if (block == ModBlocks.BATTERY_BOX.get()) {
                withExistingParent(name, modLoc("block/" + name + "_6"));
            } else {
                withExistingParent(name, modLoc("block/" + name));
            }
        } catch (IllegalStateException ignored) {

        }
    }

    private void builder(ItemLike item, ModelFile parent) {
        String name = NameUtils.fromItem(item).getPath();
        builder(item, parent, "items/" + name);
    }

    private void builder(ItemLike item, ModelFile parent, String texture) {
        try {
            getBuilder(NameUtils.fromItem(item).getPath())
                    .parent(parent)
                    .texture("layer0", modLoc(texture));
        } catch (IllegalArgumentException e) {
            getBuilder(NameUtils.fromItem(item).getPath())
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", modLoc("wip"));
        }
    }
}
