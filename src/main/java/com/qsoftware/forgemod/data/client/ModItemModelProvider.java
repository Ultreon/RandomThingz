package com.qsoftware.forgemod.data.client;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import com.qsoftware.forgemod.modules.items.ModItems;
import com.qsoftware.forgemod.modules.items.OreMaterial;
import com.qsoftware.forgemod.init.Registration;
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
        super(generator, QForgeMod.MOD_ID, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return "QForgeMod - Item Models";
    }

    @Override
    protected void registerModels() {
        Registration.BLOCKS.getEntries().forEach(block -> blockBuilder(block.get()));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        //noinspection OverlyLongLambda
        Arrays.stream(OreMaterial.values()).forEach(metal -> {
            metal.getOre().ifPresent(this::blockBuilder);
            metal.getStorageBlock().ifPresent(this::blockBuilder);
            metal.getChunks().ifPresent(item -> builder(item, itemGenerated));
            metal.getDust().ifPresent(item -> builder(item, itemGenerated));
            metal.getIngot().ifPresent(item -> builder(item, itemGenerated));
            metal.getNugget().ifPresent(item -> builder(item, itemGenerated));
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
        String name = NameUtils.from(block).getPath();
        if (block == ModBlocks.BATTERY_BOX.get()) {
            withExistingParent(name, modLoc("block/" + name + "_6"));
        } else {
            withExistingParent(name, modLoc("block/" + name));
        }
    }

    private void builder(IItemProvider item, ModelFile parent) {
        String name = NameUtils.fromItem(item).getPath();
        builder(item, parent, "item/" + name);
    }

    private void builder(IItemProvider item, ModelFile parent, String texture) {
        getBuilder(NameUtils.fromItem(item).getPath())
                .parent(parent)
                .texture("layer0", modLoc(texture));
    }
}
