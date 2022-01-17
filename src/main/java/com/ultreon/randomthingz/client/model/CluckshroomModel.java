package com.ultreon.randomthingz.client.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ultreon.randomthingz.entity.CluckshroomEntity;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldCanBeLocal")
public class CluckshroomModel<T extends CluckshroomEntity> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart mushroom1;
    private final ModelPart mushroom2;
    private final ModelPart body;
    private final ModelPart rotation;
    private final ModelPart mushroom3;
    private final ModelPart mushroom4;
    private final ModelPart mushroom5;
    private final ModelPart mushroom6;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart bill;
    private final ModelPart chin;

    public CluckshroomModel() {
        texWidth = 64;
        texHeight = 32;

        head = new ModelPart(this);
        head.setPos(0.0f, 15.0f, -4.0f);
        head.texOffs(19, 5).addBox(-2.0f, -6.0f, -2.0f, 4.0f, 6.0f, 3.0f, 0.0f, false);

        mushroom1 = new ModelPart(this);
        mushroom1.setPos(0.0f, 0.0f, 0.0f);
        head.addChild(mushroom1);
        setRotationAngle(mushroom1, 0.0f, 0.48F, 0.0f);
        mushroom1.texOffs(5, 0).addBox(0.0f, -13.0f, -4.0f, 0.0f, 7.0f, 7.0f, 0.0f, false);

        mushroom2 = new ModelPart(this);
        mushroom2.setPos(0.0f, 0.0f, 0.0f);
        head.addChild(mushroom2);
        setRotationAngle(mushroom2, 0.0f, -1.0908F, 0.0f);
        mushroom2.texOffs(5, 0).addBox(-0.5F, -13.0f, -3.5F, 0.0f, 7.0f, 7.0f, 0.0f, false);

        body = new ModelPart(this);
        body.setPos(0.0f, 16.0f, 0.0f);

        rotation = new ModelPart(this);
        rotation.setPos(0.0f, 0.0f, 0.0f);
        body.addChild(rotation);
        setRotationAngle(rotation, 1.5708F, 0.0f, 0.0f);
        rotation.texOffs(19, 14).addBox(-3.0f, -4.0f, -3.0f, 6.0f, 8.0f, 6.0f, 0.0f, false);

        mushroom3 = new ModelPart(this);
        mushroom3.setPos(0.0f, 0.0f, 0.0f);
        body.addChild(mushroom3);
        setRotationAngle(mushroom3, 0.0f, -1.0908F, 0.0f);
        mushroom3.texOffs(5, 0).addBox(-1.0f, -10.0f, -2.5F, 0.0f, 7.0f, 7.0f, 0.0f, false);

        mushroom4 = new ModelPart(this);
        mushroom4.setPos(0.0f, 0.0f, 0.0f);
        body.addChild(mushroom4);
        setRotationAngle(mushroom4, 0.0f, 0.48F, 0.0f);
        mushroom4.texOffs(5, 0).addBox(-1.0f, -10.0f, -4.5F, 0.0f, 7.0f, 7.0f, 0.0f, false);

        mushroom5 = new ModelPart(this);
        mushroom5.setPos(0.0f, 0.0f, 0.0f);
        body.addChild(mushroom5);
        setRotationAngle(mushroom5, 0.0f, -1.0908F, 0.0f);
        mushroom5.texOffs(5, 0).addBox(2.0f, -10.0f, -4.5F, 0.0f, 7.0f, 7.0f, 0.0f, false);

        mushroom6 = new ModelPart(this);
        mushroom6.setPos(0.0f, 0.0f, 0.0f);
        body.addChild(mushroom6);
        setRotationAngle(mushroom6, 0.0f, 0.48F, 0.0f);
        mushroom6.texOffs(5, 0).addBox(1.0f, -10.0f, -1.5F, 0.0f, 7.0f, 7.0f, 0.0f, false);

        rightLeg = new ModelPart(this);
        rightLeg.setPos(2.0f, 19.0f, 1.0f);
        rightLeg.texOffs(45, 5).addBox(-2.0f, 0.0f, -3.0f, 3.0f, 5.0f, 3.0f, 0.0f, false);

        leftLeg = new ModelPart(this);
        leftLeg.setPos(-1.0f, 19.0f, 1.0f);
        leftLeg.texOffs(45, 5).addBox(-2.0f, 0.0f, -3.0f, 3.0f, 5.0f, 3.0f, 0.0f, false);

//		rightWing = new ModelRenderer(this);
//		rightWing.setRotationPoint(4.0f, 8.0f, 0.0f);
//		rightWing.setTextureOffset(43, 18).addBox(-8.0f, -5.0f, -3.0f, 1.0f, 4.0f, 6.0f, 0.0f, false);
//
//		leftWing = new ModelRenderer(this);
//		leftWing.setRotationPoint(-4.0f, 8.0f, 0.0f);
//		leftWing.setTextureOffset(43, 18).addBox(7.0f, -5.0f, -3.0f, 1.0f, 4.0f, 6.0f, 0.0f, false);

        rightWing = new ModelPart(this, 43, 18);
        rightWing.addBox(0.0f, 0.0f, -3.0f, 1.0f, 4.0f, 6.0f);
        rightWing.setPos(-4.0f, 13.0f, 0.0f);

        leftWing = new ModelPart(this, 43, 18);
        leftWing.addBox(-1.0f, 0.0f, -3.0f, 1.0f, 4.0f, 6.0f);
        leftWing.setPos(4.0f, 13.0f, 0.0f);

        bill = new ModelPart(this);
        bill.setPos(0.0f, 15.0f, -4.0f);
        bill.texOffs(33, 5).addBox(-2.0f, -4.0f, -4.0f, 4.0f, 2.0f, 2.0f, 0.0f, false);

        chin = new ModelPart(this);
        chin.setPos(0.0f, 15.0f, -4.0f);
        chin.texOffs(34, 10).addBox(-1.0f, -2.0f, -3.0f, 2.0f, 2.0f, 1.0f, 0.0f, false);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack matrixStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        rightLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
        rightWing.render(matrixStack, buffer, packedLight, packedOverlay);
        leftWing.render(matrixStack, buffer, packedLight, packedOverlay);
        bill.render(matrixStack, buffer, packedLight, packedOverlay);
        chin.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    protected @NotNull Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head, this.bill, this.chin);
    }

    @Override
    protected @NotNull Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.body, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    /**
     * Sets this entity's model rotation angles
     */
    @Override
    public void setupAnim(@NotNull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.bill.xRot = this.head.xRot;
        this.bill.yRot = this.head.yRot;
        this.chin.xRot = this.head.xRot;
        this.chin.yRot = this.head.yRot;
//		this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightWing.zRot = ageInTicks;
        this.leftWing.zRot = -ageInTicks;
    }
}