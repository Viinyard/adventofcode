grammar Solution;

options {
    language = Java;
}

@header {
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
        $out.add($file.out);
        ID++;
    }) (freeSpace {
        $out.add($freeSpace.out);
    })?)+
    ;

file[int ID] returns [ASD.File out]
    : value=DIGIT {
        $out = new ASD.File(Integer.valueOf($value.text), ID);
    }
    ;

freeSpace returns [ASD.File out]
    : value=DIGIT {
        $out = new ASD.File(Integer.valueOf($value.text));
    }
    ;

DIGIT
    : [0-9]
    ;

NEWLINE : '\r'? '\n' ;

WS
    : [ \t]+ -> skip
    ;