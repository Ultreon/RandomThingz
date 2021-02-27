package com.qsoftware.forgemod.script.js;

import com.mojang.datafixers.util.Either;
import net.minecraft.entity.player.PlayerEntity;

public abstract class AbstractScriptJSInstance {
    public abstract Either<Exception, Object> eval(String s);

    public abstract PlayerEntity getPlayer();
}
