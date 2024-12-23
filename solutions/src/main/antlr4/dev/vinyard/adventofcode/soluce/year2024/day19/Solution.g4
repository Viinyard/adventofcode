grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    :
    towels NEWLINE*? patterns NEWLINE*? {
        $out = new ASD.Root($towels.out, $patterns.out);
    }
    ;

patterns returns [List<String> out]
    @init {
        List<String> patterns = new ArrayList<>();
    }
    :
    (STRIPES NEWLINE? {
        patterns.add($STRIPES.text);
    })+ {
        $out = patterns;
    }
    ;

towels returns [List<String> out]
    @init {
        List<String> towels = new ArrayList<>();
    }
    :
    (STRIPES COMMA? {
        towels.add($STRIPES.text);
    })+ {
        $out = towels;
    }
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

fragment WHITE: 'w';
fragment BLUE: 'u';
fragment BLACK: 'b';
fragment RED: 'r';
fragment GREEN: 'g';

COMMA
    : ','
    ;

NEWLINE
    : '\r'? '\n'
    ;

STRIPES
    : (WHITE | BLUE | BLACK | RED | GREEN)+
    ;


WS
    : [ \t]+ -> skip
    ;