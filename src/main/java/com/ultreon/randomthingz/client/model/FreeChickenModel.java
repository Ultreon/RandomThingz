package com.ultreon.randomthingz.client.model;

import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Chicken;

/**
 * Free chicken entity model class.
 *
 * @author Qboi123
 */
@Deprecated
public class FreeChickenModel<T extends Chicken> extends ChickenModel<T> {
    public FreeChickenModel(ModelPart modelPart) {
        super(modelPart);
    }
}
