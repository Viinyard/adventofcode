grammar Solution;

options {
    language = Java;
}

@header {
import java.awt.Point;
}

root returns [ASD.Root out]
    : map EOF {
      $out = new ASD.Root($map.out);
    }
    ;

map returns [ASD.Map out]
		@init {
			int rowNumber = 0;
		}
    : (rows+=row[rowNumber] {
      rowNumber++;
    } )+ {
        int numRows = $rows.size();
        int numCols = $rows.get(0).out.size();
        ASD.Entity[][] grid = new ASD.Entity[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            grid[i] = $rows.get(i).out.toArray(new ASD.Entity[0]);
        }
        $out = new ASD.Map(grid);
    }
    | NEWLINE
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
    : EMPLACEMENT { $entity = new ASD.Emplacement(); }
    | OBSTRUCTION { $entity = new ASD.Obstruction(); }
    | GUARD {
        $entity = new ASD.Emplacement();
        $entity.setGuardian(new ASD.Guardian(new Point(colNumber, rowNumber)));
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