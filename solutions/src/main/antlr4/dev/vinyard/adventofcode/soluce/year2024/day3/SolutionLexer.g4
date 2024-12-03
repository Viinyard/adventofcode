lexer grammar SolutionLexer;

MUL
    : 'mul' OPEN
    ;

COMMA
    : ','
    ;

CLOSE
    : ')'
    ;

INT
    : [0-9]+
    ;

STOP
     : DONT OPEN CLOSE -> pushMode(DONT_MODE)
     ;

CONTINUE
    : DO OPEN CLOSE -> skip
    ;

fragment DO
    : 'do'
    ;

fragment OPEN
    : '('
    ;

fragment DONT
    : 'don\'t'
    ;

mode DONT_MODE;

REDO
    : DO OPEN CLOSE -> popMode
    ;

WS
    : [ \t\n\r.]+ -> skip
    ;

OTHER
    : . -> skip
    ;
