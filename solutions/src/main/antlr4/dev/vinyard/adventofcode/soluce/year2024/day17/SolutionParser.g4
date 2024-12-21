parser grammar SolutionParser;

options {
    tokenVocab = SolutionLexer;
    language = Java;
}

@header {
import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.concurrent.atomic.AtomicLong;
}

root returns [ASD.Program out]
    : program EOF {
        $out = $program.out;
    }
    ;

program returns [ASD.Program out]
    @init {
        AtomicLong registerA = new AtomicLong();
        AtomicLong registerB = new AtomicLong();
        AtomicLong registerC = new AtomicLong();
    }
    :
    REGISTER_A INT {
        registerA.set(Long.parseLong($INT.text));
    }
    REGISTER_B INT {
        registerB.set(Long.parseLong($INT.text));
    }
    REGISTER_C INT {
        registerC.set(Long.parseLong($INT.text));
    }
    PROGRAM
    (instructions+=instruction[registerA, registerB, registerC])+ {
        List<ASD.Instruction> instructions = $instructions.stream().map(i -> i.out).toList();

        $out = new ASD.Program(registerA, registerB, registerC, instructions);
    }
    ;

instruction[AtomicLong registerA, AtomicLong registerB, AtomicLong registerC] returns [ASD.Instruction out]
    : opcode[registerA, registerB, registerC] operand[registerA, registerB, registerC] {
        $out = $opcode.out.setValue($operand.out);
    }
    ;

operand[AtomicLong registerA, AtomicLong registerB, AtomicLong registerC] returns [AtomicLong out]
    :
    LITERAL {
        $out = new AtomicLong(Long.parseLong($LITERAL.text));
    }
    | OPERAND_A {
        $out = registerA;
    }
    | OPERAND_B {
        $out = registerB;
    }
    | OPERAND_C {
        $out = registerC;
    }
    | OPERAND_RESERVED {
        // reserved
    }
    | INT_LITERAL {
        $out = new AtomicLong(Long.parseLong($INT_LITERAL.text));
    }
    ;

opcode[AtomicLong registerA, AtomicLong registerB, AtomicLong registerC] returns [ASD.Instruction out]
    :
    OPCODE_ADV {
        $out = new ASD.AdvInstruction();
    }
    | OPCODE_BXL {
        $out = new ASD.BxlInstruction();
    }
    | OPCODE_BST {
        $out = new ASD.BstInstruction();
    }
    | OPCODE_JNZ {
        $out = new ASD.JnzInstruction();
    }
    | OPCODE_BXC {
        $out = new ASD.BxcInstruction();
    }
    | OPCODE_OUT {
        $out = new ASD.OutInstruction();
    }
    | OPCODE_BDV {
        $out = new ASD.BdvInstruction();
    }
    | OPCODE_CDV {
        $out = new ASD.CdvInstruction();
    }
    ;