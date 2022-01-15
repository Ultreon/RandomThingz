package com.ultreon.randomthingz.client.model;

import com.ultreon.randomthingz.entity.HogEntity;
import net.minecraft.client.model.PigModel;

/**
 * Hog entity model class.
 *
 * @author Qboi123
 */
@Deprecated
public class HogModel<T extends HogEntity> extends PigModel<T> {
    public HogModel() {
        super();
    }

    public HogModel(float scale) {
        super(scale);
    }
}
