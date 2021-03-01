package com.qsoftware.forgemod.script;

import com.google.common.collect.*;
import com.mojang.datafixers.util.Either;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.modlib.silentlib.util.Lazy;
import com.qsoftware.scriptjs.*;
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
import net.minecraftforge.api.distmarker.Dist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClientScriptInstance extends AbstractScriptInstance {
    private final ClientPlayerEntity player;

    private static final Logger LOGGER = LogManager.getLogger("QFM:Script:Client");

    ClientScriptInstance(Language language, ClientPlayerEntity player, Context engine) {
        super(language, engine, Dist.CLIENT);
        this.player = player;
        init();
    }

    @Override
    protected void init() {
        Value bindings = this.context.getBindings(this.getLanguage().getId());

        bindings.putMember("player", new ServerPlayer(player.getUniqueID()));
        bindings.putMember("client", new Client());
        bindings.putMember("server", new Server());
        bindings.putMember("script", this.scriptJS);

        // Functions
        bindings.putMember("import", (Consumer<String>) this.scriptJS::importFile);
        bindings.putMember("run", (RunFile) this.scriptJS::runFile);
        bindings.putMember("put", (BiConsumer<String, Object>) bindings::putMember);
        bindings.putMember("get", (Function<String, Object>) bindings::getMember);
        bindings.putMember("console", (Consumer<String>) this::console);
        bindings.putMember("command", (Consumer<String>) this::command);
        bindings.putMember("world", (Supplier<ClientWorld>) () -> new ClientWorld(Minecraft.getInstance().world));
        bindings.putMember("pos", (Supplier<BlockPos>) player::getPosition);

        bindings.putMember("System", new ClientSystemOverride(getLanguage(), player));
        try {
            // General classes
            if (QForgeMod.isClientSide()) {
                this.initClient(bindings);
            }
            if (QForgeMod.isServerSide()) {
                this.initServer(bindings);
            }

            // MC Generic
            bindings.putMember("SoundEvent", toNashornClass(SoundEvent.class));
            bindings.putMember("PlayerInfo", toNashornClass(PlayerInfo.class));
            bindings.putMember("WorldInfo", toNashornClass(WorldInfo.class));
            bindings.putMember("Attribute", toNashornClass(Attribute.class));
            bindings.putMember("HandSide", toNashornClass(HandSide.class));
            bindings.putMember("Server", toNashornClass(Server.class));
            bindings.putMember("World", toNashornClass(World.class));
            bindings.putMember("Raid", toNashornClass(Raid.class));
            bindings.putMember("RaidManager", toNashornClass(RaidManager.class));
            bindings.putMember("Biome", toNashornClass(Biome.class));
            bindings.putMember("Fluid", toNashornClass(Fluid.class));
            bindings.putMember("Block", toNashornClass(Block.class));
            bindings.putMember("Item", toNashornClass(Item.class));
            bindings.putMember("Hand", toNashornClass(Hand.class));
            bindings.putMember("Enchantment", toNashornClass(Enchantment.class));

            // MC Entities
            bindings.putMember("Player", toNashornClass(Player.class));
            bindings.putMember("EnderDragon", toNashornClass(EnderDragon.class));
            bindings.putMember("Raider", toNashornClass(Raider.class));
            bindings.putMember("Patroller", toNashornClass(Patroller.class));
            bindings.putMember("Monster", toNashornClass(Monster.class));
            bindings.putMember("Creature", toNashornClass(Creature.class));
            bindings.putMember("Mob", toNashornClass(Mob.class));
            bindings.putMember("LivingBeing", toNashornClass(LivingBeing.class));
            bindings.putMember("Entity", toNashornClass(Entity.class));

            // MC Instances
            bindings.putMember("DifficultyInstance", toNashornClass(DifficultyInstance.class));
            bindings.putMember("EffectInstance", toNashornClass(EffectInstance.class));

            // MC Types
            bindings.putMember("CreatureType", toNashornClass(CreatureAttribute.class));
            bindings.putMember("PlacementType", toNashornClass(EntitySpawnPlacementRegistry.PlacementType.class));
            bindings.putMember("EntityType", toNashornClass(EntityType.class));
            bindings.putMember("GameType", toNashornClass(GameType.class));
            bindings.putMember("LightType", toNashornClass(LightType.class));
            bindings.putMember("HeightmapType", toNashornClass(Heightmap.Type.class));
            bindings.putMember("PlayerPart", toNashornClass(PlayerModelPart.class));

            // MC Values
            bindings.putMember("ItemStack", toNashornClass(ItemStack.class));
            bindings.putMember("FluidState", toNashornClass(FluidState.class));
            bindings.putMember("BlockState", toNashornClass(BlockState.class));
            bindings.putMember("BlockPos", toNashornClass(BlockPos.class));

            // Vectors
            bindings.putMember("vector4f", toNashornClass(Vector4f.class));
            bindings.putMember("vector3d", toNashornClass(Vector3d.class));
            bindings.putMember("vector3f", toNashornClass(Vector3f.class));
            bindings.putMember("vector3i", toNashornClass(Vector3i.class));
            bindings.putMember("vector2f", toNashornClass(Vector2f.class));

            // MC Advanced Objects
            bindings.putMember("AttributeModifier", toNashornClass(AttributeModifier.class));
            bindings.putMember("TextFormatting", toNashornClass(TextFormatting.class));
            bindings.putMember("ChatColor", toNashornClass(TextFormatting.class));
            bindings.putMember("ResourceLocation", toNashornClass(ResourceLocation.class));

            // Java
            bindings.putMember("Multimap", toNashornClass(HashMultimap.class));
            bindings.putMember("Multiset", toNashornClass(HashMultiset.class));
            bindings.putMember("LinkedMap", toNashornClass(LinkedHashMap.class));
            bindings.putMember("LinkedSet", toNashornClass(LinkedHashSet.class));
            bindings.putMember("LinkedMultimap", toNashornClass(LinkedHashMultimap.class));
            bindings.putMember("LinkedMultiset", toNashornClass(LinkedHashMultiset.class));
            bindings.putMember("IdentityMap", toNashornClass(IdentityHashMap.class));
            bindings.putMember("WeakMap", toNashornClass(WeakHashMap.class));
            bindings.putMember("BiMap", toNashornClass(HashBiMap.class));
            bindings.putMember("EnumBiMap", toNashornClass(EnumHashBiMap.class));
            bindings.putMember("LinkedMultimap", toNashornClass(LinkedHashMultimap.class));
            bindings.putMember("LinkedMultiset", toNashornClass(LinkedHashMultiset.class));
            bindings.putMember("ListMultimap", toNashornClass(ArrayListMultimap.class));
            bindings.putMember("StringBuilder", toNashornClass(StringBuilder.class));
            bindings.putMember("StringBuffer", toNashornClass(StringBuffer.class));
            bindings.putMember("PacketBuffer", toNashornClass(PacketBuffer.class));

            // Core classes
            bindings.putMember("int8", toNashornClass(Byte.class));
            bindings.putMember("int16", toNashornClass(Short.class));
            bindings.putMember("int32", toNashornClass(Integer.class));
            bindings.putMember("int64", toNashornClass(Long.class));
            bindings.putMember("byte", toNashornClass(Byte.class));
            bindings.putMember("uuid", toNashornClass(UUID.class));
            bindings.putMember("short", toNashornClass(Short.class));
            bindings.putMember("int", toNashornClass(Integer.class));
            bindings.putMember("long", toNashornClass(Long.class));
            bindings.putMember("double", toNashornClass(Double.class));
            bindings.putMember("float", toNashornClass(Float.class));
            bindings.putMember("float32", toNashornClass(Float.class));
            bindings.putMember("float64", toNashornClass(Double.class));
            bindings.putMember("string", toNashornClass(String.class));
            bindings.putMember("str", toNashornClass(String.class));
            bindings.putMember("char", toNashornClass(Character.class));
            bindings.putMember("either", toNashornClass(Either.class));
            bindings.putMember("lazy", toNashornClass(Lazy.class));
            bindings.putMember("map", toNashornClass(HashMap.class));
            bindings.putMember("set", toNashornClass(HashSet.class));
            bindings.putMember("table", toNashornClass(HashBasedTable.class));
            bindings.putMember("list", toNashornClass(ArrayList.class));
            bindings.putMember("optional", toNashornClass(Optional.class));
            bindings.putMember("reflection", toNashornClass(Reflection.class));
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            QForgeMod.LOGGER.debug("Startup script exception occurred:");
            e.printStackTrace();
        }
    }

    @Override
    protected void initClient(Value bindings) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        bindings.putMember("ClientWorld", toNashornClass(ClientWorld.class));
        bindings.putMember("ClientPlayer", toNashornClass(ClientPlayer.class));
        bindings.putMember("ClientWorldInfo", toNashornClass(ClientWorldInfo.class));
    }

    @Override
    protected void initServer(Value bindings) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        bindings.putMember("ServerWorld", toNashornClass(ServerWorld.class));
        bindings.putMember("ServerPlayer", toNashornClass(ServerPlayer.class));
    }

    public Value toNashornClass(Class<?> c) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return Value.asValue(c);
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
