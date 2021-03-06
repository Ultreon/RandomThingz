package com.ultreon.randomthingz.item.tool.trait;

import com.ultreon.randomthingz.item.ItemType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

@SuppressWarnings("unused")
public abstract class AbstractTrait implements IForgeRegistryEntry<AbstractTrait> {
    private ResourceLocation name;

    /**
     * Return false if you want the item to be unenchantable.
     *
     * @param type a set of item types of what the tool is
     * @param stack the item stack of the tool.
     * @return true to be enchantable, false otherwise.
     */
    public boolean isEnchantable(Set<ItemType> type, ItemStack stack) {
        return true;
    }

    /**
     * Called when an living entity was hit using an item that has this trait.
     *
     * @param stack    the item stack that has this trait.
     * @param victim   the victim (the living being that has been attacked).
     * @param attacker the attacker (the living being that attacked another living being).
     * @return ?
     */
    public boolean onHitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, LivingEntity attacker) {
        return true;
    }

    /**
     * Called when a player has right-clicked with an item that has this trait.
     *
     * @param item      the item that has this trait.
     * @param dimension the dimension of the clicker.
     * @param clicker   the player that clicked.
     * @param hand      the hand where the item was held.
     * @return ?
     */
    public boolean onRightClick(Item item, Level dimension, Player clicker, InteractionHand hand) {
        return false;
    }

    /**
     * Called every tick when in inventory of an item that has this trait.
     *
     * @param stack       the item stack that is ticking.
     * @param dimensionIn the dimension where the tick happened.
     * @param entityIn    the owner of the item.
     * @param itemSlot    the slot index of the item in the inventory.
     * @param isSelected  true if the item is selected in the hotbar.
     */
    public void onInventoryTick(ItemStack stack, Level dimensionIn, Entity entityIn, int itemSlot, boolean isSelected) {

    }

    /**
     * Called every tick while equipped on an item slot for armor with an item that has this trait.
     *
     * @param stack     the item stack that is ticking.
     * @param dimension the dimension where the tick happened.
     * @param player    the owner of the item that has this trait.
     */
    public void onArmorTick(ItemStack stack, Level dimension, Player player) {

    }

    /**
     * Called when a block was broken by an item the item that has this trait.
     *
     * @param stack        the item that has this trait.
     * @param dimensionIn  the dimension where the block was broken.
     * @param state        the block state of the broken block.
     * @param pos          the position of the broken block.
     * @param entityLiving the entity that broke the block.
     * @return ?
     */
    public boolean onBlockBroken(ItemStack stack, Level dimensionIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        return true;
    }

    /**
     * Called when an item that has this trait was dropped.
     *
     * @param stack  the item that has this trait.
     * @param player the player that dropped the stack.
     * @return ?
     */
    public boolean onDroppedByPlayer(ItemStack stack, Player player) {
        return false;
    }

    /**
     * @return the translated name of this trait.
     */
    public Component getTranslation() {
        if (this.getRegistryName() == null) {
            return new TextComponent("misc.unknown");
        }
        TranslatableComponent translationTextComponent = new TranslatableComponent("qfm_trait." + this.getRegistryName().getNamespace() + "." + this.getRegistryName().getPath().replaceAll("/", "."));
        translationTextComponent.setStyle(translationTextComponent.getStyle().withColor(getColor()));
        return translationTextComponent;
    }

    public TextColor getColor() {
        return null;
    }

    @Override
    public AbstractTrait setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    /**
     * Get the registry name of this trait.
     *
     * @return an registry name.
     */
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return name;
    }

    /**
     * Return the registry type of the trait, the class {@linkplain AbstractTrait}.
     *
     * @return {@linkplain AbstractTrait}.
     */
    @Override
    public Class<AbstractTrait> getRegistryType() {
        return AbstractTrait.class;
    }

    /**
     * Will be called when a player left clicked an entity.
     *
     * @param stack  the item that has this trait.
     * @param player the clicker.
     * @param entity the entity that has been left clicked by the player.
     */
    public void onLeftClickEntity(ItemStack stack, Player player, Entity entity) {

    }

    /**
     * Will be called when the player has started breaking a block with the item that has this trait.
     *
     * @param stack  the item that has this trait.
     * @param pos    the block's position.
     * @param player the player that has started breaking a block.
     * @return ?
     */
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, Player player) {
        return false;
    }

    /**
     * @return false if the item will be unbreakable, true is it is damageable.
     */
    public boolean isDamageable() {
        return true;
    }

    /**
     * @return will return true if the item bound to this trait will be immune to fire, false otherwise.
     */
    public boolean isImmuneToFire() {
        return false;
    }

    /**
     * Multiply the base destroy speed of the tool.
     *
     * @param type  the type of tool the item stack is.
     * @param stack the item that has this trait.
     * @param state the state of the block that is currently being mined.
     * @return the multiplication in block destroy speed. This will multiply before {@linkplain #getDestroyModifier(Set, ItemStack, BlockState)}.
     */
    public float getDestroyMultiplier(Set<ItemType> type, ItemStack stack, BlockState state) {
        return 1f;
    }

    /**
     * Modify the destroy speed using addition.
     *
     * @param type  the type of tool the item stack is.
     * @param stack the item that has this trait.
     * @param state the state of the block that is currently being mined.
     * @return the addition in block destroy speed.
     */
    public float getDestroyModifier(Set<ItemType> type, ItemStack stack, BlockState state) {
        return 0f;
    }

    /**
     * Multiply the total destroy speed of the tool.
     *
     * @param type  the type of tool the item stack is.
     * @param stack the item that has this trait.
     * @param state he state of the block that is currently being mined.
     * @return the multiplication of the total block destroy speed. This will multiply after {@linkplain #getDestroyModifier(Set, ItemStack, BlockState)}.
     */
    public float getDestroyTotalMultiplier(Set<ItemType> type, ItemStack stack, BlockState state) {
        return 1f;
    }

    /**
     * Return the smite value of the tool.
     * Smite is the attack damage against undead.
     *
     * @param qfmItemTypes
     * @param stack
     * @param attacker
     * @return
     */
    public float getSmiteValue(Set<ItemType> qfmItemTypes, ItemStack stack, LivingEntity attacker) {
        return 0f;
    }

    public void onLivingDamage(LivingDamageEvent e) {

    }

    /**
     * Called when this item is used when targetting a Block
     */
    public @NotNull InteractionResult onUseItem(UseOnContext context) {
        return InteractionResult.FAIL;
    }

    public float getKnockback(Set<ItemType> qfmItemTypes) {
        return 0f;
    }
}
