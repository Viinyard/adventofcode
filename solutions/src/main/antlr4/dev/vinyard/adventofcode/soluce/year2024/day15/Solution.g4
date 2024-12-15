grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
}

root returns [ASD.Root out]
    : map NEWLINE*? moves NEWLINE*? EOF {
        $out = new ASD.Root($map.out, $moves.out);
    }
    ;

moves returns [List<ASD.Direction> out]
    @init {
        $out = new ArrayList();
    }
    : ((move {
        $out.add($move.out);
    })+ NEWLINE?)+
    ;

move returns [ASD.Direction out]
    : UP { $out = ASD.Direction.UP; }
    | DOWN { $out = ASD.Direction.DOWN; }
    | LEFT { $out = ASD.Direction.LEFT; }
    | RIGHT { $out = ASD.Direction.RIGHT; }
    ;

map returns [ASD.Warehouse out]
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
        $out = new ASD.Warehouse(grid);
    }
    ;

row[int y] returns [List<ASD.Entity> out]
    @init {
        $out = new ArrayList();
        int x = 0;
    }
    : (entity[x, y] {
        $out.add($entity.out);
        x++;
    })* NEWLINE
    ;

entity [int x, int y] returns [ASD.Entity out]
    @init {
        Point position = new Point(x, y);
    }
    : WALL { $out = new ASD.Wall(position); }
    | EMPTY { $out = new ASD.Empty(position); }
    | BOX { $out = new ASD.Box(position); }
    | PLAYER { $out = new ASD.Player(position); }
    ;

WALL
    : '#'
    ;

EMPTY
    : '.'
    ;

BOX
    : 'O'
    ;

PLAYER
    : '@'
    ;

UP
    : '^'
    ;

DOWN
    : 'v'
    ;

LEFT
    : '<'
    ;

RIGHT
    : '>'
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t]+ -> skip
    ;