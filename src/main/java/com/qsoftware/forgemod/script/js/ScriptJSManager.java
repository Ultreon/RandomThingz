package com.qsoftware.forgemod.script.js;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.modlib.common.geom.RectangleUV;
import com.qsoftware.modlib.common.maps.SequencedHashMap;
import com.qsoftware.scriptjs.Server;
import com.qsoftware.scriptjs.World;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import mezz.jei.events.PlayerJoinedWorldEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.script.ScriptEngine;
import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.*;

@Mod.EventBusSubscriber(modid = QForgeMod.modId)
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class ScriptJSManager {
    static final NashornScriptEngineFactory factory = new NashornScriptEngineFactory();

    private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS:Manager");
    private static final HashMap<UUID, ServerScriptJSInstance> mapping = new HashMap<>();
    private static final List<Class<?>> classes = new ArrayList<>();
    private static final UUID CHAT_UUID = UUID.nameUUIDFromBytes(ScriptJSManager.class.getName().getBytes());
    private static final Set<String> exclusions = new HashSet<>();
    private static final Set<String> packages = new HashSet<>();
    private static ClientScriptJSInstance clientInstance;

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

        addClassExclusion(System.class);
        addClassExclusion(Runtime.class);
        addClassExclusion(Class.class);
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

    public static ClientScriptJSInstance getClientInstance() {
        return clientInstance;
    }

    static class QFMClassFilter implements ClassFilter {
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
            for (String package_ : packages) {
                if (s.startsWith(package_ + ".")) {
                    return true;
                }
            }
            return false;
        }
    }

    public static ServerScriptJSInstance getOrCreateInstance(ServerPlayerEntity player) {
        ServerScriptJSInstance scriptEngine;
        if (!mapping.containsKey(player.getUniqueID())) {
            QForgeMod.LOGGER.debug("Script engine not created yet, creating new one.");
            ScriptEngine engine = factory.getScriptEngine(new String[]{}, ScriptJSManager.class.getClassLoader(), new QFMClassFilter());

            scriptEngine = new ServerScriptJSInstance(player, engine);

            QForgeMod.LOGGER.debug("Evaluating startup script.");
            mapping.put(player.getUniqueID(), scriptEngine);
        } else {
            QForgeMod.LOGGER.debug("Getting last used script engine.");
            scriptEngine = mapping.get(player.getUniqueID());
        }
        return scriptEngine;
    }

    private static String getStartupScript(PlayerEntity player) {
        return null;
    }

    public static <T> Class<T> registerScriptClass(Class<T> clazz) {
        classes.add(clazz);
        return clazz;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (QForgeMod.isClientSide()) {
            if (clientInstance == null) {
                generateClientInstance();
            }
        }
    }

    private static void generateClientInstance() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

        ClientScriptJSInstance jsInstance;
        QForgeMod.LOGGER.debug("Script engine not created yet, creating new one.");
        ScriptEngine engine = factory.getScriptEngine(new String[]{}, ScriptJSManager.class.getClassLoader(), new QFMClassFilter());

        jsInstance = new ClientScriptJSInstance(player, engine);
        clientInstance = jsInstance;
    }
}
