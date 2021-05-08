package com.qtech.randomthingz.item.tools;

import com.qtech.randomthingz.RandomThingz;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Infinity sword item class.
 *
 * @author Qboi123
 */
@Deprecated
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class SilverAxeItem extends AxeItem {
    public SilverAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean hitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, @NotNull LivingEntity player) {
        if (victim.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
            if (player instanceof PlayerEntity) {
                victim.attack(new EntityDamageSource("player", player), 8.0f);
            } else {
                victim.attack(new EntityDamageSource("entity", player), 8.0f);
            }
        }
        return true;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        if (!entity.dimension.isClientSided && entity instanceof LivingEntity) {
            LivingEntity victim = (LivingEntity) entity;
            if (victim.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
                victim.attack(new EntityDamageSource("player", player), 8.0f);
            }
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World dimensionIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "This item will deal more damage to undead."));
        super.addInformation(stack, dimensionIn, tooltip, flagIn);
    }
}