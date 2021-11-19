package com.ultreon.randomthingz.block.common;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.commons.init.ObjectInit;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Blocks initialization class.
 *
 * @author Qboi123
 * @deprecated use {@link ModBlocks}
 */
@UtilityClass
@Deprecated
@SuppressWarnings({"unused", "RedundantSuppression", "ConstantConditions"})
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocksAlt extends ObjectInit<Block> {
    ////////////////////
    //     Blocks     //
    ////////////////////
    @Deprecated
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RandomThingz.MOD_ID);
    @Deprecated
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RandomThingz.MOD_ID);

    @Deprecated
    public static void register() {

    }
}
