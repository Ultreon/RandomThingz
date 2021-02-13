package com.qsoftware.forgemod.modules.client.modules.render.variant;

import com.qsoftware.forgemod.modules.client.modules.MobVariantsModule;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VariantCreeperRenderer extends CreeperRenderer {
	public VariantCreeperRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getEntityTexture(CreeperEntity entity) {
		return MobVariantsModule.getTextureOrShiny(entity, MobVariantsModule.VariantTextureType.CREEPER, MobVariantsModule.enableCreeper);
	}
	
}
