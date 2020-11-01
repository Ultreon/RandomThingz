package com.qsoftware.forgemod.client.model;

import com.qsoftware.forgemod.objects.entities.HogEntity;
import net.minecraft.client.renderer.entity.model.PigModel;

public class HogModel<T extends HogEntity> extends PigModel<T> {
    public HogModel() {
        super();
    }

    public HogModel(float scale) {
        super(scale);
    }
}
