package com.qsoftware.forgemod.modules.items.tools;

import com.qsoftware.forgemod.modules.items.tools.trait.AbstractTrait;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

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

    public void onLivingDamage(LivingDamageEvent e) {
        for (AbstractTrait trait : traits.get()) {
            trait.onLivingDamage(e);
        }
    }
}
