package com.qtech.randomthingz;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        String path;
        String userName = SystemUtils.getUserName();
        if (SystemUtils.IS_OS_WINDOWS_7 || SystemUtils.IS_OS_WINDOWS_8 || SystemUtils.IS_OS_WINDOWS_10) {
            path = "C:\\Users\\" + userName + "\\AppData\\Roaming\\.minecraft\\mods";
        } else if (SystemUtils.IS_OS_LINUX) {
            path = "/home/" + userName + "/.minecraft/mods";
        } else if (SystemUtils.IS_OS_MAC) {
            path = "/Users/" + userName + "/.minecraft/mods";
        } else {
            String message = "\"Random Thingz couldn't detect what OS you have.\"\n";
            JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String message = "Do you want to install RandomThingz?\n";
        final boolean pressedYes = JOptionPane.showConfirmDialog(new Frame(), message, "Random Thingz installation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0;

        if (pressedYes) {
            File qfmFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File dir = new File(path);

            try {
                FileUtils.copyFile(qfmFile, new File(dir, qfmFile.getName()));
            } catch (IOException e) {
                e.printStackTrace();
                String error = "Failed to install mod file.\n"
                        + "You should place this file on yourself in the mods folder:"
                        + dir;
                JOptionPane.showMessageDialog(new Frame(), error, "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            String info = "Successfully installed mod file.\n";
            JOptionPane.showMessageDialog(new Frame(), info, "Success", JOptionPane.INFORMATION_MESSAGE);
        }

        System.exit(0);
    }
}
