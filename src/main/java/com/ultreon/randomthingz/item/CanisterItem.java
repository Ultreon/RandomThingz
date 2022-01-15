package com.ultreon.randomthingz.item;

import com.qsoftware.modlib.api.IFluidContainer;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;

public class CanisterItem extends Item implements IFluidContainer {
    public CanisterItem(Properties properties) {
        super(properties);
        //addPropertyOverride(RandomThingz.getId("fluid_level"), (stack, dimension, entity) -> getFluid(stack).getAmount());
    }

    public static ItemStack getStack(@Nullable Fluid fluid) {
        return getStack(fluid, 1);
    }

    public static ItemStack getStack(@Nullable Fluid fluid, int count) {
        ItemLike item = fluid != null ? ModItems.CANISTER : ModItems.EMPTY_CANISTER;
        ItemStack result = new ItemStack(item, count);
        if (fluid != null) {
            ResourceLocation fluidId = Objects.requireNonNull(fluid.getRegistryName());
            result.getOrCreateTag().putString("CanisterFluid", fluidId.toString());
        }
        return result;
    }

/*    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new FluidCanisterWrapper(stack);
    }*/

    public static String getFluidKey(ItemStack stack) {
        return stack.hasTag() ? stack.getOrCreateTag().getString("CanisterFluid") : "";
    }

    @Override
    public FluidStack getFluid(ItemStack stack) {
        if (!(stack.getItem() instanceof CanisterItem)) {
            return FluidStack.EMPTY;
        }
        ResourceLocation fluidId = ResourceLocation.tryParse(getFluidKey(stack));
        if (fluidId == null) {
            return FluidStack.EMPTY;
        }
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        if (fluid == null) {
            return FluidStack.EMPTY;
        }
        return new FluidStack(fluid, 1000);
    }

    @Override
    public ItemStack fillWithFluid(ItemStack empty, FluidStack fluid) {
        return getStack(fluid.getFluid());
    }

    @Override
    public Component getName(ItemStack stack) {
        FluidStack fluid = getFluid(stack);
        Component fluidText = fluid.isEmpty() ? TextUtils.translate("misc", "empty") : fluid.getDisplayName();
        return new TranslatableComponent(this.getDescriptionId(), fluidText);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return new ItemStack(ModItems.EMPTY_CANISTER);
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (allowdedIn(group)) {
            items.add(getStack(null));
            ForgeRegistries.FLUIDS.getValues().stream()
                    .filter(f -> f.isSource(f.defaultFluidState()))
                    .map(CanisterItem::getStack)
                    .forEach(items::add);
        }
    }
}
