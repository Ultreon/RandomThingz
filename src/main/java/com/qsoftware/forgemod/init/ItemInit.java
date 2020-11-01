package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.objects.items.*;
import com.qsoftware.forgemod.objects.items.advanced.AdvancedBowItem;
import com.qsoftware.forgemod.objects.items.base.IngotsOrDustsItem;
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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@SuppressWarnings({"unused", "NumericOverflow"})
@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
//@ObjectHolder(QForgeUtils.MOD_ID)
public class ItemInit {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Overpowered     //
    /////////////////////////
    public static final Item KILL_SWITCH = new KillSwitchItem();
    public static final Item BAN_HAMMER = new BanHammerItem();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Food     //
    //////////////////
    public static final Item CHEESE_BURGER = new Item(new Item.Properties().group(Groups.FOOD).food(new Food.Builder().hunger(1).saturation(0.2f).effect(() -> new EffectInstance(Effects.REGENERATION, 60, 1), 0.7f).build())).setRegistryName("cheese_burger");
    public static final Item CHEESE_SLICE = new Item(new Item.Properties().group(Groups.FOOD).food(new Food.Builder().hunger(1).saturation(0.2f).build())).setRegistryName("cheese_slice");
    public static final Item CHEESE = new SliceableItem(new Item.Properties().group(Groups.FOOD).food(new Food.Builder().hunger(2).saturation(0.3f).build()), (stack) -> new ItemStack(CHEESE_SLICE, stack.getCount() * 6)).setRegistryName("cheese");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Normal     //
    ////////////////////
    public static final Item STICK_VARIANT_1 = new Item(new Item.Properties().group(Groups.NATURE)).setRegistryName("stick_variant1");
    public static final Item STICK_VARIANT_2 = new Item(new Item.Properties().group(Groups.NATURE)).setRegistryName("stick_variant2");
    public static final Item EUCALYPTUS_PLANK = new Item(new Item.Properties().group(Groups.WOOD)).setRegistryName("eucalyptus_plank");
    public static final Item EUCALYPTUS_LEAF = new Item(new Item.Properties()
            .group(Groups.NATURE)
            .food(new Food.Builder()
                    .hunger(1)
                    .saturation(0.2f)
                    .effect(() -> new EffectInstance(Effects.REGENERATION, 60, 1), 0.7f)
                    .build()))
            .setRegistryName("eucalyptus_leaf");

    public static final Item KNIFE = new KnifeItem(new Item.Properties().group(Groups.SPECIALS).maxDamage(4)).setRegistryName("knife");
    public static final Item MAGNET = new MagnetItem(new Item.Properties().group(Groups.SPECIALS).maxDamage(4)).setRegistryName("magnet");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Fletching     //
    ///////////////////////

    // Bows
    public static final BowItem BLAZE_BOW = (BowItem) new AdvancedBowItem(new Item.Properties().group(Groups.FLETCHING), 6.25f, 1.0f, 6, 1, true).setRegistryName("blaze_bow");
    public static final BowItem ICE_BOW = (BowItem) new AdvancedBowItem(new Item.Properties().group(Groups.FLETCHING), 2f, 1.0f, 8, 2).setRegistryName("ice_bow");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Ingots or Dusts     //
    /////////////////////////////

    // Glass shards
    public static final Item CLEAR_SHARD = new IngredientItem("clear_shard");
    public static final Item BLACK_SHARD = new IngredientItem("black_shard");
    public static final Item BLUE_SHARD = new IngredientItem("blue_shard");
    public static final Item BROWN_SHARD = new IngredientItem("brown_shard");
    public static final Item CYAN_SHARD = new IngredientItem("cyan_shard");
    public static final Item GRAY_SHARD = new IngredientItem("gray_shard");
    public static final Item GREEN_SHARD = new IngredientItem("green_shard");
    public static final Item LIGHT_BLUE_SHARD = new IngredientItem("light_blue_shard");
    public static final Item LIGHT_GRAY_SHARD = new IngredientItem("light_gray_shard");
    public static final Item LIME_SHARD = new IngredientItem("lime_shard");
    public static final Item MAGENTA_SHARD = new IngredientItem("magenta_shard");
    public static final Item ORANGE_SHARD = new IngredientItem("orange_shard");
    public static final Item PINK_SHARD = new IngredientItem("pink_shard");
    public static final Item PURPLE_SHARD = new IngredientItem("purple_shard");
    public static final Item RED_SHARD = new IngredientItem("red_shard");
    public static final Item WHITE_SHARD = new IngredientItem("white_shard");
    public static final Item YELLOW_SHARD = new IngredientItem("yellow_shard");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Ingots or Dusts     //
    /////////////////////////////

    // Metals - Copper Level
    public static final Item COPPER_INGOT = new IngotsOrDustsItem("copper_ingot");
    public static final Item COPPER_NUGGET = new IngotsOrDustsItem("copper_nugget");
    public static final Item COPPER_DUST = new IngotsOrDustsItem("copper_dust");

    // Metals - Steel Level
    public static final Item STEEL_INGOT = new IngotsOrDustsItem("steel_ingot");
    public static final Item STEEL_NUGGET = new IngotsOrDustsItem("steel_nugget");
    public static final Item STEEL_DUST = new IngotsOrDustsItem("steel_dust");

    // Metals - Tungsten Steel Level
    public static final Item TUNGSTEN_INGOT = new IngotsOrDustsItem("tungsten_ingot");
    public static final Item TUNGSTEN_NUGGET = new IngotsOrDustsItem("tungsten_nugget");
    public static final Item TUNGSTEN_DUST = new IngotsOrDustsItem("tungsten_dust");

    // Metals - Ultrinium Level
    public static final Item ULTRINIUM_INGOT = new IngotsOrDustsItem("ultrinium_ingot");
    public static final Item ULTRINIUM_NUGGET = new IngotsOrDustsItem("ultrinium_nugget");
    public static final Item ULTRINIUM_DUST = new IngotsOrDustsItem("ultrinium_dust");

    // Metals - Infinity Level
    public static final Item UNSTABLE_INFINITY_INGOT = new UnstableInfinityIngot();
    public static final Item INFINITY_INGOT = new IngotsOrDustsItem("infinity_ingot");
    public static final Item INFINITY_NUGGET = new IngotsOrDustsItem("infinity_nugget");
    public static final Item INFINITY_DUST = new IngotsOrDustsItem("infinity_dust");

    // Metals - Uranium Level
    public static final Item URANIUM_INGOT = new IngotsOrDustsItem("uranium_ingot");
    public static final Item URANIUM_NUGGET = new IngotsOrDustsItem("uranium_nugget");
    public static final Item URANIUM_DUST = new IngotsOrDustsItem("uranium_dust");

    // Gems and other metals
    public static final Item IRON_DUST = new IngotsOrDustsItem("iron_dust");
    public static final Item GOLD_DUST = new IngotsOrDustsItem("gold_dust");
    public static final Item RUBY_DUST = new IngotsOrDustsItem("ruby_dust");
    public static final Item AMETHYST_DUST = new IngotsOrDustsItem("amethyst_dust");
    public static final Item AQUAMARINE_DUST = new IngotsOrDustsItem("aquamarine_dust");
    public static final Item SAPHIRE_DUST = new IngotsOrDustsItem("saphire_dust");
    public static final Item MALACHITE_DUST = new IngotsOrDustsItem("malachite_dust");
    public static final Item TOPAZ_DUST = new IngotsOrDustsItem("topaz_dust");
    public static final Item AMBER_DUST = new IngotsOrDustsItem("amber_dust");
    public static final Item BERYL_DUST = new IngotsOrDustsItem("beryl_dust");
    public static final Item DIAMOND_DUST = new IngotsOrDustsItem("diamond_dust");
    public static final Item TANZANITE_DUST = new IngotsOrDustsItem("tanzanite_dust");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Gems     //
    //////////////////
    public static final Item RUBY = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("ruby");
    public static final Item AMETHYST = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("amethyst");
    public static final Item AQUAMARINE = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("aquamarine");
    public static final Item SAPHIRE = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("saphire");
    public static final Item MALACHITE = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("malachite");
    public static final Item TOPAZ = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("topaz");
    public static final Item AMBER = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("amber");
    public static final Item PERIDOT = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("peridot");
    public static final Item BERYL = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("beryl");
    public static final Item TANZANITE = new Item(new Item.Properties().group(Groups.GEMS)).setRegistryName("tanzanite");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Icons     //
    ///////////////////
    public static final Item DUNGEONS = new SwordItem(ItemTier.DIAMOND, 0, 0f, new Item.Properties().setNoRepair().maxStackSize(1)).setRegistryName("dungeons");

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Dungeons     //
    //////////////////////
    public static final AxeItem DUNGEONS_DIAMOND_AXE = (AxeItem) new AxeItem(ItemTier.DIAMOND, 4, -2.0f, new Item.Properties().group(Groups.DUNGEONS)).setRegistryName("dungeons_diamond_axe");
    public static final SwordItem DUNGEONS_DIAMOND_SWORD = (SwordItem) new SwordItem(ItemTier.DIAMOND, 4, -2.0f, new Item.Properties().group(Groups.DUNGEONS)).setRegistryName("dungeons_diamond_sword");
    public static final SwordItem DUNGEONS_IRON_SWORD = (SwordItem) new SwordItem(ItemTier.IRON, 4, -2.0f, new Item.Properties().group(Groups.DUNGEONS)).setRegistryName("dungeons_iron_sword");
    public static final SwordItem DUNGEONS_BROADSWORD = (SwordItem) new SwordItem(ItemTier.IRON, 5, -3.75f, new Item.Properties().group(Groups.DUNGEONS)).setRegistryName("dungeons_broadsword");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Specials     //
    //////////////////////

    // Wands
    public static final Item WALKING_STAFF = new WandItem();
    public static final Item LIGHTNING_STAFF = new LightningStaffItem();
    public static final Item NATURE_STAFF = new NatureStaffItem();

    // Stone Level
    public static final SwordItem STONE_SWORD_OF_DOOM  = (SwordItem) new SwordItem(ItemTier.STONE, 8, -2.0f, new Item.Properties().group(Groups.SPECIALS)).setRegistryName("stone_sword_of_doom");

    // Iron Level
    public static final AxeItem EMERGENCY_FIRE_AXE = (AxeItem) new AxeItem(ItemTier.IRON, 3, -2.55f, new Item.Properties().group(Groups.SPECIALS)).setRegistryName("emergency_fire_axe");
    public static final SwordItem FIRE_SWORD = (SwordItem) new FireSwordItem(ItemTier.IRON, 5, -3.5f, new Item.Properties().group(Groups.SPECIALS)).setRegistryName("fire_sword");

    // Diamond Level
    public static final AxeItem LEVIATHAN_AXE = (AxeItem) new AxeItem(ItemTier.DIAMOND, 5, -2.55f, new Item.Properties().group(Groups.SPECIALS)).setRegistryName("leviathan_axe");
    public static final AxeItem ADAMANTANIUM_AXE_RED = (AxeItem) new AxeItem(ItemTier.DIAMOND, 8, -1.875f, new Item.Properties().group(Groups.SPECIALS)).setRegistryName("adamantanium_axe_red");

    public static final SwordItem DIAMOND_QUARTZ_SWORD = (SwordItem) new SwordItem(ItemTier.DIAMOND, 8, -2.0f, new Item.Properties().group(Groups.SPECIALS)).setRegistryName("diamond_quartz_sword");

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
    public static final Item COPPER_HELMET = new ArmorItem(copperArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("copper_helmet");
    public static final Item COPPER_CHESTPLATE = new ArmorItem(copperArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("copper_chestplate");
    public static final Item COPPER_LEGGINGS = new ArmorItem(copperArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("copper_leggings");
    public static final Item COPPER_BOOTS = new ArmorItem(copperArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("copper_boots");

    // Armors - Steel
    public static final Item STEEL_HELMET = new ArmorItem(steelArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("steel_helmet");
    public static final Item STEEL_CHESTPLATE = new ArmorItem(steelArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("steel_chestplate");
    public static final Item STEEL_LEGGINGS = new ArmorItem(steelArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("steel_leggings");
    public static final Item STEEL_BOOTS = new ArmorItem(steelArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("steel_boots");

    // Armors - Tungsten Steel
    public static final Item TUNGSTEN_HELMET = new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("tungsten_helmet");
    public static final Item TUNGSTEN_CHESTPLATE = new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("tungsten_chestplate");
    public static final Item TUNGSTEN_LEGGINGS = new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("tungsten_leggings");
    public static final Item TUNGSTEN_BOOTS = new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("tungsten_boots");

    // Armors - Uranium
    public static final Item URANIUM_HELMET = new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("uranium_helmet");
    public static final Item URANIUM_CHESTPLATE = new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("uranium_chestplate");
    public static final Item URANIUM_LEGGINGS = new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("uranium_leggings");
    public static final Item URANIUM_BOOTS = new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("uranium_boots");

    // Armors - Ruby
    public static final Item RUBY_HELMET = new ArmorItem(rubyArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("ruby_helmet");
    public static final Item RUBY_CHESTPLATE = new ArmorItem(rubyArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("ruby_chestplate");
    public static final Item RUBY_LEGGINGS = new ArmorItem(rubyArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("ruby_leggings");
    public static final Item RUBY_BOOTS = new ArmorItem(rubyArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("ruby_boots");

    // Armors - Amethyst
    public static final Item AMETHYST_HELMET = new ArmorItem(amethystArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("amethyst_helmet");
    public static final Item AMETHYST_CHESTPLATE = new ArmorItem(amethystArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("amethyst_chestplate");
    public static final Item AMETHYST_LEGGINGS = new ArmorItem(amethystArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("amethyst_leggings");
    public static final Item AMETHYST_BOOTS = new ArmorItem(amethystArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("amethyst_boots");

    // Armors - Aquamarine
    public static final Item AQUAMARINE_HELMET = new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("aquamarine_helmet");
    public static final Item AQUAMARINE_CHESTPLATE = new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("aquamarine_chestplate");
    public static final Item AQUAMARINE_LEGGINGS = new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("aquamarine_leggings");
    public static final Item AQUAMARINE_BOOTS = new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("aquamarine_boots");

    // Armors - Saphire
    public static final Item SAPHIRE_HELMET = new ArmorItem(saphireArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("saphire_helmet");
    public static final Item SAPHIRE_CHESTPLATE = new ArmorItem(saphireArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("saphire_chestplate");
    public static final Item SAPHIRE_LEGGINGS = new ArmorItem(saphireArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("saphire_leggings");
    public static final Item SAPHIRE_BOOTS = new ArmorItem(saphireArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("saphire_boots");

    // Armors - Malachite
    public static final Item MALACHITE_HELMET = new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("malachite_helmet");
    public static final Item MALACHITE_CHESTPLATE = new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("malachite_chestplate");
    public static final Item MALACHITE_LEGGINGS = new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("malachite_leggings");
    public static final Item MALACHITE_BOOTS = new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("malachite_boots");

    // Armors - Topaz
    public static final Item TOPAZ_HELMET = new ArmorItem(topazArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("topaz_helmet");
    public static final Item TOPAZ_CHESTPLATE = new ArmorItem(topazArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("topaz_chestplate");
    public static final Item TOPAZ_LEGGINGS = new ArmorItem(topazArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("topaz_leggings");
    public static final Item TOPAZ_BOOTS = new ArmorItem(topazArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("topaz_boots");

    // Armors - Amber
    public static final Item AMBER_HELMET = new ArmorItem(amberArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("amber_helmet");
    public static final Item AMBER_CHESTPLATE = new ArmorItem(amberArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("amber_chestplate");
    public static final Item AMBER_LEGGINGS = new ArmorItem(amberArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("amber_leggings");
    public static final Item AMBER_BOOTS = new ArmorItem(amberArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("amber_boots");

    // Armors - Beryl
    public static final Item BERYL_HELMET = new ArmorItem(berylArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("beryl_helmet");
    public static final Item BERYL_CHESTPLATE = new ArmorItem(berylArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("beryl_chestplate");
    public static final Item BERYL_LEGGINGS = new ArmorItem(berylArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("beryl_leggings");
    public static final Item BERYL_BOOTS = new ArmorItem(berylArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("beryl_boots");

    // Armors - Tanzanite
    public static final Item TANZANITE_HELMET = new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("tanzanite_helmet");
    public static final Item TANZANITE_CHESTPLATE = new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("tanzanite_chestplate");
    public static final Item TANZANITE_LEGGINGS = new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("tanzanite_leggings");
    public static final Item TANZANITE_BOOTS = new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("tanzanite_boots");

    // Armors - Ultrinium
    public static final Item ULTRINIUM_HELMET = new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.ARMORS)).setRegistryName("ultrinium_helmet");
    public static final Item ULTRINIUM_CHESTPLATE = new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.ARMORS)).setRegistryName("ultrinium_chestplate");
    public static final Item ULTRINIUM_LEGGINGS = new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.ARMORS)).setRegistryName("ultrinium_leggings");
    public static final Item ULTRINIUM_BOOTS = new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.ARMORS)).setRegistryName("ultrinium_boots");

    // Armors - Ultrinium
    public static final Item INFINITY_HELMET = new ArmorItem(infinityArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(Groups.OVERPOWERED)).setRegistryName("infinity_helmet");
    public static final Item INFINITY_CHESTPLATE = new ArmorItem(infinityArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(Groups.OVERPOWERED)).setRegistryName("infinity_chestplate");
    public static final Item INFINITY_LEGGINGS = new ArmorItem(infinityArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(Groups.OVERPOWERED)).setRegistryName("infinity_leggings");
    public static final Item INFINITY_BOOTS = new ArmorItem(infinityArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(Groups.OVERPOWERED)).setRegistryName("infinity_boots");

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
    public static final Item COPPER_SWORD = new SwordItem(COPPER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("copper_sword"); // ModItemTier.ULTRINIUM);
    public static final Item COPPER_PICKAXE = new PickaxeItem(COPPER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("copper_pickaxe");
    public static final Item COPPER_SHOVEL = new ShovelItem(COPPER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("copper_shovel");
    public static final Item COPPER_AXE = new AxeItem(COPPER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("copper_axe");
    public static final Item COPPER_HOE = new HoeItem(COPPER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("copper_hoe");

    // Tools - Steel
    public static final Item STEEL_SWORD = new SwordItem(STEEL_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("steel_sword"); // ModItemTier.ULTRINIUM);
    public static final Item STEEL_PICKAXE = new PickaxeItem(STEEL_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("steel_pickaxe");
    public static final Item STEEL_SHOVEL = new ShovelItem(STEEL_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("steel_shovel");
    public static final Item STEEL_AXE = new AxeItem(STEEL_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("steel_axe");
    public static final Item STEEL_HOE = new HoeItem(STEEL_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("steel_hoe");

    // Tools - Tungsten Steel
    public static final Item TUNGSTEN_SWORD = new SwordItem(TUNGSTEN_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tungsten_sword"); // ModItemTier.ULTRINIUM);
    public static final Item TUNGSTEN_PICKAXE = new PickaxeItem(TUNGSTEN_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tungsten_pickaxe");
    public static final Item TUNGSTEN_SHOVEL = new ShovelItem(TUNGSTEN_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tungsten_shovel");
    public static final Item TUNGSTEN_AXE = new AxeItem(TUNGSTEN_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tungsten_axe");
    public static final Item TUNGSTEN_HOE = new HoeItem(TUNGSTEN_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tungsten_hoe");

    // Tools - Uranium
    public static final Item URANIUM_SWORD = new SwordItem(URANIUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("uranium_sword"); // ModItemTier.ULTRINIUM);
    public static final Item URANIUM_PICKAXE = new PickaxeItem(URANIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("uranium_pickaxe");
    public static final Item URANIUM_SHOVEL = new ShovelItem(URANIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("uranium_shovel");
    public static final Item URANIUM_AXE = new AxeItem(URANIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("uranium_axe");
    public static final Item URANIUM_HOE = new HoeItem(URANIUM_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("uranium_hoe");

    // Tools - Ruby
    public static final Item RUBY_SWORD = new SwordItem(RUBY_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ruby_sword"); // ModItemTier.ULTRINIUM);
    public static final Item RUBY_PICKAXE = new PickaxeItem(RUBY_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ruby_pickaxe");
    public static final Item RUBY_SHOVEL = new ShovelItem(RUBY_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ruby_shovel");
    public static final Item RUBY_AXE = new AxeItem(RUBY_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ruby_axe");
    public static final Item RUBY_HOE = new HoeItem(RUBY_ITEM_TIER, 2, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ruby_hoe");

    // Tools - Amethyst
    public static final Item AMETHYST_SWORD = new SwordItem(AMETHYST_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amethyst_sword"); // ModItemTier.ULTRINIUM);
    public static final Item AMETHYST_PICKAXE = new PickaxeItem(AMETHYST_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amethyst_pickaxe");
    public static final Item AMETHYST_SHOVEL = new ShovelItem(AMETHYST_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amethyst_shovel");
    public static final Item AMETHYST_AXE = new AxeItem(AMETHYST_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amethyst_axe");
    public static final Item AMETHYST_HOE = new HoeItem(AMETHYST_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amethyst_hoe");

    // Tools - Aquamarine
    public static final Item AQUAMARINE_SWORD = new SwordItem(AQUAMARINE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("aquamarine_sword"); // ModItemTier.ULTRINIUM);
    public static final Item AQUAMARINE_PICKAXE = new PickaxeItem(AQUAMARINE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("aquamarine_pickaxe");
    public static final Item AQUAMARINE_SHOVEL = new ShovelItem(AQUAMARINE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("aquamarine_shovel");
    public static final Item AQUAMARINE_AXE = new AxeItem(AQUAMARINE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("aquamarine_axe");
    public static final Item AQUAMARINE_HOE = new HoeItem(AQUAMARINE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("aquamarine_hoe");

    // Tools - Saphire
    public static final Item SAPHIRE_SWORD = new SwordItem(SAPHIRE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("saphire_sword"); // ModItemTier.ULTRINIUM);
    public static final Item SAPHIRE_PICKAXE = new PickaxeItem(SAPHIRE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("saphire_pickaxe");
    public static final Item SAPHIRE_SHOVEL = new ShovelItem(SAPHIRE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("saphire_shovel");
    public static final Item SAPHIRE_AXE = new AxeItem(SAPHIRE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("saphire_axe");
    public static final Item SAPHIRE_HOE = new HoeItem(SAPHIRE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("saphire_hoe");

    // Tools - Malachite
    public static final Item MALACHITE_SWORD = new SwordItem(MALACHITE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("malachite_sword"); // ModItemTier.ULTRINIUM);
    public static final Item MALACHITE_PICKAXE = new PickaxeItem(MALACHITE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("malachite_pickaxe");
    public static final Item MALACHITE_SHOVEL = new ShovelItem(MALACHITE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("malachite_shovel");
    public static final Item MALACHITE_AXE = new AxeItem(MALACHITE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("malachite_axe");
    public static final Item MALACHITE_HOE = new HoeItem(MALACHITE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("malachite_hoe");

    // Tools - Topaz
    public static final Item TOPAZ_SWORD = new SwordItem(TOPAZ_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("topaz_sword"); // ModItemTier.ULTRINIUM);
    public static final Item TOPAZ_PICKAXE = new PickaxeItem(TOPAZ_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("topaz_pickaxe");
    public static final Item TOPAZ_SHOVEL = new ShovelItem(TOPAZ_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("topaz_shovel");
    public static final Item TOPAZ_AXE = new AxeItem(TOPAZ_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("topaz_axe");
    public static final Item TOPAZ_HOE = new HoeItem(TOPAZ_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("topaz_hoe");

    // Tools - Amber
    public static final Item AMBER_SWORD = new SwordItem(AMBER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amber_sword"); // ModItemTier.ULTRINIUM);
    public static final Item AMBER_PICKAXE = new PickaxeItem(AMBER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amber_pickaxe");
    public static final Item AMBER_SHOVEL = new ShovelItem(AMBER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amber_shovel");
    public static final Item AMBER_AXE = new AxeItem(AMBER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amber_axe");
    public static final Item AMBER_HOE = new HoeItem(AMBER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("amber_hoe");

    // Tools - Beryl
    public static final Item BERYL_SWORD = new SwordItem(BERYL_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("beryl_sword"); // ModItemTier.ULTRINIUM);
    public static final Item BERYL_PICKAXE = new PickaxeItem(BERYL_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("beryl_pickaxe");
    public static final Item BERYL_SHOVEL = new ShovelItem(BERYL_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("beryl_shovel");
    public static final Item BERYL_AXE = new AxeItem(BERYL_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("beryl_axe");
    public static final Item BERYL_HOE = new HoeItem(BERYL_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("beryl_hoe");

    // Tools - Tanzanite
    public static final Item TANZANITE_SWORD = new SwordItem(TANZANITE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tanzanite_sword"); // ModItemTier.ULTRINIUM);
    public static final Item TANZANITE_PICKAXE = new PickaxeItem(TANZANITE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tanzanite_pickaxe");
    public static final Item TANZANITE_SHOVEL = new ShovelItem(TANZANITE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tanzanite_shovel");
    public static final Item TANZANITE_AXE = new AxeItem(TANZANITE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tanzanite_axe");
    public static final Item TANZANITE_HOE = new HoeItem(TANZANITE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("tanzanite_hoe");

    // Tools - Ultrinium
    public static final Item ULTRINIUM_SWORD = new SwordItem(ULTRINIUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ultrinium_sword"); // ModItemTier.ULTRINIUM);
    public static final Item ULTRINIUM_PICKAXE = new PickaxeItem(ULTRINIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ultrinium_pickaxe");
    public static final Item ULTRINIUM_SHOVEL = new ShovelItem(ULTRINIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ultrinium_shovel");
    public static final Item ULTRINIUM_AXE = new AxeItem(ULTRINIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ultrinium_axe");
    public static final Item ULTRINIUM_HOE = new HoeItem(ULTRINIUM_ITEM_TIER, 3955, -2.0f, new Item.Properties().group(Groups.TOOLS)).setRegistryName("ultrinium_hoe");

    // Tools - Infinity
    public static final Item INFINITY_SWORD = new InfinitySwordItem();
    public static final Item INFINITY_PICKAXE = new InfinityPickaxeItem();
    public static final Item INFINITY_SHOVEL = new InfinityShovelItem();
    public static final Item INFINITY_AXE = new InfinityAxeItem();
    public static final Item INFINITY_HOE = new InfinityHoeItem();

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Class<ItemInit> clazz = ItemInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (Item.class.isAssignableFrom(field.getType())) {
                try {
                    Item item = (Item) field.get(null);
//                    field.setAccessible(true);
//                    field.set(null, item.setRegistryName(QForgeUtils.MOD_ID, field.getName().toLowerCase()));
                    event.getRegistry().register(item);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field, or registering item: " + field.getName(), t);
                }
            }
        }
    }
}
