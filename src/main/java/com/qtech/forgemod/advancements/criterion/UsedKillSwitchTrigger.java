package com.qtech.forgemod.advancements.criterion;

import com.google.gson.JsonObject;
import com.qtech.forgemod.QForgeMod;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class UsedKillSwitchTrigger extends AbstractCriterionTrigger<UsedKillSwitchTrigger.Instance> {
   private static final ResourceLocation ID = QForgeMod.rl("used_kill_switch");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   @Override
   public UsedKillSwitchTrigger.Instance deserializeTrigger(JsonObject json, EntityPredicate.AndPredicate entityPredicate, ConditionArrayParser conditionsParser) {
//      MinMaxBounds.FloatBound minmaxbounds$floatbound = MinMaxBounds.FloatBound.fromJson(json.get("distance"));
      return new UsedKillSwitchTrigger.Instance(entityPredicate);
   }

   public void trigger(ServerPlayerEntity player) {
      this.triggerListeners(player, Instance::test);
   }

   public static class Instance extends CriterionInstance {
      public Instance(EntityPredicate.AndPredicate player) {
         super(UsedKillSwitchTrigger.ID, player);
      }

      public boolean test() {
         return true;
      }
   }
}