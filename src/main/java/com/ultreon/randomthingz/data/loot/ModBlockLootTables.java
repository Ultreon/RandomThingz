package com.ultreon.randomthingz.data.loot;

import com.qsoftware.modlib.api.providers.IBlockProvider;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;

import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        for (BlockRegistryObject<Block> blockRegistryObject : ModBlocks.BOOKSHELVES) {
            Block block = blockRegistryObject.get();
            registerLootTable(block, new LootTable.Builder()
                    .addLootPool(new LootPool.Builder()
                            .rolls(new RandomValueRange(1))
                            .addEntry(new AlternativesLootEntry.Builder()
                                    .alternatively(
                                            ItemLootEntry.builder(block.asItem())
                                                    .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create()
                                                            .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))))
                                    .alternatively(
                                            ItemLootEntry.builder(Items.BOOK)
                                                    .acceptFunction(SetCount.builder(new RandomValueRange(3)))
                                                    .acceptFunction(ExplosionDecay.builder())
                                    )
                            )
                    )
            );
        }

        Registration.BLOCKS.getAllBlocks().stream()
                .map(IBlockProvider::asBlock)
                .filter(block -> block.asItem() != Items.AIR)
                .forEach(this::registerDropSelfLootTable);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getAllBlocks().stream().map(IBlockProvider::asBlock).collect(Collectors.toList());
    }
}
