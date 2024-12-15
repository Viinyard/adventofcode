grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
}

root returns [ASD.Root out]
    @init {
        int y = 0;
    }
    : (rows+=row[y] {
        y++;
    })+ NEWLINE*? EOF {
        $out = new ASD.Root($rows.stream().map(row -> row.out).flatMap(List::stream).toList());
    }
    ;

row[int y] returns [List<ASD.Galaxy> out]
    @init {
        $out = new ArrayList();
        int x = 0;
    }
    : (SPACE {
        x++;
    }| galaxy[y, x] {
        $out.add($galaxy.out);
        x++;
    })+ NEWLINE?
    ;

galaxy[int y, int x] returns [ASD.Galaxy out]
    : GALAXY { $out = new ASD.Galaxy(new Point(x, y)); }
    ;

SPACE
    : '.'
    ;

GALAXY
    : '#'
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t]+ -> skip
    ;