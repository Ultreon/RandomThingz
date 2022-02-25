package com.ultreon.randomthingz.client.debug.menu;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.debug.menu.pages.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class DebugPages {
    public static final PlayerPage1 PLAYER_PAGE1 = DebugGui.get().registerPage(new PlayerPage1(RandomThingz.MOD_ID, "player_1"));
    public static final PlayerPage2 PLAYER_PAGE2 = DebugGui.get().registerPage(new PlayerPage2(RandomThingz.MOD_ID, "player_2"));
    public static final BlockPage BLOCK = DebugGui.get().registerPage(new BlockPage(RandomThingz.MOD_ID, "block"));
    public static final FluidPage FLUID = DebugGui.get().registerPage(new FluidPage(RandomThingz.MOD_ID, "fluid"));
    public static final ItemPage ITEM = DebugGui.get().registerPage(new ItemPage(RandomThingz.MOD_ID, "item"));
    public static final EntityPage ENTITY = DebugGui.get().registerPage(new EntityPage(RandomThingz.MOD_ID, "entity"));
    public static final ItemEntityPage ITEM_ENTITY = DebugGui.get().registerPage(new ItemEntityPage(RandomThingz.MOD_ID, "item_entity"));
    public static final LivingEntityPage LIVING_ENTITY = DebugGui.get().registerPage(new LivingEntityPage(RandomThingz.MOD_ID, "living_entity"));
    public static final WorldPage WORLD = DebugGui.get().registerPage(new WorldPage(RandomThingz.MOD_ID, "world"));
    public static final WorldInfoPage WORLD_INFO = DebugGui.get().registerPage(new WorldInfoPage(RandomThingz.MOD_ID, "world_info"));
    public static final MinecraftPage MINECRAFT = DebugGui.get().registerPage(new MinecraftPage(RandomThingz.MOD_ID, "minecraft"));
    public static final WindowPage WINDOW = DebugGui.get().registerPage(new WindowPage(RandomThingz.MOD_ID, "window"));
    public static final ComputerPage COMPUTER = DebugGui.get().registerPage(new ComputerPage(RandomThingz.MOD_ID, "computer"));
}
