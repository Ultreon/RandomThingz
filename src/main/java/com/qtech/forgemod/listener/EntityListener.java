package com.qtech.forgemod.listener;

import com.qtech.forgemod.QForgeMod;
import lombok.experimental.UtilityClass;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

/**
 * Entity listener.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, value = Dist.CLIENT)
@UtilityClass
public class EntityListener {

}
