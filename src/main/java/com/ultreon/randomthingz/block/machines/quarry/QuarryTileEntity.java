package com.ultreon.randomthingz.block.machines.quarry;

import com.qsoftware.modlib.api.RedstoneMode;
import com.qsoftware.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.block.entity.ModMachineTileEntities;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseTileEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.item.upgrade.MachineUpgrades;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Quarry Tile Entity.
 * A nice piece of work!
 *
 * @author Qboi123
 */
@SuppressWarnings({"deprecation", "CommentedOutCode"})
public class QuarryTileEntity extends AbstractMachineBaseTileEntity {
    // Constants
    public static final int DEPTH = 5;
    public static final int ENERGY_PER_TICK = 15;
    public static final int FIELDS_COUNT = 22;

    // Fields
    private int x;
    private int y;
    private int z;

    private float breakProgress;
    private float breakProcessTime;

    private Status status = Status.NOT_INITIALIZED;

    // Protected fields.
    protected int tick;
    protected boolean initialized = false;
    protected final ContainerData fields = new ContainerData() {
        /**
         * Request a attribute value.
         *
         * Values:
         *   0, 1 : Energy stored.
         *   2, 3 : Maximum energy stored.
         *   4    : Redstone mode ordinal.
         *   6, 7 : Current mining X.
         *   8, 9 : Current mining Y.
         *   10,11: Current mining Z.
         *   12,13: Progress for mining block.
         *   14,15: Blocks remaining.
         *   16,17: Total blocks.
         *   18,19: Process time for mining block.
         *   20,21: Status ordinal.
         *
         * Meaning:
         *   INDEX(S): Description
         *
         *   For 2 values (an int):
         *     LSB,MSB: Description
         *
         * @param index the request index.
         * @return the requested value.
         * @see #set(int, int)
         */
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
                    return QuarryTileEntity.this.redstoneMode.ordinal();
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
//                case 12:
//                    return getBreakProgress() & 0xFFFF;
//                case 13:
//                    return (getBreakProgress() >> 16) & 0xFFFF;
                case 14:
                    return getBlocksRemaining() & 0xFFFF;
                case 15:
                    return (getBlocksRemaining() >> 16) & 0xFFFF;
                case 16:
                    return getTotalBlocks() & 0xFFFF;
                case 17:
                    return (getTotalBlocks() >> 16) & 0xFFFF;
//                case 18:
//                    return getBreakProcessTime() & 0xFFFF;
//                case 19:
//                    return (getBreakProcessTime() >> 16) & 0xFFFF;
                case 20:
                    return getStatus().ordinal() & 0xFFFF;
                case 21:
                    return (getStatus().ordinal() >> 16) & 0xFFFF;
                default:
                    return 0;
            }
        }

        /**
         * Request to set a value.
         *
         * @param index request index, see {@linkplain #get(int)}
         * @param value value to set.
         * @see #get(int)
         */
        @SuppressWarnings("SwitchStatementWithTooFewBranches")
        @Override
        public void set(int index, int value) {
            switch (index) {
                case 4:
                    QuarryTileEntity.this.redstoneMode = EnumUtils.byOrdinal(value, RedstoneMode.IGNORED);
                    break;
            }
        }

        /**
         * Get size of the int array.
         *
         * @return the size.
         */
        @Override
        public int getCount() {
            return FIELDS_COUNT;
        }
    };

    public float getBreakProgress() {
        return breakProgress;
    }

    public float getBreakProcessTime() {
        return breakProcessTime;
    }

    /**
     * @return get the status of the quarry.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Constructor.
     */
    public QuarryTileEntity() {
        super(ModMachineTileEntities.quarry, 0, 10_000, 100, 0, MachineTier.SUPER);
    }

    /**
     * @return the amount of remaining blocks to mine.
     */
    @SuppressWarnings({"UnnecessaryLocalVariable", "unused"})
    private int getBlocksRemaining() {
        int ax = this.x + 1; // AX // -1 + 1 = 0 //  1 + 1 = 2
        int bx = this.x + 2; // BX // -1 + 2 = 1 // 1 + 2 = 3
        int az = this.z + 1; // AZ // -1 + 1 = 0 // 1 + 1 + 2
        int bz = this.z + 2; // BZ // -1 + 2 = 1 // 1 + 2 = 3

        int c;
//        if (bx == 3) {
        c = 8 - (ax + (az * 3));
//        } else {
//            c = ax * bz + az;
//        }
        int d = c + (this.y - 1 - DEPTH) * 9;

        return d;
//        return (this.x - (this.pos.getX() + 1) + 1) + (this.z - (this.pos.getZ() + 1) + 1) + ((this.y - 1) - DEPTH);
    }

    /**
     * @return the total blocks to mine.
     */
    private int getTotalBlocks() {
        return (this.worldPosition.getY() - DEPTH) * 9;
    }

    /**
     * @return true if the quarry is on a illegal position, false otherwise.
     */
    public boolean isIllegalPosition() {
        return this.worldPosition.getY() <= DEPTH + 1;
    }

    /**
     * @return the amount of FE energy per operation needed.
     */
    public int getEnergyUsedPerTick() {
        return (int) (ENERGY_PER_TICK * getUpgradesEnergyMultiplier());
    }

    /**
     * @return true if the quarry is initialized, false otherwise.
     */
    public boolean isInitialized() {
        return this.initialized;
    }

    /**
     * @return the current mining x coordinate.
     */
    public int getCurrentX() {
        return this.x;
    }

    /**
     * @return the current mining z coordinate.
     */
    public int getCurrentY() {
        return this.y;
    }

    /**
     * @return the current mining z coordinate.
     */
    public int getCurrentZ() {
        return this.z;
    }

    /**
     * Check if the quarry is done.
     *
     * @return true if the quarry is done, false otherwise.
     */
    public boolean isDone() {
        return this.y < DEPTH;
    }

    /**
     * <h1>Tick event.</h1>
     * <h2>First tick:</h2>
     * initialize the quarry if not initialized.<br>
     * <br>
     * <h2>Other ticks:</h2>
     * <ol>
     *   <li>Check if there's enough energy for operation.<br>
     *       If not, set the status to not enough energy, and send blockstate update for inactive state.</li>
     *   <li>Check if the operation should run based on the redstone mode and input.<br>
     *       If it shouldn't, set the status to paused and send blockstate update for inactive state.</li>
     *   <li>Check if the current Y level is higher or equal to the lowest allowed Y level.<br>
     *       If not, set the status to done, and send blockstate update for inactive state.</li>
     *   <li>Check if the tile entity is at a legal position.<br>
     *       If not, set the status to illegal position, and send blockstate update for inactive state.</li>
     *   <li>Get block position of currently mining block.</li>
     *   <li>If dimension is null or is remote, then return.</li>
     *   <li>Set status to no problem</li>
     *   <li>If the quarry blockstate property for lit is false, send an update for active state.</li>
     *   <li>If the break progress is larger or equals the break process speed,<br>
     *       <ol>
     *           <li>Set break progress to 0.</li>
     *           <li>Destroy current block.</li>
     *           <li>Advance in X, then Z and then Y.</li>
     *           <li>Get block position from the new coordinates.</li>
     *           <li>Get block state at the new block position.</li>
     *           <li>Set break process time based on the hardness of the new mining block.</li>
     *       </ol>
     *       Otherwise:
     *       <ol>
     *           <li>Calculate block break animation index.</li>
     *           <li>Send block break animation to dimension, with breaker id -1 for current mining block.</li>
     *           <li>Advance break progress by 0.04f * the process speed upgrade count.</li>
     *       </ol>
     *   </li>
     *
     * </ol>
     */
    @Override
    public void tick() {
        if (this.level == null) {
            this.status = Status.UNIDENTIFIED_WORLD;
            return;
        }

        if (!this.initialized) {
            this.status = Status.NOT_ENOUGH_ENERGY;
            setInactiveState();
            initialize();
            return;
        } else if (this.getEnergyStored() < this.getEnergyUsedPerTick()) {
            this.status = Status.NOT_ENOUGH_ENERGY;
            setInactiveState();
            return;
        } else if (!this.redstoneMode.shouldRun(this.level.getBestNeighborSignal(this.worldPosition) > 0)) {
            this.status = Status.PAUSED;
            setInactiveState();
            return;
        } else if (this.y < DEPTH) {
            this.status = Status.DONE;
            setInactiveState();
            return;
        } else if (isIllegalPosition()) {
            this.status = Status.ILLEGAL_POSITION;
            setInactiveState();
            return;
        }

        BlockPos posToBreak = new BlockPos(this.x, this.y, this.z);

        if (level == null || level.isClientSide) {
            return;
        }

//        this.dimension.getBlockState(posToBreak).getBlock();

        this.status = Status.NO_PROBLEM;
        if (!level.getBlockState(worldPosition).getValue(AbstractFurnaceBlock.LIT)) {
            sendUpdate(getActiveState(this.level.getBlockState(this.worldPosition)));
        }

        if (breakProgress >= breakProcessTime) {
            // Create result
            breakProgress = 0;
            destroyBlock(posToBreak, true, null);

            this.x++;
            if (this.x > this.worldPosition.getX() + (1 + (getUpgradeCount(MachineUpgrades.RANGE)))) {
                this.x = this.worldPosition.getX() - (1 + (getUpgradeCount(MachineUpgrades.RANGE)));
                this.z++;
            }
            if (this.z > this.worldPosition.getZ() + (1 + (getUpgradeCount(MachineUpgrades.RANGE)))) {
                this.z = this.worldPosition.getZ() - (1 + (getUpgradeCount(MachineUpgrades.RANGE)));
                this.y--;
            }

            BlockPos newPos = new BlockPos(this.x, this.y, this.z);
            BlockState blockState = level.getBlockState(newPos);
            breakProcessTime = blockState.getDestroySpeed(level, worldPosition);
        } else {
            int i = (int) ((breakProcessTime / breakProgress) * 10.0F);
            level.destroyBlockProgress(this.worldPosition.hashCode(), posToBreak, i);
            breakProgress += 0.2f * ((getUpgradeCount(MachineUpgrades.PROCESSING_SPEED) * 2f) + 1f);
        }

        // Process
//        energy.consumeEnergy((int) (getEnergyUsedPerTick() * getUpgradesEnergyMultiplier()));
//        execute();
//
//        this.tick++;
//
//        int speed = 10 - getUpgradeCount(MachineUpgrades.PROCESSING_SPEED) * 2;
//        if (this.tick >= speed) {
//            this.tick = 0;
//            this.status = Status.NO_PROBLEM;
//            execute();
//            this.energy.consumeEnergy((int) (getEnergyUsedPerTick() * getUpgradesEnergyMultiplier()));
//        }
    }

    /**
     * Initializes the quarry.
     *
     * @deprecated Use {@linkplain #reset()} instead.
     */
    @SuppressWarnings("unused")
    @Deprecated
    private void initialize() {
        this.initialized = true;
        this.x = this.worldPosition.getX() - 1;
        this.y = this.worldPosition.getY() - 1;
        this.z = this.worldPosition.getZ() - 1;
        this.tick = 0;
    }

    /**
     * Get blockstate for active quarry state.
     *
     * @param currentState the current state of the quarry block.
     * @return the new blockstate.
     */
    protected BlockState getActiveState(BlockState currentState) {
        if (!currentState.getValue(AbstractFurnaceBlock.LIT)) {
            return currentState.setValue(AbstractFurnaceBlock.LIT, true);
        }
        return currentState;
    }

    /**
     * Get blockstate for inactive quarry state.
     *
     * @param currentState the current state of the quarry block.
     * @return the new blockstate.
     */
    protected BlockState getInactiveState(BlockState currentState) {
        if (currentState.getValue(AbstractFurnaceBlock.LIT)) {
            return currentState.setValue(AbstractFurnaceBlock.LIT, false);
        }
        return currentState;
    }

    /**
     * Send blockstate update.
     *
     * @param newState the new state.
     */
    protected void sendUpdate(BlockState newState) {
        if (this.level == null) return;
        BlockState oldState = this.level.getBlockState(this.worldPosition);
        if (oldState != newState) {
            this.level.setBlock(this.worldPosition, newState, 3);
            this.level.sendBlockUpdated(this.worldPosition, oldState, newState, 3);
            this.level.updateNeighborsAt(this.worldPosition, newState.getBlock());

//            markModified();

            if (this.level instanceof ServerLevel) {
                ServerLevel serverWorld = (ServerLevel) this.level;
                serverWorld.getServer().getPlayerList().getPlayers().forEach((player) -> player.connection.send(new ClientboundBlockUpdatePacket(serverWorld, worldPosition)));
            }
        }
    }

    protected void setInactiveState() {
        if (level == null) return;
        if (level.getBlockState(worldPosition).getValue(AbstractFurnaceBlock.LIT)) {
            sendUpdate(getInactiveState(level.getBlockState(worldPosition)));
        }
    }

    /**
     * Execute a one tick program.
     */
    private void execute() {
    }

    @Override
    public int getContainerSize() {
        return super.getContainerSize();
    }

    /**
     * Destroy a block.
     *
     * @param pos       the position of the block to destroy.
     * @param dropBlock flag to drop the block above the quarry.
     * @param entity    an entity?
     * @return flag for if the block is destroyed.
     */
    @SuppressWarnings({"UnusedReturnValue", "SameParameterValue", "ConstantConditions"})
    private boolean destroyBlock(BlockPos pos, boolean dropBlock, @Nullable Entity entity) {
        BlockState blockState;

        assert this.level != null;
        blockState = this.level.getBlockState(pos);
        if (blockState.isAir(this.level, pos)) return false;
        else {
            FluidState fluidState = this.level.getFluidState(pos);
            this.level.levelEvent(2001, pos, Block.getId(blockState));
            if (dropBlock) {
                BlockEntity tileEntity = blockState.hasTileEntity() ? this.level.getBlockEntity(pos) : null;
                BlockPos up = this.worldPosition.above();
                BlockEntity upTe = this.level.getBlockEntity(up);
                if (upTe != null) {
                    LazyOptional<IItemHandler> capability = upTe.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.SOUTH);
                    LootContext.Builder builder = new LootContext.Builder((ServerLevel) this.level).withRandom(this.level.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, this).withOptionalParameter(LootContextParams.THIS_ENTITY, null);
                    if (capability.isPresent() && capability.resolve().isPresent()) {
                        IItemHandler itemHandler = capability.resolve().get();
                        List<ItemStack> stacks = new ArrayList<>();
                        for (ItemStack stack : blockState.getDrops(builder)) {
                            for (int i = 0; i < itemHandler.getSlots(); i++) {
                                stack = itemHandler.insertItem(i, stack, false);
                            }
                            stacks.add(stack);
                        }
                        for (ItemStack stack : stacks) {
                            if (stack.isEmpty()) {
                                continue;
                            }
                            if (!this.level.isClientSide && !stack.isEmpty() && this.level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !this.level.restoringBlockSnapshots) {
                                float f = 0.5F;
                                double d0 = (double) (this.level.random.nextFloat() * 0.5F) + 0.25D;
                                double d1 = (double) (this.level.random.nextFloat() * 0.5F) + 0.25D;
                                double d2 = (double) (this.level.random.nextFloat() * 0.5F) + 0.25D;
                                ItemEntity itementity = new ItemEntity(this.level, (double) this.worldPosition.getX() + d0, this.worldPosition.getY() + 2.5d + d1, (double) this.worldPosition.getZ() + d2, stack);
                                itementity.setDefaultPickUpDelay();
                                this.level.addFreshEntity(itementity);
                            }
                        }
                    } else {
                        Block.dropResources(blockState, this.level, this.worldPosition.offset(0, 1.5, 0), tileEntity, entity, ItemStack.EMPTY);
                    }
                } else {
                    Block.dropResources(blockState, this.level, this.worldPosition.offset(0, 1.5, 0), tileEntity, entity, ItemStack.EMPTY);
                }

//                BlockPos above = this.pos.add(0, 1, 0);
//                @Nullable TileEntity aboveTE = this.dimension.getTileEntity(above);
//                if (aboveTE != null) {
//                    if (aboveTE instanceof IInventory) {
//                        IInventory inventory = (IInventory) aboveTE;
//                    }
//                }
            }
            return this.level.setBlock(pos, fluidState.createLegacyBlock(), 3);
        }
    }

    /**
     * Write the quarry tile-entity data to a nbt compound.
     *
     * @param nbt the nbt data to write to.
     * @return the newly written tile-entity data of the quarry.
     */
    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt = super.save(nbt);
        nbt.putBoolean("Initialized", this.initialized);
        nbt.putInt("CurrentX", this.x);
        nbt.putInt("CurrentY", this.y);
        nbt.putInt("CurrentZ", this.z);
        nbt.putInt("Tick", this.tick);
        nbt.putFloat("BreakProgress", this.breakProgress);
        nbt.putFloat("BreakProcessTime", this.breakProcessTime);
        nbt.putString("Status", this.status.name());
        nbt.putString("Tier", this.tier.name());
        return nbt;
    }

    /**
     * Read quarry tile-entity data from a nbt compound.
     *
     * @param state the block state of the tile entity.
     * @param nbt   the quarry nbt data to read from.
     */
    @Override
    public void load(BlockState state, CompoundTag nbt) {
        super.load(state, nbt);
        this.initialized = nbt.getBoolean("Initialized");
        this.x = nbt.getInt("CurrentX");
        this.y = nbt.getInt("CurrentY");
        this.z = nbt.getInt("CurrentZ");
        this.tick = nbt.getInt("Tick");
        try {
            this.status = Status.valueOf(nbt.getString("Status"));
        } catch (IllegalArgumentException e) {
            this.reset();
        }
        try {
            this.tier = MachineTier.valueOf(nbt.getString("Tier"));
        } catch (IllegalArgumentException e) {
            Block block = this.level.getBlockState(this.worldPosition).getBlock();
            if (block instanceof QuarryBlock) {
                this.tier = ((QuarryBlock) block).getDefaultTier();
            } else {
                this.setRemoved();
            }
        }
        this.breakProgress = nbt.getFloat("BreakProgress");
        this.breakProcessTime = nbt.getFloat("BreakProcessTime");
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        super.onDataPacket(net, packet);
        CompoundTag nbt = packet.getTag();
        this.initialized = nbt.getBoolean("Initialized");
        this.x = nbt.getInt("CurrentX");
        this.y = nbt.getInt("CurrentY");
        this.z = nbt.getInt("CurrentZ");
        this.tick = nbt.getInt("Tick");
        try {
            this.status = Status.valueOf(nbt.getString("Status"));
        } catch (IllegalArgumentException e) {
            this.reset();
        }
        try {
            this.tier = MachineTier.valueOf(nbt.getString("Tier"));
        } catch (IllegalArgumentException e) {
            Block block = Objects.requireNonNull(this.level).getBlockState(this.worldPosition).getBlock();
            if (block instanceof QuarryBlock) {
                this.tier = ((QuarryBlock) block).getDefaultTier();
            } else {
                this.setRemoved();
            }
        }
    }

    @Override
    public ContainerData getFields() {
        return fields;
    }

    /**
     * Reset the quarry to factory defaults.
     */
    private void reset() {
        this.initialized = false;
        this.x = this.worldPosition.getX() - 1;
        this.y = this.worldPosition.getY() - 1;
        this.z = this.worldPosition.getZ() - 1;
        this.tick = 0;
        this.status = Status.NOT_INITIALIZED;
    }

    /**
     * Check if machine can run, returns the flag/
     *
     * @return flag: true if machine can run otherwise false.
     * @deprecated Not used anymore.
     */
    @Deprecated
    private boolean canMachineRun() {
        return this.level != null
                && getEnergyStored() >= getEnergyUsedPerTick()
                && this.redstoneMode.shouldRun(this.level.getBestNeighborSignal(this.worldPosition) > 0)
                && this.y >= DEPTH;
    }

    /**
     * Get slots for face.
     *
     * @param side side of the block.
     * @return the available slots.
     */
    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[]{};
    }

    /**
     * Check if an item stack can be inserted.
     *
     * @param index       slot index.
     * @param itemStackIn the item stack.
     * @param direction   the direction of the block where to be inserted.
     * @return false, this block / tile-entity have no support for inserting items.
     */
    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return false;
    }

    /**
     * Check if an item stack can be extracted.
     * Todo: Add support for item transport.
     *
     * @param index     the slot index.
     * @param stack     the item stack.
     * @param direction the direction of the block where to be extracted.
     * @return false, this block / tile-entity have no support for extracting items.
     */
    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return false;
    }

    /**
     * Get the default name of the tile entity.
     *
     * @return a text component of the name of the tile entity (the quarry)
     */
    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "quarry");
    }

    /**
     * Create menu for a player.
     *
     * @param id     id?
     * @param player the player inventory.
     * @return the requested container.
     */
    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new QuarryContainer(id, player, this, this.fields);
    }

    /**
     * @param cap  a non-null capability.
     * @param side the side of the block.
     * @param <T>  capability type.
     * @return
     */
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    /**
     * Quarry status.
     */
    public enum Status {
        NOT_INITIALIZED,
        NOT_ENOUGH_ENERGY,
        ILLEGAL_POSITION,
        NO_PROBLEM,
        UNIDENTIFIED_WORLD, PAUSED, DONE
    }
}
