package com.ultreon.randomthingz.client.model;

import net.minecraft.client.model.CreeperModel;
import net.minecraft.world.entity.monster.Creeper;

/**
 * Free creeper entity model class.
 *
 * @author Qboi123
 */
@Deprecated
public class FreeCreeperModel<T extends Creeper> extends CreeperModel<T> {
    public FreeCreeperModel() {
        super();
    }

    public FreeCreeperModel(float scale) {
        super(scale);
    }
}
