grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
import java.awt.Rectangle;
}

root returns [ASD.Root out]
    @init {
        List<ASD.Pattern> patterns = new ArrayList<>();
    }
    : (pattern {
        patterns.add($pattern.out);
    } empty_line?)+ EOF {
        $out = new ASD.Root(patterns);
    }
    ;

empty_line
    : NEWLINE
    ;

pattern returns [ASD.Pattern out]
    @init {
        int y = 0;
        int x = 0;
        List<Point> points = new ArrayList<>();
    }
    : (line[y++] {
        points.addAll($line.out);
        x = Math.max(x, $line.x);
    })+ {
        $out = new ASD.Pattern(points, new Rectangle(0, 0, x, y));
    }
    ;

line [int y] returns [List<Point> out, int x]
    @init {
        $x = 0;
        $out = new ArrayList<>();
    }
    : ((ROCK {
        $out.add(new Point($x, y));
    } | ASH) {
        $x++;
    })+ NEWLINE? {

    }
    ;

ASH
    : '.'
    ;

ROCK
    : '#'
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