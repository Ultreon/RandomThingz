package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.objects.entities.GlowSquidEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TickListener {
    @SubscribeEvent
    public void randomTick(TickEvent.WorldTickEvent event) {
        if (new Random().nextInt(60) == 0) {
            GlowSquidEntity squidEntity = new GlowSquidEntity(EntityTypeInit.GLOW_SQUID_ENTITY, event.world);
            squidEntity.setPosition(0, 255, 0);
            event.world.addEntity(squidEntity);
        }
    }

}
