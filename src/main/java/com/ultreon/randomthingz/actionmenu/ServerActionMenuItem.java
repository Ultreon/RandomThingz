package com.ultreon.randomthingz.actionmenu;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

public class ServerActionMenuItem extends ActionMenuItem {
    private final int permissionLevel;

    public ServerActionMenuItem() {
        this(4);
    }

    public ServerActionMenuItem(int permissionLevel) {
        super(new TranslatableComponent("misc.unknown"), () -> true, () -> {
        });
        this.permissionLevel = permissionLevel;
    }

    public ServerActionMenuItem(Component text, Runnable onActivate) {
        this(text, onActivate, 4);
    }

    public ServerActionMenuItem(Component text, Runnable onActivate, int permissionLevel) {
        super(text, onActivate);
        this.permissionLevel = permissionLevel;
    }

    public ServerActionMenuItem(Component text, Supplier<Boolean> enabled, Runnable onActivate) {
        this(text, enabled, onActivate, 4);
    }

    public ServerActionMenuItem(Component text, Supplier<Boolean> enabled, Runnable onActivate, int permissionLevel) {
        super(text, enabled, onActivate);
        this.permissionLevel = permissionLevel;
    }

    @Override
    public final void onActivate() {
        throw new UnsupportedOperationException("Can't activate client side while being a server side instance.");
    }

    public void onActivate(ServerPlayer player) {
        onActivate.run();
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }
}
