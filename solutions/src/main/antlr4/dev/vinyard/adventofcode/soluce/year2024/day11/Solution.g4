grammar Solution;

options {
    language = Java;
}

@header {
import java.util.function.Function;
import java.util.stream.Collectors;
}

root returns [ASD.Root out]
    @init {
        List<Long> reports = new ArrayList<>();
    }
    : (INT {
        reports.add(Long.parseLong($INT.text));
    })+ EOF {
        $out = new ASD.Root(reports.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
    }
    ;

INT
    // integer part forbis leading 0s (e.g. `01`)
    : '0' | [1-9][0-9]*
    ;

WS
    : [ \t\n\r]+ -> skip
    ;