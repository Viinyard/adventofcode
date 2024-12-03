grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [int out]
    : (mulExpr {
        $out += $mulExpr.out;
    } | . )*? EOF
    ;

mulExpr returns [int out]
    : 'mul(' left=INT ',' right=INT ')' {
        $out = Integer.parseInt($left.text) * Integer.parseInt($right.text);
    }
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;