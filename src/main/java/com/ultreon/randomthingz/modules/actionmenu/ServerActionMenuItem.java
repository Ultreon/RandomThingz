package com.ultreon.randomthingz.modules.actionmenu;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Supplier;

public class ServerActionMenuItem extends ActionMenuItem {
    private final int permissionLevel;

    public ServerActionMenuItem() {
        this(4);
    }

    public ServerActionMenuItem(int permissionLevel) {
        super(new TranslationTextComponent("misc.unknown"), () -> true, () -> {
        });
        this.permissionLevel = permissionLevel;
    }

    public ServerActionMenuItem(ITextComponent text, Runnable onActivate) {
        this(text, onActivate, 4);
    }

    public ServerActionMenuItem(ITextComponent text, Runnable onActivate, int permissionLevel) {
        super(text, onActivate);
        this.permissionLevel = permissionLevel;
    }

    public ServerActionMenuItem(ITextComponent text, Supplier<Boolean> enabled, Runnable onActivate) {
        this(text, enabled, onActivate, 4);
    }

    public ServerActionMenuItem(ITextComponent text, Supplier<Boolean> enabled, Runnable onActivate, int permissionLevel) {
        super(text, enabled, onActivate);
        this.permissionLevel = permissionLevel;
    }

    @Override
    public final void onActivate() {
        throw new UnsupportedOperationException("Can't activate client side while being a server side instance.");
    }

    public void onActivate(ServerPlayerEntity player) {
        onActivate.run();
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }
}
