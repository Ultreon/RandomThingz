package com.ultreon.randomthingz.client.model;

import com.google.common.collect.ImmutableList;
import com.ultreon.randomthingz.entity.baby.BabyCreeperEntity;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BabyCreeperModel extends AgeableListModel<BabyCreeperEntity> {

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public BabyCreeperModel() {
        this(0);
    }

    public BabyCreeperModel(float size) {
        this.head = new ModelPart(this, 0, 0);
        this.head.addBox(-4, -8, -4, 8, 8, 8, size);
        //Only real difference between this model and the vanilla creeper model is the "fix" for the head's rotation point
        // the other difference is extending ageable model instead
        this.head.setPos(0, 10, -2);
        this.body = new ModelPart(this, 16, 16);
        this.body.addBox(-4, 0, -2, 8, 12, 4, size);
        this.body.setPos(0, 6, 0);
        this.leg1 = new ModelPart(this, 0, 16);
        this.leg1.addBox(-2, 0, -2, 4, 6, 4, size);
        this.leg1.setPos(-2, 18, 4);
        this.leg2 = new ModelPart(this, 0, 16);
        this.leg2.addBox(-2, 0, -2, 4, 6, 4, size);
        this.leg2.setPos(2, 18, 4);
        this.leg3 = new ModelPart(this, 0, 16);
        this.leg3.addBox(-2, 0, -2, 4, 6, 4, size);
        this.leg3.setPos(-2, 18, -4);
        this.leg4 = new ModelPart(this, 0, 16);
        this.leg4.addBox(-2, 0, -2, 4, 6, 4, size);
        this.leg4.setPos(2, 18, -4);
    }

    @NotNull
    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head);
    }

    @NotNull
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.leg1, this.leg2, this.leg3, this.leg4);
    }

    @Override
    public void setupAnim(@NotNull BabyCreeperEntity creeper, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}