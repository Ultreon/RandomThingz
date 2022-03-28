package com.ultreon.randomthingz.item.wand;

import com.ultreon.randomthingz.init.ModCreativeTabs;
import com.ultreon.randomthingz.item.WandItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * Teleport staff item class.
 *
 * @author Qboi123
 */
@Deprecated
public class TeleportStaffItem extends WandItem {
    public TeleportStaffItem() {
        super(420, 20, new Properties().tab(ModCreativeTabs.OVERPOWERED).rarity(Rarity.EPIC));
    }

    @Override
    public void activate(ItemStack stack, @NotNull Level dimensionIn, @NotNull LivingEntity livingIn, float charge) {
        if (!(livingIn instanceof Player)) {
            return;
        }

        int strength = getStrength(stack);
        if (strength == -1) {
            return;
        }

        Player player = (Player) livingIn;

        if (dimensionIn instanceof ServerLevel) {
            Vec3 vector3d = player.getLookAngle();
            double dx = vector3d.x * (4 * charge * strength);
            double dy = vector3d.y * (4 * charge * strength);
            double dz = vector3d.z * (4 * charge * strength);

            double x = player.getX() + dx;
            double y = player.getY() + dy;
            double z = player.getZ() + dz;

            player.getCooldowns().addCooldown(this, 20);
            player.teleportTo(x, y, z);

            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }
}
