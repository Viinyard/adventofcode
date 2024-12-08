grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
}

root returns [ASD.Root out]
    @init {
        int rowNumber = 0;
    }
    : (rows+=row[rowNumber] {
      rowNumber++;
    } )+ NEWLINE*? EOF {
        int numRows = $rows.size();
        int numCols = $rows.get(0).out.size();
        ASD.Entity[][] grid = new ASD.Entity[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            grid[i] = $rows.get(i).out.toArray(new ASD.Entity[0]);
        }
        $out = new ASD.Root(grid);
    }
    ;

row[int rowNumber] returns [List<ASD.Entity> out]
    @init {
        $out = new ArrayList<>();
        int colNumber = 0;
    }
    : (cell[rowNumber, colNumber] {
        $out.add($cell.entity);
        colNumber++;
      })+ NEWLINE?
    ;

cell[int rowNumber, int colNumber] returns [ASD.Entity entity]
    @init {
        Point point = new Point(colNumber, rowNumber);
    }
    : EMPTY { $entity = new ASD.Empty(point); }
    | ANTENNA { $entity = new ASD.Antenna(point, $ANTENNA.text); }
    ;

EMPTY
    : '.'
    ;

NEWLINE
    : '\r'? '\n'
    ;

ANTENNA:
    [A-Za-z0-9]
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t]+ -> skip
    ;