package com.ultreon.randomthingz.actionmenu;

import com.ultreon.randomthingz.network.ActionMenuTransferPacket;
import com.ultreon.randomthingz.network.Network;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fml.DistExecutor;

import java.util.HashMap;
import java.util.function.Supplier;

public class ActionMenuItem implements IActionMenuItem {
    private final Component text;
    private final Supplier<Boolean> enabled;
    protected final Runnable onActivate;

    private static int currentId = -1;
    private static int currentServerId = -1;
    private final int id;
    private final int serverId;

    private static final HashMap<Integer, ActionMenuItem> commonRegistry = new HashMap<>();
    private static final HashMap<Integer, ActionMenuItem> serverRegistry = new HashMap<>();

    public ActionMenuItem(Component text, Runnable onActivate) {
        this(text, () -> true, onActivate);
    }

    public ActionMenuItem(Component text, Supplier<Boolean> enabled, Runnable onActivate) {
        this.text = text;
        this.enabled = enabled;
        this.onActivate = onActivate;
        this.id = currentId++;

        commonRegistry.put(this.id, this);

        if (isServerVariant()) {
            this.serverId = currentServerId++;
            serverRegistry.put(this.serverId, this);
        } else {
            this.serverId = -1;
        }
    }

    public ActionMenuItem() {
        this(new TranslatableComponent("misc.unknown"), () -> true, () -> {
        });
    }

    public final void activate() {
        DistExecutor.unsafeRunForDist(() -> () -> {
            if (isServerVariant()) {
                Network.sendToServer(new ActionMenuTransferPacket(serverId()));
            } else {
                onActivate();
            }
            return null;
        }, () -> () -> {
            if (isServerVariant()) {
                onActivate();
            }
            return null;
        });
    }

    @Override
    public Component getText() {
        return text;
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }

    @Override
    public void onActivate() {
        onActivate.run();
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public int serverId() {
        return serverId;
    }

    public static int getCurrentId() {
        return currentId;
    }

    public static int getCurrentServerId() {
        return currentServerId;
    }

    public static ActionMenuItem fromId(int id) {
        return commonRegistry.get(id);
    }

    public static ActionMenuItem fromServerId(int id) {
        return serverRegistry.get(id);
    }

    public final boolean isServerVariant() {
        return this instanceof ServerActionMenuItem;
    }

    public final boolean isClientSideOnly() {
        return !(this instanceof ServerActionMenuItem);
    }
}
