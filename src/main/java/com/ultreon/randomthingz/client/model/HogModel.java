package com.ultreon.randomthingz.client.model;

import com.ultreon.randomthingz.entity.Hog;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelPart;

/**
 * Hog entity model class.
 *
 * @author Qboi123
 */
@Deprecated
public class HogModel<T extends Hog> extends PigModel<T> {
    public HogModel(ModelPart modelPart) {
        super(modelPart);
    }
}
