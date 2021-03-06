package com.ultreon.randomthingz.block.entity;

import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockEntityItemModels {

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
        if (!event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
            return;
        }

//    event.addSprite(IRON_CHEST_LOCATION);
    }
}
