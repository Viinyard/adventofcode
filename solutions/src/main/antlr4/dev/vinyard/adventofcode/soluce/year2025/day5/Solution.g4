grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    : ranges ingredients EOF {
			$out = new ASD.Root($ranges.out, $ingredients.out);
		}
    ;

ingredients returns [List<ASD.Ingredient> out]
	@init {
		$out = new ArrayList<>();
	}
	:
	(INT NEWLINE {
			$out.add(new ASD.Ingredient(Long.parseLong($INT.text)));
	})+
	;

ranges returns [List<ASD.Range> out]
	@init {
		$out = new ArrayList<>();
	}
	:
	(range {
			$out.add($range.out);
	})+
	;

range returns [ASD.Range out]
		:
		from=INT DASH to=INT NEWLINE? {
				$out = new ASD.Range(Long.parseLong($from.text), Long.parseLong($to.text));
		}
		;

NEWLINE
		: '\r'? '\n'
		;

DASH
		: '-'
		;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;