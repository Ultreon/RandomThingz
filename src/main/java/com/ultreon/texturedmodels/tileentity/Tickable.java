package com.ultreon.texturedmodels.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Deprecated
public interface Tickable {
	@Deprecated
	static void blockEntity(Level level, BlockPos pos, BlockState state, BlockEntity entity) {
		if (entity instanceof Tickable tickable) {
			tickable.tick(level, pos, state);
		}
	}

	@Deprecated
	void tick();

	@Deprecated
	@SuppressWarnings("unused")
	default void tick(Level level, BlockPos pos, BlockState state) {
		tick();
	}
}
