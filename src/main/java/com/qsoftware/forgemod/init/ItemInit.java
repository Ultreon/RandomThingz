package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.objects.items.*;
import com.qsoftware.forgemod.objects.items.advanced.AdvancedBowItem;
import com.qsoftware.forgemod.objects.items.base.IngotOrDustItem;
import com.qsoftware.forgemod.objects.items.base.IngredientItem;
import com.qsoftware.forgemod.objects.items.base.KnifeItem;
import com.qsoftware.forgemod.objects.items.base.SliceableItem;
import com.qsoftware.forgemod.registration.impl.EntityTypeRegistryObject;
import com.qsoftware.forgemod.registration.impl.ItemDeferredRegister;
import com.qsoftware.forgemod.registration.impl.ItemRegistryObject;
import com.qsoftware.forgemod.util.builder.ArmorMaterialBuilder;
import com.qsoftware.forgemod.util.builder.ItemTierBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.awt.*;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "NumericOverflow"})
//@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
//@ObjectHolder(QForgeUtils.MOD_ID)
public class ItemInit {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(QForgeUtils.MOD_ID);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Overpowered     //
    /////////////////////////
    public static final Item KILL_SWITCH = register("kill_switch", KillSwitchItem::new);
    public static final Item BAN_HAMMER = register("ban_hammer", BanHammerItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Miscellaneous     //
    ///////////////////////////
    public static final Item LEGENDARY_ENDER_PEARL = register("legendary_ender_pearl", () -> new LegendaryEnderPearlItem(new Item.Properties().group(Groups.MISC)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Food     //
    //////////////////
    public static final Item CHEESE_BURGER = register("cheese_burger", () -> new Item(new Item.Properties().group(Groups.FOOD).food(new Food.Builder().hunger(1).saturation(0.2f).effect(() -> new EffectInstance(Effects.REGENERATION, 60, 1), 0.7f).build())));
    public static final Item CHEESE_SLICE = register("cheese_slice", () -> new Item(new Item.Properties().group(Groups.FOOD).food(new Food.Builder().hunger(1).saturation(0.2f).build())));
    public static final Item CHEESE = register("cheese", () -> new SliceableItem(new Item.Properties().group(Groups.FOOD).food(new Food.Builder().hunger(2).saturation(0.3f).build()), (stack) -> new ItemStack(CHEESE_SLICE, stack.getCount() * 6)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Normal     //
    ////////////////////
    public static final Item STICK_VARIANT_1 = register("stick_variant_1", () -> new Item(new Item.Properties().group(Groups.NATURE)));
    public static final Item STICK_VARIANT_2 = register("stick_variant_2", () -> new Item(new Item.Properties().group(Groups.NATURE)));
    public static final Item EUCALYPTUS_PLANK = register("eucalyptus_plank", () -> new Item(new Item.Properties().group(Groups.WOOD)));
    public static final Item EUCALYPTUS_LEAF = register("eucalyptus_leaf", () -> new Item(new Item.Properties()
            .group(Groups.NATURE)
            .food(new Food.Builder()
                    .hunger(1)
                    .saturation(0.2f)
                    .effect(() -> new EffectInstance(Effects.REGENERATION, 60, 1), 0.7f)
                    .build())));

    public static final Item KNIFE = register("knife", () -> new KnifeItem(new Item.Properties().group(Groups.SPECIALS).maxDamage(4)));
    public static final Item MAGNET = register("magnet", () -> new MagnetItem(new Item.Properties().group(Groups.SPECIALS).maxDamage(4)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Fletching     //
    ///////////////////////

    // Bows
    public static final BowItem BLAZE_BOW = register("blaze_bow", () -> (BowItem) new AdvancedBowItem(new Item.Properties().group(Groups.FLETCHING), 6.25f, 1.0f, 6, 1, true));
    public static final BowItem ICE_BOW = register("ice_bow", () -> (BowItem) new AdvancedBowItem(new Item.Properties().group(Groups.FLETCHING), 2f, 1.0f, 8, 2));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Fletching     //
    ///////////////////////

//    // Bows
//    public static final SpawnEggItem DUCK_SPAWN_EGG = register("duck_spawn_egg", () -> new SpawnEggItem(
//            EntityTypeInit.DUCK_ENTITY.get(),
//            new Color(4, 104, 14).getRGB(),
//            new Color(228, 181, 15).getRGB(),
//            new Item.Properties().group(Groups.SPAWN_EGGS)));
//    public static final SpawnEggItem HOG_SPAWN_EGG = register("hog_spawn_egg", () -> new SpawnEggItem(
//            EntityTypeInit.HOG_ENTITY.get(),
//            new Color(84, 21, 0).getRGB(),
//            new Color(166, 103, 61).getRGB(),
//            new Item.Properties().group(Groups.SPAWN_EGGS)));
//    public static final SpawnEggItem WRAT_HOG_SPAWN_EGG = register("wrat_hog_spawn_egg", () -> new SpawnEggItem(
//            EntityTypeInit.WRAT_HOG_ENTITY.get(),
//            new Color(183, 111, 60).getRGB(),
//            new Color(148, 90, 49).getRGB(),
//            new Item.Properties().group(Groups.SPAWN_EGGS)));
//    public static final SpawnEggItem BISON_SPAWN_EGG = register("bison_spawn_egg", () -> new SpawnEggItem(
//            EntityTypeInit.BISON_ENTITY.get(),
//            new Color(79, 43, 5).getRGB(),
//            new Color(180, 149, 56).getRGB(),
//            new Item.Properties().group(Groups.SPAWN_EGGS)));
//    public static final SpawnEggItem MOOBLOOM_SPAWN_EGG = register("moobloom_spawn_egg", () -> new SpawnEggItem(
//            EntityTypeInit.MOOBLOOM_ENTITY.get(),
//            new Color(253, 213, 5).getRGB(),
//            new Color(247, 237, 193).getRGB(),
//            new Item.Properties().group(Groups.SPAWN_EGGS)));
//    public static final SpawnEggItem OX_SPAWN_EGG = register("ox_spawn_egg", () -> new SpawnEggItem(
//            EntityTypeInit.OX_ENTITY.get(),
//            new Color(164, 110, 61).getRGB(),
//            new Color(212, 149, 92).getRGB(),
//            new Item.Properties().group(Groups.SPAWN_EGGS)));
//    public static final SpawnEggItem ICE_ENDERMAN_SPAWN_EGG = register("ice_enderman_spawn_egg", () -> new SpawnEggItem(
//            EntityTypeInit.ICE_ENDERMAN_ENTITY.get(),
//            new Color(0, 0, 0).getRGB(),
//            new Color(123, 214, 214).getRGB(),
//            new Item.Properties().group(Groups.SPAWN_EGGS)));
//    public static final SpawnEggItem FIRE_CREEPER_SPAWN_EGG = register("fire_creeper_spawn_egg", () -> new SpawnEggItem(
//            EntityTypeInit.FIRE_CREEPER_ENTITY.get(),
//            new Color(54, 58, 54).getRGB(),
//            new Color(209, 39, 39).getRGB(),
//            new Item.Properties().group(Groups.SPAWN_EGGS)));
//    public static final SpawnEggItem GLOW_SQUID_SPAWN_EGG = register("glow_squid_spawn_egg", () -> new SpawnEggItem(
//            EntityTypeInit.GLOW_SQUID_ENTITY.get(),
//            new Color(47, 151, 153).getRGB(),
//            new Color(84, 221, 153).getRGB(),
//            new Item.Properties().group(Groups.SPAWN_EGGS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Ingredients     //
    /////////////////////////

    // Glass shards
    public static final Item CLEAR_SHARD = register("clear_shard", IngredientItem::new);
    public static final Item BLACK_SHARD = register("black_shard", IngredientItem::new);
    public static final Item BLUE_SHARD = register("blue_shard", IngredientItem::new);
    public static final Item BROWN_SHARD = register("brown_shard", IngredientItem::new);
    public static final Item CYAN_SHARD = register("cyan_shard", IngredientItem::new);
    public static final Item GRAY_SHARD = register("gray_shard", IngredientItem::new);
    public static final Item GREEN_SHARD = register("green_shard", IngredientItem::new);
    public static final Item LIGHT_BLUE_SHARD = register("light_blue_shard", IngredientItem::new);
    public static final Item LIGHT_GRAY_SHARD = register("light_gray_shard", IngredientItem::new);
    public static final Item LIME_SHARD = register("lime_shard", IngredientItem::new);
    public static final Item MAGENTA_SHARD = register("magenta_shard", IngredientItem::new);
    public static final Item ORANGE_SHARD = register("orange_shard", IngredientItem::new);
    public static final Item PINK_SHARD = register("pink_shard", IngredientItem::new);
    public static final Item PURPLE_SHARD = register("purple_shard", IngredientItem::new);
    public static final Item RED_SHARD = register("red_shard", IngredientItem::new);
    public static final Item WHITE_SHARD = register("white_shard", IngredientItem::new);
    public static final Item YELLOW_SHARD = register("yellow_shard", IngredientItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Ingots or Dusts     //
    /////////////////////////////

    // Metals - Copper Level
    public static final Item COPPER_INGOT = register("copper_ingot", IngotOrDustItem::new);
    public static final Item COPPER_NUGGET = register("copper_nugget", IngotOrDustItem::new);
    public static final Item COPPER_DUST = register("copper_dust", IngotOrDustItem::new);

    // Metals - Steel Level
    public static final Item STEEL_INGOT = register("steel_ingot", IngotOrDustItem::new);
    public static final Item STEEL_NUGGET = register("steel_nugget", IngotOrDustItem::new);
    public static final Item STEEL_DUST = register("steel_dust", IngotOrDustItem::new);

    // Metals - Tungsten Steel Level
    public static final Item TUNGSTEN_INGOT = register("tungsten_ingot", IngotOrDustItem::new);
    public static final Item TUNGSTEN_NUGGET = register("tungsten_nugget", IngotOrDustItem::new);
    public static final Item TUNGSTEN_DUST = register("tungsten_dust", IngotOrDustItem::new);

    // Metals - Ultrinium Level
    public static final Item ULTRINIUM_INGOT = register("ultrinium_ingot", IngotOrDustItem::new);
    public static final Item ULTRINIUM_NUGGET = register("ultrinium_nugget", IngotOrDustItem::new);
    public static final Item ULTRINIUM_DUST = register("ultrinium_dust", IngotOrDustItem::new);

    // Metals - Infinity Level
    public static final Item UNSTABLE_INFINITY_INGOT = register("unstable_infinity_ingot", UnstableInfinityIngot::new);
    public static final Item INFINITY_INGOT = register("infinity_ingot", IngotOrDustItem::new);
    public static final Item INFINITY_NUGGET = register("infinity_nugget", IngotOrDustItem::new);
    public static final Item INFINITY_DUST = register("infinity_dust", IngotOrDustItem::new);

    // Metals - Uranium Level
    public static final Item URANIUM_INGOT = register("uranium_ingot", IngotOrDustItem::new);
    public static final Item URANIUM_NUGGET = register("uranium_nugget", IngotOrDustItem::new);
    public static final Item URANIUM_DUST = register("uranium_dust", IngotOrDustItem::new);

    // Gems and other metals
    public static final Item IRON_DUST = register("iron_dust", IngotOrDustItem::new);
    public static final Item GOLD_DUST = register("gold_dust", IngotOrDustItem::new);
    public static final Item RUBY_DUST = register("ruby_dust", IngotOrDustItem::new);
    public static final Item AMETHYST_DUST = register("amethyst_dust", IngotOrDustItem::new);
    public static final Item AQUAMARINE_DUST = register("aquamarine_dust", IngotOrDustItem::new);
    public static final Item SAPHIRE_DUST = register("saphire_dust", IngotOrDustItem::new);
    public static final Item MALACHITE_DUST = register("malachite_dust", IngotOrDustItem::new);
    public static final Item TOPAZ_DUST = register("topaz_dust", IngotOrDustItem::new);
    public static final Item AMBER_DUST = register("amber_dust", IngotOrDustItem::new);
    public static final Item BERYL_DUST = register("beryl_dust", IngotOrDustItem::new);
    public static final Item DIAMOND_DUST = register("diamond_dust", IngotOrDustItem::new);
    public static final Item TANZANITE_DUST = register("tanzanite_dustt", IngotOrDustItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Gems     //
    //////////////////
    public static final Item RUBY = register("ruby", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final Item AMETHYST = register("amethyst", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final Item AQUAMARINE = register("aquamarine", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final Item SAPHIRE = register("saphire", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final Item MALACHITE = register("malachite", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final Item TOPAZ = register("topaz", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final Item AMBER = register("amber", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final Item PERIDOT = register("peridot", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final Item BERYL = register("beryl", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final Item TANZANITE = register("tanzanite", () -> new Item(new Item.Properties().group(Groups.GEMS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Icons     //
    ///////////////////
    public static final Item DUNGEONS = register("dungeons", () -> new SwordItem(ItemTier.DIAMOND, 0, 0f, new Item.Properties().setNoRepair().maxStackSize(1)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Dungeons     //
    //////////////////////
    public static final AxeItem DUNGEONS_DIAMOND_AXE = register("dungeons_diamond_axe", () -> new AxeItem(ItemTier.DIAMOND, 4, -2.0f, new Item.Properties().group(Groups.DUNGEONS)));
    public static final SwordItem DUNGEONS_DIAMOND_SWORD = register("dungeons_diamond_sword", () -> new SwordItem(ItemTier.DIAMOND, 4, -2.0f, new Item.Properties().group(Groups.DUNGEONS)));
    public static final SwordItem DUNGEONS_IRON_SWORD = register("dungeons_iron_sword", () -> new SwordItem(ItemTier.IRON, 4, -2.0f, new Item.Properties().group(Groups.DUNGEONS)));
    public static final SwordItem DUNGEONS_BROADSWORD = register("dungeons_broadsword", () -> new SwordItem(ItemTier.IRON, 5, -3.75f, new Item.Properties().group(Groups.DUNGEONS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Specials     //
    //////////////////////

    // Wands
    public static final Item WALKING_STAFF = register("walking_staff", WandItem::new);
    public static final Item LIGHTNING_STAFF = register("lightning_staff", LightningStaffItem::new);
    public static final Item NATURE_STAFF = register("nature_staff", NatureStaffItem::new);

    // Stone Level
    public static final SwordItem STONE_SWORD_OF_DOOM = register("stone_sword_of_doom", () -> new SwordItem(ItemTier.STONE, 8, -2.0f, new Item.Properties().group(Groups.SPECIALS)));

    // Iron Level
    public static final AxeItem EMERGENCY_FIRE_AXE = register("emergency_fire_axe", () -> new AxeItem(ItemTier.IRON, 3, -2.55f, new Item.Properties().group(Groups.SPECIALS)));
    public static final SwordItem FIRE_SWORD = register("fire_sword", () -> (SwordItem) new FireSwordItem(ItemTier.IRON, 5, -3.5f, new Item.Properties().group(Groups.SPECIALS)));

    // Diamond Level
    public static final AxeItem LEVIATHAN_AXE = register("leviathan_axe", () -> new AxeItem(ItemTier.DIAMOND, 5, -2.55f, new Item.Properties().group(Groups.SPECIALS)));
    public static final AxeItem ADAMANTANIUM_AXE_RED = register("adamantanium_axe_red", () -> new AxeItem(ItemTier.DIAMOND, 8, -1.875f, new Item.Properties().group(Groups.SPECIALS)));

    public static final SwordItem DIAMOND_QUARTZ_SWORD = register("diamond_quartz_sword", () -> new SwordItem(ItemTier.DIAMOND, 8, -2.0f, new Item.Properties().group(Groups.SPECIALS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Armors     //
    ////////////////////

    // Materials
    public static final IArmorMaterial copperArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":copper", 13, new int[]{2, 5, 6, 2}, 10, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> Ingredient.fromItems(ItemInit.COPPER_INGOT)
    );
    public static final IArmorMaterial steelArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":steel", 24, new int[]{3, 6, 8, 4}, 14, 4f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, () -> Ingredient.fromItems(ItemInit.STEEL_INGOT)
    );
    public static final IArmorMaterial tungstenArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":tungsten", 42, new int[]{4, 8, 12, 6}, 28, 5f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, () -> Ingredient.fromItems(ItemInit.TUNGSTEN_INGOT)
    );
    public static final IArmorMaterial uraniumArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":uranium", 11, new int[]{2, 4, 5, 2}, 4, 0.5f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, () -> Ingredient.fromItems(ItemInit.URANIUM_INGOT)
    );
    public static final IArmorMaterial rubyArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":ruby", 24, new int[]{3, 6, 8, 4}, 14, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.RUBY)
    );
    public static final IArmorMaterial amethystArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":amethyst", 21, new int[]{2, 5, 7, 3}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.AMETHYST)
    );
    public static final IArmorMaterial aquamarineArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":aquamarine", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.AQUAMARINE)
    );
    public static final IArmorMaterial saphireArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":saphire", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.SAPHIRE)
    );
    public static final IArmorMaterial malachiteArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":malachite", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.MALACHITE)
    );
    public static final IArmorMaterial topazArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":topaz", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.TOPAZ)
    );
    public static final IArmorMaterial amberArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":amber", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.AMBER)
    );
    public static final IArmorMaterial berylArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":beryl", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.BERYL)
    );
    public static final IArmorMaterial tanzaniteArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":tanzanite", 19, new int[]{3, 6, 8, 3}, 48, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.TANZANITE)
    );
    public static final IArmorMaterial ultriniumArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":ultrinium", 95250, new int[]{2375, 5643, 6485, 1947}, 375, 3854f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 290.0F, () -> Ingredient.fromItems(ItemInit.ULTRINIUM_INGOT)
    );
    public static final IArmorMaterial infinityArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":infinity", 95250, new int[]{Integer.MAX_VALUE + 1, Integer.MAX_VALUE + 1, Integer.MAX_VALUE + 1, Integer.MAX_VALUE + 1}, 9999, Float.POSITIVE_INFINITY,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, () -> Ingredient.fromItems(ItemInit.INFINITY_INGOT)
    );

    // Armors - Copper
    public static final Item COPPER_HELMET = register("copper_helmet", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item COPPER_CHESTPLATE = register("copper_chestplate", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item COPPER_LEGGINGS = register("copper_leggings", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item COPPER_BOOTS = register("copper_boots", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Steel
    public static final Item STEEL_HELMET = register("steel_helmet", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item STEEL_CHESTPLATE = register("steel_chestplate", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item STEEL_LEGGINGS = register("steel_leggings", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item STEEL_BOOTS = register("steel_boots", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Tungsten Steel
    public static final Item TUNGSTEN_HELMET = register("tungsten_helmet", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item TUNGSTEN_CHESTPLATE = register("tungsten_chestplate", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item TUNGSTEN_LEGGINGS = register("tungsten_leggings", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item TUNGSTEN_BOOTS = register("tungsten_boots", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Uranium
    public static final Item URANIUM_HELMET = register("uranium_helmet", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item URANIUM_CHESTPLATE = register("uranium_chestplate", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item URANIUM_LEGGINGS = register("uranium_leggings", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item URANIUM_BOOTS = register("uranium_boots", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Ruby
    public static final Item RUBY_HELMET = register("ruby_helmet", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item RUBY_CHESTPLATE = register("ruby_chestplate", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item RUBY_LEGGINGS = register("ruby_leggings", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item RUBY_BOOTS = register("ruby_boots", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Amethyst
    public static final Item AMETHYST_HELMET = register("amethyst_helmet", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item AMETHYST_CHESTPLATE = register("amethyst_chestplate", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item AMETHYST_LEGGINGS = register("amethyst_leggings", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item AMETHYST_BOOTS = register("amethyst_boots", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Aquamarine
    public static final Item AQUAMARINE_HELMET = register("aquamarine_helmet", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item AQUAMARINE_CHESTPLATE = register("aquamarine_chestplate", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item AQUAMARINE_LEGGINGS = register("aquamarine_leggings", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item AQUAMARINE_BOOTS = register("aquamarine_boots", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Saphire
    public static final Item SAPHIRE_HELMET = register("saphire_helmet", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item SAPHIRE_CHESTPLATE = register("saphire_chestplate", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item SAPHIRE_LEGGINGS = register("saphire_leggings", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item SAPHIRE_BOOTS = register("saphire_boots", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Malachite
    public static final Item MALACHITE_HELMET = register("malachite_helmet", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item MALACHITE_CHESTPLATE = register("malachite_chestplate", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item MALACHITE_LEGGINGS = register("malachite_leggings", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item MALACHITE_BOOTS = register("malachite_boots", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Topaz
    public static final Item TOPAZ_HELMET = register("topaz_helmet", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item TOPAZ_CHESTPLATE = register("topaz_chestplate", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item TOPAZ_LEGGINGS = register("topaz_leggings", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item TOPAZ_BOOTS = register("topaz_boots", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Amber
    public static final Item AMBER_HELMET = register("amber_helmet", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item AMBER_CHESTPLATE = register("amber_chestplate", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item AMBER_LEGGINGS = register("amber_leggings", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item AMBER_BOOTS = register("amber_boots", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Beryl
    public static final Item BERYL_HELMET = register("beryl_helmet", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item BERYL_CHESTPLATE = register("beryl_chestplate", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item BERYL_LEGGINGS = register("beryl_leggings", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item BERYL_BOOTS = register("beryl_boots", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Tanzanite
    public static final Item TANZANITE_HELMET = register("tanzanite_helmet", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item TANZANITE_CHESTPLATE = register("tanzanite_chestplate", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item TANZANITE_LEGGINGS = register("tanzanite_leggings", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item TANZANITE_BOOTS = register("tanzanite_boots", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Ultrinium
    public static final Item ULTRINIUM_HELMET = register("ultrinium_helmet", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final Item ULTRINIUM_CHESTPLATE = register("ultrinium_chestplate", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final Item ULTRINIUM_LEGGINGS = register("ultrinium_leggings", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final Item ULTRINIUM_BOOTS = register("ultrinium_boots", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Ultrinium
    public static final Item INFINITY_HELMET = register("infinity_helmet", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.OVERPOWERED)));
    public static final Item INFINITY_CHESTPLATE = register("infinity_chestplate", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.OVERPOWERED)));
    public static final Item INFINITY_LEGGINGS = register("infinity_leggings", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.OVERPOWERED)));
    public static final Item INFINITY_BOOTS = register("infinity_boots", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.OVERPOWERED)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Tools     //
    ///////////////////

    // Materials
    public static final IItemTier COPPER_ITEM_TIER = new ItemTierBuilder.Builder(2, 420, 5.3f, 1.4f, 10,
            () -> Ingredient.fromItems(COPPER_INGOT)
    );
    public static final IItemTier STEEL_ITEM_TIER = new ItemTierBuilder.Builder(3, 1465, 8.1f, 3.8f, 14,
            () -> Ingredient.fromItems(STEEL_INGOT)
    );
    public static final IItemTier TUNGSTEN_ITEM_TIER = new ItemTierBuilder.Builder(3, 3194, 9.4f, 4.7f, 28,
            () -> Ingredient.fromItems(TUNGSTEN_INGOT)
    );
    public static final IItemTier URANIUM_ITEM_TIER = new ItemTierBuilder.Builder(2, 730, 3.6f, 5.3f, 4,
            () -> Ingredient.fromItems(URANIUM_INGOT)
    );
    public static final IItemTier RUBY_ITEM_TIER = new ItemTierBuilder.Builder(3, 970, 7.6f, 3.6f, 13,
            () -> Ingredient.fromItems(RUBY)
    );
    public static final IItemTier AMETHYST_ITEM_TIER = new ItemTierBuilder.Builder(3, 650, 7.3f, 3.1f, 31,
            () -> Ingredient.fromItems(AMETHYST)
    );
    public static final IItemTier AQUAMARINE_ITEM_TIER = new ItemTierBuilder.Builder(3, 740, 5.3f, 2.6f, 23,
            () -> Ingredient.fromItems(AQUAMARINE)
    );
    public static final IItemTier SAPHIRE_ITEM_TIER = new ItemTierBuilder.Builder(2, 810, 5.2f, 2.5f, 29,
            () -> Ingredient.fromItems(SAPHIRE)
    );
    public static final IItemTier MALACHITE_ITEM_TIER = new ItemTierBuilder.Builder(2, 670, 4.3f, 3.2f, 12,
            () -> Ingredient.fromItems(MALACHITE)
    );
    public static final IItemTier TOPAZ_ITEM_TIER = new ItemTierBuilder.Builder(2, 665, 4.4f, 3.9f, 17,
            () -> Ingredient.fromItems(TOPAZ)
    );
    public static final IItemTier AMBER_ITEM_TIER = new ItemTierBuilder.Builder(2, 670, 3.9f, 3.1f, 16,
            () -> Ingredient.fromItems(AMBER)
    );
    public static final IItemTier BERYL_ITEM_TIER = new ItemTierBuilder.Builder(2, 730, 4.8f, 3.5f, 11,
            () -> Ingredient.fromItems(BERYL)
    );
    public static final IItemTier TANZANITE_ITEM_TIER = new ItemTierBuilder.Builder(3, 1090, 7.7125f, 3.5f, 48,
            () -> Ingredient.fromItems(COPPER_INGOT)
    );
    public static final IItemTier ULTRINIUM_ITEM_TIER = new ItemTierBuilder.Builder(4, 95250, 290.0f, 2375.4f, 375,
            () -> Ingredient.fromItems(ULTRINIUM_INGOT)
    );
    public static final IItemTier INFINITY = new ItemTierBuilder.Builder(5, Integer.MAX_VALUE + 1, Float.MAX_VALUE + 1, Float.MAX_VALUE + 1, Integer.MAX_VALUE + 1,
            () -> Ingredient.fromItems(INFINITY_INGOT)
    );

    // Tools - Copper
    public static final Item COPPER_SWORD = register("copper_sword", () -> new SwordItem(COPPER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item COPPER_PICKAXE = register("copper_pickaxe", () -> new PickaxeItem(COPPER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item COPPER_SHOVEL = register("copper_shovel", () -> new ShovelItem(COPPER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item COPPER_AXE = register("copper_axe", () -> new AxeItem(COPPER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item COPPER_HOE = register("copper_hoe", () -> new HoeItem(COPPER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Steel
    public static final Item STEEL_SWORD = register("steel_sword", () -> new SwordItem(STEEL_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item STEEL_PICKAXE = register("steel_pickaxe", () -> new PickaxeItem(STEEL_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item STEEL_SHOVEL = register("steel_shovel", () -> new ShovelItem(STEEL_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item STEEL_AXE = register("steel_axe", () -> new AxeItem(STEEL_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item STEEL_HOE = register("steel_hoe", () -> new HoeItem(STEEL_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Tungsten Steel
    public static final Item TUNGSTEN_SWORD = register("tungsten_sword", () -> new SwordItem(TUNGSTEN_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item TUNGSTEN_PICKAXE = register("tungsten_pickaxe", () -> new PickaxeItem(TUNGSTEN_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item TUNGSTEN_SHOVEL = register("tungsten_shovel", () -> new ShovelItem(TUNGSTEN_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item TUNGSTEN_AXE = register("tungsten_axe", () -> new AxeItem(TUNGSTEN_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item TUNGSTEN_HOE = register("tungsten_hoe", () -> new HoeItem(TUNGSTEN_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Uranium
    public static final Item URANIUM_SWORD = register("uranium_sword", () -> new SwordItem(URANIUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item URANIUM_PICKAXE = register("uranium_pickaxe", () -> new PickaxeItem(URANIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item URANIUM_SHOVEL = register("uranium_shovel", () -> new ShovelItem(URANIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item URANIUM_AXE = register("uranium_axe", () -> new AxeItem(URANIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item URANIUM_HOE = register("uranium_hoe", () -> new HoeItem(URANIUM_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Ruby
    public static final Item RUBY_SWORD = register("ruby_sword", () -> new SwordItem(RUBY_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item RUBY_PICKAXE = register("ruby_pickaxe", () -> new PickaxeItem(RUBY_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item RUBY_SHOVEL = register("ruby_shovel", () -> new ShovelItem(RUBY_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item RUBY_AXE = register("ruby_axe", () -> new AxeItem(RUBY_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item RUBY_HOE = register("ruby_hoe", () -> new HoeItem(RUBY_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Amethyst
    public static final Item AMETHYST_SWORD = register("amethyst_sword", () -> new SwordItem(AMETHYST_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item AMETHYST_PICKAXE = register("amethyst_pickaxe", () -> new PickaxeItem(AMETHYST_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item AMETHYST_SHOVEL = register("amethyst_shovel", () -> new ShovelItem(AMETHYST_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item AMETHYST_AXE = register("amethyst_axe", () -> new AxeItem(AMETHYST_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item AMETHYST_HOE = register("amethyst_hoe", () -> new HoeItem(AMETHYST_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Aquamarine
    public static final Item AQUAMARINE_SWORD = register("aquamarine_sword", () -> new SwordItem(AQUAMARINE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item AQUAMARINE_PICKAXE = register("aquamarine_pickaxe", () -> new PickaxeItem(AQUAMARINE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item AQUAMARINE_SHOVEL = register("aquamarine_shovel", () -> new ShovelItem(AQUAMARINE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item AQUAMARINE_AXE = register("aquamarine_axe", () -> new AxeItem(AQUAMARINE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item AQUAMARINE_HOE = register("aquamarine_hoe", () -> new HoeItem(AQUAMARINE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Saphire
    public static final Item SAPHIRE_SWORD = register("saphire_sword", () -> new SwordItem(SAPHIRE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item SAPHIRE_PICKAXE = register("saphire_pickaxe", () -> new PickaxeItem(SAPHIRE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item SAPHIRE_SHOVEL = register("saphire_shovel", () -> new ShovelItem(SAPHIRE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item SAPHIRE_AXE = register("saphire_axe", () -> new AxeItem(SAPHIRE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item SAPHIRE_HOE = register("saphire_hoe", () -> new HoeItem(SAPHIRE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Malachite
    public static final Item MALACHITE_SWORD = register("malachite_sword", () -> new SwordItem(MALACHITE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item MALACHITE_PICKAXE = register("malachite_pickaxe", () -> new PickaxeItem(MALACHITE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item MALACHITE_SHOVEL = register("malachite_shovel", () -> new ShovelItem(MALACHITE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item MALACHITE_AXE = register("malachite_axe", () -> new AxeItem(MALACHITE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item MALACHITE_HOE = register("malachite_hoe", () -> new HoeItem(MALACHITE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Topaz
    public static final Item TOPAZ_SWORD = register("topaz_sword", () -> new SwordItem(TOPAZ_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item TOPAZ_PICKAXE = register("topaz_pickaxe", () -> new PickaxeItem(TOPAZ_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item TOPAZ_SHOVEL = register("topaz_shovel", () -> new ShovelItem(TOPAZ_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item TOPAZ_AXE = register("topaz_axe", () -> new AxeItem(TOPAZ_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item TOPAZ_HOE = register("topaz_hoe", () -> new HoeItem(TOPAZ_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Amber
    public static final Item AMBER_SWORD = register("amber_sword", () -> new SwordItem(AMBER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item AMBER_PICKAXE = register("amber_pickaxe", () -> new PickaxeItem(AMBER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item AMBER_SHOVEL = register("amber_shovel", () -> new ShovelItem(AMBER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item AMBER_AXE = register("amber_axe", () -> new AxeItem(AMBER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item AMBER_HOE = register("amber_hoe", () -> new HoeItem(AMBER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Beryl
    public static final Item BERYL_SWORD = register("beryl_sword", () -> new SwordItem(BERYL_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item BERYL_PICKAXE = register("beryl_pickaxe", () -> new PickaxeItem(BERYL_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item BERYL_SHOVEL = register("beryl_shovel", () -> new ShovelItem(BERYL_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item BERYL_AXE = register("beryl_axe", () -> new AxeItem(BERYL_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item BERYL_HOE = register("beryl_hoe", () -> new HoeItem(BERYL_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Tanzanite
    public static final Item TANZANITE_SWORD = register("tanzanite_sword", () -> new SwordItem(TANZANITE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item TANZANITE_PICKAXE = register("tanzanite_pickaxe", () -> new PickaxeItem(TANZANITE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item TANZANITE_SHOVEL = register("tanzanite_shovel", () -> new ShovelItem(TANZANITE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item TANZANITE_AXE = register("tanzanite_axe", () -> new AxeItem(TANZANITE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item TANZANITE_HOE = register("tanzanite_hoe", () -> new HoeItem(TANZANITE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Ultrinium
    public static final Item ULTRINIUM_SWORD = register("ultrinium_sword", () -> new SwordItem(ULTRINIUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final Item ULTRINIUM_PICKAXE = register("ultrinium_pickaxe", () -> new PickaxeItem(ULTRINIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item ULTRINIUM_SHOVEL = register("ultrinium_shovel", () -> new ShovelItem(ULTRINIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item ULTRINIUM_AXE = register("ultrinium_axe", () -> new AxeItem(ULTRINIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final Item ULTRINIUM_HOE = register("ultrinium_hoe", () -> new HoeItem(ULTRINIUM_ITEM_TIER, 3955, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Infinity
    public static final Item INFINITY_SWORD = register("infinity_sword", InfinitySwordItem::new);
    public static final Item INFINITY_PICKAXE = register("infinity_pickaxe", InfinityPickaxeItem::new);
    public static final Item INFINITY_SHOVEL = register("infinity_shovel", InfinityShovelItem::new);
    public static final Item INFINITY_AXE = register("infinity_axe", InfinityAxeItem::new);
    public static final Item INFINITY_HOE = register("infinity_hoe", InfinityHoeItem::new);

//    @SubscribeEvent
//    public static void registerItems(final RegistryEvent.Register<Item> event) {
//        Class<ItemInit> clazz = ItemInit.class;
//        Field[] fields = clazz.getFields();
//        for (Field field : fields) {
//            if (Item.class.isAssignableFrom(field.getType())) {
//                try {
//                    Item item = (Item) field.get(null);
////                    field.setAccessible(true);
////                    field.set(null, item.setRegistryName(QForgeUtils.MOD_ID, field.getName().toLowerCase()));
//                    event.getRegistry().register(item);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (Throwable t) {
//                    throw new RuntimeException("Error occurred when reading field, or registering item: " + field.getName(), t);
//                }
//            }
//        }
//    }

    private static <T extends Item> T register(String name, Supplier<T> supplier) {
        ITEMS.register(name, supplier);
        return supplier.get();
    }

    private static <ENTITY extends Entity> ItemRegistryObject<AdditionsSpawnEggItem> registerSpawnEgg(EntityTypeRegistryObject<ENTITY> entityTypeProvider,
                                                                                                      int primaryColor, int secondaryColor) {
        //Note: We are required to use a custom item as we cannot use the base SpawnEggItem due to the entity type not being initialized yet
        return ITEMS.register(entityTypeProvider.getInternalRegistryName() + "_spawn_egg", () -> new AdditionsSpawnEggItem(entityTypeProvider, primaryColor, secondaryColor));
    }
}
