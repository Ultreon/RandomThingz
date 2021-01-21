package com.qsoftware.forgemod.modules.entities.model;

import net.minecraft.client.renderer.entity.model.CreeperModel;
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
