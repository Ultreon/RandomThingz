package com.qtech.randomthingz.modules.client.modules.render.variant;

import com.qtech.randomthingz.modules.client.modules.MobVariantsModule;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.ResourceLocation;

public class VariantPigRenderer extends PigRenderer {

	public VariantPigRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	public ResourceLocation getEntityTexture(PigEntity entity) {
		return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.PIG, MobVariantsModule.enablePig);
	}

}
