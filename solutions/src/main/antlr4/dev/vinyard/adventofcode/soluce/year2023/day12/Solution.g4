grammar Solution;

options {
    language = Java;
}

@header {
import java.util.LinkedList;
}

root returns [ASD.Root out]
    @init {
        ArrayList<ASD.Line> lines = new ArrayList<>();
    }
    : (springs condition_records NEWLINE?  {
        lines.add(new ASD.Line($springs.out, $condition_records.out));
    })+ EOF {
        $out = new ASD.Root(lines);
    }
    ;

springs returns [String out]
    @init {
        $out = "";
    }
    : (spring {
        $out += $spring.out;
    })+
    ;

condition_records returns [LinkedList<Integer> out]
    @init {
        $out = new LinkedList<>();
    }
    : (condition_record {
        $out.add($condition_record.out);
    })+
    ;

condition_record returns [Integer out]
    : INT COMMA? {
        $out = Integer.parseInt($INT.text);
    }
    ;


spring returns [String out]
    : DAMAGED_SPRING {
        $out = ASD.DAMAGED;
    }
    | OPERATIONAL_SPRING {
        $out = ASD.OPERATIONAL;
    }
    | UNKNOWN_SPRING {
        $out = ASD.UNKNOWN;
    }
    ;

DAMAGED_SPRING
    : '#'
    ;

OPERATIONAL_SPRING
    : '.'
    ;

UNKNOWN_SPRING
    : '?'
    ;

COMMA
    : ','
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

NEWLINE
    : '\r'? '\n'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;