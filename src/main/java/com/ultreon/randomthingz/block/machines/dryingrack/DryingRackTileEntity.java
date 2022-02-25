package com.ultreon.randomthingz.block.machines.dryingrack;

import com.ultreon.modlib.silentlib.util.PlayerUtils;
import com.ultreon.randomthingz.block.entity.ModMachines;
import com.ultreon.randomthingz.item.crafting.DryingRecipe;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.util.ParticleUtils;
import com.ultreon.texturedmodels.tileentity.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DryingRackTileEntity extends BlockEntity implements Container, Tickable {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> new InvWrapper(this));
    private int processTime;

    public DryingRackTileEntity(BlockPos pos, BlockState state) {
        super(ModMachines.DRYING_RACK.get(), pos, state);
    }

    public ItemStack getItem() {
        return items.get(0);
    }

    public boolean interact(Player player) {
        ItemStack stack = getItem(0);
        if (!stack.isEmpty()) {
            // Remove hanging item
            PlayerUtils.giveItem(player, stack);
            setItem(0, ItemStack.EMPTY);
            return true;
        } else {
            // Hang item on rack
            ItemStack heldItem = player.getMainHandItem();
            if (!heldItem.isEmpty()) {
                setItem(0, heldItem.split(1));
                return true;
            }
            return false;
        }
    }

    @Override
    public void tick() {
        if (this.level == null || this.level.isClientSide || isEmpty()) return;

        DryingRecipe recipe = this.level.getRecipeManager().getRecipeFor(ModRecipes.Types.DRYING, this, this.level).orElse(null);
        if (recipe != null && canWork()) {
            ++processTime;
            if (processTime >= recipe.getProcessTime()) {
                setItem(0, recipe.getResultItem());
                processTime = 0;
            }
            if (processTime % 10 == 0) {
                ParticleUtils.spawn(level, ParticleTypes.SMOKE, worldPosition, 1, 0.1, 0.1, 0.1, 0.01);
            }
        } else {
            processTime = 0;
        }
    }

    private boolean canWork() {
        return level != null && !level.getBlockState(worldPosition).getValue(DryingRackBlock.WATERLOGGED);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        sendUpdate();
    }

    private void sendUpdate() {
        if (this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
        }
    }

    @Override
    public int getContainerSize() {
        return 1;
    }
    
    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return isEmpty();
    }

    @Override
    public boolean isEmpty() {
        return getItem(0).isEmpty();
    }

    @Override
    public ItemStack getItem(int index) {
        return this.items.get(0);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack result = ContainerHelper.removeItem(this.items, index, count);
        this.setChanged();
        return result;
    }
    
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack result = ContainerHelper.takeItem(this.items, index);
        this.setChanged();
        return result;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        this.items.set(0, stack);
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("Item")) {
            setItem(0, ItemStack.of(compound.getCompound("Item")));
        }
        this.processTime = compound.getInt("ProcessTime");
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        ItemStack stack = getItem();
        if (!stack.isEmpty()) {
            compound.put("Item", stack.save(new CompoundTag()));
        }
        compound.putInt("ProcessTime", this.processTime);
        return compound;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        ItemStack stack = getItem();
        if (!stack.isEmpty()) {
            nbt.put("Item", stack.save(new CompoundTag()));
        }
        return nbt;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if (pkt.getTag().contains("Item")) {
            this.items.set(0, ItemStack.of(pkt.getTag().getCompound("Item")));
        } else {
            this.items.set(0, ItemStack.EMPTY);
        }
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerCap.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandlerCap.invalidate();
    }
}
