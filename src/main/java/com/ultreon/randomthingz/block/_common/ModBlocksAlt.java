package com.ultreon.randomthingz.block._common;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.init.ObjectInit;
import lombok.experimental.UtilityClass;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Blocks initialization class.
 *
 * @author Qboi123
 */
@UtilityClass
@SuppressWarnings({"unused", "RedundantSuppression", "ConstantConditions"})
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocksAlt extends ObjectInit<Block> {
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Blocks     //
    ////////////////////
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RandomThingz.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RandomThingz.MOD_ID);

    public static void register() {

    }
}
