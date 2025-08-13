grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
import java.awt.Rectangle;
}

root returns [ASD.Root out]
    : platform EOF {
        $out = new ASD.Root($platform.out);
    }
    ;

platform returns [ASD.Platform out]
    @init {
        int y = 0;
        List<ASD.Entity> entities = new ArrayList<>();

    }
    : line[entities, y++]+ {
        $out = new ASD.Platform(entities, new Rectangle(0, 0, $line.x, y));
    }
    ;

line [List<ASD.Entity> entities, int y] returns [int x]
    @init {
        $x = 0;
    }
    : entity[$entities, $x++, y]+ NEWLINE?
    ;

entity [List<ASD.Entity> entities, int x, int y]
    : CUBE_SHAPED_ROCK {
        $entities.add(new ASD.CubeShapedRock(new Point($x, $y)));
    }
    | ROUNDED_ROCK {
        $entities.add(new ASD.RoundedRock(new Point($x, $y)));
    }
    | EMPTY_SPACE {
        $entities.add(new ASD.EmptySpace(new Point($x, $y)));
    }
    ;

ROUNDED_ROCK
    : 'O'
    ;

CUBE_SHAPED_ROCK
    : '#'
    ;

EMPTY_SPACE
    : '.'
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;