grammar Solution;

options {
    language = Java;
}

@header {
import java.util.LinkedList;
}

root returns [ASD.Root out]
    @init {
       LinkedList<String> datastream = new LinkedList<>();
    }
    : char[datastream]+ EOF {
        $out = new ASD.Root(datastream);
    }
    ;

char [LinkedList<String> datastream]
    : CHAR { datastream.addLast($CHAR.text); }
    ;

CHAR
    : [a-z]
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;