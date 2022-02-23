package com.ultreon.randomthingz.block.machines.pipe;

import com.ultreon.randomthingz.block.entity.ModMachines;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PipeTileEntity extends BlockEntity {
    public PipeTileEntity(BlockPos pos, BlockState state) {
        super(ModMachines.PIPE.get(), pos, state);
    }

    public String getPipeNetworkData() {
        if (level == null) return "world is null";

        PipeNetwork net = PipeConnection.get(level, worldPosition);
        return net != null ? net.toString() : "null";
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
        super.load(compound);
    }

    @NotNull
    @Override
    public CompoundTag save(@NotNull CompoundTag compound) {
        return super.save(compound);
    }

    @Override
    public void setRemoved() {
        if (level != null) {
            PipeConnection.invalidateNetwork(level, worldPosition);
        }
        super.setRemoved();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (level != null && !remove && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side != null) {
            LazyOptional<PipeNetwork> networkOptional = PipeConnection.getLazy(level, worldPosition);
            if (networkOptional.isPresent()) {
                return networkOptional.orElseThrow(IllegalStateException::new).getConnection(worldPosition, side).getLazyOptional().cast();
            }
        }
        return LazyOptional.empty();
    }
}
