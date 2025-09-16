grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Dimension;
import java.awt.Point;
}

root returns [ASD.Root out]
    : grid EOF {
        $out = new ASD.Root($grid.squares, new Dimension($grid.x, $grid.y));
    }
    ;

grid returns [List<ASD.Square> squares, int y, int x]
    @init {
        $squares = new ArrayList<>();
        $y = 0;
    }
    : (row[$squares, $y++] {
        $x = Math.max($row.x, $x);
    })+
    ;

row [List<ASD.Square> squares, int y] returns [int x]
    @init {
        $x = 0;
    }
    : (square[$squares, $x++, $y])+ NEWLINE?
    ;

square [List<ASD.Square> squares, int x, int y]
    : ELEVATION {
        $squares.add(new ASD.Square(new Point($x, $y), $ELEVATION.text));
    }
    | START {
        $squares.add(new ASD.Square(new Point($x, $y), $START.text));
    }
    | END {
        $squares.add(new ASD.Square(new Point($x, $y), $END.text));
    }
    ;

START
    : 'S'
    ;

END
    : 'E'
    ;

ELEVATION
    : [a-z]
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