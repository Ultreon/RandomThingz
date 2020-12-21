package com.qsoftware.silent.lib.world.placement;

import com.qsoftware.silent.lib.SilentLib;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SilentLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class LibPlacements {
    public static final Placement<DimensionFilterConfig> DIMENSION_FILTER = new DimensionFilterPlacement(DimensionFilterConfig.CODEC);

    private LibPlacements() {
    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Placement<?>> event) {
        event.getRegistry().register(DIMENSION_FILTER.setRegistryName(SilentLib.getId("dimension_filter")));
    }
}
