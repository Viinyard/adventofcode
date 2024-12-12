grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
}

root returns [ASD.Garden out]
    @init {
        int rowNumber = 0;
    }
    : (rows+=row[rowNumber] {
        rowNumber++;
    })+ NEWLINE*? EOF {
        int numRows = $rows.size();
        int numCols = $rows.get(0).out.size();
        ASD.Plant[][] grid = new ASD.Plant[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            grid[i] = $rows.get(i).out.toArray(new ASD.Plant[0]);
        }
        $out = new ASD.Garden(grid);
    }
    ;

row[int rowNumber] returns [List<ASD.Plant> out]
    @init {
        $out = new ArrayList();
        int colNumber = 0;
    }
    : (cell[rowNumber, colNumber] {
        $out.add($cell.out);
        colNumber++;
    })+ NEWLINE?
    ;

cell[int rowNumber, int colNumber] returns [ASD.Plant out]
    @init {
        Point point = new Point(colNumber, rowNumber);
    }
    : PLANT { $out = new ASD.Plant(point, $PLANT.text); }
    ;

PLANT
    : [A-Z]
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t]+ -> skip
    ;