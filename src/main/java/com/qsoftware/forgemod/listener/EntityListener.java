package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeMod;
import lombok.experimental.UtilityClass;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

/**
 * Entity listener.
 *
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeMod.modId, value = Dist.CLIENT)
@UtilityClass
@Deprecated
public class EntityListener {
}
