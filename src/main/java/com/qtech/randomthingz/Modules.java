package com.qtech.randomthingz;

import com.qtech.randomthingz.block.common.BlocksModule;
import com.qtech.randomthingz.commons.Module;
import com.qtech.randomthingz.commons.ModuleManager;
import com.qtech.randomthingz.entity.common.EntitiesModule;
import com.qtech.randomthingz.item.common.ItemsModule;
import com.qtech.randomthingz.modules.MainModule;
import com.qtech.randomthingz.modules.actionmenu.ActionMenuModule;
import com.qtech.randomthingz.modules.client.ClientTweaksModule;
import com.qtech.randomthingz.modules.confirmExit.ConfirmExitModule;
import com.qtech.randomthingz.modules.debugMenu.DebugMenuModule;
import com.qtech.randomthingz.modules.tiles.TileEntitiesModule;
import com.qtech.randomthingz.modules.updates.UpdatesModule;
import com.qtech.randomthingz.world.gen.WorldGenerationModule;
import com.qtech.randomthingz.world.gen.ores.OresModule;
import lombok.experimental.UtilityClass;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Module registration class.
 *
 * @see Module
 * @see ModuleManager
 */
@SuppressWarnings("unused")
@UtilityClass
public class Modules {
    public static final List<Module> MODULES = new ArrayList<>();
    public static final MainModule MAIN = new MainModule();

    @OnlyIn(Dist.CLIENT)
    public static class Client {
        public static final ClientTweaksModule CLIENT = new ClientTweaksModule();
        public static final ConfirmExitModule CONFIRM_EXIT = new ConfirmExitModule();

        public static void initialize(ModuleManager manager) {
            manager.register(CONFIRM_EXIT);
            manager.register(CLIENT);
        }
    }

    public static class Common {
        public static final BlocksModule BLOCKS = new BlocksModule();
        public static final ItemsModule ITEMS = new ItemsModule();
        public static final EntitiesModule ENTITIES = new EntitiesModule();
        public static final TileEntitiesModule TILE_ENTITIES = new TileEntitiesModule();
        public static final WorldGenerationModule BIOMES = new WorldGenerationModule();
        public static final OresModule ORES = new OresModule();
        public static final UpdatesModule UPDATES = new UpdatesModule();
        public static final DebugMenuModule DEBUG_MENU = new DebugMenuModule();
        public static final ActionMenuModule ACTION_MENU = new ActionMenuModule();

        public static void initialize(ModuleManager manager) {
            manager.register(BLOCKS);
            manager.register(ITEMS);
            manager.register(ENTITIES);
            manager.register(TILE_ENTITIES);
            manager.register(BIOMES);
            manager.register(ORES);
            manager.register(UPDATES);
            manager.register(DEBUG_MENU);
            manager.register(ACTION_MENU);
        }
    }

    public static class Server {
        public static void initialize(ModuleManager manager) {

        }
    }

    public static void initialize(ModuleManager manager) {
        manager.register(MAIN);
        DistExecutor.unsafeRunForDist(() -> () -> {
            Client.initialize(manager);
            return null;
        }, () -> () -> {
            Server.initialize(manager);
            return null;
        });
        Common.initialize(manager);
    }
}
