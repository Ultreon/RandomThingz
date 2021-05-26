package com.qtech.randomthingz.client.renderer.layers;

import com.qtech.randomthingz.client.model.FreeEndermanModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Free enderman eyes layer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class CustomEndermanEyesLayer<T extends EndermanEntity> extends AbstractEyesLayer<T, FreeEndermanModel<T>> {
    private final RenderType renderType;

    public CustomEndermanEyesLayer(IEntityRenderer<T, FreeEndermanModel<T>> rendererIn, RenderType renderType) {
        super(rendererIn);
        this.renderType = renderType;
    }

    public @NotNull RenderType getRenderType() {
        return renderType;
    }
}