package com.qtech.randomthingz.listener;

import com.qtech.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Server event listener.
 * Listens to server events only.
 *
 * @author Qboi123
 */
@SuppressWarnings("CommentedOutCode")
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
@UtilityClass
public class ServerListener {
    /**
     * Chunk scanner for ores.
     * Scans for infinity ore when ore profiler is active.
     *
     * @param event chunk load event.
     */
//    @SubscribeEvent
    public static void onChunkLoadEvent(ChunkEvent.Load event) {
//        if (OreProfiler.isActive()) {
//            IChunk chunk = event.getChunk();
//            ChunkPos chunkPos = chunk.getPos();
//            for (int x = 0; x < 16; x++) {
//                for (int y = 0; y < 255; y++) {
//                    for (int z = 0; z < 255; z++) {
//                        BlockState blockState = chunk.getBlockState(new BlockPos(x, y, z));
//                        Block block = blockState.getBlock();
//                        BlockPos pos = new BlockPos(chunkPos.x * 16 + x, y, chunkPos.z * 16 + z);
//                        if (block == Ores.INFINITY.getOre()) {
//                            event.getWorld().getPlayers().forEach((p) -> p.sendMessage(new StringTextComponent("= Found Infinity Ore at: " + pos.getCoordinatesAsString()), p.getUniqueID()));
//                        }
//                        if (block == Ores.ULTRINIUM.getOre()) {
//                            event.getDimension().getPlayers().forEach((p) -> p.sendMessage(new StringTextComponent("= Found Ultrinium Ore at: " + pos.getCoordinatesAsString()), p.getUniqueID()));
//                        }
//                    }
//                }
//            }
//            event.getDimension().getPlayers().forEach((p) -> p.sendMessage(new StringTextComponent("+ Scanned chunk " + chunkPos.toString()), p.getUniqueID()));
//        }
    }
}