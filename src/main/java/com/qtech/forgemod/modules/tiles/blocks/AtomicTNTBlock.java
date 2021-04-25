package com.qtech.forgemod.modules.tiles.blocks;

import com.qtech.forgemod.common.TNTProperties;
import com.qtech.forgemod.modules.environment.ModEffects;
import com.qtech.forgemod.modules.environment.entities.custom.CustomTNTEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.List;

/**
 * Atomic TNT block.
 *
 * @author Qboi123
 */
public class AtomicTNTBlock extends CustomTNTBlock<AtomicTNTBlock> {
    public AtomicTNTBlock(Properties properties) {
        super(properties, TNTProperties.builder().radius(48.0f).fuse(200).causesFire(true).mode(Explosion.Mode.DESTROY).build());
    }

    @Override
    public void afterExplosion(World dimension, BlockPos pos, BlockState state, CustomTNTEntity tntEntity) {
        System.out.println("World: " + dimension);
        System.out.println("Pos: " + pos);
        System.out.println("State: " + state);
        List<Entity> affectedEntities = dimension.getEntitiesWithinBoxExcludingEntity(null, new AxisAlignedBB(pos.getX() - 50, pos.getY() - 100, pos.getZ() - 50, pos.getX() + 50, pos.getY() + 300, pos.getZ() + 50));
        for (Entity entity : affectedEntities) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingBeing = (LivingEntity) entity;
                livingBeing.addPotionEffect(new EffectInstance(ModEffects.RADIATION.get(), 24000, 0, false, false, true));
            }
        }
    }
}
