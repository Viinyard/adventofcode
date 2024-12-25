grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.Code> codes = new ArrayList();
    }
    : (code {
        codes.add($code.out);
    })+ EOF {
        $out = new ASD.Root(codes);
    }
    ;

code returns [ASD.Code out]
    : INT A {
        $out = new ASD.Code($INT.text);
    }
    ;

A
    : 'A'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : [0-9]+
    ;

WS
    : [ \t\n\r]+ -> skip
    ;