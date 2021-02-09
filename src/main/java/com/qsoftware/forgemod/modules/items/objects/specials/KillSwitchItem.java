package com.qsoftware.forgemod.modules.items.objects.specials;

import com.qsoftware.forgemod.common.text.Translations;
import com.qsoftware.forgemod.modules.ui.ModItemGroups;
import com.qsoftware.forgemod.util.Utils;
import com.sun.jna.Platform;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Kill switch item class.
 *
 * @author Qboi123
 */
public final class KillSwitchItem extends Item {
    public KillSwitchItem() {
        super(new Item.Properties().group(ModItemGroups.OVERPOWERED));
    }

    @Override
    public @NotNull ActionResultType onItemUse(@NotNull ItemUseContext context) {
        ActionResultType actionResultType = Utils.shutdown();
        if (actionResultType == ActionResultType.SUCCESS || actionResultType == ActionResultType.PASS) {
            PlayerEntity player = context.getPlayer();
            if (player != null) {
                player.addStat(Stats.ITEM_USED.get(this));
            }
        }
        return actionResultType;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Platform.isWindows() || Platform.isLinux()) {
//            tooltip.add(new StringTextComponent(TextColors.LIGHT_RED + "Warning: " + TextColors.WHITE + TextColors.ITALIC + "This will shutdown you computer!"));
            tooltip.add(Translations.getTooltip("kill_switch", "warning"));
        } else {
//            tooltip.add(new StringTextComponent(TextColors.AQUA + "Note: " + TextColors.WHITE + TextColors.ITALIC + "This item works only on Windows or Linux computers!"));
            tooltip.add(Translations.getTooltip("kill_switch", "not_supported"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ITextComponent getHighlightTip(ItemStack item, ITextComponent displayName) {
        if (Platform.isWindows() || Platform.isLinux()) {
            return Translations.getTooltip("kill_switch", "warning");
        } else {
            return Translations.getTooltip("kill_switch", "not_supported");
        }
    }
}
