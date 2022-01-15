package com.ultreon.randomthingz.block.machines.itempipe;

import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemPipeTileEntity extends BlockEntity {
    public ItemPipeTileEntity() {
        super(ModMachineTileEntities.pipe);
    }

    @SuppressWarnings("unused")
    public String getPipeNetworkData() {
        if (level == null) return "world is null";

        ItemPipeNetwork net = ItemPipeNetworkManager.get(level, worldPosition);
        return net != null ? net.toString() : "null";
    }

    @Override
    public void load(@NotNull BlockState state, @NotNull CompoundTag compound) {
        super.load(state, compound);
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compound) {
        return super.save(compound);
    }

    @Override
    public void setRemoved() {
        if (level != null) {
            ItemPipeNetworkManager.invalidateNetwork(level, worldPosition);
        }
        super.setRemoved();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (level != null && !remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != null) {
            LazyOptional<ItemPipeNetwork> networkOptional = ItemPipeNetworkManager.getLazy(level, worldPosition);
            if (networkOptional.isPresent()) {
                return networkOptional.orElseThrow(IllegalStateException::new).getConnection(worldPosition, side).getLazyOptional().cast();
            }
        }
        return LazyOptional.empty();
    }
}
