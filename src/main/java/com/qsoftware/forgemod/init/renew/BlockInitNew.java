package com.qsoftware.forgemod.init.renew;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.groups.Groups;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings({"unused", "RedundantSuppression", "ConstantConditions"})
@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlockInitNew {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Blocks     //
    ////////////////////
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, QForgeUtils.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, QForgeUtils.MOD_ID);
    public static final RegistryObject<Block> TOPAZ_ORE = BLOCKS.register("topaz_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AMBER_ORE = BLOCKS.register("amber_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> AMBER_BLOCK = BLOCKS.register("amber_block", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BERYL_ORE = BLOCKS.register("beryl_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BERYL_BLOCK = BLOCKS.register("beryl_block", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE)));

    public static final RegistryObject<Item> TOPAZ_ORE_ITEM = ITEMS.register("topaz_ore", () -> new BlockItem(TOPAZ_ORE.get(), new Item.Properties().group(Groups.ORES)).setRegistryName("topaz_ore"));
    public static final RegistryObject<Item> TOPAZ_BLOCK_ITEM = ITEMS.register("topaz_block", () -> new BlockItem(TOPAZ_BLOCK.get(), new Item.Properties().group(Groups.ORES)).setRegistryName("topaz_block"));
    public static final RegistryObject<Item> AMBER_ORE_ITEM = ITEMS.register("amber_ore", () -> new BlockItem(AMBER_ORE.get(), new Item.Properties().group(Groups.ORES)).setRegistryName("amber_ore"));
    public static final RegistryObject<Item> AMBER_BLOCK_ITEM = ITEMS.register("amber_block", () -> new BlockItem(AMBER_BLOCK.get(), new Item.Properties().group(Groups.ORES)).setRegistryName("amber_block"));
    public static final RegistryObject<Item> BERYL_ORE_ITEM = ITEMS.register("beryl_ore", () -> new BlockItem(BERYL_ORE.get(), new Item.Properties().group(Groups.ORES)).setRegistryName("beryl_ore"));
    public static final RegistryObject<Item> BERYL_BLOCK_ITEM = ITEMS.register("beryl_block", () -> new BlockItem(BERYL_BLOCK.get(), new Item.Properties().group(Groups.ORES)).setRegistryName("beryl_block"));
}
