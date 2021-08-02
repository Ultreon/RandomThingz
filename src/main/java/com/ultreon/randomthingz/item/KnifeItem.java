package com.ultreon.randomthingz.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
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
     * Modifiers applied when the item is in the mainhand of a user.
     */
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public KnifeItem(IItemTier tierIn, Properties properties) {
        super(tierIn, properties.defaultMaxDamage(4));

        // Attack damage.
        this.attackDamage = tierIn.getAttackDamage() + 1f;

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

    @SuppressWarnings("NullableProblems")
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
        stack.damageItem(1, attacker, (entity) -> {
            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
        });
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    @Override
    public boolean onBlockBroken(@NotNull ItemStack stack, @NotNull World dimensionIn, BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (state.getBlockHardness(dimensionIn, pos) != 0.0F) {
            stack.damageItem(2, entityLiving, (entity) -> {
                entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }

        return true;
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return blockIn.matchesBlock(Blocks.COBWEB);
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

//    @Override
//    public int getDamage(ItemStack stack) {
//        return 0;
//    }
//
//    @Override
//    public int getMaxDamage(ItemStack stack) {
//        return 145;
//    }

    //    @Override
//    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World dimensionIn, PlayerEntity playerIn, @NotNull Hand handIn) {
//        Entity entity1 = Targeter.getTarget(playerIn);
//
//        ItemStack stack = playerIn.getHeldItem(handIn);
//
//        if (!(entity1 instanceof ItemEntity)) {
//            return ActionResult.resultPass(stack);
//        }
//
//        ItemEntity entity = (ItemEntity) entity1;
//        playerIn.sendMessage(new StringTextComponent(TextColors.LIGHT_RED + "Entity: " + TextColors.WHITE + Objects.toString(entity, "null")), UUID.randomUUID());
//
//        ItemStack stack1 = entity.getItem();
//        Item item = stack1.getItem();
//
//        if (item instanceof Sliceable) {
//            Sliceable sliceable = (Sliceable) item;
//            ItemStack newStack = sliceable.onKnifeSlice(stack1.copy());
//
//            double x = entity.getPosX();
//            double y = entity.getPosY();
//            double z = entity.getPosZ();
//
//            entity1.remove(false);
//
//            ItemEntity entity2 = new ItemEntity(dimensionIn, x, y, z, newStack);
//            entity2.setDefaultPickupDelay();
//            dimensionIn.spawnEntity(entity2);
////            playerIn.sendMessage(new StringTextComponent(TextColors.LIGHT_RED + "Entity: " + TextColors.WHITE + Objects.toString(entity, "null")), UUID.randomUUID());
//            Entity entity3 = Targeter.getTarget(playerIn);
//
//            if (!(entity3 instanceof ItemEntity)) {
//                throw new IllegalArgumentException("Entity type changed while slicing item.");
//            }
//
//            ItemEntity entity4 = (ItemEntity) entity3;
//            ItemStack stack2 = entity4.getItem();
//            stack2.getItem();
//
//            stack.damageItem(1, playerIn, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
//            return ActionResult.resultSuccess(stack);
//        } else {
//            stack.damageItem(2, playerIn, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
//            return ActionResult.resultFail(stack);
//        }
//    }
}
