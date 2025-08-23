grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    @init {
        ASD.Dispatcher dispatcher = new ASD.Dispatcher();
    }
    : module[dispatcher]* EOF {
        $out = new ASD.Root(dispatcher);
    }
    ;

module [ASD.Dispatcher dispatcher]
    : FLIP_FLOP NAME ARROW outputs {
        new ASD.FlipFlopModule($NAME.text, $outputs.out, dispatcher);
    }
    | BROADCAST ARROW outputs {
        new ASD.BroadcasterModule($outputs.out, dispatcher);
    }
    | CONJUNCTION NAME ARROW outputs {
        new ASD.ConjunctionModule($NAME.text, $outputs.out, dispatcher);
    }
    ;

outputs returns [List<String> out]
    @init {
        $out = new ArrayList<>();
    }
    : NAME {
        $out.add($NAME.text);
    } (COMMA NAME {
        $out.add($NAME.text);
    })*
    ;

FLIP_FLOP
    : '%'
    ;

COMMA
    : ','
    ;

ARROW
    : '->'
    ;

CONJUNCTION
    : '&'
    ;

BROADCAST
    : 'broadcaster'
    ;

NAME
    : [a-z]+
    ;

WS
    : [ \t\n\r]+ -> skip
    ;