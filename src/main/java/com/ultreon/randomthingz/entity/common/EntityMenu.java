package com.ultreon.randomthingz.entity.common;

import com.ultreon.randomthingz.modules.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.modules.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.modules.debugMenu.DebugMenu;
import com.ultreon.randomthingz.util.Targeter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityMenu extends AbstractActionMenu {
    public EntityMenu() {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void client() {
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                ClientWorld dimension = mc.dimension;
                ClientPlayerEntity player = mc.player;
                PlayerController controller = mc.playerController;

                EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
                if (result != null && controller != null) {
                    controller.attackEntity(player, result.getEntity());
                }
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Attack");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                ClientWorld dimension = mc.dimension;
                ClientPlayerEntity player = mc.player;

                EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
                return result != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                Minecraft mc = Minecraft.getInstance();
                ClientWorld dimension = mc.dimension;
                ClientPlayerEntity player = mc.player;
                PlayerController controller = mc.playerController;

                EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
                if (result != null && controller != null) {
                    controller.interactWithEntity(player, result.getEntity(), result, Hand.MAIN_HAND);
                }
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Interact");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                ClientWorld dimension = mc.dimension;
                ClientPlayerEntity player = mc.player;

                EntityRayTraceResult result = Targeter.rayTraceEntities(player, dimension);
                return result != null;
            }
        });
        addClient(new ActionMenuItem() {
            @Override
            public void onActivate() {
                DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.ENTITY;
            }

            @Override
            public ITextComponent getText() {
                return new StringTextComponent("Set Debug Page");
            }

            @Override
            public boolean isEnabled() {
                Minecraft mc = Minecraft.getInstance();
                return mc.player != null && mc.dimension != null && mc.playerController != null;
            }
        });
    }

    @Override
    public void server() {

    }
}
