package com.qsoftware.forgemod;

import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.modules.biomes.BiomesModule;
import com.qsoftware.forgemod.modules.entities.EntitiesModule;
import com.qsoftware.forgemod.modules.blocks.BlocksModule;
import com.qsoftware.forgemod.modules.confirmExit.ConfirmExitModule;
import com.qsoftware.forgemod.modules.debugMenu.DebugMenuModule;
import com.qsoftware.forgemod.modules.MainModule;
import com.qsoftware.forgemod.modules.items.ItemsModule;
import com.qsoftware.forgemod.modules.ores.OresModule;
import com.qsoftware.forgemod.modules.pcCrash.PCCrashModule;
import com.qsoftware.forgemod.modules.pcShutdown.PCShutdownModule;
import com.qsoftware.forgemod.modules.tileentities.TileEntitiesModule;
import com.qsoftware.forgemod.modules.updates.UpdatesModule;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Modules {
    public static final List<Module> MODULES = new ArrayList<>();
    public static final MainModule MAIN = new MainModule();
    public static final BlocksModule BLOCKS = new BlocksModule();
    public static final ItemsModule ITEMS = new ItemsModule();
    public static final EntitiesModule ENTITIES = new EntitiesModule();
    public static final TileEntitiesModule TILE_ENTITIES = new TileEntitiesModule();
    public static final BiomesModule BIOMES = new BiomesModule();
    public static final OresModule ORES = new OresModule();
    public static final ConfirmExitModule CONFIRM_EXIT = new ConfirmExitModule();
    public static final PCShutdownModule PC_SHUTDOWN = new PCShutdownModule();
    public static final PCCrashModule PC_CRASH = new PCCrashModule();
    public static final UpdatesModule UPDATES = new UpdatesModule();
    public static final DebugMenuModule DEBUG_MENU = new DebugMenuModule();

    public static void init(ModuleManager manager) {
        manager.register(MAIN);
        manager.register(BLOCKS);
        manager.register(ITEMS);
        manager.register(ENTITIES);
        manager.register(TILE_ENTITIES);
        manager.register(BIOMES);
        manager.register(ORES);
        manager.register(CONFIRM_EXIT);
        manager.register(PC_SHUTDOWN);
        manager.register(PC_CRASH);
        manager.register(UPDATES);
        manager.register(DEBUG_MENU);
    }
}
