package com.ultreon.randomthingz.client.model;
/*
 Made with Blockbench 3.7.4
 Exported for Minecraft version 1.15
 Paste this class into your mod and generate all required imports
 */


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ultreon.randomthingz.entity.MoobloomEntity;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

/**
 * Moobloom Model
 *
 * @param <T> the moobloom entity.
 * @author Blockbench, Qboi123
 */
@SuppressWarnings({"FieldCanBeLocal", "DuplicatedCode"})
public class MoobloomModel<T extends MoobloomEntity> extends AgeableListModel<T> {
    private final ModelPart body;
    private final ModelPart fungus2;
    private final ModelPart bone7;
    private final ModelPart bone2;
    private final ModelPart cactus2;
    private final ModelPart bone10;
    private final ModelPart fungus3;
    private final ModelPart bone8;
    private final ModelPart bone3;
    private final ModelPart cactus3;
    private final ModelPart bone11;
    private final ModelPart fungus4;
    private final ModelPart bone5;
    private final ModelPart fungus5;
    private final ModelPart bone6;
    private final ModelPart head;
    private final ModelPart head_sub_0;
    private final ModelPart head_sub_1;
    private final ModelPart fungus;
    private final ModelPart bone;
    private final ModelPart bone4;
    private final ModelPart cactus;
    private final ModelPart bone9;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public MoobloomModel() {
        texWidth = 96;
        texHeight = 64;

        body = new ModelPart(this);
        body.setPos(0.0F, 5.0F, 2.0F);
        setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
        body.texOffs(18, 4).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, 0.0F, true);
        body.texOffs(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, 0.0F, true);

        fungus2 = new ModelPart(this);
        fungus2.setPos(4.0F, -0.5F, -8.0F);
        body.addChild(fungus2);
        setRotationAngle(fungus2, -1.5708F, 0.0F, -0.7854F);
        fungus2.texOffs(64, 0).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus2.texOffs(64, 0).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone7 = new ModelPart(this);
        bone7.setPos(-2.2929F, -4.5F, -7.1213F);
        fungus2.addChild(bone7);
        setRotationAngle(bone7, 0.0F, -0.7854F, 0.0F);
        bone7.texOffs(0, 32).addBox(0.3284F, -14.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        bone2 = new ModelPart(this);
        bone2.setPos(-1.0F, -15.5F, -6.0F);
        fungus2.addChild(bone2);
        setRotationAngle(bone2, 0.0F, 1.5708F, 0.0F);
        bone2.texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        bone2.texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        cactus2 = new ModelPart(this);
        cactus2.setPos(-1.8787F, -14.5F, -7.1213F);
        fungus2.addChild(cactus2);
        setRotationAngle(cactus2, 0.0F, -0.7854F, -0.2182F);
        cactus2.texOffs(12, 32).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
        cactus2.texOffs(12, 32).addBox(-2.0919F, -8.9001F, -2.0081F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        cactus2.texOffs(24, 52).addBox(-3.4845F, -13.7441F, -3.0155F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        bone10 = new ModelPart(this);
        bone10.setPos(0.0F, 0.0F, 0.0F);
        cactus2.addChild(bone10);
        bone10.texOffs(13, 34).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone10.texOffs(13, 34).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone10.texOffs(13, 34).addBox(2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone10.texOffs(13, 34).addBox(-3.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone10.texOffs(13, 33).addBox(-2.0F, -1.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone10.texOffs(13, 33).addBox(2.0F, -2.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone10.texOffs(13, 33).addBox(2.0F, 3.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone10.texOffs(13, 33).addBox(-2.0F, 3.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        fungus3 = new ModelPart(this);
        fungus3.setPos(4.0F, 10.0F, -7.5F);
        body.addChild(fungus3);
        setRotationAngle(fungus3, -1.5708F, 0.0F, 0.0F);
        fungus3.texOffs(64, 0).addBox(-5.0F, -18.0F, -5.5F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus3.texOffs(64, 0).addBox(-5.0F, -18.0F, -5.5F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone8 = new ModelPart(this);
        bone8.setPos(-2.2929F, -15.0F, -7.6213F);
        fungus3.addChild(bone8);
        setRotationAngle(bone8, 0.0F, -0.7854F, 0.0F);
        bone8.texOffs(0, 32).addBox(1.7071F, -3.0F, -2.1213F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        bone3 = new ModelPart(this);
        bone3.setPos(0.0F, -14.0F, -5.5F);
        fungus3.addChild(bone3);
        setRotationAngle(bone3, 0.0F, 1.5708F, 0.0F);
        bone3.texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        bone3.texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        cactus3 = new ModelPart(this);
        cactus3.setPos(-1.0F, -13.0F, -6.5F);
        fungus3.addChild(cactus3);
        setRotationAngle(cactus3, 0.2399F, -0.4253F, -0.1006F);
        cactus3.texOffs(12, 32).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
        cactus3.texOffs(24, 52).addBox(-3.4061F, -6.9333F, -2.662F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        bone11 = new ModelPart(this);
        bone11.setPos(0.0F, 0.0F, 0.0F);
        cactus3.addChild(bone11);
        bone11.texOffs(13, 34).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone11.texOffs(13, 34).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone11.texOffs(13, 34).addBox(2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone11.texOffs(13, 34).addBox(-3.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone11.texOffs(13, 33).addBox(-2.0F, -1.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone11.texOffs(13, 33).addBox(2.0F, -2.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone11.texOffs(13, 33).addBox(2.0F, 3.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone11.texOffs(13, 33).addBox(-2.0F, 3.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        fungus4 = new ModelPart(this);
        fungus4.setPos(4.0F, -0.5F, 0.0F);
        body.addChild(fungus4);
        setRotationAngle(fungus4, -1.5708F, 0.0F, -0.7854F);
        fungus4.texOffs(64, 8).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus4.texOffs(64, 8).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone5 = new ModelPart(this);
        bone5.setPos(-1.0F, -15.5F, -6.0F);
        fungus4.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 1.5708F, 0.0F);
        bone5.texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        bone5.texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        fungus5 = new ModelPart(this);
        fungus5.setPos(4.0F, 10.0F, 0.5F);
        body.addChild(fungus5);
        setRotationAngle(fungus5, -1.5708F, 0.0F, 0.0F);
        fungus5.texOffs(64, 8).addBox(-5.0F, -18.0F, -5.5F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus5.texOffs(64, 8).addBox(-5.0F, -18.0F, -5.5F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone6 = new ModelPart(this);
        bone6.setPos(0.0F, -14.0F, -5.5F);
        fungus5.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 1.5708F, 0.0F);
        bone6.texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        bone6.texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        head = new ModelPart(this);
        head.setPos(0.0F, 4.0F, -8.0F);
        head.texOffs(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, 0.0F, true);
        head.texOffs(22, 0).addBox(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F, true);
        head.texOffs(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F, true);
        head.texOffs(0, 0).addBox(-6.0F, -2.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, true);

        head_sub_0 = new ModelPart(this);
        head_sub_0.setPos(0.0F, 20.0F, 8.0F);
        head.addChild(head_sub_0);
        head_sub_0.texOffs(0, 32).addBox(-1.5F, -32.0F, -12.5F, 3.0F, 8.0F, 3.0F, 0.0F, true);

        head_sub_1 = new ModelPart(this);
        head_sub_1.setPos(0.0F, 40.0F, 16.0F);
        head.addChild(head_sub_1);
        head_sub_1.texOffs(0, 0).addBox(4.0F, -42.0F, -21.0F, 2.0F, 2.0F, 1.0F, 0.0F, true);
        head_sub_1.texOffs(53, 10).addBox(-2.0F, -38.0F, -22.5F, 4.0F, 2.0F, 1.0F, 0.0F, true);

        fungus = new ModelPart(this);
        fungus.setPos(0.0F, -8.0F, -3.0F);
        head.addChild(fungus);
        setRotationAngle(fungus, 0.0F, -1.1345F, 0.0F);
        fungus.texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus.texOffs(64, 8).addBox(-5.0F, -12.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone = new ModelPart(this);
        bone.setPos(0.0F, 0.0F, 0.0F);
        fungus.addChild(bone);
        setRotationAngle(bone, 0.0F, 1.5708F, 0.0F);
        bone.texOffs(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone4 = new ModelPart(this);
        bone4.setPos(0.0F, -8.0F, 0.0F);
        fungus.addChild(bone4);
        setRotationAngle(bone4, 0.0F, 1.5708F, 0.0F);
        bone4.texOffs(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        cactus = new ModelPart(this);
        cactus.setPos(0.0F, 1.0F, 0.0F);
        fungus.addChild(cactus);
        setRotationAngle(cactus, 0.1922F, -0.4293F, -0.0808F);
        cactus.texOffs(12, 32).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
        cactus.texOffs(0, 52).addBox(-2.7653F, -9.348F, -3.3967F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        bone9 = new ModelPart(this);
        bone9.setPos(0.0F, 0.0F, 0.0F);
        cactus.addChild(bone9);
        bone9.texOffs(13, 34).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone9.texOffs(13, 34).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone9.texOffs(13, 34).addBox(2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone9.texOffs(13, 34).addBox(-3.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone9.texOffs(13, 33).addBox(-2.0F, -1.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone9.texOffs(13, 33).addBox(2.0F, -2.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone9.texOffs(13, 33).addBox(2.0F, 3.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone9.texOffs(13, 33).addBox(-2.0F, 3.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        leg1 = new ModelPart(this);
        leg1.setPos(4.0F, 12.0F, 7.0F);
        leg1.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg2 = new ModelPart(this);
        leg2.setPos(-4.0F, 12.0F, 7.0F);
        leg2.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg3 = new ModelPart(this);
        leg3.setPos(4.0F, 12.0F, -6.0F);
        leg3.texOffs(0, 16).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg4 = new ModelPart(this);
        leg4.setPos(-4.0F, 12.0F, -6.0F);
        leg4.texOffs(0, 16).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
    }

    @Override
    protected @NotNull
    Iterable<ModelPart> headParts() {
        return ImmutableList.of(head, head_sub_0, head_sub_1, fungus, bone, bone4, cactus, bone9);
    }

    @Override
    protected @NotNull
    Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(body,
                bone2, bone3, bone5, bone6, bone7, bone8, bone9, bone10, bone11,
                fungus2, fungus3, fungus4, fungus5,
                leg1, leg2, leg3, leg4,
                cactus2, cactus3);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack matrixStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        leg1.render(matrixStack, buffer, packedLight, packedOverlay);
        leg2.render(matrixStack, buffer, packedLight, packedOverlay);
        leg3.render(matrixStack, buffer, packedLight, packedOverlay);
        leg4.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
