package com.qsoftware.forgemod.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.qsoftware.forgemod.QForgeUtils;

@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoundInit {

    public static final SoundEvent TELEPORT = createSound("teleport");

    @SuppressWarnings("SameParameterValue")
    private static SoundEvent createSound(String id){
        ResourceLocation loc = new ResourceLocation(QForgeUtils.MOD_ID, id);
        return new SoundEvent(loc).setRegistryName(loc);
    }

    @SubscribeEvent
    public static void registerSound(RegistryEvent.Register<SoundEvent> e) {
        e.getRegistry().register(TELEPORT);
    }
}
