package com.ultreon.randomthingz.client.model;


import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.entity.baby.BabyEnderman;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class BabyEndermanModel extends HumanoidModel<BabyEnderman> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RandomThingz.MOD_ID, "baby_enderman_model"), "main");

    public boolean carrying;
    public boolean creepy;

    public BabyEndermanModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        float f = -14f;
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, -14f);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartPose partpose = PartPose.offset(0f, -13f, 0f);
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 16).addBox(-4f, -8f, -4f, 8f, 8f, 8f, new CubeDeformation(-.5f)), partpose);
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4f, -8f, -4f, 8f, 8f, 8f), partpose);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 16).addBox(-4f, 0f, -2f, 8f, 12f, 4f), PartPose.offset(0f, -14f, 0f));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(56, 0).addBox(-1f, -2f, -1f, 2f, 30f, 2f), PartPose.offset(-5f, -12f, 0f));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-1f, -2f, -1f, 2f, 30f, 2f), PartPose.offset(5f, -12f, 0f));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(56, 0).addBox(-1f, 0f, -1f, 2f, 30f, 2f), PartPose.offset(-2f, -5f, 0f));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(-1f, 0f, -1f, 2f, 30f, 2f), PartPose.offset(2f, -5f, 0f));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(BabyEnderman entity, float p_102589_, float p_102590_, float p_102591_, float p_102592_, float p_102593_) {
        this.head.visible = true;
        int i = -14;
        this.body.xRot = 0f;
        this.body.y = -14f;
        this.body.z = -0f;
        this.rightLeg.xRot -= 0f;
        this.leftLeg.xRot -= 0f;
        this.rightArm.xRot = (float)((double)this.rightArm.xRot * 0.5D);
        this.leftArm.xRot = (float)((double)this.leftArm.xRot * 0.5D);
        this.rightLeg.xRot = (float)((double)this.rightLeg.xRot * 0.5D);
        this.leftLeg.xRot = (float)((double)this.leftLeg.xRot * 0.5D);
        float f = .4f;
        if (this.rightArm.xRot > .4f) {
            this.rightArm.xRot = .4f;
        }

        if (this.leftArm.xRot > .4f) {
            this.leftArm.xRot = .4f;
        }

        if (this.rightArm.xRot < -.4f) {
            this.rightArm.xRot = -.4f;
        }

        if (this.leftArm.xRot < -.4f) {
            this.leftArm.xRot = -.4f;
        }

        if (this.rightLeg.xRot > .4f) {
            this.rightLeg.xRot = .4f;
        }

        if (this.leftLeg.xRot > .4f) {
            this.leftLeg.xRot = .4f;
        }

        if (this.rightLeg.xRot < -.4f) {
            this.rightLeg.xRot = -.4f;
        }

        if (this.leftLeg.xRot < -.4f) {
            this.leftLeg.xRot = -.4f;
        }

        if (this.carrying) {
            this.rightArm.xRot = -.5f;
            this.leftArm.xRot = -.5f;
            this.rightArm.zRot = .05f;
            this.leftArm.zRot = -.05f;
        }

        this.rightLeg.z = 0f;
        this.leftLeg.z = 0f;
        this.rightLeg.y = -5f;
        this.leftLeg.y = -5f;
        this.head.z = -0f;
        this.head.y = -13f;
        this.hat.x = this.head.x;
        this.hat.y = this.head.y;
        this.hat.z = this.head.z;
        this.hat.xRot = this.head.xRot;
        this.hat.yRot = this.head.yRot;
        this.hat.zRot = this.head.zRot;
        if (this.creepy) {
            float f1 = 1f;
            this.head.y -= 5f;
        }

        int j = -14;
        this.rightArm.setPos(-5f, -12f, 0f);
        this.leftArm.setPos(5f, -12f, 0f);
    }
}
