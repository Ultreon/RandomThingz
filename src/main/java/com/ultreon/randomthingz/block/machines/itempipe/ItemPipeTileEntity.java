package com.ultreon.randomthingz.block.machines.itempipe;

import com.ultreon.randomthingz.modules.tiles.ModMachineTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemPipeTileEntity extends TileEntity {
    public ItemPipeTileEntity() {
        super(ModMachineTileEntities.pipe);
    }

    @SuppressWarnings("unused")
    public String getPipeNetworkData() {
        if (dimension == null) return "world is null";

        ItemPipeNetwork net = ItemPipeNetworkManager.get(dimension, pos);
        return net != null ? net.toString() : "null";
    }

    @Override
    public void read(@NotNull BlockState state, @NotNull CompoundNBT compound) {
        super.read(state, compound);
    }

    @Override
    public @NotNull CompoundNBT write(@NotNull CompoundNBT compound) {
        return super.write(compound);
    }

    @Override
    public void delete() {
        if (dimension != null) {
            ItemPipeNetworkManager.invalidateNetwork(dimension, pos);
        }
        super.delete();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (dimension != null && !removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != null) {
            LazyOptional<ItemPipeNetwork> networkOptional = ItemPipeNetworkManager.getLazy(dimension, pos);
            if (networkOptional.isPresent()) {
                return networkOptional.orElseThrow(IllegalStateException::new).getConnection(pos, side).getLazyOptional().cast();
            }
        }
        return LazyOptional.empty();
    }
}
