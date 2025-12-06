grammar Solution;

options {
    language = Java;
}

@header {
import java.util.Map;
import java.util.HashMap;
}

root returns [ASD.Root out]
    @init {
        Map<Integer, ASD.Problem> problems = new HashMap<>();
    }
    : numbers[problems]+ operations[problems] NEWLINE* EOF {
        $out = new ASD.Root(problems.values());
    }
    ;

number [int x, Map<Integer, ASD.Problem> problems]
    : INT {
        problems.merge(x, new ASD.Problem(Long.parseLong($INT.text)), (oldP, newP) -> oldP.merge(newP));
    }
    ;

numbers [Map<Integer, ASD.Problem> problems]
    @init {
        int x = 0;
    }
    : number[x++, problems]+ NEWLINE?
    ;

operations [Map<Integer, ASD.Problem> problems]
    @init {
        int x = 0;
    }
    : operation[x++, problems]+ NEWLINE?
    ;

operation [int x, Map<Integer, ASD.Problem> problems]
    : ADD {
        problems.get(x).setOperation(ASD.Operation.ADD);
    }
    | MUL {
        problems.get(x).setOperation(ASD.Operation.MULTIPLY);
    }
    ;

MUL
    : '*'
    ;

ADD
    : '+'
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