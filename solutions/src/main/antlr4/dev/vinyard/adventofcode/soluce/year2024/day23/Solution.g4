grammar Solution;

options {
    language = Java;
}

@header {
import java.util.*;
}

root returns [ASD.Root out]
    @init {
        Map<String, ASD.Computer> computers = new HashMap<>();
        List<ASD.Connection> connections = new ArrayList<>();
    }
    : (connection[computers] {
        connections.add($connection.out);
    })* EOF {
        $out = new ASD.Root(connections);
    }
    ;

connection[Map<String, ASD.Computer> computers] returns [ASD.Connection out]
    : pc1=computer[computers] TO pc2=computer[computers] {
        $out = new ASD.Connection($pc1.out, $pc2.out);
    }
    ;

computer[Map<String, ASD.Computer> computers] returns [ASD.Computer out]
    : NAME {
        $computers.putIfAbsent($NAME.text, new ASD.Computer($NAME.text));
        $out = $computers.get($NAME.text);
    }
    ;

NAME
    : [a-z]+
    ;

TO
    : '-'
    ;

WS
    : [ \t\n\r]+ -> skip
    ;