package com.qsoftware.forgemod.modules.items;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.blocks.ModFluids;
import com.qsoftware.forgemod.modules.ui.ModItemGroups;
import com.qsoftware.forgemod.modules.items.objects.CraftingItems;
import com.qsoftware.forgemod.modules.items.objects.EucalyptusLeafItem;
import com.qsoftware.forgemod.modules.items.objects.LegendaryEnderPearlItem;
import com.qsoftware.forgemod.modules.items.objects.advanced.AdvancedBowItem;
import com.qsoftware.forgemod.modules.items.objects.debug.DebugItem;
import com.qsoftware.forgemod.modules.items.objects.energy.BatteryItem;
import com.qsoftware.forgemod.modules.items.objects.energy.WrenchItem;
import com.qsoftware.forgemod.modules.items.objects.fluid.CanisterItem;
import com.qsoftware.forgemod.modules.items.objects.fluid.EmptyCanisterItem;
import com.qsoftware.forgemod.modules.items.objects.fluid.HandPumpItem;
import com.qsoftware.forgemod.modules.items.objects.fluid.NoPlaceBucketItem;
import com.qsoftware.forgemod.modules.items.objects.overpowered.UnstableInfinityIngot;
import com.qsoftware.forgemod.modules.items.objects.specials.KillSwitchItem;
import com.qsoftware.forgemod.modules.items.objects.specials.KnifeItem;
import com.qsoftware.forgemod.modules.items.objects.specials.MagnetItem;
import com.qsoftware.forgemod.modules.items.objects.tools.*;
import com.qsoftware.forgemod.modules.items.objects.type.IngotOrDustItem;
import com.qsoftware.forgemod.modules.items.objects.type.IngredientItem;
import com.qsoftware.forgemod.modules.items.objects.type.SliceableItem;
import com.qsoftware.forgemod.modules.items.objects.upgrades.MachineUpgrades;
import com.qsoftware.forgemod.modules.items.objects.wand.*;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import com.qsoftware.forgemod.util.builder.ArmorMaterial;
import com.qsoftware.forgemod.util.builder.ItemTier;
import com.qsoftware.forgemod.util.color.ColorGetter;
import lombok.experimental.UtilityClass;
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

@SuppressWarnings({"unused", "OptionalGetWithoutIsPresent"})
@UtilityClass
public final class ModItems {
    static {
        OreMaterial.registerItems();
        Tools.registerItems();
        CraftingItems.register();
        MachineUpgrades.register();
    }

    public static final ItemRegistryObject<WrenchItem> WRENCH = register("wrench", WrenchItem::new);
    public static final ItemRegistryObject<DebugItem> DEBUG_ITEM = register("debug_item", DebugItem::new);
    public static final ItemRegistryObject<BatteryItem> BATTERY = register("battery", BatteryItem::new);
    public static final ItemRegistryObject<HandPumpItem> HAND_PUMP = register("hand_pump", HandPumpItem::new);
    public static final ItemRegistryObject<CanisterItem> CANISTER = register("canister", () -> new CanisterItem(new Item.Properties().group(ModItemGroups.MISC)));
    public static final ItemRegistryObject<EmptyCanisterItem> EMPTY_CANISTER = register("empty_canister", () -> new EmptyCanisterItem(new Item.Properties().group(ModItemGroups.MISC)));
    public static final ItemRegistryObject<BucketItem> OIL_BUCKET = register("oil_bucket", () -> createBucketItem(() -> ModFluids.OIL));
    public static final ItemRegistryObject<BucketItem> DIESEL_BUCKET = register("diesel_bucket", () -> createBucketItem(() -> ModFluids.DIESEL));
    public static final ItemRegistryObject<NoPlaceBucketItem> ETHANE_BUCKET = register("ethane_bucket", () -> createNoPlaceBucketItem(() -> ModFluids.ETHANE));
    public static final ItemRegistryObject<NoPlaceBucketItem> POLYETHYLENE_BUCKET = register("polyethylene_bucket", () -> createNoPlaceBucketItem(() -> ModFluids.POLYETHYLENE));

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
    public static final ItemRegistryObject<Item> FRIED_EGG = register("fried_egg", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(1).saturation(0.13f).build())));
    public static final ItemRegistryObject<Item> BACON = register("bacon", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(1).saturation(0.13f).effect(() -> new EffectInstance(Effects.HUNGER, 200), 0.8f).build())));
    public static final ItemRegistryObject<Item> COOKED_BACON = register("cooked_bacon", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(5).saturation(0.40f).build())));
    public static final ItemRegistryObject<Item> CHICKEN_LEG = register("chicken_leg", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(1).saturation(0.1f).effect(() -> new EffectInstance(Effects.HUNGER, 400), 0.9f).build())));
    public static final ItemRegistryObject<Item> COOKED_CHICKEN_LEG = register("cooked_chicken_leg", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(4).saturation(0.35f).build())));
    public static final ItemRegistryObject<Item> MEATBALL = register("meatball", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(1).saturation(0.15f).effect(() -> new EffectInstance(Effects.HUNGER, 100), 1f).build())));
    public static final ItemRegistryObject<Item> COOKED_MEATBALL = register("cooked_meatball", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(3).saturation(0.225f).build())));
    public static final ItemRegistryObject<Item> SHOARMA = register("shoarma", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(2).saturation(0.15f).build())));
    public static final ItemRegistryObject<Item> COOKED_SHOARMA = register("cooked_shoarma", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(8).saturation(0.45f).build())));
    public static final ItemRegistryObject<Item> PORK_SHANK = register("pork_shank", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(2).saturation(0.15f).build())));
    public static final ItemRegistryObject<Item> COOKED_PORK_SHANK = register("cooked_pork_shank", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(7).saturation(0.375f).build())));
    public static final ItemRegistryObject<Item> TOMAHAWK = register("tomahawk", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(2).saturation(0.215f).build())));
    public static final ItemRegistryObject<Item> COOKED_TOMAHAWK = register("cooked_tomahawk", () -> new Item(new Item.Properties().group(ModItemGroups.FOOD).food(new Food.Builder().hunger(7).saturation(0.415f).build())));

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
    public static final ItemRegistryObject<Item> DUNGEONS = register("dungeons", () -> new SwordItem(net.minecraft.item.ItemTier.DIAMOND, 0, 0f, new Item.Properties().setNoRepair().maxStackSize(1)));

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
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_DIAMOND_PICKAXE = register("dungeons_diamond_pickaxe", () -> new PickaxeItem(net.minecraft.item.ItemTier.DIAMOND, 3, -2.1f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_DIAMOND_AXE = register("dungeons_diamond_axe", () -> new AxeItem(net.minecraft.item.ItemTier.DIAMOND, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_AXE = register("dungeons_axe", () -> new AxeItem(net.minecraft.item.ItemTier.IRON, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_CURSED_AXE = register("cursed_axe", () -> new AxeItem(net.minecraft.item.ItemTier.GOLD, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_FIREBRAND = register("firebrand", () -> new AxeItem(net.minecraft.item.ItemTier.DIAMOND, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_HIGHLAND_AXE = register("highland_axe", () -> new AxeItem(net.minecraft.item.ItemTier.IRON, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_WHIRLWIND = register("whirlwind", () -> new AxeItem(net.minecraft.item.ItemTier.IRON, 4, -2.2f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_DAGGER = register("dungeons_dagger", () -> new SwordItem(net.minecraft.item.ItemTier.IRON, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_ETERNAL_KNIFE = register("eternal_knife", () -> new SwordItem(net.minecraft.item.ItemTier.DIAMOND, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_FANGS_OF_FROST = register("fangs_of_frost", () -> new SwordItem(net.minecraft.item.ItemTier.IRON, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_MOON_DAGGER = register("moon_dagger", () -> new SwordItem(net.minecraft.item.ItemTier.IRON, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_NIGHTMARES_BITE = register("nightmares_bite", () -> new SwordItem(net.minecraft.item.ItemTier.DIAMOND, 2, -1.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_SPIRIT_KNIFE = register("spirit_knife", () -> new SwordItem(net.minecraft.item.ItemTier.DIAMOND, 2, -1.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_THE_LAST_LAUGH_GOLD = register("the_last_laugh_gold", () -> new SwordItem(net.minecraft.item.ItemTier.GOLD, 3, -0.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_THE_LAST_LAUGH_SILVER = register("the_last_laugh_silver", () -> new SwordItem(net.minecraft.item.ItemTier.IRON, 2, -0.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_DIAMOND_SWORD = register("dungeons_diamond_sword", () -> new SwordItem(net.minecraft.item.ItemTier.DIAMOND, 4, -2.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_IRON_SWORD = register("dungeons_iron_sword", () -> new SwordItem(net.minecraft.item.ItemTier.IRON, 4, -2.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_BROADSWORD = register("dungeons_broadsword", () -> new SwordItem(net.minecraft.item.ItemTier.IRON, 5, -3.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_STORMLANDER = register("stormlander", () -> new PickaxeItem(net.minecraft.item.ItemTier.DIAMOND, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_GREAT_HAMMER = register("great_hammer", () -> new PickaxeItem(net.minecraft.item.ItemTier.IRON, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_HAMMER_OF_GRAVITY = register("hammer_of_gravity", () -> new PickaxeItem(net.minecraft.item.ItemTier.DIAMOND, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_FLAIL = register("flail", () -> new SwordItem(net.minecraft.item.ItemTier.STONE, 5, -3.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_SUNS_GRACE = register("suns_grace", () -> new SwordItem(net.minecraft.item.ItemTier.STONE, 5, -3.0f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<HoeItem> DUNGEONS_FROST_SCYTHE = register("frost_scythe", () -> new HoeItem(net.minecraft.item.ItemTier.STONE, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
        @Override
        public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<ITextComponent> tooltip, @NotNull ITooltipFlag flagIn) {
            tooltip.add(new TranslationTextComponent("desc.qforgemod.mc_dungeons"));
            super.addInformation(stack, worldIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<HoeItem> DUNGEONS_JAILORS_SCYTHE = register("jailors_scythe", () -> new HoeItem(net.minecraft.item.ItemTier.STONE, 6, -2.5f, new Item.Properties().group(ModItemGroups.DUNGEONS)) {
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

    public static final ItemRegistryObject<KnifeItem> KNIFE = register("knife", () -> new KnifeItem(net.minecraft.item.ItemTier.IRON, new Item.Properties().group(ModItemGroups.SPECIALS).maxDamage(4)));
    public static final ItemRegistryObject<MagnetItem> MAGNET = register("magnet", () -> new MagnetItem(new Item.Properties().group(ModItemGroups.SPECIALS).maxDamage(4)));

    // Wands
    public static final ItemRegistryObject<FireWandItem> FIRE_STAFF = register("fire_wand", FireWandItem::new);
    public static final ItemRegistryObject<NatureStaffItem> NATURE_STAFF = register("nature_wand", NatureStaffItem::new);
    public static final ItemRegistryObject<TeleportStaffItem> TELEPORT_STAFF = register("teleport_wand", TeleportStaffItem::new);

    // Stone Level
    public static final ItemRegistryObject<SwordItem> STONE_SWORD_OF_DOOM = register("stone_sword_of_doom", () -> new SwordItem(net.minecraft.item.ItemTier.STONE, 8, -2.0f, new Item.Properties().group(ModItemGroups.SPECIALS).rarity(Rarity.RARE)));

    // Iron Level
    public static final ItemRegistryObject<AxeItem> EMERGENCY_FIRE_AXE = register("emergency_fire_axe", () -> new AxeItem(net.minecraft.item.ItemTier.IRON, 2, -2.55f, new Item.Properties().group(ModItemGroups.SPECIALS).rarity(Rarity.UNCOMMON)));
    public static final ItemRegistryObject<FireSwordItem> FIRE_SWORD = register("fire_sword", () -> new FireSwordItem(net.minecraft.item.ItemTier.IRON, 3, -3.5f, new Item.Properties().group(ModItemGroups.SPECIALS).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<EnderSwordItem> ENDER_SWORD = register("ender_sword", () -> new EnderSwordItem(net.minecraft.item.ItemTier.IRON, 3, -1.9f, new Item.Properties().group(ModItemGroups.SPECIALS).rarity(Rarity.EPIC)));

    // Diamond Level
    public static final ItemRegistryObject<AxeItem> LEVIATHAN_AXE = register("leviathan_axe", () -> new AxeItem(net.minecraft.item.ItemTier.DIAMOND, 5, -2.55f, new Item.Properties().group(ModItemGroups.SPECIALS)));
    public static final ItemRegistryObject<AxeItem> ADAMANTANIUM_AXE_RED = register("adamantanium_axe_red", () -> new AxeItem(net.minecraft.item.ItemTier.DIAMOND, 5, -1.875f, new Item.Properties().group(ModItemGroups.SPECIALS)));
    public static final ItemRegistryObject<SwordItem> DIAMOND_QUARTZ_SWORD = register("diamond_quartz_sword", () -> new SwordItem(net.minecraft.item.ItemTier.DIAMOND, 6, -2.0f, new Item.Properties().group(ModItemGroups.SPECIALS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Tools     //
    ///////////////////

    /////////////////////////////
    //     Armor materials     //
    /////////////////////////////
    public static final IArmorMaterial copperArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":copper")
            .maxDamageFactor(13)
            .damageReduction(new int[]{2, 5, 6, 2})
            .enchantability(10)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.COPPER.getIngot().get()))
            .build();
    public static final IArmorMaterial tinArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":tin")
            .maxDamageFactor(15)
            .damageReduction(new int[]{2, 4, 5, 2})
            .enchantability(8)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.TIN.getIngot().get()))
            .build();
    public static final IArmorMaterial silverArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":silver")
            .maxDamageFactor(15)
            .damageReduction(new int[]{2, 5, 6, 2})
            .enchantability(48)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.SILVER.getIngot().get()))
            .build();
    public static final IArmorMaterial enderiumArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":enderium")
            .maxDamageFactor(34)
            .damageReduction(new int[]{4, 10, 13, 7})
            .enchantability(41)
            .knockbackResistance(3f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ENDERIUM.getIngot().get()))
            .build();
    public static final IArmorMaterial leadArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":lead")
            .maxDamageFactor(34)
            .damageReduction(new int[]{4, 10, 13, 7})
            .enchantability(41)
            .knockbackResistance(3f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.LEAD.getIngot().get()))
            .build();
    public static final IArmorMaterial zincArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":zinc")
            .maxDamageFactor(34)
            .damageReduction(new int[]{4, 10, 13, 7})
            .enchantability(41)
            .knockbackResistance(3f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ZINC.getIngot().get()))
            .build();
    public static final IArmorMaterial electrumArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":electrum")
            .maxDamageFactor(34)
            .damageReduction(new int[]{4, 10, 13, 7})
            .enchantability(41)
            .knockbackResistance(3f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ELECTRUM.getIngot().get()))
            .build();
    public static final IArmorMaterial lumiumArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":lumium")
            .maxDamageFactor(34)
            .damageReduction(new int[]{4, 10, 13, 7})
            .enchantability(41)
            .knockbackResistance(3f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.LUMIUM.getIngot().get()))
            .build();
    public static final IArmorMaterial redstoneArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":redstone")
            .maxDamageFactor(8)
            .damageReduction(new int[]{1, 4, 3, 2})
            .enchantability(5)
            .knockbackResistance(0.2f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(0.5F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.REDSTONE_ALLOY.getIngot().get()))
            .build();
    public static final IArmorMaterial nickelArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":nickel")
            .maxDamageFactor(13)
            .damageReduction(new int[]{2, 5, 7, 3})
            .enchantability(16)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.NICKEL.getIngot().get()))
            .build();
    public static final IArmorMaterial steelArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":steel")
            .maxDamageFactor(24)
            .damageReduction(new int[]{3, 6, 8, 4})
            .enchantability(14)
            .knockbackResistance(4f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(1.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.STEEL.getIngot().get()))
            .build();
    public static final IArmorMaterial tungstenArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":tungsten")
            .maxDamageFactor(42)
            .damageReduction(new int[]{4, 8, 12, 6})
            .enchantability(28)
            .knockbackResistance(5f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(3.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.TUNGSTEN_INGOT.get()))
            .build();
    public static final IArmorMaterial uraniumArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":uranium")
            .maxDamageFactor(11)
            .damageReduction(new int[]{2, 4, 5, 2})
            .enchantability(4)
            .knockbackResistance(0.5f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(3.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.URANIUM.getIngot().get()))
            .build();
    public static final IArmorMaterial platinumArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":platinum")
            .maxDamageFactor(46)
            .damageReduction(new int[]{3, 6, 8, 4})
            .enchantability(4)
            .knockbackResistance(2f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_IRON)
            .toughness(3.0F)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.PLATINUM.getIngot().get()))
            .build();
    public static final IArmorMaterial rubyArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":ruby")
            .maxDamageFactor(24)
            .damageReduction(new int[]{3, 6, 8, 4})
            .enchantability(14)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(2.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.RUBY.get()))
            .build();
    public static final IArmorMaterial amethystArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":amethyst")
            .maxDamageFactor(21)
            .damageReduction(new int[]{2, 5, 7, 3})
            .enchantability(31)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(2.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.AMETHYST.get()))
            .build();
    public static final IArmorMaterial aquamarineArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":aquamarine")
            .maxDamageFactor(21)
            .damageReduction(new int[]{2, 4, 6, 2})
            .enchantability(31)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(2.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.AQUAMARINE.get()))
            .build();
    public static final IArmorMaterial saphireArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":saphire")
            .maxDamageFactor(21)
            .damageReduction(new int[]{2, 4, 6, 2})
            .enchantability(31)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(2.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.SAPHIRE.get()))
            .build();
    public static final IArmorMaterial malachiteArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":malachite")
            .maxDamageFactor(21)
            .damageReduction(new int[]{2, 4, 6, 2})
            .enchantability(31)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(2.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.MALACHITE.get()))
            .build();
    public static final IArmorMaterial topazArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":topaz")
            .maxDamageFactor(21)
            .damageReduction(new int[]{2, 4, 6, 2})
            .enchantability(31)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(2.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.TOPAZ.get()))
            .build();
    public static final IArmorMaterial amberArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":amber")
            .maxDamageFactor(21)
            .damageReduction(new int[]{2, 4, 6, 2})
            .enchantability(31)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(2.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.AMBER.get()))
            .build();
    public static final IArmorMaterial berylArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":beryl")
            .maxDamageFactor(21)
            .damageReduction(new int[]{2, 4, 6, 2})
            .enchantability(31)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(2.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.BERYL.get()))
            .build();
    public static final IArmorMaterial tanzaniteArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":tanzanite")
            .maxDamageFactor(19)
            .damageReduction(new int[]{3, 6, 8, 3})
            .enchantability(48)
            .knockbackResistance(1f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND)
            .toughness(2.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.TANZANITE.get()))
            .build();
    public static final IArmorMaterial obsidianArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":obsidian")
            .maxDamageFactor(175)
            .damageReduction(new int[]{9, 18, 24, 7})
            .enchantability(19)
            .knockbackResistance(9f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
            .toughness(0.0F)
            .repairMaterial(() -> Ingredient.fromItems(Items.OBSIDIAN))
            .build();
    public static final IArmorMaterial ultriniumArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":ultrinium")
            .maxDamageFactor(95250)
            .damageReduction(new int[]{2375, 5643, 6485, 1947})
            .enchantability(375)
            .knockbackResistance(3854f)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
            .toughness(290.0F)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.ULTRINIUM_INGOT.get()))
            .build();
    public static final IArmorMaterial infinityArmorMaterial = ArmorMaterial.builder()
            .name(QForgeMod.modId + ":infinity")
            .maxDamageFactor((int) Double.POSITIVE_INFINITY)
            .damageReduction(new int[]{(int) Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY, (int) Double.POSITIVE_INFINITY})
            .enchantability((int) Double.POSITIVE_INFINITY)
            .knockbackResistance(Float.POSITIVE_INFINITY)
            .sound(SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE)
            .toughness(0)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.INFINITY_INGOT.get()))
            .build();

    ////////////////////////
    //     Item tiers     //
    ////////////////////////
    public static final IItemTier COPPER_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(420).efficiency(5.3f).attackDamage(1.4f).enchantability(10)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.COPPER.getIngot().orElseThrow(() -> new NullPointerException("Copper ingot not found in OreMaterial class.")))).build();
    public static final IItemTier TIN_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(630).efficiency(5.8f).attackDamage(1.8f).enchantability(8)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.TIN.getIngot().orElseThrow(() -> new NullPointerException("Tin ingot not found in OreMaterial class.")))).build();
    public static final IItemTier SILVER_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(580).efficiency(5.4f).attackDamage(1.7f).enchantability(48)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.SILVER.getIngot().orElseThrow(() -> new NullPointerException("Silver ingot not found in OreMaterial class.")))).build();
    public static final IItemTier ENDERIUM_ITEM_TIER = ItemTier.builder()
            .tier(4).maxUses(2185).efficiency(9.3f).attackDamage(7.5f).enchantability(41)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ENDERIUM.getIngot().orElseThrow(() -> new NullPointerException("Enderium ingot not found in OreMaterial class.")))).build();
    public static final IItemTier LEAD_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(780).efficiency(9.5f).attackDamage(5.3f).enchantability(4)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.LEAD.getIngot().orElseThrow(() -> new NullPointerException("Lead ingot not found in OreMaterial class.")))).build();
    public static final IItemTier ZINC_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(780).efficiency(9.5f).attackDamage(5.3f).enchantability(4)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ZINC.getIngot().orElseThrow(() -> new NullPointerException("Zinc ingot not found in OreMaterial class.")))).build();
    public static final IItemTier ELECTRUM_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(780).efficiency(9.5f).attackDamage(5.3f).enchantability(4)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.ELECTRUM.getIngot().orElseThrow(() -> new NullPointerException("Electrum ingot not found in OreMaterial class.")))).build();
    public static final IItemTier LUMIUM_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(780).efficiency(9.5f).attackDamage(5.3f).enchantability(4)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.LUMIUM.getIngot().orElseThrow(() -> new NullPointerException("Lumium ingot not found in OreMaterial class.")))).build();
    public static final IItemTier REDSTONE_ITEM_TIER = ItemTier.builder()
            .tier(0).maxUses(230).efficiency(2.3f).attackDamage(1.2f).enchantability(7)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.REDSTONE_ALLOY.getIngot().orElseThrow(() -> new NullPointerException("Redstone Alloy ingot not found in OreMaterial class.")))).build();
    public static final IItemTier NICKLE_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(480).efficiency(4.9f).attackDamage(1.7f).enchantability(16)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.NICKEL.getIngot().orElseThrow(() -> new NullPointerException("Nickel ingot not found in OreMaterial class.")))).build();
    public static final IItemTier STEEL_ITEM_TIER = ItemTier.builder()
            .tier(3).maxUses(1465).efficiency(8.1f).attackDamage(3.8f).enchantability(14)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.STEEL.getIngot().orElseThrow(() -> new NullPointerException("Steel ingot not found in OreMaterial class.")))).build();
    public static final IItemTier TUNGSTEN_ITEM_TIER = ItemTier.builder()
            .tier(4).maxUses(2995).efficiency(9.4f).attackDamage(4.7f).enchantability(28)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.TUNGSTEN_INGOT)).build();
    public static final IItemTier URANIUM_ITEM_TIER = ItemTier.builder()
            .tier(3).maxUses(820).efficiency(3.6f).attackDamage(6.3f).enchantability(4)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.URANIUM.getIngot().orElseThrow(() -> new NullPointerException("Uranium ingot not found in OreMaterial class.")))).build();
    public static final IItemTier PLATINUM_ITEM_TIER = ItemTier.builder()
            .tier(4).maxUses(1390).efficiency(7.5f).attackDamage(4f).enchantability(12)
            .repairMaterial(() -> Ingredient.fromItems(OreMaterial.PLATINUM.getIngot().orElseThrow(() -> new NullPointerException("Platinum ingot not found in OreMaterial class.")))).build();
    public static final IItemTier RUBY_ITEM_TIER = ItemTier.builder()
            .tier(3).maxUses(970).efficiency(7.6f).attackDamage(3.6f).enchantability(13)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.RUBY)).build();
    public static final IItemTier AMETHYST_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(650).efficiency(7.3f).attackDamage(3.1f).enchantability(31)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.AMETHYST)).build();
    public static final IItemTier AQUAMARINE_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(740).efficiency(5.3f).attackDamage(2.6f).enchantability(23)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.AQUAMARINE)).build();
    public static final IItemTier SAPHIRE_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(810).efficiency(5.2f).attackDamage(2.5f).enchantability(29)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.SAPHIRE)).build();
    public static final IItemTier MALACHITE_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(670).efficiency(4.3f).attackDamage(3.2f).enchantability(12)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.MALACHITE)).build();
    public static final IItemTier TOPAZ_ITEM_TIER = ItemTier.builder()
            .tier(1).maxUses(665).efficiency(4.4f).attackDamage(3.9f).enchantability(17)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.TOPAZ)).build();
    public static final IItemTier AMBER_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(670).efficiency(3.9f).attackDamage(3.1f).enchantability(16)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.AMBER)).build();
    public static final IItemTier BERYL_ITEM_TIER = ItemTier.builder()
            .tier(2).maxUses(730).efficiency(4.8f).attackDamage(3.5f).enchantability(11)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.BERYL)).build();
    public static final IItemTier TANZANITE_ITEM_TIER = ItemTier.builder()
            .tier(3).maxUses(1090).efficiency(7.7f).attackDamage(3.5f).enchantability(48)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.TANZANITE)).build();
    public static final IItemTier OBSIDIAN_ITEM_TIER = ItemTier.builder()
            .tier(5).maxUses(3950).efficiency(53.7f).attackDamage(12.5f).enchantability(19)
            .repairMaterial(() -> Ingredient.fromItems(Items.OBSIDIAN)).build();
    public static final IItemTier ULTRINIUM_ITEM_TIER = ItemTier.builder()
            .tier(6).maxUses(952530).efficiency(7403.0f).attackDamage(237.4f).enchantability(375)
            .repairMaterial(() -> Ingredient.fromItems(ModItems.ULTRINIUM_INGOT)).build();
    public static final IItemTier INFINITY = new ItemTier(7, (int) Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, (int) Float.POSITIVE_INFINITY,
            () -> Ingredient.fromItems(ModItems.INFINITY_INGOT)
    );

    // Tools - Vanilla
    public static final ItemRegistryObject<AxeItem> WOODEN_BATTLEAXE = register("wooden_battleaxe", () -> new AxeItem(net.minecraft.item.ItemTier.WOOD, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> STONE_BATTLEAXE = register("stone_battleaxe", () -> new AxeItem(net.minecraft.item.ItemTier.STONE, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> GOLDEN_BATTLEAXE = register("golden_battleaxe", () -> new AxeItem(net.minecraft.item.ItemTier.GOLD, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> IRON_BATTLEAXE = register("iron_battleaxe", () -> new AxeItem(net.minecraft.item.ItemTier.IRON, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> DIAMOND_BATTLEAXE = register("diamond_battleaxe", () -> new AxeItem(net.minecraft.item.ItemTier.DIAMOND, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> NETHERITE_BATTLEAXE = register("netherite_battleaxe", () -> new AxeItem(net.minecraft.item.ItemTier.NETHERITE, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));

//    // Armors - Copper
//    public static final ItemRegistryObject<ArmorItem> COPPER_HELMET = register("copper_helmet", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> COPPER_CHESTPLATE = register("copper_chestplate", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> COPPER_LEGGINGS = register("copper_leggings", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> COPPER_BOOTS = register("copper_boots", () -> new ArmorItem(copperArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> COPPER_SWORD = register("copper_sword", () -> new SwordItem(COPPER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> COPPER_PICKAXE = register("copper_pickaxe", () -> new PickaxeItem(COPPER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> COPPER_SHOVEL = register("copper_shovel", () -> new ShovelItem(COPPER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> COPPER_AXE = register("copper_axe", () -> new AxeItem(COPPER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> COPPER_BATTLEAXE = register("copper_battleaxe", () -> new AxeItem(COPPER_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> COPPER_HOE = register("copper_hoe", () -> new HoeItem(COPPER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//
//    // Armors - Tin
//    public static final ItemRegistryObject<ArmorItem> TIN_HELMET = register("tin_helmet", () -> new ArmorItem(tinArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> TIN_CHESTPLATE = register("tin_chestplate", () -> new ArmorItem(tinArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> TIN_LEGGINGS = register("tin_leggings", () -> new ArmorItem(tinArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> TIN_BOOTS = register("tin_boots", () -> new ArmorItem(tinArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> TIN_SWORD = register("tin_sword", () -> new SwordItem(TIN_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> TIN_PICKAXE = register("tin_pickaxe", () -> new PickaxeItem(TIN_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> TIN_SHOVEL = register("tin_shovel", () -> new ShovelItem(TIN_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> TIN_AXE = register("tin_axe", () -> new AxeItem(TIN_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> TIN_BATTLEAXE = register("tin_battleaxe", () -> new AxeItem(TIN_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> TIN_HOE = register("tin_hoe", () -> new HoeItem(TIN_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Silver
    public static final ItemRegistryObject<ArmorItem> SILVER_HELMET = register("silver_helmet", () -> new SilverArmorItem(silverArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> SILVER_CHESTPLATE = register("silver_chestplate", () -> new SilverArmorItem(silverArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> SILVER_LEGGINGS = register("silver_leggings", () -> new SilverArmorItem(silverArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> SILVER_BOOTS = register("silver_boots", () -> new SilverArmorItem(silverArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> SILVER_SWORD = register("silver_sword", () -> new SilverSwordItem(SILVER_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> SILVER_PICKAXE = register("silver_pickaxe", () -> new SilverPickaxeItem(SILVER_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> SILVER_SHOVEL = register("silver_shovel", () -> new SilverShovelItem(SILVER_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> SILVER_AXE = register("silver_axe", () -> new SilverAxeItem(SILVER_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> SILVER_BATTLEAXE = register("silver_battleaxe", () -> new SilverAxeItem(SILVER_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> SILVER_HOE = register("silver_hoe", () -> new SilverHoeItem(SILVER_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

//    // Armors - Enderium
//    public static final ItemRegistryObject<ArmorItem> ENDERIUM_HELMET = register("enderium_helmet", () -> new ArmorItem(enderiumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> ENDERIUM_CHESTPLATE = register("enderium_chestplate", () -> new ArmorItem(enderiumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> ENDERIUM_LEGGINGS = register("enderium_leggings", () -> new ArmorItem(enderiumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> ENDERIUM_BOOTS = register("enderium_boots", () -> new ArmorItem(enderiumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> ENDERIUM_SWORD = register("enderium_sword", () -> new SwordItem(ENDERIUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> ENDERIUM_PICKAXE = register("enderium_pickaxe", () -> new PickaxeItem(ENDERIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> ENDERIUM_SHOVEL = register("enderium_shovel", () -> new ShovelItem(ENDERIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> ENDERIUM_AXE = register("enderium_axe", () -> new AxeItem(ENDERIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> ENDERIUM_BATTLEAXE = register("enderium_battleaxe", () -> new AxeItem(ENDERIUM_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> ENDERIUM_HOE = register("enderium_hoe", () -> new HoeItem(ENDERIUM_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//
//    // Armors - Lead
//    public static final ItemRegistryObject<ArmorItem> LEAD_HELMET = register("lead_helmet", () -> new ArmorItem(leadArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> LEAD_CHESTPLATE = register("lead_chestplate", () -> new ArmorItem(leadArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> LEAD_LEGGINGS = register("lead_leggings", () -> new ArmorItem(leadArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> LEAD_BOOTS = register("lead_boots", () -> new ArmorItem(leadArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> LEAD_SWORD = register("lead_sword", () -> new SwordItem(LEAD_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> LEAD_PICKAXE = register("lead_pickaxe", () -> new PickaxeItem(LEAD_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> LEAD_SHOVEL = register("lead_shovel", () -> new ShovelItem(LEAD_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> LEAD_AXE = register("lead_axe", () -> new AxeItem(LEAD_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> LEAD_BATTLEAXE = register("lead_battleaxe", () -> new AxeItem(LEAD_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> LEAD_HOE = register("lead_hoe", () -> new HoeItem(LEAD_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//
//    // Armors - Electrum
//    public static final ItemRegistryObject<ArmorItem> ELECTRUM_HELMET = register("electrum_helmet", () -> new ArmorItem(electrumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> ELECTRUM_CHESTPLATE = register("electrum_chestplate", () -> new ArmorItem(electrumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> ELECTRUM_LEGGINGS = register("electrum_leggings", () -> new ArmorItem(electrumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> ELECTRUM_BOOTS = register("electrum_boots", () -> new ArmorItem(electrumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> ELECTRUM_SWORD = register("electrum_sword", () -> new SwordItem(ELECTRUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> ELECTRUM_PICKAXE = register("electrum_pickaxe", () -> new PickaxeItem(ELECTRUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> ELECTRUM_SHOVEL = register("electrum_shovel", () -> new ShovelItem(ELECTRUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> ELECTRUM_AXE = register("electrum_axe", () -> new AxeItem(ELECTRUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> ELECTRUM_BATTLEAXE = register("electrum_battleaxe", () -> new AxeItem(ELECTRUM_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> ELECTRUM_HOE = register("electrum_hoe", () -> new HoeItem(ELECTRUM_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//
//    // Armors - Lumium
//    public static final ItemRegistryObject<ArmorItem> LUMIUM_HELMET = register("lumium_helmet", () -> new ArmorItem(lumiumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> LUMIUM_CHESTPLATE = register("lumium_chestplate", () -> new ArmorItem(lumiumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> LUMIUM_LEGGINGS = register("lumium_leggings", () -> new ArmorItem(lumiumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> LUMIUM_BOOTS = register("lumium_boots", () -> new ArmorItem(lumiumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> LUMIUM_SWORD = register("lumium_sword", () -> new SwordItem(LUMIUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> LUMIUM_PICKAXE = register("lumium_pickaxe", () -> new PickaxeItem(LUMIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> LUMIUM_SHOVEL = register("lumium_shovel", () -> new ShovelItem(LUMIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> LUMIUM_AXE = register("lumium_axe", () -> new AxeItem(LUMIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> LUMIUM_BATTLEAXE = register("lumium_battleaxe", () -> new AxeItem(LUMIUM_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> LUMIUM_HOE = register("lumium_hoe", () -> new HoeItem(LUMIUM_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//
//    // Armors - Redstone
//    public static final ItemRegistryObject<ArmorItem> REDSTONE_HELMET = register("redstone_helmet", () -> new ArmorItem(redstoneArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> REDSTONE_CHESTPLATE = register("redstone_chestplate", () -> new ArmorItem(redstoneArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> REDSTONE_LEGGINGS = register("redstone_leggings", () -> new ArmorItem(redstoneArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> REDSTONE_BOOTS = register("redstone_boots", () -> new ArmorItem(redstoneArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> REDSTONE_SWORD = register("redstone_sword", () -> new SwordItem(REDSTONE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> REDSTONE_PICKAXE = register("redstone_pickaxe", () -> new PickaxeItem(REDSTONE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> REDSTONE_SHOVEL = register("redstone_shovel", () -> new ShovelItem(REDSTONE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> REDSTONE_AXE = register("redstone_axe", () -> new AxeItem(REDSTONE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> REDSTONE_BATTLEAXE = register("redstone_battleaxe", () -> new AxeItem(REDSTONE_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> REDSTONE_HOE = register("redstone_hoe", () -> new HoeItem(REDSTONE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//
//    // Armors - Nickle
//    public static final ItemRegistryObject<ArmorItem> NICKLE_HELMET = register("nickel_helmet", () -> new ArmorItem(nickelArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> NICKLE_CHESTPLATE = register("nickel_chestplate", () -> new ArmorItem(nickelArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> NICKLE_LEGGINGS = register("nickel_leggings", () -> new ArmorItem(nickelArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> NICKLE_BOOTS = register("nickel_boots", () -> new ArmorItem(nickelArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> NICKLE_SWORD = register("nickel_sword", () -> new SwordItem(NICKLE_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> NICKLE_PICKAXE = register("nickel_pickaxe", () -> new PickaxeItem(NICKLE_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> NICKLE_SHOVEL = register("nickel_shovel", () -> new ShovelItem(NICKLE_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> NICKLE_AXE = register("nickel_axe", () -> new AxeItem(NICKLE_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> NICKLE_BATTLEAXE = register("nickel_battleaxe", () -> new AxeItem(NICKLE_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> NICKLE_HOE = register("nickel_hoe", () -> new HoeItem(NICKLE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//
//    // Armors - Steel
//    public static final ItemRegistryObject<ArmorItem> STEEL_HELMET = register("steel_helmet", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> STEEL_CHESTPLATE = register("steel_chestplate", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> STEEL_LEGGINGS = register("steel_leggings", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> STEEL_BOOTS = register("steel_boots", () -> new ArmorItem(steelArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> STEEL_SWORD = register("steel_sword", () -> new SwordItem(STEEL_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> STEEL_PICKAXE = register("steel_pickaxe", () -> new PickaxeItem(STEEL_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> STEEL_SHOVEL = register("steel_shovel", () -> new ShovelItem(STEEL_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> STEEL_AXE = register("steel_axe", () -> new AxeItem(STEEL_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> STEEL_BATTLEAXE = register("steel_battleaxe", () -> new AxeItem(STEEL_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> STEEL_HOE = register("steel_hoe", () -> new HoeItem(STEEL_ITEM_TIER, 2, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//
//    // Armors - Tungsten Steel
//    public static final ItemRegistryObject<ArmorItem> TUNGSTEN_HELMET = register("tungsten_helmet", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> TUNGSTEN_CHESTPLATE = register("tungsten_chestplate", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> TUNGSTEN_LEGGINGS = register("tungsten_leggings", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> TUNGSTEN_BOOTS = register("tungsten_boots", () -> new ArmorItem(tungstenArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> TUNGSTEN_SWORD = register("tungsten_sword", () -> new SwordItem(TUNGSTEN_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> TUNGSTEN_PICKAXE = register("tungsten_pickaxe", () -> new PickaxeItem(TUNGSTEN_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> TUNGSTEN_SHOVEL = register("tungsten_shovel", () -> new ShovelItem(TUNGSTEN_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> TUNGSTEN_AXE = register("tungsten_axe", () -> new AxeItem(TUNGSTEN_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> TUNGSTEN_BATTLEAXE = register("tungsten_battleaxe", () -> new AxeItem(TUNGSTEN_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> TUNGSTEN_HOE = register("tungsten_hoe", () -> new HoeItem(TUNGSTEN_ITEM_TIER, 2, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Uranium
    public static final ItemRegistryObject<ArmorItem> URANIUM_HELMET = register("uranium_helmet", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> URANIUM_CHESTPLATE = register("uranium_chestplate", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> URANIUM_LEGGINGS = register("uranium_leggings", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> URANIUM_BOOTS = register("uranium_boots", () -> new ArmorItem(uraniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<UraniumSwordItem> URANIUM_SWORD = register("uranium_sword", () -> new UraniumSwordItem(URANIUM_ITEM_TIER, 3, -2.0f));
    public static final ItemRegistryObject<PickaxeItem> URANIUM_PICKAXE = register("uranium_pickaxe", () -> new UraniumPickaxeItem(URANIUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> URANIUM_SHOVEL = register("uranium_shovel", () -> new UraniumShovelItem(URANIUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> URANIUM_AXE = register("uranium_axe", () -> new UraniumAxeItem(URANIUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> URANIUM_BATTLEAXE = register("uranium_battleaxe", () -> new UraniumAxeItem(URANIUM_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> URANIUM_HOE = register("uranium_hoe", () -> new UraniumHoeItem(URANIUM_ITEM_TIER, 2, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

//    // Armors - Platinum
//    public static final ItemRegistryObject<ArmorItem> PLATINUM_HELMET = register("platinum_helmet", () -> new ArmorItem(platinumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> PLATINUM_CHESTPLATE = register("platinum_chestplate", () -> new ArmorItem(platinumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> PLATINUM_LEGGINGS = register("platinum_leggings", () -> new ArmorItem(platinumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ArmorItem> PLATINUM_BOOTS = register("platinum_boots", () -> new ArmorItem(platinumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<SwordItem> PLATINUM_SWORD = register("platinum_sword", () -> new SwordItem(PLATINUM_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<PickaxeItem> PLATINUM_PICKAXE = register("platinum_pickaxe", () -> new PickaxeItem(PLATINUM_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<ShovelItem> PLATINUM_SHOVEL = register("platinum_shovel", () -> new ShovelItem(PLATINUM_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> PLATINUM_AXE = register("platinum_axe", () -> new AxeItem(PLATINUM_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<AxeItem> PLATINUM_BATTLEAXE = register("platinum_battleaxe", () -> new AxeItem(PLATINUM_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
//    public static final ItemRegistryObject<HoeItem> PLATINUM_HOE = register("platinum_hoe", () -> new HoeItem(PLATINUM_ITEM_TIER, 2, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

    // Armors - Ruby
    public static final ItemRegistryObject<ArmorItem> RUBY_HELMET = register("ruby_helmet", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> RUBY_CHESTPLATE = register("ruby_chestplate", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> RUBY_LEGGINGS = register("ruby_leggings", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ArmorItem> RUBY_BOOTS = register("ruby_boots", () -> new ArmorItem(rubyArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<SwordItem> RUBY_SWORD = register("ruby_sword", () -> new SwordItem(RUBY_ITEM_TIER, 3, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<PickaxeItem> RUBY_PICKAXE = register("ruby_pickaxe", () -> new PickaxeItem(RUBY_ITEM_TIER, 1, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<ShovelItem> RUBY_SHOVEL = register("ruby_shovel", () -> new ShovelItem(RUBY_ITEM_TIER, 1.5F, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> RUBY_AXE = register("ruby_axe", () -> new AxeItem(RUBY_ITEM_TIER, 6.0F, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<AxeItem> RUBY_BATTLEAXE = register("ruby_battleaxe", () -> new AxeItem(RUBY_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
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
    public static final ItemRegistryObject<AxeItem> AMETHYST_BATTLEAXE = register("amethyst_battleaxe", () -> new AxeItem(AMETHYST_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
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
    public static final ItemRegistryObject<AxeItem> AQUAMARINE_BATTLEAXE = register("aquamarine_battleaxe", () -> new AxeItem(AQUAMARINE_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
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
    public static final ItemRegistryObject<AxeItem> SAPHIRE_BATTLEAXE = register("saphire_battleaxe", () -> new AxeItem(SAPHIRE_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
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
    public static final ItemRegistryObject<AxeItem> MALACHITE_BATTLEAXE = register("malachite_battleaxe", () -> new AxeItem(MALACHITE_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
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
    public static final ItemRegistryObject<AxeItem> TOPAZ_BATTLEAXE = register("topaz_battleaxe", () -> new AxeItem(TOPAZ_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
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
    public static final ItemRegistryObject<AxeItem> AMBER_BATTLEAXE = register("amber_battleaxe", () -> new AxeItem(AMBER_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
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
    public static final ItemRegistryObject<AxeItem> BERYL_BATTLEAXE = register("beryl_battleaxe", () -> new AxeItem(BERYL_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
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
    public static final ItemRegistryObject<AxeItem> TANZANITE_BATTLEAXE = register("tanzanite_battleaxe", () -> new AxeItem(TANZANITE_ITEM_TIER, 7.0F, -2.2f, new Item.Properties().group(ModItemGroups.TOOLS)));
    public static final ItemRegistryObject<HoeItem> TANZANITE_HOE = register("tanzanite_hoe", () -> new HoeItem(TANZANITE_ITEM_TIER, 1, -2.0f, new Item.Properties().group(ModItemGroups.TOOLS)));

//    // Armors - Obsidian
//    public static final ItemRegistryObject<ArmorItem> OBSIDIAN_HELMET = register("obsidian_helmet", () -> new ArmorItem(obsidianArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
//    public static final ItemRegistryObject<ArmorItem> OBSIDIAN_CHESTPLATE = register("obsidian_chestplate", () -> new ArmorItem(obsidianArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
//    public static final ItemRegistryObject<ArmorItem> OBSIDIAN_LEGGINGS = register("obsidian_leggings", () -> new ArmorItem(obsidianArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
//    public static final ItemRegistryObject<ArmorItem> OBSIDIAN_BOOTS = register("obsidian_boots", () -> new ArmorItem(obsidianArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
//    public static final ItemRegistryObject<SwordItem> OBSIDIAN_SWORD = register("obsidian_sword", () -> new SwordItem(OBSIDIAN_ITEM_TIER, 3, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
//    public static final ItemRegistryObject<PickaxeItem> OBSIDIAN_PICKAXE = register("obsidian_pickaxe", () -> new PickaxeItem(OBSIDIAN_ITEM_TIER, 1, -2.9f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
//    public static final ItemRegistryObject<ShovelItem> OBSIDIAN_SHOVEL = register("obsidian_shovel", () -> new ShovelItem(OBSIDIAN_ITEM_TIER, 1.5F, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
//    public static final ItemRegistryObject<AxeItem> OBSIDIAN_AXE = register("obsidian_axe", () -> new AxeItem(OBSIDIAN_ITEM_TIER, 7.0F, -3.1f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
//    public static final ItemRegistryObject<AxeItem> OBSIDIAN_BATTLEAXE = register("obsidian_battleaxe", () -> new AxeItem(OBSIDIAN_ITEM_TIER, 10.0F, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
//    public static final ItemRegistryObject<HoeItem> OBSIDIAN_HOE = register("obsidian_hoe", () -> new HoeItem(OBSIDIAN_ITEM_TIER, 2, -2.9f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));

    // Armors - Ultrinium
    public static final ItemRegistryObject<ArmorItem> ULTRINIUM_HELMET = register("ultrinium_helmet", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ArmorItem> ULTRINIUM_CHESTPLATE = register("ultrinium_chestplate", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ArmorItem> ULTRINIUM_LEGGINGS = register("ultrinium_leggings", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ArmorItem> ULTRINIUM_BOOTS = register("ultrinium_boots", () -> new ArmorItem(ultriniumArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<SwordItem> ULTRINIUM_SWORD = register("ultrinium_sword", () -> new SwordItem(ULTRINIUM_ITEM_TIER, 3, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<PickaxeItem> ULTRINIUM_PICKAXE = register("ultrinium_pickaxe", () -> new PickaxeItem(ULTRINIUM_ITEM_TIER, 1, -3.2f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<ShovelItem> ULTRINIUM_SHOVEL = register("ultrinium_shovel", () -> new ShovelItem(ULTRINIUM_ITEM_TIER, 1.5F, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<AxeItem> ULTRINIUM_AXE = register("ultrinium_axe", () -> new AxeItem(ULTRINIUM_ITEM_TIER, 6.0F, -3.5f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<AxeItem> ULTRINIUM_BATTLEAXE = register("ultrinium_battleaxe", () -> new AxeItem(ULTRINIUM_ITEM_TIER, 9.0F, -3.2f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));
    public static final ItemRegistryObject<HoeItem> ULTRINIUM_HOE = register("ultrinium_hoe", () -> new HoeItem(ULTRINIUM_ITEM_TIER, 23, -3.2f, new Item.Properties().group(ModItemGroups.TOOLS).rarity(Rarity.RARE)));

    // Armors - Infinity
    public static final ItemRegistryObject<ArmorItem> INFINITY_HELMET = register("infinity_helmet", () -> new InfinityArmorItem(infinityArmorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<ArmorItem> INFINITY_CHESTPLATE = register("infinity_chestplate", () -> new InfinityArmorItem(infinityArmorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<ArmorItem> INFINITY_LEGGINGS = register("infinity_leggings", () -> new InfinityArmorItem(infinityArmorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<ArmorItem> INFINITY_BOOTS = register("infinity_boots", () -> new InfinityArmorItem(infinityArmorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC)));
    public static final ItemRegistryObject<InfinitySwordItem> INFINITY_SWORD = register("infinity_sword", InfinitySwordItem::new);
    public static final ItemRegistryObject<InfinityPickaxeItem> INFINITY_PICKAXE = register("infinity_pickaxe", InfinityPickaxeItem::new);
    public static final ItemRegistryObject<InfinityShovelItem> INFINITY_SHOVEL = register("infinity_shovel", InfinityShovelItem::new);
    public static final ItemRegistryObject<InfinityAxeItem> INFINITY_AXE = register("infinity_axe", InfinityAxeItem::new);
    public static final ItemRegistryObject<InfinityAxeItem> INFINITY_BATTLEAXE = register("infinity_battleaxe", InfinityAxeItem::new);
    public static final ItemRegistryObject<InfinityHoeItem> INFINITY_HOE = register("infinity_hoe", InfinityHoeItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Utility Methods     //
    /////////////////////////////

    public static void register() {
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
        return ItemsModule.ITEMS.register(name, item);
    }
}
