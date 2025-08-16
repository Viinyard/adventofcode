grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        List<ASD.Word> words = new ArrayList<>();
    }
    : (word {
        words.add($word.out);
    })* EOF {
        $out = new ASD.Root(words);
    }
    ;

word returns [ASD.Word out]
    @init {
        StringBuilder sb = new StringBuilder();
    }
    : (ASCII_CHAR {
        sb.append($ASCII_CHAR.text);
    })+ COMMA? {
        $out = new ASD.Word(sb.toString());
    }
    ;

COMMA
    : ','
    ;

WS
    : [ \t\n\r]+ -> skip
    ;

ASCII_CHAR
    : .
    ;