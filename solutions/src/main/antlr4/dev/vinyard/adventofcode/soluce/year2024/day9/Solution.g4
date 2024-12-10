grammar Solution;

options {
    language = Java;
}

@header {
import java.util.stream.Stream;
import java.util.stream.Collectors;
}

root returns [ASD.DiskMap out]
    : disk NEWLINE*? {
        $out = new ASD.DiskMap($disk.out);
    }
    ;

disk returns [List<ASD.File> out]
    @init {
        $out = new ArrayList<>();
        int ID = 0;
    }
    : ((file[ID] {
        $out.addAll($file.out);
        ID++;
    }) (freeSpace {
        $out.addAll($freeSpace.out);
    })?)+
    ;

file[int ID] returns [List<ASD.File> out]
    : value=DIGIT {
        $out = Stream.generate(() -> new ASD.File(ID)).limit(Integer.parseInt($value.text)).toList();
    }
    ;

freeSpace returns [List<ASD.File> out]
    : value=DIGIT {
        $out = Stream.generate(() -> new ASD.File(null)).limit(Integer.parseInt($value.text)).toList();
    }
    ;

DIGIT
    : [0-9]
    ;

NEWLINE : '\r'? '\n' ;

WS
    : [ \t]+ -> skip
    ;