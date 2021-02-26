package com.qsoftware.forgemod.script.js;

import com.google.common.collect.*;
import com.mojang.datafixers.util.Either;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.mcscript.*;
import com.qsoftware.modlib.silentlib.util.Lazy;
import jdk.internal.dynalink.beans.StaticClass;
import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ScriptJSInstance {
    @Getter private final ServerPlayerEntity player;
    @Getter private final ScriptEngine engine;
    @Getter private final ScriptJS scriptJS;

    ScriptJSInstance(ServerPlayerEntity player, ScriptEngine engine) {
        this.player = player;
        this.engine = engine;
        this.scriptJS = new ScriptJS(this);

        this.engine.put("player", new Player(player.getUniqueID()));
        this.engine.put("server", new Server());
        this.engine.put("script", this.scriptJS);

        // Functions
        this.engine.put("import", (Consumer<String>) this.scriptJS::importFile);
        this.engine.put("run", (RunFile) this.scriptJS::runFile);
        this.engine.put("put", (BiConsumer<String, Object>) this.engine::put);
        this.engine.put("get", (Function<String, Object>) this.engine::get);
        this.engine.put("console", (Consumer<String>) this::console);
        this.engine.put("command", (Consumer<String>) this::command);
        this.engine.put("world", (Consumer<String>) this::command);

        this.engine.put("system", new SystemOverride(player));
        try {
            // General classes
            this.engine.put("Player", toNashornClass(Player.class));
            this.engine.put("Entity", toNashornClass(Entity.class));
            this.engine.put("Fluid", toNashornClass(Fluid.class));
            this.engine.put("Block", toNashornClass(Block.class));
            this.engine.put("Item", toNashornClass(Item.class));
            this.engine.put("ItemStack", toNashornClass(ItemStack.class));
            this.engine.put("FluidState", toNashornClass(FluidState.class));
            this.engine.put("BlockState", toNashornClass(BlockState.class));
            this.engine.put("LivingEntity", toNashornClass(LivingEntity.class));
            this.engine.put("EffectInstance", toNashornClass(EffectInstance.class));
            this.engine.put("Effects", toNashornClass(Effects.class));
            this.engine.put("GameType", toNashornClass(GameType.class));
            this.engine.put("Hand", toNashornClass(Hand.class));
            this.engine.put("Attribute", toNashornClass(Attribute.class));
            this.engine.put("AttributeModifier", toNashornClass(AttributeModifier.class));
            this.engine.put("Enchantment", toNashornClass(Enchantment.class));
            this.engine.put("CreatureType", toNashornClass(CreatureAttribute.class));
            this.engine.put("PlacementType", toNashornClass(EntitySpawnPlacementRegistry.PlacementType.class));
            this.engine.put("EntityType", toNashornClass(EntityType.class));
            this.engine.put("HandSide", toNashornClass(HandSide.class));
            this.engine.put("TextFormatting", toNashornClass(TextFormatting.class));
            this.engine.put("ChatColor", toNashornClass(TextFormatting.class));
            this.engine.put("ResourceLocation", toNashornClass(ResourceLocation.class));
            this.engine.put("BlockPos", toNashornClass(BlockPos.class));
            this.engine.put("Server", toNashornClass(Server.class));
            this.engine.put("World", toNashornClass(World.class));
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

            // Core classes
            this.engine.put("byte", toNashornClass(Byte.class));
            this.engine.put("short", toNashornClass(Short.class));
            this.engine.put("int", toNashornClass(Integer.class));
            this.engine.put("long", toNashornClass(Long.class));
            this.engine.put("double", toNashornClass(Double.class));
            this.engine.put("float", toNashornClass(Float.class));
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

    private void console(String command) {
        ServerScriptUtils.getServer().getCommandManager().handleCommand(ServerScriptUtils.getServer().getCommandSource(), command);
    }

    private void command(String command) {
        ServerScriptUtils.getServer().getCommandManager().handleCommand(player.getCommandSource(), command);
    }
}
