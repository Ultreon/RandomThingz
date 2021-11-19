package com.ultreon.randomthingz.modules.updates;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.modules.ui.widgets.Progressbar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class UpdateScreen extends Screen {
    private static boolean initializedAlready = false;
    private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;
    private final Screen backScreen;
    private final URL downloadUrl;
    private final Set<Dependency> dependencies;
    private int ticksUntilEnable;
    private long downloaded;
    private long totalSize = -1L;
    private Button done;
    private Progressbar progressbar;
    private Thread downloadThread;
    private int blockSize = 1024;

    private boolean failed = false;

    public UpdateScreen(Screen backScreen, URL downloadUrl, Dependencies dependencies) {
        super(new TranslationTextComponent("msg.randomthingz.downloading_update.title"));
        this.backScreen = backScreen;
        this.downloadUrl = downloadUrl;
        this.dependencies = dependencies.getAll();
    }

    protected void initialize() {
        super.initialize();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Downloading Update", true);
        }

        this.buttons.clear();
        this.children.clear();

        this.done = this.addButton(new Button(this.width / 2 - 50, this.height / 6 + 96, 100, 20, DialogTexts.GUI_DONE, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.displayGuiScreen(this.failed ? new UpdateFailedScreen(this.backScreen) : new UpdateDownloadedScreen(this.backScreen));
            }
        }));

        this.progressbar = this.addButton(new Progressbar(this.width / 2 - 91, 120, 0, 100));

        this.done.active = false;
        initializedAlready = true;
        
        this.downloadThread = new Thread(this::downloadThread, "RandomThingzUpdateDownloader");
        this.downloadThread.start();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void downloadThread() {
        download(downloadUrl);

        for (Dependency dependency : this.dependencies) {
            download(dependency.getDownload());
        }
        this.done.active = true;
    }
    
    private void download(URL url) {

        OutputStream updateStream = null;
        InputStream inputStream = null;
        try {
            RandomThingz.LOGGER.info("Opening connection to the update file.");
            URLConnection urlConnection = url.openConnection();
//            urlConnection.connect();

            String headerField = urlConnection.getHeaderField("Content-Length");
            this.totalSize = Integer.parseInt(headerField);
            progressbar.setLength(totalSize);
            RandomThingz.LOGGER.info("Total download size is: " + this.totalSize);

            RandomThingz.LOGGER.info("Loading input stream for connection...");
            // Url Input stream
            inputStream = urlConnection.getInputStream();

            // Update folder.
            File updateFolder = new File(Minecraft.getInstance().gameDir.getAbsolutePath(), "updates");
            if (!updateFolder.exists()) {
                RandomThingz.LOGGER.info("Update folder doesn't exists, creating one...");
                updateFolder.mkdirs();
            }

            // Update file.
            String[] split = url.getPath().split("/");
            File updateFile = new File(updateFolder.getAbsolutePath(), split[split.length - 1]);
            if (updateFile.exists()) {
                RandomThingz.LOGGER.info("Update file already exists, deleting...");
                updateFile.delete();
            }

            RandomThingz.LOGGER.info("Creating file...");
            updateFile.createNewFile();
            updateStream = new FileOutputStream(updateFile);

            // Set initial offset value.
            int offset = 0;

            // Get current block size.
            int currentBlockSize = blockSize;

            RandomThingz.LOGGER.info("Download started!");
            RandomThingz.LOGGER.info("Block size: " + currentBlockSize);

            // Read data.
//            RandomThingz.LOGGER.info("Reading data...");
            byte[] block = new byte[currentBlockSize];
            int read = read(inputStream, block, currentBlockSize);

            // Write data.
//            RandomThingz.LOGGER.info("Writing data...");
            if (read != -1) {
                updateStream.write(block, 0, read);
                updateStream.flush();

                // Advance in offset.
                offset += read;
                progressbar.setValue(offset);
            }
//            RandomThingz.LOGGER.info("New offset: " + offset);

            // Read other bytes.
            while (read != -1) {
                // Get current block size.
                currentBlockSize = blockSize;

                // Read data.
//                RandomThingz.LOGGER.info("Reading data...");
                block = new byte[currentBlockSize];
                read = read(inputStream, block, currentBlockSize);
                if (read == -1) {
                    break;
                }

                // Write data.
//                RandomThingz.LOGGER.info("Writing data...");
                updateStream.write(block, 0, read);
                updateStream.flush();

                // Advance in offset.
                offset += read;
                progressbar.setValue(offset);
//                RandomThingz.LOGGER.info("New offset: " + offset);
            }

            // Close remote input stream.
            inputStream.close();

            // Flush and close local output stream.
            updateStream.flush();
            updateStream.close();
        } catch (IOException e) {
            // An error occurred.
            e.printStackTrace();
            failed = true;

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            if (updateStream != null) {
                try {
                    updateStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public int read(InputStream stream, byte[] b, int len) throws IOException {
        int off = 0;

        int c = stream.read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte)c;

        int i = 1;
        try {
            for (; i < len ; i++) {
                c = stream.read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte)c;
            }
        } catch (IOException ignored) {

        }
        return i;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public long getDownloaded() {
        return progressbar.getValue();
    }

    public long getTotalSize() {
        return progressbar.getLength();
    }

    public URL getDownloadUrl() {
        return downloadUrl;
    }

    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        int i;
        if (totalSize != -1L) {
            if (totalSize == 0) {
                i = 0;
            } else {
                i = (int) (100 * this.downloaded / this.totalSize);
            }
            drawCenteredString(matrixStack, this.font, new TranslationTextComponent("msg.randomthingz.downloading_update.description", this.downloaded, this.totalSize, i), this.width / 2, 90, 0xbfbfbf);
        }
        
        this.field_243276_q.func_241863_a(matrixStack, this.width / 2, 90);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public void tick() {
        super.tick();
        this.downloaded = progressbar.getValue();
        this.totalSize = progressbar.getLength();
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public static boolean isInitializedAlready() {
        return initializedAlready;
    }

    public Thread getDownloadThread() {
        return downloadThread;
    }
}
