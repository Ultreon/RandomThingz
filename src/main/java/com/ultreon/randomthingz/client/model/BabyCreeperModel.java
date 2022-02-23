package com.ultreon.randomthingz.client.model;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.entity.baby.BabyCreeper;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BabyCreeperModel<T extends BabyCreeper> extends AgeableListModel<T> {
   // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RandomThingz.MOD_ID, "baby_creeper_model"), "main");

   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart body;
   private final ModelPart rightHindLeg;
   private final ModelPart leftHindLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;

   public BabyCreeperModel(ModelPart p_170524_) {
      this.root = p_170524_;
      this.head = p_170524_.getChild("head");
      this.body = p_170524_.getChild("body");
      this.leftHindLeg = p_170524_.getChild("right_hind_leg");
      this.rightHindLeg = p_170524_.getChild("left_hind_leg");
      this.leftFrontLeg = p_170524_.getChild("right_front_leg");
      this.rightFrontLeg = p_170524_.getChild("left_front_leg");
   }

   public static LayerDefinition createBodyLayer(CubeDeformation p_170526_) {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4f, -8f, -4f, 8f, 8f, 8f, p_170526_), PartPose.offset(0f, 6f, 0f));
      partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4f, 0f, -2f, 8f, 12f, 4f, p_170526_), PartPose.offset(0f, 6f, 0f));
      CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(0, 16).addBox(-2f, 0f, -2f, 4f, 6f, 4f, p_170526_);
      partdefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-2f, 18f, 4f));
      partdefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder, PartPose.offset(2f, 18f, 4f));
      partdefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-2f, 18f, -4f));
      partdefinition.addOrReplaceChild("left_front_leg", cubelistbuilder, PartPose.offset(2f, 18f, -4f));
      return LayerDefinition.create(meshdefinition, 64, 32);
   }

   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(@NotNull T entity, float p_102464_, float p_102465_, float p_102466_, float p_102467_, float p_102468_) {
      this.head.yRot = p_102467_ * ((float)Math.PI / 180f);
      this.head.xRot = p_102468_ * ((float)Math.PI / 180f);
      this.rightHindLeg.xRot = Mth.cos(p_102464_ * .6662f) * 1.4F * p_102465_;
      this.leftHindLeg.xRot = Mth.cos(p_102464_ * .6662f + (float)Math.PI) * 1.4F * p_102465_;
      this.rightFrontLeg.xRot = Mth.cos(p_102464_ * .6662f + (float)Math.PI) * 1.4F * p_102465_;
      this.leftFrontLeg.xRot = Mth.cos(p_102464_ * .6662f) * 1.4F * p_102465_;
   }

   @Override
   protected @NotNull Iterable<ModelPart> headParts() {
      return List.of(head);
   }

   @Override
   protected @NotNull Iterable<ModelPart> bodyParts() {
      return List.of(body, leftFrontLeg, rightFrontLeg, leftHindLeg, rightHindLeg);
   }

   public static LayerDefinition createBodyLayer() {
      return createBodyLayer(CubeDeformation.NONE);
   }
}