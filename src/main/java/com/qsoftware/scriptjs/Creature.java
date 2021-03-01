package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.monster.MonsterEntity;

public class Creature extends Mob implements Formattable {
    private final CreatureEntity wrapper;

    public static Creature getCorrectEntity(CreatureEntity entity) {
        if (entity instanceof MonsterEntity) {
            return Monster.getCorrectEntity((MonsterEntity) entity);
        }
        return new Creature(entity);
    }

    public Creature(CreatureEntity wrapper) {
        super(wrapper);
        this.wrapper = wrapper;
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Creature", getUUID());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Creature", getUUID());
    }
}
