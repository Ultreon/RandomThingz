package com.ultreon.randomthingz.modules.client.modules.render.variant;

import com.ultreon.randomthingz.modules.client.modules.MobVariantsModule;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;

public class VariantChickenRenderer extends ChickenRenderer {

	public VariantChickenRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}
	
	@Override
	public ResourceLocation getEntityTexture(ChickenEntity entity) {
		return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.CHICKEN, MobVariantsModule.enableChicken);
	}
	
}
