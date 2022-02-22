package com.ultreon.randomthingz.block.machines.wire;

import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public final class WireConnection {
    private static final Collection<LazyOptional<WireNetwork>> NETWORK_LIST = Collections.synchronizedList(new ArrayList<>());

    private WireConnection() {
        throw new IllegalAccessError("Utility class");
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static WireNetwork get(LevelReader dimension, BlockPos pos) {
        return getLazy(dimension, pos).orElse(null);
    }

    public static LazyOptional<WireNetwork> getLazy(LevelReader dimension, BlockPos pos) {
        synchronized (NETWORK_LIST) {
            for (LazyOptional<WireNetwork> network : NETWORK_LIST) {
                if (network.isPresent()) {
                    WireNetwork net = network.orElseThrow(IllegalStateException::new);
                    if (net.contains(dimension, pos)) {
//                    RandomThingz.LOGGER.debug("get network {}", network);
                        return network;
                    }
                }
            }

            // Create new
            WireNetwork network = WireNetwork.buildNetwork(dimension, pos);
            LazyOptional<WireNetwork> lazy = LazyOptional.of(() -> network);
            NETWORK_LIST.add(lazy);
            RandomThingz.LOGGER.debug("create network {}", network);
            return lazy;
        }
    }

    public static void invalidateNetwork(LevelReader dimension, BlockPos pos) {
        Collection<LazyOptional<WireNetwork>> toRemove = NETWORK_LIST.stream()
                .filter(n -> n != null && n.isPresent() && n.orElseThrow(IllegalStateException::new).contains(dimension, pos))
                .toList();
        toRemove.forEach(WireConnection::invalidateNetwork);
    }

    private static void invalidateNetwork(LazyOptional<WireNetwork> network) {
        RandomThingz.LOGGER.debug("invalidateNetwork {}", network);
        synchronized (NETWORK_LIST) {
            NETWORK_LIST.removeIf(n -> n.isPresent() && n.equals(network));
            network.ifPresent(WireNetwork::invalidate);
            network.invalidate();
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        // Send energy from wire networks to connected blocks
        synchronized (NETWORK_LIST) {
            NETWORK_LIST.stream()
                    .filter(n -> n != null && n.isPresent())
                    .forEach(n -> n.ifPresent(WireNetwork::sendEnergy));
        }
    }
}
