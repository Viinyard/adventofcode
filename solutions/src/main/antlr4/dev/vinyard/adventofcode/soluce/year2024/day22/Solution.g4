grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.Secret> secrets = new ArrayList();
    }
    : (secret {
        secrets.add($secret.out);
    })+ EOF {
        $out = new ASD.Root(secrets);
    }
    ;

secret returns [ASD.Secret out]
    : INT {
        $out = new ASD.Secret(Long.valueOf($INT.text));
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