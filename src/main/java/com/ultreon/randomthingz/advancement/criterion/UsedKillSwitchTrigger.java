package com.ultreon.randomthingz.advancement.criterion;

import com.google.gson.JsonObject;
import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class UsedKillSwitchTrigger extends SimpleCriterionTrigger<UsedKillSwitchTrigger.Instance> {
    private static final ResourceLocation ID = RandomThingz.res("used_kill_switch");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public UsedKillSwitchTrigger.Instance createInstance(JsonObject json, EntityPredicate.Composite entityPredicate, DeserializationContext conditionsParser) {
//      MinMaxBounds.FloatBound minmaxbounds$floatbound = MinMaxBounds.FloatBound.fromJson(json.get("distance"));
        return new UsedKillSwitchTrigger.Instance(entityPredicate);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, Instance::test);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(EntityPredicate.Composite player) {
            super(UsedKillSwitchTrigger.ID, player);
        }

        public boolean test() {
            return true;
        }
    }
}