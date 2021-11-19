package com.ultreon.randomthingz.event;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.capability.EnergyStorageImplBase;
import com.ultreon.randomthingz.entity.damagesource.ModDamageSources;
import com.ultreon.randomthingz.item.BatteryItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class CommonEvents {
    @SubscribeEvent
    public static void onAttachItemCaps(AttachCapabilitiesEvent<Item> event) {
        if (event.getObject() instanceof BatteryItem) {
            event.addCapability(RandomThingz.rl("energy"), new EnergyStorageImplBase(500_000, 10_000, 10_000));
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        try {
            if (event.getSource() == ModDamageSources.RADIATION) {
                Entity entity = event.getEntity();
                World dimension = entity.getEntityDimension();
                Vector3d deathLocation = entity.getPositionVec();
                if (!dimension.isClientSided && dimension instanceof ServerWorld) {
                    ServerWorld serverDim = (ServerWorld) dimension;
                    ZombieEntity zombieEntity = EntityType.ZOMBIE.create(serverDim);
                    if (zombieEntity != null) {
                        zombieEntity.setLocationAndAngles(deathLocation.x, deathLocation.y, deathLocation.z, 0f, 0f);
                        serverDim.spawnEntity(zombieEntity);
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
