grammar Solution;

options {
    language = Java;
}

@header {
import java.util.Map;
import java.util.HashMap;
}

root returns [ASD.Root out]
		@init {
			Map<Integer, ASD.Shape> shapes = new HashMap<>();
			List<ASD.Box> boxs = new ArrayList<>();
		}
    : gift[shapes]+ (box[shapes] { boxs.add($box.out); })+ {
      $out = new ASD.Root(boxs);
    }
    ;

gift [Map<Integer, ASD.Shape> shapes]
		: INT COLON NEWLINE shape {
			shapes.put(Integer.parseInt($INT.text), $shape.out);
		}
		;

shape returns [ASD.Shape out]
		@init {
			List<ASD.Cell> cells = new ArrayList<>();
			int y = 0;
		}
		:
		(row_shape[y++, cells])+ {
			$out = new ASD.Shape(cells);
		}
		;

row_shape [int y, List<ASD.Cell> cells] returns [int x]
		@init {
			$x = 0;
		}
		:
		(cell[$cells, $x++, $y])+ NEWLINE?
		;

cell [List<ASD.Cell> cells, int x, int y]
		:
		FILLED {
			$cells.add(new ASD.Filled(x, y));
		}
		| EMPTY {
			$cells.add(new ASD.Empty(x, y));
		}
		;

box [Map<Integer, ASD.Shape> shapes] returns [ASD.Box out]
		@init {
			int index = 0;
		}
		: x=INT MUL y=INT COLON nb_gift[shapes.get(index++)]+ NEWLINE? {
			$out = new ASD.Box(Integer.parseInt($x.text), Integer.parseInt($y.text), $nb_gift.out);
		}
		;

nb_gift [ASD.Shape s] returns [List<ASD.Gift> out]
		@init {
			$out = new ArrayList<>();
		}
		: (INT {
      $out.add(new ASD.Gift(Integer.parseInt($INT.text), s));
    })+
		;

MUL
		: 'x'
		;

FILLED
		: '#'
		;

EMPTY
		: '.'
		;

NEWLINE
		: '\r'? '\n'
		;

COLON
		: ':'
		;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;