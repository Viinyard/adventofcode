grammar Solution;

options {
    language = Java;
}

@header {
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
}

root returns [ASD.Root out]
    @init {
       LinkedList<ASD.CrateStack> stacks = new LinkedList<>();
       Map<Integer, ASD.CrateStack> map = new HashMap<>();
       LinkedList<ASD.Move> moves = new LinkedList<>();
    }
    : crates[stacks]+ columns[stacks, map] move[map, moves]* EOF {
        $out = new ASD.Root(map, moves);
    }
    ;

crates [LinkedList<ASD.CrateStack> stacks]
    @init {
        int x = 0;
    }
    : (crate[stacks, x++])+ NEWLINE?
    ;

move [Map<Integer, ASD.CrateStack> map, List<ASD.Move> moves]
    : MOVE SPACE+ quantity=INT SPACE+ FROM SPACE+ from=INT SPACE+ TO SPACE+ to=INT NEWLINE? {
        moves.add(new ASD.Move(
            Integer.parseInt($quantity.text),
            map.get(Integer.parseInt($from.text)),
            map.get(Integer.parseInt($to.text))
        ));
    }
    ;

columns [LinkedList<ASD.CrateStack> stacks, Map<Integer, ASD.CrateStack> map]
    : ( column[stacks.pollFirst(), map] SPACE?)+  NEWLINE?
    ;

column [ASD.CrateStack stack, Map<Integer, ASD.CrateStack> map]
    : SPACE INT SPACE {
        map.put(Integer.parseInt($INT.text), stack);
    }
    ;

crate [LinkedList<ASD.CrateStack> stacks, int x]
    : OPEN_BRACKET LETTER CLOSE_BRACKET SPACE? {
        if (stacks.size() <= x)
            stacks.add(new ASD.CrateStack());
        stacks.get(x).push(new ASD.Crate($LETTER.text));
    }
    | SPACE SPACE SPACE SPACE? {
        if (stacks.size() <= x)
            stacks.add(new ASD.CrateStack());
    }
    ;

OPEN_BRACKET
    : '['
    ;

CLOSE_BRACKET
    : ']'
    ;

MOVE
    : 'move'
    ;

FROM
    : 'from'
    ;

TO
    : 'to'
    ;

LETTER
    : [A-Z]
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

SPACE
    : ' '
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [\t\n\r]+ -> skip
    ;