package com.qsoftware.forgemod.objects.block.quarry;

import com.qsoftware.forgemod.api.RedstoneMode;
import com.qsoftware.forgemod.init.ModTileEntities;
import com.qsoftware.forgemod.objects.block.AbstractMachineBaseTileEntity;
import com.qsoftware.forgemod.util.MachineTier;
import com.qsoftware.forgemod.util.TextUtil;
import com.qsoftware.forgemod.util.helpers.NBTHelper;
import com.qsoftware.silent.utils.EnumUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class QuarryTileEntity extends AbstractMachineBaseTileEntity {
    // Constants
    public static final int DEPTH = 5;
    public static final int ENERGY_PER_BLOCK = 100;
    public static final int FIELDS_COUNT = 20;
    // Fields
    public int x;
    public int y;
    public int z;
    // Protected fields.
    protected int tick;
    protected boolean initialized = false;
    protected final IIntArray fields = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                //Minecraft actually sends fields as shorts, so we need to split energy into 2 fields
                case 0:
                    // Energy lower bytes
                    return getEnergyStored() & 0xFFFF;
                case 1:
                    // Energy upper bytes
                    return (getEnergyStored() >> 16) & 0xFFFF;
                case 2:
                    return getMaxEnergyStored() & 0xFFFF;
                case 3:
                    return (getMaxEnergyStored() >> 16) & 0xFFFF;
                case 4:
                    return redstoneMode.ordinal();
                case 6:
                    return getCurrentX() & 0xFFFF;
                case 7:
                    return (getCurrentX() >> 16) & 0xFFFF;
                case 8:
                    return getCurrentY() & 0xFFFF;
                case 9:
                    return (getCurrentY() >> 16) & 0xFFFF;
                case 10:
                    return getCurrentZ() & 0xFFFF;
                case 11:
                    return (getCurrentZ() >> 16) & 0xFFFF;
                case 12:
                    return isInitialized() ? 1 : 0;
                case 13:
                    return isDone() ? 1 : 0;
                case 14:
                    return getBlocksRemaining() & 0xFFFF;
                case 15:
                    return (getBlocksRemaining() >> 16) & 0xFFFF;
                case 16:
                    return getTotalBlocks() & 0xFFFF;
                case 17:
                    return (getTotalBlocks() >> 16) & 0xFFFF;
                case 18:
                    return isIllegalPosition() ? 1 : 0;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 4:
                    redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
                    break;
            }
        }

        @Override
        public int size() {
            return FIELDS_COUNT;
        }
    };

    public QuarryTileEntity() {
        super(ModTileEntities.quarry, 2, 10_000, 100, 0, MachineTier.STANDARD);
    }

    private int getBlocksRemaining() {
        return (x - (pos.getX() + 1) + 1) + (z - (pos.getZ() + 1) + 1) + ((y - 1) - DEPTH);
    }

    private int getTotalBlocks() {
        return (pos.getY() - DEPTH) * 9;
    }

    private boolean isIllegalPosition() {
        return this.pos.getY() <= DEPTH + 1;
    }

    private int getEnergyPerOperation() {
        return (int) (ENERGY_PER_BLOCK * getUpgradesEnergyMultiplier());
    }

    public boolean isInitialized() {
        return initialized;
    }

    public int getCurrentX() {
        return x;
    }

    public int getCurrentY() {
        return y;
    }

    public int getCurrentZ() {
        return z;
    }

    public boolean isDone() {
        return y < DEPTH;
    }

    @Override
    public void tick() {
        if (!initialized) {
            init();
        }

        if (!canMachineRun()) return;

        tick++;

        if (tick == 5) {
            tick = 0;
            if (y >= DEPTH) execute();
        }
        energy.consumeEnergy(getEnergyPerOperation());
    }

    private void init() {
        initialized = true;
        this.x = this.pos.getX() - 1;
        this.y = this.pos.getY() - 1;
        this.z = this.pos.getZ() - 1;
        tick = 0;
    }

    private void execute() {
        BlockPos posToBreak = new BlockPos(this.x, this.y, this.z);

        assert this.world != null;
        this.world.getBlockState(posToBreak).getBlock();
        destroyBlock(posToBreak, true, null);

        x++;
        if (x > this.pos.getX() + 1) {
            x = this.pos.getX() - 1;
            z++;
        }
        if (z > this.pos.getZ() + 1) {
            z = this.pos.getZ() - 1;
            y--;
        }

//        Block[] blocksRemoved = new Block[9];
//        for (int x = 0; x < 3; x++) {
//            for (int z = 0; z< 3; z++) {
//                BlockPos posToBreak = new BlockPos(this.x + x, this.y, this.z + z);
//
//                assert this.world != null;
//                blocksRemoved[index] = this.world.getBlockState(posToBreak).getBlock();
//                destroyBlock(posToBreak, true, null);
//                index++;
//            }
//        }
    }

    @SuppressWarnings({"UnusedReturnValue", "SameParameterValue"})
    private boolean destroyBlock(BlockPos pos, boolean dropBlock, @Nullable Entity entity) {
        BlockState blockState;

        assert world != null;
        blockState = world.getBlockState(pos);
        if (blockState.isAir(world, pos)) return false;
        else {
            FluidState fluidState = world.getFluidState(pos);
            world.playEvent(2001, pos, Block.getStateId(blockState));
            if (dropBlock) {
                TileEntity tileEntity = blockState.hasTileEntity() ? world.getTileEntity(pos) : null;
                Block.spawnDrops(blockState, world, this.pos.add(0, 1.5, 0), tileEntity, entity, ItemStack.EMPTY);

//                BlockPos above = this.pos.add(0, 1, 0);
//                @Nullable TileEntity aboveTE = this.world.getTileEntity(above);
//                if (aboveTE != null) {
//                    if (aboveTE instanceof IInventory) {
//                        IInventory inventory = (IInventory) aboveTE;
//                    }
//                }
            }
            return world.setBlockState(pos, fluidState.getBlockState(), 3);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("initValues", NBTHelper.toNBT(this));
        return super.write(compound);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        CompoundNBT initValues = compound.getCompound("initValues");
        if (initValues != null) {
            this.x = initValues.getInt("x");
            this.y = initValues.getInt("y");
            this.z = initValues.getInt("z");
            this.tick = 0;
            initialized = true;
            return;
        }
        init();
    }

    private boolean canMachineRun() {
        return world != null
                && getEnergyStored() >= getEnergyPerOperation()
                && redstoneMode.shouldRun(world.getRedstonePowerFromNeighbors(pos) > 0)
                && y >= DEPTH;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return TextUtil.translate("container", "quarry");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new QuarryContainer(id, player, this, this.fields);
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }
}
