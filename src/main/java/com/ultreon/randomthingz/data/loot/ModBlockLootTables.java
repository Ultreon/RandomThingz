package com.ultreon.randomthingz.data.loot;

import com.ultreon.modlib.api.holders.BlockHolder;
import com.ultreon.modlib.silentlib.registry.BlockRegistryObject;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModBlockLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        for (BlockRegistryObject<Block> blockObj : ModBlocks.BOOKSHELVES) {
            Block block = blockObj.get();
            add(block, new LootTable.Builder()
                    .withPool(new LootPool.Builder()
                            .setRolls(UniformGenerator.between(0, 1))
                            .add(new AlternativesEntry.Builder()
                                    .otherwise(
                                            LootItem.lootTableItem(block.asItem())
                                                    .when(MatchTool.toolMatches(ItemPredicate.Builder.item()
                                                            .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))))
                                    .otherwise(
                                            LootItem.lootTableItem(Items.BOOK)
                                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 3)))
                                                    .apply(ApplyExplosionDecay.explosionDecay())
                                    )
                            )
                    )
            );
        }

        Registration.BLOCKS.getAllBlocks().stream()
                .map(BlockHolder::asBlock)
                .filter(block -> block.asItem() != Items.AIR)
                .forEach(this::dropSelf);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getAllBlocks().stream().map(BlockHolder::asBlock).toList();
    }
}
