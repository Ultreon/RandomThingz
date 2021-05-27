package com.qtech.randomthingz.listener;

import com.qtech.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

/**
 * Entity listener.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, value = Dist.CLIENT)
@UtilityClass
public class EntityListener {

}
