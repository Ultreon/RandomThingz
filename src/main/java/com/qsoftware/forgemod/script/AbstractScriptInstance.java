package com.qsoftware.forgemod.script;

import com.mojang.datafixers.util.Either;
import lombok.Getter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractScriptInstance {
    @Getter protected final ScriptJS scriptJS;
    @Getter protected final Context context;
    @Getter protected final Dist dist;
    private Language language;

    public AbstractScriptInstance(Language language, Context context, Dist dist) {
        context.initialize(language.getId());

        this.language = language;
        this.context = context;
        this.dist = dist;
        this.scriptJS = new ScriptJS(this);
    }

    private void defaultInit() {

    }

    protected abstract void init();

    public final Either<Exception, Value> eval(Language lang, String script) {
        init();
        try {
            return Either.right(this.context.eval(lang.getId(), script));
        } catch (Exception e) {
            return Either.left(e);
        }
    }

    protected abstract void initClient(Value bindings) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    protected abstract void initServer(Value bindings) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    public abstract PlayerEntity getPlayer();

    public Language getLanguage() {
        return language;
    }
}
