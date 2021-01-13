package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.variants.OreMaterial;
import com.qsoftware.forgemod.objects.items.CraftingItems;
import com.qsoftware.forgemod.objects.items.EucalyptusLeafItem;
import com.qsoftware.forgemod.objects.items.LegendaryEnderPearlItem;
import com.qsoftware.forgemod.objects.items.advanced.AdvancedBowItem;
import com.qsoftware.forgemod.objects.items.debug.DebugItem;
import com.qsoftware.forgemod.objects.items.energy.BatteryItem;
import com.qsoftware.forgemod.objects.items.energy.WrenchItem;
import com.qsoftware.forgemod.objects.items.fluid.CanisterItem;
import com.qsoftware.forgemod.objects.items.fluid.EmptyCanisterItem;
import com.qsoftware.forgemod.objects.items.fluid.HandPumpItem;
import com.qsoftware.forgemod.objects.items.fluid.NoPlaceBucketItem;
import com.qsoftware.forgemod.objects.items.overpowered.UnstableInfinityIngot;
import com.qsoftware.forgemod.objects.items.specials.KillSwitchItem;
import com.qsoftware.forgemod.objects.items.specials.KnifeItem;
import com.qsoftware.forgemod.objects.items.specials.MagnetItem;
import com.qsoftware.forgemod.objects.items.tools.*;
import com.qsoftware.forgemod.objects.items.type.IngotOrDustItem;
import com.qsoftware.forgemod.objects.items.type.IngredientItem;
import com.qsoftware.forgemod.objects.items.type.SliceableItem;
import com.qsoftware.forgemod.objects.items.upgrades.MachineUpgrades;
import com.qsoftware.forgemod.objects.items.wand.*;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import com.qsoftware.forgemod.util.ExceptionUtil;
import com.qsoftware.forgemod.util.builder.ArmorMaterialBuilder;
import com.qsoftware.forgemod.util.builder.ItemTierBuilder1;
import com.qsoftware.forgemod.util.color.ColorGetter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.item.Items.OBSIDIAN;

@SuppressWarnings({"unused", "OptionalGetWithoutIsPresent"})
public final class ModItems {
    static {
        OreMaterial.registerItems();
        CraftingItems.register();
        MachineUpgrades.register();
    }

    public static final ItemRegistryObject<WrenchItem> WRENCH = register("wrench", WrenchItem::new);
    public static final ItemRegistryObject<DebugItem> DEBUG_ITEM = register("debug_item", DebugItem::new);
    public static final ItemRegistryObject<BatteryItem> BATTERY = register("battery", BatteryItem::new);
    public static final ItemRegistryObject<HandPumpItem> HAND_PUMP = register("hand_pump", HandPumpItem::new);
    public static final ItemRegistryObject<CanisterItem> CANISTER = register("canister", () ->
            new CanisterItem(new Item.Properties().group(ModItemGroups.MISC)));
    public static final ItemRegistryObject<EmptyCanisterItem> EMPTY_CANISTER = register("empty_canister", () ->
            new EmptyCanisterItem(new Item.Properties().group(ModItemGroups.MISC)));
    public static final ItemRegistryObject<BucketItem> OIL_BUCKET = register("oil_bucket", () ->
            createBucketItem(() -> ModFluids.OIL));
    public static final ItemRegistryObject<BucketItem> DIESEL_BUCKET = register("diesel_bucket", () ->
            createBucketItem(() -> ModFluids.DIESEL));
    public static final ItemRegistryObject<NoPlaceBucketItem> ETHANE_BUCKET = register("ethane_bucket", () ->
            createNoPlaceBucketItem(() -> ModFluids.ETHANE));
    public static final ItemRegistryObject<NoPlaceBucketItem> POLYETHYLENE_BUCKET = register("polyethylene_bucket", () ->
            createNoPlaceBucketItem(() -> ModFluids.POLYETHYLENE));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Overpowered     //
    /////////////////////////
    public static final ItemRegistryObject<KillSwitchItem> KILL_SWITCH = register("kill_switch", KillSwitchItem::new);
    public static final ItemRegistryObject<BanHammerItem> BAN_HAMMER = register("ban_hammer", BanHammerItem::new);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Miscellaneous     //
    ///////////////////////////
    public static final ItemRegistryObject<LegendaryEnderPearlItem> LEGENDARY_ENDER_PEARL = register("legendary_ender_pearl", () -> new LegendaryEnderPearlItem(new Item.Properties().group(ModItemGroups.MISC)));
    public static final ItemRegistryObject<DynamiteItem> DYNAMITE = register("dynamite", () -> new DynamiteItem(new Item.Properties().group(ModItemGroups.MISC).rarity(Rarity.UNCOMMON)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Food     //
    //////////////////
    public static final ItemRegistryObject<Item> CHEESE_BURGER = register("cheese_burger", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(1).saturation(0.2f).effect(() -> new EffectInstance(Effects.REGENERATION, 60, 1), 0.7f).build())));
    public static final ItemRegistryObject<Item> CHEESE_SLICE = register("cheese_slice", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(1).saturation(0.2f).build())));
    public static final ItemRegistryObject<SliceableItem> CHEESE = register("cheese", () -> new SliceableItem(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(2).saturation(0.3f).build()), (stack) -> new ItemStack(CHEESE_SLICE, stack.getCount() * 6)));
    public static final ItemRegistryObject<Item> COOKED_CARROT = register("cooked_carrot", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(3).saturation(0.25f).build())));
    public static final ItemRegistryObject<Item> BAKED_APPLE = register("baked_apple", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(4).saturation(0.3f).build())));
    public static final ItemRegistryObject<Item> FLOUR = register("flour", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD)));
    public static final ItemRegistryObject<Item> DOUGH = register("dough", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD)));
    public static final ItemRegistryObject<Item> BACON = register("bacon", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(1).saturation(0.13f).build())));
    public static final ItemRegistryObject<Item> COOKED_BACON = register("cooked_bacon", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(5).saturation(0.40f).build())));
    public static final ItemRegistryObject<Item> CHICKEN_LEG = register("chicken_leg", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(1).saturation(0.1f).build())));
    public static final ItemRegistryObject<Item> COOKED_CHICKEN_LEG = register("cooked_chicken_leg", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(4).saturation(0.35f).build())));
    public static final ItemRegistryObject<Item> MEATBALL = register("meatball", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(1).saturation(0.15f).effect(() -> new EffectInstance(Effects.HUNGER, 100, 1), 1).build())));
    public static final ItemRegistryObject<Item> COOKED_MEATBALL = register("cooked_meatball", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(3).saturation(0.225f).build())));
    public static final ItemRegistryObject<Item> SHOARMA = register("shoarma", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(2).saturation(0.15f).build())));
    public static final ItemRegistryObject<Item> COOKED_SHOARMA = register("cooked_shoarma", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(6).saturation(0.45f).build())));
    public static final ItemRegistryObject<Item> PORK_SHANK = register("pork_shank", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(2).saturation(0.15f).build())));
    public static final ItemRegistryObject<Item> COOKED_PORK_SHANK = register("cooked_pork_shank", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(5).saturation(0.375f).build())));
    public static final ItemRegistryObject<Item> TOMAHAWK = register("tomahawk", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(2).saturation(0.215f).build())));
    public static final ItemRegistryObject<Item> COOKED_TOMAHAWK = register("cooked_tomahawk", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(6).saturation(0.415f).build())));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Nature     //
    ////////////////////
    public static final ItemRegistryObject<Item> STICK_VARIANT_1 = register("stick_variant1", () -> new Item(new Item.Properties().group(ModItemGroups.NATURE)));
    public static final ItemRegistryObject<Item> STICK_VARIANT_2 = register("stick_variant2", () -> new Item(new Item.Properties().group(ModItemGroups.NATURE)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Wood     //
    //////////////////
    public static final ItemRegistryObject<Item> EUCALYPTUS_LEAF = register("eucalyptus_leaf", () -> new EucalyptusLeafItem(new Item.Properties()
            .group(ModItemGroups.NATURE)
            .maxStackSize(128)
            .food(new Food.Builder()
                    .hunger(1)
                    .saturation(0.2f)
                    .effect(() -> new EffectInstance(Effects.REGENERATION, 60, 1), 0.7f)
                    .build())));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Fletching     //
    ///////////////////////

    // Bows
    public static final ItemRegistryObject<AdvancedBowItem> BLAZE_BOW = register("blaze_bow", () -> new AdvancedBowItem(new Item.Properties().group(ModItemGroups.FLETCHING), 6.25f, 1.0f, 6, 1, true));
    public static final ItemRegistryObject<AdvancedBowItem> ICE_BOW = register("ice_bow", () -> new AdvancedBowItem(new Item.Properties().group(ModItemGroups.FLETCHING), 2f, 1.0f, 8, 2));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Ingredients     //
    /////////////////////////

    // Glass shards
    public static final ItemRegistryObject<IngredientItem> CLEAR_SHARD = register("clear_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> BLACK_SHARD = register("black_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> BLUE_SHARD = register("blue_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> BROWN_SHARD = register("brown_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> CYAN_SHARD = register("cyan_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> GRAY_SHARD = register("gray_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> GREEN_SHARD = register("green_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> LIGHT_BLUE_SHARD = register("light_blue_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> LIGHT_GRAY_SHARD = register("light_gray_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> LIME_SHARD = register("lime_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> MAGENTA_SHARD = register("magenta_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> ORANGE_SHARD = register("orange_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> PINK_SHARD = register("pink_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> PURPLE_SHARD = register("purple_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> RED_SHARD = register("red_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> WHITE_SHARD = register("white_shard", IngredientItem::new);
    public static final ItemRegistryObject<IngredientItem> YELLOW_SHARD = register("yellow_shard", IngredientItem::new);

    // Rods
    public static final ItemRegistryObject<IngredientItem> URANIUM_ROD = register("uranium_rod", IngredientItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Ingots or Dusts     //
    /////////////////////////////

    // Metals - Tungsten Steel Level
    public static final ItemRegistryObject<IngotOrDustItem> TUNGSTEN_INGOT = register("tungsten_ingot", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> TUNGSTEN_NUGGET = register("tungsten_nugget", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> TUNGSTEN_DUST = register("tungsten_dust", IngotOrDustItem::new);
    // Metals - Ultrinium Level
    public static final ItemRegistryObject<IngotOrDustItem> ULTRINIUM_INGOT = register("ultrinium_ingot", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> ULTRINIUM_NUGGET = register("ultrinium_nugget", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> ULTRINIUM_DUST = register("ultrinium_dust", IngotOrDustItem::new);
    // Metals - Infinity Level
    public static final ItemRegistryObject<UnstableInfinityIngot> UNSTABLE_INFINITY_INGOT = register("unstable_infinity_ingot", UnstableInfinityIngot::new);
    public static final ItemRegistryObject<IngotOrDustItem> INFINITY_INGOT = register("infinity_ingot", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> INFINITY_NUGGET = register("infinity_nugget", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> INFINITY_DUST = register("infinity_dust", IngotOrDustItem::new);
    // Dusts
    public static final ItemRegistryObject<IngotOrDustItem> RUBY_DUST = register("ruby_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> AMETHYST_DUST = register("amethyst_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> AQUAMARINE_DUST = register("aquamarine_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> SAPHIRE_DUST = register("saphire_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> MALACHITE_DUST = register("malachite_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> TOPAZ_DUST = register("topaz_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> AMBER_DUST = register("amber_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> BERYL_DUST = register("beryl_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> DIAMOND_DUST = register("diamond_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> TANZANITE_DUST = register("tanzanite_dust", IngotOrDustItem::new);
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Gems     //
    //////////////////
    public static final ItemRegistryObject<Item> RUBY = register("ruby", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    public static final ItemRegistryObject<Item> AMETHYST = register("amethyst", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    public static final ItemRegistryObject<Item> AQUAMARINE = register("aquamarine", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    public static final ItemRegistryObject<Item> SAPHIRE = register("saphire", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    public static final ItemRegistryObject<Item> MALACHITE = register("malachite", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    public static final ItemRegistryObject<Item> TOPAZ = register("topaz", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    public static final ItemRegistryObject<Item> AMBER = register("amber", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    public static final ItemRegistryObject<Item> PERIDOT = register("peridot", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    public static final ItemRegistryObject<Item> BERYL = register("beryl", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    public static final ItemRegistryObject<Item> TANZANITE = register("tanzanite", () -> new Item(new Item.Properties().group(ModItemGroups.GEMS)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Icons     //
    ///////////////////
    public static final ItemRegistryObject<Item> DUNGEONS = register("dungeons", () -> new SwordItem(ItemTier.DIAMOND, 0, 0f, new Item.Properties().setNoRepair().maxStackSize(1)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Dungeons     //
    //////////////////////
    public static final ItemRegistryObject<AdvancedBowItem> DUNGEONS_HUNTERS_BOW = register("hunters_bow", () -> new AdvancedBowItem(new Item.Properties().group(ModItemGroups.DUNGEONS), 4.0f, 1.0f, 1.75d) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_DIAMOND_PICKAXE = register("dungeons_diamond_pickaxe", () -> new PickaxeItem(ItemTier.DIAMOND, 3, -2.1f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_DIAMOND_AXE = register("dungeons_diamond_axe", () -> new AxeItem(ItemTier.DIAMOND, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_AXE = register("dungeons_axe", () -> new AxeItem(ItemTier.IRON, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_CURSED_AXE = register("cursed_axe", () -> new AxeItem(ItemTier.GOLD, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_FIREBRAND = register("firebrand", () -> new AxeItem(ItemTier.DIAMOND, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_HIGHLAND_AXE = register("highland_axe", () -> new AxeItem(ItemTier.IRON, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_WHIRLWIND = register("whirlwind", () -> new AxeItem(ItemTier.IRON, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_DAGGER = register("dungeons_dagger", () -> new SwordItem(ItemTier.IRON, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_ETERNAL_KNIFE = register("eternal_knife", () -> new SwordItem(ItemTier.DIAMOND, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_FANGS_OF_FROST = register("fangs_of_frost", () -> new SwordItem(ItemTier.IRON, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_MOON_DAGGER = register("moon_dagger", () -> new SwordItem(ItemTier.IRON, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_NIGHTMARES_BITE = register("nightmares_bite", () -> new SwordItem(ItemTier.DIAMOND, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_SPIRIT_KNIFE = register("spirit_knife", () -> new SwordItem(ItemTier.DIAMOND, 2, -1.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_THE_LAST_LAUGH_GOLD = register("the_last_laugh_gold", () -> new SwordItem(ItemTier.GOLD, 3, -0.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_THE_LAST_LAUGH_SILVER = register("the_last_laugh_silver", () -> new SwordItem(ItemTier.IRON, 2, -0.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_DIAMOND_SWORD = register("dungeons_diamond_sword", () -> new SwordItem(ItemTier.DIAMOND, 4, -2.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_IRON_SWORD = register("dungeons_iron_sword", () -> new SwordItem(ItemTier.IRON, 4, -2.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_BROADSWORD = register("dungeons_broadsword", () -> new SwordItem(ItemTier.IRON, 5, -3.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_STORMLANDER = register("stormlander", () -> new PickaxeItem(ItemTier.DIAMOND, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_GREAT_HAMMER = register("great_hammer", () -> new PickaxeItem(ItemTier.IRON, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_HAMMER_OF_GRAVITY = register("hammer_of_gravity", () -> new PickaxeItem(ItemTier.DIAMOND, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_FLAIL = register("flail", () -> new SwordItem(ItemTier.STONE, 5, -3.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_SUNS_GRACE = register("suns_grace", () -> new SwordItem(ItemTier.STONE, 5, -3.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<HoeItem> DUNGEONS_FROST_SCYTHE = register("frost_scythe", () -> new HoeItem(ItemTier.STONE, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<HoeItem> DUNGEONS_JAILORS_SCYTHE = register("jailors_scythe", () -> new HoeItem(ItemTier.STONE, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Overpowered     //
    /////////////////////////
    public static final ItemRegistryObject<WalkingStaffItem> WALKING_STAFF = register("walking_wand", WalkingStaffItem::new);
    public static final ItemRegistryObject<LightningStaffItem> LIGHTNING_STAFF = register("lightning_wand", LightningStaffItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Specials     //
    //////////////////////

    public static final ItemRegistryObject<KnifeItem> KNIFE = register("knife", () -> new KnifeItem(ItemTier.IRON, new Item.Properties().group(ModItemGroups.SPECIALS).maxDamage(4)));
    public static final ItemRegistryObject<MagnetItem> MAGNET = register("magnet", () -> new MagnetItem(new Item.Properties().group(ModItemGroups.SPECIALS).maxDamage(4)));

    // Wands
    public static final ItemRegistryObject<FireWandItem> FIRE_STAFF = register("fire_wand", FireWandItem::new);
    public static final ItemRegistryObject<NatureStaffItem> NATURE_STAFF = register("nature_wand", NatureStaffItem::new);
    public static final ItemRegistryObject<TeleportStaffItem> TELEPORT_STAFF = register("teleport_wand", TeleportStaffItem::new);

    // Stone Level
    public static final ItemRegistryObject<SwordItem> STONE_SWORD_OF_DOOM = register("stone_sword_of_doom", () -> new SwordItem(ItemTier.STONE, 8, -2.0f, new Item.Properties().group(ModItemGroups.SPECIALS).rarity(Rarity.RARE)));

    // Iron Level
    public static final ItemRegistryObject<AxeItem> EMERGENCY_FIRE_AXE = register("emergency_fire_axe", () -> new AxeItem(ItemTier.IRON, 2, -2.55f, new Item.Properties().group(ModItemGroups.SPECIALS).rarity(Rarity.UNCOMMON)));
    public static final ItemRegistryObject<FireSwordItem> FIRE_SWORD = register("fire_sword", () -> new FireSwordItem(ItemTier.IRON, 3, -3.5f, new Item.Properties().group(ModItemGroups.SPECIALS).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<EnderSwordItem> ENDER_SWORD = register("ender_sword", () -> new EnderSwordItem(ItemTier.IRON, 3, -1.9f, new Item.Properties().group(ModItemGroups.SPECIALS).rarity(Rarity.EPIC)));

    // Diamond Level
    public static final ItemRegistryObject<AxeItem> LEVIATHAN_AXE = register("leviathan_axe", () -> new AxeItem(ItemTier.DIAMOND, 5, -2.55f, new Item.Properties().group(ModItemGroups.SPECIALS)));
    public static final ItemRegistryObject<AxeItem> ADAMANTANIUM_AXE_RED = register("adamantanium_axe_red", () -> new AxeItem(ItemTier.DIAMOND, 5, -1.875f, new Item.Properties().group(ModItemGroups.SPECIALS)));
    public static final ItemRegistryObject<SwordItem> DIAMOND_QUARTZ_SWORD = register("diamond_quartz_sword", () -> new SwordItem(ItemTier.DIAMOND, 6, -2.0f, new Item.Properties().group(ModItemGroups.SPECIALS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Tools     //
    ///////////////////

    /////////////////////////////
    //     Armor materials     //
    /////////////////////////////
    public static final IArmorMaterial copperArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":copper", 13, new int[]{2, 5, 6, 2}, 10, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> Ingredient.fromItems(OreMaterial.COPPER.getIngot().get())
    );
    public static final IArmorMaterial redstoneArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":redstone", 8, new int[]{1, 4, 3, 2}, 5, 0.2f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.5F, () -> Ingredient.fromItems(OreMaterial.REDSTONE_ALLOY.getIngot().get())
    );
    public static final IArmorMaterial nickelArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":nickel", 13, new int[]{2, 5, 7, 3}, 16, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, () -> Ingredient.fromItems(OreMaterial.NICKEL.getIngot().get())
    );
    public static final IArmorMaterial steelArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":steel", 24, new int[]{3, 6, 8, 4}, 14, 4f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F, () -> Ingredient.fromItems(OreMaterial.STEEL.getIngot().get())
    );
    public static final IArmorMaterial tungstenArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":tungsten", 42, new int[]{4, 8, 12, 6}, 28, 5f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, () -> Ingredient.fromItems(ModItems.TUNGSTEN_INGOT.get())
    );
    public static final IArmorMaterial uraniumArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":uranium", 11, new int[]{2, 4, 5, 2}, 4, 0.5f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, () -> Ingredient.fromItems(OreMaterial.URANIUM.getIngot().get())
    );
    public static final IArmorMaterial platinumArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":platinum", 46, new int[]{3, 6, 8, 4}, 4, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F, () -> Ingredient.fromItems(OreMaterial.PLATINUM.getIngot().get())
    );
    public static final IArmorMaterial rubyArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":ruby", 24, new int[]{3, 6, 8, 4}, 14, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ModItems.RUBY.get())
    );
    public static final IArmorMaterial amethystArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":amethyst", 21, new int[]{2, 5, 7, 3}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ModItems.AMETHYST.get())
    );
    public static final IArmorMaterial aquamarineArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":aquamarine", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ModItems.AQUAMARINE.get())
    );
    public static final IArmorMaterial saphireArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":saphire", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ModItems.SAPHIRE.get())
    );
    public static final IArmorMaterial malachiteArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":malachite", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ModItems.MALACHITE.get())
    );
    public static final IArmorMaterial topazArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":topaz", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ModItems.TOPAZ.get())
    );
    public static final IArmorMaterial amberArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":amber", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ModItems.AMBER.get())
    );
    public static final IArmorMaterial berylArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":beryl", 21, new int[]{2, 4, 6, 2}, 31, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ModItems.BERYL.get())
    );
    public static final IArmorMaterial tanzaniteArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":tanzanite", 19, new int[]{3, 6, 8, 3}, 48, 1f,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, () -> Ingredient.fromItems(ModItems.TANZANITE.get())
    );
    public static final IArmorMaterial obsidianArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":obsidian", 42, new int[]{9, 18, 24, 7}, 19, 9f,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 0.0F, () -> Ingredient.fromItems(OreMaterial.COPPER.getIngot().get())
    );
    public static final IArmorMaterial ultriniumArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":ultrinium", 95250, new int[]{2375, 5643, 6485, 1947}, 375, 3854f,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 290.0F, () -> Ingredient.fromItems(ModItems.ULTRINIUM_INGOT.get())
    );
    public static final IArmorMaterial infinityArmorMaterial = new ArmorMaterialBuilder.Builder(QForgeMod.MOD_ID + ":infinity", (int) Double.POSITIVE_INFINITY, new int[]{(int) Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY}, (int) Double.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 0, () -> Ingredient.fromItems(ModItems.INFINITY_INGOT.get())
    );

    ////////////////////////
    //     Item tiers     //
    ////////////////////////
    public static final IItemTier COPPER_ITEM_TIER = new ItemTierBuilder1(2, 420, 5.3f, 1.4f, 10,
            () -> Ingredient.fromItems(OreMaterial.COPPER.getIngot().orElseThrow(() -> new NullPointerException("Copper ingot not found in OreMaterial class.")))
    );
    public static final IItemTier REDSTONE_ITEM_TIER = new ItemTierBuilder1(0, 230, 2.3f, 1.2f, 5,
            () -> Ingredient.fromItems(OreMaterial.REDSTONE_ALLOY.getIngot().orElseThrow(() -> new NullPointerException("Redstone Alloy ingot not found in OreMaterial class.")))
    );
    public static final IItemTier NICKLE_ITEM_TIER = new ItemTierBuilder1(2, 480, 4.9f, 1.7f, 16,
            () -> Ingredient.fromItems(OreMaterial.NICKEL.getIngot().orElseThrow(() -> new NullPointerException("Nickel ingot not found in OreMaterial class.")))
    );
    public static final IItemTier STEEL_ITEM_TIER = new ItemTierBuilder1(3, 1465, 8.1f, 3.8f, 14,
            () -> Ingredient.fromItems(OreMaterial.STEEL.getIngot().orElseThrow(() -> new NullPointerException("Steel ingot not found in OreMaterial class.")))
    );
    public static final IItemTier TUNGSTEN_ITEM_TIER = new ItemTierBuilder1(4, 2994, 9.4f, 4.7f, 28,
            () -> Ingredient.fromItems(TUNGSTEN_INGOT)
    );
    public static final IItemTier URANIUM_ITEM_TIER = new ItemTierBuilder1(3, 340, 3.6f, 6.3f, 4,
            () -> Ingredient.fromItems(OreMaterial.URANIUM.getIngot().orElseThrow(() -> new NullPointerException("Uranium ingot not found in OreMaterial class.")))
    );
    public static final IItemTier PLATINUM_ITEM_TIER = new ItemTierBuilder1(3, 1294, 7.5f, 4f, 12,
            () -> Ingredient.fromItems(OreMaterial.PLATINUM.getIngot().orElseThrow(() -> new NullPointerException("Platinum ingot not found in OreMaterial class.")))
    );
    public static final IItemTier RUBY_ITEM_TIER = new ItemTierBuilder1(3, 970, 7.6f, 3.6f, 13,
            () -> Ingredient.fromItems(RUBY)
    );
    public static final IItemTier AMETHYST_ITEM_TIER = new ItemTierBuilder1(2, 650, 7.3f, 3.1f, 31,
            () -> Ingredient.fromItems(AMETHYST)
    );
    public static final IItemTier AQUAMARINE_ITEM_TIER = new ItemTierBuilder1(2, 740, 5.3f, 2.6f, 23,
            () -> Ingredient.fromItems(AQUAMARINE)
    );
    public static final IItemTier SAPHIRE_ITEM_TIER = new ItemTierBuilder1(2, 810, 5.2f, 2.5f, 29,
            () -> Ingredient.fromItems(SAPHIRE)
    );
    public static final IItemTier MALACHITE_ITEM_TIER = new ItemTierBuilder1(2, 670, 4.3f, 3.2f, 12,
            () -> Ingredient.fromItems(MALACHITE)
    );
    public static final IItemTier TOPAZ_ITEM_TIER = new ItemTierBuilder1(1, 665, 4.4f, 3.9f, 17,
            () -> Ingredient.fromItems(TOPAZ)
    );
    public static final IItemTier AMBER_ITEM_TIER = new ItemTierBuilder1(2, 670, 3.9f, 3.1f, 16,
            () -> Ingredient.fromItems(AMBER)
    );
    public static final IItemTier BERYL_ITEM_TIER = new ItemTierBuilder1(2, 730, 4.8f, 3.5f, 11,
            () -> Ingredient.fromItems(BERYL)
    );
    public static final IItemTier TANZANITE_ITEM_TIER = new ItemTierBuilder1(3, 1090, 7.7125f, 3.5f, 48,
            () -> Ingredient.fromItems(TANZANITE)
    );
    public static final IItemTier OBSIDIAN_ITEM_TIER = new ItemTierBuilder1(5, 3953, 53.7125f, 12.5f, 19,
            () -> Ingredient.fromItems(OBSIDIAN)
    );
    public static final IItemTier ULTRINIUM_ITEM_TIER = new ItemTierBuilder1(6, 952530, 7403.0f, 237.4f, 375,
            () -> Ingredient.fromItems(ULTRINIUM_INGOT)
    );
    public static final IItemTier INFINITY = new ItemTierBuilder1(7, (int) Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, (int) Float.POSITIVE_INFINITY,
            () -> Ingredient.fromItems(INFINITY_INGOT)
    );

    // Armors - Copper
    public static final ItemRegistryObject<ArmorItem> COPPER_HELMET = register("copper_helmet", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> COPPER_CHESTPLATE = register("copper_chestplate", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> COPPER_LEGGINGS = register("copper_leggings", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> COPPER_BOOTS = register("copper_boots", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> COPPER_SWORD = register("copper_sword", () -> new SwordItem(COPPER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> COPPER_PICKAXE = register("copper_pickaxe", () -> new PickaxeItem(COPPER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> COPPER_SHOVEL = register("copper_shovel", () -> new ShovelItem(COPPER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> COPPER_AXE = register("copper_axe", () -> new AxeItem(COPPER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> COPPER_HOE = register("copper_hoe", () -> new HoeItem(COPPER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Redstone
    public static final ItemRegistryObject<ArmorItem> REDSTONE_HELMET = register("redstone_helmet", () -> new ArmorItem(redstoneArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> REDSTONE_CHESTPLATE = register("redstone_chestplate", () -> new ArmorItem(redstoneArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> REDSTONE_LEGGINGS = register("redstone_leggings", () -> new ArmorItem(redstoneArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> REDSTONE_BOOTS = register("redstone_boots", () -> new ArmorItem(redstoneArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> REDSTONE_SWORD = register("redstone_sword", () -> new SwordItem(REDSTONE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> REDSTONE_PICKAXE = register("redstone_pickaxe", () -> new PickaxeItem(REDSTONE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> REDSTONE_SHOVEL = register("redstone_shovel", () -> new ShovelItem(REDSTONE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> REDSTONE_AXE = register("redstone_axe", () -> new AxeItem(REDSTONE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> REDSTONE_HOE = register("redstone_hoe", () -> new HoeItem(REDSTONE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Nickle
    public static final ItemRegistryObject<ArmorItem> NICKLE_HELMET = register("nickel_helmet", () -> new ArmorItem(nickelArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> NICKLE_CHESTPLATE = register("nickel_chestplate", () -> new ArmorItem(nickelArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> NICKLE_LEGGINGS = register("nickel_leggings", () -> new ArmorItem(nickelArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> NICKLE_BOOTS = register("nickel_boots", () -> new ArmorItem(nickelArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> NICKLE_SWORD = register("nickel_sword", () -> new SwordItem(NICKLE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> NICKLE_PICKAXE = register("nickel_pickaxe", () -> new PickaxeItem(NICKLE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> NICKLE_SHOVEL = register("nickel_shovel", () -> new ShovelItem(NICKLE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> NICKLE_AXE = register("nickel_axe", () -> new AxeItem(NICKLE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> NICKLE_HOE = register("nickel_hoe", () -> new HoeItem(NICKLE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Steel
    public static final ItemRegistryObject<ArmorItem> STEEL_HELMET = register("steel_helmet", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> STEEL_CHESTPLATE = register("steel_chestplate", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> STEEL_LEGGINGS = register("steel_leggings", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> STEEL_BOOTS = register("steel_boots", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> STEEL_SWORD = register("steel_sword", () -> new SwordItem(STEEL_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> STEEL_PICKAXE = register("steel_pickaxe", () -> new PickaxeItem(STEEL_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> STEEL_SHOVEL = register("steel_shovel", () -> new ShovelItem(STEEL_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> STEEL_AXE = register("steel_axe", () -> new AxeItem(STEEL_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> STEEL_HOE = register("steel_hoe", () -> new HoeItem(STEEL_ITEM_TIER, 2, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Tungsten Steel
    public static final ItemRegistryObject<ArmorItem> TUNGSTEN_HELMET = register("tungsten_helmet", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> TUNGSTEN_CHESTPLATE = register("tungsten_chestplate", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> TUNGSTEN_LEGGINGS = register("tungsten_leggings", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> TUNGSTEN_BOOTS = register("tungsten_boots", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> TUNGSTEN_SWORD = register("tungsten_sword", () -> new SwordItem(TUNGSTEN_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> TUNGSTEN_PICKAXE = register("tungsten_pickaxe", () -> new PickaxeItem(TUNGSTEN_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> TUNGSTEN_SHOVEL = register("tungsten_shovel", () -> new ShovelItem(TUNGSTEN_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> TUNGSTEN_AXE = register("tungsten_axe", () -> new AxeItem(TUNGSTEN_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> TUNGSTEN_HOE = register("tungsten_hoe", () -> new HoeItem(TUNGSTEN_ITEM_TIER, 2, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Uranium
    public static final ItemRegistryObject<ArmorItem> URANIUM_HELMET = register("uranium_helmet", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> URANIUM_CHESTPLATE = register("uranium_chestplate", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> URANIUM_LEGGINGS = register("uranium_leggings", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> URANIUM_BOOTS = register("uranium_boots", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<UraniumSwordItem> URANIUM_SWORD = register("uranium_sword", () -> new UraniumSwordItem(URANIUM_ITEM_TIER, 3, -2.0f));
    public static final ItemRegistryObject<PickaxeItem> URANIUM_PICKAXE = register("uranium_pickaxe", () -> new PickaxeItem(URANIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> URANIUM_SHOVEL = register("uranium_shovel", () -> new ShovelItem(URANIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> URANIUM_AXE = register("uranium_axe", () -> new AxeItem(URANIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> URANIUM_HOE = register("uranium_hoe", () -> new HoeItem(URANIUM_ITEM_TIER, 2, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Platinum
    public static final ItemRegistryObject<ArmorItem> PLATINUM_HELMET = register("platinum_helmet", () -> new ArmorItem(platinumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> PLATINUM_CHESTPLATE = register("platinum_chestplate", () -> new ArmorItem(platinumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> PLATINUM_LEGGINGS = register("platinum_leggings", () -> new ArmorItem(platinumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> PLATINUM_BOOTS = register("platinum_boots", () -> new ArmorItem(platinumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> PLATINUM_SWORD = register("platinum_sword", () -> new SwordItem(PLATINUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> PLATINUM_PICKAXE = register("platinum_pickaxe", () -> new PickaxeItem(PLATINUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> PLATINUM_SHOVEL = register("platinum_shovel", () -> new ShovelItem(PLATINUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> PLATINUM_AXE = register("platinum_axe", () -> new AxeItem(PLATINUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> PLATINUM_HOE = register("platinum_hoe", () -> new HoeItem(PLATINUM_ITEM_TIER, 2, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Ruby
    public static final ItemRegistryObject<ArmorItem> RUBY_HELMET = register("ruby_helmet", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> RUBY_CHESTPLATE = register("ruby_chestplate", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> RUBY_LEGGINGS = register("ruby_leggings", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> RUBY_BOOTS = register("ruby_boots", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> RUBY_SWORD = register("ruby_sword", () -> new SwordItem(RUBY_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> RUBY_PICKAXE = register("ruby_pickaxe", () -> new PickaxeItem(RUBY_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> RUBY_SHOVEL = register("ruby_shovel", () -> new ShovelItem(RUBY_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> RUBY_AXE = register("ruby_axe", () -> new AxeItem(RUBY_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> RUBY_HOE = register("ruby_hoe", () -> new HoeItem(RUBY_ITEM_TIER, 2, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Amethyst
    public static final ItemRegistryObject<ArmorItem> AMETHYST_HELMET = register("amethyst_helmet", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> AMETHYST_CHESTPLATE = register("amethyst_chestplate", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> AMETHYST_LEGGINGS = register("amethyst_leggings", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> AMETHYST_BOOTS = register("amethyst_boots", () -> new ArmorItem(amethystArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> AMETHYST_SWORD = register("amethyst_sword", () -> new SwordItem(AMETHYST_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> AMETHYST_PICKAXE = register("amethyst_pickaxe", () -> new PickaxeItem(AMETHYST_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> AMETHYST_SHOVEL = register("amethyst_shovel", () -> new ShovelItem(AMETHYST_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> AMETHYST_AXE = register("amethyst_axe", () -> new AxeItem(AMETHYST_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> AMETHYST_HOE = register("amethyst_hoe", () -> new HoeItem(AMETHYST_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Aquamarine
    public static final ItemRegistryObject<ArmorItem> AQUAMARINE_HELMET = register("aquamarine_helmet", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> AQUAMARINE_CHESTPLATE = register("aquamarine_chestplate", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> AQUAMARINE_LEGGINGS = register("aquamarine_leggings", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> AQUAMARINE_BOOTS = register("aquamarine_boots", () -> new ArmorItem(aquamarineArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> AQUAMARINE_SWORD = register("aquamarine_sword", () -> new SwordItem(AQUAMARINE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> AQUAMARINE_PICKAXE = register("aquamarine_pickaxe", () -> new PickaxeItem(AQUAMARINE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> AQUAMARINE_SHOVEL = register("aquamarine_shovel", () -> new ShovelItem(AQUAMARINE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> AQUAMARINE_AXE = register("aquamarine_axe", () -> new AxeItem(AQUAMARINE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> AQUAMARINE_HOE = register("aquamarine_hoe", () -> new HoeItem(AQUAMARINE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Saphire
    public static final ItemRegistryObject<ArmorItem> SAPHIRE_HELMET = register("saphire_helmet", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> SAPHIRE_CHESTPLATE = register("saphire_chestplate", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> SAPHIRE_LEGGINGS = register("saphire_leggings", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> SAPHIRE_BOOTS = register("saphire_boots", () -> new ArmorItem(saphireArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> SAPHIRE_SWORD = register("saphire_sword", () -> new SwordItem(SAPHIRE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> SAPHIRE_PICKAXE = register("saphire_pickaxe", () -> new PickaxeItem(SAPHIRE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> SAPHIRE_SHOVEL = register("saphire_shovel", () -> new ShovelItem(SAPHIRE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> SAPHIRE_AXE = register("saphire_axe", () -> new AxeItem(SAPHIRE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> SAPHIRE_HOE = register("saphire_hoe", () -> new HoeItem(SAPHIRE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Malachite
    public static final ItemRegistryObject<ArmorItem> MALACHITE_HELMET = register("malachite_helmet", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> MALACHITE_CHESTPLATE = register("malachite_chestplate", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> MALACHITE_LEGGINGS = register("malachite_leggings", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> MALACHITE_BOOTS = register("malachite_boots", () -> new ArmorItem(malachiteArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> MALACHITE_SWORD = register("malachite_sword", () -> new SwordItem(MALACHITE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> MALACHITE_PICKAXE = register("malachite_pickaxe", () -> new PickaxeItem(MALACHITE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> MALACHITE_SHOVEL = register("malachite_shovel", () -> new ShovelItem(MALACHITE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> MALACHITE_AXE = register("malachite_axe", () -> new AxeItem(MALACHITE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> MALACHITE_HOE = register("malachite_hoe", () -> new HoeItem(MALACHITE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Topaz
    public static final ItemRegistryObject<ArmorItem> TOPAZ_HELMET = register("topaz_helmet", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> TOPAZ_CHESTPLATE = register("topaz_chestplate", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> TOPAZ_LEGGINGS = register("topaz_leggings", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> TOPAZ_BOOTS = register("topaz_boots", () -> new ArmorItem(topazArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> TOPAZ_SWORD = register("topaz_sword", () -> new SwordItem(TOPAZ_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> TOPAZ_PICKAXE = register("topaz_pickaxe", () -> new PickaxeItem(TOPAZ_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> TOPAZ_SHOVEL = register("topaz_shovel", () -> new ShovelItem(TOPAZ_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> TOPAZ_AXE = register("topaz_axe", () -> new AxeItem(TOPAZ_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> TOPAZ_HOE = register("topaz_hoe", () -> new HoeItem(TOPAZ_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Amber
    public static final ItemRegistryObject<ArmorItem> AMBER_HELMET = register("amber_helmet", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> AMBER_CHESTPLATE = register("amber_chestplate", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> AMBER_LEGGINGS = register("amber_leggings", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> AMBER_BOOTS = register("amber_boots", () -> new ArmorItem(amberArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> AMBER_SWORD = register("amber_sword", () -> new SwordItem(AMBER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> AMBER_PICKAXE = register("amber_pickaxe", () -> new PickaxeItem(AMBER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> AMBER_SHOVEL = register("amber_shovel", () -> new ShovelItem(AMBER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> AMBER_AXE = register("amber_axe", () -> new AxeItem(AMBER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> AMBER_HOE = register("amber_hoe", () -> new HoeItem(AMBER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Beryl
    public static final ItemRegistryObject<ArmorItem> BERYL_HELMET = register("beryl_helmet", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> BERYL_CHESTPLATE = register("beryl_chestplate", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> BERYL_LEGGINGS = register("beryl_leggings", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> BERYL_BOOTS = register("beryl_boots", () -> new ArmorItem(berylArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> BERYL_SWORD = register("beryl_sword", () -> new SwordItem(BERYL_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> BERYL_PICKAXE = register("beryl_pickaxe", () -> new PickaxeItem(BERYL_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> BERYL_SHOVEL = register("beryl_shovel", () -> new ShovelItem(BERYL_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> BERYL_AXE = register("beryl_axe", () -> new AxeItem(BERYL_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> BERYL_HOE = register("beryl_hoe", () -> new HoeItem(BERYL_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Tanzanite
    public static final ItemRegistryObject<ArmorItem> TANZANITE_HELMET = register("tanzanite_helmet", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> TANZANITE_CHESTPLATE = register("tanzanite_chestplate", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> TANZANITE_LEGGINGS = register("tanzanite_leggings", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> TANZANITE_BOOTS = register("tanzanite_boots", () -> new ArmorItem(tanzaniteArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> TANZANITE_SWORD = register("tanzanite_sword", () -> new SwordItem(TANZANITE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> TANZANITE_PICKAXE = register("tanzanite_pickaxe", () -> new PickaxeItem(TANZANITE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> TANZANITE_SHOVEL = register("tanzanite_shovel", () -> new ShovelItem(TANZANITE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> TANZANITE_AXE = register("tanzanite_axe", () -> new AxeItem(TANZANITE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> TANZANITE_HOE = register("tanzanite_hoe", () -> new HoeItem(TANZANITE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Obsidian
    public static final ItemRegistryObject<ArmorItem> OBSIDIAN_HELMET = register("obsidian_helmet", () -> new ArmorItem(obsidianArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ArmorItem> OBSIDIAN_CHESTPLATE = register("obsidian_chestplate", () -> new ArmorItem(obsidianArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ArmorItem> OBSIDIAN_LEGGINGS = register("obsidian_leggings", () -> new ArmorItem(obsidianArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ArmorItem> OBSIDIAN_BOOTS = register("obsidian_boots", () -> new ArmorItem(obsidianArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<SwordItem> OBSIDIAN_SWORD = register("obsidian_sword", () -> new SwordItem(OBSIDIAN_ITEM_TIER, 3, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<PickaxeItem> OBSIDIAN_PICKAXE = register("obsidian_pickaxe", () -> new PickaxeItem(OBSIDIAN_ITEM_TIER, 1, -2.9f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ShovelItem> OBSIDIAN_SHOVEL = register("obsidian_shovel", () -> new ShovelItem(OBSIDIAN_ITEM_TIER, 1.5F, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<AxeItem> OBSIDIAN_AXE = register("obsidian_axe", () -> new AxeItem(OBSIDIAN_ITEM_TIER, 7.0F, -3.1f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<HoeItem> OBSIDIAN_HOE = register("obsidian_hoe", () -> new HoeItem(OBSIDIAN_ITEM_TIER, 2, -2.9f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));

    // Armors - Ultrinium
    public static final ItemRegistryObject<ArmorItem> ULTRINIUM_HELMET = register("ultrinium_helmet", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ArmorItem> ULTRINIUM_CHESTPLATE = register("ultrinium_chestplate", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ArmorItem> ULTRINIUM_LEGGINGS = register("ultrinium_leggings", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ArmorItem> ULTRINIUM_BOOTS = register("ultrinium_boots", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<SwordItem> ULTRINIUM_SWORD = register("ultrinium_sword", () -> new SwordItem(ULTRINIUM_ITEM_TIER, 3, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<PickaxeItem> ULTRINIUM_PICKAXE = register("ultrinium_pickaxe", () -> new PickaxeItem(ULTRINIUM_ITEM_TIER, 1, -3.2f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ShovelItem> ULTRINIUM_SHOVEL = register("ultrinium_shovel", () -> new ShovelItem(ULTRINIUM_ITEM_TIER, 1.5F, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<AxeItem> ULTRINIUM_AXE = register("ultrinium_axe", () -> new AxeItem(ULTRINIUM_ITEM_TIER, 6.0F, -3.5f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<HoeItem> ULTRINIUM_HOE = register("ultrinium_hoe", () -> new HoeItem(ULTRINIUM_ITEM_TIER, 23, -3.2f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));

    // Armors - Infinity
    public static final ItemRegistryObject<ArmorItem> INFINITY_HELMET = register("infinity_helmet", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<ArmorItem> INFINITY_CHESTPLATE = register("infinity_chestplate", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<ArmorItem> INFINITY_LEGGINGS = register("infinity_leggings", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<ArmorItem> INFINITY_BOOTS = register("infinity_boots", () -> new ArmorItem(infinityArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<InfinitySwordItem> INFINITY_SWORD = register("infinity_sword", InfinitySwordItem::new);
    public static final ItemRegistryObject<InfinityPickaxeItem> INFINITY_PICKAXE = register("infinity_pickaxe", InfinityPickaxeItem::new);
    public static final ItemRegistryObject<InfinityShovelItem> INFINITY_SHOVEL = register("infinity_shovel", InfinityShovelItem::new);
    public static final ItemRegistryObject<InfinityAxeItem> INFINITY_AXE = register("infinity_axe", InfinityAxeItem::new);
    public static final ItemRegistryObject<InfinityHoeItem> INFINITY_HOE = register("infinity_hoe", InfinityHoeItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Utility Methods     //
    /////////////////////////////

    private ModItems() {
        throw ExceptionUtil.utilityConstructor();
    }

    static void register() {
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerItemColors(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                return ColorGetter.getColor(CANISTER.get().getFluid(stack).getFluid());
            }
            return 0xFFFFFF;
        }, CANISTER);
    }

    private static BucketItem createBucketItem(Supplier<FlowingFluid> fluid) {
        return new BucketItem(fluid, new Item.Properties().group(ModItemGroups.FLUIDS).maxStackSize(1).containerItem(Items.BUCKET));
    }

    private static NoPlaceBucketItem createNoPlaceBucketItem(Supplier<Fluid> fluid) {
        return new NoPlaceBucketItem(fluid, new Item.Properties().group(ModItemGroups.FLUIDS).maxStackSize(1).containerItem(Items.BUCKET));
    }

    private static <T extends Item> ItemRegistryObject<T> register(String name, Supplier<T> item) {
        return Registration.ITEMS.register(name, item);
    }
}
