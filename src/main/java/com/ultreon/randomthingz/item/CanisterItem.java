package com.ultreon.randomthingz.item;

import com.qsoftware.modlib.api.IFluidContainer;
import com.ultreon.randomthingz.client.ModModelProperties;
import com.ultreon.randomthingz.item.common.ModItems;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Objects;

public class CanisterItem extends Item implements IFluidContainer {
    public CanisterItem(Properties properties) {
        super(properties);
        ModModelProperties.delayedRegister(() -> this, "fluid_level", (stack, dimension, entity) -> getFluid(stack).getAmount());
    }

    public static ItemStack getStack(@Nullable Fluid fluid) {
        return getStack(fluid, 1);
    }

    public static ItemStack getStack(@Nullable Fluid fluid, int count) {
        IItemProvider item = fluid != null ? ModItems.CANISTER : ModItems.EMPTY_CANISTER;
        ItemStack result = new ItemStack(item, count);
        if (fluid != null) {
            ResourceLocation fluidId = Objects.requireNonNull(fluid.getRegistryName());
            result.getOrCreateTag().putString("CanisterFluid", fluidId.toString());
        }
        return result;
    }

//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @org.jetbrains.annotations.Nullable CompoundNBT nbt) {
//        return new FluidCanisterWrapper(stack);
//    }

    public static String getFluidKey(ItemStack stack) {
        return stack.hasTag() ? stack.getOrCreateTag().getString("CanisterFluid") : "";
    }

    @Override
    public FluidStack getFluid(ItemStack stack) {
        if (!(stack.getItem() instanceof CanisterItem)) {
            return FluidStack.EMPTY;
        }
        ResourceLocation fluidId = ResourceLocation.tryCreate(getFluidKey(stack));
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
    public ITextComponent getDisplayName(ItemStack stack) {
        FluidStack fluid = getFluid(stack);
        ITextComponent fluidText = fluid.isEmpty() ? TextUtils.translate("misc", "empty") : fluid.getDisplayName();
        return new TranslationTextComponent(this.getTranslationId(), fluidText);
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
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            items.add(getStack(null));
            ForgeRegistries.FLUIDS.getValues().stream()
                    .filter(f -> f.isSource(f.getDefaultState()))
                    .map(CanisterItem::getStack)
                    .forEach(items::add);
        }
    }
}
