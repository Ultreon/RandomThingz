package com.ultreon.randomthingz.block.machines.arcaneescalator;

import com.ultreon.randomthingz.block._common.MachineType;
import com.ultreon.randomthingz.block.machines.MachineBlockEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.item.crafting.ArcaneEscalatingRecipe;
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
import java.util.Collections;
import java.util.stream.IntStream;

public class ArcaneEscalatorBlockEntity extends MachineBlockEntity<ArcaneEscalatingRecipe> {
    // Energy constant
    public static final int MAX_ENERGY = 50_000;
    public static final int MAX_RECEIVE = 500;
    public static final int ENERGY_USED_PER_TICK = 30;

    // Inventory constants
    static final int INPUT_SLOT_COUNT = 1;
    private static final int[] SLOTS_INPUT = IntStream.range(0, INPUT_SLOT_COUNT).toArray();
    private static final int[] SLOTS_OUTPUT = {INPUT_SLOT_COUNT};
    private static final int[] SLOTS_ALL = IntStream.range(0, INPUT_SLOT_COUNT + 1).toArray();

    public ArcaneEscalatorBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, MachineTier.STANDARD);
    }

    public ArcaneEscalatorBlockEntity(BlockPos pos, BlockState state, MachineTier tier) {
        super(MachineType.ARCANE_ESCALATOR.getTileEntityType(tier), pos, state, SLOTS_ALL.length, tier);
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
    protected ArcaneEscalatingRecipe getRecipe() {
        if (level == null) return null;
        return level.getRecipeManager().getRecipeFor(ModRecipes.Types.ARCANE_ESCALATING, this, level).orElse(null);
    }

    @Override
    protected int getProcessTime(ArcaneEscalatingRecipe recipe) {
        return recipe.getProcessTime();
    }

    @Override
    protected Collection<ItemStack> getProcessResults(ArcaneEscalatingRecipe recipe) {
        return Collections.singleton(recipe.getResultItem());
    }

    @Override
    protected void consumeIngredients(ArcaneEscalatingRecipe recipe) {
        recipe.consumeIngredients(this);
    }

    @Override
    public int getInputSlotCount() {
        return INPUT_SLOT_COUNT;
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
        return index == INPUT_SLOT_COUNT;
    }

    @Override
    protected Component getDefaultName() {
        return TextUtils.translate("container", "arcane_escalator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new ArcaneEscalatorContainer(id, playerInventory, this, this.fields);
    }

    public static class Basic extends ArcaneEscalatorBlockEntity {
        public Basic(BlockPos pos, BlockState state) {
            super(pos, state, MachineTier.BASIC);
        }
    }
}
