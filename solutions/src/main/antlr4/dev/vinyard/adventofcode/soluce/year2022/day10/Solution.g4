grammar Solution;

options {
    language = Java;
}

@header {
import java.util.LinkedList;
}

root returns [ASD.Root out]
    @init {
        LinkedList<ASD.Instruction> instructions = new LinkedList<>();
    }
    : instruction[instructions]* {
        $out = new ASD.Root(instructions);
    }
    ;

instruction [List<ASD.Instruction> instructions]
    : ADDX INT {
        instructions.add(new ASD.AddX(Integer.parseInt($INT.text)));
    }
    | NOOP {
        instructions.add(new ASD.NoOp());
    }
    ;

ADDX
    : 'addx'
    ;

NOOP
    : 'noop'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '-'? ('0' | [1-9][0-9]*)
    ;

WS
    : [ \t\n\r]+ -> skip
    ;