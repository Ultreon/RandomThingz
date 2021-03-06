package com.ultreon.randomthingz.client.gui.screen;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.common.Resizer;
import com.ultreon.randomthingz.client.common.Screenshot;
import com.ultreon.randomthingz.client.gui.widgets.ScreenshotSelectionList;
import com.ultreon.randomthingz.common.FloatSize;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, value = Dist.CLIENT)
public class ScreenshotsScreen extends AdvancedScreen {
    // No getter / setter.
    private final List<File> files = new ArrayList<>();
    private final List<Screenshot> screenshots = new ArrayList<>();

    // Getter only.
    @Getter
    private final Screen backScreen;
    @Getter
    private Screenshot currentScreenshot;

    // Getter & setter.
    @Getter
    private int index;
    @Getter
    private ScreenshotSelectionList list;
    @Getter
    private Thread loadThread;
    @Getter
    private int currentIndex = -1;
    @Getter
    private int total;
    @Getter
    private int loaded;

    /**
     * Screenshots screen: constructor.
     *
     * @param backScreen back screen.
     * @param titleIn    the screen title.
     */
    public ScreenshotsScreen(Screen backScreen, Component titleIn) {
        super(titleIn);
        this.backScreen = backScreen;

        this.reload();
    }

    private void reload() {
        File dir = new File(Minecraft.getInstance().gameDirectory, "screenshots");
        if (dir.exists()) {
            this.files.addAll(Arrays.asList(Objects.requireNonNull(dir.listFiles())));
        }
        this.total = this.files.size();
        this.index = 0;

        this.screenshots.clear();

        this.loadThread = new Thread(this::loadShots, "ScreenshotLoader");
        this.loadThread.start();
    }

    @SuppressWarnings({"BusyWait", "ConstantConditions"})
    @SneakyThrows
    private void loadShots() {
        this.loaded = 0;

        RandomThingz.LOGGER.info("Refreshing screenshot cache.");
        AtomicBoolean active = new AtomicBoolean(true);
        for (File file : this.files) {
            active.set(true);
            RenderSystem.recordRenderCall(() -> {
                ResourceLocation location = new ResourceLocation(RandomThingz.MOD_ID, "screenshots_screen/" + file.getName().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9/._-]", "_"));
                AbstractTexture tex = Minecraft.getInstance().getTextureManager().getTexture(location, null);

                DynamicTexture texture;

                if (tex == null) {
                    texture = this.loadTexture(location, file);

                    if (texture == null) {
                        location = null;
                    }
                } else {
                    if (tex instanceof DynamicTexture) {
                        texture = (DynamicTexture) tex;
                    } else {
                        texture = null;
                        location = null;
                    }
                }
                Screenshot screenshot = new Screenshot(file, texture, location);
                screenshots.add(screenshot);
                this.loaded++;
                active.set(false);
            });

            while (active.get()) {
                Thread.sleep(50);
            }
        }
        while (this.files.size() != this.screenshots.size()) {
            Thread.sleep(50);
        }
        this.list.loadScreenshots();
    }

    @Override
    protected void init() {
        super.init();

        this.list = this.addWidget(new ScreenshotSelectionList(this, Minecraft.getInstance(),
                200, this.height - 50, 10, this.height - 40, null));
        this.addRenderableWidget(new Button(10, this.height - 30, 200, 20, CommonComponents.GUI_BACK, (btn) -> this.back()));
    }

    /**
     * Refresh the screenshot cache.
     */
    public void refresh() {
        ScreenshotSelectionList.Entry selected = this.list.getSelected();
        int index;
        if (selected == null) {
            this.currentScreenshot = null;
            this.index = -1;
        } else if ((selected.getIndex()) != currentIndex) {
            index = selected.getIndex();
            this.currentIndex = index;
            this.currentScreenshot = screenshots.get(index);
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.list.render(matrixStack, mouseX, mouseY, partialTicks);

        // Buffer and tessellator.
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();

        // Dirt texture.
        RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);

        // Color.
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        // Render dirt.
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0D, this.height, 0.0D).uv(0f, (float) this.height / 32f).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(224, this.height, 0.0D).uv((float) 224 / 32f, (float) this.height / 32f).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(224, this.height - 40d, 0.0D).uv((float) 224 / 32f, ((float) this.height - 40f) / 32f).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(0.0D, this.height - 40d, 0.0D).uv(0f, ((float) this.height - 40f) / 32f).color(64, 64, 64, 255).endVertex();

        // Draw
        tessellator.end();

        // Render all children.
        for (Widget renderable : this.renderables) {
            renderable.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        if (this.loaded != this.total) {
            drawString(matrixStack, font, "Loaded screenshot " + loaded + " of " + total, 20, 20, 0xffffffff);
        }

        fill(matrixStack, 220, 10, width - 10, height - 10, 0x7f000000);

        if (currentScreenshot != null) {
            DynamicTexture texture = currentScreenshot.getTexture();
            ResourceLocation location = currentScreenshot.getResourceLocation();

            RenderSystem.setShaderTexture(0, location);

            if (texture != null) {
                int imgWidth = texture.getPixels().getWidth();
                int imgHeight = texture.getPixels().getHeight();

                Resizer resizer = new Resizer(imgWidth, imgHeight);
                FloatSize size = resizer.thumbnail(this.width - 10 - 220, this.height - 20);

                int centerX = this.width / 2;
                int centerY = this.height / 2;
                int width = (int) size.width;
                int height = (int) size.height;

                blit(matrixStack, 210 / 2 + centerX - width / 2, centerY - height / 2, 0, 0, width, height, width, height);
            } else {
                blit(matrixStack, 220, 10, width - 20, height - 20, 0, 0, 2, 2, 2, 2);
            }
        }
    }

    /**
     * Load texture file into a resource location.
     *
     * @param location the resource location to read the texture into.
     * @param file     the file to read.
     * @return an instance of {@linkplain DynamicTexture} containing data of the given file.
     */
    @Nullable
    public DynamicTexture loadTexture(ResourceLocation location, File file) {
        try (InputStream input = new FileInputStream(file)) {
            NativeImage nativeImage = NativeImage.read(input);
            DynamicTexture texture = new DynamicTexture(nativeImage);

            texture.setBlurMipmap(true, false);

            Minecraft mc = Minecraft.getInstance();

            mc.getTextureManager().getTexture(location, texture);
            return texture;
        } catch (Throwable t) {
            RandomThingz.LOGGER.error("Couldn't read image: {}", file.getAbsolutePath(), t);
            return null;
        }
    }

    /**
     * Go back to previous screen.
     */
    private void back() {
        // Go back to the previous screen.
        Objects.requireNonNull(this.minecraft).setScreen(this.backScreen);
    }

    /**
     * <b>WARNING: Do not invoke!</b>
     * Input event.
     */
    @SubscribeEvent
    public static void onInput(InputEvent.KeyInputEvent event) {
        if (event.getAction() != GLFW.GLFW_RELEASE) {
            return;
        }

        // Get minecraft instance.
        Minecraft mc = Minecraft.getInstance();

        // Get current screen.
        Screen screen = mc.screen;

        // Check if current screen is the screenshots screen.
        if (screen instanceof ScreenshotsScreen screenshots) {
            // Navigate
            if (event.getKey() == 263) screenshots.prevShot();
            if (event.getKey() == 262) screenshots.nextShot();
        }
    }

    /**
     * Go to the previous screenshot.
     */
    public void prevShot() {
        if (this.index > 0) {
            this.index--;
            this.refresh();
        }
    }

    /**
     * Go to the next screenshot.
     */
    public void nextShot() {
        if (this.index < this.files.size() - 1) {
            this.index++;
            this.refresh();
        }
    }

    @Override
    public void onClose() {
        this.back();
    }

    public List<File> getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    @NonNull
    public List<Screenshot> getScreenshots() {
        return Collections.unmodifiableList(this.screenshots);
    }
}
