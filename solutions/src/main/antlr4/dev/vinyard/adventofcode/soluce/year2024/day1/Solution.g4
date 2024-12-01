grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    :
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;