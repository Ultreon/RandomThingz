package com.qsoftware.forgemod.registration.impl;

import com.qsoftware.forgemod.registration.WrappedDeferredRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityTypeDeferredRegister extends WrappedDeferredRegister<EntityType<?>> {

    public EntityTypeDeferredRegister(String modid) {
        super(modid, ForgeRegistries.ENTITIES);
    }

    public <ENTITY extends Entity> EntityTypeRegistryObject<ENTITY> register(String name, EntityType.Builder<ENTITY> builder) {
        return register(name, () -> builder.build(name), EntityTypeRegistryObject::new);
    }

    public <ENTITY extends Entity> EntityTypeRegistryObject<ENTITY> register(String name, EntityType<ENTITY> type) {
        return register(name, () -> type, EntityTypeRegistryObject::new);
    }

    /*

   @Nullable
   public T create(ServerWorld worldIn, @Nullable CompoundNBT compound, @Nullable ITextComponent customName, @Nullable PlayerEntity playerIn, BlockPos pos, SpawnReason reason, boolean p_220349_7_, boolean p_220349_8_) {
      T t = this.create(worldIn);
      if (t == null) {
         return (T)null;
      } else {
         double d0;
         if (p_220349_7_) {
            t.setPosition((double)pos.getX() + 0.5D, (double)(pos.getY() + 1), (double)pos.getZ() + 0.5D);
            d0 = func_208051_a(worldIn, pos, p_220349_8_, t.getBoundingBox());
         } else {
            d0 = 0.0D;
         }

         t.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() + d0, (double)pos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
         if (t instanceof MobEntity) {
            MobEntity mobentity = (MobEntity)t;
            mobentity.rotationYawHead = mobentity.rotationYaw;
            mobentity.renderYawOffset = mobentity.rotationYaw;
            mobentity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(mobentity.getPosition()), reason, (ILivingEntityData)null, compound);
            mobentity.playAmbientSound();
         }

         if (customName != null && t instanceof LivingEntity) {
            t.setCustomName(customName);
         }

         applyItemNBT(worldIn, playerIn, t, compound);
         return t;
      }
   }

     */
}