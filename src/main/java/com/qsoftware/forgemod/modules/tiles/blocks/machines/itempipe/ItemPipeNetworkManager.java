package com.qsoftware.forgemod.modules.tiles.blocks.machines.itempipe;

import com.qsoftware.forgemod.QForgeMod;
import lombok.experimental.UtilityClass;
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

@UtilityClass
@Mod.EventBusSubscriber(modid = QForgeMod.modId)
public final class ItemPipeNetworkManager {
    private static final Collection<LazyOptional<ItemPipeNetwork>> NETWORK_LIST = Collections.synchronizedList(new ArrayList<>());

    @SuppressWarnings("ConstantConditions")
    @Nullable
    public static ItemPipeNetwork get(IWorldReader world, BlockPos pos) {
        return getLazy(world, pos).orElse(null);
    }

    public static LazyOptional<ItemPipeNetwork> getLazy(IWorldReader world, BlockPos pos) {
        synchronized (NETWORK_LIST) {
            for (LazyOptional<ItemPipeNetwork> network : NETWORK_LIST) {
                if (network.isPresent()) {
                    ItemPipeNetwork net = network.orElseThrow(IllegalStateException::new);
                    if (net.contains(world, pos)) {
//                    QForgeUtils.LOGGER.debug("get network {}", network);
                        return network;
                    }
                }
            }
        }

        // Create new
        ItemPipeNetwork network = ItemPipeNetwork.buildNetwork(world, pos);
        LazyOptional<ItemPipeNetwork> lazy = LazyOptional.of(() -> network);
        NETWORK_LIST.add(lazy);
        QForgeMod.LOGGER.debug("create network {}", network);
        return lazy;
    }

    public static void invalidateNetwork(IWorldReader world, BlockPos pos) {
        Collection<LazyOptional<ItemPipeNetwork>> toRemove = NETWORK_LIST.stream()
                .filter(n -> n != null && n.isPresent() && n.orElseThrow(IllegalStateException::new).contains(world, pos))
                .collect(Collectors.toList());
        toRemove.forEach(ItemPipeNetworkManager::invalidateNetwork);
    }

    private static void invalidateNetwork(LazyOptional<ItemPipeNetwork> network) {
        QForgeMod.LOGGER.debug("invalidateNetwork {}", network);
        NETWORK_LIST.removeIf(n -> n.isPresent() && n.equals(network));
        network.ifPresent(ItemPipeNetwork::invalidate);
        network.invalidate();
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        NETWORK_LIST.stream()
                .filter(n -> n != null && n.isPresent())
                .forEach(n -> n.ifPresent(ItemPipeNetwork::moveItems));
    }
}
