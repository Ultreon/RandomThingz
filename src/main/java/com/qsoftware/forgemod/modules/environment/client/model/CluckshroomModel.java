package com.qsoftware.forgemod.modules.environment.client.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.qsoftware.forgemod.modules.environment.entities.CluckshroomEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldCanBeLocal")
public class CluckshroomModel<T extends CluckshroomEntity> extends AgeableModel<T> {
	private final ModelRenderer head;
	private final ModelRenderer mushroom1;
	private final ModelRenderer mushroom2;
	private final ModelRenderer body;
	private final ModelRenderer rotation;
	private final ModelRenderer mushroom3;
	private final ModelRenderer mushroom4;
	private final ModelRenderer mushroom5;
	private final ModelRenderer mushroom6;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;
	private final ModelRenderer rightWing;
	private final ModelRenderer leftWing;
	private final ModelRenderer bill;
	private final ModelRenderer chin;

	public CluckshroomModel() {
		textureWidth = 64;
		textureHeight = 32;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 15.0F, -4.0F);
		head.setTextureOffset(19, 5).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F, false);

		mushroom1 = new ModelRenderer(this);
		mushroom1.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(mushroom1);
		setRotationAngle(mushroom1, 0.0F, 0.48F, 0.0F);
		mushroom1.setTextureOffset(5, 0).addBox(0.0F, -13.0F, -4.0F, 0.0F, 7.0F, 7.0F, 0.0F, false);

		mushroom2 = new ModelRenderer(this);
		mushroom2.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.addChild(mushroom2);
		setRotationAngle(mushroom2, 0.0F, -1.0908F, 0.0F);
		mushroom2.setTextureOffset(5, 0).addBox(-0.5F, -13.0F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 16.0F, 0.0F);

		rotation = new ModelRenderer(this);
		rotation.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(rotation);
		setRotationAngle(rotation, 1.5708F, 0.0F, 0.0F);
		rotation.setTextureOffset(19, 14).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);

		mushroom3 = new ModelRenderer(this);
		mushroom3.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(mushroom3);
		setRotationAngle(mushroom3, 0.0F, -1.0908F, 0.0F);
		mushroom3.setTextureOffset(5, 0).addBox(-1.0F, -10.0F, -2.5F, 0.0F, 7.0F, 7.0F, 0.0F, false);

		mushroom4 = new ModelRenderer(this);
		mushroom4.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(mushroom4);
		setRotationAngle(mushroom4, 0.0F, 0.48F, 0.0F);
		mushroom4.setTextureOffset(5, 0).addBox(-1.0F, -10.0F, -4.5F, 0.0F, 7.0F, 7.0F, 0.0F, false);

		mushroom5 = new ModelRenderer(this);
		mushroom5.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(mushroom5);
		setRotationAngle(mushroom5, 0.0F, -1.0908F, 0.0F);
		mushroom5.setTextureOffset(5, 0).addBox(2.0F, -10.0F, -4.5F, 0.0F, 7.0F, 7.0F, 0.0F, false);

		mushroom6 = new ModelRenderer(this);
		mushroom6.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.addChild(mushroom6);
		setRotationAngle(mushroom6, 0.0F, 0.48F, 0.0F);
		mushroom6.setTextureOffset(5, 0).addBox(1.0F, -10.0F, -1.5F, 0.0F, 7.0F, 7.0F, 0.0F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setRotationPoint(2.0F, 19.0F, 1.0F);
		rightLeg.setTextureOffset(45, 5).addBox(-2.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setRotationPoint(-1.0F, 19.0F, 1.0F);
		leftLeg.setTextureOffset(45, 5).addBox(-2.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

//		rightWing = new ModelRenderer(this);
//		rightWing.setRotationPoint(4.0F, 8.0F, 0.0F);
//		rightWing.setTextureOffset(43, 18).addBox(-8.0F, -5.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
//
//		leftWing = new ModelRenderer(this);
//		leftWing.setRotationPoint(-4.0F, 8.0F, 0.0F);
//		leftWing.setTextureOffset(43, 18).addBox(7.0F, -5.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);

		rightWing = new ModelRenderer(this, 43, 18);
		rightWing.addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
		rightWing.setRotationPoint(-4.0F, 13.0F, 0.0F);

		leftWing = new ModelRenderer(this, 43, 18);
		leftWing.addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F);
		leftWing.setRotationPoint(4.0F, 13.0F, 0.0F);

		bill = new ModelRenderer(this);
		bill.setRotationPoint(0.0F, 15.0F, -4.0F);
		bill.setTextureOffset(33, 5).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);

		chin = new ModelRenderer(this);
		chin.setRotationPoint(0.0F, 15.0F, -4.0F);
		chin.setTextureOffset(34, 10).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void render(@NotNull MatrixStack matrixStack, @NotNull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
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
	protected @NotNull Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.head, this.bill, this.chin);
	}

	@Override
	protected @NotNull Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	/**
	 * Sets this entity's model rotation angles
	 */
	@Override
	public void setRotationAngles(@NotNull T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.rotateAngleX = headPitch * ((float)Math.PI / 180F);
		this.head.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
		this.bill.rotateAngleX = this.head.rotateAngleX;
		this.bill.rotateAngleY = this.head.rotateAngleY;
		this.chin.rotateAngleX = this.head.rotateAngleX;
		this.chin.rotateAngleY = this.head.rotateAngleY;
//		this.body.rotateAngleX = ((float)Math.PI / 2F);
		this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
		this.rightWing.rotateAngleZ = ageInTicks;
		this.leftWing.rotateAngleZ = -ageInTicks;
	}
}