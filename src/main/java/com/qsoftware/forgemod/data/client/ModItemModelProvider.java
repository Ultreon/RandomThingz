package com.qsoftware.forgemod.data.client;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.interfaces.IMachine;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import com.qsoftware.forgemod.modules.items.ModItems;
import com.qsoftware.forgemod.modules.items.OreMaterial;
import com.qsoftware.forgemod.modules.items.Tools;
import com.qsoftware.forgemod.modules.items.objects.CraftingItems;
import com.qsoftware.forgemod.modules.items.objects.upgrades.MachineUpgrades;
import com.qsoftware.modlib.silentlib.block.IBlockProvider;
import com.qsoftware.modlib.silentlib.util.NameUtils;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, QForgeMod.modId, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return "QForgeMod - Item Models";
    }

    @Override
    protected void registerModels() {
        Registration.BLOCKS.getEntries().stream().filter((block) -> block.get().getClass().getPackage().getName().startsWith("com.qsoftware.forgemod.modules.blocks.blocks.machines")).forEach(block -> blockBuilder(block.get()));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));
        ModelFile itemHandheldDouble = getExistingFile(modLoc("item/handheld_double_size"));

        //noinspection OverlyLongLambda
        Arrays.stream(OreMaterial.values()).forEach(metal -> {
            metal.getOre().ifPresent(this::blockBuilder);
            metal.getStorageBlock().ifPresent(this::blockBuilder);
            metal.getChunks().ifPresent(item -> builder(item, itemGenerated));
            metal.getDust().ifPresent(item -> builder(item, itemGenerated));
            metal.getIngot().ifPresent(item -> builder(item, itemGenerated));
            metal.getNugget().ifPresent(item -> builder(item, itemGenerated));
        });
        Arrays.stream(Tools.values()).forEach(metal -> {
            metal.getHelmet().ifPresent(item -> builder(item, itemHandheld));
            metal.getChestplate().ifPresent(item -> builder(item, itemHandheld));
            metal.getLeggings().ifPresent(item -> builder(item, itemHandheld));
            metal.getBoots().ifPresent(item -> builder(item, itemHandheld));
            metal.getSword().ifPresent(item -> builder(item, itemHandheld));
            metal.getAxe().ifPresent(item -> builder(item, itemHandheld));
            metal.getPickaxe().ifPresent(item -> builder(item, itemHandheld));
            metal.getShovel().ifPresent(item -> builder(item, itemHandheld));
            metal.getHoe().ifPresent(item -> builder(item, itemHandheld));
            metal.getLongsword().ifPresent(item -> builder(item, itemHandheldDouble));
            metal.getKatana().ifPresent(item -> builder(item, itemHandheld));
            metal.getBroadsword().ifPresent(item -> builder(item, itemHandheld));
            metal.getLumberAxe().ifPresent(item -> builder(item, itemHandheld));
            metal.getBattleaxe().ifPresent(item -> builder(item, itemHandheld));
            metal.getHammer().ifPresent(item -> builder(item, itemHandheld));
            metal.getExcavator().ifPresent(item -> builder(item, itemHandheld));
        });
        Arrays.stream(CraftingItems.values()).forEach(item -> builder(item, itemGenerated));
        Arrays.stream(MachineUpgrades.values()).forEach(item -> builder(item, itemGenerated));

        builder(ModItems.WRENCH, itemGenerated);
        builder(ModItems.DEBUG_ITEM, itemGenerated);
        builder(ModItems.BATTERY, itemGenerated);
        builder(ModItems.HAND_PUMP, itemGenerated);
    }

    private void blockBuilder(IBlockProvider block) {
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

    private void builder(IItemProvider item, ModelFile parent) {
        String name = NameUtils.fromItem(item).getPath();
        builder(item, parent, "items/" + name);
    }

    private void builder(IItemProvider item, ModelFile parent, String texture) {
        try {
            getBuilder(NameUtils.fromItem(item).getPath())
                    .parent(parent)
                    .texture("layer0", modLoc(texture));
        } catch (IllegalArgumentException e) {
            getBuilder(NameUtils.fromItem(item).getPath())
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", modLoc("default"));
        }
    }
}
