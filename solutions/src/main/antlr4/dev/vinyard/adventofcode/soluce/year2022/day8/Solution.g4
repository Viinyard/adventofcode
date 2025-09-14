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
        List<ASD.Tree> trees = new ArrayList<>();
    }
    : grid[trees] NEWLINE* EOF{
          $out = new ASD.Root(new Dimension($grid.x, $grid.y), trees);
    }
    ;

grid [List<ASD.Tree> trees] returns [int x, int y]
    @init {
        $y = 0;
    }
    : (row[trees, $y++] {
        $x = Math.max($x, $row.x);
    })+
    ;

row [List<ASD.Tree> trees, int y] returns [int x]
    @init {
        $x = 0;
    }
    : tree[trees, $x++, y]+ NEWLINE?
    ;

tree [List<ASD.Tree> trees, int x, int y]
    : height=INT {
        trees.add(new ASD.Tree(new Point(x, y), Integer.parseInt($height.text)));
    }
    ;

NEWLINE
    : '\r'? '\n'
    ;

INT
    : [0-9]
    ;

WS
    : [ \t\n\r]+ -> skip
    ;