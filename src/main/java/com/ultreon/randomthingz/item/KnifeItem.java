package com.ultreon.randomthingz.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ultreon.randomthingz.common.interfaces.Sliceable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

/**
 * Knife item class.
 *
 * @author Qboi123
 */
public class KnifeItem extends TieredItem {
    private final float attackDamage;
    private final float attackSpeed = 1.3f;

    /**
     * Modifiers applied when the item is in the main hand of a user.
     */
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public KnifeItem(Tier tierIn, Properties properties) {
        super(tierIn, properties.defaultDurability(120));

        // Attack damage.
        this.attackDamage = tierIn.getAttackDamageBonus() + 2f;

        // Attributes
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level dimensionIn, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        // Damage item.
        stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level dimensionIn, BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (state.getDestroySpeed(dimensionIn, pos) != 0.0F) {
            stack.hurtAndBreak(isBlockMinable(state) ? 1 : 2, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }

    public boolean isBlockMinable(BlockState state) {
        return state.getBlock() instanceof Sliceable;
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        return blockIn.is(Blocks.COBWEB) || blockIn.getBlock() instanceof Sliceable;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @SuppressWarnings("deprecation")
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public float getDestroySpeed(@NotNull ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack copy = itemStack.copy();
        if (copy.isDamageableItem()) {
            copy.setDamageValue(copy.getDamageValue() + 1);
            if (copy.getDamageValue() == copy.getMaxDamage()) {
                return ItemStack.EMPTY;
            }
        }

        return copy;
    }
}
