package com.qsoftware.forgemod.objects.block.pipe;

import com.qsoftware.forgemod.QForgeMod;
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
public final class PipeNetworkManager {
    private static final Collection<LazyOptional<PipeNetwork>> NETWORK_LIST = Collections.synchronizedList(new ArrayList<>());

    private PipeNetworkManager() {
        throw new IllegalAccessError("Utility class");
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static PipeNetwork get(IWorldReader world, BlockPos pos) {
        return getLazy(world, pos).orElse(null);
    }

    public static LazyOptional<PipeNetwork> getLazy(IWorldReader world, BlockPos pos) {
        synchronized (NETWORK_LIST) {
            for (LazyOptional<PipeNetwork> network : NETWORK_LIST) {
                if (network.isPresent()) {
                    PipeNetwork net = network.orElseThrow(IllegalStateException::new);
                    if (net.contains(world, pos)) {
//                    QForgeUtils.LOGGER.debug("get network {}", network);
                        return network;
                    }
                }
            }
        }

        // Create new
        PipeNetwork network = PipeNetwork.buildNetwork(world, pos);
        LazyOptional<PipeNetwork> lazy = LazyOptional.of(() -> network);
        NETWORK_LIST.add(lazy);
        QForgeMod.LOGGER.debug("create network {}", network);
        return lazy;
    }

    public static void invalidateNetwork(IWorldReader world, BlockPos pos) {
        Collection<LazyOptional<PipeNetwork>> toRemove = NETWORK_LIST.stream()
                .filter(n -> n != null && n.isPresent() && n.orElseThrow(IllegalStateException::new).contains(world, pos))
                .collect(Collectors.toList());
        toRemove.forEach(PipeNetworkManager::invalidateNetwork);
    }

    private static void invalidateNetwork(LazyOptional<PipeNetwork> network) {
        QForgeMod.LOGGER.debug("invalidateNetwork {}", network);
        NETWORK_LIST.removeIf(n -> n.isPresent() && n.equals(network));
        network.ifPresent(PipeNetwork::invalidate);
        network.invalidate();
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        NETWORK_LIST.stream()
                .filter(n -> n != null && n.isPresent())
                .forEach(n -> n.ifPresent(PipeNetwork::moveFluids));
    }
}
