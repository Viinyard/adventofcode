grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
import java.util.function.IntPredicate;
import java.util.Objects;
}

root returns [ASD.Root out]
    @init {
        List<List<ASD.Node>> raceTrack = new ArrayList<>();
        IntPredicate cheat = (pico) -> pico >= 100;
    }
    :
    (cheat {
        cheat = $cheat.out;
    } )?
    (row[raceTrack.size()] {
        raceTrack.add($row.out);
    })* NEWLINE*? EOF {
        $out = new ASD.Root(raceTrack, cheat);
    }
    ;

cheat returns [IntPredicate out]
    :
    'picoseconds' SEMICOLON INT NEWLINE {
        $out = (pico) -> pico == Integer.parseInt($INT.text);
    }
    ;

row[int y] returns [List<ASD.Node> out]
    @init {
        $out = new ArrayList<>();
    }
    :
    (node[$out.size(), y] {
        $out.add($node.out);
    })+ NEWLINE?
    ;

node[int x, int y] returns [ASD.Node out]
    @init {
        Point position = new Point(x, y);
    }
    :
    WALL {
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

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

SEMICOLON
    : ':'
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

WS
    : [ \t]+ -> skip
    ;