package com.qtech.forgemod.modules.items.objects.type;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.capability.EnergyStorageItemImpl;
import com.qtech.forgemod.hud.HudItem;
import com.qtech.forgemod.util.GraphicsUtil;
import com.qtech.forgemod.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EnergyStoringItem extends HudItem {
    public static final ResourceLocation CHARGE = new ResourceLocation(QForgeMod.modId, "charge");

    private final int maxEnergy;
    private final int maxReceive;
    private final int maxExtract;

    public EnergyStoringItem(Properties properties, int maxEnergy, int maxTransfer) {
        this(properties, maxEnergy, maxTransfer, maxTransfer);
    }

    public EnergyStoringItem(Properties properties, int maxEnergy, int maxReceive, int maxExtract) {
        super(properties);
        this.maxEnergy = maxEnergy;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public static float getChargeRatio(ItemStack stack) {
        LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);
        if (optional.isPresent()) {
            IEnergyStorage energyStorage = optional.orElseThrow(IllegalStateException::new);
            return (float) energyStorage.getEnergyStored() / energyStorage.getMaxEnergyStored();
        }
        return 0;
    }

    public static IEnergyStorage getEnergyStorage(ItemStack stack) {
        LazyOptional<IEnergyStorage> optional = stack.getCapability(CapabilityEnergy.ENERGY);
        if (optional.isPresent()) {
            return optional.orElseThrow(IllegalStateException::new);
        }
        return null;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == CapabilityEnergy.ENERGY)
                    return LazyOptional.of(() -> new EnergyStorageItemImpl(stack, maxEnergy, maxReceive, maxExtract)).cast();
                return LazyOptional.empty();
            }
        };
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World dimensionIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        // Apparently, addInformation can be called before caps are initialized
        if (CapabilityEnergy.ENERGY == null) return;

        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e ->
                tooltip.add(TextUtils.energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored())));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));

            ItemStack full = new ItemStack(this);
            full.getOrCreateTag().putInt("Energy", maxEnergy);
            items.add(full);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1 - getChargeRatio(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.hsvToRGB((1 + getChargeRatio(stack)) / 3.0F, 1.0F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderHud(GraphicsUtil gu, Minecraft mc, ItemStack stack, ClientPlayerEntity player) {
        // Apparently, addInformation can be called before caps are initialized
        if (CapabilityEnergy.ENERGY == null) return;

        int height = mc.getMainWindow().getScaledHeight();

        World dimensionIn = player.getEntityDimension();

        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> {
            int val;

            int maxEnergy = e.getMaxEnergyStored();

            if (maxEnergy == 0) {
                val = 0;
            }

            val = (int)(64d * e.getEnergyStored() / maxEnergy);

            TextureManager textureManager = mc.getTextureManager();
            textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/energy_item/background.png"));
            gu.blit(0, height - 64, 128, 64, 0, 0, 128, 64, 128, 64);

            textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/energy_item/bar.png"));
            gu.blit(33, height - 11, 64, 2, 0, 2, 64, 1, 64, 3);

            textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/energy_item/bar.png"));
            gu.blit(32, height - 12, 64, 2, 0, 1, 64, 1, 64, 3);

            textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/energy_item/bar.png"));
            gu.blit(33, height - 11, val, 2, 0, 1, val, 1, 64, 3);

            textureManager.bindTexture(new ResourceLocation(QForgeMod.modId, "textures/gui/energy_item/bar.png"));
            gu.blit(32, height - 12, val, 2, 0, 0, val, 1, 64, 3);

            gu.drawItemStack(stack, 56, height - 60, "");
            gu.drawCenteredString(Math.round(e.getEnergyStored()) + " / " + Math.round(e.getMaxEnergyStored()), 64, height - 24, 0xff7f7f);
        });
    }
}
