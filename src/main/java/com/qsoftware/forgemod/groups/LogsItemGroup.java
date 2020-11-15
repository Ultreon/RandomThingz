package com.qsoftware.forgemod.groups;

import com.qsoftware.forgemod.init.ModBlocks;
import com.qsoftware.forgemod.init.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Logs item group.
 * Will be in the future merged with {@link WoodItemGroup}.
 *
 * @author Qboi123
 */
@Deprecated
public class LogsItemGroup extends ItemGroup {
    public static final LogsItemGroup instance = new LogsItemGroup(ItemGroup.GROUPS.length, "qforgemod_logs");

    public LogsItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public @NotNull ItemStack createIcon() {
        return new ItemStack(Blocks.OAK_LOG);
    }
}
