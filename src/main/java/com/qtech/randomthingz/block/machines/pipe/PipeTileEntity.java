package com.qtech.randomthingz.block.machines.pipe;

import com.qtech.randomthingz.modules.tiles.ModMachineTileEntities;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PipeTileEntity extends TileEntity {
    public PipeTileEntity() {
        super(ModMachineTileEntities.pipe);
    }

    public String getPipeNetworkData() {
        if (dimension == null) return "world is null";

        PipeNetwork net = PipeNetworkManager.get(dimension, pos);
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
            PipeNetworkManager.invalidateNetwork(dimension, pos);
        }
        super.delete();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (dimension != null && !removed && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side != null) {
            LazyOptional<PipeNetwork> networkOptional = PipeNetworkManager.getLazy(dimension, pos);
            if (networkOptional.isPresent()) {
                return networkOptional.orElseThrow(IllegalStateException::new).getConnection(pos, side).getLazyOptional().cast();
            }
        }
        return LazyOptional.empty();
    }
}
