package com.ultreon.randomthingz.client.model;


import com.ultreon.randomthingz.entity.baby.BabyEndermanEntity;
import net.minecraft.client.model.EndermanModel;

import javax.annotation.Nonnull;

public class BabyEnderman extends EndermanModel<BabyEndermanEntity> {

    public BabyEnderman() {
        super(0);
    }

    @Override
    public void setupAnim(@Nonnull BabyEndermanEntity enderman, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(enderman, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        //Shift the head to be in the proper place for baby endermen
        head.y += 5.0F;
        if (creepy) {
            //Shift the head when angry to only the third the distance it goes up when it is an adult
            head.y += 1.67F;
        }
    }
}