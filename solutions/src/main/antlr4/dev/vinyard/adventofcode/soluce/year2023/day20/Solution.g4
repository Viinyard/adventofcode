grammar Solution;

options {
    language = Java;
}

@header {

}

register returns [ASD.Registry out]
    @init {
        $out = new ASD.Registry();
    }
    : moduleRegistry[ $out ]* EOF {
        $out.computeAll();
    }
    ;

moduleRegistry [ASD.Registry registry]
    : moduleTypeRegistry outputRegistry[registry] {
        $registry.registerModule($moduleTypeRegistry.out);
    }
    ;

moduleTypeRegistry returns [ASD.Module out]
    : FLIP_FLOP NAME ARROW {
        $out = new ASD.FlipFlopModule($NAME.text);
    }
    | BROADCAST ARROW {
        $out = new ASD.BroadcasterModule();
    }
    | CONJUNCTION NAME ARROW {
        $out = new ASD.ConjunctionModule($NAME.text);
    }
    ;

outputRegistry [ASD.Registry registry]
    : NAME {
        $registry.registerModule($NAME.text);
    } (COMMA NAME {
        $registry.registerModule($NAME.text);
    })*
    ;

root [ASD.Registry registry] returns [ASD.Root out]
    : module[registry]* EOF {
        $out = new ASD.Root(registry);
        ASD.Module button = new ASD.ButtonModule();
        registry.registerModule(button);
        button.addOutput(registry.getModule("broadcaster"));
    }
    ;

module [ASD.Registry registry]
    : FLIP_FLOP NAME ARROW outputs[registry] {
        $outputs.out.forEach(output -> registry.getModule($NAME.text).addOutput(output));
    }
    | BROADCAST ARROW outputs[registry] {
        $outputs.out.forEach(output -> registry.getModule($BROADCAST.text).addOutput(output));
    }
    | CONJUNCTION NAME ARROW outputs[registry] {
        $outputs.out.forEach(output -> registry.getModule($NAME.text).addOutput(output));
    }
    ;

outputs [ASD.Registry registry] returns [List<ASD.Module> out]
    @init {
        $out = new ArrayList<>();
    }
    : NAME {
        $out.add(registry.getModule($NAME.text));
    } (COMMA NAME {
        $out.add(registry.getModule($NAME.text));
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