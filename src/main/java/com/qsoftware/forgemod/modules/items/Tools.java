package com.qsoftware.forgemod.modules.items;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.modules.items.objects.tools.*;
import com.qsoftware.forgemod.modules.ui.ModItemGroups;
import com.qsoftware.forgemod.util.builder.ArmorMaterial;
import com.qsoftware.forgemod.util.builder.ItemTier;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import lombok.Getter;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Supplier;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public enum Tools {
    // Metals
    REDSTONE(builder("redstone")
            .material(() -> OreMaterial.REDSTONE_ALLOY.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":redstone")
                    .maxDamageFactor(8)
                    .damageReduction(new int[]{1, 4, 3, 2})
                    .enchantability(5)
                    .knockbackResistance(-0.8f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
                    .toughness(0.5F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.REDSTONE_ALLOY.getIngot().get()))
                    .build())
            .tools(() ->  ItemTier.builder()
                    .tier(0).maxUses(230).efficiency(2.3f).attackDamage(1.2f).enchantability(7).build())),
    COPPER(builder("copper")
            .material(() -> OreMaterial.COPPER.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":copper")
                    .maxDamageFactor(13)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.COPPER.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.COPPER.getIngot().orElseThrow(() -> new NullPointerException("Copper ingot not found in OreMaterial class.")))).build())),
    TIN(builder("tin")
            .material(() -> OreMaterial.TIN.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":tin")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.TIN.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.TIN.getIngot().orElseThrow(() -> new NullPointerException("Tin ingot not found in OreMaterial class.")))).build())),
    COMPRESSED_IRON(builder("compressed_iron")
            .material(() -> OreMaterial.COMPRESSED_IRON.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":compressed_iron")
                    .maxDamageFactor(17)
                    .damageReduction(new int[]{3, 6, 7, 3})
                    .enchantability(9)
                    .knockbackResistance(0.05f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.5F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.COMPRESSED_IRON.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(350).efficiency(7.0f).attackDamage(2.5f).enchantability(10)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.COPPER.getIngot().orElseThrow(() -> new NullPointerException("Copper ingot not found in OreMaterial class.")))).build())),
    OBSIDIAN(builder("obsidian")
            .material(() -> Items.OBSIDIAN, () -> Items.BLAZE_ROD, () -> Items.BLAZE_ROD)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":obsidian")
                    .maxDamageFactor(72)
                    .damageReduction(new int[]{467, 853, 787, 326})
                    .enchantability(17)
                    .knockbackResistance(0.2f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
                    .toughness(8.0F)
                    .repairMaterial(() -> Ingredient.fromItems(Items.OBSIDIAN))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(5).maxUses(4738).efficiency(63.0f).attackDamage(18f).enchantability(17)
                    .repairMaterial(() -> Ingredient.fromItems(Items.OBSIDIAN)).build())),
//    SILVER(builder("silver")
//            .material(() -> OreMaterial.SILVER.getIngot().get(), () -> Items.STICK)
//            .armor(() -> ArmorMaterial.builder()
//                    .name(QForgeMod.modId + ":silver")
//                    .maxDamageFactor(14)
//                    .damageReduction(new int[]{2, 5, 6, 2})
//                    .enchantability(10)
//                    .knockbackResistance(0f)
//                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
//                    .toughness(0.0F)
//                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.SILVER.getIngot().get()))
//                    .build())
//            .tools(() -> ItemTier.builder()
//                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
//                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.SILVER.getIngot().orElseThrow(() -> new NullPointerException("Silver ingot not found in OreMaterial class.")))).build())),
    LEAD(builder("lead")
            .material(() -> OreMaterial.LEAD.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":lead")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 8, 3})
                    .enchantability(7)
                    .knockbackResistance(0.1f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.LEAD.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(450).efficiency(7.0f).attackDamage(3.0f).enchantability(7)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.LEAD.getIngot().orElseThrow(() -> new NullPointerException("Lead ingot not found in OreMaterial class.")))).build())),
//    TUNGSTEN(builder("tungsten")
//            .material(ModItems.TUNGSTEN_INGOT::get, () -> Items.STICK)
//            .armor(() -> ArmorMaterial.builder()
//                    .name(QForgeMod.modId + ":tungsten")
//                    .maxDamageFactor(21)
//                    .damageReduction(new int[]{3, 6, 8, 3})
//                    .enchantability(10)
//                    .knockbackResistance(0f)
//                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
//                    .toughness(0.0F)
//                    .repairMaterial(() -> Ingredient.fromItems(ModItems.TUNGSTEN_INGOT))
//                    .build())
//            .tools(() -> ItemTier.builder()
//                    .tier(3).maxUses(760).efficiency(7.5f).attackDamage(3.0f).enchantability(9)
//                    .repairMaterial(() -> Ingredient.fromItems(ModItems.TUNGSTEN_INGOT)).build())),
    NICKEL(builder("nickel")
            .material(() -> OreMaterial.NICKEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":nickel")
                    .maxDamageFactor(13)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.NICKEL.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(260).efficiency(5.5f).attackDamage(1.8f).enchantability(10)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.NICKEL.getIngot().orElseThrow(() -> new NullPointerException("Nickel ingot not found in OreMaterial class.")))).build())),
    PLATINUM(builder("platinum")
            .material(() -> OreMaterial.PLATINUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":platinum")
                    .maxDamageFactor(38)
                    .damageReduction(new int[]{3, 6, 8, 3})
                    .enchantability(14)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.PLATINUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(4).maxUses(1240).efficiency(8.0f).attackDamage(5.0f).enchantability(14)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.PLATINUM.getIngot().orElseThrow(() -> new NullPointerException("Platinum ingot not found in OreMaterial class.")))).build())),
    ZINC(builder("zinc")
            .material(() -> OreMaterial.ZINC.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":zinc")
                    .maxDamageFactor(6)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ZINC.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(170).efficiency(6.2f).attackDamage(2.5f).enchantability(6)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ZINC.getIngot().orElseThrow(() -> new NullPointerException("Zinc ingot not found in OreMaterial class.")))).build())),
    BISMUTH(builder("bismuth")
            .material(() -> OreMaterial.BISMUTH.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":bismuth")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BISMUTH.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(230).efficiency(5.3f).attackDamage(1.4f).enchantability(14)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BISMUTH.getIngot().orElseThrow(() -> new NullPointerException("Bismuth ingot not found in OreMaterial class.")))).build())),
    ALUMINUM(builder("aluminum")
            .material(() -> OreMaterial.ALUMINUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":aluminum")
                    .maxDamageFactor(13)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ALUMINUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(180).efficiency(5.6f).attackDamage(3.0f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ALUMINUM.getIngot().orElseThrow(() -> new NullPointerException("Aluminum ingot not found in OreMaterial class.")))).build())),
//    URANIUM(builder("uranium")
//            .material(() -> OreMaterial.URANIUM.getIngot().get(), () -> Items.STICK)
//            .armor(() -> ArmorMaterial.builder()
//                    .name(QForgeMod.modId + ":uranium")
//                    .maxDamageFactor(14)
//                    .damageReduction(new int[]{2, 5, 6, 2})
//                    .enchantability(10)
//                    .knockbackResistance(0f)
//                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
//                    .toughness(0.0F)
//                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.URANIUM.getIngot().get()))
//                    .build())
//            .tools(() -> ItemTier.builder()
//                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
//                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.URANIUM.getIngot().orElseThrow(() -> new NullPointerException("Uranium ingot not found in OreMaterial class.")))).build())),
    BRONZE(builder("bronze")
            .material(() -> OreMaterial.BRONZE.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":bronze")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BRONZE.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BRONZE.getIngot().orElseThrow(() -> new NullPointerException("Bronze ingot not found in OreMaterial class.")))).build())),
    BRASS(builder("brass")
            .material(() -> OreMaterial.BRASS.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":brass")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BRASS.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BRASS.getIngot().orElseThrow(() -> new NullPointerException("Brass ingot not found in OreMaterial class.")))).build())),
    INVAR(builder("invar")
            .material(() -> OreMaterial.INVAR.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":invar")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.INVAR.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.INVAR.getIngot().orElseThrow(() -> new NullPointerException("Invar ingot not found in OreMaterial class.")))).build())),
    ELECTRUM(builder("electrum")
            .material(() -> OreMaterial.ELECTRUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":electrum")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(11)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ELECTRUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(1).maxUses(220).efficiency(4.5f).attackDamage(2.0f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ELECTRUM.getIngot().orElseThrow(() -> new NullPointerException("Electrum ingot not found in OreMaterial class.")))).build())),
    STEEL(builder("steel")
            .material(() -> OreMaterial.STEEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":steel")
                    .maxDamageFactor(19)
                    .damageReduction(new int[]{2, 6, 8, 3})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.STEEL.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(570).efficiency(7.1f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.STEEL.getIngot().orElseThrow(() -> new NullPointerException("Steel ingot not found in OreMaterial class.")))).build())),
    BISMUTH_BRASS(builder("bismuth_brass")
            .material(() -> OreMaterial.BISMUTH_BRASS.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":bismuth_brass")
                    .maxDamageFactor(16)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BISMUTH_BRASS.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(300).efficiency(5.3f).attackDamage(1.4f).enchantability(10)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BISMUTH_BRASS.getIngot().orElseThrow(() -> new NullPointerException("Bismuth Brass ingot not found in OreMaterial class.")))).build())),
    ALUMINUM_STEEL(builder("aluminum_steel")
            .material(() -> OreMaterial.ALUMINUM_STEEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":aluminum_steel")
                    .maxDamageFactor(17)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(9)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ALUMINUM_STEEL.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(340).efficiency(6.5f).attackDamage(1.4f).enchantability(9)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ALUMINUM_STEEL.getIngot().orElseThrow(() -> new NullPointerException("Aluminum Steel ingot not found in OreMaterial class.")))).build())),
    BISMUTH_STEEL(builder("bismuth_steel")
            .material(() -> OreMaterial.BISMUTH_STEEL.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":bismuth_steel")
                    .maxDamageFactor(18)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BISMUTH_STEEL.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.BISMUTH_STEEL.getIngot().orElseThrow(() -> new NullPointerException("Bismuth Steel ingot not found in OreMaterial class.")))).build())),
    SIGNALUM(builder("signalum")
            .material(() -> OreMaterial.SIGNALUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":signalum")
                    .maxDamageFactor(14)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(10)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.SIGNALUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(220).efficiency(5.3f).attackDamage(1.4f).enchantability(11)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.SIGNALUM.getIngot().orElseThrow(() -> new NullPointerException("Signalum ingot not found in OreMaterial class.")))).build())),
    LUMIUM(builder("lumium")
            .material(() -> OreMaterial.LUMIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":lumium")
                    .maxDamageFactor(12)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(36)
                    .knockbackResistance(0f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.LUMIUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(200).efficiency(5.0f).attackDamage(2.7f).enchantability(36)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.LUMIUM.getIngot().orElseThrow(() -> new NullPointerException("Lumium ingot not found in OreMaterial class.")))).build())),
    ENDERIUM(builder("enderium")
            .material(() -> OreMaterial.ENDERIUM.getIngot().get(), () -> Items.STICK)
            .armor(() -> ArmorMaterial.builder()
                    .name(QForgeMod.modId + ":enderium")
                    .maxDamageFactor(42)
                    .damageReduction(new int[]{2, 5, 6, 2})
                    .enchantability(56)
                    .knockbackResistance(0.2f)
                    .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
                    .toughness(0.0F)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ENDERIUM.getIngot().get()))
                    .build())
            .tools(() -> ItemTier.builder()
                    .tier(2).maxUses(2340).efficiency(9.0f).attackDamage(7.0f).enchantability(56)
                    .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ENDERIUM.getIngot().orElseThrow(() -> new NullPointerException("Enderium ingot not found in OreMaterial class.")))).build())),
    ;
    //    IRON(builder("iron").chunks().dust().ingotTagOnly().nuggetTagOnly()),
//    GOLD(builder("gold").chunks().dust().ingotTagOnly().nuggetTagOnly()),
//    COPPER(builderBaseWithOre("copper", Ore.COPPER)),
//    // Gems
//    RUBY(builderGem("ruby", Ore.RUBY)),
//    BERYL(builderGem("beryl", Ore.BERYL)),
//    MALACHITE(builderGem("malachite", Ore.MALACHITE)),
//    PERIDOT(builderGem("peridot", Ore.PERIDOT)),
//    AMBER(builderGem("amber", Ore.AMBER)),
//    SAPPHIRE(builderGem("sapphire", Ore.SAPPHIRE)),
//    AMETHYST(builderGem("amethyst", Ore.AMETHYST)),
//    TANZANITE(builderGem("tanzanite", Ore.TANZANITE)),
//    ;

    private final String toolName;

    @Getter private final Supplier<Item> baseMaterial;
    @Getter private final Supplier<Item> handleMaterial;
    @Nullable
    @Getter private final Supplier<Item> armorSubMaterial;

    @Getter private final Supplier<IArmorMaterial> armorMaterial;
    @Getter private final Supplier<IItemTier> itemTier;

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
    private final Supplier<BroadswordItem> broadswordSupplier;
    private final Supplier<LumberAxeItem> lumberAxeSupplier;
    private final Supplier<BattleaxeItem> battleaxeSupplier;
    private final Supplier<HammerItem> hammerSupplier;
    private final Supplier<ExcavatorItem> excavatorSupplier;

    @Getter private ItemRegistryObject<ArmorItem> helmet;
    @Getter private ItemRegistryObject<ArmorItem> chestplate;
    @Getter private ItemRegistryObject<ArmorItem> leggings;
    @Getter private ItemRegistryObject<ArmorItem> boots;
    @Getter private ItemRegistryObject<SwordItem> sword;
    @Getter private ItemRegistryObject<AxeItem> axe;
    @Getter private ItemRegistryObject<PickaxeItem> pickaxe;
    @Getter private ItemRegistryObject<ShovelItem> shovel;
    @Getter private ItemRegistryObject<HoeItem> hoe;
    @Getter private ItemRegistryObject<LongswordItem> longsword;
    @Getter private ItemRegistryObject<KatanaItem> katana;
    @Getter private ItemRegistryObject<BroadswordItem> broadsword;
    @Getter private ItemRegistryObject<LumberAxeItem> lumberAxe;
    @Getter private ItemRegistryObject<BattleaxeItem> battleaxe;
    @Getter private ItemRegistryObject<HammerItem> hammer;
    @Getter private ItemRegistryObject<ExcavatorItem> excavator;

    Tools(Builder builder) {
        this(builder, builder.name);
    }

    Tools(Builder builder, String toolName) {
        if (!builder.name.equals(this.getName())) {
            throw new IllegalArgumentException("Builder name is incorrect, should be " + this.getName());
        }
        this.toolName = toolName;
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
        this.broadswordSupplier = builder.broadsword;
        this.lumberAxeSupplier = builder.lumberAxe;
        this.battleaxeSupplier = builder.battleaxe;
        this.hammerSupplier = builder.hammer;
        this.excavatorSupplier = builder.excavator;
    }

    public static void registerItems() {
        for (Tools metal : values()) {
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

    private static Builder builder(String name) {
        return new Builder(name);
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    private static class Builder {
        final String name;
        private Supplier<Item> baseMaterial;
        private Supplier<Item> handleMaterial;
        private @Nullable Supplier<Item> armorSubMaterial;
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
        private Supplier<BroadswordItem> broadsword;
        private Supplier<LumberAxeItem> lumberAxe;
        private Supplier<BattleaxeItem> battleaxe;
        private Supplier<HammerItem> hammer;
        private Supplier<ExcavatorItem> excavator;

        Builder(String name) {
            this.name = name;
        }

        Builder material(Supplier<Item> material, Supplier<Item> handleMaterial) {
            return material(material, handleMaterial, null);
        }

        Builder material(Supplier<Item> material, Supplier<Item> handleMaterial, @Nullable Supplier<Item> armorSubMaterial) {
            this.baseMaterial = material;
            this.handleMaterial = handleMaterial;
            this.armorSubMaterial = armorSubMaterial;
            return this;
        }

        Builder armor(Supplier<IArmorMaterial> armorMaterial) {
            this.armorMaterial = armorMaterial;
            this.helmet = () -> new ArmorItem(armorMaterial.get(), EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS));
            this.chestplate = () -> new ArmorItem(armorMaterial.get(), EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS));
            this.leggings = () -> new ArmorItem(armorMaterial.get(), EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS));
            this.boots = () -> new ArmorItem(armorMaterial.get(), EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS));
            return this;
        }

        Builder tools(Supplier<IItemTier> itemTier) {
            this.itemTier = itemTier;
            this.sword = () -> new SwordItem(itemTier.get(), 3, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.axe = () -> new AxeItem(itemTier.get(), 5.0f, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.pickaxe = () -> new PickaxeItem(itemTier.get(), 1, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.shovel = () -> new ShovelItem(itemTier.get(), 1.5f, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.hoe = () -> new HoeItem(itemTier.get(), (int) -(itemTier.get().getAttackDamage() - 1), -1.0f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.longsword = () -> new LongswordItem(itemTier.get(), 3, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.broadsword = () -> new BroadswordItem(itemTier.get(), 4, -2.5f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.katana = () -> new KatanaItem(itemTier.get(), 3, -1.0f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.lumberAxe = () -> new LumberAxeItem(itemTier.get(), 5.5f, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.battleaxe = () -> new BattleaxeItem(itemTier.get(), 6.5f, -2.7f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.hammer = () -> new HammerItem(itemTier.get(), 4, -2.7f, new Item.Properties().group(ModItemGroups.TOOLS));
            this.excavator = () -> new ExcavatorItem(itemTier.get(), 2.0f, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS));
            return this;
        }
    }
}
