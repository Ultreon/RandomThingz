package com.ultreon.randomthingz.block.machines.dryingrack;

import com.qsoftware.modlib.silentlib.util.PlayerUtils;
import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import com.ultreon.randomthingz.item.crafting.DryingRecipe;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.util.ParticleUtils;
import net.minecraft.block.BlockState;
import net.minecraft.core.NonNullList;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DryingRackTileEntity extends BlockEntity implements Container, TickableBlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    private final LazyOptional<IItemHandler> itemHandlerCap = LazyOptional.of(() -> new InvWrapper(this));
    private int processTime;

    public DryingRackTileEntity() {
        super(ModMachineTileEntities.dryingRack);
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
                setInventorySlotContents(0, recipe.getCraftingResult(this));
                processTime = 0;
            }
            if (processTime % 10 == 0) {
                ParticleUtils.spawn(dimension, ParticleTypes.SMOKE, pos, 1, 0.1, 0.1, 0.1, 0.01);
            }
        } else {
            processTime = 0;
        }
    }

    private boolean canWork() {
        return dimension != null && !dimension.getBlockState(pos).get(DryingRackBlock.WATERLOGGED);
    }

    @Override
    public void markModified() {
        super.markModified();
        sendUpdate();
    }

    private void sendUpdate() {
        if (this.dimension != null) {
            BlockState state = this.dimension.getBlockState(this.pos);
            this.dimension.notifyBlockUpdate(this.pos, state, state, 3);
        }
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return isEmpty();
    }

    @Override
    public boolean isEmpty() {
        return getStackInSlot(0).isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.items.get(0);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack result = ItemStackHelper.getAndSplit(this.items, index, count);
        this.markModified();
        return result;
    }

    @Override
    public ItemStack deleteStackFromSlot(int index) {
        ItemStack result = ItemStackHelper.getAndRemove(this.items, index);
        this.markModified();
        return result;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.items.set(0, stack);
        this.markModified();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        if (compound.contains("Item")) {
            setInventorySlotContents(0, ItemStack.read(compound.getCompound("Item")));
        }
        this.processTime = compound.getInt("ProcessTime");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStack stack = getItem();
        if (!stack.isEmpty()) {
            compound.put("Item", stack.write(new CompoundNBT()));
        }
        compound.putInt("ProcessTime", this.processTime);
        return compound;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 0, getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        ItemStack stack = getItem();
        if (!stack.isEmpty()) {
            nbt.put("Item", stack.write(new CompoundNBT()));
        }
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if (pkt.getNbt().contains("Item")) {
            this.items.set(0, ItemStack.read(pkt.getNbt().getCompound("Item")));
        } else {
            this.items.set(0, ItemStack.EMPTY);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.removed && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerCap.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void delete() {
        super.delete();
        itemHandlerCap.invalidate();
    }
}
