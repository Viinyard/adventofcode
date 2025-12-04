grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Dimension;
import java.awt.Point;
}

root returns [ASD.Root out]
    : grid EOF {
				$out = new ASD.Root($grid.entities, new Dimension($grid.x, $grid.y));
		}
    ;

grid returns [List<ASD.Entity> entities, int y, int x]
		@init {
				$entities = new ArrayList<>();
				$y = 0;
		}
		:
		(row[$entities, $y++] {
				$x = Math.max($row.x, $x);
		})+
		;

row [List<ASD.Entity> entities, int y] returns [int x]
		@init {
				$x = 0;
		}
		:
		(entity[$entities, $x++, $y])+ NEWLINE?
		;

entity [List<ASD.Entity> entities, int x, int y] returns [ASD.Entity out]
		: PAPER {
			$entities.add(new ASD.Paper(new Point($x, $y)));
		}
		| EMPTY {
			$entities.add(new ASD.Empty(new Point($x, $y)));
		}
		;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

PAPER
		: '@'
		;

NEWLINE
		: '\r'? '\n'
		;

EMPTY
		: '.'
		;

WS
    : [ \t\n\r]+ -> skip
    ;