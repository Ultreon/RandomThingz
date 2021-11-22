package com.ultreon.randomthingz.client.model;
/*
 Made with Blockbench 3.7.4
 Exported for Minecraft version 1.15
 Paste this class into your mod and generate all required imports
 */


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.ultreon.randomthingz.entity.MoobloomEntity;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

/**
 * Moobloom Model
 *
 * @param <T> the moobloom entity.
 * @author Blockbench, Qboi123
 */
@SuppressWarnings({"FieldCanBeLocal", "DuplicatedCode"})
public class MoobloomModel<T extends MoobloomEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer fungus2;
    private final ModelRenderer bone7;
    private final ModelRenderer bone2;
    private final ModelRenderer cactus2;
    private final ModelRenderer bone10;
    private final ModelRenderer fungus3;
    private final ModelRenderer bone8;
    private final ModelRenderer bone3;
    private final ModelRenderer cactus3;
    private final ModelRenderer bone11;
    private final ModelRenderer fungus4;
    private final ModelRenderer bone5;
    private final ModelRenderer fungus5;
    private final ModelRenderer bone6;
    private final ModelRenderer head;
    private final ModelRenderer head_sub_0;
    private final ModelRenderer head_sub_1;
    private final ModelRenderer fungus;
    private final ModelRenderer bone;
    private final ModelRenderer bone4;
    private final ModelRenderer cactus;
    private final ModelRenderer bone9;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;

    public MoobloomModel() {
        textureWidth = 96;
        textureHeight = 64;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 5.0F, 2.0F);
        setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
        body.setTextureOffset(18, 4).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, 0.0F, true);
        body.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4.0F, 6.0F, 1.0F, 0.0F, true);

        fungus2 = new ModelRenderer(this);
        fungus2.setRotationPoint(4.0F, -0.5F, -8.0F);
        body.addChild(fungus2);
        setRotationAngle(fungus2, -1.5708F, 0.0F, -0.7854F);
        fungus2.setTextureOffset(64, 0).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus2.setTextureOffset(64, 0).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(-2.2929F, -4.5F, -7.1213F);
        fungus2.addChild(bone7);
        setRotationAngle(bone7, 0.0F, -0.7854F, 0.0F);
        bone7.setTextureOffset(0, 32).addBox(0.3284F, -14.0F, -1.5F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(-1.0F, -15.5F, -6.0F);
        fungus2.addChild(bone2);
        setRotationAngle(bone2, 0.0F, 1.5708F, 0.0F);
        bone2.setTextureOffset(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        bone2.setTextureOffset(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        cactus2 = new ModelRenderer(this);
        cactus2.setRotationPoint(-1.8787F, -14.5F, -7.1213F);
        fungus2.addChild(cactus2);
        setRotationAngle(cactus2, 0.0F, -0.7854F, -0.2182F);
        cactus2.setTextureOffset(12, 32).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
        cactus2.setTextureOffset(12, 32).addBox(-2.0919F, -8.9001F, -2.0081F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        cactus2.setTextureOffset(24, 52).addBox(-3.4845F, -13.7441F, -3.0155F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        bone10 = new ModelRenderer(this);
        bone10.setRotationPoint(0.0F, 0.0F, 0.0F);
        cactus2.addChild(bone10);
        bone10.setTextureOffset(13, 34).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone10.setTextureOffset(13, 34).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone10.setTextureOffset(13, 34).addBox(2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone10.setTextureOffset(13, 34).addBox(-3.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone10.setTextureOffset(13, 33).addBox(-2.0F, -1.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone10.setTextureOffset(13, 33).addBox(2.0F, -2.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone10.setTextureOffset(13, 33).addBox(2.0F, 3.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone10.setTextureOffset(13, 33).addBox(-2.0F, 3.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        fungus3 = new ModelRenderer(this);
        fungus3.setRotationPoint(4.0F, 10.0F, -7.5F);
        body.addChild(fungus3);
        setRotationAngle(fungus3, -1.5708F, 0.0F, 0.0F);
        fungus3.setTextureOffset(64, 0).addBox(-5.0F, -18.0F, -5.5F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus3.setTextureOffset(64, 0).addBox(-5.0F, -18.0F, -5.5F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(-2.2929F, -15.0F, -7.6213F);
        fungus3.addChild(bone8);
        setRotationAngle(bone8, 0.0F, -0.7854F, 0.0F);
        bone8.setTextureOffset(0, 32).addBox(1.7071F, -3.0F, -2.1213F, 3.0F, 8.0F, 3.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, -14.0F, -5.5F);
        fungus3.addChild(bone3);
        setRotationAngle(bone3, 0.0F, 1.5708F, 0.0F);
        bone3.setTextureOffset(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        bone3.setTextureOffset(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        cactus3 = new ModelRenderer(this);
        cactus3.setRotationPoint(-1.0F, -13.0F, -6.5F);
        fungus3.addChild(cactus3);
        setRotationAngle(cactus3, 0.2399F, -0.4253F, -0.1006F);
        cactus3.setTextureOffset(12, 32).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
        cactus3.setTextureOffset(24, 52).addBox(-3.4061F, -6.9333F, -2.662F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        bone11 = new ModelRenderer(this);
        bone11.setRotationPoint(0.0F, 0.0F, 0.0F);
        cactus3.addChild(bone11);
        bone11.setTextureOffset(13, 34).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone11.setTextureOffset(13, 34).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone11.setTextureOffset(13, 34).addBox(2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone11.setTextureOffset(13, 34).addBox(-3.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone11.setTextureOffset(13, 33).addBox(-2.0F, -1.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone11.setTextureOffset(13, 33).addBox(2.0F, -2.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone11.setTextureOffset(13, 33).addBox(2.0F, 3.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone11.setTextureOffset(13, 33).addBox(-2.0F, 3.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        fungus4 = new ModelRenderer(this);
        fungus4.setRotationPoint(4.0F, -0.5F, 0.0F);
        body.addChild(fungus4);
        setRotationAngle(fungus4, -1.5708F, 0.0F, -0.7854F);
        fungus4.setTextureOffset(64, 8).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus4.setTextureOffset(64, 8).addBox(-6.0F, -19.5F, -6.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(-1.0F, -15.5F, -6.0F);
        fungus4.addChild(bone5);
        setRotationAngle(bone5, 0.0F, 1.5708F, 0.0F);
        bone5.setTextureOffset(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        bone5.setTextureOffset(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        fungus5 = new ModelRenderer(this);
        fungus5.setRotationPoint(4.0F, 10.0F, 0.5F);
        body.addChild(fungus5);
        setRotationAngle(fungus5, -1.5708F, 0.0F, 0.0F);
        fungus5.setTextureOffset(64, 8).addBox(-5.0F, -18.0F, -5.5F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus5.setTextureOffset(64, 8).addBox(-5.0F, -18.0F, -5.5F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(0.0F, -14.0F, -5.5F);
        fungus5.addChild(bone6);
        setRotationAngle(bone6, 0.0F, 1.5708F, 0.0F);
        bone6.setTextureOffset(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        bone6.setTextureOffset(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 4.0F, -8.0F);
        head.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 8.0F, 6.0F, 0.0F, true);
        head.setTextureOffset(22, 0).addBox(4.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F, true);
        head.setTextureOffset(22, 0).addBox(-5.0F, -5.0F, -4.0F, 1.0F, 3.0F, 1.0F, 0.0F, true);
        head.setTextureOffset(0, 0).addBox(-6.0F, -2.0F, -5.0F, 2.0F, 2.0F, 1.0F, 0.0F, true);

        head_sub_0 = new ModelRenderer(this);
        head_sub_0.setRotationPoint(0.0F, 20.0F, 8.0F);
        head.addChild(head_sub_0);
        head_sub_0.setTextureOffset(0, 32).addBox(-1.5F, -32.0F, -12.5F, 3.0F, 8.0F, 3.0F, 0.0F, true);

        head_sub_1 = new ModelRenderer(this);
        head_sub_1.setRotationPoint(0.0F, 40.0F, 16.0F);
        head.addChild(head_sub_1);
        head_sub_1.setTextureOffset(0, 0).addBox(4.0F, -42.0F, -21.0F, 2.0F, 2.0F, 1.0F, 0.0F, true);
        head_sub_1.setTextureOffset(53, 10).addBox(-2.0F, -38.0F, -22.5F, 4.0F, 2.0F, 1.0F, 0.0F, true);

        fungus = new ModelRenderer(this);
        fungus.setRotationPoint(0.0F, -8.0F, -3.0F);
        head.addChild(fungus);
        setRotationAngle(fungus, 0.0F, -1.1345F, 0.0F);
        fungus.setTextureOffset(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);
        fungus.setTextureOffset(64, 8).addBox(-5.0F, -12.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 0.0F, 0.0F);
        fungus.addChild(bone);
        setRotationAngle(bone, 0.0F, 1.5708F, 0.0F);
        bone.setTextureOffset(64, 0).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(0.0F, -8.0F, 0.0F);
        fungus.addChild(bone4);
        setRotationAngle(bone4, 0.0F, 1.5708F, 0.0F);
        bone4.setTextureOffset(64, 8).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, 0.0F, false);

        cactus = new ModelRenderer(this);
        cactus.setRotationPoint(0.0F, 1.0F, 0.0F);
        fungus.addChild(cactus);
        setRotationAngle(cactus, 0.1922F, -0.4293F, -0.0808F);
        cactus.setTextureOffset(12, 32).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.0F, false);
        cactus.setTextureOffset(0, 52).addBox(-2.7653F, -9.348F, -3.3967F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        bone9 = new ModelRenderer(this);
        bone9.setRotationPoint(0.0F, 0.0F, 0.0F);
        cactus.addChild(bone9);
        bone9.setTextureOffset(13, 34).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone9.setTextureOffset(13, 34).addBox(2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone9.setTextureOffset(13, 34).addBox(2.0F, 2.0F, -2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone9.setTextureOffset(13, 34).addBox(-3.0F, 1.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
        bone9.setTextureOffset(13, 33).addBox(-2.0F, -1.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone9.setTextureOffset(13, 33).addBox(2.0F, -2.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone9.setTextureOffset(13, 33).addBox(2.0F, 3.0F, 2.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
        bone9.setTextureOffset(13, 33).addBox(-2.0F, 3.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);

        leg1 = new ModelRenderer(this);
        leg1.setRotationPoint(4.0F, 12.0F, 7.0F);
        leg1.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg2 = new ModelRenderer(this);
        leg2.setRotationPoint(-4.0F, 12.0F, 7.0F);
        leg2.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg3 = new ModelRenderer(this);
        leg3.setRotationPoint(4.0F, 12.0F, -6.0F);
        leg3.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        leg4 = new ModelRenderer(this);
        leg4.setRotationPoint(-4.0F, 12.0F, -6.0F);
        leg4.setTextureOffset(0, 16).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);
    }

    @Override
    protected @NotNull
    Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(head, head_sub_0, head_sub_1, fungus, bone, bone4, cactus, bone9);
    }

    @Override
    protected @NotNull
    Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(body,
                bone2, bone3, bone5, bone6, bone7, bone8, bone9, bone10, bone11,
                fungus2, fungus3, fungus4, fungus5,
                leg1, leg2, leg3, leg4,
                cactus2, cactus3);
    }

    @Override
    public void setRotationAngles(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, @NotNull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        leg1.render(matrixStack, buffer, packedLight, packedOverlay);
        leg2.render(matrixStack, buffer, packedLight, packedOverlay);
        leg3.render(matrixStack, buffer, packedLight, packedOverlay);
        leg4.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
