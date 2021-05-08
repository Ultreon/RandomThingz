package com.qtech.randomthingz.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.item.tools.trait.AbstractTrait;
import com.qtech.randomthingz.util.ItemUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ShovelTool extends ShovelItem implements ITool {
    private final Supplier<AbstractTrait[]> traits;
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> toolAttributes;

    public ShovelTool(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn, Supplier<AbstractTrait[]> traits) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.traits = traits;
        this.attackDamage = attackDamageIn + tier.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
        this.toolAttributes = builder.build();
    }

    @Override
    public Set<ToolType> getQfmToolTypes() {
        return new HashSet<>(Arrays.asList(ToolType.SHOVEL));
    }

    @Override
    public AbstractTrait[] getTraits() {
        return traits.get();
    }

    @Override
    public ActionResultType onUseItem(ItemUseContext context) {
        ActionResultType result = super.onUseItem(context);
        for (AbstractTrait trait : getTraits()) {
            ActionResultType actionResultType = trait.onUseItem(context);
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
    public boolean isDamageable() {
        boolean val = super.isDamageable();
        for (AbstractTrait trait : getTraits()) {
            val &= trait.isDamageable();
        }
        return val;
    }

    @Override
    public boolean isImmuneToFire() {
        boolean val = super.isImmuneToFire();
        for (AbstractTrait trait : getTraits()) {
            val |= trait.isImmuneToFire();
        }
        return val;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        if (isDamageable()) {
            super.setDamage(stack, damage);
            return;
        }
        if (isDamaged(stack)) {
            super.setDamage(stack, 0);
        }
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if (isDamageable()) {
            super.damageItem(stack, amount, entity, onBroken);
        }
        return 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World dimensionIn, PlayerEntity playerIn, Hand handIn) {
        boolean val = false;
        for (AbstractTrait trait : getTraits()) {
            val |= trait.onRightClick(this, dimensionIn, playerIn, handIn);
        }
        if (val) {
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }
        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity clicked) {
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
                if (living.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
                    clicked.attack(new EntityDamageSource("player", player), smite);
                }
            }
        }

        return false;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity victim, LivingEntity attacker) {
        float smite = 0.0f;
        boolean val = super.hitEntity(stack, victim, attacker);
        for (AbstractTrait trait : getTraits()) {
            float smiteValue = trait.getSmiteValue(getQfmToolTypes(), stack, attacker);
            if (smiteValue < 0.0f) {
                RandomThingz.LOGGER.warn("Smite value is less that zero, this can cause weird behavior");
            }

            smite += smiteValue;
            val &= trait.onHitEntity(stack, victim, attacker);
        }

        if (smite > 0.0f) {
            if (victim.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
                if (attacker instanceof PlayerEntity) {
                    victim.attack(new EntityDamageSource("player", attacker), smite);
                } else {
                    victim.attack(new EntityDamageSource("entity", attacker), smite);
                }
            }
        }

        if (val) {
            super.hitEntity(stack, victim, attacker);
        }

        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World dimension, Entity entity, int slot, boolean selected) {
        if (!isDamageable()) {
            setDamage(stack, 0);
        }

        for (AbstractTrait trait : getTraits()) {
            trait.onInventoryTick(stack, dimension, entity, slot, selected);
        }
    }

    @Override
    public boolean onBlockBroken(ItemStack stack, World dimension, BlockState state, BlockPos pos, LivingEntity living) {
        boolean op = super.onBlockBroken(stack, dimension, state, pos, living);
        for (AbstractTrait trait : getTraits()) {
            op |= trait.onBlockBroken(stack, dimension, state, pos, living);
        }
        return op;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack stack, PlayerEntity player) {
        boolean op = super.onDroppedByPlayer(stack, player);
        for (AbstractTrait trait : getTraits()) {
            op &= trait.onDroppedByPlayer(stack, player);
        }
        return op;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
        for (AbstractTrait trait : traits.get()) {
            tooltip.add(trait.getTranslation());
        }
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, PlayerEntity player) {
        boolean val = super.onBlockStartBreak(stack, pos, player);
        for (AbstractTrait trait : getTraits()) {
            val |= trait.onBlockStartBreak(stack, pos, player);
        }
        return val;
    }

    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        float val = super.getMiningSpeed(stack, state);
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
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.toolAttributes : super.getAttributeModifiers(equipmentSlot);
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }
}
