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
        List<char[]> matrix = new ArrayList<>();
    }
    : (row { matrix.add($row.out); })+ {
        char[][] problems = new char[matrix.size()][];
        for (int i = 0; i < matrix.size(); i++) {
            problems[i] = matrix.get(i);
        }

        $out = new ASD.Root(problems);
    }
    ;

char [StringBuilder sb]
    : INT {
        sb.append($INT.text);
    }
    | SPACE {
        sb.append($SPACE.text);
    }
    | MUL {
        sb.append($MUL.text);
    }
    | ADD {
        sb.append($ADD.text);
    }
    ;

row returns [char[] out]
    @init {
        StringBuilder sb = new StringBuilder();
    }
    :
    char[sb]+ NEWLINE? {
        $out = sb.toString().toCharArray();
    }
    ;

MUL
    : '*'
    ;

ADD
    : '+'
    ;

SPACE
    : ' '
    ;

NEWLINE
    : '\r'? '\n'
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : [0-9]
    ;

WS
    : [\t]+ -> skip
    ;