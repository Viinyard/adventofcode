grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.Equation> equations = new ArrayList<>();
    }
    : (equation {
        equations.add($equation.out);
    })+ NEWLINE*? EOF {
        $out = new ASD.Root(equations);
    }
    ;

equation returns [ASD.Equation out]
    @init {
        List<Long> values = new ArrayList<>();
    }
    : result=number COLON (value=number {
        values.add($value.out);
    })+ NEWLINE? {
        $out = new ASD.Equation($result.out, values);
    }
    ;

number returns [long out]
    : INT { $out = Long.parseLong($INT.text); }
    ;

COLON
    : ':'
    ;

NEWLINE
    : '\r'? '\n'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t]+ -> skip
    ;