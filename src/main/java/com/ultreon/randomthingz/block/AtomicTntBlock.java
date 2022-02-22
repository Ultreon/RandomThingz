package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.TntProperties;
import com.ultreon.randomthingz.effect.common.ModEffects;
import com.ultreon.randomthingz.entity.custom.CustomPrimedTnt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * Atomic TNT block.
 *
 * @author Qboi123
 */
public class AtomicTntBlock extends CustomTntBlock<AtomicTntBlock> {
    public AtomicTntBlock(Properties properties) {
        super(properties, TntProperties.builder().radius(48f).fuse(200).causesFire(true).mode(Explosion.BlockInteraction.DESTROY).build());
    }

    @Override
    public void afterExplosion(Level level, BlockPos pos, BlockState state, CustomPrimedTnt tntEntity) {
        System.out.println("Level: " + level);
        System.out.println("Pos: " + pos);
        System.out.println("State: " + state);
        List<Entity> affectedEntities = level.getEntities(null, new AABB(pos.getX() - 50, pos.getY() - 100, pos.getZ() - 50, pos.getX() + 50, pos.getY() + 300, pos.getZ() + 50));
        for (Entity entity : affectedEntities) {
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(ModEffects.RADIATION.get(), 24000, 0, false, false, true));
            }
        }
    }
}
