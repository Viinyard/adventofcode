grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
import java.awt.Dimension;
}

root returns [ASD.Root out]
    @init {
        List<Point> coordinates = new ArrayList<>();
    }
    : dimensions number_of_bits (coordinate {
        coordinates.add($coordinate.out);
    })+ EOF {
        $out = new ASD.Root(coordinates, $dimensions.out, $number_of_bits.out);
    }
    |
    (coordinate {
        coordinates.add($coordinate.out);
    })+ EOF {
        $out = new ASD.Root(coordinates);
    }
    ;

dimensions returns [Dimension out]
    :
    'dimensions' x=INT ',' y=INT {
        $out = new Dimension(Integer.parseInt($x.text), Integer.parseInt($y.text));
    }
    ;

number_of_bits returns [int out]
    :
    'number of bits' INT {
        $out = Integer.parseInt($INT.text);
    }
    ;

coordinate returns [Point out]
    : x=INT ',' y=INT {
        $out = new Point(Integer.parseInt($x.text), Integer.parseInt($y.text));
    }
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;