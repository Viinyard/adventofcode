grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
import java.awt.Dimension;
}

root returns [ASD.Root out]
    @init {
        int y = 0;
        int x = 0;
        List<ASD.Cell> cells = new ArrayList<>();
    }
    : (row[cells, y++] { x = Math.max(x, $row.out); })+ EOF {
        $out = new ASD.Root(cells, new Dimension(x, y));
    }
    ;
    
row [List<ASD.Cell> cells, int y] returns [int out]
    @init {
        int x = 0;
    }
    : (cell[x++, y] { cells.add($cell.out); })+ NEWLINE? { $out = x; }
    ;
    
cell [int x, int y] returns [ASD.Cell out]
    : START { $out = new ASD.Start(new Point(x, y)); }
    | EMPTY_SPACE { $out = new ASD.EmptySpace(new Point(x, y)); }
    | SPLITTER { $out = new ASD.Splitter(new Point(x, y)); }
    ;
    
START
    : 'S'
    ;
    
EMPTY_SPACE
    : '.'
    ;
    
SPLITTER
    : '^'
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