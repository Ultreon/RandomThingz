package com.qtech.forgemod.modules.tiles.tileentities;

import com.qtech.forgemod.container.CrateContainer;
import com.qtech.forgemod.modules.tiles.ModTileEntities;
import com.qtech.forgemod.modules.tiles.blocks.furniture.WoodenCrateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
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
public class CrateTileEntity extends LockableLootTileEntity {
    // Item handler.
    private final IItemHandlerModifiable items = createHandler();
    // Amount players using.
    protected int numPlayersUsing;
    // Chest contents.
    private NonNullList<ItemStack> chestContents = NonNullList.withSize(36, ItemStack.EMPTY);
    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

    public CrateTileEntity(TileEntityType<?> typeIn) {
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
    public int getSizeInventory() {
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

    public IInventory getInventory() {
        return new Inventory(getItems().toArray(new ItemStack[]{}));
    }

    @Override
    public ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.wooden_crate");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new CrateContainer(id, player, this);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.writeAllItems(compound, this.chestContents);
        }
        return compound;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        this.chestContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.readAllItems(compound, this.chestContents);
        }
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    private void playSound(SoundEvent sound) {
        double dx = (double) this.pos.getX() + 0.5d;
        double dy = (double) this.pos.getY() + 0.5d;
        double dz = (double) this.pos.getZ() + 0.5d;

        Objects.requireNonNull(this.dimension).playSound(null, dx, dy, dz, sound, SoundCategory.BLOCKS, 0.5f, this.dimension.rand.nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.receiveClientEvent(id, type);
        }
    }

    @Override
    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();
        Objects.requireNonNull(this.dimension);
        if (block instanceof WoodenCrateBlock) {
            this.dimension.addBlockEvent(this.pos, block, 1, this.numPlayersUsing);
            this.dimension.notifyNeighborsOfStateChange(this.pos, block);
        }
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public int getPlayersUsing(IBlockReader reader, BlockPos pos) {
        BlockState blockState = reader.getBlockState(pos);
        if (blockState.hasTileEntity()) {
            TileEntity tileEntity = reader.getTileEntity(pos);
            if (tileEntity instanceof CrateTileEntity) {
                return ((CrateTileEntity) tileEntity).numPlayersUsing;
            }
        }
        return 0;
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
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
    public void delete() {
        assert dimension != null;
        BlockState blockState = dimension.getBlockState(pos);
        for (int i = 0; i < chestContents.size(); i++) {
            ItemStack stack = chestContents.get(i);
            chestContents.set(i, ItemStack.EMPTY);
            TileEntity tileEntity = blockState.hasTileEntity() ? dimension.getTileEntity(pos) : null;

            //noinspection ConstantConditions
            Block.spawnDrops(blockState, dimension, this.pos.add(0, 1.5, 0), tileEntity, null, stack);
        }

        super.delete();
        if (itemHandler != null) {
            itemHandler.invalidate();
        }
    }
}
