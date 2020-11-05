package com.qsoftware.forgemod.client.renderer;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.objects.entities.MoobloomEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Moobloom entity renderer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class MoobloomRenderer extends MobRenderer<MoobloomEntity, CowModel<MoobloomEntity>> {
   private static final ResourceLocation MOOBLOOM_TEXTURES = new ResourceLocation(QForgeUtils.MOD_ID, "textures/entity/cow/moobloom.png");

   public MoobloomRenderer(EntityRendererManager renderManagerIn) {
      super(renderManagerIn, new CowModel<>(), 0.7F);
      this.addLayer(new MoobloomFlowerLayer<>(this));
   }

   /**
    * Returns the location of an entity's texture.
    */
   public @NotNull ResourceLocation getEntityTexture(@NotNull MoobloomEntity entity) {
      return MOOBLOOM_TEXTURES;
   }

}
