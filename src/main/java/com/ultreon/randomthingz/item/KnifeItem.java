package com.ultreon.randomthingz.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ultreon.randomthingz.common.interfaces.Sliceable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

    public KnifeItem(IItemTier tierIn, Properties properties) {
        super(tierIn, properties.defaultMaxDamage(120));

        // Attack damage.
        this.attackDamage = tierIn.getAttackDamage() + 2f;

        // Attributes
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World dimensionIn, BlockPos pos, PlayerEntity player) {
        return !player.isCreative();
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    @Override
    public boolean hitEntity(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        // Damage item.
        stack.damageItem(1, attacker, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean onBlockBroken(@NotNull ItemStack stack, @NotNull World dimensionIn, BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (state.getBlockHardness(dimensionIn, pos) != 0.0F) {
            stack.damageItem(isBlockMinable(state) ? 1 : 2, entityLiving, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
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
    public boolean canHarvestBlock(BlockState blockIn) {
        return blockIn.matchesBlock(Blocks.COBWEB) || blockIn.getBlock() instanceof Sliceable;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @SuppressWarnings("deprecation")
    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    public float getMiningSpeed(@NotNull ItemStack stack, BlockState state) {
        if (state.matchesBlock(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack copy = itemStack.copy();
        if (copy.isDamageable()) {
            copy.setDamage(copy.getDamage() + 1);
            if (copy.getDamage() == copy.getMaxDamage()) {
                return ItemStack.EMPTY;
            }
        }

        return copy;
    }
}
