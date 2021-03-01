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
public class ScriptJSServerGui extends AbstractScriptJSGui {
   @Getter
   private static final ScriptJSServerGui instance = new ScriptJSServerGui(Minecraft.getInstance());

   private ScriptJSServerGui(Minecraft mcIn) {
      super(mcIn);
   }
}
