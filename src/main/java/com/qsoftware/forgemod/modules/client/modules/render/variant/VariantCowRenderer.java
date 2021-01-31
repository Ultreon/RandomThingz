package com.qsoftware.forgemod.modules.client.modules.render.variant;

import com.qsoftware.forgemod.modules.client.modules.MobVariantsModule;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.ResourceLocation;

public class VariantCowRenderer extends CowRenderer {

	public VariantCowRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	public ResourceLocation getEntityTexture(CowEntity entity) {
		return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.COW, MobVariantsModule.enableCow);
	}
	
}
