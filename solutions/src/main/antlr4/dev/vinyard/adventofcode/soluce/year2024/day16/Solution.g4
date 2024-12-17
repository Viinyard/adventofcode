grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
}

root returns [ASD.Root out]
    : puzzle NEWLINE*? EOF {
        $out = new ASD.Root($puzzle.out);
    }
    ;

puzzle returns [ASD.Puzzle out]
    @init {
        int y = 0;
    }
    : (rows+=row[y] {
        y++;
    })* {
        int numRows = $rows.size();
        int numCols = $rows.get(0).out.size();
        ASD.Entity[][] grid = new ASD.Entity[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            grid[i] = $rows.get(i).out.toArray(new ASD.Entity[0]);
        }
        $out = new ASD.Puzzle(grid);
    }
    ;

row[int y] returns [List<ASD.Entity> out]
    @init {
        $out = new ArrayList();
        int x = 0;
    }
    : (cell[x, y] {
        $out.add($cell.out);
        x++;
    })+ NEWLINE?
    ;

cell[int x, int y] returns [ASD.Entity out]
    @init {
        Point position = new Point(x, y);
    }
    : WALL {
        $out = new ASD.Wall(position);
    }
    | EMPTY {
        $out = new ASD.Empty(position);
    }
    | START {
        $out = new ASD.Start(position);
    }
    | END {
        $out = new ASD.End(position);
    }
    ;

WALL
    : '#'
    ;

EMPTY
    : '.'
    ;

NEWLINE
    : '\r'? '\n'
    ;

START
    : 'S'
    ;

END
    : 'E'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;