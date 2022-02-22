package com.ultreon.randomthingz.client.model;// Made with Blockbench 4.1.4
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ultreon.randomthingz.entity.Cluckshroom;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class CluckshroomModel<T extends Cluckshroom> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "custom_model"), "main");
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart bill;
    private final ModelPart chin;

    public CluckshroomModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
        this.right_wing = root.getChild("right_wing");
        this.left_wing = root.getChild("left_wing");
        this.bill = root.getChild("bill");
        this.chin = root.getChild("chin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(19, 5).addBox(-2f, -6f, -2f, 4f, 6f, 3f, new CubeDeformation(0f)), PartPose.offset(0f, 15f, -4f));

        PartDefinition shroom1 = head.addOrReplaceChild("shroom1", CubeListBuilder.create().texOffs(5, 0).addBox(0f, -13f, -4f, 0f, 7f, 7f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, .48f, 0f));

        PartDefinition shroom2 = head.addOrReplaceChild("shroom2", CubeListBuilder.create().texOffs(5, 0).addBox(-.5f, -13f, -3.5F, 0f, 7f, 7f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, -1.0908F, 0f));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0f, 16f, 0f));

        PartDefinition rotation = body.addOrReplaceChild("rotation", CubeListBuilder.create().texOffs(19, 14).addBox(-3f, -4f, -3f, 6f, 8f, 6f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 1.5708F, 0f, 0f));

        PartDefinition shroom3 = body.addOrReplaceChild("shroom3", CubeListBuilder.create().texOffs(5, 0).addBox(-1f, -10f, -2.5F, 0f, 7f, 7f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, -1.0908F, 0f));

        PartDefinition shroom4 = body.addOrReplaceChild("shroom4", CubeListBuilder.create().texOffs(5, 0).addBox(-1f, -10f, -4.5F, 0f, 7f, 7f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, .48f, 0f));

        PartDefinition shroom5 = body.addOrReplaceChild("shroom5", CubeListBuilder.create().texOffs(5, 0).addBox(2f, -10f, -4.5F, 0f, 7f, 7f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, -1.0908F, 0f));

        PartDefinition shroom6 = body.addOrReplaceChild("shroom6", CubeListBuilder.create().texOffs(5, 0).addBox(1f, -10f, -1.5F, 0f, 7f, 7f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, .48f, 0f));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(45, 5).addBox(-2f, 0f, -3f, 3f, 5f, 3f, new CubeDeformation(0f)), PartPose.offset(2f, 19f, 1f));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(45, 5).addBox(-2f, 0f, -3f, 3f, 5f, 3f, new CubeDeformation(0f)), PartPose.offset(-1f, 19f, 1f));

        PartDefinition right_wing = partdefinition.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(43, 18).addBox(-8f, 0f, -3f, 1f, 4f, 6f, new CubeDeformation(0f)), PartPose.offset(4f, 13f, 0f));

        PartDefinition left_wing = partdefinition.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(43, 18).addBox(7f, 0f, -3f, 1f, 4f, 6f, new CubeDeformation(0f)), PartPose.offset(-4f, 13f, 0f));

        PartDefinition bill = partdefinition.addOrReplaceChild("bill", CubeListBuilder.create().texOffs(33, 5).addBox(-2f, -4f, -4f, 4f, 2f, 2f, new CubeDeformation(0f)), PartPose.offset(0f, 15f, -4f));

        PartDefinition chin = partdefinition.addOrReplaceChild("chin", CubeListBuilder.create().texOffs(34, 10).addBox(-1f, -2f, -3f, 2f, 2f, 1f, new CubeDeformation(0f)), PartPose.offset(0f, 15f, -4f));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, buffer, packedLight, packedOverlay);
        body.render(poseStack, buffer, packedLight, packedOverlay);
        right_leg.render(poseStack, buffer, packedLight, packedOverlay);
        left_leg.render(poseStack, buffer, packedLight, packedOverlay);
        right_wing.render(poseStack, buffer, packedLight, packedOverlay);
        left_wing.render(poseStack, buffer, packedLight, packedOverlay);
        bill.render(poseStack, buffer, packedLight, packedOverlay);
        chin.render(poseStack, buffer, packedLight, packedOverlay);
    }
}