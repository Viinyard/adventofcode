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
        $out.add($cell.out);
        colNumber++;
      })+ NEWLINE?
    ;

cell[int rowNumber, int colNumber] returns [ASD.Entity out]
    @init {
        Point point = new Point(colNumber, rowNumber);
    }
    : NORTH_SOUTH_PIPE {
        $out = new ASD.NorthSouthPipe(point);
    }
    | EAST_WEST_PIPE {
        $out = new ASD.EastWestPipe(point);
    }
    | NORTH_EAST_PIPE {
        $out = new ASD.NorthEastPipe(point);
    }
    | NORTH_WEST_PIPE {
        $out = new ASD.NorthWestPipe(point);
    }
    | SOUTH_WEST_PIPE {
        $out = new ASD.SouthWestPipe(point);
    }
    | SOUTH_EAST_PIPE {
        $out = new ASD.SouthEastPipe(point);
    }
    | GROUND {
        $out = new ASD.Ground(point);
    }
    | ANIMAL {
        $out = new ASD.Animal(point);
    }
    ;

NORTH_SOUTH_PIPE
    : '|'
    ;

EAST_WEST_PIPE
    : '-'
    ;

NORTH_EAST_PIPE
    : 'L'
    ;

NORTH_WEST_PIPE
    : 'J'
    ;

SOUTH_WEST_PIPE
    : '7'
    ;

SOUTH_EAST_PIPE
    : 'F'
    ;

GROUND
    : '.'
    ;

ANIMAL
    : 'S'
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t]+ -> skip
    ;