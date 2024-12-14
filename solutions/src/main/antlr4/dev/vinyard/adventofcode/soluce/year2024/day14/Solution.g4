grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
}

root returns [ASD.Root out]
    @init {
        List<ASD.Robot> robots = new ArrayList<>();
    }
    : (robot {
        robots.add($robot.out);
    })* EOF {
        $out = new ASD.Root(robots);
    }
    ;

robot returns [ASD.Robot out]
    : POSITION p=position VELOCITY v=position {
        $out = new ASD.Robot($p.out, $v.out);
    }
    ;

position returns [Point out]
    : x=INT ',' y=INT {
        $out = new Point(Integer.parseInt($x.text), Integer.parseInt($y.text));
    }
    ;

fragment EQUALS : '=';

POSITION
    : 'p' EQUALS
    ;

VELOCITY
    : 'v' EQUALS
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '-'? ('0' | [1-9][0-9]*)
    ;

WS
    : [ \t\n\r]+ -> skip
    ;