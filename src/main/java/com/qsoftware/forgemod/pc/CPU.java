package com.qsoftware.forgemod.pc;

@SuppressWarnings({"ConstantConditions", "unused"})
public class CPU {

    private byte x, y, ac, pcl, pch;
    private short pc;
    private boolean debugEnabled;
    private boolean isGood = true;
    private final byte [] mainMem;
    public Opcode opcode;

    CPU() {
        this(false);
    }

    CPU(boolean debugEnabled) {
        this(debugEnabled, new byte [0x10000]);
    }

    CPU(boolean debugEnabled, byte[] mainMem){
        this.mainMem = mainMem;
        this.opcode =Opcode.nop;
        this.pc = 0;
        this.debugEnabled = debugEnabled;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public void setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    public boolean isGood() {
        return isGood;
    }

    public void setGood(boolean good) {
        isGood = good;
    }

    public enum Opcode implements Runnable{


        adc(){public void run(){System.out.println("adc");}},
        and(){public void run(){System.out.println("and");}},
        asl(){public void run(){System.out.println("asl");}},
        bcc(){public void run(){System.out.println("bcc");}},
        bcs(){public void run(){System.out.println("bcs");}},
        beq(){public void run(){System.out.println("beq");}},
        bit(){public void run(){System.out.println("bit");}},
        bmi(){public void run(){System.out.println("bmi");}},
        bne(){public void run(){System.out.println("bne");}},
        bpl(){public void run(){System.out.println("bpl");}},
        brk(){public void run(){System.out.println("brk");}},
        bvc(){public void run(){System.out.println("bvc");}},
        bvs(){public void run(){System.out.println("bvs");}},
        clc(){public void run(){System.out.println("clc");}},
        cld(){public void run(){System.out.println("cld");}},
        cli(){public void run(){System.out.println("cli");}},
        clv(){public void run(){System.out.println("clv");}},
        cmp(){public void run(){System.out.println("cmp");}},
        cpx(){public void run(){System.out.println("cpx");}},
        cpy(){public void run(){System.out.println("cpy");}},
        dec(){public void run(){System.out.println("dec");}},
        dex(){public void run(){System.out.println("dex");}},
        dey(){public void run(){System.out.println("dey");}},
        eor(){public void run(){System.out.println("eor");}},
        inc(){public void run(){System.out.println("inc");}},
        inx(){public void run(){System.out.println("inx");}},
        iny(){public void run(){System.out.println("iny");}},
        jmp(){public void run(){System.out.println("jmp");}},
        jsr(){public void run(){System.out.println("jsr");}},
        lda(){public void run(){System.out.println("lda");}},
        ldx(){public void run(){System.out.println("ldx");}},
        ldy(){public void run(){System.out.println("ldy");}},
        lsr(){public void run(){System.out.println("lsr");}},
        nop(){public void run(){System.out.println("nop");}},
        ora(){public void run(){System.out.println("ora");}},
        pha(){public void run(){System.out.println("pha");}},
        php(){public void run(){System.out.println("php");}},
        pla(){public void run(){System.out.println("pla");}},
        plp(){public void run(){System.out.println("plp");}},
        rol(){public void run(){System.out.println("rol");}},
        ror(){public void run(){System.out.println("ror");}},
        rti(){public void run(){System.out.println("rti");}},
        rts(){public void run(){System.out.println("rts");}},
        sbc(){public void run(){System.out.println("sbc");}},
        sec(){public void run(){System.out.println("sec");}},
        sed(){public void run(){System.out.println("sed");}},
        sei(){public void run(){System.out.println("sei");}},
        sta(){public void run(){System.out.println("sta");}},
        stx(){public void run(){System.out.println("stx");}},
        sty(){public void run(){System.out.println("sty");}},
        tax(){public void run(){System.out.println("tax");}},
        tay(){public void run(){System.out.println("tay");}},
        tsx(){public void run(){System.out.println("tsx");}},
        txa(){public void run(){System.out.println("txa");}},
        txs(){public void run(){System.out.println("txs");}},
        tya(){public void run(){System.out.println("tya");}},
        ;

        public String mnemonic;
        public String addressMode;
        public byte code;
        public byte data;

        Opcode(){
            this.mnemonic = "";
        }

        public void print(){
            System.out.printf("Opcode: %02X %s %s\n", 
                              this.code, 
                              this.mnemonic.toUpperCase(),
                              this.addressMode);
        }

        public String getMode00(byte opcode){
            switch(opcode){
                case 0x00: return "Immediate";
                case 0x04: return "ZeroPaged";
                case 0x0C: return "Absolute";
                case 0x14: return "IndexedZeroPagedX";
                case 0x1C: return "IndexedAbsoluteX";
                default: return "Type 0 undefined";
            }
        }

        public String getMode01(byte opcode){
            switch(opcode){
                case 0x00: return "IndirectIndexedZeroPagedX";
                case 0x04: return "ZeroPaged";
                case 0x08: return "Immediate";
                case 0x0C: return "Absolute";
                case 0x10: return "IndirectedZeroPagedY";
                case 0x14: return "IndexedZeroPagedX";
                case 0x18: return "IndexedAbsoluteY";
                case 0x1C: return "IndexedAbsoluteX";
                default: return "Type 1 Undefined";         

            }

        }

        public String getMode02(byte opcode){
            switch(opcode){
                case 0x00: return "Immediate";
                case 0x04: return "ZeroPaged";
                case 0x08: return "Accumulator";
                case 0x0C: return "Absolute";
                case 0x14: return "IndexedZeroPagedX";
                case 0x1C: return "IndexedAbsoluteX";
                default: return "Type 2 Undefined";
            }
        }

        public String getMode03(byte opcode){ return "";}

        public void decode(){
            switch(this.code & 0x03){
                case 0x00: this.addressMode = getMode00((byte)(this.code & 0x1C)); break;
                case 0x01: this.addressMode = getMode01((byte)(this.code & 0x1C)); break;
                case 0x02: this.addressMode = getMode02((byte)(this.code & 0x1C)); break;
                case 0x03: this.addressMode = getMode03((byte)(this.code & 0x1C)); break;
                default: break;
            }
        }
    }

    public void init(){
        pc = 0;
    }

    public void start(){
        while(isGood){
            opcode.code = readMem(pc++);
            CPU.Opcode.valueOf(opcode.mnemonic).run();
        }

        if(!isGood){
            System.err.println("isGood == false");
        }
    }

    public byte readMem(short ptr){
        return mainMem[ptr];
    }

    public byte readMem(short ptr, byte addressMode){
        return mainMem[ptr];
    }

    public void exec(){
        opcode.decode();
        switch(opcode.code & 0xFF){
            case 0x69: case 0x65: case 0x75: 
            case 0x6D: case 0x7D: case 0x79: 
            case 0x61: case 0x71: opcode.mnemonic = "adc"; break;

            case 0x29: case 0x25: case 0x35: 
            case 0x2D: case 0x3D: case 0x39:
            case 0x21: case 0x31: opcode.mnemonic = "and"; break;

            case 0x0A: case 0x06: case 0x16:
            case 0x0E: case 0x1E: opcode.mnemonic = "asl"; break;

            default: opcode.mnemonic = null;
        }

        //Opcodes.valueOf(this.mnemonic).run();
    }

    public void testOpcodes(){
        opcode.code = 0;
        while((opcode.code & 0xFF) < 0xFF){
            //System.out.printf("PC = 0x%04X \n", PC);
            exec();
            if(opcode.mnemonic != null)
                opcode.print();
                //Opcode.valueOf(opcode.mnemonic).run();

            opcode.code++;
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        CPU cpu = new CPU(true);
        cpu.init();
        cpu.testOpcodes();
    }
}