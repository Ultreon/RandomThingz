package com.qsoftware.forgemod.modules.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.CoreModule;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.util.NetworkUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class UserInterfaceModule extends CoreModule {
    private static final ResourceLocation ONLINE_STATUS_OVERLAY = new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/hud/online_status_overlay.png");
    private static final ResourceLocation ONLINE_STATUS_OFFLINE = new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/hud/online_status_offline.png");
    private static final ResourceLocation ONLINE_STATUS_ONLINE = new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/hud/online_status_online.png");
    private static final ResourceLocation ONLINE_STATUS_PINGING = new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/hud/online_status_pinging.png");
    private Minecraft minecraft;

    // Ping flags.
    private boolean pinged = false;
    private boolean online = false;

    // Threads.
    private Thread pingThread;
    private boolean pinging = false;
    private int ticks;
    private int animTicks;

    @Override
    public void onEnable() {
//        if (QForgeMod.isClientSide()) {
//            MinecraftForge.EVENT_BUS.register(this);
//        }
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void onDisable() {
//        if (QForgeMod.isClientSide()) {
//            MinecraftForge.EVENT_BUS.unregister(this);
//        }
    }

//    @SubscribeEvent
    public void onMainMenuInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof MainMenuScreen) {
            this.pinging = true;
            this.pingThread = new Thread(this::ping, "AuthPing");
            this.pingThread.start();
            this.ticks = 0;
        }
    }

    private void ping() {
        this.online = NetworkUtils.ping();
        this.pinged = true;

        this.pinging = false;
    }

//    @SubscribeEvent
    public void onMainMenuRender(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (event.getGui() instanceof MainMenuScreen) {
            this.renderMainMenu(event.getMatrixStack(), event.getMouseX(), event.getMouseY(), event.getRenderPartialTicks());
        }
    }

    private void renderMainMenu(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.pinged) {
            if (this.online) {
                this.minecraft.getTextureManager().bindTexture(ONLINE_STATUS_ONLINE);
                this.minecraft.fontRenderer.drawStringWithShadow(matrixStack, "Online", 27f, 7f, 0xff_ffffff);
            } else {
                this.minecraft.getTextureManager().bindTexture(ONLINE_STATUS_OFFLINE);
                this.minecraft.fontRenderer.drawStringWithShadow(matrixStack, "Offline", 27f, 7f, 0xff_ffffff);
            }
            Screen.blit(matrixStack, 0, 0, 24, 24, 0, 0, 24, 24, 24, 24);
        } else {
            this.minecraft.getTextureManager().bindTexture(ONLINE_STATUS_PINGING);
            this.minecraft.fontRenderer.drawStringWithShadow(matrixStack, "Pinging...", 27f, 7f, 0xff_ffffff);
            Screen.blit(matrixStack, 0, 0, 24, 24, 0, 16 * animTicks, 24, 24, 24, 24);
        }

        this.minecraft.getTextureManager().bindTexture(ONLINE_STATUS_OVERLAY);
        Screen.blit(matrixStack, 0, 0, 24, 24, 0, 0, 24, 24, 24, 24);
    }

//    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        this.ticks++;
        if (ticks % 5 == 0) {
            if (this.animTicks >= 9) {
                this.animTicks = 0;
            } else {
                this.animTicks++;
            }
        }
    }

    @Override
    public @NotNull String getName() {
        return "user_interface";
    }

    public DeferredRegister<ContainerType<?>> getDeferredRegister() {
        return Registration.CONTAINERS;
    }

    public Collection<RegistryObject<ContainerType<?>>> getContainers() {
        return Registration.CONTAINERS.getEntries();
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }

    public boolean isPinged() {
        return pinged;
    }

    public boolean isOnline() {
        return online;
    }

    public Thread getPingThread() {
        return pingThread;
    }

    public boolean isPinging() {
        return pinging;
    }
}
