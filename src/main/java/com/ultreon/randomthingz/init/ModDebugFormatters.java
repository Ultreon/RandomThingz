package com.ultreon.randomthingz.init;

import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.debug.menu.Formatter;
import com.ultreon.randomthingz.common.Angle;
import com.ultreon.randomthingz.common.enums.MoonPhase;
import com.ultreon.randomthingz.common.interfaces.Formattable;
import com.ultreon.randomthingz.registries.DbgFormatterRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.util.FastColor;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;

import java.awt.*;
import java.util.*;
import java.util.List;

import static net.minecraft.ChatFormatting.*;

@SuppressWarnings({"rawtypes", "unused"})
public class ModDebugFormatters {
    public static final Formatter<Number> NUMBER = DbgFormatterRegistry.get().register(new Formatter<>(Number.class, RandomThingz.res("java/number")) {
        @Override
        public void format(Number obj, StringBuilder sb) {
            sb.append(BLUE).append(obj);
        }
    });
    public static final Formatter<Boolean> BOOLEAN = DbgFormatterRegistry.get().register(new Formatter<>(Boolean.class, RandomThingz.res("java/boolean")) {
        @Override
        public void format(Boolean obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append(obj ? "true" : "false");
        }
    });
    public static final Formatter<String> STRING = DbgFormatterRegistry.get().register(new Formatter<>(String.class, RandomThingz.res("java/string")) {
        @Override
        public void format(String obj, StringBuilder sb) {
            sb.append(GREEN);
            sb.append("\"");
            sb.append(obj
                    .replaceAll("\\\\", AQUA + "\\\\" + GREEN)
                    .replaceAll("\n", AQUA + "\\n" + GREEN)
                    .replaceAll("\r", AQUA + "\\r" + GREEN)
                    .replaceAll("\t", AQUA + "\\t" + GREEN)
                    .replaceAll("\b", AQUA + "\\b" + GREEN)
                    .replaceAll("\f", AQUA + "\\f" + GREEN));
            sb.append("\"");
        }
    });
    public static final Formatter<Character> CHARACTER = DbgFormatterRegistry.get().register(new Formatter<>(Character.class, RandomThingz.res("java/character")) {
        @Override
        public void format(Character obj, StringBuilder sb) {
            sb.append(GREEN);
            sb.append("'");
            if (obj.equals('\\')) {
                sb.append(AQUA);
                sb.append("\\\\");
            } else if (obj.equals('\n')) {
                sb.append(AQUA);
                sb.append("\\n");
            } else if (obj.equals('\r')) {
                sb.append(AQUA);
                sb.append("\\r");
            } else if (obj.equals('\t')) {
                sb.append(AQUA);
                sb.append("\\t");
            } else if (obj.equals('\b')) {
                sb.append(AQUA);
                sb.append("\\b");
            } else if (obj.equals('\f')) {
                sb.append(AQUA);
                sb.append("\\f");
            } else {
                sb.append(obj);
            }
            sb.append(GOLD);
            sb.append("'");
        }
    });
    public static final Formatter<List> LIST = DbgFormatterRegistry.get().register(new Formatter<>(List.class, RandomThingz.res("java/list")) {
        @Override
        public void format(List obj, StringBuilder sb) {
            sb.append(GRAY);
            sb.append("[");

            Iterator<?> it = obj.iterator();
            if (!it.hasNext()) {
                sb.append("]");
                return;
            }

            while (true) {
                Object e = it.next();
                sb.append(e == obj ? (GRAY + "(" + LIGHT_PURPLE + "this List" + GRAY + ")") : format(e));
                if (!it.hasNext()) {
                    sb.append(GRAY).append(']');
                    return;
                }
                sb.append(GRAY).append(',').append(' ');
            }
        }
    });
    public static final Formatter<Map> MAP = DbgFormatterRegistry.get().register(new Formatter<>(Map.class, RandomThingz.res("java/map")) {
        @SuppressWarnings("unchecked")
        @Override
        public void format(Map obj, StringBuilder sb) {
            sb.append(GRAY);
            sb.append("{");

            Iterator<? extends Map.Entry> it = obj.entrySet().iterator();
            if (!it.hasNext()) {
                sb.append("}");
            }

            for (; ; ) {
                Map.Entry<?, ?> e = it.next();
                sb.append(e.getKey() == obj ? (GRAY + "(" + LIGHT_PURPLE + "this Map" + GRAY + ")") : format(e.getKey()));
                sb.append(GRAY).append(": ");
                sb.append(e.getValue() == obj ? (GRAY + "(" + LIGHT_PURPLE + "this Map" + GRAY + ")") : format(e.getValue()));
                if (!it.hasNext()) {
                    sb.append(GRAY).append('}');
                    return;
                }
                sb.append(GRAY).append(", ");
            }
        }
    });
    public static final Formatter<Map.Entry> MAP_ENTRY = DbgFormatterRegistry.get().register(new Formatter<>(Map.Entry.class, RandomThingz.res("java/map/entry")) {
        @Override
        public void format(Map.Entry obj, StringBuilder sb) {
            sb.append(GRAY);

            sb.append(obj.getKey() == obj ? (GRAY + "(" + LIGHT_PURPLE + "this Entry" + GRAY + ")") : format(obj.getKey()));
            sb.append(GRAY).append(": ");
            sb.append(obj.getValue() == obj ? (GRAY + "(" + LIGHT_PURPLE + "this Entry" + GRAY + ")") : format(obj.getValue()));
            sb.append(GRAY).append(", ");
        }
    });
    public static final Formatter<Set> SET = DbgFormatterRegistry.get().register(new Formatter<>(Set.class, RandomThingz.res("java/set")) {
        @Override
        public void format(Set obj, StringBuilder sb) {
            sb.append(GRAY);
            sb.append("{");

            Iterator<?> it = obj.iterator();
            if (!it.hasNext()) {
                sb.append("}");
            }

            for (; ; ) {
                Object e = it.next();
                sb.append(e == obj ? (GRAY + "(" + LIGHT_PURPLE + "this Set" + GRAY + ")") : format(e));
                if (!it.hasNext()) {
                    sb.append(GRAY).append('}');
                    return;
                }
                sb.append(GRAY).append(',').append(' ');
            }
        }
    });
    public static final Formatter<Collection> COLLECTION = DbgFormatterRegistry.get().register(new Formatter<>(Collection.class, RandomThingz.res("java/set")) {
        @Override
        public void format(Collection obj, StringBuilder sb) {
            sb.append(GRAY);
            sb.append("(");

            Iterator<?> it = obj.iterator();
            if (!it.hasNext()) {
                sb.append(")");
            }

            for (; ; ) {
                Object e = it.next();
                sb.append(e == obj ? (GRAY + "(" + LIGHT_PURPLE + "this Collection" + GRAY + ")") : format(e));
                if (!it.hasNext()) {
                    sb.append(GRAY).append(')');
                    return;
                }
                sb.append(GRAY).append(',').append(' ');
            }
        }
    });
    public static final Formatter<UUID> UUID = DbgFormatterRegistry.get().register(new Formatter<>(UUID.class, RandomThingz.res("java/uuid")) {
        @Override
        public void format(UUID obj, StringBuilder sb) {
            sb.append(GREEN).append(obj.toString().replaceAll("-", GRAY + "-" + GREEN));
        }
    });
    public static final Formatter<Color> AWT_COLOR = DbgFormatterRegistry.get().register(new Formatter<>(Color.class, RandomThingz.res("java/awt/color")) {
        @Override
        public void format(Color obj, StringBuilder sb) {
            sb.append(GRAY).append("#");
            sb.append(BLUE);
            String s = Integer.toHexString(obj.getRGB());
            sb.append("0".repeat(8 - s.length()));

            sb.append(s);
        }
    });
    public static final Formatter<Entity> ENTITY = DbgFormatterRegistry.get().register(new Formatter<>(Entity.class, RandomThingz.res("minecraft/entity")) {
        @Override
        public void format(Entity obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("Entity");
            sb.append(WHITE).append(": ");
            sb.append(format(obj.getDisplayName().getString()));
            sb.append(GRAY).append("#");
            sb.append(BLUE).append(obj.getId());
        }
    });
    public static final Formatter<Player> PLAYER = DbgFormatterRegistry.get().register(new Formatter<>(Player.class, RandomThingz.res("minecraft/player")) {
        @Override
        public void format(Player obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("Player");
            sb.append(WHITE).append(": ");
            sb.append(GREEN).append(obj.getGameProfile().getName());
        }
    });
    public static final Formatter<ResourceLocation> RESOURCE_LOCATION = DbgFormatterRegistry.get().register(new Formatter<>(ResourceLocation.class, RandomThingz.res("minecraft/resource_location")) {
        @Override
        public void format(ResourceLocation obj, StringBuilder sb) {
            sb.append(DARK_GREEN).append(obj.getNamespace());
            sb.append(GRAY).append(":");
            sb.append(GREEN).append(obj.getPath());
        }
    });
    public static final Formatter<ItemStack> ITEM_STACK = DbgFormatterRegistry.get().register(new Formatter<>(ItemStack.class, RandomThingz.res("minecraft/item_stack")) {
        @Override
        public void format(ItemStack obj, StringBuilder sb) {
            sb.append(format(obj.getItem().getRegistryName())).append(" ");
            sb.append(BLUE).append(obj.getCount()).append("x");
        }
    });
    public static final Formatter<Component> CHAT_COMPONENT = DbgFormatterRegistry.get().register(new Formatter<>(Component.class, RandomThingz.res("minecraft/chat/component")) {
        @Override
        public void format(Component obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("Component");
            sb.append(WHITE).append(": ");
            sb.append(format(obj.getString()));
        }
    });
    public static final Formatter<Vec2> VEC2 = DbgFormatterRegistry.get().register(new Formatter<>(Vec2.class, RandomThingz.res("minecraft/chat/component")) {
        @Override
        public void format(Vec2 obj, StringBuilder sb) {
            sb.append(RED).append("x: ");
            sb.append(format(obj.x));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("y: ");
            sb.append(format(obj.y));
        }
    });
    public static final Formatter<Vec3> VEC3 = DbgFormatterRegistry.get().register(new Formatter<>(Vec3.class, RandomThingz.res("minecraft/chat/component")) {
        @Override
        public void format(Vec3 obj, StringBuilder sb) {
            sb.append(RED).append("x: ");
            sb.append(format(obj.x()));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("y: ");
            sb.append(format(obj.y()));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("z: ");
            sb.append(format(obj.z()));
        }
    });
    public static final Formatter<Vec3i> VEC3I = DbgFormatterRegistry.get().register(new Formatter<>(Vec3i.class, RandomThingz.res("minecraft/chat/component")) {
        @Override
        public void format(Vec3i obj, StringBuilder sb) {
            sb.append(RED).append("x: ");
            sb.append(format(obj.getX()));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("y: ");
            sb.append(format(obj.getY()));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("z: ");
            sb.append(format(obj.getZ()));
        }
    });
    public static final Formatter<Vector3f> VECTOR3F = DbgFormatterRegistry.get().register(new Formatter<>(Vector3f.class, RandomThingz.res("minecraft/chat/component")) {
        @Override
        public void format(Vector3f obj, StringBuilder sb) {
            sb.append(RED).append("x: ");
            sb.append(format(obj.x()));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("y: ");
            sb.append(format(obj.y()));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("z: ");
            sb.append(format(obj.z()));
        }
    });
    public static final Formatter<Vector3d> VECTOR3D = DbgFormatterRegistry.get().register(new Formatter<>(Vector3d.class, RandomThingz.res("minecraft/chat/component")) {
        @Override
        public void format(Vector3d obj, StringBuilder sb) {
            sb.append(RED).append("x: ");
            sb.append(format(obj.x));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("y: ");
            sb.append(format(obj.y));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("z: ");
            sb.append(format(obj.z));
        }
    });
    public static final Formatter<Vector4f> VECTOR4F = DbgFormatterRegistry.get().register(new Formatter<>(Vector4f.class, RandomThingz.res("minecraft/chat/component")) {
        @Override
        public void format(Vector4f obj, StringBuilder sb) {
            sb.append(RED).append("x: ");
            sb.append(format(obj.x()));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("y: ");
            sb.append(format(obj.y()));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("z: ");
            sb.append(format(obj.z()));
            sb.append(GRAY).append(", ");
            sb.append(RED).append("w: ");
            sb.append(format(obj.w()));
        }
    });
    public static final Formatter<Pose> POSE = DbgFormatterRegistry.get().register(new Formatter<>(Pose.class, RandomThingz.res("minecraft/pose")) {
        @Override
        public void format(Pose obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("mc-pose");
            sb.append(GRAY).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case STANDING -> "standing";
                case CROUCHING -> "crouching";
                case DYING -> "dying";
                case FALL_FLYING -> "fall_flying";
                case LONG_JUMPING -> "long_jumping";
                case SLEEPING -> "sleeping";
                case SPIN_ATTACK -> "spin_attack";
                case SWIMMING -> "swimming";
            });
        }
    });
    public static final Formatter<MaterialColor> MATERIAL_COLOR = DbgFormatterRegistry.get().register(new Formatter<>(MaterialColor.class, RandomThingz.res("minecraft/material_color")) {
        @Override
        public void format(MaterialColor obj, StringBuilder sb) {
            sb.append(RED).append("id: ");
            sb.append(GOLD).append(obj.id);
            sb.append(RED).append("col: ");
            AWT_COLOR.format(new Color(FastColor.ARGB32.red(obj.col), FastColor.ARGB32.green(obj.col), FastColor.ARGB32.blue(obj.col)), sb);
        }
    });
    public static final Formatter<Block> BLOCK = DbgFormatterRegistry.get().register(new Formatter<>(Block.class, RandomThingz.res("minecraft/block")) {
        @Override
        public void format(Block obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("block");
            sb.append(WHITE).append(": ");
            RESOURCE_LOCATION.format(obj.getRegistryName(), sb);
        }
    });
    public static final Formatter<EntityType> ENTITY_TYPE = DbgFormatterRegistry.get().register(new Formatter<>(EntityType.class, RandomThingz.res("minecraft/entity_type")) {
        @Override
        public void format(EntityType obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("entity-type");
            sb.append(WHITE).append(": ");
            RESOURCE_LOCATION.format(obj.getRegistryName(), sb);
        }
    });
    public static final Formatter<BlockEntityType> BLOCK_ENTITY_TYPE = DbgFormatterRegistry.get().register(new Formatter<>(BlockEntityType.class, RandomThingz.res("minecraft/block_entity_type")) {
        @Override
        public void format(BlockEntityType obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("block-entity-type");
            sb.append(WHITE).append(": ");
            RESOURCE_LOCATION.format(obj.getRegistryName(), sb);
        }
    });
    public static final Formatter<Advancement> ADVANCEMENT = DbgFormatterRegistry.get().register(new Formatter<>(Advancement.class, RandomThingz.res("minecraft/advancement")) {
        @Override
        public void format(Advancement obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("advancement");
            sb.append(WHITE).append(": ");
            RESOURCE_LOCATION.format(obj.getId(), sb);
        }
    });
    public static final Formatter<Stat> STAT = DbgFormatterRegistry.get().register(new Formatter<>(Stat.class, RandomThingz.res("minecraft/stat")) {
        @Override
        public void format(Stat obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("stat");
            sb.append(WHITE).append(": ");
            STRING.format(obj.getName(), sb);
        }
    });
    public static final Formatter<StatType> STAT_TYPE = DbgFormatterRegistry.get().register(new Formatter<>(StatType.class, RandomThingz.res("minecraft/stat")) {
        @Override
        public void format(StatType obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("stat-type");
            sb.append(WHITE).append(": ");
            RESOURCE_LOCATION.format(obj.getRegistryName(), sb);
        }
    });
    public static final Formatter<VillagerProfession> VILLAGER_PROFESSION = DbgFormatterRegistry.get().register(new Formatter<>(VillagerProfession.class, RandomThingz.res("minecraft/stat")) {
        @Override
        public void format(VillagerProfession obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("villager-profession");
            sb.append(WHITE).append(": ");
            RESOURCE_LOCATION.format(obj.getRegistryName(), sb);
        }
    });
    public static final Formatter<Item> ITEM = DbgFormatterRegistry.get().register(new Formatter<>(Item.class, RandomThingz.res("minecraft/item")) {
        @Override
        public void format(Item obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("item");
            sb.append(WHITE).append(": ");
            RESOURCE_LOCATION.format(obj.getRegistryName(), sb);
        }
    });
    public static final Formatter<Rarity> RARITY = DbgFormatterRegistry.get().register(new Formatter<>(Rarity.class, RandomThingz.res("minecraft/item/rarity")) {
        @SuppressWarnings("UnnecessaryDefault")
        @Override
        public void format(Rarity obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("item-rarity");
            sb.append(WHITE).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case COMMON -> "common";
                case UNCOMMON -> "uncommon";
                case RARE -> "rare";
                case EPIC -> "epic";
                default -> obj.name();
            });
        }
    });
    public static final Formatter<RenderShape> RENDER_SHAPE = DbgFormatterRegistry.get().register(new Formatter<>(RenderShape.class, RandomThingz.res("minecraft/render_shape")) {
        @SuppressWarnings("UnnecessaryDefault")
        @Override
        public void format(RenderShape obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("render-shape");
            sb.append(WHITE).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case ENTITYBLOCK_ANIMATED -> "entity_block_animated";
                case INVISIBLE -> "invisible";
                case MODEL -> "model";
                default -> obj.name();
            });
        }
    });
    public static final Formatter<BlockBehaviour.OffsetType> BLOCK_OFFSET_TYPE = DbgFormatterRegistry.get().register(new Formatter<>(BlockBehaviour.OffsetType.class, RandomThingz.res("minecraft/block/offset_type")) {
        @SuppressWarnings("UnnecessaryDefault")
        @Override
        public void format(BlockBehaviour.OffsetType obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("block-offset-type");
            sb.append(WHITE).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case NONE -> "none";
                case XYZ -> "xyz";
                case XZ -> "model";
                default -> obj.name();
            });
        }
    });
    public static final Formatter<Difficulty> DIFFICULTY = DbgFormatterRegistry.get().register(new Formatter<>(Difficulty.class, RandomThingz.res("minecraft/difficulty")) {
        @SuppressWarnings("UnnecessaryDefault")
        @Override
        public void format(Difficulty obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("difficulty");
            sb.append(WHITE).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case PEACEFUL -> "peaceful";
                case EASY -> "easy";
                case NORMAL -> "normal";
                case HARD -> "hard";
                default -> obj.name();
            });
        }
    });
    public static final Formatter<InteractionHand> INTERACTION_HAND = DbgFormatterRegistry.get().register(new Formatter<>(InteractionHand.class, RandomThingz.res("minecraft/interaction_hand")) {
        @SuppressWarnings("UnnecessaryDefault")
        @Override
        public void format(InteractionHand obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("interaction-hand");
            sb.append(WHITE).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case MAIN_HAND -> "main_hand";
                case OFF_HAND -> "off_hand";
                default -> obj.name();
            });
        }
    });
    public static final Formatter<Dist> DIST = DbgFormatterRegistry.get().register(new Formatter<>(Dist.class, RandomThingz.res("forge/dist")) {
        @SuppressWarnings("UnnecessaryDefault")
        @Override
        public void format(Dist obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("dist");
            sb.append(WHITE).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case CLIENT -> "client";
                case DEDICATED_SERVER -> "dedicated_server";
                default -> obj.name();
            });
        }
    });
    public static final Formatter<MobSpawnType> MOB_SPAWN_TYPE = DbgFormatterRegistry.get().register(new Formatter<>(MobSpawnType.class, RandomThingz.res("minecraft/entity/mob_spawn_type")) {
        @SuppressWarnings("UnnecessaryDefault")
        @Override
        public void format(MobSpawnType obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("mob-spawn-type");
            sb.append(WHITE).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case BREEDING -> "breeding";
                case BUCKET -> "bucket";
                case CHUNK_GENERATION -> "chunk_generation";
                case COMMAND -> "command";
                case CONVERSION -> "conversion";
                case DISPENSER -> "dispenser";
                case EVENT -> "event";
                case MOB_SUMMONED -> "summoned";
                case JOCKEY -> "jocky";
                case NATURAL -> "natural";
                case SPAWN_EGG -> "spawn_egg";
                case PATROL -> "patrol";
                case REINFORCEMENT -> "reinforcement";
                case SPAWNER -> "spawner";
                case STRUCTURE -> "structure";
                case TRIGGERED -> "triggered";
                default -> obj.name();
            });
        }
    });
    public static final Formatter<MobType> MOB_TYPE = DbgFormatterRegistry.get().register(new Formatter<>(MobType.class, RandomThingz.res("minecraft/entity/mob_type")) {
        @Override
        public void format(MobType obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("mob-type");
            sb.append(GRAY).append("@");
            sb.append(YELLOW).append(obj.hashCode());
        }
    });
    public static final Formatter<MoonPhase> RT_MOON_PHASE = DbgFormatterRegistry.get().register(new Formatter<>(MoonPhase.class, RandomThingz.res("randomthingz/moon_phase")) {
        @Override
        public void format(MoonPhase obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("moon-phase");
            sb.append(GRAY).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case NEW -> "new";
                case FULL -> "full";
                case FIRST_QUARTER -> "first_quarter";
                case THIRD_QUARTER -> "third_quarter";
                case WANING_CRESCENT -> "waning_crescent";
                case WANING_GIBBOUS -> "waning_gibbous";
                case WAXING_CRESCENT -> "waxing_crescent";
                case WAXING_GIBBOUS -> "waxing_gibbous";
            });
        }
    });
    public static final Formatter<Formattable> RT_FORMATTABLE = DbgFormatterRegistry.get().register(new Formatter<>(Formattable.class, RandomThingz.res("randomthingz/formattable")) {
        @Override
        public void format(Formattable obj, StringBuilder sb) {
            sb.append(obj.toFormattedString());
        }
    });
    public static final Formatter<Angle> RT_ANGLE = DbgFormatterRegistry.get().register(new Formatter<>(Angle.class, RandomThingz.res("randomthingz/angle")) {
        @Override
        public void format(Angle obj, StringBuilder sb) {
            sb.append(obj.toFormattedString());
        }
    });
    public static final Formatter<com.ultreon.modlib.common.enums.MoonPhase> UML_MOON_PHASE = DbgFormatterRegistry.get().register(new Formatter<>(com.ultreon.modlib.common.enums.MoonPhase.class, RandomThingz.res("randomthingz/moon_phase")) {
        @Override
        public void format(com.ultreon.modlib.common.enums.MoonPhase obj, StringBuilder sb) {
            sb.append(LIGHT_PURPLE).append("moon-phase");
            sb.append(GRAY).append(": ");
            sb.append(GOLD).append(switch (obj) {
                case NEW -> "new";
                case FULL -> "full";
                case FIRST_QUARTER -> "first_quarter";
                case THIRD_QUARTER -> "third_quarter";
                case WANING_CRESCENT -> "waning_crescent";
                case WANING_GIBBOUS -> "waning_gibbous";
                case WAXING_CRESCENT -> "waxing_crescent";
                case WAXING_GIBBOUS -> "waxing_gibbous";
            });
        }
    });
    public static final Formatter<com.ultreon.modlib.common.interfaces.Formattable> UML_FORMATTABLE = DbgFormatterRegistry.get().register(new Formatter<>(com.ultreon.modlib.common.interfaces.Formattable.class, RandomThingz.res("ultreon_mod_lib/formattable")) {
        @Override
        public void format(com.ultreon.modlib.common.interfaces.Formattable obj, StringBuilder sb) {
            sb.append(obj.toFormattedString());
        }
    });

    static {
        if (System.getenv("RT_DEBUG_FORMATTER_DUMP") != null) {
            DbgFormatterRegistry.get().dump();
        }
    }

    public static void initClass() {

    }
}
