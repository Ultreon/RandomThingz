package com.ultreon.randomthingz.block.machines.alloysmelter;

import com.ultreon.randomthingz.block.machines.MachineType;
import com.ultreon.randomthingz.block.machines.MachineBlockEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import com.ultreon.randomthingz.item.crafting.AlloySmeltingRecipe;
import com.ultreon.randomthingz.item.crafting.common.ModRecipes;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.IntStream;

public class AlloySmelterBlockEntity extends MachineBlockEntity<AlloySmeltingRecipe> {
    // Energy constant
    public static final int MAX_ENERGY = 50_000;
    public static final int MAX_RECEIVE = 500;
    public static final int ENERGY_USED_PER_TICK = 30;

    // Inventory constants
    static final int INPUT_SLOT_COUNT = 4;
    private static final int[] SLOTS_INPUT = IntStream.range(0, INPUT_SLOT_COUNT).toArray();
    private static final int[] SLOTS_OUTPUT = {INPUT_SLOT_COUNT};
    private static final int[] SLOTS_ALL = IntStream.range(0, INPUT_SLOT_COUNT + 1).toArray();

    public AlloySmelterBlockEntity(BlockPos pos, BlockState state) {
        this(MachineTier.STANDARD, pos, state);
    }

    public AlloySmelterBlockEntity(MachineTier tier, BlockPos pos, BlockState state) {
        super(MachineType.ALLOY_SMELTER.getTileEntityType(tier), pos, state, SLOTS_ALL.length + 2, tier);
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
    protected AlloySmeltingRecipe getRecipe() {
        if (level == null) return null;
        return level.getRecipeManager().getRecipeFor(ModRecipes.Types.ALLOY_SMELTING, this, level).orElse(null);
    }

    @Override
    public void load(CompoundTag tags) {
        System.out.println("[alloy_smelter] -----------------------");
        System.out.println("[alloy_smelter] worldPosition = " + worldPosition);
        System.out.println("[alloy_smelter[ level = " + level);
        System.out.println("[alloy_smelter] tags = " + tags);
        System.out.println("[alloy_smelter] -----------------------");

        String path = "randomthingz-debug/load-dump/block-entity/" + Objects.requireNonNull(level).dimension().location().toDebugFileName() + " " + worldPosition.getX() + "." + worldPosition.getY() + "." + worldPosition.getZ() + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".nbt";
        try {
            File file = new File(Minecraft.getInstance().getLevelSource().getBaseDir().toFile(), path);
            file.getParentFile().mkdirs();
            NbtIo.writeCompressed(tags, file);
        } catch (IOException e) {
            CrashReport crashReport = new CrashReport("Debugging dump failure", e);
            CrashReportCategory dump_details = crashReport.addCategory("Dump Details");
            dump_details.setDetail("Dump Path", () -> path);
            Minecraft.crash(crashReport);
        }

        super.load(tags);
    }

    @Override
    public void saveAdditional(CompoundTag tags) {
        super.saveAdditional(tags);
        System.out.println("[alloy_smelter] -----------------------");
        System.out.println("[alloy_smelter] worldPosition = " + worldPosition);
        System.out.println("[alloy_smelter[ level = " + level);
        System.out.println("[alloy_smelter] saved = " + tags);
        System.out.println("[alloy_smelter] -----------------------");

        String path = "randomthingz-debug/save-dump/block-entity/" + Objects.requireNonNull(level).dimension().location().toDebugFileName() + " " + worldPosition.getX() + "." + worldPosition.getY() + "." + worldPosition.getZ() + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".nbt";
        try {
            File file = new File(Minecraft.getInstance().getLevelSource().getBaseDir().toFile(), path);
            file.getParentFile().mkdirs();
            NbtIo.writeCompressed(tags, file);
        } catch (IOException e) {
            CrashReport crashReport = new CrashReport("Debugging dump failure", e);
            CrashReportCategory dump_details = crashReport.addCategory("Dump Details");
            dump_details.setDetail("Dump Path", () -> path);
            Minecraft.crash(crashReport);
        }
    }

    @Override
    protected int getProcessTime(AlloySmeltingRecipe recipe) {
        return recipe.getProcessTime();
    }

    @Override
    protected Collection<ItemStack> getProcessResults(AlloySmeltingRecipe recipe) {
        ItemStack resultItem = recipe.assemble(this);
        System.out.println("recipe.getResult() = " + recipe.getResult());
        System.out.println("recipe.assemble(this) = " + recipe.assemble(this));
        return Collections.singleton(resultItem);
    }

    @Override
    protected void consumeIngredients(AlloySmeltingRecipe recipe) {
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
        return TextUtils.translate("container", "alloy_smelter");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new AlloySmelterContainer(id, playerInventory, tier, inventory, worldPosition, fields);
    }

    public static class Basic extends AlloySmelterBlockEntity {
        public Basic(BlockPos pos, BlockState state) {
            super(MachineTier.BASIC, pos, state);
        }
    }
}
