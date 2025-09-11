grammar Solution;

options {
    language = Java;
}

@header {
import java.util.HashMap;
}

root returns [ASD.Root out]
    @init {
        HashMap<String, ASD.Machine> machineMap = new HashMap<>();
    }
    : connection[machineMap]* EOF {
        $out = new ASD.Root(machineMap);
    }
    ;

connection [HashMap<String, ASD.Machine> machineMap]
    : m1=MACHINE {
        machineMap.putIfAbsent($m1.text, new ASD.Machine($m1.text));

    } COLON (m2=MACHINE {
        machineMap.putIfAbsent($m2.text, new ASD.Machine($m2.text));
        machineMap.get($m1.text).addConnection(machineMap.get($m2.text));
     })* NEWLINE?
    ;

COLON
    : ':'
    ;

MACHINE
    : [a-zA-Z]+
    ;

NEWLINE
    : '\r'? '\n'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;