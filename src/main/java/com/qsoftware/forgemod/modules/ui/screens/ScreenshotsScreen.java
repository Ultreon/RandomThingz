package com.qsoftware.forgemod.modules.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.FloatSize;
import com.qsoftware.forgemod.modules.ui.common.AspectRatio;
import com.qsoftware.forgemod.modules.ui.common.Screenshot;
import com.qsoftware.forgemod.modules.ui.widgets.ScreenshotSelectionList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
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

@Mod.EventBusSubscriber(modid = QForgeMod.modId)
public class ScreenshotsScreen extends AdvancedScreen {
    // No getter / setter.
    private final List<File> files;
    private final List<Screenshot> screenshots = new ArrayList<>();

    // Getter only.
    @Getter private final Screen backScreen;
    @Getter private AspectRatio.Orientation orientation;
    @Getter private Screenshot current;

    // Getter & setter.
    @Getter @Setter private int index;
    private ScreenshotSelectionList list;


    /**
     * Screenshots screen: constructor.
     *
     * @param backScreen back screen.
     * @param titleIn the screen title.
     */
    public ScreenshotsScreen(Screen backScreen, ITextComponent titleIn) {
        super(titleIn);
        this.backScreen = backScreen;

        File dir = new File(Minecraft.getInstance().gameDir, "screenshots");
        this.files = Arrays.asList(dir.listFiles());
        this.index = 0;


        QForgeMod.LOGGER.info("Refreshing screenshot cache.");
        for (File file : this.files) {
            ResourceLocation location = new ResourceLocation(QForgeMod.modId, "screenshots_screen/" + file.getName());
            Texture texture0 = Minecraft.getInstance().getTextureManager().getTexture(location);

            DynamicTexture texture;

            if (texture0 == null) {
                QForgeMod.LOGGER.info("Loading texture from file @ " + file.getPath());
                QForgeMod.LOGGER.info("Loading texture to " + location);
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
        }

        this.refresh();
    }

    /**
     * Refresh the screen shot cache.
     */
    protected void refresh() {
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        fill(matrixStack, 160, 10, width - 20, height - 10, 0x7f000000);

        if (current != null) {
            DynamicTexture texture = current.getTexture();
            ResourceLocation location = current.getResourceLocation();

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

                AspectRatio aspectRatio = new AspectRatio(imgWidth, imgHeight);
                FloatSize size = aspectRatio.thumbnail(this.width - 20 - 160, this.height - 20);

                int centerX = this.width / 2;
                int centerY = this.height / 2;
                int width = (int) size.width;
                int height = (int) size.height;

                blit(matrixStack, centerX - width / 2 + 160, centerY / height / 2, 0, 0, width, height, width, height);
            } else {
                blit(matrixStack, 10, 10, width - 20, height - 20, 0, 0, 2, 2, 2, 2);
            }
        }
    }

    /**
     * Load texture file into a resource location.
     *
     * @param location the resource location to load the texture into.
     * @param file the file to load.
     * @return an instance of {@link DynamicTexture} containing data of the given file.
     */
    @Nullable
    public DynamicTexture loadTexture(ResourceLocation location, File file) {
        try (InputStream input = new FileInputStream(file)) {
            NativeImage nativeImage = NativeImage.read(input);
            DynamicTexture texture = new DynamicTexture(nativeImage);

            texture.setBlurMipmap(true, false);

            Minecraft mc = Minecraft.getInstance();

            mc.getTextureManager().loadTexture(location, texture);
            QForgeMod.LOGGER.info("Loaded texture for screenshot: " + location);
            QForgeMod.LOGGER.info("File: " + file);
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

    public void refreshFromList(boolean present) {
        refresh();
    }

    public List<File> getFiles() {
        return Collections.unmodifiableList(this.files);
    }

    @NonNull
    public List<Screenshot> getScreenshots() {
        return Collections.unmodifiableList(this.screenshots);
    }
}
