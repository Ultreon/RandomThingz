package com.qsoftware.forgemod.script.js;

import com.google.common.collect.*;
import com.mojang.datafixers.util.Either;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.modlib.silentlib.util.Lazy;
import com.qsoftware.scriptjs.*;
import jdk.internal.dynalink.beans.StaticClass;
import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameType;
import net.minecraft.world.LightType;
import net.minecraft.world.gen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.script.ScriptEngine;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClientScriptJSInstance extends AbstractScriptJSInstance {
    private final ClientPlayerEntity player;
    @Getter private final ScriptEngine engine;
    @Getter private final ScriptJS scriptJS;

    private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS:Client");

    ClientScriptJSInstance(ClientPlayerEntity player, ScriptEngine engine) {
        this.player = player;
        this.engine = engine;
        this.scriptJS = new ScriptJS(this);
        
        this.engine.put("player", new ClientPlayer(player));
        if (QForgeMod.isClientSide()) {
            this.engine.put("client", new Client());
        }
        if (QForgeMod.isServerSide()) {
            this.engine.put("server", new Server());
        }
        this.engine.put("script", this.scriptJS);

        // Functions
        this.engine.put("import", (Consumer<String>) this.scriptJS::importFile);
        this.engine.put("run", (RunFile) this.scriptJS::runFile);
        this.engine.put("put", (BiConsumer<String, Object>) this.engine::put);
        this.engine.put("get", (Function<String, Object>) this.engine::get);
        this.engine.put("console", (Consumer<String>) this::console);
        this.engine.put("command", (Consumer<String>) this::command);
        this.engine.put("world", (Supplier<World>) () -> new ClientWorld(Minecraft.getInstance().world));
        this.engine.put("pos", (Supplier<BlockPos>) player::getPosition);

        this.engine.put("System", new ClientSystemOverride(player));
        try {
            // General classes
            if (QForgeMod.isClientSide()) {
                this.initClient(this.engine);
            }
            if (QForgeMod.isServerSide()) {
                this.initServer(this.engine);
            }

            // MC Generic
            this.engine.put("SoundEvent", toNashornClass(SoundEvent.class));
            this.engine.put("PlayerInfo", toNashornClass(PlayerInfo.class));
            this.engine.put("WorldInfo", toNashornClass(WorldInfo.class));
            this.engine.put("Attribute", toNashornClass(Attribute.class));
            this.engine.put("HandSide", toNashornClass(HandSide.class));
            this.engine.put("Server", toNashornClass(Server.class));
            this.engine.put("World", toNashornClass(World.class));
            this.engine.put("Raid", toNashornClass(Raid.class));
            this.engine.put("RaidManager", toNashornClass(RaidManager.class));
            this.engine.put("Biome", toNashornClass(Biome.class));
            this.engine.put("Fluid", toNashornClass(Fluid.class));
            this.engine.put("Block", toNashornClass(Block.class));
            this.engine.put("Item", toNashornClass(Item.class));
            this.engine.put("Hand", toNashornClass(Hand.class));
            this.engine.put("Enchantment", toNashornClass(Enchantment.class));

            // MC Entities
            this.engine.put("Player", toNashornClass(ServerPlayer.class));
            this.engine.put("EnderDragon", toNashornClass(EnderDragon.class));
            this.engine.put("Raider", toNashornClass(Raider.class));
            this.engine.put("Patroller", toNashornClass(Patroller.class));
            this.engine.put("Monster", toNashornClass(Monster.class));
            this.engine.put("Creature", toNashornClass(Creature.class));
            this.engine.put("Mob", toNashornClass(Mob.class));
            this.engine.put("LivingBeing", toNashornClass(LivingBeing.class));
            this.engine.put("Entity", toNashornClass(Entity.class));

            // MC Instances
            this.engine.put("DifficultyInstance", toNashornClass(DifficultyInstance.class));
            this.engine.put("EffectInstance", toNashornClass(EffectInstance.class));

            // MC Types
            this.engine.put("CreatureType", toNashornClass(CreatureAttribute.class));
            this.engine.put("PlacementType", toNashornClass(EntitySpawnPlacementRegistry.PlacementType.class));
            this.engine.put("EntityType", toNashornClass(EntityType.class));
            this.engine.put("GameType", toNashornClass(GameType.class));
            this.engine.put("LightType", toNashornClass(LightType.class));
            this.engine.put("HeightmapType", toNashornClass(Heightmap.Type.class));
            this.engine.put("PlayerPart", toNashornClass(PlayerModelPart.class));

            // MC Values
            this.engine.put("ItemStack", toNashornClass(ItemStack.class));
            this.engine.put("FluidState", toNashornClass(FluidState.class));
            this.engine.put("BlockState", toNashornClass(BlockState.class));
            this.engine.put("BlockPos", toNashornClass(BlockPos.class));

            // Vectors
            this.engine.put("vector4f", toNashornClass(Vector4f.class));
            this.engine.put("vector3d", toNashornClass(Vector3d.class));
            this.engine.put("vector3f", toNashornClass(Vector3f.class));
            this.engine.put("vector3i", toNashornClass(Vector3i.class));
            this.engine.put("vector2f", toNashornClass(Vector2f.class));

            // MC Advanced Objects
            this.engine.put("AttributeModifier", toNashornClass(AttributeModifier.class));
            this.engine.put("TextFormatting", toNashornClass(TextFormatting.class));
            this.engine.put("ChatColor", toNashornClass(TextFormatting.class));
            this.engine.put("ResourceLocation", toNashornClass(ResourceLocation.class));

            // Java
            this.engine.put("Multimap", toNashornClass(HashMultimap.class));
            this.engine.put("Multiset", toNashornClass(HashMultiset.class));
            this.engine.put("LinkedMap", toNashornClass(LinkedHashMap.class));
            this.engine.put("LinkedSet", toNashornClass(LinkedHashSet.class));
            this.engine.put("LinkedMultimap", toNashornClass(LinkedHashMultimap.class));
            this.engine.put("LinkedMultiset", toNashornClass(LinkedHashMultiset.class));
            this.engine.put("IdentityMap", toNashornClass(IdentityHashMap.class));
            this.engine.put("WeakMap", toNashornClass(WeakHashMap.class));
            this.engine.put("BiMap", toNashornClass(HashBiMap.class));
            this.engine.put("EnumBiMap", toNashornClass(EnumHashBiMap.class));
            this.engine.put("LinkedMultimap", toNashornClass(LinkedHashMultimap.class));
            this.engine.put("LinkedMultiset", toNashornClass(LinkedHashMultiset.class));
            this.engine.put("ListMultimap", toNashornClass(ArrayListMultimap.class));
            this.engine.put("StringBuilder", toNashornClass(StringBuilder.class));
            this.engine.put("StringBuffer", toNashornClass(StringBuffer.class));
            this.engine.put("PacketBuffer", toNashornClass(PacketBuffer.class));

            // Core classes
            this.engine.put("int8", toNashornClass(Byte.class));
            this.engine.put("int16", toNashornClass(Short.class));
            this.engine.put("int32", toNashornClass(Integer.class));
            this.engine.put("int64", toNashornClass(Long.class));
            this.engine.put("byte", toNashornClass(Byte.class));
            this.engine.put("uuid", toNashornClass(UUID.class));
            this.engine.put("short", toNashornClass(Short.class));
            this.engine.put("int", toNashornClass(Integer.class));
            this.engine.put("long", toNashornClass(Long.class));
            this.engine.put("double", toNashornClass(Double.class));
            this.engine.put("float", toNashornClass(Float.class));
            this.engine.put("float32", toNashornClass(Float.class));
            this.engine.put("float64", toNashornClass(Double.class));
            this.engine.put("string", toNashornClass(String.class));
            this.engine.put("char", toNashornClass(Character.class));
            this.engine.put("either", toNashornClass(Either.class));
            this.engine.put("lazy", toNashornClass(Lazy.class));
            this.engine.put("map", toNashornClass(HashMap.class));
            this.engine.put("set", toNashornClass(HashSet.class));
            this.engine.put("table", toNashornClass(HashBasedTable.class));
            this.engine.put("list", toNashornClass(ArrayList.class));
            this.engine.put("optional", toNashornClass(Optional.class));
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            QForgeMod.LOGGER.debug("Startup script exception occurred:");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private void initClient(ScriptEngine engine) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.engine.put("ClientWorld", toNashornClass(ClientWorld.class));
        this.engine.put("ClientPlayer", toNashornClass(ClientPlayer.class));
        this.engine.put("ClientWorldInfo", toNashornClass(ClientWorldInfo.class));
    }

    @SuppressWarnings("unused")
    private void initServer(ScriptEngine engine) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.engine.put("ServerWorld", toNashornClass(ServerWorld.class));
        this.engine.put("ServerPlayer", toNashornClass(ServerPlayer.class));
    }

    public StaticClass toNashornClass(Class<?> c) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> cl = Class.forName("jdk.internal.dynalink.beans.StaticClass");

        Constructor<?> constructor = cl.getDeclaredConstructor(Class.class);
        constructor.setAccessible(true);

        return (StaticClass) constructor.newInstance(c);
    }

    public Either<Exception, Object> eval(String script) {
        try {
            return Either.right(this.engine.eval(script));
        } catch (Exception e) {
            return Either.left(e);
        }
    }

    @Override
    public PlayerEntity getPlayer() {
        return player;
    }

    private void console(String command) {
        ServerScriptJSUtils.getServer().getCommandManager().handleCommand(ServerScriptJSUtils.getServer().getCommandSource(), command);
    }

    private void command(String command) {
        ServerScriptJSUtils.getServer().getCommandManager().handleCommand(player.getCommandSource(), command);
    }
}
