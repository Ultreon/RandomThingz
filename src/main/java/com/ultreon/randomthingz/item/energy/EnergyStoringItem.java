package com.ultreon.randomthingz.item.energy;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.capability.EnergyStorageItemImpl;
import com.ultreon.randomthingz.client.hud.HudItem;
import com.ultreon.randomthingz.util.GraphicsUtil;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Deprecated
public abstract class EnergyStoringItem extends HudItem {
    public static final ResourceLocation CHARGE = RandomThingz.rl("charge");

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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilityProvider() {
            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                if (cap == CapabilityEnergy.ENERGY)
                    return LazyOptional.of(() -> new EnergyStorageItemImpl(stack, maxEnergy, maxReceive, maxExtract)).cast();
                return LazyOptional.empty();
            }
        };
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level dimensionIn, List<Component> tooltip, TooltipFlag flagIn) {
        // Apparently, appendHoverText can be called before caps are initialized
        if (CapabilityEnergy.ENERGY == null) return;

        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e ->
                tooltip.add(TextUtils.energyWithMax(e.getEnergyStored(), e.getMaxEnergyStored())));
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            items.add(new ItemStack(this));

            ItemStack full = new ItemStack(this);
            full.getOrCreateTag().putInt("Energy", maxEnergy);
            items.add(full);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return (int) (1 - getChargeRatio(stack));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Mth.hsvToRgb((1 + getChargeRatio(stack)) / 3.0f, 1.0f, 1.0f);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderHud(GraphicsUtil gu, Minecraft mc, ItemStack stack, LocalPlayer player) {
        // Apparently, appendHoverText can be called before caps are initialized
        if (CapabilityEnergy.ENERGY == null) return;

        int height = mc.getWindow().getGuiScaledHeight();

        Level dimensionIn = player.getCommandSenderWorld();

        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(e -> {
            int val;

            int maxEnergy = e.getMaxEnergyStored();

            if (maxEnergy == 0) {
                val = 0;
            }

            val = (int) (64d * e.getEnergyStored() / maxEnergy);

            TextureManager textureManager = mc.getTextureManager();
            RenderSystem.setShaderTexture(0, new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/energy_item/background.png"));
            gu.blit(0, height - 64, 128, 64, 0, 0, 128, 64, 128, 64);

            RenderSystem.setShaderTexture(0, new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/energy_item/bar.png"));
            gu.blit(33, height - 11, 64, 2, 0, 2, 64, 1, 64, 3);

            RenderSystem.setShaderTexture(0, new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/energy_item/bar.png"));
            gu.blit(32, height - 12, 64, 2, 0, 1, 64, 1, 64, 3);

            RenderSystem.setShaderTexture(0, new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/energy_item/bar.png"));
            gu.blit(33, height - 11, val, 2, 0, 1, val, 1, 64, 3);

            RenderSystem.setShaderTexture(0, new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/energy_item/bar.png"));
            gu.blit(32, height - 12, val, 2, 0, 0, val, 1, 64, 3);

            gu.drawItemStack(stack, 56, height - 60, "");
            gu.drawCenteredString(Math.round(e.getEnergyStored()) + " / " + Math.round(e.getMaxEnergyStored()), 64, height - 24, 0xff7f7f);
        });
    }
}
