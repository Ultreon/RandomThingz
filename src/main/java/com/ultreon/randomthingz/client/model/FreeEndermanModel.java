package com.ultreon.randomthingz.client.model;

import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.EnderMan;

/**
 * Free enderman model class.
 *
 * @author Qboi123
 */
@Deprecated
public class FreeEndermanModel<T extends EnderMan> extends EndermanModel<T> {
    public FreeEndermanModel(ModelPart modelPart) {
        super(modelPart);
    }
}
