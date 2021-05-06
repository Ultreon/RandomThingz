package com.qtech.forgemod.block.machines.wire;

import com.qtech.forgemod.QForgeMod;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID)
public final class WireNetworkManager {
    private static final Collection<LazyOptional<WireNetwork>> NETWORK_LIST = Collections.synchronizedList(new ArrayList<>());

    private WireNetworkManager() {
        throw new IllegalAccessError("Utility class");
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static WireNetwork get(IWorldReader dimension, BlockPos pos) {
        return getLazy(dimension, pos).orElse(null);
    }

    public static LazyOptional<WireNetwork> getLazy(IWorldReader dimension, BlockPos pos) {
        synchronized (NETWORK_LIST) {
            for (LazyOptional<WireNetwork> network : NETWORK_LIST) {
                if (network.isPresent()) {
                    WireNetwork net = network.orElseThrow(IllegalStateException::new);
                    if (net.contains(dimension, pos)) {
//                    QForgeUtils.LOGGER.debug("get network {}", network);
                        return network;
                    }
                }
            }

            // Create new
            WireNetwork network = WireNetwork.buildNetwork(dimension, pos);
            LazyOptional<WireNetwork> lazy = LazyOptional.of(() -> network);
            NETWORK_LIST.add(lazy);
            QForgeMod.LOGGER.debug("create network {}", network);
            return lazy;
        }
    }

    public static void invalidateNetwork(IWorldReader dimension, BlockPos pos) {
        Collection<LazyOptional<WireNetwork>> toRemove = NETWORK_LIST.stream()
                .filter(n -> n != null && n.isPresent() && n.orElseThrow(IllegalStateException::new).contains(dimension, pos))
                .collect(Collectors.toList());
        toRemove.forEach(WireNetworkManager::invalidateNetwork);
    }

    private static void invalidateNetwork(LazyOptional<WireNetwork> network) {
        QForgeMod.LOGGER.debug("invalidateNetwork {}", network);
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
