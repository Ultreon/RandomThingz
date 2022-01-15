package com.ultreon.randomthingz.client.renderer.layers;

import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * Free enderman eyes layer class.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
public class CustomEndermanEyesLayer<T extends EnderMan> extends EyesLayer<T, EndermanModel<T>> {
    private final RenderType renderType;

    public CustomEndermanEyesLayer(RenderLayerParent<T, EndermanModel<T>> rendererIn, RenderType renderType) {
        super(rendererIn);
        this.renderType = renderType;
    }

    public @NotNull RenderType renderType() {
        return renderType;
    }
}