package com.qtech.randomthingz.client.model;

import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.entity.monster.EndermanEntity;

/**
 * Free enderman model class.
 *
 * @author Qboi123
 */
public class FreeEndermanModel<T extends EndermanEntity> extends EndermanModel<T> {
    public FreeEndermanModel(float scale) {
        super(scale);
    }
}
