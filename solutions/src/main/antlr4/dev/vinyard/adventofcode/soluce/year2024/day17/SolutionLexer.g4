lexer grammar SolutionLexer;

REGISTER_A
    : 'Register A:'
    ;

REGISTER_B
    : 'Register B:'
    ;

REGISTER_C
    : 'Register C:'
    ;

PROGRAM
    : 'Program:' -> pushMode(OPCODE_MODE)
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;

mode OPCODE_MODE;

COMMA_OPCODE
    : ',' -> skip
    ;

OPCODE_ADV
    : '0'  -> pushMode(COMBO_MODE)
    ;

OPCODE_BXL
    : '1'  -> pushMode(LITERAL_MODE)
    ;

OPCODE_BST
    : '2'  -> pushMode(COMBO_MODE)
    ;

OPCODE_JNZ
    : '3'  -> pushMode(LITERAL_MODE)
    ;

OPCODE_BXC
    : '4'  -> pushMode(LITERAL_MODE)
    ;

OPCODE_OUT
    : '5'  -> pushMode(COMBO_MODE)
    ;

OPCODE_BDV
    : '6'  -> pushMode(COMBO_MODE)
    ;

OPCODE_CDV
    : '7'  -> pushMode(COMBO_MODE)
    ;

WS_OPCODE
    : [ \t\n\r]+ -> skip
    ;

mode COMBO_MODE;

LITERAL
    : [0-3] -> pushMode(OPCODE_MODE)
    ;

OPERAND_A
    : '4' -> pushMode(OPCODE_MODE)
    ;

OPERAND_B
    : '5' -> pushMode(OPCODE_MODE)
    ;

OPERAND_C
    : '6' -> pushMode(OPCODE_MODE)
    ;

OPERAND_RESERVED
    : '7' -> pushMode(OPCODE_MODE)
    ;

COMMA_OPERAND
    : ',' -> skip
    ;

WS_OPERAND
    : [ \t\n\r]+ -> skip
    ;

mode LITERAL_MODE;

COMMAL_LITERAL
    : ',' -> skip
    ;

INT_LITERAL
    // integer part forbis leading 0s (e.g. `01`)
    : [0-9] -> pushMode(OPCODE_MODE)
    ;

