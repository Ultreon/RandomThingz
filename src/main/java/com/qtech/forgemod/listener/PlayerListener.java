package com.qtech.forgemod.listener;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.modules.tiles.ModBlocks;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Blocks;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Calendar;

@Mod.EventBusSubscriber(modid = QForgeMod.modId, bus = Mod.EventBusSubscriber.Bus.FORGE)
@UtilityClass
public class PlayerListener {
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
//        Calendar calendar = Calendar.getInstance();
//        if (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26) { // Is christmas.
//            IWorld world = event.getWorld();
//            if (event.getState().getBlock() == Blocks.CHEST) {
//                event.setCanceled(true);
//                world.setBlockState(event.getPos(), M, 11);
//            }
//        }
    }
}
