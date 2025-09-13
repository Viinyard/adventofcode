grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.Pair> pairs = new ArrayList<>();
    }
    : pair[pairs]+ EOF {
        $out = new ASD.Root(pairs);
    }
    ;

pair [List<ASD.Pair> pairs]
    : left=section ',' right=section { $pairs.add(new ASD.Pair($left.out, $right.out)); }
    ;

section returns [ASD.Section out]
    : min=value '-' max=value { $out = new ASD.Section($min.out, $max.out); }
    ;

value returns [long out]
    : INT { $out = Long.parseLong($INT.getText()); }
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;