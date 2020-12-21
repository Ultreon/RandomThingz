package com.qsoftware.forgemod.util;

import com.qsoftware.forgemod.config.Config;
import com.sun.jna.Platform;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;

import java.awt.*;
import java.io.IOException;

public final class Utils {
    private Utils() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static ActionResultType shutdown() {
        if (!Config.allowShutdownPC.get()) {
            return ActionResultType.FAIL;
        }

        if (Platform.isWindows()) {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("shutdown /s /t 0");
            } catch (IOException e) {
                try {
                    runtime.exec("shutdown -s -t 0");
                } catch (IOException f) {
                    f.printStackTrace();
                    return ActionResultType.PASS;
                }
            }

            return ActionResultType.SUCCESS;
        } else if (Platform.isLinux()) {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("shutdown");
            } catch (IOException e) {
                try {
                    runtime.exec("sudo shutdown");
                } catch (IOException f) {
                    f.printStackTrace();
                    e.printStackTrace();
                    return ActionResultType.PASS;
                }
            }

            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
        }
    }

    public static <T> ActionResult<T> shutdown(T type) {
        ActionResultType shutdown = shutdown();
        if (shutdown == ActionResultType.FAIL) {
            return ActionResult.resultFail(type);
        } else if (shutdown == ActionResultType.PASS) {
            return ActionResult.resultPass(type);
        } else if (shutdown == ActionResultType.SUCCESS) {
            return ActionResult.resultSuccess(type);
        } else if (shutdown == ActionResultType.CONSUME) {
            return ActionResult.resultConsume(type);
        } else {
            return ActionResult.resultFail(type);
        }
    }
}
