grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Color;
}

root returns [ASD.Root out]
    @init {
        List<ASD.Digger> diggers = new ArrayList<>();
    }
    : (digger { diggers.add($digger.out); })+ {
        $out = new ASD.Root(diggers);
    }
    ;

digger returns [ASD.Digger out]
    : direction NUMBER edgeColor {
        $out = new ASD.Digger($direction.out, Integer.parseInt($NUMBER.text), $edgeColor.out);
    }
    ;

edgeColor returns [Color out]
    : OPEN_PAREN SHARP NUMBER CLOSE_PAREN {
        $out = new Color(Integer.valueOf($NUMBER.text, 16));
    }
    ;

direction returns [ASD.Direction out]
    : UP {
        $out = ASD.Direction.NORTH;
    }
    | DOWN {
        $out = ASD.Direction.SOUTH;
    }
    | LEFT {
        $out = ASD.Direction.WEST;
    }
    | RIGHT {
        $out = ASD.Direction.EAST;
    }
    ;


DOWN
    : 'D'
    ;

UP
    : 'U'
    ;

LEFT
    : 'L'
    ;

RIGHT
    : 'R'
    ;

SHARP
    : '#'
    ;

OPEN_PAREN
    : '('
    ;

CLOSE_PAREN
    : ')'
    ;

NUMBER
    : [0-9a-fA-F]+
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;