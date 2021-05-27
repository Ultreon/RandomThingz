package com.qtech.randomthingz.test;

import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetWindowsVersion {
    private static final Runtime runtime = Runtime.getRuntime();

    public static void main(String[] args) {

        Process process;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder();
        String stdOutLine = null;
        String windowsVersion = null;

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
            Integer integer = Integer.valueOf(group);
//            return integer > 16299 && integer <= 19042;
            System.out.println(group);
            System.out.println(integer);
        } else {
            throw new IllegalStateException("Invalid windows version!");
        }

        System.out.println(SystemUtils.OS_VERSION);
    }
}
