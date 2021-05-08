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
    public static final ClientTweaksModule CLIENT = new ClientTweaksModule();
    public static final BlocksModule BLOCKS = new BlocksModule();
    public static final ItemsModule ITEMS = new ItemsModule();
    public static final EntitiesModule ENTITIES = new EntitiesModule();
    public static final TileEntitiesModule TILE_ENTITIES = new TileEntitiesModule();
    public static final WorldGenerationModule BIOMES = new WorldGenerationModule();
    public static final OresModule ORES = new OresModule();
    public static final ConfirmExitModule CONFIRM_EXIT = new ConfirmExitModule();
    public static final UpdatesModule UPDATES = new UpdatesModule();
    public static final DebugMenuModule DEBUG_MENU = new DebugMenuModule();
    public static final ActionMenuModule ACTION_MENU = new ActionMenuModule();

    public static void initialize(ModuleManager manager) {
        manager.register(MAIN);
        manager.register(BLOCKS);
        manager.register(ITEMS);
        manager.register(ENTITIES);
        manager.register(TILE_ENTITIES);
        manager.register(BIOMES);
        manager.register(ORES);
        manager.register(CONFIRM_EXIT);
        manager.register(UPDATES);
        if (RandomThingz.isClientSide()) {
            manager.register(CLIENT);
        }
        manager.register(DEBUG_MENU);
        manager.register(ACTION_MENU);
    }
}
