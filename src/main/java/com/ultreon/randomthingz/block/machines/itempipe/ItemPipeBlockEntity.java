package com.ultreon.randomthingz.block.machines.itempipe;

import com.ultreon.randomthingz.block.entity.ModMachines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemPipeBlockEntity extends BlockEntity {
    public ItemPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModMachines.ITEM_PIPE.get(), pos, state);
    }

    @SuppressWarnings("unused")
    public String getPipeNetworkData() {
        if (level == null) return "world is null";

        ItemPipeNetwork net = ItemPipeConnection.get(level, worldPosition);
        return net != null ? net.toString() : "null";
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
        super.load(compound);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound) {
        super.save(compound);
    }

    @Override
    public void setRemoved() {
        if (level != null) {
            ItemPipeConnection.invalidateNetwork(level, worldPosition);
        }
        super.setRemoved();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (level != null && !remove && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side != null) {
            LazyOptional<ItemPipeNetwork> networkOptional = ItemPipeConnection.getLazy(level, worldPosition);
            if (networkOptional.isPresent()) {
                return networkOptional.orElseThrow(IllegalStateException::new).getConnection(worldPosition, side).getLazyOptional().cast();
            }
        }
        return LazyOptional.empty();
    }
}
