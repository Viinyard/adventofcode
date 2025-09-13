grammar Solution;

options {
    language = Java;
}

@header {

}

root returns [ASD.Root out]
    : elves EOF {
        $out = new ASD.Root($elves.out);
    }
    ;

elves returns [List<ASD.Elf> out]
    @init {
        $out = new ArrayList<>();
    }
    : (elf NEWLINE? { $out.add($elf.out); })+
    ;

elf returns [ASD.Elf out]
    @init {
        List<Long> calories = new ArrayList<>();
    }
    : (food[calories] { $out = new ASD.Elf(calories); } NEWLINE)+
    ;


food [List<Long> calories]
    : INT { $calories.add(Long.parseLong($INT.text)); }
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