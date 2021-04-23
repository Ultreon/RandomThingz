package com.qsoftware.forgemod.modules.items.tools;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.items.tools.trait.AbstractTrait;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = QForgeMod.modId)
public class ArmorTool extends ArmorItem implements ITool {
    private final Supplier<AbstractTrait[]> traits;

    public ArmorTool(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn, Supplier<AbstractTrait[]> traits) {
        super(materialIn, slot, builderIn);
        this.traits = traits;
    }

    @Override
    public void onArmorTick(ItemStack stack, World dimension, PlayerEntity player) {
        for (AbstractTrait trait : traits.get()) {
            trait.onArmorTick(stack, dimension, player);
        }
    }

    @Override
    public final AbstractTrait[] getTraits() {
        return traits.get();
    }

    @Override
    public Set<ToolType> getQfmToolTypes() {
        switch (slot) {
            case HEAD:
                return new HashSet<>(Collections.singletonList(ToolType.HELMET));
            case CHEST:
                return new HashSet<>(Collections.singletonList(ToolType.CHESTPLATE));
            case LEGS:
                return new HashSet<>(Collections.singletonList(ToolType.LEGGINGS));
            case FEET:
                return new HashSet<>(Collections.singletonList(ToolType.BOOTS));
            default:
                return new HashSet<>(Collections.emptyList());
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World dimension, List<ITextComponent> tooltip, ITooltipFlag flag) {
        for (AbstractTrait trait : traits.get()) {
            tooltip.add(trait.getTranslation());
        }
    }

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent e) {
        LivingEntity entityLiving = e.getEntityLiving();
        if (entityLiving.getHeldItem(Hand.MAIN_HAND).getItem() instanceof ArmorTool) {
            ArmorTool item = (ArmorTool) entityLiving.getHeldItem(Hand.MAIN_HAND).getItem();
            item.livingDamage(e);
        }
    }

    public void livingDamage(LivingDamageEvent e) {
        for (AbstractTrait trait : traits.get()) {
            trait.onLivingDamage(e);
        }
    }
}
