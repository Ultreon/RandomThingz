package com.ultreon.randomthingz.item.tool;

import com.ultreon.modlib.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.FeatureStatus;
import com.ultreon.randomthingz.common.exceptions.UnidentifiedObjectException;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.common.item.ModCreativeTabs;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.registration.Registration;
import com.ultreon.randomthingz.util.builder.ArmorMaterial;
import com.ultreon.randomthingz.util.builder.ItemTier;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

@SuppressWarnings({"OptionalGetWithoutIsPresent", "Convert2MethodRef", "FunctionalExpressionCanBeFolded", "deprecation"})
public enum Toolset {
    // Metals
    REDSTONE(builder("redstone", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.REDSTONE_ALLOY.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":redstone")
                    .maxDamageFactor(8)
                    .damageReduction(new int[]{1, 3, 4, 2})
                    .enchantability(5)
                    .knockbackResistance(-.8f)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(.5f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.REDSTONE_ALLOY.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(1).maxUses(230).efficiency(2.3f).attackDamage(1.2f).enchantability(7).build())),
//    COPPER(builder("copper", FeatureStatus.DEPRECATED)
//            .material(() -> ItemMaterial.COPPER.getIngot().get(), () -> Items.STICK)
//            .armor(() -> ArmorMaterial.builder()
//                    .name(RandomThingz.MOD_ID + ":copper")
//                    .maxDamageFactor(13)
//                    .damageReduction(new int[]{2, 5, 6, 2})
//                    .enchantability(10)
//                    .knockbackResistance(0f)
//                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
//                    .toughness(0f)
//                    .repairMaterial(() -> Ingredient.of(ItemMaterial.COPPER.getIngot().get()))
//                    .build())
//            .tools(() -> ItemTier.builder()
//                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
//                    .repairMaterial(() -> Ingredient.of(ItemMaterial.COPPER.getIngot().orElseThrow(() -> new NullPointerException("Copper ingot not found in OreMaterial class.")))).build())),
    TIN(builder("tin", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.TIN.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":tin")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.TIN.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.2f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.TIN.getIngot().orElseThrow(() -> new NullPointerException("Tin ingot not found in OreMaterial class.")))).build())),
    COMPRESSED_IRON(builder("compressed_iron", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.COMPRESSED_IRON.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":compressed_iron")
                    .maxDamageFactor(17)
                    .damageReduction(new int[]{3, 6, 7, 3})
                    .enchantability(9)
                    .knockbackResistance(.05f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(.5f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.COMPRESSED_IRON.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(350).efficiency(7f).attackDamage(2.5f).enchantability(10)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.COMPRESSED_IRON.getIngot().orElseThrow(() -> new NullPointerException("Copper ingot not found in OreMaterial class.")))).build())),
    OBSIDIAN(builder("obsidian")
            .material(() -> Items.OBSIDIAN, () -> Items.BLAZE_ROD, () -> Items.BLAZE_ROD)
            .armor(() -> TraitPack.create().armor(ModTraits.BLAST_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":obsidian")
                    .maxDamageFactor(131)
                    .damageReduction(new int[]{4, 8, 10, 6})
                    .enchantability(17)
                    .knockbackResistance(.2f)
                    .sound(SoundEvents.ARMOR_EQUIP_NETHERITE)
                    .toughness(4f)
                    .repairMaterial(() -> Ingredient.of(Items.OBSIDIAN))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.SHARP.get(), ModTraits.BLAZE.get()).build(), () -> ItemTier.builder()
                    .tier(4).maxUses(4738).efficiency(10f).attackDamage(6f).enchantability(17)
                    .repairMaterial(() -> Ingredient.of(Items.OBSIDIAN)).build())),
    SILVER(builder("silver")
            .material(() -> ItemMaterial.SILVER.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().all(ModTraits.HOLY.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":silver")
                    .maxDamageFactor(15)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(48)
                    .knockbackResistance(1f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.SILVER.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.HOLY.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(580).efficiency(5.4f).attackDamage(1.7f).enchantability(48)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.SILVER.getIngot().orElseThrow(() -> new NullPointerException("Silver ingot not found in OreMaterial class.")))).build())),
    LEAD(builder("lead")
            .material(() -> ItemMaterial.LEAD.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().all(ModTraits.POISON.get(), ModTraits.SHARP.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":lead")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{4, 9, 12, 7})
                    .enchantability(4)
                    .knockbackResistance(1f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(4f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.LEAD.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.POISON.get(), ModTraits.SHARP.get()).build(), () -> ItemTier.builder()
                    .tier(3).maxUses(450).efficiency(7f).attackDamage(3f).enchantability(7)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.LEAD.getIngot().orElseThrow(() -> new NullPointerException("Lead ingot not found in OreMaterial class.")))).build())),
    TUNGSTEN(builder("tungsten", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.TUNGSTEN.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":tungsten")
                    .maxDamageFactor(40)
                    .damageReduction(new int[]{3, 6, 8, 3})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.TUNGSTEN.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(3).maxUses(760).efficiency(7.5f).attackDamage(3f).enchantability(9)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.TUNGSTEN.getIngot().get())).build())),
    NICKEL(builder("nickel", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.NICKEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":nickel")
                    .maxDamageFactor(13)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.NICKEL.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(260).efficiency(5.5f).attackDamage(1.8f).enchantability(10)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.NICKEL.getIngot().orElseThrow(() -> new NullPointerException("Nickel ingot not found in OreMaterial class.")))).build())),
    PLATINUM(builder("platinum")
            .material(() -> ItemMaterial.PLATINUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":platinum")
                    .maxDamageFactor(36)
                    .damageReduction(new int[]{3, 6, 8, 3})
                    .enchantability(14)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.PLATINUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(4).maxUses(1240).efficiency(8.6f).attackDamage(5f).enchantability(14)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.PLATINUM.getIngot().orElseThrow(() -> new NullPointerException("Platinum ingot not found in OreMaterial class.")))).build())),
    ZINC(builder("zinc", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.ZINC.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().all(ModTraits.POISON.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":zinc")
                    .maxDamageFactor(6)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ZINC.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.POISON.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(170).efficiency(6.2f).attackDamage(2.5f).enchantability(6)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ZINC.getIngot().orElseThrow(() -> new NullPointerException("Zinc ingot not found in OreMaterial class.")))).build())),
    BISMUTH(builder("bismuth", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.BISMUTH.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":bismuth")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BISMUTH.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(230).efficiency(5.3f).attackDamage(1.4f).enchantability(14)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BISMUTH.getIngot().orElseThrow(() -> new NullPointerException("Bismuth ingot not found in OreMaterial class.")))).build())),
    ALUMINUM(builder("aluminum")
            .material(() -> ItemMaterial.ALUMINUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":aluminum")
                    .maxDamageFactor(13)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ALUMINUM.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.SHARP.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(180).efficiency(5.6f).attackDamage(3f).enchantability(9)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ALUMINUM.getIngot().orElseThrow(() -> new NullPointerException("Aluminum ingot not found in OreMaterial class.")))).build())),
    URANIUM(builder("uranium")
            .material(() -> ItemMaterial.URANIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().all(ModTraits.RADIOACTIVE.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":uranium")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.URANIUM.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.RADIOACTIVE.get()).build(), () -> ItemTier.builder()
                    .tier(3).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.URANIUM.getIngot().orElseThrow(() -> new NullPointerException("Uranium ingot not found in OreMaterial class.")))).build())),
    BRONZE(builder("bronze", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.BRONZE.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":bronze")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BRONZE.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BRONZE.getIngot().orElseThrow(() -> new NullPointerException("Bronze ingot not found in OreMaterial class.")))).build())),
    BRASS(builder("brass", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.BRASS.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":brass")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BRASS.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BRASS.getIngot().orElseThrow(() -> new NullPointerException("Brass ingot not found in OreMaterial class.")))).build())),
    INVAR(builder("invar", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.INVAR.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":invar")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.INVAR.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.INVAR.getIngot().orElseThrow(() -> new NullPointerException("Invar ingot not found in OreMaterial class.")))).build())),
    ELECTRUM(builder("electrum", FeatureStatus.WIP)
            .material(() -> ItemMaterial.ELECTRUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":electrum")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(11)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ELECTRUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(1).maxUses(283).efficiency(4.5f).attackDamage(2f).enchantability(11)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ELECTRUM.getIngot().orElseThrow(() -> new NullPointerException("Electrum ingot not found in OreMaterial class.")))).build())),
    STEEL(builder("steel")
            .material(() -> ItemMaterial.STEEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.BLAST_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":steel")
                    .maxDamageFactor(28)
                    .damageReduction(new int[]{2, 6, 8, 3})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(1f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.STEEL.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().tools(ModTraits.KNOCKBACK.get(), ModTraits.SHARP.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(570).efficiency(7.1f).attackDamage(2.3f).enchantability(9)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.STEEL.getIngot().orElseThrow(() -> new NullPointerException("Steel ingot not found in OreMaterial class.")))).build())),
    BISMUTH_BRASS(builder("bismuth_brass", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.BISMUTH_BRASS.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":bismuth_brass")
                    .maxDamageFactor(16)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BISMUTH_BRASS.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(300).efficiency(5.3f).attackDamage(1.4f).enchantability(10)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BISMUTH_BRASS.getIngot().orElseThrow(() -> new NullPointerException("Bismuth Brass ingot not found in OreMaterial class.")))).build())),
    ALUMINUM_STEEL(builder("aluminum_steel", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.ALUMINUM_STEEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.BLAST_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":aluminum_steel")
                    .maxDamageFactor(17)
                    .damageReduction(new int[]{2, 6, 7, 3})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ALUMINUM_STEEL.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().tools(ModTraits.SHARP.get()).build(), () -> ItemTier.builder()
                    .tier(3).maxUses(684).efficiency(6.5f).attackDamage(2.8f).enchantability(9)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ALUMINUM_STEEL.getIngot().orElseThrow(() -> new NullPointerException("Aluminum Steel ingot not found in OreMaterial class.")))).build())),
    BISMUTH_STEEL(builder("bismuth_steel", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.BISMUTH_STEEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":bismuth_steel")
                    .maxDamageFactor(18)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BISMUTH_STEEL.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(467).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.BISMUTH_STEEL.getIngot().orElseThrow(() -> new NullPointerException("Bismuth Steel ingot not found in OreMaterial class.")))).build())),
    SIGNALUM(builder("signalum", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.SIGNALUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":signalum")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.SIGNALUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(344).efficiency(5.8f).attackDamage(1.7f).enchantability(11)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.SIGNALUM.getIngot().orElseThrow(() -> new NullPointerException("Signalum ingot not found in OreMaterial class.")))).build())),
    LUMIUM(builder("lumium", FeatureStatus.WIP)
            .material(() -> ItemMaterial.LUMIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":lumium")
                    .maxDamageFactor(9)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(36)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(0f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.LUMIUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(200).efficiency(5f).attackDamage(2.7f).enchantability(36)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.LUMIUM.getIngot().orElseThrow(() -> new NullPointerException("Lumium ingot not found in OreMaterial class.")))).build())),
    ENDERIUM(builder("enderium")
            .material(() -> ItemMaterial.ENDERIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.ENDER.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":enderium")
                    .maxDamageFactor(42)
                    .damageReduction(new int[]{4, 10, 12, 3})
                    .enchantability(56)
                    .knockbackResistance(.2f)
                    .sound(SoundEvents.ARMOR_EQUIP_IRON)
                    .toughness(4f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ENDERIUM.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().tools(ModTraits.ENDER.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(2340).efficiency(9f).attackDamage(7f).enchantability(56)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ENDERIUM.getIngot().orElseThrow(() -> new NullPointerException("Enderium ingot not found in OreMaterial class.")))).build())),
    COBALT(builder("cobalt", FeatureStatus.WIP)
            .material(() -> ItemMaterial.COBALT.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.MAGIC_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":cobalt")
                    .maxDamageFactor(64)
                    .damageReduction(new int[]{28, 48, 56, 35})
                    .enchantability(24)
                    .knockbackResistance(.2f)
                    .sound(SoundEvents.ARMOR_EQUIP_NETHERITE)
                    .toughness(6f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.COBALT.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(5).maxUses(6535).efficiency(27.5f).attackDamage(31f).enchantability(93)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.COBALT.getIngot().orElseThrow(() -> new UnidentifiedObjectException("Cobalt ingot not found in OreMaterial class.")))).build())),
    ULTRINIUM(builder("ultrinium")
            .material(() -> ItemMaterial.ULTRINIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.BLAST_RESISTANT.get(), ModTraits.MAGIC_RESISTANT.get(), ModTraits.FIRE_RESISTANT.get(), ModTraits.HOLY.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":ultrinium")
                    .maxDamageFactor(192)
                    .damageReduction(new int[]{125, 200, 275, 165})
                    .enchantability(224)
                    .knockbackResistance(1f)
                    .sound(SoundEvents.ARMOR_EQUIP_NETHERITE)
                    .toughness(64f)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ULTRINIUM.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().armor(ModTraits.HOLY.get(), ModTraits.WITHER.get(), ModTraits.POISON.get()).build(), () -> ItemTier.builder()
                    .tier(6).maxUses(65536).efficiency(102.8f).attackDamage(127f).enchantability(224)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.ULTRINIUM.getIngot().orElseThrow(() -> new UnidentifiedObjectException("Ultrinium ingot not found in OreMaterial class.")))).build())),
    INFINITY(builder("infinity")
            .material(() -> ItemMaterial.INFINITY.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(
                    ModTraits.BLAST_RESISTANT.get(), ModTraits.MAGIC_RESISTANT.get(),
                    ModTraits.FIRE_RESISTANT.get(), ModTraits.PROJECTILE_RESISTANT.get(),
                    ModTraits.HOLY.get(), ModTraits.INFINITY.get(), ModTraits.POISON.get(),
                    ModTraits.WITHER.get(), ModTraits.SHARP.get(), ModTraits.ENDER.get(),
                    ModTraits.RADIOACTIVE.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":infinity")
                    .maxDamageFactor((int) Float.POSITIVE_INFINITY)
                    .damageReduction(new int[]{(int) Float.POSITIVE_INFINITY, (int) Float.POSITIVE_INFINITY, (int) Float.POSITIVE_INFINITY, (int) Float.POSITIVE_INFINITY})
                    .enchantability((int) Float.POSITIVE_INFINITY)
                    .knockbackResistance(Float.POSITIVE_INFINITY)
                    .sound(SoundEvents.ARMOR_EQUIP_NETHERITE)
                    .toughness(Float.POSITIVE_INFINITY)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.INFINITY.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(
                    ModTraits.INFINITY.get(), ModTraits.POISON.get(),
                    ModTraits.WITHER.get(), ModTraits.SHARP.get(),
                    ModTraits.ENDER.get(), ModTraits.BLAZE.get(),
                    ModTraits.RADIOACTIVE.get()).build(), () -> ItemTier.builder()
                    .tier(7).maxUses((int) Float.POSITIVE_INFINITY).efficiency(Float.POSITIVE_INFINITY).attackDamage(Float.POSITIVE_INFINITY).enchantability((int) Float.POSITIVE_INFINITY)
                    .repairMaterial(() -> Ingredient.of(ItemMaterial.INFINITY.getIngot().orElseThrow(() -> new UnidentifiedObjectException("Infinity ingot not found in OreMaterial class.")))).build())),
    AQUAMARINE(builder("aquamarine", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.AQUAMARINE.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":aquamarine")
                    .maxDamageFactor(5)
                    .damageReduction(new int[]{2, 4, 5, 2})
                    .enchantability(13)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.AQUAMARINE.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(9f).attackDamage(3.2f).enchantability(13)
                    .repairMaterial(() -> Ingredient.of(ModItems.AQUAMARINE.get())).build())),
    AMETHYST(builder("amethyst", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.AMETHYST.get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.MAGIC_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":amethyst")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(38)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.AMETHYST.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.of(ModItems.AMETHYST.get())).build())),
    BERYL(builder("beryl", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.BERYL.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":beryl")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(17)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.BERYL.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.of(ModItems.BERYL.get())).build())),
    AMBER(builder("amber", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.AMBER.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":amber")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(8)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.AMBER.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.of(ModItems.AMBER.get())).build())),
    MALACHITE(builder("malachite", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.MALACHITE.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":malachite")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(14)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.MALACHITE.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.of(ModItems.MALACHITE.get())).build())),
    PERIDOT(builder("peridot", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.PERIDOT.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":peridot")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(12)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.PERIDOT.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.of(ModItems.PERIDOT.get())).build())),
    SAPHIRE(builder("saphire", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.SAPPHIRE.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":saphire")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(23)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.SAPPHIRE.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.of(ModItems.SAPPHIRE.get())).build())),
    RUBY(builder("ruby", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.RUBY.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":ruby")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(35)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.RUBY.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.of(ModItems.RUBY.get())).build())),
    TANZANITE(builder("tanzanite", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.TANZANITE.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":tanzanite")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(20)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.TANZANITE.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.of(ModItems.TANZANITE.get())).build())),
    TOPAZ(builder("topaz", FeatureStatus.DEPRECATED)
            .material(() -> ModItems.TOPAZ.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":topaz")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(24)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.of(ModItems.TOPAZ.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.of(ModItems.TOPAZ.get())).build())),
    ;

    private final String toolName;

    @Getter
    private final Supplier<Item> baseMaterial;
    @Getter
    private final Supplier<Item> handleMaterial;
    @Nullable
    @Getter
    private final Supplier<Item> armorSubMaterial;

    @Getter
    private final Supplier<ArmorMaterial> armorMaterial;
    @Getter
    private final Supplier<Tier> itemTier;

    private final Supplier<ArmorItem> helmetSupplier;
    private final Supplier<ArmorItem> chestplateSupplier;
    private final Supplier<ArmorItem> leggingsSupplier;
    private final Supplier<ArmorItem> bootsSupplier;
    private final Supplier<SwordItem> swordSupplier;
    private final Supplier<AxeItem> axeSupplier;
    private final Supplier<PickaxeItem> pickaxeSupplier;
    private final Supplier<ShovelItem> shovelSupplier;
    private final Supplier<HoeItem> hoeSupplier;

    private final FeatureStatus status;

    @Getter
    private ItemRegistryObject<ArmorItem> helmet;
    @Getter
    private ItemRegistryObject<ArmorItem> chestplate;
    @Getter
    private ItemRegistryObject<ArmorItem> leggings;
    @Getter
    private ItemRegistryObject<ArmorItem> boots;
    @Getter
    private ItemRegistryObject<SwordItem> sword;
    @Getter
    private ItemRegistryObject<AxeItem> axe;
    @Getter
    private ItemRegistryObject<PickaxeItem> pickaxe;
    @Getter
    private ItemRegistryObject<ShovelItem> shovel;
    @Getter
    private ItemRegistryObject<HoeItem> hoe;

    Toolset(Builder builder) {
        this(builder, builder.name);
    }

    Toolset(Builder builder, String toolName) {
        if (!builder.name.equals(this.getName())) {
            throw new IllegalArgumentException("Builder name is incorrect, should be " + this.getName());
        }
        this.status = builder.status;
        this.toolName = toolName;
        RandomThingz.LOGGER.debug(toolName + "{<class>:<init>[1].0}: " + builder.baseMaterial);
        this.baseMaterial = builder.baseMaterial;
        this.handleMaterial = builder.handleMaterial;
        this.armorSubMaterial = builder.armorSubMaterial;

        this.armorMaterial = builder.armorMaterial;
        this.itemTier = builder.itemTier;

        this.helmetSupplier = builder.helmet;
        this.chestplateSupplier = builder.chestplate;
        this.leggingsSupplier = builder.leggings;
        this.bootsSupplier = builder.boots;
        this.swordSupplier = builder.sword;
        this.axeSupplier = builder.axe;
        this.pickaxeSupplier = builder.pickaxe;
        this.shovelSupplier = builder.shovel;
        this.hoeSupplier = builder.hoe;
        RandomThingz.LOGGER.debug(toolName + "{<class>:<init>[1].1}: " + this.baseMaterial);
    }

    public static void registerItems() {
        for (Toolset metal : values()) {
            if (metal.helmetSupplier != null) {
                metal.helmet = Registration.ITEMS.register(
                        metal.toolName + "_helmet", metal.helmetSupplier);
            }
            if (metal.chestplateSupplier != null) {
                metal.chestplate = Registration.ITEMS.register(
                        metal.toolName + "_chestplate", metal.chestplateSupplier);
            }
            if (metal.leggingsSupplier != null) {
                metal.leggings = Registration.ITEMS.register(
                        metal.toolName + "_leggings", metal.leggingsSupplier);
            }
            if (metal.bootsSupplier != null) {
                metal.boots = Registration.ITEMS.register(
                        metal.toolName + "_boots", metal.bootsSupplier);
            }
            if (metal.swordSupplier != null) {
                metal.sword = Registration.ITEMS.register(
                        metal.toolName + "_sword", metal.swordSupplier);
            }
            if (metal.axeSupplier != null) {
                metal.axe = Registration.ITEMS.register(
                        metal.toolName + "_axe", metal.axeSupplier);
            }
            if (metal.pickaxeSupplier != null) {
                metal.pickaxe = Registration.ITEMS.register(
                        metal.toolName + "_pickaxe", metal.pickaxeSupplier);
            }
            if (metal.shovelSupplier != null) {
                metal.shovel = Registration.ITEMS.register(
                        metal.toolName + "_shovel", metal.shovelSupplier);
            }
            if (metal.hoeSupplier != null) {
                metal.hoe = Registration.ITEMS.register(
                        metal.toolName + "_hoe", metal.hoeSupplier);
            }
        }
    }

    public FeatureStatus getStatus() {
        return status;
    }

    private static Builder builder(String name) {
        return new Builder(name);
    }

    private static Builder builder(String name, FeatureStatus status) {
        return new Builder(name, status);
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @SuppressWarnings({"SameParameterValue", "deprecation"})
    private static final class Builder {
        final String name;
        private Supplier<Item> baseMaterial;
        private Supplier<Item> handleMaterial;
        private @Nullable
        Supplier<Item> armorSubMaterial;
        private Supplier<ArmorMaterial> armorMaterial;
        private Supplier<Tier> itemTier;
        private Supplier<ArmorItem> helmet;
        private Supplier<ArmorItem> chestplate;
        private Supplier<ArmorItem> leggings;
        private Supplier<ArmorItem> boots;
        private Supplier<SwordItem> sword;
        private Supplier<AxeItem> axe;
        private Supplier<PickaxeItem> pickaxe;
        private Supplier<ShovelItem> shovel;
        private Supplier<HoeItem> hoe;
        private FeatureStatus status = FeatureStatus.NORMAL;

        Builder(String name) {
            this.name = name;
        }

        Builder(String name, FeatureStatus status) {
            this.name = name;
            this.status = status;
        }

        Builder material(Supplier<Item> material, Supplier<Item> handleMaterial) {
            RandomThingz.LOGGER.debug(name + "{BUILDER:MATERIAL[0]}: " + material);
            return material(material, handleMaterial, null);
        }

        Builder material(Supplier<Item> material, Supplier<Item> handleMaterial, @Nullable Supplier<Item> armorSubMaterial) {
            RandomThingz.LOGGER.debug(name + "{BUILDER:MATERIAL[1]}: " + material);
            this.baseMaterial = material;
            this.handleMaterial = handleMaterial;
            this.armorSubMaterial = armorSubMaterial;
            return this;
        }

        Builder armor(Supplier<ArmorMaterial> armorMaterial) {
            return armor(() -> TraitPack.EMPTY, armorMaterial);
        }

        Builder armor(Supplier<TraitPack> pack, Supplier<ArmorMaterial> armorMaterial) {
            this.armorMaterial = armorMaterial;

            if (status == FeatureStatus.WIP) {
                this.helmet = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().helmet) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.wip"));
                    }
                };
                this.chestplate = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().chestplate) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.wip"));
                    }
                };
                this.leggings = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().leggings) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.wip"));
                    }
                };
                this.boots = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().boots) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.wip"));
                    }
                };
            } else if (status == FeatureStatus.NORMAL) {
                this.helmet = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().helmet) {
                };
                this.chestplate = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.CHEST, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().chestplate) {
                };
                this.leggings = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.LEGS, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().leggings) {
                };
                this.boots = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().boots) {
                };
            } else if (status == FeatureStatus.DEPRECATED) {
                this.helmet = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.HEAD, new Item.Properties(), () -> pack.get().helmet) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.chestplate = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.CHEST, new Item.Properties(), () -> pack.get().chestplate) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.leggings = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.LEGS, new Item.Properties(), () -> pack.get().leggings) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.boots = () -> new ArmorTool(armorMaterial.get(), EquipmentSlot.FEET, new Item.Properties(), () -> pack.get().boots) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.deprecated"));
                    }
                };
            }
            return this;
        }

        Builder tools(Supplier<Tier> itemTier) {
            return tools(() -> TraitPack.EMPTY, itemTier);
        }

        Builder tools(Supplier<TraitPack> pack, Supplier<Tier> itemTier) {
            this.itemTier = itemTier;

            if (status == FeatureStatus.WIP) {
                this.sword = () -> new SwordTool(itemTier.get(), 3, -2.4f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().sword) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.wip"));
                    }
                };
                this.axe = () -> new AxeTool(itemTier.get(), 5f, -3f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().axe) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.wip"));
                    }
                };
                this.pickaxe = () -> new PickaxeTool(itemTier.get(), 1, -2.8f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().pickaxe) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.wip"));
                    }
                };
                this.shovel = () -> new ShovelTool(itemTier.get(), 1.5f, -3f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().shovel) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.wip"));
                    }
                };
                this.hoe = () -> new HoeTool(itemTier.get(), (int) -(itemTier.get().getAttackDamageBonus() - 1), -1f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().hoe) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.wip"));
                    }
                };
            } else if (status == FeatureStatus.NORMAL) {
                this.sword = () -> new SwordTool(itemTier.get(), 3, -2.4f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().sword);
                this.axe = () -> new AxeTool(itemTier.get(), 5f, -3f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().axe);
                this.pickaxe = () -> new PickaxeTool(itemTier.get(), 1, -2.8f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().pickaxe);
                this.shovel = () -> new ShovelTool(itemTier.get(), 1.5f, -3f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().shovel);
                this.hoe = () -> new HoeTool(itemTier.get(), (int) -(itemTier.get().getAttackDamageBonus() - 1), -1f, new Item.Properties().tab(ModCreativeTabs.TOOLS), () -> pack.get().hoe);
            } else if (status == FeatureStatus.DEPRECATED) {
                this.sword = () -> new SwordTool(itemTier.get(), 3, -2.4f, new Item.Properties(), () -> pack.get().sword) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.axe = () -> new AxeTool(itemTier.get(), 5f, -3f, new Item.Properties(), () -> pack.get().axe) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.pickaxe = () -> new PickaxeTool(itemTier.get(), 1, -2.8f, new Item.Properties(), () -> pack.get().pickaxe) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.shovel = () -> new ShovelTool(itemTier.get(), 1.5f, -3f, new Item.Properties(), () -> pack.get().shovel) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.hoe = () -> new HoeTool(itemTier.get(), (int) -(itemTier.get().getAttackDamageBonus() - 1), -1f, new Item.Properties(), () -> pack.get().hoe) {
                    @Override
                    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
                        super.appendHoverText(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslatableComponent("misc.randomthingz.deprecated"));
                    }
                };
            }
            return this;
        }
    }
}
