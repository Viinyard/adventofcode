grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Dimension;
import java.awt.Point;
}

root returns [ASD.Root out]
    : steps grid EOF {
        $out = new ASD.Root($grid.out, $steps.out);
    }
    ;

steps returns [int out]
    : STEPS COLON INT NEWLINE? {
        $out = Integer.parseInt($INT.text);
    }
    ;

grid returns [ASD.Grid out]
    @init {
        List<ASD.Cell> cells = new ArrayList<>();
        int y = 0;
        int x = 0;
    }
    : (row[cells, y++] {
        x = Math.max($row.x, x);
    } )* {
        $out = new ASD.Grid(cells, new Dimension(x, y));
    }
    ;

row [List<ASD.Cell> cells, int y] returns [int x]
    @init {
        $x = 0;
    }
    : (cell[$cells, $x++, $y])+ NEWLINE?
    ;

cell [List<ASD.Cell> cells, int x, int y] returns [ASD.Cell out]
    : ROCK {
        $cells.add(new ASD.Rock(new Point($x, $y)));
    }
    | PLOT {
        $cells.add(new ASD.Plot(new Point($x, $y)));
    }
    | ELVE {
        $cells.add(new ASD.Elve(new Point($x, $y)));
    }
    ;

ROCK
    : '#'
    ;

PLOT
    : '.'
    ;

ELVE
    : 'S'
    ;

NEWLINE
    : '\r'? '\n'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

STEPS
    : 'Steps'
    ;

COLON
    : ':'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;