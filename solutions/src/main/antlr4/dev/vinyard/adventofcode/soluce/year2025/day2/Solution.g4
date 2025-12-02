grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    : intervals EOF
				{ $out = new ASD.Root($intervals.out); }
    ;

intervals returns [List<ASD.Interval> out]
	@init {
		$out = new ArrayList<>();
	}
	: (interval { $out.add($interval.out); } COMMA?)+
	;

interval returns [ASD.Interval out]
		: from=INT DASH to=INT
				{ $out = new ASD.Interval(Long.parseLong($from.text), Long.parseLong($to.text)); }
		;

DASH
		: '-'
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