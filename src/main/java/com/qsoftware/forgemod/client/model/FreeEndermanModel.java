package com.qsoftware.forgemod.client.model;

import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.entity.monster.EndermanEntity;

public class FreeEndermanModel<T extends EndermanEntity> extends EndermanModel<T> {
    public FreeEndermanModel(float scale) {
        super(scale);
    }
}
