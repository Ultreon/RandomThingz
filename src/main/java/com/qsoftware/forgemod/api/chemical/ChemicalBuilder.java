package com.qsoftware.forgemod.api.chemical;

import com.qsoftware.forgemod.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.forgemod.api.chemical.attribute.ChemicalAttribute;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChemicalBuilder<CHEMICAL extends Chemical<CHEMICAL>, BUILDER extends ChemicalBuilder<CHEMICAL, BUILDER>> {

    private final Map<Class<? extends ChemicalAttribute>, ChemicalAttribute> attributeMap = new Object2ObjectOpenHashMap<>();
    private final ResourceLocation texture;
    private int color = 0xFFFFFF;
    private boolean hidden;

    protected ChemicalBuilder(ResourceLocation texture) {
        this.texture = texture;
    }

    public BUILDER with(ChemicalAttribute attribute) {
        attributeMap.put(attribute.getClass(), attribute);
        return getThis();
    }

    public Map<Class<? extends ChemicalAttribute>, ChemicalAttribute> getAttributeMap() {
        return attributeMap;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public BUILDER color(int color) {
        this.color = color;
        return getThis();
    }

    public BUILDER hidden() {
        this.hidden = true;
        return getThis();
    }

    public int getColor() {
        return color;
    }

    public boolean isHidden() {
        return hidden;
    }

    @SuppressWarnings("unchecked")
    public BUILDER getThis() {
        return (BUILDER) this;
    }
}