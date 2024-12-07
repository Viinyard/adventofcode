grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
}

root returns [ASD.Map out]
	@init {
		int rowNumber = 0;
	}
    : (rows+=row[rowNumber] {
      rowNumber++;
    } )+ NEWLINE*? EOF {
        int numRows = $rows.size();
        int numCols = $rows.get(0).out.size();
        ASD.Entity[][] grid = new ASD.Entity[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            grid[i] = $rows.get(i).out.toArray(new ASD.Entity[0]);
        }
        $out = new ASD.Map(grid);
    }
    ;

row[int rowNumber] returns [List<ASD.Entity> out]
		@init {
			$out = new ArrayList<>();
			int colNumber = 0;
		}
    : (cell[rowNumber, colNumber] {
        $out.add($cell.entity);
        colNumber++;
      })+ NEWLINE?
    ;

cell[int rowNumber, int colNumber] returns [ASD.Entity entity]
    @init {
        Point point = new Point(colNumber, rowNumber);
    }
    : EMPLACEMENT { $entity = new ASD.Emplacement(point); }
    | OBSTRUCTION { $entity = new ASD.Obstruction(point); }
    | GUARD {
        $entity = new ASD.Emplacement(point);
        $entity.setGuardian(new ASD.Guardian());
    }
    ;

EMPLACEMENT
		: '.'
		;

OBSTRUCTION
		: '#'
		;

GUARD
		: '^'
		;

NEWLINE
		: '\r'? '\n';

WS
    : [ \t]+ -> skip
    ;