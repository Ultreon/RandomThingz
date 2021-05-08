package com.qtech.randomthingz.pc.cpu;

public final class CPU {
    private final int NEGATIVE = 128, OVERFLOW = 64, ZERO = 2, CARRY = 1;
    private int ac, xr, yr, st, sp, pc;
    private final int[] memory;
    private boolean cpurunning;
    
    public CPU() {
        memory = new int[65536];
        for (int i = 0; i < 65536; i++) {
            memory[i] = 0;
        }
        cpurunning = true;
    }
    /**
     * Resets emulator
     * <p>
     * also clears ST (lda #$00:pha:plp) and resets SP (ldx #$ff:txs)
     * </p>
     */
    public void reset() {
        pc = memory[0xfffc] + 256 * memory[0xfffd];
        sp = 0xff;    // ?
        st = 0b00000000; // ?
    }
    /**
     * Writes a byte to a specified address
     * 
     * @param addr  The address ($0000-$ffff)
     * @param pbyte The byte to poke
     */
    public void pokeByte(int addr, int pbyte) {
        memory[addr] = pbyte;
    }
    /**
     * Reads a byte
     * 
     * @param addr
     * @return      Byte from the address
     */
    public int peekByte(int addr) {
        return memory[addr];
    }
    private int peekWord(int addr) { 
        return memory[addr] + 256 * memory[addr + 1];
    }
    private int immediateByte() {
        return peekByte(pc);
    }
    private int addrZPiY() {
        return peekWord(peekByte(pc)) + yr;
    }
    private int addrZPiX() { // Dafuq
        return peekWord(peekByte(pc) + xr);
    }
    private int addrAbs() {
        int result = peekWord(pc);
        updatePC();
        return result;
    }
    private int addrBranch() {
        return pc + 1 + (byte)peekByte(pc);
    }
    private int addrAbsInd() {
        int result = peekWord(peekWord(pc));
        updatePC();
        return result;
    }
    private int addrAbsX() {
        int result = peekWord(pc) + xr;
        updatePC();
        return result;
    }
    private int addrZP() {
        return peekByte(peekByte(pc));
    }
    private int addrZPX() {
        return (peekWord(pc) & 255) + xr;
    }
    private int addrZPY() {
        return (peekWord(pc) & 255) + yr;
    }
    private int addrAbsY() {
        int result = peekWord(pc) + yr;
        updatePC();
        return result;
    }
    private void updatePC() {
        pc = (pc + 1) % 65536;
    }
    private void lda(int b) {
        ac = b;
        updateFlags(b);
        updatePC();
    }
    private void ldx(int b) {
        xr = b;
        updateFlags(b);
        updatePC();
    }
    private void ldy(int b) {
        yr = b;
        updateFlags(b);
        updatePC();
    }
    private void asl(int b) {
        int c = b & 128;
        ac = (b << 1) & 255;
        setCarry(c);
        updateFlags(ac);
        updatePC();
    }
    private void and(int b) {
        ac = ac & b;
        updateFlags(ac);
        updatePC();
    }
    private void ora(int b) {
        ac = ac | b;
        updateFlags(ac);
        updatePC();
    }
    private void eor(int b) {
        ac = ac ^ b;
        updateFlags(ac);
        updatePC();
    }
    private void sta(int addr) {
        memory[addr] = ac;
        updatePC();
    }
    private void stx(int addr) {
        memory[addr] = xr;
        updatePC();
    }
    private void sty(int addr) {
        memory[addr] = yr;
        updatePC();
    }
    private void push(int b) {
        memory[0x100 + sp] = b;
        sp = (sp + 255) & 255; 
    }
    private int pop() {
        sp = (sp + 1) & 255;
        return memory[0x100 + sp];
    }
    private void branch(int flag, int state) {
        if ((st & flag) == state) {
            pc = addrBranch();
        } else {
            updatePC();
        }
    }
    private void updateFlags(int b) {
        setNegative(b);
        setZero(b);
    }
    private void setNegative(int b) {
        if (b > 0) {
            setBit(NEGATIVE);
        } else {
            clearBit(NEGATIVE);
        }
    }
    private void setCarry(int b) {
        if (b > 0) {
            setBit(CARRY);
        } else {
            clearBit(CARRY);
        }
    }
    private void setZero(int b) {
        if (b == 0) { // !
            setBit(ZERO);
        } else {
            clearBit(ZERO);
        }
    }
    private void clearBit(int bit) {
        st &= 255 - bit;
    }
    private void setBit(int bit) {
        st |= bit;
    }
    /**
     * Executes an opcode
     * 
     * @return false if the program hat ended or failed
     */
    public boolean execute() {
        int opcode = peekByte(pc);
        if (!cpurunning) {
            return false;
        }
        updatePC();
        switch (opcode) {
            case 0x00: // BRK
                System.out.println(String.format("* BREAK at $%04X", pc - 1));
                cpurunning = false;
                break;
            case 0x01: // ORA (--,X)
                ora(addrZPiX());
                break;
            case 0x05: // ORA --
                ora(addrZP());
                break;
            case 0x06: // ASL --
                asl(addrZP());
                break;
            case 0x08: // PHP
                push(st);
                break;
            case 0x09: // ORA #--
                ora(immediateByte());
                break;
            case 0x0a: // ASL
                asl(ac);
                break;
            case 0x0d: // ORA ----
                ora(addrAbs());
                break;
            case 0x0e: // ASL ----
                asl(addrAbs());
                break;
            case 0x10: // BPL ----
                branch(NEGATIVE, 0);
                break;
            case 0x11: // ORA (--),Y
                ora(addrZPiY());
                break;
            case 0x15: // ORA --,X
                ora(addrZPX());
                break;
            case 0x16: // ASL --,X
                asl(addrZPX());
                break;
            case 0x18: // CLC
                clearBit(CARRY);
                break;
            case 0x19: // ORA ----,Y
                ora(addrAbsY());
                break;
            case 0x1d: // ORA ----,X
                ora(addrAbsX());
                break;
            case 0x1e: // ASL ----,X
                asl(addrAbsX());
                break;
            case 0x20: // JSR ----
                int target = addrAbs();
                push(pc >> 8);
                push(pc & 255);
                pc = target;
                break;
            case 0x21: // AND (--,X)
                and(addrZPiX());
                break;
            case 0x25: // AND --,X
                and(addrZP());
                break;
            case 0x28: // PLP
                st = pop();
                break;
            case 0x29: // AND #--
                and(immediateByte());
                break;
            case 0x2d: // AND ----
                and(addrAbs());
                break;
            case 0x30: // BMI ----
                branch(NEGATIVE, NEGATIVE);
                break;
            case 0x31: // AND (--),Y
                and(addrZPiY());
                break;
            case 0x35:  // AND --,X
                and(addrZPX());
                break;
            case 0x38: // SEC
                setBit(CARRY);
                break;
            case 0x39: // AND ----,Y
                and(addrAbsY());
                break;
            case 0x3d: // AND ----,X
                and(addrAbsX());
                break;
            case 0x41: // EOR (--,X)
                eor(addrZPiX());
                break;
            case 0x45: // EOR --
                eor(addrZP());
                break;
            case 0x48: // PHA
                push(ac);
                break;
            case 0x49: // EOR #--
                eor(immediateByte());
                break;
            case 0x4c: // JMP ----
                pc = addrAbs();
                break;
            case 0x4d: // EOR ----
                eor(addrAbs());
                break;
            case 0x51: // EOR (--),Y
                eor(addrZPiY());
                break;
            case 0x55: // EOR --,X
                eor(addrZPX());
                break;
            case 0x59: // EOR ----,Y
                eor(addrAbsY());
                break;
            case 0x5d: // EOR ----,X
                eor(addrAbsX());
                break;
            case 0x60: // RTS
                pc = pop() + 256 * pop();
                updatePC();
                break;
            case 0x68: // PLA
                ac = pop();
                break;
            case 0x6c: // JMP (----)
                pc = addrAbsInd();
                break;
            case 0x84: // STY --
                sty(addrZP());
                break;
            case 0x86: // STX --
                stx(addrZP());
                break;
            case 0x88: // DEY
                yr = (yr + 255) % 256;
                updateFlags(yr);
                break;
            case 0x8a: // TXA
                ac = xr;
                break;
            case 0x8c: // STY ----
                sty(addrAbs());
                break;
            case 0x8d: // STA ----
                sta(addrAbs());
                break;
            case 0x8e: // STX ----
                stx(addrAbs());
                break;
            case 0x90: // BCC ----
                branch(CARRY, 0);
                break;
            case 0x91: // STA (--),Y
                sta(addrZPiY());
                break;
            case 0x94: // STY --,X
                sty(addrZPX());
                break;
            case 0x96: // STX --,Y
                stx(addrZPY());
                break;
            case 0x98: // TYA
                ac = yr;
                break;
            case 0x9a: // TXS
                sp = xr;
                break;
            case 0x9d: // STA ----,X
                sta(addrAbsX());
                break;
            case 0xa0: // LDY #--
                ldy(immediateByte());
                break;
            case 0xa1: // LDA (--,X)
                lda(addrZPiX());
                break;
            case 0xa2: // LDX #--
                ldx(immediateByte());
                break;
            case 0xa5: // LDA --
                lda(addrZP());
                break;
            case 0xa8: // TAY
                yr = ac;
                break;
            case 0xa9: // LDA #--
                lda(immediateByte());
                break;
            case 0xaa: // TAX
                xr = ac;
                break;
            case 0xad: // LDA ----
                lda(addrAbs());
                break;
            case 0xb0: // BCS ----
                branch(CARRY, CARRY);
                break;
            case 0xb1: // LDA (--),Y
                lda(addrZPiY());
                break;
            case 0xb5: // LDA --,X
                lda(addrZPX());
                break;
            case 0xb9: // LDA ----,Y
                lda(addrAbsY());
                break;
            case 0xba: // TSX
                xr = sp;
                break;
            case 0xbd: // LDA ----,X
                lda(addrAbsX());
                break;
            case 0xca: // DEX
                xr = (xr + 255) % 256;
                updateFlags(xr);
                break;
            case 0xd0: // BNE ----
                branch(ZERO, 0);
                break;
            case 0xe8: // INX
                xr = (xr + 1) % 256;
                updateFlags(xr);
                break;
            case 0xea: // NOP
                break;
            case 0xf0: // BEQ ----
                branch(ZERO, ZERO);
                break;
            case 0xff: // INT #-- // Test command
                int c = immediateByte();
                updatePC();
                switch (c) {
                    case 0:
                        System.out.print(ac); // Print AC as number
                        break;
                    case 1:
                        System.out.printf("%c", (char)ac); // Print AC as character
                        break;
                    default:
                        System.out.println(String.format("* Unknown INT at $%04X", pc - 1));
                        cpurunning = false;
                }
                break;
            default:
                System.out.println("Error: $" + String.format("%04X: %02X", pc - 1, peekByte(pc - 1)));
                cpurunning = false;
        }
        return true;
    }
}
