package com.ultreon.randomthingz.actionmenu;

import com.ultreon.randomthingz.common.exceptions.IllegalDistException;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractActionMenu {
    protected List<ActionMenuItem> client = new ArrayList<>();
    protected List<ActionMenuItem> server = new ArrayList<>();
    private boolean clientLock = false;
    private boolean serverLock = false;

    public AbstractActionMenu() {
        DistExecutor.unsafeRunForDist(() -> () -> {
            clientLock = true;
            client();
            clientLock = false;
            return null;
        }, () -> () -> null);
        serverLock = true;
        server();
        serverLock = false;
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void client();

    public abstract void server();

    public List<? extends ActionMenuItem> getClient() {
        return Collections.unmodifiableList(this.client);
    }

    @OnlyIn(Dist.CLIENT)
    public final <T extends ActionMenuItem> T addClient(T item) {
        if (!clientLock) {
            throw new IllegalStateException("Expected to call from #client()");
        }
        if (item.isServerVariant()) {
            throw new IllegalDistException("Expected client side only action menu item, got a server variant.");
        }

        return add(item);
    }

    public final <T extends ActionMenuItem> T addServer(T item) {
        if (!serverLock) {
            throw new IllegalStateException("Expected to call from #server()");
        }
        if (item.isClientSideOnly()) {
            throw new IllegalDistException("Expected server action menu item, got a client side only variant.");
        }

        return add(item);
    }

    public final <T extends ActionMenuItem> T add(T item) {
        if (item.isServerVariant()) {
            if (!serverLock && !(this instanceof MainActionMenu)) {
                throw new IllegalStateException("Expected to call from #server()");
            }
            this.client.add(item);
            this.server.add(item);
        } else if (item.isClientSideOnly()) {
            if (!clientLock && !(this instanceof MainActionMenu)) {
                throw new IllegalStateException("Expected to call from #client()");
            }
            this.client.add(item);
        } else {
            throw new IllegalStateException("Couldn't determine if menu item is client side only or not.");
        }
        return item;
    }
}
