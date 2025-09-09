grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.Hail> hails = new ArrayList<>();
    }
    : bounds (hail { hails.add($hail.out); })* EOF { $out = new ASD.Root(hails, $bounds.out); }
    ;

hail returns [ASD.Hail out]
    : position AT velocity { $out = new ASD.Hail($position.out, $velocity.out); }
    ;

position returns [ASD.Position out]
    : x=value COMMA y=value COMMA z=value { $out = new ASD.Position($x.out, $y.out, $z.out); }
    ;

velocity returns [ASD.Velocity out]
    : x=value COMMA y=value COMMA z=value { $out = new ASD.Velocity($x.out, $y.out, $z.out); }
    ;

value returns [double out]
    : INT { $out = Double.valueOf($INT.text); }
    ;

bounds returns [ASD.Bounds out]
    : BOUNDS EQ min=value COMMA max=value {
        $out = new ASD.Bounds($min.out, $max.out);
    }
    ;

BOUNDS
    : 'Bounds'
    ;

EQ
    : '='
    ;



AT
    : '@'
    ;

COMMA
    : ','
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '-'? ('0' | [1-9][0-9]*)
    ;

WS
    : [ \t\n\r]+ -> skip
    ;