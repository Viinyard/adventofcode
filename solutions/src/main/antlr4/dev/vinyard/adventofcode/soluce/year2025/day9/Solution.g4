grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
		@init {
				List<ASD.Tile> tiles = new ArrayList<>();
		}
    : (tile {
				tiles.add($tile.out);
		})+ EOF {
				$out = new ASD.Root(tiles);
		}
    ;

tile returns [ASD.Tile out]
		: x=INT COMMA y=INT {
				$out = new ASD.Tile(Integer.parseInt($x.text), Integer.parseInt($y.text));
		}
		;

COMMA
		: ','
		;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;