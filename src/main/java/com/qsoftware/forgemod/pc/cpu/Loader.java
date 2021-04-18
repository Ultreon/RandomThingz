package com.qsoftware.forgemod.pc.cpu;

import com.qsoftware.forgemod.pc.common.device.component.Rom;

public class Loader {
    private final CPU cpu;
    public Loader(CPU cpu) {
        this.cpu = cpu;
    }
    public void read(String program) {
        int address = Integer.parseInt(program.substring(0, 4), 16);
        program = program.substring(5);
        for (int i = 0; i < program.length(); i += 2) {
            //System.out.println("Poking "+String.format("%04X",addr)+": "+String.format("%02X",Integer.parseInt(program.substring(i, i + 2), 16)));
            cpu.pokeByte(address++, Integer.parseUnsignedInt(program.substring(i, i + 2), 16));
        }
    }

    @Deprecated
    public void write(int address, String program) {
        int b;
        program = program.substring(5);
        for (int i = 0; i < program.length(); i += 2) {
            //System.out.println("Poking "+String.format("%04X",address)+": "+String.format("%02X",Integer.parseInt(program.substring(i, i + 2), 16)));
            cpu.pokeByte(address++, Integer.parseUnsignedInt(program.substring(i, i + 2), 16));
        }
    }

    public void write(int address, byte[] bytecode) {
        for (byte b : bytecode) {
            //System.out.println("Poking "+String.format("%04X",address)+": "+String.format("%02X",Integer.parseInt(program.substring(i, i + 2), 16)));
            cpu.pokeByte(address++, b);
        }
    }

    public void read(Rom rom) {

    }
}
