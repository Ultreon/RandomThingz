package com.ultreon.texturedmodels.setup;

import com.ultreon.texturedmodels.QTextureModels;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is used to register textures to the Texture Atlas (texture stitching)
 *
 * @author PianoManu
 * @version 1.2 09/30/20
 */
@Mod.EventBusSubscriber(modid = QTextureModels.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TextureSetup {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onStitchEvent(TextureStitchEvent.Pre event) {
        ResourceLocation stitching = event.getMap().location();
        if (stitching.equals(TextureAtlas.LOCATION_BLOCKS)) {
            LOGGER.info("Stitching textures from QTextureModels");
            event.addSprite(loc("block/chest_front"));
            event.addSprite(loc("block/chest_side"));
            event.addSprite(loc("block/chest_top"));
            event.addSprite(loc("block/grass_block_snow_overlay"));
            event.addSprite(loc("block/grass_block_snow_overlay_small"));
            event.addSprite(loc("block/grass_block_side_overlay_large"));
            event.addSprite(loc("block/stone_brick_overlay"));
            event.addSprite(loc("block/brick_overlay"));
            event.addSprite(loc("block/chiseled_sandstone_overlay"));
            event.addSprite(loc("block/boundary_overlay"));
            event.addSprite(loc("block/chiseled_stone_overlay"));
            LOGGER.info("Stitched all textures from QTextureModels");
        }
    }

    private static ResourceLocation loc(String name) {
        return new ResourceLocation(QTextureModels.MOD_ID, name);
    }
}
