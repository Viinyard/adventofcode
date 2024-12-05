grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<List<String>> lines = new ArrayList<>();
    }
    : (line {
        lines.add($line.out);
    })* EOF {
        String[][] array = lines.stream()
                        .filter(l -> !l.isEmpty())
                        .map(l -> l.toArray(String[]::new))
                        .toArray(String[][]::new);
        $out = new ASD.Root(array);
    }
    ;

line returns [List<String> out]
    @init {
        $out = new ArrayList<>();
    }
    : (value=CHAR {
        $out.add($value.text);
    })+ NEWLINE?
    | NEWLINE
    ;

CHAR
    : (X | M | A | S)
    ;

fragment X: 'X';
fragment M: 'M';
fragment A: 'A';
fragment S: 'S';

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t]+ -> skip
    ;