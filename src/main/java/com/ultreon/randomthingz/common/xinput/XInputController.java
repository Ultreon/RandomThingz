package com.ultreon.randomthingz.common.xinput;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import lombok.Getter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

public class XInputController extends AbstractController {
    @Getter
    private final ControllerManager manager;
    @Getter
    private final int index;
    private ControllerState state;

    public XInputController(ControllerManager manager, int index) {
        this.manager = manager;
        this.index = index;
        this.state = manager.getState(index);
    }

    public void tick() {
        this.state = this.manager.getState(this.index);
    }

    public Vec2 getPlayerHorizontalMotion(Player entity) {
        float walkSpeed = entity.abilities.getWalkingSpeed();
        float x = this.state.leftStickX * walkSpeed;
        float y = this.state.leftStickY * walkSpeed;
        return new Vec2(x, y);
    }

    public boolean isJumping() {
        return state.a;
    }

    public boolean isOpeningMenu() {
        return state.startJustPressed;
    }

    @Override
    public boolean isPressingBack() {
        return state.b;
    }
}
