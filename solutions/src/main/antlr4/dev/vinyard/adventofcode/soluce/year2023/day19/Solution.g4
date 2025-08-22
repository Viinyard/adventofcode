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
        List<ASD.Call> calls = new ArrayList<>();
        Map<String, ASD.Expression<Long>> functions = new HashMap<>();
    }
    : function[functions]* (call[functions] { calls.add($call.out); })* EOF {
        $out = new ASD.Root(calls, functions);
    }
    ;

function [Map<String, ASD.Expression<Long>> functions]
    : NAME OPEN_BRACE ifStatement[functions] CLOSE_BRACE {
        functions.put($NAME.text, $ifStatement.out);
    }
    ;

ifStatement [Map<String, ASD.Expression<Long>> functions] returns [ASD.IfStatement out]
    : condition COLON thenStmt=statement[functions] COMMA elseIfStmt=ifStatement[functions] {
        $out = new ASD.IfStatement($condition.out, $thenStmt.out, $elseIfStmt.out);
    }
    | condition COLON thenStmt=statement[functions] COMMA elseStmt=statement[functions] {
        $out = new ASD.IfStatement($condition.out, $thenStmt.out, $elseStmt.out);
    }
    ;

statement [Map<String, ASD.Expression<Long>> functions] returns [ASD.Statement out]
    : NAME {
        $out = new ASD.Statement($NAME.text, functions);
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

call [Map<String, ASD.Expression<Long>> functions] returns [ASD.Call out]
    : OPEN_BRACE parameters CLOSE_BRACE {
        $out = new ASD.Call("in", $parameters.out, functions);
    }
    ;

parameters returns [List<ASD.Expression<Void>> out]
    @init {
        List<ASD.Expression<Void>> params = new ArrayList<>();
    }
    : parameter[params] (COMMA parameter[params])* {
        $out = params;
    }
    ;

parameter [List<ASD.Expression<Void>> params]
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
