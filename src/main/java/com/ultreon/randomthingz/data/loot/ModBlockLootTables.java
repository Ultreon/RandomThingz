package com.ultreon.randomthingz.data.loot;

import com.qsoftware.modlib.api.providers.IBlockProvider;
import com.qsoftware.modlib.silentlib.registry.BlockRegistryObject;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.loot.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.RandomValueBounds;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        for (BlockRegistryObject<Block> blockRegistryObject : ModBlocks.BOOKSHELVES) {
            Block block = blockRegistryObject.get();
            add(block, new LootTable.Builder()
                    .withPool(new LootPool.Builder()
                            .setRolls(new RandomValueBounds(1))
                            .add(new AlternativesEntry.Builder()
                                    .otherwise(
                                            LootItem.lootTableItem(block.asItem())
                                                    .when(MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                            .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))))
                                    .otherwise(
                                            LootItem.lootTableItem(Items.BOOK)
                                                    .apply(SetItemCountFunction.setCount(new RandomValueBounds(3)))
                                                    .apply(ApplyExplosionDecay.explosionDecay())
                                    )
                            )
                    )
            );
        }

        Registration.BLOCKS.getAllBlocks().stream()
                .map(IBlockProvider::asBlock)
                .filter(block -> block.asItem() != Items.AIR)
                .forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getAllBlocks().stream().map(IBlockProvider::asBlock).collect(Collectors.toList());
    }
}
