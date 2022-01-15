package com.ultreon.randomthingz.util;

import lombok.experimental.UtilityClass;
import net.minecraft.world.InteractionResult;

@UtilityClass
public class ItemUtils {
    public static InteractionResult maxActionResult(InteractionResult old, InteractionResult new_) {
        if (old == null) {
            if (new_ == null) {
                return InteractionResult.FAIL;
            }
            return new_;
        }

        switch (old) {
            case FAIL:
                switch (new_) {
                    case PASS:
                    case CONSUME:
                    case SUCCESS:
                        return new_;
                    case FAIL:
                    default:
                        return old;
                }
            case PASS:
                switch (new_) {
                    case CONSUME:
                    case SUCCESS:
                        return new_;
                    case FAIL:
                    case PASS:
                    default:
                        return old;
                }
            case CONSUME:
                switch (new_) {
                    case SUCCESS:
                        return new_;
                    case FAIL:
                    case PASS:
                    case CONSUME:
                    default:
                        return old;
                }
            case SUCCESS:
                return old;
        }
        return old;
    }
}
