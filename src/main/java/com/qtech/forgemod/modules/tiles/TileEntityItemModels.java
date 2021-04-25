package com.qtech.forgemod.modules.tiles;

import com.qtech.forgemod.QForgeMod;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QForgeMod.modId, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityItemModels {

  public static final ResourceLocation CHRISTMAS_CHEST_LOCATION = new ResourceLocation("minecraft", "entity/chest/christmas");

//  public static ResourceLocation chooseChestTexture(IronChestsTypes type) {
//    switch (type) {
//      case IRON:
//        return IRON_CHEST_LOCATION;
//      default:
//        return VANLLA_CHEST_LOCATION;
//    }
//  }

  @SubscribeEvent
  public static void onStitch(TextureStitchEvent.Pre event) {
    if (!event.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS)) {
      return;
    }

//    event.addSprite(IRON_CHEST_LOCATION);
  }
}
