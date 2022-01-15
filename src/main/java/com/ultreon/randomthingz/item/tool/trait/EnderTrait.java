package com.ultreon.randomthingz.item.tool.trait;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Attacked entity has 5 in 16 chance to randomly teleport.
 * Right click causes teleport to the block you looking at in range.
 */
public class EnderTrait extends AbstractTrait {
    public EnderTrait() {

    }

    public TextColor getColor() {
        return TextColor.parseColor("#3D9964");
    }

    @Override
    public boolean onHitEntity(@NotNull ItemStack stack, @NotNull LivingEntity victim, LivingEntity attacker) {
        super.onHitEntity(stack, victim, attacker);

        int retries = 5;

        while (retries > 0) {
            if (victim.getRandom().nextInt(16) == 0) {
                Level entityDimension = victim.getCommandSenderWorld();
                int x = victim.getRandom().nextInt(20) - 10;
                int z = victim.getRandom().nextInt(20) - 10;
                int y = entityDimension.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

                victim.teleportToWithTicket(x, y, z);
                break;
            }

            retries--;
        }

        return true;
    }

    protected static BlockHitResult rayTrace(Level dimensionIn, Player player) {
        float pitch = player.xRot;
        float yaw = player.yRot;

        // Get the player's eye position, put is as start position.
        Vec3 startPos = player.getEyePosition(1.0F);

        // Calculations.
        float fz = Mth.cos(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float fx = Mth.sin(-yaw * ((float) Math.PI / 180F) - (float) Math.PI);
        float f = -Mth.cos(-pitch * ((float) Math.PI / 180F));
        float lookY = Mth.sin(-pitch * ((float) Math.PI / 180F));
        float lookX = fx * f;
        float lookZ = fz * f;

        // Ray length.
        double rayLength = 12;
        Vec3 endPos = startPos.add((double) lookX * rayLength, (double) lookY * rayLength, (double) lookZ * rayLength);

        // Raytracing.
        return dimensionIn.clip(new ClipContext(startPos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
    }

    @Override
    public boolean onRightClick(Item item, Level dimension, Player clicker, InteractionHand hand) {
        if (dimension instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel) dimension;

            HitResult raytraceresult = rayTrace(serverWorld, clicker);
            double posX = raytraceresult.getLocation().x;
            double posY = Math.floor(raytraceresult.getLocation().y);
            double posZ = raytraceresult.getLocation().z;

            for (int i = 0; i < 32; ++i) {
                clicker.level.addParticle(ParticleTypes.PORTAL, posX, posY + clicker.getRandom().nextDouble() * 2.0D, posZ, clicker.getRandom().nextGaussian(), 0.0D, clicker.getRandom().nextGaussian());
            }

            if (clicker.isPassenger()) {
                clicker.stopRiding();
            }

            clicker.teleportTo(posX, posY, posZ);
            clicker.fallDistance = 0.0F;

            clicker.awardStat(Stats.ITEM_USED.get(item));
            clicker.getCooldowns().addCooldown(item, 100);
        }
        return true;
    }

    @Override
    public void onLivingDamage(LivingDamageEvent e) {
        LivingEntity livingBeing = e.getEntityLiving();
        if (e.getEntityLiving().getHealth() - e.getAmount() < 1.0f) {
            if (livingBeing.getRandom().nextInt(5) == 0) {
                e.setCanceled(true);

                Level entityDimension = livingBeing.getCommandSenderWorld();
                int x = livingBeing.getRandom().nextInt(20) - 10;
                int z = livingBeing.getRandom().nextInt(20) - 10;
                x = (int) (e.getEntityLiving().getX() + x);
                z = (int) (e.getEntityLiving().getZ() + z);
                int y = entityDimension.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

                livingBeing.teleportToWithTicket(x, y, z);
                return;
            }
        }
        super.onLivingDamage(e);
    }
}
