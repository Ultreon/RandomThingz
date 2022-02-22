/**
 Made with Blockbench 4.1.4
 Exported for Minecraft version 1.17 with Mojang mappings
 Paste this class into your mod and generate all required imports
 */
package com.ultreon.randomthingz.client.model;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.entity.Moobloom;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MoobloomModel<T extends Moobloom> extends AgeableListModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RandomThingz.MOD_ID, "moobloom_model"), "main");
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public MoobloomModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshes = new MeshDefinition();
        PartDefinition parts = meshes.getRoot();

        PartDefinition body = parts.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 4).mirror().addBox(-6f, -10f, -7f, 12f, 18f, 10f, new CubeDeformation(0f)).mirror(false)
                .texOffs(52, 0).mirror().addBox(-2f, 2f, -8f, 4f, 6f, 1f, new CubeDeformation(0f)).mirror(false), PartPose.offsetAndRotation(0f, 5f, 2f, 1.5708F, 0f, 0f));

        PartDefinition fungus2 = body.addOrReplaceChild("fungus2", CubeListBuilder.create().texOffs(64, 0).addBox(-6f, -19.5F, -6f, 10f, 8f, 0f, new CubeDeformation(0f))
                .texOffs(64, 0).addBox(-6f, -19.5F, -6f, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(4f, -.5f, -8f, -1.5708F, 0f, -.7854f));

        PartDefinition bone7 = fungus2.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0, 32).addBox(.3284f, -14f, -1.5F, 3f, 8f, 3f, new CubeDeformation(0f)), PartPose.offsetAndRotation(-2.2929F, -4.5F, -7.1213F, 0f, -.7854f, 0f));

        PartDefinition bone2 = fungus2.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(64, 0).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f))
                .texOffs(64, 0).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(-1f, -15.5F, -6f, 0f, 1.5708F, 0f));

        PartDefinition cactus2 = fungus2.addOrReplaceChild("cactus2", CubeListBuilder.create().texOffs(12, 32).addBox(-2f, -4f, -2f, 4f, 8f, 4f, new CubeDeformation(0f))
                .texOffs(12, 32).addBox(-2.0919F, -8.9001F, -2.0081F, 4f, 5f, 4f, new CubeDeformation(0f))
                .texOffs(24, 52).addBox(-3.4845F, -13.7441F, -3.0155F, 6f, 6f, 6f, new CubeDeformation(0f)), PartPose.offsetAndRotation(-1.8787F, -14.5F, -7.1213F, 0f, -.7854f, -.2182f));

        PartDefinition bone10 = cactus2.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(13, 34).addBox(-3f, -1f, -2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 34).addBox(2f, -3f, -2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 34).addBox(2f, 2f, -2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 34).addBox(-3f, 1f, 2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(-2f, -1f, 2f, 0f, 1f, 1f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(2f, -2f, 2f, 0f, 1f, 1f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(2f, 3f, 2f, 0f, 1f, 1f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(-2f, 3f, -3f, 0f, 1f, 1f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));

        PartDefinition fungus3 = body.addOrReplaceChild("fungus3", CubeListBuilder.create().texOffs(64, 0).addBox(-5f, -18f, -5.5F, 10f, 8f, 0f, new CubeDeformation(0f))
                .texOffs(64, 0).addBox(-5f, -18f, -5.5F, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(4f, 10f, -7.5F, -1.5708F, 0f, 0f));

        PartDefinition bone8 = fungus3.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(0, 32).addBox(1.7071F, -3f, -2.1213F, 3f, 8f, 3f, new CubeDeformation(0f)), PartPose.offsetAndRotation(-2.2929F, -15f, -7.6213F, 0f, -.7854f, 0f));

        PartDefinition bone3 = fungus3.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(64, 0).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f))
                .texOffs(64, 0).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, -14f, -5.5F, 0f, 1.5708F, 0f));

        PartDefinition cactus3 = fungus3.addOrReplaceChild("cactus3", CubeListBuilder.create().texOffs(12, 32).addBox(-2f, -4f, -2f, 4f, 8f, 4f, new CubeDeformation(0f))
                .texOffs(24, 52).addBox(-3.4061F, -6.9333F, -2.662F, 6f, 6f, 6f, new CubeDeformation(0f)), PartPose.offsetAndRotation(-1f, -13f, -6.5F, .2399f, -.4253f, -.1006f));

        PartDefinition bone11 = cactus3.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(13, 34).addBox(-3f, -1f, -2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 34).addBox(2f, -3f, -2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 34).addBox(2f, 2f, -2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 34).addBox(-3f, 1f, 2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(-2f, -1f, 2f, 0f, 1f, 1f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(2f, -2f, 2f, 0f, 1f, 1f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(2f, 3f, 2f, 0f, 1f, 1f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(-2f, 3f, -3f, 0f, 1f, 1f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));

        PartDefinition fungus4 = body.addOrReplaceChild("fungus4", CubeListBuilder.create().texOffs(64, 8).addBox(-6f, -19.5F, -6f, 10f, 8f, 0f, new CubeDeformation(0f))
                .texOffs(64, 8).addBox(-6f, -19.5F, -6f, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(4f, -.5f, 0f, -1.5708F, 0f, -.7854f));

        PartDefinition bone5 = fungus4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(64, 8).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f))
                .texOffs(64, 8).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(-1f, -15.5F, -6f, 0f, 1.5708F, 0f));

        PartDefinition fungus5 = body.addOrReplaceChild("fungus5", CubeListBuilder.create().texOffs(64, 8).addBox(-5f, -18f, -5.5F, 10f, 8f, 0f, new CubeDeformation(0f))
                .texOffs(64, 8).addBox(-5f, -18f, -5.5F, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(4f, 10f, .5f, -1.5708F, 0f, 0f));

        PartDefinition bone6 = fungus5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(64, 8).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f))
                .texOffs(64, 8).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, -14f, -5.5F, 0f, 1.5708F, 0f));

        PartDefinition head = parts.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4f, -4f, -6f, 8f, 8f, 6f, new CubeDeformation(0f)).mirror(false)
                .texOffs(22, 0).mirror().addBox(4f, -5f, -4f, 1f, 3f, 1f, new CubeDeformation(0f)).mirror(false)
                .texOffs(22, 0).mirror().addBox(-5f, -5f, -4f, 1f, 3f, 1f, new CubeDeformation(0f)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-6f, -2f, -5f, 2f, 2f, 1f, new CubeDeformation(0f)).mirror(false), PartPose.offset(0f, 4f, -8f));

        PartDefinition headSub0 = head.addOrReplaceChild("head_sub_0", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-1.5F, -32f, -12.5F, 3f, 8f, 3f, new CubeDeformation(0f)).mirror(false), PartPose.offset(0f, 20f, 8f));

        PartDefinition headSub1 = head.addOrReplaceChild("head_sub_1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(4f, -42f, -21f, 2f, 2f, 1f, new CubeDeformation(0f)).mirror(false)
                .texOffs(53, 10).mirror().addBox(-2f, -38f, -22.5F, 4f, 2f, 1f, new CubeDeformation(0f)).mirror(false), PartPose.offset(0f, 40f, 16f));

        PartDefinition fungus = head.addOrReplaceChild("fungus", CubeListBuilder.create().texOffs(64, 0).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f))
                .texOffs(64, 8).addBox(-5f, -12f, 0f, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, -8f, -3f, 0f, -1.1345F, 0f));

        PartDefinition bone = fungus.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(64, 0).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 1.5708F, 0f));

        PartDefinition bone4 = fungus.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(64, 8).addBox(-5f, -4f, 0f, 10f, 8f, 0f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, -8f, 0f, 0f, 1.5708F, 0f));

        PartDefinition cactus = fungus.addOrReplaceChild("cactus", CubeListBuilder.create().texOffs(12, 32).addBox(-2f, -4f, -2f, 4f, 8f, 4f, new CubeDeformation(0f))
                .texOffs(0, 52).addBox(-2.7653F, -9.348F, -3.3967F, 6f, 6f, 6f, new CubeDeformation(0f)), PartPose.offsetAndRotation(0f, 1f, 0f, .1922f, -.4293f, -.0808f));

        PartDefinition bone9 = cactus.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(13, 34).addBox(-3f, -1f, -2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 34).addBox(2f, -3f, -2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 34).addBox(2f, 2f, -2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 34).addBox(-3f, 1f, 2f, 1f, 1f, 0f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(-2f, -1f, 2f, 0f, 1f, 1f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(2f, -2f, 2f, 0f, 1f, 1f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(2f, 3f, 2f, 0f, 1f, 1f, new CubeDeformation(0f))
                .texOffs(13, 33).addBox(-2f, 3f, -3f, 0f, 1f, 1f, new CubeDeformation(0f)), PartPose.offset(0f, 0f, 0f));

        PartDefinition leg1 = parts.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0f)).mirror(false), PartPose.offset(4f, 12f, 7f));

        PartDefinition leg2 = parts.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2f, 0f, -2f, 4f, 12f, 4f, new CubeDeformation(0f)).mirror(false), PartPose.offset(-4f, 12f, 7f));

        PartDefinition leg3 = parts.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2f, 0f, -1f, 4f, 12f, 4f, new CubeDeformation(0f)).mirror(false), PartPose.offset(4f, 12f, -6f));

        PartDefinition leg4 = parts.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2f, 0f, -1f, 4f, 12f, 4f, new CubeDeformation(0f)).mirror(false), PartPose.offset(-4f, 12f, -6f));

        return LayerDefinition.create(meshes, 96, 64);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, buffer, packedLight, packedOverlay);
        head.render(poseStack, buffer, packedLight, packedOverlay);
        leg1.render(poseStack, buffer, packedLight, packedOverlay);
        leg2.render(poseStack, buffer, packedLight, packedOverlay);
        leg3.render(poseStack, buffer, packedLight, packedOverlay);
        leg4.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    protected @NotNull Iterable<ModelPart> headParts() {
        return List.of(head);
    }

    @Override
    protected @NotNull Iterable<ModelPart> bodyParts() {
        return List.of(body, leg1, leg2, leg3, leg4);
    }

    public ModelPart getHead() {
        return head;
    }
}