package com.qtech.forgemod;

import com.qtech.forgemod.common.Module;
import com.qtech.forgemod.common.ModuleManager;
import com.qtech.forgemod.modules.MainModule;
import com.qtech.forgemod.modules.actionmenu.ActionMenuModule;
import com.qtech.forgemod.modules.client.ClientTweaksModule;
import com.qtech.forgemod.modules.confirmExit.ConfirmExitModule;
import com.qtech.forgemod.modules.debug.DebuggingModule;
import com.qtech.forgemod.modules.debugMenu.DebugMenuModule;
import com.qtech.forgemod.modules.environment.EntitiesModule;
import com.qtech.forgemod.modules.environment.WorldGenerationModule;
import com.qtech.forgemod.modules.environment.ores.OresModule;
import com.qtech.forgemod.modules.items.ItemsModule;
import com.qtech.forgemod.modules.tiles.BlocksModule;
import com.qtech.forgemod.modules.tiles.TileEntitiesModule;
import com.qtech.forgemod.modules.updates.UpdatesModule;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@UtilityClass
public class Modules {
    public static final List<Module> MODULES = new ArrayList<>();
    public static final MainModule MAIN = new MainModule();
    public static final ClientTweaksModule CLIENT = new ClientTweaksModule();
    public static final BlocksModule BLOCKS = new BlocksModule();
    public static final ItemsModule ITEMS = new ItemsModule();
    public static final EntitiesModule ENTITIES = new EntitiesModule();
    public static final TileEntitiesModule TILE_ENTITIES = new TileEntitiesModule();
    public static final WorldGenerationModule BIOMES = new WorldGenerationModule();
    public static final OresModule ORES = new OresModule();
    public static final ConfirmExitModule CONFIRM_EXIT = new ConfirmExitModule();
//    public static final PCShutdownModule PC_SHUTDOWN = new PCShutdownModule();
//    public static final PCCrashModule PC_CRASH = new PCCrashModule();
    public static final UpdatesModule UPDATES = new UpdatesModule();
    public static final DebugMenuModule DEBUG_MENU = new DebugMenuModule();
    public static final ActionMenuModule ACTION_MENU = new ActionMenuModule();
    public static final DebuggingModule DEBUGGING = new DebuggingModule();

    public static void initialize(ModuleManager manager) {
        manager.register(MAIN);
        manager.register(BLOCKS);
        manager.register(ITEMS);
        manager.register(ENTITIES);
        manager.register(TILE_ENTITIES);
        manager.register(BIOMES);
        manager.register(ORES);
        manager.register(CONFIRM_EXIT);
//        manager.register(PC_SHUTDOWN);
//        manager.register(PC_CRASH);
        manager.register(UPDATES);
        if (QForgeMod.isClientSide()) {
            manager.register(CLIENT);
        }
        if (QForgeMod.isModDev(false)) {
            manager.register(DEBUGGING);
        }
        manager.register(DEBUG_MENU);
        manager.register(ACTION_MENU);
    }
}
