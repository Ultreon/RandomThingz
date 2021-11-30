package com.ultreon.randomthingz.item.wand;

import com.ultreon.randomthingz.common.item.ModItemGroups;
import com.ultreon.randomthingz.item.WandItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

/**
 * Teleport staff item class.
 *
 * @author Qboi123
 */
@Deprecated
public class TeleportStaffItem extends WandItem {
    public TeleportStaffItem() {
        super(420, 20, new Properties().group(ModItemGroups.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public void activate(ItemStack stack, @NotNull World dimensionIn, @NotNull LivingEntity livingIn, float charge) {
        if (!(livingIn instanceof PlayerEntity)) {
            return;
        }

        int strength = getStrength(stack);
        if (strength == -1) {
            return;
        }

        PlayerEntity player = (PlayerEntity) livingIn;

        if (dimensionIn instanceof ServerWorld) {
            Vector3d vector3d = player.getLookVec();
            double dx = vector3d.x * (4 * charge * strength);
            double dy = vector3d.y * (4 * charge * strength);
            double dz = vector3d.z * (4 * charge * strength);

            double x = player.getPosX() + dx;
            double y = player.getPosY() + dy;
            double z = player.getPosZ() + dz;

            player.getCooldownTracker().setCooldown(this, 20);
            player.setPositionAndUpdate(x, y, z);

            player.addStat(Stats.ITEM_USED.get(this));
        }
    }
}
