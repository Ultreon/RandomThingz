package com.qsoftware.forgemod.modules.environment;

import com.qsoftware.forgemod.Main;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.CoreRegisterModule;
import com.qsoftware.forgemod.common.ModuleSecurity;
import com.qsoftware.forgemod.modules.actionmenu.AbstractActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.MainActionMenu;
import com.qsoftware.forgemod.modules.actionmenu.IMenuHandler;
import com.qsoftware.forgemod.modules.actionmenu.MenuHandler;
import com.qsoftware.forgemod.modules.environment.client.model.AdditionsModelCache;
import com.qsoftware.forgemod.util.Targeter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EntitiesModule extends CoreRegisterModule<EntityType<?>> {
    public final DeferredRegister<EntityType<?>> ENTITIES = create(ForgeRegistries.ENTITIES);
    private static final EntityMenu entityMenu = new EntityMenu();

    public EntitiesModule() {
        MainActionMenu.registerHandler(new MenuHandler(new StringTextComponent("Entity"), entityMenu, EntitiesModule::enableMenu));
    }

    private static boolean enableMenu() {
        Minecraft mc = Minecraft.getInstance();
        ClientWorld dimension = mc.dimension;
        ClientPlayerEntity player = mc.player;

        EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
        return result != null;
    }

    @Override
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
    }

    @SubscribeEvent
    public void modelRegEvent(ModelRegistryEvent event) {
        AdditionsModelCache.INSTANCE.setup();
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        AdditionsModelCache.INSTANCE.onBake(event);
    }

    @Override
    public void onEnable() {
        ModEntities.register();

        ENTITIES.register(modEventBus);
        modEventBus.register(this);
    }

    @Override
    public @NotNull String getName() {
        return "entities";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }

    @Override
    public DeferredRegister<EntityType<?>> getDeferredRegister() {
        return ENTITIES;
    }

    @Override
    public <O extends EntityType<?>> RegistryObject<O> register(String name, Supplier<O> supplier) {
        return ENTITIES.register(name, supplier);
    }
}
