grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    : instructions EOF {
        $out = new ASD.Root($instructions.out);
    }
    ;

instructions returns [List<ASD.Instruction> out]
    @init {
        $out = new ArrayList<>();
    }
    : (instruction {
        $out.add($instruction.out);
    } COMMA?)+
    ;

instruction returns [ASD.Instruction out]
    @init {
        StringBuilder sb = new StringBuilder();
    }
    : (LABEL { sb.append($LABEL.text); } )+ DASH {
        $out = new ASD.DashInstruction(sb.toString());
    }
    | (LABEL { sb.append($LABEL.text); } )+ EQUALS_SIGN FOCAL_LENGTH {
        $out = new ASD.FocalLengthInstruction(sb.toString(), Integer.parseInt($FOCAL_LENGTH.text));
    }
    ;

COMMA
    : ','
    ;

FOCAL_LENGTH
   // integer part forbis leading 0s (e.g. `01`)
   : [0-9]
   ;

EQUALS_SIGN
    : '='
    ;

DASH
    : '-'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;

LABEL
    : .
    ;