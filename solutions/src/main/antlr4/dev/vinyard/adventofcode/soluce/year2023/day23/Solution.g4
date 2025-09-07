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
        $out = new ASD.Root($grid.out);
    }
    ;

grid returns [ASD.Grid out]
    @init {
        List<ASD.Cell> in = new ArrayList<>();
        int y = 0;
        int x = 0;
    }
    : (row[in, y++] {
        x = Math.max(x, $row.x);
    })* {
        $out = new ASD.Grid(in, new Dimension(x, y));
    }
    ;


row [List<ASD.Cell> in, int y] returns [int x]
    @init {
        $x = 0;
    }
    : (cell[$in, $x++, $y])+ NEWLINE?
    ;

cell [List<ASD.Cell> in, int x, int y]
    : PATH {
        in.add(new ASD.Path(new Point(x, y)));
    }
    | FOREST {
        in.add(new ASD.Forest(new Point(x, y)));
    }
    | STEEP_SLOPE_NORTH {
        in.add(new ASD.SteepSlopeNorth(new Point(x, y)));
    }
    | STEEP_SLOPE_SOUTH {
        in.add(new ASD.SteepSlopeSouth(new Point(x, y)));
    }
    | STEEP_SLOPE_WEST {
        in.add(new ASD.SteepSlopeWest(new Point(x, y)));
    }
    | STEEP_SLOPE_EAST {
        in.add(new ASD.SteepSlopeEast(new Point(x, y)));
    }
    ;


PATH
    : '.'
    ;

FOREST
    : '#'
    ;

STEEP_SLOPE_NORTH
    : '^'
    ;

STEEP_SLOPE_SOUTH
    : 'v'
    ;

STEEP_SLOPE_WEST
    : '<'
    ;

STEEP_SLOPE_EAST
    : '>'
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