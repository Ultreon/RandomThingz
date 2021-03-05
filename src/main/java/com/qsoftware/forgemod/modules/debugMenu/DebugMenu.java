package com.qsoftware.forgemod.modules.debugMenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.common.IntSize;
import com.qsoftware.forgemod.common.enums.MoonPhase;
import com.qsoftware.forgemod.hud.HudItems;
import com.qsoftware.forgemod.hud.IHasHud;
import com.qsoftware.forgemod.keybinds.KeybindingList;
import com.qsoftware.forgemod.modules.debugMenu.pages.DefaultPage;
import com.qsoftware.modlib.common.Angle;
import com.qsoftware.modlib.common.Multiplier;
import com.qsoftware.modlib.common.Percentage;
import com.qsoftware.modlib.common.interfaces.Formattable;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Monitor;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Client listener
 *
 * @author (partial) CoFH - https://github.com/CoFH
 */
@SuppressWarnings("unused")
public final class DebugMenu {
    @Getter
    @Setter
    private DebugPage page = null;
//    private final List<DebugPage> pages = new ArrayList<>();

    public enum Page {
        NONE, PLAYER_1, PLAYER_2, ITEM, BLOCK, ENTITY, WORLD, WORLD_INFO, MINECRAFT, WINDOW, COMPUTER
    }

    DebugMenu() {

    }

    public Collection<RegistryObject<DebugPage>> getPages() {
        return ModDebugPages.REGISTRY.getEntries();
    }

    @SuppressWarnings("UnusedReturnValue")
//    public <T extends DebugPage> T registerPage(T page) {
//        this.pages.add(page);
//        return page;
//    }

    public void onClientTick(TickEvent.ClientTickEvent event) {
//        if (event.getAction() == GLFW.GLFW_RELEASE) {
//            if (event.getKey() == KeyBindingList.DEBUG_SCREEN.getKey().getKeyCode()) {
//                if (InputUtils.isShiftDown()) {
//                    int next = this.pages.indexOf(this.page) - 1;
//
//                    List<DebugPage> values = this.pages;
////                    if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
////                        if (next < 1) {
////                            next = values.length - 1;
////                        }
////                    } else {
//                        if (next < 0) {
//                            next = values.size() - 1;
//                        }
////                    }
//
//                    this.page = values.get(next);
//                    return;
//                }
//
//                int next = this.page.ordinal() + 1;
//
//                List<DebugPage> values = pages;
//                if (next >= values.size()) {
////                    if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
////                        next = 1;
////                    } else {
//                        next = 0;
////                    }
//                }
//
//                page = values.get(next);

//            }
//        }

        if (KeybindingList.DEBUG_SCREEN.isPressed()) {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new DebugChoiceScreen(new TranslationTextComponent("screen.qforgemod.debug_choice")));
        }
    }

    @SuppressWarnings({"UnusedAssignment"})
    public void renderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) return;

        if (Minecraft.getInstance().gameSettings.showDebugInfo) {
            return;
        }

        MatrixStack matrixStack = event.getMatrixStack();
        Minecraft mc = Minecraft.getInstance();
        MainWindow mainWindow = mc.getMainWindow();
        Monitor monitor = mainWindow.getMonitor();

        int width = mainWindow.getScaledWidth();
        int height = mainWindow.getScaledHeight();

        int dw, dh;
        if (monitor != null) {
            dw = monitor.getDefaultVideoMode().getWidth();
            dh = monitor.getDefaultVideoMode().getHeight();
        } else {
            dw = 0;
            dh = 0;
        }

        for (IHasHud hud : HudItems.ITEMS) {
            hud.render(matrixStack, mc);
        }

        if (!(page instanceof DefaultPage) && page != null) {
            mc.fontRenderer.drawStringWithShadow(matrixStack, "debugPage: " + page.getRegistryName(), 12f, height - 16f, 0xffaa00);
        }

        if (page == null) {
            return;
        }

        if (page.hasRequiredComponents()) {
            List<DebugEntry> linesLeft = page.getLinesLeft();
            for (int i = 0; i < linesLeft.size(); i++) {
                DebugEntry entry = linesLeft.get(i);
                try {
                    drawLeftTopString(matrixStack, entry.getKey(), i, entry.getValueSupplier().get());
                } catch (Exception e) {
                    drawLeftTopString(matrixStack, entry.getKey(), i, e);
                }
            }

            List<DebugEntry> linesRight = page.getLinesRight();
            for (int i = 0; i < linesRight.size(); i++) {
                DebugEntry entry = linesRight.get(i);
                try {
                    drawRightTopString(matrixStack, entry.getKey(), i, entry.getValueSupplier().get());
                } catch (Exception e) {
                    drawRightTopString(matrixStack, entry.getKey(), i, e);
                }
            }
        }

//        switch (page) {
//            case PLAYER_1: {
//                if (Minecraft.getInstance().player != null) {
//                    int k = 0;
//                    {
//                        float f = player.rotationPitch;
//                        float f1 = player.rotationYaw;
//
//                        Vector3d vec3d = player.getEyePosition(1.0F);
//
//                        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
//                        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
//                        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
//                        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));
//
//                        float f6 = f3 * f4;
//                        float f7 = f2 * f4;
//
//                        double d0 = 16;
//
//                        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
//
//                        BlockRayTraceResult lookingAt = null;
//                        if (Minecraft.getInstance().world != null) {
//                            lookingAt = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
//                            if (lookingAt.getType() == RayTraceResult.Type.BLOCK) {
//                                BlockPos pos = lookingAt.getPos();
//
//                                // now the coordinates you want are in pos. Example of use:
//                                BlockState blockState = Minecraft.getInstance().player.getEntityWorld().getBlockState(pos);
//                                drawTopString(matrixStack, "-== Block ==-", k++);
//                                drawTopString(matrixStack, blockState.getBlock().getTranslatedName().getString(), k++);
//                                k++;
//                            } else {
//                                // not looking at a block, or too far away from one to tell
////                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 16, 0xff0000);
//                            }
//                            lookingAt = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));
//                            if (lookingAt.getType() == RayTraceResult.Type.BLOCK) {
//                                BlockPos pos = lookingAt.getPos();
//
//                                // now the coordinates you want are in pos. Example of use:
//                                BlockState blockState = Minecraft.getInstance().player.getEntityWorld().getBlockState(pos);
//                                FluidState fluidState = blockState.getFluidState();
//                                if (!fluidState.isEmpty()) {
//                                    drawTopString(matrixStack, "-== Fluid ==-", k++);
//                                    drawTopString(matrixStack, blockState.getBlock().getTranslatedName().getString(), k++);
//                                    k++;
//                                } else {
//                                    // not looking at a fluid, or too far away from one to tell
////                                    Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
//                                }
//                            } else {
//                                // not looking at a fluid, or too far away from one to tell
//                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 32, 0xff0000);
//                            }
//                        } else {
//                            Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<Invalid>", width / 2, height / 2 - 32, 0xbf0000);
//                        }
//                    }
//
//                    {
//                        float f = player.rotationPitch;
//                        float f1 = player.rotationYaw;
//
//                        Vector3d vec3d = player.getEyePosition(1.0F);
//
//                        float f2 = MathHelper.cos(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
//                        float f3 = MathHelper.sin(-f1 * ((float) Math.PI / 180F) - (float) Math.PI);
//                        float f4 = -MathHelper.cos(-f * ((float) Math.PI / 180F));
//                        float f5 = MathHelper.sin(-f * ((float) Math.PI / 180F));
//
//                        float f6 = f3 * f4;
//                        float f7 = f2 * f4;
//
//                        double d0 = 16;
//
//                        Vector3d vec3d1 = vec3d.add((double) f6 * d0, (double) f5 * d0, (double) f7 * d0);
//
//                        if (Minecraft.getInstance().world != null) {
//                            RayTraceResult raytraceresult = Minecraft.getInstance().world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
//                            if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
//                                vec3d1 = raytraceresult.getHitVec();
//                            }
//
//                            RayTraceResult rayTraceResult1 = ProjectileHelper.rayTraceEntities(Minecraft.getInstance().world, player, vec3d, vec3d1, player.getBoundingBox().grow(16.0D), entity -> !entity.equals(player));
//                            if (rayTraceResult1 != null) {
//                                raytraceresult = rayTraceResult1;
//                            }
//                            if (raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
//                                @SuppressWarnings("ConstantConditions") EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) raytraceresult;
//
//                                drawTopString(matrixStack, "-== Entity ==-", k++);
//                                drawTopString(matrixStack, I18n.format(entityRayTraceResult.getEntity().getType().getTranslationKey()), k++);
//                                k++;
////                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, I18n.format(entityRayTraceResult.getEntity().getType().getTranslationKey()), width / 2, height / 2 - 48, 0x00bf00);
//                            } else {
//                                // not looking at a block, or too far away from one to tell
////                                Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<None>", width / 2, height / 2 - 48, 0xff0000);
//                            }
//                        }
//                    }
//                } else {
////                    Screen.drawCenteredString(matrixStack, mc.fontRenderer, "<Invalid>", width / 2, height / 2 - 16, 0xbf0000);
//                }
//                break;
//            } case NONE: {
//                ClientWorld world = Minecraft.getInstance().world;
//                ClientPlayerEntity player = Minecraft.getInstance().player;
//                if (world != null && player != null) {
//                    String dayTimeStr = Long.toString(world.getDayTime() % 24000);
//                    StringBuilder timeText = new StringBuilder();
//                    if (dayTimeStr.length() == 1) {
//                        timeText.append("0:00,").append(dayTimeStr);
//                    } else if (dayTimeStr.length() == 2) {
//                        timeText.append("0:0").append(dayTimeStr.charAt(0)).append(",").append(dayTimeStr.substring(1));
//                    } else if (dayTimeStr.length() == 3) {
//                        timeText.append("0:").append(dayTimeStr, 0, 2).append(",").append(dayTimeStr.substring(2));
//                    } else if (dayTimeStr.length() == 4) {
//                        timeText.append(dayTimeStr.charAt(0)).append(":").append(dayTimeStr, 1, 3).append(",").append(dayTimeStr.substring(3));
//                    } else if (dayTimeStr.length() == 5) {
//                        timeText.append(dayTimeStr, 0, 2).append(":").append(dayTimeStr, 2, 4).append(",").append(dayTimeStr.substring(4));
//                    } else {
//                        timeText.append("OVERFLOW");
//                    }
//
//                    int i = 0;
//
//                    drawLeftTopString(matrixStack, "QFM Build", i++, new Formatted(QForgeMod.getModArgs().getVersion().getBuild()));
//                    drawLeftTopString(matrixStack, "Time", i++, new Formatted(timeText.toString()));
//
//                    long dayTime = world.getDayTime() % 24000;
//
//                    String timePhase;
//                    if (dayTime < 3000 || dayTime > 21000) {
//                        timePhase = "Evening";
//                    } else if (dayTime > 3000 && dayTime < 9000) {
//                        timePhase = "Noon";
//                    } else if (dayTime > 9000 && dayTime < 15000) {
//                        timePhase = "Afternoon";
//                    } else if (dayTime > 15000 && dayTime < 21000) {
//                        timePhase = "Night";
//                    } else {
//                        timePhase = "UNKNOWN";
//                    }
//
//                    drawLeftTopString(matrixStack, "Time Phase", i++, new Formatted(timePhase));
//
//                    if (!world.isRemote) {
//                        Biome biome = world.getBiome(player.getPosition());
//                        ResourceLocation location = biome.getRegistryName();
//                        if (location != null) {
//                            @Nonnull ResourceLocation registryName = location;
//                            drawLeftTopString(matrixStack, "Current Biome", i++, new Formatted(I18n.format("biome." + registryName.getNamespace() + "." + registryName.getPath())));
//                        }
//                    }
//                    drawLeftTopString(matrixStack, "Current Pos", i++, new Formatted(player.getPosition().getCoordinatesAsString()));
//                }
//            } default: {
//                break;
//            }
//        }
    }

    private Formattable getFormatted(String s) {
        return () -> s;
    }

    private Formattable getMultiplier(double multiplier) {
        return new Multiplier(multiplier);
    }

    private Formattable getSize(int w, int h) {
        return new IntSize(w, h);
    }

    private Formattable getSize(float w, float h) {
        return () -> TextFormatting.GOLD.toString() + w + TextFormatting.GRAY + " x " + TextFormatting.GOLD + h;
    }

    private Formattable getPercentage(double value) {
        return new Percentage(value);
    }

    private Color getColor(Vector3d color) {
        return new Color((float)color.x, (float)color.y, (float)color.z);
    }

    private Color getColor(int rgb) {
        return new Color(rgb);
    }

    private Formattable getAngle(double angle) {
        return new Angle(angle * 360.0d);
    }

    private Formattable getRadians(double angle) {
        return new Angle(Math.toDegrees(angle));
    }

    private Formattable getDegrees(double angle) {
        return new Angle(angle);
    }

    private MoonPhase getMoonPhase(int index) {
        return MoonPhase.fromIndex(index);
    }

    public String format(String text, Object obj, Object... objects) {
        StringBuilder sb = new StringBuilder();

//        sb.append(TextFormatting.DARK_AQUA).append(text);
         sb.append(TextFormatting.GRAY).append(text);
        sb.append(TextFormatting.GRAY).append(": ");
        sb.append(format(obj));

        for (Object object : objects) {
            sb.append(TextFormatting.GRAY).append(", ");
            sb.append(format(object));
        }

        return sb.toString();
    }

    public String format(Object obj) {
        StringBuilder sb = new StringBuilder();

        if (obj == null) {
            sb.append(TextFormatting.LIGHT_PURPLE);
            sb.append("null");
        } else if (obj instanceof String) {
            sb.append(TextFormatting.GOLD);
            sb.append("\"");
            sb.append(obj.toString()
                    .replaceAll("\\\\", TextFormatting.WHITE + "\\\\" + TextFormatting.GOLD)
                    .replaceAll("\n", TextFormatting.WHITE + "\\n" + TextFormatting.GOLD)
                    .replaceAll("\r", TextFormatting.WHITE + "\\r" + TextFormatting.GOLD)
                    .replaceAll("\t", TextFormatting.WHITE + "\\t" + TextFormatting.GOLD)
                    .replaceAll("\b", TextFormatting.WHITE + "\\b" + TextFormatting.GOLD)
                    .replaceAll("\f", TextFormatting.WHITE + "\\f" + TextFormatting.GOLD));
            sb.append("\"");
        } else if (obj instanceof Character) {
            sb.append(TextFormatting.GOLD);
            sb.append("'");
            if (obj.equals('\\')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\\\");
            } else if (obj.equals('\n')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\n");
            } else if (obj.equals('\r')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\r");
            } else if (obj.equals('\t')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\t");
            } else if (obj.equals('\b')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\b");
            } else if (obj.equals('\f')) {
                sb.append(TextFormatting.WHITE);
                sb.append("\\f");
            } else {
                sb.append(obj.toString());
            }
            sb.append(TextFormatting.GOLD);
            sb.append("'");
        } else if (obj instanceof Integer) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
        } else if (obj instanceof Short) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("s");
        } else if (obj instanceof Byte) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("b");
        } else if (obj instanceof Long) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("L");
        } else if (obj instanceof Float) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("f");
        } else if (obj instanceof Double) {
            sb.append(TextFormatting.YELLOW);
            sb.append(obj.toString());
            sb.append("d");
        } else if (obj instanceof Boolean) {
            sb.append(TextFormatting.LIGHT_PURPLE);
            sb.append(obj.toString());
        } else if (obj instanceof Enum<?>) {
            Enum<?> e = (Enum<?>) obj;

            sb.append(TextFormatting.GREEN).append(e);
        } else if (obj instanceof List) {
            sb.append(TextFormatting.GRAY);
            sb.append("[");

            List<?> list = (List<?>) obj;

            Iterator<?> it = list.iterator();
            if (! it.hasNext()) {
                sb.append("]");
                return sb.toString();
            }

            for (;;) {
                Object e = it.next();
                sb.append(e == list ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this List" + TextFormatting.GRAY + ")") : format(e));
                if (! it.hasNext()) {
                    return sb.append(TextFormatting.GRAY).append(']').toString();
                }
                sb.append(TextFormatting.GRAY).append(',').append(' ');
            }
        } else if (obj instanceof Set<?>) {
            sb.append(TextFormatting.GRAY);
            sb.append("{");

            Set<?> set = (Set<?>) obj;

            Iterator<?> it = set.iterator();
            if (! it.hasNext()) {
                sb.append("}");
                return sb.toString();
            }

            for (;;) {
                Object e = it.next();
                sb.append(e == set ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Set" + TextFormatting.GRAY + ")") : format(e));
                if (! it.hasNext()) {
                    return sb.append(TextFormatting.GRAY).append('}').toString();
                }
                sb.append(TextFormatting.GRAY).append(',').append(' ');
            }
        } else if (obj instanceof Map<?, ?>) {
            sb.append(TextFormatting.GRAY);
            sb.append("{");

            Map<?, ?> map = (Map<?, ?>) obj;

            Iterator<? extends Map.Entry<?, ?>> it = map.entrySet().iterator();
            if (! it.hasNext()) {
                sb.append("}");
                return sb.toString();
            }

            for (;;) {
                Map.Entry<?, ?> e = it.next();
                sb.append(e.getKey() == map ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Map" + TextFormatting.GRAY + ")") : format(e.getKey()));
                sb.append(TextFormatting.GRAY).append(": ");
                sb.append(e.getValue() == map ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Map" + TextFormatting.GRAY + ")") : format(e.getValue()));
                if (! it.hasNext()) {
                    return sb.append(TextFormatting.GRAY).append('}').toString();
                }
                sb.append(TextFormatting.GRAY).append(", ");
            }
        } else if (obj instanceof Map.Entry<?, ?>) {
            sb.append(TextFormatting.GRAY);

            Map.Entry<?, ?> e = (Map.Entry<?, ?>) obj;

            sb.append(e.getKey() == e ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Entry" + TextFormatting.GRAY + ")") : format(e.getKey()));
            sb.append(TextFormatting.GRAY).append(": ");
            sb.append(e.getValue() == e ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Entry" + TextFormatting.GRAY + ")") : format(e.getValue()));
            sb.append(TextFormatting.GRAY).append(", ");
        } else if (obj instanceof Vector2f) {
            Vector2f v = (Vector2f) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(v.x));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(v.y));
        } else if (obj instanceof Vector3f) {
            Vector3f v = (Vector3f) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(v.getX()));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(v.getY()));
            sb.append(TextFormatting.GRAY).append(", z: ");
            sb.append(format(v.getZ()));
        } else if (obj instanceof Vector3d) {
            Vector3d v = (Vector3d) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(v.getX()));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(v.getY()));
            sb.append(TextFormatting.GRAY).append(", z: ");
            sb.append(format(v.getZ()));
        } else if (obj instanceof Vector4f) {
            Vector4f v = (Vector4f) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(v.getX()));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(v.getY()));
            sb.append(TextFormatting.GRAY).append(", z: ");
            sb.append(format(v.getZ()));
            sb.append(TextFormatting.GRAY).append(", w: ");
            sb.append(format(v.getW()));
        } else if (obj instanceof BlockPos) {
            BlockPos v = (BlockPos) obj;
            sb.append(TextFormatting.GRAY).append("x: ");
            sb.append(format(Math.round(v.getX())));
            sb.append(TextFormatting.GRAY).append(", y: ");
            sb.append(format(Math.round(v.getY())));
            sb.append(TextFormatting.GRAY).append(", z: ");
            sb.append(format(Math.round(v.getZ())));
        } else if (obj instanceof Color) {
            Color c = (Color) obj;
            sb.append(TextFormatting.GRAY).append("#");
            sb.append(TextFormatting.BLUE);
            String s = Integer.toHexString(c.getRGB());
            for (int i = 0; i < 8 - s.length(); i++) {
                sb.append("0");
            }

            sb.append(s);
        } else if (obj instanceof ITextComponent) {
            ITextComponent e = (ITextComponent) obj;
            sb.append(TextFormatting.GRAY).append("ITextComponent: ");
            sb.append(format(e.getString()));
        } else if (obj instanceof ItemStack) {
            ItemStack e = (ItemStack) obj;
            sb.append(TextFormatting.BLUE).append(e.getItem().getRegistryName()).append(" ");
            sb.append(TextFormatting.GRAY).append(e.getCount()).append("x");
        } else if (obj instanceof Formattable) {
            Formattable e = (Formattable) obj;
            sb.append(e.toFormattedString());
        } else if (obj instanceof IForgeRegistryEntry<?>) {
            IForgeRegistryEntry<?> ifrEntry = (IForgeRegistryEntry<?>) obj;
            sb.append(format(ifrEntry.getRegistryType()));
            sb.append(TextFormatting.GRAY).append("@");
            sb.append(format(ifrEntry.getRegistryName()));
        } else if (obj instanceof IForgeRegistry<?>) {
            IForgeRegistry<?> ifr = (IForgeRegistry<?>) obj;
            sb.append(format(ifr.getRegistryName()));
        } else if (obj instanceof ResourceLocation) {
            ResourceLocation rl = (ResourceLocation) obj;
            sb.append(TextFormatting.GOLD).append(rl.getNamespace());
            sb.append(TextFormatting.GRAY).append(":");
            sb.append(TextFormatting.YELLOW).append(rl.getPath());
        } else if (obj instanceof PlayerEntity) {
            Entity e = (Entity) obj;
            sb.append(TextFormatting.GRAY).append("Player: ");
            sb.append(format(e.getName().getString()));
        } else if (obj instanceof Entity) {
            Entity e = (Entity) obj;
            sb.append(TextFormatting.GRAY).append("Entity: ");
            sb.append(format(e.getUniqueID()));
        } else if (obj instanceof UUID) {
            UUID uuid = (UUID) obj;
            sb.append(TextFormatting.GOLD).append(uuid.toString().replaceAll("-", TextFormatting.GRAY + "-" + TextFormatting.GOLD));
        } else if (obj instanceof Collection<?>) {
            sb.append(TextFormatting.GRAY);
            sb.append("(");

            Collection<?> collection = (Collection<?>) obj;

            Iterator<?> it = collection.iterator();
            if (! it.hasNext()) {
                sb.append(")");
                return sb.toString();
            }

            for (;;) {
                Object e = it.next();
                sb.append(e == collection ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Collection" + TextFormatting.GRAY + ")") : format(e));
                if (! it.hasNext()) {
                    return sb.append(TextFormatting.GRAY).append(')').toString();
                }
                sb.append(TextFormatting.GRAY).append(',').append(' ');
            }
        } else if (obj instanceof Class<?>) {
            Class<?> c = (Class<?>) obj;

            sb.append(TextFormatting.AQUA);
            sb.append(c.getPackage().getName().replaceAll("\\.", TextFormatting.GRAY + "." + TextFormatting.AQUA));
            sb.append(TextFormatting.GRAY).append(".").append(TextFormatting.AQUA);
            sb.append(TextFormatting.DARK_AQUA);
            sb.append(c.getSimpleName());
        } else {
            Class<?> c = obj.getClass();
            sb.append(TextFormatting.AQUA);
            sb.append(c.getPackage().getName().replaceAll("\\.", TextFormatting.GRAY + "." + TextFormatting.AQUA));
            sb.append(TextFormatting.GRAY).append(".").append(TextFormatting.AQUA);
            sb.append(TextFormatting.DARK_AQUA);
            sb.append(c.getSimpleName());
            sb.append(TextFormatting.GRAY).append("@").append(TextFormatting.YELLOW);
            sb.append(Integer.toHexString(obj.hashCode()));
        }
        return sb.toString();
    }

    private void drawTopString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width / 2f - fontRenderer.getStringWidth(text) / 2f,  12f + (line * 12), 0xffffff);
    }

    private void drawLeftTopString(MatrixStack matrixStack, String text, int line, Object obj, Object... objects) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

        text = format(text, obj, objects);

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, 12f,  12f + (line * 12), 0xffffff);
    }

    private void drawRightTopString(MatrixStack matrixStack, String text, int line, Object obj, Object... objects) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        text = format(text, obj, objects);

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width - 12 - fontRenderer.getStringWidth(text), 12f + (line * 12), 0xffffff);
    }

    @SuppressWarnings("unused")
    private void drawBottomString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width / 2f - fontRenderer.getStringWidth(text) / 2f,  height - 29f - (line * 12), 0xffffff);
    }

    @SuppressWarnings("unused")
    private void drawLeftBottomString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, 12, height - 29f - (line * 12), 0xffffff);
    }

    @SuppressWarnings("unused")
    private void drawRightBottomString(MatrixStack matrixStack, String text, int line) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();
        int height = Minecraft.getInstance().getMainWindow().getScaledHeight();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width - 12 - fontRenderer.getStringWidth(text), height - 29f - (line * 12), 0xffffff);
    }

    @SuppressWarnings({"unused", "SameParameterValue"})
    private void drawRightString(MatrixStack matrixStack, String text, float mx, float y, int color) {
        // Declare local variables before draw.
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        int width = Minecraft.getInstance().getMainWindow().getScaledWidth();

        // Draw text.
        fontRenderer.drawStringWithShadow(matrixStack, text, width - mx - fontRenderer.getStringWidth(text), y, color);
    }
}
