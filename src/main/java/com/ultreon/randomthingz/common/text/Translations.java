package com.ultreon.randomthingz.common.text;

import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.ultreon.randomthingz.RandomThingz;
import joptsimple.internal.Strings;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Translations<T> {
    private final Map<T, Translation> values = new HashMap<>();

    private Translations() {

    }

    protected final Translation add(T type, Translation translation) {
        values.put(type, translation);
        return translation;
    }

    public static String getKey0(String... path) {
        return Strings.join(path, ".");
    }

    public static String getKey(String type, String object, String... strings) {
        if (strings.length == 0) {
            return getKey0(type, RandomThingz.MOD_ID, object);
        }
        return getKey0(type, RandomThingz.MOD_ID, object) + "." + Strings.join(strings, ".");
    }

    public static abstract class Translator<T> {
        protected static final Translation MISSING_NO = new Translation("misc.randomthingz.missing_no");
        protected final String name;

        protected Translator(String name) {
            this.name = name;
        }

        protected final TranslatableComponent create(String subPath, Object... args) {
            if (subPath == null) return new TranslatableComponent(name, args);
            return new TranslatableComponent(name + "." + subPath, args);
        }

        protected final String translate(@Nullable String subPath, Object... args) {
            if (subPath == null) return I18n.get(name, args);
            return I18n.get(name + "." + subPath, args);
        }

        public TranslatableComponent create(T obj) {
            return new TranslatableComponent(path(obj));
        }

        protected Object[] objects(T obj) {
            return new Object[0];
        }

        public String translate(T obj) {
            return I18n.get(path(obj));
        }

        public Translation get(T obj) {
            return new Translation(path(obj));
        }

        protected abstract String path(T obj);
    }

    public static final class BlockTranslator extends Translator<Block> {
        public static final BlockTranslator INSTANCE = new BlockTranslator();

        private BlockTranslator() {
            super("block");
        }

        @Override
        protected String path(Block obj) {
            return obj.getDescriptionId();
        }
    }

    public static final class FluidTranslator extends Translator<Fluid> {
        public static final FluidTranslator INSTANCE = new FluidTranslator();

        private FluidTranslator() {
            super("fluid");
        }

        @Override
        protected String path(Fluid obj) {
            ResourceLocation registryName = obj.getRegistryName();
            if (registryName == null) return MISSING_NO.id;
            return "fluid." + registryName.getNamespace() + "." + registryName.getPath();
        }
    }

    public static final class ItemTranslator extends Translator<Item> {
        public static final ItemTranslator INSTANCE = new ItemTranslator();

        private ItemTranslator() {
            super("item");
        }

        @Override
        protected String path(Item obj) {
            return obj.getDescriptionId();
        }
    }

    public static final class ItemStackTranslator extends Translator<ItemStack> {
        public static final ItemStackTranslator INSTANCE = new ItemStackTranslator();

        private ItemStackTranslator() {
            super("item_stack");
        }

        @Override
        protected String path(ItemStack obj) {
            return "type.randomthingz.item_stack";
        }

        @Override
        protected Object[] objects(ItemStack obj) {
            return new Object[]{obj.getCount(), ItemTranslator.INSTANCE.translate(obj.getItem())};
        }
    }

    public static final class PlayerTranslator extends Translator<Player> {
        public static final PlayerTranslator INSTANCE = new PlayerTranslator();

        private PlayerTranslator() {
            super("item_stack");
        }

        @Override
        protected String path(Player obj) {
            return "type.randomthingz.player";
        }

        @Override
        protected Object[] objects(Player obj) {
            return new Object[]{obj.getGameProfile().getName()};
        }
    }

    public static final class LivingEntityTranslator extends Translator<LivingEntity> {
        public static final PlayerTranslator INSTANCE = new PlayerTranslator();

        private LivingEntityTranslator() {
            super("item_stack");
        }

        @Override
        protected String path(LivingEntity obj) {
            if (obj.getCustomName() != null) return "type.randomthingz.living_entity.named";
            return "type.randomthingz.living_entity";
        }

        @Override
        protected Object[] objects(LivingEntity obj) {
            Component customName = obj.getCustomName();
            if (customName == null) {
                return new Object[]{I18n.get(obj.getType().getDescriptionId())};
            }
            return new Object[]{I18n.get(obj.getType().getDescriptionId()), customName.getString()};
        }
    }

    public static final class EntityTranslator extends Translator<Entity> {
        public static final EntityTranslator INSTANCE = new EntityTranslator();

        private EntityTranslator() {
            super("entity");
        }

        @Override
        protected String path(Entity obj) {
            return obj.getType().getDescriptionId();
        }
    }

    public static final class Vec3dTranslator extends Translator<Vector3d> {
        public static final Vec3dTranslator INSTANCE = new Vec3dTranslator();

        private Vec3dTranslator() {
            super("vec3d");
        }

        @Override
        protected String path(Vector3d obj) {
            return "type.randomthingz.vec3";
        }

        @Override
        protected Object[] objects(Vector3d obj) {
            return new Object[]{obj.x, obj.y, obj.z};
        }
    }

    public static final class Vec3fTranslator extends Translator<Vector3f> {
        public static final Vec3fTranslator INSTANCE = new Vec3fTranslator();

        private Vec3fTranslator() {
            super("vec3f");
        }

        @Override
        protected String path(Vector3f obj) {
            return "type.randomthingz.vec3";
        }

        @Override
        protected Object[] objects(Vector3f obj) {
            return new Object[]{obj.x(), obj.y(), obj.z()};
        }
    }

    public static final class Vec3iTranslator extends Translator<Vec3i> {
        public static final Vec3iTranslator INSTANCE = new Vec3iTranslator();

        private Vec3iTranslator() {
            super("vec3i");
        }

        @Override
        protected String path(Vec3i obj) {
            return "type.randomthingz.vec3";
        }

        @Override
        protected Object[] objects(Vec3i obj) {
            return new Object[]{obj.getX(), obj.getY(), obj.getZ()};
        }
    }

    public static final class Vec3Translator extends Translator<Vec3> {
        public static final Vec3Translator INSTANCE = new Vec3Translator();

        private Vec3Translator() {
            super("vec3");
        }

        @Override
        protected String path(Vec3 obj) {
            return "type.randomthingz.vec3";
        }

        @Override
        protected Object[] objects(Vec3 obj) {
            return new Object[]{obj.x, obj.y, obj.z};
        }
    }

    public static final class BlockPosTranslator extends Translator<BlockPos> {
        public static final BlockPosTranslator INSTANCE = new BlockPosTranslator();

        private BlockPosTranslator() {
            super("vec3");
        }

        @Override
        protected String path(BlockPos obj) {
            return "type.randomthingz.block_pos";
        }

        @Override
        protected Object[] objects(BlockPos obj) {
            return new Object[]{obj.getX(), obj.getY(), obj.getZ()};
        }
    }

    public static final class ModTranslator extends Translator<ModContainer> {
        public static final ModTranslator INSTANCE = new ModTranslator();

        private ModTranslator() {
            super("vec3");
        }

        @Override
        protected String path(ModContainer obj) {
            return "type.randomthingz.mod";
        }

        @Override
        protected Object[] objects(ModContainer obj) {
            return new Object[]{obj.getModInfo().getDisplayName()};
        }
    }

    public static final class BooleanTranslator extends Translator<Boolean> {
        public static final BooleanTranslator INSTANCE = new BooleanTranslator();

        private BooleanTranslator() {
            super("vec3");
        }

        @Override
        protected String path(Boolean obj) {
            return obj ? "misc.true" : "misc.false";
        }
    }

    public static final class BiomeTranslator extends Translator<Biome> {
        public static final EntityTranslator INSTANCE = new EntityTranslator();

        private BiomeTranslator() {
            super("biome");
        }

        @Override
        protected String path(Biome obj) {
            ResourceLocation registryName = obj.getRegistryName();
            if (registryName == null) return MISSING_NO.id;
            return "biome." + registryName.getNamespace() + "." + registryName.getPath();
        }
    }

    public static final class MiscTranslator extends Translator<Misc> {
        public static final MiscTranslator INSTANCE = new MiscTranslator();

        private MiscTranslator() {
            super("misc");
        }

        @Override
        protected String path(Misc obj) {
            return name + "." + obj.id;
        }
    }

    public enum Misc {
        YES("yes"),
        NO("no"),
        TRUE("true"),
        FALSE("false"),
        ON("on"),
        OFF("off"),
        ENABLED("enabled"),
        DISABLED("disabled"),
        ENABLE("enable"),
        DISABLE("disable"),
        OK("ok"),
        MISSING_NO("randomthingz.missing_no"),
        ;

        private final String id;

        Misc(String id) {
            this.id = id;
        }

        public Component get() {
            return MiscTranslator.INSTANCE.create(this);
        }
    }

    public static TranslatableComponent get(String object, ResourceLocation resource) {
        return new TranslatableComponent(object + "." + resource.getNamespace() + "." + resource.getPath().replaceAll("/", "."));
    }

    public static TranslatableComponent getTooltip(String object, String variant) {
        return new TranslatableComponent(getKey("tooltip", object, variant));
    }

    public static TranslatableComponent getButton(String object, String msg) {
        return new TranslatableComponent(getKey("button", object, msg));
    }

    public static TranslatableComponent getMessage(String object, String msg) {
        return new TranslatableComponent(getKey("msg", object, msg));
    }

    public static TranslatableComponent getItem(String object, String... path) {
        return new TranslatableComponent(getKey("item", object, path));
    }

    public static TranslatableComponent getBlock(String object, String... path) {
        return new TranslatableComponent(getKey("block", object, path));
    }

    public static TranslatableComponent getFluid(String object, String... path) {
        return new TranslatableComponent(getKey("fluid", object, path));
    }

    public static TranslatableComponent getScreen(String object, String... path) {
        return new TranslatableComponent(getKey("screen", object, path));
    }

    public static TranslatableComponent getMisc(String object, String... path) {
        return new TranslatableComponent(getKey("misc", object, path));
    }
    public record Translation(String id) {
        public TranslatableComponent get(Object... args) {
            return new TranslatableComponent(id, args);
        }

    }
}
