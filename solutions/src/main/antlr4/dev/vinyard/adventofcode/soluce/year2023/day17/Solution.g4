grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Dimension;
import java.awt.Point;
}

root returns [ASD.Root out]
    : lines EOF {
        $out = new ASD.Root($lines.blocks, new Dimension($lines.x, $lines.y));
    }
    ;

lines returns [List<ASD.Block> blocks, int y, int x]
    @init {
        $blocks = new ArrayList<>();
        $y = 0;
    }
    : (line[$y++, $blocks] {
        $x = Math.max($x, $line.x);
    })+
    ;

line [int y, List<ASD.Block> blocks] returns [int x]
    @init {
        $x = 0;
    }
    : block[$y, $x++, $blocks]+ NEWLINE?
    ;

block [int y, int x, List<ASD.Block> blocks]
    : BLOCK {
        $blocks.add(new ASD.Block(new Point($x, $y), Integer.parseInt($BLOCK.text)));
    }
    ;

BLOCK
    // integer part forbis leading 0s (e.g. `01`)
    : [0-9]
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;