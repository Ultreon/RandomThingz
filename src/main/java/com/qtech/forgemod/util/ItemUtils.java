package com.qtech.forgemod.util;

import lombok.experimental.UtilityClass;
import net.minecraft.util.ActionResultType;

@UtilityClass
public class ItemUtils {
    public static ActionResultType maxActionResult(ActionResultType old, ActionResultType new_) {
        if (old == null) {
            if (new_ == null) {
                return ActionResultType.FAIL;
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
