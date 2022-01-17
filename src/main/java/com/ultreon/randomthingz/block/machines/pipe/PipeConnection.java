package com.ultreon.randomthingz.block.machines.pipe;

import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public final class PipeConnection {
    private static final Collection<LazyOptional<PipeNetwork>> NETWORK_LIST = Collections.synchronizedList(new ArrayList<>());

    private PipeConnection() {
        throw new IllegalAccessError("Utility class");
    }

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static PipeNetwork get(LevelReader dimension, BlockPos pos) {
        return getLazy(dimension, pos).orElse(null);
    }

    public static LazyOptional<PipeNetwork> getLazy(LevelReader dimension, BlockPos pos) {
        synchronized (NETWORK_LIST) {
            for (LazyOptional<PipeNetwork> network : NETWORK_LIST) {
                if (network.isPresent()) {
                    PipeNetwork net = network.orElseThrow(IllegalStateException::new);
                    if (net.contains(dimension, pos)) {
//                    RandomThingz.LOGGER.debug("get network {}", network);
                        return network;
                    }
                }
            }
        }

        // Create new
        PipeNetwork network = PipeNetwork.buildNetwork(dimension, pos);
        LazyOptional<PipeNetwork> lazy = LazyOptional.of(() -> network);
        NETWORK_LIST.add(lazy);
        RandomThingz.LOGGER.debug("create network {}", network);
        return lazy;
    }

    public static void invalidateNetwork(LevelReader dimension, BlockPos pos) {
        Collection<LazyOptional<PipeNetwork>> toRemove = NETWORK_LIST.stream()
                .filter(n -> n != null && n.isPresent() && n.orElseThrow(IllegalStateException::new).contains(dimension, pos)).toList();
        toRemove.forEach(PipeConnection::invalidateNetwork);
    }

    private static void invalidateNetwork(LazyOptional<PipeNetwork> network) {
        RandomThingz.LOGGER.debug("invalidateNetwork {}", network);
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
