package com.qsoftware.forgemod.script;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.util.TextUtils;
import lombok.experimental.UtilityClass;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.graalvm.polyglot.Value;

import java.awt.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.*;

@SuppressWarnings("unused")
@UtilityClass
public class CommonScriptJSUtils {
    public static String formatClass(String text) {
        return MessageFormat.format("{0}{1}{2}[{3}]", TextFormatting.YELLOW, text, TextFormatting.GRAY, TextFormatting.GRAY);
    }

    public static String formatClass(String text, Object obj) {
        if (obj instanceof String) {
            return TextFormatting.DARK_GREEN +
                    obj.toString()
                            .replaceAll("\\\\", TextFormatting.DARK_AQUA + "\\\\" + TextFormatting.DARK_GREEN)
                            .replaceAll("\n", TextFormatting.DARK_AQUA + "\\n" + TextFormatting.DARK_GREEN)
                            .replaceAll("\r", TextFormatting.DARK_AQUA + "\\r" + TextFormatting.DARK_GREEN)
                            .replaceAll("\t", TextFormatting.DARK_AQUA + "\\t" + TextFormatting.DARK_GREEN)
                            .replaceAll("\b", TextFormatting.DARK_AQUA + "\\b" + TextFormatting.DARK_GREEN)
                            .replaceAll("\f", TextFormatting.DARK_AQUA + "\\f" + TextFormatting.DARK_GREEN);
        }

        return MessageFormat.format("{0}{1}{2}[{3}{4}]", TextFormatting.YELLOW, text, TextFormatting.GRAY, format(obj), TextFormatting.GRAY);
    }

    public static String formatClass(String text, Object obj, Object... objects) {
        StringBuilder sb = new StringBuilder();

//        sb.append(TextFormatting.DARK_AQUA).append(text);
        sb.append(TextFormatting.YELLOW).append(text);
        sb.append(TextFormatting.GRAY).append("[");
        if (obj instanceof String) {
            sb.append(TextFormatting.DARK_GREEN).append(obj.toString()
                    .replaceAll("\\\\", TextFormatting.DARK_AQUA + "\\\\" + TextFormatting.DARK_GREEN)
                    .replaceAll("\n", TextFormatting.DARK_AQUA + "\\n" + TextFormatting.DARK_GREEN)
                    .replaceAll("\r", TextFormatting.DARK_AQUA + "\\r" + TextFormatting.DARK_GREEN)
                    .replaceAll("\t", TextFormatting.DARK_AQUA + "\\t" + TextFormatting.DARK_GREEN)
                    .replaceAll("\b", TextFormatting.DARK_AQUA + "\\b" + TextFormatting.DARK_GREEN)
                    .replaceAll("\f", TextFormatting.DARK_AQUA + "\\f" + TextFormatting.DARK_GREEN));
        } else {
            sb.append(format(obj));
        }
        
        for (Object object : objects) {
            sb.append(TextFormatting.GRAY).append(", ");
            if (object instanceof String) {
                sb.append(TextFormatting.DARK_GREEN).append(object.toString()
                        .replaceAll("\\\\", TextFormatting.DARK_AQUA + "\\\\" + TextFormatting.DARK_GREEN)
                        .replaceAll("\n", TextFormatting.DARK_AQUA + "\\n" + TextFormatting.DARK_GREEN)
                        .replaceAll("\r", TextFormatting.DARK_AQUA + "\\r" + TextFormatting.DARK_GREEN)
                        .replaceAll("\t", TextFormatting.DARK_AQUA + "\\t" + TextFormatting.DARK_GREEN)
                        .replaceAll("\b", TextFormatting.DARK_AQUA + "\\b" + TextFormatting.DARK_GREEN)
                        .replaceAll("\f", TextFormatting.DARK_AQUA + "\\f" + TextFormatting.DARK_GREEN));
            } else {
                sb.append(format(object));
            }

        }

        sb.append(TextFormatting.GRAY).append("]");

        return sb.toString();
    }

    public static String formatClassRaw(String text) {
        return MessageFormat.format("{0}[]", text);
    }

    public static String formatClassRaw(String text, Object obj) {
        if (obj instanceof String) {
            return TextFormatting.DARK_GREEN + obj.toString()
                    .replaceAll("\\\\", "\\\\")
                    .replaceAll("\n", "\\n")
                    .replaceAll("\r", "\\r")
                    .replaceAll("\t", "\\t")
                    .replaceAll("\b", "\\b")
                    .replaceAll("\f", "\\f");
        }
        return MessageFormat.format("{0}[{1}]", text, formatRaw(obj));
    }

    public static String formatClassRaw(String text, Object obj, Object... objects) {
        StringBuilder sb = new StringBuilder();

//        sb.append(TextFormatting.DARK_AQUA).append(text);
        sb.append(text);
        sb.append("[");
        sb.append(formatRaw(obj));

        for (Object object : objects) {
            sb.append(", ");
            if (object instanceof String) {
                sb.append(object.toString()
                        .replaceAll("\\\\", "\\\\")
                        .replaceAll("\n", "\\n")
                        .replaceAll("\r", "\\r")
                        .replaceAll("\t", "\\t")
                        .replaceAll("\b", "\\b")
                        .replaceAll("\f", "\\f"));
            } else {
                sb.append(formatRaw(object));
            }
        }

        sb.append("]");

        return sb.toString();
    }

    public static String formatRaw(Object obj) {
        return TextUtils.stripFormatting(format(obj));
    }

    public static String format(Object obj) {
        StringBuilder sb = new StringBuilder();

        if (obj == null) {
            sb.append(TextFormatting.LIGHT_PURPLE);
            sb.append("null");
        } else if (obj instanceof Value) {
            return formatClass("InternalObject", obj.toString());
        } else if (obj instanceof String) {
            sb.append(TextFormatting.DARK_GREEN);
            sb.append("\"");
            sb.append(obj.toString()
                    .replaceAll("\\\\", TextFormatting.DARK_AQUA + "\\\\" + TextFormatting.DARK_GREEN)
                    .replaceAll("\n", TextFormatting.DARK_AQUA + "\\n" + TextFormatting.DARK_GREEN)
                    .replaceAll("\r", TextFormatting.DARK_AQUA + "\\r" + TextFormatting.DARK_GREEN)
                    .replaceAll("\t", TextFormatting.DARK_AQUA + "\\t" + TextFormatting.DARK_GREEN)
                    .replaceAll("\b", TextFormatting.DARK_AQUA + "\\b" + TextFormatting.DARK_GREEN)
                    .replaceAll("\f", TextFormatting.DARK_AQUA + "\\f" + TextFormatting.DARK_GREEN));
            sb.append("\"");
        } else if (obj instanceof Character) {
            sb.append(TextFormatting.DARK_GREEN);
            sb.append("'");
            if (obj.equals('\\')) {
                sb.append(TextFormatting.DARK_AQUA);
                sb.append("\\\\");
            } else if (obj.equals('\n')) {
                sb.append(TextFormatting.DARK_AQUA);
                sb.append("\\n");
            } else if (obj.equals('\r')) {
                sb.append(TextFormatting.DARK_AQUA);
                sb.append("\\r");
            } else if (obj.equals('\t')) {
                sb.append(TextFormatting.DARK_AQUA);
                sb.append("\\t");
            } else if (obj.equals('\b')) {
                sb.append(TextFormatting.DARK_AQUA);
                sb.append("\\b");
            } else if (obj.equals('\f')) {
                sb.append(TextFormatting.DARK_AQUA);
                sb.append("\\f");
            } else {
                sb.append(obj.toString());
            }
            sb.append(TextFormatting.DARK_GREEN);
            sb.append("'");
        } else if (obj instanceof Integer) {
            sb.append(TextFormatting.GOLD);
            sb.append(obj.toString());
        } else if (obj instanceof Short) {
            sb.append(TextFormatting.GOLD);
            sb.append(obj.toString());
            sb.append("s");
        } else if (obj instanceof Byte) {
            sb.append(TextFormatting.GOLD);
            sb.append(obj.toString());
            sb.append("b");
        } else if (obj instanceof Long) {
            sb.append(TextFormatting.GOLD);
            sb.append(obj.toString());
            sb.append("L");
        } else if (obj instanceof Float) {
            sb.append(TextFormatting.GOLD);
            sb.append(obj.toString());
            sb.append("f");
        } else if (obj instanceof Double) {
            sb.append(TextFormatting.GOLD);
            sb.append(obj.toString());
            sb.append("d");
        } else if (obj instanceof Boolean) {
            sb.append(TextFormatting.LIGHT_PURPLE);
            sb.append(obj.toString());
        } else if (obj instanceof Enum<?>) {
            Enum<?> e = (Enum<?>) obj;

            sb.append(TextFormatting.YELLOW).append(e.getClass().getSimpleName());
            sb.append(TextFormatting.GOLD).append(e);
        } else if (obj instanceof java.util.List) {
            sb.append(TextFormatting.YELLOW).append(obj.getClass().getSimpleName());
            sb.append(TextFormatting.GRAY);
            sb.append("[");

            java.util.List<?> list = (List<?>) obj;

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
            sb.append(TextFormatting.YELLOW).append(obj.getClass().getSimpleName());
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
            sb.append(TextFormatting.YELLOW).append(obj.getClass().getSimpleName());
            sb.append(TextFormatting.GRAY);
            sb.append("{");

            Map<?, ?> map = (Map<?, ?>) obj;

            Iterator<? extends Map.Entry<?, ?>> it = map.entrySet().iterator();
            if (! it.hasNext()) {
                sb.append("}");
                return sb.toString();
            }

            while (true) {
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
            sb.append(TextFormatting.GRAY);
        } else if (obj instanceof Vector2f) {
            Vector2f v = (Vector2f) obj;
            return formatClass("Vector2f", v.x, v.y);
        } else if (obj instanceof Vector3f) {
            Vector3f v = (Vector3f) obj;
            return formatClass("Vector3f", v.getX(), v.getY(), v.getZ());
        } else if (obj instanceof Vector3d) {
            Vector3d v = (Vector3d) obj;
            return formatClass("Vector3d", v.getX(), v.getY(), v.getZ());
        } else if (obj instanceof Vector4f) {
            Vector4f v = (Vector4f) obj;
            return formatClass("Vector4f", v.getX(), v.getY(), v.getZ(), v.getW());
        } else if (obj instanceof BlockPos) {
            BlockPos v = (BlockPos) obj;
            return formatClass("BlockPos", v.getX(), v.getY(), v.getZ());
        } else if (obj instanceof Vector3i) {
            Vector3i v = (Vector3i) obj;
            return formatClass("Vector3i", v.getX(), v.getY(), v.getZ());
        } else if (obj instanceof Color) {
            Color c = (Color) obj;
            sb.append(TextFormatting.GRAY).append("#");
            sb.append(TextFormatting.GOLD);
            String s = Integer.toHexString(c.getRGB());
            for (int i = 0; i < 8 - s.length(); i++) {
                sb.append("0");
            }

            sb.append(s);
        } else if (obj instanceof TranslationTextComponent) {
            TranslationTextComponent e = (TranslationTextComponent) obj;
            return formatClass(obj.getClass().getSimpleName(), e.getKey());
        } else if (obj instanceof ITextComponent) {
            ITextComponent e = (ITextComponent) obj;
            return formatClass(obj.getClass().getSimpleName(), e.getString());
        } else if (obj instanceof Formattable) {
            Formattable e = (Formattable) obj;
            sb.append(e.toFormattedString());
        } else if (obj instanceof ResourceLocation) {
            ResourceLocation rl = (ResourceLocation) obj;
            sb.append(TextFormatting.WHITE).append(rl.getNamespace());
            sb.append(TextFormatting.GRAY).append(":");
            sb.append(TextFormatting.YELLOW).append(rl.getPath());
        } else if (obj instanceof UUID) {
            UUID uuid = (UUID) obj;
            sb.append(TextFormatting.GOLD).append(uuid.toString().replaceAll("-", TextFormatting.WHITE + "-" + TextFormatting.GOLD));
        } else if (obj instanceof Collection<?>) {
            sb.append(TextFormatting.GRAY);
            sb.append("(");

            Collection<?> collection = (Collection<?>) obj;

            Iterator<?> it = collection.iterator();
            if (!it.hasNext()) {
                sb.append(")");
                return sb.toString();
            }

            for (; ; ) {
                Object e = it.next();
                sb.append(e == collection ? (TextFormatting.GRAY + "(" + TextFormatting.WHITE + "this Collection" + TextFormatting.GRAY + ")") : format(e));
                if (!it.hasNext()) {
                    return sb.append(TextFormatting.GRAY).append(')').toString();
                }
                sb.append(TextFormatting.GRAY).append(',').append(' ');
            }
        } else {
            return TextFormatting.WHITE + obj.toString();
        }
        return sb.toString();
    }
}
