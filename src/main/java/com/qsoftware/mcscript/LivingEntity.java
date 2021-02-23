package com.qsoftware.mcscript;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import java.util.Collection;
import java.util.Locale;

@SuppressWarnings("unused")
public class LivingEntity extends Entity {
    private final net.minecraft.entity.LivingEntity wrapper;

    protected static LivingEntity getCorrectEntity(net.minecraft.entity.LivingEntity entity) {
        if (entity instanceof net.minecraft.entity.player.ServerPlayerEntity) {
            return new Player((ServerPlayerEntity) entity);
        }
        return new LivingEntity(entity);
    }

    protected LivingEntity(net.minecraft.entity.LivingEntity entity) {
        super(entity);
        this.wrapper = entity;
    }

    public boolean addPotionEffect(EffectInstance effect) {
        return wrapper.addPotionEffect(effect);
    }

    public int getMaxAir() {
        return wrapper.getMaxAir();
    }
    public HandSide getPrimaryHand() {
        return wrapper.getPrimaryHand();
    }
    public CreatureAttribute getCreatureType() {
        return wrapper.getCreatureAttribute();
    }
    public Collection<EffectInstance> getActivePotionEffects() {
        return wrapper.getActivePotionEffects();
    }
    public int getItemInUseCount() {
        return wrapper.getItemInUseCount();
    }
    public int getItemInUseMaxCount() {
        return wrapper.getItemInUseMaxCount();
    }
    public int getLastAttackedEntityTime() {
        return wrapper.getLastAttackedEntityTime();
    }
    public DamageSource getLastDamageSource() {
        return wrapper.getLastDamageSource();
    }
    public float getMaxHealth() {
        return wrapper.getMaxHealth();
    }
    public boolean hasMovement() {
        return wrapper.getMovementSpeed();
    }
    public float getRenderScale() {
        return wrapper.getRenderScale();
    }
    public int getRevengeTimer() {
        return wrapper.getRevengeTimer();
    }
    public EntitySize getSize(Pose pose) {
        return wrapper.getSize(pose);
    }
    public int getTotalArmorValue() {
        return wrapper.getTotalArmorValue();
    }

    ////////////////////
    //     Combat     //
    ////////////////////
    public LivingEntity getBestAttacker() {
        return LivingEntity.getCorrectEntity(wrapper.getCombatTracker().getBestAttacker());
    }

    public LivingEntity getFighter() {
        return LivingEntity.getCorrectEntity(wrapper.getCombatTracker().getFighter());
    }
    public int getCombatDuration() {
        return wrapper.getCombatTracker().getCombatDuration();
    }
    public ITextComponent getCurrentAttackMessage() {
        return wrapper.getCombatTracker().getDeathMessage();
    }

    ////////////////////////
    //     Properties     //
    ////////////////////////
    public boolean isJumping() {
        return wrapper.isJumping;
    }
    public float getAbsorptionAmount() {
        return wrapper.getAbsorptionAmount();
    }
    public float getHealth() {
        return wrapper.getHealth();
    }
    public int getBeeStingCount() {
        return wrapper.getBeeStingCount();
    }
    public BlockPos getBedPosition() {
        return wrapper.getBedPosition().orElse(null);
    }
    public Hand getActiveHand() {
        return wrapper.getActiveHand();
    }
    public ItemStack getHeldItem(Hand hand) {
        return new ItemStack(wrapper.getHeldItem(hand));
    }
    public ItemStack getItemStackFromSlot(EquipmentSlotType slot) {
        return new ItemStack(wrapper.getItemStackFromSlot(slot));
    }
    public Entity getLastAttackedEntity() {
        return Entity.getCorrectEntity(wrapper.getLastAttackedEntity());
    }
    public boolean hasNoGravity() {
        return wrapper.hasNoGravity();
    }
    public LivingEntity getRevengeTarget() {
        return LivingEntity.getCorrectEntity(wrapper.getRevengeTarget());
    }
    public void setRevengeTarget(LivingEntity entity) {
        wrapper.setRevengeTarget(entity.wrapper);
    }
    public void setNoGravity(boolean enabled) {
        wrapper.setNoGravity(enabled);
    }
    public void setLastAttackedEntity(Entity entity) {
        wrapper.setLastAttackedEntity(entity.wrapper);
    }
    public void setItemStackToSlot(EquipmentSlotType slot, ItemStack stack) {
        wrapper.setItemStackToSlot(slot, stack.wrapper);
    }
    public void setHeldItem(String hand, ItemStack stack) {
        wrapper.setHeldItem(Hand.valueOf(hand.toUpperCase(Locale.ROOT)), stack.wrapper);
    }
    public void setHeldItem(Hand hand, ItemStack stack) {
        wrapper.setHeldItem(hand, stack.wrapper);
    }
    public void setActiveHand(String hand) {
        wrapper.setActiveHand(Hand.valueOf(hand.toUpperCase(Locale.ROOT)));
    }
    public void setActiveHand(Hand hand) {
        wrapper.setActiveHand(hand);
    }
    public void setBedPosition(BlockPos pos) {
        wrapper.setBedPosition(pos);
    }
    public void setBeeStingCount(int health) {
        wrapper.setBeeStingCount(health);
    }
    public void setHealth(float health) {
        wrapper.setHealth(health);
    }
    public void setAbsorptionAmount(float pos) {
        wrapper.setAbsorptionAmount(pos);
    }
    public void setJumping(boolean value) {
        wrapper.setJumping(value);
    }
}
