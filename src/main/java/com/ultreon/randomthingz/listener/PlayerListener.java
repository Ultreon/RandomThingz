package com.ultreon.randomthingz.listener;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@UtilityClass
public class PlayerListener {
    @Beta
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
