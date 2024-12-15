grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Dimension;
import java.util.Objects;
}

root[Dimension dimension] returns [ASD.Root out]
    : map[dimension] NEWLINE*? moves NEWLINE*? EOF {
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

map[Dimension dimension] returns [ASD.Warehouse out]
    @init {
        int y = 0;
    }
    : (rows+=row[y, dimension] {
        y+= dimension.height;
    })* {
        $out = new ASD.Warehouse($rows.stream().map(row -> row.out).flatMap(List::stream).filter(Objects::nonNull).toList());
    }
    ;

row[int y, Dimension dimension] returns [List<ASD.Entity> out]
    @init {
        $out = new ArrayList();
        int x = 0;
    }
    : (entity[x, y, dimension] {
        $out.add($entity.out);
        x+= dimension.width;
    })* NEWLINE
    ;

entity [int x, int y, Dimension dimension] returns [ASD.Entity out]
    @init {
        Point position = new Point(x, y);
    }
    : WALL { $out = new ASD.Wall(new Rectangle(position, dimension)); }
    | EMPTY { $out = null; }
    | BOX { $out = new ASD.Box(new Rectangle(position, dimension)); }
    | PLAYER { $out = new ASD.Player(new Rectangle(position, new Dimension(1, 1))); }
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