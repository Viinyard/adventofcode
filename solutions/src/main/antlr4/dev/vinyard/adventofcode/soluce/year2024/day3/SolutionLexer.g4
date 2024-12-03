lexer grammar SolutionLexer;

@members {
    public static final int ENABLED = 1;
    public static final int DISABLED = 2;
}

MUL
    : 'mul('
    ;

COMMA
    : ',';

fragment OPEN
    : '(';

CLOSE
    : ')';

INT
    : [0-9]+;

fragment DONT
    : 'don\'t'
    ;

STOP
     : DONT OPEN CLOSE -> pushMode(DONT_MODE);

fragment DO
    : 'do';

CONTINUE
    : DO OPEN CLOSE -> skip;

mode DONT_MODE;

REDO
    : DO OPEN CLOSE -> popMode;

WS
    : [ \t\n\r]+ -> skip
    ;

OTHER
    : . -> skip
    ;




