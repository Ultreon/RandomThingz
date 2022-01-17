package com.ultreon.randomthingz.common.item;

import com.ultreon.modlib.embedded.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.block.fluid.common.ModFluids;
import com.ultreon.randomthingz.item.*;
import com.ultreon.randomthingz.item.magic.LightningStaffItem;
import com.ultreon.randomthingz.item.magic.NatureStaffItem;
import com.ultreon.randomthingz.item.magic.TeleportStaffItem;
import com.ultreon.randomthingz.item.magic.WalkingStaffItem;
import com.ultreon.randomthingz.item.tool.*;
import com.ultreon.randomthingz.item.tool.trait.AbstractTrait;
import com.ultreon.randomthingz.item.tool.types.HammerItem;
import com.ultreon.randomthingz.item.type.IngotOrDustItem;
import com.ultreon.randomthingz.item.type.IngredientItem;
import com.ultreon.randomthingz.item.upgrade.MachineUpgrades;
import com.ultreon.randomthingz.util.color.ColorGetter;
import lombok.experimental.UtilityClass;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"unused"})
@UtilityClass
public final class ModItems {

    static {
        ItemMaterial.registerItems();
        Toolset.registerItems();
        CraftingItems.register();
        MachineUpgrades.register();
    }

    public static final ItemRegistryObject<WrenchItem> WRENCH = register("wrench", WrenchItem::new);
    public static final ItemRegistryObject<Item> DEBUG_ITEM = register("debug_item", DebugItem::new);
    public static final ItemRegistryObject<BatteryItem> BATTERY = register("battery", BatteryItem::new);
    public static final ItemRegistryObject<HandPumpItem> HAND_PUMP = register("hand_pump", HandPumpItem::new);
    public static final ItemRegistryObject<CanisterItem> CANISTER = register("canister", () -> new CanisterItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<EmptyCanisterItem> EMPTY_CANISTER = register("empty_canister", () -> new EmptyCanisterItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<BucketItem> OIL_BUCKET = register("oil_bucket", () -> createBucketItem(() -> ModFluids.OIL));
    public static final ItemRegistryObject<BucketItem> DIESEL_BUCKET = register("diesel_bucket", () -> createBucketItem(() -> ModFluids.DIESEL));
    public static final ItemRegistryObject<BucketItem> ETHANE_BUCKET = register("ethane_bucket", () -> createBucketItem(() -> ModFluids.ETHANE));
    public static final ItemRegistryObject<BucketItem> POLYETHYLENE_BUCKET = register("polyethylene_bucket", () -> createBucketItem(() -> ModFluids.POLYETHYLENE));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Overpowered     //
    /////////////////////////
    public static final ItemRegistryObject<Item> KILL_SWITCH = register("kill_switch", () -> new Item(new Item.Properties()));
    public static final ItemRegistryObject<BanHammerItem> BAN_HAMMER = register("ban_hammer", BanHammerItem::new);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Miscellaneous     //
    ///////////////////////////
    public static final ItemRegistryObject<LegendaryEnderPearlItem> LEGENDARY_ENDER_PEARL = register("legendary_ender_pearl", () -> new LegendaryEnderPearlItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<DynamiteItem> DYNAMITE = register("dynamite", () -> new DynamiteItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.UNCOMMON)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Food     //
    //////////////////
    public static final ItemRegistryObject<Item> HAMBURGER_BUN = register("hamburger_bun", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 0.7f).build())));
    public static final ItemRegistryObject<Item> CHEESE_BURGER = register("cheese_burger", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 0.7f).build())));
    public static final ItemRegistryObject<Item> CHEESE_SLICE = register("cheese_slice", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build())));
    public static final ItemRegistryObject<CheeseItem> CHEESE = register("cheese", () -> new CheeseItem(new Item.Properties().tab(CreativeModeTab.TAB_FOOD), (stack) -> new ItemStack(CHEESE_SLICE, stack.getCount() * 6)));
    public static final ItemRegistryObject<Item> COOKED_CARROT = register("cooked_carrot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(3).saturationMod(0.25f).build())));
    public static final ItemRegistryObject<Item> BAKED_APPLE = register("baked_apple", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).build())));
    public static final ItemRegistryObject<Item> FLOUR = register("flour", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));
    public static final ItemRegistryObject<Item> DOUGH = register("dough", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));
    public static final ItemRegistryObject<Item> FRIED_EGG = register("fried_egg", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.13f).build())));
    public static final ItemRegistryObject<Item> BACON = register("bacon", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.13f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200), 0.8f).build())));
    public static final ItemRegistryObject<Item> COOKED_BACON = register("cooked_bacon", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(5).saturationMod(0.40f).build())));
    public static final ItemRegistryObject<Item> CHICKEN_LEG = register("chicken_leg", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.1f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 400), 0.9f).build())));
    public static final ItemRegistryObject<Item> COOKED_CHICKEN_LEG = register("cooked_chicken_leg", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(4).saturationMod(0.35f).build())));
    public static final ItemRegistryObject<Item> MEATBALL = register("meatball", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(1).saturationMod(0.15f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 100), 1f).build())));
    public static final ItemRegistryObject<Item> COOKED_MEATBALL = register("cooked_meatball", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(3).saturationMod(0.225f).build())));
    public static final ItemRegistryObject<Item> SHOARMA = register("shoarma", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.15f).build())));
    public static final ItemRegistryObject<Item> COOKED_SHOARMA = register("cooked_shoarma", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(8).saturationMod(0.45f).build())));
    public static final ItemRegistryObject<Item> PORK_SHANK = register("pork_shank", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.15f).build())));
    public static final ItemRegistryObject<Item> COOKED_PORK_SHANK = register("cooked_pork_shank", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(7).saturationMod(0.375f).build())));
    public static final ItemRegistryObject<Item> TOMAHAWK = register("tomahawk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(0.215f).build())));
    public static final ItemRegistryObject<Item> COOKED_TOMAHAWK = register("cooked_tomahawk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(7).saturationMod(0.415f).build())));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Nature     //
    ////////////////////
    public static final ItemRegistryObject<Item> STICK_VARIANT_1 = register("stick_variant1", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> STICK_VARIANT_2 = register("stick_variant2", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> STICK_VARIANT_3 = register("stick_variant3", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> STICK_VARIANT_4 = register("stick_variant4", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> STICK_VARIANT_5 = register("stick_variant5", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> STICK_VARIANT_6 = register("stick_variant6", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Wood     //
    //////////////////
    public static final ItemRegistryObject<Item> EUCALYPTUS_LEAF = register("eucalyptus_leaf", () -> new EucalyptusLeafItem(new Item.Properties()
            .tab(CreativeModeTab.TAB_DECORATIONS)
            .stacksTo(128)
            .food(new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationMod(0.2f)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60, 1), 0.7f)
                    .build())));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Fletching     //
    ///////////////////////

    // Bows
    public static final ItemRegistryObject<AdvancedBowItem> BLAZE_BOW = register("blaze_bow", () -> new AdvancedBowItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT), 6.25f, 1.0f, 6, 1, true));
    public static final ItemRegistryObject<AdvancedBowItem> ICE_BOW = register("ice_bow", () -> new AdvancedBowItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT), 2f, 1.0f, 8, 2));

    // Rods
    public static final ItemRegistryObject<IngredientItem> URANIUM_ROD = register("uranium_rod", IngredientItem::new);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Ingots or Dusts     //
    /////////////////////////////

    // Unstable
    public static final ItemRegistryObject<UnstableInfinityIngot> UNSTABLE_INFINITY_INGOT = register("unstable_infinity_ingot", UnstableInfinityIngot::new);

    // Dusts
    public static final ItemRegistryObject<IngotOrDustItem> RUBY_DUST = register("ruby_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> AMETHYST_DUST = register("amethyst_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> AQUAMARINE_DUST = register("aquamarine_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> SAPPHIRE_DUST = register("saphire_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> MALACHITE_DUST = register("malachite_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> TOPAZ_DUST = register("topaz_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> AMBER_DUST = register("amber_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> BERYL_DUST = register("beryl_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> DIAMOND_DUST = register("diamond_dust", IngotOrDustItem::new);
    public static final ItemRegistryObject<IngotOrDustItem> TANZANITE_DUST = register("tanzanite_dust", IngotOrDustItem::new);

    // Gems
    public static final ItemRegistryObject<Item> RUBY = register("ruby", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> AMETHYST = register("amethyst", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> AQUAMARINE = register("aquamarine", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> SAPPHIRE = register("saphire", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> MALACHITE = register("malachite", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> TOPAZ = register("topaz", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> AMBER = register("amber", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> PERIDOT = register("peridot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> BERYL = register("beryl", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final ItemRegistryObject<Item> TANZANITE = register("tanzanite", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // Icons
    public static final ItemRegistryObject<Item> DUNGEONS = register("dungeons", () -> new SwordItem(Tiers.DIAMOND, 0, 0f, new Item.Properties().setNoRepair().stacksTo(1)));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Dungeons     //
    //////////////////////
    public static final ItemRegistryObject<AdvancedBowItem> DUNGEONS_HUNTERS_BOW = register("hunters_bow", () -> new AdvancedBowItem(new Item.Properties().tab(ModCreativeTabs.DUNGEONS), 4.0f, 1.0f, 1.75d) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_DIAMOND_PICKAXE = register("dungeons_diamond_pickaxe", () -> new PickaxeItem(Tiers.DIAMOND, 3, -2.1f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_DIAMOND_AXE = register("dungeons_diamond_axe", () -> new AxeItem(Tiers.DIAMOND, 4, -2.2f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_AXE = register("dungeons_axe", () -> new AxeItem(Tiers.IRON, 4, -2.2f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_CURSED_AXE = register("cursed_axe", () -> new AxeItem(Tiers.GOLD, 4, -2.2f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_FIREBRAND = register("firebrand", () -> new AxeItem(Tiers.DIAMOND, 4, -2.2f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_HIGHLAND_AXE = register("highland_axe", () -> new AxeItem(Tiers.IRON, 4, -2.2f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<AxeItem> DUNGEONS_WHIRLWIND = register("whirlwind", () -> new AxeItem(Tiers.IRON, 4, -2.2f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_DAGGER = register("dungeons_dagger", () -> new SwordItem(Tiers.IRON, 2, -1.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_ETERNAL_KNIFE = register("eternal_knife", () -> new SwordItem(Tiers.DIAMOND, 2, -1.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_FANGS_OF_FROST = register("fangs_of_frost", () -> new SwordItem(Tiers.IRON, 2, -1.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_MOON_DAGGER = register("moon_dagger", () -> new SwordItem(Tiers.IRON, 2, -1.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_NIGHTMARES_BITE = register("nightmares_bite", () -> new SwordItem(Tiers.DIAMOND, 2, -1.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_SPIRIT_KNIFE = register("spirit_knife", () -> new SwordItem(Tiers.DIAMOND, 2, -1.0f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_THE_LAST_LAUGH_GOLD = register("the_last_laugh_gold", () -> new SwordItem(Tiers.GOLD, 3, -0.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_THE_LAST_LAUGH_SILVER = register("the_last_laugh_silver", () -> new SwordItem(Tiers.IRON, 2, -0.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_DIAMOND_SWORD = register("dungeons_diamond_sword", () -> new SwordItem(Tiers.DIAMOND, 4, -2.0f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_IRON_SWORD = register("dungeons_iron_sword", () -> new SwordItem(Tiers.IRON, 4, -2.0f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_BROADSWORD = register("dungeons_broadsword", () -> new SwordItem(Tiers.IRON, 5, -3.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_STORMLANDER = register("stormlander", () -> new PickaxeItem(Tiers.DIAMOND, 6, -2.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_GREAT_HAMMER = register("great_hammer", () -> new PickaxeItem(Tiers.IRON, 6, -2.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<PickaxeItem> DUNGEONS_HAMMER_OF_GRAVITY = register("hammer_of_gravity", () -> new PickaxeItem(Tiers.DIAMOND, 6, -2.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_FLAIL = register("flail", () -> new SwordItem(Tiers.STONE, 5, -3.0f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<SwordItem> DUNGEONS_SUNS_GRACE = register("suns_grace", () -> new SwordItem(Tiers.STONE, 5, -3.0f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<HoeItem> DUNGEONS_FROST_SCYTHE = register("frost_scythe", () -> new HoeItem(Tiers.STONE, 6, -2.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
        }
    });
    public static final ItemRegistryObject<HoeItem> DUNGEONS_JAILORS_SCYTHE = register("jailors_scythe", () -> new HoeItem(Tiers.STONE, 6, -2.5f, new Item.Properties().tab(ModCreativeTabs.DUNGEONS)) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level dimensionIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
            tooltip.add(new TranslatableComponent("desc.randomthingz.mc_dungeons"));
            super.appendHoverText(stack, dimensionIn, tooltip, flagIn);
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

    public static final ItemRegistryObject<KnifeItem> KNIFE = register("knife", () -> new KnifeItem(Tiers.IRON, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(120)));
    public static final ItemRegistryObject<MagnetItem> MAGNET = register("magnet", () -> new MagnetItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(4)));

    // Wands
    public static final ItemRegistryObject<FireWandItem> FIRE_STAFF = register("fire_wand", FireWandItem::new);
    public static final ItemRegistryObject<NatureStaffItem> NATURE_STAFF = register("nature_wand", NatureStaffItem::new);
    public static final ItemRegistryObject<TeleportStaffItem> TELEPORT_STAFF = register("teleport_wand", TeleportStaffItem::new);

    // Stone Level
    public static final ItemRegistryObject<SwordItem> STONE_SWORD_OF_DOOM = register("stone_sword_of_doom", () -> new SwordTool(Tiers.STONE, 8, -2.0f, new Item.Properties().tab(ModCreativeTabs.SPECIALS).rarity(Rarity.RARE), () -> new AbstractTrait[]{ModTraits.WITHER.get()}));

    // Iron Level
    public static final ItemRegistryObject<AxeItem> EMERGENCY_FIRE_AXE = register("emergency_fire_axe", () -> new AxeItem(Tiers.IRON, 2, -2.55f, new Item.Properties().tab(ModCreativeTabs.SPECIALS).rarity(Rarity.UNCOMMON)));
    public static final ItemRegistryObject<SwordTool> FIRE_SWORD = register("fire_sword", () -> new SwordTool(Tiers.IRON, 3, -3.5f, new Item.Properties().tab(ModCreativeTabs.SPECIALS).rarity(Rarity.RARE), () -> new AbstractTrait[]{ModTraits.BLAZE.get()}));
    public static final ItemRegistryObject<SwordTool> ENDER_SWORD = register("ender_sword", () -> new SwordTool(Tiers.IRON, 3, -1.9f, new Item.Properties().tab(ModCreativeTabs.SPECIALS).rarity(Rarity.RARE), () -> new AbstractTrait[]{ModTraits.ENDER.get()}));
    public static final ItemRegistryObject<SwordTool> SLIME_SWORD = register("slime_sword", () -> new SwordTool(Tiers.WOOD, 0, -1.5f, new Item.Properties().tab(ModCreativeTabs.SPECIALS).rarity(Rarity.RARE), () -> new AbstractTrait[]{ModTraits.SLIMEY.get()}));

    // Diamond Level
    public static final ItemRegistryObject<AxeItem> LEVIATHAN_AXE = register("leviathan_axe", () -> new AxeItem(Tiers.DIAMOND, 5, -2.55f, new Item.Properties().tab(ModCreativeTabs.SPECIALS)));
    public static final ItemRegistryObject<AxeItem> ADAMANTANIUM_AXE_RED = register("adamantanium_axe_red", () -> new AxeItem(Tiers.DIAMOND, 5, -1.875f, new Item.Properties().tab(ModCreativeTabs.SPECIALS)));
    public static final ItemRegistryObject<SwordItem> DIAMOND_QUARTZ_SWORD = register("diamond_quartz_sword", () -> new SwordItem(Tiers.DIAMOND, 6, -2.0f, new Item.Properties().tab(ModCreativeTabs.SPECIALS)));

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Tools     //
    ///////////////////

    // Tools - Vanilla
    public static final ItemRegistryObject<AxeItem> WOODEN_BATTLEAXE = register("wooden_battleaxe", () -> new AxeItem(Tiers.WOOD, 7.0f, -2.2f, new Item.Properties().tab(ModCreativeTabs.TOOLS)));
    public static final ItemRegistryObject<AxeItem> STONE_BATTLEAXE = register("stone_battleaxe", () -> new AxeItem(Tiers.STONE, 7.0f, -2.2f, new Item.Properties().tab(ModCreativeTabs.TOOLS)));
    public static final ItemRegistryObject<AxeItem> GOLDEN_BATTLEAXE = register("golden_battleaxe", () -> new AxeItem(Tiers.GOLD, 7.0f, -2.2f, new Item.Properties().tab(ModCreativeTabs.TOOLS)));
    public static final ItemRegistryObject<AxeItem> IRON_BATTLEAXE = register("iron_battleaxe", () -> new AxeItem(Tiers.IRON, 7.0f, -2.2f, new Item.Properties().tab(ModCreativeTabs.TOOLS)));
    public static final ItemRegistryObject<AxeItem> DIAMOND_BATTLEAXE = register("diamond_battleaxe", () -> new AxeItem(Tiers.DIAMOND, 7.0f, -2.2f, new Item.Properties().tab(ModCreativeTabs.TOOLS)));
    public static final ItemRegistryObject<AxeItem> NETHERITE_BATTLEAXE = register("netherite_battleaxe", () -> new AxeItem(Tiers.NETHERITE, 7.0f, -2.2f, new Item.Properties().tab(ModCreativeTabs.TOOLS)));
    public static final ItemRegistryObject<HammerItem> HAMMER = register("hammer", () -> new HammerItem(Tiers.DIAMOND, 7, -3.0f, new Item.Properties().tab(ModCreativeTabs.TOOLS)));

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

    private static BucketItem createBucketItem(Supplier<? extends Fluid> fluid) {
        return new BucketItem(fluid, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).craftRemainder(Items.BUCKET));
    }

    private static NoPlaceBucketItem createNoPlaceBucketItem(Supplier<? extends Fluid> fluid) {
        return new NoPlaceBucketItem(fluid, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).craftRemainder(Items.BUCKET));
    }

    private static <T extends Item> ItemRegistryObject<T> register(String name, Supplier<T> item) {
        return ItemsModule.ITEMS.register(name, item);
    }
}
