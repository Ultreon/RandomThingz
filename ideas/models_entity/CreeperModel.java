// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports


public class baby_creeper<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "baby_creeper"), "main");
	private final ModelPart body;

	public baby_creeper(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4f, -18f, -2f, 8f, 12f, 4f, new CubeDeformation(0f)), PartPose.offset(0f, 24f, 0f));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4f, -8f, -4f, 8f, 8f, 8f, new CubeDeformation(0f)), PartPose.offset(0f, -18f, 0f));

		PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(0, 16).addBox(-2f, 0f, -2f, 4f, 6f, 4f, new CubeDeformation(0f)), PartPose.offset(-2f, -6f, 4f));

		PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 16).addBox(-2f, 0f, -2f, 4f, 6f, 4f, new CubeDeformation(0f)), PartPose.offset(2f, -6f, 4f));

		PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 16).addBox(-2f, 0f, -2f, 4f, 6f, 4f, new CubeDeformation(0f)), PartPose.offset(-2f, -6f, -4f));

		PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 16).addBox(-2f, 0f, -2f, 4f, 6f, 4f, new CubeDeformation(0f)), PartPose.offset(2f, -6f, -4f));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, buffer, packedLight, packedOverlay);
	}
}