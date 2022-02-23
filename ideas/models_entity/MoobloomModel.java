// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class MoobloomModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "moobloommodel"), "main");
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart left_hind_leg;
	private final ModelPart right_hind_leg;
	private final ModelPart left_front_leg;
	private final ModelPart right_front_leg;

	public MoobloomModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.left_hind_leg = root.getChild("left_hind_leg");
		this.right_hind_leg = root.getChild("right_hind_leg");
		this.left_front_leg = root.getChild("left_front_leg");
		this.right_front_leg = root.getChild("right_front_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 4).mirror().addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(52, 0).mirror().addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition plant_body_middle = body.addOrReplaceChild("plant_body_middle", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -0.5F, -8.0F, -1.5708F, 0.0F, -0.7854F));

		PartDefinition flower_body_middle = plant_body_middle.addOrReplaceChild("flower_body_middle", CubeListBuilder.create(), PartPose.offset(-4.0F, 19.5F, 6.0F));

		PartDefinition flower_bottom5 = flower_body_middle.addOrReplaceChild("flower_bottom5", CubeListBuilder.create().texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -35.0F, -12.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition flower_bottom3 = flower_body_middle.addOrReplaceChild("flower_bottom3", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -39.0F, -12.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-2.0F, -39.0F, -12.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition stick = plant_body_middle.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(0, 32).addBox(0.3284F, -14.0F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2929F, -4.5F, -7.1213F, 0.0F, -0.7854F, 0.0F));

		PartDefinition plant_body_middle2 = plant_body_middle.addOrReplaceChild("plant_body_middle2", CubeListBuilder.create().texOffs(24, 52).addBox(-2.9845F, -13.7441F, -3.0155F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8787F, -14.5F, -7.1213F, 0.0F, -0.7854F, -0.2182F));

		PartDefinition cactus_body_middle = plant_body_middle2.addOrReplaceChild("cactus_body_middle", CubeListBuilder.create().texOffs(12, 32).addBox(0.1213F, -38.0F, -15.1213F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(12, 32).addBox(0.0294F, -42.9001F, -15.1294F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.1213F, 34.0F, 13.1213F));

		PartDefinition plant_deco_body_middle = plant_body_middle2.addOrReplaceChild("plant_deco_body_middle", CubeListBuilder.create().texOffs(13, 34).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 34).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 34).addBox(2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 34).addBox(-3.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(-2.0F, -1.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(2.0F, -2.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(2.0F, 3.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(-2.0F, 3.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition plant_back = body.addOrReplaceChild("plant_back", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 10.0F, -7.5F, -1.5708F, 0.0F, 0.0F));

		PartDefinition flower_back = plant_back.addOrReplaceChild("flower_back", CubeListBuilder.create().texOffs(64, 0).addBox(-1.0F, -27.0F, -11.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-1.0F, -27.0F, -11.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 9.0F, 5.5F));

		PartDefinition bamboo = plant_back.addOrReplaceChild("bamboo", CubeListBuilder.create().texOffs(0, 32).addBox(1.7071F, -3.0F, -2.1213F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2929F, -15.0F, -7.6213F, 0.0F, -0.7854F, 0.0F));

		PartDefinition flower_back_alt = plant_back.addOrReplaceChild("flower_back_alt", CubeListBuilder.create().texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.0F, -5.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition plant_block_back = plant_back.addOrReplaceChild("plant_block_back", CubeListBuilder.create().texOffs(12, 32).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 52).addBox(-3.1561F, -6.9333F, -2.912F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -13.0F, -6.5F, 0.2399F, -0.4253F, -0.1006F));

		PartDefinition plant_deco_back = plant_block_back.addOrReplaceChild("plant_deco_back", CubeListBuilder.create().texOffs(13, 34).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 34).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 34).addBox(2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 34).addBox(-3.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(-2.0F, -1.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(2.0F, -2.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(2.0F, 3.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(-2.0F, 3.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition plant4 = body.addOrReplaceChild("plant4", CubeListBuilder.create().texOffs(64, 8).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 8).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -0.5F, 0.0F, -1.5708F, 0.0F, -0.7854F));

		PartDefinition bone5 = plant4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -15.5F, -6.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition flower_top_back = body.addOrReplaceChild("flower_top_back", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, 10.0F, 0.5F, -1.5708F, 0.0F, 0.0F));

		PartDefinition flower_top_back_a = flower_top_back.addOrReplaceChild("flower_top_back_a", CubeListBuilder.create().texOffs(64, 8).addBox(-1.0F, -27.0F, -3.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 8).addBox(-1.0F, -27.0F, -3.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 9.0F, -2.5F));

		PartDefinition flower_top_back_b = flower_top_back.addOrReplaceChild("flower_top_back_b", CubeListBuilder.create().texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -14.0F, -5.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 0).mirror().addBox(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 0).mirror().addBox(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-6.0F, -2.0F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.0F, -8.0F));

		PartDefinition head_bamboo = head.addOrReplaceChild("head_bamboo", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-1.5F, -32.0F, -12.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 20.0F, 8.0F));

		PartDefinition head_sub_1 = head.addOrReplaceChild("head_sub_1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(4.0F, -42.0F, -21.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(53, 10).mirror().addBox(-2.0F, -38.0F, -22.5F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 40.0F, 16.0F));

		PartDefinition fungus = head.addOrReplaceChild("fungus", CubeListBuilder.create().texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(64, 8).addBox(-5.0F, -12.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, -3.0F, 0.0F, -1.1345F, 0.0F));

		PartDefinition flower1 = fungus.addOrReplaceChild("flower1", CubeListBuilder.create().texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition flower_top1 = fungus.addOrReplaceChild("flower_top1", CubeListBuilder.create().texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition top_plant = fungus.addOrReplaceChild("top_plant", CubeListBuilder.create().texOffs(12, 32).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 52).addBox(-2.7653F, -9.348F, -2.8967F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.1922F, -0.4293F, -0.0808F));

		PartDefinition cactus_parts = top_plant.addOrReplaceChild("cactus_parts", CubeListBuilder.create().texOffs(13, 34).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 34).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 34).addBox(2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 34).addBox(-3.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(-2.0F, -1.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(2.0F, -2.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(2.0F, 3.0F, 2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(13, 33).addBox(-2.0F, 3.0F, -3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = partdefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 12.0F, 7.0F));

		PartDefinition right_hind_leg = partdefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 12.0F, 7.0F));

		PartDefinition left_front_leg = partdefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 12.0F, -6.0F));

		PartDefinition right_front_leg = partdefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 12.0F, -6.0F));

		return LayerDefinition.create(meshdefinition, 96, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, buffer, packedLight, packedOverlay);
		head.render(poseStack, buffer, packedLight, packedOverlay);
		left_hind_leg.render(poseStack, buffer, packedLight, packedOverlay);
		right_hind_leg.render(poseStack, buffer, packedLight, packedOverlay);
		left_front_leg.render(poseStack, buffer, packedLight, packedOverlay);
		right_front_leg.render(poseStack, buffer, packedLight, packedOverlay);
	}
}