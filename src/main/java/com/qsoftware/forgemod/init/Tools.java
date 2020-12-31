package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.interfaces.Translatable;
import com.qsoftware.forgemod.objects.common.INamed;
import com.qsoftware.forgemod.util.Targeter;
import com.qsoftware.silent.lib.registry.ItemDeferredRegister;
import com.qsoftware.silent.lib.registry.ItemRegistryObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public enum Tools implements INamed, Translatable {
    COPPER(OreMaterial.COPPER, 80f, 4.6f, 5.8f, 0.5f, 15, 2),
    PLATINUM(OreMaterial.PLATINUM, 400f, 4.6f, 5.8f, 0.2f, 17, 3),
    OBSIDIAN(OreMaterial.OBSIDIAN, 800f, 14.0f, 20.0f, 0.0f, 6, 5),
    ALUMINUM(OreMaterial.ALUMINUM, 40f, 6.0f, 6.5f, 0.5f, 12, 2),
    ALUMINUM_STEEL(OreMaterial.ALUMINUM_STEEL, 40f, 6.0f, 6.5f, 0.4f, 11, 2),
    BRONZE(OreMaterial.BRONZE, 90f, 6.0f, 6.5f, 0.45f, 14, 2),
    ELECTRUM(OreMaterial.ELECTRUM, 120f, 3.8f, 6.5f, 0.65f, 44, 1),
    ENDERIUM(OreMaterial.ENDERIUM, 600f, 8.0f, 9.0f, 0.05f, 34, 4) {
        @Override
        public ActionResultType onItemUse(ItemUseContext context) {
            PlayerEntity playerEntity = context.getPlayer();
            World world = context.getWorld();

            if (playerEntity != null && world instanceof ServerWorld) {
                if (context.getHand() == Hand.MAIN_HAND) {
                    ItemStack item = context.getItem();
                    BlockRayTraceResult blockRayTraceResult = rayTrace(context.getWorld(), playerEntity, RayTraceContext.FluidMode.NONE);
                    BlockPos add = blockRayTraceResult.getPos().add(blockRayTraceResult.getFace().getDirectionVec());

                    playerEntity.teleportKeepLoaded(add.getX(), add.getY(), add.getZ());

                    item.damageItem(2, playerEntity, (player) -> {
                        player.sendBreakAnimation(context.getHand());
                    });
                }
            }
            return super.onItemUse(context);
        }

        protected BlockRayTraceResult rayTrace(World worldIn, PlayerEntity player, RayTraceContext.FluidMode fluidMode) {
            float f = player.rotationPitch;
            float f1 = player.rotationYaw;
            Vector3d vector3d = player.getEyePosition(1.0F);
            float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
            float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
            float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
            float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
            float f6 = f3 * f4;
            float f7 = f2 * f4;
            double d0 = player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();;
            Vector3d vector3d1 = vector3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
            return worldIn.rayTraceBlocks(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.COLLIDER, fluidMode, player));
        }
    },
    LEAD(OreMaterial.LEAD, 1_600f, 3.0f, 9.0f, 0.05f, 34, 4, (entity) -> new EffectInstance(Effects.POISON, ThreadLocalRandom.current().nextInt(400, 800), 1, false, false), null),
    ;

//    private final int baseDurability;
//    private final int baseDamage;

    // Registry
    private final ItemDeferredRegister registry;

    // Flags
    private final boolean isMetal;
    private final boolean isMetalGem;
    private final boolean isGem;

    // Types
    private final SoundEvent armorSound;
    private final IItemTier itemTier;
    private final IArmorMaterial armorMaterial;
    private final OreMaterial material;
    private final Supplier<Ingredient> repairMaterialSupplier;
    private final Function<LivingEntity, EffectInstance> attackEffect;
    private final Function<LivingEntity, EffectInstance> defenseEffect;

    // Input values.
    private final float resistance;
    private final float sharpness;
    private final float weight;

    // Convert values.
    private final double baseDurability;
    private final double toolDurability;
    private final double armorDurabilityFactor;

    // Output values.
    private final float knockbackResistance;
    private final float maxDamageFactor;
    private final float attackDamage;

    // Direct values.
    private final int efficiency;
    private final int enchantability;
    private final int harvestLevel;

    // Output items.
    // Items: Armors.
    private ArmorItem helmet;
    private ArmorItem chestplate;
    private ArmorItem leggings;
    private ArmorItem boots;

    // Items: Tools.
    private SwordItem sword;
    private AxeItem axe;
    private PickaxeItem pickaxe;
    private ShovelItem shovel;
    private HoeItem hoe;

    // Registry objects.
    // RO: Armors.
    private ItemRegistryObject<ArmorItem> helmetProvider;
    private ItemRegistryObject<ArmorItem> chestplateProvider;
    private ItemRegistryObject<ArmorItem> leggingsProvider;
    private ItemRegistryObject<ArmorItem> bootsProvider;

    // RO: Tools.
    private ItemRegistryObject<SwordItem> swordProvider;
    private ItemRegistryObject<AxeItem> axeProvider;
    private ItemRegistryObject<PickaxeItem> pickaxeProvider;
    private ItemRegistryObject<ShovelItem> shovelProvider;
    private ItemRegistryObject<HoeItem> hoeProvider;

    // Arrays
    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private static final float[] DAMAGE_REDUCTION_ARRAY = new float[]{2.5f, 5.0f, 7.5f, 3.75f};

    // Runnables
    private Runnable onArmorTick;
    private Runnable onAttack;

    /**
     * Weight equals weight in grams.
     *
     * Charcoal:
     *  - Weight = 2f (0.002 KG -> 0.0005 Knockback Resistance)
     *  - Resistance = ca. 0.8f
     *  - Sharpness = 1.0f
     *  - Enchantability = 2
     * Charcoal:
     *  - Weight = 20f (0.02 KG -> 0.005 Knockback Resistance)
     *  - Resistance = ca. 0.85f
     *  - Sharpness = 1.2f
     *  - Enchantability = 3
     * Wood:
     *  - Weight = 15f (0.015 KG -> 0.00375 Knockback Resistance)
     *  - Resistance = ca. 1.3f
     *  - Sharpness = 2.0f
     *  - Enchantability = 15
     * Stone:
     *  - Weight = 80f (0.08 KG -> 0.02 Knockback Resistance)
     *  - Resistance = ca. 2.325f
     *  - Sharpness = 4.0f
     *  - Enchantability = 5
     * Gold:
     *  - Weight = 250f (0.25 KG -> 0.0625 Knockback Resistance)
     *  - Resistance = 3.0f
     *  - Flexibility = 0.8f
     *  - Sharpness = 6.0f
     *  - Enchantability = 22
     * Copper:
     *  - Weight = 150f (0.15 KG -> 0.0375 Knockback Resistance)
     *  - Resistance = ca. 4.8f
     *  - Sharpness = 5.9f
     *  - Enchantability = 15
     * Iron:
     *  - Weight = 200f (0.2 KG -> 0.0500 Knockback Resistance)
     *  - Resistance = ca. 5.0f
     *  - Sharpness = 6.0f
     *  - Enchantability = 14
     * Diamond:
     *  - Weight = 20f (0.02 KG -> 0.0050 Knockback Resistance)
     *  - Resistance = ca. 8.0f
     *  - Sharpness = 8.0f
     *  - Enchantability = 10
     * Netherite:
     *  - Weight = 400f (0.4 KG -> 0.1 Knockback Resistance)
     *  - Resistance = ca. 14.0f
     *  - Sharpness = 9.0f
     *  - Enchantability = 15
     * Obsidian:
     *  - Weight = 800f (0.8 KG -> 0.2 Knockback Resistance)
     *  - Resistance = ca. 14.0f
     *  - Sharpness = 20.0f
     *  - Enchantability = 6
     * Lead:
     *  - Weight = 1'600f (1.6 KG -> 0.4 Knockback Resistance)
     *  - Resistance = ca. 8.0f
     *  - Sharpness = 5.0f
     *  - Enchantability = 5
     *  @param material the ore material, used for repair material.
     * @param weight the weight in grams per cubic centimeter, will be used for knockback resistance.
     * @param resistance the resistance, will be calculated into durability, armor toughness and armor points.
     * @param sharpness the sharpness, will be calculated into efficiency and attack damage.
     * @param flexibility the flexibility. will be calculated into durability with resistance and bow draw speed.
     * @param enchantability the enchantability of the tool / armor piece.
     * @param harvestLevel the harvest level for the tools.
     */
    Tools(OreMaterial material, float weight, float resistance, float sharpness, float flexibility, int enchantability, int harvestLevel) {
        this(material, weight, resistance, sharpness, flexibility, enchantability, harvestLevel, null);
    }

    /**
     * Weight equals weight in grams.
     *
     * Charcoal:
     *  - Weight = 2f (0.002 KG -> 0.0005 Knockback Resistance)
     *  - Resistance = ca. 0.8f
     *  - Sharpness = 1.0f
     *  - Enchantability = 2
     * Charcoal:
     *  - Weight = 20f (0.02 KG -> 0.005 Knockback Resistance)
     *  - Resistance = ca. 0.85f
     *  - Sharpness = 1.2f
     *  - Enchantability = 3
     * Wood:
     *  - Weight = 15f (0.015 KG -> 0.00375 Knockback Resistance)
     *  - Resistance = ca. 1.3f
     *  - Sharpness = 2.0f
     *  - Enchantability = 15
     * Stone:
     *  - Weight = 80f (0.08 KG -> 0.02 Knockback Resistance)
     *  - Resistance = ca. 2.325f
     *  - Sharpness = 4.0f
     *  - Enchantability = 5
     * Gold:
     *  - Weight = 250f (0.25 KG -> 0.0625 Knockback Resistance)
     *  - Resistance = ca. 1.865f
     *  - Sharpness = 6.0f
     *  - Enchantability = 22
     * Copper:
     *  - Weight = 150f (0.15 KG -> 0.0375 Knockback Resistance)
     *  - Resistance = ca. 4.8f
     *  - Sharpness = 5.9f
     *  - Enchantability = 15
     * Iron:
     *  - Weight = 200f (0.2 KG -> 0.0500 Knockback Resistance)
     *  - Resistance = ca. 5.0f
     *  - Sharpness = 6.0f
     *  - Enchantability = 14
     * Diamond:
     *  - Weight = 20f (0.02 KG -> 0.0050 Knockback Resistance)
     *  - Resistance = ca. 8.0f
     *  - Sharpness = 8.0f
     *  - Enchantability = 10
     * Netherite:
     *  - Weight = 400f (0.4 KG -> 0.1 Knockback Resistance)
     *  - Resistance = ca. 14.0f
     *  - Sharpness = 9.0f
     *  - Enchantability = 15
     * Obsidian:
     *  - Weight = 800f (0.8 KG -> 0.2 Knockback Resistance)
     *  - Resistance = ca. 14.0f
     *  - Sharpness = 20.0f
     *  - Enchantability = 6
     * Lead:
     *  - Weight = 1'600f (1.6 KG -> 0.4 Knockback Resistance)
     *  - Resistance = ca. 8.0f
     *  - Sharpness = 5.0f
     *  - Enchantability = 5
     * @param material the ore material, used for repair material.
     * @param weight the weight in grams per cubic centimeter, will be used for knockback resistance.
     * @param resistance the resistance, will be calculated into durability with flexibility, armor toughness and armor points.
     * @param sharpness the sharpness, will be calculated into efficiency and attack damage.
     * @param flexibility the flexibility. will be calculated into durability with resistance and bow draw speed.
     * @param enchantability the enchantability of the tool / armor piece.
     * @param harvestLevel the harvest level for the tools.
     * @param effect the effect for attack and on armor tick.
     */
    Tools(OreMaterial material, float weight, float resistance, float sharpness, float flexibility, int enchantability, int harvestLevel, @Nullable Function<LivingEntity, EffectInstance> effect) {
        this(material, weight, resistance, sharpness, flexibility, enchantability, harvestLevel, effect, effect);
    }

    /**
     * Weight equals weight in grams.
     *
     * Charcoal:
     *  - Weight = 2f (0.002 KG -> 0.0005 Knockback Resistance)
     *  - Resistance = ca. 0.8f
     *  - Sharpness = 1.0f
     *  - Enchantability = 2
     * Charcoal:
     *  - Weight = 20f (0.02 KG -> 0.005 Knockback Resistance)
     *  - Resistance = ca. 0.85f
     *  - Sharpness = 1.2f
     *  - Enchantability = 3
     * Wood:
     *  - Weight = 15f (0.015 KG -> 0.00375 Knockback Resistance)
     *  - Resistance = ca. 1.3f
     *  - Sharpness = 2.0f
     *  - Enchantability = 15
     * Stone:
     *  - Weight = 80f (0.08 KG -> 0.02 Knockback Resistance)
     *  - Resistance = ca. 2.325f
     *  - Sharpness = 4.0f
     *  - Enchantability = 5
     * Gold:
     *  - Weight = 250f (0.25 KG -> 0.0625 Knockback Resistance)
     *  - Resistance = ca. 1.865f
     *  - Sharpness = 6.0f
     *  - Enchantability = 22
     * Copper:
     *  - Weight = 150f (0.15 KG -> 0.0375 Knockback Resistance)
     *  - Resistance = ca. 4.8f
     *  - Sharpness = 5.9f
     *  - Enchantability = 15
     * Iron:
     *  - Weight = 200f (0.2 KG -> 0.0500 Knockback Resistance)
     *  - Resistance = ca. 5.0f
     *  - Sharpness = 6.0f
     *  - Enchantability = 14
     * Diamond:
     *  - Weight = 20f (0.02 KG -> 0.0050 Knockback Resistance)
     *  - Resistance = ca. 8.0f
     *  - Sharpness = 8.0f
     *  - Enchantability = 10
     * Netherite:
     *  - Weight = 400f (0.4 KG -> 0.1 Knockback Resistance)
     *  - Resistance = ca. 14.0f
     *  - Sharpness = 9.0f
     *  - Enchantability = 15
     * Obsidian:
     *  - Weight = 800f (0.8 KG -> 0.2 Knockback Resistance)
     *  - Resistance = ca. 14.0f
     *  - Sharpness = 20.0f
     *  - Enchantability = 6
     * Lead:
     *  - Weight = 1'600f (1.6 KG -> 0.4 Knockback Resistance)
     *  - Resistance = ca. 8.0f
     *  - Sharpness = 5.0f
     *  - Enchantability = 5
     * @param material the ore material, used for repair material.
     * @param weight the weight in grams per cubic centimeter, will be used for knockback resistance.
     * @param resistance the resistance, will be calculated into durability, armor toughness and armor points.
     * @param sharpness the sharpness, will be calculated into efficiency and attack damage.
     * @param flexibility the flexibility. will be calculated into durability with resistance and bow draw speed.
     * @param enchantability the enchantability of the tool / armor piece.
     * @param harvestLevel the harvest level for the tools.
     * @param attackEffect the effect to give at attack to the victim.
     * @param defenseEffect the effect to give on armor tick.
     */
    Tools(OreMaterial material, float weight, float resistance, float sharpness, float flexibility, int enchantability, int harvestLevel, @Nullable Function<LivingEntity, EffectInstance> attackEffect, @Nullable Function<LivingEntity, EffectInstance> defenseEffect) {
        this.material = material;
        this.resistance = resistance;
        this.sharpness = sharpness;
        this.weight = weight;
        this.attackEffect = attackEffect;
        this.defenseEffect = defenseEffect;
        this.efficiency = (int) sharpness;
        this.attackDamage = (sharpness - 2) / 2;
        this.enchantability = enchantability;
        this.harvestLevel = harvestLevel;
        this.maxDamageFactor = this.resistance * 4;
        this.baseDurability = Math.pow(this.resistance * (Math.PI / 2 * 100), (1f / (100f / 3f) + 1f));
        this.toolDurability = baseDurability / ((flexibility + 1) / 2 + 0.5);
        this.armorDurabilityFactor = this.baseDurability * (100d / 3d) / (Math.PI * ((5d / 9d + 5d) * 100d));
        this.repairMaterialSupplier = () -> Ingredient.fromItems(material.getIngot().orElse(material.getGem().orElseThrow(() -> new IllegalArgumentException("Repair material was not found."))));
        this.knockbackResistance = weight / 4_000;

        this.isMetal = material.getIngot().isPresent() && !material.getGem().isPresent();
        this.isMetalGem = material.getIngot().isPresent() && material.getGem().isPresent();
        this.isGem = !material.getIngot().isPresent() && material.getGem().isPresent();

        if (isMetal) {
            this.armorSound = SoundEvents.ITEM_ARMOR_EQUIP_IRON;
        } else if (isMetalGem) {
            this.armorSound = SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
        } else if (isGem) {
            this.armorSound = SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
        } else {
            this.armorSound = SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        }
        
        this.itemTier = new IItemTier() {
            @Override
            public int getMaxUses() {
//                return (int) Math.pow(resistance * (Math.PI / 2 * 100), Math.PI / 3);
//                return resistance * (resistance ^ pi) / resistance * 5
                return (int) baseDurability;
            }

            @Override
            public float getEfficiency() {
                return sharpness;
            }

            @Override
            public float getAttackDamage() {
                return (sharpness - 2) / 2;
            }

            @Override
            public int getHarvestLevel() {
                return harvestLevel;
            }

            @Override
            public int getEnchantability() {
                return enchantability;
            }

            @Override
            public Ingredient getRepairMaterial() {
                return repairMaterialSupplier.get();
            }
        };

        this.armorMaterial = new IArmorMaterial() {
            @Override
            public int getDurability(EquipmentSlotType slotIn) {
                return (int) (MAX_DAMAGE_ARRAY[slotIn.getIndex()] * armorDurabilityFactor);
            }

            @Override
            public int getDamageReductionAmount(EquipmentSlotType slotIn) {
                return (int) (DAMAGE_REDUCTION_ARRAY[slotIn.getIndex()] * (resistance / 5f));
            }

            @Override
            public int getEnchantability() {
                return enchantability;
            }

            @Override
            public SoundEvent getSoundEvent() {
                return armorSound;
            }

            @Override
            public Ingredient getRepairMaterial() {
                return repairMaterialSupplier.get();
            }

            @Override
            public String getName() {
                return name().toLowerCase(Locale.ROOT);
            }

            @Override
            public float getToughness() {
                return Math.max(resistance / 7f - 1f, 0f);
            }

            @Override
            public float getKnockbackResistance() {
                return knockbackResistance;
            }
        };

        // Registry.
        this.registry = Registration.ITEMS;
    }
    
    public static void register() {
        for (Tools tools : Tools.values()) {
            // Armor pieces
            tools.helmetProvider = tools.registry.register(tools.name() + "_helmet", () -> new ArmorItem(tools.armorMaterial, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroups.TOOLS)) {
                @Override
                public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
                    tools.doArmorTick(stack, world, player);
                }
            });
            tools.chestplateProvider = tools.registry.register(tools.name() + "_chestplate", () -> new ArmorItem(tools.armorMaterial, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroups.TOOLS)) {
                @Override
                public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
                    tools.doArmorTick(stack, world, player);
                }
            });
            tools.leggingsProvider = tools.registry.register(tools.name() + "_leggings", () -> new ArmorItem(tools.armorMaterial, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroups.TOOLS)) {
                @Override
                public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
                    tools.doArmorTick(stack, world, player);
                }
            });
            tools.bootsProvider = tools.registry.register(tools.name() + "_boots", () -> new ArmorItem(tools.armorMaterial, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroups.TOOLS)) {
                @Override
                public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
                    tools.doArmorTick(stack, world, player);
                }
            });

            // Tools.
            tools.swordProvider = tools.registry.register(tools.name() + "_sword", () -> new SwordItem(tools.itemTier, 3, -2.4f, new Item.Properties().group(ModItemGroups.TOOLS)) {
                @Override
                public ActionResultType onItemUse(ItemUseContext context) {
                    return tools.onItemUse(context);
                }

                @Override
                public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    return tools.onHitEntity(stack, target, attacker);
                }

                @Override
                public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
                    if (entity instanceof LivingEntity) {
                        return tools.onHitEntity(stack, (LivingEntity) entity, player);
                    }
                    return super.onLeftClickEntity(stack, player, entity);
                }
            });
            tools.axeProvider = tools.registry.register(tools.name() + "_axe", () -> new AxeItem(tools.itemTier, 5.0f, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS)) {
                @Override
                public ActionResultType onItemUse(ItemUseContext context) {
                    return tools.onItemUse(context);
                }

                @Override
                public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    return tools.onHitEntity(stack, target, attacker);
                }

                @Override
                public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
                    if (entity instanceof LivingEntity) {
                        return tools.onHitEntity(stack, (LivingEntity) entity, player);
                    }
                    return super.onLeftClickEntity(stack, player, entity);
                }
            });
            tools.pickaxeProvider = tools.registry.register(tools.name() + "_pickaxe", () -> new PickaxeItem(tools.itemTier, 1, -2.8f, new Item.Properties().group(ModItemGroups.TOOLS)) {
                @Override
                public ActionResultType onItemUse(ItemUseContext context) {
                    return tools.onItemUse(context);
                }

                @Override
                public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    return tools.onHitEntity(stack, target, attacker);
                }

                @Override
                public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
                    if (entity instanceof LivingEntity) {
                        return tools.onHitEntity(stack, (LivingEntity) entity, player);
                    }
                    return super.onLeftClickEntity(stack, player, entity);
                }
            });
            tools.shovelProvider = tools.registry.register(tools.name() + "_shovel", () -> new ShovelItem(tools.itemTier, 1.5f, -3.0f, new Item.Properties().group(ModItemGroups.TOOLS)) {
                @Override
                public ActionResultType onItemUse(ItemUseContext context) {
                    return tools.onItemUse(context);
                }

                @Override
                public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    return tools.onHitEntity(stack, target, attacker);
                }

                @Override
                public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
                    if (entity instanceof LivingEntity) {
                        return tools.onHitEntity(stack, (LivingEntity) entity, player);
                    }
                    return super.onLeftClickEntity(stack, player, entity);
                }
            });
            tools.hoeProvider = tools.registry.register(tools.name() + "_hoe", () -> new HoeItem(tools.itemTier, -3, -0.0f, new Item.Properties().group(ModItemGroups.TOOLS)) {
                @Override
                public ActionResultType onItemUse(ItemUseContext context) {
                    return tools.onItemUse(context);
                }

                @Override
                public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
                    return tools.onHitEntity(stack, target, attacker);
                }

                @Override
                public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
                    if (entity instanceof LivingEntity) {
                        return tools.onHitEntity(stack, (LivingEntity) entity, player);
                    }
                    return super.onLeftClickEntity(stack, player, entity);
                }
            });
        }
    }

    private boolean onHitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return false;
    }

    /**
     * Get the repair material.
     * 
     * @return the repair material or null if it failed to get the repair material.
     */
    @Nullable
    public Ingredient getRepairMaterial() {
        try {
            return repairMaterialSupplier.get();
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the helmet item.
     *
     * @return the helmet item or null if it failed to get it.
     */
    @Nullable
    public ArmorItem getHelmet() {
        try {
            if (helmet == null) {
                helmet = helmetProvider.getItem();
            }
            return helmet;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the chestplate item.
     *
     * @return the chestplate item or null if it failed to get it.
     */
    @Nullable
    public ArmorItem getChestplate() {
        try {
            if (chestplate == null) {
                chestplate = chestplateProvider.getItem();
            }
            return chestplate;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the leggings item.
     *
     * @return the leggings item or null if it failed to get it.
     */
    @Nullable
    public ArmorItem getLeggings() {
        try {
            if (leggings == null) {
                leggings = leggingsProvider.getItem();
            }
            return leggings;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the boots item.
     *
     * @return the boots item or null if it failed to get it.
     */
    @Nullable
    public ArmorItem getBoots() {
        try {
            if (boots == null) {
                boots = bootsProvider.getItem();
            }
            return boots;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the sword item.
     * 
     * @return the sword item or null if it failed to get it.
     */
    @Nullable
    public SwordItem getSword() {
        try {
            if (sword == null) {
                sword = swordProvider.getItem();
            }
            return sword;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the axe item.
     *
     * @return the axe item or null if it failed to get it.
     */
    @Nullable
    public AxeItem getAxe() {
        try {
            if (axe == null) {
                axe = axeProvider.getItem();
            }
            return axe;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the pickaxe item.
     *
     * @return the pickaxe item or null if it failed to get it.
     */
    @Nullable
    public PickaxeItem getPickaxe() {
        try {
            if (pickaxe == null) {
                pickaxe = pickaxeProvider.getItem();
            }
            return pickaxe;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the shovel item.
     *
     * @return the shovel item or null if it failed to get it.
     */
    @Nullable
    public ShovelItem getShovel() {
        try {
            if (shovel == null) {
                shovel = shovelProvider.getItem();
            }
            return shovel;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the hoe item.
     *
     * @return the hoe item or null if it failed to get it.
     */
    @Nullable
    public HoeItem getHoe() {
        try {
            if (hoe == null) {
                hoe = hoeProvider.getItem();
            }
            return hoe;
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * Get the material resistance.
     *
     * @return the material resistance.
     */
    public float getResistance() {
        return resistance;
    }

    /**
     * Get the weight.
     *
     * @return the weight.
     */
    public float getWeight() {
        return weight;
    }

    public boolean isMetal() {
        return isMetal;
    }

    public boolean isMetalGem() {
        return isMetalGem;
    }

    public boolean isGem() {
        return isGem;
    }

    /**
     * Get the armor durability factor.
     *
     * @return the armor durability factor.
     */
    public double getArmorDurabilityFactor() {
        return armorDurabilityFactor;
    }

    /**
     * Get the tool sharpness.
     *
     * @return the tool sharpness.
     */
    public float getSharpness() {
        return sharpness;
    }

    /**
     * Get maximum damage factor.
     *
     * @return the maximum damage factor.
     */
    public float getMaxDamageFactor() {
        return maxDamageFactor;
    }

    /**
     * Get the tool item tier.
     *
     * @return the tool item tier.
     */
    public IItemTier getItemTier() {
        return itemTier;
    }

    /**
     * Get the ore material.
     *
     * @return the ore material.
     */
    public OreMaterial getMaterial() {
        return material;
    }

    /**
     * Get the base durability.
     *
     * @return the base durability.
     */
    public double getBaseDurability() {
        return baseDurability;
    }

    /**
     * Get the tool durability.
     *
     * @return the tool durability.
     */
    public double getToolDurability() {
        return toolDurability;
    }
    /**
     * Get mining efficiency.
     *
     * @return the mining efficiency.
     */
    public int getEfficiency() {
        return efficiency;
    }

    /**
     * Get enchantability.
     *
     * @return the enchantability.
     */
    public int getEnchantability() {
        return enchantability;
    }

    /**
     * Get the harvest level.
     *
     * @return the harvest level.
     */
    public int getHarvestLevel() {
        return harvestLevel;
    }

    /**
     * Get the attack damage.
     *
     * @return the attack damage.
     */
    public float getAttackDamage() {
        return attackDamage;
    }

    /**
     * Get armor equip sound.
     * 
     * @return the armor equip {@link SoundEvent sound event}.
     * @see SoundEvents
     */
    public SoundEvent getArmorSound() {
        return armorSound;
    }

    /**
     * Get the armor material.
     *
     * @return the {@link IArmorMaterial armor material}.
     */
    public IArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }

    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    /**
     * Get attack effect.
     *
     * @return the attack effect got from a supplier, null, if no effect.
     * @param player the player entity for the effect.
     */
    @Nullable
    public EffectInstance getAttackEffect(LivingEntity player) {
        return attackEffect != null ? attackEffect.apply(player) : null;
    }

    /**
     * Get defense effect.
     *
     * @return the defense effect got from a supplier.
     * @param player the player entity for the effect.
     */
    @Nullable
    public EffectInstance getDefenseEffect(ServerPlayerEntity player) {
        return defenseEffect != null ? defenseEffect.apply(player) : null;
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        return ActionResultType.PASS;
    }

    public final void doArmorTick(ItemStack stack, World world, PlayerEntity playerEntity) {
        if (playerEntity instanceof ServerPlayerEntity) {
            EffectInstance defenseEffect = getDefenseEffect((ServerPlayerEntity) playerEntity);
            if (defenseEffect != null) {
                playerEntity.addPotionEffect(defenseEffect);
            }
        }
        this.onArmorTick(stack, world, playerEntity);
    }

    private void onArmorTick(ItemStack stack, World world, PlayerEntity playerEntity) {

    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String getTranslationKey() {
        return "tool_material." + QForgeMod.MOD_ID + "." + getName();
    }
}
