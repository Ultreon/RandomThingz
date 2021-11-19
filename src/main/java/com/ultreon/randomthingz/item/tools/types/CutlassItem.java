package com.ultreon.randomthingz.item.tools.types;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ultreon.randomthingz.commons.IHasToolType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.UUID;

public class CutlassItem extends SwordItem implements IHasToolType {
    private final float attackDamage;
    /** Modifiers applied when the item is in the mainhand of a user. */
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    protected static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.nameUUIDFromBytes("Attack Knockback".getBytes());

    public CutlassItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn.defaultMaxDamage((int) (tier.getMaxUses() * 1.4)));
        this.attackDamage = (float)attackDamageIn + (tier.getAttackDamage() * 2);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage * 0.8, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeedIn * 1.2, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        if (state.matchesBlock(Blocks.COBWEB)) {
            return 20.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !state.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0F : 1.75F;
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damageItem(1, attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockBroken(ItemStack stack, World dimensionIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.getBlockHardness(dimensionIn, pos) > 0.2F) {
            stack.damageItem(2, entityLiving, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlot);
    }

    @Override
    public ToolType getToolType() {
        return ToolType.get("sword");
    }
}
