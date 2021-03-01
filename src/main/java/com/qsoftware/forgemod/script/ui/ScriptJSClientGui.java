package com.qsoftware.forgemod.script.ui;

import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class ScriptJSClientGui extends AbstractScriptJSGui {
   @Getter
   private static final ScriptJSClientGui instance = new ScriptJSClientGui(Minecraft.getInstance());

   public ScriptJSClientGui(Minecraft mcIn) {
      super(mcIn);
   }
}
