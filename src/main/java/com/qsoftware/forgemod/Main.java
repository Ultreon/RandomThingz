package com.qsoftware.forgemod;

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
            String message = "\"QForgeMod couldn't detect what OS you have.\"\n";
            JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

//        String message = "\"You tried to open QForgeMod itself.\"\n"
//                + "You should place this file in the mods folder of your Minecraft instance.\n"
//                + path;

        String message = "Do you want to install QForgeMod?\n";
        final boolean pressedYes = JOptionPane.showConfirmDialog(new Frame(), message, "QForgeMod installation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0;

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

//    public static OS getOSType() {
//        String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
//        if (s.contains("win")) {
//            return OS.WINDOWS;
//        } else if (s.contains("mac")) {
//            return OS.OSX;
//        } else if (s.contains("solaris")) {
//            return OS.SOLARIS;
//        } else if (s.contains("sunos")) {
//            return OS.SOLARIS;
//        } else if (s.contains("linux")) {
//            return OS.LINUX;
//        } else {
//            return s.contains("unix") ? OS.LINUX : OS.UNKNOWN;
//        }
//    }
//
//    public enum OS {
//        LINUX,
//        SOLARIS,
//        WINDOWS {
//            protected String[] getOpenCommandLine(URL url) {
//                return new String[]{"rundll32", "url.dll,FileProtocolHandler", url.toString()};
//            }
//        },
//        OSX {
//            protected String[] getOpenCommandLine(URL url) {
//                return new String[]{"open", url.toString()};
//            }
//        },
//        UNKNOWN;
//
//        OS() {
//        }
//
//        public void openURL(URL url) {
//            try {
//                Process process = AccessController.doPrivileged((PrivilegedExceptionAction<Process>)(() -> Runtime.getRuntime().exec(this.getOpenCommandLine(url))));
//
//                for(String s : IOUtils.readLines(process.getErrorStream())) {
//                    System.err.println(s);
//                }
//
//                process.getInputStream().close();
//                process.getErrorStream().close();
//                process.getOutputStream().close();
//            } catch (IOException | PrivilegedActionException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        public void openFile(File fileIn) {
//            try {
//                this.openURL(fileIn.toURI().toURL());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        protected String[] getOpenCommandLine(URL url) {
//            String s = url.toString();
//            if ("file".equals(url.getProtocol())) {
//                s = s.replace("file:", "file://");
//            }
//
//            return new String[]{"xdg-open", s};
//        }
//
//    }
}
