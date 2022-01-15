package com.ultreon.randomthingz.item.tool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.item.tool.trait.AbstractTrait;
import com.ultreon.randomthingz.util.ItemUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class HoeTool extends HoeItem implements ITool {
    private final Supplier<AbstractTrait[]> traits;
    private final double attackDamage;
    private final Multimap<Attribute, AttributeModifier> toolAttributes;

    protected static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.nameUUIDFromBytes("Attack Knockback".getBytes());

    public HoeTool(Tier tier, double attackDamageIn, double attackSpeedIn, Properties builderIn, Supplier<AbstractTrait[]> traits) {
        super(tier, (int) attackDamageIn, (float) attackSpeedIn, builderIn);
        this.traits = traits;
        this.attackDamage = attackDamageIn + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.toolAttributes = builder.build();
    }

    @Override
    public Set<ToolType> getQfmToolTypes() {
        return new HashSet<>(Collections.singletonList(ToolType.AXE));
    }

    @Override
    public AbstractTrait[] getTraits() {
        return traits.get();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = super.useOn(context);
        for (AbstractTrait trait : getTraits()) {
            InteractionResult actionResultType = trait.onUseItem(context);
            result = ItemUtils.maxActionResult(result, actionResultType);
        }
        return result;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        if (isEnchantable(stack)) {
            return super.isBookEnchantable(stack, book);
        }
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        boolean val = super.isEnchantable(stack);
        for (AbstractTrait trait : getTraits()) {
            val |= trait.isEnchantable(getQfmToolTypes(), stack);
        }
        return val;
    }

    @Override
    public boolean canBeDepleted() {
        boolean val = super.canBeDepleted();
        for (AbstractTrait trait : getTraits()) {
            val &= trait.isDamageable();
        }
        return val;
    }

    @Override
    public boolean isFireResistant() {
        boolean val = super.isFireResistant();
        for (AbstractTrait trait : getTraits()) {
            val |= trait.isImmuneToFire();
        }
        return val;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        if (canBeDepleted()) {
            super.setDamage(stack, damage);
            return;
        }
        if (isDamaged(stack)) {
            super.setDamage(stack, 0);
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if (canBeDepleted()) {
            super.damageItem(stack, amount, entity, onBroken);
        }
        return 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level dimensionIn, Player playerIn, InteractionHand handIn) {
        boolean val = false;
        for (AbstractTrait trait : getTraits()) {
            val |= trait.onRightClick(this, dimensionIn, playerIn, handIn);
        }
        if (val) {
            return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
        }
        return InteractionResultHolder.fail(playerIn.getItemInHand(handIn));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity clicked) {
        float smite = 0.0f;
        for (AbstractTrait trait : getTraits()) {
            float smiteValue = trait.getSmiteValue(getQfmToolTypes(), stack, player);
            if (smiteValue < 0.0f) {
                RandomThingz.LOGGER.warn("Smite value is less that zero, this can cause weird behavior");
            }

            smite += smiteValue;
            trait.onLeftClickEntity(stack, player, clicked);
        }

        if (smite > 0.0f) {
            if (clicked instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) clicked;
                if (living.getMobType() == MobType.UNDEAD) {
                    clicked.hurt(new EntityDamageSource("player", player), smite);
                }
            }
        }

        return false;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity victim, LivingEntity attacker) {
        float smite = 0.0f;
        boolean val = super.hurtEnemy(stack, victim, attacker);
        for (AbstractTrait trait : getTraits()) {
            float smiteValue = trait.getSmiteValue(getQfmToolTypes(), stack, attacker);
            if (smiteValue < 0.0f) {
                RandomThingz.LOGGER.warn("Smite value is less that zero, this can cause weird behavior");
            }

            smite += smiteValue;
            val &= trait.onHitEntity(stack, victim, attacker);
        }

        if (smite > 0.0f) {
            if (victim.getMobType() == MobType.UNDEAD) {
                if (attacker instanceof Player) {
                    victim.hurt(new EntityDamageSource("player", attacker), smite);
                } else {
                    victim.hurt(new EntityDamageSource("entity", attacker), smite);
                }
            }
        }

        if (val) {
            super.hurtEnemy(stack, victim, attacker);
        }

        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level dimension, Entity entity, int slot, boolean selected) {
        if (!canBeDepleted()) {
            setDamage(stack, 0);
        }

        for (AbstractTrait trait : getTraits()) {
            trait.onInventoryTick(stack, dimension, entity, slot, selected);
        }
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level dimension, BlockState state, BlockPos pos, LivingEntity living) {
        boolean op = super.mineBlock(stack, dimension, state, pos, living);
        for (AbstractTrait trait : getTraits()) {
            op |= trait.onBlockBroken(stack, dimension, state, pos, living);
        }
        return op;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, Player player) {
        boolean op = super.onDroppedByPlayer(stack, player);
        for (AbstractTrait trait : getTraits()) {
            op &= trait.onDroppedByPlayer(stack, player);
        }
        return op;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
        for (AbstractTrait trait : traits.get()) {
            tooltip.add(trait.getTranslation());
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        boolean val = super.onBlockStartBreak(stack, pos, player);
        for (AbstractTrait trait : getTraits()) {
            val |= trait.onBlockStartBreak(stack, pos, player);
        }
        return val;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float val = super.getDestroySpeed(stack, state);
        for (AbstractTrait trait : getTraits()) {
            val *= trait.getDestroyMultiplier(getQfmToolTypes(), stack, state);
        }
        for (AbstractTrait trait : getTraits()) {
            val += trait.getDestroyModifier(getQfmToolTypes(), stack, state);
        }
        for (AbstractTrait trait : getTraits()) {
            val *= trait.getDestroyTotalMultiplier(getQfmToolTypes(), stack, state);
        }
        return val;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        Multimap<Attribute, AttributeModifier> attributes;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.toolAttributes);

        int knockback = 0;
        for (AbstractTrait trait : getTraits()) {
            knockback += trait.getKnockback(getQfmToolTypes());
        }

        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Tool modifier", knockback, AttributeModifier.Operation.ADDITION));
        attributes = builder.build();
        return equipmentSlot == EquipmentSlot.MAINHAND ? attributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public float getAttackDamage() {
        return (float) this.attackDamage;
    }
}
