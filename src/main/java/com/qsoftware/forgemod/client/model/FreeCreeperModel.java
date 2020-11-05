package com.qsoftware.forgemod.client.model;

import com.qsoftware.forgemod.objects.entities.HogEntity;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.entity.monster.CreeperEntity;

/**
 * Free creeper entity model class.
 *
 * @author Qboi123
 */
public class FreeCreeperModel<T extends CreeperEntity> extends CreeperModel<T> {
    public FreeCreeperModel() {
        super();
    }

    public FreeCreeperModel(float scale) {
        super(scale);
    }
}
