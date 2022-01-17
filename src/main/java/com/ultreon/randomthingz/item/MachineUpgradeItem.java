package com.ultreon.randomthingz.item;

import com.ultreon.modlib.embedded.silentutils.MathUtils;
import com.ultreon.randomthingz.api.IMachineUpgrade;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import org.jetbrains.annotations.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class MachineUpgradeItem extends Item {
    private final IMachineUpgrade upgrade;

    public MachineUpgradeItem(IMachineUpgrade upgrade) {
        super(new Properties().tab(CreativeModeTab.TAB_MISC));
        this.upgrade = upgrade;
    }

    public IMachineUpgrade getUpgrade() {
        return upgrade;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level dimensionIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, dimensionIn, tooltip, flagIn);

        // Upgrade description and value
        tooltip.add(new TranslatableComponent(this.getDescriptionId() + ".desc", upgrade.getDisplayValue()));

        // Energy usage multiplier
        float energyCost = upgrade.getEnergyUsageMultiplier();
        if (!MathUtils.floatsEqual(energyCost, 0f)) {
            String str = String.format("%d", (int) (100 * energyCost));
            if (energyCost > 0)
                str = "+" + str;
            tooltip.add(TextUtils.translate("item", "machine_upgrade.energy", str));
        }
    }
}
