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
import com.qsoftware.forgemod.util.builder.ArmorMaterialBuilder;
import com.qsoftware.forgemod.util.builder.ItemTierBuilder;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;

@SuppressWarnings({"unused", "NumericOverflow"})
//@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
//@ObjectHolder(QForgeUtils.MOD_ID)
public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, QForgeUtils.MOD_ID);
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Overpowered     //
    /////////////////////////
    public static final RegistryObject<Item> KILL_SWITCH = ITEMS.register("kill_switch", KillSwitchItem::new);
    public static final RegistryObject<Item> BAN_HAMMER = ITEMS.register("ban_hammer", BanHammerItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Miscellaneous     //
    ///////////////////////////
    public static final RegistryObject<Item> LEGENDARY_ENDER_PEARL = ITEMS.register("legendary_ender_pearl", () -> new LegendaryEnderPearlItem(new Item.Properties().group(Groups.MISC)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Food     //
    //////////////////
    public static final RegistryObject<Item> CHEESE_BURGER = ITEMS.register("cheese_burger", () -> new Item(new Item.Properties().group(Groups.FOOD).food(new Food.Builder().hunger(1).saturation(0.2f).effect(() -> new EffectInstance(Effects.REGENERATION, 60, 1), 0.7f).build())));
    public static final RegistryObject<Item> CHEESE_SLICE = ITEMS.register("cheese_slice", () -> new Item(new Item.Properties().group(Groups.FOOD).food(new Food.Builder().hunger(1).saturation(0.2f).build())));
    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese", () -> new SliceableItem(new Item.Properties().group(Groups.FOOD).food(new Food.Builder().hunger(2).saturation(0.3f).build()), (stack) -> new ItemStack(CHEESE_SLICE.get(), stack.getCount() * 6)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Normal     //
    ////////////////////
    public static final RegistryObject<Item> STICK_VARIANT_1 = ITEMS.register("stick_variant_1", () -> new Item(new Item.Properties().group(Groups.NATURE)));
    public static final RegistryObject<Item> STICK_VARIANT_2 = ITEMS.register("stick_variant_2", () -> new Item(new Item.Properties().group(Groups.NATURE)));
    public static final RegistryObject<Item> EUCALYPTUS_PLANK = ITEMS.register("eucalyptus_plank", () -> new Item(new Item.Properties().group(Groups.WOOD)));
    public static final RegistryObject<Item> EUCALYPTUS_LEAF = ITEMS.register("eucalyptus_leaf", () -> new Item(new Item.Properties()
            .group(Groups.NATURE)
            .food(new Food.Builder()
                    .hunger(1)
                    .saturation(0.2f)
                    .effect(() -> new EffectInstance(Effects.REGENERATION, 60, 1), 0.7f)
                    .build())));

    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", () -> new KnifeItem(new Item.Properties().group(Groups.SPECIALS).maxDamage(4)));
    public static final RegistryObject<Item> MAGNET = ITEMS.register("magnet", () -> new MagnetItem(new Item.Properties().group(Groups.SPECIALS).maxDamage(4)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Fletching     //
    ///////////////////////

    // Bows
    public static final RegistryObject<BowItem> BLAZE_BOW = ITEMS.register("blaze_bow", () -> (BowItem) new AdvancedBowItem(new Item.Properties().group(Groups.FLETCHING), 6.25f, 1.0f, 6, 1, true));
    public static final RegistryObject<BowItem> ICE_BOW = ITEMS.register("ice_bow", () -> (BowItem) new AdvancedBowItem(new Item.Properties().group(Groups.FLETCHING), 2f, 1.0f, 8, 2));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Fletching     //
    ///////////////////////

    // Bows
    public static final RegistryObject<SpawnEggItem> DUCK_SPAWN_EGG = ITEMS.register("duck_spawn_egg", () -> (SpawnEggItem) new SpawnEggItem(
            EntityTypeInit.DUCK_ENTITY.get(),
            new Color(4, 104, 14).getRGB(),
            new Color(228, 181, 15).getRGB(),
            new Item.Properties().group(Groups.SPAWN_EGGS)));
    public static final RegistryObject<SpawnEggItem> HOG_SPAWN_EGG = ITEMS.register("hog_spawn_egg", () -> (SpawnEggItem) new SpawnEggItem(
            EntityTypeInit.HOG_ENTITY.get(),
            new Color(84, 21, 0).getRGB(),
            new Color(166, 103, 61).getRGB(),
            new Item.Properties().group(Groups.SPAWN_EGGS)));
    public static final RegistryObject<SpawnEggItem> WRAT_HOG_SPAWN_EGG = ITEMS.register("wrat_hog_spawn_egg", () -> (SpawnEggItem) new SpawnEggItem(
            EntityTypeInit.WRAT_HOG_ENTITY.get(),
            new Color(183, 111, 60).getRGB(),
            new Color(148, 90, 49).getRGB(),
            new Item.Properties().group(Groups.SPAWN_EGGS)));
    public static final RegistryObject<SpawnEggItem> BISON_SPAWN_EGG = ITEMS.register("bison_spawn_egg", () -> (SpawnEggItem) new SpawnEggItem(
            EntityTypeInit.BISON_ENTITY.get(),
            new Color(79, 43, 5).getRGB(),
            new Color(180, 149, 56).getRGB(),
            new Item.Properties().group(Groups.SPAWN_EGGS)));
    public static final RegistryObject<SpawnEggItem> MOOBLOOM_SPAWN_EGG = ITEMS.register("moobloom_spawn_egg", () -> (SpawnEggItem) new SpawnEggItem(
            EntityTypeInit.MOOBLOOM_ENTITY.get(),
            new Color(253, 213, 5).getRGB(),
            new Color(247, 237, 193).getRGB(),
            new Item.Properties().group(Groups.SPAWN_EGGS)));
    public static final RegistryObject<SpawnEggItem> OX_SPAWN_EGG = ITEMS.register("ox_spawn_egg", () -> (SpawnEggItem) new SpawnEggItem(
            EntityTypeInit.OX_ENTITY.get(),
            new Color(164, 110, 61).getRGB(),
            new Color(212, 149, 92).getRGB(),
            new Item.Properties().group(Groups.SPAWN_EGGS)));
    public static final RegistryObject<SpawnEggItem> ICE_ENDERMAN_SPAWN_EGG = ITEMS.register("ice_enderman_spawn_egg", () -> (SpawnEggItem) new SpawnEggItem(
            EntityTypeInit.ICE_ENDERMAN_ENTITY.get(),
            new Color(0, 0, 0).getRGB(),
            new Color(123, 214, 214).getRGB(),
            new Item.Properties().group(Groups.SPAWN_EGGS)));
    public static final RegistryObject<SpawnEggItem> FIRE_CREEPER_SPAWN_EGG = ITEMS.register("fire_creeper_spawn_egg", () -> (SpawnEggItem) new SpawnEggItem(
            EntityTypeInit.FIRE_CREEPER_ENTITY.get(),
            new Color(54, 58, 54).getRGB(),
            new Color(209, 39, 39).getRGB(),
            new Item.Properties().group(Groups.SPAWN_EGGS)));
    public static final RegistryObject<SpawnEggItem> GLOW_SQUID_SPAWN_EGG = ITEMS.register("glow_squid_spawn_egg", () -> (SpawnEggItem) new SpawnEggItem(
            EntityTypeInit.GLOW_SQUID_ENTITY.get(),
            new Color(47, 151, 153).getRGB(),
            new Color(84, 221, 153).getRGB(),
            new Item.Properties().group(Groups.SPAWN_EGGS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Ingredients     //
    /////////////////////////

    // Glass shards
    public static final RegistryObject<Item> CLEAR_SHARD = ITEMS.register("clear_shard", IngredientItem::new);
    public static final RegistryObject<Item> BLACK_SHARD = ITEMS.register("black_shard", IngredientItem::new);
    public static final RegistryObject<Item> BLUE_SHARD = ITEMS.register("blue_shard", IngredientItem::new);
    public static final RegistryObject<Item> BROWN_SHARD = ITEMS.register("brown_shard", IngredientItem::new);
    public static final RegistryObject<Item> CYAN_SHARD = ITEMS.register("cyan_shard", IngredientItem::new);
    public static final RegistryObject<Item> GRAY_SHARD = ITEMS.register("gray_shard", IngredientItem::new);
    public static final RegistryObject<Item> GREEN_SHARD = ITEMS.register("green_shard", IngredientItem::new);
    public static final RegistryObject<Item> LIGHT_BLUE_SHARD = ITEMS.register("light_blue_shard", IngredientItem::new);
    public static final RegistryObject<Item> LIGHT_GRAY_SHARD = ITEMS.register("light_gray_shard", IngredientItem::new);
    public static final RegistryObject<Item> LIME_SHARD = ITEMS.register("lime_shard", IngredientItem::new);
    public static final RegistryObject<Item> MAGENTA_SHARD = ITEMS.register("magenta_shard", IngredientItem::new);
    public static final RegistryObject<Item> ORANGE_SHARD = ITEMS.register("orange_shard", IngredientItem::new);
    public static final RegistryObject<Item> PINK_SHARD = ITEMS.register("pink_shard", IngredientItem::new);
    public static final RegistryObject<Item> PURPLE_SHARD = ITEMS.register("purple_shard", IngredientItem::new);
    public static final RegistryObject<Item> RED_SHARD = ITEMS.register("red_shard", IngredientItem::new);
    public static final RegistryObject<Item> WHITE_SHARD = ITEMS.register("white_shard", IngredientItem::new);
    public static final RegistryObject<Item> YELLOW_SHARD = ITEMS.register("yellow_shard", IngredientItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Ingots or Dusts     //
    /////////////////////////////

    // Metals - Copper Level
    public static final RegistryObject<Item> COPPER_INGOT = ITEMS.register("copper_ingot", IngotOrDustItem::new);
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", IngotOrDustItem::new);
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", IngotOrDustItem::new);

    // Metals - Steel Level
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", IngotOrDustItem::new);
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", IngotOrDustItem::new);
    public static final RegistryObject<Item> STEEL_DUST = ITEMS.register("steel_dust", IngotOrDustItem::new);

    // Metals - Tungsten Steel Level
    public static final RegistryObject<Item> TUNGSTEN_INGOT = ITEMS.register("tungsten_ingot", IngotOrDustItem::new);
    public static final RegistryObject<Item> TUNGSTEN_NUGGET = ITEMS.register("tungsten_nugget", IngotOrDustItem::new);
    public static final RegistryObject<Item> TUNGSTEN_DUST = ITEMS.register("tungsten_dust", IngotOrDustItem::new);

    // Metals - Ultrinium Level
    public static final RegistryObject<Item> ULTRINIUM_INGOT = ITEMS.register("ultrinium_ingot", IngotOrDustItem::new);
    public static final RegistryObject<Item> ULTRINIUM_NUGGET = ITEMS.register("ultrinium_nugget", IngotOrDustItem::new);
    public static final RegistryObject<Item> ULTRINIUM_DUST = ITEMS.register("ultrinium_dust", IngotOrDustItem::new);

    // Metals - Infinity Level
    public static final RegistryObject<Item> UNSTABLE_INFINITY_INGOT = ITEMS.register("unstable_infinity_ingot", UnstableInfinityIngot::new);
    public static final RegistryObject<Item> INFINITY_INGOT = ITEMS.register("infinity_ingot", IngotOrDustItem::new);
    public static final RegistryObject<Item> INFINITY_NUGGET = ITEMS.register("infinity_nugget", IngotOrDustItem::new);
    public static final RegistryObject<Item> INFINITY_DUST = ITEMS.register("infinity_dust", IngotOrDustItem::new);

    // Metals - Uranium Level
    public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot", IngotOrDustItem::new);
    public static final RegistryObject<Item> URANIUM_NUGGET = ITEMS.register("uranium_nugget", IngotOrDustItem::new);
    public static final RegistryObject<Item> URANIUM_DUST = ITEMS.register("uranium_dust", IngotOrDustItem::new);

    // Gems and other metals
    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> RUBY_DUST = ITEMS.register("ruby_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> AMETHYST_DUST = ITEMS.register("amethyst_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> AQUAMARINE_DUST = ITEMS.register("aquamarine_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> SAPHIRE_DUST = ITEMS.register("saphire_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> MALACHITE_DUST = ITEMS.register("malachite_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> TOPAZ_DUST = ITEMS.register("topaz_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> AMBER_DUST = ITEMS.register("amber_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> BERYL_DUST = ITEMS.register("beryl_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> DIAMOND_DUST = ITEMS.register("diamond_dust", IngotOrDustItem::new);
    public static final RegistryObject<Item> TANZANITE_DUST = ITEMS.register("tanzanite_dustt", IngotOrDustItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Gems     //
    //////////////////
    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final RegistryObject<Item> AQUAMARINE = ITEMS.register("aquamarine", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final RegistryObject<Item> SAPHIRE = ITEMS.register("saphire", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final RegistryObject<Item> MALACHITE = ITEMS.register("malachite", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final RegistryObject<Item> TOPAZ = ITEMS.register("topaz", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final RegistryObject<Item> AMBER = ITEMS.register("amber", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final RegistryObject<Item> PERIDOT = ITEMS.register("peridot", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final RegistryObject<Item> BERYL = ITEMS.register("beryl", () -> new Item(new Item.Properties().group(Groups.GEMS)));
    public static final RegistryObject<Item> TANZANITE = ITEMS.register("tanzanite", () -> new Item(new Item.Properties().group(Groups.GEMS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Icons     //
    ///////////////////
    public static final RegistryObject<Item> DUNGEONS = ITEMS.register("dungeons", () -> new SwordItem(ItemTier.DIAMOND, 0, 0f, new Item.Properties().setNoRepair().maxStackSize(1)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Dungeons     //
    //////////////////////
    public static final RegistryObject<AxeItem> DUNGEONS_DIAMOND_AXE = ITEMS.register("dungeons_diamond_axe", () -> (AxeItem) new AxeItem(ItemTier.DIAMOND, 4, -2.0f, new Item.Properties().group(Groups.DUNGEONS)));
    public static final RegistryObject<SwordItem> DUNGEONS_DIAMOND_SWORD = ITEMS.register("dungeons_diamond_sword", () -> (SwordItem) new SwordItem(ItemTier.DIAMOND, 4, -2.0f, new Item.Properties().group(Groups.DUNGEONS)));
    public static final RegistryObject<SwordItem> DUNGEONS_IRON_SWORD = ITEMS.register("dungeons_iron_sword", () -> (SwordItem) new SwordItem(ItemTier.IRON, 4, -2.0f, new Item.Properties().group(Groups.DUNGEONS)));
    public static final RegistryObject<SwordItem> DUNGEONS_BROADSWORD = ITEMS.register("dungeons_broadsword", () -> (SwordItem) new SwordItem(ItemTier.IRON, 5, -3.75f, new Item.Properties().group(Groups.DUNGEONS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Specials     //
    //////////////////////

    // Wands
    public static final RegistryObject<Item> WALKING_STAFF = ITEMS.register("walking_staff", WandItem::new);
    public static final RegistryObject<Item> LIGHTNING_STAFF = ITEMS.register("lightning_staff", LightningStaffItem::new);
    public static final RegistryObject<Item> NATURE_STAFF = ITEMS.register("nature_staff", NatureStaffItem::new);

    // Stone Level
    public static final RegistryObject<SwordItem> STONE_SWORD_OF_DOOM = ITEMS.register("stone_sword_of_doom", () -> (SwordItem) new SwordItem(ItemTier.STONE, 8, -2.0f, new Item.Properties().group(Groups.SPECIALS)));

    // Iron Level
    public static final RegistryObject<AxeItem> EMERGENCY_FIRE_AXE = ITEMS.register("emergency_fire_axe", () -> (AxeItem) new AxeItem(ItemTier.IRON, 3, -2.55f, new Item.Properties().group(Groups.SPECIALS)));
    public static final RegistryObject<SwordItem> FIRE_SWORD = ITEMS.register("fire_sword", () -> (SwordItem) new FireSwordItem(ItemTier.IRON, 5, -3.5f, new Item.Properties().group(Groups.SPECIALS)));

    // Diamond Level
    public static final RegistryObject<AxeItem> LEVIATHAN_AXE = ITEMS.register("leviathan_axe", () -> (AxeItem) new AxeItem(ItemTier.DIAMOND, 5, -2.55f, new Item.Properties().group(Groups.SPECIALS)));
    public static final RegistryObject<AxeItem> ADAMANTANIUM_AXE_RED = ITEMS.register("adamantanium_axe_red", () -> (AxeItem) new AxeItem(ItemTier.DIAMOND, 8, -1.875f, new Item.Properties().group(Groups.SPECIALS)));

    public static final RegistryObject<SwordItem> DIAMOND_QUARTZ_SWORD = ITEMS.register("diamond_quartz_sword", () -> (SwordItem) new SwordItem(ItemTier.DIAMOND, 8, -2.0f, new Item.Properties().group(Groups.SPECIALS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Armors     //
    ////////////////////

    // Materials
    public static final IArmorMaterial copperArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":copper", 13, new int[]{2, 5, 6, 2}, 10, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> Ingredient.fromItems(ItemInit.COPPER_INGOT.get())
    );
    public static final IArmorMaterial steelArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":steel", 24, new int[]{3, 6, 8, 4}, 14, 4f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, () -> Ingredient.fromItems(ItemInit.STEEL_INGOT.get())
    );
    public static final IArmorMaterial tungstenArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":tungsten", 42, new int[]{4, 8, 12, 6}, 28, 5f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, () -> Ingredient.fromItems(ItemInit.TUNGSTEN_INGOT.get())
    );
    public static final IArmorMaterial uraniumArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":uranium", 11, new int[]{2, 4, 5, 2}, 4, 0.5f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, () -> Ingredient.fromItems(ItemInit.URANIUM_INGOT.get())
    );
    public static final IArmorMaterial rubyArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":ruby", 24, new int[]{3, 6, 8, 4}, 14, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.RUBY.get())
    );
    public static final IArmorMaterial amethystArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":amethyst", 21, new int[]{2, 5, 7, 3}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.AMETHYST.get())
    );
    public static final IArmorMaterial aquamarineArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":aquamarine", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.AQUAMARINE.get())
    );
    public static final IArmorMaterial saphireArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":saphire", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.SAPHIRE.get())
    );
    public static final IArmorMaterial malachiteArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":malachite", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.MALACHITE.get())
    );
    public static final IArmorMaterial topazArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":topaz", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.TOPAZ.get())
    );
    public static final IArmorMaterial amberArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":amber", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.AMBER.get())
    );
    public static final IArmorMaterial berylArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":beryl", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.BERYL.get())
    );
    public static final IArmorMaterial tanzaniteArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":tanzanite", 19, new int[]{3, 6, 8, 3}, 48, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ItemInit.TANZANITE.get())
    );
    public static final IArmorMaterial ultriniumArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":ultrinium", 95250, new int[]{2375, 5643, 6485, 1947}, 375, 3854f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 290.0F, () -> Ingredient.fromItems(ItemInit.ULTRINIUM_INGOT.get())
    );
    public static final IArmorMaterial infinityArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeUtils.MOD_ID + ":infinity", 95250, new int[]{Integer.MAX_VALUE + 1, Integer.MAX_VALUE + 1, Integer.MAX_VALUE + 1, Integer.MAX_VALUE + 1}, 9999, Float.POSITIVE_INFINITY,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0, () -> Ingredient.fromItems(ItemInit.INFINITY_INGOT.get())
    );

    // Armors - Copper
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Steel
    public static final RegistryObject<Item> STEEL_HELMET = ITEMS.register("steel_helmet", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> STEEL_BOOTS = ITEMS.register("steel_boots", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Tungsten Steel
    public static final RegistryObject<Item> TUNGSTEN_HELMET = ITEMS.register("tungsten_helmet", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> TUNGSTEN_CHESTPLATE = ITEMS.register("tungsten_chestplate", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> TUNGSTEN_LEGGINGS = ITEMS.register("tungsten_leggings", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> TUNGSTEN_BOOTS = ITEMS.register("tungsten_boots", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Uranium
    public static final RegistryObject<Item> URANIUM_HELMET = ITEMS.register("uranium_helmet", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> URANIUM_CHESTPLATE = ITEMS.register("uranium_chestplate", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> URANIUM_LEGGINGS = ITEMS.register("uranium_leggings", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> URANIUM_BOOTS = ITEMS.register("uranium_boots", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Ruby
    public static final RegistryObject<Item> RUBY_HELMET = ITEMS.register("ruby_helmet", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> RUBY_CHESTPLATE = ITEMS.register("ruby_chestplate", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> RUBY_LEGGINGS = ITEMS.register("ruby_leggings", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> RUBY_BOOTS = ITEMS.register("ruby_boots", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Amethyst
    public static final RegistryObject<Item> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> AMETHYST_LEGGINGS = ITEMS.register("amethyst_leggings", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> AMETHYST_BOOTS = ITEMS.register("amethyst_boots", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Aquamarine
    public static final RegistryObject<Item> AQUAMARINE_HELMET = ITEMS.register("aquamarine_helmet", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> AQUAMARINE_CHESTPLATE = ITEMS.register("aquamarine_chestplate", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> AQUAMARINE_LEGGINGS = ITEMS.register("aquamarine_leggings", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> AQUAMARINE_BOOTS = ITEMS.register("aquamarine_boots", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Saphire
    public static final RegistryObject<Item> SAPHIRE_HELMET = ITEMS.register("saphire_helmet", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> SAPHIRE_CHESTPLATE = ITEMS.register("saphire_chestplate", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> SAPHIRE_LEGGINGS = ITEMS.register("saphire_leggings", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> SAPHIRE_BOOTS = ITEMS.register("saphire_boots", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Malachite
    public static final RegistryObject<Item> MALACHITE_HELMET = ITEMS.register("malachite_helmet", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> MALACHITE_CHESTPLATE = ITEMS.register("malachite_chestplate", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> MALACHITE_LEGGINGS = ITEMS.register("malachite_leggings", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> MALACHITE_BOOTS = ITEMS.register("malachite_boots", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Topaz
    public static final RegistryObject<Item> TOPAZ_HELMET = ITEMS.register("topaz_helmet", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> TOPAZ_CHESTPLATE = ITEMS.register("topaz_chestplate", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> TOPAZ_LEGGINGS = ITEMS.register("topaz_leggings", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> TOPAZ_BOOTS = ITEMS.register("topaz_boots", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Amber
    public static final RegistryObject<Item> AMBER_HELMET = ITEMS.register("amber_helmet", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> AMBER_CHESTPLATE = ITEMS.register("amber_chestplate", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> AMBER_LEGGINGS = ITEMS.register("amber_leggings", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> AMBER_BOOTS = ITEMS.register("amber_boots", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Beryl
    public static final RegistryObject<Item> BERYL_HELMET = ITEMS.register("beryl_helmet", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> BERYL_CHESTPLATE = ITEMS.register("beryl_chestplate", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> BERYL_LEGGINGS = ITEMS.register("beryl_leggings", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> BERYL_BOOTS = ITEMS.register("beryl_boots", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Tanzanite
    public static final RegistryObject<Item> TANZANITE_HELMET = ITEMS.register("tanzanite_helmet", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> TANZANITE_CHESTPLATE = ITEMS.register("tanzanite_chestplate", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> TANZANITE_LEGGINGS = ITEMS.register("tanzanite_leggings", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> TANZANITE_BOOTS = ITEMS.register("tanzanite_boots", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Ultrinium
    public static final RegistryObject<Item> ULTRINIUM_HELMET = ITEMS.register("ultrinium_helmet", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> ULTRINIUM_CHESTPLATE = ITEMS.register("ultrinium_chestplate", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> ULTRINIUM_LEGGINGS = ITEMS.register("ultrinium_leggings", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)));
    public static final RegistryObject<Item> ULTRINIUM_BOOTS = ITEMS.register("ultrinium_boots", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)));

    // Armors - Ultrinium
    public static final RegistryObject<Item> INFINITY_HELMET = ITEMS.register("infinity_helmet", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.OVERPOWERED)));
    public static final RegistryObject<Item> INFINITY_CHESTPLATE = ITEMS.register("infinity_chestplate", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.OVERPOWERED)));
    public static final RegistryObject<Item> INFINITY_LEGGINGS = ITEMS.register("infinity_leggings", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.OVERPOWERED)));
    public static final RegistryObject<Item> INFINITY_BOOTS = ITEMS.register("infinity_boots", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.OVERPOWERED)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Tools     //
    ///////////////////

    // Materials
    public static final IItemTier COPPER_ITEM_TIER = new ItemTierBuilder.Builder(2, 420, 5.3f, 1.4f, 10,
            () -> Ingredient.fromItems(COPPER_INGOT.get())
    );
    public static final IItemTier STEEL_ITEM_TIER = new ItemTierBuilder.Builder(3, 1465, 8.1f, 3.8f, 14,
            () -> Ingredient.fromItems(STEEL_INGOT.get())
    );
    public static final IItemTier TUNGSTEN_ITEM_TIER = new ItemTierBuilder.Builder(3, 3194, 9.4f, 4.7f, 28,
            () -> Ingredient.fromItems(TUNGSTEN_INGOT.get())
    );
    public static final IItemTier URANIUM_ITEM_TIER = new ItemTierBuilder.Builder(2, 730, 3.6f, 5.3f, 4,
            () -> Ingredient.fromItems(URANIUM_INGOT.get())
    );
    public static final IItemTier RUBY_ITEM_TIER = new ItemTierBuilder.Builder(3, 970, 7.6f, 3.6f, 13,
            () -> Ingredient.fromItems(RUBY.get())
    );
    public static final IItemTier AMETHYST_ITEM_TIER = new ItemTierBuilder.Builder(3, 650, 7.3f, 3.1f, 31,
            () -> Ingredient.fromItems(AMETHYST.get())
    );
    public static final IItemTier AQUAMARINE_ITEM_TIER = new ItemTierBuilder.Builder(3, 740, 5.3f, 2.6f, 23,
            () -> Ingredient.fromItems(AQUAMARINE.get())
    );
    public static final IItemTier SAPHIRE_ITEM_TIER = new ItemTierBuilder.Builder(2, 810, 5.2f, 2.5f, 29,
            () -> Ingredient.fromItems(SAPHIRE.get())
    );
    public static final IItemTier MALACHITE_ITEM_TIER = new ItemTierBuilder.Builder(2, 670, 4.3f, 3.2f, 12,
            () -> Ingredient.fromItems(MALACHITE.get())
    );
    public static final IItemTier TOPAZ_ITEM_TIER = new ItemTierBuilder.Builder(2, 665, 4.4f, 3.9f, 17,
            () -> Ingredient.fromItems(TOPAZ.get())
    );
    public static final IItemTier AMBER_ITEM_TIER = new ItemTierBuilder.Builder(2, 670, 3.9f, 3.1f, 16,
            () -> Ingredient.fromItems(AMBER.get())
    );
    public static final IItemTier BERYL_ITEM_TIER = new ItemTierBuilder.Builder(2, 730, 4.8f, 3.5f, 11,
            () -> Ingredient.fromItems(BERYL.get())
    );
    public static final IItemTier TANZANITE_ITEM_TIER = new ItemTierBuilder.Builder(3, 1090, 7.7125f, 3.5f, 48,
            () -> Ingredient.fromItems(COPPER_INGOT.get())
    );
    public static final IItemTier ULTRINIUM_ITEM_TIER = new ItemTierBuilder.Builder(4, 95250, 290.0f, 2375.4f, 375,
            () -> Ingredient.fromItems(ULTRINIUM_INGOT.get())
    );
    public static final IItemTier INFINITY = new ItemTierBuilder.Builder(5, Integer.MAX_VALUE + 1, Float.MAX_VALUE + 1, Float.MAX_VALUE + 1, Integer.MAX_VALUE + 1,
            () -> Ingredient.fromItems(INFINITY_INGOT.get())
    );

    // Tools - Copper
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new SwordItem(COPPER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new PickaxeItem(COPPER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new ShovelItem(COPPER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new AxeItem(COPPER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new HoeItem(COPPER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Steel
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(STEEL_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe", () -> new PickaxeItem(STEEL_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel", () -> new ShovelItem(STEEL_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe", () -> new AxeItem(STEEL_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe", () -> new HoeItem(STEEL_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Tungsten Steel
    public static final RegistryObject<Item> TUNGSTEN_SWORD = ITEMS.register("tungsten_sword", () -> new SwordItem(TUNGSTEN_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> TUNGSTEN_PICKAXE = ITEMS.register("tungsten_pickaxe", () -> new PickaxeItem(TUNGSTEN_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> TUNGSTEN_SHOVEL = ITEMS.register("tungsten_shovel", () -> new ShovelItem(TUNGSTEN_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> TUNGSTEN_AXE = ITEMS.register("tungsten_axe", () -> new AxeItem(TUNGSTEN_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> TUNGSTEN_HOE = ITEMS.register("tungsten_hoe", () -> new HoeItem(TUNGSTEN_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Uranium
    public static final RegistryObject<Item> URANIUM_SWORD = ITEMS.register("uranium_sword", () -> new SwordItem(URANIUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> URANIUM_PICKAXE = ITEMS.register("uranium_pickaxe", () -> new PickaxeItem(URANIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> URANIUM_SHOVEL = ITEMS.register("uranium_shovel", () -> new ShovelItem(URANIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> URANIUM_AXE = ITEMS.register("uranium_axe", () -> new AxeItem(URANIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> URANIUM_HOE = ITEMS.register("uranium_hoe", () -> new HoeItem(URANIUM_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Ruby
    public static final RegistryObject<Item> RUBY_SWORD = ITEMS.register("ruby_sword", () -> new SwordItem(RUBY_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> RUBY_PICKAXE = ITEMS.register("ruby_pickaxe", () -> new PickaxeItem(RUBY_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> RUBY_SHOVEL = ITEMS.register("ruby_shovel", () -> new ShovelItem(RUBY_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> RUBY_AXE = ITEMS.register("ruby_axe", () -> new AxeItem(RUBY_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> RUBY_HOE = ITEMS.register("ruby_hoe", () -> new HoeItem(RUBY_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Amethyst
    public static final RegistryObject<Item> AMETHYST_SWORD = ITEMS.register("amethyst_sword", () -> new SwordItem(AMETHYST_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe", () -> new PickaxeItem(AMETHYST_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> AMETHYST_SHOVEL = ITEMS.register("amethyst_shovel", () -> new ShovelItem(AMETHYST_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> AMETHYST_AXE = ITEMS.register("amethyst_axe", () -> new AxeItem(AMETHYST_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> AMETHYST_HOE = ITEMS.register("amethyst_hoe", () -> new HoeItem(AMETHYST_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Aquamarine
    public static final RegistryObject<Item> AQUAMARINE_SWORD = ITEMS.register("aquamarine_sword", () -> new SwordItem(AQUAMARINE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> AQUAMARINE_PICKAXE = ITEMS.register("aquamarine_pickaxe", () -> new PickaxeItem(AQUAMARINE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> AQUAMARINE_SHOVEL = ITEMS.register("aquamarine_shovel", () -> new ShovelItem(AQUAMARINE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> AQUAMARINE_AXE = ITEMS.register("aquamarine_axe", () -> new AxeItem(AQUAMARINE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> AQUAMARINE_HOE = ITEMS.register("aquamarine_hoe", () -> new HoeItem(AQUAMARINE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Saphire
    public static final RegistryObject<Item> SAPHIRE_SWORD = ITEMS.register("saphire_sword", () -> new SwordItem(SAPHIRE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> SAPHIRE_PICKAXE = ITEMS.register("saphire_pickaxe", () -> new PickaxeItem(SAPHIRE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> SAPHIRE_SHOVEL = ITEMS.register("saphire_shovel", () -> new ShovelItem(SAPHIRE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> SAPHIRE_AXE = ITEMS.register("saphire_axe", () -> new AxeItem(SAPHIRE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> SAPHIRE_HOE = ITEMS.register("saphire_hoe", () -> new HoeItem(SAPHIRE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Malachite
    public static final RegistryObject<Item> MALACHITE_SWORD = ITEMS.register("malachite_sword", () -> new SwordItem(MALACHITE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> MALACHITE_PICKAXE = ITEMS.register("malachite_pickaxe", () -> new PickaxeItem(MALACHITE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> MALACHITE_SHOVEL = ITEMS.register("malachite_shovel", () -> new ShovelItem(MALACHITE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> MALACHITE_AXE = ITEMS.register("malachite_axe", () -> new AxeItem(MALACHITE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> MALACHITE_HOE = ITEMS.register("malachite_hoe", () -> new HoeItem(MALACHITE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Topaz
    public static final RegistryObject<Item> TOPAZ_SWORD = ITEMS.register("topaz_sword", () -> new SwordItem(TOPAZ_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> TOPAZ_PICKAXE = ITEMS.register("topaz_pickaxe", () -> new PickaxeItem(TOPAZ_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> TOPAZ_SHOVEL = ITEMS.register("topaz_shovel", () -> new ShovelItem(TOPAZ_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> TOPAZ_AXE = ITEMS.register("topaz_axe", () -> new AxeItem(TOPAZ_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> TOPAZ_HOE = ITEMS.register("topaz_hoe", () -> new HoeItem(TOPAZ_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Amber
    public static final RegistryObject<Item> AMBER_SWORD = ITEMS.register("amber_sword", () -> new SwordItem(AMBER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> AMBER_PICKAXE = ITEMS.register("amber_pickaxe", () -> new PickaxeItem(AMBER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> AMBER_SHOVEL = ITEMS.register("amber_shovel", () -> new ShovelItem(AMBER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> AMBER_AXE = ITEMS.register("amber_axe", () -> new AxeItem(AMBER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> AMBER_HOE = ITEMS.register("amber_hoe", () -> new HoeItem(AMBER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Beryl
    public static final RegistryObject<Item> BERYL_SWORD = ITEMS.register("beryl_sword", () -> new SwordItem(BERYL_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> BERYL_PICKAXE = ITEMS.register("beryl_pickaxe", () -> new PickaxeItem(BERYL_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> BERYL_SHOVEL = ITEMS.register("beryl_shovel", () -> new ShovelItem(BERYL_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> BERYL_AXE = ITEMS.register("beryl_axe", () -> new AxeItem(BERYL_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> BERYL_HOE = ITEMS.register("beryl_hoe", () -> new HoeItem(BERYL_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Tanzanite
    public static final RegistryObject<Item> TANZANITE_SWORD = ITEMS.register("tanzanite_sword", () -> new SwordItem(TANZANITE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> TANZANITE_PICKAXE = ITEMS.register("tanzanite_pickaxe", () -> new PickaxeItem(TANZANITE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> TANZANITE_SHOVEL = ITEMS.register("tanzanite_shovel", () -> new ShovelItem(TANZANITE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> TANZANITE_AXE = ITEMS.register("tanzanite_axe", () -> new AxeItem(TANZANITE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> TANZANITE_HOE = ITEMS.register("tanzanite_hoe", () -> new HoeItem(TANZANITE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Ultrinium
    public static final RegistryObject<Item> ULTRINIUM_SWORD = ITEMS.register("ultrinium_sword", () -> new SwordItem(ULTRINIUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS))); // ModItemTier.ULTRINIUM)));
    public static final RegistryObject<Item> ULTRINIUM_PICKAXE = ITEMS.register("ultrinium_pickaxe", () -> new PickaxeItem(ULTRINIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> ULTRINIUM_SHOVEL = ITEMS.register("ultrinium_shovel", () -> new ShovelItem(ULTRINIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> ULTRINIUM_AXE = ITEMS.register("ultrinium_axe", () -> new AxeItem(ULTRINIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)));
    public static final RegistryObject<Item> ULTRINIUM_HOE = ITEMS.register("ultrinium_hoe", () -> new HoeItem(ULTRINIUM_ITEM_TIER, 3955, -2.0f, new Item.Properties().group(Groups.TOOLS)));

    // Tools - Infinity
    public static final RegistryObject<Item> INFINITY_SWORD = ITEMS.register("infinity_sword", InfinitySwordItem::new);
    public static final RegistryObject<Item> INFINITY_PICKAXE = ITEMS.register("infinity_pickaxe", InfinityPickaxeItem::new);
    public static final RegistryObject<Item> INFINITY_SHOVEL = ITEMS.register("infinity_shovel", InfinityShovelItem::new);
    public static final RegistryObject<Item> INFINITY_AXE = ITEMS.register("infinity_axe", InfinityAxeItem::new);
    public static final RegistryObject<Item> INFINITY_HOE = ITEMS.register("infinity_hoe", InfinityHoeItem::new);

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
}
