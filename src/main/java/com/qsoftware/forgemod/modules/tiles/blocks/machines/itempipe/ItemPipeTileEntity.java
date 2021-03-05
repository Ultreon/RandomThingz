package com.qsoftware.forgemod.modules.tiles.blocks.machines.itempipe;

import com.qsoftware.forgemod.modules.tiles.ModMachineTileEntities;
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
        if (world == null) return "world is null";

        ItemPipeNetwork net = ItemPipeNetworkManager.get(world, pos);
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
    public void remove() {
        if (world != null) {
            ItemPipeNetworkManager.invalidateNetwork(world, pos);
        }
        super.remove();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (world != null && !removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != null) {
            LazyOptional<ItemPipeNetwork> networkOptional = ItemPipeNetworkManager.getLazy(world, pos);
            if (networkOptional.isPresent()) {
                return networkOptional.orElseThrow(IllegalStateException::new).getConnection(pos, side).getLazyOptional().cast();
            }
        }
        return LazyOptional.empty();
    }
}
