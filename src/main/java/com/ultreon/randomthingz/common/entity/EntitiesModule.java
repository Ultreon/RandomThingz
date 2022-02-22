package com.ultreon.randomthingz.common.entity;

import com.ultreon.randomthingz.actionmenu.MainActionMenu;
import com.ultreon.randomthingz.actionmenu.MenuHandler;
import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.common.CoreRegisterModule;
import com.ultreon.randomthingz.common.ModuleSafety;
import com.ultreon.randomthingz.util.Targeter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
        MainActionMenu.registerHandler(new MenuHandler(new TextComponent("Entity"), entityMenu, EntitiesModule::enableMenu));
        MainActionMenu.registerHandler(new MenuHandler(new TextComponent("Player"), playerMenu, EntitiesModule::enableMenu));
    }

    private static boolean enableMenu() {
        return DistExecutor.unsafeRunForDist(() -> EntitiesModule::clientEnableMenu, () -> () -> true);
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean clientEnableMenu() {
        Minecraft mc = Minecraft.getInstance();
        Level dimension = mc.level;
        Player player = mc.player;

        EntityHitResult result = Targeter.rayTraceEntities(player, dimension);
        return result != null;
    }

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
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
