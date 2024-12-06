grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    : map EOF {
      $out = new ASD.Root($map.out);
    }
    ;

map returns [ASD.Map out]
    : rows+=row+ {
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

row returns [List<ASD.Entity> out]
		@init {
			$out = new ArrayList<>();
		}
    : (cell {
        $out.add($cell.entity);
      })+ NEWLINE?
    ;

cell returns [ASD.Entity entity]
    : DOT { $entity = new ASD.Emplacement(); }
    | OBSTRUCTION { $entity = new ASD.Obstruction(); }
    | GUARD { $entity = new ASD.Guardian(); }
    ;

DOT
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