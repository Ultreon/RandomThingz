package com.ultreon.randomthingz.inventory.pc;

import com.ultreon.randomthingz.pc.common.computerapi.DesktopImage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DesktopContainer extends Container {
    private final DesktopImage image = null;

    protected DesktopContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }

    @Override
    public boolean canInteractWith(@NotNull PlayerEntity playerIn) {
        return false;  // Todo: change to use with ownership.
    }
}
