grammar Solution;

options {
    language = Java;
}

@header {
import java.util.*;
}

root returns [ASD.Root out]
    @init {
        Map<String, ASD.Wire> wires = new HashMap<>();
        List<ASD.Gate> gates = new ArrayList<>();
    }
    : initialization[wires]* (expression[wires] {
        gates.add($expression.out);
    })* EOF {
        $out = new ASD.Root(wires.values().stream().toList(), gates);
    }
    ;

initialization[Map<String, ASD.Wire> wires]
    : wire[wires] COLON BOOL {
        $wire.out.connect($BOOL.text.equals("1"));
    }
    ;

expression [Map<String, ASD.Wire> wires] returns [ASD.Gate out]
    : left=wire[wires] 'AND' right=wire[wires] '->' output=wire[wires] {
        $out = new ASD.AndGate($left.out, $right.out, $output.out);
    }
    | left=wire[wires] 'OR' right=wire[wires] '->' output=wire[wires] {
        $out = new ASD.OrGate($left.out, $right.out, $output.out);
    }
    | left=wire[wires] 'XOR' right=wire[wires] '->' output=wire[wires] {
        $out = new ASD.XorGate($left.out, $right.out, $output.out);
    }
    ;

wire[Map<String, ASD.Wire> wires] returns [ASD.Wire out]
    : NAME  {
        wires.putIfAbsent($NAME.text, new ASD.Wire($NAME.text));
        $out = wires.get($NAME.text);
    }
    ;

COLON
    : ':'
    ;

BOOL
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | '1'
    ;

NAME
    : [a-z0-9]+
    ;

WS
    : [ \t\n\r]+ -> skip
    ;