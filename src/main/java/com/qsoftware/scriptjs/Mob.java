package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;

public class Mob extends LivingBeing implements Formattable {
    private final MobEntity wrapper;

    public static Mob getCorrectEntity(MobEntity entity) {
        if (entity instanceof CreatureEntity) {
            return Creature.getCorrectEntity((CreatureEntity) entity);
        }
        return new Mob(entity);
    }

    public Mob(MobEntity wrapper) {
        super(wrapper);
        this.wrapper = wrapper;
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Mob", getUUID());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Mob", getUUID());
    }
}
