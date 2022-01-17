package com.ultreon.texturedmodels.setup;

import com.ultreon.texturedmodels.client.ChestFrameScreen;
import com.ultreon.texturedmodels.client.IllusionChestScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

/**
 * Registering standard rendering layers for all frame blocks - need to be translucent to work with glass and similar blocks
 *
 * @author PianoManu
 * @version 1.5 09/24/20
 */
public class RenderSetup {
    public static void setup() {
        ItemBlockRenderTypes.setRenderLayer(Registration.FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.SLAB_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.STAIRS_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.FENCE_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.DOOR_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.TRAPDOOR_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.PRESSURE_PLATE_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.BUTTON_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.BED_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.WALL_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.LADDER_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.CHEST_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.FENCE_GATE_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.EDGED_SLOPE_FRAMEBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.SLOPE_FRAMEBLOCK.get(), RenderType.translucent());

        ItemBlockRenderTypes.setRenderLayer(Registration.ILLUSION_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.SLAB_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.STAIRS_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.FENCE_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.DOOR_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.TRAPDOOR_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.PRESSURE_PLATE_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.BUTTON_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.BED_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.WALL_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.LADDER_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.CHEST_ILLUSIONBLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(Registration.FENCE_GATE_ILLUSIONBLOCK.get(), RenderType.translucent());


        MenuScreens.register(Registration.CHEST_FRAME_CONTAINER.get(), ChestFrameScreen::new);
        MenuScreens.register(Registration.CHEST_ILLUSION_CONTAINER.get(), IllusionChestScreen::new);
        //ScreenManager.registerFactory(Registration.STANDING_SIGN_FRAME_CONTAINER.get(), EditSignScreen::new);
    }
}
