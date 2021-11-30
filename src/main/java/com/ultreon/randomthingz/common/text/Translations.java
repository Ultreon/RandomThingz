package com.ultreon.randomthingz.common.text;

import com.ultreon.randomthingz.RandomThingz;
import joptsimple.internal.Strings;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
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

    public static TranslationTextComponent get(String object, ResourceLocation resource) {
        return new TranslationTextComponent(object + "." + resource.getNamespace() + "." + resource.getPath().replaceAll("/", "."));
    }

    public static TranslationTextComponent get(object object, ResourceLocation resource) {
        return new TranslationTextComponent(object.getName() + "." + resource.getNamespace() + "." + resource.getPath().replaceAll("/", "."));
    }

    public static TranslationTextComponent getTooltip(String object, String variant) {
        return new TranslationTextComponent(getKey("tooltip", object, variant));
    }

    public static TranslationTextComponent getButton(String object, String msg) {
        return new TranslationTextComponent(getKey("button", object, msg));
    }

    public static TranslationTextComponent getMessage(String object, String msg) {
        return new TranslationTextComponent(getKey("msg", object, msg));
    }

    public static TranslationTextComponent getItem(String object, String... path) {
        return new TranslationTextComponent(getKey("item", object, path));
    }

    public static TranslationTextComponent getBlock(String object, String... path) {
        return new TranslationTextComponent(getKey("block", object, path));
    }

    public static TranslationTextComponent getFluid(String object, String... path) {
        return new TranslationTextComponent(getKey("fluid", object, path));
    }

    public static TranslationTextComponent getScreen(String object, String... path) {
        return new TranslationTextComponent(getKey("screen", object, path));
    }

    public static TranslationTextComponent getMisc(String object, String... path) {
        return new TranslationTextComponent(getKey("misc", object, path));
    }
}
