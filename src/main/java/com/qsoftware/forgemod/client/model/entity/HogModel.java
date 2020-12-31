package com.qsoftware.forgemod.client.model.entity;

import com.qsoftware.forgemod.objects.entities.HogEntity;
import net.minecraft.client.renderer.entity.model.PigModel;

/**
 * Hog entity model class.
 *
 * @author Qboi123
 */
public class HogModel<T extends HogEntity> extends PigModel<T> {
    public HogModel() {
        super();
    }

    public HogModel(float scale) {
        super(scale);
    }
}
