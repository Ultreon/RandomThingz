package com.qtech.randomthingz.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.entity.damagesource.DamageSourceInfinitySword;
import com.qtech.randomthingz.modules.ui.ModStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Deprecated
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class InfinityArmorItem extends ArmorItem {
    private UUID uuid;

    public InfinityArmorItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
        super(materialIn, slot, builderIn);
        uuid = ARMOR_MODIFIERS[slot.getIndex()];
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
        player.dimension.destroyBlock(pos, true);
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity victim, LivingEntity player) {

        if (player.dimension.isClientSided) {
            return true;
        }
        if (victim instanceof PlayerEntity) {
            PlayerEntity pvp = (PlayerEntity) victim;
            if (isInfinite(pvp)) {
                victim.attack(new DamageSourceInfinitySword(player).setDamageBypassesArmor(), 4.0F);
                return true;
            }
            if (pvp.getHeldItem(Hand.MAIN_HAND) != null && pvp.getHeldItem(Hand.MAIN_HAND).getItem() == Tools.INFINITY.getSword().get() && pvp.isHandActive()) {
                return true;
            }
        }

        victim.recentlyHit = 60;
        victim.getCombatTracker().trackDamage(new DamageSourceInfinitySword(player), victim.getHealth(), victim.getHealth());
        victim.setHealth(0);
        victim.onDeath(new EntityDamageSource("infinity", player));

//        return true;
//        if (target instanceof PlayerEntity) {
//            // Get player
//            PlayerEntity player1 = (PlayerEntity) target;
//
//            // Check Armor
//            if (!armor.isEmpty()) {
//                if (armor.get(0).getItem().equals(ModItems.INFINITY_BOOTS.get())) {
//                    if (armor.get(1).getItem().equals(ModItems.INFINITY_LEGGINGS.get())) {
//                        if (armor.get(2).getItem().equals(ModItems.INFINITY_CHESTPLATE.get())) {
//                            if (armor.get(3).getItem().equals(ModItems.INFINITY_HELMET.get())) {
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        EntityUtils.instantKill(target, "infinity_sword");
        return true;
    }

    private boolean isInfinite(PlayerEntity player) {
        // Get armor list.
        List<ItemStack> armor = (List<ItemStack>) player.getArmorInventoryList();

        // Check Armor
        if (!armor.isEmpty()) {
            if (armor.get(0).getItem().equals(Tools.INFINITY.getBoots().get())) {
                if (armor.get(1).getItem().equals(Tools.INFINITY.getLeggings().get())) {
                    if (armor.get(2).getItem().equals(Tools.INFINITY.getChestplate().get())) {
                        return armor.get(3).getItem().equals(Tools.INFINITY.getHelmet().get());
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (!entity.dimension.isClientSided && entity instanceof PlayerEntity) {
            PlayerEntity victim = (PlayerEntity) entity;
            if (victim.isCreative() && !(victim.getHealth() <= 0) && victim.getHealth() > 0 && !isInfinite(victim)) {
                victim.getCombatTracker().trackDamage(new DamageSourceInfinitySword(player), victim.getHealth(), victim.getHealth());
                victim.setHealth(0);
                victim.onDeath(new EntityDamageSource("infinity", player));
                player.addStat(ModStats.INFINITY_KILL, 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        uuid = ARMOR_MODIFIERS[slot.getIndex()];

        Multimap<Attribute, AttributeModifier> attrsOld = super.getAttributeModifiers(equipmentSlot);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attrs = ImmutableMultimap.builder();

        attrs.putAll(attrsOld);

        if (equipmentSlot == EquipmentSlotType.FEET && slot == EquipmentSlotType.FEET) {
            attrs.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Movement Speed", 2.0d, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if (equipmentSlot == EquipmentSlotType.CHEST && slot == EquipmentSlotType.CHEST) {
            attrs.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(uuid, "Attack Knockback", 10.0d, AttributeModifier.Operation.ADDITION));
            attrs.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, "Attack Speed", 1024.0d, AttributeModifier.Operation.ADDITION));
        }
        if (equipmentSlot == EquipmentSlotType.HEAD && slot == EquipmentSlotType.HEAD) {
            attrs.put(Attributes.LUCK, new AttributeModifier(uuid, "Luck", 1024.0d, AttributeModifier.Operation.ADDITION));
        }

        return attrs.build();
    }

    @SubscribeEvent
    public static void equipEvent(LivingEquipmentChangeEvent event) {
        LivingEntity entityLiving = event.getEntityLiving();
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            if (player.isServerDimension()) {
                if (event.getSlot() == EquipmentSlotType.FEET) {
                    if (event.getFrom().getItem() instanceof InfinityArmorItem) {
                        player.abilities.allowFlying = false;
                    }
                    if (event.getTo().getItem() instanceof InfinityArmorItem) {
                        player.abilities.allowFlying = true;
                    }
                }
            }
        }
    }
}
