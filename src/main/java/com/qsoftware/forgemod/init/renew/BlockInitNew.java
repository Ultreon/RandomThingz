package com.qsoftware.forgemod.init.renew;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.init.BlockInit;
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

import java.lang.reflect.Field;

@SuppressWarnings({"unused", "RedundantSuppression", "ConstantConditions"})
@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class BlockInitNew {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Blocks     //
    ////////////////////
//    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, QForgeUtils.MOD_ID);
    public static final Block TOPAZ_ORE = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE));
    public static final Block TOPAZ_BLOCK = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE));
    public static final Block AMBER_ORE = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE));
    public static final Block AMBER_BLOCK = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE));
    public static final Block BERYL_ORE = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE));
    public static final Block BERYL_BLOCK = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.263f, 3.7460f).sound(SoundType.STONE));

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        Class<BlockInit> clazz = BlockInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (Block.class.isAssignableFrom(field.getType())) {
                try {
                    Block block = (Block) field.get(null);
                    event.getRegistry().register(block);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field, or registering block: " + field.getName(), t);
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        Class<BlockInit> clazz = BlockInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (Item.class.isAssignableFrom(field.getType())) {
                try {
                    Item item = (Item) field.get(null);
                    event.getRegistry().register(item);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field, or registering item: " + field.getName(), t);
                }
            }
        }
        event.getRegistry().register(new BlockItem(TOPAZ_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("topaz_ore"));
        event.getRegistry().register(new BlockItem(TOPAZ_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("topaz_block"));
        event.getRegistry().register(new BlockItem(AMBER_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("amber_ore"));
        event.getRegistry().register(new BlockItem(AMBER_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("amber_block"));
        event.getRegistry().register(new BlockItem(BERYL_ORE, new Item.Properties().group(Groups.ORES)).setRegistryName("beryl_ore"));
        event.getRegistry().register(new BlockItem(BERYL_BLOCK, new Item.Properties().group(Groups.ORES)).setRegistryName("beryl_block"));
    }
}
