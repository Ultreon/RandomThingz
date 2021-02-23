package com.qsoftware.forgemod.script;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.network.Network;
import com.qsoftware.forgemod.network.ScriptResponsePacket;
import com.qsoftware.mcscript.*;
import com.qsoftware.modlib.common.geom.RectangleUV;
import com.qsoftware.modlib.common.maps.SequencedHashMap;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.*;

public class ScriptManager {
    private static final HashMap<UUID, ScriptEngine> mapping = new HashMap<>();
    private static final NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
    private static final List<Class<?>> classes = new ArrayList<>();
    private static final UUID CHAT_UUID = UUID.nameUUIDFromBytes(ScriptManager.class.getName().getBytes());
    private static final Set<String> exclusions = new HashSet<>();
    private static final Set<String> packages = new HashSet<>();

    static {
        registerScriptClass(World.class);
        registerScriptClass(Server.class);
        registerScriptClass(UUID.class);
        registerScriptClass(Byte.class);
        registerScriptClass(Short.class);
        registerScriptClass(Integer.class);
        registerScriptClass(Long.class);
        registerScriptClass(BigInteger.class);
        registerScriptClass(BigDecimal.class);
        registerScriptClass(Math.class);
        registerScriptClass(Random.class);
        registerScriptClass(String.class);
        registerScriptClass(StringBuilder.class);
        registerScriptClass(Double.class);
        registerScriptClass(Float.class);
        registerScriptClass(Rectangle.class);
        registerScriptClass(RectangleUV.class);
        registerScriptClass(SequencedHashMap.class);
        registerScriptClass(org.apache.commons.collections.SequencedHashMap.class);
        registerScriptClass(com.qsoftware.forgemod.common.java.maps.SequencedHashMap.class);
        registerScriptClass(com.qsoftware.forgemod.common.geom.RectangleUV.class);
        registerScriptClass(BlockPos.class);
        registerScriptClass(ResourceLocation.class);

        registerScriptPackage("java.lang");
        registerScriptPackage("java.util");
        registerScriptPackage("com.qsoftware.forgemod.common.geom");
        registerScriptPackage("com.qsoftware.modlib.common.geom");
        registerScriptPackage("com.qsoftware.mcscript");
        registerScriptPackage("net.minecraft.util.math");
        registerScriptPackage("net.minecraft.util.text");

        addClassExclusion(System.class);
        addClassExclusion(Runtime.class);
    }

    private static void addClassExclusion(Class<?> clazz) {
        exclusions.add(clazz.getName());
    }

    private static void addClassExclusion(String clazz) {
        exclusions.add(clazz);
    }

    private static void registerScriptPackage(Package pak) {
        packages.add(pak.getName());
    }

    private static void registerScriptPackage(String s) {
        packages.add(s);
    }

    static class MyCF implements ClassFilter {
        @Override
        public boolean exposeToScripts(String s) {
            if (exclusions.contains(s)) {
                return false;
            }

            for (Class<?> clazz : classes) {
                if (s.equals(clazz.getName())) {
                    return true;
                }
            }
            if (s.startsWith("java.util.")) {
                return true;
            }
            if (s.startsWith("java.lang.")) {
                return true;
            }
            if (s.startsWith("com.qsoftware.forgemod.common.geom.")) {
                return true;
            }
            if (s.startsWith("com.qsoftware.modlib.common.geom.")) {
                return true;
            }
            if (s.startsWith("com.google.common.collect.")) {
                return true;
            }
            if (s.startsWith("net.minecraft.util.math.")) {
                return true;
            }
            if (s.startsWith("net.minecraft.util.text.")) {
                return true;
            }
            return s.startsWith("org.apache.commons.collections.");
        }
    }

    public static StaticClass toNashornClass(Class<?> c) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> cl = Class.forName("jdk.internal.dynalink.beans.StaticClass");

        Constructor<?> constructor = cl.getDeclaredConstructor(Class.class);
        constructor.setAccessible(true);

        return (StaticClass) constructor.newInstance(c);
    }

    public static ScriptEngine get(ServerPlayerEntity player) {
        ScriptEngine scriptEngine;
        if (!mapping.containsKey(player.getUniqueID())) {
            QForgeMod.LOGGER.debug("Script engine not created yet, creating new one.");
            scriptEngine = factory.getScriptEngine(new String[]{}, Player.class.getClassLoader(), new MyCF());
            scriptEngine.put("player", new Player(player.getUniqueID()));
            scriptEngine.put("server", new Server());
            scriptEngine.put("script", new Script(scriptEngine));
            try {
                scriptEngine.put("Player", toNashornClass(Player.class));
                scriptEngine.put("Entity", toNashornClass(Entity.class));
                scriptEngine.put("Fluid", toNashornClass(Fluid.class));
                scriptEngine.put("Block", toNashornClass(Block.class));
                scriptEngine.put("Item", toNashornClass(Item.class));
                scriptEngine.put("ItemStack", toNashornClass(ItemStack.class));
                scriptEngine.put("FluidState", toNashornClass(FluidState.class));
                scriptEngine.put("BlockState", toNashornClass(BlockState.class));
                scriptEngine.put("LivingEntity", toNashornClass(LivingEntity.class));
                scriptEngine.put("EffectInstance", toNashornClass(EffectInstance.class));
                scriptEngine.put("Effects", toNashornClass(Effects.class));
                scriptEngine.put("GameType", toNashornClass(GameType.class));
                scriptEngine.put("Hand", toNashornClass(Hand.class));
                scriptEngine.put("Attribute", toNashornClass(Attribute.class));
                scriptEngine.put("AttributeModifier", toNashornClass(AttributeModifier.class));
                scriptEngine.put("Enchantment", toNashornClass(Enchantment.class));
                scriptEngine.put("CreatureType", toNashornClass(CreatureAttribute.class));
                scriptEngine.put("PlacementType", toNashornClass(EntitySpawnPlacementRegistry.PlacementType.class));
                scriptEngine.put("EntityType", toNashornClass(EntityType.class));
                scriptEngine.put("HandSide", toNashornClass(HandSide.class));
                scriptEngine.put("TextFormatting", toNashornClass(TextFormatting.class));
                scriptEngine.put("ChatColor", toNashornClass(TextFormatting.class));
                scriptEngine.put("ResourceLocation", toNashornClass(ResourceLocation.class));
                scriptEngine.put("BlockPos", toNashornClass(BlockPos.class));
                scriptEngine.put("Server", toNashornClass(Server.class));
                scriptEngine.put("World", toNashornClass(World.class));
                scriptEngine.put("System", new SystemOverride(player));
                scriptEngine.put("Byte", toNashornClass(Byte.class));
                scriptEngine.put("Short", toNashornClass(Short.class));
                scriptEngine.put("Int", toNashornClass(Integer.class));
                scriptEngine.put("Long", toNashornClass(Long.class));
                scriptEngine.put("Double", toNashornClass(Double.class));
                scriptEngine.put("Float", toNashornClass(Float.class));
                scriptEngine.put("String", toNashornClass(String.class));
                scriptEngine.put("Char", toNashornClass(Character.class));
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                QForgeMod.LOGGER.debug("Startup script exception occurred:");
                e.printStackTrace();
                return scriptEngine;
            }
            try {
                QForgeMod.LOGGER.debug("Evaluating startup script.");
                scriptEngine.eval(getStartupScript(player));
                mapping.put(player.getUniqueID(), scriptEngine);
            } catch (ScriptException e) {
                QForgeMod.LOGGER.debug("Startup script exception occurred:");
                e.printStackTrace();
                Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.RED + e.getMessage()));
                return scriptEngine;
            }
        } else {
            QForgeMod.LOGGER.debug("Getting last used script engine.");
            scriptEngine = mapping.get(player.getUniqueID());
        }
        return scriptEngine;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private static String getStartupScript(PlayerEntity player) {
        StringBuilder sb = new StringBuilder();
        sb.append("var byte = Java.type(\"java.lang.Byte\");");
        sb.append("var short = Java.type(\"java.lang.Short\");");
        sb.append("var int = Java.type(\"java.lang.Integer\");");
        sb.append("var integer = Java.type(\"java.lang.Integer\");");
        sb.append("var long = Java.type(\"java.lang.Long\");");
        sb.append("var int8 = Java.type(\"java.lang.Byte\");");
        sb.append("var int16 = Java.type(\"java.lang.Short\");");
        sb.append("var int32 = Java.type(\"java.lang.Integer\");");
        sb.append("var int64 = Java.type(\"java.lang.Long\");");
        sb.append("var double = Java.type(\"java.lang.Double\");");
        sb.append("var float = Java.type(\"java.lang.Float\");");
        sb.append("var float32 = Java.type(\"java.lang.Float\");");
        sb.append("var float64 = Java.type(\"java.lang.Double\");");
        sb.append("var string = Java.type(\"java.lang.String\");");
        sb.append("var str = Java.type(\"java.lang.String\");");
        sb.append("var character = Java.type(\"java.lang.Character\");");
        sb.append("var char = Java.type(\"java.lang.Character\");");
        sb.append("var UUID = Java.type(\"java.util.UUID\");");
        sb.append("var HashMap = Java.type(\"java.util.HashMap\");");
        sb.append("var Map = Java.type(\"java.util.Map\");");
        sb.append("var Maps = Java.type(\"com.google.common.collect.Maps\");");
        sb.append("var HashSet = Java.type(\"java.util.HashSet\");");
        sb.append("var Set = Java.type(\"java.util.Set\");");
        sb.append("var Sets = Java.type(\"com.google.common.collect.Sets\");");
        sb.append("var ArrayList = Java.type(\"java.util.ArrayList\");");
        sb.append("var List = Java.type(\"java.util.List\");");
        sb.append("var Lists = Java.type(\"com.google.common.collect.Lists\");");
        sb.append("var Arrays = Java.type(\"java.util.Arrays\");");
        sb.append("var Collection = Java.type(\"java.util.Collection\");");
        sb.append("var Collections = Java.type(\"java.util.Collections\");");
        return sb.toString();
    }

    public static <T> Class<T> registerScriptClass(Class<T> clazz) {
        classes.add(clazz);
        return clazz;
    }
}
