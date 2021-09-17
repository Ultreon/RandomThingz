package com.ultreon.randomthingz.item.tools;

import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.commons.FeatureStatus;
import com.ultreon.randomthingz.commons.exceptions.UnidentifiedObjectException;
import com.ultreon.randomthingz.item.common.ItemMaterial;
import com.ultreon.randomthingz.item.common.ModItems;
import com.ultreon.randomthingz.item.tools.types.*;
import com.ultreon.randomthingz.modules.ui.ModItemGroups;
import com.ultreon.randomthingz.registration.Registration;
import com.ultreon.randomthingz.util.builder.ArmorMaterial;
import com.ultreon.randomthingz.util.builder.ItemTier;
import lombok.Getter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

@SuppressWarnings({"OptionalGetWithoutIsPresent", "Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
public enum Toolset {
    // Metals
    REDSTONE(builder("redstone")
            .material(() -> ItemMaterial.REDSTONE_ALLOY.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":redstone")
                    .maxDamageFactor(8)
                    .damageReduction(new int[]{1, 3, 4, 2})
                    .enchantability(5)
                    .knockbackResistance(-0.8f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0.5F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.REDSTONE_ALLOY.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(1).maxUses(230).efficiency(2.3f).attackDamage(1.2f).enchantability(7).build())),
    COPPER(builder("copper")
            .material(() -> ItemMaterial.COPPER.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":copper")
                    .maxDamageFactor(13)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.COPPER.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.COPPER.getIngot().orElseThrow(() -> new NullPointerException("Copper ingot not found in OreMaterial class.")))).build())),
    TIN(builder("tin")
            .material(() -> ItemMaterial.TIN.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":tin")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.TIN.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.2f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.TIN.getIngot().orElseThrow(() -> new NullPointerException("Tin ingot not found in OreMaterial class.")))).build())),
    COMPRESSED_IRON(builder("compressed_iron", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.COMPRESSED_IRON.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":compressed_iron")
                    .maxDamageFactor(17)
                    .damageReduction(new int[]{3, 6, 7, 3})
                    .enchantability(9)
                    .knockbackResistance(0.05f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.5F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.COMPRESSED_IRON.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(350).efficiency(7.0f).attackDamage(2.5f).enchantability(10)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.COPPER.getIngot().orElseThrow(() -> new NullPointerException("Copper ingot not found in OreMaterial class.")))).build())),
    OBSIDIAN(builder("obsidian")
            .material(() -> Items.OBSIDIAN, () -> Items.BLAZE_ROD, () -> Items.BLAZE_ROD)
            .armor(() -> TraitPack.create().armor(ModTraits.BLAST_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":obsidian")
                    .maxDamageFactor(131)
                    .damageReduction(new int[]{4, 8, 10, 6})
                    .enchantability(17)
                    .knockbackResistance(0.2f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
                    .toughness(4.0F)
                    .repairMaterial(() -> Ingredient.fromItems(Items.OBSIDIAN))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.SHARP.get(), ModTraits.BLAZE.get()).build(), () -> ItemTier.builder()
                    .tier(4).maxUses(4738).efficiency(10.0f).attackDamage(6f).enchantability(17)
                    .repairMaterial(() -> Ingredient.fromItems(Items.OBSIDIAN)).build())),
    SILVER(builder("silver")
            .material(() -> ItemMaterial.SILVER.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().all(ModTraits.HOLY.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":silver")
                    .maxDamageFactor(15)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(48)
                    .knockbackResistance(1f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.SILVER.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.HOLY.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(580).efficiency(5.4f).attackDamage(1.7f).enchantability(48)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.SILVER.getIngot().orElseThrow(() -> new NullPointerException("Silver ingot not found in OreMaterial class.")))).build())),
    LEAD(builder("lead")
            .material(() -> ItemMaterial.LEAD.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().all(ModTraits.POISON.get(), ModTraits.SHARP.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":lead")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{4, 9, 12, 7})
                    .enchantability(4)
                    .knockbackResistance(1.0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(4.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.LEAD.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.POISON.get(), ModTraits.SHARP.get()).build(), () -> ItemTier.builder()
                    .tier(3).maxUses(450).efficiency(7.0f).attackDamage(3.0f).enchantability(7)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.LEAD.getIngot().orElseThrow(() -> new NullPointerException("Lead ingot not found in OreMaterial class.")))).build())),
    TUNGSTEN(builder("tungsten", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.TUNGSTEN.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":tungsten")
                    .maxDamageFactor(40)
                    .damageReduction(new int[]{3, 6, 8, 3})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.TUNGSTEN.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(3).maxUses(760).efficiency(7.5f).attackDamage(3.0f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.TUNGSTEN.getIngot().get())).build())),
    NICKEL(builder("nickel", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.NICKEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":nickel")
                    .maxDamageFactor(13)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.NICKEL.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(260).efficiency(5.5f).attackDamage(1.8f).enchantability(10)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.NICKEL.getIngot().orElseThrow(() -> new NullPointerException("Nickel ingot not found in OreMaterial class.")))).build())),
    PLATINUM(builder("platinum")
            .material(() -> ItemMaterial.PLATINUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":platinum")
                    .maxDamageFactor(36)
                    .damageReduction(new int[]{3, 6, 8, 3})
                    .enchantability(14)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.PLATINUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(4).maxUses(1240).efficiency(8.6f).attackDamage(5.0f).enchantability(14)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.PLATINUM.getIngot().orElseThrow(() -> new NullPointerException("Platinum ingot not found in OreMaterial class.")))).build())),
    ZINC(builder("zinc")
            .material(() -> ItemMaterial.ZINC.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().all(ModTraits.POISON.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":zinc")
                    .maxDamageFactor(6)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ZINC.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.POISON.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(170).efficiency(6.2f).attackDamage(2.5f).enchantability(6)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ZINC.getIngot().orElseThrow(() -> new NullPointerException("Zinc ingot not found in OreMaterial class.")))).build())),
    BISMUTH(builder("bismuth", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.BISMUTH.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":bismuth")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BISMUTH.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(230).efficiency(5.3f).attackDamage(1.4f).enchantability(14)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BISMUTH.getIngot().orElseThrow(() -> new NullPointerException("Bismuth ingot not found in OreMaterial class.")))).build())),
    ALUMINUM(builder("aluminum")
            .material(() -> ItemMaterial.ALUMINUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":aluminum")
                    .maxDamageFactor(13)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ALUMINUM.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.SHARP.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(180).efficiency(5.6f).attackDamage(3.0f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ALUMINUM.getIngot().orElseThrow(() -> new NullPointerException("Aluminum ingot not found in OreMaterial class.")))).build())),
    URANIUM(builder("uranium")
            .material(() -> ItemMaterial.URANIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().all(ModTraits.RADIOACTIVE.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":uranium")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.URANIUM.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(ModTraits.RADIOACTIVE.get()).build(), () -> ItemTier.builder()
                    .tier(3).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.URANIUM.getIngot().orElseThrow(() -> new NullPointerException("Uranium ingot not found in OreMaterial class.")))).build())),
    BRONZE(builder("bronze")
            .material(() -> ItemMaterial.BRONZE.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":bronze")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BRONZE.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BRONZE.getIngot().orElseThrow(() -> new NullPointerException("Bronze ingot not found in OreMaterial class.")))).build())),
    BRASS(builder("brass", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.BRASS.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":brass")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BRASS.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BRASS.getIngot().orElseThrow(() -> new NullPointerException("Brass ingot not found in OreMaterial class.")))).build())),
    INVAR(builder("invar", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.INVAR.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":invar")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.INVAR.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.INVAR.getIngot().orElseThrow(() -> new NullPointerException("Invar ingot not found in OreMaterial class.")))).build())),
    ELECTRUM(builder("electrum", FeatureStatus.WIP)
            .material(() -> ItemMaterial.ELECTRUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":electrum")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(11)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ELECTRUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(1).maxUses(283).efficiency(4.5f).attackDamage(2.0f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ELECTRUM.getIngot().orElseThrow(() -> new NullPointerException("Electrum ingot not found in OreMaterial class.")))).build())),
    STEEL(builder("steel")
            .material(() -> ItemMaterial.STEEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.BLAST_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":steel")
                    .maxDamageFactor(28)
                    .damageReduction(new int[]{2, 6, 8, 3})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(1.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.STEEL.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().tools(ModTraits.KNOCKBACK.get(), ModTraits.SHARP.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(570).efficiency(7.1f).attackDamage(2.3f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.STEEL.getIngot().orElseThrow(() -> new NullPointerException("Steel ingot not found in OreMaterial class.")))).build())),
    BISMUTH_BRASS(builder("bismuth_brass", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.BISMUTH_BRASS.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":bismuth_brass")
                    .maxDamageFactor(16)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BISMUTH_BRASS.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(300).efficiency(5.3f).attackDamage(1.4f).enchantability(10)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BISMUTH_BRASS.getIngot().orElseThrow(() -> new NullPointerException("Bismuth Brass ingot not found in OreMaterial class.")))).build())),
    ALUMINUM_STEEL(builder("aluminum_steel")
            .material(() -> ItemMaterial.ALUMINUM_STEEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.BLAST_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":aluminum_steel")
                    .maxDamageFactor(17)
                    .damageReduction(new int[]{2, 6, 7, 3})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ALUMINUM_STEEL.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().tools(ModTraits.SHARP.get()).build(), () -> ItemTier.builder()
                    .tier(3).maxUses(684).efficiency(6.5f).attackDamage(2.8f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ALUMINUM_STEEL.getIngot().orElseThrow(() -> new NullPointerException("Aluminum Steel ingot not found in OreMaterial class.")))).build())),
    BISMUTH_STEEL(builder("bismuth_steel", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.BISMUTH_STEEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":bismuth_steel")
                    .maxDamageFactor(18)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BISMUTH_STEEL.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(467).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.BISMUTH_STEEL.getIngot().orElseThrow(() -> new NullPointerException("Bismuth Steel ingot not found in OreMaterial class.")))).build())),
    SIGNALUM(builder("signalum", FeatureStatus.DEPRECATED)
            .material(() -> ItemMaterial.SIGNALUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":signalum")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.SIGNALUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(344).efficiency(5.8f).attackDamage(1.7f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.SIGNALUM.getIngot().orElseThrow(() -> new NullPointerException("Signalum ingot not found in OreMaterial class.")))).build())),
    LUMIUM(builder("lumium", FeatureStatus.WIP)
            .material(() -> ItemMaterial.LUMIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":lumium")
                    .maxDamageFactor(9)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(36)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.LUMIUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(200).efficiency(5.0f).attackDamage(2.7f).enchantability(36)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.LUMIUM.getIngot().orElseThrow(() -> new NullPointerException("Lumium ingot not found in OreMaterial class.")))).build())),
    ENDERIUM(builder("enderium")
            .material(() -> ItemMaterial.ENDERIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.ENDER.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":enderium")
                    .maxDamageFactor(42)
                    .damageReduction(new int[]{4, 10, 12, 3})
                    .enchantability(56)
                    .knockbackResistance(0.2f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(4.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ENDERIUM.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().tools(ModTraits.ENDER.get()).build(), () -> ItemTier.builder()
                    .tier(2).maxUses(2340).efficiency(9.0f).attackDamage(7.0f).enchantability(56)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ENDERIUM.getIngot().orElseThrow(() -> new NullPointerException("Enderium ingot not found in OreMaterial class.")))).build())),
    COBALT(builder("cobalt", FeatureStatus.WIP)
            .material(() -> ItemMaterial.COBALT.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.MAGIC_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":cobalt")
                    .maxDamageFactor(64)
                    .damageReduction(new int[]{28, 48, 56, 35})
                    .enchantability(24)
                    .knockbackResistance(0.2f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
                    .toughness(6.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.COBALT.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(5).maxUses(6535).efficiency(27.5f).attackDamage(31.0f).enchantability(93)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.COBALT.getIngot().orElseThrow(() -> new UnidentifiedObjectException("Cobalt ingot not found in OreMaterial class.")))).build())),
    ULTRINIUM(builder("ultrinium")
            .material(() -> ItemMaterial.ULTRINIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.BLAST_RESISTANT.get(), ModTraits.MAGIC_RESISTANT.get(), ModTraits.FIRE_RESISTANT.get(), ModTraits.HOLY.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":ultrinium")
                    .maxDamageFactor(192)
                    .damageReduction(new int[]{125, 200, 275, 165})
                    .enchantability(224)
                    .knockbackResistance(1f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
                    .toughness(64.0F)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ULTRINIUM.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().armor(ModTraits.HOLY.get(), ModTraits.WITHER.get(), ModTraits.POISON.get()).build(), () -> ItemTier.builder()
                    .tier(6).maxUses(65536).efficiency(102.8f).attackDamage(127.0f).enchantability(224)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.ULTRINIUM.getIngot().orElseThrow(() -> new UnidentifiedObjectException("Ultrinium ingot not found in OreMaterial class.")))).build())),
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
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
                    .toughness(Float.POSITIVE_INFINITY)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.INFINITY.getIngot().get()))
                    .build())
            .tools(() -> TraitPack.create().all(
                    ModTraits.INFINITY.get(), ModTraits.POISON.get(),
                    ModTraits.WITHER.get(), ModTraits.SHARP.get(),
                    ModTraits.ENDER.get(), ModTraits.BLAZE.get(),
                    ModTraits.RADIOACTIVE.get()).build(), () -> ItemTier.builder()
                    .tier(7).maxUses((int) Float.POSITIVE_INFINITY).efficiency(Float.POSITIVE_INFINITY).attackDamage(Float.POSITIVE_INFINITY).enchantability((int) Float.POSITIVE_INFINITY)
                    .repairMaterial(() -> Ingredient.fromItems(ItemMaterial.INFINITY.getIngot().orElseThrow(() -> new UnidentifiedObjectException("Infinity ingot not found in OreMaterial class.")))).build())),
    AQUAMARINE(builder("aquamarine", FeatureStatus.WIP)
            .material(() -> ModItems.AQUAMARINE.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":aquamarine")
                    .maxDamageFactor(5)
                    .damageReduction(new int[]{2, 4, 5, 2})
                    .enchantability(13)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.AQUAMARINE.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(9.0f).attackDamage(3.2f).enchantability(13)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.AQUAMARINE.get())).build())),
    AMETHYST(builder("amethyst")
            .material(() -> ModItems.AMETHYST.get(), () -> Items.STICK)
            .armor(() -> TraitPack.create().armor(ModTraits.MAGIC_RESISTANT.get()).build(), () -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":amethyst")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(38)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.AMETHYST.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.AMETHYST.get())).build())),
    BERYL(builder("beryl", FeatureStatus.WIP)
            .material(() -> ModItems.BERYL.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":beryl")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(17)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.BERYL.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.BERYL.get())).build())),
    AMBER(builder("amber", FeatureStatus.WIP)
            .material(() -> ModItems.AMBER.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":amber")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(8)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.AMBER.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.AMBER.get())).build())),
    MALACHITE(builder("malachite", FeatureStatus.WIP)
            .material(() -> ModItems.MALACHITE.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":malachite")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(14)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.MALACHITE.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.MALACHITE.get())).build())),
    PERIDOT(builder("peridot", FeatureStatus.WIP)
            .material(() -> ModItems.PERIDOT.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":peridot")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(12)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.PERIDOT.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.PERIDOT.get())).build())),
    SAPHIRE(builder("saphire", FeatureStatus.WIP)
            .material(() -> ModItems.SAPHIRE.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":saphire")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(23)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.SAPHIRE.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.SAPHIRE.get())).build())),
    RUBY(builder("ruby", FeatureStatus.WIP)
            .material(() -> ModItems.RUBY.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":ruby")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(35)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.RUBY.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.RUBY.get())).build())),
    TANZANITE(builder("tanzanite", FeatureStatus.WIP)
            .material(() -> ModItems.TANZANITE.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":tanzanite")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(20)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.TANZANITE.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.TANZANITE.get())).build())),
    TOPAZ(builder("topaz", FeatureStatus.WIP)
            .material(() -> ModItems.TOPAZ.get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(RandomThingz.MOD_ID + ":topaz")
                    .maxDamageFactor(21)
                    .damageReduction(new int[]{4, 9, 11, 5})
                    .enchantability(24)
                    .knockbackResistance(0)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.TOPAZ.get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(7).maxUses(226).efficiency(6.5f).attackDamage(3.8f).enchantability(30)
                    .repairMaterial(() -> Ingredient.fromItems(ModItems.TOPAZ.get())).build())),
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
    private final Supplier<IArmorMaterial> armorMaterial;
    @Getter
    private final Supplier<IItemTier> itemTier;

    private final Supplier<ArmorItem> helmetSupplier;
    private final Supplier<ArmorItem> chestplateSupplier;
    private final Supplier<ArmorItem> leggingsSupplier;
    private final Supplier<ArmorItem> bootsSupplier;
    private final Supplier<SwordItem> swordSupplier;
    private final Supplier<AxeItem> axeSupplier;
    private final Supplier<PickaxeItem> pickaxeSupplier;
    private final Supplier<ShovelItem> shovelSupplier;
    private final Supplier<HoeItem> hoeSupplier;

    private final Supplier<LongswordItem> longswordSupplier;
    private final Supplier<KatanaItem> katanaSupplier;
    private final Supplier<CutlassItem> cutlassSupplier;
    private final Supplier<BroadswordItem> broadswordSupplier;
    private final Supplier<LumberAxeItem> lumberAxeSupplier;
    private final Supplier<BattleaxeItem> battleaxeSupplier;
    private final Supplier<HammerItem> hammerSupplier;
    private final Supplier<ExcavatorItem> excavatorSupplier;
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
    @Getter
    private ItemRegistryObject<LongswordItem> longsword;
    @Getter
    private ItemRegistryObject<KatanaItem> katana;
    @Getter
    private ItemRegistryObject<CutlassItem> cutlass;
    @Getter
    private ItemRegistryObject<BroadswordItem> broadsword;
    @Getter
    private ItemRegistryObject<LumberAxeItem> lumberAxe;
    @Getter
    private ItemRegistryObject<BattleaxeItem> battleaxe;
    @Getter
    private ItemRegistryObject<HammerItem> hammer;
    @Getter
    private ItemRegistryObject<ExcavatorItem> excavator;

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
        this.longswordSupplier = builder.longsword;
        this.katanaSupplier = builder.katana;
        this.cutlassSupplier = builder.cutlass;
        this.broadswordSupplier = builder.broadsword;
        this.lumberAxeSupplier = builder.lumberAxe;
        this.battleaxeSupplier = builder.battleaxe;
        this.hammerSupplier = builder.hammer;
        this.excavatorSupplier = builder.excavator;
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
            if (metal.longswordSupplier != null) {
                metal.longsword = Registration.ITEMS.register(
                        metal.toolName + "_longsword", metal.longswordSupplier);
            }
            if (metal.katanaSupplier != null) {
                metal.katana = Registration.ITEMS.register(
                        metal.toolName + "_katana", metal.katanaSupplier);
            }
            if (metal.cutlassSupplier != null) {
                metal.cutlass = Registration.ITEMS.register(
                        metal.toolName + "_cutlass", metal.cutlassSupplier);
            }
            if (metal.broadswordSupplier != null) {
                metal.broadsword = Registration.ITEMS.register(
                        metal.toolName + "_broadsword", metal.broadswordSupplier);
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
            if (metal.lumberAxeSupplier != null) {
                metal.lumberAxe = Registration.ITEMS.register(
                        metal.toolName + "_lumber_axe", metal.lumberAxeSupplier);
            }
            if (metal.battleaxeSupplier != null) {
                metal.battleaxe = Registration.ITEMS.register(
                        metal.toolName + "_battleaxe", metal.battleaxeSupplier);
            }
            if (metal.hammerSupplier != null) {
                metal.hammer = Registration.ITEMS.register(
                        metal.toolName + "_hammer", metal.hammerSupplier);
            }
            if (metal.excavatorSupplier != null) {
                metal.excavator = Registration.ITEMS.register(
                        metal.toolName + "_excavator", metal.excavatorSupplier);
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

    @SuppressWarnings("SameParameterValue")
    private static final class Builder {
        final String name;
        private Supplier<Item> baseMaterial;
        private Supplier<Item> handleMaterial;
        private @Nullable
        Supplier<Item> armorSubMaterial;
        private Supplier<IArmorMaterial> armorMaterial;
        private Supplier<IItemTier> itemTier;
        private Supplier<ArmorItem> helmet;
        private Supplier<ArmorItem> chestplate;
        private Supplier<ArmorItem> leggings;
        private Supplier<ArmorItem> boots;
        private Supplier<SwordItem> sword;
        private Supplier<AxeItem> axe;
        private Supplier<PickaxeItem> pickaxe;
        private Supplier<ShovelItem> shovel;
        private Supplier<HoeItem> hoe;
        private Supplier<LongswordItem> longsword;
        private Supplier<KatanaItem> katana;
        private Supplier<CutlassItem> cutlass;
        private Supplier<BroadswordItem> broadsword;
        private Supplier<LumberAxeItem> lumberAxe;
        private Supplier<BattleaxeItem> battleaxe;
        private Supplier<HammerItem> hammer;
        private Supplier<ExcavatorItem> excavator;
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

        Builder armor(Supplier<IArmorMaterial> armorMaterial) {
            return armor(() -> TraitPack.EMPTY, armorMaterial);
        }

        Builder armor(Supplier<TraitPack> pack, Supplier<IArmorMaterial> armorMaterial) {
            this.armorMaterial = armorMaterial;

            if (status == FeatureStatus.WIP) {
                this.helmet = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().helmet) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.chestplate = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().chestplate) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.leggings = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().leggings) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.boots = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().boots) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
            } else if (status == FeatureStatus.NORMAL) {
                this.helmet = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().helmet) {
                };
                this.chestplate = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().chestplate) {
                };
                this.leggings = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().leggings) {
                };
                this.boots = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().boots) {
                };
            } else if (status == FeatureStatus.DEPRECATED) {
                this.helmet = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.HEAD, new Item.Properties(), () -> pack.get().helmet) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.chestplate = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.CHEST, new Item.Properties(), () -> pack.get().chestplate) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.leggings = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.LEGS, new Item.Properties(), () -> pack.get().leggings) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.boots = () -> new ArmorTool(armorMaterial.get(), EquipmentSlotType.FEET, new Item.Properties(), () -> pack.get().boots) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
            }
            return this;
        }

        Builder tools(Supplier<IItemTier> itemTier) {
            return tools(() -> TraitPack.EMPTY, itemTier);
        }

        Builder tools(Supplier<TraitPack> pack, Supplier<IItemTier> itemTier) {
            this.itemTier = itemTier;

            if (status == FeatureStatus.WIP) {
                this.sword = () -> new SwordTool(itemTier.get(), 3, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().sword) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.axe = () -> new AxeTool(itemTier.get(), 5.0f, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().axe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.pickaxe = () -> new PickaxeTool(itemTier.get(), 1, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().pickaxe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.shovel = () -> new ShovelTool(itemTier.get(), 1.5f, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().shovel) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.hoe = () -> new HoeTool(itemTier.get(), (int) -(itemTier.get().getAttackDamage() - 1), -1.0f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().hoe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.longsword = () -> new LongswordTool(itemTier.get(), 3, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().longsword) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.broadsword = () -> new BroadswordTool(itemTier.get(), 5, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().broadsword) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.katana = () -> new KatanaTool(itemTier.get(), 3, -1.2f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().katana) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.cutlass = () -> new CutlassTool(itemTier.get(), 4, -1.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().cutlass) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.lumberAxe = () -> new LumberAxeTool(itemTier.get(), 5.5f, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().lumberAxe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.battleaxe = () -> new BattleaxeTool(itemTier.get(), 6.5f, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().battleaxe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.hammer = () -> new HammerTool(itemTier.get(), 4, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().hammer) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
                this.excavator = () -> new ExcavatorTool(itemTier.get(), 2.0f, -2.6f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().excavator) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.wip"));
                    }
                };
            } else if (status == FeatureStatus.NORMAL) {
                this.sword = () -> new SwordTool(itemTier.get(), 3, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().sword);
                this.axe = () -> new AxeTool(itemTier.get(), 5.0f, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().axe);
                this.pickaxe = () -> new PickaxeTool(itemTier.get(), 1, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().pickaxe);
                this.shovel = () -> new ShovelTool(itemTier.get(), 1.5f, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().shovel);
                this.hoe = () -> new HoeTool(itemTier.get(), (int) -(itemTier.get().getAttackDamage() - 1), -1.0f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().hoe);
                this.longsword = () -> new LongswordTool(itemTier.get(), 3, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().longsword);
                this.broadsword = () -> new BroadswordTool(itemTier.get(), 5, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().broadsword);
                this.katana = () -> new KatanaTool(itemTier.get(), 3, -1.2f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().katana);
                this.cutlass = () -> new CutlassTool(itemTier.get(), 4, -1.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().cutlass);
                this.lumberAxe = () -> new LumberAxeTool(itemTier.get(), 5.5f, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().lumberAxe);
                this.battleaxe = () -> new BattleaxeTool(itemTier.get(), 6.5f, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().battleaxe);
                this.hammer = () -> new HammerTool(itemTier.get(), 4, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().hammer);
                this.excavator = () -> new ExcavatorTool(itemTier.get(), 2.0f, -2.6f, new Item.Properties().group(ModItemGroups.TOOLS), () -> pack.get().excavator);
            } else if (status == FeatureStatus.DEPRECATED) {
                this.sword = () -> new SwordTool(itemTier.get(), 3, -2.4f, new Item.Properties(), () -> pack.get().sword) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.axe = () -> new AxeTool(itemTier.get(), 5.0f, -3.0f, new Item.Properties(), () -> pack.get().axe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.pickaxe = () -> new PickaxeTool(itemTier.get(), 1, -2.8f, new Item.Properties(), () -> pack.get().pickaxe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.shovel = () -> new ShovelTool(itemTier.get(), 1.5f, -3.0f, new Item.Properties(), () -> pack.get().shovel) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.hoe = () -> new HoeTool(itemTier.get(), (int) -(itemTier.get().getAttackDamage() - 1), -1.0f, new Item.Properties(), () -> pack.get().hoe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.longsword = () -> new LongswordTool(itemTier.get(), 3, -2.8f, new Item.Properties(), () -> pack.get().longsword) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.broadsword = () -> new BroadswordTool(itemTier.get(), 5, -3.0f, new Item.Properties(), () -> pack.get().broadsword) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.katana = () -> new KatanaTool(itemTier.get(), 3, -1.2f, new Item.Properties(), () -> pack.get().katana) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.cutlass = () -> new CutlassTool(itemTier.get(), 4, -1.8f, new Item.Properties(), () -> pack.get().cutlass) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.lumberAxe = () -> new LumberAxeTool(itemTier.get(), 5.5f, -2.8f, new Item.Properties(), () -> pack.get().lumberAxe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.battleaxe = () -> new BattleaxeTool(itemTier.get(), 6.5f, -2.4f, new Item.Properties(), () -> pack.get().battleaxe) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.hammer = () -> new HammerTool(itemTier.get(), 4, -2.8f, new Item.Properties(), () -> pack.get().hammer) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
                this.excavator = () -> new ExcavatorTool(itemTier.get(), 2.0f, -2.6f, new Item.Properties(), () -> pack.get().excavator) {
                    @Override
                    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
                        super.addInformation(stack, dimension, tooltip, flag);
                        tooltip.add(new TranslationTextComponent("misc.randomthingz.deprecated"));
                    }
                };
            }
            return this;
        }
    }
}