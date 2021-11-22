package com.ultreon.randomthingz.item;

import com.qsoftware.modlib.silentutils.MathUtils;
import com.ultreon.randomthingz.api.IMachineUpgrade;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class MachineUpgradeItem extends Item {
    private final IMachineUpgrade upgrade;

    public MachineUpgradeItem(IMachineUpgrade upgrade) {
        super(new Properties().group(ItemGroup.MISC));
        this.upgrade = upgrade;
    }

    public IMachineUpgrade getUpgrade() {
        return upgrade;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World dimensionIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, dimensionIn, tooltip, flagIn);

        // Upgrade description and value
        tooltip.add(new TranslationTextComponent(this.getTranslationId() + ".desc", upgrade.getDisplayValue()));

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
