package com.qsoftware.forgemod.script;

import com.google.common.base.Charsets;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.modlib.common.geom.RectangleUV;
import com.qsoftware.modlib.common.maps.SequencedHashMap;
import com.qsoftware.scriptjs.Server;
import com.qsoftware.scriptjs.World;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.EnvironmentAccess;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;
import org.jline.utils.WriterOutputStream;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.*;

@Mod.EventBusSubscriber(modid = QForgeMod.modId)
@SuppressWarnings({"UnusedReturnValue", "unused", "MismatchedQueryAndUpdateOfCollection"})
public class ScriptManager {
    private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS:Manager");
    private static final HashMap<UUID, ServerScriptInstance> mapping = new HashMap<>();
    private static final List<Class<?>> classes = new ArrayList<>();
    private static final UUID CHAT_UUID = UUID.nameUUIDFromBytes(ScriptManager.class.getName().getBytes());
    private static final Set<String> exclusions = new HashSet<>();
    private static final Set<String> packages = new HashSet<>();
    private static ClientScriptInstance clientInstance;

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

    public static ClientScriptInstance getClientInstance() {
        return clientInstance;
    }

    public static Context.Builder newContext(Dist dist) {
        return Context.newBuilder()
                .allowHostAccess(HostAccess.newBuilder()
                        .allowPublicAccess(true)
                        .denyAccess(Class.class)
                        .denyAccess(File.class)
                        .denyAccess(URL.class)
                        .denyAccess(URI.class)
                        .denyAccess(Path.class)
                        .denyAccess(Socket.class)
                        .allowArrayAccess(true).build())
                .allowHostClassLoading(false)
                .allowIO(false)
                .allowCreateProcess(false)
                .allowAllAccess(true);
    }

    public static ServerScriptInstance getOrCreateInstance(ServerPlayerEntity player) {
        ServerScriptInstance scriptEngine;
        if (!mapping.containsKey(player.getUniqueID())) {
            QForgeMod.LOGGER.debug("Script engine not created yet, creating new one.");

            Context context = newContext(Dist.DEDICATED_SERVER)
                    .out(new WriterOutputStream(new ServerSystemOverride(Language.JS, player).out, Charsets.UTF_8))
                    .err(new WriterOutputStream(new ServerSystemOverride(Language.JS, player).err, Charsets.UTF_8)).build();
            scriptEngine = new ServerScriptInstance(Language.JS, player, context);

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

        ClientScriptInstance jsInstance;
        QForgeMod.LOGGER.debug("Script engine not created yet, creating new one.");
        Context context = newContext(Dist.CLIENT)
                .out(new WriterOutputStream(new ClientSystemOverride(Language.JS, player).out, Charsets.UTF_8))
                .err(new WriterOutputStream(new ClientSystemOverride(Language.JS, player).err, Charsets.UTF_8)).build();
        jsInstance = new ClientScriptInstance(Language.JS, player, context);
        clientInstance = jsInstance;
    }
}
