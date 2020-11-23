package com.qsoftware.forgemod.container.pc;

import com.qsoftware.forgemod.pc.DesktopImage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import org.jetbrains.annotations.Nullable;

public class DesktopContainer extends Container {
    private final DesktopImage image = null;

    protected DesktopContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return false;  // Todo: change to use with ownership.
    }
}
