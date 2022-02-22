package com.ultreon.randomthingz.event;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.capability.EnergyStorageImplBase;
import com.ultreon.randomthingz.entity.damagesource.ModDamageSources;
import com.ultreon.randomthingz.item.BatteryItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void onAttachItemCaps(AttachCapabilitiesEvent<Item> event) {
        if (event.getObject() instanceof BatteryItem) {
            event.addCapability(RandomThingz.res("energy"), new EnergyStorageImplBase(500_000, 10_000, 10_000));
        }
    }

    @SubscribeEvent
    public static void die(LivingDeathEvent event) {
        try {
            if (event.getSource() == ModDamageSources.RADIATION) {
                Entity entity = event.getEntity();
                Level dimension = entity.getCommandSenderWorld();
                Vec3 deathLocation = entity.position();
                if (!dimension.isClientSide && dimension instanceof ServerLevel) {
                    ServerLevel serverDim = (ServerLevel) dimension;
                    Zombie zombieEntity = EntityType.ZOMBIE.create(serverDim);
                    if (zombieEntity != null) {
                        zombieEntity.moveTo(deathLocation.x, deathLocation.y, deathLocation.z, 0f, 0f);
                        serverDim.addFreshEntity(zombieEntity);
                    }
                }
            }
        } catch (Throwable t) {
            RandomThingz.LOGGER.error("Exception thrown in death event.");
            RandomThingz.LOGGER.error("Stack trace follows:");
            t.printStackTrace();
        }
    }
}
