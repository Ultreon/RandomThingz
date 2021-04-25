package com.qtech.forgemod.pc.common;

import com.qtech.forgemod.pc.cpu.CPU;
import com.qtech.forgemod.pc.common.device.component.Rom;
import com.qtech.forgemod.pc.common.device.component.Win;
import com.qtech.forgemod.pc.cpu.Loader;

import java.io.IOException;
import java.nio.file.*;

public class Run {
    public static final Path ROM_FILE = Paths.get("rom.bin");
    public static final Path PROGRAM_FILE = Paths.get("program.rom");
    public static final short ROM_SIZE = (short) 0xffff;

    public static void main(String[] args) throws IOException {
//        String program = "2000-20-00-30-a9-02-a2-0a-9d-00-04-ca-10-fa-a0-03-91-a0-88-d0-fb-00";
        if (!Files.exists(ROM_FILE)) {
            Files.createFile(ROM_FILE);
            Rom rom;
            if (Files.exists(PROGRAM_FILE)) {
                rom = Rom.of(Files.readAllBytes(ROM_FILE));
            } else {
                rom = Rom.empty();
            }
            Files.write(ROM_FILE, rom.getBytecode(), StandardOpenOption.CREATE_NEW, StandardOpenOption.READ);
        }

        Rom rom = Rom.of(Files.readAllBytes(ROM_FILE));
        CPU cpu = new CPU();
        Loader loader = new Loader(cpu);
        loader.read(rom);

        loader.read("fffc-0020"); // Reset vector
        loader.read("00a0-2004"); // Test vector for (--),Y
        loader.write(0x2000, Files.readAllBytes(Paths.get("rom.bin")));
//        loader.read(program);
        loader.read("3000-a934ff01a932ff0160");
        cpu.reset();
        for (int i = 0; i < 100; i++) {
            cpu.execute();
        }        
        java.awt.EventQueue.invokeLater(() -> {
            new Win(cpu).setVisible(true);
        });
    }
}
