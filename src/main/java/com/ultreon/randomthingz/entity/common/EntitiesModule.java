package com.ultreon.randomthingz.entity.common;

import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.client.model.AdditionsModelCache;
import com.ultreon.randomthingz.commons.CoreRegisterModule;
import com.ultreon.randomthingz.commons.ModuleSafety;
import com.ultreon.randomthingz.modules.actionmenu.MainActionMenu;
import com.ultreon.randomthingz.modules.actionmenu.MenuHandler;
import com.ultreon.randomthingz.util.Targeter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EntitiesModule extends CoreRegisterModule<EntityType<?>> {
    public final DeferredRegister<EntityType<?>> ENTITIES = create(ForgeRegistries.ENTITIES);
    private static final EntityMenu entityMenu = new EntityMenu();
    private static final PlayerMenu playerMenu = new PlayerMenu();

    public EntitiesModule() {
        MainActionMenu.registerHandler(new MenuHandler(new StringTextComponent("Entity"), entityMenu, EntitiesModule::enableMenu));
        MainActionMenu.registerHandler(new MenuHandler(new StringTextComponent("Player"), playerMenu, EntitiesModule::enableMenu));
    }

    private static boolean enableMenu() {
        return DistExecutor.unsafeRunForDist(() -> EntitiesModule::clientEnableMenu, () -> () -> true);
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean clientEnableMenu() {
        Minecraft mc = Minecraft.getInstance();
        World dimension = mc.dimension;
        PlayerEntity player = mc.player;

        EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
        return result != null;
    }

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event) {
        AdditionsModelCache.INSTANCE.setup();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        AdditionsModelCache.INSTANCE.onBake(event);
    }

    @Override
    public void onEnable() {
        ModEntities.register();

        ENTITIES.register(modEventBus);
//        modEventBus.register(this);

        DistExecutor.unsafeRunForDist(() -> () -> {
            enableClient();
            return null;
        }, () -> () -> null);
    }

    private void enableClient() {
        MinecraftForge.EVENT_BUS.addListener(this::onModelRegistry);
        MinecraftForge.EVENT_BUS.addListener(this::onModelBake);
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
