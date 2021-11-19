package com.ultreon.randomthingz.block.machines.wire;

import com.ultreon.randomthingz.tiles.ModMachineTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WireTileEntity extends TileEntity {
    int energyStored;

    public WireTileEntity() {
        super(ModMachineTileEntities.wire);
    }

    public String getWireNetworkData() {
        if (dimension == null) return "world is null";

        WireNetwork net = WireNetworkManager.get(dimension, pos);
        return net != null ? net.toString() : "null";
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        this.energyStored = compound.getInt("EnergyStored");
        super.read(state, compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("EnergyStored", energyStored);
        return super.write(compound);
    }

    @Override
    public void delete() {
        if (dimension != null) {
            WireNetworkManager.invalidateNetwork(dimension, pos);
        }
        super.delete();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (dimension != null && !removed && cap == CapabilityEnergy.ENERGY && side != null) {
            LazyOptional<WireNetwork> networkOptional = WireNetworkManager.getLazy(dimension, pos);
            if (networkOptional.isPresent()) {
                return networkOptional.orElseThrow(IllegalStateException::new).getConnection(pos, side).getLazyOptional().cast();
            }
        }
        return LazyOptional.empty();
    }
}
