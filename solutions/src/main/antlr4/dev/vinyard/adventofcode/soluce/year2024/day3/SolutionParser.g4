parser grammar SolutionParser;

options {
    tokenVocab = SolutionLexer;
    language = Java;
}

@header {

}

root returns [int out]
    : (mulExpr {
        $out += $mulExpr.out;
    } | .)*? EOF
    ;


mulExpr returns [int out]
    : MUL left=INT COMMA right=INT CLOSE {
        $out = Integer.parseInt($left.text) * Integer.parseInt($right.text);
    }
    ;