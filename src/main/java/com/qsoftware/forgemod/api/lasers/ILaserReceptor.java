package com.qsoftware.forgemod.api.lasers;

import com.qsoftware.forgemod.api.math.FloatingLong;
import net.minecraft.util.Direction;

import javax.annotation.Nonnull;

public interface ILaserReceptor {

    void receiveLaserEnergy(@Nonnull FloatingLong energy, Direction side);

    boolean canLasersDig();
}