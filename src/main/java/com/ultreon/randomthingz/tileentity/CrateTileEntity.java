package com.ultreon.randomthingz.tileentity;

import com.ultreon.randomthingz.block.entity.ModTileEntities;
import com.ultreon.randomthingz.block.furniture.WoodenCrateBlock;
import com.ultreon.randomthingz.inventory.container.CrateContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Crate tile entity class.
 *
 * @author Qboi123
 * @see WoodenCrateBlock
 */
public class CrateTileEntity extends RandomizableContainerBlockEntity {
    // Item handler.
    private final IItemHandlerModifiable items = createHandler();
    // Amount players using.
    protected int numPlayersUsing;
    // Chest contents.
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(36, ItemStack.EMPTY);
    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

    public CrateTileEntity(BlockEntityType<?> typeIn) {
        super(typeIn);
    }

    public CrateTileEntity() {
        this(ModTileEntities.EXAMPLE_CHEST.get());
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void swapContents(CrateTileEntity te, CrateTileEntity otherTe) {
        NonNullList<ItemStack> list = te.getItems();
        te.setItems(otherTe.getItems());
        otherTe.setItems(list);
    }

    @Override
    public int getContainerSize() {
        return 36;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        this.chestContents = items;
    }

    public Container getInventory() {
        return new SimpleContainer(getItems().toArray(new ItemStack[]{}));
    }

    @Override
    public Component getDefaultName() {
        return new TranslatableComponent("container.wooden_crate");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new CrateContainer(id, player, this);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.chestContents);
        }
        return compound;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void load(BlockState state, CompoundTag compound) {
        super.load(state, compound);
        this.chestContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compound)) {
            ContainerHelper.loadAllItems(compound, this.chestContents);
        }
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    private void playSound(SoundEvent sound) {
        double dx = (double) this.worldPosition.getX() + 0.5d;
        double dy = (double) this.worldPosition.getY() + 0.5d;
        double dz = (double) this.worldPosition.getZ() + 0.5d;

        Objects.requireNonNull(this.level).playSound(null, dx, dy, dz, sound, SoundSource.BLOCKS, 0.5f, this.level.random.nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    @Override
    public void startOpen(Player player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();
        Objects.requireNonNull(this.level);
        if (block instanceof WoodenCrateBlock) {
            this.level.blockEvent(this.worldPosition, block, 1, this.numPlayersUsing);
            this.level.updateNeighborsAt(this.worldPosition, block);
        }
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public int getPlayersUsing(BlockGetter reader, BlockPos pos) {
        BlockState blockState = reader.getBlockState(pos);
        if (blockState.hasTileEntity()) {
            BlockEntity tileEntity = reader.getBlockEntity(pos);
            if (tileEntity instanceof CrateTileEntity) {
                return ((CrateTileEntity) tileEntity).numPlayersUsing;
            }
        }
        return 0;
    }

    @Override
    public void clearCache() {
        super.clearCache();
        if (this.itemHandler != null) {
            this.itemHandler.invalidate();
            this.itemHandler = null;
        }
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    @Override
    public void setRemoved() {
        assert level != null;
        BlockState blockState = level.getBlockState(worldPosition);
        for (int i = 0; i < chestContents.size(); i++) {
            ItemStack stack = chestContents.get(i);
            chestContents.set(i, ItemStack.EMPTY);
            BlockEntity tileEntity = blockState.hasTileEntity() ? level.getBlockEntity(worldPosition) : null;

            //noinspection ConstantConditions
            Block.dropResources(blockState, level, this.worldPosition.offset(0, 1.5, 0), tileEntity, null, stack);
        }

        super.setRemoved();
        if (itemHandler != null) {
            itemHandler.invalidate();
        }
    }
}
