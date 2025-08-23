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
        List<ASD.Rating> ratings = new ArrayList<>();
        Map<String, ASD.Expression<Long>> registry = new HashMap<>();

        registry.put("A", (h) -> h.values().stream().mapToLong(l -> l).sum());
        registry.put("R", (h) -> 0L);
    }
    : workflow[registry]* (rating[registry] { ratings.add($rating.out); })* EOF {
        $out = new ASD.Root(ratings, registry);
    }
    ;

workflow [Map<String, ASD.Expression<Long>> registry]
    : NAME OPEN_BRACE r=rule[registry] CLOSE_BRACE {
        registry.put($NAME.text, $r.out);
    }
    ;

rule [Map<String, ASD.Expression<Long>> registry] returns [ASD.IfStatement out]
    : condition COLON thenStmt=statement[registry] COMMA elseRule=rule[registry] {
        $out = new ASD.IfStatement($condition.out, $thenStmt.out, $elseRule.out);
    }
    | condition COLON thenStmt=statement[registry] COMMA elseStmt=statement[registry] {
        $out = new ASD.IfStatement($condition.out, $thenStmt.out, $elseStmt.out);
    }
    ;

statement [Map<String, ASD.Expression<Long>> registry] returns [ASD.Statement out]
    : NAME {
        $out = new ASD.Statement($NAME.text, registry);
    }
    ;

condition returns [ASD.Expression<Boolean> out]
    : NAME OP_INF INT {
        $out = new ASD.LessThan($NAME.text, Integer.parseInt($INT.text));
    }
    | NAME OP_SUP INT {
        $out = new ASD.GreaterThan($NAME.text, Integer.parseInt($INT.text));
    }
    ;

rating [Map<String, ASD.Expression<Long>> registry] returns [ASD.Rating out]
    : OPEN_BRACE categories CLOSE_BRACE {
        $out = new ASD.Rating("in", $categories.out, registry);
    }
    ;

categories returns [List<ASD.Expression<Void>> out]
    @init {
        List<ASD.Expression<Void>> params = new ArrayList<>();
    }
    : category[params] (COMMA category[params])* {
        $out = params;
    }
    ;

category [List<ASD.Expression<Void>> params]
    : NAME ASSIGN INT {
        params.add(new ASD.Assignment($NAME.text, new ASD.Constant(Long.parseLong($INT.text))));
    }
    ;


OPEN_BRACE : '{';
CLOSE_BRACE : '}';
COMMA      : ',';
COLON      : ':';
ASSIGN     : '=';
OP_INF : '<';
OP_SUP : '>';
NAME       : [a-zA-Z]+;
VAR        : [xmas];
INT        : [0-9]+;
WS         : [ \t\r\n]+ -> skip;
