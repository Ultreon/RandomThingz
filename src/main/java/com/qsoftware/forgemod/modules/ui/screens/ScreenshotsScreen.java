package com.qsoftware.forgemod.modules.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.FloatSize;
import com.qsoftware.forgemod.modules.ui.common.Resizer;
import com.qsoftware.forgemod.modules.ui.common.Screenshot;
import com.qsoftware.forgemod.modules.ui.widgets.ScreenshotSelectionList;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
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

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = QForgeMod.modId)
public class ScreenshotsScreen extends AdvancedScreen {
    // No getter / setter.
    private final List<File> files = new ArrayList<>();
    private final List<Screenshot> screenshots = new ArrayList<>();

    // Getter only.
    @Getter private final Screen backScreen;
    @Getter private Screenshot currentScreenshot;

    // Getter & setter.
    @Getter private int index;
    @Getter private ScreenshotSelectionList list;
    @Getter private Thread loadThread;
    @Getter private int currentIndex = -1;
    @Getter private int total;
    @Getter private int loaded;

    /**
     * Screenshots screen: constructor.
     *
     * @param backScreen back screen.
     * @param titleIn the screen title.
     */
    public ScreenshotsScreen(Screen backScreen, ITextComponent titleIn) {
        super(titleIn);
        this.backScreen = backScreen;

        this.reload();
    }

    private void reload() {
        File dir = new File(Minecraft.getInstance().gameDir, "screenshots");
        if (dir.exists()) {
            this.files.addAll(Arrays.asList(Objects.requireNonNull(dir.listFiles())));
        }
        this.total = this.files.size();
        this.index = 0;

        this.screenshots.clear();

        this.loadThread = new Thread(this::loadShots, "ScreenshotLoader");
        this.loadThread.start();
    }

    @SuppressWarnings("BusyWait")
    @SneakyThrows
    private void loadShots() {
        this.loaded = 0;

        QForgeMod.LOGGER.info("Refreshing screenshot cache.");
        AtomicBoolean active = new AtomicBoolean(true);
        for (File file : this.files) {
            active.set(true);
            RenderSystem.recordRenderCall(() -> {
                ResourceLocation location = new ResourceLocation(QForgeMod.modId, "screenshots_screen/" + file.getName().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9/._-]", "_"));
                Texture texture0 = Minecraft.getInstance().getTextureManager().getTexture(location);

                DynamicTexture texture;

                if (texture0 == null) {
                    texture = this.loadTexture(location, file);

                    if (texture == null) {
                        location = null;
                    }
                } else {
                    if (texture0 instanceof DynamicTexture) {
                        texture = (DynamicTexture) texture0;
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
    protected void initialize() {
        super.initialize();

        this.list = this.addListener(new ScreenshotSelectionList(this, Minecraft.getInstance(),
                200, this.height - 50, 10, this.height - 40, null));
        this.addButton(new Button(10, this.height - 30, 200, 20, DialogTexts.GUI_BACK, (btn) -> this.goBack()));
    }

    /**
     * Refresh the screen shot cache.
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
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.list.render(matrixStack, mouseX, mouseY, partialTicks);

        // Buffer and tessellator.
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        // Dirt texture.
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_LOCATION);

        // Color.
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        // Render dirt.
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0D, this.height, 0.0D).tex(0.0F, (float)this.height  / 32.0F).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(224, this.height, 0.0D).tex((float)224 / 32.0F, (float)this.height  / 32.0F).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(224, this.height - 40d, 0.0D).tex((float)224 / 32.0F, ((float)this.height - 40f) / 32.0f).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(0.0D, this.height - 40d, 0.0D).tex(0.0F, ((float)this.height - 40f) / 32.0f).color(64, 64, 64, 255).endVertex();

        // Draw
        tessellator.draw();

        // Render all children.
        for (Widget child : this.buttons) {
            child.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        if (this.loaded != this.total) {
            drawString(matrixStack, font, "Loaded screenshot " + loaded + " of " + total, 20, 20, 0xffffffff);
        }

        fill(matrixStack, 220, 10, width - 10, height - 10, 0x7f000000);

        if (currentScreenshot != null) {
            DynamicTexture texture = currentScreenshot.getTexture();
            ResourceLocation location = currentScreenshot.getResourceLocation();

            this.minecraft.textureManager.bindTexture(location);

            if (texture != null) {
                int imgWidth = texture.getTextureData().getWidth();
                int imgHeight = texture.getTextureData().getHeight();

//            int centerX;
//            int width;
//            int height;
//            if (imgWidth > imgHeight) {
//                this.aspectRatio = (float) (imgWidth / (double) imgHeight);
//                this.orientation = AspectRatio.Orientation.LANDSCAPE;
//
//                centerX = this.width / 2;
//                width = this.width - 20;
//                height = (int) (width / aspectRatio);
//
//                if (height > this.height - 20) {
//                    this.aspectRatio = (float) (imgHeight / (double) imgWidth);
//                    this.orientation = AspectRatio.Orientation.PORTRAIT;
//
//                    centerX = this.width / 2;
//                    height = this.height - 20;
//                    width = (int) (height / aspectRatio);
//                }
//            } else {
//                this.aspectRatio = (float) (imgHeight / (double) imgWidth);
//                this.orientation = AspectRatio.Orientation.PORTRAIT;
//
//                centerX = this.width / 2;
//                height = this.height - 20;
//                width = (int) (height / aspectRatio);
//                if (width > this.width - 20) {
//                    this.aspectRatio = (float) (imgWidth / (double) imgHeight);
//                    this.orientation = AspectRatio.Orientation.LANDSCAPE;
//
//                    centerX = (this.width) / 2;
//                    width = this.width - 20;
//                    height = (int) (width / aspectRatio);
//                }
//            }

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
     * @param file the file to read.
     * @return an instance of {@link DynamicTexture} containing data of the given file.
     */
    @Nullable
    public DynamicTexture loadTexture(ResourceLocation location, File file) {
        try (InputStream input = new FileInputStream(file)) {
            NativeImage nativeImage = NativeImage.read(input);
            DynamicTexture texture = new DynamicTexture(nativeImage);

            texture.setBlurMipmap(true, false);

            Minecraft mc = Minecraft.getInstance();

            mc.getTextureManager().readTexture(location, texture);
            return texture;
        } catch (Throwable t) {
            QForgeMod.LOGGER.error("Couldn't read image: {}", file.getAbsolutePath(), t);
            return null;
        }
    }

    /**
     * Go back to previous screen.
     */
    private void goBack() {
        // Go back to the previous screen.
        Objects.requireNonNull(this.minecraft).displayGuiScreen(this.backScreen);
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
        Screen screen = mc.currentScreen;

        // Check if current screen is the screenshots screen.
        if (screen instanceof ScreenshotsScreen) {
            // Cast
            ScreenshotsScreen screenshots = (ScreenshotsScreen) screen;

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
    public void closeScreen() {
        this.goBack();
    }

    public List<File> getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    @NonNull
    public List<Screenshot> getScreenshots() {
        return Collections.unmodifiableList(this.screenshots);
    }

//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//        return this.list.mouseClicked(mouseX, mouseY, button);
//    }
//    @Override
//    public boolean mouseReleased(double mouseX, double mouseY, int button) {
//        return this.list.mouseReleased(mouseX, mouseY, button);
//    }
//    @Override
//    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
//        return this.list.mouseDragged(mouseX, mouseY, button, dragX, dragY);
//    }
//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
//        return this.list.mouseScrolled(mouseX, mouseY, delta);
//    }
//    @Override
//    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
//        super.keyPressed(keyCode, s)
//        return this.list.keyPressed(keyCode, scanCode, modifiers);
//    }
//    @Override
//    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
//        return this.list.keyReleased(keyCode, scanCode, modifiers);
//    }
//    @Override
//    public boolean charTyped(char codePoint, int modifiers) {
//        return this.list.charTyped(codePoint, modifiers);
//    }
}
