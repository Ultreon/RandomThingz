package com.ultreon.randomthingz.block.machines.crusher;

import com.ultreon.randomthingz.block.machines.MachineType;
import com.ultreon.randomthingz.block.machines.MachineBlockEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.item.crafting.CrushingRecipe;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.stream.IntStream;

public class CrusherBlockEntity extends MachineBlockEntity<CrushingRecipe> {
    // Energy constant
    private static final int MAX_ENERGY = 50_000;
    private static final int MAX_RECEIVE = 500;
    private static final int ENERGY_USED_PER_TICK = 30;

    // Inventory constants
    private static final int INPUT_SLOT_COUNT = 1;
    private static final int OUTPUT_SLOT_COUNT = 4;
    private static final int INVENTORY_SIZE = INPUT_SLOT_COUNT + OUTPUT_SLOT_COUNT;
    private static final int[] SLOTS_INPUT = {0};
    private static final int[] SLOTS_OUTPUT = IntStream.range(INPUT_SLOT_COUNT, INVENTORY_SIZE).toArray();
    private static final int[] SLOTS_ALL = IntStream.range(0, INVENTORY_SIZE).toArray();

    public CrusherBlockEntity(BlockPos pos, BlockState state) {
        this(MachineTier.STANDARD, pos, state);
    }

    public CrusherBlockEntity(MachineTier tier, BlockPos pos, BlockState state) {
        super(MachineType.CRUSHER.getTileEntityType(tier), pos, state, INVENTORY_SIZE, tier);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected int getEnergyUsedPerTick() {
        return ENERGY_USED_PER_TICK;
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    protected int[] getOutputSlots() {
        return SLOTS_OUTPUT;
    }

    @Nullable
    @Override
    protected CrushingRecipe getRecipe() {
        if (level == null) return null;
        return level.getRecipeManager().getRecipeFor(ModRecipes.Types.CRUSHING, this, level).orElse(null);
    }

    @Override
    protected int getProcessTime(CrushingRecipe recipe) {
        return recipe.getProcessTime();
    }

    @Override
    protected Collection<ItemStack> getProcessResults(CrushingRecipe recipe) {
        return recipe.getResults(this);
    }

    @Override
    protected Collection<ItemStack> getPossibleProcessResult(CrushingRecipe recipe) {
        return recipe.getPossibleResults(this);
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS_ALL;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return index < INPUT_SLOT_COUNT;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return index >= INPUT_SLOT_COUNT;
    }

    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "crusher");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new CrusherContainer(id, inv, tier, inventory, worldPosition, this.fields);
    }

    public static class Basic extends CrusherBlockEntity {
        public Basic(BlockPos pos, BlockState state) {
            super(MachineTier.BASIC, pos, state);
        }
    }
}
