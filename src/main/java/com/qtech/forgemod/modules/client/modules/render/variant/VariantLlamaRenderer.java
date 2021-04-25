package com.qtech.forgemod.modules.client.modules.render.variant;

import com.qtech.forgemod.modules.client.modules.MobVariantsModule;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.util.ResourceLocation;

public class VariantLlamaRenderer extends LlamaRenderer {

	public VariantLlamaRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getEntityTexture(LlamaEntity entity) {
		return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.LLAMA, () -> super.getEntityTexture(entity));
	}
	
}
