package com.ultreon.randomthingz.common.text;

import com.ultreon.randomthingz.RandomThingz;
import joptsimple.internal.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.resources.I18n;
import net.minecraft.fluid.Fluid;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.ContainerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public enum Translations {
    YES("misc.yes"),
    NO("misc.no"),
    TRUE("misc.true"),
    FALSE("misc.false"),
    ON("misc.on"),
    OFF("misc.off"),
    ENABLED("misc.enabled"),
    DISABLED("misc.disabled"),
    ENABLE("misc.enable"),
    DISABLE("misc.disable");

    @Getter
    private final String translationKey;

    public static String get(Translations key, java.lang.Object... params) {
        String translationKey = key.getTranslationKey();
        return I18n.format(translationKey, params);
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

    public enum object {
        BLOCK("block", Block.class),
        ITEM("item", Item.class),
        FLUID("fluid", Fluid.class),
        ENTITY("entity", EntityType.class),
        CONTAINER("entity", ContainerType.class),
        STAT("stat", StatType.class),
        ADVANCEMENT("advancement", Advancement.class);

        private final String name;
        private final Class<?> clazz;

        object(String name, @Nullable Class<?> clazz) {
            this.name = name;
            this.clazz = clazz;
        }

        public String getName() {
            return name;
        }

        @Nullable
        public Class<?> getType() {
            return clazz;
        }
    }

    public static TranslatableComponent get(String object, ResourceLocation resource) {
        return new TranslatableComponent(object + "." + resource.getNamespace() + "." + resource.getPath().replaceAll("/", "."));
    }

    public static TranslatableComponent get(object object, ResourceLocation resource) {
        return new TranslatableComponent(object.getName() + "." + resource.getNamespace() + "." + resource.getPath().replaceAll("/", "."));
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
}
