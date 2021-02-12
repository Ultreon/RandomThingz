package com.qsoftware.forgemod.modules.ui.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.File;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class Screenshot {
    private final File file;
    @Nullable
    private final DynamicTexture texture;
    @Nullable
    private final ResourceLocation resourceLocation;
}
