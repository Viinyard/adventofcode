grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
}

root returns [ASD.TopographicMap out]
		@init {
			int rowNumber = 0;
		}
    : (rows+=row[rowNumber] {
      rowNumber++;
    })+ NEWLINE*? EOF {
      int numRows = $rows.size();
      int numCols = $rows.get(0).out.size();
      ASD.Entity[][] grid = new ASD.Entity[numRows][numCols];
      for (int i = 0; i < numRows; i++) {
          grid[i] = $rows.get(i).out.toArray(new ASD.Entity[0]);
      }
      $out = new ASD.TopographicMap(grid);
    }
    ;

row[int rowNumber] returns [List<ASD.Entity> out]
		@init {
				$out = new ArrayList();
				int colNumber = 0;
		}
		: (cell[rowNumber, colNumber] {
				$out.add($cell.out);
				colNumber++;
		})+ NEWLINE?
		;

cell[int rowNumber, int colNumber] returns [ASD.Entity out]
		@init {
				Point point = new Point(colNumber, rowNumber);
		}
		: DIGIT { $out = new ASD.Entity(point, Integer.parseInt($DIGIT.text)); }
		| DOT { $out = new ASD.Entity(point, null); }
		;

DOT
		: '.'
		;

DIGIT
    : [0-9]
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t]+ -> skip
    ;