package com.qsoftware.forgemod.objects.items.upgrades;

import com.qsoftware.forgemod.api.IMachineUpgrade;
import com.qsoftware.forgemod.init.ModItemGroups;
import com.qsoftware.forgemod.util.TextUtils;
import com.qsoftware.modlib.silentutils.MathUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MachineUpgradeItem extends Item {
    private final IMachineUpgrade upgrade;

    public MachineUpgradeItem(IMachineUpgrade upgrade) {
        super(new Properties().group(ModItemGroups.MISC));
        this.upgrade = upgrade;
    }

    public IMachineUpgrade getUpgrade() {
        return upgrade;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        // Upgrade description and value
        tooltip.add(new TranslationTextComponent(this.getTranslationKey() + ".desc", upgrade.getDisplayValue()));

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
