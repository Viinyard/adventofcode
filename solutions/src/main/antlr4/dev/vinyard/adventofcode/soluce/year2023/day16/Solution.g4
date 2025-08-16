grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
import java.awt.Dimension;
}

root returns [ASD.Root out]
    : lines EOF {
        $out = new ASD.Root($lines.out, new Dimension($lines.x, $lines.y));
    }
    ;

lines returns [List<ASD.Tile> out, int x, int y]
    @init {
        $y = 0;
        $out = new ArrayList<>();
    }
    : (line[$out, $y++] NEWLINE? {
        $x = Math.max($x, $line.x);
    })+ EOF
    ;

line [List<ASD.Tile> tiles, int y] returns [int x]
    @init {
        $x = 0;
    }
    : (tile[$x++, $y] { $tiles.add($tile.out); })+ NEWLINE?
    ;

tile [int x, int y] returns [ASD.Tile out]
    : EMPTY_SPACE {
        $out = new ASD.EmptySpace(new Point(x, y));
    }
    | POSITIVE_MIRROR {
        $out = new ASD.PositiveMirror(new Point(x, y));
    }
    | NEGATIVE_MIRROR {
        $out = new ASD.NegativeMirror(new Point(x, y));
    }
    | VERTICAL_SPLITTER {
        $out = new ASD.VerticalSplitter(new Point(x, y));
    }
    | HORIZONTAL_SPLITTER {
        $out = new ASD.HorizontalSplitter(new Point(x, y));
    }
    ;

EMPTY_SPACE
    : '.'
    ;

POSITIVE_MIRROR
    : '\\'
    ;

NEGATIVE_MIRROR
    : '/'
    ;

VERTICAL_SPLITTER
    : '|'
    ;

HORIZONTAL_SPLITTER
    : '-'
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;