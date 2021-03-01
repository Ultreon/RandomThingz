package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.PatrollerEntity;

public class Monster extends Creature implements Formattable {
    private final MonsterEntity wrapper;

    public static Monster getCorrectEntity(MonsterEntity entity) {
        if (entity instanceof AbstractRaiderEntity) {
            return new Raider((AbstractRaiderEntity) entity);
        }
        if (entity instanceof PatrollerEntity) {
            return new Patroller((PatrollerEntity) entity);
        }
        return new Monster(entity);
    }

    public Monster(MonsterEntity wrapper) {
        super(wrapper);
        this.wrapper = wrapper;
    }

    public void something() {

    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Monster", getUUID());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Monster", getUUID());
    }
}
