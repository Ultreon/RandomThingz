package com.ultreon.randomthingz.item.tool;

import com.google.common.collect.Streams;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.item.ItemType;
import com.ultreon.randomthingz.item.tool.trait.AbstractTrait;
import com.ultreon.randomthingz.item.tool.types.ModToolTypes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class ArmorTool extends ArmorItem implements ITool {
    private final Supplier<AbstractTrait[]> traits;

    public ArmorTool(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn, Supplier<AbstractTrait[]> traits) {
        super(materialIn, slot, builderIn);
        this.traits = traits;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level dimension, Player player) {
        for (AbstractTrait trait : traits.get()) {
            trait.onArmorTick(stack, dimension, player);
        }
    }

    @Override
    public final AbstractTrait[] getTraits() {
        return traits.get();
    }

    @Override
    public Set<ItemType> getQfmToolTypes() {
        return switch (slot) {
            case HEAD -> Set.of(ItemType.HELMET);
            case CHEST -> Set.of(ItemType.CHESTPLATE);
            case LEGS -> Set.of(ItemType.LEGGINGS);
            case FEET -> Set.of(ItemType.BOOTS);
            default -> new HashSet<>(Collections.emptyList());
        };
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level dimension, List<Component> tooltip, TooltipFlag flag) {
        for (AbstractTrait trait : traits.get()) {
            tooltip.add(trait.getTranslation());
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent e) {
        LivingEntity entityLiving = e.getEntityLiving();
        for (ItemStack stack : Streams.stream(entityLiving.getAllSlots()).filter((itemStack) -> itemStack.getItem() instanceof ArmorTool).toList()) {
            ArmorTool item = (ArmorTool) stack.getItem();
            item.livingDamage(stack, e);
        }
    }

    public void livingDamage(ItemStack stack, LivingDamageEvent e) {
        float smite = 0f;
        for (AbstractTrait trait : traits.get()) {
            trait.onLivingDamage(e);
            float smiteValue = trait.getSmiteValue(getQfmToolTypes(), stack, e.getEntityLiving());
            if (smiteValue < 0f) {
                RandomThingz.LOGGER.warn("Smite value is less that zero, this can cause weird behavior");
            }

            smite += smiteValue;
        }

        Entity trueSource = e.getSource().getEntity();
        if (trueSource != null) {
            trueSource.hurt(new EntityDamageSource("player", e.getEntityLiving()), smite);
        }
    }
}
