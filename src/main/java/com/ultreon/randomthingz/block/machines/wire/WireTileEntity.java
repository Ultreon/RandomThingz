package com.ultreon.randomthingz.block.machines.wire;

import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WireTileEntity extends BlockEntity {
    int energyStored;

    public WireTileEntity() {
        super(ModMachineTileEntities.wire);
    }

    public String getWireNetworkData() {
        if (level == null) return "world is null";

        WireNetwork net = WireNetworkManager.get(level, worldPosition);
        return net != null ? net.toString() : "null";
    }

    @Override
    public void load(BlockState state, CompoundTag compound) {
        this.energyStored = compound.getInt("EnergyStored");
        super.load(state, compound);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.putInt("EnergyStored", energyStored);
        return super.save(compound);
    }

    @Override
    public void setRemoved() {
        if (level != null) {
            WireNetworkManager.invalidateNetwork(level, worldPosition);
        }
        super.setRemoved();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (level != null && !remove && cap == CapabilityEnergy.ENERGY && side != null) {
            LazyOptional<WireNetwork> networkOptional = WireNetworkManager.getLazy(level, worldPosition);
            if (networkOptional.isPresent()) {
                return networkOptional.orElseThrow(IllegalStateException::new).getConnection(worldPosition, side).getLazyOptional().cast();
            }
        }
        return LazyOptional.empty();
    }
}
