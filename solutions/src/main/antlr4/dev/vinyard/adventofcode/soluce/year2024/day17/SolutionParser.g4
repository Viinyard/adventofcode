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
        List<Long> source = new ArrayList<>();
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
    (instructions+=instruction[registerA, registerB, registerC, source])+ {
        List<ASD.Instruction> instructions = $instructions.stream().map(i -> i.out).toList();

        $out = new ASD.Program(registerA, registerB, registerC, instructions, source);
    }
    ;

instruction[AtomicLong registerA, AtomicLong registerB, AtomicLong registerC, List<Long> source] returns [ASD.Instruction out]
    : opcode[registerA, registerB, registerC, source] operand[registerA, registerB, registerC, source] {
        $out = $opcode.out.setValue($operand.out);
    }
    ;

operand[AtomicLong registerA, AtomicLong registerB, AtomicLong registerC, List<Long> source] returns [AtomicLong out]
    :
    LITERAL {
        $out = new AtomicLong(Long.parseLong($LITERAL.text));
        source.add(Long.parseLong($LITERAL.text));
    }
    | OPERAND_A {
        $out = registerA;
        source.add(Long.parseLong($OPERAND_A.text));
    }
    | OPERAND_B {
        $out = registerB;
        source.add(Long.parseLong($OPERAND_B.text));
    }
    | OPERAND_C {
        $out = registerC;
        source.add(Long.parseLong($OPERAND_C.text));
    }
    | OPERAND_RESERVED {
        // reserved
        source.add(Long.parseLong($OPERAND_RESERVED.text));
    }
    | INT_LITERAL {
        $out = new AtomicLong(Long.parseLong($INT_LITERAL.text));
        source.add(Long.parseLong($INT_LITERAL.text));
    }
    ;

opcode[AtomicLong registerA, AtomicLong registerB, AtomicLong registerC, List<Long> source] returns [ASD.Instruction out]
    :
    OPCODE_ADV {
        $out = new ASD.AdvInstruction();
        source.add(Long.parseLong($OPCODE_ADV.text));
    }
    | OPCODE_BXL {
        $out = new ASD.BxlInstruction();
        source.add(Long.parseLong($OPCODE_BXL.text));
    }
    | OPCODE_BST {
        $out = new ASD.BstInstruction();
        source.add(Long.parseLong($OPCODE_BST.text));
    }
    | OPCODE_JNZ {
        $out = new ASD.JnzInstruction();
        source.add(Long.parseLong($OPCODE_JNZ.text));
    }
    | OPCODE_BXC {
        $out = new ASD.BxcInstruction();
        source.add(Long.parseLong($OPCODE_BXC.text));
    }
    | OPCODE_OUT {
        $out = new ASD.OutInstruction();
        source.add(Long.parseLong($OPCODE_OUT.text));
    }
    | OPCODE_BDV {
        $out = new ASD.BdvInstruction();
        source.add(Long.parseLong($OPCODE_BDV.text));
    }
    | OPCODE_CDV {
        $out = new ASD.CdvInstruction();
        source.add(Long.parseLong($OPCODE_CDV.text));
    }
    ;