package com.qtech.randomthingz.item.tools;

import com.google.common.collect.Streams;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.item.tools.trait.AbstractTrait;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
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
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
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

    @SuppressWarnings("UnstableApiUsage")
    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent e) {
        LivingEntity entityLiving = e.getEntityLiving();
        for (ItemStack stack : Streams.stream(entityLiving.getEquipmentAndArmor()).filter((itemStack) -> itemStack.getItem() instanceof ArmorTool).collect(Collectors.toList())) {
            ArmorTool item = (ArmorTool) stack.getItem();
            item.livingDamage(e);
        }
    }

    public void livingDamage(LivingDamageEvent e) {
        for (AbstractTrait trait : traits.get()) {
            trait.onLivingDamage(e);
        }
    }
}
